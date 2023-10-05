package com.mobingc.personal.common.utils;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.Duration;
import java.util.*;

/**
 * 蓝奏云工具类
 */
public class LanZouUtils {

    /**
     * 登录接口地址
     */
    private static final String LOGIN_URL = "https://pc.woozooo.com/account.php";

    /**
     * 上传接口地址
     */
    private static final String UPLOAD_URL = "https://pc.woozooo.com/fileup.php";

    /**
     * HTTP请求客户端
     */
    private static final RestTemplate restTemplate;

    /**
     * 请求头
     */
    private static final MultiValueMap<String, String> headerMap;

    static {
        restTemplate = new RestTemplateBuilder()
                .requestFactory(NoRedirectSimpleClientHttpRequestFactory.class)
                .setConnectTimeout(Duration.ofMillis(30000))
                .build();
        headerMap = new LinkedMultiValueMap<>(5);
        headerMap.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        headerMap.add("Referer", "https://pc.woozooo.com/mydisk.php");
        headerMap.add("Accept-Language", "zh-CN,zh;q=0.9");
    }

    private LanZouUtils() {
    }

    /**
     * 登录，获取Cookie
     *
     * @param yLogin      用户登录ID（在浏览器登录后从Cookie中获取）
     * @param phpDiskInfo PHP磁盘信息（在浏览器登录后从Cookie中获取）
     * @return Cookie列表
     */
    public static List<String> login(String yLogin, String phpDiskInfo) {

        // 创建Cookie列表
        List<String> cookieList = new ArrayList<>();
        cookieList.add("ylogin=" + yLogin);
        cookieList.add("phpdisk_info=" + phpDiskInfo);

        // 构建请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.addAll(headerMap);
        requestHeaders.put(HttpHeaders.COOKIE, cookieList);

        // 构建请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);

        // 发送登录请求
        ResponseEntity<String> response = restTemplate.exchange(LOGIN_URL, HttpMethod.GET, requestEntity, String.class);

        // 从响应体Cookie中获取PHPSESSID并加入当前Cookie列表
        String responseCookie = Objects.requireNonNull(response.getHeaders().get("Set-Cookie")).get(0);
        String phpSessionId = responseCookie.substring(0, responseCookie.indexOf(';'));
        cookieList.add(phpSessionId);

        return cookieList;
    }

    /**
     * 上传文件
     *
     * @param multipartFile 上传文件
     * @param folderId      文件夹ID（根目录：-1）
     * @param cookieList    用户Cookie列表
     * @return 文件下载路径
     */
    public static String upload(MultipartFile multipartFile, String folderId, List<String> cookieList) {

        // 文件流
        CommonInputStreamResource fileStream;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String nameFilename = Objects.requireNonNull(originalFilename).substring(0, originalFilename.lastIndexOf('.')) + ".zip";
            fileStream = new CommonInputStreamResource(multipartFile.getInputStream(), multipartFile.getSize(), nameFilename);
        } catch (IOException e) {
            throw new RuntimeException("上传文件读取失败!", e);
        }

        // 构建请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.addAll(headerMap);
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        requestHeaders.put(HttpHeaders.COOKIE, cookieList);

        // 构建请求体
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("task", "1");
        requestBody.add("folder_id", folderId);
        //  requestBody.add("folder_id", "6201883");
        requestBody.add("id", "WU_FILE_0");
        requestBody.add("name", fileStream.getFilename());
        requestBody.add("upload_file", fileStream);

        // 构建请求实体
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, requestHeaders);

        // 发送上传请求
        ResponseEntity<String> response = restTemplate.postForEntity(UPLOAD_URL, requestEntity, String.class);

        // 解析返回体
        Map<String, Object> body = JSONUtils.parse2Map(response.getBody());
        List<?> text = (List<?>) Objects.requireNonNull(body).get("text");
        Map<?, ?> fileInfo = (Map<?, ?>) text.get(0);
        String host = (String) fileInfo.get("is_newd");
        String id = (String) fileInfo.get("f_id");

        // 返回文件下载路径
        return host + "/" + id;
    }

    /**
     * 将外链解析为直链
     *
     * @param shareURL 分享外链
     * @return 直链
     */
    public static String parseDirectURL(String shareURL) {

        // 修改链接
        int last = shareURL.lastIndexOf('/');
        shareURL = shareURL.substring(0, last) + "/tp" + shareURL.substring(last);

        // 构建请求头，模拟手机
        Map<String, String> headers = new HashMap<>(2);
        headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 9; zh-cn; Redmi Note 5 Build/PKQ1.180904.001) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.141 Mobile Safari/537.36 XiaoMi/MiuiBrowser/11.10.8");
        headers.put("Accept-Language", "zh");

        // 请求文件下载页面
        Document downloadDocument;
        try {
            downloadDocument = Jsoup.connect(shareURL)
                    .headers(headers)
                    .timeout(30000)
                    .get();
        } catch (IOException e) {
            throw new RuntimeException("页面请求失败!", e);
        }

        // 解析伪直链
        String tmpDirectURL;
        Element script = downloadDocument.getElementsByTag("script").get(0);
        String scriptText = script.html();
        String prefix = "(function(){var document={getElementById:function(){return document;}};";
        String suffix = "for(var k in document)typeof document[k]==\"function\"&&document[k]();return document.href;})()";
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        try {
            tmpDirectURL = (String) engine.eval(prefix + scriptText + suffix);
        } catch (ScriptException e) {
            throw new RuntimeException("蓝奏云直链解析有变动，解析失败!");
        }

        // 通过伪直链获取直链
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.addAll(headerMap);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(tmpDirectURL, HttpMethod.GET, requestEntity, String.class);
        List<String> location = response.getHeaders().get("Location");
        if (location == null) {
            throw new RuntimeException("伪直链未重定向直链，解析失败!");
        }

        return location.get(0);
    }

    /**
     * 自动重定向取消
     */
    private static class NoRedirectSimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory {
        @Override
        protected void prepareConnection(@NotNull HttpURLConnection connection, @NotNull String httpMethod) throws IOException {
            super.prepareConnection(connection, httpMethod);
            connection.setInstanceFollowRedirects(false);
        }
    }

    /**
     * 输入流资源对象
     */
    public static class CommonInputStreamResource extends InputStreamResource {

        private long length;
        private String fileName;

        public CommonInputStreamResource(InputStream inputStream, long length, String fileName) {
            super(inputStream);
            this.setLength(length);
            this.setFileName(fileName);
        }

        /**
         * 覆写父类方法
         * 如果不重写这个方法，并且文件有一定大小，那么服务端会出现异常
         * {@code The multi-part request contained parameter data (excluding uploaded files) that exceeded}
         */
        @Override
        public String getFilename() {
            return fileName;
        }

        /**
         * 覆写父类 contentLength 方法
         * 因为 {@link org.springframework.core.io.AbstractResource#contentLength()}方法会重新读取一遍文件，
         * 而上传文件时，restTemplate 会通过这个方法获取大小。然后当真正需要读取内容的时候，发现已经读完，会报如下错误。
         * <code>
         * java.lang.IllegalStateException: InputStream has already been read - do not use InputStreamResource if a stream needs to be read multiple times
         * at org.springframework.core.io.InputStreamResource.getInputStream(InputStreamResource.java:96)
         * </code>
         * <p>
         * ref:com.amazonaws.services.s3.model.S3ObjectInputStream#available()
         */
        @Override
        public long contentLength() {
            long estimate = length;
            return estimate == 0 ? 1 : estimate;
        }

        public void setLength(long length) {
            this.length = length;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

    }

}

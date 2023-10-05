package com.mobingc.personal.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mobingc.personal.common.consts.SettingConst;
import com.mobingc.personal.common.exception.BizException;
import com.mobingc.personal.common.utils.AssertUtils;
import com.mobingc.personal.common.utils.LanZouUtils;
import com.mobingc.personal.model.dto.ImageUploadDTO;
import com.mobingc.personal.model.entity.Setting;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.repository.SettingRepository;
import com.mobingc.personal.service.ImageService;
import com.mobingc.personal.common.utils.DateConvertUtils;
import com.mobingc.personal.common.utils.JSONUtils;
import com.mobingc.personal.common.Validators;
import com.mobingc.personal.service.NoSqlService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * <p>
 * 图片 服务实现类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Service
public class ImageServiceImpl implements ImageService {

    private final RestTemplate restTemplate;

    private final SettingRepository settingRepository;

    private final NoSqlService noSqlService;

    public ImageServiceImpl(RestTemplate restTemplate, SettingRepository settingRepository, NoSqlService noSqlService) {
        this.restTemplate = restTemplate;
        this.settingRepository = settingRepository;
        this.noSqlService = noSqlService;
    }

    @Override
    public String upload(MultipartFile multipartFile) {

        // 蓝奏云配置项
        String yLogin;
        String phpDiskInfo;
        String folderId;

        // 获取蓝奏云配置
        String lanzouSettingKey = SettingConst.Lanzou.THIS;
        Map<String, Object> lanzouMap = noSqlService.getObject(lanzouSettingKey, new TypeReference<Map<String, Object>>(){});
        if (lanzouMap == null) {
            Setting lanzouSetting = settingRepository.findByKey(lanzouSettingKey);
            AssertUtils.isTrue(lanzouSetting, AssertUtils::isNotNull, () -> new BizException(ResultCode.SETTING_IS_NOT_FOUND));
            lanzouMap = JSONUtils.parse2Map(lanzouSetting.getValue());
            AssertUtils.isTrue(lanzouMap, AssertUtils::isNotNull, () -> new BizException(ResultCode.SETTING_VALUE_IS_INCOMPLETE));
            noSqlService.setObject(lanzouSettingKey, lanzouMap);
        }

        // 获取配置项
        yLogin = (String) Objects.requireNonNull(lanzouMap).get(SettingConst.Lanzou.Y_LOGIN);
        AssertUtils.isTrue(yLogin, AssertUtils::isNotNull, () -> new BizException(ResultCode.SETTING_VALUE_IS_INCOMPLETE));
        phpDiskInfo = (String) lanzouMap.get(SettingConst.Lanzou.PHPDISK_INFO);
        AssertUtils.isTrue(phpDiskInfo, AssertUtils::isNotNull, () -> new BizException(ResultCode.SETTING_VALUE_IS_INCOMPLETE));
        folderId = (String) lanzouMap.get(SettingConst.Lanzou.FOLDER_ID);
        AssertUtils.isTrue(folderId, AssertUtils::isNotNull, () -> new BizException(ResultCode.SETTING_VALUE_IS_INCOMPLETE));

        // 获取蓝奏云Cookie列表
        String lanzouCookieKey = DigestUtils.md5DigestAsHex((yLogin + phpDiskInfo).getBytes());
        List<String> cookieList = noSqlService.getObject(lanzouCookieKey, new TypeReference<List<String>>(){});
        if (cookieList == null) {
            cookieList = LanZouUtils.login(yLogin, phpDiskInfo);
            noSqlService.setObject(lanzouCookieKey, cookieList, 1800L);
        }

        // 上传
        String shareURL = LanZouUtils.upload(multipartFile, folderId, cookieList);

        // 获取文件信息
        String lanzouDownloadOrigin = shareURL.substring(0, shareURL.lastIndexOf('/') + 1);
        String fileId = shareURL.substring(shareURL.lastIndexOf('/') + 1);
        noSqlService.set("lanzou_download_origin", lanzouDownloadOrigin);

        return fileId;
    }

    @Override
    public String getDownloadURL(String fileId) {
        String imageURLKey = "lanzou_image_url_by_" + fileId;
        String imageURL = noSqlService.get(imageURLKey);
        if (imageURL == null) {
            String lanzouDownloadOrigin = noSqlService.get("lanzou_download_origin");
            if (lanzouDownloadOrigin == null) {
                Setting lanzouSetting = settingRepository.findByKey(SettingConst.Lanzou.THIS);
                AssertUtils.isTrue(lanzouSetting, AssertUtils::isNotNull, () -> new BizException(ResultCode.SETTING_IS_NOT_FOUND));
                Map<String, Object> lanzouMap = JSONUtils.parse2Map(lanzouSetting.getValue());
                AssertUtils.isTrue(lanzouMap, AssertUtils::isNotNull, () -> new BizException(ResultCode.SETTING_VALUE_IS_INCOMPLETE));
                lanzouDownloadOrigin = (String) Objects.requireNonNull(lanzouMap).get(SettingConst.Lanzou.DOWNLOAD_ORIGIN);
                noSqlService.set("lanzou_download_origin", lanzouDownloadOrigin);
            }
            imageURL = LanZouUtils.parseDirectURL(lanzouDownloadOrigin + fileId);
            noSqlService.set(imageURLKey, imageURL, 1800L);
        }
        return imageURL;
    }

    /**
     * Github上传图片
     */
    public Map<String, Object> uploadImage(ImageUploadDTO imageUploadDTO) {

        // 参数校验
        Validators.validate(imageUploadDTO);

        // 获取GitHub配置项
        Map<String, String> settingMap = this.getGitHubSettingMap();

        // 文件名
        String fileName = DateConvertUtils.toDateString(new Date(), "yyyyMMddHHmmss") + "." + imageUploadDTO.getType();
        // 用户
        String user = settingMap.get(SettingConst.GITHUB_USER);
        // 邮箱
        String email = settingMap.get(SettingConst.GITHUB_EMAIL);
        // 图片仓库
        String repo = settingMap.get(SettingConst.GITHUB_PICTURE_REPOSITORY);
        // 图片路径
        String path = settingMap.get(SettingConst.GITHUB_PICTURE_PATH);
        // 令牌
        String token = settingMap.get(SettingConst.GITHUB_TOKEN);

        // 图片上传请求路径
        String url = "https://api.github.com/repos/" + user + "/" + repo + "/contents/" + path + "/" + fileName;
        // 构建文件上传HTTP实体对象
        HttpEntity<String> entity = this.buildUploadFileHttpEntity(user, email, repo, path, imageUploadDTO.getImageBase64(), token);

        // 上传图片
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        Map<String, Object> resData = JSONUtils.parse2Map(response.getBody());

        if (!HttpStatus.CREATED.equals(response.getStatusCode()) || resData == null) {
            throw new RuntimeException("图片上传失败!");
        }

        // 代理域名
        String proxy = settingMap.get(SettingConst.GITHUB_PROXY);
        // 仓库分支
        String branch = settingMap.get(SettingConst.GITHUB_BRANCH);
        // 图片地址
        String imageURL = "https://" + proxy + "/" + user + "/" + repo + "/" + branch + "/" + path + "/" + fileName;
        resData.put("imageURL", imageURL);

        return resData;
    }

    /**
     * 获取GitHub配置项
     */
    private Map<String, String> getGitHubSettingMap() {
        List<String> settingNameList = Arrays.asList(
                SettingConst.GITHUB_USER,
                SettingConst.GITHUB_EMAIL,
                SettingConst.GITHUB_PICTURE_REPOSITORY,
                SettingConst.GITHUB_PICTURE_PATH,
                SettingConst.GITHUB_TOKEN,
                SettingConst.GITHUB_PROXY,
                SettingConst.GITHUB_BRANCH);
        List<Setting> settingList = settingRepository.selectAllByKeyIn(settingNameList);
        if (settingList == null || settingList.size() < settingNameList.size()) {
            throw new BizException(ResultCode.MISSING_SETTING_PROPERTY);
        }
        Map<String, String> settingMap = new HashMap<>(settingNameList.size());
        for (Setting setting : settingList) {
            settingMap.put(setting.getKey(), setting.getValue());
        }
        return settingMap;
    }

    /**
     * 构建文件上传HTTP实体对象
     *
     * @param user    用户名
     * @param email   邮箱
     * @param repo    项目名
     * @param path    存放路径
     * @param content 上传内容（base64编码字符串）
     * @param token   认证令牌
     * @return HTTP实体对象
     */
    private HttpEntity<String> buildUploadFileHttpEntity(String user, String email, String repo, String path, String content, String token) {
        Map<String, String> committer = new HashMap<>();
        committer.put("name", user);
        committer.put("email", email);
        Map<String, Object> json = new HashMap<>();
        json.put("owner", user);
        json.put("repo", repo);
        json.put("path", path);
        json.put("message", "Upload by Cloudflare Workers");
        json.put("committer", committer);
        json.put("content", content);
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", user);
        headers.set("Content-Type", "application/json");
        headers.set("authorization", "token " + token);
        return new HttpEntity<>(JSONUtils.toJSONString(json), headers);
    }

}

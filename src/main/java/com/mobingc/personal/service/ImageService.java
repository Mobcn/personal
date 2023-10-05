package com.mobingc.personal.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 图片 服务类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
public interface ImageService {

    /**
     * 上传图片
     *
     * @param multipartFile 图片上传文件
     * @return 图片ID
     */
    String upload(MultipartFile multipartFile);

    /**
     * 获取图片下载地址
     *
     * @param fileId 文件ID
     * @return 图片下载地址
     */
    String getDownloadURL(String fileId);

}

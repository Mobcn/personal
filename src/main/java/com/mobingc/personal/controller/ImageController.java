package com.mobingc.personal.controller;


import com.mobingc.personal.common.Result;
import com.mobingc.personal.common.ResultMap;
import com.mobingc.personal.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * <p>
 * 图片 前端控制器
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Api(tags = "图片管理")
@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @ApiOperation(value = "图片上传", notes = "图片上传")
    @PostMapping("/upload")
    public Result<ResultMap> uploadImage(@ApiParam(name = "multipartFile", value = "图片上传文件") @RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        String fileId = imageService.upload(multipartFile);
        String requestURL = request.getRequestURL().toString();
        if (requestURL.charAt(requestURL.length() - 1) == '/') {
            requestURL = requestURL.substring(0, requestURL.length() - 1);
        }
        String fileURL = new StringBuffer(requestURL)
                .replace(requestURL.length() - "upload".length(), requestURL.length(), "download/")
                .append(fileId)
                .toString();
        ResultMap resultMap = ResultMap.create().put("fileURL", fileURL);
        return Result.ok("图片上传成功!", resultMap);
    }

    @ApiOperation(value = "获取图片下载链接", notes = "获取图片下载链接")
    @GetMapping("/download/{fileId}")
    public Result<ResultMap> getImageDownloadURL(@ApiParam(name = "fileId", value = "图片文件ID") @PathVariable("fileId") String fileId, HttpServletResponse response) throws IOException {
        String downloadURL = imageService.getDownloadURL(fileId);
        ResultMap resultMap = ResultMap.create().put("downloadURL", downloadURL);
        response.sendRedirect(downloadURL);
        return Result.ok("图片下载链接获取成功!", resultMap);
    }

}

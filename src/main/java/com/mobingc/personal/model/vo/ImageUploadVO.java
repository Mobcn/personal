package com.mobingc.personal.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 图片上传数据对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ImageUploadVO对象", description="图片上传数据对象")
public class ImageUploadVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件后缀名")
    private String type;

    @ApiModelProperty(value = "图片base64字符串")
    private String imageBase64;


}

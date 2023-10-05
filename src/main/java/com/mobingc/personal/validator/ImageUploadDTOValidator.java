package com.mobingc.personal.validator;

import com.mobingc.personal.common.exception.BizException;
import com.mobingc.personal.common.utils.AssertUtils;
import com.mobingc.personal.model.dto.ImageUploadDTO;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.base.BaseValidator;
import com.mobingc.personal.common.annotation.Validator;
import org.apache.commons.lang3.StringUtils;

/**
 * 图片上传数据对象校验器
 *
 * @author Mo
 * @since 2022-08-25
 */
@Validator
public class ImageUploadDTOValidator implements BaseValidator<ImageUploadDTO> {

    @Override
    public void validate(ImageUploadDTO entity) {
        // 校验文件后缀名是否为空
        AssertUtils.isTrue(entity.getType(), StringUtils::isNotEmpty, () -> new BizException(ResultCode.IMAGE_TYPE_IS_EMPTY));
        // 校验图片base64字符串是否为空
        AssertUtils.isTrue(entity.getImageBase64(), StringUtils::isNotEmpty, () -> new BizException(ResultCode.IMAGE_BASE64_IS_EMPTY));
        // 校验字符串是否为图片base64字符串
        String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        AssertUtils.isTrue(entity.getImageBase64().matches(base64Pattern), () -> new BizException(ResultCode.IMAGE_BASE64_IS_NOT_MATCHES));
    }

}

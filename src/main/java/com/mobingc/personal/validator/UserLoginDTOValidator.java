package com.mobingc.personal.validator;

import com.mobingc.personal.common.exception.BizException;
import com.mobingc.personal.common.utils.AssertUtils;
import com.mobingc.personal.model.dto.UserLoginDTO;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.base.BaseValidator;
import com.mobingc.personal.common.annotation.Validator;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户登录数据对象校验器
 *
 * @author Mo
 * @since 2022-08-25
 */
@Validator
public class UserLoginDTOValidator implements BaseValidator<UserLoginDTO> {

    @Override
    public void validate(UserLoginDTO entity) {
        // 校验用户名是否为空
        AssertUtils.isTrue(entity.getUserName(), StringUtils::isNotEmpty, () -> new BizException(ResultCode.USER_NAME_IS_EMPTY));
        // 校验密码是否为空
        AssertUtils.isTrue(entity.getPassword(), StringUtils::isNotEmpty, () -> new BizException(ResultCode.PASSWORD_IS_EMPTY));
    }

}

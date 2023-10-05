package com.mobingc.personal.validator;

import com.mobingc.personal.common.exception.BizException;
import com.mobingc.personal.common.utils.AssertUtils;
import com.mobingc.personal.model.dto.add.TagAddDTO;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.common.utils.SpringUtils;
import com.mobingc.personal.base.BaseValidator;
import com.mobingc.personal.common.annotation.Validator;
import com.mobingc.personal.repository.TagRepository;
import org.apache.commons.lang3.StringUtils;

/**
 * 标签保存对象校验器
 *
 * @author Mo
 * @since 2022-08-25
 */
@Validator
public class TagAddDTOValidator implements BaseValidator<TagAddDTO> {

    private final TagRepository tagRepository;

    public TagAddDTOValidator() {
        tagRepository = SpringUtils.getBean(TagRepository.class);
    }

    @Override
    public void validate(TagAddDTO entity) {
        // 校验标签名是否为空
        AssertUtils.isTrue(entity.getName(), StringUtils::isNotEmpty, () -> new BizException(ResultCode.TAG_NAME_IS_EMPTY));
        // 校验标签名是否重复
        AssertUtils.isTrue(!tagRepository.existsByName(entity.getName()), () -> new BizException(ResultCode.TAG_NAME_IS_REPEATED));
    }

}

package com.mobingc.personal.validator;

import com.mobingc.personal.common.exception.BizException;
import com.mobingc.personal.common.utils.AssertUtils;
import com.mobingc.personal.model.dto.update.TagUpdateDTO;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.common.utils.SpringUtils;
import com.mobingc.personal.base.BaseValidator;
import com.mobingc.personal.common.annotation.Validator;
import com.mobingc.personal.model.entity.Tag;
import com.mobingc.personal.repository.TagRepository;
import org.apache.commons.lang3.StringUtils;

/**
 * 标签更新对象校验器
 *
 * @author Mo
 * @since 2022-08-25
 */
@Validator
public class TagUpdateDTOValidator implements BaseValidator<TagUpdateDTO> {

    private final TagRepository tagRepository;

    public TagUpdateDTOValidator() {
        tagRepository = SpringUtils.getBean(TagRepository.class);
    }

    @Override
    public void validate(TagUpdateDTO entity) {
        // 校验标签ID是否为空
        AssertUtils.isTrue(entity.getId(), AssertUtils::isNotNull, () -> new BizException(ResultCode.TAG_ID_IS_EMPTY));
        // 校验标签是否存在
        Tag tag = tagRepository.selectById(entity.getId());
        AssertUtils.isTrue(tag, AssertUtils::isNotNull, () -> new BizException(ResultCode.TAG_IS_NOT_FOUND));
        // 校验标签名是否重复
        boolean condition = StringUtils.isNotEmpty(entity.getName()) && !entity.getName().equals(tag.getName());
        AssertUtils.isTrue(condition, !tagRepository.existsByNameAndIdNot(entity.getName(), entity.getId()), () -> new BizException(ResultCode.TAG_NAME_IS_REPEATED));
    }

}

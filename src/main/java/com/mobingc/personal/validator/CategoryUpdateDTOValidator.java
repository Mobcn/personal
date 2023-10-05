package com.mobingc.personal.validator;

import com.mobingc.personal.base.BaseValidator;
import com.mobingc.personal.common.annotation.Validator;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.common.exception.BizException;
import com.mobingc.personal.common.utils.AssertUtils;
import com.mobingc.personal.common.utils.SpringUtils;
import com.mobingc.personal.model.dto.update.CategoryUpdateDTO;
import com.mobingc.personal.model.entity.Category;
import com.mobingc.personal.repository.CategoryRepository;
import org.apache.commons.lang3.StringUtils;

/**
 * 分类更新对象校验器
 *
 * @author Mo
 * @since 2022-08-25
 */
@Validator
public class CategoryUpdateDTOValidator implements BaseValidator<CategoryUpdateDTO> {

    private final CategoryRepository categoryRepository;

    public CategoryUpdateDTOValidator() {
        categoryRepository = SpringUtils.getBean(CategoryRepository.class);
    }

    @Override
    public void validate(CategoryUpdateDTO entity) {
        // 校验分类ID是否为空
        AssertUtils.isTrue(entity.getId(), AssertUtils::isNotNull, () -> new BizException(ResultCode.CATEGORY_ID_IS_EMPTY));
        // 校验分类是否存在
        Category category = categoryRepository.selectById(entity.getId());
        AssertUtils.isTrue(category, AssertUtils::isNotNull, () -> new BizException(ResultCode.CATEGORY_IS_NOT_FOUND));
        // 校验分类名是否重复
        boolean condition = StringUtils.isNotEmpty(entity.getName()) && !entity.getName().equals(category.getName());
        AssertUtils.isTrue(condition, !categoryRepository.existsByNameAndIdNot(entity.getName(), entity.getId()), () -> new BizException(ResultCode.CATEGORY_NAME_IS_REPEATED));
    }

}

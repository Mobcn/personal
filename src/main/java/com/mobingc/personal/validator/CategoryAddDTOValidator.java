package com.mobingc.personal.validator;

import com.mobingc.personal.base.BaseValidator;
import com.mobingc.personal.common.annotation.Validator;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.common.exception.BizException;
import com.mobingc.personal.common.utils.AssertUtils;
import com.mobingc.personal.common.utils.SpringUtils;
import com.mobingc.personal.model.dto.add.CategoryAddDTO;
import com.mobingc.personal.repository.CategoryRepository;
import org.apache.commons.lang3.StringUtils;

/**
 * 分类保存对象校验器
 *
 * @author Mo
 * @since 2022-08-25
 */
@Validator
public class CategoryAddDTOValidator implements BaseValidator<CategoryAddDTO> {

    private final CategoryRepository categoryRepository;

    public CategoryAddDTOValidator() {
        categoryRepository = SpringUtils.getBean(CategoryRepository.class);
    }

    @Override
    public void validate(CategoryAddDTO entity) {
        // 校验分类名是否为空
        AssertUtils.isTrue(entity.getName(), StringUtils::isNotEmpty, () -> new BizException(ResultCode.CATEGORY_NAME_IS_EMPTY));
        // 校验分类描述是否为空
        AssertUtils.isTrue(entity.getDescription(), StringUtils::isNotEmpty, () -> new BizException(ResultCode.CATEGORY_DESCRIPTION_IS_EMPTY));
        // 校验分类名是否重复
        AssertUtils.isTrue(!categoryRepository.existsByName(entity.getName()), () -> new BizException(ResultCode.CATEGORY_NAME_IS_REPEATED));
    }

}

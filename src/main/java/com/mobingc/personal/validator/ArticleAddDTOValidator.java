package com.mobingc.personal.validator;

import com.mobingc.personal.common.exception.BizException;
import com.mobingc.personal.common.utils.AssertUtils;
import com.mobingc.personal.model.dto.add.ArticleAddDTO;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.base.BaseValidator;
import com.mobingc.personal.common.annotation.Validator;
import org.apache.commons.lang3.StringUtils;

/**
 * 文章保存数据对象校验器
 *
 * @author Mo
 * @since 2022-08-25
 */
@Validator
public class ArticleAddDTOValidator implements BaseValidator<ArticleAddDTO> {

    @Override
    public void validate(ArticleAddDTO entity) {
        // 校验文章标题是否为空
        AssertUtils.isTrue(entity.getTitle(), StringUtils::isNotBlank, () -> new BizException(ResultCode.ARTICLE_TITLE_IS_EMPTY));
        // 校验文章描述是否为空
        AssertUtils.isTrue(entity.getDescription(), StringUtils::isNotBlank, () -> new BizException(ResultCode.ARTICLE_DESCRIPTION_IS_EMPTY));
    }

}

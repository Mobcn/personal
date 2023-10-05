package com.mobingc.personal.validator;

import com.mobingc.personal.common.exception.BizException;
import com.mobingc.personal.common.utils.AssertUtils;
import com.mobingc.personal.model.dto.update.ArticleUpdateDTO;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.common.utils.SpringUtils;
import com.mobingc.personal.base.BaseValidator;
import com.mobingc.personal.common.annotation.Validator;
import com.mobingc.personal.repository.ArticleRepository;
import org.apache.commons.lang3.StringUtils;

/**
 * 文章更新数据对象校验器
 *
 * @author Mo
 * @since 2022-08-25
 */
@Validator
public class ArticleUpdateDTOValidator implements BaseValidator<ArticleUpdateDTO> {

    private final ArticleRepository articleRepository;

    public ArticleUpdateDTOValidator() {
        articleRepository = SpringUtils.getBean(ArticleRepository.class);
    }

    @Override
    public void validate(ArticleUpdateDTO entity) {
        // 校验文章ID是否为空
        AssertUtils.isTrue(entity.getId(), AssertUtils::isNotNull, () -> new BizException(ResultCode.ARTICLE_ID_IS_EMPTY));
        // 校验文章是否存在
        AssertUtils.isTrue(entity.getId(), articleRepository::existsById, () -> new BizException(ResultCode.ARTICLE_IS_NOT_FOUND));
        // 校验文章标题是否为空
        AssertUtils.isTrue(entity.getTitle() != null, entity.getTitle(), StringUtils::isNotBlank, () -> new BizException(ResultCode.ARTICLE_TITLE_IS_EMPTY));
        // 校验文章描述是否为空
        AssertUtils.isTrue(entity.getDescription() != null, entity.getDescription(), StringUtils::isNotBlank, () -> new BizException(ResultCode.ARTICLE_DESCRIPTION_IS_EMPTY));
    }

}

package com.mobingc.personal.service;

import com.mobingc.personal.base.BaseService;
import com.mobingc.personal.model.entity.ArticleContent;

/**
 * <p>
 * 文章内容表 服务类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
public interface ArticleContentService extends BaseService<ArticleContent, Long> {

    /**
     * 通过文章获取文章内容对象
     *
     * @param articleId 文章ID
     * @return 文章内容对象
     */
    ArticleContent findByArticleId(Long articleId);

}

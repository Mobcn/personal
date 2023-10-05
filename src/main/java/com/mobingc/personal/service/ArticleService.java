package com.mobingc.personal.service;

import com.mobingc.personal.base.BaseService;
import com.mobingc.personal.model.dto.info.ArticleInfoDTO;
import com.mobingc.personal.model.dto.search.ArticleSearchDTO;
import com.mobingc.personal.model.entity.Article;
import org.springframework.data.domain.Page;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
public interface ArticleService extends BaseService<Article, Long> {

    /**
     * 通过文章ID查询文章
     *
     * @param articleId 文章ID
     * @return 文章信息
     */
    ArticleInfoDTO selectArticleById(Long articleId);

    /**
     * @param articleSearchDTO 文章查询参数对象
     * @param page             查询页数
     * @param size             每页数据条数
     * @return 返回分页数据
     */
    Page<ArticleInfoDTO> pageArticle(ArticleSearchDTO articleSearchDTO, Integer page, Integer size);

}

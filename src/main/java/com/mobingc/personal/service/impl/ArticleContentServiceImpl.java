package com.mobingc.personal.service.impl;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.model.entity.ArticleContent;
import com.mobingc.personal.repository.ArticleContentRepository;
import com.mobingc.personal.service.ArticleContentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章内容表 服务实现类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Service
public class ArticleContentServiceImpl implements ArticleContentService {

    private final ArticleContentRepository articleContentRepository;

    public ArticleContentServiceImpl(ArticleContentRepository articleContentRepository) {
        this.articleContentRepository = articleContentRepository;
    }

    @Override
    public BaseRepository<ArticleContent, Long> getRepository() {
        return this.articleContentRepository;
    }

    @Override
    public ArticleContent findByArticleId(Long articleId) {
        return articleContentRepository.findByArticleId(articleId);
    }

}

package com.mobingc.personal.service.impl;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.common.exception.BizException;
import com.mobingc.personal.common.utils.AssertUtils;
import com.mobingc.personal.common.utils.ConverterUtils;
import com.mobingc.personal.model.dto.info.ArticleInfoDTO;
import com.mobingc.personal.model.dto.search.ArticleSearchDTO;
import com.mobingc.personal.model.entity.Article;
import com.mobingc.personal.model.entity.Statistic;
import com.mobingc.personal.repository.ArticleRepository;
import com.mobingc.personal.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public BaseRepository<Article, Long> getRepository() {
        return this.articleRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ArticleInfoDTO selectArticleById(Long articleId) {
        Article article = articleRepository.selectById(articleId);
        AssertUtils.isTrue(article, AssertUtils::isNotNull, () -> new BizException(ResultCode.ARTICLE_IS_NOT_FOUND));
        // 获取文章时，文章浏览次数+1
        Statistic statistic = article.getStatistic();
        statistic.setViews(statistic.getViews() + 1);
        return ConverterUtils.copyConvert(article, ArticleInfoDTO.class);
    }

    @Override
    public Page<ArticleInfoDTO> pageArticle(ArticleSearchDTO articleSearchDTO, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Article> articlePage = articleRepository.selectPage(pageable, articleSearchDTO);
        List<ArticleInfoDTO> articleInfoDTOList = articlePage.stream()
                .map(article -> {
                    ArticleInfoDTO articleInfoDTO = new ArticleInfoDTO();
                    BeanUtils.copyProperties(article, articleInfoDTO);
                    return articleInfoDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(articleInfoDTOList, pageable, articlePage.getTotalElements());
    }

}

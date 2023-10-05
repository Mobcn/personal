package com.mobingc.personal.repository;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.model.entity.ArticleContent;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 文章内容表 数据访问类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Repository
public interface ArticleContentRepository extends BaseRepository<ArticleContent, Long> {

//    /**
//     * 通过文章获取文章内容对象
//     *
//     * @param articleId 文章ID
//     * @return 文章内容对象
//     */
//    @Query(value = "SELECT id, content, create_time, update_time " +
//            "FROM b_article_content " +
//            "WHERE id=(" +
//            "    SELECT article_content_id " +
//            "    FROM b_article " +
//            "    WHERE id=:articleId" +
//            ")", nativeQuery = true)
//    ArticleContent findByArticleId(Long articleId);

    /**
     * 通过文章获取文章内容对象
     *
     * @param articleId 文章ID
     * @return 文章内容对象
     */
    ArticleContent findByArticleId(Long articleId);

}
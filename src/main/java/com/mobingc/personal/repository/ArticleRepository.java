package com.mobingc.personal.repository;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.model.dto.search.ArticleSearchDTO;
import com.mobingc.personal.model.entity.Article;
import com.mobingc.personal.model.entity.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 文章表 数据访问类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Repository
public interface ArticleRepository extends BaseRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    /**
     * 通过查询参数查询文章
     *
     * @param pageable         分页信息
     * @param articleSearchDTO 查询参数
     * @return 分页数据
     */
    default Page<Article> selectPage(Pageable pageable, ArticleSearchDTO articleSearchDTO) {
        return this.findAll((Specification<Article>) (root, query, builder) -> {

            Predicate where = null;

            // 通过key全模糊标题或描述查询
            String key = articleSearchDTO.getKey();
            if (StringUtils.isNotBlank(key)) {
                key = key.trim();
                Predicate likeTitle = builder.like(root.get("title"), "%" + key + "%");
                Predicate likeDescription = builder.like(root.get("description"), "%" + key + "%");
                where = builder.or(likeTitle, likeDescription);
            }

            // 通过时间范围查询
            Date startTime = articleSearchDTO.getStartTime();
            Date endTime = articleSearchDTO.getEndTime();
            if (startTime != null || endTime != null) {
                startTime = startTime == null ? new Date(0L) : startTime;
                endTime = endTime == null ? new Date() : endTime;
                Predicate betweenCreateTime = builder.between(root.get("createTime"), startTime, endTime);
                if (where == null) {
                    where = betweenCreateTime;
                } else {
                    where = builder.and(where, betweenCreateTime);
                }
            }

            // 通过分类ID查询
            Long categoryId = articleSearchDTO.getCategoryId();
            if (categoryId != null) {
                Predicate equalCategoryId = builder.equal(root.get("category").get("id"), categoryId);
                if (where == null) {
                    where = equalCategoryId;
                } else {
                    where = builder.and(where, equalCategoryId);
                }
            }

            // 通过标签ID列表查询
            List<Long> tagIds = articleSearchDTO.getTagIds();
            if (tagIds != null && !tagIds.isEmpty()) {
                Subquery<Long> subQuery = query.subquery(Long.class);
                Root<Article> subRoot = subQuery.from(Article.class);
                SetJoin<Article, Tag> subTagJoin = subRoot.join(subRoot.getModel().getSet("tags", Tag.class), JoinType.LEFT);
                Predicate subIdEqual = builder.equal(root.get("id"), subRoot.get("id"));
                CriteriaBuilder.In<Object> subTagIn = builder.in(subTagJoin.get("id")).value(tagIds);
                Predicate subWhere = builder.and(subIdEqual, subTagIn);
                Expression<Long> subSelection = subQuery.select(builder.count(subRoot)).where(subWhere).getSelection();
                Predicate tagContains = builder.equal(subSelection, tagIds.size());
                if (where == null) {
                    where = tagContains;
                } else {
                    where = builder.and(where, tagContains);
                }
            }

            if (where != null) {
                query.where(where);
            }

            return query.orderBy(builder.desc(root.get("createTime"))).getRestriction();
        }, pageable);
    }

}

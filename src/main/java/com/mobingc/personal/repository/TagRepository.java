package com.mobingc.personal.repository;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.model.entity.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 标签表 数据访问类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Repository
public interface TagRepository extends BaseRepository<Tag, Long> {

    /**
     * 通过标签ID列表批量查询标签
     *
     * @param ids 标签ID列表
     * @return 标签列表
     */
    default List<Tag> selectAllById(Iterable<Long> ids) {
        return this.findAllById(ids);
    }

    /**
     * 通过标签名或者标签描述全模糊查询标签
     *
     * @param pageable    分页信息
     * @param name        标签名
     * @param description 标签描述
     * @return 分页数据
     */
    Page<Tag> findAllByNameContainingOrDescriptionContaining(Pageable pageable, String name, String description);

    /**
     * 通过查询关键字全模糊查询标签
     *
     * @param pageable 分页信息
     * @param key      查询关键字
     * @return 分页数据
     */
    default Page<Tag> selectPageByKey(Pageable pageable, String key) {
        if (StringUtils.isEmpty(key)) {
            return this.findAll(pageable);
        }
        return this.findAllByNameContainingOrDescriptionContaining(pageable, key, key);
    }

    /**
     * 查询所有标签
     *
     * @param pageable 分页信息
     * @return 分页数据
     */
    default Page<Tag> selectPage(Pageable pageable) {
        return this.findAll(pageable);
    }

    /**
     * 查询添加时标签名称是否重复
     *
     * @param name 标签名称
     * @return 标签名称是否重复
     */
    boolean existsByName(String name);

    /**
     * 查询更新时标签名称是否重复
     *
     * @param name 标签名称
     * @param id   标签ID
     * @return 标签名称是否重复
     */
    boolean existsByNameAndIdNot(String name, Long id);

    /**
     * 查询是否存在与标签有关联的文章
     *
     * @param id 标签ID
     * @return 是否存在与标签有关联的文章
     */
    @Query(value = "SELECT IF(COUNT(1) > 0, 'true', 'false') FROM r_article_tag WHERE tag_id=:id LIMIT 1", nativeQuery = true)
    boolean existsArticle(Long id);

}
package com.mobingc.personal.repository;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.model.entity.Category;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 文章分类表 数据访问类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {

    /**
     * 通过分类名或者分类描述全模糊查询分类
     *
     * @param pageable    分页信息
     * @param name        分类名
     * @param description 分类描述
     * @return 分页数据
     */
    Page<Category> findAllByNameContainingOrDescriptionContaining(Pageable pageable, String name, String description);

    /**
     * 通过查询关键字全模糊查询分类
     *
     * @param pageable 分页信息
     * @param key      查询关键字
     * @return 分页数据
     */
    default Page<Category> selectPageByKey(Pageable pageable, String key) {
        if (StringUtils.isEmpty(key)) {
            return this.findAll(pageable);
        }
        return this.findAllByNameContainingOrDescriptionContaining(pageable, key, key);
    }

    /**
     * 查询添加时分类名称是否重复
     *
     * @param name 分类名称
     * @return 分类名称是否重复
     */
    boolean existsByName(String name);

    /**
     * 查询更新时分类名称是否重复
     *
     * @param name 分类名称
     * @param id   分类ID
     * @return 分类名称是否重复
     */
    boolean existsByNameAndIdNot(String name, Long id);

}
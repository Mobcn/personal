package com.mobingc.personal.service;

import com.mobingc.personal.base.BaseService;
import com.mobingc.personal.model.dto.info.CategoryInfoDTO;
import com.mobingc.personal.model.dto.search.CategorySearchDTO;
import com.mobingc.personal.model.entity.Category;
import org.springframework.data.domain.Page;

/**
 * <p>
 * 文章分类表 服务类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
public interface CategoryService extends BaseService<Category, Long> {

    /**
     * 获取分类列表
     *
     * @param categorySearchDTO 分类查询参数对象
     * @param page              查询页数
     * @param size              每页数据条数
     * @return 返回分页数据
     */
    Page<CategoryInfoDTO> pageCategory(CategorySearchDTO categorySearchDTO, Integer page, Integer size);

    /**
     * 通过分类ID查询分类
     *
     * @param categoryId 分类ID
     * @return 分类信息
     */
    CategoryInfoDTO infoCategoryById(Long categoryId);

    /**
     * 修改分类状态
     *
     * @param categoryId 分类ID
     * @return 当前分类状态
     */
    Boolean statusCategory(Long categoryId);

}

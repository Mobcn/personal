package com.mobingc.personal.service.impl;

import com.mobingc.personal.base.BaseRepository;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.common.exception.BizException;
import com.mobingc.personal.common.utils.AssertUtils;
import com.mobingc.personal.model.dto.info.CategoryInfoDTO;
import com.mobingc.personal.model.dto.search.CategorySearchDTO;
import com.mobingc.personal.model.entity.Category;
import com.mobingc.personal.repository.CategoryRepository;
import com.mobingc.personal.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章分类表 服务实现类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public BaseRepository<Category, Long> getRepository() {
        return this.categoryRepository;
    }

    @Override
    public Page<CategoryInfoDTO> pageCategory(CategorySearchDTO categorySearchDTO, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Category> categoryPage = categoryRepository.selectPageByKey(pageable, categorySearchDTO.getKey());
        List<CategoryInfoDTO> categoryInfoDTOList = categoryPage.stream()
                .map(tag -> {
                    CategoryInfoDTO categoryInfoDTO = new CategoryInfoDTO();
                    BeanUtils.copyProperties(tag, categoryInfoDTO);
                    return categoryInfoDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(categoryInfoDTOList, pageable, categoryPage.getTotalElements());
    }

    @Override
    public CategoryInfoDTO infoCategoryById(Long categoryId) {
        Category category = categoryRepository.selectById(categoryId);
        AssertUtils.isTrue(category, AssertUtils::isNotNull, () -> new BizException(ResultCode.CATEGORY_IS_NOT_FOUND));
        CategoryInfoDTO categoryInfoDTO = new CategoryInfoDTO();
        BeanUtils.copyProperties(category, categoryInfoDTO);
        return categoryInfoDTO;
    }

    @Override
    public Boolean statusCategory(Long categoryId) {
        // 查询分类
        Category category = categoryRepository.selectById(categoryId);
        AssertUtils.isTrue(category, AssertUtils::isNotNull, () -> new BizException(ResultCode.CATEGORY_IS_NOT_FOUND));
        // 修改分类状态
        category.setStatus(!category.getStatus());
        categoryRepository.updateById(category);
        return category.getStatus();
    }

}

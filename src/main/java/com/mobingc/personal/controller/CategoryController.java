package com.mobingc.personal.controller;


import com.mobingc.personal.base.BaseController;
import com.mobingc.personal.base.BaseService;
import com.mobingc.personal.common.Result;
import com.mobingc.personal.common.ResultList;
import com.mobingc.personal.common.ResultPage;
import com.mobingc.personal.common.consts.PageConst;
import com.mobingc.personal.common.utils.ConverterUtils;
import com.mobingc.personal.model.dto.add.CategoryAddDTO;
import com.mobingc.personal.model.dto.info.CategoryInfoDTO;
import com.mobingc.personal.model.dto.search.CategorySearchDTO;
import com.mobingc.personal.model.dto.update.CategoryUpdateDTO;
import com.mobingc.personal.model.entity.Category;
import com.mobingc.personal.model.vo.add.CategoryAddVO;
import com.mobingc.personal.model.vo.field.IdFieldVO;
import com.mobingc.personal.model.vo.field.IdsFieldVO;
import com.mobingc.personal.model.vo.field.StatusFieldVO;
import com.mobingc.personal.model.vo.info.CategoryInfoVO;
import com.mobingc.personal.model.vo.info.CategorySimpleInfoVO;
import com.mobingc.personal.model.vo.search.CategorySearchVO;
import com.mobingc.personal.model.vo.update.CategoryUpdateVO;
import com.mobingc.personal.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章分类表 前端控制器
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Api(tags = "分类管理")
@RestController
@RequestMapping("/category")
public class CategoryController implements BaseController<Category, Long> {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public BaseService<Category, Long> getService() {
        return this.categoryService;
    }

    @ApiOperation(value = "添加分类", notes = "添加分类")
    @PutMapping("/add")
    public Result<IdFieldVO<Long>> addCategory(@ApiParam(name = "categoryAddVO", value = "分类添加对象") @RequestBody CategoryAddVO categoryAddVO) {
        CategoryAddDTO categoryAddDTO = ConverterUtils.copyConvert(categoryAddVO, CategoryAddDTO.class);
        return this.add(categoryAddDTO);
    }

    @ApiOperation(value = "更新分类", notes = "更新分类")
    @PostMapping("/update")
    public Result<IdFieldVO<Long>> updateCategory(@ApiParam(name = "categoryUpdateVO", value = "分类更新对象") @RequestBody CategoryUpdateVO categoryUpdateVO) {
        CategoryUpdateDTO categoryUpdateDTO = ConverterUtils.copyConvert(categoryUpdateVO, CategoryUpdateDTO.class);
        categoryUpdateDTO.setId(categoryUpdateVO.getId() != null ? Long.valueOf(categoryUpdateVO.getId()) : null);
        return this.update(categoryUpdateDTO);
    }

    @ApiOperation(value = "删除分类", notes = "删除分类")
    @DeleteMapping("/remove/{id}")
    public Result<IdFieldVO<Long>> removeCategory(@ApiParam(name = "id", value = "分类ID") @PathVariable Long id) {
        return this.remove(id);
    }

    @ApiOperation(value = "批量删除分类", notes = "批量删除分类")
    @DeleteMapping("/batch/remove")
    public Result<IdsFieldVO<Long>> removeBatchCategory(@ApiParam(name = "ids", value = "分类ID数组") @RequestBody Set<Long> ids) {
        return this.removeBatch(ids);
    }

    @ApiOperation(value = "获取分类列表", notes = "获取分类列表")
    @PostMapping("/list")
    public Result<ResultPage<CategoryInfoVO>> listCategory(
            @ApiParam(name = "categorySearchVO", value = "分类查询参数对象") @RequestBody(required = false) CategorySearchVO categorySearchVO,
            @ApiParam(name = "page", value = "查询页数") @RequestParam(value = "page", required = false, defaultValue = PageConst.DEFAULT_PAGE_STRING) Integer page,
            @ApiParam(name = "size", value = "每页数据条数") @RequestParam(value = "size", required = false, defaultValue = PageConst.DEFAULT_PAGE_SIZE_STRING) Integer size) {
        CategorySearchDTO categorySearchDTO = new CategorySearchDTO();
        if (categorySearchVO != null) {
            BeanUtils.copyProperties(categorySearchVO, categorySearchDTO);
        }
        Page<CategoryInfoDTO> categoryInfoDTOPage = categoryService.pageCategory(categorySearchDTO, page, size);
        ResultPage<CategoryInfoVO> resultPage = new ResultPage<>(categoryInfoDTOPage, categoryInfoDTO -> {
            CategoryInfoVO categoryInfoVO = new CategoryInfoVO();
            BeanUtils.copyProperties(categoryInfoDTO, categoryInfoVO);
            categoryInfoVO.setId(String.valueOf(categoryInfoDTO.getId()));
            return categoryInfoVO;
        });
        return Result.ok("获取分类列表成功!", resultPage);
    }

    @ApiOperation(value = "获取分类简要信息列表", notes = "获取分类简要信息列表")
    @GetMapping("/simple/list")
    public Result<ResultList<CategorySimpleInfoVO>> listSimpleCategory() {
        List<Category> categoryList = categoryService.selectAll();
        List<CategorySimpleInfoVO> categorySimpleInfoVOList = categoryList.stream().map(category -> {
            CategorySimpleInfoVO categorySimpleInfoVO = new CategorySimpleInfoVO();
            categorySimpleInfoVO.setId(String.valueOf(category.getId()));
            categorySimpleInfoVO.setName(category.getName());
            return categorySimpleInfoVO;
        }).collect(Collectors.toList());
        return Result.ok("获取分类简要信息列表成功!", ResultList.create(categorySimpleInfoVOList));
    }

    @ApiOperation(value = "获取分类信息", notes = "获取分类信息")
    @GetMapping("/info/{id}")
    public Result<CategoryInfoVO> infoCategory(@ApiParam(name = "id", value = "分类ID") @PathVariable Long id) {
        CategoryInfoDTO categoryInfoDTO = categoryService.infoCategoryById(id);
        CategoryInfoVO categoryInfoVO = ConverterUtils.copyConvert(categoryInfoDTO, CategoryInfoVO.class);
        categoryInfoVO.setId(String.valueOf(categoryInfoDTO.getId()));
        return Result.ok("获取分类信息成功!", categoryInfoVO);
    }

    @ApiOperation(value = "修改分类状态", notes = "修改分类状态")
    @PostMapping("/status/{id}")
    public Result<StatusFieldVO<Boolean>> statusTag(@ApiParam(name = "id", value = "分类ID") @PathVariable Long id) {
        Boolean tagStatus = categoryService.statusCategory(id);
        StatusFieldVO<Boolean> tagStatusVO = new StatusFieldVO<Boolean>().setStatus(tagStatus);
        return Result.ok("标签状态修改成功!", tagStatusVO);
    }

}


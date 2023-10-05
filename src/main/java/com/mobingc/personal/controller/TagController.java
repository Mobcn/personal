package com.mobingc.personal.controller;


import com.mobingc.personal.base.BaseController;
import com.mobingc.personal.base.BaseService;
import com.mobingc.personal.common.ResultList;
import com.mobingc.personal.common.consts.PageConst;
import com.mobingc.personal.common.utils.ConverterUtils;
import com.mobingc.personal.model.dto.info.TagInfoDTO;
import com.mobingc.personal.model.dto.search.TagSearchDTO;
import com.mobingc.personal.model.dto.update.TagUpdateDTO;
import com.mobingc.personal.model.entity.Category;
import com.mobingc.personal.model.entity.Tag;
import com.mobingc.personal.common.Result;
import com.mobingc.personal.model.dto.add.TagAddDTO;
import com.mobingc.personal.common.ResultPage;
import com.mobingc.personal.model.vo.add.TagAddVO;
import com.mobingc.personal.model.vo.field.IdFieldVO;
import com.mobingc.personal.model.vo.field.IdsFieldVO;
import com.mobingc.personal.model.vo.field.StatusFieldVO;
import com.mobingc.personal.model.vo.info.CategorySimpleInfoVO;
import com.mobingc.personal.model.vo.info.TagInfoVO;
import com.mobingc.personal.model.vo.info.TagSimpleInfoVO;
import com.mobingc.personal.model.vo.search.TagSearchVO;
import com.mobingc.personal.model.vo.update.TagUpdateVO;
import com.mobingc.personal.service.TagService;
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
 * 标签表 前端控制器
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Api(tags = "标签管理")
@RestController
@RequestMapping("/tag")
public class TagController implements BaseController<Tag, Long> {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public BaseService<Tag, Long> getService() {
        return this.tagService;
    }

    @ApiOperation(value = "添加标签", notes = "添加标签")
    @PutMapping("/add")
    public Result<IdFieldVO<Long>> addTag(@ApiParam(name = "tagAddVO", value = "标签添加对象") @RequestBody TagAddVO tagAddVO) {
        TagAddDTO tagAddDTO = ConverterUtils.copyConvert(tagAddVO, TagAddDTO.class);
        return this.add(tagAddDTO);
    }

    @ApiOperation(value = "更新标签", notes = "更新标签")
    @PostMapping("/update")
    public Result<IdFieldVO<Long>> updateTag(@ApiParam(name = "tagUpdateVO", value = "标签更新对象") @RequestBody TagUpdateVO tagUpdateVO) {
        TagUpdateDTO tagUpdateDTO = ConverterUtils.copyConvert(tagUpdateVO, TagUpdateDTO.class);
        tagUpdateDTO.setId(tagUpdateVO.getId() != null ? Long.valueOf(tagUpdateVO.getId()) : null);
        return this.update(tagUpdateDTO);
    }

    @ApiOperation(value = "删除标签", notes = "删除标签")
    @DeleteMapping("/remove/{id}")
    public Result<IdFieldVO<Long>> removeTag(@ApiParam(name = "id", value = "标签ID") @PathVariable Long id) {
        return this.remove(id);
    }

    @ApiOperation(value = "批量删除标签", notes = "批量删除标签")
    @DeleteMapping("/batch/remove")
    public Result<IdsFieldVO<Long>> removeBatchTag(@ApiParam(name = "ids", value = "标签ID数组") @RequestBody Set<Long> ids) {
        return this.removeBatch(ids);
    }

    @ApiOperation(value = "获取标签列表", notes = "获取标签列表")
    @PostMapping("/list")
    public Result<ResultPage<TagInfoVO>> listTag(
            @ApiParam(name = "tagSearchVO", value = "标签查询参数对象") @RequestBody(required = false) TagSearchVO tagSearchVO,
            @ApiParam(name = "page", value = "查询页数") @RequestParam(value = "page", required = false, defaultValue = PageConst.DEFAULT_PAGE_STRING) Integer page,
            @ApiParam(name = "size", value = "每页数据条数") @RequestParam(value = "size", required = false, defaultValue = PageConst.DEFAULT_PAGE_SIZE_STRING) Integer size) {
        TagSearchDTO tagSearchDTO = new TagSearchDTO();
        if (tagSearchVO != null) {
            BeanUtils.copyProperties(tagSearchVO, tagSearchDTO);
        }
        Page<TagInfoDTO> tagInfoDTOPage = tagService.pageTag(tagSearchDTO, page, size);
        ResultPage<TagInfoVO> resultPage = new ResultPage<>(tagInfoDTOPage, tagInfoDTO -> {
            TagInfoVO tagInfoVO = new TagInfoVO();
            BeanUtils.copyProperties(tagInfoDTO, tagInfoVO);
            tagInfoVO.setId(String.valueOf(tagInfoDTO.getId()));
            return tagInfoVO;
        });
        return Result.ok("获取标签列表成功!", resultPage);
    }

    @ApiOperation(value = "获取标签简要信息列表", notes = "获取标签简要信息列表")
    @GetMapping("/simple/list")
    public Result<ResultList<TagSimpleInfoVO>> listSimpleCategory() {
        List<Tag> tagList = tagService.selectAll();
        List<TagSimpleInfoVO> tagSimpleInfoVOList = tagList.stream().map(tag -> {
            TagSimpleInfoVO tagSimpleInfoVO = new TagSimpleInfoVO();
            tagSimpleInfoVO.setId(String.valueOf(tag.getId()));
            tagSimpleInfoVO.setName(tag.getName());
            return tagSimpleInfoVO;
        }).collect(Collectors.toList());
        return Result.ok("获取标签简要信息列表成功!", ResultList.create(tagSimpleInfoVOList));
    }

    @ApiOperation(value = "获取标签信息", notes = "获取标签信息")
    @GetMapping("/info/{id}")
    public Result<TagInfoVO> infoTag(@ApiParam(name = "id", value = "标签ID") @PathVariable Long id) {
        TagInfoDTO tagInfoDTO = tagService.selectTagById(id);
        TagInfoVO tagInfoVO = ConverterUtils.copyConvert(tagInfoDTO, TagInfoVO.class);
        tagInfoVO.setId(String.valueOf(tagInfoDTO.getId()));
        return Result.ok("获取标签信息成功!", tagInfoVO);
    }

    @ApiOperation(value = "修改标签状态", notes = "修改标签状态")
    @PostMapping("/status/{id}")
    public Result<StatusFieldVO<Boolean>> statusTag(@ApiParam(name = "id", value = "标签ID") @PathVariable Long id) {
        Boolean tagStatus = tagService.statusTag(id);
        StatusFieldVO<Boolean> tagStatusFieldVO = new StatusFieldVO<Boolean>().setStatus(tagStatus);
        return Result.ok("标签状态修改成功!", tagStatusFieldVO);
    }

}


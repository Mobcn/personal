package com.mobingc.personal.controller;


import com.mobingc.personal.base.BaseController;
import com.mobingc.personal.base.BaseService;
import com.mobingc.personal.common.Result;
import com.mobingc.personal.common.ResultPage;
import com.mobingc.personal.common.consts.PageConst;
import com.mobingc.personal.common.utils.DateConvertUtils;
import com.mobingc.personal.common.utils.ReflectUtils;
import com.mobingc.personal.model.dto.add.ArticleAddDTO;
import com.mobingc.personal.model.dto.info.ArticleInfoDTO;
import com.mobingc.personal.model.dto.search.ArticleSearchDTO;
import com.mobingc.personal.model.dto.update.ArticleUpdateDTO;
import com.mobingc.personal.model.entity.*;
import com.mobingc.personal.model.vo.add.ArticleAddVO;
import com.mobingc.personal.model.vo.field.IdFieldVO;
import com.mobingc.personal.model.vo.field.IdsFieldVO;
import com.mobingc.personal.model.vo.info.ArticleInfoVO;
import com.mobingc.personal.model.vo.info.ArticleSimpleInfoVO;
import com.mobingc.personal.model.vo.info.TagSimpleInfoVO;
import com.mobingc.personal.model.vo.search.ArticleSearchVO;
import com.mobingc.personal.model.vo.update.ArticleUpdateVO;
import com.mobingc.personal.service.ArticleContentService;
import com.mobingc.personal.service.ArticleService;
import com.mobingc.personal.service.CategoryService;
import com.mobingc.personal.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Api(tags = "文章管理")
@RestController
@RequestMapping("/article")
public class ArticleController implements BaseController<Article, Long> {

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final ArticleContentService articleContentService;

    public ArticleController(ArticleService articleService, CategoryService categoryService, TagService tagService, ArticleContentService articleContentService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.articleContentService = articleContentService;
    }

    @Override
    public BaseService<Article, Long> getService() {
        return this.articleService;
    }

    @ApiOperation(value = "添加文章", notes = "添加文章")
    @PutMapping("/add")
    public Result<IdFieldVO<Long>> addArticle(@ApiParam(name = "articleAddVO", value = "文章添加对象") @RequestBody ArticleAddVO articleAddVO) {
        ArticleAddDTO articleAddDTO = convertToAddDTO(articleAddVO);
        return this.add(articleAddDTO);
    }

    @ApiOperation(value = "更新文章", notes = "更新文章")
    @PostMapping("/update")
    public Result<IdFieldVO<Long>> updateArticle(@ApiParam(name = "articleUpdateVO", value = "文章更新对象") @RequestBody ArticleUpdateVO articleUpdateVO) {
        ArticleUpdateDTO articleUpdateDTO = convertToUpdateDTO(articleUpdateVO);
        return this.update(articleUpdateDTO);
    }

    @ApiOperation(value = "删除文章", notes = "删除文章")
    @DeleteMapping("/remove/{id}")
    public Result<IdFieldVO<Long>> removeArticle(@ApiParam(name = "id", value = "文章ID") @PathVariable Long id) {
        return this.remove(id);
    }

    @ApiOperation(value = "批量删除文章", notes = "批量删除文章")
    @DeleteMapping("/batch/remove")
    public Result<IdsFieldVO<Long>> removeBatchArticle(HttpServletResponse response, @ApiParam(name = "ids", value = "文章ID数组") @RequestBody Set<Long> ids) {

        return this.removeBatch(ids);
    }

    @ApiOperation(value = "获取文章简要信息列表", notes = "获取文章简要信息列表")
    @PostMapping("/list")
    public Result<ResultPage<ArticleSimpleInfoVO>> listTag(
            @ApiParam(name = "articleSearchVO", value = "文章查询参数对象") @RequestBody(required = false) ArticleSearchVO articleSearchVO,
            @ApiParam(name = "page", value = "查询页数") @RequestParam(value = "page", required = false, defaultValue = PageConst.DEFAULT_PAGE_STRING) Integer page,
            @ApiParam(name = "size", value = "每页数据条数") @RequestParam(value = "size", required = false, defaultValue = PageConst.DEFAULT_PAGE_SIZE_STRING) Integer size) {
        ArticleSearchDTO articleSearchDTO = new ArticleSearchDTO();
        if (articleSearchVO != null) {
            BeanUtils.copyProperties(articleSearchVO, articleSearchDTO);
            if (StringUtils.isNotBlank(articleSearchVO.getCategoryId())) {
                articleSearchDTO.setCategoryId(Long.valueOf(articleSearchVO.getCategoryId()));
            }
            if (articleSearchVO.getTabIds() != null && articleSearchVO.getTabIds().size() > 0) {
                articleSearchDTO.setTagIds(articleSearchVO.getTabIds()
                        .stream()
                        .map(Long::valueOf)
                        .collect(Collectors.toList()));
            }
        }
        Page<ArticleInfoDTO> articleInfoDTOPage = articleService.pageArticle(articleSearchDTO, page, size);
        ResultPage<ArticleSimpleInfoVO> resultPage = new ResultPage<>(articleInfoDTOPage, articleInfoDTO -> convertToVO(articleInfoDTO, ArticleSimpleInfoVO.class));
        return Result.ok("获取文章简要信息列表成功!", resultPage);
    }

    @ApiOperation(value = "获取文章信息", notes = "获取文章信息")
    @GetMapping("/info/{id}")
    public Result<ArticleInfoVO> infoArticle(@ApiParam(name = "id", value = "文章ID") @PathVariable Long id) {
        ArticleInfoDTO articleInfoDTO = articleService.selectArticleById(id);
        ArticleInfoVO articleInfoVO = convertToVO(articleInfoDTO, ArticleInfoVO.class);
        return Result.ok("获取文章简要信息成功!", articleInfoVO);
    }

    /**
     * 将文章添加VO转换为DTO
     *
     * @param articleAddVO 文章添加VO
     * @return 文章添加DTO
     */
    private ArticleAddDTO convertToAddDTO(ArticleAddVO articleAddVO) {
        ArticleAddDTO articleAddDTO = new ArticleAddDTO();
        BeanUtils.copyProperties(articleAddVO, articleAddDTO);
        // 分类
        String categoryId = articleAddVO.getCategoryId();
        if (StringUtils.isNotBlank(categoryId)) {
            Category category = categoryService.selectById(Long.valueOf(articleAddVO.getCategoryId()));
            articleAddDTO.setCategory(category);
        }
        // 文章
        Set<String> tagIds = articleAddVO.getTagIds();
        if (tagIds != null) {
            Set<Tag> tags = tagService.findTagSetById(articleAddVO.getTagIds());
            articleAddDTO.setTags(tags);
        }
        // 内容
        ArticleContent articleContent = new ArticleContent();
        articleContent.setContent(articleAddVO.getContent());
        articleAddDTO.setContent(articleContent);
        // 统计
        Statistic statistic = new Statistic();
        articleAddDTO.setStatistic(statistic);
        return articleAddDTO;
    }

    /**
     * 将文章更新VO转换为DTO
     *
     * @param articleUpdateVO 文章更新VO
     * @return 文章更新DTO
     */
    private ArticleUpdateDTO convertToUpdateDTO(ArticleUpdateVO articleUpdateVO) {
        ArticleUpdateDTO articleUpdateDTO = new ArticleUpdateDTO();
        BeanUtils.copyProperties(articleUpdateVO, articleUpdateDTO);
        if (StringUtils.isNotBlank(articleUpdateVO.getId())) {
            // ID
            Long id = Long.valueOf(articleUpdateVO.getId());
            articleUpdateDTO.setId(id);
            // 内容
            ArticleContent articleContent = articleContentService.findByArticleId(id);
            articleContent.setContent(articleUpdateVO.getContent());
            articleUpdateDTO.setContent(articleContent);
            // 分类
            String categoryId = articleUpdateVO.getCategoryId();
            if (StringUtils.isNotBlank(categoryId)) {
                Category category = categoryService.selectById(Long.valueOf(categoryId));
                articleUpdateDTO.setCategory(category);
            }
            // 文章
            Set<String> tagIds = articleUpdateVO.getTagIds();
            if (tagIds != null) {
                Set<Tag> tags = tagService.findTagSetById(tagIds);
                articleUpdateDTO.setTags(tags);
            }
        }
        return articleUpdateDTO;
    }

    /**
     * 将文章信息DTO转换为VO
     *
     * @param articleInfoDTO 文章信息DTO
     * @return 文章信息VO
     */
    private <T> T convertToVO(ArticleInfoDTO articleInfoDTO, Class<T> clazz) {
        try {
            T articleVO = clazz.newInstance();
            BeanUtils.copyProperties(articleInfoDTO, articleVO);
            ReflectUtils.setFieldValue("id", articleVO, String.valueOf(articleInfoDTO.getId()));
            ReflectUtils.setFieldValue("views", articleVO, articleInfoDTO.getStatistic().getViews());
            ReflectUtils.setFieldValue("createTime", articleVO, DateConvertUtils.toDateString(articleInfoDTO.getCreateTime()));
            ReflectUtils.setFieldValue("tags", articleVO, articleInfoDTO.getTags()
                    .stream()
                    .map(tag -> {
                        TagSimpleInfoVO tagSimpleInfoVO = new TagSimpleInfoVO();
                        tagSimpleInfoVO.setId(String.valueOf(tag.getId()));
                        tagSimpleInfoVO.setName(tag.getName());
                        return tagSimpleInfoVO;
                    })
                    .collect(Collectors.toList()));
            if (articleInfoDTO.getCategory() != null) {
                ReflectUtils.setFieldValue("categoryId", articleVO, String.valueOf(articleInfoDTO.getCategory().getId()));
                ReflectUtils.setFieldValue("categoryName", articleVO, articleInfoDTO.getCategory().getName());
            }
            if (ArticleSimpleInfoVO.class.equals(clazz)) {
                ReflectUtils.setFieldValue("contentId", articleVO, String.valueOf(articleInfoDTO.getContent().getId()));
            } else if (ArticleInfoVO.class.equals(clazz)) {
                ReflectUtils.setFieldValue("content", articleVO, articleInfoDTO.getContent().getContent());
            }
            return articleVO;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}


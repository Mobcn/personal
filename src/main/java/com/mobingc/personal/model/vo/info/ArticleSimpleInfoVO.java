package com.mobingc.personal.model.vo.info;

import com.mobingc.personal.base.BaseResultData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 文章简要信息对象（不包含文章内容）
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ArticleSimpleInfoVO对象", description="文章简要信息对象")
public class ArticleSimpleInfoVO implements BaseResultData {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID")
    private String id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章描述")
    private String description;

    @ApiModelProperty(value = "文章封面")
    private String cover;

    @ApiModelProperty(value = "文章内容ID")
    private String contentId;

    @ApiModelProperty(value = "文章分类ID")
    private String categoryId;

    @ApiModelProperty(value = "文章分类名称")
    private String categoryName;

    @ApiModelProperty(value = "文章标签信息列表")
    private List<TagSimpleInfoVO> tags;

    @ApiModelProperty(value = "文章浏览次数")
    private Integer views;

    @ApiModelProperty(value = "是否置顶")
    private Boolean topping;

    @ApiModelProperty(value = "状态（0：隐藏，1：发布）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

}

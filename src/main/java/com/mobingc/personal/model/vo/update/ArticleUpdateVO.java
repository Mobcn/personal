package com.mobingc.personal.model.vo.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 * 文章更新对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ArticleUpdateVO对象", description="文章更新对象")
public class ArticleUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID")
    private String id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章描述")
    private String description;

    @ApiModelProperty(value = "文章封面")
    private String cover;

    @ApiModelProperty(value = "文章分类ID")
    private String categoryId;

    @ApiModelProperty(value = "文章标签ID列表")
    private Set<String> tagIds;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "是否置顶")
    private Boolean topping;

    @ApiModelProperty(value = "状态（0：隐藏，1：发布）")
    private Integer status;


}

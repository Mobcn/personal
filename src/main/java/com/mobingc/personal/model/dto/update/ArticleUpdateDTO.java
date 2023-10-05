package com.mobingc.personal.model.dto.update;

import com.mobingc.personal.base.BaseEntity;
import com.mobingc.personal.model.entity.ArticleContent;
import com.mobingc.personal.model.entity.Category;
import com.mobingc.personal.model.entity.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * <p>
 * 文章更新数据对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ArticleUpdateDTO对象", description="文章更新数据对象")
public class ArticleUpdateDTO implements BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID")
    private Long id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章描述")
    private String description;

    @ApiModelProperty(value = "文章封面")
    private String cover;

    @ApiModelProperty(value = "文章分类")
    private Category category;

    @ApiModelProperty(value = "文章标签列表")
    private Set<Tag> tags;

    @ApiModelProperty(value = "文章内容")
    private ArticleContent content;

    @ApiModelProperty(value = "是否置顶")
    private Boolean topping;

    @ApiModelProperty(value = "状态（0：隐藏，1：发布）")
    private Integer status;


}

package com.mobingc.personal.model.dto.info;

import com.mobingc.personal.model.entity.ArticleContent;
import com.mobingc.personal.model.entity.Category;
import com.mobingc.personal.model.entity.Statistic;
import com.mobingc.personal.model.entity.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * <p>
 * 文章信息数据对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ArticleInfoDTO对象", description="文章信息数据对象")
public class ArticleInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID")
    private Long id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章描述")
    private String description;

    @ApiModelProperty(value = "文章封面")
    private String cover;

    @ApiModelProperty(value = "文章内容")
    private ArticleContent content;

    @ApiModelProperty(value = "文章分类信息")
    private Category category;

    @ApiModelProperty(value = "文章标签信息列表")
    private Set<Tag> tags;

    @ApiModelProperty(value = "文章统计信息")
    private Statistic statistic;

    @ApiModelProperty(value = "是否置顶")
    private Boolean topping;

    @ApiModelProperty(value = "状态（0：隐藏，1：发布）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}

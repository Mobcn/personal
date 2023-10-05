package com.mobingc.personal.model.dto.add;

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
import java.util.Set;

/**
 * <p>
 * 文章保存数据对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ArticleSaveDTO对象", description="文章保存数据对象")
public class ArticleAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章描述")
    private String description;

    @ApiModelProperty(value = "文章封面")
    private String cover;

    @ApiModelProperty(value = "文章分类ID")
    private Category category;

    @ApiModelProperty(value = "文章标签ID列表")
    private Set<Tag> tags;

    @ApiModelProperty(value = "文章内容")
    private ArticleContent content;

    @ApiModelProperty(value = "文章统计")
    private Statistic statistic;


}

package com.mobingc.personal.model.dto.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 文章查询参数对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ArticleSearchDTO对象", description="文章查询参数对象")
public class ArticleSearchDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "查询key")
    private String key;

    @ApiModelProperty(value = "分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "标签ID列表")
    private List<Long> tagIds;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "截至时间")
    private Date endTime;


}

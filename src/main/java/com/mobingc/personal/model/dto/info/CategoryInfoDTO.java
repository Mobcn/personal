package com.mobingc.personal.model.dto.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 分类信息对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CategoryInfoDTO对象", description="分类信息对象")
public class CategoryInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类ID")
    private Long id;

    @ApiModelProperty(value = "分类名")
    private String name;

    @ApiModelProperty(value = "分类描述")
    private String description;

    @ApiModelProperty(value = "包含文章数量")
    private Integer articleAmount;

    @ApiModelProperty(value = "分类图片")
    private String image;

    @ApiModelProperty(value = "状态（0：禁用，1：启用）")
    private Boolean status;


}

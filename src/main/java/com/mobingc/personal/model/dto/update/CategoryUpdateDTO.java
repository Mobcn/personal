package com.mobingc.personal.model.dto.update;

import com.mobingc.personal.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分类更新对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CategoryUpdateDTO对象", description="分类更新对象")
public class CategoryUpdateDTO implements BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类ID")
    private Long id;

    @ApiModelProperty(value = "分类名")
    private String name;

    @ApiModelProperty(value = "分类描述")
    private String description;

    @ApiModelProperty(value = "分类图片")
    private String image;

    @ApiModelProperty(value = "分类状态")
    private Boolean status;

}

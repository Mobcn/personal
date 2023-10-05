package com.mobingc.personal.model.dto.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 标签信息数据对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TagInfoDTO对象", description="标签信息数据对象")
public class TagInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签ID")
    private Long id;

    @ApiModelProperty(value = "标签名")
    private String name;

    @ApiModelProperty(value = "标签描述")
    private String description;

    @ApiModelProperty(value = "包含文章数量")
    private Integer articleAmount;

    @ApiModelProperty(value = "状态（0：禁用，1：启用）")
    private Boolean status;

}

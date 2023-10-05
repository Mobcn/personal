package com.mobingc.personal.model.vo.info;

import com.mobingc.personal.base.BaseResultData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 标签信息对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TagInfoVO对象", description="标签信息对象")
public class TagInfoVO implements BaseResultData {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签ID")
    private String id;

    @ApiModelProperty(value = "标签名")
    private String name;

    @ApiModelProperty(value = "标签描述")
    private String description;

    @ApiModelProperty(value = "包含文章数量")
    private Integer articleAmount;

    @ApiModelProperty(value = "状态（0：禁用，1：启用）")
    private Boolean status;


}

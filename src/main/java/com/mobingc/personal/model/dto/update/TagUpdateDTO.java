package com.mobingc.personal.model.dto.update;

import com.mobingc.personal.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 标签更新对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TagUpdateDTO对象", description="标签更新对象")
public class TagUpdateDTO implements BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签ID")
    private Long id;

    @ApiModelProperty(value = "标签名")
    private String name;

    @ApiModelProperty(value = "标签描述")
    private String description;

    @ApiModelProperty(value = "标签状态")
    private Boolean status;


}

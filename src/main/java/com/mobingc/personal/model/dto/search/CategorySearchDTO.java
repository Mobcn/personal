package com.mobingc.personal.model.dto.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 分类查询参数对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CategorySearchDTO对象", description="分类查询参数对象")
public class CategorySearchDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "查询key")
    private String key;


}

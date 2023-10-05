package com.mobingc.personal.model.vo.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 标签查询参数对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TagSearchVO对象", description="标签查询参数对象")
public class TagSearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "查询key")
    private String key;


}

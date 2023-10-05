package com.mobingc.personal.model.vo.field;

import com.mobingc.personal.base.BaseResultData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * ID字段列表返回对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="IdsFieldVO对象", description="ID字段列表返回对象")
public class IdsFieldVO<ID> implements BaseResultData {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private List<ID> id;


}

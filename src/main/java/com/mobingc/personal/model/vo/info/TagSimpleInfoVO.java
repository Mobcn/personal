package com.mobingc.personal.model.vo.info;

import com.mobingc.personal.base.BaseResultData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 标签简要信息对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TagSimpleInfoVO对象", description="标签简要信息对象")
public class TagSimpleInfoVO implements BaseResultData {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签ID")
    private String id;

    @ApiModelProperty(value = "标签名")
    private String name;


}

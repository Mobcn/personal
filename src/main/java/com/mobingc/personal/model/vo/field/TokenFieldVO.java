package com.mobingc.personal.model.vo.field;

import com.mobingc.personal.base.BaseResultData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 令牌字段返回对象
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TokenFieldVO对象", description="令牌字段返回对象")
public class TokenFieldVO<T> implements BaseResultData {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "令牌")
    private T token;


}

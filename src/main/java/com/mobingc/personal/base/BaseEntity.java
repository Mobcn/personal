package com.mobingc.personal.base;

import java.io.Serializable;

/**
 * 基础实体对象接口
 *
 * @author Mo
 * @since 2022-08-25
 */
public interface BaseEntity<ID extends Serializable> extends Serializable {

    /**
     * 获取ID
     *
     * @return ID
     */
    ID getId();

}

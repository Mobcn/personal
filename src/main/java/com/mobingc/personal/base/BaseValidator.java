package com.mobingc.personal.base;

/**
 * 校验器接口
 *
 * @author Mo
 * @since 2022-08-25
 */
public interface BaseValidator<T> {

    /**
     * 校验
     * @param entity 校验对象
     */
    void validate(T entity);

}

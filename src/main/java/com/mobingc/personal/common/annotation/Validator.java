package com.mobingc.personal.common.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 校验器注解
 *
 * @author Mo
 * @since 2022-08-25
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validator {

    /**
     * 校验器默认名称
     */
    String DEFAULT_VALIDATOR_NAME = "";

    /**
     * 校验器名称
     */
    @AliasFor("name")
    String value() default DEFAULT_VALIDATOR_NAME;

    /**
     * 校验器名称
     */
    @AliasFor("value")
    String name() default DEFAULT_VALIDATOR_NAME;

}

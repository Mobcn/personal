package com.mobingc.personal.common.utils;

import java.lang.reflect.Field;

/**
 * 反射工具类
 */
public class ReflectUtils {

    private ReflectUtils() {
    }

    /**
     * 设置值
     *
     * @param fieldName 对象字段名
     * @param object    对象
     * @param value     设置字段值
     */
    public static <T> void setFieldValue(String fieldName, T object, Object value) {
        if (object == null) return;
        Class<?> clazz = object.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}

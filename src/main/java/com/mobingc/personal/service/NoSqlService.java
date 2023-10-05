package com.mobingc.personal.service;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * <p>
 * 键值数据库 服务类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
public interface NoSqlService {

    /**
     * 判断是否存在键
     *
     * @param key 键
     * @return 是否存在键
     */
    Boolean hasKey(String key);

    /**
     * 设置键值
     *
     * @param key   键
     * @param value 值
     */
    void set(String key, String value);

    /**
     * 设置键值
     *
     * @param key     键
     * @param value   值
     * @param timeout 有效时间
     */
    void set(String key, String value, Long timeout);

    /**
     * 设置键值
     *
     * @param key   键
     * @param value 值对象
     */
    <T> void setObject(String key, T value);

    /**
     * 设置键值
     *
     * @param key     键
     * @param value   值对象
     * @param timeout 有效时间
     */
    <T> void setObject(String key, T value, Long timeout);

    /**
     * 获取值
     *
     * @param key 键
     * @return 值
     */
    String get(String key);

    /**
     * 获取值对象
     *
     * @param key   键
     * @param clazz 值类对象
     * @return 值
     */
    <T> T getObject(String key, Class<T> clazz);

    /**
     * 获取值对象
     *
     * @param key   键
     * @param valueTypeRef 值类型
     * @return 值
     */
    <T> T getObject(String key, TypeReference<T> valueTypeRef);

    /**
     * 删除键值
     *
     * @param key 键
     */
    void remove(String key);

}

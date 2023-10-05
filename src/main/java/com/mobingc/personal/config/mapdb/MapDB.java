package com.mobingc.personal.config.mapdb;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mobingc.personal.common.utils.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * MapDB
 **/
@Component
public class MapDB {

    /**
     * MapDB默认文件
     */
    private static final String DEFAULT_FILE = "./data/mapdb";

    /**
     * 跳转标识（用于过期缓存Map）
     */
    private static final String JUMP_FLAG = "_VALUE_ON_MAPDB:";

    /**
     * MapDB实例
     */
    private final DB db;

    /**
     * 默认缓存Map
     */
    private final ConcurrentMap<String, String> defaultMap;

    /**
     * 过期清理线程池
     */
    private final ScheduledExecutorService expireExecutor;

    public MapDB() {
        this(DEFAULT_FILE);
    }

    public MapDB(String file) {
        this.db = DBMaker.fileDB(file).checksumHeaderBypass().closeOnJvmShutdown().make();
        this.defaultMap = this.db
                .hashMap("CaseCache", Serializer.STRING, Serializer.STRING)
                // 设置缓存的容量，当超出expireMaxSize时，按照LRU进行缓存回收。
                .expireMaxSize(10000L)
                .createOrOpen();
        this.expireExecutor = Executors.newScheduledThreadPool(2);
    }

    /**
     * 判断是否存在键
     *
     * @param key 键
     * @return 是否存在键
     */
    public boolean hasKey(String key) {
        if (this.defaultMap.containsKey(key)) {
            String value = this.defaultMap.get(key);
            if (value.startsWith(JUMP_FLAG)) {
                String mapName = value.substring(JUMP_FLAG.length());
                ConcurrentMap<String, String> concurrentMap = this.db.hashMap(mapName, Serializer.STRING, Serializer.STRING).createOrOpen();
                if (concurrentMap.containsKey(key)) {
                    return true;
                }
                this.defaultMap.remove(key);
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置键值对象
     *
     * @param key   键
     * @param value 值对象
     */
    public <T> void setObject(String key, T value) {
        setObject(key, value, null);
    }

    /**
     * 设置键值对象
     *
     * @param key     键
     * @param value   值对象
     * @param timeout 有效时间（秒）
     */
    public <T> void setObject(String key, T value, Long timeout) {
        set(key, JSONUtils.toJSONString(value), timeout);
    }

    /**
     * 设置键值
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, String value) {
        set(key, value, null);
    }

    /**
     * 设置键值
     *
     * @param key     键
     * @param value   值
     * @param timeout 有效时间（秒）
     */
    public void set(String key, String value, Long timeout) {
        if (StringUtils.isNotBlank(key) && StringUtils.isNotEmpty(value)) {
            if (timeout != null) {
                String mapName = "TIMEOUT_MAP_" + timeout;
                ConcurrentMap<String, String> concurrentMap = this.db.hashMap(mapName, Serializer.STRING, Serializer.STRING)
                        .expireMaxSize(10000L)
                        .expireAfterCreate(timeout, TimeUnit.SECONDS)
                        .expireExecutor(this.expireExecutor)
                        .expireExecutorPeriod(Math.min(10000L, timeout))
                        .createOrOpen();
                concurrentMap.remove(key);
                concurrentMap.put(key, value);
                value = JUMP_FLAG + mapName;
            }
            this.defaultMap.put(key, value);
        } else {
            throw new RuntimeException("键和值不能为空!");
        }
    }

    /**
     * 获取值对象
     *
     * @param key   键
     * @param clazz 值类对象
     * @return 值
     */
    public <T> T getObject(String key, Class<T> clazz) {
        return JSONUtils.parse2Object(get(key), clazz);
    }

    /**
     * 获取值对象
     *
     * @param key          键
     * @param valueTypeRef 值类型
     * @return 值
     */
    public <T> T getObject(String key, TypeReference<T> valueTypeRef) {
        return JSONUtils.parse2Object(get(key), valueTypeRef);
    }

    /**
     * 获取值
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        String value = this.defaultMap.get(key);
        if (value == null) {
            return null;
        }
        if (value.startsWith(JUMP_FLAG)) {
            String mapName = value.substring(JUMP_FLAG.length());
            ConcurrentMap<String, String> concurrentMap = this.db.hashMap(mapName, Serializer.STRING, Serializer.STRING).createOrOpen();
            value = concurrentMap.get(key);
            if (value == null) {
                this.defaultMap.remove(key);
                return null;
            }
        }
        return value;
    }

    /**
     * 删除键值
     *
     * @param key 键
     */
    public void remove(String key) {
        String value = this.defaultMap.get(key);
        if (value != null && value.startsWith(JUMP_FLAG)) {
            String mapName = value.substring(JUMP_FLAG.length());
            remove(key, mapName);
        }
        this.defaultMap.remove(key);
    }

    /**
     * 删除键值
     *
     * @param key     键
     * @param mapName 缓存Map
     */
    private void remove(String key, String mapName) {
        ConcurrentMap<String, String> concurrentMap = this.db.hashMap(mapName, Serializer.STRING, Serializer.STRING).createOrOpen();
        concurrentMap.remove(key);
    }

}


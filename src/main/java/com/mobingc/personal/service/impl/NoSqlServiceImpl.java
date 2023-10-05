package com.mobingc.personal.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mobingc.personal.config.mapdb.MapDB;
import com.mobingc.personal.service.NoSqlService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 键值数据库 服务实现类
 * </p>
 *
 * @author Mo
 * @since 2022-08-25
 */
@Service
public class NoSqlServiceImpl implements NoSqlService {

    private final MapDB mapDB;

    public NoSqlServiceImpl(MapDB mapDB) {
        this.mapDB = mapDB;
    }

    @Override
    public Boolean hasKey(String key) {
        return mapDB.hasKey(key);
    }

    @Override
    public void set(String key, String value) {
        mapDB.set(key, value);
    }

    @Override
    public void set(String key, String value, Long timeout) {
        mapDB.set(key, value, timeout);
    }

    @Override
    public <T> void setObject(String key, T value) {
        mapDB.setObject(key, value);
    }

    @Override
    public <T> void setObject(String key, T value, Long timeout) {
        mapDB.setObject(key, value, timeout);
    }

    @Override
    public String get(String key) {
        return mapDB.get(key);
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        return mapDB.getObject(key, clazz);
    }

    @Override
    public <T> T getObject(String key, TypeReference<T> valueTypeRef) {
        return mapDB.getObject(key, valueTypeRef);
    }

    @Override
    public void remove(String key) {
        mapDB.remove(key);
    }

}

package com.mobingc.personal.common;

import com.mobingc.personal.base.BaseResultData;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 返回数据Map
 *
 * @author Mo
 * @since 2022-08-25
 */
public class ResultMap extends LinkedHashMap<String, Object> implements BaseResultData {

    private static final long serialVersionUID = 1L;

    private ResultMap() {
    }

    private ResultMap(int initialCapacity) {
        super(initialCapacity);
    }

    private ResultMap(Map<? extends String, ?> map) {
        super(map);
    }

    public static ResultMap create() {
        return new ResultMap();
    }

    public static ResultMap create(int initialCapacity) {
        return new ResultMap(initialCapacity);
    }

    public static ResultMap create(Map<? extends String, ?> map) {
        return new ResultMap(map);
    }

    @Override
    public ResultMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}

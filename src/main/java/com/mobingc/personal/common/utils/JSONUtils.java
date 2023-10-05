package com.mobingc.personal.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * JSON工具类
 */
public class JSONUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(JSONUtils.class);
    // Jackson核心类对象
    private final static ObjectMapper MAPPER = new ObjectMapper();
    // 日期格式化
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        // 对象的所有字段全部列入
        MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 取消默认转换timestamps形式
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 忽略空Bean转json的错误
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        MAPPER.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        // 忽略在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 解决反序列化单引号问题
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    private JSONUtils() {
    }

    /**
     * 将JSON字符串反序列化为Java对象
     *
     * @param text  JSON字符串
     * @param clazz Java对象类型
     * @param <T>   Java对象类型Tag
     * @return Java对象
     */
    public static <T> T parse2Object(String text, Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        return parse2Object(text, new TypeReference<T>(){});
    }

    /**
     * 将JSON字符串反序列化为Java对象
     *
     * @param text         JSON字符串
     * @param valueTypeRef Java对象类型
     * @param <T>          Java对象类型Tag
     * @return Java对象
     */
    public static <T> T parse2Object(String text, TypeReference<T> valueTypeRef) {
        if (StringUtils.isEmpty(text) || valueTypeRef == null) {
            return null;
        }
        try {
            return MAPPER.readValue(text, valueTypeRef);
        } catch (Exception e) {
            LOGGER.error("执行方法parse2Object出错, 错误信息: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON字符串反序列化为Java Map对象
     *
     * @param text JSON字符串
     * @return Java Map对象
     */
    public static Map<String, Object> parse2Map(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        try {
            return MAPPER.readValue(text, new TypeReference<Map<String, Object>>(){});
        } catch (Exception e) {
            LOGGER.error("执行方法parse2Map出错, 错误信息: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON字符串反序列化为Java列表对象
     *
     * @param text  JSON字符串
     * @param clazz Java对象类型
     * @param <T>   Java对象类型Tag
     * @return Java列表对象
     */
    public static <T> List<T> parse2List(String text, Class<T> clazz) {
        if (StringUtils.isEmpty(text) || clazz == null) {
            return null;
        }
        try {
            return MAPPER.readValue(text, new TypeReference<List<T>>(){});
        } catch (Exception e) {
            LOGGER.error("执行方法parse2List出错, 错误信息: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON字符串反序列化为Java Map列表对象
     *
     * @param text JSON字符串
     * @return Java Map列表对象
     */
    public static List<Map<String, Object>> parse2MapList(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        try {
            return MAPPER.readValue(text, new TypeReference<List<Map<String, Object>>>(){});
        } catch (Exception e) {
            LOGGER.error("执行方法parse2MapList出错, 错误信息: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param object Java对象
     * @return JSON字符串
     */
    public static String toJSONString(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return object instanceof String ? (String) object : MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            LOGGER.error("执行方法toJSONString出错, 错误信息: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}

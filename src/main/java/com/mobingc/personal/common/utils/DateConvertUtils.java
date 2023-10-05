package com.mobingc.personal.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间转换工具类
 */
public class DateConvertUtils {

    // 默认时间解析格式
    private final static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private DateConvertUtils() {
    }

    /**
     * 字符串转Date对象（时间解析格式为：yyyy-MM-dd HH:mm:ss）
     *
     * @param time 时间字符串
     * @return Date对象
     */
    public static Date parse2Date(String time) {
        return DateConvertUtils.parse2Date(time, DateConvertUtils.DEFAULT_PATTERN);
    }

    /**
     * 字符串转Date对象
     *
     * @param time    时间字符串
     * @param pattern 时间解析格式
     * @return Date对象
     */
    public static Date parse2Date(String time, String pattern) {
        LocalDateTime localDateTime = DateConvertUtils.parse2LocalDateTime(time, pattern);
        return DateConvertUtils.localDateTime2Date(localDateTime);
    }

    /**
     * 字符串转LocalDateTime对象（时间解析格式为：yyyy-MM-dd HH:mm:ss）
     *
     * @param time 时间字符串
     * @return LocalDateTime对象
     */
    public static LocalDateTime parse2LocalDateTime(String time) {
        return DateConvertUtils.parse2LocalDateTime(time, DateConvertUtils.DEFAULT_PATTERN);
    }

    /**
     * 字符串转LocalDateTime对象
     *
     * @param time    时间字符串
     * @param pattern 时间解析格式
     * @return LocalDateTime对象
     */
    public static LocalDateTime parse2LocalDateTime(String time, String pattern) {
        if (StringUtils.isEmpty(time)) {
            throw new IllegalArgumentException("参数time必须有值!");
        }
        if (StringUtils.isEmpty(pattern)) {
            throw new IllegalArgumentException("参数pattern必须有值!");
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(time, dateTimeFormatter);
    }

    /**
     * Date对象转字符串（时间解析格式为：yyyy-MM-dd HH:mm:ss）
     *
     * @param date Date对象
     * @return 时间字符串
     */
    public static String toDateString(Date date) {
        return DateConvertUtils.toDateString(date, DateConvertUtils.DEFAULT_PATTERN);
    }

    /**
     * Date对象转字符串
     *
     * @param date    Date对象
     * @param pattern 时间解析格式
     * @return 时间字符串
     */
    public static String toDateString(Date date, String pattern) {
        LocalDateTime localDateTime = DateConvertUtils.date2LocalDateTime(date);
        return DateConvertUtils.toDateString(localDateTime, pattern);
    }

    /**
     * LocalDateTime对象转字符串（时间解析格式为：yyyy-MM-dd HH:mm:ss）
     *
     * @param localDateTime LocalDateTime对象
     * @return 时间字符串
     */
    public static String toDateString(LocalDateTime localDateTime) {
        return DateConvertUtils.toDateString(localDateTime, DateConvertUtils.DEFAULT_PATTERN);
    }

    /**
     * LocalDateTime对象转字符串
     *
     * @param localDateTime LocalDateTime对象
     * @param pattern       时间解析格式
     * @return 时间字符串
     */
    public static String toDateString(LocalDateTime localDateTime, String pattern) {
        if (localDateTime == null) {
            throw new IllegalArgumentException("参数localDateTime不能为null!");
        }
        if (StringUtils.isEmpty(pattern)) {
            throw new IllegalArgumentException("参数pattern必须有值!");
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * LocalDateTime对象转Date对象
     *
     * @param localDateTime LocalDateTime对象
     * @return Date对象
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            throw new IllegalArgumentException("参数localDateTime不能为null!");
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * Date对象转LocalDateTime对象
     *
     * @param date Date对象
     * @return LocalDateTime对象
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("参数date不能为null!");
        }
        ZoneId zone = ZoneId.systemDefault();
        return date.toInstant().atZone(zone).toLocalDateTime();
    }

}

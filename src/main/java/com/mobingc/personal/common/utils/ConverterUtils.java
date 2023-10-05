package com.mobingc.personal.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.function.Function;

/**
 * 转换工具类
 */
public class ConverterUtils {

    private ConverterUtils() {
    }

    /**
     * 对象属性复制转换
     *
     * @param src       源对象
     * @param destClass 目标对象类
     * @return 目标对象
     */
    public static <D, S> D copyConvert(S src, Class<D> destClass) {
        return convert(src, ConverterUtils.copyConverter(destClass));
    }

    /**
     * 对象转换
     *
     * @param src       源对象
     * @param converter 转换器
     * @return 目标对象
     */
    public static <D, S> D convert(S src, Function<S, D> converter) {
        return converter.apply(src);
    }

    /**
     * 属性复制转换器
     *
     * @param destClass 目标对象类
     * @return 转换器
     */
    public static <S, D> Function<S, D> copyConverter(Class<D> destClass) {
        return src -> {
            try {
                D dest = destClass.newInstance();
                BeanUtils.copyProperties(src, dest);
                return dest;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("对象转换失败!", e);
            }
        };
    }

}

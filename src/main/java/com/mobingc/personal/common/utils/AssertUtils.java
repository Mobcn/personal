package com.mobingc.personal.common.utils;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 断言工具类
 */
public class AssertUtils {

    private AssertUtils() {
    }

    /**
     * 结果断言
     */
    public static <T, E extends RuntimeException> void isTrue(Boolean asserted, Supplier<E> exSupplier) {
        // 断言未通过
        if (!asserted) throw exSupplier.get();
    }

    /**
     * 结果断言
     */
    public static <T, E extends RuntimeException> void isTrue(Boolean condition, Boolean asserted, Supplier<E> exSupplier) {
        if (condition) {
            // 断言未通过
            if (!asserted) throw exSupplier.get();
        }
    }

    /**
     * 单参数断言
     */
    public static <T, E extends RuntimeException> void isTrue(T t, Predicate<T> verifier, Supplier<E> exSupplier) {
        // 断言结果
        boolean asserted = verifier.test(t);
        // 断言未通过
        if (!asserted) throw exSupplier.get();
    }

    /**
     * 单参数断言
     */
    public static <T, E extends RuntimeException> void isTrue(Boolean condition, T t, Predicate<T> verifier, Supplier<E> exSupplier) {
        if (condition) {
            // 断言结果
            boolean asserted = verifier.test(t);
            // 断言未通过
            if (!asserted) throw exSupplier.get();
        }
    }

    /**
     * 比较参数断言
     */
    public static <T, E extends RuntimeException> void isTrue(T caller, T reference, BiPredicate<T, T> verifier, Supplier<E> exSupplier) {
        // 断言结果
        boolean asserted = verifier.test(caller, reference);
        // 断言未通过
        if (!asserted) throw exSupplier.get();
    }

    /**
     * 比较参数断言
     */
    public static <T, E extends RuntimeException> void isTrue(Boolean condition, T caller, T reference, BiPredicate<T, T> verifier, Supplier<E> exSupplier) {
        if (condition) {
            // 断言结果
            boolean asserted = verifier.test(caller, reference);
            // 断言未通过
            if (!asserted) throw exSupplier.get();
        }
    }

    /**
     *  对象为空
     */
    public static <T> Boolean isNull(T t) {
        return Objects.isNull(t);
    }

    /**
     * 对象非空
     */
    public static <T> Boolean isNotNull(T t) {
        return !isNull(t);
    }

    /**
     * 比较后相等
     */
    public static <T extends Comparable<T>> Boolean isEq(T caller, T reference) {
        return caller.compareTo(reference) == 0;
    }

    /**
     * 比较不相等
     */
    public static <T extends Comparable<T>> Boolean isNe(T caller, T reference) {
        return caller.compareTo(reference) != 0;
    }

    /**
     * 比较后大于
     */
    public static <T extends Comparable<T>> Boolean isGt(T caller, T reference) {
        return caller.compareTo(reference) > 0;
    }

    /**
     * 比较后大于等于
     */
    public static <T extends Comparable<T>> Boolean isGe(T caller, T reference) {
        return caller.compareTo(reference) >= 0;
    }

    /**
     * 比较后小于
     */
    public static <T extends Comparable<T>> Boolean isLt(T caller, T reference) {
        return caller.compareTo(reference) < 0;
    }

    /**
     * 比较后小于等于
     */
    public static <T extends Comparable<T>> Boolean isLe(T caller, T reference) {
        return caller.compareTo(reference) <= 0;
    }

}

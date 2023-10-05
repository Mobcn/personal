package com.mobingc.personal.common;

import com.mobingc.personal.base.BaseResultData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 返回数据列表
 *
 * @author Mo
 * @since 2022-08-25
 */
public class ResultList<T> extends ArrayList<T> implements BaseResultData {

    private ResultList() {
    }

    private ResultList(int initialCapacity) {
        super(initialCapacity);
    }

    private ResultList(@NotNull Collection<? extends T> c) {
        super(c);
    }

    public static <E> ResultList<E> create() {
        return new ResultList<>();
    }

    public static <E> ResultList<E> create(int initialCapacity) {
        return new ResultList<>(initialCapacity);
    }

    public static <E> ResultList<E> create(@NotNull Collection<? extends E> c) {
        return new ResultList<>(c);
    }

}

package com.mobingc.personal.common;

import com.mobingc.personal.base.BaseResultData;
import com.mobingc.personal.common.utils.ConverterUtils;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 返回分页数据
 *
 * @author Mo
 * @since 2022-08-25
 */
public class ResultPage<R extends BaseResultData> implements Serializable, BaseResultData {

    private static final long serialVersionUID = 1L;

    /**
     * 数据总条数
     */
    private final Long total;

    /**
     * 数据列表
     */
    private final List<R> list;

    public ResultPage(Long total, List<R> list) {
        this.total = total;
        this.list = list;
    }

    public <T> ResultPage(Page<T> page, Class<R> clazz) {
        this.total = page.getTotalElements();
        this.list = page.stream().map(ConverterUtils.copyConverter(clazz)).collect(Collectors.toList());
    }

    public <T> ResultPage(Page<T> page, Function<T, R> converter) {
        this.total = page.getTotalElements();
        this.list = page.stream().map(converter).collect(Collectors.toList());
    }

    public Long getTotal() {
        return total;
    }

    public List<R> getList() {
        return list;
    }

}

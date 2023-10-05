package com.mobingc.personal.common;

import com.mobingc.personal.base.BaseResultData;
import com.mobingc.personal.common.enums.ResultCode;

import java.io.Serializable;

/**
 * 返回对象
 *
 * @author Mo
 * @since 2022-08-25
 */
public class Result<T extends BaseResultData> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应代码
     */
    private final Integer code;

    /**
     * 响应消息
     */
    private final String message;

    /**
     * 响应数据
     */
    private final T data;

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <E extends BaseResultData> Result<E> error() {
        return error(ResultCode.ERROR.getMessage());
    }

    public static <E extends BaseResultData> Result<E> error(String message) {
        return error(ResultCode.ERROR.getCode(), message);
    }

    public static <E extends BaseResultData> Result<E> error(E data) {
        return error(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage(), data);
    }

    public static <E extends BaseResultData> Result<E> error(ResultCode resultCode) {
        return error(resultCode, null);
    }

    public static <E extends BaseResultData> Result<E> error(ResultCode resultCode, E data) {
        return error(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static <E extends BaseResultData> Result<E> error(Integer code, String message) {
        return error(code, message, null);
    }

    public static <E extends BaseResultData> Result<E> error(String message, E data) {
        return error(ResultCode.ERROR.getCode(), message, data);
    }

    public static <E extends BaseResultData> Result<E> error(Integer code, String message, E data) {
        return new Result<>(code, message, data);
    }

    public static <E extends BaseResultData> Result<E> ok() {
        return ok(ResultCode.OK.getMessage());
    }

    public static <E extends BaseResultData> Result<E> ok(String message) {
        return ok(ResultCode.OK.getCode(), message);
    }

    public static <E extends BaseResultData> Result<E> ok(E data) {
        return ok(ResultCode.OK.getCode(), ResultCode.OK.getMessage(), data);
    }

    public static <E extends BaseResultData> Result<E> ok(ResultCode resultCode) {
        return ok(resultCode, null);
    }

    public static <E extends BaseResultData> Result<E> ok(ResultCode resultCode, E data) {
        return ok(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static <E extends BaseResultData> Result<E> ok(Integer code, String message) {
        return ok(code, message, null);
    }

    public static <E extends BaseResultData> Result<E> ok(String message, E data) {
        return ok(ResultCode.OK.getCode(), message, data);
    }

    public static <E extends BaseResultData> Result<E> ok(Integer code, String message, E data) {
        return new Result<>(code, message, data);
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}

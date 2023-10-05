package com.mobingc.personal.common.exception;

import com.mobingc.personal.common.enums.ResultCode;

/**
 * 自定义异常类
 *
 * @author Mo
 * @since 2022-08-25
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误代码
     */
    protected Integer errorCode;

    /**
     * 错误信息
     */
    protected String errorMessage;

    /**
     * 错误类别信息
     */
    protected String errorTypeMessage;


    public BizException() {
        super();
        this.errorCode = ResultCode.BUSINESS_ERROR.getCode();
        this.errorMessage = ResultCode.BUSINESS_ERROR.getMessage();
        this.errorTypeMessage = ResultCode.BUSINESS_ERROR.getMessage();
    }


    public BizException(ResultCode resultCode) {
        super(String.valueOf(resultCode.getCode()));
        this.errorCode = resultCode.getCode();
        this.errorMessage = resultCode.getMessage();
        this.errorTypeMessage = resultCode.getTypeMessage() == null ? resultCode.getMessage() : resultCode.getTypeMessage();
    }


    public BizException(ResultCode resultCode, Throwable cause) {
        super(String.valueOf(resultCode.getCode()), cause);
        this.errorCode = resultCode.getCode();
        this.errorMessage = resultCode.getMessage();
        this.errorTypeMessage = resultCode.getTypeMessage() == null ? resultCode.getMessage() : resultCode.getTypeMessage();
    }


    public BizException(String errorMessage) {
        super(errorMessage);
        this.errorCode = ResultCode.BUSINESS_ERROR.getCode();
        this.errorMessage = errorMessage;
        this.errorTypeMessage = ResultCode.BUSINESS_ERROR.getMessage();
    }


    public BizException(Integer errorCode, String errorMessage) {
        super(String.valueOf(errorCode));
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorTypeMessage = ResultCode.BUSINESS_ERROR.getMessage();
    }


    public BizException(Integer errorCode, String errorMessage, Throwable cause) {
        super(String.valueOf(errorCode), cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorTypeMessage = ResultCode.BUSINESS_ERROR.getMessage();
    }


    public Integer getErrorCode() {
        return errorCode;
    }


    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }


    public String getErrorMessage() {
        return errorMessage;
    }


    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorTypeMessage() {
        return errorTypeMessage;
    }

    public void setErrorTypeMessage(String errorTypeMessage) {
        this.errorTypeMessage = errorTypeMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

}

package com.mobingc.personal.common.handler;

import com.mobingc.personal.common.exception.BizException;
import com.mobingc.personal.common.Result;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.common.ResultMap;
import com.mobingc.personal.model.entity.Logging;
import com.mobingc.personal.service.LoggingService;
import com.mobingc.personal.common.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常配置类
 *
 * @author Mo
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final LoggingService loggingService;

    public GlobalExceptionHandler(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    /**
     * 处理自定义的业务异常
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public Result<ResultMap> bizExceptionHandler(HttpServletRequest request, BizException e) {
        log.error("发生业务异常! 原因是: ", e);
        ResultMap resultMap = ResultMap.create(1).put("errorMessage", e.getErrorMessage());
        Result<ResultMap> result = Result.error(e.getErrorCode(), e.getErrorTypeMessage(), resultMap);
        this.saveLogging(request.getAttribute("errorLogging"), result);
        return result;
    }

    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public Result<ResultMap> exceptionHandler(HttpServletRequest request, NullPointerException e) {
        log.error("发生空指针异常! 原因是: ", e);
        ResultMap resultMap = ResultMap.create(1).put("errorMessage", e.getMessage());
        Result<ResultMap> result = Result.error(ResultCode.NULL_POINTER_ERROR, resultMap);
        this.saveLogging(request.getAttribute("errorLogging"), result);
        return result;
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<ResultMap> exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("未知异常! 原因是: ", e);
        ResultMap resultMap = ResultMap.create(1).put("errorMessage", e.getMessage());
        Result<ResultMap> result = Result.error(resultMap);
        this.saveLogging(request.getAttribute("errorLogging"), result);
        return result;
    }

    /**
     * 保存异常日志记录
     *
     * @param errorLogging 错误日志对象
     * @param result 返回值
     */
    private void saveLogging(Object errorLogging, Result<?> result) {
        if (errorLogging instanceof Logging) {
            // 保存异常日志记录
            Logging logging = (Logging) errorLogging;
            String resultString = JSONUtils.toJSONString(result);
            if (resultString.length() > 255) {
                resultString = resultString.substring(0, 252) + "...";
            }
            logging.setResult(resultString);
            loggingService.save(logging);
        }
    }

}

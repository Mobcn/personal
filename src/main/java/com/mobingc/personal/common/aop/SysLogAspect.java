package com.mobingc.personal.common.aop;

import com.mobingc.personal.common.Result;
import com.mobingc.personal.common.enums.ResultCode;
import com.mobingc.personal.model.entity.Logging;
import com.mobingc.personal.service.LoggingService;
import com.mobingc.personal.common.utils.JSONUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.StringJoiner;

/**
 * 系统访问日志记录
 *
 * @author Mo
 * @since 2022-08-25
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect {

    private final LoggingService loggingService;

    public SysLogAspect(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    /**
     * 设置切入点记录操作日志 扫描所有controller包下操作
     */
    @Pointcut("execution(* com.mobingc.personal.controller..*.*(..))")
    public void logPointCut() {
    }

    /**
     * 请求前置记录
     */
    @Before("logPointCut()")
    public void beforeSysLog(JoinPoint joinPoint) {
        try {
            // 解析获取日志信息
            LogInfo logInfo = new LogInfo(joinPoint);
            // 执行日志记录
            log.info("请求{}, 执行{}: {}.{}()", logInfo.getRequestURI(), logInfo.getOperationName(), logInfo.getClassName(), logInfo.getMethodName());
        } catch (Exception e) {
            log.warn("日志记录异常!", e);
            e.printStackTrace();
        }
    }


    /**
     * 请求后置记录
     */
    @AfterReturning(value = "logPointCut()", returning = "result")
    public void afterSysLog(JoinPoint joinPoint, Object result) {
        try {
            // 解析获取日志信息
            LogInfo logInfo = new LogInfo(joinPoint);
            // 创建日志数据对象
            Logging logging = this.createLogging(logInfo, result);
            // 保存日志数据对象
            loggingService.save(logging);
            log.info("执行结果: {}", JSONUtils.toJSONString(result));
        } catch (Exception e) {
            log.warn("日志记录异常!", e);
            e.printStackTrace();
        }
    }

    /**
     * 异常后置记录
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "result")
    public void throwSysLog(JoinPoint joinPoint, Throwable result) {
        try {
            // 解析获取日志信息
            LogInfo logInfo = new LogInfo(joinPoint);
            // 创建日志数据对象
            Logging logging = this.createLogging(logInfo, result);
            // 获取当前Http请求对象
            HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
            // 添加日志数据对象
            request.setAttribute("errorLogging", logging);
        } catch (Exception e) {
            log.warn("日志记录异常!", e);
            e.printStackTrace();
        }
    }

    /**
     * 创建日志对象
     *
     * @param logInfo 日志信息对象
     * @param result  执行结果
     * @return 日志对象
     */
    private Logging createLogging(LogInfo logInfo, Object result) {
        Logging logging = new Logging();
        logging.setOperation(logInfo.getOperationName() + " | " + logInfo.getClassName() + "." + logInfo.getMethodName() + "()");
        logging.setParams(logInfo.getParams());
        logging.setCreateTime(logInfo.getOperationTime());
        if (result instanceof Result) {
            Result<?> resultObj = (Result<?>) result;
            String resultString = JSONUtils.toJSONString(result);
            if (resultString.length() > 255) {
                resultString = resultString.substring(0, 252) + "...";
            }
            logging.setResult(resultString);
            logging.setStatus(ResultCode.OK.getCode().equals(resultObj.getCode()));
        } else if (result instanceof Throwable) {
            logging.setStatus(false);
        } else {
            logging.setStatus(result != null);
            logging.setResult(JSONUtils.toJSONString(result));
        }
        return logging;
    }

    /**
     * 日志信息对象
     */
    static class LogInfo {

        /**
         * 操作时间
         */
        private final Date operationTime;

        /**
         * 操作名称
         */
        private final String operationName;

        /**
         * 操作URI
         */
        private final String requestURI;

        /**
         * 操作类名
         */
        private final String className;

        /**
         * 操作方法名
         */
        private final String methodName;

        /**
         * 请求参数
         */
        private final String params;

        private LogInfo(JoinPoint joinPoint) {
            // 获取当前操作时间
            this.operationTime = new Date();
            // 获取请求的类名
            this.className = joinPoint.getTarget().getClass().getName();
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取请求的方法名
            this.methodName = method.getName();
            // 获取Swagger注解信息
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            // 获取操作名称
            this.operationName = apiOperation != null ? apiOperation.value() : "";
            // 获取请求参数
            this.params = this.getParams(joinPoint);
            // 获取当前Http请求对象
            HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
            // 获取操作URI
            this.requestURI = request.getRequestURI();
        }

        public Date getOperationTime() {
            return operationTime;
        }

        public String getOperationName() {
            return operationName;
        }

        public String getRequestURI() {
            return requestURI;
        }

        public String getClassName() {
            return className;
        }

        public String getMethodName() {
            return methodName;
        }

        public String getParams() {
            return params;
        }

        /**
         * 获取请求参数
         */
        private String getParams(JoinPoint joinPoint) {
            StringJoiner params = new StringJoiner(" | ");
            Object[] joinPointArgs = joinPoint.getArgs();
            if (joinPointArgs != null && joinPointArgs.length > 0) {
                for (Object joinPointArg : joinPointArgs) {
                    if (joinPointArg instanceof HttpServletResponse
                            || joinPointArg instanceof HttpServletRequest
                            || joinPointArg instanceof MultipartFile
                            || joinPointArg instanceof MultipartFile[]) {
                        continue;
                    }
                    params.add(JSONUtils.toJSONString(joinPointArg));
                }
            }
            String paramsString = params.toString();
            if (paramsString.length() > 255) {
                paramsString = paramsString.substring(0, 252) + "...";
            }
            return paramsString;
        }

    }

}


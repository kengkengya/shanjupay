package com.shanjupay.merchant.common.intercept;


import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import com.shanjupay.common.domain.ErrorCode;
import com.shanjupay.common.domain.RestErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 全局异常处理程序
 * 全局异常处理器使用ControllerAdvice注解实现，ControllerAdvice是SpringMVC3.2提供的注解，
 * 用 ControllerAdvice可以方便实现对Controller面向切面编程
 * 1、ControllerAdvice和ExceptionHandler注解实现全局异常处理
 * 2、ControllerAdvice和ModelAttribute注解实现全局数据绑定
 * 3、ControllerAdvice生InitBinder注解实现全局数据预处理
 *
 * ControllerAdvice和ExceptionHandler结合可以捕获Controller抛出的异常，
 * 根据异常处理流程，Service和持久层 最终都会抛给Controller，所以此方案可以实现全局异常捕获，
 * 异常被捕获到即可格式为前端要的信息格式响应给前端。
 * @author guolonghang
 * @date 2021-11-02
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 日志记录器
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 全局处理异常处理器
     *
     * @param request  请求
     * @param response 响应
     * @param e        e
     * @return {@code RestErrorResponse}
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse processExcetion(HttpServletRequest request, HttpServletResponse response, Exception e) {
        //如果是自定义异常则直接取出异常信息

        if (e instanceof BusinessException) {
            LOGGER.info(e.getMessage(), e);
            BusinessException businessException = (BusinessException) e;
            ErrorCode errorCode = businessException.getErrorCode();

            return new RestErrorResponse(errorCode.getDesc(), String.valueOf(errorCode.getCode()));
        }
        LOGGER.info("系统异常", e);
        return new RestErrorResponse(CommonErrorCode.UNKOWN.getDesc(), String.valueOf(CommonErrorCode.UNKOWN.getCode()));

    }
}
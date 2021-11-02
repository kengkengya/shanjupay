package com.shanjupay.common.domain;


import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * 自定义业务处理器
 *
 * @author guolonghang
 * @date 2021-11-02
 */
@ApiModel(value = "RestErrorResponse", description = "错误响应参数包装")
@Data
public class RestErrorResponse {

    /**
     * 错误代码
     */
    private String errorCode;

    /**
     * 错误消息
     */
    private String errorMessage;

    /**
     * 自定义业务处理器
     *
     * @param errorCode    错误代码
     * @param errorMessage 错误消息
     */
    public RestErrorResponse(String errorMessage, String errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}

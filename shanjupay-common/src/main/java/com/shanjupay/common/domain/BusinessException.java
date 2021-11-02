package com.shanjupay.common.domain;

/**
 * 业务异常
 *
 * @author guolonghang
 * @date 2021-11-02
 */

public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    /**
     * 业务异常
     *
     * @param errorCode 错误代码
     */
    public BusinessException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    /**
     * 业务异常
     */
    public BusinessException() {
        super();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}

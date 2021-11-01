package com.shanjupay.merchant.service;

/**
 * @author guolonghang
 */
public interface SmsService {

    /**
     * 发送短信服务
     *
     * @param phone 电话
     * @return {@code String}
     */
    String sendMsg(String phone);

    /**
     * 检查验证码，抛出异常则无效
     *
     * @param verifiyKey  verifiy关键
     * @param verifiyCode verifiy代码
     */
    void checkVerifiyCode(String verifiyKey,String verifiyCode);
}

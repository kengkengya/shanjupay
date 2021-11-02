package com.shanjupay.merchant.service;

import com.alibaba.fastjson.JSON;
import com.shanjupay.common.domain.BusinessException;
import com.shanjupay.common.domain.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务impl
 * 此处供本地controller层调用实现，因此不需要dubbo的service（dubbo服务是rpc调用）
 *
 * @author guolonghang
 * @date 2021-11-01
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${sms.url}")
    private String url;

    @Value("${sms.effectiveTime}")
    private String effectiveTime;

    /**
     * 发送短信验证码
     *
     * @param phone 电话
     * @return {@code String}
     */
    @Override
    public String sendMsg(String phone) {
        //向验证码发送服务请求
        String sms_url = url + "/generate?name=sms&effectiveTime=" + effectiveTime;
        log.info("调用短信微服务发送验证码：url:{}", url);

        //请求头
        HashMap<String, Object> body = new HashMap<>();
        body.put("mobile", phone);

        //请求体
        HttpHeaders httpHeaders = new HttpHeaders();
        //设置为json格式
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //封装请求参数
        HttpEntity httpEntity = new HttpEntity(body);

        Map responseMap = null;
        ResponseEntity<Map> exchange = null;
        //如果请求抛送异常，则在此处抛出一个异常
        try {
            //发送post参数
            exchange = restTemplate.exchange(sms_url, HttpMethod.POST, httpEntity, Map.class);
            log.info("调用短信微服务发送验证码:返回值:{}", JSON.toJSONString(exchange));
            //获取response
            responseMap = exchange.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("发送验证码错误");
        }

        // 取出body里的result数据
        if (responseMap == null || responseMap.get("result") == null) {
            throw new RuntimeException("发送验证码错误");
        }
        Map resultMap = (Map) responseMap.get("result");
        String key = resultMap.get("key").toString();
        log.info("发送人手机号phone：{}", phone);
        log.info("得到解析到的key：{}", key);
        return key;
    }

    /**
     * 校验验证码
     *
     * @param verifiyKey  verifiy关键
     * @param verifiyCode verifiy代码
     */
    @Override
    public void checkVerifiyCode(String verifiyKey, String verifiyCode) throws BusinessException {
        //实现校验验证码的逻辑
        String url = this.url + "/verify?name=sms&verificationCode=" + verifiyCode +
                "&verificationKey=" + verifiyKey;
        //发送请求进行验证码校验
        Map responseMap = null;
        ResponseEntity<Map> exchange = null;
        //通过try-catch块确保sql语句执行错误，提示异常
        try {
            exchange = restTemplate.exchange(url, HttpMethod.POST, HttpEntity.EMPTY, Map.class);
            responseMap = exchange.getBody();
            log.info("校验验证码，响应内容为：{}", JSON.toJSONString(responseMap));
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new BusinessException(CommonErrorCode.E_100102);
        }
        //校验得到的map不为空，且校验正确
        if (responseMap == null || responseMap.get("result") == null || !(Boolean) responseMap.get("result")) {
            throw new BusinessException(CommonErrorCode.E_100102);
        }


    }
}

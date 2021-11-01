package com.shanjupay.merchant;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SmsTest {

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 测试短信
     */
    @Test
    public void testSms() {
        //验证码过 期时间为600秒
        String url = "http://localhost:56085/sailing/generate?effectiveTime=600&name=sms";
        String phone = "18801350184";
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

        try {
            //发送post参数
            ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
            log.info("调用短信微服务发送验证码:返回值:{}", JSON.toJSONString(exchange));
            //获取response
            responseMap = exchange.getBody();
            System.out.println(responseMap);
        } catch (RestClientException e) {
            log.info(e.getMessage(), e);
        }

        // 取出body里的result数据
        if (responseMap != null || responseMap.get("result") != null) {
            Map resultMap = (Map) responseMap.get("result");
            String value = resultMap.get("key").toString();
            System.out.println(value);
        }


    }
}

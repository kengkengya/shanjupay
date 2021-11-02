package com.shanjupay.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootApplication
public class MerchantApplicationBootstrap {
    /**
     * 主要
     *
     * @param args arg游戏
     */
    public static void main(String[] args) {
        SpringApplication.run(MerchantApplicationBootstrap.class, args);
    }


    /**
     * 其他模板
     * RestTemplate是Spring提供的用于访问RESTful服务的客户端，RestTemplate提供了多种便捷访问远程Http服务
     * 的方法,能够大大提高客户端的编写效率。RestTemplate默认依赖JDK提供http连接的能力
     *
     * 网页内容中中文乱码解决方案：
     * 【原因】
     * RestTemplate默认使用String存储body内容时默认使用ISO_8859_1字符集。
     * 【解决】
     * 配置StringHttpMessageConverter 消息转换器，使用utf-8字符集。
     *
     * @return {@code RestTemplate}
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory());
        //获取消息转换器列表
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        //配置消息转换器为utf-8
        messageConverters.set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8) {
        });
        return restTemplate;
    }
}

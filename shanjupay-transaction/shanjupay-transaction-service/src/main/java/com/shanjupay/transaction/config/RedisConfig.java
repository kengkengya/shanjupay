package com.shanjupay.transaction.config;


import com.shanjupay.common.cache.Cache;
import com.shanjupay.transaction.common.util.RedisCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 复述,配置
 *
 * @author guolonghang
 * @date 2021-11-10
 */
@Configuration
public class RedisConfig {

    /**
     * 缓存
     *
     * @param redisTemplate 复述,模板
     * @return {@code Cache}
     */
    @Bean
    public Cache cache(StringRedisTemplate redisTemplate) {
        return new RedisCache(redisTemplate);
    }
}

package com.springboot.cloud.shop.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://180.76.59.81:6379")
                .setPassword("Cheng_Yi_Shu_Yuan_2020.9_DM");
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

}

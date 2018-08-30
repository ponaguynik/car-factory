package com.myprojects.carfactory.carassembler.config;

import com.myprojects.carfactory.carassembler.converter.BytesToWheelsConverter;
import com.myprojects.carfactory.carassembler.converter.WheelsToBytesConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.convert.RedisCustomConversions;

import java.util.Arrays;

@Configuration
public class AppConfig {

    @Bean
    public RedisCustomConversions redisCustomConversions() {
        return new RedisCustomConversions(Arrays.asList(new WheelsToBytesConverter(), new BytesToWheelsConverter()));
    }
}

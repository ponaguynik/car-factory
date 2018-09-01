package com.myprojects.carfactory.carassembler.config;

import com.myprojects.carfactory.carassembler.converter.BytesToWheelsConverter;
import com.myprojects.carfactory.carassembler.converter.WheelsToBytesConverter;
import com.myprojects.carfactory.carassembler.model.CarConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;

import java.util.Arrays;

@Configuration
public class RedisConfig {

    @Bean
    public RedisCustomConversions redisCustomConversions() {
        return new RedisCustomConversions(Arrays.asList(new WheelsToBytesConverter(), new BytesToWheelsConverter()));
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }
}

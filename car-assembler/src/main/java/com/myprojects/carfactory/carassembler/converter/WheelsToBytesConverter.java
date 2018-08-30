package com.myprojects.carfactory.carassembler.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myprojects.carfactory.model.Wheels;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@WritingConverter
public class WheelsToBytesConverter implements Converter<Wheels, byte[]> {
    private final Jackson2JsonRedisSerializer<Wheels> serializer;

    public WheelsToBytesConverter() {
        this.serializer = new Jackson2JsonRedisSerializer<>(Wheels.class);
        serializer.setObjectMapper(new ObjectMapper());
    }

    @Override
    public byte[] convert(Wheels wheels) {
        return serializer.serialize(wheels);
    }
}

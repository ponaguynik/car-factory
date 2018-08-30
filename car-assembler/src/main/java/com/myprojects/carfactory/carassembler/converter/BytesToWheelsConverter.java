package com.myprojects.carfactory.carassembler.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myprojects.carfactory.model.Wheels;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@ReadingConverter
public class BytesToWheelsConverter implements Converter<byte[], Wheels> {
    private final Jackson2JsonRedisSerializer<Wheels> serializer;

    public BytesToWheelsConverter() {
        this.serializer = new Jackson2JsonRedisSerializer<>(Wheels.class);
        serializer.setObjectMapper(new ObjectMapper());
    }

    @Override
    public Wheels convert(byte[] source) {
        return serializer.deserialize(source);
    }
}

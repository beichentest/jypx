package com.geekcattle.redis.serializer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;

/**
 *
 * @author apple
 */
public class JdkSerializationRedisSerializer implements RedisSerializer<Object> {

    private final Converter<Object, byte[]> serializer;
    private final Converter<byte[], Object> deserializer;

    public JdkSerializationRedisSerializer() {
        serializer = new SerializingConverter();
        deserializer  = new DeserializingConverter();
    }
    
    public Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            return deserializer.convert(bytes);
        } catch (Exception ex) {
            throw new SerializationException("Cannot deserialize", ex);
        }
    }

    public byte[] serialize(Object object) {
        if (object == null) {
            return EMPTY_BYTES;
        }
        try {
            return serializer.convert(object);
        } catch (Exception ex) {
            throw new SerializationException("Cannot serialize", ex);
        }
    }

}

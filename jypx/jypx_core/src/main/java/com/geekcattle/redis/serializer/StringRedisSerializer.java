package com.geekcattle.redis.serializer;

import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.RedisSerializer;
/**
 *
 * @author apple
 */
public class StringRedisSerializer implements RedisSerializer<String> {
	public byte[] EMPTY_BYTES = new byte[0];
    public Charset UTF_8 = Charset.forName("UTF-8");
    @Override
    public byte[] serialize(String str) {
        return (str == null || str.length() == 0) ? EMPTY_BYTES : str.getBytes(UTF_8);
    }

    @Override
    public String deserialize(byte[] bytes) {
        return (bytes == null || bytes.length == 0) ? "" : new String(bytes, UTF_8);
    }	    
}

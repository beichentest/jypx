package com.geekcattle.redis.serializer;

import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xerial.snappy.Snappy;
import org.springframework.data.redis.serializer.RedisSerializer;
/**
 *
 * @author apple
 */
public class SnappyRedisSerializer<T> implements RedisSerializer<T> {
	public byte[] EMPTY_BYTES = new byte[0];
    public Charset UTF_8 = Charset.forName("UTF-8");
    private static final Logger log = LoggerFactory.getLogger(SnappyRedisSerializer.class);
    
    private final RedisSerializer<T> inner;

    public SnappyRedisSerializer() {
        this(new FstRedisSerializer<T>());
    }

    public SnappyRedisSerializer(RedisSerializer<T> innerSerializer) {
        assert (innerSerializer != null);
        this.inner = innerSerializer;
    }

    @Override
    public byte[] serialize(T graph) {
        try {
            return Snappy.compress(inner.serialize(graph));
        } catch (IOException e) {
            log.error("Fail to serialize graph.", e);
            return EMPTY_BYTES;
        }
    }

    @Override
    public T deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0)
            return null;
        try {
            return inner.deserialize(Snappy.uncompress(bytes));
        } catch (IOException e) {
            log.error("Fail to deserialize graph.", e);
            return null;
        }
    }
    
}

package com.geekcattle.redis.serializer;

import java.nio.charset.Charset;

/**
 *
 * @author apple
 */
public interface RedisSerializer<T> {
    
    public byte[] EMPTY_BYTES = new byte[0];

    public Charset UTF_8 = Charset.forName("UTF-8");

    /**
     * Serialize Object
     * @param graph
     * @return 
     */
    byte[] serialize(final T graph);

    /**
     * Deserialize to object
     * @param bytes
     * @return 
     */
    T deserialize(final byte[] bytes);
    
}

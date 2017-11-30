package com.geekcattle.redis.serializer;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
import org.springframework.data.redis.serializer.RedisSerializer;
/**
 *
 * @author apple
 */
public class FstRedisSerializer<T> implements RedisSerializer<T> {
	public byte[] EMPTY_BYTES = new byte[0];
    public Charset UTF_8 = Charset.forName("UTF-8");
    private static final Logger log = Logger.getLogger(FstRedisSerializer.class);
    
    private final FSTConfiguration conf;

    public FstRedisSerializer() {
        conf = FSTConfiguration.createDefaultConfiguration();
    }
    
    @Override
    public byte[] serialize(final T graph) {
        if (graph == null)
            return EMPTY_BYTES;

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            FSTObjectOutput oos = conf.getObjectOutput(os);
            oos.writeObject(graph);
            oos.flush();

            return os.toByteArray();
        } catch (Exception e) {
            log.warn("Fail to serializer graph. graph=" + graph, e);
            return EMPTY_BYTES;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(final byte[] bytes) {
        if (SerializationTool.isEmpty(bytes))
            return null;

        try {
            ByteArrayInputStream is = new ByteArrayInputStream(bytes);
            FSTObjectInput ois = conf.getObjectInput(is);
            return (T) ois.readObject();
        } catch (Exception e) {
            log.warn("Fail to deserialize bytes.", e);
            return null;
        }
    }
    
}

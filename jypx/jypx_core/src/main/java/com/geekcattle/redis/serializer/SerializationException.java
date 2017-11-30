package com.geekcattle.redis.serializer;


/**
 *
 * @author apple
 */
public class SerializationException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7944470179272038138L;

	public SerializationException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}

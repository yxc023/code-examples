package com.yangxiaochen.examples.json;

/**
 * @author yangxiaochen
 * @date 16/5/15 上午12:18
 */
public class JsonProcessingQuietException extends RuntimeException {
    public JsonProcessingQuietException() {
        super();
    }

    public JsonProcessingQuietException(String message) {
        super(message);
    }

    public JsonProcessingQuietException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonProcessingQuietException(Throwable cause) {
        super(cause);
    }

    protected JsonProcessingQuietException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

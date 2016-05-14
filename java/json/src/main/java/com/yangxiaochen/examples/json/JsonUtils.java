package com.yangxiaochen.examples.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author yangxiaochen
 * @date 16/5/14 下午9:48
 */
public class JsonUtils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    public static String object2String(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonProcessingQuietException(e);
        }
    }
}

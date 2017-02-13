package com.yangxiaochen.examples.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = new HashMap();
        map.put("姓名", "杨晓辰");

        System.out.println(objectMapper.writeValueAsString(map));

        Set<String> set = new HashSet<>();
        set.add("11");
        set.add("22");
        System.out.println(objectMapper.writeValueAsString(set));

    }
}

package xyz.huanju.accounting.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author HuanJu
 * @date 2020/8/9 17:17
 */
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static{
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public static String toJson(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> T toObject(String str,Class<T> clazz){
        try {
            return MAPPER.readValue(str,clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}

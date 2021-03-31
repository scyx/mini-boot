package boot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author dell
 */
public class SerializerUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static byte[] serialize(Object object) {
        byte[] bytes = new byte[0];
        try {
            bytes = objectMapper.writeValueAsBytes(object);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return bytes;
    }

    public static Object convert(Class<?> type, String s) {
        return objectMapper.convertValue(s,type);
    }
}

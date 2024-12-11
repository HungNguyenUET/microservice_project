package hungnv.account_service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> String toJson(T t) {
        try {
            String jsonString = objectMapper.writeValueAsString(t);
            return jsonString;
        } catch (JsonProcessingException e) {
            log.info("JsonUtils|toJson|ERROR|", e);
            return null;
        }
    }
}

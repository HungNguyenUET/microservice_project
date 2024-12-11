package hungnv.account_service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JsonUtils {
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    public static  <T> String toJson(T t) {
        try {
            String jsonString = objectMapper.writeValueAsString(t);
            System.out.println("JSON String: " + jsonString);
        } catch (JsonProcessingException e) {
            log.info("JsonUtils|toJson|ERROR|", e);
        }
    }
}

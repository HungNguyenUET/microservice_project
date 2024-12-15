package hungnv.account_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseAPIDTO <T> {
    @Builder.Default
    private int code = 200;
    private String message;
    private T result;
}

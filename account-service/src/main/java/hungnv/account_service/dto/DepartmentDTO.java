package hungnv.account_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
public class DepartmentDTO {
    private String name;

    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    private List<AccountDTO> accounts;

    @Data
    @NoArgsConstructor
    static class AccountDTO {

        @JsonProperty("accountId")
        private int id;

        private String username;
    }
}

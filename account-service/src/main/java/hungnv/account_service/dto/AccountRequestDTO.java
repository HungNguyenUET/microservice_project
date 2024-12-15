package hungnv.account_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRequestDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String role;
    private String password;
}

package hungnv.account_service.dto;

import lombok.Data;

@Data
public class AccountDTO {
    private int id;

    private String username;

    private String firstName;

    private String lastName;

    private String role;

    private String departmentName;

    private int departmentId;
}

package hungnv.account_service.model;

import lombok.Data;

@Data
public class Account {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String role;
    private String departmentName;
    private int departmentId;
}

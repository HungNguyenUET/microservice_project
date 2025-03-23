package hungnv.department_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DepartmentDTO {
    private String name;

    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    private List<AccountDTO> accounts;
}

package hungnv.account_service.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountFilterForm {

	private Integer minId;

	private Integer maxId;

}


package com.vti.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTO {

	private int id;
	
	private String username;

	private String departmentName;
}

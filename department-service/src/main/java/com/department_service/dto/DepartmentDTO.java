package com.department_service.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDTO {

	private String name;

	private String type;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;

	private List<AccountDTO> accounts;
}


package com.vti.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.vti.entity.Department;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentFilterForm {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date minCreatedDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date maxCreatedDate;

	private Integer minYear;
	
	private Department.DepartmentType type;

}


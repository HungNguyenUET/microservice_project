package com.department_service.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.department_service.dto.DepartmentDTO;
import com.department_service.entity.Department;
import com.department_service.service.IDepartmentService;

@RestController
@RequestMapping(value = "/api/v1/departments")
public class DepartmentController {
	@Autowired
	private IDepartmentService departmentService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<DepartmentDTO> getAllDepartment() {
		List<Department> departments = departmentService.getAllDepartments();
		return modelMapper.map(
				departments,
				new TypeToken<List<DepartmentDTO>>() {}.getType()
		);
	}
}

package com.vti.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.vti.dto.DepartmentDTO;
import com.vti.entity.Department;
import com.vti.form.DepartmentFilterForm;
import com.vti.service.IDepartmentService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/departments")
public class DepartmentController {
	private final IDepartmentService dpService;
	private final ModelMapper modelMapper;

	
	@GetMapping()
	public Page<DepartmentDTO> getAllDepartments(
			Pageable pageable,
			@RequestParam(name = "search", required = false) String search,
			DepartmentFilterForm filterForm) {
		Page<Department> entityPages = dpService.getAllDepartments(pageable, search, filterForm);

		// convert entities --> dtos
		List<DepartmentDTO> dtos = modelMapper.map(
				entityPages.getContent(),
				new TypeToken<List<DepartmentDTO>>() {}.getType());

		Page<DepartmentDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

		return dtoPages;

	}

	@GetMapping(value = "/{id}")
	public DepartmentDTO getDepartmentById(@PathVariable(name = "id") int id) {
		Department department = dpService.getDepartmentById(id);

		// convert entity to dto
		DepartmentDTO dpDTO = modelMapper.map(department, DepartmentDTO.class);

		return dpDTO;
	}

//	@GetMapping
//	public List<DepartmentDTO> getListAccounts() {
//		List<Department> departments = dpService.getListDepartment();
//
//		List<DepartmentDTO> listDpDTO = modelMapper.map(
//				departments,
//				new TypeToken<List<DepartmentDTO>>() {}.getType()
//		);
//
//		return listDpDTO;
//	}
}

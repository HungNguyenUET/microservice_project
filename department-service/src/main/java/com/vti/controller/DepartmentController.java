package com.vti.controller;

import com.vti.dto.DepartmentDTO;
import com.vti.entity.Department;
import com.vti.form.DepartmentFilterForm;
import com.vti.service.IDepartmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/departments")
public class DepartmentController {
	private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);
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
		log.info("DepartmentController|getDepartmentById|START|id|{}", id);
		Department department = dpService.getDepartmentById(id);
    private final IDepartmentService departmentService;

    @GetMapping
    public List<Department> getAllDepartment() {
        return departmentService.getAllDepartments();
    }
		// convert entity to dto
		DepartmentDTO dpDTO = modelMapper.map(department, DepartmentDTO.class);
		log.info("DepartmentController|getDepartmentById|START|id|{}|dpDTP|{}", id, dpDTO);
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

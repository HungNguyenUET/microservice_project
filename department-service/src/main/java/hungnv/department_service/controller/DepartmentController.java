package hungnv.department_service.controller;

import java.util.List;

import hungnv.department_service.dto.DepartmentDTO;
import hungnv.department_service.entity.DepartmentEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import hungnv.department_service.service.IDepartmentService;

@RestController
@RequestMapping(value = "/api/v1/departments")
public class DepartmentController {
	@Autowired
	private IDepartmentService departmentService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<DepartmentDTO> getAllDepartment() {
		List<DepartmentEntity> departments = departmentService.getAllDepartments();
		return modelMapper.map(
				departments,
				new TypeToken<List<DepartmentDTO>>() {}.getType()
		);
	}
}

package hungnv.department_service.controller;

import hungnv.department_service.dto.DepartmentDTO;
import hungnv.department_service.entity.DepartmentEntity;
import hungnv.department_service.service.IDepartmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/departments")
public class DepartmentController {
    private final IDepartmentService departmentService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<DepartmentDTO> getAllDepartment() {
        List<DepartmentEntity> departments = departmentService.getAllDepartments();
        return modelMapper.map(
                departments,
                new TypeToken<List<DepartmentDTO>>() {
                }.getType()
        );
    }
}

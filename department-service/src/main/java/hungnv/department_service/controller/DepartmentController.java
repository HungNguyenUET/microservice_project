package hungnv.department_service.controller;

import hungnv.department_service.dto.DepartmentDTO;
import hungnv.department_service.entity.DepartmentEntity;
import hungnv.department_service.service.IDepartmentService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment() {
        final List<DepartmentEntity> departments = departmentService.getAllDepartments();
        final List<DepartmentDTO> lsDepartmentDTO = modelMapper.map(
                    departments,
                    new TypeToken<List<DepartmentDTO>>() {
                }.getType()
        );

        return ResponseEntity.ok(lsDepartmentDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable("id") int id) {
        final DepartmentEntity departmentEntity = departmentService.getDepartmentById(id);
        final DepartmentDTO departmentDTO = modelMapper.map(
                    departmentEntity,
                    new TypeToken<DepartmentDTO>() {
                }.getType()
        );

        return ResponseEntity.ok(departmentDTO);
    }
}

package hungnv.department_service.service;

import hungnv.department_service.entity.DepartmentEntity;

import java.util.List;

public interface IDepartmentService {
    List<DepartmentEntity> getAllDepartments();
    DepartmentEntity getDepartmentById(int id);
}

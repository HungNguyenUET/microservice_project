package hungnv.department_service.service;

import hungnv.department_service.entity.DepartmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService implements IDepartmentService {
    @Autowired
    private hungnv.department_service.repository.IDepartmentRepository repository;

    @Override
    public List<DepartmentEntity> getAllDepartments() {
        return repository.findAll();
    }

}

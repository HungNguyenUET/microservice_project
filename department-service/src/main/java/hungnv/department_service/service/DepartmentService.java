package hungnv.department_service.service;

import hungnv.department_service.entity.DepartmentEntity;
import hungnv.department_service.repository.IDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService implements IDepartmentService {
    @Autowired
    private IDepartmentRepository repository;

    @Override
    public List<DepartmentEntity> getAllDepartments() {
        return repository.findAll();
    }

    @Override
    public DepartmentEntity getDepartmentById(int id) {
        final Optional<DepartmentEntity> departmentEntityOpt = repository.findById(id);
        return departmentEntityOpt.orElse(null);
    }

}

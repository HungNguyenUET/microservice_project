package com.department_service.service;

import com.department_service.entity.Department;
import com.department_service.repository.IDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService implements IDepartmentService {
    @Autowired
    private IDepartmentRepository repository;

    @Override
    public List<Department> getAllDepartments() {
        return repository.findAll();
    }

}

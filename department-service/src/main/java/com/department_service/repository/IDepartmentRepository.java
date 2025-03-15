package com.department_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.department_service.entity.Department;

public interface IDepartmentRepository extends JpaRepository<Department, Integer> {
}

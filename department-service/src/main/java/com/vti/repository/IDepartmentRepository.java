package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vti.entity.Department;

public interface IDepartmentRepository extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {
}

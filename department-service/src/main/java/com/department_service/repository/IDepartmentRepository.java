package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vti.entity.DepartmentEntity;

public interface IDepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
}

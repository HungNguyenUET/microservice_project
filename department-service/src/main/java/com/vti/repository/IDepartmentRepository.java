package com.vti.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vti.entity.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDepartmentRepository extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM department dp WHERE dp.id = :id"
    )
    List<Department> getDepartmentsByAccountId(@Param("id") int id);

    Department findById(int id);

    Page<Department> findAll(Specification<Department> where, Pageable pageable);
}

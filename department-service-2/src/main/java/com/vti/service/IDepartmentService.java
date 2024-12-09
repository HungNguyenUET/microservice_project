package com.vti.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.entity.Department;
import com.vti.form.DepartmentFilterForm;

import java.util.List;

public interface IDepartmentService {

	Page<Department> getAllDepartments(Pageable pageable, String search, DepartmentFilterForm filterForm);

	Department getDepartmentById(int id);

	List<Department> getListDepartment();

	List<Department> getDepartmentsByAccountId(int acId);
}

package com.vti.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.entity.Department;
import com.vti.form.DepartmentFilterForm;

public interface IDepartmentService {

	public Page<Department> getAllDepartments(Pageable pageable, String search, DepartmentFilterForm filterForm);

	public Department getDepartmentByID(int id);
}

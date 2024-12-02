package com.vti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vti.entity.Department;
import com.vti.form.DepartmentFilterForm;
import com.vti.repository.IDepartmentRepository;
import com.vti.specification.department.DepartmentSpecification;

@Service
public class DepartmentService implements IDepartmentService {

	@Autowired
	private IDepartmentRepository repository;

	public Page<Department> getAllDepartments(Pageable pageable, String search, DepartmentFilterForm filterForm) {

		Specification<Department> where = DepartmentSpecification.buildWhere(search, filterForm);
		return repository.findAll(where, pageable);
	}

	@Override
	public Department getDepartmentByID(int id) {
		return repository.findById(id).isPresent()? repository.findById(id).get() : null;
	}

}

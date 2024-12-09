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

import java.util.List;

@Service
public class DepartmentService implements IDepartmentService {

	@Autowired
	private IDepartmentRepository dpRepository;

	public Page<Department> getAllDepartments(Pageable pageable, String search, DepartmentFilterForm filterForm) {

		Specification<Department> where = DepartmentSpecification.buildWhere(search, filterForm);
		return dpRepository.findAll(where, pageable);
	}

	@Override
	public Department getDepartmentById(int id) {
		return dpRepository.findById(id);
	}

	@Override
	public List<Department> getListDepartment() {
		return dpRepository.findAll();
	}

	@Override
	public List<Department> getDepartmentsByAccountId(int acId) {
		return dpRepository.getDepartmentsByAccountId(acId);
	}

}

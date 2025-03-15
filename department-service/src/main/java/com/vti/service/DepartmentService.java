package com.vti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vti.entity.Department;
import com.vti.form.DepartmentFilterForm;
import com.vti.dto.Account;
import com.vti.dto.Department;
import com.vti.entity.AccountEntity;
import com.vti.entity.DepartmentEntity;
import com.vti.repository.IDepartmentRepository;
import com.vti.specification.department.DepartmentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {
    private final IDepartmentRepository repository;

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
    public List<Department> getAllDepartments() {
        List<DepartmentEntity> departmentEntities = repository.findAll();
        List<Department> departments = new ArrayList<>();

        for (DepartmentEntity departmentEntity: departmentEntities) {
            List<AccountEntity> accountEntities = departmentEntity.getAccountEntityEntities();
            List<Account> accounts = new ArrayList<>();
            for (AccountEntity accountEntity: accountEntities) {
                Account account = Account.builder()
                        .id(accountEntity.getId())
                        .departmentId(departmentEntity.getId())
                        .departmentName(departmentEntity.getName())
                        .firstName(accountEntity.getFirstName())
                        .lastName(accountEntity.getLastName())
                        .username(accountEntity.getUsername())
                        .build();
                accounts.add(account);
            }

            Department department = Department.builder()
                    .type(departmentEntity.getType().toString())
                    .createdDate(departmentEntity.getCreatedAt())
                    .name(departmentEntity.getName())
                    .accounts(accounts)
                    .build();
            departments.add(department);
        }

	@Override
	public List<Department> getDepartmentsByAccountId(int acId) {
		return dpRepository.getDepartmentsByAccountId(acId);
	}

        return departments;
    }

}

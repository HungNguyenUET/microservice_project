package com.department_service.service;

import com.department_service.dto.Account;
import com.department_service.dto.Department;
import com.department_service.entity.AccountEntity;
import com.department_service.entity.DepartmentEntity;
import com.department_service.repository.IDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {
    private final IDepartmentRepository repository;

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


        return departments;
    }

}

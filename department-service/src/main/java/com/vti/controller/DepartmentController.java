package com.vti.controller;

import com.vti.dto.Department;
import com.vti.service.IDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/departments")
public class DepartmentController {
    private final IDepartmentService departmentService;

    @GetMapping
    public List<Department> getAllDepartment() {
        return departmentService.getAllDepartments();
    }
}

package com.department_service.dto;

import lombok.Builder;

@Builder
public class Account {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String departmentName;
    private int departmentId;
}

package com.department_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
<<<<<<<< HEAD:department-service/src/main/java/com/department_service/entity/AccountEntity.java
@Table(name = "`account`")
@Getter
public class AccountEntity {
    @Column(name = "id")
========
@Table(name = "account")
public class Account {
>>>>>>>> 47f37699d2ea9828b383ce775658bc07a27d7ddb:department-service/src/main/java/com/department_service/entity/Account.java
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private DepartmentEntity departmentEntity;
}

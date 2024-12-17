package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vti.entity.Account;

public interface IAccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {
}

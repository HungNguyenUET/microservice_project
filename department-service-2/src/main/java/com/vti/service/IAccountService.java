package com.vti.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.entity.Account;
import com.vti.form.AccountFilterForm;

public interface IAccountService {

	public Page<Account> getAllAccounts(Pageable pageable, String search, AccountFilterForm filterForm);

}

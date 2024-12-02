package com.vti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vti.entity.Account;
import com.vti.form.AccountFilterForm;
import com.vti.repository.IAccountRepository;
import com.vti.specification.account.AccountSpecification;

@Service
public class AccountService implements IAccountService {

	@Autowired
	private IAccountRepository repository;

	public Page<Account> getAllAccounts(
			Pageable pageable, 
			String search, 
			AccountFilterForm filterForm) {
		
		Specification<Account> where = AccountSpecification.buildWhere(search, filterForm);
		return repository.findAll(where, pageable);
	}
}



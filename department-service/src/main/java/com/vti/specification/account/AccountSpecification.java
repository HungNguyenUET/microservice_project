package com.vti.specification.account;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vti.entity.Account;
import com.vti.form.AccountFilterForm;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class AccountSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<Account> buildWhere(String search, AccountFilterForm filterForm) {
		
		Specification<Account> where = null;
		
		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification username = new CustomSpecification("username", search);
			CustomSpecification departmentName = new CustomSpecification("departmentName", search);
			where = Specification.where(username).or(departmentName);
		}
		
		// if there is filter by min id
		if (filterForm != null && filterForm.getMinId() != null) {
			CustomSpecification minId = new CustomSpecification("minId", filterForm.getMinId());
			if (where == null) {
				where = minId;
			} else {
				where = where.and(minId);
			}
		}
		
		// if there is filter by max id
		if (filterForm != null && filterForm.getMaxId() != null) {
			CustomSpecification maxId = new CustomSpecification("maxId", filterForm.getMaxId());
			if (where == null) {
				where = maxId;
			} else {
				where = where.and(maxId);
			}
		}

		return where;
	}
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<Account> {

	@NonNull
	private String field;
	@NonNull
	private Object value;

	@Override
	public Predicate toPredicate(
			Root<Account> root, 
			CriteriaQuery<?> query, 
			CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("username")) {
			return criteriaBuilder.like(root.get("username"), "%" + value.toString() + "%");
		}
		
		if (field.equalsIgnoreCase("minId")) {
			return criteriaBuilder.greaterThanOrEqualTo(root.get("id"), value.toString());
		}
		
		if (field.equalsIgnoreCase("maxId")) {
			return criteriaBuilder.lessThanOrEqualTo(root.get("id"), value.toString());
		}
		
		if (field.equalsIgnoreCase("departmentName")) {
			return criteriaBuilder.like(root.get("department").get("name"), "%" + value.toString() + "%");
		}

		return null;
	}
}


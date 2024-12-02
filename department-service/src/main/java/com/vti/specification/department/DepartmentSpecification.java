package com.vti.specification.department;

import java.util.Date;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vti.entity.Department;
import com.vti.form.DepartmentFilterForm;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class DepartmentSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<Department> buildWhere(String search, DepartmentFilterForm filterForm) {
		
		Specification<Department> where = null;
		
		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification username = new CustomSpecification("username", search);
			where = Specification.where(username);
		}
		// if there is filter by min created date
		if (filterForm != null && filterForm.getCreatedDate() != null) {
			CustomSpecification createdDate = new CustomSpecification("createdDate", filterForm.getCreatedDate());
			if (where == null) {
				where = createdDate;
			} else {
				where = where.and(createdDate);
			}
		}
				
		// if there is filter by min created date
		if (filterForm != null && filterForm.getMinCreatedDate() != null) {
			CustomSpecification minCreatedDate = new CustomSpecification("minCreatedDate", filterForm.getMinCreatedDate());
			if (where == null) {
				where = minCreatedDate;
			} else {
				where = where.and(minCreatedDate);
			}
		}		
				
		// if there is filter by max created date
		if (filterForm != null && filterForm.getMaxCreatedDate() != null) {
			CustomSpecification maxCreatedDate = new CustomSpecification("maxCreatedDate", filterForm.getMaxCreatedDate());
			if (where == null) {
				where = maxCreatedDate;
			} else {
				where = where.and(maxCreatedDate);
			}
		}
				
		// if there is filter by min year
		if (filterForm != null && filterForm.getMinYear() != null) {
			CustomSpecification minYear = new CustomSpecification("minYear", filterForm.getMinYear());
			if (where == null) {
				where = minYear;
			} else {
				where = where.and(minYear);
			}
		}
		
		// if there is filter by type
		if (filterForm != null && filterForm.getType() != null) {
			CustomSpecification type = new CustomSpecification("type", filterForm.getType());
			if (where == null) {
				where = type;
			} else {
				where = where.and(type);
			}
		}
		
		return where;
	}
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<Department> {

	@NonNull
	private String field;
	@NonNull
	private Object value;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Predicate toPredicate(
			Root<Department> root, 
			CriteriaQuery<?> query, 
			CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("username")) {
			Join join = root.join("accounts", JoinType.LEFT);
			return criteriaBuilder.like(join.get("username"), "%" + value.toString() + "%");
		}
		
		if (field.equalsIgnoreCase("createdDate")) {
			return criteriaBuilder.equal(
					root.get("createdDate").as(java.sql.Date.class),
					(Date) value);
		}
		
		if (field.equalsIgnoreCase("minCreatedDate")) {
			return criteriaBuilder.greaterThanOrEqualTo(
					root.get("createdDate").as(java.sql.Date.class),
					(Date) value);
		}
	
		if (field.equalsIgnoreCase("maxCreatedDate")) {
			return criteriaBuilder.lessThanOrEqualTo(
					root.get("createdDate").as(java.sql.Date.class),
					(Date) value);
		}
		
		if (field.equalsIgnoreCase("minYear")) {
			return criteriaBuilder.greaterThanOrEqualTo(
					criteriaBuilder.function("YEAR", Integer.class, root.get("createdDate")),
					(Integer) value);
		}
		
		if (field.equalsIgnoreCase("type")) {
			return criteriaBuilder.equal(root.get("type"), value);
		}
		
		return null;
	}
}


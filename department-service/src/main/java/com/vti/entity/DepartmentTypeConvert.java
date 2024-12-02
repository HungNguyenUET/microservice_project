package com.vti.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DepartmentTypeConvert implements AttributeConverter<Department.DepartmentType, String> {

	@Override
	public String convertToDatabaseColumn(Department.DepartmentType type) {
		if (type == null) {
			return null;
		}

		return type.toString();
	}

	@Override
	public Department.DepartmentType convertToEntityAttribute(String sqlValue) {
		if (sqlValue == null) {
			return null;
		}

		return Department.DepartmentType.toEnum(sqlValue);
	}

}

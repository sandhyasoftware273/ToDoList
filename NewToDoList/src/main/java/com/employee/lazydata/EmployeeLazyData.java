package com.employee.lazydata;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.employee.model.EmployeeModel;
import com.employee.service.EmployeeService;

public class EmployeeLazyData extends LazyDataModel<EmployeeModel> {

	private EmployeeService employeeService;

	public EmployeeLazyData(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Override
	public List<EmployeeModel> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<EmployeeModel> data = employeeService.getEmployeeList(first, pageSize, sortField, sortOrder, filters);
		this.setRowCount(employeeService.getEmployeeTotalCount());
		return data;
	}

}

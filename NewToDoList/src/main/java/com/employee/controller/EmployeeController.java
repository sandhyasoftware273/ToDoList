package com.employee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;

import com.employee.lazydata.EmployeeLazyData;

import com.employee.model.EmployeeModel;
import com.employee.service.EmployeeService;

@ManagedBean(name = "emp")
@SessionScoped
public class EmployeeController {

	private LazyDataModel<EmployeeModel> lazyDataModel;
	private DualListModel<String> dualListModel;

	private boolean editable = false;
	private EmployeeModel updatedEmployee;
	private String selectEmployeeId;
	private List<String> states;
	private List<String> districts;
	private Map<String, List<String>> data =new HashMap<>();
  

	@Inject
    private EmployeeService employeeService;

	EmployeeModel employeeModel = new EmployeeModel();

	@PostConstruct
	public void init() {
		lazyDataModel = new EmployeeLazyData(employeeService);
		// pickList code
		List<String> sourceEmployee = employeeService.getAllId();
		List<String> targetEmployee = new ArrayList<String>();
		dualListModel = new DualListModel<String>(sourceEmployee, targetEmployee);
		//Dropdown
		states=new ArrayList<>();
		states.add("Andhrapradesh");
		states.add("Telangana");
		districts=new ArrayList<>();
		districts.add("vishakapatnam");
		districts.add("srikakulam");
		districts.add("vizayanagaram");
		data.put("Andhrapradesh", districts);
		districts=new ArrayList<>();
		districts.add("Hyderabad");
		districts.add("karimnagar");
		districts.add("varangal");
		data.put("Telangana",districts);
		
	}

	public void edit() {
		editable = !editable;
	}

	public void add() {
		employeeService.add(employeeModel);
		 updatePicklist();
		employeeModel = new EmployeeModel();

	}

	public List<EmployeeModel> getEmployee() {
		return employeeService.getAll();
	}

	public void updateEmployee() {
		employeeService.update(updatedEmployee);
		editable = !editable;
		employeeModel = new EmployeeModel();
	}

	public void deleteEmployee(String id) {
		employeeService.delete(id);
		 updatePicklist();
	}
	
	public void deleteSelectedItemsFromPickList(){
	    List<String> selectedItems = dualListModel.getTarget();
	    for (String id : selectedItems) {
			deleteEmployee(id);
		}
	    dualListModel.getTarget().clear(); 
	 }

	private void updatePicklist() {
		List<String> sourceEmployee = employeeService.getAllId();
	    List<String> targetEmployee = new ArrayList<>();
	    dualListModel = new DualListModel<>(sourceEmployee, targetEmployee);
	}
	
	public void onStateChange() {
		if(employeeModel.getState()!=null) {
			districts=data.get(employeeModel.getState());
		}
		else {
			districts=new ArrayList<>();
		}
	}

	//getters and setters
	public void setLazyDataModel(LazyDataModel<EmployeeModel> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}

	public LazyDataModel<EmployeeModel> getLazyDataModel() {
		return lazyDataModel;
	}

	public EmployeeModel getEmployeeModel() {
		return employeeModel;
	}

	public void setEmployeeModel(EmployeeModel employeeModel) {
		this.employeeModel = employeeModel;
	}

	public EmployeeModel getUpdatedEmployee() {
		return updatedEmployee;
	}

	public void setUpdatedEmployee(EmployeeModel updatedEmployee) {
		this.updatedEmployee = updatedEmployee;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getSelectEmployeeId() {
		return selectEmployeeId;
	}

	public void setSelectEmployeeId(String selectEmployeeId) {
		this.selectEmployeeId = selectEmployeeId;
	}

	public DualListModel<String> getDualListModel() {
		return dualListModel;
	}

	public void setDualListModel(DualListModel<String> dualListModel) {
		this.dualListModel = dualListModel;
	}

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}

	public List<String> getDistricts() {
		return districts;
	}

	public void setDistricts(List<String> districts) {
		this.districts = districts;
	}

	public Map<String, List<String>> getData() {
		return data;
	}

	public void setData(Map<String, List<String>> data) {
		this.data = data;
	}	
	
}

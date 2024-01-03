package com.sample;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

@Named
@RequestScoped
public class PickList {
	private DualListModel<String> cities;
	
	@PostConstruct
	public void init() {
		List<String> countriesource = new ArrayList<>();
		List<String> countrieTarget = new ArrayList<>();
		countriesource.add("america");
    	countriesource.add("india");
    	countriesource.add("pakistan");
    	countriesource.add("england");
    	
    	cities=new DualListModel<>(countriesource,countrieTarget );
	}

	public DualListModel<String> getCities() {
		return cities;
	}

	public void setCities(DualListModel<String> cities) {
		this.cities = cities;
	}
	

}

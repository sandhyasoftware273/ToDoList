package com.sample;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class SelectOneButtonView {

    private  List<String> countries;
    private String country;
    
    @PostConstruct
    public void init() {
    	countries=new ArrayList<>();
    	countries.add("america");
    	countries.add("india");
    	countries.add("pakistan");
    	countries.add("england");
    }

	public List<String> getCountries() {
		return countries;
	}

	public void setCountries(List<String> countries) {
		this.countries = countries;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
    
    
    
}
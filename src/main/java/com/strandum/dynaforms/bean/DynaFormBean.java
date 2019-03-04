package com.strandum.dynaforms.bean;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(value = "dynaForm")
@ViewScoped
public class DynaFormBean {

	private Map<String, Object> map = new LinkedHashMap<>();
	
	@PostConstruct
	public void initialize() {
		
		Map<String, String> parameterMap = FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRequestParameterMap();
		
		String layoutId = parameterMap.get("layoutId");
		String dataFormId = parameterMap.get("dataFormId");
		
		if (layoutId == null) {
			map = new LinkedHashMap<>();
		} else {
			
		}
		
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}

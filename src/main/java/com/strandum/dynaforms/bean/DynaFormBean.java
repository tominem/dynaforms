package com.strandum.dynaforms.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.jsf.FacesContextUtils;

import com.strandum.dynaforms.model.IFormField;
import com.strandum.dynaforms.service.FormConfigService;

@ManagedBean(name="dynaForm")
@ViewScoped
public class DynaFormBean implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * General form fields
	 */
	private Map<String, Object> fieldMap = new LinkedHashMap<>();
	
	/**
	 * Large Objects map
	 */
	private Map<String, Object> loMap = new LinkedHashMap<>();
	
//	private Map<String, IFormField>  = new LinkedHashMap<>();
	
	@Autowired
	private FormConfigService formConfigService;

	private Integer formConfigId;
	
	@PostConstruct
	public void initialize() {
		
		FacesContextUtils
			.getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
			.getAutowireCapableBeanFactory().autowireBean(this);
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRequest();
		
		Optional.ofNullable(request.getAttribute("formConfigId"))
			.ifPresent(configId -> formConfigId = Integer.valueOf(configId.toString()));
		
		loadMaps(formConfigId);
	}

	private void loadMaps(Integer formConfigId) {
		List<IFormField> formFields 	 = formConfigService.findFormFieldByFormConfig(formConfigId);
		List<IFormField> formLargeObject = formConfigService.findFormLargeObjectByFormConfig(formConfigId);
		
		putInMap(formFields, getFieldMap());
		putInMap(formLargeObject, getLoMap());
	}

	private void putInMap(List<IFormField> formFields, Map<String, Object> destiny) {
		if (formFields != null && !formFields.isEmpty()) {
			formFields.forEach(v -> {
				
				try {
//					destiny.put(v.getName(), getValueAsType(v));
					destiny.put(v.getName(), v.getValue());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				
			});
		}
	}

	private Object getValueAsType(IFormField v) throws Exception {
		return ConvertUtils.convert(v.getValue(), Class.forName(v.getType()));
	}

	public Map<String, Object> getLoMap() {
		return loMap;
	}

	public void setLoMap(Map<String, Object> loMap) {
		this.loMap = loMap;
	}

	public Map<String, Object> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(Map<String, Object> fieldMap) {
		this.fieldMap = fieldMap;
	}
	
	public void submitAction() {
		
//		List<FormField> formFields = 
//		
//		formConfigService.saveFormField(fieldMap, loMap);
//		
//		System.out.println("FIELDMAP");
		
		fieldMap.forEach((k,v)-> {
			System.out.printf("%s= %s, class=%s\n", k, v, v.getClass());
		});
		
		System.out.println("LOMAP");
		
		loMap.forEach((k,v)-> {
			System.out.printf("%s= %s, class=%s\n", k, v, v.getClass());
		});
	}

}

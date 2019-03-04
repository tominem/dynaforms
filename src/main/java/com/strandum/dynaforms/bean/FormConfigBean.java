package com.strandum.dynaforms.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.jsf.FacesContextUtils;

import com.strandum.dynaforms.filter.DynaFormFilter;
import com.strandum.dynaforms.model.FormConfig;
import com.strandum.dynaforms.model.FormLayout;
import com.strandum.dynaforms.repository.FormConfigRepository;
import com.strandum.dynaforms.repository.FormLayoutRepository;

@ManagedBean(name = "formConfigBean")
@ViewScoped
public class FormConfigBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String formName;

	private String dataIdentifier;
	
	private Boolean saved;
	
	private String token;
	
	private String url;
	
	@Autowired
	private FormLayoutRepository formLayoutRepository;
	
	@Autowired
	private FormConfigRepository formConfigRepository;
	
	@PostConstruct
	public void initialize() {
		
		FacesContextUtils
			.getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
			.getAutowireCapableBeanFactory().autowireBean(this);
		
	}
	
	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getDataIdentifier() {
		return dataIdentifier;
	}

	public void setDataIdentifier(String dataIdentifier) {
		this.dataIdentifier = dataIdentifier;
	}

	public Boolean getSaved() {
		return saved;
	}

	public void setSaved(Boolean saved) {
		this.saved = saved;
	}
	
	@Transactional
	public void saveAndGenerateToken() {
		
		String token = RandomStringUtils.random(50, true, true);
		FormLayout formLayout = formLayoutRepository.findByFormName(getFormName());
		FormConfig formConfig = new FormConfig(formLayout, getDataIdentifier(), token);
		formConfigRepository.save(formConfig);
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		this.setSaved(true);
		this.setToken(token);
		
		String requestURL = request.getRequestURL().toString();
		String appName = request.getContextPath();
		String prefixurl = requestURL.substring(0, requestURL.lastIndexOf(appName + "/") + 10);
		
		this.setUrl(String.format("%s/%s%s", prefixurl, DynaFormFilter.FORMS_PATH, token));
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

package com.strandum.dynaforms.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.jsf.FacesContextUtils;

import com.strandum.dynaforms.model.Element;
import com.strandum.dynaforms.model.Form;
import com.strandum.dynaforms.model.FormElement;
import com.strandum.dynaforms.prop.FieldProperty;
import com.strandum.dynaforms.service.FormElementService;

@ManagedBean
@ViewScoped
public class FormViewBean implements Serializable{
	
	private static final long serialVersionUID = 8953068464452926576L;
	
	@Autowired
	private FormElementService formElementService;
	private DynaFormModel model;
	private Form form;
	
	@PostConstruct
	public void load() {
		FacesContextUtils
     		.getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
     		.getAutowireCapableBeanFactory().autowireBean(this);
		
		String formId = FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRequestParameterMap()
				.get("formId");
		
		loadFieldProps(formId);
	}

	private void loadFieldProps(String formId) {
		List<FormElement> formElements = formElementService.findByFormId(Long.parseLong(formId));
		if (!formElements.isEmpty()) {
			this.form = formElements.get(0).getForm();
			setModel(new DynaFormModel());
			formElements.forEach(fe -> {
				loadFieldProperty(fe);
			});
		}
	}
	
	private void loadFieldProperty(FormElement formElement) {
		Element element = formElement.getElement();
		DynaFormRow row = getModel().createRegularRow();
		row.addControl(new FieldProperty(formElement), element.getType());
	}
	
	public String getTemplate() {
		return this.form.getTemplate();
	}

	public DynaFormModel getModel() {
		return model;
	}

	public void setModel(DynaFormModel model) {
		this.model = model;
	}

}

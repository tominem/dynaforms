package com.strandum.dynaforms.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.jsf.FacesContextUtils;

import com.strandum.dynaforms.model.Element;
import com.strandum.dynaforms.model.ElementSelectValue;
import com.strandum.dynaforms.model.Form;
import com.strandum.dynaforms.model.FormElement;
import com.strandum.dynaforms.prop.FieldProperty;
import com.strandum.dynaforms.service.FormElementService;
import com.strandum.dynaforms.ui.ComponentUtils;
import com.strandum.dynaforms.ui.Components;
import com.strandum.dynaforms.ui.UIProperty;

@ManagedBean(name="formBean")
@ViewScoped
public class FormBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String inputType;

	private DynaFormModel model;
	
	private FieldProperty selectedField;

	private UIComponent oldClickedComponent;
	private UIComponent clickedComponent;
	
	private AtomicInteger counter = new AtomicInteger(-1);
	
	private Form form;
	
	private List<FormElement> formElements = new ArrayList<>();
	
	private boolean clickedControlHasChanged;

	private List<UIProperty> uiProperties;
	
	private Object selectedOptionValue;

	@Autowired
	private FormElementService formElementService;

	@PostConstruct
	public void initialize() {
		FacesContextUtils
 			.getRequiredWebApplicationContext(FacesContext.getCurrentInstance())
 			.getAutowireCapableBeanFactory().autowireBean(this);
		
		this.setModel(new DynaFormModel());
		this.form = new Form();
	}
	
	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public DynaFormModel getModel() {
		return model;
	}

	public void setModel(DynaFormModel model) {
		this.model = model;
	}
	
	/**
	 * add Template to the form
	 */
	public void addTemplate() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String template = params.get("template-type");
		setTemplate(template);
	}
	
	/**
	 * add Field to the form
	 */
	public void addField() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String compType = params.get("comp-type");
		
		int index = counter.incrementAndGet();
		Element element = new Element(compType);
		element.setIndex(index);
		element.setLabel(compType + index);
		
		FormElement formElement = new FormElement(form, element);
		
		if (compType.equals(Components.SINGLE_SELECT)) {
			IntStream.range(0, 4).forEach(i -> {
				addOptionValuePerform(formElement, "Option" + (i+1), i);
			});
		}
		
		formElements.add(formElement);
		
		DynaFormRow row = model.createRegularRow();
		row.addControl(new FieldProperty(formElement), compType);
	}

	@SuppressWarnings("serial")
	private void addOptionValuePerform(FormElement formElement, String label, int index) {
		Element element = formElement.getElement();
		element.getValues().add(new ElementSelectValue(formElement, label, index) {
			@Override
			public boolean equals(Object obj) {
				ElementSelectValue other = (ElementSelectValue) obj;
				if (other.getValue().equals(getValue())) {
					return true;
				}
				return false;
			}
		});
	}
	
	private void reloadElementIndexes() {
		IntStream.range(0, formElements.size())
			.forEach(i -> formElements.get(i).getElement().setIndex(i));
	}
	
	public void removeField() {
		Element elementSelected = getSelectedField().getElement();
		Integer indexToRemove = elementSelected.getIndex();
		model.getRegularRows().remove(indexToRemove.intValue());
		formElements.removeIf(fe -> fe.getElement().equals(elementSelected));
		reloadElementIndexes();
		counter.set(formElements.size()-1);
	}

	public String save() {
		formElementService.saveAll(formElements);
		
		return "formview.xhtml?faces-redirect=true&" + "formId=" + form.getId();
	}

	public FieldProperty getSelectedField() {
		return selectedField;
	}

	public void setSelectedField(FieldProperty selectedField) {
		this.selectedField = selectedField;
	}
	
	public String getTemplate() {
		return form.getTemplate();
	}

	public void setTemplate(String template) {
		this.form.setTemplate(template);
	}
	
	public List<UIProperty> getUiProperties(){
		if (clickedControlHasChanged && selectedField != null) {
			Element element = selectedField.getElement();
			
			return this.uiProperties = ComponentUtils.getUIProps(element, element.getType());
		}
		
		return this.uiProperties;
	}
	
	public void dynaControlOnClick(AjaxBehaviorEvent event) {
		Map<String, Object> attributes = event.getComponent().getAttributes();
		FieldProperty fieldSelected = (FieldProperty) attributes.get("dataSelected");
		setSelectedField(fieldSelected);
		
		this.clickedComponent = event.getComponent();
		this.clickedControlHasChanged = this.clickedComponent != this.oldClickedComponent;

		if(this.clickedControlHasChanged) {
			this.clickedControlHasChanged = true;
			this.oldClickedComponent = this.clickedComponent;
		} else {
			this.clickedControlHasChanged = false;
		}
	}
	
	public void addOptionValue() {
		FormElement formElement = getSelectedField().getFormElement();
		Element selectedElement = formElement.getElement();
		List<ElementSelectValue> values = selectedElement.getValues();
		addOptionValuePerform(formElement, "New Option" + values.size(), values.size()-1);
	}
	
	public void removeOptionValue() {
		FormElement formElement = getSelectedField().getFormElement();
		Element element = formElement.getElement();
		element.getValues().remove(getSelectedOptionValue());
	}
	
	public void selectOptionValue(AjaxBehaviorEvent event) {
		Map<String, Object> attributes = event.getComponent().getAttributes();
		setSelectedOptionValue(attributes.get("selectedOptionValue"));
	}

	public Object getSelectedOptionValue() {
		return selectedOptionValue;
	}

	public void setSelectedOptionValue(Object selectedOptionValue) {
		this.selectedOptionValue = selectedOptionValue;
	}
	
}

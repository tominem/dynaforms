//package com.strandum.dynaforms.bean;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
//import javax.faces.context.FacesContext;
//import javax.faces.model.SelectItem;
//
//import org.primefaces.PrimeFaces;
//import org.primefaces.extensions.model.dynaform.DynaFormControl;
//import org.primefaces.extensions.model.dynaform.DynaFormLabel;
//import org.primefaces.extensions.model.dynaform.DynaFormModel;
//import org.primefaces.extensions.model.dynaform.DynaFormRow;
//
//import com.strandum.dynaforms.prop.FieldProperty;
//
//@ManagedBean
//@ViewScoped
//public class DynaFormController implements Serializable {
//
//    private static final long serialVersionUID = 20120423L;
//
//    private DynaFormModel model;
//
//    private List<SelectItem> typeOfFields = new ArrayList<SelectItem>();
//    
//    private String typeOfField;
//
//    private static List<SelectItem> LANGUAGES = new ArrayList<SelectItem>();
//    
//    private int countFields = 0;
//
//    @PostConstruct
//    protected void initialize() {
//        model = new DynaFormModel();
//
//        // add rows, labels and editable controls
//        // set relationship between label and editable controls to support outputLabel with "for" attribute
//
//        // 1. row
//        DynaFormRow row = model.createRegularRow();
//
//        DynaFormLabel label11 = row.addLabel("Author");
//        DynaFormControl control12 = row.addControl(new FieldProperty("Author", "input", true, 0), "input");
//        label11.setForControl(control12);
//
//        DynaFormLabel label13 = row.addLabel("ISBN");
//        DynaFormControl control14 = row.addControl(new FieldProperty("ISBN", "input", false, 0), "input");
//        label13.setForControl(control14);
//        
//        DynaFormLabel label33 = row.addLabel("Published on");
//        DynaFormControl control34 = row.addControl(new FieldProperty("Published on", "calendar", false, 0), "calendar");
//        label33.setForControl(control34);
//
//        // 2. row
//        row = model.createRegularRow();
//
//        DynaFormLabel label21 = row.addLabel("Title");
//        DynaFormControl control22 = row.addControl(new FieldProperty("Title", "input", false, 0), "input", 3, 1);
//        label21.setForControl(control22);
//
//        // 3. row
//        row = model.createRegularRow();
//
//        DynaFormLabel label31 = row.addLabel("Publisher");
//        DynaFormControl control32 = row.addControl(new FieldProperty("Publisher", "input", false, 0), "input");
//        label31.setForControl(control32);
//
////        DynaFormLabel label33 = row.addLabel("Published on");
////        DynaFormControl control34 = row.addControl(new BookProperty("Published on", false), "calendar");
////        label33.setForControl(control34);
//
//        // 4. row
////        row = model.createRegularRow();
////
////        DynaFormLabel label41 = row.addLabel("Language");
////        DynaFormControl control42 = row.addControl(new BookProperty("Language", false), "select");
////        label41.setForControl(control42);
////
////        DynaFormLabel label43 = row.addLabel("Description", 1, 2);
////        DynaFormControl control44 = row.addControl(new BookProperty("Description", false), "textarea", 1, 2);
////        label43.setForControl(control44);
////
////        // 5. row
////        row = model.createRegularRow();
////
////        DynaFormLabel label51 = row.addLabel("Rating");
////        DynaFormControl control52 = row.addControl(new BookProperty("Rating", 3, true), "rating");
////        label51.setForControl(control52);
//    }
//
//    public DynaFormModel getModel() {
//        return model;
//    }
//
//    public List<FieldProperty> getBookProperties() {
//        if (model == null) {
//            return null;
//        }
//
//        List<FieldProperty> bookProperties = new ArrayList<FieldProperty>();
//        for (DynaFormControl dynaFormControl : model.getControls()) {
//            bookProperties.add((FieldProperty) dynaFormControl.getData());
//        }
//
//        return bookProperties;
//    }
//
//    public String submitForm() {
//        FacesMessage.Severity sev = FacesContext.getCurrentInstance().getMaximumSeverity();
//        boolean hasErrors = sev != null && FacesMessage.SEVERITY_ERROR.compareTo(sev) >= 0;
//
//        PrimeFaces.current().ajax().addCallbackParam("isValid", !hasErrors);
//
//        return null;
//    }
//    
//    public void addField() {
//    	int count = countFields++;
//    	DynaFormRow row = model.createRegularRow();
//    	String label = getTypeOfField() + " " + count;
//		DynaFormLabel labelComp = row.addLabel(label);
//        DynaFormControl formControl = row.addControl(new FieldProperty(label, getTypeOfField(), false, 0), getTypeOfField());
//        labelComp.setForControl(formControl);
//    }
//
//    public List<SelectItem> getLanguages() {
//        if (LANGUAGES.isEmpty()) {
//            LANGUAGES.add(new SelectItem("en", "English"));
//            LANGUAGES.add(new SelectItem("de", "German"));
//            LANGUAGES.add(new SelectItem("ru", "Russian"));
//            LANGUAGES.add(new SelectItem("tr", "Turkish"));
//        }
//
//        return LANGUAGES;
//    }
//
//	public List<SelectItem> getTypeOfFields() {
//		if (typeOfFields.isEmpty()) {
//			typeOfFields.add(new SelectItem("input"));
//			typeOfFields.add(new SelectItem("calendar"));
//		}
//		
//		return typeOfFields;
//	}
//
//	public String getTypeOfField() {
//		return typeOfField;
//	}
//
//	public void setTypeOfField(String typeOfField) {
//		this.typeOfField = typeOfField;
//	}
//
//}
//            
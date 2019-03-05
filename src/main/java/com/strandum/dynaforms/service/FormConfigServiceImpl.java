package com.strandum.dynaforms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strandum.dynaforms.model.FormConfig;
import com.strandum.dynaforms.model.FormField;
import com.strandum.dynaforms.model.FormLargeObject;
import com.strandum.dynaforms.model.IFormField;
import com.strandum.dynaforms.repository.FormConfigRepository;

@Service
public class FormConfigServiceImpl implements FormConfigService{

	@Autowired
	private FormConfigRepository      formConfigRepository;
	
//	@Autowired
//	private FormFieldRepository       formFieldRepository;

//	@Autowired
//	private FormLargeObjectRepository formLargeObjectRepository;
	
	@Override
	public FormConfig findFormConfigByToken(String token) {
		return formConfigRepository.findByToken(token);
	}

	@Override
	public List<IFormField> findFormFieldByFormConfig(Integer formConfigId) {
		return null;
	}

	@Override
	public List<IFormField> findFormLargeObjectByFormConfig(Integer formConfigId) {
//		return formLargeObjectRepository.findByFormConfigId(formConfigId);
		return null;
	}

	private void saveFormField(List<FormField> formFields) {
//		formFieldRepository.saveAll(formFields);
	}

	private void saveFormLargeObject(List<FormLargeObject> formLargeObjects) {
//		formLargeObjectRepository.saveAll(formLargeObjects);
	}

	@Override
	public void saveFormFields(List<IFormField> iFormFields) {
//		List<FormField> formFields = new ArrayList<>();
//		List<FormLargeObject> formLargeObjects = new ArrayList<>();
//		
//		iFormFields.stream().forEach(ff -> {
//			if (ff instanceof FormField) {
//				formFields.add((FormField) ff);
//			} else if(ff instanceof FormLargeObject) {
//				formLargeObjects.add((FormLargeObject) ff);
//			}
//		});
//		
//		saveFormField(formFields);
//		saveFormLargeObject(formLargeObjects);
//		
	}

}

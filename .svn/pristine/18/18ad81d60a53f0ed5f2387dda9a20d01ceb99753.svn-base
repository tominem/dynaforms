package com.strandum.dynaforms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strandum.dynaforms.model.FormElement;
import com.strandum.dynaforms.repository.FormElementRepository;

@Service(value="formElementService")
public class FormElementServiceImpl implements FormElementService{

	private FormElementRepository formElementRepository;

	@Autowired
	public FormElementServiceImpl(FormElementRepository formElementRepository) {
		this.formElementRepository = formElementRepository;
	}
	
	@Override
	public void saveAll(List<FormElement> formElements) {
		formElementRepository.saveAll(formElements);
	}

	@Override
	public List<FormElement> findByFormId(long formId) {
		return formElementRepository.findByFormId(formId);
	}

}

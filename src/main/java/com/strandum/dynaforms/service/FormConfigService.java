package com.strandum.dynaforms.service;

import java.util.List;

import com.strandum.dynaforms.model.FormConfig;
import com.strandum.dynaforms.model.IFormField;

public interface FormConfigService {

	FormConfig findFormConfigByToken(String token);

	List<IFormField> findFormFieldByFormConfig(Integer formConfigId);

	List<IFormField> findFormLargeObjectByFormConfig(Integer formConfigId);

	void saveFormFields(List<IFormField> iFormFields);

}

package com.strandum.dynaforms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strandum.dynaforms.model.FormConfig;
import com.strandum.dynaforms.repository.FormConfigRepository;

@Service
public class FormConfigServiceImpl implements FormConfigService{

	@Autowired
	private FormConfigRepository formConfigRepository;
	
	@Override
	public FormConfig findFormConfigByToken(String token) {
		return formConfigRepository.findByToken(token);
	}

}

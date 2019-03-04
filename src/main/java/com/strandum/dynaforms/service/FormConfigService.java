package com.strandum.dynaforms.service;

import com.strandum.dynaforms.model.FormConfig;

public interface FormConfigService {

	FormConfig findFormConfigByToken(String token);

}

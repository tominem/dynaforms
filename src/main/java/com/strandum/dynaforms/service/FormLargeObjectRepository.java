package com.strandum.dynaforms.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.strandum.dynaforms.model.FormLargeObject;
import com.strandum.dynaforms.model.IFormField;

public interface FormLargeObjectRepository extends CrudRepository<FormLargeObject, Integer> {

	List<IFormField> findByFormConfigId(Integer formConfigId);

}

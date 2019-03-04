package com.strandum.dynaforms.repository;

import org.springframework.data.repository.CrudRepository;

import com.strandum.dynaforms.model.FormLayout;

public interface FormLayoutRepository extends CrudRepository<FormLayout, Integer>{

	FormLayout findByFormName(String formName);

}

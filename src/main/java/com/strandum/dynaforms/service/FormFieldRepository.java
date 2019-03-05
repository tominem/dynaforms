package com.strandum.dynaforms.service;

import org.springframework.data.repository.CrudRepository;

import com.strandum.dynaforms.model.FormField;

public interface FormFieldRepository extends CrudRepository<FormField, Integer> {

}

package com.strandum.dynaforms.repository;

import org.springframework.data.repository.CrudRepository;

import com.strandum.dynaforms.model.FormConfig;

public interface FormConfigRepository extends CrudRepository<FormConfig, Integer> {

	FormConfig findByToken(String token);

}

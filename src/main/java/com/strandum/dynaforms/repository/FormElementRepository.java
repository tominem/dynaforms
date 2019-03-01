package com.strandum.dynaforms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.strandum.dynaforms.model.FormElement;

@Repository
public interface FormElementRepository extends CrudRepository<FormElement, Long>{

	List<FormElement> findByFormId(Long formId);
	
}

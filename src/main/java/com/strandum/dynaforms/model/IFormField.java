package com.strandum.dynaforms.model;

public interface IFormField {
	
	String getName();
	
	void setValue(Object value);
	
	Object getValue();
	
	String getType();

	void setType(String type);

	void setName(String name);

}

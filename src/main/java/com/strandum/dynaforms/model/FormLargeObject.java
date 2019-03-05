package com.strandum.dynaforms.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class FormLargeObject implements IFormField{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private FormConfig formConfig;
	
	private String name;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	private byte[] value;
	
	private String type;
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setValue(Object value) {
		this.value = (byte[]) value;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public void setType(String type) {
		this.type = type;
	}
	
}

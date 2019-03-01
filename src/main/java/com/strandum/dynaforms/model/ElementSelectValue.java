package com.strandum.dynaforms.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class ElementSelectValue implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private FormElement formElement;
	
	@Type(type="java.lang.String")
	private Object value;
	
	private Integer itemOrder;

	public ElementSelectValue() {}
	
	public ElementSelectValue(FormElement formElement, Object value, Integer itemOrder) {
		this.formElement = formElement;
		this.value = value;
		this.setItemOrder(itemOrder);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FormElement getFormElement() {
		return formElement;
	}

	public void setFormElement(FormElement formElement) {
		this.formElement = formElement;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	public Integer getItemOrder() {
		return itemOrder;
	}

	public void setItemOrder(Integer itemOrder) {
		this.itemOrder = itemOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementSelectValue other = (ElementSelectValue) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

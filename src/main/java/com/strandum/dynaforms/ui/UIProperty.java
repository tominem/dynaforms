package com.strandum.dynaforms.ui;

import java.io.Serializable;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class UIProperty implements Comparable<UIProperty>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8289495039744454314L;
	
	private String label;
	private String fieldName;
	private Class<?> fieldType;
	private String fieldTypeName;
	private Object value;
	private Integer order;
	private Object bindObject;

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public UIProperty(String label, String fieldName, Class<?> fieldType, Object value, Integer order) {
		this.label = label;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.value = ConvertUtils.convert(value, getFieldType());
		this.fieldTypeName = extractSimpleTypeClassName(fieldType);
		this.order = order;
	}
	
	private String extractSimpleTypeClassName(Class<?> clazz) {
		String typeName = clazz.getTypeName();
		typeName = typeName.substring(typeName.lastIndexOf(".")+1);
		return typeName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		if (getBindObject() != null) {
			try {
				this.value = ConvertUtils.convert(value, getFieldType());
				PropertyUtils.setProperty(getBindObject(), getFieldName(), this.value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		this.value = value;
	}
	
	public Object getBindObject() {
		return bindObject;
	}

	public void bind(Object bindObject) {
		this.bindObject = bindObject;
	}

	@Override
	public int compareTo(UIProperty o) {
		return this.order.compareTo(o.getOrder());
	}

	public Class<?> getFieldType() {
		return fieldType;
	}

	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}

	@Override
	public String toString() {
		return "UIProperty [label=" + label + ", fieldName=" + fieldName + ", fieldType=" + fieldType + ", value="
				+ value + ", order=" + order + ", bindObject=" + bindObject + "]";
	}

	public String getFieldTypeName() {
		return fieldTypeName;
	}

	public void setFieldTypeName(String fieldTypeName) {
		this.fieldTypeName = fieldTypeName;
	}
	
}

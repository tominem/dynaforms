package com.strandum.dynaforms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.strandum.dynaforms.ui.ComponentType;
import com.strandum.dynaforms.ui.UIComponent;
import com.strandum.dynaforms.ui.annotation.ComponentProp;

@Entity
public class Element implements Serializable, UIComponent {

	private static final long serialVersionUID = 3167711374008234032L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ComponentProp(label="Name", order=1)
	private String name;

	@ComponentProp(label="Hidden", order=3)
	private Boolean hidden;

	@ComponentProp(label="Class", order=6)
	private String styleClass;

	@ComponentProp(label="Style", order=7)
	private String style;

	@ComponentProp(type=ComponentType.INPUT, label="Required", order=4)
	private Boolean required;

	@ComponentProp(type=ComponentType.INPUT, label="Label", order=2)
	private String label;

	@ComponentProp(type=ComponentType.INPUT, label="Read Only", order=5)
	private Boolean readOnly;

	@ComponentProp(type=ComponentType.INPUT, label="Place Holder", order=8)
	private String placeHolder;
	
	@Transient
	@ComponentProp(type=ComponentType.SINGLE_SELECT, label="Options", order=9)
	private List<ElementSelectValue> values = new ArrayList<>();

	private String type;
	
	private Integer index;

	public Element() {
	}
	
	public Element(String type) {
		this.type = type;
	}

	public Element(String name, String type, Boolean required) {
		this.name = name;
		this.type = type;
		this.required = required;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean isHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getPlaceHolder() {
		return placeHolder;
	}

	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	
	@Transient
	public List<ElementSelectValue> getValues() {
		return values;
	}

	@Transient
	public void setValues(List<ElementSelectValue> values) {
		this.values = values;
	}

}

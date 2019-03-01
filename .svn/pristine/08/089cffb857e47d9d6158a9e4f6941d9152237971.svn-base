package com.strandum.dynaforms.ui;

import java.util.Arrays;

import com.strandum.dynaforms.ui.component.Component;
import com.strandum.dynaforms.ui.component.Input;
import com.strandum.dynaforms.ui.component.SingleSelect;

public enum ComponentType {
	
	COMPONENT(Components.COMPONENT, new Component()),
	INPUT(Components.INPUT, new Input()),
	SINGLE_SELECT(Components.SINGLE_SELECT, new SingleSelect());
	
	private String type;
	private Component component;

	private ComponentType(String type, Component component) {
		this.type = type;
		this.component = component;
	}
	
	public Component getComponent() {
		return component;
	}
	
	public static ComponentType getByType(String type) {
		return Arrays.stream(values())
				.filter(ct -> ct.type.equals(type))
				.findFirst()
				.orElse(null);
	}
	
	@Override
	public String toString() {
		return type;
	}
	
}

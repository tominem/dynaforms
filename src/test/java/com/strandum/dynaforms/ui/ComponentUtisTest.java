package com.strandum.dynaforms.ui;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.strandum.dynaforms.model.Element;

public class ComponentUtisTest {
	
	@Test
	public void input_props_test() {
		
		Element element = new Element();
		element.setType(Components.INPUT);
		
		List<UIProperty> uiProps = ComponentUtils.getUIProps(element, element.getType());
		
		assertEquals(uiProps.size(), 8);
		
		//testing order
		assertEquals(uiProps.get(0).getLabel(), "Name");
		assertEquals(uiProps.get(1).getLabel(), "Label");
	}
	
	@Test
	public void component_props_test() {
		
		Element element = new Element();
		element.setType(Components.COMPONENT);
		
		List<UIProperty> uiProps = ComponentUtils.getUIProps(element, element.getType());
		
		assertEquals(uiProps.size(), 4);
		
		//testing order
		assertEquals(uiProps.get(0).getLabel(), "Name");
		assertEquals(uiProps.get(1).getLabel(), "Hidden");
	}
	
}

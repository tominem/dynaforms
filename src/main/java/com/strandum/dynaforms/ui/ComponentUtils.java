package com.strandum.dynaforms.ui;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.strandum.dynaforms.ui.annotation.ComponentProp;
import com.strandum.dynaforms.ui.component.Component;

public class ComponentUtils implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static List<UIProperty> getUIProps(Object object, String type){
		final List<UIProperty> props = new ArrayList<>();
		ComponentType componentType = ComponentType.getByType(type);
		loadPropsByComponentTypeAndAnnotation(object, componentType, props);
		
		if (props.isEmpty()) 
			throw new IllegalArgumentException("object of type " + object.getClass() + " must contains at least one field has been annotated by @" + ComponentProp.class.getSimpleName());
		
		Collections.sort(props);
		
		return props;
	}

	private static void loadPropsByComponentTypeAndAnnotation(Object object, ComponentType componentType, final List<UIProperty> props) {
		FieldUtils.getFieldsListWithAnnotation(object.getClass(), ComponentProp.class)
			.forEach(field -> {

				try {
					
					field.setAccessible(true);
					
					if(checkIfBelongsToComponentHierarchy(componentType, field)) {
						
						String fieldName = field.getName();
						Object value = field.get(object);
						Class<?> classType = field.getType();
						ComponentProp annotation = field.getAnnotation(ComponentProp.class);
						String label = annotation.label();
						int order = annotation.order();
						
						UIProperty property = new UIProperty(label, fieldName, classType, value, order);
						property.bind(object);
						
						props.add(property);
					}
					
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				
			});
	}

	private static boolean checkIfBelongsToComponentHierarchy(ComponentType componentType, Field field) {
		Component component = componentType.getComponent();
		ComponentProp annotation = field.getAnnotation(ComponentProp.class);
		Component annotatedComponent = annotation.type().getComponent();
		
		if (component.equals(annotatedComponent) == false) {
			
			Component parent = component.getParent();
			while (parent != null) {

				if (parent.equals(annotatedComponent)) {
					return true;
				}

				parent = parent.getParent();
			}
			
			return false;
		}
		
		return true;
	}

}

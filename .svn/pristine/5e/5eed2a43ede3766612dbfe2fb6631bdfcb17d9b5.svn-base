package com.strandum.dynaforms.ui.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.strandum.dynaforms.ui.ComponentType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ComponentProp {
	
	public ComponentType type() default ComponentType.COMPONENT;
	
	public String label();
	
	public int order();
	
}

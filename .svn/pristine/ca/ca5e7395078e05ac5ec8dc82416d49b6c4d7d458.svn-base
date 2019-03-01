/*
 * Copyright 2011-2015 PrimeFaces Extensions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * $Id$
 */

package com.strandum.dynaforms.prop;

import java.io.Serializable;

import com.strandum.dynaforms.model.Element;
import com.strandum.dynaforms.model.FormElement;

public class FieldProperty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4337925660627987453L;
	
	private FormElement formElement;
	private Object value;

	public FieldProperty() {
		// TODO Auto-generated constructor stub
	}
	
	public FieldProperty(FormElement formElement) {
		this.setFormElement(formElement);
	}
	
	public Element getElement() {
		return getFormElement().getElement();
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public FormElement getFormElement() {
		return formElement;
	}

	public void setFormElement(FormElement formElement) {
		this.formElement = formElement;
	}

}
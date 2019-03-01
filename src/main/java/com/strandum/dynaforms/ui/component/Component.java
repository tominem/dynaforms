package com.strandum.dynaforms.ui.component;

import com.strandum.dynaforms.ui.Components;

public class Component {

	protected String type;
	
	protected Component parent;

	public Component() {
		this(Components.COMPONENT, null);
	}

	protected Component(String type, Component parent) {
		this.type   = type;
		this.parent = parent;
	}

	public final String getType() {
		return type;
	}

	public final void setType(String name) {
		this.type = name;
	}

	public final Component getParent() {
		return parent;
	}

	public final void setParent(Component parent) {
		this.parent = parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Component other = (Component) obj;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
}

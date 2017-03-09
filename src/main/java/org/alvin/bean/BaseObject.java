package org.alvin.bean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import org.alvin.util.ObjectUtil;

public abstract class BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String id;
	protected String name;
	protected PropertyChangeSupport support;

	public BaseObject() {
		// 实例化支持类
		support = new PropertyChangeSupport(this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BaseObject cloneObject() throws Exception {
		return (BaseObject) ObjectUtil.cloneObject(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
		support.addPropertyChangeListener(listener);
	}

	public void firePropertyChange(String name, Object oldValue, Object newValue) {
		support.firePropertyChange(name, oldValue, newValue);
	}

}

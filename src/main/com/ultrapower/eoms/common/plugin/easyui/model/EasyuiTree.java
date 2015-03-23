package com.ultrapower.eoms.common.plugin.easyui.model;

import java.util.List;

public class EasyuiTree {
	private String id;
	private String text;
	private List<EasyuiTree> children;
	private String state;//state: The node state, 'open' or 'closed'
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<EasyuiTree> getChildren() {
		return children;
	}
	public void setChildren(List<EasyuiTree> children) {
		this.children = children;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}

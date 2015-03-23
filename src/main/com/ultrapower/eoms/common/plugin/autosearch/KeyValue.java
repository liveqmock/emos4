package com.ultrapower.eoms.common.plugin.autosearch;

public class KeyValue {
	private String key;// id值,比如员工工号
	private String value;// name值,比如员工姓名

	public KeyValue() {
		// TODO Auto-generated constructor stub
	}

	public KeyValue(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

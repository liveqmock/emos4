package com.ultrapower.eoms.ftrmaintain.model;

public class SysParam {
	private String key;
	private String value;
	private String desc;
	
	public SysParam(String key, String value, String desc) {
		super();
		this.key = key;
		this.value = value;
		this.desc = desc;
	}
	
	public SysParam() {
		super();
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}

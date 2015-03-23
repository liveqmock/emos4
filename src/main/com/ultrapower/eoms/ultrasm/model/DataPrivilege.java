package com.ultrapower.eoms.ultrasm.model;

import com.ultrapower.eoms.common.core.util.StringUtils;

public class DataPrivilege {
	private ResProperty rp;
	private String operator;
	private String data;
	
	public ResProperty getRp() {
		return rp;
	}
	public void setRp(ResProperty rp) {
		this.rp = rp;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}

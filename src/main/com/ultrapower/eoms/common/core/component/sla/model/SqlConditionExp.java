package com.ultrapower.eoms.common.core.component.sla.model;
/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-13 下午03:04:32
 */
public class SqlConditionExp {

	private String fieldid;
	private String inputvaluetype;
	private String operator;
	private String value;
	public String getFieldid() {
		return fieldid;
	}
	public void setFieldid(String fieldid) {
		this.fieldid = fieldid;
	}
	public String getInputvaluetype() {
		return inputvaluetype;
	}
	public void setInputvaluetype(String inputvaluetype) {
		this.inputvaluetype = inputvaluetype;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}

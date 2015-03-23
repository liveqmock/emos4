package com.ultrapower.eoms.workflow.flowmap.model;
/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public class BaseFieldBean
{
	/**
	 * 工单字段名称
	 */
	private String m_BaseFieldDBName;
	/**
	 * 工单字段对应值
	 */
	private String m_BaseFieldValue;
	/**
	 * 工单字段类型
	 */
	private int m_BaseFieldType;
	
	private String fieldLabel;
	
	private int colSpan;
	
	public String getM_BaseFieldDBName() {
		return m_BaseFieldDBName;
	}
	public void setM_BaseFieldDBName(String baseFieldDBName) {
		m_BaseFieldDBName = baseFieldDBName;
	}
	public String getM_BaseFieldValue() {
		return m_BaseFieldValue;
	}
	public void setM_BaseFieldValue(String baseFieldValue) {
		m_BaseFieldValue = baseFieldValue;
	}
	public int getM_BaseFieldType() {
		return m_BaseFieldType;
	}
	public void setM_BaseFieldType(int baseFieldType) {
		m_BaseFieldType = baseFieldType;
	}
	public String getFieldLabel() {
		return fieldLabel;
	}
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	public int getColSpan() {
		return colSpan;
	}
	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}

	

}

package com.ultrapower.eoms.workflow.flowmap.model;

import java.util.Map;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-8-19
 */
public class ModifyLogModel {
	private String baseID;
	private String baseSchema;
	private String phaseNo;
	private String fieldID;
	private String date;
	private String fieldDBName;
	private String dealerID;
	private String dealer;
	private String oldValue;
	private String newValue;
	private String processType;
	private String baseFieldShowName;
	private String processID;
	private String processLogID;
	private String baseFieldType;
	private String baseFieldTypeValue;

	private Map<String,BaseFieldBean> m_FieldHashtable;
	
	public Map<String, BaseFieldBean> getM_FieldHashtable() {
		return m_FieldHashtable;
	}
	public void setM_FieldHashtable(Map<String, BaseFieldBean> fieldHashtable) {
		m_FieldHashtable = fieldHashtable;
	}
	public String getBaseID() {
		return baseID;
	}
	public void setBaseID(String baseID) {
		this.baseID = baseID;
	}
	public String getBaseSchema() {
		return baseSchema;
	}
	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}
	public String getPhaseNo() {
		return phaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}
	public String getFieldID() {
		return fieldID;
	}
	public void setFieldID(String fieldID) {
		this.fieldID = fieldID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFieldDBName() {
		return fieldDBName;
	}
	public void setFieldDBName(String fieldDBName) {
		this.fieldDBName = fieldDBName;
	}
	public String getDealerID() {
		return dealerID;
	}
	public void setDealerID(String dealerID) {
		this.dealerID = dealerID;
	}
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getBaseFieldShowName() {
		return baseFieldShowName;
	}
	public void setBaseFieldShowName(String baseFieldShowName) {
		this.baseFieldShowName = baseFieldShowName;
	}
	public String getProcessID() {
		return processID;
	}
	public void setProcessID(String processID) {
		this.processID = processID;
	}
	public String getProcessLogID() {
		return processLogID;
	}
	public void setProcessLogID(String processLogID) {
		this.processLogID = processLogID;
	}
	public String getBaseFieldType() {
		return baseFieldType;
	}
	public void setBaseFieldType(String baseFieldType) {
		this.baseFieldType = baseFieldType;
	}
	public String getBaseFieldTypeValue() {
		return baseFieldTypeValue;
	}
	public void setBaseFieldTypeValue(String baseFieldTypeValue) {
		this.baseFieldTypeValue = baseFieldTypeValue;
	}
	
}

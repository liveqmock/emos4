package com.ultrapower.eoms.workflow.flowmap.model;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */ 
public class ModifyFieldView {
private String name;
private String dbName;
private String value;
private String printOneLine;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
public String getPrintOneLine() {
	return printOneLine;
}
public void setPrintOneLine(String printOneLine) {
	this.printOneLine = printOneLine;
}
public String getDbName() {
	return dbName;
}
public void setDbName(String dbName) {
	this.dbName = dbName;
}

}

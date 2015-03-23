package com.ultrapower.eoms.workflow.design.model;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public class LinkModel {
private String lId;
private String lType;
private String lNum;
private String lContent;
private String lBprocess;
private String lBpoint;
private String lEprocess;
private String lEpoint;
public String getLId() {
	return lId;
}
public void setLId(String id) {
	lId = id;
}
public String getLType() {
	return lType;
}
public void setLType(String type) {
	lType = type;
}
public String getLNum() {
	return lNum;
}
public void setLNum(String num) {
	lNum = num;
}
public String getLContent() {
	return lContent;
}
public void setLContent(String content) {
	lContent = content;
}
public String getLBprocess() {
	return lBprocess;
}
public void setLBprocess(String bprocess) {
	lBprocess = bprocess;
}
public String getLBpoint() {
	return lBpoint;
}
public void setLBpoint(String bpoint) {
	lBpoint = bpoint;
}
public String getLEprocess() {
	return lEprocess;
}
public void setLEprocess(String eprocess) {
	lEprocess = eprocess;
}
public String getLEpoint() {
	return lEpoint;
}
public void setLEpoint(String epoint) {
	lEpoint = epoint;
}

}

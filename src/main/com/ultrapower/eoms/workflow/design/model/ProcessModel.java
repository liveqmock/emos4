package com.ultrapower.eoms.workflow.design.model;

import java.util.List;
import java.util.Map;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */ 
public class ProcessModel {
private String pId;
private String pType;
private String pTitle;
private String pContent;
private String pX;
private String pY;
private String pStatusId;
private String pStatusName;
private String pImageUrl;
private String pModelkey;
private Map pDataMap;

private List<ProcessModel> nextProcessModelList;//用于visio导入，保存连线的下一个模型

public List<ProcessModel> getNextProcessModelList() {
	return nextProcessModelList;
}
public void setNextProcessModelList(List<ProcessModel> nextProcessModelList) {
	this.nextProcessModelList = nextProcessModelList;
}
public String getPId() {
	return pId;
}
public void setPId(String id) {
	pId = id;
}
public String getPType() {
	return pType;
}
public void setPType(String type) {
	pType = type;
}
public String getPTitle() {
	return pTitle;
}
public void setPTitle(String title) {
	pTitle = title;
}
public String getPContent() {
	return pContent;
}
public void setPContent(String content) {
	pContent = content;
}
public String getPX() {
	return pX;
}
public void setPX(String px) {
	pX = px;
}
public String getPY() {
	return pY;
}
public void setPY(String py) {
	pY = py;
}
public String getPStatusId() {
	return pStatusId;
}
public void setPStatusId(String statusId) {
	pStatusId = statusId;
}
public String getPStatusName() {
	return pStatusName;
}
public void setPStatusName(String statusName) {
	pStatusName = statusName;
}
public String getPImageUrl() {
	return pImageUrl;
}
public void setPImageUrl(String imageUrl) {
	pImageUrl = imageUrl;
}
public String getPModelkey() {
	return pModelkey;
}
public void setPModelkey(String modelkey) {
	pModelkey = modelkey;
}
public Map getPDataMap() {
	return pDataMap;
}
public void setPDataMap(Map dataMap) {
	pDataMap = dataMap;
}


}

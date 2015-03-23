package com.ultrapower.eoms.workflow.design.model;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public class DealProcessLog implements java.io.Serializable
{

	private String processid;
	private String logUser;
	private String actionName;
	private String  logTime;
	private String result;

	// Constructors

	/** full constructor */
	public DealProcessLog(String actionName,String logUser,String stDate,String result)
	{
		this.logUser = logUser;
		this.actionName = actionName;
		this.logTime = stDate;
		this.result = result;
	}

	public String getLogUser() {
		return logUser;
	}

	public void setLogUser(String logUser) {
		this.logUser = logUser;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	

}
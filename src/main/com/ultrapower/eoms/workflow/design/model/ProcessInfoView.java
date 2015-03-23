package com.ultrapower.eoms.workflow.design.model;

import java.util.List;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public class ProcessInfoView {
	/**
	 * 环节状态
	 */
	private String processinfoStatus;
	/**
	 * 环节描述
	 */
	private String processinfoDesc;
	/**
	 * 环节处理人
	 */
	private String processinfoDealer;
	/**
	 *上一环节处理人
	 */
	private String processinfoPreDealer;
	/**
	 * 环节开始时间
	 */
	private String processinfoStDate;
	/**
	 * 环节处理时间
	 */
	private String processinfoDealDate;
	/**
	 * 环节结束时间
	 */
	private String processinfoFinishDate;
	/**
	 * 环节log列表
	 */
	
    private List processlogList;
    
    /**
     * 环节用户输入信息
     * 
     */
    private List processfields;
    /**
     * flowid
     * @return
     */
    private String flowid;
    private String[] subProcessLog;
    
	public List getProcessfields() {
		return processfields;
	}

	public void setProcessfields(List processfields) {
		this.processfields = processfields;
	}

	public List getProcesslogList() {
		return processlogList;
	}

	public void setProcesslogList(List processlogList) {
		this.processlogList = processlogList;
	}

	public String getProcessinfoStatus() {
		return processinfoStatus;
	}
	public void setProcessinfoStatus(String processinfoStatus) {
		this.processinfoStatus = processinfoStatus;
	}
	public String getProcessinfoDesc() {
		return processinfoDesc;
	}
	public void setProcessinfoDesc(String processinfoDesc) {
		this.processinfoDesc = processinfoDesc;
	}
	public String getProcessinfoDealer() {
		return processinfoDealer;
	}
	public void setProcessinfoDealer(String processinfoDealer) {
		this.processinfoDealer = processinfoDealer;
	}
	public String getProcessinfoPreDealer() {
		return processinfoPreDealer;
	}
	public void setProcessinfoPreDealer(String processinfoPreDealer) {
		this.processinfoPreDealer = processinfoPreDealer;
	}
	public String getProcessinfoStDate() {
		return processinfoStDate;
	}
	public void setProcessinfoStDate(String processinfoStDate) {
		this.processinfoStDate = processinfoStDate;
	}
	public String getProcessinfoDealDate() {
		return processinfoDealDate;
	}
	public void setProcessinfoDealDate(String processinfoDealDate) {
		this.processinfoDealDate = processinfoDealDate;
	}
	public String getProcessinfoFinishDate() {
		return processinfoFinishDate;
	}
	public void setProcessinfoFinishDate(String processinfoFinishDate) {
		this.processinfoFinishDate = processinfoFinishDate;
	}

	public String getFlowid()
	{
		return flowid;
	}

	public void setFlowid(String flowid)
	{
		this.flowid = flowid;
	}

	public String[] getSubProcessLog()
	{
		return subProcessLog;
	}

	public void setSubProcessLog(String[] subProcessLog)
	{
		this.subProcessLog = subProcessLog;
	}
	
	
}

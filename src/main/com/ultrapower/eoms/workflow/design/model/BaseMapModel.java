package com.ultrapower.eoms.workflow.design.model;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public class BaseMapModel {
	
	
	private String id;
	/**
	 * 类型标识
	 */
	private String basecode;
	/**
	 * 类型名称
	 */
	private String basename;
	/**
	 * 流程编号
	 */
	private String code;
	/**
	 * 流程名称
	 */
	private String name;
	/**
	 * 活动标识
	 */
	private String active;
	/**
	 * 启用时间
	 */
	private String startdate;
	/**
	 * 创建时间
	 */
	private String createdate;
	/**
	 * 描述
	 */
	private String desc;
	
	/**
	 * 子流程
	 * @return
	 */
	private String subflow;
	
	public String getSubflow() {
		return subflow;
	}
	public void setSubflow(String subflow) {
		this.subflow = subflow;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBasecode() {
		return basecode;
	}
	public void setBasecode(String basecode) {
		this.basecode = basecode;
	}
	public String getBasename() {
		return basename;
	}
	public void setBasename(String basename) {
		this.basename = basename;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
}

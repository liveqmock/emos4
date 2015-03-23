package com.ultrapower.eoms.ultrasla.model;

import java.util.HashMap;

public class EventDealParam {
	private String id; // 主键id
	private String eventid; // 事件id
	private String ruleids; // 规则id
	private long duetime; // 处理时限
	private String defaultUser; // 默认通知用户
	private String defaultGroup; // 默认通知组
	private String defaultRole; // 默认通知角色
	private HashMap<String, String> paramMap; // 事件参数集合
	private String handletype; // 类型 产生：NEW、销毁：DESTROY
	private String logRecord; // 更新日志
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEventid() {
		return eventid;
	}
	public void setEventid(String eventid) {
		this.eventid = eventid;
	}
	public String getRuleids() {
		return ruleids;
	}
	public void setRuleids(String ruleids) {
		this.ruleids = ruleids;
	}
	public long getDuetime() {
		return duetime;
	}
	public void setDuetime(long duetime) {
		this.duetime = duetime;
	}
	public String getDefaultUser() {
		return defaultUser;
	}
	public void setDefaultUser(String defaultUser) {
		this.defaultUser = defaultUser;
	}
	public String getDefaultGroup() {
		return defaultGroup;
	}
	public void setDefaultGroup(String defaultGroup) {
		this.defaultGroup = defaultGroup;
	}
	public String getDefaultRole() {
		return defaultRole;
	}
	public void setDefaultRole(String defaultRole) {
		this.defaultRole = defaultRole;
	}
	public HashMap<String, String> getParamMap() {
		return paramMap;
	}
	public void setParamMap(HashMap<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	public String getHandletype() {
		return handletype;
	}
	public void setHandletype(String handletype) {
		this.handletype = handletype;
	}
	public String getLogRecord() {
		return logRecord;
	}
	public void setLogRecord(String logRecord) {
		this.logRecord = logRecord;
	}
}

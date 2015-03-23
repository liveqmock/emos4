package com.ultrapower.eoms.ultrasla.util;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ultrasla.mq.ASLAMessage;
import com.ultrapower.eoms.ultrasla.util.ConstantMark.EventType;

/**
 * @author RenChenglin
 * @date 2011-10-31 下午03:17:56
 * @version
 */
public class UltraSLAEvent extends ASLAMessage {

	private EventType eventType;//事件类型 1：产生 0：销毁
	private String eventId;
	private List<String> ruleIdLst;
	private Long duetime;
	private List<String> defaultUser;
	private List<String> defaultGroup;
	private List<String> defaultRole;
	private Map params;

	public UltraSLAEvent() {
		super();
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public List<String> getRuleIdLst() {
		return ruleIdLst;
	}

	public void setRuleIdLst(List<String> ruleIdLst) {
		this.ruleIdLst = ruleIdLst;
	}

	public Long getDuetime() {
		return duetime;
	}

	public void setDuetime(Long duetime) {
		this.duetime = duetime;
	}

	public List<String> getDefaultUser() {
		return defaultUser;
	}

	public void setDefaultUser(List<String> defaultUser) {
		this.defaultUser = defaultUser;
	}

	public List<String> getDefaultGroup() {
		return defaultGroup;
	}

	public void setDefaultGroup(List<String> defaultGroup) {
		this.defaultGroup = defaultGroup;
	}

	public List<String> getDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(List<String> defaultRole) {
		this.defaultRole = defaultRole;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "事件ID：" + this.eventId;
	}

}

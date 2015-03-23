package com.ultrapower.eoms.ultrasla.util;
/**
 * @author RenChenglin
 * @date 2011-11-23 上午11:12:48
 * @version
 */
public class TrigItem {
	public enum EventType{FORM,STEP,ACTION}; //定义事件类型枚举：工单事件，环节事件，动作事件
	public enum EventAction{IN,OUT,DO}; //定义事件动作枚举：流入，流出，执行
	
	private EventType eventType; //事件类型：工单事件/环节事件/动作事件
	private EventAction eventAction; //事件动作：流入/流出/执行
	private String eventCondition; //事件触发条件
	
	public TrigItem() {
		super();
	}
	
	public TrigItem(EventType eventType, EventAction eventAction,
			String eventCondition) {
		super();
		this.eventType = eventType;
		this.eventAction = eventAction;
		this.eventCondition = eventCondition;
	}
	
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	public EventAction getEventAction() {
		return eventAction;
	}
	public void setEventAction(EventAction eventAction) {
		this.eventAction = eventAction;
	}
	public String getEventCondition() {
		return eventCondition;
	}
	public void setEventCondition(String eventCondition) {
		this.eventCondition = eventCondition;
	}
}

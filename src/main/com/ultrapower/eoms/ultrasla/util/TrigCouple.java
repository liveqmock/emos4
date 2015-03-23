package com.ultrapower.eoms.ultrasla.util;
/**
 * @author RenChenglin
 * @date 2011-11-23 上午11:08:25
 * @version
 */
public class TrigCouple {
	public enum TimeOffset{BEFORE,AFTER}; //时间偏移类型枚举：提前,超时
	
	private String id; //触发规则ID
	private String name; //触发规则名称
	private TimeOffset timeOffset; //时间偏移类型：提前/超时
	private String duetimeField; //时限字段ID
	private TrigItem trigNew; //触发新建单元
	private TrigItem trigDestroy; //触发销毁单元
	
	public TrigCouple() {
		super();
	}
	
	public TrigCouple(String id, String name, TimeOffset timeOffset,
			String duetimeField, TrigItem trigNew, TrigItem trigDestroy) {
		super();
		this.id = id;
		this.name = name;
		this.timeOffset = timeOffset;
		this.duetimeField = duetimeField;
		this.trigNew = trigNew;
		this.trigDestroy = trigDestroy;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TimeOffset getTimeOffset() {
		return timeOffset;
	}
	public void setTimeOffset(TimeOffset timeOffset) {
		this.timeOffset = timeOffset;
	}
	public String getDuetimeField() {
		return duetimeField;
	}
	public void setDuetimeField(String duetimeField) {
		this.duetimeField = duetimeField;
	}
	public TrigItem getTrigNew() {
		return trigNew;
	}
	public void setTrigNew(TrigItem trigNew) {
		this.trigNew = trigNew;
	}
	public TrigItem getTrigDestroy() {
		return trigDestroy;
	}
	public void setTrigDestroy(TrigItem trigDestroy) {
		this.trigDestroy = trigDestroy;
	}
}

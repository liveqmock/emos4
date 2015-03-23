package com.ultrapower.eoms.extend.workcalendar.util;
/**
 * @author RenChenglin
 * @date 2011-12-15 下午02:01:44
 * @version
 */
public class TimeSpan {
	private String name;
	private String mark;
	private long startTime;
	private long endTime;
	
	public TimeSpan() {
		super();
	}
	
	public TimeSpan(String name, String mark, long startTime, long endTime) {
		super();
		this.name = name;
		this.mark = mark;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
}

package com.ultrapower.eoms.ultrasla.util;

public class EventStatusInfo {
	
	private String actionid; // 动作id
	private long actionstatus; // 事件动作状态
	private long actiontime; // 动作响应事件
	private long noticenum; // 通知次数
	
	public EventStatusInfo () {
		
	}
	
	public EventStatusInfo (String actionid, long actionstatus, long actiontime, long noticenum) {
		this.actionid = actionid;
		this.actionstatus = actionstatus;
		this.actiontime = actiontime;
		this.noticenum = noticenum;
	}

	public String getActionid() {
		return actionid;
	}

	public void setActionid(String actionid) {
		this.actionid = actionid;
	}

	public long getActionstatus() {
		return actionstatus;
	}

	public void setActionstatus(long actionstatus) {
		this.actionstatus = actionstatus;
	}

	public long getActiontime() {
		return actiontime;
	}

	public void setActiontime(long actiontime) {
		this.actiontime = actiontime;
	}

	public long getNoticenum() {
		return noticenum;
	}

	public void setNoticenum(long noticenum) {
		this.noticenum = noticenum;
	}
}

package com.ultrapower.eoms.ultrasla.mq.admin.util;
/**
 * @author RenChenglin
 * @date 2012-1-10 上午10:12:08
 * @version
 */
public class MqRuntimeInfo {
	private String brokerId;
	private String brokerName;
	private int status;
	private long remainMsgCount;
	private long rate; //处理频率，每秒
	
	public MqRuntimeInfo() {
		super();
	}

	public String getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getRemainMsgCount() {
		return remainMsgCount;
	}

	public void setRemainMsgCount(long remainMsgCount) {
		this.remainMsgCount = remainMsgCount;
	}

	public long getRate() {
		return rate;
	}

	public void setRate(long rate) {
		this.rate = rate;
	}
	
}

package com.ultrapower.eoms.ultrasla.util;
/**
 * @author RenChenglin
 * @date 2011-10-31 下午02:49:10
 * @version
 */
public class UltraSLAMessage {
	private String topic;
	private String content;
	private String remark;
	public UltraSLAMessage() {
		super();
	}
	public UltraSLAMessage(String topic, String content) {
		super();
		this.topic = topic;
		this.content = content;
	}
	public UltraSLAMessage(String topic, String content, String remark) {
		super();
		this.topic = topic;
		this.content = content;
		this.remark = remark;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}

package com.ultrapower.eoms.common.core.component.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 邮件信息对象
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-16 下午01:41:23
 */
public class EmailBean {

	// 邮件体参数
	private List<String> mailTo = new ArrayList<String>();// 收件人地址 (多个采用,隔开)
	private String subject;// 邮件主题
	private String msgContent;// 邮件内容
	private List<String> mailbccTo = new ArrayList<String>();//邮件密送(暗送) (多个采用,隔开)
	private List<String> mailccTo = new ArrayList<String>();//邮件抄送 (多个采用,隔开)
	private Vector attachedFileList;//附件
	private String messageContentMimeType = "text/html; charset=gbk";
	
	public List<String> getMailTo() {
		return mailTo;
	}
	public void setMailTo(List<String> mailTo) {
		this.mailTo = mailTo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public List<String> getMailbccTo() {
		return mailbccTo;
	}
	public void setMailbccTo(List<String> mailbccTo) {
		this.mailbccTo = mailbccTo;
	}
	public List<String> getMailccTo() {
		return mailccTo;
	}
	public void setMailccTo(List<String> mailccTo) {
		this.mailccTo = mailccTo;
	}
	public Vector getAttachedFileList() {
		return attachedFileList;
	}
	public void setAttachedFileList(Vector attachedFileList) {
		this.attachedFileList = attachedFileList;
	}
	public String getMessageContentMimeType() {
		return messageContentMimeType;
	}
	public void setMessageContentMimeType(String messageContentMimeType) {
		this.messageContentMimeType = messageContentMimeType;
	}
}

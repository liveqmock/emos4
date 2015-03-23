package com.ultrapower.eoms.ultrasla.util;
/**
 * @author RenChenglin
 * @date 2011-10-31 下午02:50:20
 * @version
 */
public class BaseDuetime {
	private Long acceptTime;
	private Long processTime;
	
	public BaseDuetime() {
		super();
	}
	public BaseDuetime(Long acceptTime, Long processTime) {
		super();
		this.acceptTime = acceptTime;
		this.processTime = processTime;
	}
	public Long getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(Long acceptTime) {
		this.acceptTime = acceptTime;
	}
	public Long getProcessTime() {
		return processTime;
	}
	public void setProcessTime(Long processTime) {
		this.processTime = processTime;
	}
	
	
}

package com.ultrapower.eoms.ultrasla.exception;
/**
 * @author RenChenglin
 * @date 2011-11-3 下午04:12:26
 * @version
 */
public class UltraSLAException extends Exception {
	private String msg;
	
	public UltraSLAException(){
		msg = "发生UltraSLA未知异常！";
	}
	
	public UltraSLAException(String msg){
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString(){
		return msg;
	}
}

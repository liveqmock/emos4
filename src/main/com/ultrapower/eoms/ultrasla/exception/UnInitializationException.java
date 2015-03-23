package com.ultrapower.eoms.ultrasla.exception;
/**
 * @author RenChenglin
 * @date 2011-11-3 下午04:16:23
 * @version
 */
public class UnInitializationException extends UltraSLAException {
	
	public UnInitializationException(){
		super("未初始化异常!");
	}
	
	public UnInitializationException(String msg){
		super(msg);
	}
	
	@Override
	public String toString(){
		return this.getMsg();
	}
}

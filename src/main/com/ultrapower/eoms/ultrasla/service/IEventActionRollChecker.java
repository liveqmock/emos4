package com.ultrapower.eoms.ultrasla.service;

/**
 * 事件动作轮询服务接口
 * @author SunHailong
 */
public interface IEventActionRollChecker {

	/**
	 * 轮询
	 */
	public void checkValueMatch();
}

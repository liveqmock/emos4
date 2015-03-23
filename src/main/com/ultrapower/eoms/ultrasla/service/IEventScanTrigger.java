package com.ultrapower.eoms.ultrasla.service;

/**
 * 事件扫描触发器
 * 产生事件时，通过此接口轮询扫描事件来出发事件规则的产生与销毁
 * @author SunHailong
 */
public interface IEventScanTrigger {
	/**
	 * 事件扫描处理
	 * @param sqlName
	 */
	public void eventScanDeal(String sqlName);
}

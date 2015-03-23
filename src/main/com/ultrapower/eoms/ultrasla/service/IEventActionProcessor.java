package com.ultrapower.eoms.ultrasla.service;

import java.util.List;

import com.ultrapower.eoms.ultrasla.model.EventAction;

/**
 * 事件动作处理服务接口
 * @author SunHailong
 */
public interface IEventActionProcessor {

	/**
	 * 事件动作处理
	 * @param eventActionList 事件动作集合
	 * @param currentTime 当前事件
	 * @return
	 */
	public void process(List<EventAction> eventActionList, long currentTime);
}

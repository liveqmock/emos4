package com.ultrapower.eoms.ultrasla.manager;

import java.util.List;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.ultrasla.model.EventAction;
import com.ultrapower.eoms.ultrasla.service.IEventActionProcessor;
import com.ultrapower.eoms.ultrasla.service.IEventActionRollChecker;
import com.ultrapower.eoms.ultrasla.service.IEventActionService;
import com.ultrapower.eoms.ultrasla.util.ConstantMark;

public class EventActionRollCheckerImpl implements IEventActionRollChecker {

	private IEventActionService eventActionService;
	private IEventActionProcessor eventActionProcessor;

	public void checkValueMatch() {
		// 扫描待处理事件动作表
		long currentTime = TimeUtils.getCurrentTime();
		List<EventAction> eventActionList = eventActionService.getTimeLimitEventAction(currentTime - ConstantMark.EVENT_ACTION_LIMIT_START, currentTime + ConstantMark.EVENT_ACTION_LIMIT_END);
		
		// 事件动作处理
		eventActionProcessor.process(eventActionList, currentTime);
	}
	
	public void setEventActionService(IEventActionService eventActionService) {
		this.eventActionService = eventActionService;
	}

	public void setEventActionProcessor(IEventActionProcessor eventActionProcessor) {
		this.eventActionProcessor = eventActionProcessor;
	}
}
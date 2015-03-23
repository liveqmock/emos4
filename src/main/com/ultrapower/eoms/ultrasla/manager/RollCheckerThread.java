package com.ultrapower.eoms.ultrasla.manager;

import com.ultrapower.eoms.ultrasla.service.IEventActionRollChecker;
import com.ultrapower.eoms.ultrasla.util.ConstantMark;

public class RollCheckerThread {
	private IEventActionRollChecker eventActionRollChecker;

	public void rollChecker() {
		if(ConstantMark.EVENT_ROLL_SWITCH) { // 当事件轮询开关打开时才进行轮询
			eventActionRollChecker.checkValueMatch();
		}
	}
	
	public void setEventActionRollChecker(IEventActionRollChecker eventActionRollChecker) {
		this.eventActionRollChecker = eventActionRollChecker;
	}
}

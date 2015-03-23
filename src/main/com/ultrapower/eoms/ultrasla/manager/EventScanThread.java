package com.ultrapower.eoms.ultrasla.manager;

import com.ultrapower.eoms.ultrasla.service.IEventScanTrigger;
import com.ultrapower.eoms.ultrasla.util.ConstantMark;

public class EventScanThread {
	private IEventScanTrigger eventScanTrigger;
	
	public void eventScan() {
		if(ConstantMark.EVENT_SCAN_SWITCH) { // 当事件扫描开关打开时才进行扫描
			eventScanTrigger.eventScanDeal(ConstantMark.EVENT_SCAN_SQLNAME);
		}
	}

	public void setEventScanTrigger(IEventScanTrigger eventScanTrigger) {
		this.eventScanTrigger = eventScanTrigger;
	}
}

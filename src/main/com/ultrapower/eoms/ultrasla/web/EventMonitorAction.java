package com.ultrapower.eoms.ultrasla.web;

import java.util.HashMap;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;

public class EventMonitorAction extends BaseAction {
	public String eventInstanceMonitor() {
		return SUCCESS;
	}
	public String eventActionMonitor() {
		String eventId = StringUtils.checkNullString(this.getRequest().getParameter("eventId"));
		HashMap valueMap = new HashMap();
		valueMap.put("eventId", eventId);
		this.getRequest().setAttribute("valueMap", valueMap);
		return SUCCESS;
	}
	public String eventMessageMonitor() {
		String actionId = StringUtils.checkNullString(this.getRequest().getParameter("actionId"));
		HashMap valueMap = new HashMap();
		valueMap.put("actionId", actionId);
		this.getRequest().setAttribute("valueMap", valueMap);
		return SUCCESS;
	}
}

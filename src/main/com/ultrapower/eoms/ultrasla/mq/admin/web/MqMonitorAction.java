package com.ultrapower.eoms.ultrasla.mq.admin.web;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasla.mq.admin.service.MqMonitorService;
import com.ultrapower.eoms.ultrasla.mq.admin.util.MqRuntimeInfo;

/**
 * @author RenChenglin
 * @date 2012-1-10 上午09:57:54
 * @version
 */
public class MqMonitorAction extends BaseAction {
	private MqRuntimeInfo mqRuntimeInfo;
	private MqMonitorService mqMonitorService;
	
	public String mqMonitor()
	{
		mqRuntimeInfo = mqMonitorService.getMqRuntimeInfo();
		return SUCCESS;
	}

	public MqRuntimeInfo getMqRuntimeInfo() {
		return mqRuntimeInfo;
	}

	public void setMqRuntimeInfo(MqRuntimeInfo mqRuntimeInfo) {
		this.mqRuntimeInfo = mqRuntimeInfo;
	}

	public void setMqMonitorService(MqMonitorService mqMonitorService) {
		this.mqMonitorService = mqMonitorService;
	}
	
	
}

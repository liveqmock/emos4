package com.ultrapower.eoms.ultrasla.mq.admin.service;

import com.ultrapower.eoms.ultrasla.mq.admin.util.MqRuntimeInfo;

/**
 * @author RenChenglin
 * @date 2012-1-10 上午10:10:45
 * @version
 */
public interface MqMonitorService {
	
	/**
	 * 获得MQ的运行时信息
	 * @return
	 */
	public MqRuntimeInfo getMqRuntimeInfo();
	
	/**
	 * 测试mq事件处理
	 * @return
	 */
	public String mqEventTest();
}

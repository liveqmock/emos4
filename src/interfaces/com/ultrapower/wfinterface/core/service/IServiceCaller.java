package com.ultrapower.wfinterface.core.service;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.wfinterface.core.model.ConfigClient;

public interface IServiceCaller
{
	/**
	 * 探测接口是否正常
	 * @return 为空表示相应服务可用，非空表示该服务当前出现问题
	 */
	public String isAlive(ConfigClient client) throws Exception;
	
	/**
	 * 调用服务
	 * @param client
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String call(ConfigClient client, DataRow data) throws Exception;
}

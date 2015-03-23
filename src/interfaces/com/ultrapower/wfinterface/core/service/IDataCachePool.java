package com.ultrapower.wfinterface.core.service;

import com.ultrapower.wfinterface.core.model.ServiceContext;

public interface IDataCachePool
{
	public String saveInputData(String dataID, ServiceContext serviceContext, String status);

	public String saveOutputData(String dataID, ServiceContext serviceContext, String status);
}

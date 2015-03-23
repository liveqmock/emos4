package com.ultrapower.wfinterface.core.service;

public interface IWorkflowClient
{
	/**
	 * 返回null标识处理成功，否则返回错误信息
	 * @param serviceContext
	 * @return
	 */
	public String call() throws Exception;
}

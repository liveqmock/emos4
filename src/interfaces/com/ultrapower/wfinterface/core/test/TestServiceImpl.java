package com.ultrapower.wfinterface.core.test;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.wfinterface.core.model.WorkflowContext;
import com.ultrapower.wfinterface.core.service.AbsWorkflowService;

public class TestServiceImpl extends AbsWorkflowService
{

	@Override
	protected boolean dataException(String errorString, Exception ex)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean init()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean init2()
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean postHandle()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean dataHandle(WorkflowContext wfContext, List<Attachment> attachmentsList) throws Exception
	{
		// TODO Auto-generated method stub
		return false;
	}
}

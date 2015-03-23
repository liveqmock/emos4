package com.ultrapower.wfinterface.core.test;

import com.ultrapower.wfinterface.core.model.WfiDatain;
import com.ultrapower.wfinterface.core.service.IWorkflowService;

import junit.framework.TestCase;

public class WorkflowServiceTest extends TestCase
{
	public void testCall()
	{
		IWorkflowService service = new TestServiceImpl();
		WfiDatain context = new WfiDatain();
		
		
		try
		{
			String result = service.call(context);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}

package com.ultrapower.workflow.bizservice;

import com.ultrapower.workflow.bizform.model.DealProcessModel;
import com.ultrapower.workflow.engine.task.model.ProcessTask;
import com.ultrapower.workflow.exception.WorkflowException;

public abstract interface BizCheck
{
  public abstract DealProcessModel checkPermission(String paramString1, String paramString2, String paramString3)
    throws WorkflowException;

  public abstract void chasePriviCheck(DealProcessModel paramDealProcessModel);

  public abstract ProcessTask checkTask(String paramString1, String paramString2, String paramString3)
    throws WorkflowException;

  public abstract void chasePriviCheck(ProcessTask paramProcessTask);
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizservice.BizCheck
 * JD-Core Version:    0.6.0
 */
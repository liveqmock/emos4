package com.ultrapower.workflow.engine.def;

import com.ultrapower.workflow.engine.def.model.WorkflowDescriptor;
import com.ultrapower.workflow.exception.WorkflowDefineException;

public abstract interface WorkflowFactory
{
  public abstract void init()
    throws WorkflowDefineException;

  public abstract WorkflowDescriptor getWorkflowDescriptor(String paramString)
    throws WorkflowDefineException;

  public abstract void reflushCache();

  public abstract void putCache(String paramString1, String paramString2);

  public abstract void removeCache(String paramString);
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.def.WorkflowFactory
 * JD-Core Version:    0.6.0
 */
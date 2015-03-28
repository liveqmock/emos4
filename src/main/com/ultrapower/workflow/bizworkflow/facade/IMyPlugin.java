package com.ultrapower.workflow.bizworkflow.facade;

import java.util.List;

public abstract interface IMyPlugin
{
  public abstract List<String> call(List<String> paramList)
    throws Exception;
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizworkflow.facade.IMyPlugin
 * JD-Core Version:    0.6.0
 */
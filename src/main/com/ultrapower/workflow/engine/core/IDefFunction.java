package com.ultrapower.workflow.engine.core;

import com.ultrapower.workflow.engine.core.model.ActionInfo;
import com.ultrapower.workflow.engine.core.model.DataField;
import com.ultrapower.workflow.engine.def.model.AbstractDescriptor;
import com.ultrapower.workflow.engine.def.model.ActionDescriptor;
import com.ultrapower.workflow.engine.store.model.Step;
import com.ultrapower.workflow.engine.task.model.BaseTask;
import com.ultrapower.workflow.exception.WorkflowException;
import java.util.Map;

public abstract interface IDefFunction
{
  public abstract void execute(String paramString, BaseTask paramBaseTask1, BaseTask paramBaseTask2, Step paramStep, ActionDescriptor paramActionDescriptor, AbstractDescriptor paramAbstractDescriptor, ActionInfo paramActionInfo, Map<String, DataField> paramMap)
    throws WorkflowException;
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.core.IDefFunction
 * JD-Core Version:    0.6.0
 */
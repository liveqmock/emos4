package com.ultrapower.workflow.engine.core;

import com.ultrapower.workflow.engine.core.model.DataField;
import com.ultrapower.workflow.engine.store.model.Step;
import com.ultrapower.workflow.engine.task.model.BaseTask;
import java.util.Map;

public abstract interface ICondition
{
  public abstract boolean passCondition(BaseTask paramBaseTask, Step paramStep, Map<String, String> paramMap, Map<String, DataField> paramMap1);
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.core.ICondition
 * JD-Core Version:    0.6.0
 */
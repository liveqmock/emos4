package com.ultrapower.workflow;

import com.ultrapower.workflow.engine.core.model.ActionInfo;
import com.ultrapower.workflow.engine.core.model.DataField;
import com.ultrapower.workflow.engine.core.model.EngineModel;
import com.ultrapower.workflow.engine.core.model.WfAction;
import com.ultrapower.workflow.engine.core.model.WfStep;
import com.ultrapower.workflow.engine.def.model.WorkflowDescriptor;
import com.ultrapower.workflow.engine.task.ITaskManager;
import com.ultrapower.workflow.exception.WorkflowException;
import java.util.List;
import java.util.Map;

public abstract interface UltraWorkflow
{
  public abstract void test()
    throws WorkflowException;

  public abstract EngineModel doAction(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, boolean paramBoolean, List<ActionInfo> paramList, Map<String, DataField> paramMap, Map<String, String> paramMap1)
    throws WorkflowException;

  public abstract List<WfAction> getAvailableActions(String paramString1, String paramString2, String paramString3, Map<String, DataField> paramMap)
    throws WorkflowException;

  public abstract List<WfStep> getNextWfSteps(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Map<String, DataField> paramMap)
    throws WorkflowException;

  public abstract List<WfStep> getCurrentWfSteps(String paramString, Map<String, DataField> paramMap)
    throws WorkflowException;

  public abstract WfStep getCurrentWfStep(String paramString1, String paramString2, String paramString3, Map<String, DataField> paramMap)
    throws WorkflowException;

  public abstract ITaskManager getTaskManager()
    throws WorkflowException;

  public abstract WorkflowDescriptor getWorkflowDescriptor(String paramString1, String paramString2);
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.UltraWorkflow
 * JD-Core Version:    0.6.0
 */
package com.ultrapower.workflow.engine.task;

import com.ultrapower.workflow.engine.core.model.ActionInfo;
import com.ultrapower.workflow.engine.core.model.DataField;
import com.ultrapower.workflow.engine.def.model.StepDescriptor;
import com.ultrapower.workflow.engine.store.model.Step;
import com.ultrapower.workflow.engine.store.model.WfCurrentStep;
import com.ultrapower.workflow.engine.task.model.BaseTask;
import com.ultrapower.workflow.exception.TaskException;
import java.util.List;
import java.util.Map;

public abstract interface ITaskManager<B extends BaseTask, C extends B, H extends B>
{
  public abstract B assignTask(String paramString, B paramB, Step paramStep, StepDescriptor paramStepDescriptor, ActionInfo paramActionInfo, Map<String, DataField> paramMap)
    throws TaskException;

  public abstract C getCurrentTaskById(String paramString)
    throws TaskException;

  public abstract H getHistoryTaskById(String paramString)
    throws TaskException;

  public abstract List<C> queryCurrentTask(String paramString)
    throws TaskException;

  public abstract List<H> queryHistoryTask(String paramString)
    throws TaskException;

  public abstract C updateCurrentTask(C paramC)
    throws TaskException;

  public abstract H updateHistoryTask(H paramH)
    throws TaskException;

  public abstract boolean deleteCurrentTaskById(String paramString)
    throws TaskException;

  public abstract boolean deleteHistoryTaskById(String paramString)
    throws TaskException;

  public abstract C saveCurrentTask(C paramC)
    throws TaskException;

  public abstract H saveHistoryTask(H paramH)
    throws TaskException;

  public abstract void moveToHistory(C paramC)
    throws TaskException;

  public abstract C moveToCurrent(H paramH)
    throws TaskException;

  public abstract C getCurrentTaskByStepId(String paramString)
    throws TaskException;

  public abstract H getHistoryTaskByStepId(String paramString)
    throws TaskException;

  public abstract void updateAllTaskEntryState(String paramString1, String paramString2)
    throws TaskException;

  public abstract void cancelAuditTask(WfCurrentStep paramWfCurrentStep, BaseTask paramBaseTask);

  public abstract BaseTask getTaskById(String paramString);

  public abstract void updateBizProperties(C paramC, Map<String, DataField> paramMap);
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.task.ITaskManager
 * JD-Core Version:    0.6.0
 */
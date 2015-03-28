package com.ultrapower.workflow.bizform.dpfunction.free;

import com.ultrapower.workflow.bizform.model.DealProcessModel;
import com.ultrapower.workflow.engine.core.model.ActionInfo;
import com.ultrapower.workflow.engine.core.model.DataField;
import com.ultrapower.workflow.engine.store.model.Step;
import com.ultrapower.workflow.engine.task.model.BaseTask;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DefaultFreeOutFunction extends AbstractFreeOutFunction
{
  public void setOwnFlag(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel)
  {
  }
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.DefaultFreeOutFunction
 * JD-Core Version:    0.6.0
 */
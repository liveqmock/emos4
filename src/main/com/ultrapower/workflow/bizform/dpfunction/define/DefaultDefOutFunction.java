package com.ultrapower.workflow.bizform.dpfunction.define;

import com.ultrapower.workflow.bizform.model.DealProcessModel;
import com.ultrapower.workflow.engine.core.model.ActionInfo;
import com.ultrapower.workflow.engine.core.model.DataField;
import com.ultrapower.workflow.engine.def.model.AbstractDescriptor;
import com.ultrapower.workflow.engine.store.model.Step;
import com.ultrapower.workflow.engine.task.model.BaseTask;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DefaultDefOutFunction extends AbstractDefOutFunction
{
  public void setOwnFlag(String userId, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel, AbstractDescriptor desc)
  {
  }
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.define.DefaultDefOutFunction
 * JD-Core Version:    0.6.0
 */
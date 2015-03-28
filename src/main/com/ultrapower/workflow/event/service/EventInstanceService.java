package com.ultrapower.workflow.event.service;

import com.ultrapower.workflow.engine.core.model.DataField;
import com.ultrapower.workflow.engine.task.model.BaseTask;
import com.ultrapower.workflow.event.model.EventInstance;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract interface EventInstanceService
{
  public abstract void addEventInstanceIfAllow(String paramString1, String paramString2, String paramString3, String paramString4, List<BaseTask> paramList, Map<String, DataField> paramMap);

  public abstract void addEventInstanceIfAllow(String paramString1, String paramString2, String paramString3, List<BaseTask> paramList, Map<String, DataField> paramMap);

  public abstract EventInstance getEventInstanceForForm(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract void addEventInstanceIfAllowForStep(String paramString1, String paramString2, String paramString3, String paramString4, List<BaseTask> paramList, Map<String, DataField> paramMap);
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.event.service.EventInstanceService
 * JD-Core Version:    0.6.0
 */
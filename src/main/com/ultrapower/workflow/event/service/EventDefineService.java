package com.ultrapower.workflow.event.service;

import com.ultrapower.workflow.event.model.EventDefine;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract interface EventDefineService
{
  public abstract boolean addEventDefine(EventDefine paramEventDefine);

  public abstract boolean deleteById(String paramString);

  public abstract EventDefine getByPId(String paramString);

  public abstract boolean saveOrUpdate(EventDefine paramEventDefine);

  public abstract List<EventDefine> getFormEventDefine(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract List<EventDefine> getByEventType(String paramString1, String paramString2, String paramString3);

  public abstract List<EventDefine> getByEventSubject(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract List<EventDefine> getByEventSubject(String paramString1, String paramString2, String paramString3);

  public abstract EventDefine getStepEventDefine(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract List<EventDefine> getStepSubjectEvent(String paramString1, String paramString2);

  public abstract int delEventDefOfStepSubject(String paramString1, String paramString2);

  public abstract int delEventDefByEventSubject(String paramString1, String paramString2, String paramString3);

  public abstract List<EventDefine> getStepEventDefine(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract int deleteBySchema(String paramString1, String paramString2);

  public abstract List<EventDefine> get(String paramString1, String paramString2, String paramString3);

  public abstract int deleteEventDef(String paramString1, String paramString2, String paramString3);
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.event.service.EventDefineService
 * JD-Core Version:    0.6.0
 */
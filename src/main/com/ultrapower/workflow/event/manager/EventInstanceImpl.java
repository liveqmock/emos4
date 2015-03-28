/*     */ package com.ultrapower.workflow.event.manager;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.eoms.common.core.util.NumberUtils;
/*     */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*     */ import com.ultrapower.eoms.ultrasla.service.IEventRuleTrigger;
/*     */ import com.ultrapower.workflow.engine.core.model.DataField;
/*     */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*     */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*     */ import com.ultrapower.workflow.event.model.EventDefine;
/*     */ import com.ultrapower.workflow.event.model.EventInstance;
/*     */ import com.ultrapower.workflow.event.service.EventDefineService;
/*     */ import com.ultrapower.workflow.event.service.EventInstanceService;
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional
/*     */ public class EventInstanceImpl
/*     */   implements EventInstanceService
/*     */ {
/*     */   private IDao<EventInstance> eventInstanceDao;
/*     */   public EventDefineService wfEventDefineService;
/*     */   public IEventRuleTrigger eventRuleTrigger;
/*     */ 
/*     */   public void addEventInstanceIfAllow(String baseId, String baseSchema, String oldEntryState, String newEntryState, List<BaseTask> tasks, Map<String, DataField> inputs)
/*     */   {
/*  42 */     String newHandleType = "NEW";
/*  43 */     String oldHandleType = "DESTROY";
/*  44 */     String operationType = "SLA";
/*  45 */     if (oldEntryState == null) oldEntryState = "草稿";
/*  46 */     System.out.println("oldEntryState:" + oldEntryState + ",newEntryState:" + newEntryState);
/*     */ 
/*  65 */     if (!newEntryState.equals(oldEntryState))
/*     */     {
/*  67 */       createFormEventInstance(baseId, baseSchema, tasks, inputs, oldEntryState, "OUT");
/*  68 */       createFormEventInstance(baseId, baseSchema, tasks, inputs, newEntryState, "IN");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void createOldEventInstance(String oldHandleType, EventInstance eventInstance)
/*     */   {
/*  78 */     if (eventInstance != null) {
/*  79 */       String handlemark = eventInstance.getHandlemark();
/*     */ 
/*  81 */       int eventstatus = this.eventRuleTrigger.destory(handlemark);
/*     */       try
/*     */       {
/*  84 */         EventInstance destroy = (EventInstance)eventInstance.clone();
/*  85 */         long currentTime = TimeUtils.getCurrentTime();
/*  86 */         destroy.setPid(UUIDGenerator.getId());
/*  87 */         destroy.setEventinstanceid(UUIDGenerator.getId());
/*  88 */         destroy.setHandletype(oldHandleType);
/*  89 */         destroy.setCreatetime(Long.valueOf(currentTime));
/*  90 */         destroy.setUpdatetime(Long.valueOf(currentTime));
/*  91 */         destroy.setEventstatus(Long.valueOf(Long.parseLong(eventstatus)));
/*  92 */         this.eventInstanceDao.saveOrUpdate(destroy);
/*     */       } catch (CloneNotSupportedException e) {
/*  94 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void createNewEventInstance(String baseId, String baseSchema, List<BaseTask> tasks, Map<String, DataField> inputs, String operationType, EventDefine formEventDefine, ProcessTask ptask)
/*     */   {
/* 111 */     if (formEventDefine == null) {
/* 112 */       return;
/*     */     }
/* 114 */     String eventdefid = formEventDefine.getEventdefid();
/* 115 */     String handletypegroupid = formEventDefine.getHandletypegroupid();
/* 116 */     String handletype = formEventDefine.getHandletype();
/* 117 */     String eventtype = formEventDefine.getEventtype();
/* 118 */     String eventsubject = formEventDefine.getEventsubject();
/* 119 */     String eventaction = formEventDefine.getEventaction();
/* 120 */     String eventcondition = formEventDefine.getEventcondition();
/* 121 */     String operationname = formEventDefine.getOperationname();
/*     */ 
/* 123 */     String duetimefield = formEventDefine.getDuetimefield();
/* 124 */     String dueTimeStr = null;
/*     */ 
/* 126 */     long dueTime = 0L;
/* 127 */     if (StringUtils.isNotBlank(duetimefield))
/*     */     {
/* 129 */       dueTimeStr = WfEngineUtils.getValueFromInput(inputs, duetimefield);
/* 130 */       if (StringUtils.isNotBlank(dueTimeStr)) {
/* 131 */         if (dueTimeStr.indexOf(":") > 0)
/* 132 */           dueTime = TimeUtils.formatDateStringToInt(dueTimeStr);
/*     */         else {
/* 134 */           dueTime = NumberUtils.formatToLong(dueTimeStr);
/*     */         }
/*     */       }
/* 137 */       if (ptask != null)
/*     */       {
/* 139 */         long acceptDate = ptask.getAcceptDate();
/* 140 */         long dueDate = ptask.getDueDate();
/* 141 */         if ("AcceptDate".equalsIgnoreCase(duetimefield))
/* 142 */           dueTime = acceptDate;
/* 143 */         else if ("DueDate".equalsIgnoreCase(duetimefield)) {
/* 144 */           dueTime = dueDate;
/*     */         }
/*     */       }
/*     */     }
/* 148 */     Map params = new HashMap();
/* 149 */     params.put("BASEID", baseId);
/* 150 */     params.put("BASESCHEMA", baseSchema);
/* 151 */     List ruleIdLst = new ArrayList();
/* 152 */     if (StringUtils.isNotBlank(operationname)) {
/* 153 */       String[] split = operationname.split(",");
/* 154 */       ruleIdLst = Arrays.asList(split);
/*     */     }
/* 156 */     List defaultUser = new ArrayList();
/* 157 */     List defaultDept = new ArrayList();
/* 158 */     List defaultRole = new ArrayList();
/* 159 */     long acType = 0L;
/* 160 */     if (CollectionUtils.isNotEmpty(tasks)) {
/* 161 */       for (int i = 0; i < tasks.size(); i++) {
/* 162 */         BaseTask task = (BaseTask)tasks.get(i);
/* 163 */         String actorType = task.getActorType();
/* 164 */         String assigneeId = task.getAssigneeId();
/* 165 */         String assignGroupId = task.getAssignGroupId();
/* 166 */         if ("USER".equals(actorType)) {
/* 167 */           defaultUser.add(assigneeId);
/* 168 */         } else if ("GROUP".equals(actorType)) {
/* 169 */           defaultDept.add(assignGroupId);
/* 170 */           acType = 1L;
/*     */         } else {
/* 172 */           defaultRole.add(assignGroupId);
/* 173 */           acType = 2L;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 178 */     String defaulterHanderId = null;
/* 179 */     if (acType == 0L) {
/* 180 */       if (CollectionUtils.isNotEmpty(defaultUser))
/* 181 */         defaulterHanderId = StringUtils.join(defaultUser, ",");
/*     */     }
/* 183 */     else if (acType == 1L) {
/* 184 */       if (CollectionUtils.isNotEmpty(defaultDept))
/* 185 */         defaulterHanderId = StringUtils.join(defaultDept, ",");
/*     */     }
/* 187 */     else if ((acType == 2L) && 
/* 188 */       (CollectionUtils.isNotEmpty(defaultRole))) {
/* 189 */       defaulterHanderId = StringUtils.join(defaultRole, ",");
/*     */     }
/*     */ 
/* 192 */     String handlemark = UUIDGenerator.getId();
/*     */ 
/* 194 */     int eventstatus = this.eventRuleTrigger.produce(handlemark, ruleIdLst, Long.valueOf(dueTime), defaultUser, defaultDept, defaultRole, params);
/* 195 */     if (eventstatus != 10) {
/* 196 */       eventstatus = 0;
/*     */     }
/*     */ 
/* 200 */     long currentTime = TimeUtils.getCurrentTime();
/* 201 */     EventInstance instance = new EventInstance();
/* 202 */     instance.setEventdefid(eventdefid);
/* 203 */     instance.setEventcreatetime(Long.valueOf(currentTime));
/* 204 */     instance.setHandletypegroupid(handletypegroupid);
/* 205 */     instance.setBaseschema(baseSchema);
/* 206 */     instance.setSubjectinstanceid(baseId);
/* 207 */     instance.setEventtype(eventtype);
/* 208 */     instance.setEventsubject(eventsubject);
/* 209 */     instance.setEventaction(eventaction);
/* 210 */     instance.setEventcondition(eventcondition);
/* 211 */     instance.setHandletype(handletype);
/* 212 */     instance.setOperationtype(operationType);
/* 213 */     instance.setOperationname(operationname);
/* 214 */     instance.setCreatetime(Long.valueOf(currentTime));
/* 215 */     instance.setUpdatetime(Long.valueOf(currentTime));
/* 216 */     instance.setEventbaseid(baseId);
/* 217 */     instance.setDefaulthandlertype(Long.valueOf(acType));
/* 218 */     instance.setDefaulthandlerid(defaulterHanderId);
/* 219 */     instance.setHandlemark(handlemark);
/* 220 */     instance.setEventinstanceid(handlemark);
/* 221 */     instance.setEventstatus(Long.valueOf(Long.parseLong(eventstatus)));
/* 222 */     instance.setDuetime(Long.valueOf(dueTime));
/* 223 */     this.eventInstanceDao.save(instance);
/*     */   }
/*     */ 
/*     */   public void addEventInstanceIfAllow(String baseId, String baseSchema, String actionType, List<BaseTask> tasks, Map<String, DataField> inputs)
/*     */   {
/* 230 */     if (StringUtils.isNotBlank(actionType))
/* 231 */       createActionEventInstance(baseId, baseSchema, tasks, inputs, actionType, "DO");
/*     */   }
/*     */ 
/*     */   public void addEventInstanceIfAllowForStep(String baseId, String baseSchema, String oldStepCode, String newStepCode, List<BaseTask> tasks, Map<String, DataField> inputs)
/*     */   {
/* 241 */     String oldHandleType = "DESTROY";
/* 242 */     String operationType = "SLA";
/* 243 */     String eventType = "STEP";
/* 244 */     if (CollectionUtils.isNotEmpty(tasks))
/*     */     {
/* 246 */       for (int i = 0; i < tasks.size(); i++)
/*     */       {
/* 248 */         ProcessTask task = (ProcessTask)tasks.get(i);
/* 249 */         String stepCode = task.getStepCode();
/* 250 */         newStepCode = stepCode;
/* 251 */         createStepEventInstance(baseId, baseSchema, tasks, inputs, newStepCode, "IN");
/*     */       }
/*     */     }
/*     */ 
/* 255 */     createStepEventInstance(baseId, baseSchema, tasks, inputs, oldStepCode, "OUT");
/*     */   }
/*     */ 
/*     */   private void createEventInstance(List<EventDefine> eventDefineList, String baseID, String baseSchema, List<BaseTask> tasks, Map<String, DataField> inputs)
/*     */   {
/* 260 */     if (eventDefineList != null)
/*     */     {
/* 262 */       for (EventDefine eventDefine : eventDefineList)
/*     */       {
/* 264 */         System.out.println("EventDefineID=" + eventDefine.getEventdefid() + ",Eventaction=" + eventDefine.getEventaction());
/* 265 */         if (eventDefine.getHandletype().equals("NEW"))
/*     */         {
/* 267 */           createNewEventInstance(baseID, baseSchema, tasks, inputs, "SLA", eventDefine, null);
/*     */         }
/*     */         else
/*     */         {
/* 271 */           EventInstance eventInstance = getEventInstanceForForm(baseID, baseSchema, eventDefine.getHandletypegroupid(), "NEW");
/* 272 */           createOldEventInstance("DESTORY", eventInstance);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void createFormEventInstance(String baseID, String baseSchema, List<BaseTask> tasks, Map<String, DataField> inputs, String condition, String eventAction)
/*     */   {
/* 280 */     System.out.println("Form输入参数：baseSchema=" + baseSchema + "condition=" + condition + "eventAction=" + eventAction);
/* 281 */     List eventDefineList = this.wfEventDefineService.getFormEventDefine(baseSchema, condition, eventAction, "SLA");
/* 282 */     System.out.println("Form获取到工单的" + eventAction + "EventDefine数量：" + (eventDefineList != null ? Integer.valueOf(eventDefineList.size()) : "0"));
/* 283 */     createEventInstance(eventDefineList, baseID, baseSchema, tasks, inputs);
/*     */   }
/*     */ 
/*     */   private void createStepEventInstance(String baseID, String baseSchema, List<BaseTask> tasks, Map<String, DataField> inputs, String condition, String eventAction)
/*     */   {
/* 288 */     System.out.println("Step输入参数：baseSchema=" + baseSchema + "condition=" + condition + "eventAction=" + eventAction);
/* 289 */     List eventDefineList = this.wfEventDefineService.getStepEventDefine(baseSchema, condition, eventAction, "SLA");
/* 290 */     System.out.println("Step获取到环节的" + eventAction + "EventDefine数量：" + (eventDefineList != null ? Integer.valueOf(eventDefineList.size()) : "0"));
/* 291 */     createEventInstance(eventDefineList, baseID, baseSchema, tasks, inputs);
/*     */   }
/*     */ 
/*     */   private void createActionEventInstance(String baseID, String baseSchema, List<BaseTask> tasks, Map<String, DataField> inputs, String condition, String eventAction)
/*     */   {
/* 296 */     System.out.println("Action输入参数：baseSchema=" + baseSchema + "condition=" + condition + "eventAction=" + eventAction);
/* 297 */     List eventDefineList = this.wfEventDefineService.getByEventType(baseSchema, "ACTION", condition);
/* 298 */     System.out.println("Action获取到动作的" + eventAction + "EventDefine数量：" + (eventDefineList != null ? Integer.valueOf(eventDefineList.size()) : "0"));
/* 299 */     createEventInstance(eventDefineList, baseID, baseSchema, tasks, inputs);
/*     */   }
/*     */ 
/*     */   public EventInstance getEventInstanceForForm(String baseId, String baseSchema, String eventGroupId, String handleType)
/*     */   {
/* 307 */     EventInstance ei = null;
/* 308 */     String hql = "from EventInstance where subjectinstanceid=? and baseschema=? and handletypegroupid=? and handletype=?";
/* 309 */     List temp = this.eventInstanceDao.find(hql, new Object[] { baseId, baseSchema, eventGroupId, handleType });
/* 310 */     if ((temp != null) && (temp.size() > 0)) {
/* 311 */       ei = (EventInstance)temp.get(0);
/*     */     }
/* 313 */     return ei;
/*     */   }
/*     */ 
/*     */   public IDao<EventInstance> getEventInstanceDao() {
/* 317 */     return this.eventInstanceDao;
/*     */   }
/*     */ 
/*     */   public void setEventInstanceDao(IDao<EventInstance> eventInstanceDao) {
/* 321 */     this.eventInstanceDao = eventInstanceDao;
/*     */   }
/*     */ 
/*     */   public EventDefineService getWfEventDefineService()
/*     */   {
/* 326 */     return this.wfEventDefineService;
/*     */   }
/*     */ 
/*     */   public void setWfEventDefineService(EventDefineService wfEventDefineService)
/*     */   {
/* 331 */     this.wfEventDefineService = wfEventDefineService;
/*     */   }
/*     */ 
/*     */   public IEventRuleTrigger getEventRuleTrigger() {
/* 335 */     return this.eventRuleTrigger;
/*     */   }
/*     */ 
/*     */   public void setEventRuleTrigger(IEventRuleTrigger eventRuleTrigger) {
/* 339 */     this.eventRuleTrigger = eventRuleTrigger;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.event.manager.EventInstanceImpl
 * JD-Core Version:    0.6.0
 */
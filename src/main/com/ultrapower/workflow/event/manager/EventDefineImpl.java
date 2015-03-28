/*     */ package com.ultrapower.workflow.event.manager;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.randomutil.Random15;
/*     */ import com.ultrapower.randomutil.RandomN;
/*     */ import com.ultrapower.workflow.event.model.EventDefine;
/*     */ import com.ultrapower.workflow.event.service.EventDefineService;
/*     */ import com.ultrapower.workflow.utils.WfEventUtils;
/*     */ import java.util.List;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional
/*     */ public class EventDefineImpl
/*     */   implements EventDefineService
/*     */ {
/*     */   private IDao<EventDefine> eventDefineDao;
/*     */ 
/*     */   public EventDefine getByPId(String pId)
/*     */   {
/*  20 */     EventDefine eventDefine = WfEventUtils.getEventDefineById(pId);
/*  21 */     if (eventDefine == null) {
/*  22 */       eventDefine = (EventDefine)this.eventDefineDao.get(pId);
/*     */     }
/*  24 */     return eventDefine;
/*     */   }
/*     */ 
/*     */   public boolean addEventDefine(EventDefine eventDefine) {
/*  28 */     boolean result = false;
/*  29 */     if (eventDefine == null)
/*  30 */       return result;
/*  31 */     RandomN random = new Random15();
/*  32 */     String id = random.getRandom(System.currentTimeMillis());
/*  33 */     eventDefine.setPid(id);
/*     */     try {
/*  35 */       this.eventDefineDao.save(eventDefine);
/*  36 */       WfEventUtils.addEventDefineCache(id, eventDefine);
/*  37 */       result = true;
/*     */     } catch (Exception e) {
/*  39 */       e.printStackTrace();
/*     */     }
/*  41 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean deleteById(String id) {
/*     */     try {
/*  46 */       this.eventDefineDao.executeUpdate("delete EventDefine where pid = ?", new Object[] { id });
/*  47 */       WfEventUtils.removeEventDefineCache(id);
/*     */     } catch (Exception e) {
/*  49 */       e.printStackTrace();
/*     */     }
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean saveOrUpdate(EventDefine eventDefine)
/*     */   {
/*  56 */     boolean result = false;
/*  57 */     if (eventDefine == null)
/*  58 */       return result;
/*     */     try {
/*  60 */       this.eventDefineDao.saveOrUpdate(eventDefine);
/*  61 */       WfEventUtils.addEventDefineCache(eventDefine.getPid(), eventDefine);
/*  62 */       result = true;
/*     */     } catch (Exception e) {
/*  64 */       e.printStackTrace();
/*     */     }
/*  66 */     return result;
/*     */   }
/*     */ 
/*     */   public void setEventDefineDao(IDao<EventDefine> eventDefineDao) {
/*  70 */     this.eventDefineDao = eventDefineDao;
/*     */   }
/*     */ 
/*     */   public List<EventDefine> getFormEventDefine(String baseSchema, String eventcondition, String eventAction, String operationType)
/*     */   {
/*  78 */     if ((baseSchema == null) || 
/*  79 */       (eventcondition == null) || 
/*  80 */       (eventAction == null) || 
/*  81 */       (operationType == null)) {
/*  82 */       return null;
/*     */     }
/*  84 */     List ed = WfEventUtils.getFormEventDefine(baseSchema, eventcondition, eventAction, operationType);
/*     */ 
/*  92 */     return ed;
/*     */   }
/*     */ 
/*     */   public List<EventDefine> getByEventSubject(String baseSchema, String eventtype, String eventSubjecte, String eventcondition)
/*     */   {
/* 101 */     if ((baseSchema == null) || (eventSubjecte == null) || (eventtype == null) || (eventcondition == null)) {
/* 102 */       return null;
/*     */     }
/* 104 */     List result = WfEventUtils.getByEventSubject(baseSchema, eventtype, eventSubjecte, eventcondition);
/*     */ 
/* 109 */     return result;
/*     */   }
/*     */ 
/*     */   public List<EventDefine> getByEventSubject(String baseSchema, String eventtype, String eventSubjecte)
/*     */   {
/* 114 */     if ((baseSchema == null) || (eventSubjecte == null) || (eventtype == null)) {
/* 115 */       return null;
/*     */     }
/* 117 */     String hql = "from EventDefine where baseschema=? and eventtype=? and eventsubject=?";
/* 118 */     List result = this.eventDefineDao.find(hql, new Object[] { baseSchema, eventSubjecte, eventtype });
/* 119 */     return result;
/*     */   }
/*     */ 
/*     */   public List<EventDefine> getByEventType(String baseSchema, String eventType, String eventcondition)
/*     */   {
/* 127 */     if ((baseSchema == null) || (eventType == null) || (eventcondition == null)) {
/* 128 */       return null;
/*     */     }
/* 130 */     List result = WfEventUtils.getByEventType(baseSchema, eventType, eventcondition);
/*     */ 
/* 135 */     return result;
/*     */   }
/*     */ 
/*     */   public int deleteEventDef(String baseSchema, String eventType, String operationType) {
/* 139 */     if ((baseSchema == null) || (eventType == null) || (operationType == null)) {
/* 140 */       return -1;
/*     */     }
/* 142 */     String hql = "delete EventDefine where baseschema=? and eventtype=? and operationtype=?";
/* 143 */     this.eventDefineDao.executeUpdate(hql, new Object[] { baseSchema, eventType, operationType });
/* 144 */     WfEventUtils.deleteEventDef(baseSchema, eventType, operationType);
/* 145 */     return 0;
/*     */   }
/*     */ 
/*     */   public List<EventDefine> get(String baseSchema, String eventType, String operationType) {
/* 149 */     if ((baseSchema == null) || (eventType == null) || (operationType == null)) {
/* 150 */       return null;
/*     */     }
/* 152 */     String hql = "from EventDefine where baseschema=? and eventtype=? and operationtype=?";
/* 153 */     List result = this.eventDefineDao.find(hql, new Object[] { baseSchema, eventType, operationType });
/* 154 */     return result;
/*     */   }
/*     */ 
/*     */   public int deleteBySchema(String baseSchema, String operationType) {
/* 158 */     if ((baseSchema == null) || (operationType == null) || (this.eventDefineDao == null)) {
/* 159 */       return -1;
/*     */     }
/* 161 */     String hql = "delete EventDefine where baseschema = ? and operationtype = ?";
/* 162 */     int count = this.eventDefineDao.executeUpdate(hql, new Object[] { baseSchema, operationType });
/* 163 */     WfEventUtils.deleteBySchema(baseSchema, operationType);
/* 164 */     return count;
/*     */   }
/*     */ 
/*     */   public List<EventDefine> getStepEventDefine(String baseSchema, String step, String eventAction, String operationType)
/*     */   {
/* 169 */     if ((baseSchema == null) || 
/* 170 */       (step == null) || 
/* 171 */       (eventAction == null) || 
/* 172 */       (operationType == null))
/*     */     {
/* 174 */       return null;
/*     */     }
/* 176 */     List ed = WfEventUtils.getStepEventDefine(baseSchema, step, eventAction, operationType);
/*     */ 
/* 182 */     return ed;
/*     */   }
/*     */ 
/*     */   public int delEventDefByEventSubject(String baseSchema, String eventSubject, String operationType) {
/* 186 */     if ((baseSchema == null) || (eventSubject == null) || (operationType == null)) {
/* 187 */       return -1;
/*     */     }
/* 189 */     String hql = "delete EventDefine where baseschema=? and eventsubject=? and operationtype=?";
/* 190 */     int count = this.eventDefineDao.executeUpdate(hql, new Object[] { baseSchema, eventSubject, operationType });
/* 191 */     WfEventUtils.delEventDefByEventSubject(baseSchema, eventSubject, operationType);
/* 192 */     return count;
/*     */   }
/*     */ 
/*     */   public int delEventDefOfStepSubject(String baseSchema, String operationType)
/*     */   {
/* 197 */     if ((baseSchema == null) || (operationType == null))
/*     */     {
/* 199 */       return -1;
/*     */     }
/* 201 */     String hql = "delete EventDefine where baseschema=? and operationtype=? and eventsubject<>'FORM'";
/* 202 */     int count = this.eventDefineDao.executeUpdate(hql, new Object[] { baseSchema, operationType });
/* 203 */     WfEventUtils.delEventDefOfStepSubject(baseSchema, operationType);
/* 204 */     return count;
/*     */   }
/*     */ 
/*     */   public List<EventDefine> getStepSubjectEvent(String baseSchema, String operationType)
/*     */   {
/* 209 */     if ((baseSchema == null) || (operationType == null))
/*     */     {
/* 211 */       return null;
/*     */     }
/* 213 */     String hql = "from EventDefine where baseschema=? and operationtype=? and eventsubject<>'FORM'";
/* 214 */     List result = this.eventDefineDao.find(hql, new Object[] { baseSchema, operationType });
/* 215 */     return result;
/*     */   }
/*     */ 
/*     */   public EventDefine getStepEventDefine(String baseSchema, String step, String eventConditionId, String handletype, String operationType)
/*     */   {
/* 220 */     if ((baseSchema == null) || 
/* 221 */       (step == null) || 
/* 222 */       (eventConditionId == null) || 
/* 223 */       (handletype == null) || 
/* 224 */       (operationType == null))
/*     */     {
/* 226 */       return null;
/*     */     }
/* 228 */     String hql = "from EventDefine where baseschema=? and eventsubject=? and eventconditionid=? and handletype=? and operationtype=?";
/* 229 */     EventDefine ed = null;
/* 230 */     List temp = this.eventDefineDao.find(hql, new Object[] { baseSchema, step, eventConditionId, handletype, operationType });
/* 231 */     if ((temp != null) && (temp.size() > 0)) {
/* 232 */       ed = (EventDefine)temp.get(0);
/*     */     }
/* 234 */     return ed;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.event.manager.EventDefineImpl
 * JD-Core Version:    0.6.0
 */
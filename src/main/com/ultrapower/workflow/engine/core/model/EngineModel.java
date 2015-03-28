/*     */ package com.ultrapower.workflow.engine.core.model;
/*     */ 
/*     */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class EngineModel
/*     */   implements Serializable
/*     */ {
/*  13 */   private String topEntryId = "";
/*  14 */   private String entryState = "";
/*  15 */   private String baseSn = "";
/*  16 */   private String mailContent = "";
/*  17 */   private String entryStateFlag = "";
/*     */   private String createTaskId;
/*     */   private String createStepCode;
/*     */   private String createProcessId;
/*     */   private long baseCreateTime;
/*     */   private long baseCloseTime;
/*  23 */   private Map<String, String> customMap = new HashMap();
/*  24 */   private List<BaseTask> newTasks = new ArrayList();
/*  25 */   private List<BaseTask> oldTasks = new ArrayList();
/*     */ 
/*     */   public void putCustomMap(String key, String value) {
/*  28 */     this.customMap.put(key, value);
/*     */   }
/*     */ 
/*     */   public String getCustomMap(String key) {
/*  32 */     return (String)this.customMap.get(key);
/*     */   }
/*     */ 
/*     */   public String getTopEntryId()
/*     */   {
/*  40 */     return this.topEntryId;
/*     */   }
/*     */   public void setTopEntryId(String topEntryId) {
/*  43 */     this.topEntryId = topEntryId;
/*     */   }
/*     */ 
/*     */   public String getEntryState()
/*     */   {
/*  51 */     return this.entryState;
/*     */   }
/*     */   public void setEntryState(String entryState) {
/*  54 */     this.entryState = entryState;
/*     */   }
/*     */ 
/*     */   public String getMailContent()
/*     */   {
/*  62 */     return this.mailContent;
/*     */   }
/*     */   public void setMailContent(String mailContent) {
/*  65 */     this.mailContent = mailContent;
/*     */   }
/*     */ 
/*     */   public String getEntryStateFlag()
/*     */   {
/*  73 */     return this.entryStateFlag;
/*     */   }
/*     */   public void setEntryStateFlag(String entryStateFlag) {
/*  76 */     this.entryStateFlag = entryStateFlag;
/*     */   }
/*     */   public Map<String, String> getCustomMap() {
/*  79 */     return this.customMap;
/*     */   }
/*     */   public void setCustomMap(Map<String, String> customMap) {
/*  82 */     this.customMap = customMap;
/*     */   }
/*     */ 
/*     */   public String getCreateTaskId()
/*     */   {
/*  90 */     return this.createTaskId;
/*     */   }
/*     */ 
/*     */   public void setCreateTaskId(String createTaskId) {
/*  94 */     this.createTaskId = createTaskId;
/*     */   }
/*     */ 
/*     */   public String getCreateStepCode()
/*     */   {
/* 102 */     return this.createStepCode;
/*     */   }
/*     */ 
/*     */   public void setCreateStepCode(String createStepCode) {
/* 106 */     this.createStepCode = createStepCode;
/*     */   }
/*     */ 
/*     */   public String getCreateProcessId()
/*     */   {
/* 114 */     return this.createProcessId;
/*     */   }
/*     */ 
/*     */   public void setCreateProcessId(String createProcessId) {
/* 118 */     this.createProcessId = createProcessId;
/*     */   }
/*     */ 
/*     */   public long getBaseCreateTime()
/*     */   {
/* 126 */     return this.baseCreateTime;
/*     */   }
/*     */ 
/*     */   public void setBaseCreateTime(long baseCreateTime) {
/* 130 */     this.baseCreateTime = baseCreateTime;
/*     */   }
/*     */ 
/*     */   public List<BaseTask> getNewTasks()
/*     */   {
/* 138 */     return this.newTasks;
/*     */   }
/*     */ 
/*     */   public void setNewTasks(List<BaseTask> newTasks) {
/* 142 */     this.newTasks = newTasks;
/*     */   }
/*     */ 
/*     */   public List<BaseTask> getOldTasks()
/*     */   {
/* 150 */     return this.oldTasks;
/*     */   }
/*     */ 
/*     */   public void setOldTasks(List<BaseTask> oldTasks) {
/* 154 */     this.oldTasks = oldTasks;
/*     */   }
/*     */ 
/*     */   public String getBaseSn()
/*     */   {
/* 162 */     return this.baseSn;
/*     */   }
/*     */ 
/*     */   public void setBaseSn(String baseSn) {
/* 166 */     this.baseSn = baseSn;
/*     */   }
/*     */ 
/*     */   public long getBaseCloseTime() {
/* 170 */     return this.baseCloseTime;
/*     */   }
/*     */ 
/*     */   public void setBaseCloseTime(long baseCloseTime) {
/* 174 */     this.baseCloseTime = baseCloseTime;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.core.model.EngineModel
 * JD-Core Version:    0.6.0
 */
/*     */ package com.ultrapower.workflow.engine.core.model;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ActionInfo
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private String actorId;
/*     */   private String actorType;
/*     */   private String actorName;
/*     */   private String dealType;
/*     */   private String actionType;
/*     */   private String actionName;
/*     */   private String subName;
/*     */   private String stepCode;
/*     */   private String stepId;
/*     */   private String actionDesc;
/*     */   private long assignOverTimeDate;
/*     */   private long acceptOverTimeDate;
/*     */   private long dealOverTimeDate;
/*     */ 
/*     */   public ActionInfo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ActionInfo(String actionType)
/*     */   {
/*  28 */     this.actionType = actionType;
/*     */   }
/*     */ 
/*     */   public ActionInfo(String actorId, String actorType, String actorName, String actionType, String actionName) {
/*  32 */     this.actorId = actorId;
/*  33 */     this.actorType = actorType;
/*  34 */     this.actorName = actorName;
/*  35 */     this.actionType = actionType;
/*  36 */     this.actionName = actionName;
/*     */   }
/*     */ 
/*     */   public ActionInfo(String actorId, String actorType, String actorName, String actionType, String actionName, String stepCode) {
/*  40 */     this(actorId, actorType, actorName, actionType, actionName);
/*  41 */     this.stepCode = stepCode;
/*     */   }
/*     */ 
/*     */   public String getActorId() {
/*  45 */     return this.actorId;
/*     */   }
/*     */   public void setActorId(String actorId) {
/*  48 */     this.actorId = actorId;
/*     */   }
/*     */   public String getActorType() {
/*  51 */     return this.actorType;
/*     */   }
/*     */   public void setActorType(String actorType) {
/*  54 */     this.actorType = actorType;
/*     */   }
/*     */   public String getDealType() {
/*  57 */     return this.dealType;
/*     */   }
/*     */   public void setDealType(String dealType) {
/*  60 */     this.dealType = dealType;
/*     */   }
/*     */   public String getActionType() {
/*  63 */     return this.actionType;
/*     */   }
/*     */   public void setActionType(String actionType) {
/*  66 */     this.actionType = actionType;
/*     */   }
/*     */   public String getSubName() {
/*  69 */     return this.subName;
/*     */   }
/*     */   public void setSubName(String subName) {
/*  72 */     this.subName = subName;
/*     */   }
/*     */   public String getActorName() {
/*  75 */     return this.actorName;
/*     */   }
/*     */   public void setActorName(String actorName) {
/*  78 */     this.actorName = actorName;
/*     */   }
/*     */ 
/*     */   public String getActionName()
/*     */   {
/*  83 */     return this.actionName;
/*     */   }
/*     */ 
/*     */   public void setActionName(String actionName)
/*     */   {
/*  88 */     this.actionName = actionName;
/*     */   }
/*     */ 
/*     */   public String getStepCode() {
/*  92 */     return this.stepCode;
/*     */   }
/*     */ 
/*     */   public void setStepCode(String stepCode) {
/*  96 */     this.stepCode = stepCode;
/*     */   }
/*     */ 
/*     */   public String getStepId() {
/* 100 */     return this.stepId;
/*     */   }
/*     */ 
/*     */   public void setStepId(String stepId) {
/* 104 */     this.stepId = stepId;
/*     */   }
/*     */ 
/*     */   public long getAssignOverTimeDate() {
/* 108 */     return this.assignOverTimeDate;
/*     */   }
/*     */ 
/*     */   public void setAssignOverTimeDate(long assignOverTimeDate) {
/* 112 */     this.assignOverTimeDate = assignOverTimeDate;
/*     */   }
/*     */ 
/*     */   public long getAcceptOverTimeDate() {
/* 116 */     return this.acceptOverTimeDate;
/*     */   }
/*     */ 
/*     */   public void setAcceptOverTimeDate(long acceptOverTimeDate) {
/* 120 */     this.acceptOverTimeDate = acceptOverTimeDate;
/*     */   }
/*     */ 
/*     */   public long getDealOverTimeDate() {
/* 124 */     return this.dealOverTimeDate;
/*     */   }
/*     */ 
/*     */   public void setDealOverTimeDate(long dealOverTimeDate) {
/* 128 */     this.dealOverTimeDate = dealOverTimeDate;
/*     */   }
/*     */ 
/*     */   public ActionInfo clone() {
/* 132 */     ActionInfo result = null;
/*     */     try {
/* 134 */       result = (ActionInfo)super.clone();
/*     */     } catch (CloneNotSupportedException e) {
/* 136 */       e.printStackTrace();
/*     */     }
/* 138 */     return result;
/*     */   }
/*     */ 
/*     */   public String getActionDesc() {
/* 142 */     return this.actionDesc;
/*     */   }
/*     */ 
/*     */   public void setActionDesc(String actionDesc) {
/* 146 */     this.actionDesc = actionDesc;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.core.model.ActionInfo
 * JD-Core Version:    0.6.0
 */
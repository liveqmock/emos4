/*     */ package com.ultrapower.workflow.engine.core.model;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class WfAction
/*     */   implements Serializable
/*     */ {
/*   9 */   private String actionId = "";
/*  10 */   private String actionName = "";
/*  11 */   private String actionType = "";
/*  12 */   private String actionNo = "";
/*     */   private int linkpri;
/*     */   private int remap;
/*  15 */   private String actorDesc = "处理人";
/*  16 */   private String actorActionType = "";
/*     */   private boolean needActor;
/*  18 */   private String radio = "0";
/*  19 */   private String page = "";
/*     */ 
/*     */   public WfAction()
/*     */   {
/*     */   }
/*     */ 
/*     */   public WfAction(String actionType, String actionName, String page) {
/*  26 */     this.actionType = actionType;
/*  27 */     this.actionName = actionName;
/*  28 */     this.page = page;
/*     */   }
/*     */ 
/*     */   public WfAction(String actionType, String actionName, boolean needActor)
/*     */   {
/*  33 */     this.actionType = actionType;
/*  34 */     this.actionName = actionName;
/*  35 */     this.needActor = needActor;
/*  36 */     if (needActor) {
/*  37 */       this.radio = "1";
/*     */     }
/*  39 */     if ((!"SAVE".equals(actionType)) && (!"ACCEPT".equals(actionType)))
/*  40 */       this.page = actionType.toLowerCase();
/*     */   }
/*     */ 
/*     */   public String getActionId()
/*     */   {
/*  45 */     return this.actionId;
/*     */   }
/*     */   public void setActionId(String actionId) {
/*  48 */     this.actionId = actionId;
/*     */   }
/*     */   public String getActionName() {
/*  51 */     return this.actionName;
/*     */   }
/*     */   public void setActionName(String actionName) {
/*  54 */     this.actionName = actionName;
/*     */   }
/*     */   public String getActionType() {
/*  57 */     return this.actionType;
/*     */   }
/*     */   public void setActionType(String actionType) {
/*  60 */     this.actionType = actionType;
/*     */   }
/*     */ 
/*     */   public boolean isNeedActor() {
/*  64 */     return this.needActor;
/*     */   }
/*     */ 
/*     */   public void setNeedActor(boolean needActor) {
/*  68 */     this.needActor = needActor;
/*     */   }
/*     */ 
/*     */   public String getActorDesc() {
/*  72 */     return this.actorDesc;
/*     */   }
/*     */ 
/*     */   public void setActorDesc(String actorDesc) {
/*  76 */     this.actorDesc = actorDesc;
/*     */   }
/*     */ 
/*     */   public String getPage() {
/*  80 */     return this.page;
/*     */   }
/*     */ 
/*     */   public void setPage(String page) {
/*  84 */     this.page = page;
/*     */   }
/*     */ 
/*     */   public String getRadio() {
/*  88 */     return this.radio;
/*     */   }
/*     */ 
/*     */   public void setRadio(String radio) {
/*  92 */     this.radio = radio;
/*     */   }
/*     */ 
/*     */   public String getActorActionType() {
/*  96 */     return this.actorActionType;
/*     */   }
/*     */ 
/*     */   public void setActorActionType(String actorActionType) {
/* 100 */     this.actorActionType = actorActionType;
/*     */   }
/*     */ 
/*     */   public String getActionNo() {
/* 104 */     return this.actionNo;
/*     */   }
/*     */ 
/*     */   public void setActionNo(String actionNo) {
/* 108 */     this.actionNo = actionNo;
/*     */   }
/*     */ 
/*     */   public int getLinkpri() {
/* 112 */     return this.linkpri;
/*     */   }
/*     */ 
/*     */   public void setLinkpri(int linkpri) {
/* 116 */     this.linkpri = linkpri;
/*     */   }
/*     */ 
/*     */   public int getRemap() {
/* 120 */     return this.remap;
/*     */   }
/*     */ 
/*     */   public void setRemap(int remap) {
/* 124 */     this.remap = remap;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.core.model.WfAction
 * JD-Core Version:    0.6.0
 */
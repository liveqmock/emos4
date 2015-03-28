/*     */ package com.ultrapower.workflow.engine.def.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class StepDescriptor extends AbstractDescriptor
/*     */ {
/*     */   private String statusId;
/*     */   private String statusName;
/*     */   private String desc;
/*     */   private String assignOver;
/*     */   private String acceptOver;
/*     */   private String dealOver;
/*     */   private String taskPolicy;
/*     */   private String stepNo;
/*     */   private String stepGroup;
/*     */   private boolean auto;
/*     */   private SubflowsDescriptor subflows;
/*     */   private ActorDescriptor actorDesc;
/*  23 */   private Map<String, FunctionDescriptor> preFunMap = new HashMap();
/*  24 */   private Map<String, ActionDescriptor> actionMap = new HashMap();
/*  25 */   private Map<String, FunctionDescriptor> postFunMap = new HashMap();
/*  26 */   private List<MetaDescriptor> metaList = new ArrayList();
/*     */ 
/*  28 */   public StepDescriptor(AbstractDescriptor parent) { setParent(parent); }
/*     */ 
/*     */   public String getDesc() {
/*  31 */     return this.desc;
/*     */   }
/*     */   public void setDesc(String desc) {
/*  34 */     this.desc = desc;
/*     */   }
/*     */   public SubflowsDescriptor getSubflows() {
/*  37 */     return this.subflows;
/*     */   }
/*     */   public void setSubflows(SubflowsDescriptor subflows) {
/*  40 */     this.subflows = subflows;
/*     */   }
/*     */   public Map<String, FunctionDescriptor> getPreFunMap() {
/*  43 */     return this.preFunMap;
/*     */   }
/*     */   public void setPreFunMap(Map<String, FunctionDescriptor> preFunMap) {
/*  46 */     this.preFunMap = preFunMap;
/*     */   }
/*     */   public List<FunctionDescriptor> getPreFuncList() {
/*  49 */     List preFuncList = new ArrayList();
/*  50 */     if (this.preFunMap != null) {
/*  51 */       for (Iterator iter = this.preFunMap.keySet().iterator(); iter.hasNext(); ) {
/*  52 */         String funcId = (String)iter.next();
/*  53 */         preFuncList.add(getPreFuncById(funcId));
/*     */       }
/*     */     }
/*  56 */     return preFuncList;
/*     */   }
/*     */   public Map<String, FunctionDescriptor> getPostFunMap() {
/*  59 */     return this.postFunMap;
/*     */   }
/*     */   public void setPostFunMap(Map<String, FunctionDescriptor> postFunMap) {
/*  62 */     this.postFunMap = postFunMap;
/*     */   }
/*     */   public List<FunctionDescriptor> getPostFuncList() {
/*  65 */     List postFuncList = new ArrayList();
/*  66 */     if (this.postFunMap != null) {
/*  67 */       for (Iterator iter = this.postFunMap.keySet().iterator(); iter.hasNext(); ) {
/*  68 */         String funcId = (String)iter.next();
/*  69 */         postFuncList.add(getPostFuncById(funcId));
/*     */       }
/*     */     }
/*  72 */     return postFuncList;
/*     */   }
/*     */   public List<ActionDescriptor> getActionList() {
/*  75 */     List acList = new ArrayList();
/*  76 */     if (this.postFunMap != null) {
/*  77 */       for (Iterator iter = this.actionMap.keySet().iterator(); iter.hasNext(); ) {
/*  78 */         String acId = (String)iter.next();
/*  79 */         acList.add(getActionById(acId));
/*     */       }
/*     */     }
/*  82 */     Collections.sort(acList);
/*  83 */     return acList;
/*     */   }
/*     */ 
/*     */   public void putPreFuncMap(String funcId, FunctionDescriptor func) {
/*  87 */     this.preFunMap.put(funcId, func);
/*     */   }
/*     */ 
/*     */   public void putPostFuncMap(String funcId, FunctionDescriptor func) {
/*  91 */     this.postFunMap.put(funcId, func);
/*     */   }
/*     */   public void putActionMap(String actionId, ActionDescriptor action) {
/*  94 */     this.actionMap.put(actionId, action);
/*     */   }
/*     */   public ActionDescriptor getActionById(String actionId) {
/*  97 */     return (ActionDescriptor)this.actionMap.get(actionId);
/*     */   }
/*     */   public Map<String, ActionDescriptor> getActionMap() {
/* 100 */     return this.actionMap;
/*     */   }
/*     */   public void setActionMap(Map<String, ActionDescriptor> actionMap) {
/* 103 */     this.actionMap = actionMap;
/*     */   }
/*     */   public String getStatusId() {
/* 106 */     return this.statusId;
/*     */   }
/*     */   public void setStatusId(String statusId) {
/* 109 */     this.statusId = statusId;
/*     */   }
/*     */   public FunctionDescriptor getPreFuncById(String preId) {
/* 112 */     return (FunctionDescriptor)this.preFunMap.get(preId);
/*     */   }
/*     */   public FunctionDescriptor getPostFuncById(String postId) {
/* 115 */     return (FunctionDescriptor)this.postFunMap.get(postId);
/*     */   }
/*     */   public String getTaskPolicy() {
/* 118 */     return this.taskPolicy;
/*     */   }
/*     */   public void setTaskPolicy(String taskPolicy) {
/* 121 */     this.taskPolicy = taskPolicy;
/*     */   }
/*     */   public String getStatusName() {
/* 124 */     return this.statusName;
/*     */   }
/*     */   public void setStatusName(String statusName) {
/* 127 */     this.statusName = statusName;
/*     */   }
/*     */   public String getAssignOver() {
/* 130 */     return this.assignOver;
/*     */   }
/*     */   public void setAssignOver(String assignOver) {
/* 133 */     this.assignOver = assignOver;
/*     */   }
/*     */   public String getAcceptOver() {
/* 136 */     return this.acceptOver;
/*     */   }
/*     */   public void setAcceptOver(String acceptOver) {
/* 139 */     this.acceptOver = acceptOver;
/*     */   }
/*     */   public String getDealOver() {
/* 142 */     return this.dealOver;
/*     */   }
/*     */   public void setDealOver(String dealOver) {
/* 145 */     this.dealOver = dealOver;
/*     */   }
/*     */   public boolean isAuto() {
/* 148 */     return this.auto;
/*     */   }
/*     */   public void setAuto(boolean auto) {
/* 151 */     this.auto = auto;
/*     */   }
/*     */   public ActorDescriptor getActorDesc() {
/* 154 */     return this.actorDesc;
/*     */   }
/*     */   public void setActorDesc(ActorDescriptor actorDesc) {
/* 157 */     this.actorDesc = actorDesc;
/*     */   }
/*     */   public List<MetaDescriptor> getMetaList() {
/* 160 */     return this.metaList;
/*     */   }
/*     */   public void setMetaList(List<MetaDescriptor> metaList) {
/* 163 */     this.metaList = metaList;
/*     */   }
/*     */   public String getStepNo() {
/* 166 */     return this.stepNo;
/*     */   }
/*     */   public void setStepNo(String stepNo) {
/* 169 */     this.stepNo = stepNo;
/*     */   }
/*     */   public String getStepGroup() {
/* 172 */     return this.stepGroup;
/*     */   }
/*     */   public void setStepGroup(String stepGroup) {
/* 175 */     this.stepGroup = stepGroup;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.def.model.StepDescriptor
 * JD-Core Version:    0.6.0
 */
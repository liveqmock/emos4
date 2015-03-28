/*     */ package com.ultrapower.workflow.engine.core.model;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class WfStep
/*     */   implements Serializable
/*     */ {
/*     */   private String id;
/*     */   private String desc;
/*     */   private String type;
/*     */   private String stepNo;
/*     */   private String stepGroup;
/*     */   private String subName;
/*     */   private String statusId;
/*     */   private String statusName;
/*     */   private String assignOver;
/*     */   private String acceptOver;
/*     */   private String dealOver;
/*     */   private String taskPolicy;
/*     */   private String roleId;
/*     */   private String roleName;
/*     */   private boolean isAuto;
/*  25 */   private Map<String, String> metaMaps = new HashMap();
/*  26 */   private List<ActionInfo> acInfoList = new ArrayList();
/*     */ 
/*  28 */   public String getId() { return this.id; }
/*     */ 
/*     */   public void setId(String id) {
/*  31 */     this.id = id;
/*     */   }
/*     */   public String getDesc() {
/*  34 */     return this.desc;
/*     */   }
/*     */   public void setDesc(String desc) {
/*  37 */     this.desc = desc;
/*     */   }
/*     */   public String getSubName() {
/*  40 */     return this.subName;
/*     */   }
/*     */   public void setSubName(String subName) {
/*  43 */     this.subName = subName;
/*     */   }
/*     */   public String getStatusId() {
/*  46 */     return this.statusId;
/*     */   }
/*     */   public void setStatusId(String statusId) {
/*  49 */     this.statusId = statusId;
/*     */   }
/*     */   public String getStatusName() {
/*  52 */     return this.statusName;
/*     */   }
/*     */   public void setStatusName(String statusName) {
/*  55 */     this.statusName = statusName;
/*     */   }
/*     */   public String getTaskPolicy() {
/*  58 */     return this.taskPolicy;
/*     */   }
/*     */   public void setTaskPolicy(String taskPolicy) {
/*  61 */     this.taskPolicy = taskPolicy;
/*     */   }
/*     */   public String getAssignOver() {
/*  64 */     return this.assignOver;
/*     */   }
/*     */   public void setAssignOver(String assignOver) {
/*  67 */     this.assignOver = assignOver;
/*     */   }
/*     */   public String getAcceptOver() {
/*  70 */     return this.acceptOver;
/*     */   }
/*     */   public void setAcceptOver(String acceptOver) {
/*  73 */     this.acceptOver = acceptOver;
/*     */   }
/*     */   public String getDealOver() {
/*  76 */     return this.dealOver;
/*     */   }
/*     */   public void setDealOver(String dealOver) {
/*  79 */     this.dealOver = dealOver;
/*     */   }
/*     */   public String getType() {
/*  82 */     return this.type;
/*     */   }
/*     */   public void setType(String type) {
/*  85 */     this.type = type;
/*     */   }
/*     */   public String getRoleId() {
/*  88 */     return this.roleId;
/*     */   }
/*     */   public void setRoleId(String roleId) {
/*  91 */     this.roleId = roleId;
/*     */   }
/*     */   public String getRoleName() {
/*  94 */     return this.roleName;
/*     */   }
/*     */   public void setRoleName(String roleName) {
/*  97 */     this.roleName = roleName;
/*     */   }
/*     */   public Map<String, String> getMetaMaps() {
/* 100 */     return this.metaMaps;
/*     */   }
/*     */   public void setMetaMaps(Map<String, String> metaMaps) {
/* 103 */     this.metaMaps = metaMaps;
/*     */   }
/*     */   public boolean isAuto() {
/* 106 */     return this.isAuto;
/*     */   }
/*     */   public void setAuto(boolean isAuto) {
/* 109 */     this.isAuto = isAuto;
/*     */   }
/*     */   public List<ActionInfo> getAcInfoList() {
/* 112 */     return this.acInfoList;
/*     */   }
/*     */   public void setAcInfoList(List<ActionInfo> acInfoList) {
/* 115 */     this.acInfoList = acInfoList;
/*     */   }
/*     */   public String getStepNo() {
/* 118 */     return this.stepNo;
/*     */   }
/*     */   public void setStepNo(String stepNo) {
/* 121 */     this.stepNo = stepNo;
/*     */   }
/*     */   public String getStepGroup() {
/* 124 */     return this.stepGroup;
/*     */   }
/*     */   public void setStepGroup(String stepGroup) {
/* 127 */     this.stepGroup = stepGroup;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.core.model.WfStep
 * JD-Core Version:    0.6.0
 */
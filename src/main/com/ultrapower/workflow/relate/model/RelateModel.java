/*     */ package com.ultrapower.workflow.relate.model;
/*     */ 
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_RELATION")
/*     */ public class RelateModel
/*     */   implements Serializable
/*     */ {
/*     */   private String id;
/*     */   private String baseId;
/*     */   private String baseSchema;
/*     */   private String baseName;
/*     */   private String baseSN;
/*     */   private String baseFlowId;
/*     */   private String baseProcessId;
/*     */   private String basePhaseNo;
/*     */   private String baseProcessType;
/*     */   private String baseProcessLogId;
/*     */   private long baseCreateTime;
/*     */   private String relateBaseId;
/*     */   private String relateBaseSchema;
/*     */   private String relateBaseName;
/*     */   private long relateType;
/*     */   private String relateBaseSn;
/*     */   private long relateBaseCreateTime;
/*     */   private String relateBaseCreateUser;
/*     */   private String relateTaskId;
/*     */   private long relateTime;
/*     */   private String relateUserLoginName;
/*     */   private String relateUserName;
/*     */ 
/*     */   public String getBaseSN()
/*     */   {
/*  39 */     return this.baseSN;
/*     */   }
/*     */ 
/*     */   public void setBaseSN(String baseSN) {
/*  43 */     this.baseSN = baseSN;
/*     */   }
/*     */ 
/*     */   public String getRelateUserName() {
/*  47 */     return this.relateUserName;
/*     */   }
/*     */ 
/*     */   public void setRelateUserName(String relateUserName) {
/*  51 */     this.relateUserName = relateUserName;
/*     */   }
/*     */ 
/*     */   public RelateModel() {
/*  55 */     this.id = UUIDGenerator.getId();
/*     */   }
/*     */   @Id
/*     */   public String getId() {
/*  60 */     return this.id;
/*     */   }
/*     */   public void setId(String id) {
/*  63 */     this.id = id;
/*     */   }
/*     */   public String getBaseId() {
/*  66 */     return this.baseId;
/*     */   }
/*     */   public void setBaseId(String baseId) {
/*  69 */     this.baseId = baseId;
/*     */   }
/*     */   public String getBaseSchema() {
/*  72 */     return this.baseSchema;
/*     */   }
/*     */   public void setBaseSchema(String baseSchema) {
/*  75 */     this.baseSchema = baseSchema;
/*     */   }
/*     */   public String getBaseName() {
/*  78 */     return this.baseName;
/*     */   }
/*     */   public void setBaseName(String baseName) {
/*  81 */     this.baseName = baseName;
/*     */   }
/*     */   public String getBaseFlowId() {
/*  84 */     return this.baseFlowId;
/*     */   }
/*     */   public void setBaseFlowId(String baseFlowId) {
/*  87 */     this.baseFlowId = baseFlowId;
/*     */   }
/*     */   public String getBaseProcessId() {
/*  90 */     return this.baseProcessId;
/*     */   }
/*     */   public void setBaseProcessId(String baseProcessId) {
/*  93 */     this.baseProcessId = baseProcessId;
/*     */   }
/*     */   public String getBasePhaseNo() {
/*  96 */     return this.basePhaseNo;
/*     */   }
/*     */   public void setBasePhaseNo(String basePhaseNo) {
/*  99 */     this.basePhaseNo = basePhaseNo;
/*     */   }
/*     */   public String getBaseProcessType() {
/* 102 */     return this.baseProcessType;
/*     */   }
/*     */   public void setBaseProcessType(String baseProcessType) {
/* 105 */     this.baseProcessType = baseProcessType;
/*     */   }
/*     */   public String getBaseProcessLogId() {
/* 108 */     return this.baseProcessLogId;
/*     */   }
/*     */   public void setBaseProcessLogId(String baseProcessLogId) {
/* 111 */     this.baseProcessLogId = baseProcessLogId;
/*     */   }
/*     */   public long getBaseCreateTime() {
/* 114 */     return this.baseCreateTime;
/*     */   }
/*     */   public void setBaseCreateTime(long baseCreateTime) {
/* 117 */     this.baseCreateTime = baseCreateTime;
/*     */   }
/*     */   public String getRelateBaseId() {
/* 120 */     return this.relateBaseId;
/*     */   }
/*     */   public void setRelateBaseId(String relateBaseId) {
/* 123 */     this.relateBaseId = relateBaseId;
/*     */   }
/*     */   public String getRelateBaseSchema() {
/* 126 */     return this.relateBaseSchema;
/*     */   }
/*     */   public void setRelateBaseSchema(String relateBaseSchema) {
/* 129 */     this.relateBaseSchema = relateBaseSchema;
/*     */   }
/*     */   public String getRelateBaseName() {
/* 132 */     return this.relateBaseName;
/*     */   }
/*     */   public void setRelateBaseName(String relateBaseName) {
/* 135 */     this.relateBaseName = relateBaseName;
/*     */   }
/*     */   public long getRelateType() {
/* 138 */     return this.relateType;
/*     */   }
/*     */   public void setRelateType(long relateType) {
/* 141 */     this.relateType = relateType;
/*     */   }
/*     */   public String getRelateBaseSn() {
/* 144 */     return this.relateBaseSn;
/*     */   }
/*     */   public void setRelateBaseSn(String relateBaseSn) {
/* 147 */     this.relateBaseSn = relateBaseSn;
/*     */   }
/*     */   public long getRelateBaseCreateTime() {
/* 150 */     return this.relateBaseCreateTime;
/*     */   }
/*     */   public void setRelateBaseCreateTime(long relateBaseCreateTime) {
/* 153 */     this.relateBaseCreateTime = relateBaseCreateTime;
/*     */   }
/*     */   public String getRelateBaseCreateUser() {
/* 156 */     return this.relateBaseCreateUser;
/*     */   }
/*     */   public void setRelateBaseCreateUser(String relateBaseCreateUser) {
/* 159 */     this.relateBaseCreateUser = relateBaseCreateUser;
/*     */   }
/*     */   public long getRelateTime() {
/* 162 */     return this.relateTime;
/*     */   }
/*     */   public void setRelateTime(long relateTime) {
/* 165 */     this.relateTime = relateTime;
/*     */   }
/*     */ 
/*     */   public String getRelateUserLoginName() {
/* 169 */     return this.relateUserLoginName;
/*     */   }
/*     */ 
/*     */   public void setRelateUserLoginName(String relateUserLoginName) {
/* 173 */     this.relateUserLoginName = relateUserLoginName;
/*     */   }
/*     */ 
/*     */   public String getRelateTaskId() {
/* 177 */     return this.relateTaskId;
/*     */   }
/*     */ 
/*     */   public void setRelateTaskId(String relateTaskId) {
/* 181 */     this.relateTaskId = relateTaskId;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.relate.model.RelateModel
 * JD-Core Version:    0.6.0
 */
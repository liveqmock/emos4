/*     */ package com.ultrapower.workflow.engine.store.model;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_ENTRY")
/*     */ public class WfEntry
/*     */   implements Serializable
/*     */ {
/*     */   private String id;
/*     */   private String parentEntryId;
/*     */   private String topEntryId;
/*     */   private String parentFlowId;
/*     */   private String topFlowId;
/*     */   private String type;
/*     */   private String defName;
/*     */   private String state;
/*     */   private long closeTime;
/*     */   private long createTime;
/*     */   private String endCode;
/*     */ 
/*     */   public WfEntry()
/*     */   {
/*  31 */     this.id = UUIDGenerator.getId();
/*     */   }
/*     */ 
/*     */   public WfEntry(String defName, String type, String parEntryId, String topEntryId, String parFlowId) {
/*  35 */     this.defName = defName;
/*  36 */     this.type = type;
/*  37 */     this.state = "active";
/*  38 */     this.createTime = TimeUtils.getCurrentTime();
/*  39 */     this.parentEntryId = parEntryId;
/*  40 */     this.topEntryId = topEntryId;
/*  41 */     this.parentFlowId = parFlowId;
/*  42 */     this.id = UUIDGenerator.getId();
/*     */   }
/*     */   @Id
/*     */   public String getId() {
/*  47 */     return this.id;
/*     */   }
/*     */   public void setId(String id) {
/*  50 */     this.id = id;
/*     */   }
/*     */   public String getParentEntryId() {
/*  53 */     return this.parentEntryId;
/*     */   }
/*     */   public void setParentEntryId(String parentEntryId) {
/*  56 */     this.parentEntryId = parentEntryId;
/*     */   }
/*     */   public String getTopEntryId() {
/*  59 */     return this.topEntryId;
/*     */   }
/*     */   public void setTopEntryId(String topEntryId) {
/*  62 */     this.topEntryId = topEntryId;
/*     */   }
/*     */   public String getParentFlowId() {
/*  65 */     return this.parentFlowId;
/*     */   }
/*     */   public void setParentFlowId(String parentFlowId) {
/*  68 */     this.parentFlowId = parentFlowId;
/*     */   }
/*     */   public String getType() {
/*  71 */     return this.type;
/*     */   }
/*     */   public void setType(String type) {
/*  74 */     this.type = type;
/*     */   }
/*     */   public String getDefName() {
/*  77 */     return this.defName;
/*     */   }
/*     */   public void setDefName(String defName) {
/*  80 */     this.defName = defName;
/*     */   }
/*     */   public String getState() {
/*  83 */     return this.state;
/*     */   }
/*     */   public void setState(String state) {
/*  86 */     this.state = state;
/*     */   }
/*     */   public long getCloseTime() {
/*  89 */     return this.closeTime;
/*     */   }
/*     */   public void setCloseTime(long closeTime) {
/*  92 */     this.closeTime = closeTime;
/*     */   }
/*     */   public long getCreateTime() {
/*  95 */     return this.createTime;
/*     */   }
/*     */   public void setCreateTime(long createTime) {
/*  98 */     this.createTime = createTime;
/*     */   }
/*     */ 
/*     */   public String getEndCode() {
/* 102 */     return this.endCode;
/*     */   }
/*     */ 
/*     */   public void setEndCode(String endCode) {
/* 106 */     this.endCode = endCode;
/*     */   }
/*     */ 
/*     */   public String getTopFlowId() {
/* 110 */     return this.topFlowId;
/*     */   }
/*     */ 
/*     */   public void setTopFlowId(String topFlowId) {
/* 114 */     this.topFlowId = topFlowId;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.store.model.WfEntry
 * JD-Core Version:    0.6.0
 */
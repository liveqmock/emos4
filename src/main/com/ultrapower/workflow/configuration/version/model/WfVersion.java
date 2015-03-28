/*     */ package com.ultrapower.workflow.configuration.version.model;
/*     */ 
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_VERSION")
/*     */ public class WfVersion
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private String id;
/*     */   private String baseCode;
/*     */   private String baseName;
/*     */   private String code;
/*     */   private String name;
/*     */   private long isPublish;
/*     */   private long publishTime;
/*     */   private long entryCount;
/*     */   private long todayEntryCount;
/*     */   private String remark;
/*     */   private long createTime;
/*     */   private long lastEntryTime;
/*     */   private String designXml;
/*     */   private String workflowXml;
/*     */   private long subflag;
/*     */ 
/*     */   public WfVersion()
/*     */   {
/*  45 */     this.id = UUIDGenerator.getId();
/*     */   }
/*     */ 
/*     */   public WfVersion(String baseCode, String code, String name, long isPublish)
/*     */   {
/*  50 */     this.id = UUIDGenerator.getId();
/*  51 */     this.baseCode = baseCode;
/*  52 */     this.code = code;
/*  53 */     this.name = name;
/*  54 */     this.isPublish = isPublish;
/*     */   }
/*     */ 
/*     */   public WfVersion(String baseCode, String code, String name, long isPublish, long entryCount, long todayEntryCount, String remark)
/*     */   {
/*  59 */     this.id = UUIDGenerator.getId();
/*  60 */     this.baseCode = baseCode;
/*  61 */     this.code = code;
/*  62 */     this.name = name;
/*  63 */     this.isPublish = isPublish;
/*  64 */     this.entryCount = entryCount;
/*  65 */     this.todayEntryCount = todayEntryCount;
/*  66 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   @Id
/*     */   public String getId()
/*     */   {
/*  73 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  77 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getRemark()
/*     */   {
/*  82 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/*  86 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   public String getBaseCode() {
/*  90 */     return this.baseCode;
/*     */   }
/*     */ 
/*     */   public void setBaseCode(String baseCode) {
/*  94 */     this.baseCode = baseCode;
/*     */   }
/*     */ 
/*     */   public String getBaseName() {
/*  98 */     return this.baseName;
/*     */   }
/*     */ 
/*     */   public void setBaseName(String baseName) {
/* 102 */     this.baseName = baseName;
/*     */   }
/*     */ 
/*     */   public String getCode() {
/* 106 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(String code) {
/* 110 */     this.code = code;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 114 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 118 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public long getIsPublish() {
/* 122 */     return this.isPublish;
/*     */   }
/*     */ 
/*     */   public void setIsPublish(long isPublish) {
/* 126 */     this.isPublish = isPublish;
/*     */   }
/*     */ 
/*     */   public long getEntryCount() {
/* 130 */     return this.entryCount;
/*     */   }
/*     */ 
/*     */   public void setEntryCount(long entryCount) {
/* 134 */     this.entryCount = entryCount;
/*     */   }
/*     */ 
/*     */   public long getTodayEntryCount() {
/* 138 */     return this.todayEntryCount;
/*     */   }
/*     */ 
/*     */   public void setTodayEntryCount(long todayEntryCount) {
/* 142 */     this.todayEntryCount = todayEntryCount;
/*     */   }
/*     */ 
/*     */   public long getCreateTime() {
/* 146 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(long createTime) {
/* 150 */     this.createTime = createTime;
/*     */   }
/*     */ 
/*     */   public long getLastEntryTime() {
/* 154 */     return this.lastEntryTime;
/*     */   }
/*     */ 
/*     */   public void setLastEntryTime(long lastEntryTime) {
/* 158 */     this.lastEntryTime = lastEntryTime;
/*     */   }
/*     */ 
/*     */   public String getDesignXml() {
/* 162 */     return this.designXml;
/*     */   }
/*     */ 
/*     */   public void setDesignXml(String designXml) {
/* 166 */     this.designXml = designXml;
/*     */   }
/*     */ 
/*     */   public String getWorkflowXml() {
/* 170 */     return this.workflowXml;
/*     */   }
/*     */ 
/*     */   public void setWorkflowXml(String workflowXml) {
/* 174 */     this.workflowXml = workflowXml;
/*     */   }
/*     */ 
/*     */   public WfVersion clone() {
/* 178 */     WfVersion result = null;
/*     */     try {
/* 180 */       result = (WfVersion)super.clone();
/*     */     } catch (CloneNotSupportedException e) {
/* 182 */       e.printStackTrace();
/*     */     }
/* 184 */     return result;
/*     */   }
/*     */ 
/*     */   public long getPublishTime() {
/* 188 */     return this.publishTime;
/*     */   }
/*     */ 
/*     */   public void setPublishTime(long publishTime) {
/* 192 */     this.publishTime = publishTime;
/*     */   }
/*     */ 
/*     */   public long getSubflag() {
/* 196 */     return this.subflag;
/*     */   }
/*     */ 
/*     */   public void setSubflag(long subflag) {
/* 200 */     this.subflag = subflag;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.version.model.WfVersion
 * JD-Core Version:    0.6.0
 */
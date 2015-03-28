/*     */ package com.ultrapower.workflow.configuration.custom.model;
/*     */ 
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_MODEL")
/*     */ public class WfModel
/*     */   implements Serializable
/*     */ {
/*     */   private String id;
/*     */   private String code;
/*     */   private String name;
/*     */   private String photoPath;
/*     */   private long isPublish;
/*     */   private String type;
/*     */   private String remark;
/*     */   private String preFunction;
/*     */   private String postFunction;
/*     */ 
/*     */   public WfModel()
/*     */   {
/*  37 */     this.id = UUIDGenerator.getId();
/*     */   }
/*     */ 
/*     */   public WfModel(String code, String name, String photoPath, Long isPublish)
/*     */   {
/*  43 */     this.id = UUIDGenerator.getId();
/*  44 */     this.code = code;
/*  45 */     this.name = name;
/*  46 */     this.photoPath = photoPath;
/*  47 */     this.isPublish = isPublish.longValue();
/*     */   }
/*     */ 
/*     */   public WfModel(String code, String name, String photoPath, Long isPublish, String type, String remark)
/*     */   {
/*  54 */     this.id = UUIDGenerator.getId();
/*  55 */     this.code = code;
/*  56 */     this.name = name;
/*  57 */     this.photoPath = photoPath;
/*  58 */     this.isPublish = isPublish.longValue();
/*  59 */     this.type = type;
/*  60 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   @Id
/*     */   public String getId()
/*     */   {
/*  67 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  71 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getRemark() {
/*  75 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/*  79 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   public String getCode() {
/*  83 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(String code) {
/*  87 */     this.code = code;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  91 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  95 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getPhotoPath() {
/*  99 */     return this.photoPath;
/*     */   }
/*     */ 
/*     */   public void setPhotoPath(String photoPath) {
/* 103 */     this.photoPath = photoPath;
/*     */   }
/*     */ 
/*     */   public long getIsPublish() {
/* 107 */     return this.isPublish;
/*     */   }
/*     */ 
/*     */   public String getPreFunction() {
/* 111 */     return this.preFunction;
/*     */   }
/*     */ 
/*     */   public void setPreFunction(String preFunction) {
/* 115 */     this.preFunction = preFunction;
/*     */   }
/*     */ 
/*     */   public String getPostFunction() {
/* 119 */     return this.postFunction;
/*     */   }
/*     */ 
/*     */   public void setPostFunction(String postFunction) {
/* 123 */     this.postFunction = postFunction;
/*     */   }
/*     */ 
/*     */   public void setIsPublish(long isPublish) {
/* 127 */     this.isPublish = isPublish;
/*     */   }
/*     */ 
/*     */   public String getType() {
/* 131 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/* 135 */     this.type = type;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.custom.model.WfModel
 * JD-Core Version:    0.6.0
 */
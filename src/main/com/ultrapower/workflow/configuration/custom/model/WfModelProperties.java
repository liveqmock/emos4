/*     */ package com.ultrapower.workflow.configuration.custom.model;
/*     */ 
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_MODEL_PROPERTIES")
/*     */ public class WfModelProperties
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String id;
/*     */   private String modelCode;
/*     */   private String modelName;
/*     */   private String code;
/*     */   private String name;
/*     */   private String type;
/*     */   private String dict;
/*     */   private long isNull;
/*     */   private String defaulvalue;
/*     */   private long orderBy;
/*     */ 
/*     */   public long getOrderBy()
/*     */   {
/*  41 */     return this.orderBy;
/*     */   }
/*     */ 
/*     */   public void setOrderBy(long orderBy) {
/*  45 */     this.orderBy = orderBy;
/*     */   }
/*     */ 
/*     */   public WfModelProperties()
/*     */   {
/*  50 */     this.id = UUIDGenerator.getId();
/*     */   }
/*     */ 
/*     */   public WfModelProperties(String code, String name)
/*     */   {
/*  56 */     this.id = UUIDGenerator.getId();
/*  57 */     this.code = code;
/*  58 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public WfModelProperties(String code, String name, String type, String dict, long isNull, String defaulvalue)
/*     */   {
/*  66 */     this.id = UUIDGenerator.getId();
/*  67 */     this.code = code;
/*  68 */     this.name = name;
/*  69 */     this.type = type;
/*  70 */     this.dict = dict;
/*  71 */     this.isNull = isNull;
/*  72 */     this.defaulvalue = defaulvalue;
/*     */   }
/*     */ 
/*     */   @Id
/*     */   public String getId()
/*     */   {
/*  79 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  83 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getCode() {
/*  87 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(String code) {
/*  91 */     this.code = code;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  95 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  99 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getType() {
/* 103 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/* 107 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public String getDict() {
/* 111 */     return this.dict;
/*     */   }
/*     */ 
/*     */   public void setDict(String dict) {
/* 115 */     this.dict = dict;
/*     */   }
/*     */ 
/*     */   public long getIsNull() {
/* 119 */     return this.isNull;
/*     */   }
/*     */ 
/*     */   public void setIsNull(long isNull) {
/* 123 */     this.isNull = isNull;
/*     */   }
/*     */ 
/*     */   public String getDefaulvalue() {
/* 127 */     return this.defaulvalue;
/*     */   }
/*     */ 
/*     */   public void setDefaulvalue(String defaulvalue) {
/* 131 */     this.defaulvalue = defaulvalue;
/*     */   }
/*     */ 
/*     */   public String getModelCode() {
/* 135 */     return this.modelCode;
/*     */   }
/*     */ 
/*     */   public void setModelCode(String modelCode) {
/* 139 */     this.modelCode = modelCode;
/*     */   }
/*     */ 
/*     */   public String getModelName() {
/* 143 */     return this.modelName;
/*     */   }
/*     */ 
/*     */   public void setModelName(String modelName) {
/* 147 */     this.modelName = modelName;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.custom.model.WfModelProperties
 * JD-Core Version:    0.6.0
 */
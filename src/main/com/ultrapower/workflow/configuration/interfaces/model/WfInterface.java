/*     */ package com.ultrapower.workflow.configuration.interfaces.model;
/*     */ 
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_INTERFACE")
/*     */ public class WfInterface
/*     */   implements Serializable
/*     */ {
/*     */   private String id;
/*     */   private String code;
/*     */   private String name;
/*     */   private String type;
/*     */   private String path;
/*     */   private String remark;
/*     */ 
/*     */   public WfInterface()
/*     */   {
/*  34 */     this.id = UUIDGenerator.getId();
/*     */   }
/*     */ 
/*     */   public WfInterface(String code, String name, String type, String path)
/*     */   {
/*  39 */     this.id = UUIDGenerator.getId();
/*  40 */     this.code = code;
/*  41 */     this.name = name;
/*  42 */     this.type = type;
/*  43 */     this.path = path;
/*     */   }
/*     */ 
/*     */   public WfInterface(String code, String name, String type, String path, String remark)
/*     */   {
/*  49 */     this.id = UUIDGenerator.getId();
/*  50 */     this.code = code;
/*  51 */     this.name = name;
/*  52 */     this.type = type;
/*  53 */     this.path = path;
/*  54 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   @Id
/*     */   public String getId() {
/*  60 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  64 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getRemark() {
/*  68 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/*  72 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   public String getCode() {
/*  76 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(String code) {
/*  80 */     this.code = code;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  84 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/*  88 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getType() {
/*  92 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(String type) {
/*  96 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public String getPath() {
/* 100 */     return this.path;
/*     */   }
/*     */ 
/*     */   public void setPath(String path) {
/* 104 */     this.path = path;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.interfaces.model.WfInterface
 * JD-Core Version:    0.6.0
 */
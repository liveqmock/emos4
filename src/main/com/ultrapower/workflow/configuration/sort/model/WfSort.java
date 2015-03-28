/*     */ package com.ultrapower.workflow.configuration.sort.model;
/*     */ 
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_SORT")
/*     */ public class WfSort
/*     */   implements Serializable
/*     */ {
/*     */   private String id;
/*     */   private String code;
/*     */   private String name;
/*     */   private String pid;
/*     */   private Long orderby;
/*     */   private String remark;
/*     */   private long createTime;
/*     */ 
/*     */   public WfSort()
/*     */   {
/*  36 */     this.id = UUIDGenerator.getId();
/*     */   }
/*     */ 
/*     */   public WfSort(String code, String name)
/*     */   {
/*  41 */     this.id = UUIDGenerator.getId();
/*  42 */     this.code = code;
/*  43 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public WfSort(String code, String name, String pid, Long orderby, String remark)
/*     */   {
/*  48 */     this.id = UUIDGenerator.getId();
/*  49 */     this.code = code;
/*  50 */     this.name = name;
/*  51 */     this.pid = pid;
/*  52 */     this.orderby = orderby;
/*  53 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   @Id
/*     */   public String getId() {
/*  59 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  63 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getPid() {
/*  67 */     return this.pid;
/*     */   }
/*     */ 
/*     */   public void setPid(String pid) {
/*  71 */     this.pid = pid;
/*     */   }
/*     */ 
/*     */   public Long getOrderby() {
/*  75 */     return this.orderby;
/*     */   }
/*     */ 
/*     */   public void setOrderby(Long orderby) {
/*  79 */     this.orderby = orderby;
/*     */   }
/*     */ 
/*     */   public String getRemark() {
/*  83 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/*  87 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   public String getCode() {
/*  91 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(String code) {
/*  95 */     this.code = code;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  99 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 103 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public long getCreateTime() {
/* 107 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(long createTime) {
/* 111 */     this.createTime = createTime;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.sort.model.WfSort
 * JD-Core Version:    0.6.0
 */
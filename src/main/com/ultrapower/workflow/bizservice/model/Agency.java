/*     */ package com.ultrapower.workflow.bizservice.model;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.Transient;
/*     */ import org.hibernate.annotations.GenericGenerator;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_AGENT")
/*     */ public class Agency
/*     */   implements Serializable
/*     */ {
/*     */   private String id;
/*     */   private String baseSchema;
/*     */   private String baseName;
/*     */   private String dealerId;
/*     */   private String dealer;
/*     */   private String agentId;
/*     */   private String agent;
/*     */   private Long bgDate;
/*     */   private Long edDate;
/*     */   private String bgDateStr;
/*     */   private String edDateStr;
/*     */   private Long createTime;
/*     */ 
/*     */   @Id
/*     */   @GeneratedValue(generator="system-uuid")
/*     */   @GenericGenerator(name="system-uuid", strategy="uuid")
/*     */   public String getId()
/*     */   {
/*  36 */     return this.id;
/*     */   }
/*     */   public void setId(String id) {
/*  39 */     this.id = id;
/*     */   }
/*     */   public String getBaseSchema() {
/*  42 */     return this.baseSchema;
/*     */   }
/*     */   public void setBaseSchema(String baseSchema) {
/*  45 */     this.baseSchema = baseSchema;
/*     */   }
/*     */   public String getBaseName() {
/*  48 */     return this.baseName;
/*     */   }
/*     */   public void setBaseName(String baseName) {
/*  51 */     this.baseName = baseName;
/*     */   }
/*     */   public String getDealerId() {
/*  54 */     return this.dealerId;
/*     */   }
/*     */   public void setDealerId(String dealerId) {
/*  57 */     this.dealerId = dealerId;
/*     */   }
/*     */   public String getDealer() {
/*  60 */     return this.dealer;
/*     */   }
/*     */   public void setDealer(String dealer) {
/*  63 */     this.dealer = dealer;
/*     */   }
/*     */   public String getAgentId() {
/*  66 */     return this.agentId;
/*     */   }
/*     */   public void setAgentId(String agentId) {
/*  69 */     this.agentId = agentId;
/*     */   }
/*     */   public String getAgent() {
/*  72 */     return this.agent;
/*     */   }
/*     */   public void setAgent(String agent) {
/*  75 */     this.agent = agent;
/*     */   }
/*     */ 
/*     */   public Long getBgDate() {
/*  79 */     return this.bgDate;
/*     */   }
/*     */ 
/*     */   public void setBgDate(Long bgDate) {
/*  83 */     this.bgDate = bgDate;
/*     */   }
/*     */ 
/*     */   public Long getEdDate() {
/*  87 */     return this.edDate;
/*     */   }
/*     */ 
/*     */   public void setEdDate(Long edDate) {
/*  91 */     this.edDate = edDate;
/*     */   }
/*     */ 
/*     */   public Long getCreateTime() {
/*  95 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(Long createTime) {
/*  99 */     this.createTime = createTime;
/*     */   }
/*     */   @Transient
/*     */   public String getBgDateStr() {
/* 104 */     return this.bgDateStr;
/*     */   }
/*     */ 
/*     */   public void setBgDateStr(String bgDateStr) {
/* 108 */     this.bgDateStr = bgDateStr;
/*     */   }
/*     */   @Transient
/*     */   public String getEdDateStr() {
/* 113 */     return this.edDateStr;
/*     */   }
/*     */ 
/*     */   public void setEdDateStr(String edDateStr) {
/* 117 */     this.edDateStr = edDateStr;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizservice.model.Agency
 * JD-Core Version:    0.6.0
 */
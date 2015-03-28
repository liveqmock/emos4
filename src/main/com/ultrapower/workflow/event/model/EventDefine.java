/*     */ package com.ultrapower.workflow.event.model;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_EVENTDEF")
/*     */ public class EventDefine
/*     */   implements Serializable
/*     */ {
/*     */   private String pid;
/*     */   private String eventdefid;
/*     */   private String baseschema;
/*     */   private String eventtype;
/*     */   private String eventsubject;
/*     */   private String eventaction;
/*     */   private String eventcondition;
/*     */   private String eventconditionid;
/*     */   private String handletype;
/*     */   private String handletypegroupid;
/*     */   private String operationtype;
/*     */   private String operationname;
/*     */   private String duetimefield;
/*     */   private Long createtime;
/*     */   private Long updatetime;
/*     */ 
/*     */   public EventDefine()
/*     */   {
/*     */   }
/*     */ 
/*     */   public EventDefine(String pid)
/*     */   {
/*  45 */     this.pid = pid;
/*     */   }
/*     */ 
/*     */   public EventDefine(String pid, String eventdefid, String baseschema, String eventtype, String eventsubject, String eventaction, String eventcondition, String eventconditionid, String handletype, String handletypegroupid, String operationtype, String operationname, String duetimefield, Long createtime, Long updatetime)
/*     */   {
/*  52 */     this.pid = pid;
/*  53 */     this.eventdefid = eventdefid;
/*  54 */     this.baseschema = baseschema;
/*  55 */     this.eventtype = eventtype;
/*  56 */     this.eventsubject = eventsubject;
/*  57 */     this.eventaction = eventaction;
/*  58 */     this.eventcondition = eventcondition;
/*  59 */     this.eventconditionid = eventconditionid;
/*  60 */     this.handletype = handletype;
/*  61 */     this.handletypegroupid = handletypegroupid;
/*  62 */     this.operationtype = operationtype;
/*  63 */     this.operationname = operationname;
/*  64 */     this.duetimefield = duetimefield;
/*  65 */     this.createtime = createtime;
/*  66 */     this.updatetime = updatetime;
/*     */   }
/*     */ 
/*     */   @Id
/*     */   @Column(name="PID", unique=true, nullable=false, insertable=true, updatable=true, length=15)
/*     */   public String getPid()
/*     */   {
/*  76 */     return this.pid;
/*     */   }
/*     */ 
/*     */   public void setPid(String pid) {
/*  80 */     this.pid = pid;
/*     */   }
/*     */ 
/*     */   @Column(name="EVENTDEFID", unique=false, nullable=true, insertable=true, updatable=true, length=200)
/*     */   public String getEventdefid() {
/*  86 */     return this.eventdefid;
/*     */   }
/*     */ 
/*     */   public void setEventdefid(String eventdefid) {
/*  90 */     this.eventdefid = eventdefid;
/*     */   }
/*     */   @Column(name="BASESCHEMA", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getBaseschema() {
/*  95 */     return this.baseschema;
/*     */   }
/*     */ 
/*     */   public void setBaseschema(String baseschema) {
/*  99 */     this.baseschema = baseschema;
/*     */   }
/*     */ 
/*     */   @Column(name="EVENTTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=20)
/*     */   public String getEventtype() {
/* 105 */     return this.eventtype;
/*     */   }
/*     */ 
/*     */   public void setEventtype(String eventtype) {
/* 109 */     this.eventtype = eventtype;
/*     */   }
/*     */ 
/*     */   @Column(name="EVENTSUBJECT", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getEventsubject() {
/* 115 */     return this.eventsubject;
/*     */   }
/*     */ 
/*     */   public void setEventsubject(String eventsubject) {
/* 119 */     this.eventsubject = eventsubject;
/*     */   }
/*     */ 
/*     */   @Column(name="EVENTACTION", unique=false, nullable=true, insertable=true, updatable=true, length=20)
/*     */   public String getEventaction() {
/* 125 */     return this.eventaction;
/*     */   }
/*     */ 
/*     */   public void setEventaction(String eventaction) {
/* 129 */     this.eventaction = eventaction;
/*     */   }
/*     */ 
/*     */   @Column(name="EVENTCONDITION", unique=false, nullable=true, insertable=true, updatable=true, length=200)
/*     */   public String getEventcondition() {
/* 135 */     return this.eventcondition;
/*     */   }
/*     */ 
/*     */   public void setEventcondition(String eventcondition) {
/* 139 */     this.eventcondition = eventcondition;
/*     */   }
/*     */ 
/*     */   @Column(name="EVENTCONDITIONID", unique=false, nullable=true, insertable=true, updatable=true, length=100)
/*     */   public String getEventconditionid() {
/* 145 */     return this.eventconditionid;
/*     */   }
/*     */ 
/*     */   public void setEventconditionid(String eventconditionid) {
/* 149 */     this.eventconditionid = eventconditionid;
/*     */   }
/*     */ 
/*     */   @Column(name="HANDLETYPE", unique=false, nullable=true, insertable=true, updatable=true, length=20)
/*     */   public String getHandletype() {
/* 155 */     return this.handletype;
/*     */   }
/*     */ 
/*     */   public void setHandletype(String handletype) {
/* 159 */     this.handletype = handletype;
/*     */   }
/*     */ 
/*     */   @Column(name="HANDLETYPEGROUPID", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getHandletypegroupid() {
/* 165 */     return this.handletypegroupid;
/*     */   }
/*     */ 
/*     */   public void setHandletypegroupid(String handletypegroupid) {
/* 169 */     this.handletypegroupid = handletypegroupid;
/*     */   }
/*     */   @Column(name="OPERATIONTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=20)
/*     */   public String getOperationtype() {
/* 174 */     return this.operationtype;
/*     */   }
/*     */ 
/*     */   public void setOperationtype(String operationtype) {
/* 178 */     this.operationtype = operationtype;
/*     */   }
/*     */ 
/*     */   @Column(name="OPERATIONNAME", unique=false, nullable=true, insertable=true, updatable=true, length=200)
/*     */   public String getOperationname() {
/* 184 */     return this.operationname;
/*     */   }
/*     */ 
/*     */   public void setOperationname(String operationname) {
/* 188 */     this.operationname = operationname;
/*     */   }
/*     */ 
/*     */   @Column(name="DUETIMEFIELD", unique=false, nullable=true, insertable=true, updatable=true, length=15)
/*     */   public String getDuetimefield() {
/* 194 */     return this.duetimefield;
/*     */   }
/*     */ 
/*     */   public void setDuetimefield(String duetimefield) {
/* 198 */     this.duetimefield = duetimefield;
/*     */   }
/*     */ 
/*     */   @Column(name="CREATETIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)
/*     */   public Long getCreatetime() {
/* 204 */     return this.createtime;
/*     */   }
/*     */ 
/*     */   public void setCreatetime(Long createtime) {
/* 208 */     this.createtime = createtime;
/*     */   }
/*     */ 
/*     */   @Column(name="UPDATETIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)
/*     */   public Long getUpdatetime() {
/* 214 */     return this.updatetime;
/*     */   }
/*     */ 
/*     */   public void setUpdatetime(Long updatetime) {
/* 218 */     this.updatetime = updatetime;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.event.model.EventDefine
 * JD-Core Version:    0.6.0
 */
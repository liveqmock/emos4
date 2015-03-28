/*     */ package com.ultrapower.workflow.event.model;
/*     */ 
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_EVENTINSTANCE")
/*     */ public class EventInstance
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private String pid;
/*     */   private String eventinstanceid;
/*     */   private String subjectinstanceid;
/*     */   private Long eventcreatetime;
/*     */   private String handlemark;
/*     */   private Long eventstatus;
/*     */   private String eventdefid;
/*     */   private String baseschema;
/*     */   private String eventtype;
/*     */   private String eventsubject;
/*     */   private String eventaction;
/*     */   private String eventcondition;
/*     */   private String eventbaseid;
/*     */   private String handletype;
/*     */   private String handletypegroupid;
/*     */   private String operationtype;
/*     */   private String operationname;
/*     */   private Long duetime;
/*     */   private Long createtime;
/*     */   private Long updatetime;
/*     */   private Long defaulthandlertype;
/*     */   private String defaulthandlerid;
/*     */ 
/*     */   public EventInstance()
/*     */   {
/*  50 */     this.pid = UUIDGenerator.getId();
/*     */   }
/*     */ 
/*     */   public EventInstance(String pid)
/*     */   {
/*  55 */     this.pid = pid;
/*     */   }
/*     */ 
/*     */   public EventInstance(String pid, String eventinstanceid, String subjectinstanceid, Long eventcreatetime, String handlemark, Long eventstatus, String eventdefid, String baseschema, String eventtype, String eventsubject, String eventaction, String eventcondition, String handletype, String handletypegroupid, String operationtype, String operationname, Long duetime, Long createtime, Long updatetime, Long defaulthandlertype, String defaulthandlerid)
/*     */   {
/*  60 */     this.pid = pid;
/*  61 */     this.eventinstanceid = eventinstanceid;
/*  62 */     this.subjectinstanceid = subjectinstanceid;
/*  63 */     this.eventcreatetime = eventcreatetime;
/*  64 */     this.handlemark = handlemark;
/*  65 */     this.eventstatus = eventstatus;
/*  66 */     this.eventdefid = eventdefid;
/*  67 */     this.baseschema = baseschema;
/*  68 */     this.eventtype = eventtype;
/*  69 */     this.eventsubject = eventsubject;
/*  70 */     this.eventaction = eventaction;
/*  71 */     this.eventcondition = eventcondition;
/*  72 */     this.handletype = handletype;
/*  73 */     this.handletypegroupid = handletypegroupid;
/*  74 */     this.operationtype = operationtype;
/*  75 */     this.operationname = operationname;
/*  76 */     this.duetime = duetime;
/*  77 */     this.createtime = createtime;
/*  78 */     this.updatetime = updatetime;
/*  79 */     this.defaulthandlertype = defaulthandlertype;
/*  80 */     this.defaulthandlerid = defaulthandlerid;
/*     */   }
/*     */ 
/*     */   @Id
/*     */   @Column(name="PID", unique=true, nullable=false, insertable=true, updatable=true, length=15)
/*     */   public String getPid()
/*     */   {
/*  90 */     return this.pid;
/*     */   }
/*     */ 
/*     */   public void setPid(String pid) {
/*  94 */     this.pid = pid;
/*     */   }
/*     */ 
/*     */   @Column(name="EVENTINSTANCEID", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getEventinstanceid() {
/* 100 */     return this.eventinstanceid;
/*     */   }
/*     */ 
/*     */   public void setEventinstanceid(String eventinstanceid) {
/* 104 */     this.eventinstanceid = eventinstanceid;
/*     */   }
/*     */ 
/*     */   @Column(name="SUBJECTINSTANCEID", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getSubjectinstanceid() {
/* 110 */     return this.subjectinstanceid;
/*     */   }
/*     */ 
/*     */   public void setSubjectinstanceid(String subjectinstanceid) {
/* 114 */     this.subjectinstanceid = subjectinstanceid;
/*     */   }
/*     */ 
/*     */   @Column(name="EVENTCREATETIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)
/*     */   public Long getEventcreatetime() {
/* 120 */     return this.eventcreatetime;
/*     */   }
/*     */ 
/*     */   public void setEventcreatetime(Long eventcreatetime) {
/* 124 */     this.eventcreatetime = eventcreatetime;
/*     */   }
/*     */ 
/*     */   @Column(name="HANDLEMARK", unique=false, nullable=true, insertable=true, updatable=true, length=200)
/*     */   public String getHandlemark() {
/* 130 */     return this.handlemark;
/*     */   }
/*     */ 
/*     */   public void setHandlemark(String handlemark) {
/* 134 */     this.handlemark = handlemark;
/*     */   }
/*     */ 
/*     */   @Column(name="EVENTSTATUS", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)
/*     */   public Long getEventstatus() {
/* 140 */     return this.eventstatus;
/*     */   }
/*     */ 
/*     */   public void setEventstatus(Long eventstatus) {
/* 144 */     this.eventstatus = eventstatus;
/*     */   }
/*     */ 
/*     */   @Column(name="EVENTDEFID", unique=false, nullable=true, insertable=true, updatable=true, length=15)
/*     */   public String getEventdefid() {
/* 150 */     return this.eventdefid;
/*     */   }
/*     */ 
/*     */   public void setEventdefid(String eventdefid) {
/* 154 */     this.eventdefid = eventdefid;
/*     */   }
/*     */ 
/*     */   @Column(name="BASESCHEMA", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getBaseschema() {
/* 160 */     return this.baseschema;
/*     */   }
/*     */ 
/*     */   public void setBaseschema(String baseschema) {
/* 164 */     this.baseschema = baseschema;
/*     */   }
/*     */ 
/*     */   @Column(name="EVENTTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=20)
/*     */   public String getEventtype() {
/* 170 */     return this.eventtype;
/*     */   }
/*     */ 
/*     */   public void setEventtype(String eventtype) {
/* 174 */     this.eventtype = eventtype;
/*     */   }
/*     */ 
/*     */   @Column(name="EVENTSUBJECT", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getEventsubject() {
/* 180 */     return this.eventsubject;
/*     */   }
/*     */ 
/*     */   public void setEventsubject(String eventsubject) {
/* 184 */     this.eventsubject = eventsubject;
/*     */   }
/*     */ 
/*     */   @Column(name="EVENTACTION", unique=false, nullable=true, insertable=true, updatable=true, length=20)
/*     */   public String getEventaction() {
/* 190 */     return this.eventaction;
/*     */   }
/*     */ 
/*     */   public void setEventaction(String eventaction) {
/* 194 */     this.eventaction = eventaction;
/*     */   }
/*     */ 
/*     */   @Column(name="EVENTCONDITION", unique=false, nullable=true, insertable=true, updatable=true, length=200)
/*     */   public String getEventcondition() {
/* 200 */     return this.eventcondition;
/*     */   }
/*     */ 
/*     */   public void setEventcondition(String eventcondition) {
/* 204 */     this.eventcondition = eventcondition;
/*     */   }
/*     */ 
/*     */   @Column(name="HANDLETYPE", unique=false, nullable=true, insertable=true, updatable=true, length=20)
/*     */   public String getHandletype() {
/* 210 */     return this.handletype;
/*     */   }
/*     */ 
/*     */   public void setHandletype(String handletype) {
/* 214 */     this.handletype = handletype;
/*     */   }
/*     */ 
/*     */   @Column(name="HANDLETYPEGROUPID", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getHandletypegroupid() {
/* 220 */     return this.handletypegroupid;
/*     */   }
/*     */ 
/*     */   public void setHandletypegroupid(String handletypegroupid) {
/* 224 */     this.handletypegroupid = handletypegroupid;
/*     */   }
/*     */ 
/*     */   @Column(name="OPERATIONTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=20)
/*     */   public String getOperationtype() {
/* 230 */     return this.operationtype;
/*     */   }
/*     */ 
/*     */   public void setOperationtype(String operationtype) {
/* 234 */     this.operationtype = operationtype;
/*     */   }
/*     */ 
/*     */   @Column(name="OPERATIONNAME", unique=false, nullable=true, insertable=true, updatable=true, length=200)
/*     */   public String getOperationname() {
/* 240 */     return this.operationname;
/*     */   }
/*     */ 
/*     */   public void setOperationname(String operationname) {
/* 244 */     this.operationname = operationname;
/*     */   }
/*     */ 
/*     */   @Column(name="DUETIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)
/*     */   public Long getDuetime() {
/* 250 */     return this.duetime;
/*     */   }
/*     */ 
/*     */   public void setDuetime(Long duetime) {
/* 254 */     this.duetime = duetime;
/*     */   }
/*     */ 
/*     */   @Column(name="CREATETIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)
/*     */   public Long getCreatetime() {
/* 260 */     return this.createtime;
/*     */   }
/*     */ 
/*     */   public void setCreatetime(Long createtime) {
/* 264 */     this.createtime = createtime;
/*     */   }
/*     */ 
/*     */   @Column(name="UPDATETIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)
/*     */   public Long getUpdatetime() {
/* 270 */     return this.updatetime;
/*     */   }
/*     */ 
/*     */   public void setUpdatetime(Long updatetime) {
/* 274 */     this.updatetime = updatetime;
/*     */   }
/*     */ 
/*     */   @Column(name="DEFAULTHANDLERTYPE", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)
/*     */   public Long getDefaulthandlertype() {
/* 280 */     return this.defaulthandlertype;
/*     */   }
/*     */ 
/*     */   public void setDefaulthandlertype(Long defaulthandlertype) {
/* 284 */     this.defaulthandlertype = defaulthandlertype;
/*     */   }
/*     */ 
/*     */   @Column(name="DEFAULTHANDLERID", unique=false, nullable=true, insertable=true, updatable=true, length=300)
/*     */   public String getDefaulthandlerid() {
/* 290 */     return this.defaulthandlerid;
/*     */   }
/*     */ 
/*     */   public void setDefaulthandlerid(String defaulthandlerid) {
/* 294 */     this.defaulthandlerid = defaulthandlerid;
/*     */   }
/*     */ 
/*     */   public Object clone() throws CloneNotSupportedException
/*     */   {
/* 299 */     return super.clone();
/*     */   }
/*     */ 
/*     */   public String getEventbaseid() {
/* 303 */     return this.eventbaseid;
/*     */   }
/*     */ 
/*     */   public void setEventbaseid(String eventbaseid) {
/* 307 */     this.eventbaseid = eventbaseid;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.event.model.EventInstance
 * JD-Core Version:    0.6.0
 */
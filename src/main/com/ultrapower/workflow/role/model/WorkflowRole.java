/*     */ package com.ultrapower.workflow.role.model;
/*     */ 
/*     */ import com.ultrapower.randomutil.Random15;
/*     */ import com.ultrapower.randomutil.RandomN;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_WorkflowRole", uniqueConstraints={})
/*     */ public class WorkflowRole
/*     */   implements Serializable
/*     */ {
/*     */   private String roleid;
/*     */   private String rolecode;
/*     */   private String rolename;
/*     */   private String baseschema;
/*     */   private String basename;
/*     */   private String baseversion;
/*     */   private String phaseno;
/*     */ 
/*     */   public WorkflowRole()
/*     */   {
/*  36 */     RandomN random = new Random15();
/*  37 */     this.roleid = random.getRandom(System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */   public WorkflowRole(String roleid)
/*     */   {
/*  42 */     this.roleid = roleid;
/*     */   }
/*     */ 
/*     */   public WorkflowRole(String roleid, String rolecode, String rolename, String baseschema, String basename, String baseversion, String phaseno)
/*     */   {
/*  49 */     this.roleid = roleid;
/*  50 */     this.rolecode = rolecode;
/*  51 */     this.rolename = rolename;
/*  52 */     this.baseschema = baseschema;
/*  53 */     this.basename = basename;
/*  54 */     this.baseversion = baseversion;
/*  55 */     this.phaseno = phaseno;
/*     */   }
/*     */ 
/*     */   @Id
/*     */   public String getRoleid() {
/*  61 */     return this.roleid;
/*     */   }
/*     */ 
/*     */   public void setRoleid(String roleid) {
/*  65 */     this.roleid = roleid;
/*     */   }
/*     */   @Column(name="ROLECODE", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getRolecode() {
/*  70 */     return this.rolecode;
/*     */   }
/*     */ 
/*     */   public void setRolecode(String rolecode) {
/*  74 */     this.rolecode = rolecode;
/*     */   }
/*     */   @Column(name="ROLENAME", unique=false, nullable=true, insertable=true, updatable=true, length=200)
/*     */   public String getRolename() {
/*  79 */     return this.rolename;
/*     */   }
/*     */ 
/*     */   public void setRolename(String rolename) {
/*  83 */     this.rolename = rolename;
/*     */   }
/*     */   @Column(name="BASESCHEMA", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getBaseschema() {
/*  88 */     return this.baseschema;
/*     */   }
/*     */ 
/*     */   public void setBaseschema(String baseschema) {
/*  92 */     this.baseschema = baseschema;
/*     */   }
/*     */   @Column(name="BASENAME", unique=false, nullable=true, insertable=true, updatable=true, length=200)
/*     */   public String getBasename() {
/*  97 */     return this.basename;
/*     */   }
/*     */ 
/*     */   public void setBasename(String basename) {
/* 101 */     this.basename = basename;
/*     */   }
/*     */   @Column(name="BASEVERSION", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getBaseversion() {
/* 106 */     return this.baseversion;
/*     */   }
/*     */ 
/*     */   public void setBaseversion(String baseversion) {
/* 110 */     this.baseversion = baseversion;
/*     */   }
/*     */   @Column(name="PHASENO", unique=false, nullable=true, insertable=true, updatable=true, length=15)
/*     */   public String getPhaseno() {
/* 115 */     return this.phaseno;
/*     */   }
/*     */ 
/*     */   public void setPhaseno(String phaseno) {
/* 119 */     this.phaseno = phaseno;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.role.model.WorkflowRole
 * JD-Core Version:    0.6.0
 */
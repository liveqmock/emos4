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
/*     */ @Table(name="BS_T_WF_ROLEUSER")
/*     */ public class RoleUser
/*     */   implements Serializable
/*     */ {
/*     */   private String roleUserId;
/*     */   private String childRoleId;
/*     */   private String roleCode;
/*     */   private String loginName;
/*     */   private String fullName;
/*     */   private String depName;
/*     */   private String depID;
/*     */ 
/*     */   public RoleUser()
/*     */   {
/*  36 */     RandomN random = new Random15();
/*  37 */     this.roleUserId = random.getRandom(System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */   public RoleUser(String roleuserid)
/*     */   {
/*  42 */     this.roleUserId = roleuserid;
/*     */   }
/*     */   @Id
/*     */   public String getRoleUserId() {
/*  47 */     return this.roleUserId;
/*     */   }
/*     */ 
/*     */   public void setRoleUserId(String roleUserId) {
/*  51 */     this.roleUserId = roleUserId;
/*     */   }
/*     */   @Column(name="ROLECODE", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getRoleCode() {
/*  56 */     return this.roleCode;
/*     */   }
/*     */ 
/*     */   public void setRoleCode(String roleCode) {
/*  60 */     this.roleCode = roleCode;
/*     */   }
/*     */   @Column(name="LOGINNAME", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getLoginName() {
/*  65 */     return this.loginName;
/*     */   }
/*     */ 
/*     */   public void setLoginName(String loginName) {
/*  69 */     this.loginName = loginName;
/*     */   }
/*     */   @Column(name="FULLNAME", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getFullName() {
/*  74 */     return this.fullName;
/*     */   }
/*     */ 
/*     */   public void setFullName(String fullName) {
/*  78 */     this.fullName = fullName;
/*     */   }
/*     */   @Column(name="CHILDROLEID", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getChildRoleId() {
/*  83 */     return this.childRoleId;
/*     */   }
/*     */ 
/*     */   public void setChildRoleId(String childRoleId)
/*     */   {
/*  88 */     this.childRoleId = childRoleId;
/*     */   }
/*     */ 
/*     */   public String getDepName()
/*     */   {
/*  93 */     return this.depName;
/*     */   }
/*     */ 
/*     */   public void setDepName(String depName)
/*     */   {
/*  98 */     this.depName = depName;
/*     */   }
/*     */ 
/*     */   public String getDepID()
/*     */   {
/* 103 */     return this.depID;
/*     */   }
/*     */ 
/*     */   public void setDepID(String depID)
/*     */   {
/* 108 */     this.depID = depID;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.role.model.RoleUser
 * JD-Core Version:    0.6.0
 */
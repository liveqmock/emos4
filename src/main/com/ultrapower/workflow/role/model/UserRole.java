/*    */ package com.ultrapower.workflow.role.model;
/*    */ 
/*    */ import com.ultrapower.randomutil.Random15;
/*    */ import com.ultrapower.randomutil.RandomN;
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Id;
/*    */ import javax.persistence.Table;
/*    */ 
/*    */ @Entity
/*    */ @Table(name="BS_T_WF_USERROLE")
/*    */ public class UserRole
/*    */   implements Serializable
/*    */ {
/*    */   private String id;
/*    */   private String childRoleId;
/*    */   private String loginName;
/*    */   private String fullName;
/*    */ 
/*    */   public UserRole()
/*    */   {
/* 33 */     RandomN random = new Random15();
/* 34 */     this.id = random.getRandom(System.currentTimeMillis());
/*    */   }
/*    */   @Column(name="LOGINNAME", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*    */   public String getLoginName() {
/* 39 */     return this.loginName;
/*    */   }
/*    */ 
/*    */   public void setLoginName(String loginName) {
/* 43 */     this.loginName = loginName;
/*    */   }
/*    */   @Column(name="FULLNAME", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*    */   public String getFullName() {
/* 48 */     return this.fullName;
/*    */   }
/*    */ 
/*    */   public void setFullName(String fullName) {
/* 52 */     this.fullName = fullName;
/*    */   }
/*    */   @Column(name="CHILDROLEID", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*    */   public String getChildRoleId() {
/* 57 */     return this.childRoleId;
/*    */   }
/*    */ 
/*    */   public void setChildRoleId(String childRoleId)
/*    */   {
/* 62 */     this.childRoleId = childRoleId;
/*    */   }
/*    */   @Id
/*    */   public String getId() {
/* 67 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(String id)
/*    */   {
/* 72 */     this.id = id;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.role.model.UserRole
 * JD-Core Version:    0.6.0
 */
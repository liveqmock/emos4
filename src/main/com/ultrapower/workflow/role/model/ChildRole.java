/*     */ package com.ultrapower.workflow.role.model;
/*     */ 
/*     */ import com.ultrapower.eoms.ultrasm.model.DepInfo;
/*     */ import com.ultrapower.eoms.ultrasm.model.UserInfo;
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.Transient;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_CHILDROLE", uniqueConstraints={})
/*     */ public class ChildRole
/*     */   implements Serializable
/*     */ {
/*     */   private String childRoleId;
/*     */   private String childRoleName;
/*     */   private String roleCode;
/*     */   private Long matchCount;
/*     */   private String childRoleDesc;
/*     */   private String dimensionValue;
/*     */   private String charge;
/*  36 */   private List<UserInfo> userList = new ArrayList();
/*  37 */   private List<DepInfo> depList = new ArrayList();
/*     */ 
/*     */   public ChildRole()
/*     */   {
/*  43 */     this.childRoleId = UUIDGenerator.getId();
/*     */   }
/*     */ 
/*     */   public ChildRole(String childRoleId)
/*     */   {
/*  48 */     this.childRoleId = childRoleId;
/*     */   }
/*     */   @Id
/*     */   public String getChildRoleId() {
/*  53 */     return this.childRoleId;
/*     */   }
/*     */ 
/*     */   public void setChildRoleId(String childRoleId) {
/*  57 */     this.childRoleId = childRoleId;
/*     */   }
/*     */ 
/*     */   @Column(name="CHILDROLENAME", unique=false, nullable=true, insertable=true, updatable=true, length=200)
/*     */   public String getChildRoleName() {
/*  63 */     return this.childRoleName;
/*     */   }
/*     */ 
/*     */   public void setChildRoleName(String childRoleName) {
/*  67 */     this.childRoleName = childRoleName;
/*     */   }
/*     */   @Column(name="ROLECODE", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*     */   public String getRoleCode() {
/*  72 */     return this.roleCode;
/*     */   }
/*     */ 
/*     */   public void setRoleCode(String roleCode) {
/*  76 */     this.roleCode = roleCode;
/*     */   }
/*     */   @Column(name="MATCHCOUNT", unique=false, nullable=true, insertable=true, updatable=true, precision=22, scale=0)
/*     */   public Long getMatchCount() {
/*  81 */     return this.matchCount;
/*     */   }
/*     */ 
/*     */   public void setMatchCount(Long matchCount) {
/*  85 */     this.matchCount = matchCount;
/*     */   }
/*     */   @Column(name="CHILDROLEDESC", unique=false, nullable=true, insertable=true, updatable=true, length=400)
/*     */   public String getChildRoleDesc() {
/*  90 */     return this.childRoleDesc;
/*     */   }
/*     */ 
/*     */   public void setChildRoleDesc(String childRoleDesc) {
/*  94 */     this.childRoleDesc = childRoleDesc;
/*     */   }
/*     */   @Column(name="DIMENSIONVALUE", unique=false, nullable=true, insertable=true, updatable=true, length=4000)
/*     */   public String getDimensionValue() {
/*  99 */     return this.dimensionValue;
/*     */   }
/*     */ 
/*     */   public void setDimensionValue(String dimensionValue) {
/* 103 */     this.dimensionValue = dimensionValue;
/*     */   }
/*     */ 
/*     */   public String getCharge() {
/* 107 */     return this.charge;
/*     */   }
/*     */ 
/*     */   public void setCharge(String charge) {
/* 111 */     this.charge = charge;
/*     */   }
/*     */   @Transient
/*     */   public List<UserInfo> getUserList() {
/* 116 */     return this.userList;
/*     */   }
/*     */ 
/*     */   public void setUserList(List<UserInfo> userList) {
/* 120 */     this.userList = userList;
/*     */   }
/*     */   @Transient
/*     */   public List<DepInfo> getDepList() {
/* 125 */     return this.depList;
/*     */   }
/*     */ 
/*     */   public void setDepList(List<DepInfo> depList) {
/* 129 */     this.depList = depList;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.role.model.ChildRole
 * JD-Core Version:    0.6.0
 */
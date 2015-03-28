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
/*    */ @Table(name="BS_T_WF_ROLEMATCHDIMENSION", uniqueConstraints={})
/*    */ public class RoleMatchDimension
/*    */   implements Serializable
/*    */ {
/*    */   private String matchid;
/*    */   private String dimensioncode;
/*    */   private String rolecode;
/*    */   private Long orderby;
/*    */   private String baseVersion;
/*    */ 
/*    */   public RoleMatchDimension()
/*    */   {
/* 34 */     RandomN random = new Random15();
/* 35 */     this.matchid = random.getRandom(System.currentTimeMillis());
/*    */   }
/*    */ 
/*    */   public RoleMatchDimension(String matchid)
/*    */   {
/* 40 */     this.matchid = matchid;
/*    */   }
/*    */ 
/*    */   public RoleMatchDimension(String matchid, String dimensioncode, String rolecode, Long orderby)
/*    */   {
/* 46 */     this.matchid = matchid;
/* 47 */     this.dimensioncode = dimensioncode;
/* 48 */     this.rolecode = rolecode;
/* 49 */     this.orderby = orderby;
/*    */   }
/*    */ 
/*    */   @Id
/*    */   public String getMatchid() {
/* 55 */     return this.matchid;
/*    */   }
/*    */ 
/*    */   public void setMatchid(String matchid) {
/* 59 */     this.matchid = matchid;
/*    */   }
/*    */   @Column(name="DIMENSIONCODE", unique=false, nullable=true, insertable=true, updatable=true, length=15)
/*    */   public String getDimensioncode() {
/* 64 */     return this.dimensioncode;
/*    */   }
/*    */ 
/*    */   public void setDimensioncode(String dimensioncode) {
/* 68 */     this.dimensioncode = dimensioncode;
/*    */   }
/*    */   @Column(name="ROLECODE", unique=false, nullable=true, insertable=true, updatable=true, length=50)
/*    */   public String getRolecode() {
/* 73 */     return this.rolecode;
/*    */   }
/*    */ 
/*    */   public void setRolecode(String rolecode) {
/* 77 */     this.rolecode = rolecode;
/*    */   }
/*    */   @Column(name="ORDERBY", unique=false, nullable=true, insertable=true, updatable=true, precision=22, scale=0)
/*    */   public Long getOrderby() {
/* 82 */     return this.orderby;
/*    */   }
/*    */ 
/*    */   public void setOrderby(Long orderby) {
/* 86 */     this.orderby = orderby;
/*    */   }
/*    */ 
/*    */   public String getBaseVersion() {
/* 90 */     return this.baseVersion;
/*    */   }
/*    */ 
/*    */   public void setBaseVersion(String baseVersion) {
/* 94 */     this.baseVersion = baseVersion;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.role.model.RoleMatchDimension
 * JD-Core Version:    0.6.0
 */
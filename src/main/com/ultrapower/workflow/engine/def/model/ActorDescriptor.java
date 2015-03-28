/*    */ package com.ultrapower.workflow.engine.def.model;
/*    */ 
/*    */ public class ActorDescriptor extends AbstractDescriptor
/*    */ {
/*    */   private String roleId;
/*    */   private String roleName;
/*    */   private String actorType;
/*    */   private String rule;
/*    */ 
/*    */   public ActorDescriptor(AbstractDescriptor parent)
/*    */   {
/* 10 */     setParent(parent);
/*    */   }
/*    */ 
/*    */   public String getRoleId() {
/* 14 */     return this.roleId;
/*    */   }
/*    */   public void setRoleId(String roleId) {
/* 17 */     this.roleId = roleId;
/*    */   }
/*    */   public String getRoleName() {
/* 20 */     return this.roleName;
/*    */   }
/*    */   public void setRoleName(String roleName) {
/* 23 */     this.roleName = roleName;
/*    */   }
/*    */   public String getActorType() {
/* 26 */     return this.actorType;
/*    */   }
/*    */   public void setActorType(String actorType) {
/* 29 */     this.actorType = actorType;
/*    */   }
/*    */   public String getRule() {
/* 32 */     return this.rule;
/*    */   }
/*    */   public void setRule(String rule) {
/* 35 */     this.rule = rule;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.def.model.ActorDescriptor
 * JD-Core Version:    0.6.0
 */
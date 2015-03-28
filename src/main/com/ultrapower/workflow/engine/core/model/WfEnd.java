/*    */ package com.ultrapower.workflow.engine.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class WfEnd
/*    */   implements Serializable
/*    */ {
/*    */   private String id;
/*    */   private String statusId;
/*    */   private String statusName;
/*    */ 
/*    */   public String getId()
/*    */   {
/* 10 */     return this.id;
/*    */   }
/*    */   public void setId(String id) {
/* 13 */     this.id = id;
/*    */   }
/*    */   public String getStatusId() {
/* 16 */     return this.statusId;
/*    */   }
/*    */   public void setStatusId(String statusId) {
/* 19 */     this.statusId = statusId;
/*    */   }
/*    */   public String getStatusName() {
/* 22 */     return this.statusName;
/*    */   }
/*    */   public void setStatusName(String statusName) {
/* 25 */     this.statusName = statusName;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.core.model.WfEnd
 * JD-Core Version:    0.6.0
 */
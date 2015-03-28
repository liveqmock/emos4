/*    */ package com.ultrapower.workflow.exception;
/*    */ 
/*    */ public class WorkflowException extends RuntimeException
/*    */ {
/*    */   public WorkflowException(String msg)
/*    */   {
/*  5 */     super(msg);
/*    */   }
/*    */ 
/*    */   public WorkflowException(Throwable cause) {
/*  9 */     super(cause);
/*    */   }
/*    */ 
/*    */   public WorkflowException(String msg, Throwable cause) {
/* 13 */     super(msg, cause);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.exception.WorkflowException
 * JD-Core Version:    0.6.0
 */
/*    */ package com.ultrapower.workflow.exception;
/*    */ 
/*    */ public class TaskException extends WorkflowException
/*    */ {
/*    */   public TaskException(String msg)
/*    */   {
/*  5 */     super(msg);
/*    */   }
/*    */ 
/*    */   public TaskException(Throwable cause) {
/*  9 */     super(cause);
/*    */   }
/*    */ 
/*    */   public TaskException(String msg, Throwable cause) {
/* 13 */     super(msg, cause);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.exception.TaskException
 * JD-Core Version:    0.6.0
 */
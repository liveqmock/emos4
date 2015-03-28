/*    */ package com.ultrapower.workflow.exception;
/*    */ 
/*    */ public class WorkflowEngineException extends WorkflowException
/*    */ {
/*    */   public WorkflowEngineException(String msg)
/*    */   {
/*  5 */     super(msg);
/*    */   }
/*    */ 
/*    */   public WorkflowEngineException(Throwable cause) {
/*  9 */     super(cause);
/*    */   }
/*    */ 
/*    */   public WorkflowEngineException(String msg, Throwable cause) {
/* 13 */     super(msg, cause);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.exception.WorkflowEngineException
 * JD-Core Version:    0.6.0
 */
/*    */ package com.ultrapower.workflow.engine.task.model;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Table;
/*    */ import org.apache.commons.beanutils.BeanUtils;
/*    */ 
/*    */ @Entity
/*    */ @Table(name="BS_T_WF_CURRENTTASK")
/*    */ public class CurrentProcessTask extends ProcessTask<CurrentProcessTask>
/*    */ {
/*    */   private static final long serialVersionUID = 2681127860115247179L;
/*    */ 
/*    */   public HistoryProcessTask toHistory()
/*    */   {
/* 18 */     HistoryProcessTask his = new HistoryProcessTask();
/*    */     try {
/* 20 */       BeanUtils.copyProperties(his, this);
/*    */     } catch (IllegalAccessException e) {
/* 22 */       e.printStackTrace();
/*    */     } catch (InvocationTargetException e) {
/* 24 */       e.printStackTrace();
/*    */     }
/* 26 */     return his;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.task.model.CurrentProcessTask
 * JD-Core Version:    0.6.0
 */
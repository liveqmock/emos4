/*    */ package com.ultrapower.workflow.engine.task.model;
/*    */ 
/*    */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Table;
/*    */ import org.apache.commons.beanutils.BeanUtils;
/*    */ 
/*    */ @Entity
/*    */ @Table(name="BS_T_WF_HISTORYTASK")
/*    */ public class HistoryProcessTask extends ProcessTask<HistoryProcessTask>
/*    */ {
/*    */   private static final long serialVersionUID = -4872709215459323514L;
/*    */ 
/*    */   public CurrentProcessTask toCurrent()
/*    */   {
/* 21 */     CurrentProcessTask cur = new CurrentProcessTask();
/*    */     try {
/* 23 */       BeanUtils.copyProperties(cur, this);
/* 24 */       cur.setId(UUIDGenerator.getId());
/*    */     } catch (IllegalAccessException e) {
/* 26 */       e.printStackTrace();
/*    */     } catch (InvocationTargetException e) {
/* 28 */       e.printStackTrace();
/*    */     }
/* 30 */     return cur;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.task.model.HistoryProcessTask
 * JD-Core Version:    0.6.0
 */
/*    */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*    */ 
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.store.model.Step;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*    */ import java.util.Map;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class AssignInFunction extends AbstractFreeInFunction
/*    */ {
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel)
/*    */   {
/* 26 */     checkUser(acInfo);
/* 27 */     ProcessTask ptask = (ProcessTask)task;
/* 28 */     dpModel.setFlagAssign(Long.valueOf(1L));
/* 29 */     dpModel.setFlagToAuditing(Long.valueOf(1L));
/* 30 */     dpModel.setFlagTurnUp(Long.valueOf(1L));
/* 31 */     dpModel.setFlagTransfer(Long.valueOf(1L));
/* 32 */     dpModel.setFlagCopy(Long.valueOf(1L));
/* 33 */     ptask.setFlagAssign(1L);
/* 34 */     ptask.setFlagToAuditing(1L);
/* 35 */     ptask.setFlagTurnUp(1L);
/* 36 */     ptask.setFlagTransfer(1L);
/* 37 */     ptask.setFlagCopy(1L);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.AssignInFunction
 * JD-Core Version:    0.6.0
 */
/*    */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*    */ 
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.store.model.Step;
/*    */ import com.ultrapower.workflow.engine.task.ITaskManager;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import com.ultrapower.workflow.utils.ThreadUtils;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class FinishOutFunction extends AbstractFreeOutFunction
/*    */ {
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel)
/*    */   {
/* 30 */     String historyStepId = StringUtils.isNotBlank(step.getOrigId()) ? step.getOrigId() : step.getForwardId();
/* 31 */     BaseTask hisTask = this.taskManager.getHistoryTaskByStepId(historyStepId);
/* 32 */     if (hisTask != null) {
/* 33 */       acInfo.setActorType(hisTask.getActorType());
/* 34 */       if ("USER".equals(hisTask.getActorType())) {
/* 35 */         acInfo.setActorId(hisTask.getAssigneeId());
/*    */       } else {
/* 37 */         acInfo.setActorId(hisTask.getAssignGroupId());
/* 38 */         acInfo.setDealType(hisTask.getDealType());
/*    */       }
/* 40 */       Object isAllFinish = ThreadUtils.getIsAllFinish();
/* 41 */       if ((getSendFlag(step)) && (isAllFinish == null)) {
/* 42 */         BaseTask cloneTask = (BaseTask)task.clone();
/* 43 */         cloneTask.setId(null);
/* 44 */         super.sendMsg(userId, acInfo, fromTask, cloneTask, inputs);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.FinishOutFunction
 * JD-Core Version:    0.6.0
 */
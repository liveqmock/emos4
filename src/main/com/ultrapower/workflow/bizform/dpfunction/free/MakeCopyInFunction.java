/*    */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*    */ 
/*    */ import com.ultrapower.eoms.ultrasm.model.UserInfo;
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.core.model.ThreadObj;
/*    */ import com.ultrapower.workflow.engine.store.model.Step;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*    */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*    */ import java.util.Map;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class MakeCopyInFunction extends AbstractFreeInFunction
/*    */ {
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel)
/*    */   {
/* 29 */     UserInfo user = ThreadObj.getUsersInfo(userId);
/* 30 */     String actorName = getActorName(acInfo);
/* 31 */     dpModel.setFlagCopy(Long.valueOf(1L));
/* 32 */     String actionType = acInfo.getActionType();
/* 33 */     String dpDesc = WfEngineUtils.getDpDesc(actionType, inputs, user.getFullname(), actorName);
/* 34 */     dpModel.setDpDesc(dpDesc + WfEngineUtils.getActionDesc(acInfo));
/* 35 */     task.setTaskName(dpModel.getDpDesc());
/* 36 */     ProcessTask ptask = (ProcessTask)task;
/* 37 */     ptask.setFlagCopy(1L);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.MakeCopyInFunction
 * JD-Core Version:    0.6.0
 */
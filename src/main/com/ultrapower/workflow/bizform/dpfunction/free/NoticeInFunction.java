/*    */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.dao.IDao;
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.store.model.Step;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import com.ultrapower.workflow.exception.WorkflowException;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class NoticeInFunction extends AcceptInFunction
/*    */ {
/*    */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
/*    */     throws WorkflowException
/*    */   {
/* 29 */     super.execute(userId, fromTask, task, step, acInfo, inputs);
/*    */ 
/* 31 */     if (this.dpModel != null) {
/* 32 */       String forwardStepId = this.dpModel.getForwardStepId();
/* 33 */       String hql = "from DealProcessModel where stepId = '" + forwardStepId + "'";
/* 34 */       List find = this.bizDao.find(hql, null);
/* 35 */       if ((CollectionUtils.isNotEmpty(find)) && (find.size() > 0))
/*    */       {
/* 37 */         DealProcessModel dp = (DealProcessModel)find.get(0);
/* 38 */         String actorType = dp.getActorType();
/* 39 */         String assigneeId = dp.getAssigneeId();
/* 40 */         String assignee = dp.getAssignee();
/* 41 */         String assignGroupId = dp.getAssignGroupId();
/* 42 */         String assignGroup = dp.getAssignGroup();
/* 43 */         if ("USER".equals(actorType)) {
/* 44 */           acInfo.setActorId(assigneeId);
/* 45 */           acInfo.setActorName(assignee);
/* 46 */           acInfo.setActorType(actorType);
/*    */         } else {
/* 48 */           acInfo.setActorId(assignGroupId);
/* 49 */           acInfo.setActorName(assignGroup);
/* 50 */           acInfo.setActorType(actorType);
/*    */         }
/* 52 */         sendMsg(userId, acInfo, fromTask, task, inputs);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.NoticeInFunction
 * JD-Core Version:    0.6.0
 */
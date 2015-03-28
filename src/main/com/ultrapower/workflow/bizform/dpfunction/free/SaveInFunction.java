/*    */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.dao.IDao;
/*    */ import com.ultrapower.eoms.ultrasm.model.UserInfo;
/*    */ import com.ultrapower.workflow.bizform.BizFormUtil;
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.core.model.ThreadObj;
/*    */ import com.ultrapower.workflow.engine.store.model.Step;
/*    */ import com.ultrapower.workflow.engine.store.model.WfEntry;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*    */ import com.ultrapower.workflow.exception.WorkflowException;
/*    */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class SaveInFunction extends AbstractFreeInFunction
/*    */ {
/*    */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
/*    */     throws WorkflowException
/*    */   {
/* 33 */     if ((step != null) && (task != null) && (StringUtils.isNotBlank(userId))) {
/* 34 */       String actionType = acInfo.getActionType();
/* 35 */       UserInfo user = ThreadObj.getUsersInfo(userId);
/* 36 */       UserInfo actor = null;
/* 37 */       if (acInfo != null) {
/* 38 */         actor = ThreadObj.getUsersInfo(acInfo.getActorId());
/*    */       }
/* 40 */       String entryId = step.getEntryId();
/* 41 */       String baseId = task.getBaseId();
/* 42 */       String baseSchema = task.getBaseSchema();
/* 43 */       WfEntry entry = (WfEntry)this.entryDao.get(entryId);
/* 44 */       String hql = "from DealProcessModel where baseId='" + baseId + "' and baseSchema='" + baseSchema + "' and phaseNo='" + task.getStepCode() + "' and flagEndDuplicated=1";
/* 45 */       List dpList = this.bizDao.find(hql, null);
/* 46 */       DealProcessModel dpModel = BizFormUtil.inDp(userId, task, step, acInfo, inputs, entry, dpList, this.bizDao, null);
/* 47 */       setOwnFlag(userId, fromTask, task, step, acInfo, inputs, dpModel);
/* 48 */       Long flagPredefined = dpModel.getFlagPredefined();
/* 49 */       if (flagPredefined.longValue() != 1L)
/*    */       {
/* 53 */         String msg = WfEngineUtils.getDpDesc(actionType, inputs, user != null ? user.getFullname() : "", "");
/* 54 */         dpModel.setDpDesc(msg);
/* 55 */         task.setTaskName(dpModel.getDpDesc());
/*    */       }
/* 57 */       if (isSubFlow(task)) {
/* 58 */         dpModel.setFlagClose(Long.valueOf(0L));
/* 59 */         dpModel.setFlagCancel(Long.valueOf(0L));
/* 60 */         ProcessTask ptask = (ProcessTask)task;
/* 61 */         ptask.setFlagClose(0L);
/* 62 */         ptask.setFlagCancel(0L);
/*    */       }
/* 64 */       this.bizDao.merge(dpModel);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel)
/*    */   {
/* 72 */     ProcessTask ptask = (ProcessTask)task;
/* 73 */     dpModel.setFlagAssign(Long.valueOf(1L));
/* 74 */     dpModel.setFlagToAuditing(Long.valueOf(1L));
/* 75 */     dpModel.setFlagAssist(Long.valueOf(1L));
/* 76 */     dpModel.setFlagCopy(Long.valueOf(1L));
/* 77 */     dpModel.setFlagClose(Long.valueOf(1L));
/* 78 */     dpModel.setFlagCancel(Long.valueOf(1L));
/* 79 */     ptask.setFlagAssign(1L);
/* 80 */     ptask.setFlagToAuditing(1L);
/* 81 */     ptask.setFlagAssist(1L);
/* 82 */     ptask.setFlagCopy(1L);
/* 83 */     ptask.setFlagClose(1L);
/* 84 */     ptask.setFlagCancel(1L);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.SaveInFunction
 * JD-Core Version:    0.6.0
 */
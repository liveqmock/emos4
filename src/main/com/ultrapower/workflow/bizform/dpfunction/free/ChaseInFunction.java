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
/*    */ public class ChaseInFunction extends AbstractFreeInFunction
/*    */ {
/*    */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
/*    */     throws WorkflowException
/*    */   {
/* 33 */     if ((step != null) && (task != null) && (StringUtils.isNotBlank(userId))) {
/* 34 */       ProcessTask ptask = (ProcessTask)task;
/* 35 */       String entryId = step.getEntryId();
/* 36 */       String baseId = task.getBaseId();
/* 37 */       String baseSchema = task.getBaseSchema();
/* 38 */       String actionType = acInfo.getActionType();
/* 39 */       UserInfo user = ThreadObj.getUsersInfo(userId);
/* 40 */       WfEntry entry = (WfEntry)this.entryDao.get(entryId);
/* 41 */       String hql = "from DealProcessModel where baseId='" + baseId + "' and baseSchema='" + baseSchema + "' and phaseNo='" + task.getStepCode() + "' and flagEndDuplicated=1";
/* 42 */       List dpList = this.bizDao.find(hql, null);
/* 43 */       DealProcessModel dpModel = BizFormUtil.inDp(userId, task, step, acInfo, inputs, entry, dpList, this.bizDao, null);
/* 44 */       setOwnFlag(userId, fromTask, task, step, acInfo, inputs, dpModel);
/* 45 */       String preAssignString = dpModel.getPreAssignString();
/* 46 */       dpModel.setPreAssignString(null);
/* 47 */       ptask.setPreAssignString(null);
/* 48 */       String msg = WfEngineUtils.getDpDesc(actionType, inputs, user != null ? user.getFullname() : "", "");
/* 49 */       dpModel.setDpDesc(msg);
/* 50 */       task.setTaskName(dpModel.getDpDesc());
/* 51 */       if (isSubFlow(task)) {
/* 52 */         dpModel.setFlagClose(Long.valueOf(0L));
/* 53 */         dpModel.setFlagCancel(Long.valueOf(0L));
/* 54 */         ptask.setFlagClose(0L);
/* 55 */         ptask.setFlagCancel(0L);
/*    */       }
/* 57 */       this.bizDao.merge(dpModel);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel)
/*    */   {
/* 65 */     ProcessTask ptask = (ProcessTask)task;
/* 66 */     dpModel.setFlagRecall(Long.valueOf(0L));
/* 67 */     dpModel.setFlagAssign(Long.valueOf(1L));
/* 68 */     dpModel.setFlagToAuditing(Long.valueOf(1L));
/* 69 */     dpModel.setFlagClose(Long.valueOf(1L));
/* 70 */     dpModel.setFlagCancel(Long.valueOf(1L));
/* 71 */     ptask.setFlagRecall(0L);
/* 72 */     ptask.setFlagAssign(1L);
/* 73 */     ptask.setFlagToAuditing(1L);
/* 74 */     ptask.setFlagClose(1L);
/* 75 */     ptask.setFlagCancel(1L);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.ChaseInFunction
 * JD-Core Version:    0.6.0
 */
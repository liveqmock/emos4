/*    */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.dao.IDao;
/*    */ import com.ultrapower.eoms.common.core.util.TimeUtils;
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
/*    */ public class ChaseAllInFunction extends AbstractFreeInFunction
/*    */ {
/*    */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
/*    */     throws WorkflowException
/*    */   {
/* 34 */     if ((step != null) && (task != null) && (StringUtils.isNotBlank(userId))) {
/* 35 */       ProcessTask ptask = (ProcessTask)task;
/* 36 */       String actionType = acInfo.getActionType();
/* 37 */       this.user = ThreadObj.getUsersInfo(userId);
/* 38 */       String actorName = getActorName(acInfo);
/* 39 */       String entryId = step.getEntryId();
/* 40 */       String baseId = task.getBaseId();
/* 41 */       String baseSchema = task.getBaseSchema();
/* 42 */       WfEntry entry = (WfEntry)this.entryDao.get(entryId);
/* 43 */       String hql = "from DealProcessModel where baseId='" + baseId + "' and baseSchema='" + baseSchema + "' and phaseNo='" + task.getStepCode() + "' and flagEndDuplicated=1";
/* 44 */       List dpList = this.bizDao.find(hql, null);
/* 45 */       DealProcessModel dpModel = BizFormUtil.inDp(userId, task, step, acInfo, inputs, entry, dpList, this.bizDao, null);
/* 46 */       setOwnFlag(userId, fromTask, task, step, acInfo, inputs, dpModel);
/* 47 */       String preAssignString = dpModel.getPreAssignString();
/* 48 */       dpModel.setPreAssignString(null);
/* 49 */       ptask.setPreAssignString(null);
/* 50 */       String msg = WfEngineUtils.getDpDesc(actionType, inputs, this.user != null ? this.user.getFullname() : "", actorName);
/* 51 */       dpModel.setDpDesc(msg + WfEngineUtils.getActionDesc(acInfo));
/* 52 */       task.setTaskName(dpModel.getDpDesc());
/* 53 */       if (isSubFlow(task)) {
/* 54 */         dpModel.setFlagClose(Long.valueOf(0L));
/* 55 */         dpModel.setFlagCancel(Long.valueOf(0L));
/* 56 */         ptask.setFlagClose(0L);
/* 57 */         ptask.setFlagCancel(0L);
/*    */       }
/* 59 */       this.bizDao.merge(dpModel);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel)
/*    */   {
/* 67 */     dpModel.setFlagRecall(Long.valueOf(0L));
/* 68 */     dpModel.setFlagAssign(Long.valueOf(1L));
/* 69 */     dpModel.setFlagToAuditing(Long.valueOf(1L));
/* 70 */     dpModel.setFlagClose(Long.valueOf(1L));
/* 71 */     dpModel.setFlagCancel(Long.valueOf(1L));
/* 72 */     ProcessTask ptask = (ProcessTask)task;
/* 73 */     ptask.setFlagRecall(0L);
/* 74 */     ptask.setFlagAssign(1L);
/* 75 */     ptask.setFlagToAuditing(1L);
/* 76 */     ptask.setFlagClose(1L);
/* 77 */     ptask.setFlagCancel(1L);
/*    */ 
/* 79 */     String actionType = acInfo.getActionType();
/*    */ 
/* 81 */     String msg = WfEngineUtils.getDpDesc(actionType, inputs, this.user != null ? this.user.getFullname() : "", "");
/* 82 */     String outProcessState = WfEngineUtils.outProcessState(actionType);
/* 83 */     dpModel.setDpDesc(msg);
/* 84 */     task.setTaskName(dpModel.getDpDesc());
/*    */ 
/* 86 */     String baseId = task.getBaseId();
/* 87 */     String baseSchema = task.getBaseSchema();
/* 88 */     String hql = "update DealProcessModel set flagActive=0,dpDesc='" + msg + "',processStatus='" + outProcessState + "',edDate=" + TimeUtils.getCurrentTime() + "  where baseId='" + baseId + "' and baseSchema='" + baseSchema + "' and flagActive=1  and processId != '" + dpModel.getProcessId() + "'";
/* 89 */     this.bizDao.executeUpdate(hql, null);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.ChaseAllInFunction
 * JD-Core Version:    0.6.0
 */
/*    */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.dao.IDao;
/*    */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*    */ import com.ultrapower.workflow.UltraWorkflow;
/*    */ import com.ultrapower.workflow.bizform.BizFormUtil;
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.store.model.Step;
/*    */ import com.ultrapower.workflow.engine.task.ITaskManager;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*    */ import com.ultrapower.workflow.exception.WorkflowException;
/*    */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*    */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.apache.commons.lang.ArrayUtils;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class ActiveParentInFunction extends AbstractFreeInFunction
/*    */ {
/*    */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
/*    */     throws WorkflowException
/*    */   {
/* 35 */     setOwnFlag(userId, fromTask, task, step, acInfo, inputs, null);
/*    */   }
/*    */ 
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask currTask, Step currStep, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dp)
/*    */   {
/* 42 */     String actionType = acInfo.getActionType();
/* 43 */     String parentEntryId = currStep.getEntryId();
/* 44 */     String parStepId = currStep.getId();
/* 45 */     String parStepCode = currStep.getStepCode();
/* 46 */     Long nowSec = Long.valueOf(TimeUtils.getCurrentTime());
/* 47 */     String baseId = currTask.getBaseId();
/* 48 */     String baseSchema = currTask.getBaseSchema();
/* 49 */     String dphql = "from DealProcessModel where phaseNo='" + parStepCode + "' and flagEndDuplicated=1 and baseId='" + baseId + "' and baseSchema='" + baseSchema + "'";
/* 50 */     List dpList = this.bizDao.find(dphql, null);
/* 51 */     if (CollectionUtils.isNotEmpty(dpList)) {
/* 52 */       DealProcessModel dealProcess = (DealProcessModel)dpList.get(0);
/* 53 */       String preAssignStr = dealProcess.getPreAssignString();
/* 54 */       dealProcess.setFlagEndDuplicated(Long.valueOf(0L));
/* 55 */       String oldTaskId = dealProcess.getTaskId();
/* 56 */       BaseTask oldTask = this.taskManager.getTaskById(oldTaskId);
/* 57 */       if (oldTask != null) {
/* 58 */         ProcessTask ot = (ProcessTask)oldTask;
/* 59 */         ot.setFlagEndDuplicated(0L);
/*    */       }
/* 61 */       DealProcessModel dpModel = dealProcess.clone();
/* 62 */       dpModel.setProcessId(UUIDGenerator.getId());
/* 63 */       dpModel.setFlagDuplicated(Long.valueOf(1L));
/* 64 */       dpModel.setFlagEndDuplicated(Long.valueOf(1L));
/* 65 */       dpModel.setEdDate(Long.valueOf(0L));
/* 66 */       dpModel.setBgDate(Long.valueOf(0L));
/* 67 */       ProcessTask ptask = (ProcessTask)currTask;
/* 68 */       ptask.setFlagDuplicated(dpModel.getFlagDuplicated().longValue());
/* 69 */       ptask.setFlagEndDuplicated(dpModel.getFlagEndDuplicated().longValue());
/*    */ 
/* 71 */       String dealer = dpModel.getDealerId();
/* 72 */       WfEngineUtils.resetDpDealer(dpModel);
/* 73 */       dpModel.setStepId(parStepId);
/* 74 */       dpModel.setFlowId(currStep.getFlowId());
/* 75 */       dpModel.setTaskId(currTask.getId());
/* 76 */       dpModel.setProcessStatus(currTask.getProcessState());
/* 77 */       dpModel.setFlagActive(Long.valueOf(1L));
/* 78 */       dpModel.setEdProcessAction("");
/* 79 */       dpModel.setStDate(nowSec);
/* 80 */       dpModel.setProcessStatus("待处理");
/* 81 */       dpModel.setPreAssignString(null);
/* 82 */       this.bizDao.save(dpModel);
/* 83 */       ptask.setPreAssignString(null);
/* 84 */       ptask.setFlowId(dpModel.getFlowId());
/* 85 */       ptask.setFlagActive(1L);
/* 86 */       ptask.setEdProcessAction("");
/*    */ 
/* 88 */       if ((StringUtils.isNotBlank(preAssignStr)) && ("AUDITINGPASS".equals(actionType)))
/*    */       {
/* 90 */         if (("AUDITINGPASS".equals(actionType)) && 
/* 91 */           (StringUtils.isNotBlank(preAssignStr))) {
/* 92 */           String[] preAsses = preAssignStr.split("#;");
/* 93 */           if (!ArrayUtils.isEmpty(preAsses)) {
/* 94 */             List acInfoList = new ArrayList();
/* 95 */             for (int i = 0; i < preAsses.length; i++) {
/* 96 */               String assignee = preAsses[i];
/* 97 */               if (StringUtils.isNotBlank(assignee)) {
/* 98 */                 BizFormUtil.parseAssignStr(acInfoList, assignee);
/*    */               }
/*    */             }
/* 101 */             String actorType = dpModel.getActorType();
/* 102 */             String actorStr = "";
/* 103 */             if ("USER".equals(actorType))
/* 104 */               actorStr = dpModel.getAssigneeId();
/*    */             else {
/* 106 */               actorStr = dealer;
/*    */             }
/* 108 */             this.ultraWorkflow.doAction(null, null, "FREE", actorStr, "USER", currTask.getEntryId(), null, currTask.getId(), ((ActionInfo)acInfoList.get(0)).getActionType(), true, acInfoList, inputs, null);
/*    */           }
/*    */         }
/*    */       }
/*    */       else
/*    */       {
/* 114 */         String actorType = currTask.getActorType();
/* 115 */         String actorId = null;
/* 116 */         if (("GROUP".equals(actorType)) || ("ROLE".equals(actorType)))
/* 117 */           actorId = currTask.getAssignGroupId();
/* 118 */         else if ("USER".equals(actorType)) {
/* 119 */           actorId = currTask.getAssigneeId();
/*    */         }
/* 121 */         sendMsg(userId, new ActionInfo(actorId, actorType, null, "NEXT", null), fromTask, currTask, inputs);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.ActiveParentInFunction
 * JD-Core Version:    0.6.0
 */
/*    */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.dao.IDao;
/*    */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*    */ import com.ultrapower.eoms.ultrasm.model.UserInfo;
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.core.model.ThreadObj;
/*    */ import com.ultrapower.workflow.engine.def.model.MetaDescriptor;
/*    */ import com.ultrapower.workflow.engine.def.model.StepDescriptor;
/*    */ import com.ultrapower.workflow.engine.store.model.Step;
/*    */ import com.ultrapower.workflow.engine.task.ITaskManager;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*    */ import com.ultrapower.workflow.utils.ApplicationContextUtils;
/*    */ import com.ultrapower.workflow.utils.CommonUtils;
/*    */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*    */ import com.ultrapower.workflow.utils.WfEngineConstants;
/*    */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class SendMoreClassicInFunction
/*    */ {
/* 39 */   private Logger log = LoggerFactory.getLogger(SendMoreClassicInFunction.class);
/*    */ 
/*    */   public void subDeal(String userId, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, String desc, StepDescriptor stDesc)
/*    */   {
/* 43 */     IDao bizDao = (IDao)ApplicationContextUtils.getBean("bizDao");
/* 44 */     ITaskManager taskManager = (ITaskManager)ApplicationContextUtils.getBean("taskManager");
/* 45 */     String actionType = acInfo.getActionType();
/* 46 */     String hql = "from DealProcessModel where taskId='" + task.getId() + "'";
/* 47 */     List find = bizDao.find(hql, null);
/* 48 */     this.log.info("SendMoreClassicInFunction, size=" + find.size() + ",hql=" + hql);
/* 49 */     if (CollectionUtils.isNotEmpty(find)) {
/* 50 */       BaseTask virTask = task.toHistory();
/* 51 */       ProcessTask ptask = (ProcessTask)virTask;
/* 52 */       ptask.setId(UUIDGenerator.getId());
/* 53 */       DealProcessModel dp = (DealProcessModel)find.get(0);
/* 54 */       dp.setFlagActive(Long.valueOf(0L));
/* 55 */       dp.setFlagRecall(Long.valueOf(0L));
/* 56 */       DealProcessModel clone = dp.clone();
/* 57 */       clone.setProcessId(UUIDGenerator.getId());
/* 58 */       clone.setFlowId(step.getFlowId());
/* 59 */       clone.setPhaseNo(step.getStepCode());
/* 60 */       clone.setPrePhaseNo(dp.getPhaseNo());
/* 61 */       clone.setStepId(step.getId());
/* 62 */       clone.setForwardStepId(step.getForwardId());
/* 63 */       clone.setDpDesc(desc);
/* 64 */       clone.setProcessStatus("处理中");
/* 65 */       clone.setFlagDuplicated(Long.valueOf(1L));
/* 66 */       clone.setFlagEndDuplicated(Long.valueOf(0L));
/* 67 */       clone.setTaskId(ptask.getId());
/* 68 */       clone.setTrack(step.getTrack());
/*    */ 
/* 70 */       if (stDesc != null) {
/* 71 */         List metaList = stDesc.getMetaList();
/* 72 */         if (CollectionUtils.isNotEmpty(metaList)) {
/* 73 */           for (int i = 0; i < metaList.size(); i++) {
/* 74 */             MetaDescriptor mtDesc = (MetaDescriptor)metaList.get(i);
/* 75 */             if (mtDesc != null) {
/* 76 */               String name = mtDesc.getName();
/* 77 */               String value = CommonUtils.getString(mtDesc.getValue());
/* 78 */               long longValue = CommonUtils.getLong(value);
/* 79 */               if (WfEngineConstants.CUSTOM_ACTIONS.equals(name)) {
/* 80 */                 clone.setCustomActions(value);
/*    */               }
/*    */ 
/*    */             }
/*    */ 
/*    */           }
/*    */ 
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/* 107 */       ptask.setStepCode(clone.getPhaseNo());
/* 108 */       ptask.setStepId(clone.getStepId());
/* 109 */       ptask.setFlagActive(0L);
/* 110 */       ptask.setFlagDuplicated(1L);
/* 111 */       ptask.setFlagEndDuplicated(0L);
/* 112 */       ptask.setFlagRecall(0L);
/* 113 */       ptask.setFlowId(clone.getFlowId());
/* 114 */       ptask.setTrack(step.getTrack());
/*    */ 
/* 117 */       long now = TimeUtils.getCurrentTime();
/* 118 */       clone.setStDate(Long.valueOf(now));
/* 119 */       clone.setBgDate(Long.valueOf(now));
/* 120 */       clone.setEdDate(Long.valueOf(now));
/*    */ 
/* 122 */       ptask.setCreateTime(now);
/* 123 */       ptask.setAcceptTime(0L);
/* 124 */       ptask.setFinishTime(0L);
/* 125 */       ptask.setPrePhaseNo(clone.getPrePhaseNo());
/*    */ 
/* 127 */       WfEngineUtils.resetDpAssignee(clone);
/* 128 */       WfEngineUtils.resetDpDealer(clone);
/* 129 */       WfEngineUtils.resetTaskAssignee(ptask);
/* 130 */       WfEngineUtils.resetTaskDealer(ptask);
/* 131 */       if ((StringUtils.isNotBlank(userId)) && (StringUtils.isNotBlank(actionType)) && (!"NEXT".equals(actionType))) {
/* 132 */         UserInfo user = ThreadObj.getUsersInfo(userId);
/* 133 */         if (user != null) {
/* 134 */           String userName = user.getFullname();
/* 135 */           if ("AUDIT".equals(actionType))
/*    */           {
/* 137 */             clone.setProcessStatus("审批中");
/* 138 */             ptask.setProcessState("审批中");
/* 139 */           } else if (("ASSIGN".equals(actionType)) || ("MAKECOPY".equals(actionType)) || ("ASSIST".equals(actionType)))
/*    */           {
/* 141 */             clone.setProcessStatus("处理中");
/* 142 */             ptask.setProcessState("处理中");
/*    */           }
/*    */         }
/*    */       }
/*    */ 
/* 147 */       WfEngineUtils.setFlagActive(clone, ptask, actionType);
/* 148 */       bizDao.save(clone);
/* 149 */       taskManager.saveHistoryTask(ptask);
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.SendMoreClassicInFunction
 * JD-Core Version:    0.6.0
 */
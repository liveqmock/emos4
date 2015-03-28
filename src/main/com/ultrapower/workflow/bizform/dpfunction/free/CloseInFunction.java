/*    */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.dao.IDao;
/*    */ import com.ultrapower.eoms.ultrasm.model.UserInfo;
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.core.model.ThreadObj;
/*    */ import com.ultrapower.workflow.engine.store.model.Step;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import com.ultrapower.workflow.exception.WorkflowException;
/*    */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class CloseInFunction extends AbstractFreeInFunction
/*    */ {
/*    */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
/*    */     throws WorkflowException
/*    */   {
/* 31 */     String baseId = task.getBaseId();
/* 32 */     String baseSchema = task.getBaseSchema();
/* 33 */     String updateHql = "update DealProcessModel set flagActive=0 where baseId='" + baseId + "' and baseSchema='" + baseSchema + "' and flagActive=1 and taskId!='" + task.getId() + "'";
/* 34 */     this.bizDao.executeUpdate(updateHql, null);
/* 35 */     String hql = "from DealProcessModel where baseId='" + baseId + "' and baseSchema='" + baseSchema + "' and phaseNo='" + task.getStepCode() + "' and flagEndDuplicated=1";
/* 36 */     List dpList = this.bizDao.find(hql, null);
/* 37 */     DealProcessModel dpModel = (DealProcessModel)dpList.get(0);
/* 38 */     setOwnFlag(userId, fromTask, task, step, acInfo, inputs, dpModel);
/*    */   }
/*    */ 
/*    */   public void setOwnFlag(String userId, BaseTask fromTask, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel)
/*    */   {
/* 45 */     String actionType = acInfo.getActionType();
/* 46 */     UserInfo user = ThreadObj.getUsersInfo(userId);
/* 47 */     String msg = WfEngineUtils.getDpDesc(actionType, inputs, user != null ? user.getFullname() : "", "");
/* 48 */     dpModel.setDpDesc(msg);
/* 49 */     task.setTaskName(dpModel.getDpDesc());
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.CloseInFunction
 * JD-Core Version:    0.6.0
 */
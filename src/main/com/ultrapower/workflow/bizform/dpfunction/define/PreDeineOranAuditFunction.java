/*    */ package com.ultrapower.workflow.bizform.dpfunction.define;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.dao.IDao;
/*    */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*    */ import com.ultrapower.eoms.ultrasm.model.UserInfo;
/*    */ import com.ultrapower.eoms.ultrasm.service.UserManagerService;
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.def.model.AbstractDescriptor;
/*    */ import com.ultrapower.workflow.engine.def.model.ActionDescriptor;
/*    */ import com.ultrapower.workflow.engine.store.model.Step;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import com.ultrapower.workflow.exception.WorkflowException;
/*    */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class PreDeineOranAuditFunction extends AbstractDefOutFunction
/*    */ {
/*    */   public void execute(String userId, BaseTask fromTask, BaseTask task, Step step, ActionDescriptor acDesc, AbstractDescriptor desc, ActionInfo acInfo, Map<String, DataField> inputs)
/*    */     throws WorkflowException
/*    */   {
/* 31 */     if (task != null) {
/* 32 */       UserInfo user = this.userManagerService.getUserByLoginName(userId);
/* 33 */       String stepGroup = task.getStepGroup();
/* 34 */       if (StringUtils.isNotBlank(stepGroup)) {
/* 35 */         String entryId = task.getTopEntryId();
/* 36 */         String taskId = task.getId();
/* 37 */         String processState = WfEngineUtils.outProcessState("TERMINATE");
/* 38 */         long now = TimeUtils.getCurrentTime();
/* 39 */         String dealerId = user.getLoginname();
/* 40 */         String dealer = user.getFullname();
/* 41 */         String edProcessAction = "TERMINATE";
/* 42 */         String hql = "update from DealProcessModel set dealerId='" + dealerId + "', dealer='" + dealer + "', edProcessAction='" + edProcessAction + "', processStatus='" + processState + "', flagActive=0,edDate=" + now + " where  topEntryId = '" + entryId + "' and taskId !='" + taskId + "' and stepGroup='" + stepGroup + "' and flagActive = 1";
/* 43 */         System.out.println("固定会审，审批不通过终止其余同级会审，hql=" + hql);
/* 44 */         this.bizDao.executeUpdate(hql, null);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setOwnFlag(String userId, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dpModel, AbstractDescriptor desc)
/*    */   {
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.define.PreDeineOranAuditFunction
 * JD-Core Version:    0.6.0
 */
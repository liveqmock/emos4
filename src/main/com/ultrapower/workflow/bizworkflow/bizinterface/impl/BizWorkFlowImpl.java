/*     */ package com.ultrapower.workflow.bizworkflow.bizinterface.impl;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.workflow.UltraWorkflow;
/*     */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*     */ import com.ultrapower.workflow.bizworkflow.bizinterface.IBizWorkFlow;
/*     */ import com.ultrapower.workflow.configuration.version.service.IWfVersionService;
/*     */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*     */ import com.ultrapower.workflow.engine.core.model.DataField;
/*     */ import com.ultrapower.workflow.engine.core.model.EngineModel;
/*     */ import com.ultrapower.workflow.engine.core.model.WfAction;
/*     */ import com.ultrapower.workflow.engine.core.model.WfStep;
/*     */ import com.ultrapower.workflow.engine.def.model.WorkflowDescriptor;
/*     */ import com.ultrapower.workflow.exception.WorkflowException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional
/*     */ public class BizWorkFlowImpl
/*     */   implements IBizWorkFlow
/*     */ {
/*  28 */   private static Logger log = LoggerFactory.getLogger(BizWorkFlowImpl.class);
/*     */   protected IDao<DealProcessModel> bizDao;
/*     */   private UltraWorkflow ultraWorkflow;
/*     */   public IWfVersionService verServiceImpl;
/*     */ 
/*     */   public List<WfAction> getAvailableActions(String baseSchema, String taskId, String defName, Map<String, DataField> inputs)
/*     */   {
/*  41 */     return this.ultraWorkflow.getAvailableActions(baseSchema, taskId, defName, inputs);
/*     */   }
/*     */ 
/*     */   public IDao<DealProcessModel> getBizDao() {
/*  45 */     return this.bizDao;
/*     */   }
/*     */ 
/*     */   public void setBizDao(IDao<DealProcessModel> bizDao) {
/*  49 */     this.bizDao = bizDao;
/*     */   }
/*     */ 
/*     */   public UltraWorkflow getUltraWorkflow() {
/*  53 */     return this.ultraWorkflow;
/*     */   }
/*     */ 
/*     */   public void setUltraWorkflow(UltraWorkflow ultraWorkflow) {
/*  57 */     this.ultraWorkflow = ultraWorkflow;
/*     */   }
/*     */ 
/*     */   public EngineModel doAction(String baseSchema, String defName, String entryType, String actorId, String actorType, String entryId, String actionId, String taskId, String actionType, boolean isCurrent, List<ActionInfo> acInfos, Map<String, DataField> inputs, Map<String, String> params)
/*     */   {
/*  64 */     EngineModel wfRtn = null;
/*     */     try {
/*  66 */       wfRtn = this.ultraWorkflow.doAction(baseSchema, defName, entryType, actorId, "USER", entryId, actionId, taskId, actionType, isCurrent, acInfos, inputs, params);
/*     */     } catch (Exception e) {
/*  68 */       log.error(e.getMessage(), e);
/*  69 */       throw new WorkflowException(e.getMessage(), e);
/*     */     }
/*  71 */     return wfRtn;
/*     */   }
/*     */ 
/*     */   public List<WfStep> getNextWfSteps(String entryId, String taskId, String baseSchema, String defName, String stepCode, Map<String, DataField> inputs) throws WorkflowException {
/*  75 */     return this.ultraWorkflow.getNextWfSteps(entryId, taskId, baseSchema, defName, stepCode, inputs);
/*     */   }
/*     */ 
/*     */   public WfStep getCurrentWfStep(String baseSchema, String entryId, String stepCode, Map<String, DataField> inputs) throws WorkflowException {
/*  79 */     return this.ultraWorkflow.getCurrentWfStep(baseSchema, entryId, stepCode, inputs);
/*     */   }
/*     */ 
/*     */   public List<WfStep> getCurrentWfSteps(String entryId, Map<String, DataField> inputs) throws WorkflowException {
/*  83 */     return this.ultraWorkflow.getCurrentWfSteps(entryId, inputs);
/*     */   }
/*     */ 
/*     */   public List<String> getConditions(String baseSchema, String defName) {
/*  87 */     return this.verServiceImpl.getConditions(baseSchema, defName);
/*     */   }
/*     */ 
/*     */   public IWfVersionService getVerServiceImpl() {
/*  91 */     return this.verServiceImpl;
/*     */   }
/*     */ 
/*     */   public void setVerServiceImpl(IWfVersionService verServiceImpl) {
/*  95 */     this.verServiceImpl = verServiceImpl;
/*     */   }
/*     */ 
/*     */   public WorkflowDescriptor getWorkflowDescriptor(String baseSchema, String defName)
/*     */   {
/* 100 */     return this.ultraWorkflow.getWorkflowDescriptor(baseSchema, defName);
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizworkflow.bizinterface.impl.BizWorkFlowImpl
 * JD-Core Version:    0.6.0
 */
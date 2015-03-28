/*     */ package com.ultrapower.workflow.bizform.model;
/*     */ 
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_DEALPROCESSLOG")
/*     */ public class DealProcessLogModel
/*     */   implements Serializable
/*     */ {
/*     */   private String processLogId;
/*     */   private String processId;
/*     */   private String processType;
/*     */   private String phaseNo;
/*     */   private String baseId;
/*     */   private String baseSchema;
/*     */   private String flowId;
/*     */   private String parentBaseId;
/*     */   private String parentFlowId;
/*     */   private String baseBaseId;
/*     */   private String baseFlowId;
/*     */   private String logUserId;
/*     */   private String logUser;
/*     */   private String logUserDepId;
/*     */   private String logUserDep;
/*     */   private String logUserCorpId;
/*     */   private String logUserCorp;
/*     */   private String logUserDnid;
/*     */   private String logUserDn;
/*     */   private String actionName;
/*     */   private String logDesc;
/*     */   private Long logTime;
/*     */   private long baseCreateTime;
/*     */ 
/*     */   public DealProcessLogModel()
/*     */   {
/*  52 */     this.processLogId = UUIDGenerator.getId();
/*     */   }
/*     */ 
/*     */   public DealProcessLogModel(String processType, String processId, String phaseNo, String baseId, String baseSchema, String flowId, String parentBaseId, String parentFlowId, String baseBaseId, String baseFlowId, String logUserId, String logUser, String logUserDepId, String logUserDep, String logUserCorpId, String logUserCorp, String logUserDnid, String logUserDn, String actionName, Long logTime)
/*     */   {
/*  58 */     this.processType = processType;
/*  59 */     this.processId = processId;
/*  60 */     this.phaseNo = phaseNo;
/*  61 */     this.baseId = baseId;
/*  62 */     this.baseSchema = baseSchema;
/*  63 */     this.flowId = flowId;
/*  64 */     this.parentBaseId = parentBaseId;
/*  65 */     this.parentFlowId = parentFlowId;
/*  66 */     this.baseBaseId = baseBaseId;
/*  67 */     this.baseFlowId = baseFlowId;
/*  68 */     this.logUserId = logUserId;
/*  69 */     this.logUser = logUser;
/*  70 */     this.logUserDepId = logUserDepId;
/*  71 */     this.logUserDep = logUserDep;
/*  72 */     this.logUserCorpId = logUserCorpId;
/*  73 */     this.logUserCorp = logUserCorp;
/*  74 */     this.logUserDnid = logUserDnid;
/*  75 */     this.logUserDn = logUserDn;
/*  76 */     this.actionName = actionName;
/*  77 */     this.logTime = logTime;
/*     */   }
/*     */ 
/*     */   @Id
/*     */   public String getProcessLogId()
/*     */   {
/*  85 */     return this.processLogId;
/*     */   }
/*     */ 
/*     */   public void setProcessLogId(String processLogId)
/*     */   {
/*  90 */     this.processLogId = processLogId;
/*     */   }
/*     */ 
/*     */   public String getProcessId()
/*     */   {
/*  95 */     return this.processId;
/*     */   }
/*     */ 
/*     */   public void setProcessId(String processId)
/*     */   {
/* 100 */     this.processId = processId;
/*     */   }
/*     */ 
/*     */   public String getPhaseNo()
/*     */   {
/* 105 */     return this.phaseNo;
/*     */   }
/*     */ 
/*     */   public void setPhaseNo(String phaseNo)
/*     */   {
/* 110 */     this.phaseNo = phaseNo;
/*     */   }
/*     */ 
/*     */   public String getBaseId()
/*     */   {
/* 115 */     return this.baseId;
/*     */   }
/*     */ 
/*     */   public void setBaseId(String baseId)
/*     */   {
/* 120 */     this.baseId = baseId;
/*     */   }
/*     */ 
/*     */   public String getBaseSchema()
/*     */   {
/* 125 */     return this.baseSchema;
/*     */   }
/*     */ 
/*     */   public void setBaseSchema(String baseSchema)
/*     */   {
/* 130 */     this.baseSchema = baseSchema;
/*     */   }
/*     */ 
/*     */   public String getFlowId()
/*     */   {
/* 135 */     return this.flowId;
/*     */   }
/*     */ 
/*     */   public void setFlowId(String flowId)
/*     */   {
/* 140 */     this.flowId = flowId;
/*     */   }
/*     */ 
/*     */   public String getParentBaseId()
/*     */   {
/* 145 */     return this.parentBaseId;
/*     */   }
/*     */ 
/*     */   public void setParentBaseId(String parentBaseId)
/*     */   {
/* 150 */     this.parentBaseId = parentBaseId;
/*     */   }
/*     */ 
/*     */   public String getParentFlowId()
/*     */   {
/* 155 */     return this.parentFlowId;
/*     */   }
/*     */ 
/*     */   public void setParentFlowId(String parentFlowId)
/*     */   {
/* 160 */     this.parentFlowId = parentFlowId;
/*     */   }
/*     */ 
/*     */   public String getBaseBaseId()
/*     */   {
/* 165 */     return this.baseBaseId;
/*     */   }
/*     */ 
/*     */   public void setBaseBaseId(String baseBaseId)
/*     */   {
/* 170 */     this.baseBaseId = baseBaseId;
/*     */   }
/*     */ 
/*     */   public String getBaseFlowId()
/*     */   {
/* 175 */     return this.baseFlowId;
/*     */   }
/*     */ 
/*     */   public void setBaseFlowId(String baseFlowId)
/*     */   {
/* 180 */     this.baseFlowId = baseFlowId;
/*     */   }
/*     */ 
/*     */   public String getLogUserId()
/*     */   {
/* 185 */     return this.logUserId;
/*     */   }
/*     */ 
/*     */   public void setLogUserId(String logUserId)
/*     */   {
/* 190 */     this.logUserId = logUserId;
/*     */   }
/*     */ 
/*     */   public String getLogUser()
/*     */   {
/* 195 */     return this.logUser;
/*     */   }
/*     */ 
/*     */   public void setLogUser(String logUser)
/*     */   {
/* 200 */     this.logUser = logUser;
/*     */   }
/*     */ 
/*     */   public String getLogUserDepId()
/*     */   {
/* 205 */     return this.logUserDepId;
/*     */   }
/*     */ 
/*     */   public void setLogUserDepId(String logUserDepId)
/*     */   {
/* 210 */     this.logUserDepId = logUserDepId;
/*     */   }
/*     */ 
/*     */   public String getLogUserDep()
/*     */   {
/* 215 */     return this.logUserDep;
/*     */   }
/*     */ 
/*     */   public void setLogUserDep(String logUserDep)
/*     */   {
/* 220 */     this.logUserDep = logUserDep;
/*     */   }
/*     */ 
/*     */   public String getLogUserCorpId()
/*     */   {
/* 225 */     return this.logUserCorpId;
/*     */   }
/*     */ 
/*     */   public void setLogUserCorpId(String logUserCorpId)
/*     */   {
/* 230 */     this.logUserCorpId = logUserCorpId;
/*     */   }
/*     */ 
/*     */   public String getLogUserCorp()
/*     */   {
/* 235 */     return this.logUserCorp;
/*     */   }
/*     */ 
/*     */   public void setLogUserCorp(String logUserCorp)
/*     */   {
/* 240 */     this.logUserCorp = logUserCorp;
/*     */   }
/*     */ 
/*     */   public String getLogUserDnid()
/*     */   {
/* 245 */     return this.logUserDnid;
/*     */   }
/*     */ 
/*     */   public void setLogUserDnid(String logUserDnid)
/*     */   {
/* 250 */     this.logUserDnid = logUserDnid;
/*     */   }
/*     */ 
/*     */   public String getLogUserDn()
/*     */   {
/* 255 */     return this.logUserDn;
/*     */   }
/*     */ 
/*     */   public void setLogUserDn(String logUserDn)
/*     */   {
/* 260 */     this.logUserDn = logUserDn;
/*     */   }
/*     */ 
/*     */   public String getActionName()
/*     */   {
/* 265 */     return this.actionName;
/*     */   }
/*     */ 
/*     */   public void setActionName(String actionName)
/*     */   {
/* 270 */     this.actionName = actionName;
/*     */   }
/*     */ 
/*     */   public Long getLogTime()
/*     */   {
/* 275 */     return this.logTime;
/*     */   }
/*     */ 
/*     */   public void setLogTime(Long logTime)
/*     */   {
/* 280 */     this.logTime = logTime;
/*     */   }
/*     */ 
/*     */   public String getProcessType()
/*     */   {
/* 285 */     return this.processType;
/*     */   }
/*     */ 
/*     */   public void setProcessType(String processType)
/*     */   {
/* 290 */     this.processType = processType;
/*     */   }
/*     */ 
/*     */   public long getBaseCreateTime() {
/* 294 */     return this.baseCreateTime;
/*     */   }
/*     */ 
/*     */   public void setBaseCreateTime(long baseCreateTime) {
/* 298 */     this.baseCreateTime = baseCreateTime;
/*     */   }
/*     */ 
/*     */   public String getLogDesc() {
/* 302 */     return this.logDesc;
/*     */   }
/*     */ 
/*     */   public void setLogDesc(String logDesc) {
/* 306 */     this.logDesc = logDesc;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.model.DealProcessLogModel
 * JD-Core Version:    0.6.0
 */
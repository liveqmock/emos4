/*     */ package com.ultrapower.workflow.bizservice.impl;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.eoms.ultrasm.model.UserInfo;
/*     */ import com.ultrapower.eoms.ultrasm.service.DealGroupService;
/*     */ import com.ultrapower.eoms.ultrasm.service.UserManagerService;
/*     */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*     */ import com.ultrapower.workflow.bizservice.AgencyService;
/*     */ import com.ultrapower.workflow.bizservice.BizCheck;
/*     */ import com.ultrapower.workflow.bizservice.model.Agency;
/*     */ import com.ultrapower.workflow.engine.core.model.ThreadObj;
/*     */ import com.ultrapower.workflow.engine.task.ITaskManager;
/*     */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*     */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*     */ import com.ultrapower.workflow.exception.WorkflowException;
/*     */ import com.ultrapower.workflow.role.service.IRoleService;
/*     */ import com.ultrapower.workflow.utils.WfEngineConstants;
/*     */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*     */ import java.rmi.RemoteException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class BizCheckImpl
/*     */   implements BizCheck
/*     */ {
/*  31 */   private static Logger log = LoggerFactory.getLogger(BizCheckImpl.class);
/*     */   protected UserManagerService userManagerService;
/*     */   private IDao<DealProcessModel> bizDao;
/*     */   private AgencyService agencyService;
/*     */   private IRoleService roleService;
/*     */   private DealGroupService dealGroupService;
/*     */   protected ITaskManager taskManager;
/*     */ 
/*     */   public DealProcessModel checkPermission(String baseid, String baseSchema, String user)
/*     */     throws WorkflowException
/*     */   {
/*  40 */     log.info("验证用户是否有权限操作工单,baseid=" + baseid + ",baseschema=" + baseSchema + ",user=" + user);
/*  41 */     DealProcessModel processModel = null;
/*  42 */     DealProcessModel[] processModels = new DealProcessModel[13];
/*  43 */     String groupIds = null;
/*  44 */     if (StringUtils.isNotBlank(user)) {
/*  45 */       UserInfo userInfo = ThreadObj.getUsersInfo(user);
/*  46 */       groupIds = userInfo != null ? userInfo.getGroupid() : null;
/*  47 */       String GROUP = "GROUP";
/*  48 */       String ROLE = "ROLE";
/*  49 */       String USER = "USER";
/*  50 */       String DEAL = "DEAL";
/*  51 */       String ASSIST = "ASSIST";
/*  52 */       String COPY = "COPY";
/*  53 */       String AUDITING = "AUDITING";
/*  54 */       String ONLY = "ONLY";
/*  55 */       String SHARE = "SHARE";
/*  56 */       String chase = WfEngineUtils.getActionName("CHASE");
/*  57 */       List proxys = getCurrentProxy(user);
/*  58 */       String hql = "from DealProcessModel where baseId=? and baseSchema=? order by stDate desc, processId desc";
/*  59 */       List list = this.bizDao.find(hql, new Object[] { baseid, baseSchema });
/*  60 */       if (CollectionUtils.isNotEmpty(list)) {
/*  61 */         for (int i = 0; i < list.size(); i++) {
/*  62 */           DealProcessModel dp = (DealProcessModel)list.get(i);
/*  63 */           long flagActive = dp.getFlagActive().longValue();
/*  64 */           String assignee = dp.getAssigneeId();
/*  65 */           String assigneeGrpId = dp.getAssignGroupId();
/*  66 */           String dealer = dp.getDealerId();
/*  67 */           String processType = dp.getProcessType();
/*  68 */           String actorType = dp.getActorType();
/*  69 */           String dealType = dp.getDealType();
/*  70 */           String edProcessAction = dp.getEdProcessAction();
/*  71 */           String taskId = dp.getTaskId();
/*  72 */           long stDate = dp.getStDate().longValue();
/*  73 */           long bgDate = dp.getBgDate().longValue();
/*  74 */           boolean gtBgDate = bgDate > 0L;
/*  75 */           boolean eqBgDate = bgDate == 0L;
/*  76 */           boolean isFlagActive = 1L == flagActive;
/*  77 */           boolean isFlagSuspend = WfEngineConstants.FLAGACTIVE_SUSPEND == flagActive;
/*  78 */           boolean isBelongGrp = (StringUtils.isNotBlank(assigneeGrpId)) && (StringUtils.isNotBlank(groupIds)) && (groupIds.indexOf(assigneeGrpId) >= 0);
/*  79 */           boolean isBelongRole = (StringUtils.isNotBlank(assigneeGrpId)) && (this.roleService.isBelongChildRole(user, assigneeGrpId));
/*  80 */           boolean grpTypeAndIsBelongGrp = (isBelongGrp) && (GROUP.equals(actorType));
/*  81 */           boolean roleTypeAndIsBelongRole = (isBelongRole) && (ROLE.equals(actorType));
/*  82 */           boolean isFlagActiveAndAudit = (isFlagActive) && (AUDITING.equals(processType));
/*  83 */           boolean isFlagActiveAndDeal = (isFlagActive) && ((DEAL.equals(processType)) || (ASSIST.equals(processType)) || (COPY.equals(processType)));
/*     */ 
/*  86 */           if ((StringUtils.isNotBlank(edProcessAction)) && (edProcessAction.equals(chase)))
/*     */           {
/*     */             continue;
/*     */           }
/*  90 */           if ((USER.equals(actorType)) && (isFlagActiveAndAudit) && ((user.equals(dealer)) || (isInDealGroup(user, dealer, taskId))))
/*     */           {
/*  93 */             setString(processModels, 0, dp);
/*  94 */             break;
/*  95 */           }if ((user.equals(dealer)) && ((GROUP.equals(actorType)) || (ROLE.equals(actorType))) && (ONLY.equals(dealType)) && (isFlagActiveAndAudit))
/*     */           {
/*  98 */             setString(processModels, 1, dp);
/*  99 */             break;
/* 100 */           }if (((grpTypeAndIsBelongGrp) || (roleTypeAndIsBelongRole)) && (SHARE.equals(dealType)) && (gtBgDate) && (isFlagActiveAndAudit))
/*     */           {
/* 103 */             setString(processModels, 2, dp);
/* 104 */             break;
/* 105 */           }if ((USER.equals(actorType)) && (eqBgDate) && (isFlagActiveAndAudit) && ((user.equals(assignee)) || (proxys.contains(assignee)) || (isInDealGroup(user, assignee, taskId))))
/*     */           {
/* 108 */             setString(processModels, 3, dp);
/* 109 */             break;
/* 110 */           }if (((grpTypeAndIsBelongGrp) || (roleTypeAndIsBelongRole)) && (eqBgDate) && (isFlagActiveAndAudit))
/*     */           {
/* 113 */             setString(processModels, 4, dp);
/* 114 */             break;
/* 115 */           }if ((USER.equals(actorType)) && (isFlagActiveAndDeal) && ((user.equals(dealer)) || (isInDealGroup(user, dealer, taskId))))
/*     */           {
/* 118 */             setString(processModels, 5, dp);
/* 119 */             break;
/* 120 */           }if (((GROUP.equals(actorType)) || (ROLE.equals(actorType))) && (user.equals(dealer)) && (ONLY.equals(dealType)) && (isFlagActiveAndDeal))
/*     */           {
/* 123 */             setString(processModels, 6, dp);
/* 124 */             break;
/* 125 */           }if (((grpTypeAndIsBelongGrp) || (roleTypeAndIsBelongRole)) && (SHARE.equals(dealType)) && (gtBgDate) && (isFlagActiveAndDeal))
/*     */           {
/* 128 */             setString(processModels, 7, dp);
/* 129 */             break;
/* 130 */           }if ((USER.equals(actorType)) && (eqBgDate) && (isFlagActiveAndDeal) && ((user.equals(assignee)) || (proxys.contains(assignee)) || (isInDealGroup(user, assignee, taskId))))
/*     */           {
/* 133 */             setString(processModels, 8, dp);
/* 134 */             break;
/* 135 */           }if (((grpTypeAndIsBelongGrp) || (roleTypeAndIsBelongRole)) && (eqBgDate) && (isFlagActiveAndDeal))
/*     */           {
/* 138 */             setString(processModels, 9, dp);
/* 139 */             break;
/* 140 */           }if ((isFlagSuspend) && (user.equals(dealer))) {
/* 141 */             setString(processModels, 10, dp);
/* 142 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 147 */     for (int i = 0; i < processModels.length; i++) {
/* 148 */       if (processModels[i] != null) {
/* 149 */         processModel = processModels[i];
/* 150 */         break;
/*     */       }
/*     */     }
/* 153 */     if (processModel == null) {
/* 154 */       processModel = chasePriviCheck(baseid, baseSchema, user, processModels, "", 11);
/*     */     }
/* 156 */     if (processModel == null) {
/* 157 */       processModel = chasePriviCheck(baseid, baseSchema, user, processModels, groupIds, 12);
/*     */     }
/* 159 */     if (processModel != null)
/* 160 */       log.info("匹配到的processIdStr=" + processModel.getDealTypeStr() + ":" + processModel.getProcessId());
/*     */     else {
/* 162 */       log.info("没有匹配的processId！");
/*     */     }
/* 164 */     return processModel;
/*     */   }
/*     */ 
/*     */   public ProcessTask checkTask(String baseid, String baseSchema, String user) throws WorkflowException {
/* 168 */     log.info("验证用户是否有权限操作工单,baseid=" + baseid + ",baseschema=" + baseSchema + ",user=" + user);
/* 169 */     ProcessTask rtnTask = null;
/* 170 */     ProcessTask[] ptasks = new ProcessTask[13];
/* 171 */     String groupIds = null;
/* 172 */     if (StringUtils.isNotBlank(user)) {
/* 173 */       UserInfo userInfo = ThreadObj.getUsersInfo(user);
/* 174 */       groupIds = userInfo != null ? userInfo.getGroupid() : null;
/* 175 */       String GROUP = "GROUP";
/* 176 */       String ROLE = "ROLE";
/* 177 */       String USER = "USER";
/* 178 */       String DEAL = "DEAL";
/* 179 */       String ASSIST = "ASSIST";
/* 180 */       String COPY = "COPY";
/* 181 */       String AUDITING = "AUDITING";
/* 182 */       String ONLY = "ONLY";
/* 183 */       String SHARE = "SHARE";
/* 184 */       String chase = WfEngineUtils.getActionName("CHASE");
/* 185 */       List proxys = getCurrentProxy(user);
/* 186 */       String hql = "from CurrentProcessTask where baseId='" + baseid + "' and baseSchema='" + baseSchema + "' order by createTime desc, id desc";
/* 187 */       List list = this.taskManager.queryCurrentTask(hql);
/* 188 */       if (CollectionUtils.isNotEmpty(list)) {
/* 189 */         for (int i = 0; i < list.size(); i++) {
/* 190 */           ProcessTask ptask = (ProcessTask)list.get(i);
/* 191 */           long flagActive = ptask.getFlagActive();
/* 192 */           String assignee = ptask.getAssigneeId();
/* 193 */           String assigneeGrpId = ptask.getAssignGroupId();
/* 194 */           String dealer = ptask.getDealerId();
/* 195 */           String processType = ptask.getProcessType();
/* 196 */           String actorType = ptask.getActorType();
/* 197 */           String dealType = ptask.getDealType();
/* 198 */           String edProcessAction = ptask.getEdProcessAction();
/* 199 */           String taskId = ptask.getId();
/* 200 */           long stDate = ptask.getCreateTime();
/* 201 */           long bgDate = ptask.getAcceptTime();
/* 202 */           boolean gtBgDate = bgDate > 0L;
/* 203 */           boolean eqBgDate = bgDate == 0L;
/* 204 */           boolean isFlagActive = 1L == flagActive;
/* 205 */           boolean isFlagSuspend = WfEngineConstants.FLAGACTIVE_SUSPEND == flagActive;
/* 206 */           boolean isBelongGrp = (StringUtils.isNotBlank(assigneeGrpId)) && (StringUtils.isNotBlank(groupIds)) && (groupIds.indexOf(assigneeGrpId) >= 0);
/* 207 */           boolean isBelongRole = (StringUtils.isNotBlank(assigneeGrpId)) && (this.roleService.isBelongChildRole(user, assigneeGrpId));
/* 208 */           boolean grpTypeAndIsBelongGrp = (isBelongGrp) && (GROUP.equals(actorType));
/* 209 */           boolean roleTypeAndIsBelongRole = (isBelongRole) && (ROLE.equals(actorType));
/* 210 */           boolean isFlagActiveAndAudit = (isFlagActive) && (AUDITING.equals(processType));
/* 211 */           boolean isFlagActiveAndDeal = (isFlagActive) && ((DEAL.equals(processType)) || (ASSIST.equals(processType)) || (COPY.equals(processType)));
/*     */ 
/* 214 */           if ((StringUtils.isNotBlank(edProcessAction)) && (edProcessAction.equals(chase)))
/*     */           {
/*     */             continue;
/*     */           }
/* 218 */           if ((USER.equals(actorType)) && (isFlagActiveAndAudit) && ((user.equals(dealer)) || (isInDealGroup(user, dealer, taskId))))
/*     */           {
/* 221 */             setString(ptasks, 0, ptask);
/* 222 */             break;
/* 223 */           }if ((user.equals(dealer)) && (GROUP.equals(actorType)) && (ONLY.equals(dealType)) && (isFlagActiveAndAudit))
/*     */           {
/* 226 */             setString(ptasks, 1, ptask);
/* 227 */             break;
/* 228 */           }if (((grpTypeAndIsBelongGrp) || (roleTypeAndIsBelongRole)) && (SHARE.equals(dealType)) && (gtBgDate) && (isFlagActiveAndAudit))
/*     */           {
/* 231 */             setString(ptasks, 2, ptask);
/* 232 */             break;
/* 233 */           }if ((USER.equals(actorType)) && (eqBgDate) && (isFlagActiveAndAudit) && ((user.equals(assignee)) || (proxys.contains(assignee)) || (isInDealGroup(user, assignee, taskId))))
/*     */           {
/* 236 */             setString(ptasks, 3, ptask);
/* 237 */             break;
/* 238 */           }if (((grpTypeAndIsBelongGrp) || (roleTypeAndIsBelongRole)) && (eqBgDate) && (isFlagActiveAndAudit))
/*     */           {
/* 241 */             setString(ptasks, 4, ptask);
/* 242 */             break;
/* 243 */           }if ((USER.equals(actorType)) && (isFlagActiveAndDeal) && ((user.equals(dealer)) || (isInDealGroup(user, dealer, taskId))))
/*     */           {
/* 246 */             setString(ptasks, 5, ptask);
/* 247 */             break;
/* 248 */           }if ((GROUP.equals(actorType)) && (user.equals(dealer)) && (ONLY.equals(dealType)) && (isFlagActiveAndDeal))
/*     */           {
/* 251 */             setString(ptasks, 6, ptask);
/* 252 */             break;
/* 253 */           }if (((grpTypeAndIsBelongGrp) || (roleTypeAndIsBelongRole)) && (SHARE.equals(dealType)) && (gtBgDate) && (isFlagActiveAndDeal))
/*     */           {
/* 256 */             setString(ptasks, 7, ptask);
/* 257 */             break;
/* 258 */           }if ((USER.equals(actorType)) && (eqBgDate) && (isFlagActiveAndDeal) && ((user.equals(assignee)) || (proxys.contains(assignee)) || (isInDealGroup(user, assignee, taskId))))
/*     */           {
/* 261 */             setString(ptasks, 8, ptask);
/* 262 */             break;
/* 263 */           }if (((grpTypeAndIsBelongGrp) || (roleTypeAndIsBelongRole)) && (eqBgDate) && (isFlagActiveAndDeal))
/*     */           {
/* 266 */             setString(ptasks, 9, ptask);
/* 267 */             break;
/* 268 */           }if ((isFlagSuspend) && (user.equals(dealer))) {
/* 269 */             setString(ptasks, 10, ptask);
/* 270 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 275 */     for (int i = 0; i < ptasks.length; i++) {
/* 276 */       if (ptasks[i] != null) {
/* 277 */         rtnTask = ptasks[i];
/* 278 */         break;
/*     */       }
/*     */     }
/* 281 */     if (rtnTask == null) {
/* 282 */       rtnTask = chasePriviCheck(baseid, baseSchema, user, ptasks, "", 11);
/*     */     }
/* 284 */     if (rtnTask == null) {
/* 285 */       rtnTask = chasePriviCheck(baseid, baseSchema, user, ptasks, groupIds, 12);
/*     */     }
/* 287 */     if (rtnTask != null)
/* 288 */       log.info("匹配到的taskIdStr=" + rtnTask.getDealTypeStr() + ":" + rtnTask.getId() + ",flagActive=" + rtnTask.getFlagActive());
/*     */     else {
/* 290 */       log.info("没有匹配的taskId！");
/*     */     }
/* 292 */     return rtnTask;
/*     */   }
/*     */ 
/*     */   private boolean isInDealGroup(String user, String acotrOrDealer, String taskId)
/*     */   {
/* 302 */     boolean flag = false;
/* 303 */     if ((StringUtils.isNotBlank(user)) && (StringUtils.isNotBlank(acotrOrDealer))) {
/* 304 */       String entryState = null;
/* 305 */       BaseTask task = this.taskManager.getTaskById(taskId);
/* 306 */       if (task != null) {
/* 307 */         entryState = task.getEntryState();
/*     */       }
/* 309 */       List dealGroupUsers = this.dealGroupService.getDealGroupUsers(user, entryState);
/* 310 */       if (CollectionUtils.isNotEmpty(dealGroupUsers)) {
/* 311 */         for (int i = 0; i < dealGroupUsers.size(); i++) {
/* 312 */           UserInfo userInfo = (UserInfo)dealGroupUsers.get(i);
/* 313 */           if ((userInfo != null) && (userInfo.getLoginname().equals(acotrOrDealer))) {
/* 314 */             flag = true;
/* 315 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 320 */     log.info("当前用户：" + user + "，目标用户：" + acotrOrDealer + "，是否同组关单：" + flag);
/* 321 */     return flag;
/*     */   }
/*     */ 
/*     */   private String conDealGroup(String user) {
/* 325 */     String con = user;
/* 326 */     List dealGroupUsers = this.dealGroupService.getDealGroupUsers(user, "");
/* 327 */     if (CollectionUtils.isNotEmpty(dealGroupUsers)) {
/* 328 */       for (int i = 0; i < dealGroupUsers.size(); i++) {
/* 329 */         UserInfo userInfo = (UserInfo)dealGroupUsers.get(i);
/* 330 */         String loginname = userInfo.getLoginname();
/* 331 */         con = con + "','" + loginname;
/*     */       }
/*     */     }
/* 334 */     return con;
/*     */   }
/*     */ 
/*     */   private DealProcessModel chasePriviCheck(String baseid, String baseSchema, String user, DealProcessModel[] processIds, String groupIds, int position)
/*     */   {
/* 339 */     DealProcessModel rtn = null;
/* 340 */     String con = " assignGroupId in('" + StringUtils.join(groupIds.split(","), "','") + "')";
/* 341 */     String hql = "from DealProcessModel where flagAssignType !='抄送' and baseId=? and baseSchema=? and flagActive<>1 and (" + con + ") and edProcessAction <> '" + WfEngineUtils.getActionName("CHASE") + "' order by stDate desc, processId desc";
/* 342 */     List list = this.bizDao.find(hql, new Object[] { baseid, baseSchema });
/* 343 */     if (CollectionUtils.isNotEmpty(list)) {
/* 344 */       for (int i = 0; i < list.size(); i++) {
/* 345 */         DealProcessModel dpModel = (DealProcessModel)list.get(i);
/* 346 */         String prePhaseNo = dpModel.getPrePhaseNo();
/* 347 */         String parentFlowId = dpModel.getParentFlowId();
/* 348 */         if (("BEGIN".equals(prePhaseNo)) && (StringUtils.isNotBlank(parentFlowId))) {
/*     */           continue;
/*     */         }
/* 351 */         chasePriviCheck(dpModel);
/* 352 */         processIds[position] = dpModel;
/* 353 */         rtn = dpModel;
/* 354 */         break;
/*     */       }
/*     */     }
/* 357 */     return rtn;
/*     */   }
/*     */ 
/*     */   public void chasePriviCheck(DealProcessModel dp) {
/* 361 */     if (dp != null) {
/* 362 */       String baseid = dp.getBaseId();
/* 363 */       String baseSchema = dp.getBaseSchema();
/* 364 */       String phaseNo = dp.getPhaseNo();
/* 365 */       String hql = "from DealProcessModel where baseId=? and baseSchema=? and flagActive=1 and prePhaseNo=? order by stDate desc, processId desc";
/* 366 */       List list = this.bizDao.find(hql, new Object[] { baseid, baseSchema, phaseNo });
/* 367 */       if (CollectionUtils.isNotEmpty(list)) {
/* 368 */         for (int i = 0; i < list.size(); i++) {
/* 369 */           DealProcessModel tmp = (DealProcessModel)list.get(i);
/* 370 */           if (tmp.getBgDate().longValue() == 0L) {
/* 371 */             dp.setDealTypeStr("21");
/* 372 */             break;
/*     */           }
/* 374 */           dp.setDealTypeStr("22");
/*     */         }
/*     */       }
/*     */       else
/* 378 */         dp.setDealTypeStr("22");
/*     */     }
/*     */   }
/*     */ 
/*     */   private ProcessTask chasePriviCheck(String baseid, String baseSchema, String user, ProcessTask[] taskIds, String groupIds, int position)
/*     */   {
/* 385 */     ProcessTask rtn = null;
/* 386 */     String con = " assignGroupId in('" + StringUtils.join(groupIds.split(","), "','") + "')";
/* 387 */     String hql = "from HistoryProcessTask where flagAssignType !='抄送' and baseId=? and baseSchema=? and flagActive<>1 and (" + con + ") and edProcessAction <> '" + WfEngineUtils.getActionName("CHASE") + "' order by createTime desc, id desc";
/* 388 */     List list = this.bizDao.find(hql, new Object[] { baseid, baseSchema });
/* 389 */     if (CollectionUtils.isNotEmpty(list)) {
/* 390 */       for (int i = 0; i < list.size(); i++) {
/* 391 */         ProcessTask ptask = (ProcessTask)list.get(i);
/* 392 */         String prePhaseNo = ptask.getPrePhaseNo();
/* 393 */         String parentFlowId = ptask.getParentFlowId();
/* 394 */         if (("BEGIN".equals(prePhaseNo)) && (StringUtils.isNotBlank(parentFlowId))) {
/*     */           continue;
/*     */         }
/* 397 */         chasePriviCheck(ptask);
/* 398 */         taskIds[position] = ptask;
/* 399 */         rtn = ptask;
/* 400 */         break;
/*     */       }
/*     */     }
/* 403 */     return rtn;
/*     */   }
/*     */ 
/*     */   public void chasePriviCheck(ProcessTask ptask) {
/* 407 */     if (ptask != null) {
/* 408 */       String baseid = ptask.getBaseId();
/* 409 */       String baseSchema = ptask.getBaseSchema();
/* 410 */       String phaseNo = ptask.getStepCode();
/* 411 */       String hql = "from CurrentProcessTask where baseId=? and baseSchema=? and flagActive=1 and prePhaseNo=? order by createTime desc, id desc";
/* 412 */       List list = this.bizDao.find(hql, new Object[] { baseid, baseSchema, phaseNo });
/* 413 */       if (CollectionUtils.isNotEmpty(list)) {
/* 414 */         for (int i = 0; i < list.size(); i++) {
/* 415 */           ProcessTask tmp = (ProcessTask)list.get(i);
/* 416 */           if (tmp.getAcceptTime() == 0L) {
/* 417 */             ptask.setDealTypeStr("21");
/* 418 */             break;
/*     */           }
/* 420 */           ptask.setDealTypeStr("22");
/*     */         }
/*     */       }
/*     */       else
/* 424 */         ptask.setDealTypeStr("22");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setString(DealProcessModel[] processIds, int index, DealProcessModel dp)
/*     */   {
/* 430 */     dp.setDealTypeStr("11");
/* 431 */     processIds[index] = dp;
/*     */   }
/*     */ 
/*     */   private void setString(ProcessTask[] taskIds, int index, ProcessTask task) {
/* 435 */     task.setDealTypeStr("11");
/* 436 */     taskIds[index] = task;
/*     */   }
/*     */ 
/*     */   public List<String> getCurrentProxy(String user)
/*     */   {
/* 445 */     long now = System.currentTimeMillis() / 1000L;
/* 446 */     List proxys = new ArrayList();
/* 447 */     List agencys = this.agencyService.getAll();
/* 448 */     if (CollectionUtils.isNotEmpty(agencys)) {
/* 449 */       for (int i = 0; i < agencys.size(); i++) {
/* 450 */         Agency agency = (Agency)agencys.get(i);
/* 451 */         String agentId = agency.getAgentId();
/* 452 */         String dealerId = agency.getDealerId();
/* 453 */         Long bgDate = agency.getBgDate();
/* 454 */         Long edDate = agency.getEdDate();
/* 455 */         if ((bgDate != null) && (edDate != null) && (StringUtils.isNotBlank(agentId)) && (agentId.equals(user)) && (now >= bgDate.longValue()) && (now <= edDate.longValue())) {
/* 456 */           proxys.add(dealerId);
/*     */         }
/*     */       }
/*     */     }
/* 460 */     return proxys;
/*     */   }
/*     */ 
/*     */   public IDao<DealProcessModel> getBizDao() {
/* 464 */     return this.bizDao;
/*     */   }
/*     */ 
/*     */   public void setBizDao(IDao<DealProcessModel> bizDao) {
/* 468 */     this.bizDao = bizDao;
/*     */   }
/*     */ 
/*     */   public UserManagerService getUserManagerService() {
/* 472 */     return this.userManagerService;
/*     */   }
/*     */ 
/*     */   public void setUserManagerService(UserManagerService userManagerService) {
/* 476 */     this.userManagerService = userManagerService;
/*     */   }
/*     */ 
/*     */   public AgencyService getAgencyService() {
/* 480 */     return this.agencyService;
/*     */   }
/*     */ 
/*     */   public void setAgencyService(AgencyService agencyService) {
/* 484 */     this.agencyService = agencyService;
/*     */   }
/*     */ 
/*     */   public void test() throws RemoteException {
/*     */   }
/*     */ 
/*     */   public IRoleService getRoleService() {
/* 491 */     return this.roleService;
/*     */   }
/*     */ 
/*     */   public void setRoleService(IRoleService roleService) {
/* 495 */     this.roleService = roleService;
/*     */   }
/*     */ 
/*     */   public DealGroupService getDealGroupService() {
/* 499 */     return this.dealGroupService;
/*     */   }
/*     */ 
/*     */   public void setDealGroupService(DealGroupService dealGroupService) {
/* 503 */     this.dealGroupService = dealGroupService;
/*     */   }
/*     */ 
/*     */   public ITaskManager getTaskManager() {
/* 507 */     return this.taskManager;
/*     */   }
/*     */ 
/*     */   public void setTaskManager(ITaskManager taskManager) {
/* 511 */     this.taskManager = taskManager;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizservice.impl.BizCheckImpl
 * JD-Core Version:    0.6.0
 */
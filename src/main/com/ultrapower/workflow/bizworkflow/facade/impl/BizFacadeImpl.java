/*     */ package com.ultrapower.workflow.bizworkflow.facade.impl;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*     */ import com.ultrapower.eoms.ultrasm.model.DepInfo;
/*     */ import com.ultrapower.eoms.ultrasm.model.UserInfo;
/*     */ import com.ultrapower.eoms.ultrasm.service.UserManagerService;
/*     */ import com.ultrapower.workflow.bizform.BizFormUtil;
/*     */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*     */ import com.ultrapower.workflow.bizservice.ARService;
/*     */ import com.ultrapower.workflow.bizservice.BizCheck;
/*     */ import com.ultrapower.workflow.bizworkflow.bizinterface.IBizWorkFlow;
/*     */ import com.ultrapower.workflow.bizworkflow.facade.IBizFacade;
/*     */ import com.ultrapower.workflow.bizworkflow.facade.IMyPlugin;
/*     */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*     */ import com.ultrapower.workflow.engine.core.model.DataField;
/*     */ import com.ultrapower.workflow.engine.core.model.EngineModel;
/*     */ import com.ultrapower.workflow.engine.core.model.ThreadObj;
/*     */ import com.ultrapower.workflow.engine.core.model.WfAction;
/*     */ import com.ultrapower.workflow.engine.core.model.WfStep;
/*     */ import com.ultrapower.workflow.engine.def.model.WorkflowDescriptor;
/*     */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*     */ import com.ultrapower.workflow.relate.RelateService;
/*     */ import com.ultrapower.workflow.relate.model.RelateModel;
/*     */ import com.ultrapower.workflow.role.model.ChildRole;
/*     */ import com.ultrapower.workflow.role.model.Dimension;
/*     */ import com.ultrapower.workflow.role.service.IRoleService;
/*     */ import com.ultrapower.workflow.utils.CommonUtils;
/*     */ import com.ultrapower.workflow.utils.WfEngineConstants;
/*     */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.UnicastRemoteObject;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional
/*     */ public class BizFacadeImpl extends UnicastRemoteObject
/*     */   implements IBizFacade
/*     */ {
/*  48 */   private Logger log = LoggerFactory.getLogger(BizFacadeImpl.class);
/*     */   private BizCheck bizCheck;
/*     */   private UserManagerService userManagerService;
/*     */   private ARService arService;
/*     */   private IRoleService roleService;
/*     */   private IBizWorkFlow bizWorkFlow;
/*     */   private RelateService relateService;
/*     */ 
/*     */   protected BizFacadeImpl()
/*     */     throws RemoteException
/*     */   {
/*     */   }
/*     */ 
/*     */   public void test()
/*     */     throws RemoteException
/*     */   {
/*  67 */     this.log.info("连接正常！");
/*     */   }
/*     */ 
/*     */   public List<WfAction> getAvailableActions(String baseSchema, String taskId, String defName, Map<String, DataField> inputs)
/*     */   {
/*  80 */     return this.bizWorkFlow.getAvailableActions(baseSchema, taskId, defName, inputs);
/*     */   }
/*     */ 
/*     */   public DealProcessModel checkPerm(String baseId, String baseSchema, String userLoginName)
/*     */     throws RemoteException
/*     */   {
/*  87 */     return this.bizCheck.checkPermission(baseId, baseSchema, userLoginName);
/*     */   }
/*     */ 
/*     */   public ProcessTask checkTask(String baseId, String baseSchema, String userLoginName)
/*     */     throws RemoteException
/*     */   {
/*  94 */     return this.bizCheck.checkTask(baseId, baseSchema, userLoginName);
/*     */   }
/*     */ 
/*     */   public EngineModel doAction(String baseSchema, String defName, String entryType, String actorId, String actorType, String entryId, String actionId, String taskId, String actionType, boolean isCurrent, List<ActionInfo> actionInfo, Map<String, DataField> inputs, Map<String, String> params) throws RemoteException
/*     */   {
/*  99 */     String relateType = null;
/* 100 */     String relateBaseId = null;
/* 101 */     String relateBaseName = null;
/* 102 */     String relateBaseSchema = null;
/* 103 */     String relateBaseSn = null;
/* 104 */     String relateTaskId = null;
/* 105 */     if (params != null) {
/* 106 */       relateType = (String)params.get("relateType");
/* 107 */       relateBaseId = (String)params.get("relateBaseId");
/* 108 */       relateBaseName = (String)params.get("relateBaseName");
/* 109 */       relateBaseSchema = (String)params.get("relateBaseSchema");
/* 110 */       relateBaseSn = (String)params.get("relateBaseSn");
/* 111 */       relateTaskId = (String)params.get("relateTaskId");
/*     */     }
/*     */ 
/* 114 */     EngineModel engineModel = this.bizWorkFlow.doAction(baseSchema, defName, entryType, actorId, actorType, entryId, actionId, taskId, actionType, isCurrent, actionInfo, inputs, params);
/* 115 */     DataField baseId = (DataField)inputs.get(WfEngineConstants.INPUTS_BASEID);
/* 116 */     DataField baseName = (DataField)inputs.get(WfEngineConstants.INPUTS_BASENAME);
/* 117 */     String baseSn = engineModel.getBaseSn();
/*     */ 
/* 119 */     if ("2".equals(engineModel.getEntryStateFlag()))
/*     */     {
/* 121 */       List syncRelateList = this.relateService.getSyncRelateList(baseId.getValue(), baseSchema);
/* 122 */       if (CollectionUtils.isNotEmpty(syncRelateList)) {
/* 123 */         for (int i = 0; i < syncRelateList.size(); i++) {
/* 124 */           RelateModel relateModel = (RelateModel)syncRelateList.get(i);
/* 125 */           String activeTaskId = relateModel.getRelateTaskId();
/* 126 */           this.bizWorkFlow.doAction(null, null, null, actorId, "USER", null, null, activeTaskId, "ACTIVE", isCurrent, null, null, params);
/*     */         }
/*     */       }
/*     */     }
/* 130 */     if (StringUtils.isNotBlank(relateType))
/*     */     {
/* 132 */       if ("1".equals(relateType))
/*     */       {
/* 134 */         this.bizWorkFlow.doAction(null, null, null, actorId, "USER", null, null, relateTaskId, "SUSPEND", isCurrent, null, null, params);
/*     */       }
/*     */ 
/* 137 */       RelateModel relateModel = new RelateModel();
/* 138 */       relateModel.setBaseId(baseId.getValue());
/* 139 */       relateModel.setBaseSchema(baseSchema);
/* 140 */       relateModel.setBaseName(baseName.getValue());
/* 141 */       relateModel.setBaseSN(baseSn);
/* 142 */       relateModel.setRelateTime(TimeUtils.getCurrentTime());
/* 143 */       relateModel.setRelateType(WfEngineUtils.parseLong(relateType));
/* 144 */       relateModel.setRelateUserLoginName(actorId);
/* 145 */       relateModel.setRelateBaseId(relateBaseId);
/* 146 */       relateModel.setRelateBaseSchema(relateBaseSchema);
/* 147 */       relateModel.setRelateBaseName(relateBaseName);
/* 148 */       relateModel.setRelateBaseSn(relateBaseSn);
/* 149 */       relateModel.setRelateTaskId(relateTaskId);
/* 150 */       UserInfo user = this.userManagerService.getUserByLoginName(actorId);
/* 151 */       if (user != null) {
/* 152 */         relateModel.setRelateUserName(user.getFullname());
/*     */       }
/* 154 */       this.relateService.save(relateModel);
/*     */     }
/* 156 */     return engineModel;
/*     */   }
/*     */ 
/*     */   public List<WfStep> getNextWfSteps(String entryId, String taskId, String baseSchema, String defName, String stepCode, Map<String, DataField> inputs) throws RemoteException {
/* 160 */     return this.bizWorkFlow.getNextWfSteps(entryId, taskId, baseSchema, defName, stepCode, inputs);
/*     */   }
/*     */ 
/*     */   public WfStep getCurrentWfStep(String baseSchema, String entryId, String stepCode, Map<String, DataField> inputs) throws RemoteException {
/* 164 */     return this.bizWorkFlow.getCurrentWfStep(baseSchema, entryId, stepCode, inputs);
/*     */   }
/*     */ 
/*     */   public List<WfStep> getCurrentWfSteps(String entryId, Map<String, DataField> inputs) throws RemoteException {
/* 168 */     return this.bizWorkFlow.getCurrentWfSteps(entryId, inputs);
/*     */   }
/*     */ 
/*     */   public List<String> getOwnFields(String baseschema, String stepCode, String entryState, String entryType, String defName) throws RemoteException {
/* 172 */     return this.arService.getOwnFields(baseschema, stepCode, entryState, entryType, defName);
/*     */   }
/*     */ 
/*     */   public List<Dimension> matchDimensions(String defName, String stepCode) throws RemoteException {
/* 176 */     return this.roleService.getDimensionIds(defName, stepCode);
/*     */   }
/*     */ 
/*     */   public List<ChildRole> matchRole(String baseSchema, String defName, String stepCode, Map<String, DataField> inputs) throws RemoteException {
/* 180 */     return this.roleService.matchChildRole(baseSchema, defName, stepCode, inputs);
/*     */   }
/*     */ 
/*     */   public List<ChildRole> matchRole(String defName, String stepCode, String dimensionValue) throws RemoteException {
/* 184 */     return this.roleService.matchRole(defName, stepCode, dimensionValue);
/*     */   }
/*     */ 
/*     */   public List<String> execPlugin(String classFullName, List<String> params) throws RemoteException
/*     */   {
/* 189 */     if (StringUtils.isNotBlank(classFullName)) {
/*     */       try {
/* 191 */         String[] ary = classFullName.split("=");
/* 192 */         if ((ary != null) && (ary.length == 2)) {
/* 193 */           this.log.info("调用扩展类 =" + ary[1].substring(0, ary[1].length() - 1) + "=");
/* 194 */           IMyPlugin plugin = (IMyPlugin)Class.forName(ary[1].substring(0, ary[1].length() - 1)).newInstance();
/* 195 */           this.log.info("获取到需要调用扩展类 =" + plugin.getClass().getName() + "=");
/* 196 */           List callRe = new ArrayList();
/* 197 */           this.log.info("开始调用扩展类 ");
/* 198 */           callRe = plugin.call(params);
/* 199 */           this.log.info("调用扩展类完成 =");
/* 200 */           return callRe;
/*     */         }
/*     */       } catch (Exception e) {
/* 203 */         e.printStackTrace();
/* 204 */         this.log.info(e.getMessage());
/* 205 */         throw new RemoteException(e.getMessage());
/*     */       }
/*     */     }
/* 208 */     return null;
/*     */   }
/*     */ 
/*     */   public List<String> getConditions(String baseSchema, String defName) throws RemoteException {
/* 212 */     return this.bizWorkFlow.getConditions(baseSchema, defName);
/*     */   }
/*     */ 
/*     */   public IRoleService getRoleService() {
/* 216 */     return this.roleService;
/*     */   }
/*     */ 
/*     */   public void setRoleService(IRoleService roleService) {
/* 220 */     this.roleService = roleService;
/*     */   }
/*     */ 
/*     */   public IBizWorkFlow getBizWorkFlow() {
/* 224 */     return this.bizWorkFlow;
/*     */   }
/*     */ 
/*     */   public void setBizWorkFlow(IBizWorkFlow bizWorkFlow) {
/* 228 */     this.bizWorkFlow = bizWorkFlow;
/*     */   }
/*     */ 
/*     */   public BizCheck getBizCheck() {
/* 232 */     return this.bizCheck;
/*     */   }
/*     */ 
/*     */   public void setBizCheck(BizCheck bizCheck) {
/* 236 */     this.bizCheck = bizCheck;
/*     */   }
/*     */ 
/*     */   public UserManagerService getUserManagerService() {
/* 240 */     return this.userManagerService;
/*     */   }
/*     */ 
/*     */   public void setUserManagerService(UserManagerService userManagerService) {
/* 244 */     this.userManagerService = userManagerService;
/*     */   }
/*     */ 
/*     */   public ARService getArService() {
/* 248 */     return this.arService;
/*     */   }
/*     */ 
/*     */   public void setArService(ARService arService) {
/* 252 */     this.arService = arService;
/*     */   }
/*     */ 
/*     */   public List<String> call(List<String> values) throws RemoteException {
/* 256 */     if (CollectionUtils.isEmpty(values)) {
/* 257 */       throw new RemoteException("从AR传过来的参数为空！");
/*     */     }
/* 259 */     List reVals = new ArrayList();
/* 260 */     String type = (String)values.get(0);
/* 261 */     this.log.info("调用类型标识符 " + type);
/*     */     try {
/* 263 */       if (type.equals("CHECKPERM")) {
/* 264 */         this.log.info("从工单打开进行权限判断并返回processId！");
/* 265 */         String baseid = values.get(1) == null ? "" : (String)values.get(1);
/* 266 */         String baseSchema = values.get(2) == null ? "" : (String)values.get(2);
/* 267 */         String user = values.get(3) == null ? "" : (String)values.get(3);
/* 268 */         this.log.info("baseid = " + baseid);
/* 269 */         this.log.info("baseSchema = " + baseSchema);
/* 270 */         this.log.info("user = " + user);
/* 271 */         DealProcessModel dp = checkPerm(baseid, baseSchema, user);
/* 272 */         if (dp != null) {
/* 273 */           reVals.add(dp.getDealTypeStr());
/* 274 */           reVals.add(dp.getProcessId());
/*     */         }
/* 276 */       } else if (type.equals("ROLEDIMENSION")) {
/* 277 */         this.log.info("匹配角色细分！");
/* 278 */         String version = values.get(1) == null ? "" : (String)values.get(1);
/* 279 */         String phaseNo = values.get(2) == null ? "" : (String)values.get(2);
/* 280 */         if (values.size() == 3) {
/* 281 */           this.log.info("取匹配角色细分的维度字段，version=" + version + ",phaseNo=" + phaseNo);
/* 282 */           List matchDimensions = matchDimensions(version, phaseNo);
/* 283 */           String dimensionValues = "";
/* 284 */           if (CollectionUtils.isNotEmpty(matchDimensions)) {
/* 285 */             for (int i = 0; i < matchDimensions.size(); i++) {
/* 286 */               Dimension d = (Dimension)matchDimensions.get(i);
/* 287 */               dimensionValues = dimensionValues + d.getFieldid() + "&" + d.getDimensionname() + "&" + d.getDimCode() + "#";
/*     */             }
/*     */           }
/* 290 */           reVals.add(dimensionValues);
/* 291 */           this.log.info("匹配到的维度值 dimIds=" + dimensionValues);
/* 292 */         } else if (values.size() == 4) {
/* 293 */           String dimensionValue = values.get(3) == null ? "" : (String)values.get(3);
/* 294 */           this.log.info("取匹配角色细分的维度字段，version=" + version + ",phaseNo=" + phaseNo + ",dimensionValue=" + dimensionValue);
/* 295 */           StringBuffer chiRoleName = new StringBuffer();
/* 296 */           StringBuffer chiRoleCode = new StringBuffer();
/* 297 */           List chiList = matchRole(version, phaseNo, dimensionValue);
/* 298 */           if (CollectionUtils.isNotEmpty(chiList)) {
/* 299 */             for (int i = 0; i < chiList.size(); i++) {
/* 300 */               ChildRole chiRole = (ChildRole)chiList.get(i);
/* 301 */               if (chiRole != null) {
/* 302 */                 String code = "R#:" + chiRole.getChildRoleId() + "#:NEXT#:2#:#:#:#:#:" + phaseNo + "#:#:#;";
/* 303 */                 String chiName = chiRole.getChildRoleName();
/* 304 */                 chiRoleName.append(chiName);
/* 305 */                 if (i != chiList.size() - 1) {
/* 306 */                   chiRoleName.append(":");
/*     */                 }
/* 308 */                 chiRoleCode.append(code);
/*     */               }
/*     */             }
/*     */           }
/* 312 */           reVals.add(chiRoleName.toString());
/* 313 */           reVals.add(chiRoleCode.toString());
/* 314 */           this.log.info("匹配到的角色细分 chiRoleCode=" + chiRoleCode.toString() + ",name=" + chiRoleName.toString());
/*     */         }
/* 316 */       } else if (type.equals("TRANSFLOW")) {
/* 317 */         String entryId = values.get(1) == null ? "" : (String)values.get(1);
/* 318 */         String defName = values.get(2) == null ? "" : (String)values.get(2);
/* 319 */         String entryType = values.get(3) == null ? "" : (String)values.get(3);
/* 320 */         String taskId = values.get(4) == null ? "" : (String)values.get(4);
/* 321 */         String userId = values.get(5) == null ? "" : (String)values.get(5);
/* 322 */         String actionType = values.get(6) == null ? "" : (String)values.get(6);
/* 323 */         String assignees = values.get(7) == null ? "" : (String)values.get(7);
/* 324 */         String input = values.get(8) == null ? "" : (String)values.get(8);
/* 325 */         String taskType = values.get(9) == null ? "" : (String)values.get(9);
/*     */ 
/* 327 */         String relateBaseId = null;
/* 328 */         String relateBaseName = null;
/* 329 */         String relateBaseSchema = null;
/* 330 */         String relateType = null;
/* 331 */         String relateTaskId = null;
/*     */ 
/* 333 */         this.log.info("驱动流程流转！");
/* 334 */         this.log.info("entryId = " + entryId);
/* 335 */         this.log.info("defName = " + defName);
/* 336 */         this.log.info("entryType = " + entryType);
/* 337 */         this.log.info("taskId = " + taskId);
/* 338 */         this.log.info("userId = " + userId);
/* 339 */         this.log.info("actionName = " + actionType);
/* 340 */         this.log.info("assignees = " + assignees);
/* 341 */         this.log.info("input = " + input);
/* 342 */         this.log.info("taskType = " + taskType);
/* 343 */         Map params = new HashMap();
/* 344 */         if (values.size() == 15) {
/* 345 */           relateBaseId = values.get(10) == null ? "" : (String)values.get(10);
/* 346 */           relateBaseName = values.get(11) == null ? "" : (String)values.get(11);
/* 347 */           relateBaseSchema = values.get(12) == null ? "" : (String)values.get(12);
/* 348 */           relateType = values.get(13) == null ? "" : (String)values.get(13);
/* 349 */           relateTaskId = values.get(14) == null ? "" : (String)values.get(14);
/* 350 */           this.log.info("工单关联！");
/* 351 */           this.log.info("relateBaseId = " + relateBaseId);
/* 352 */           this.log.info("relateBaseName = " + relateBaseName);
/* 353 */           this.log.info("relateBaseSchema = " + relateBaseSchema);
/* 354 */           this.log.info("relateType = " + relateType);
/* 355 */           this.log.info("relateTaskId = " + relateTaskId);
/* 356 */           params.put("relateBaseId", relateBaseId);
/* 357 */           params.put("relateBaseName", relateBaseName);
/* 358 */           params.put("relateBaseSchema", relateBaseSchema);
/* 359 */           params.put("relateTaskId", relateTaskId);
/* 360 */           if (StringUtils.isBlank(relateType)) {
/* 361 */             params.put("relateType", relateType);
/*     */           }
/*     */         }
/*     */ 
/* 365 */         int flagActive = 0;
/* 366 */         if (StringUtils.isNotBlank(taskType)) {
/* 367 */           flagActive = Integer.parseInt(taskType);
/*     */         }
/* 369 */         boolean isCurrent = (flagActive == 1) || (flagActive == WfEngineConstants.FLAGACTIVE_SUSPEND);
/* 370 */         Map inputs = BizFormUtil.formatInputs(input);
/* 371 */         List actionInfos = BizFormUtil.formatActionInfo(assignees);
/* 372 */         EngineModel engineModel = doAction(null, defName, entryType, userId, "USER", entryId, null, taskId, actionType, isCurrent, actionInfos, inputs, params);
/* 373 */         reVals.add(engineModel.getTopEntryId());
/* 374 */         reVals.add(engineModel.getEntryState());
/* 375 */         reVals.add(engineModel.getBaseSn());
/* 376 */         reVals.add(engineModel.getMailContent());
/* 377 */         reVals.add(engineModel.getCreateTaskId());
/* 378 */         reVals.add(engineModel.getEntryStateFlag());
/* 379 */         reVals.add(engineModel.getCreateStepCode());
/* 380 */         reVals.add(engineModel.getCreateProcessId());
/* 381 */         reVals.add(engineModel.getBaseCreateTime());
/* 382 */       } else if (type.indexOf("CLASSNAME=") > -1) {
/* 383 */         String[] ary = type.split("=");
/* 384 */         if ((ary != null) && (ary.length == 2)) {
/* 385 */           values.remove(0);
/* 386 */           int length = ary[1].substring(ary[1].length() - 1, ary[1].length()).equals(".") ? ary[1].length() - 1 : ary[1].length();
/* 387 */           this.log.info("调用扩展类 =" + ary[1].substring(0, length) + "=");
/* 388 */           IMyPlugin plugin = (IMyPlugin)Class.forName(ary[1].substring(0, length)).newInstance();
/* 389 */           this.log.info("获取到需要调用扩展类 =" + plugin.getClass().getName() + "=");
/* 390 */           List callRe = new ArrayList();
/*     */           try
/*     */           {
/* 393 */             this.log.info("开始调用扩展类 ");
/* 394 */             callRe = plugin.call(values);
/* 395 */             this.log.info("调用扩展类完成 =");
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 399 */             e.printStackTrace();
/* 400 */             this.log.info(e.getMessage());
/* 401 */             throw e;
/*     */           }
/* 403 */           return callRe;
/*     */         }
/*     */       } else {
/* 405 */         if (type.equals("GETOWNFIELDS")) {
/* 406 */           String baseschema = (String)values.get(1);
/* 407 */           String phaseNo = (String)values.get(2);
/* 408 */           String entryState = (String)values.get(3);
/* 409 */           String entryType = (String)values.get(4);
/* 410 */           String version = values.size() == 6 ? (String)values.get(5) : null;
/* 411 */           return getOwnFields(baseschema, phaseNo, entryState, entryType, version);
/* 412 */         }if (!type.equals("TEST"))
/* 413 */           if (type.equals("GETUSERINFO")) {
/* 414 */             String loginName = (String)values.get(1);
/* 415 */             this.log.info("获取用户信息 loginName=" + loginName);
/* 416 */             UserInfo user = this.arService.getUserInfo(loginName);
/* 417 */             String fullname = user.getFullname();
/* 418 */             String email = user.getEmail();
/* 419 */             String mobile = user.getMobile();
/* 420 */             String phone = user.getPhone();
/* 421 */             String groupid = user.getGroupid();
/* 422 */             String groupname = user.getGroupname();
/* 423 */             String depid = user.getDepid();
/* 424 */             String depname = user.getDepname();
/* 425 */             String companyId = user.getCompanyId();
/* 426 */             String companyName = user.getCompanyName();
/* 427 */             String dn = "";
/* 428 */             String dnId = "";
/* 429 */             DepInfo dep = ThreadObj.getDepInfo(depid);
/* 430 */             if (dep != null) {
/* 431 */               dn = dep.getDepfullname();
/* 432 */               dnId = dep.getDepdns();
/*     */             }
/* 434 */             String phoneMobile = CommonUtils.getString(phone) + "," + CommonUtils.getString(mobile);
/* 435 */             if (phoneMobile.indexOf(",") == 0) {
/* 436 */               phoneMobile = phoneMobile.replace(",", "");
/*     */             }
/* 438 */             reVals.add(CommonUtils.getString(fullname));
/* 439 */             reVals.add(phoneMobile);
/* 440 */             reVals.add(CommonUtils.getString(email));
/* 441 */             reVals.add(CommonUtils.getString(groupid));
/* 442 */             reVals.add(CommonUtils.getString(groupname));
/* 443 */             reVals.add(CommonUtils.getString(depid));
/* 444 */             reVals.add(CommonUtils.getString(depname));
/* 445 */             reVals.add(CommonUtils.getString(companyId));
/* 446 */             reVals.add(CommonUtils.getString(companyName));
/* 447 */             reVals.add(CommonUtils.getString(dnId));
/* 448 */             reVals.add(CommonUtils.getString(dn));
/*     */           }
/*     */           else
/*     */           {
/*     */             List nextStepInfo;
/* 449 */             if (type.equals("GETNEXTINFO")) {
/* 450 */               if (values.size() != 5) {
/* 451 */                 throw new RuntimeException("获取下一步环节信息出错！参数不对！");
/*     */               }
/* 453 */               List rtn = new ArrayList();
/* 454 */               String defName = (String)values.get(1);
/* 455 */               String entryId = (String)values.get(2);
/* 456 */               String stepCode = (String)values.get(3);
/* 457 */               String inputStr = (String)values.get(4);
/* 458 */               Map inputs = BizFormUtil.formatInputs(inputStr);
/* 459 */               nextStepInfo = getNextWfSteps(entryId, null, null, defName, stepCode, inputs);
/*     */             } else {
/* 461 */               throw new RemoteException("第一个标识参数不正确！type=" + type);
/*     */             }
/*     */           }
/*     */       }
/*     */     } catch (Exception e) {
/* 464 */       e.printStackTrace();
/* 465 */       this.log.error(e.getMessage(), e);
/* 466 */       throw new RemoteException(e.getMessage(), e);
/*     */     }
/* 468 */     return reVals;
/*     */   }
/*     */ 
/*     */   public RelateService getRelateService() {
/* 472 */     return this.relateService;
/*     */   }
/*     */ 
/*     */   public void setRelateService(RelateService relateService) {
/* 476 */     this.relateService = relateService;
/*     */   }
/*     */ 
/*     */   public WorkflowDescriptor getWorkflowDescriptor(String baseSchema, String defName) throws RemoteException
/*     */   {
/* 481 */     return this.bizWorkFlow.getWorkflowDescriptor(baseSchema, defName);
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizworkflow.facade.impl.BizFacadeImpl
 * JD-Core Version:    0.6.0
 */
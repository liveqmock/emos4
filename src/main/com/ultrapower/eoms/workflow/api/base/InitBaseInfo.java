package com.ultrapower.eoms.workflow.api.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.workflow.api.bean.BaseAttachmentInfo;
import com.ultrapower.eoms.workflow.api.bean.BaseFieldInfo;
import com.ultrapower.eoms.workflow.api.bean.DealObject;
import com.ultrapower.eoms.workflow.design.control.DealProcessManager;
import com.ultrapower.eoms.workflow.design.model.DealProcess;
import com.ultrapower.eoms.workflow.flowmap.control.BaseManage;
import com.ultrapower.eoms.workflow.flowmap.model.BaseModel;
import com.ultrapower.remedy4j.core.RemedySession;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;

public abstract class InitBaseInfo extends Utils {
	
	private final static Logger log = LoggerFactory.getLogger(InitBaseInfo.class);
	
	private UserManagerService userManagerService = (UserManagerService)WebApplicationManager.getBean("userManagerService");
	
	private boolean isLoadedBaseFields = false;
	
	/**
	 * 初始化工单打开逻辑
	 * @param baseSchema
	 * @param baseId
	 * @param userLoginName
	 * @param dealObjs
	 * @param bizFields
	 * @param attachs
	 * @return
	 */
	protected boolean initBase(String baseSchema, String baseId, String taskId, String userLoginName, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		try{
			//初始化字段信息
			if (!isLoadedBaseFields && !loadBaseFields(baseSchema)) {
				return false;	
			}
			//初始化人员信息		
			if (!loadUserInfo(userLoginName)) {
				return false;	
			}			
			//初始化工单类别信息		
			if (!loadBaseCategory(baseSchema)) {
				return false;	
			}
			
			//修改工单
			if (StringUtils.isNotBlank(baseId)) {
				//初始化工单主体信息
				if (!loadBaseModel(baseSchema, baseId)) {
					return false;
				}
				String processIdStr = null;
				if (StringUtils.isNotBlank(taskId)) {
					processIdStr = getProcessByTaskId(taskId);
				}
				if (StringUtils.isBlank(processIdStr)) {
					processIdStr = getProcessStr(baseSchema, baseId, userLoginName);
				}
				if (StringUtils.isBlank(processIdStr)) {
					log.error("没有匹配的processIdStr");
					return false;
				}
				baseAllFields.put("tmp_wfattach_processId", new BaseFieldInfo("tmp_wfattach_processId", null, processIdStr.substring(3)));
				if (!checkPermmsion(baseId, baseSchema, processIdStr, userLoginName, dealObjs, bizFields, attachs)) {
					return false;
				}
				if (!loadDealProcessModel(processIdStr)) {
					return false;
				}
			} else {
			//新建工单
				//初始化工单新建的信息		
				if (!setDefaultBaseInfo(baseSchema)) {
					return false;	
				}
				if (!checkPermmsion(baseId, baseSchema, null, userLoginName, dealObjs, bizFields, attachs)) {
					return false;
				}
				//初始化工单新建环节的信息
				if (!setDefaultProcessInfo()) {
					return false;	
				}
			}
			
			//设置公共字段
			if (!setCommonField()) {
				return false;
			}
		} catch(Exception ex) {
			log.error("初始化信息",ex);
			return false;
		}
		return true;
	}	
	
	/**
	 * 设置公共字段信息
	 */
	protected boolean setCommonField() {
		setFieldValue("tmp_PluginName", "eomsPlugin");
		setFieldValue("tmp_PluginType", "TRANSFLOW");
		
		String tName = RemedySession.UtilInfor.getTableName("WF4:Config_SysKeyWord");
		String sql = "select c650000002 as keyVal from " + tName + " where c650000001='DBSqlTop'";
		log.info("查询WF4:Config_SysKeyWord中DBSqlTop的对应值,sql=" + sql);
		QueryAdapter qa = new QueryAdapter();
		DataTable dt = qa.executeQuery(sql, null);
		if (dt != null && dt.length() > 0) {
			String keyVal = dt.getDataRow(0).getString("keyVal");
			setFieldValue("tmp_ExecuteSqlTabelTop", keyVal);
		} else {
			log.error("查询WF4:Config_SysKeyWord中DBSqlTop的对应值失败！");
			return false;
		}
		return true;
	}
	
	/**
	 * 获取当前工单的所有字段
	 * @param baseSchema
	 * @return
	 */
	protected boolean loadBaseFields(String baseSchema) {
		log.info("从AR的Field表获取获取baseSchema=" + baseSchema + "的所有字段");
		String BaseFormId = RemedySession.UtilInfor.getTableName(baseSchema);
		HashMap params = new HashMap();
		params.put("BaseFormName", baseSchema);
		params.put("BaseFormId", BaseFormId.substring(1));
		RQuery rquery = new RQuery("SQL_API_BASEFIELDS.query",params);
		rquery.setIsCount(2);
		DataTable dt = rquery.getDataTable();
		if(dt != null && dt.length() > 0){
			for (int i = 0; i < dt.length(); i++) {
				DataRow dr = dt.getDataRow(i);
				String fieldid = dr.getString("fieldid");
				String fieldname = dr.getString("fieldname");
				String datatype = dr.getString("datatype");
				baseAllFields.put(fieldname, new BaseFieldInfo(fieldname, fieldid, null, Integer.parseInt(datatype)));
			}
		} else {
			log.error("获取工单字段信息失败！");
			return false;
		}
		isLoadedBaseFields = true;
    	return true;
	}
	
	/**
	 * 获取当前用户信息
	 * @param loginName
	 * @return
	 */
	protected boolean loadUserInfo(String loginName) {
		log.info("获取当前用户信息,loginName=" + loginName);
		UserInfo user = userManagerService.getUserByLoginName(loginName);
		if(user != null){
			setFieldValue("tmp_UserLoginName",user.getLoginname());
			setFieldValue("tmp_UserFullName",user.getFullname());
//			if(user.getEmail()!=null)
//			{
//				setFieldValue("tmp_UserEmailAdd",user.getEmail());
//			}
			setFieldValue("tmp_UserCorp",user.getCompanyName());
			setFieldValue("tmp_UserCorpID",user.getCompanyId());
			setFieldValue("tmp_UserDep",user.getDepname(	));
			setFieldValue("tmp_UserDepID",user.getDepid());
//				setFieldValue("tmp_UserDN",user.getd);
//				setFieldValue("tmp_UserDNID",userModel.getDNID());
		} else {
			log.error("获取当前用户信息失败,loginName=" + loginName);
			return false;
		}
		return true;
	}
	
	/**
	 * 获取工单类别信息 
	 * @param baseSchama
	 * @return
	 */
	protected boolean loadBaseCategory(String baseSchama) {
		log.info("获取工单类别信息,baseSchama=" + baseSchama);
		try {
			IWfSortManager ver = WorkFlowServiceClient.clientInstance().getSortService();
			WfType wfType = ver.getWfTypeByCode(baseSchama);
			if (wfType != null) {
				String wfDefaultVersion = wfType.getWfDefaultVersion();
				long type = wfType.getWfType();
				setFieldValue("tmp_BaseCategorySchama", wfType.getCode());
				setFieldValue("tmp_BaseCategoryBeginPhaseNo", wfType.getBaseCategoryBeginPhaseNo());
				setFieldValue("tmp_BaseCategoryCustomActions", wfType.getBaseCategoryCustomActions());
				setFieldValue("tmp_BaseCategoryPageHIDS", wfType.getBaseCategoryPanelHids());
				setFieldValue("tmp_BaseCategoryBtnFixIDS", wfType.getBaseCategoryBtnFixIds());
				setFieldValue("tmp_BaseCategoryBtnFreeIDS", wfType.getBaseCategoryBtnFreeIds());
				setFieldValue("tmp_BaseCategoryPageIDS", wfType.getBaseCategoryPanelIds());
				setFieldValue("tmp_BaseCategoryBtnIDS", wfType.getBaseCategoryBtnAllIds());
				setFieldValue("tmp_BaseCategoryIsFlow", type + "");
				setFieldValue("tmp_BaseCategoryCode", wfType.getBaseCategoryCode());
				setFieldValue("tmp_BaseCategoryName", wfType.getName());
				setFieldValue("BaseTplID", wfDefaultVersion);
				if (type == 1 && StringUtils.isBlank(wfDefaultVersion)) {
					log.error("固定流程" + baseSchama + "未设置默认版本！");
					return false;
				}
				if (type == 1) {//固定流程
					setFieldValue("tmp_EntryType", "DEFINE");
				} else {//自由流程
					setFieldValue("tmp_EntryType", "FREE");
					setFieldValue("BaseTplID", wfType.getCode());
				}
			}
		} catch (Exception e) {
			log.error("获取流程分类失败！", e);
			return false;
		}
		return true;
	}	
	
	/**
	 * 新建工单，设置工单主体信息默认值
	 * @param baseSchema
	 * @return
	 */
	protected boolean setDefaultBaseInfo(String baseSchema) {
		log.info("新建工单，设置工单主体信息默认值");
		setFieldValue("BaseAttachGUID", getGUID());
		setFieldValue("BaseFlagAccept", "0");
		setFieldValue("BaseOpenDateTime", TimeUtils.getCurrentTime() + "");
		setFieldValue("BaseFlagIsCreateChild", "0");
		setFieldValue("BaseFieldLogName", baseSchema + "_FieldModifyLog");
		setFieldValue("BaseDealOutFlag", "0");
		setFieldValue("BaseAcceptOutFlag", "0");
		setFieldValue("BaseFlagCloseed", "0");
		setFieldValue("BaseFlagFinished", "0");
		setFieldValue("BaseFlagSended", "0");
		setFieldValue("BaseFlagCreated", "0");
		setFieldValue("BaseIsTrueClose", "0");
		setFieldValue("BaseIsArchive", "0");
		setFieldValue("BaseStatus", "草稿");
		setFieldFromField("BaseCreatorLoginName", "tmp_UserLoginName");
		setFieldFromField("BaseCreatorFullName", "tmp_UserFullName");
		setFieldFromField("BaseCreatorDNID", "tmp_UserDNID");
		setFieldFromField("BaseCreatorDN", "tmp_UserDN");
		setFieldFromField("BaseCreatorDepID", "tmp_UserDepID");
		setFieldFromField("BaseCreatorDep", "tmp_UserDep");
		setFieldFromField("BaseCreatorCorpID", "tmp_UserCorpID");
		setFieldFromField("BaseCreatorCorp", "tmp_UserCorp");
		setFieldFromField("BaseCreatorConnectWay", "tmp_UserEmailAdd");
		setFieldFromField("BaseWorkFlowFlag", "tmp_BaseCategoryIsFlow");
		setFieldFromField("BaseName", "tmp_BaseCategoryName");
		setFieldFromField("BaseSchema", "tmp_BaseCategorySchama");
		return true;
	}
	
	/**
	 * 新建工单，设置建单环节信息默认值
	 * @return
	 */
	protected boolean setDefaultProcessInfo() {
		log.info("新建工单，设置建单环节信息默认值");
		setFieldValue("tmp_Pro_Flag03Assist", "0");
		setFieldValue("tmp_Pro_Flag04Transfer", "0");
		setFieldValue("tmp_Pro_Flag05TurnDown", "0");
		setFieldValue("tmp_Pro_Flag06TurnUp", "0");
		setFieldValue("tmp_Pro_Flag16ToAssistAuditing", "0");
		setFieldValue("tmp_Pro_Flag30AuditingResult", "0");
		setFieldValue("tmp_Pro_Flag33IsEndPhase", "1");
		setFieldValue("tmp_Pro_Flag34IsEndDuplicated", "1");
		setFieldValue("tmp_Pro_Flag38StartInsideFlow", "0");
		setFieldValue("tmp_Pro_FlagAssignGroupOrUser", "0");
		setFieldValue("tmp_Pro_DealTime", TimeUtils.getCurrentTime() + "");
		setFieldValue("tmp_Pro_StProcessAction", "新建");
		setFieldValue("tmp_Pro_BaseID", getGUID());
		setFieldValue("tmp_Pro_ProcessType", "DEAL");
		setFieldValue("tmp_Pro_Status", "待处理");
		setFieldValue("tmp_Pro_FlagDuplicated", "0");
		setFieldValue("tmp_Pro_FlagActive", "1");
		setFieldValue("tmp_Pro_FlagType", "主办");
		setFieldFromField("tmp_Pro_DealerDNID", "tmp_UserDNID");
		setFieldFromField("tmp_Pro_DealerDN", "tmp_UserDN");
		setFieldFromField("tmp_Pro_DealerDepID", "tmp_UserDepID");
		setFieldFromField("tmp_Pro_DealerDep", "tmp_UserDep");
		setFieldFromField("tmp_Pro_DealerCorpID", "tmp_UserCorpID");
		setFieldFromField("tmp_Pro_DealerCorp", "tmp_UserCorp");
		setFieldFromField("tmp_Pro_DealerID", "tmp_UserLoginName");
		setFieldFromField("tmp_Pro_Dealer", "tmp_UserFullName");
		setFieldFromField("tmp_Pro_AssigneeDNID", "tmp_UserDNID");
		setFieldFromField("tmp_Pro_AssigneeDN", "tmp_UserDN");
		setFieldFromField("tmp_Pro_AssigneeDepID", "tmp_UserDepID");
		setFieldFromField("tmp_Pro_AssigneeDep", "tmp_UserDep");
		setFieldFromField("tmp_Pro_AssigneeCorpID", "tmp_UserCorpID");
		setFieldFromField("tmp_Pro_AssigneeCorp", "tmp_UserCorp");
		setFieldFromField("tmp_Pro_AssgineeID", "tmp_UserLoginName");
		setFieldFromField("tmp_Pro_Assginee", "tmp_UserFullName");
		setFieldFromField("tmp_Pro_FlagStart", "tmp_Pro_DealTime");
		setFieldFromField("tmp_Pro_FlagBegin", "tmp_Pro_DealTime");
		setFieldFromField("tmp_Pro_BgDate", "tmp_Pro_DealTime");
		setFieldFromField("tmp_Pro_StDate", "tmp_Pro_DealTime");
		setFieldFromField("tmp_Pro_ProcessID", "tmp_Pro_BaseID");
		setFieldFromField("tmp_Pro_BaseSchema", "tmp_BaseCategorySchama");
		String wfType = getFieldValue("tmp_EntryType");
		if ("FREE".equals(wfType)) {//自由流程
			setFieldValue("tmp_Pro_Flag01Assign", "1");
			setFieldValue("tmp_Pro_Flag07Recall", "1");
			setFieldValue("tmp_Pro_Flag08Cancel", "1");
			setFieldValue("tmp_Pro_Flag09Close", "1");
			setFieldValue("tmp_Pro_Flag15ToAuditing", "1");
			setFieldValue("tmp_Pro_FlagGroupSnatch", "0");
			setFieldValue("tmp_Pro_FlagPredefined", "0");
			setFieldValue("tmp_Pro_Desc", "新建工单；");
			setFieldValue("tmp_Pro_PrevPhaseNo", "BEGIN");
			setFieldFromField("tmp_Pro_PhaseNo", "tmp_Pro_BaseID");
		} else if ("DEFINE".equals(wfType)) {//固定流程
			setFieldValue("tmp_Pro_Flag01Assign", "0");
			setFieldValue("tmp_Pro_Flag07Recall", "0");
			setFieldValue("tmp_Pro_Flag08Cancel", "0");
			setFieldValue("tmp_Pro_Flag09Close", "0");
			setFieldValue("tmp_Pro_Flag15ToAuditing", "0");
			setFieldFromField("tmp_Pro_CustomActions", "tmp_BaseCategoryCustomActions");
			setFieldValue("tmp_Pro_ActionName", "下一步");
			setFieldValue("tmp_Pro_FlagPredefined", "1");
			setFieldFromField("tmp_Pro_PhaseNo", "tmp_BaseCategoryBeginPhaseNo");
		} else {
			log.error("没指定流程类型（自由 or 固定）！wfType=" + wfType);
			return false;
		}
		return true;
	}
	
	/**
	 * 初始化工单主体信息
	 * @param baseSchema
	 * @param baseId
	 * @return
	 */
	protected boolean loadBaseModel(String baseSchema, String baseId) {
		log.info("获取工单主体信息，baseSchema=" + baseSchema + ",baseId=" + baseId);
		try {
			BaseModel base = (new BaseManage()).getOneBaseModel(baseId, baseSchema);
			//因为工单表中的baseId和baseInfo表中的baseid字段不同所以需要特殊处理
			baseAllFields.put("BaseID", new BaseFieldInfo("BaseID", 1 + "", base.getBaseID()));
			setFieldValue("BaseSchema", base.getBaseSchema());
			setFieldValue("BaseName", base.getBaseName());
			setFieldValue("BaseSN", base.getBaseSN());
			setFieldValue("BaseCreatorFullName", base.getBaseCreatorFullName());
			setFieldValue("BaseCreatorLoginName", base.getBaseCreatorLoginName());
			setFieldValue("BaseCreateDate", base.getBaseCreateDate() + "");
			setFieldValue("BaseSendDate", base.getBaseSendDate() + "");
			setFieldValue("BaseFinishDate", base.getBaseFinishDate() + "");
			setFieldValue("BaseCloseDate", base.getBaseCloseDate() + "");
			setFieldValue("BaseStatus", base.getBaseStatus());
//			setFieldValue("BaseSummary", base.getBaseSummary());
//			setFieldValue("BaseItems", base.getBaseItems());
//			setFieldValue("BasePriority", base.getBasePriority());
//			setFieldValue("BaseAcceptOutTime", base.getBaseAcceptOutTime() + "");
//			setFieldValue("BaseDealOutTime", base.getBaseDealOutTime() + "");
//			setFieldValue("BaseDescrption", base.getBaseDescrption());
//			setFieldValue("BaseCloseSatisfy", base.getBaseCloseSatisfy());
			setFieldValue("BaseResult", base.getBaseResult());
			setFieldValue("BaseTplID", base.getBaseTplID());
			setFieldValue("BaseIsArchive", base.getBaseIsArchive() + "");
			setFieldValue("BaseIsTrueClose", base.getBaseIsTrueClose() + "");
			setFieldValue("BaseWorkFlowFlag", base.getBaseWorkFlowFlag() + "");
			setFieldValue("BaseFlagCreated", base.getBaseFlagCreated() + "");
			setFieldValue("BaseFlagSended", base.getBaseFlagSended() + "");
			setFieldValue("BaseFlagFinished", base.getBaseFlagFinished() + "");
			setFieldValue("BaseFlagCloseed", base.getBaseFlagCloseed() + "");
			setFieldValue("BaseStatusCode", base.getBaseStatusCode());
			setFieldValue("BaseAcceptOutFlag", base.getBaseAcceptOutFlag() + "");
			setFieldValue("BaseDealOutFlag", base.getBaseDealOutFlag() + "");
			setFieldValue("BaseFieldLogName", base.getBaseFieldLogName());
			setFieldValue("BaseFlagIsMotherCreated", base.getBaseFlagIsMotherCreated() + "");
			setFieldValue("BaseFlowDrawDesc", base.getBaseFlowDrawDesc());
			setFieldValue("BaseFlagIsCreateChild", base.getBaseFlagIsCreateChild() + "");
			setFieldValue("BaseOpenDateTime", base.getBaseOpenDateTime() + "");
			setFieldValue("BaseCreatorConnectWay", base.getBaseCreatorConnectWay());
			setFieldValue("BaseCreatorCorp", base.getBaseCreatorCorp());
			setFieldValue("BaseCreatorCorpID", base.getBaseCreatorCorpID());
			setFieldValue("BaseCreatorDep", base.getBaseCreatorDep());
			setFieldValue("BaseCreatorDepID", base.getBaseCreatorDepID());
			setFieldValue("BaseCreatorDN", base.getBaseCreatorDN());
			setFieldValue("BaseCreatorDNID", base.getBaseCreatorDNID());
			setFieldValue("tmp_Begin_PhaseNo", base.getTmp_Begin_PhaseNo());
			setFieldValue("tmp_Begin_ProcessID", base.getTmp_Begin_ProcessID());
			setFieldValue("BaseEntryID", base.getBaseEntryID());
			setFieldValue("BaseAttachGUID", base.getBaseAttachGUID());
		} catch(Exception ex) {
			log.error("初始化工单基础数据", ex);
			return false;
		}
		return true;
	}
	
	protected void initBaseFieldValues(String baseSchema, String baseId)
	{
		if(!isLoadedBaseFields)
		{
			String tName = RemedySession.UtilInfor.getTableName(baseSchema);
			if(baseId != null && !baseId.equals(""))
			{
				String sql = "select * from " + tName + " where C1='" + baseId + "'";
				log.info("查询" + baseSchema + "中所有字段,sql=" + sql);
				QueryAdapter qa = new QueryAdapter();
				DataTable dt = qa.executeQuery(sql, null);
				if(dt != null && dt.length() > 0)
				{
					DataRow row = dt.getDataRow(0);
					for (Iterator<String> it = baseAllFields.keySet().iterator(); it.hasNext();) {
						String key = it.next();
						BaseFieldInfo baseFieldInfo = baseAllFields.get(key);
						String tmpValue = row.getString("C" + baseFieldInfo.getId());
						if(tmpValue != null && !tmpValue.equals(""))
						{
							baseFieldInfo.setValue(row.getString("C" + baseFieldInfo.getId()));
						}
					}
				}
			}
		}
	}
	
	/**
	 * 对当前用户和执行动作的权限检查
	 * @param baseId
	 * @param baseSchema
	 * @param processId
	 * @param userLoginName
	 * @param dealObjs
	 * @param bizFields
	 * @param attachs
	 * @return
	 */
	protected abstract boolean checkPermmsion(String baseId, String baseSchema, String processId, String userLoginName, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs);
	
	/**
	 * 设置环节信息
	 * @param processIdStr
	 * @return
	 */
	protected boolean loadDealProcessModel(String processIdStr) {
		log.info("获取环节信息，processIdStr=" + processIdStr);
		try {	
			DealProcessManager dpMan = new DealProcessManager();
			DealProcess dp = dpMan.getDealProcessById(processIdStr.substring(3));
			setFieldValue("tmp_Pro_FlagActive", dp.getFlagActive() + "");
			setFieldValue("tmp_Pro_CurrentTaskID", dp.getTaskId());
			setFieldValue("tmp_Pro_Flag30AuditingResult", dp.getFlagAuditingResult() + "");
			setFieldValue("tmp_Pro_Flag33IsEndPhase", dp.getFlagEndPhase() + "");
			setFieldValue("tmp_Pro_Flag34IsEndDuplicated", dp.getFlagDuplicated() + "");
			setFieldValue("tmp_Pro_Flag38StartInsideFlow", dp.getFlagStartInsideFlow() + "");
			setFieldValue("tmp_Pro_DealTime", TimeUtils.getCurrentTime() + "");
			setFieldValue("tmp_Pro_BaseID", dp.getBaseId());
			setFieldValue("tmp_Pro_ProcessType", dp.getProcessType());
			setFieldValue("tmp_Pro_Status", dp.getProcessStatus());
			setFieldValue("tmp_Pro_FlagDuplicated", dp.getFlagDuplicated() + "");
			setFieldValue("tmp_Pro_FlagActive", dp.getFlagActive() + "");
			setFieldValue("tmp_Pro_ProcessID", dp.getProcessId());
			setFieldValue("tmp_Pro_BaseSchema", dp.getBaseSchema());
			setFieldValue("tmp_Pro_FlagPredefined", dp.getFlagPredefined() + "");
			setFieldValue("tmp_Pro_PrevPhaseNo", dp.getPrePhaseNo() + "");
			setFieldValue("tmp_Pro_PhaseNo", dp.getPhaseNo() + "");
		} catch(Exception ex) {
			log.error("根据工单环节关键字,读工单环节信息",ex);
			return false;
		}		
		return true;
		
	}

	protected String getProcessStr(String baseSchema, String baseId, String userLoginName) throws Exception {
		log.info("通过baseSchema="+baseSchema+",baseId="+baseId+",userLoginName="+userLoginName+",反查processId");
		return checkPermission(baseId, baseSchema, userLoginName);
	}
	
	/**
	 * 连审带派时，设置处理人规则串。
	 */
	protected void setPreAssign(String actor, String actorType) {
		if (StringUtils.isNotBlank(actor) && StringUtils.isNotBlank(actorType)) {
			String[] actors = actor.split(",");
			String[] actorTypes = actorType.split(",");
			if (!ArrayUtils.isEmpty(actors) && !ArrayUtils.isEmpty(actorTypes)) {
				String preAssignStr = "";
				for (int i = 0; i < actorTypes.length; i++) {
					preAssignStr += actorTypes[i] + "#:" + actors[i] + "#:ASSIGN#:2#:0#:0#:0#:#:#:#:";
				}
				baseAllFields.put("PreAssign", new BaseFieldInfo("PreAssign", "", preAssignStr, true));
			}
		}
	}
	
	public String checkPermission(String baseid, String baseSchema, String user) throws Exception {
		String rtn="";
		com.ultrapower.workflow.bizform.model.DealProcessModel dpModel = WorkFlowServiceClient.clientInstance().getBizFacade().checkPerm(baseid, baseSchema, user);
		if (dpModel != null) {
			String dealTypeStr = dpModel.getDealTypeStr();
			String processId = dpModel.getProcessId();
			rtn = dealTypeStr + ":" + processId;
		}
		return rtn;
	}
	
	public String getProcessByTaskId(String taskId) {
		String processStr = null;
		if (StringUtils.isNotBlank(taskId)) {
			String sql = "select processid from bs_t_wf_dealprocess where taskid='"+taskId+"'";
			QueryAdapter query = new QueryAdapter();
			DataTable rs = query.executeQuery(sql, null, 0);
			if (rs != null && rs.length() > 0) {
				DataRow dr = rs.getDataRow(0);
				processStr = "11:" + dr.getString("processid");
			}
		}
		return processStr;
	}
}

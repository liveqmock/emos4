package com.ultrapower.eoms.workflow.api.action;

import java.util.List;

import com.ultrapower.eoms.workflow.api.bean.BaseAttachmentInfo;
import com.ultrapower.eoms.workflow.api.bean.BaseFieldInfo;
import com.ultrapower.eoms.workflow.api.bean.DealObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.workflow.design.control.DealProcessManager;
import com.ultrapower.eoms.workflow.design.model.DealProcess;


	
public class AcceptAction extends BaseAction {
	
	private final static Logger log = LoggerFactory.getLogger(AcceptAction.class);
	
	@Override
	public boolean setActionField(String baseSchema, String baseId, String userLoginName, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		setFieldValue("tmp_BaseActionBtn_Char", "ACCEPT");
		setFieldValue("tmp_BaseActionBtn_Fix_Char", StringUtils.isNotBlank(actionText) ? actionText : "受理");
		return true;
	}
	
	/**
	 * 受理规则
	 * ((((('tmp_Pro_FlagType' = "主办") OR ('tmp_Pro_FlagType' = "协办")) AND ('tmp_Pro_FlagActive' = 1)) AND ('tmp_Pro_ProcessType' = "DEAL")) AND ('tmp_Pro_FlagBegin' = 0)) AND ((('tmp_Pro_FlagPredefined' = 0) AND ('tmp_Pro_PrevPhaseNo' != "BEGIN")) OR (('tmp_Pro_FlagPredefined' = 1) AND ('tmp_Pro_StProcessAction' != "新建")))
	 */
	@Override
	public boolean checkPermmsion(String baseId, String baseSchema, String processIdStr, String userLoginName, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		if (StringUtils.isNotBlank(baseId) && StringUtils.isNotBlank(processIdStr)) {
			DealProcessManager dpManager = new DealProcessManager();
			DealProcess dp = dpManager.getDealProcessById(processIdStr.substring(3));
			log.info("flagAssignType=主办 or 协办,flagActive=1,processType=DEAL,stDate=,((flagPredefined=0 and prePhaseNo!='BEGIN') or (flagPredefined=1 and stProcessAction!='新建'))");
			if (dp != null) {
				Long flagActive = dp.getFlagActive();
				String processType = dp.getProcessType();
				String stDate = dp.getBgDate();
				String stProcessAction = dp.getStProcessAction();
				String flagAssignType = dp.getFlagAssignType();
				Long flagPredefined = dp.getFlagPredefined();
				String prePhaseNo = dp.getPrePhaseNo();
				if (("主办".equals(flagAssignType) || "协办".equals(flagAssignType))
						&& flagActive == 1
						&& "DEAL".equals(processType)
						&& "".equals(stDate)
						&& ((flagPredefined == 0 && !"BEGIN".equals(prePhaseNo)) || (flagPredefined == 1 && !"新建".equals(stProcessAction)))) {
					return true;
				}
				log.error("flagAssignType="+flagAssignType+",flagActive="+flagActive+",processType="+processType+",stDate="+stDate+",((flagPredefined="+flagPredefined+" and prePhaseNo="+prePhaseNo+") or (flagPredefined="+flagPredefined+" and stProcessAction="+stProcessAction+"))");
			}
		} 
		log.error("没有权限，受理动作执行失败！baseId="+baseId+",baseSchema="+baseSchema+",processId="+processIdStr);
		return false;
	}
}

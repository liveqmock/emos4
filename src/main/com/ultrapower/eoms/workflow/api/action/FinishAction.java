package com.ultrapower.eoms.workflow.api.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.workflow.api.bean.BaseAttachmentInfo;
import com.ultrapower.eoms.workflow.api.bean.BaseFieldInfo;
import com.ultrapower.eoms.workflow.api.bean.DealObject;
import com.ultrapower.eoms.workflow.design.control.DealProcessManager;
import com.ultrapower.eoms.workflow.design.model.DealProcess;


	
public class FinishAction extends BaseAction {
	
	private final static Logger log = LoggerFactory.getLogger(FinishAction.class);
	
	@Override
	public boolean setActionField(String baseSchema, String baseId, String userLoginName, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		setFieldValue("tmp_BaseActionBtn_Char", "FINISH");
		setFieldValue("tmp_BaseActionBtn_Fix_Char", StringUtils.isNotBlank(actionText) ? actionText : "完成");
		return true;
	}
	
	/**
	 * 完成规则
	 *  (((('tmp_Pro_FlagActive' = 1) AND (('tmp_Pro_FlagType' = "主办") OR ('tmp_Pro_FlagType' = "协办"))) AND ('tmp_Pro_FlagPredefined' = 0)) AND ('tmp_Pro_ProcessType' = "DEAL")) AND ('tmp_Pro_PrevPhaseNo' != "BEGIN")
	 */
	@Override
	public boolean checkPermmsion(String baseId, String baseSchema, String processIdStr, String userLoginName, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		if (StringUtils.isNotBlank(baseId) && StringUtils.isNotBlank(processIdStr)) {
			DealProcessManager dpManager = new DealProcessManager();
			DealProcess dp = dpManager.getDealProcessById(processIdStr.substring(3));
			log.info("flagActive=1,flagPredefined=0,processType=DEAL,prePhaseNo!=BEGIN,flagAssignType=主办 or 协办");
			if (dp != null) {
				Long flagActive = dp.getFlagActive();
				Long flagPredefined = dp.getFlagPredefined();
				String prePhaseNo = dp.getPrePhaseNo();
				String flagAssignType = dp.getFlagAssignType();
				String processType = dp.getProcessType();
				if (flagActive == 1
						&& flagPredefined == 0
						&& "DEAL".equals(processType)
						&& !"BEGIN".equals(prePhaseNo)
						&& ("主办".equals(flagAssignType) || "协办".equals(flagAssignType))) {
					return true;
				}
				log.error("flagActive="+flagActive+",flagPredefined="+flagPredefined+",processType="+processType+",prePhaseNo="+prePhaseNo+",flagAssignType="+flagAssignType);
			}
		} 
		log.error("没有权限，完成动作执行失败！");
		return false;
	}
}

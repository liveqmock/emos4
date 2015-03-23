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


	
public class NoticeAction extends BaseAction {
	
	private final static Logger log = LoggerFactory.getLogger(NoticeAction.class);
	
	@Override
	public boolean setActionField(String baseSchema, String baseId, String userLoginName, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		setFieldValue("tmp_BaseActionBtn_Char", "NOTICE");
		setFieldValue("tmp_BaseActionBtn_Fix_Char", StringUtils.isNotBlank(actionText) ? actionText : "阶段处理");
		return true;
	}
	
	/**
	 * 阶段通知(阶段处理)规则
	 * (((('tmp_Pro_FlagType' = "主办") OR ('tmp_Pro_FlagType' = "协办")) AND ('tmp_Pro_FlagActive' = 1)) AND ('tmp_Pro_ProcessType' = "DEAL")) AND ((('tmp_Pro_FlagPredefined' = 0) AND ('tmp_Pro_PrevPhaseNo' != "BEGIN")) OR (('tmp_Pro_FlagPredefined' = 1) AND ('tmp_Pro_StProcessAction' != "新建"))) 
	 */
	@Override
	public boolean checkPermmsion(String baseId, String baseSchema, String processIdStr, String userLoginName, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		if (StringUtils.isNotBlank(baseId) && StringUtils.isNotBlank(processIdStr)) {
			DealProcessManager dpManager = new DealProcessManager();
			DealProcess dp = dpManager.getDealProcessById(processIdStr.substring(3));
			log.info("flagActive=1,processType=DEAL,flagAssignType=主办 or 协办,((flagPredefined=0,prePhaseNo!=BEGIN) or (flagPredefined=1,stProcessAction!=新建))");
			if (dp != null) {
				Long flagActive = dp.getFlagActive();
				String processType = dp.getProcessType();
				String flagAssignType = dp.getFlagAssignType();
				Long flagPredefined = dp.getFlagPredefined();
				String prePhaseNo = dp.getPrePhaseNo();
				String stProcessAction = dp.getStProcessAction();
				if (flagActive == 1 && "DEAL".equals(processType) && ("主办".equals(flagAssignType) || "协办".equals(flagAssignType)) && ((flagPredefined == 0 && !"BEGIN".equals(prePhaseNo)) || (flagPredefined == 1 && !"新建".equals(stProcessAction)))) {
					return true;
				}
				log.error("flagActive="+flagActive+",processType="+processType+",flagAssignType="+flagAssignType+",flagPredefined="+flagPredefined+",prePhaseNo="+prePhaseNo+",stProcessAction="+stProcessAction);
			}
		} 
		log.error("没有权限，阶段处理动作执行失败！baseId="+baseId+",baseSchema="+baseSchema+",processId="+processIdStr);
		return false;
	}
}

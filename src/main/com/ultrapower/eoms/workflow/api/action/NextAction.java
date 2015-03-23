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


	
public class NextAction extends BaseAction {
	
	private final static Logger log = LoggerFactory.getLogger(NextAction.class);
	
	@Override
	public boolean setActionField(String baseSchema, String baseId, String userLoginName, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		setFieldValue("tmp_BaseActionBtn_Char", "NEXT");
		setFieldValue("tmp_BaseActionBtn_Fix_Char", StringUtils.isNotBlank(actionText) ? actionText : "下一步");
		setFieldValue("tmp_AssigneesInfo", getAssignStr(dealObjs, "NEXT"));
		return true;
	}
	
	/**
	 * 下一步规则
	 * (('tmp_Pro_FlagActive' = 1) AND ('tmp_Pro_FlagPredefined' = 1)) AND ('tmp_Pro_ProcessType' = "DEAL")
	 */
	@Override
	public boolean checkPermmsion(String baseId, String baseSchema, String processIdStr, String userLoginName, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		if (StringUtils.isBlank(baseId) && StringUtils.isBlank(processIdStr)) {
			return true;
		}
		if (StringUtils.isNotBlank(baseId) && StringUtils.isNotBlank(processIdStr)) {
			DealProcessManager dpManager = new DealProcessManager();
			DealProcess dp = dpManager.getDealProcessById(processIdStr.substring(3));
			log.info("flagActive=1,flagPredefined=1,processType=DEAL");
			if (dp != null) {
				Long flagActive = dp.getFlagActive();
				Long flagPredefined = dp.getFlagPredefined();
				String processType = dp.getProcessType();
				if (flagActive == 1 && flagPredefined == 1 && "DEAL".equals(processType)) {
					return true;
				}
				log.error("flagActive="+flagActive+",flagPredefined="+flagPredefined+",processType="+processType);
			}
		} 
		log.error("没有权限，下一步动作执行失败！baseId="+baseId+",baseSchema="+baseSchema+",processId="+processIdStr);
		return false;
	}
}

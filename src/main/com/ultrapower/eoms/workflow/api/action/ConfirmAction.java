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


	
public class ConfirmAction extends BaseAction {
	
	private final static Logger log = LoggerFactory.getLogger(ConfirmAction.class);
	
	@Override
	public boolean setActionField(String baseSchema, String baseId, String userLoginName, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		setFieldValue("tmp_BaseActionBtn_Char", "CONFIRM");
		setFieldValue("tmp_BaseActionBtn_Fix_Char", StringUtils.isNotBlank(actionText) ? actionText : "确认");
		return true;
	}
	
	/**
	 * 确认规则
	 * (('tmp_Pro_FlagType' = "抄送") AND ('tmp_Pro_FlagActive' = 1)) AND ('tmp_Pro_ProcessType' = "DEAL") 
	 */
	@Override
	public boolean checkPermmsion(String baseId, String baseSchema, String processIdStr, String userLoginName, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		if (StringUtils.isNotBlank(baseId) && StringUtils.isNotBlank(processIdStr)) {
			DealProcessManager dpManager = new DealProcessManager();
			DealProcess dp = dpManager.getDealProcessById(processIdStr.substring(3));
			log.info("flagActive=1,processType=DEAL,flagAssignType=抄送");
			if (dp != null) {
				Long flagActive = dp.getFlagActive();
				String processType = dp.getProcessType();
				String flagAssignType = dp.getFlagAssignType();
				if (flagActive == 1 && "DEAL".equals(processType) && "抄送".equals(flagAssignType) ) {
					return true;
				}
				log.error("flagActive="+flagActive+",processType="+processType+",flagAssignType="+flagAssignType);
			}
		} 
		log.error("没有权限，确认动作执行失败！baseId="+baseId+",baseSchema="+baseSchema+",processId="+processIdStr);
		return false;
	}
}

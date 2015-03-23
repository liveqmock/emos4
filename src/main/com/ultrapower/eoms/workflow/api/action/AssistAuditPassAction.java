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


	
public class AssistAuditPassAction extends BaseAction {
	
	private final static Logger log = LoggerFactory.getLogger(AssistAuditPassAction.class);
	
	@Override
	public boolean setActionField(String baseSchema, String baseId, String userLoginName, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		setFieldValue("tmp_BaseActionBtn_Char", "ORGANIZEAUDITINGPASS");
		setFieldValue("tmp_BaseActionBtn_Fix_Char", StringUtils.isNotBlank(actionText) ? actionText : "会审通过");
		return true;
	}
	
	@Override
	public boolean checkPermmsion(String baseId, String baseSchema, String processIdStr, String userLoginName, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		if (StringUtils.isNotBlank(baseId) && StringUtils.isNotBlank(processIdStr)) {
			DealProcessManager dpManager = new DealProcessManager();
			DealProcess dp = dpManager.getDealProcessById(processIdStr.substring(3));
			log.info("flagActive=1,flagPredefined=0,processType=AUDITING,flagAssignType=会审");
			if (dp != null) {
				Long flagActive = dp.getFlagActive();
				Long flagPredefined = dp.getFlagPredefined();
				String flagAssignType = dp.getFlagAssignType();
				String processType = dp.getProcessType();
				if (flagActive == 1
						&& flagPredefined == 0
						&& "AUDITING".equals(processType)
						&& "会审".equals(flagAssignType)) {
					return true;
				}
				log.error("flagActive="+flagActive+",flagPredefined="+flagPredefined+",processType="+processType+",flagAssignType="+flagAssignType);
			}
		} 
		log.error("没有权限，会审动作执行失败！");
		return false;
	}
}

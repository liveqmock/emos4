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


	
public class ReAuditAction extends BaseAction {
	
	private final static Logger log = LoggerFactory.getLogger(ReAuditAction.class);
	
	@Override
	public boolean setActionField(String baseSchema, String baseId, String userLoginName, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		setFieldValue("tmp_BaseActionBtn_Char", "REAUDIT");
		setFieldValue("tmp_BaseActionBtn_Fix_Char", StringUtils.isNotBlank(actionText) ? actionText : "转审");
		setFieldValue("tmp_AssigneesInfo", getAssignStr(dealObjs, "REAUDIT"));
		return true;
	}
	
	@Override
	public boolean checkPermmsion(String baseId, String baseSchema, String processIdStr, String userLoginName, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		if (StringUtils.isNotBlank(baseId) && StringUtils.isNotBlank(processIdStr)) {
			DealProcessManager dpManager = new DealProcessManager();
			DealProcess dp = dpManager.getDealProcessById(processIdStr.substring(3));
			log.info("flagActive=1,flagToAuditing=1,processType=AUDITING");
			if (dp != null) {
				Long flagActive = dp.getFlagActive();
				String processType = dp.getProcessType();
				Long flagToAuditing = dp.getFlagToAuditing();
				if (flagActive == 1 && flagToAuditing == 1 && "AUDITING".equals(processType)) {
					return true;
				}
				log.error("flagActive="+flagActive+",flagToAuditing="+flagToAuditing+",processType="+processType);
			}
		} 
		log.error("没有权限，转审动作执行失败！baseId="+baseId+",baseSchema="+baseSchema+",processId="+processIdStr);
		return false;
	}
}

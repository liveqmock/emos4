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


	
public class CloseAction extends BaseAction {
	
	private final static Logger log = LoggerFactory.getLogger(CloseAction.class);
	
	@Override
	public boolean setActionField(String baseSchema, String baseId, String userLoginName, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		setFieldValue("tmp_BaseActionBtn_Char", "CLOSE");
		setFieldValue("tmp_BaseActionBtn_Fix_Char", StringUtils.isNotBlank(actionText) ? actionText : "归档");
		return true;
	}
	
	/**
	 * 归档规则
	 *  ((('tmp_Pro_FlagActive' = 1) AND ('tmp_Pro_Flag09Close' = "1")) AND ('tmp_Pro_ProcessType' = "DEAL")) AND ((('BaseStatus' = "已完成") OR ('BaseStatus' = "审批通过")) OR ('BaseStatus' = "草稿"))
	 */
	@Override
	public boolean checkPermmsion(String baseId, String baseSchema, String processIdStr, String userLoginName, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		if (StringUtils.isBlank(baseId) && StringUtils.isBlank(processIdStr)) {
			return true;
		}
		if (StringUtils.isNotBlank(baseId) && StringUtils.isNotBlank(processIdStr)) {
			DealProcessManager dpManager = new DealProcessManager();
			DealProcess dp = dpManager.getDealProcessById(processIdStr.substring(3));
			log.info("flagActive=1,flagClose=1,processType=DEAL,baseStatus=已完成or草稿or审批通过");
			if (dp != null) {
				Long flagActive = dp.getFlagActive();
				Long flagClose = dp.getFlagClose();
				String processType = dp.getProcessType();
				String baseStatus = getFieldValue("BaseStatus");
				if (flagActive == 1
					&& flagClose == 1
					&& "DEAL".equals(processType)
					&& ("已完成".equals(baseStatus) || "草稿".equals(baseStatus) || "审批通过".equals(baseStatus))) {
					return true;
				}
				log.error("flagActive="+flagActive+",flagClose="+flagClose+",processType="+processType+",baseStatus="+baseStatus);
			}
		} 
		log.error("没有权限，归档动作执行失败！");
		return false;
	}
}

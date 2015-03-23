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


	
public class HastenAction extends BaseAction {
	
	private final static Logger log = LoggerFactory.getLogger(HastenAction.class);
	
	@Override
	public boolean setActionField(String baseSchema, String baseId, String userLoginName, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		setFieldValue("tmp_BaseActionBtn_Char", "HASTEN");
		setFieldValue("tmp_BaseActionBtn_Fix_Char", StringUtils.isNotBlank(actionText) ? actionText : "催办");
		return true;
	}
	
	private boolean setTargetId(String baseSchema, String baseId,
			String userLoginName, List<DealObject> dealObjs, String processId) {
		DealProcessManager dpManager = new DealProcessManager();
		DealProcess dp = dpManager.getDealProcessById(processId);
		String phaseNo = dp.getPhaseNo();
		String sql = "SELECT processId, phaseNo, stepId, baseId, baseSchema, flagActive, assigneeId, assigngroupId"
				+ " FROM BS_T_WF_DEALPROCESS  dp"
				+ " WHERE ('" + baseId + "' = dp.baseId) "
				+ " AND ('" + baseSchema + "' = dp.baseSchema) "
				+ " AND (dp.flagActive = 1) "
				+ " AND (dp.prephaseNo = '" + phaseNo + "')";
		return super.setTargetId(sql, "HASTEN", dealObjs);
	}
	
	/**
	 * 催办规则
	 * ('tmp_Pro_ProcessType' = "DEAL") AND ('tmp_Pro_FlagActive' != 1)
	 */
	@Override
	public boolean checkPermmsion(String baseId, String baseSchema, String processIdStr, String userLoginName, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		if (StringUtils.isNotBlank(baseId) && StringUtils.isNotBlank(processIdStr)) {
			DealProcessManager dpManager = new DealProcessManager();
			DealProcess dp = dpManager.getDealProcessById(processIdStr.substring(3));
			log.info("flagActive!=1,processType=DEAL");
			if (dp != null) {
				Long flagActive = dp.getFlagActive();
				String processType = dp.getProcessType();
				if (flagActive != 1 && "DEAL".equals(processType)) {
					return this.setTargetId(baseSchema, baseId, userLoginName, dealObjs, processIdStr.substring(3));
				}
				log.error("flagActive="+flagActive+",processType="+processType);
			}
		} 
		log.error("没有权限，催办动作执行失败！baseId="+baseId+",baseSchema="+baseSchema+",processId="+processIdStr);
		return false;
	}
}

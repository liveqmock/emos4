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


	
public class SuggestAction extends BaseAction {
	
	private final static Logger log = LoggerFactory.getLogger(SuggestAction.class);
	
	@Override
	public boolean setActionField(String baseSchema, String baseId, String userLoginName, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		setFieldValue("tmp_BaseActionBtn_Char", "SUGGEST");
		setFieldValue("tmp_BaseActionBtn_Fix_Char", StringUtils.isNotBlank(actionText) ? actionText : "阶段建议");
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
		return super.setTargetId(sql, "SUGGEST", dealObjs);
	}
	
	/**
	 * 阶段建议规则
	 * (('tmp_Pro_ProcessType' = "DEAL") AND ('tmp_Pro_FlagActive' != 1)) AND (('tmp_Pro_FlagPredefined' = 1) OR (('tmp_Pro_FlagPredefined' = 0) AND ('tmp_Pro_PrevPhaseNo' = "BEGIN")))
	 */
	@Override
	public boolean checkPermmsion(String baseId, String baseSchema, String processIdStr, String userLoginName, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		if (StringUtils.isNotBlank(baseId) && StringUtils.isNotBlank(processIdStr)) {
			DealProcessManager dpManager = new DealProcessManager();
			DealProcess dp = dpManager.getDealProcessById(processIdStr.substring(3));
			log.info("flagActive!=1,processType=DEAL,(flagPredefined=1 or (flagPredefined=0,prePhaseNo=BEGIN))");
			if (dp != null) {
				Long flagActive = dp.getFlagActive();
				String processType = dp.getProcessType();
				Long flagPredefined = dp.getFlagPredefined();
				String prePhaseNo = dp.getPrePhaseNo();
				if (flagActive != 1
						&& "DEAL".equals(processType)
						&& (flagPredefined == 1 || (flagPredefined == 0 && "BEGIN".equals(prePhaseNo)))) {
					return this.setTargetId(baseSchema, baseId, userLoginName, dealObjs, processIdStr.substring(3));
				}
				log.error("flagActive="+flagActive+",processType="+processType+",flagPredefined="+flagPredefined+",prePhaseNo="+prePhaseNo);
			}
		} 
		log.error("没有权限，阶段建议动作执行失败！baseId="+baseId+",baseSchema="+baseSchema+",processId="+processIdStr);
		return false;
	}
}

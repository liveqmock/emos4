package com.ultrapower.eoms.workflow.api.action;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.workflow.api.bean.BaseAttachmentInfo;
import com.ultrapower.eoms.workflow.api.bean.BaseFieldInfo;
import com.ultrapower.eoms.workflow.api.bean.DealObject;
import com.ultrapower.eoms.workflow.design.control.DealProcessManager;
import com.ultrapower.eoms.workflow.design.model.DealProcess;
import com.ultrapower.remedy4j.core.RemedySession;


	
public class SendBackAction extends BaseAction {
	
	private final static Logger log = LoggerFactory.getLogger(SendBackAction.class);
	
	@Override
	public boolean setActionField(String baseSchema, String baseId, String userLoginName, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		setFieldValue("tmp_BaseActionBtn_Char", "SENDBACK");
		setFieldValue("tmp_BaseActionBtn_Fix_Char", StringUtils.isNotBlank(actionText) ? actionText : "退回");
		return true;
	}

	private boolean setTargetId(String baseSchema, String baseId,
			String userLoginName, List<DealObject> dealObjs, String processId) {
		DealProcessManager dpManager = new DealProcessManager();
		DealProcess dp = dpManager.getDealProcessById(processId);
		String phaseNo = dp.getPhaseNo();
		String sql = "SELECT processId, phaseNo, flagEndDuplicated, stepId, baseId, baseSchema, flagActive, assigneeId, assigngroupId"
				+ " FROM BS_T_WF_DEALPROCESS dp"
				+ " WHERE ('" + baseId + "' = dp.baseId) "
				+ " AND ('" + baseSchema + "' = dp.baseSchema) "
				+ " AND (dp.flagEndDuplicated = 1) "
				+ " AND (dp.flagActive = 0) "
				+ " AND (dp.processStatus != '已追回') "
				+ " AND (dp.flagAssignType != '抄送') "
				+ " AND (dp.flagAssignType != '审批') "
				+ " AND (dp.prephaseNo = '" + phaseNo + "')";
		return super.setTargetId(sql, "SENDBACK", dealObjs);
	}
	
	/**
	 * (((('tmp_Pro_FlagPredefined' = 0) AND (('tmp_Pro_FlagType' = "主办") OR ('tmp_Pro_FlagType' = "协办"))) AND (('tmp_Pro_FlagActive' = 1) OR ('tmp_Pro_FlagActive' = 2))) AND ('tmp_Pro_Flag05TurnDown' = "1")) AND ('tmp_Pro_ProcessType' = "DEAL") 
	 */
	@Override
	public boolean checkPermmsion(String baseId, String baseSchema, String processIdStr, String userLoginName, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		if (StringUtils.isNotBlank(baseId) && StringUtils.isNotBlank(processIdStr)) {
			DealProcessManager dpManager = new DealProcessManager();
			DealProcess dp = dpManager.getDealProcessById(processIdStr.substring(3));
			log.info("flagPredefined=0,flagActive=1 or 2,flagAssignType=主办 or 协办,processType=DEAL,flagTurnDown=1");
			if (dp != null) {
				Long flagActive = dp.getFlagActive();
				Long flagPredefined = dp.getFlagPredefined();
				String flagAssignType = dp.getFlagAssignType();
				String processType = dp.getProcessType();
				Long flagTurnDown = dp.getFlagTurnDown();
				if (flagTurnDown == 1 && processType.equals("DEAL") && flagPredefined == 0 && (flagActive == 1 || flagActive == 2) && (("主办".equals(flagAssignType))||("协办".equals(flagAssignType)))) {
					return setTargetId(baseSchema, baseId, userLoginName, dealObjs, processIdStr.substring(3));
				}
				log.error("flagPredefined="+flagPredefined+",flagActive="+flagActive+",flagAssignType="+flagAssignType+",processType="+processType+",flagTurnDown="+flagTurnDown);
			}
		} 
		log.error("没有权限，退回动作执行失败！");
		return false;
	}
}

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


	
public class RejectAction extends BaseAction {
	
	private final static Logger log = LoggerFactory.getLogger(RejectAction.class);
	
	@Override
	public boolean setActionField(String baseSchema, String baseId, String userLoginName, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		setFieldValue("tmp_BaseActionBtn_Char", "REJECT");
		setFieldValue("tmp_BaseActionBtn_Fix_Char", StringUtils.isNotBlank(actionText) ? actionText : "驳回");
		return true;
	}

	private boolean setTargetId(String baseSchema, String baseId,
			String userLoginName, List<DealObject> dealObjs, String processId) {
		DealProcessManager dpManager = new DealProcessManager();
		DealProcess dp = dpManager.getDealProcessById(processId);
		String phaseNo = dp.getPrePhaseNo();
		String sql = "SELECT processId, phaseNo, flagEndDuplicated, stepId, baseId, baseSchema, flagActive, assigneeId, assigngroupId"
				+ " FROM BS_T_WF_DEALPROCESS dp"
				+ " WHERE ('" + baseId + "' = dp.baseId) "
				+ " AND ('" + baseSchema + "' = dp.baseSchema) "
				+ " AND (dp.flagEndDuplicated = 1) "
				//2011-1-14 fanying 优化sql，去掉in语法结构，改用or关键字
				//+ " AND (dp.flagActive in (0,2,3,10)) "
				+ " AND (dp.flagActive=0 or dp.flagActive=2 or dp.flagActive=3 or dp.flagActive=10) "
				+ " AND (dp.phaseNo = '" + phaseNo + "')";
		return super.setTargetId(sql, "REJECT", dealObjs);
	}
	
	/**
	 *  --自由，派单驳回
SELECT T342.C1, C700020007, C700020005, C700020018, C700020003, C710020002
  FROM T342
 WHERE ('000000000000091' = T342.C700020001)
   AND ('WF4:App_Base_bak' = T342.C700020002)
   AND (T342.C700020003 = '467acb025E7291') --环节号
   AND (T342.C700020077 = 1) --是否最后复制品
   AND ((T342.C700020020 = 2) OR (T342.C700020020 = 0) OR --flagActive
       (T342.C700020020 = 3) OR (T342.C700020020 = 10))
 ORDER BY C1 DESC
 
  --固定驳回
SELECT T342.C1, C700020007, C700020005, C700020018, C700020003, C710020002
  FROM T342
 WHERE ('000000000006907' = T342.C700020001)
   AND ('WF4:EL_TTM_TTH' = T342.C700020002)
   AND (T342.C700020020 = 0) --flagActive
   AND ('dp_1' = T342.C700020003) --环节号
   AND (T342.C700020077 = 1) --最后复制品
 ORDER BY C700020001 ASC, 1 ASC
	 */
	@Override
	public boolean checkPermmsion(String baseId, String baseSchema, String processIdStr, String userLoginName, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		if (StringUtils.isNotBlank(baseId) && StringUtils.isNotBlank(processIdStr)) {
			DealProcessManager dpManager = new DealProcessManager();
			DealProcess dp = dpManager.getDealProcessById(processIdStr.substring(3));
			if (dp != null) {
				Long flagActive = dp.getFlagActive();
				Long flagPredefined = dp.getFlagPredefined();
				String flagAssignType = dp.getFlagAssignType();
				String processType = dp.getProcessType();
				Long flagTurnUp = dp.getFlagTurnUp();
				String processStatus = dp.getProcessStatus();
				String prePhaseNo = dp.getPrePhaseNo();
				
				//固定驳回
				if (flagPredefined == 1 && flagActive == 1 && flagTurnUp == 1 && processType.equals("DEAL") && !processStatus.equals("草稿")) {
					return setTargetId(baseSchema, baseId, userLoginName, dealObjs, processIdStr.substring(3));
				} else if (flagPredefined == 0 && "会审".equals(flagAssignType) && "AUDITING".equals(processType)) {
					log.info("会审驳回");
					return true;
				} else if ("审批".equals(flagAssignType) && flagActive == 1 && "AUDITING".equals(processType)) {
					log.info("审批驳回");
					return setTargetId(baseSchema, baseId, userLoginName, dealObjs, processIdStr.substring(3));
				} else if (flagPredefined == 0 && !"BEGIN".equals(prePhaseNo) && flagActive == 1 && (("主办".equals(flagAssignType))||("协办".equals(flagAssignType))) && flagTurnUp == 1 && "DEAL".equals(processType)) {
					log.info("派单驳回");
					return setTargetId(baseSchema, baseId, userLoginName, dealObjs, processIdStr.substring(3));
				}
			}
		} 
		log.error("没有权限，驳回动作执行失败！");
		return false;
	}
}

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


	
public class ChaseAction extends BaseAction {
	
	private final static Logger log = LoggerFactory.getLogger(ChaseAction.class);
	
	@Override
	public boolean setActionField(String baseSchema, String baseId, String userLoginName, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		setFieldValue("tmp_BaseActionBtn_Char", "CHASE");
		setFieldValue("tmp_BaseActionBtn_Fix_Char", StringUtils.isNotBlank(actionText) ? actionText : "追回");
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
		return super.setTargetId(sql, "CHASE", dealObjs);
	}
	
	/**
	 * 追回规则
	 *  --自由，处理追回(固定追回)
SELECT T342.C1, C700020007, C700020005, C700020018, C700020003, C710020002
  FROM T342 --WF4:App_DealProcess
 WHERE ('000000000000092' = T342.C700020001) --baseid
   AND ('WF4:App_Base_bak' = T342.C700020002) --baseschema
   AND (T342.C700020020 = 1) --flagActive
   AND (T342.C700020004 = '467ae142d0E7314') --prephaseno
 ORDER BY C1 DESC
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
				Long flagRecall = dp.getFlagRecall();
				String processType = dp.getProcessType();
				String recallType = processIdStr.substring(0, 2);
				
				if (flagPredefined == 1) {
					log.info("flagPredefined=1,flagActive=0,flagRecall=1,processType=DEAL,recallType=21");
				} else {
					log.info("flagPredefined=0,flagActive=2,flagRecall=1,processType=DEAL,recallType=21,flagAssignType=主办 or 协办");
				}
				
				//固定追回
				if (flagPredefined == 1 && flagActive == 0 && flagRecall == 1&& "DEAL".equals(processType) && "21".equals(recallType)) {
					return setTargetId(baseSchema, baseId, userLoginName, dealObjs, processIdStr.substring(3));
				} else if (flagPredefined == 0
						&& ("主办".equals(flagAssignType) || "协办".equals(flagAssignType)) 
						&& flagActive == 2 && flagRecall == 1 && "DEAL".equals(processType) && "21".equals(recallType)) {
					//自由追回
					return setTargetId(baseSchema, baseId, userLoginName, dealObjs, processIdStr.substring(3));
				}
				
				if (flagPredefined == 1) {
					log.error("flagPredefined="+flagPredefined+",flagActive="+flagActive+",flagRecall="+flagRecall+",processType="+processType+",recallType="+recallType);
				} else {
					log.error("flagPredefined="+flagPredefined+",flagActive="+flagActive+",flagRecall="+flagRecall+",processType="+processType+",recallType="+recallType+",flagAssignType="+flagAssignType);
				}
			}
		} 
		log.error("没有权限，追回动作执行失败！");
		return false;
	}
}

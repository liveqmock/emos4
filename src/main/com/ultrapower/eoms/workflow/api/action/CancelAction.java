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


	
public class CancelAction extends BaseAction {
	
	private final static Logger log = LoggerFactory.getLogger(CancelAction.class);
	
	@Override
	public boolean setActionField(String baseSchema, String baseId, String userLoginName, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		setFieldValue("tmp_BaseActionBtn_Char", "CANCEL");
		setFieldValue("tmp_BaseActionBtn_Fix_Char", StringUtils.isNotBlank(actionText) ? actionText : "作废");
		return true;
	}
	
	/**
	 * 作废规则
	 *  (('tmp_Pro_FlagActive' = 1) AND ('tmp_Pro_Flag08Cancel' = "1")) AND ($OPERATION$ != "CREATE")
	 */
	@Override
	public boolean checkPermmsion(String baseId, String baseSchema, String processIdStr, String userLoginName, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		if (StringUtils.isBlank(baseId) && StringUtils.isBlank(processIdStr)) {
			return true;
		}
		if (StringUtils.isNotBlank(baseId) && StringUtils.isNotBlank(processIdStr)) {
			DealProcessManager dpManager = new DealProcessManager();
			DealProcess dp = dpManager.getDealProcessById(processIdStr.substring(3));
			log.info("(flagPredefined=0,flagActive=1,flagCancel=1) or (flagPredefined=1,flagActive=1)");
			if (dp != null) {
				Long flagActive = dp.getFlagActive();
				Long flagCancel = dp.getFlagCancel();
				Long flagPredefined = dp.getFlagPredefined();
				if ((flagPredefined == 0 && flagActive == 1 && flagCancel == 1) || (flagPredefined == 1 && flagActive == 1)) {
					return true;
				}
				log.error("flagPredefined="+flagPredefined+",flagActive="+flagActive+",flagCancel="+flagCancel);
			}
		} 
		log.error("没有权限，作废动作执行失败！");
		return false;
	}
}

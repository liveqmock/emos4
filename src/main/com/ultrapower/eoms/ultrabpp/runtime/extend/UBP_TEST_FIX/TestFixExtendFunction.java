package com.ultrapower.eoms.ultrabpp.runtime.extend.UBP_TEST_FIX;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;
import com.ultrapower.eoms.ultrabpp.runtime.service.ExtendFunction;

public class TestFixExtendFunction implements ExtendFunction {

	public void beginOpen(WorksheetDisplayContext displayCxt) {

	}

	public void endOpen(WorksheetDisplayContext displayCxt) {
		String tmpStatus = displayCxt.getFieldMap().get("BASESTATUS");
		List<ActionModel> tempListFree = displayCxt.getFreeActionList();
		List<ActionModel> tempListFix = displayCxt.getFixActionList();

		if ("step6".equals(displayCxt.getCurrentTask().getStepNo())) {
			for (int i = 0; i < tempListFree.size(); i++) {
				ActionModel actionModel = tempListFree.get(i);
				System.out.println(actionModel.getActionName());
				if ("MAKECOPY".equals(actionModel.getActionName())) {
					tempListFree.remove(actionModel);
					i--;
				}
			}
			displayCxt.setFreeActionList(tempListFree);
		}

		Map<String, String> fields = displayCxt.getFieldMap();
		if ("step0".equals(displayCxt.getCurrentTask().getStepNo())) {
			String reCheck = fields.get("checkResult");
			String money = fields.get("MONEY_RESULT");
			fields.put("approvalResult", "0".equals(money) ? "通过" : "未通过");
			fields.put("recheckResult", reCheck);
			displayCxt.setFieldMap(fields);
		} else {
			fields.remove("approvalResult");
			fields.remove("recheckResult");
			displayCxt.setFieldMap(fields);
		}
		
		if ("step6".equals(displayCxt.getCurrentTask().getStepNo())) {
			
		}

		if ("step5".equals(displayCxt.getCurrentTask().getStepNo())) {
			// ActionModel actionModel = new ActionModel();
			// for (ActionModel temp : tempListFix) {
			// if ("reCheckPass".equals(temp.getActionName())) {
			// actionModel.setActionName("reCheckPass");
			// actionModel.setActionType(temp.getActionType());
			// actionModel.setBaseSchema(temp.getBaseSchema());
			// actionModel.setLabel("复核不通过");
			// actionModel.setDescription("复核不通过");
			// actionModel.setHasDeploy(temp.getHasDeploy());
			// actionModel.setHasNext(temp.getHasNext());
			// actionModel.setId(temp.getId());
			// actionModel.setIsClose(temp.getIsClose());
			// actionModel.setIsFree(temp.getIsFree());
			// actionModel.setOperate(temp.getOperate());
			// actionModel.setStepNo(temp.getStepNo());
			// tempListFix.add(actionModel);
			// displayCxt.setFixActionList(tempListFix);
			// break;
			// }
			// }
		}

	}

	public void commitPhase1(WorksheetCommitContext commitCxt) {
	}

	public void commitPhase2(WorksheetCommitContext commitCxt) {
	}

	public void commitPhase3(WorksheetCommitContext commitCxt) {
	}

}

package com.ultrapower.eoms.ultrabpp.runtime.extend.UBP_TEST_FREE;

import java.util.List;

import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;
import com.ultrapower.eoms.ultrabpp.runtime.service.ExtendFunction;

public class TestFreeExtendFunction implements ExtendFunction {

	public void beginOpen(WorksheetDisplayContext displayCxt) {

	}

	public void endOpen(WorksheetDisplayContext displayCxt) {
		String tmpStatus = displayCxt.getFieldMap().get("BASESTATUS");
		List<ActionModel> tempListFree = displayCxt.getFreeActionList();
		for (int i = 0; i < tempListFree.size(); i++) {
			ActionModel actionModel = tempListFree.get(i);
			System.out.println(actionModel.getActionName());
		}
		if ("草稿".equals(tmpStatus)) {
			for (int i = 0; i < tempListFree.size(); i++) {
				ActionModel actionModel = tempListFree.get(i);
				if ("CLOSE".equals(actionModel.getActionName())
						|| "ASSIGN".equals(actionModel.getActionName())) {
					tempListFree.remove(actionModel);
					i--;
				}
			}
			displayCxt.setFreeActionList(tempListFree);
		}
	}

	public void commitPhase1(WorksheetCommitContext commitCxt) {

	}

	public void commitPhase2(WorksheetCommitContext commitCxt) {

	}

	public void commitPhase3(WorksheetCommitContext commitCxt) {

	}

}

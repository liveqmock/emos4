package com.ultrapower.eoms.common.core.component.tree.web;

import java.util.Map;

import com.ultrapower.eoms.common.core.util.StringUtils;

import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.core.web.BaseAction;

public class PrivilegeTreeAction extends BaseAction
{
	public String commonTreeFrame() {
		Map valueMap = ActionContext.getMapPrivilege();
		String depids = StringUtils.checkNullString(this.getRequest().getParameter("depid"));
		if(valueMap != null) {
			Object obj = valueMap.get("depid");
			if(obj != null) {
				depids = "".equals(depids) ? (String) obj : (String) obj + "," + depids;
			}
		}
		return this.findRedirectPar("../common/tools/orgtree/commonTreeFrame.jsp?depids="+depids);
	}
}

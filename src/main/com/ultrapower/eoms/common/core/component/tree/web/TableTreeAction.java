package com.ultrapower.eoms.common.core.component.tree.web;

import com.ultrapower.eoms.common.core.component.tree.manager.DictionaryTreeImpl;
import com.ultrapower.eoms.common.core.component.tree.manager.TableTreeImpl;
import com.ultrapower.eoms.common.core.web.BaseAction;

public class TableTreeAction extends BaseAction{
	public void getTableTree() {
		TableTreeImpl tableTreeImpl = new TableTreeImpl();
		this.renderXML(tableTreeImpl.getChildXml(id));
	}
	public void getTableFldTree()
	{
		TableTreeImpl tableTreeImpl = new TableTreeImpl();
		this.renderXML(tableTreeImpl.getTableFldTreeStr(id));
	}
}

package com.ultrapower.eoms.common.core.component.tree.web;

import com.ultrapower.eoms.common.core.component.tree.manager.DictionaryTreeImpl;
import com.ultrapower.eoms.common.core.web.BaseAction;

/**
 * 数据字典树
 * @author 孙海龙
 * @version 2010-6-8 10:00:00 AM
 */
public class DictionaryTreeAction extends BaseAction{
	public void getDicTree()
	{
		String dictype = this.getRequest().getParameter("dictype");
		DictionaryTreeImpl dicTreeImpl = new DictionaryTreeImpl();
		this.renderXML(dicTreeImpl.getChildXml(id, dictype));
	}
	
	public void getAllDicItemTree()
	{
		String dtcode = this.getRequest().getParameter("dtcode");
		DictionaryTreeImpl dicTreeImpl = new DictionaryTreeImpl();
		this.renderXML(dicTreeImpl.getAllChildXml(dtcode));
	}
}

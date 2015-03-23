package com.ultrapower.eoms.common.core.component.tree.web;

import com.ultrapower.eoms.common.core.component.tree.manager.BusinessOrgTreeImpl;
import com.ultrapower.eoms.common.core.web.BaseAction;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2011-6-14 下午02:10:49
 * @descibe 
 */
public class BusinessOrgTreeAction extends BaseAction{

	public void getBusinessOrgTree()
	{
		BusinessOrgTreeImpl businessOrgTreeImpl = new BusinessOrgTreeImpl();
		this.renderXML(businessOrgTreeImpl.getBusinessOrgTreeXml(id));
	}
}

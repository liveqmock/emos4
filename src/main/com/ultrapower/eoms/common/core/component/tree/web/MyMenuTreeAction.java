package com.ultrapower.eoms.common.core.component.tree.web;

import com.ultrapower.eoms.common.core.component.tree.manager.MyMenuTreeImpl;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;

/**
 * 我的菜单树形结构
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jul 14, 2010 3:54:04 PM
 */
public class MyMenuTreeAction extends BaseAction{
	
	
	private static final long serialVersionUID = 1L;
	private MyMenuTreeImpl myMenuTreeService;
	private String fromId;
	private String toId;
	
	public void getMyMenuFolderTree()
	{
		UserSession userSession = this.getUserSession();
		String userId = "";
		if(userSession != null)
			userId = StringUtils.checkNullString(userSession.getPid());
		this.renderXML(myMenuTreeService.getMyMenuFolderTree(id, userId));
	}
	
	public void getMyMenuTree()
	{
		UserSession userSession = this.getUserSession();
		String userId = "";
		if(userSession != null)
			userId = StringUtils.checkNullString(userSession.getPid());
		this.renderXML(myMenuTreeService.getMyMenuTree(id, userId));
	}
	
	public void changeParentid(){
		myMenuTreeService.updateParentId(fromId, toId);
	}
	
	public void setMyMenuTreeService(MyMenuTreeImpl myMenuTreeService) {
		this.myMenuTreeService = myMenuTreeService;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}
}

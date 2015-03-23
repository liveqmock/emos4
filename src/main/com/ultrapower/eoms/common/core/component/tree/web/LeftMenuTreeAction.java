package com.ultrapower.eoms.common.core.component.tree.web;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.ultrapower.accredit.util.GetUserUtil;
import com.ultrapower.eoms.common.constants.ConstantsSynch;
import com.ultrapower.eoms.common.core.component.tree.manager.LeftMenuTreeImpl;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.MenuInfo;
import com.ultrapower.eoms.ultrasm.service.MenuManagerService;
import com.ultrapower.eoms.ultrasm.service.PrivilegeManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;

/**
 * 左侧菜单树
 * @author 朱朝辉 E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 2, 2010 1:54:59 PM
 */
public class LeftMenuTreeAction extends BaseAction{


	private static final long serialVersionUID = 1L;
	private RoleManagerService roleManagerService;
	private LeftMenuTreeImpl leftMenuTreeService;
	private UserManagerService userManagerService;
	private MenuInfo menuinfo;
	private MenuManagerService menuManagerService;
	private PrivilegeManagerService privilegeManagerService;
	private String shortcutHtml;
//	private IWfSortManager sortService = WorkFlowServiceClient.clientInstance().getSortService();
	/**
	 * 生成左侧树
	 */
	public void getLeftMenuTree(){
		menuinfo = menuManagerService.getMenuByID(id);
		if(menuinfo == null || !StringUtils.checkNullString(menuinfo.getClassname()).equals(""))
		{
			String classname = "";
			if(menuinfo == null)
			{
				String[] temp = id.split("\\,");
				if(temp.length > 1)
					classname=temp[0];
				    
			}
			else
			{
				classname = menuinfo.getClassname();
			}
			this.renderXML(leftMenuTreeService.getDynamicXmlTreeData(classname, id));
		}
		else
		{
			String openmenuid = StringUtils.checkNullString(this.getRequest().getParameter("openmenuid"));
			List<String> openIdList = null;
			if(!openmenuid.equals(id) && !"".equals(openmenuid))
			{
				openIdList = menuManagerService.getParentidListById(openmenuid);
			}
			
			String userPid = "";
			if(ConstantsSynch.isUip){
				String loginname = GetUserUtil.getUsername();
				if(StringUtils.checkNullString(loginname)!="")
					userPid = userManagerService.getPidByLoginName(loginname);
			}else{
				UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
				if(userSession!=null)
					userPid = userSession.getPid();
			}
			 
		    List<MenuDtree> treeList = privilegeManagerService.getMenuByNavigationID(userPid, id);
			if(openIdList != null && openIdList.size() > 0)
			{
				openIdList.add(openmenuid);
			}
			this.renderXML(leftMenuTreeService.getRoleLeftTreeXmlByCache(treeList, openIdList));//传数字1 表示树一级以下的根节点都默认打开
		}
	}
	
	/**
	 * 首页工单起草的快捷方式
	 * @return
	 */
	public String portalShortcut() {
		StringBuffer html = new StringBuffer();
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		List<MenuDtree> menuDtreeList = privilegeManagerService.getMenuByNavigationID(userSession.getPid(), id);
		if (CollectionUtils.isNotEmpty(menuDtreeList)) {
			for (int i = 0; i < menuDtreeList.size(); i++) {
				MenuDtree mSort = menuDtreeList.get(i);
				String url = mSort.getUserdata().get("url");
				String id = mSort.getId();
				String sortTxt = mSort.getText();
				if (org.apache.commons.lang.StringUtils.isNotBlank(url)) {
					String sort = "baseInfoQuery.action?wfSortId=";
					int index = url.indexOf(sort);
					if (index > 0) {//是分类节点
						StringBuffer tmp = new StringBuffer();
						for (int j = 0; j < menuDtreeList.size(); j++) {
							MenuDtree mType = menuDtreeList.get(j);
							String typeUrl = mType.getUserdata().get("url");
							String typeId = mType.getParentid();
							String text = mType.getText();
							String type = "baseInfoQuery.action?baseSchema=";
							int typeIndex = typeUrl.indexOf(type);
							if (typeId.equals(id) && typeIndex > 0) {
								String baseschema = typeUrl.substring(typeIndex + type.length());
//								try {
//									WfType wfType = sortService.getWfTypeByCode(baseschema);
//									if (wfType != null) {
										tmp.append("<li><a href=\""+getRequest().getContextPath()+"/sheet/createNewSheet.action?baseSchema="+baseschema+"\" target=\"_blank\">"+text+"</a></li>");
//									}
//								} catch (RemoteException e) {
//									e.printStackTrace();
//								}
							}
						}
						if (org.apache.commons.lang.StringUtils.isNotBlank(tmp.toString())) {
							String sortId = url.substring(index + sort.length());
//							try {
//								WfSort  wfSort = sortService.getWfSortByid(sortId);
//								if (wfSort != null) {
							if(i == 0)
							{
								html.append("<div id=\"sort" + id + "\" class=\"contract_menu_top menu1\" style=\"cursor:hand\" onclick=\"changeView('item" + id + "')\">"+sortTxt+"</div>");
							}
							else
							{
								html.append("<div id=\"sort" + id + "\" class=\"contract_menu_top\" style=\"cursor:hand\" onclick=\"changeView('item" + id + "')\">"+sortTxt+"</div>");
							}
							html.append("<div id=\"item" + id + "\" class=\"contract_menu\" style=\"display:none;\">");
							html.append(tmp.toString());
							html.append("</div>");
//								}
//							} catch (RemoteException e) {
//								e.printStackTrace();
//							}
						}
					}
				}
			}
		}
		shortcutHtml = html.toString();
		return this.findForward("frame/order_draft");
	}


	public void setLeftMenuTreeService(LeftMenuTreeImpl leftMenuTreeService) {
		this.leftMenuTreeService = leftMenuTreeService;
	}

	public void setRoleManagerService(RoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}


	public void setMenuManagerService(MenuManagerService menuManagerService) {
		this.menuManagerService = menuManagerService;
	}


	public MenuInfo getMenuinfo() {
		return menuinfo;
	}


	public void setMenuinfo(MenuInfo menuinfo) {
		this.menuinfo = menuinfo;
	}

	public String getShortcutHtml() {
		return shortcutHtml;
	}

	public void setShortcutHtml(String shortcutHtml) {
		this.shortcutHtml = shortcutHtml;
	}
	
	public void setUserManagerService(UserManagerService userManagerService) 
	{
		this.userManagerService = userManagerService;
	}
	
	public void setPrivilegeManagerService(
			PrivilegeManagerService privilegeManagerService) {
		this.privilegeManagerService = privilegeManagerService;
	}
	
}

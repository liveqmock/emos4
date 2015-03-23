package com.ultrapower.eoms.ultrasm.web;

import java.io.IOException;
import java.io.PrintWriter;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.MenuInfo;
import com.ultrapower.eoms.ultrasm.service.MenuManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

public class MenuManagerAction extends BaseAction
{
	private MenuManagerService menuManagerService;
	private MenuInfo menuinfo;
	private String menuId;
	private String parentName;//用于页面显示父节点中文名信息

	/**
	 * 菜单管理返回页面
	 * @return
	 */
	public String menuManager()
	{
		return this.findForward("menuManageFrame");
	}
	
	/**
	 * 获取菜单节点信息：菜单管理右侧窗口展示 
	 * @return
	 */
	public String menuInfo()
	{
		menuinfo = menuManagerService.getMenuByID(menuId);
		if(menuinfo != null)
		{
			parentName = "";
			if(!"0".equals(menuinfo.getParentid()))
			{
				MenuInfo parentMenu = menuManagerService.getMenuByID(menuinfo.getParentid());
				if(parentMenu != null)
					parentName = parentMenu.getNodename();
			}
		}
		return this.findForward("menuManage_right");
	}
	
	/**
	 * 菜单管理节点信息保存
	 * @return
	 */
	public String menuSave()
	{
		if(menuinfo != null)
		{
			UserSession userSession = this.getUserSession();
			String userpid = "";
			if(userSession != null)
				userpid = userSession.getPid();
			long currentTime = TimeUtils.getCurrentTime();
			//设置最后修改人和最后修改时间
			menuinfo.setLastmodifier(userpid);
			menuinfo.setLastmodifytime(currentTime);
			if("".equals(StringUtils.checkNullString(menuinfo.getPid())))//如果不存在节点ID则为添加，否则为修改
			{
				//添加需设置创建人和创建时间
				menuinfo.setCreater(userpid);
				menuinfo.setCreatetime(currentTime);
				menuId = menuManagerService.addMenuInfo(menuinfo);
			}
			else
			{
				menuId = menuManagerService.updateMenuInfo(menuinfo);
			}
		}
		this.getRequest().setAttribute("message","success");
		return this.findForward("menuManage_right");
	}
	
	/**
	 * 该方法用来检查节点标识的唯一性
	 * @return
	 * @throws IOException 
	 */
	public void checkUnique() throws IOException
	{
		String nodeMark = this.getRequest().getParameter("nodeMark");
		String pid = this.getRequest().getParameter("menuId");
		boolean result = menuManagerService.isUnique(nodeMark,pid);
		PrintWriter out = this.getResponse().getWriter();
		out.print(String.valueOf(result));
	}
	
	/**
	 * 删除菜单节点信息
	 * @return
	 */
	public String menuDel()
	{
		menuinfo = menuManagerService.getMenuByID(menuId);
		boolean result = menuManagerService.deleteMenuByID(menuId);
		String isAudit = PropertiesUtils.getProperty("eoms.isaudit");//是否添加审计日志
		if("true".equals(isAudit) && result)
		{
			RecordLog.printOperateAuditLog("1", "10302", "删除了("+menuinfo.getNodename()+")节点！");
		}
		this.getRequest().setAttribute("message","success");
		return this.findForward("menuManage_right");
	}

	/**
	 * 返回我的菜单列表页面
	 * @return
	 */
	public String myMenuList()
	{
		return SUCCESS;
	}
	
	/**
	 * 我的菜单保存
	 * @return
	 */
	public String myMenuSave()
	{
		UserSession userSession = this.getUserSession();
		String userId = userSession==null?"":StringUtils.checkNullString(userSession.getPid());
		String menuids = StringUtils.checkNullString(this.getRequest().getParameter("menuids"));
		this.getRequest().setAttribute("parafresh","true");
		if("".equals(menuids))
		{
			this.getRequest().setAttribute("returnMessage",Internation.language("com_msg_saveErr")+"!");
			return "refresh";
		}
		if(menuManagerService.addUserMenu(userId, UltraSmUtil.arrayToList(menuids.split(","))))
		{
			this.getRequest().setAttribute("returnMessage",Internation.language("com_msg_saveSuccess")+"!");
		}
		else
		{
			this.getRequest().setAttribute("returnMessage",Internation.language("com_msg_saveErr")+"!");
		}
		return "refresh";
	}
	
	/**
	 * 我的菜单删除
	 * @return
	 */
	public String myMenuDel()
	{
		String delIds = this.getRequest().getParameter("var_selectvalues");
		if("".equals(StringUtils.checkNullString(delIds)))
		{
			return findForward("myMenuList");
		}
		String[] idArr = delIds.split(",");
		menuManagerService.deleteUserMenuById(UltraSmUtil.arrayToList(idArr));
		return this.findForward("myMenuList");
	}
	
	/*
	 * 以下为属性的GET/SET方法
	 */
	public void setMenuManagerService(MenuManagerService menuManagerService) 
	{
		this.menuManagerService = menuManagerService;
	}
	public void setMenuinfo(MenuInfo menuinfo) 
	{
		this.menuinfo = menuinfo;
	}
	public MenuInfo getMenuinfo() 
	{
		return menuinfo;
	}
	public void setMenuId(String menuId) 
	{
		this.menuId = menuId;
	}
	public String getMenuId() 
	{
		return menuId;
	}
	public String getParentName() 
	{
		return parentName;
	}
}

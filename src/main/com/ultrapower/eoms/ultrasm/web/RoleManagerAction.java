package com.ultrapower.eoms.ultrasm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.plugin.datagrid.core.GridConstants;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.common.core.util.Internation;
public class RoleManagerAction extends BaseAction {
	private RoleManagerService roleManagerService;
	private List<RoleInfo> roleList;
	private RoleInfo roleInfo;
	
	/**
	 * 返回角色列表
	 * @return
	 */
	public String roleList()
	{
		//setRoleList(roleManagerService.getRoleInfo(null));
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		String userId = "";
		if(userSession != null)
			userId = userSession.getPid();
		List<RoleInfo> lst = roleManagerService.getRoleByUserID(userId);
		StringBuffer roledns = new StringBuffer();
		Map map = new HashMap();
		if(lst != null)
		{
			for(RoleInfo role:lst)
				roledns.append(role.getRoledns()+",");
		}
		if(roledns.lastIndexOf(",")!=-1)
		{
			map.put("roledns", roledns);
			this.getRequest().setAttribute("valuemap", map);
		}
		else
		{
			map.put("roledns", "");
			this.getRequest().setAttribute("valuemap", map);
		}
		if(this.getSession().getAttribute("returnMessage_delRole")!=null)
		{
			this.getRequest().setAttribute("returnMessage_delRole", this.getSession().getAttribute("returnMessage_delRole"));
			this.getSession().removeAttribute("returnMessage_delRole");
		}
		return this.findForward("roleList");
	}
	
	/**
	 * 加载特定ID的角色 
	 * @return
	 */
	public String load()
	{
		String roleId = this.getRequest().getParameter("roleId");
		setRoleInfo(roleManagerService.getRoleByID(roleId));
		if(roleInfo!=null)
		{
			RoleInfo temp = roleManagerService.getRoleByID(roleInfo.getParentid());
			if(temp!=null){
				String parentRolename = temp.getRolename();
				this.getRequest().setAttribute("parentRoleName", parentRolename);
			}
		}
		return findForward("roleSave");
	}
	
	/**
	 * 添加或者修改角色信息
	 * @return
	 */
	public String saveRole()
	{
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		String userpid = "";
		if(userSession != null)
			userpid = userSession.getPid();
		long currentTime = TimeUtils.getCurrentTime();
		roleInfo.setLastmodifytime(currentTime);
		roleInfo.setLastmodifier(userpid);
		String roleId = "";
		String saveType = this.getRequest().getParameter("saveType");
		if("".equals(StringUtils.checkNullString(roleInfo.getPid())))
		{//添加
			roleInfo.setCreater(userpid);
			roleInfo.setCreatetime(currentTime);
			String cpyid = this.getRequest().getParameter("copyPrivilege");
			roleId = roleManagerService.addRoleAndCopyPrivilege(roleInfo, cpyid);
			if("".equals(StringUtils.checkNullString(roleId)))
			{
				this.getRequest().setAttribute("parafresh","false");
				this.getRequest().setAttribute("returnMessage",Internation.language("com_msg_saveErr")+"!");
				return "refresh";
			}
			if("set".equals(saveType))
			{
				RoleInfo temp = roleManagerService.getRoleByID(roleInfo.getParentid());
				if(temp!=null){
					String parentRolename = temp.getRolename();
					this.getRequest().setAttribute("parentRoleName", parentRolename);
				}
				return findForward("roleSave");
			}
			else
			{
				this.getRequest().setAttribute("parafresh","true");
				this.getRequest().setAttribute("returnMessage",Internation.language("com_msg_saveSuccess")+"!");
				return "refresh";
			}
		}
		else
		{//修改
			boolean b = roleManagerService.updateRoleInfo(roleInfo);
			if(b==false)
			{
				this.getRequest().setAttribute("parafresh","false");
				this.getRequest().setAttribute("returnMessage",Internation.language("com_msg_saveErr")+"!");
				return "refresh";
			}
			if("set".equals(saveType))
			{
				return findForward("roleSave");
			}
			else
			{
				this.getRequest().setAttribute("parafresh","true");
				this.getRequest().setAttribute("returnMessage",Internation.language("com_msg_saveSuccess")+"!");
				return "refresh";
			}
		}
	}
	
	/**
	 * 删除角色信息
	 * @return
	 */
	public String delRole()
	{
		String roleIds = this.getRequest().getParameter("var_selectvalues");
		String[] idArr = roleIds.split(",");
		List roleIdList = UltraSmUtil.arrayToList(idArr);
		String isAudit = PropertiesUtils.getProperty("eoms.isaudit");//是否添加审计日志
		String roleName = "";
		if("true".equals(isAudit))
		{
			roleName = UltraSmUtil.getNameById("bs_t_sm_role", "rolename", roleIdList);
		}
		String canDel = roleManagerService.deleteJudgeInfo(roleIdList);
		boolean result = false;
		if("".equals(canDel))
		{
			result = roleManagerService.deleteRoleByID(roleIdList);
		}
		else
		{
			this.getSession().setAttribute("returnMessage_delRole", canDel);
		}
		if("true".equals(isAudit) && result && !"".equals(roleName))
		{
			RecordLog.printOperateAuditLog("1", "10602", "删除了("+roleName+")角色！");
		}
		return this.findRedirectPar("roleList.action?id="+this.getRequest().getParameter("id"));
	}
	
	/**
	 * 保存角色的菜单目录树
	 * @return
	 */
	public String saveRoleMenu()
	{
		String roleid = this.getRequest().getParameter("roleid");
		String menuids = this.getRequest().getParameter("menuids");
		roleManagerService.addRoleMenu(roleid, UltraSmUtil.arrayToList(menuids.split(",")));
		setRoleInfo(roleManagerService.getRoleByID(roleid));
		return findForward("roleSave");
	}
	
	/**
	 * 获得当前登录人员的角色树
	 * @return
	 */
	public String getSelfRoleTree()
	{
		UserSession userSession = this.getUserSession();
		List roleIdList = roleManagerService.getRoleIdByUserID(userSession==null?"":userSession.getPid());
		roleIdList = roleManagerService.clearChildFrmList(roleIdList);
		String roleIds = "";
		if(roleIdList!=null && roleIdList.size()>0)
		{
			for(int i=0;i<roleIdList.size();i++)
			{
				roleIds = roleIds + roleIdList.get(i) + ",";
			}
			roleIds = roleIds.substring(0, roleIds.lastIndexOf(","));
		}
		this.getRequest().setAttribute("roleIds", roleIds);
		return findForward("roleOpenRadioTree");
	}
	
	/**
	 * 给具有特定ID的角色配置组织（增加，删除）
	 * @return
	 */
	public String theRoleOrgCfg()
	{
		String theroleid = StringUtils.checkNullString(this.getRequest().getParameter("theroleid"));
		Map map = new HashMap();
		map.put("theroleid", theroleid);
		this.getRequest().setAttribute("valuemap", map);
		this.getRequest().setAttribute("theroleid", theroleid);
		return SUCCESS;
	}
	
	/**
	 * 删除该角色和组织的关系
	 * @return
	 */
	public String delTheRoleOrg()
	{
		String ids = this.getRequest().getParameter("var_selectvalues");
		if(ids==null || "".equals(ids))
		{
			return findRedirect("roleOrgList");
		}
		roleManagerService.deleteRoleOrgByID(UltraSmUtil.arrayToList(ids.split(",")));
		String theroleid = StringUtils.checkNullString(this.getRequest().getParameter("theroleid"));
		return findRedirectPar("theRoleOrgCfg.action?theroleid="+theroleid);
		
	}
	
	/**
	 * 保存该角色和组织之间的关系
	 * @return
	 */
	public String theRoleOrgSave()
	{
		String roleids = this.getRequest().getParameter("roleids");
		String depids = this.getRequest().getParameter("depids");
		String userids = this.getRequest().getParameter("userids");
		List roleIdList;
		List userIdList;
		List depIdList;
		if(roleids==null||"".equals(roleids))
		{
			roleIdList = null;
		}
		else
		{
			roleIdList = UltraSmUtil.arrayToList(roleids.split(","));
		}
		if(depids==null||"".equals(depids))
		{
			depIdList = null;
		}
		else
		{
			depIdList = UltraSmUtil.arrayToList(depids.split(","));
		}
		if(userids==null||"".equals(userids))
		{
			userIdList = null;
		}
		else
		{
			userIdList = UltraSmUtil.arrayToList(userids.split(","));
		}
		this.getRequest().setAttribute("parafresh","true");
		if(roleManagerService.addRoleOrg(roleIdList, userIdList, depIdList))
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
	 * 给具有特定ID的角色配置资源操作
	 * @return
	 */
	public String theRoleResOpCfg()
	{
		String theroleid = StringUtils.checkNullString(this.getRequest().getParameter("theroleid"));
		String parentid = StringUtils.checkNullString(this.getRequest().getParameter("parentid"));
		Map map = new HashMap();
		map.put("theroleid", theroleid);
		this.getRequest().setAttribute("valuemap", map);
		this.getRequest().setAttribute("theroleid", theroleid);
		this.getRequest().setAttribute("parentid", parentid);
		return SUCCESS;
	}
	
	/**
	 * 保存角色资源操作
	 * @return
	 */
	public String theRoleResOpSave()
	{
		String roleid = StringUtils.checkNullString(this.getRequest().getParameter("roleids"));
		String resids = StringUtils.checkNullString(this.getRequest().getParameter("resids"));
		String opids = StringUtils.checkNullString(this.getRequest().getParameter("opids"));
		String parafresh = "true";
		if(roleManagerService.addRoleResOp(roleid, UltraSmUtil.arrayToList(resids.split(",")), UltraSmUtil.arrayToList(opids.split(","))))
		{
			String returnMessage = Internation.language("com_msg_saveSuccess")+"!";
			this.getRequest().setAttribute("parafresh", parafresh);
			this.getRequest().setAttribute("returnMessage", returnMessage);
			return "refresh";
		}
		else
		{
			String returnMessage = Internation.language("com_msg_saveErr")+"!";
			this.getRequest().setAttribute("parafresh", parafresh);
			this.getRequest().setAttribute("returnMessage", returnMessage);
			return "refresh";
		}
	}
	
	/**
	 * 删除角色资源操作
	 * @return
	 */
	public String delTheRoleResOp()
	{
		String delids = StringUtils.checkNullString(this.getRequest().getParameter(GridConstants.HIDDEN_CHECKBOX_SELECTVALUES));
		if(!"".equals(delids))
		{
			roleManagerService.deleteRoleResOpByID(UltraSmUtil.arrayToList(delids.split(",")));
		}
		String roleId = StringUtils.checkNullString(this.getRequest().getParameter("theroleid"));
		String parentid = StringUtils.checkNullString(this.getRequest().getParameter("parentid"));
		return findRedirectPar("theRoleResOpCfg.action?theroleid="+roleId+"&parentid="+parentid);
	}
	
	/**
	 * 根据特定ID的角色配置其菜单目录
	 * @return
	 */
	public String theRoleMenuCfg()
	{
		String theroleid = StringUtils.checkNullString(this.getRequest().getParameter("theroleid"));
		String parentid = StringUtils.checkNullString(this.getRequest().getParameter("parentid"));
		this.getRequest().setAttribute("theroleid", theroleid);
		this.getRequest().setAttribute("parentid", parentid);
		return this.findForward("theRoleMenuCfg_");
	}
	
	/**
	 * 删除角色菜单目录
	 * @return
	 */
	public String delTheRoleMenu()
	{
		String roleId = StringUtils.checkNullString(this.getRequest().getParameter("roleids"));
		String parentid = StringUtils.checkNullString(this.getRequest().getParameter("parentid"));
		String menuIds = StringUtils.checkNullString(this.getRequest().getParameter("menuids"));
		roleManagerService.deleteRoleMenu(roleId,UltraSmUtil.arrayToList(menuIds.split(",")));
		return findRedirectPar("theRoleMenuCfg.action?theroleid="+roleId+"&parentid="+parentid);
	}
	
	/**
	 * 转到该角色菜单目录添的加页面
	 * @return
	 */
	public String theRoleMenuAdd()
	{
		String theroleid = StringUtils.checkNullString(this.getRequest().getParameter("theroleid"));
		String parentid = StringUtils.checkNullString(this.getRequest().getParameter("parentid"));
		
		this.getRequest().setAttribute("theroleid", theroleid);
		this.getRequest().setAttribute("parentid", parentid);
		return SUCCESS;
	}
	
	/**
	 * 保存该角色的菜单目录
	 * @return
	 */
	public String theRoleMenuSave()
	{
		String menuids = StringUtils.checkNullString(this.getRequest().getParameter("menuids"));
		String roleids = StringUtils.checkNullString(this.getRequest().getParameter("theroleid"));
		this.getRequest().setAttribute("parafresh","true");
		if(roleManagerService.addRoleMenu(roleids, UltraSmUtil.arrayToList(menuids.split(","))))
		{
			this.getRequest().setAttribute("returnMessage",Internation.language("com_msg_saveSuccess")+"!");
		}
		else
		{
			this.getRequest().setAttribute("returnMessage",Internation.language("com_msg_saveErr")+"!");
		}
		return "refresh";
	}
	
	/*
	 * 以下方法为属性的get/set方法
	 */
	public List<RoleInfo> getRoleList() 
	{
		return roleList;
	}

	public void setRoleList(List<RoleInfo> roleList) 
	{
		this.roleList = roleList;
	}

	public RoleInfo getRoleInfo() 
	{
		return roleInfo;
	}

	public void setRoleInfo(RoleInfo roleInfo) 
	{
		this.roleInfo = roleInfo;
	}

	public void setRoleManagerService(RoleManagerService roleManagerService) 
	{
		this.roleManagerService = roleManagerService;
	}
}

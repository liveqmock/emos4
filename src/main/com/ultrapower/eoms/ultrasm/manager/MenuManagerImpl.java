package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.support.PageLimit;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.MenuInfo;
import com.ultrapower.eoms.ultrasm.model.RoleMenu;
import com.ultrapower.eoms.ultrasm.model.UserMenuTree;
import com.ultrapower.eoms.ultrasm.service.DnsManagerService;
import com.ultrapower.eoms.ultrasm.service.MenuManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

@Transactional
public class MenuManagerImpl implements MenuManagerService
{
	private IDao<MenuInfo> menuManagerDao;
	private IDao<RoleMenu> roleMenuDao;
	private IDao<UserMenuTree> userMenuDao;
	private DnsManagerService dnsManagerService;
	private UserManagerService userManagerService;
	private QueryAdapter queryAdapter;
	
	public boolean isUnique(String nodeMark, String menuId)
	{
		if("".equals(StringUtils.checkNullString(nodeMark)))
		{
			return false;
		}
		List<MenuInfo> menuList = null;
		if("".equals(StringUtils.checkNullString(menuId)))
			menuList = menuManagerDao.find(" from MenuInfo where nodemark = ?", new Object[] {nodeMark});
		else
			menuList = menuManagerDao.find(" from MenuInfo where nodemark = ? and pid <> ?", new Object[] {nodeMark, menuId});
		if(menuList != null && menuList.size() > 0)
		{
			return false;
		}
		return true;
	}
	
	public String getMenuNameById(String menuId)
	{
		String menuName = "";
		MenuInfo menu = this.getMenuByID(menuId);
		if(menu != null)
		{
			menuName = StringUtils.checkNullString(menu.getNodename());
		}
		return menuName;
	}
	
	public String getNodePahtById(String menuId)
	{
		String nodePath = "";
		MenuInfo menu = this.getMenuByID(menuId);
		if(menu != null)
			nodePath = StringUtils.checkNullString(menu.getNodepath());
		return nodePath;
	}
	
	public String getIdByUrl(String url)
	{
		String menuId = "";
		if("".equals(StringUtils.checkNullString(url)))
			return menuId;
		try
		{
			List<MenuInfo> menuList = menuManagerDao.find(" from MenuInfo where nodeurl = ? and parentid <> '0'", new Object[] {url});
			if(menuList != null && menuList.size() > 0)
				menuId = StringUtils.checkNullString(menuList.get(0).getPid());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return menuId;
	}
	
	public MenuInfo getMenuByMark(String nodeMark)
	{
		if("".equals(StringUtils.checkNullString(nodeMark)))
			return null;
		MenuInfo menu = null;
		List<MenuInfo> menuList = menuManagerDao.find(" from MenuInfo where nodemark = ?", new Object[] {nodeMark});
		if(menuList != null && menuList.size() > 0)
			menu = menuList.get(0);
		return menu;
	}
	
	public boolean deleteMenuByMark(String nodeMark)
	{
		return this.deleteMenuByID(this.getMenuIdByMark(nodeMark));
	}
	
	public String getMenuIdByMark(String nodeMark)
	{
		if("".equals(StringUtils.checkNullString(nodeMark)))
			return null;
		String menuId = "";
		MenuInfo menu = this.getMenuByMark(nodeMark);
		if(menu != null)
			menuId = menu.getPid();
		return menuId;
	}
	
	public String addMenuInfo(MenuInfo menu)
	{
		if(menu == null)
		{
			return null;
		}
		String menuId = "";
		try
		{
			String parentid = menu.getParentid();
			MenuInfo parMenu = this.getMenuByID(parentid);
			if(parMenu != null)
				menu.setNodepath("".equals(StringUtils.checkNullString(parMenu.getNodepath())) ? ("当前位置：" + menu.getNodename()) : (parMenu.getNodepath() + ">>" + menu.getNodename()));
			menuManagerDao.save(menu);
			menuId = menu.getPid();
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return menuId;
	}

	public boolean deleteMenuByID(String menuId)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(menuId)))
		{
			return result;
		}
		//获取当前节点的子集节点IDList
		List menuIdList = dnsManagerService.getSubIdList("bs_t_sm_menutree", menuId);
		if(menuIdList != null && menuIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(menuIdList);
				//删除菜单节点与角色的关系
				roleMenuDao.executeUpdate(" delete RoleMenu where menuid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
				//删除菜单节点与子集所有节点信息
				menuManagerDao.executeUpdate(" delete MenuInfo where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
				result = true;
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		else
		{
			result = true;
		}
		return result;
	}

	public MenuInfo getMenuByID(String menuId)
	{
		if("".equals(StringUtils.checkNullString(menuId)))
		{
			return null;
		}
		return menuManagerDao.get(menuId);
	}
	
	public MenuInfo getNavigateMenuById(String menuId)
	{
		if("".equals(StringUtils.checkNullString(menuId)))
		{
			return null;
		}
		MenuInfo menu = null;
		String id = menuId;
		while (true)
		{
			menu = menuManagerDao.get(id);
			if(menu == null)
				break;
			id = menu.getParentid();
			if("0".equals(StringUtils.checkNullString(id)))
				break;
		}
		return menu;
	}
	
	public List<String> getParentidListById(String menuId)
	{
		if("".equals(StringUtils.checkNullString(menuId)))
		{
			return null;
		}
		List<String> idList = new ArrayList<String>();
		String id = menuId;
		MenuInfo menu = null;
		while (true)
		{
			menu = menuManagerDao.get(id);
			if(menu == null)
				break;
			id = menu.getParentid();
			if("0".equals(StringUtils.checkNullString(id)))
				break;
			if(!id.equals(menuId))
				idList.add((String) menu.getPid());
		}
		return idList;
	}
	
	public List<MenuInfo> getMenuByID(List menuIdList)
	{
		PageLimit pageLimit = PageLimit.getInstance();
		List<MenuInfo> menuInfo = null;
		menuIdList = UltraSmUtil.removeNullData(menuIdList);
		if(menuIdList != null && menuIdList.size() > 0)
		{
			Map map = UltraSmUtil.getSqlParameter(menuIdList);
			menuInfo = menuManagerDao.pagedQuery(" from MenuInfo where pid in (" + map.get("?") + ")", pageLimit, (Object[]) map.get("obj"));
		}
		return menuInfo;
	}

	public List<MenuInfo> getMenuByParentID(String menuId)
	{
		if(menuId == null)
		{
			return null;
		}
		List<MenuInfo> menuList = menuManagerDao.find(" from MenuInfo where parentid = ?", new Object[] {menuId});
		return menuList;
	}

	public String updateMenuInfo(MenuInfo menu)
	{
		if(menu == null)
		{
			return null;
		}
		String menuId = "";
		try
		{
			String parentid = menu.getParentid();
			MenuInfo parMenu = this.getMenuByID(parentid);
			if(parMenu != null)
				menu.setNodepath("".equals(StringUtils.checkNullString(parMenu.getNodepath())) ? ("当前位置：" + menu.getNodename()) : (parMenu.getNodepath() + ">>" + menu.getNodename()));
			menuManagerDao.saveOrUpdate(menu);
			menuId = menu.getPid();
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return menuId;
	}

	public boolean addUserMenu(String userId, List menuIdList)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(userId)))
		{
			return result;
		}
		menuIdList = UltraSmUtil.removeNullData(menuIdList);
		if(menuIdList != null && menuIdList.size() > 0)
		{
			try
			{
				UserMenuTree um = null;
				UserSession userSession = (UserSession) ActionContext.getRequest().getSession().getAttribute("userSession");
				String userpid = "";
				if(userSession != null)
				{
					userpid = userSession.getPid();
				}
				else
				{
					userpid = userManagerService.getPidByLoginName(UltraSmUtil.MANAGER);
				}
				long currentTime = TimeUtils.getCurrentTime();
				String sql = " select distinct menuid from bs_t_sm_usermenutree where userid = ? ";
				QueryAdapter queryAdapter = new QueryAdapter();
				DataTable table = queryAdapter.executeQuery(sql, new Object[] {userId});
				List oldMenuIdList = new ArrayList();
				if(table != null && table.length() > 0)
				{
					for(int i=0;i<table.length();i++)
					{
						String temp = table.getDataRow(i).getString(0);
						if(!"".equals(StringUtils.checkNullString(temp)))
						{
							oldMenuIdList.add(temp);
						}
					}
				}
				for(int i=0;i<menuIdList.size();i++)
				{
					String menuId = (String) menuIdList.get(i);
					if(oldMenuIdList.indexOf(menuId) > 0)
						continue;
					um = new UserMenuTree();
					um.setUserid(userId);
					um.setMenuid(menuId);
					um.setCreater(userpid);
					um.setCreatetime(currentTime);
					um.setLastmodifier(userpid);
					um.setLastmodifytime(currentTime);
					userMenuDao.save(um);
				}
				result = true;
			}
			catch  (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		else
		{
			result = true;
		}
		return result;
	}

	public boolean deleteUserMenuById(List umIdList)
	{
		boolean result = false;
		umIdList = UltraSmUtil.removeNullData(umIdList);
		if(umIdList != null && umIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(umIdList);
				userMenuDao.executeUpdate(" delete UserMenuTree where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
				result = true;
			}
			catch  (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		else
		{
			result = true;
		}
		return result;
	}
	
	public void setMenuManagerDao(IDao<MenuInfo> menuManagerDao) {
		this.menuManagerDao = menuManagerDao;
	}
	public void setRoleMenuDao(IDao<RoleMenu> roleMenuDao) {
		this.roleMenuDao = roleMenuDao;
	}
	public void setUserMenuDao(IDao<UserMenuTree> userMenuDao) {
		this.userMenuDao = userMenuDao;
	}
	public void setDnsManagerService(DnsManagerService dnsManagerService) {
		this.dnsManagerService = dnsManagerService;
	}
	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
}

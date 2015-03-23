package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.support.PageLimit;
import com.ultrapower.eoms.common.core.util.ArrayTransferUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.external.manager.EomsInfoDownPasmImpl;
import com.ultrapower.eoms.common.external.service.EomsInfoDownPasm;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.DataInfo;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.OpDataPrivilege;
import com.ultrapower.eoms.ultrasm.model.Operate;
import com.ultrapower.eoms.ultrasm.model.ResPropertyShow;
import com.ultrapower.eoms.ultrasm.model.Resource;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.RoleMenu;
import com.ultrapower.eoms.ultrasm.model.RoleMenuShow;
import com.ultrapower.eoms.ultrasm.model.RoleOrg;
import com.ultrapower.eoms.ultrasm.model.RoleOrgShow;
import com.ultrapower.eoms.ultrasm.model.RoleResOp;
import com.ultrapower.eoms.ultrasm.model.RoleResOpShow;
import com.ultrapower.eoms.ultrasm.model.SqlDataPrivilege;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.DnsManagerService;
import com.ultrapower.eoms.ultrasm.service.OperateManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.service.SynchDataService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

@Transactional
public class RoleManagerImpl implements RoleManagerService {
	private IDao<RoleInfo> roleManagerDao;
	private IDao<RoleOrg> roleOrgDao;
	private IDao<RoleMenu> roleMenuDao;
	private IDao<RoleResOp> roleResOpDao;
	private IDao<Resource> resourceDao;
	private IDao<UserInfo> userManagerDao;
	private IDao<OpDataPrivilege> opDataPrivilegeDao;
	private IDao<SqlDataPrivilege> sqlDataPrivilegeDao;
	private UserManagerService userManagerService;
	private DepManagerService depManagerService;
	private OperateManagerService operateManagerService;
	private DnsManagerService dnsManagerService;
	private SynchDataService synchDataDown;
	private EomsInfoDownPasm synchInfoPasm;
	
	public boolean addAllUserToRole(String roleid, String preventUser)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(roleid)))
			return result;
		try {
			String hql = "from UserInfo";
			if(!"".equals(StringUtils.checkNullString(preventUser)))
			{
				preventUser = preventUser.replaceAll(",", "','");
				hql += " where loginname not in ('"+preventUser+"')";
			}
			List<UserInfo> userList = userManagerDao.find(hql, null);
			int userLen = 0;
			if(userList != null)
				userLen = userList.size();
			UserInfo userInfo;
			List<String> userIdList = new ArrayList<String> ();
			for(int i=0 ; i<userLen ; i++)
			{
				userInfo = userList.get(i);
				userIdList.add(userInfo.getPid());
			}
			if(userIdList.size() > 0)
				result = this.addRoleOrg(roleid, userIdList, null);
			else
				result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String addRoleAndCopyPrivilege(RoleInfo role, String roleId)
	{
		if(role == null)
		{
			return null;
		}
		String pid = this.addRoleInfo(role);//添加角色信息并返回角色ID
		if(!"".equals(StringUtils.checkNullString(pid)))
		{
			if(!"".equals(StringUtils.checkNullString(roleId)))
			{
				//需要做处理
				this.copyFromRoleID(pid, roleId);//复制角色权限
			}
		}
		return pid;//返回角色ID
	}
	
	public String addRoleInfo(RoleInfo role)
	{
		return this.addRoleInfo(role, "");
	}
	
	public String addRoleInfo(RoleInfo role, String systemMark)
	{
		if(role == null)
		{
			return "";
		}
		String roleId = "";
		try
		{
			role.setRoledn(dnsManagerService.getCurrentDn("bs_t_sm_role", "roledn", role.getParentid()));
			role.setRoledns(dnsManagerService.getCurrentDns("bs_t_sm_role", "roledns", "roledn", role.getParentid()));
			if("".equals(StringUtils.checkNullString(role.getPid()))) {
				role.setPid(UUIDGenerator.getUUIDoffSpace());
			}
			roleManagerDao.save(role);
			int synchResult = synchDataDown.synchRoleAdd(role, systemMark);
			if(synchResult == 0)
			{
				roleId = role.getPid();
			}
			else
			{
				RecordLog.printLog("add roleinfo success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				roleManagerDao.remove(role);
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return roleId;
	}

	public boolean copyFromRoleID(String newRoleId, String roleId)
	{
		if("".equals(StringUtils.checkNullString(newRoleId)) || "".equals(StringUtils.checkNullString(roleId)))
		{
			return false;
		}
		//复制角色目录树权限和角色资源操作权限
		return this.copyMenuPrivilege(newRoleId, roleId) && this.copyResOpPrivilege(newRoleId, roleId);
	}

	public String deleteJudgeInfo(List roleIdList)
	{
		String result = "";
		roleIdList = UltraSmUtil.removeNullData(roleIdList);
		if(roleIdList != null && roleIdList.size() > 0)
		{
			try
			{
				List tmpList = new ArrayList();
				for(int i=0;i<roleIdList.size();i++)
				{
					String roleId = (String) roleIdList.get(i);
					StringBuffer sql = new StringBuffer();
					sql.append("select sum(n) as num");
					sql.append("  from (select count(*) as n");
					sql.append("          from bs_t_sm_role");
					sql.append("         where parentid = ?");
					sql.append("        union all");
					sql.append("        select count(*) as n");
					sql.append("          from bs_t_sm_roleorg");
					sql.append("         where roleid = ?)");
					sql.append(" group by n");
					QueryAdapter queryAdapter = new QueryAdapter();
					DataTable table = queryAdapter.executeQuery(sql.toString(),new Object[] {roleId, roleId});
					if(table != null && table.length() > 0)
					{
						String numStr = StringUtils.checkNullString(table.getDataRow(0).getString("num"));
						if(!"0".equals(numStr))
							tmpList.add(roleId);
					}
				}
				if(tmpList != null && tmpList.size() > 0)
				{
					Map map = UltraSmUtil.getSqlParameter(tmpList);
					QueryAdapter queryAdapter = new QueryAdapter();
					DataTable table = queryAdapter.executeQuery(" select rolename from bs_t_sm_role where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
					if(table != null && table.length() > 0)
					{
						for(int i=0;i<table.length();i++)
						{
							if(!"".equals(result))
								result += ",";
							result += StringUtils.checkNullString(table.getDataRow(i).getString("rolename"));
						}
					}
				}
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean deleteRoleByID(String roleId)
	{
		return this.deleteRoleByID(roleId, "");
	}
	
	public boolean deleteRoleByID(String roleId, String systemMark)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(roleId)))
		{
			return result;
		}
		try
		{
			List<String> roleIdList = new ArrayList<String> ();
			roleIdList.add(roleId);
			if(this.clearPrivilegeByRoleId(roleIdList))//清除角色所有权限
			{
				roleManagerDao.removeById(roleId);//删除角色
				result = true;
				int synchResult = synchDataDown.synchRoleDel(roleId, systemMark);
				if(synchResult > 0)
				{
					RecordLog.printLog("del role success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				}
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}

	public boolean deleteRoleByID(List roleIdList)
	{
		boolean result = false;
		roleIdList = UltraSmUtil.removeNullData(roleIdList);
		if(roleIdList != null && roleIdList.size() > 0)
		{
			try
			{
				if(this.clearPrivilegeByRoleId(roleIdList))//清除角色所有权限
				{
//					Map map = UltraSmUtil.getSqlParameter(roleIdList);
//					roleManagerDao.executeUpdate(" delete RoleInfo where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
					for(int i=0;i<roleIdList.size();i++)
					{
						if(!this.deleteRoleByID((String) roleIdList.get(i)))
						{
							return false;
						}
					}
					result = true;
				}
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
	
	public boolean updateRoleInfo(RoleInfo role)
	{
		return this.updateRoleInfo(role, "");
	}

	public boolean updateRoleInfo(RoleInfo role, String systemMark)
	{
		boolean result = false;
		if(role == null)
			return result;
		try
		{
			roleManagerDao.saveOrUpdate(role);
			result = true;
			int synchResult = synchDataDown.synchRoleEdit(role, systemMark);
			if(synchResult > 0)
			{
				RecordLog.printLog("update roleinfo success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	public RoleInfo getRoleByID(String roleId)
	{
		if("".equals(StringUtils.checkNullString(roleId)))
		{
			return null;
		}
		RoleInfo role = null;
		try
		{
			role = roleManagerDao.get(roleId);
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return role;
	}

	public List<RoleInfo> getRoleByID(List roleIdList)
	{
		List<RoleInfo> roleList = new ArrayList<RoleInfo>();
		roleIdList = UltraSmUtil.removeNullData(roleIdList);
		if(roleIdList != null && roleIdList.size() > 0)
		{
			Map map = UltraSmUtil.getSqlParameter(roleIdList);
			roleList = roleManagerDao.find(" from RoleInfo where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
		}
		return roleList;
	}
	
	public List<RoleInfo> getRoleByParentID(String roleId)
	{
		if("".equals(StringUtils.checkNullString(roleId)))
		{
			return null;
		}
		List<RoleInfo> roleList = roleManagerDao.find(" from RoleInfo where parentid = ?", new Object[] {roleId});
		return roleList;
	}

	public boolean addRoleOrg(List roleIdList, List userIdList, List depIdList)
	{
		return this.addRoleOrg(roleIdList, userIdList, depIdList, "");
	}
	
	public boolean addRoleOrg(List roleIdList, List userIdList, List depIdList, String systemMark)
	{
		roleIdList  = UltraSmUtil.removeNullData(roleIdList);
		if(roleIdList != null && roleIdList.size() > 0)
		{
			for(int i=0;i<roleIdList.size();i++)
			{
				if(!this.addRoleOrg((String) roleIdList.get(i), userIdList, depIdList, systemMark))//逐个角色添加角色组织关系
				{
					return false;
				}
			}
		}
		return true;
	}

	public boolean addRoleOrg(String roleId, List userIdList, List depIdList)
	{
		return this.addRoleOrg(roleId, userIdList, depIdList, "");
	}
	
	public boolean addRoleOrg(String roleId, List userIdList, List depIdList, String systemMark)
	{
		if("".equals(StringUtils.checkNullString(roleId)))
		{
			return false;
		}
		boolean resultUser = false;
		userIdList = UltraSmUtil.removeNullData(userIdList);
		if(userIdList != null && userIdList.size() > 0)
		{
			try
			{
				RoleOrg ro = null;
				String userpid = "";
				if("".equals(StringUtils.checkNullString(systemMark))) {
					UserSession userSession = (UserSession) ActionContext.getRequest().getSession().getAttribute("userSession");
					if(userSession != null)
					{
						userpid = userSession.getPid();
					}
					else
					{
						userpid = userManagerService.getPidByLoginName(UltraSmUtil.MANAGER);
					}
				}
				long currentTime = TimeUtils.getCurrentTime();
				List oldUserIdList = this.getOrgIdByRoleID(roleId, 1);
				List<String> uidList = new ArrayList<String> ();
				for(int i=0;i<userIdList.size();i++)
				{
					String userId = (String) userIdList.get(i);
					if(oldUserIdList.indexOf(userId) >= 0)
					{
						continue;
					}
					ro = new RoleOrg();
					ro.setOrgid(userId);
					ro.setRoleid(roleId);
					ro.setOrgtype((long) 1);//关系类型 1:人员 2:部门
					ro.setCreater(userpid);
					ro.setCreatetime(currentTime);
					ro.setLastmodifier(userpid);
					ro.setLastmodifytime(currentTime);
					roleOrgDao.save(ro);
					uidList.add(userId);
				}
				resultUser = true;
				List<String> ridList = new ArrayList<String>();
				ridList.add(roleId);
				int synchResultUser = synchDataDown.synchRoleOrgAdd(ridList, uidList, "1", systemMark);
				if(synchResultUser > 0)
				{
					RecordLog.printLog("add role user success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				}
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		else
		{
			resultUser = true;
		}
		boolean resultDep = false;
		depIdList = UltraSmUtil.removeNullData(depIdList);
		if(depIdList != null && depIdList.size() > 0)
		{
			try
			{
				RoleOrg ro = null;
				String userpid = "";
				if("".equals(StringUtils.checkNullString(systemMark))) {
					UserSession userSession = (UserSession) ActionContext.getRequest().getSession().getAttribute("userSession");
					if(userSession != null)
					{
						userpid = userSession.getPid();
					}
					else
					{
						userpid = userManagerService.getPidByLoginName(UltraSmUtil.MANAGER);
					}
				}
				long currentTime = TimeUtils.getCurrentTime();
				List oldDepIdList = this.getOrgIdByRoleID(roleId, 2);
				List<String> didList = new ArrayList<String> ();
				for(int i=0;i<depIdList.size();i++)
				{
					String depId = (String) depIdList.get(i);
					if(oldDepIdList.indexOf(depId) >= 0)
					{
						continue;
					}
					ro = new RoleOrg();
					ro.setOrgid(depId);
					ro.setRoleid(roleId);
					ro.setOrgtype((long) 2);//关系类型 1:人员 2:部门
					ro.setCreater(userpid);
					ro.setCreatetime(currentTime);
					ro.setLastmodifier(userpid);
					ro.setLastmodifytime(currentTime);
					roleOrgDao.save(ro);
					didList.add(depId);
				}
				resultDep = true;
				List<String> ridList = new ArrayList<String>();
				ridList.add(roleId);
				int synchResultDep = synchDataDown.synchRoleOrgAdd(ridList, didList, "2", systemMark);
				if(synchResultDep > 0)
				{
					RecordLog.printLog("add role dep success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				}
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		else
		{
			resultDep = true;
		}
		return resultUser && resultDep;
	}

	public boolean addUserRole(String userId, List roleIdList)
	{
		return this.addUserRole(userId, roleIdList, "");
	}
	
	public boolean addUserRole(String userId, List roleIdList, String systemMark)
	{
		return this.addOrgRole(userId, roleIdList, 1, systemMark);
	}

	public boolean addDepRole(String depId, List roleIdList)
	{
		return this.addDepRole(depId, roleIdList, "");
	}
	
	public boolean addDepRole(String depId, List roleIdList, String systemMark)
	{
		return this.addOrgRole(depId, roleIdList, 2, systemMark);
	}
	
	private boolean addOrgRole(String orgId, List roleIdList, long type, String systemMark)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(orgId)))
		{
			return result;
		}
		roleIdList = UltraSmUtil.removeNullData(roleIdList);
		if(roleIdList != null && roleIdList.size() > 0)
		{
			try
			{
				RoleOrg ro = null;
				String userpid = "";
				if("".equals(StringUtils.checkNullString(systemMark))) {
					UserSession userSession = (UserSession) ActionContext.getRequest().getSession().getAttribute("userSession");
					if(userSession != null)
					{
						userpid = userSession.getPid();
					}
					else
					{
						userpid = userManagerService.getPidByLoginName(UltraSmUtil.MANAGER);
					}
				}
				long currentTime = TimeUtils.getCurrentTime();
				List oldRoleIdList = this.getRoleIdByOrgId(orgId, type);
				List<String> ridList = new ArrayList<String> ();
				for(int i=0;i<roleIdList.size();i++)
				{
					String roleId = (String) roleIdList.get(i);
					if(oldRoleIdList.indexOf(roleId) >= 0)
					{
						continue;
					}
					ro = new RoleOrg();
					ro.setOrgid(orgId);
					ro.setRoleid(roleId);
					ro.setOrgtype(type);//关系类型 1:人员 2:组织
					ro.setCreater(userpid);
					ro.setCreatetime(currentTime);
					ro.setLastmodifier(userpid);
					ro.setLastmodifytime(currentTime);
					roleOrgDao.save(ro);
					ridList.add(roleId);
				}
				result = true;
				List<String> oidList = new ArrayList<String> ();
				oidList.add(orgId);
				int synchResult = synchDataDown.synchRoleOrgAdd(ridList, oidList, String.valueOf(type), systemMark);
				if(synchResult > 0)
				{
					RecordLog.printLog("add role organzation success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				}
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

	public boolean deleteRoleOrgByID(List roIdList)
	{
		return this.deleteRoleOrgByID(roIdList, "");
	}
	
	public boolean deleteRoleOrgByID(List roIdList, String systemMark)
	{
		boolean result = false;
		roIdList = UltraSmUtil.removeNullData(roIdList);
		if(roIdList != null && roIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(roIdList);
				List<RoleOrg> roList = roleOrgDao.find(" from RoleOrg where pid in ( " + map.get("?") + " )", (Object[]) map.get("obj"));
				roleOrgDao.executeUpdate("delete RoleOrg where pid in ( " + map.get("?") + " )", (Object[]) map.get("obj"));
				result = true;
				int roLen = 0;
				if(roList != null)
					roLen = roList.size();
				List<String> roleIdList = new ArrayList<String> ();
				List<String> userIdList = new ArrayList<String> ();
				List<String> depIdList = new ArrayList<String> ();
				RoleOrg roleOrg;
				long orgtype;
				for(int i=0;i<roLen;i++)
				{
					roleOrg = roList.get(i);
					roleIdList.add(roleOrg.getRoleid());
					orgtype = roleOrg.getOrgtype();
					if(orgtype == 1)
						userIdList.add(roleOrg.getOrgid());
					else if(orgtype == 2)
						depIdList.add(roleOrg.getOrgid());
				}
				if(userIdList.size() > 0)
				{
					if(synchDataDown.synchRoleOrgDel(roleIdList, userIdList, "1", systemMark) > 0)
					{
						RecordLog.printLog("del role user success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
					}
				}
				if(depIdList.size() > 0)
				{
					if(synchDataDown.synchRoleOrgDel(roleIdList, depIdList, "2", systemMark) > 0)
					{
						RecordLog.printLog("del role group success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
					}
				}
			} 
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean deleteRoleOrg(String roleId, List userIdList, List depIdList)
	{
		return this.deleteRoleOrg(roleId, userIdList, depIdList, "");
	}
	
	public boolean deleteRoleOrg(String roleId, List userIdList, List depIdList, String systemMark)
	{
		boolean result1 = false;
		boolean result2 = false;
		if("".equals(StringUtils.checkNullString(roleId)))
		{
			return false;
		}
		userIdList = UltraSmUtil.removeNullData(userIdList);
		if(userIdList != null && userIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(userIdList);
				Object[] obj = ArrayTransferUtils.copyArraySimple(new Object[] {roleId}, (Object[]) map.get("obj"));
				roleOrgDao.executeUpdate("delete RoleOrg where orgtype = 1 and roleId = ? and orgid in (" + map.get("?") + ")", obj);
				result1 = true;
				List<String> roleIdList = new ArrayList<String> ();
				roleIdList.add(roleId);
				int synchResult = synchDataDown.synchRoleOrgDel(roleIdList, userIdList, "1", systemMark);
				if(synchResult > 0)
				{
					RecordLog.printLog("del role user success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				}
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		else
		{
			result1 = true;
		}
		depIdList = UltraSmUtil.removeNullData(depIdList);
		if(depIdList != null && depIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(depIdList);
				Object[] obj = ArrayTransferUtils.copyArraySimple(new Object[] {roleId}, (Object[]) map.get("obj"));
				roleOrgDao.executeUpdate("delete RoleOrg where orgtype = 2 and roleId = ? and orgid in (" + map.get("?") + ")", obj);
				result2 = true;
				List<String> roleIdList = new ArrayList<String> ();
				roleIdList.add(roleId);
				int synchResult = synchDataDown.synchRoleOrgDel(roleIdList, depIdList, "2", systemMark);
				if(synchResult > 0)
				{
					RecordLog.printLog("del role group success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				}
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		else
		{
			result2 = true;
		}
		return result1 && result2;
	}

	public boolean deleteUserRole(String userId, List roleIdList)
	{
		return this.deleteUserRole(userId, roleIdList, "");
	}
	
	public boolean deleteUserRole(String userId, List roleIdList, String systemMark)
	{
		return this.deleteOrgRole(userId, roleIdList, 1, systemMark);
	}

	public boolean deleteDepRole(String depId, List roleIdList)
	{
		return this.deleteDepRole(depId, roleIdList, "");
	}

	public boolean deleteDepRole(String depId, List roleIdList, String systemMark)
	{
		return this.deleteOrgRole(depId, roleIdList, 2, systemMark);
	}
	
	/**
	 * 此方法已通过前台查询 服务中暂不提供
	 */
	public List<RoleOrgShow> getRoleOrg(Map map)
	{
		List<RoleOrgShow> roShowList = new ArrayList();
		String type = "";//人员或组织类型，作为查询条件传过来
		StringBuffer sql = new StringBuffer();
		sql.append("select /*COUNT*/ * /*COUNT*/ from (");
		if("".equals(type) || "1".equals(type))
		{
			sql.append(" (select ro.pid pid, ro.roleid roleid, r.rolename rolename, ro.orgid orgid, u.fullname orgname, ro.orgtype orgtype");
			sql.append("    from bs_t_sm_roleorg ro, bs_t_sm_user u, bs_t_sm_role r");
			sql.append("   where ro.roleid = r.pid and ro.orgid = u.pid and ro.orgtype = 1 and u.status = 1)");
		}
		if("".equals(type))
		{
			sql.append("   union all");
		}
		if("".equals(type) || "2".equals(type))
		{
			sql.append(" (select ro.pid pid, ro.roleid roleid, r.rolename rolename, ro.orgid orgid, d.depfullname orgname, ro.orgtype orgtype");
			sql.append("    from bs_t_sm_roleorg ro, bs_t_sm_dep d, bs_t_sm_role r");
			sql.append("   where ro.roleid = r.pid and ro.orgid = d.pid and ro.orgtype = 2 and d.status = 1)");
		}
		sql.append(") order by roleid, orgtype, orgid");
		try
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			PageLimit pageLimit = PageLimit.getInstance();
			int page=pageLimit.getPageSize();
			int pagesize=pageLimit.getCURRENT_ROWS_SIZE();
			DataTable table = queryAdapter.executeQuery(sql.toString(), null,page,pagesize,2);
			if (pageLimit.getLimit() != null)
				pageLimit.getLimit().setRowAttributes(queryAdapter.getQueryResultSetCount(), pageLimit.getCURRENT_ROWS_SIZE());
			DataRow row = null;
			RoleOrgShow roShow = null;
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					row = table.getDataRow(i);
					roShow = new RoleOrgShow();
					roShow.setPid(row.getString(0));
					roShow.setRoleid(row.getString(1));
					roShow.setRolename(row.getString(2));
					roShow.setOrgid(row.getString(3));
					roShow.setOrgname(row.getString(4));
					String orgtype = row.getString(5);
					if("1".equals(orgtype))
						roShow.setOrgtype("人员");
					else if("2".equals(orgtype))
						roShow.setOrgtype("部门");
					else
						roShow.setOrgtype("");
					roShowList.add(roShow);
				}
			}
		}
		catch  (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return roShowList;
	}

	public List<UserInfo> getUserByRoleID(String roleId)
	{
		List userIdList = this.getOrgIdByRoleID(roleId, 1);
		List<UserInfo> userList = userManagerService.getUserByID(userIdList);
		return userList;
	}

	public List<DepInfo> getDepByRoleID(String roleId)
	{
		List depIdList = this.getOrgIdByRoleID(roleId, 2);
		List<DepInfo> depList = depManagerService.getDepByID(depIdList);
		return depList;
	}

	/**
	 * 根据角色ID获取组织IDList
	 * @param roleId	角色ID
	 * @param type		组织类型
	 * @return
	 */
	public List getOrgIdByRoleID(String roleId, long type)
	{
		if("".equals(StringUtils.checkNullString(roleId)))
		{
			return null;
		}
		List orgIdList = new ArrayList();
		String sql = "select ro.orgid from bs_t_sm_roleorg ro where ro.roleid = ? and ro.orgtype = ?";
		try
		{
			//查询组织ID
			QueryAdapter queryAdapter = new QueryAdapter();
			Object[] values = {roleId, type};
			DataTable table = queryAdapter.executeQuery(sql,values);
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					String temp = table.getDataRow(i).getString(0);
					if(!"".equals(StringUtils.checkNullString(temp)))
					{
						orgIdList.add(temp);
					}
				}
			}
		}
		catch  (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return orgIdList;
	}
	
	public List<RoleInfo> getRoleByUserID(String userId)
	{
		List roleIdList = this.getRoleIdByUserID(userId);
		List<RoleInfo> roleList = new ArrayList<RoleInfo>();
		if(roleIdList != null && roleIdList.size() > 0)
		{
			Map map = UltraSmUtil.getSqlParameter(roleIdList);
			roleList = roleManagerDao.find(" from RoleInfo where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
		}
		return roleList;
	}

	public List getRoleIdByUserID(String userId)
	{
		if("".equals(StringUtils.checkNullString(userId)))
		{
			return null;
		}
		List roleIdList = new ArrayList(); //存储人员角色id，包括人员和角色的直接关系、人员所属部门的角色
		try
		{
			List<String> depIdList = depManagerService.getDepIdByUser(userId, null);
			Object[] obj = {userId};
			StringBuffer sql = new StringBuffer();
			sql.append("select distinct ro.roleid from bs_t_sm_roleorg ro where 1=1");
			sql.append("  and ((ro.orgid = ? and ro.orgtype = 1)");
			if(depIdList != null && depIdList.size() > 0)
			{
				Map map = UltraSmUtil.getSqlParameter(depIdList);
				sql.append(" or (ro.orgid in (");
				sql.append(map.get("?"));
				sql.append(") and ro.orgtype = 2)");
				obj = ArrayTransferUtils.copyArraySimple(obj , (Object[]) map.get("obj"));
			}
			sql.append(")");
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql.toString(), obj);
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					String temp = table.getDataRow(i).getString(0);
					if(!"".equals(StringUtils.checkNullString(temp)))
					{
						roleIdList.add(temp);
					}
				}
			}
		}
		catch  (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return roleIdList;
	}
	public List getDeptRoleIdByUserID(String userId)
	{
		List roleIdList = new ArrayList();
		StringBuffer condition = new StringBuffer();
		List<String> depIDList = depManagerService.getAllParentIdListByUserId(userId);//.getDepIdByUser(userId, null);
		for(String orgid:depIDList)
		{
			condition.append(" ro.orgid='"+orgid+"' or");
		}
		String conditionString = null;
		if(condition.length()>0){
			conditionString = condition.substring(0, condition.length()-2);
		}
		
		String sql = " select ro.roleid from bs_t_sm_roleorg ro where "+ conditionString;
		if(conditionString!=null)
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql,null);
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					String temp = table.getDataRow(i).getString(0);
					if(!"".equals(StringUtils.checkNullString(temp)))
					{
						roleIdList.add(temp);
					}
				}
			}
		}
		return roleIdList;
	}
	
	public List<String> getAllRoleIdByUserID(String userId)
	{
		List<String> roleIdUser = this.getRoleIdByUserID(userId);
		List<String> roleIdDept = this.getDeptRoleIdByUserID(userId);
		List<String> returnRoleIdList = new ArrayList<String>();
		//去重逻辑
		Map<String,String> roleIdMap = new HashMap<String,String>();
		for(String roleID:roleIdUser)
		{
			roleIdMap.put(roleID, roleID);
		}
		for(String roleID:roleIdDept)
		{
			roleIdMap.put(roleID, roleID);
		}
		for(String key : roleIdMap.keySet())
		{
			returnRoleIdList.add(key);
		}
		return returnRoleIdList;
	}

	public String getRoleIdAndNameByUserId(String userId)
	{
		if("".equals(StringUtils.checkNullString(userId)))
		{
			return null;
		}
		String roleIdAndName = "";
		try
		{
			List<String> depIdList = depManagerService.getDepIdByUser(userId, null);
			Object[] obj = {userId};
			StringBuffer sql = new StringBuffer();
			sql.append("select distinct ro.roleid, r.rolename, r.roledns");
			sql.append("  from bs_t_sm_roleorg ro, bs_t_sm_role r");
			sql.append(" where ro.roleid = r.pid");
			sql.append("  and ((ro.orgid = ? and ro.orgtype = 1)");
			if(depIdList != null && depIdList.size() > 0)
			{
				Map map = UltraSmUtil.getSqlParameter(depIdList);
				sql.append(" or (ro.orgid in (");
				sql.append(map.get("?"));
				sql.append(") and ro.orgtype = 2)");
				obj = ArrayTransferUtils.copyArraySimple(obj , (Object[]) map.get("obj"));
			}
			sql.append(")");
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql.toString(),obj,2);
			DataRow row = null;
			String ids = "";
			String names = "";
			String dns = "";
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					row = table.getDataRow(i);
					if(!"".equals(ids))
					{
						ids += ",";
						names += ",";
						dns += ",";
					}
					ids += row.getString("roleid");
					names += row.getString("rolename");
					dns += row.getString("roledns");
				}
			}
			roleIdAndName = ids + ";" + names + ";" + dns;
		}
		catch  (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return roleIdAndName;
	}
	
	public List<RoleInfo> getRoleByDepID(String depId)
	{
		if("".equals(StringUtils.checkNullString(depId)))
		{
			return null;
		}
		List roleIdList = this.getRoleIdByOrgId(depId, 2);
		List<RoleInfo> roleList = null;
		if(roleIdList != null && roleIdList.size() > 0)
		{
			Map map = UltraSmUtil.getSqlParameter(roleIdList);
			roleList = roleManagerDao.find(" from RoleInfo where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
		}
		return roleList;
	}

	/**
	 * 根据组织ID获取角色IDList（单纯获取角色，如：人员所属部门的角色不做查询）
	 * @param orgId
	 * @param type
	 * @return
	 */
	public List getRoleIdByOrgId(String orgId, long type)
	{
		if("".equals(StringUtils.checkNullString(orgId)))
			return null;
		List roleIdList = new ArrayList();
		String sql = " select ro.roleid from bs_t_sm_roleorg ro where ro.orgid = ? and ro.orgtype = " + type;
		try
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql,new Object[] {orgId});
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					String temp = table.getDataRow(i).getString(0);
					if(!"".equals(StringUtils.checkNullString(temp)))
					{
						roleIdList.add(temp);
					}
				}
			}
		}
		catch  (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return roleIdList;
	}
	
	public boolean addRoleMenu(List roleIdList, List menuIdList)
	{
		roleIdList = UltraSmUtil.removeNullData(roleIdList);
		if(roleIdList != null && roleIdList.size() > 0)
		{
			for(int i=0;i<roleIdList.size();i++)
			{
				if(!this.addRoleMenu((String) roleIdList.get(i), menuIdList))
				{
					return false;
				}
			}
		}
		return true;
	}

	public boolean addRoleMenu(String roleId, List menuIdList)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(roleId)))
		{
			return result;
		}
		menuIdList = UltraSmUtil.removeNullData(menuIdList);
		if(menuIdList != null && menuIdList.size() > 0)
		{
			try
			{
				RoleMenu rm = null;
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
				String sql = " select distinct menuid from bs_t_sm_rolemenutree where roleid = ? ";
				QueryAdapter queryAdapter = new QueryAdapter();
				DataTable table = queryAdapter.executeQuery(sql, new Object[] {roleId});
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
					if(oldMenuIdList.indexOf(menuId) >= 0)
					{
						continue;
					}
					rm = new RoleMenu();
					rm.setRoleid(roleId);
					rm.setMenuid(menuId);
					rm.setCreater(userpid);
					rm.setCreatetime(currentTime);
					rm.setLastmodifier(userpid);
					rm.setLastmodifytime(currentTime);
					roleMenuDao.save(rm);
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

	public boolean deleteRoleMenuByID(List rmIdList)
	{
		boolean result = false;
		rmIdList = UltraSmUtil.removeNullData(rmIdList);
		if(rmIdList != null && rmIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(rmIdList);
				roleMenuDao.executeUpdate(" delete RoleMenu where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
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

	public boolean deleteRoleMenu(String roleId, List menuIdList)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(roleId)))
		{
			return result;
		}
		menuIdList = UltraSmUtil.removeNullData(menuIdList);
		if(menuIdList != null && menuIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(menuIdList);
				Object[] obj = ArrayTransferUtils.copyArraySimple(new Object[] {roleId}, (Object[]) map.get("obj"));
				roleMenuDao.executeUpdate(" delete RoleMenu where roleid = ? and menuid in (" + map.get("?") + ")", obj);
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
	
	/**
	 * 此方法已通过前台查询 服务中暂不提供
	 */
	public List<RoleMenuShow> getRoleMenu(Map map)
	{
		List<RoleMenuShow> rmShowList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select /*COUNT*/ rm.pid, rm.roleid, r.rolename, rm.menuid, m.nodename, m.nodemark, m.nodeurl /*COUNT*/");
		sql.append("  from bs_t_sm_menutree m, bs_t_sm_rolemenutree rm, bs_t_sm_role r");
		sql.append(" where rm.roleid = r.pid and rm.menuid = m.pid and m.status = 1");
		sql.append(" order by rm.roleid, m.ordernum");
		try
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			PageLimit pageLimit = PageLimit.getInstance();
			int page=pageLimit.getPageSize();
			int pagesize=pageLimit.getCURRENT_ROWS_SIZE();
			DataTable table = queryAdapter.executeQuery(sql.toString(), null,page,pagesize,2);
			if (pageLimit.getLimit() != null)
			{
				pageLimit.getLimit().setRowAttributes(queryAdapter.getQueryResultSetCount(), pageLimit.getCURRENT_ROWS_SIZE());
			}
			DataRow row = null;
			RoleMenuShow rmShow = null;
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					row = table.getDataRow(i);
					rmShow = new RoleMenuShow();
					rmShow.setPid(row.getString(0));
					rmShow.setRoleid(row.getString(1));
					rmShow.setRolename(row.getString(2));
					rmShow.setMenuid(row.getString(3));
					rmShow.setNodename(row.getString(4));
					rmShow.setNodemark(row.getString(5));
					rmShow.setNodeurl(row.getString(6));
					rmShowList.add(rmShow);
				}
			}
		}
		catch  (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return rmShowList;
	}

	public List<MenuDtree> getMenuByRoleID(String roleId)
	{
		List<MenuDtree> treeList = null;
		if(!"".equals(StringUtils.checkNullString(roleId)))
		{
			if("0".equals(roleId))
			{
				treeList = this.getAllMenu();
			}
			else
			{
				List roleIdList = new ArrayList();
				roleIdList.add(roleId);
				treeList = this.getMenuByRoleID(roleIdList);
			}
		}
		return treeList;
	}

	public List<MenuDtree> getAllMenu()
	{
		List<MenuDtree> treeList = new ArrayList();
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("  select level, pid, nodename, nodemark, parentid, nodeurl");
			sql.append("    from bs_t_sm_menutree");
			sql.append("   where status = 1");
			sql.append("   start with parentid = '0'");
			sql.append(" connect by parentid = prior pid");
			sql.append("   order siblings by ordernum");
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql.toString(), null);
			MenuDtree tree = null;
			DataRow row = null;
			HashMap treeMap = null;
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					row = table.getDataRow(i);
					tree = new MenuDtree();
					tree.setLevel(Integer.parseInt(row.getString("level")));
					tree.setId(StringUtils.checkNullString(row.getString("pid")));
					tree.setParentid(StringUtils.checkNullString(row.getString("parentid")));
					tree.setText(StringUtils.checkNullString(row.getString("nodename")));
					tree.setMark(StringUtils.checkNullString(row.getString("nodemark")));
					treeMap = new HashMap();
					treeMap.put("url", StringUtils.checkNullString(row.getString("nodeurl")));
					tree.setUserdata(treeMap);
					treeList.add(tree);
				}
			}
		}
		catch  (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return treeList;
	}
	
	public List<MenuDtree> getMenuByRoleID(List roleIdList)
	{
		List<MenuDtree> treeList = new ArrayList();
		roleIdList = UltraSmUtil.removeNullData(roleIdList);
		if(roleIdList != null && roleIdList.size() > 0)
		{
			try
			{
				QueryAdapter queryAdapter = new QueryAdapter();
				Map map = UltraSmUtil.getSqlParameter(roleIdList);
				StringBuffer sql = new StringBuffer();
				sql.append("select level, m.pid, m.nodename, m.nodemark nodemark, m.parentid, m.nodeurl, m.classname, m.openway");
				sql.append("  from (select distinct m.pid pid, m.nodename nodename, m.nodemark nodemark, m.parentid parentid, m.nodeurl nodeurl, m.classname classname, m.openway openway, ordernum");
				sql.append("          from bs_t_sm_menutree m, bs_t_sm_rolemenutree rm");
				sql.append("         where m.pid = rm.menuid");
				sql.append("           and m.status = 1");
				sql.append("           and rm.roleid in (" + map.get("?") + ")) m");
				sql.append(" start with parentid = '0'");
				sql.append(" connect by parentid = prior pid");
				sql.append(" order siblings by m.ordernum");
				DataTable table = queryAdapter.executeQuery(sql.toString(), (Object[]) map.get("obj"),0,0,2);
				MenuDtree tree = null;
				DataRow row = null;
				HashMap treeMap = null;
				if(table != null && table.length() > 0)
				{
					for(int i=0;i<table.length();i++)
					{
						row = table.getDataRow(i);
						tree = new MenuDtree();
						tree.setLevel(Integer.parseInt(row.getString("level")));
						tree.setId(StringUtils.checkNullString(row.getString("pid")));
						tree.setParentid(StringUtils.checkNullString(row.getString("parentid")));
						String classname = StringUtils.checkNullString(row.getString("classname"));
						if(!StringUtils.checkNullString(classname).equals(""))
							tree.setChild("1");
						tree.setText(StringUtils.checkNullString(row.getString("nodename")));
						tree.setMark(StringUtils.checkNullString(row.getString("nodemark")));
						treeMap = new HashMap();
						treeMap.put("url", StringUtils.checkNullString(row.getString("nodeurl")));
						treeMap.put("openway", StringUtils.checkNullString(row.getString("openway")));
						tree.setUserdata(treeMap);
						treeList.add(tree);
					}
				}
			}
			catch  (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		return treeList;
	}

	public boolean addRoleResOp(List roleIdList, List resIdList, List opIdList)
	{
		roleIdList = UltraSmUtil.removeNullData(roleIdList);
		if(roleIdList != null && roleIdList.size() > 0)
		{
			for(int i=0;i<roleIdList.size();i++)
			{
				if(!this.addRoleResOp((String) roleIdList.get(i), resIdList, opIdList))
				{
					return false;
				}
			}
		}
		return true;
	}

	public boolean addRoleResOp(String roleId, List resIdList, List opIdList)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(roleId)))
		{
			return result;
		}
		resIdList = UltraSmUtil.removeNullData(resIdList);
		opIdList = UltraSmUtil.removeNullData(opIdList);
		if(resIdList != null && resIdList.size() > 0 && opIdList != null && opIdList.size() > 0)
		{
			try
			{
				RoleResOp rro = null;
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
				for(int i=0;i<resIdList.size();i++)
				{
					String resId = (String) resIdList.get(i);
					List oldOpIdList = this.getOpIdByRoleResId(roleId, resId);
					for(int j=0;j<opIdList.size();j++)
					{
						String opId = (String) opIdList.get(j);
						if(oldOpIdList.indexOf(opId) >= 0)
						{
							continue;
						}
						rro = new RoleResOp();
						rro.setRoleid(roleId);
						rro.setResid(resId);
						rro.setOpid(opId);
						rro.setCreater(userpid);
						rro.setCreatetime(currentTime);
						rro.setLastmodifier(userpid);
						rro.setLastmodifytime(currentTime);
						roleResOpDao.save(rro);
					}
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

	public boolean deleteRoleResOpByID(List rroIdList)
	{
		boolean result = false;
		rroIdList = UltraSmUtil.removeNullData(rroIdList);
		if(rroIdList != null && rroIdList.size() > 0)
		{
			try
			{
				if(this.clearDataPrivilegeByRroID(rroIdList))
				{
					Map map = UltraSmUtil.getSqlParameter(rroIdList);
					roleResOpDao.executeUpdate(" delete RoleResOp where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
					result = true;
				}
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
	
	public boolean setRoleResOpDisable(List rroIdList)
	{
		boolean result = false;
		rroIdList = UltraSmUtil.removeNullData(rroIdList);
		if(rroIdList != null && rroIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(rroIdList);
				roleResOpDao.executeUpdate(" update RoleResOp set status = 0 where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
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

	public boolean setRoleResOpEnabled(List rroIdList)
	{
		boolean result = false;
		rroIdList = UltraSmUtil.removeNullData(rroIdList);
		if(rroIdList != null && rroIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(rroIdList);
				roleResOpDao.executeUpdate(" update RoleResOp set status = 1 where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
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

	/**
	 * 此方法已通过前台查询 服务中暂不提供
	 */
	public List<RoleResOpShow> getRoleResOp(Map map)
	{
		List<RoleResOpShow> rroShowList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select /*COUNT*/ rro.pid, rro.roleid, r.rolename, rro.resid, rs.resname, rro.opid, op.opname, rro.status /*COUNT*/");
		sql.append("  from bs_t_sm_role r, bs_t_sm_resource rs, bs_t_sm_operate op, bs_t_sm_roleresop rro");
		sql.append(" where rro.roleid = r.pid and rro.resid = rs.pid and rro.opid = op.pid");
		sql.append(" order by rro.roleid, rro.resid, rro.opid");
		try
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			PageLimit pageLimit = PageLimit.getInstance();
			int page=pageLimit.getPageSize();
			int pagesize=pageLimit.getCURRENT_ROWS_SIZE();
			DataTable table = queryAdapter.executeQuery(sql.toString(), null, page, pagesize, 2);
			if (pageLimit.getLimit() != null)
				pageLimit.getLimit().setRowAttributes(queryAdapter.getQueryResultSetCount(), pageLimit.getCURRENT_ROWS_SIZE());
			DataRow row = null;
			RoleResOpShow rroShow = null;
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					row = table.getDataRow(i);
					rroShow = new RoleResOpShow();
					rroShow.setPid(row.getString(0));
					rroShow.setRoleid(row.getString(1));
					rroShow.setRolename(row.getString(2));
					rroShow.setResid(row.getString(3));
					rroShow.setResname(row.getString(4));
					rroShow.setOpid(row.getString(5));
					rroShow.setOpname(row.getString(6));
					String status = row.getString(7);
					rroShow.setStatus("1".equals(StringUtils.checkNullString(status))?"启用":"停用");
					rroShowList.add(rroShow);
				}
			}
		}
		catch  (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return rroShowList;
	}

	public RoleResOpShow getRroShowByID(String rroId)
	{
		RoleResOpShow rroShow = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select rro.pid, rro.roleid, r.rolename, rro.resid, rs.resname, rro.opid, op.opname, rro.status");
		sql.append("  from bs_t_sm_role r, bs_t_sm_resource rs, bs_t_sm_operate op, bs_t_sm_roleresop rro");
		sql.append(" where rro.roleid = r.pid and rro.resid = rs.pid and rro.opid = op.pid and rro.pid = ?");
		sql.append(" order by rro.roleid, rro.resid, rro.opid");
		try
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql.toString(), new Object[] {rroId}, 0, 0, 2);	
			DataRow row = null;
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					row = table.getDataRow(i);
					rroShow = new RoleResOpShow();
					rroShow.setPid(row.getString(0));
					rroShow.setRoleid(row.getString(1));
					rroShow.setRolename(row.getString(2));
					rroShow.setResid(row.getString(3));
					rroShow.setResname(row.getString(4));
					rroShow.setOpid(row.getString(5));
					rroShow.setOpname(row.getString(6));
					String status = row.getString(7);
					rroShow.setStatus("1".equals(StringUtils.checkNullString(status))?"启用":"停用");
				}
			}
		}
		catch  (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return rroShow;
	}
	
	public List<Resource> getResByRoleID(String roleId)
	{
		List<Resource> resList = null;
		if(!"".equals(StringUtils.checkNullString(roleId)))
		{
			List roleIdList = new ArrayList();
			roleIdList.add(roleId);
			resList = this.getResByRoleID(roleIdList);
		}
		return resList;
	}

	public List<Resource> getResByRoleID(List roleIdList)
	{
		List<Resource> resList = null;
		roleIdList = UltraSmUtil.removeNullData(roleIdList);
		if(roleIdList != null && roleIdList.size() > 0)
		{
			List resIdList = new ArrayList();
			try
			{
				QueryAdapter queryAdapter = new QueryAdapter();
				Map map = UltraSmUtil.getSqlParameter(roleIdList);
				DataTable table = queryAdapter.executeQuery("select distinct rro.resid from bs_t_sm_roleresop rro where rro.roleid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
				if(table != null && table.length() > 0)
				{
					for(int i=0;i<table.length();i++)
					{
						String temp = table.getDataRow(i).getString(0);
						if(!"".equals(StringUtils.checkNullString(temp)))
						{
							resIdList.add(temp);
						}
					}
				}
			}
			catch  (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
			if(resIdList != null && resIdList.size() > 0)
			{
				Map map = UltraSmUtil.getSqlParameter(resIdList);
				resList = resourceDao.find(" from Resource where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
			}
		}
		return resList;
	}

	public List<Operate> getOpByRoleResID(String roleId, String resId)
	{
		if("".equals(StringUtils.checkNullString(roleId)) || "".equals(StringUtils.checkNullString(resId)))
		{
			return null;
		}
		List roleIdList = new ArrayList();
		roleIdList.add(roleId);
		return this.getOpByRoleResID(roleIdList, resId);
	}

	public List<Operate> getOpByRoleResID(List roleIdList, String resId)
	{
		if("".equals(StringUtils.checkNullString(resId)))
		{
			return null;
		}
		List<Operate> opList = null;
		roleIdList = UltraSmUtil.removeNullData(roleIdList);
		if(roleIdList != null && roleIdList.size() > 0)
		{
			List opIdList = this.getOpIdByRoleResId(roleIdList, resId);
			if(opIdList != null && opIdList.size() > 0)
			{
				opList = operateManagerService.getOperateByID(opIdList);
			}
		}
		return opList;
	}

	private List getOpIdByRoleResId(String roleId, String resId)
	{
		if("".equals(StringUtils.checkNullString(roleId)) || "".equals(StringUtils.checkNullString(resId)))
		{
			return null;
		}
		List roleIdList = new ArrayList();
		roleIdList.add(roleId);
		return this.getOpIdByRoleResId(roleIdList, resId);
	}
	
	private List getOpIdByRoleResId(List roleIdList, String resId)
	{
		if("".equals(StringUtils.checkNullString(resId)))
		{
			return null;
		}
		List opIdList = new ArrayList();
		roleIdList = UltraSmUtil.removeNullData(roleIdList);
		if(roleIdList != null && roleIdList.size() > 0)
		{
			try
			{
				QueryAdapter queryAdapter = new QueryAdapter();
				Map map = UltraSmUtil.getSqlParameter(roleIdList);
				Object[] obj = ArrayTransferUtils.copyArraySimple(new Object[] {resId}, (Object[]) map.get("obj"));
				DataTable table = queryAdapter.executeQuery("select distinct opid from bs_t_sm_roleresop rro where rro.resid = ? and rro.roleid in (" + map.get("?") + ")", obj);
				if(table != null && table.length() > 0)
				{
					for(int i=0;i<table.length();i++)
					{
						String temp = table.getDataRow(i).getString(0);
						if(!"".equals(StringUtils.checkNullString(temp)))
						{
							opIdList.add(temp);
						}
					}
				}
			}
			catch  (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		return opIdList;
	}
	
	public List getRroIdByRoleResOpID(String roleId, String resId, List opIdList)
	{
		List rroIdList = new ArrayList();
		if("".equals(StringUtils.checkNullString(roleId)) || "".equals(StringUtils.checkNullString(resId)))
			return rroIdList;
		opIdList = UltraSmUtil.removeNullData(opIdList);
		if(opIdList != null && opIdList.size() > 0)
		{
			try
			{
				QueryAdapter queryAdapter = new QueryAdapter();
				Map map = UltraSmUtil.getSqlParameter(opIdList);
				Object[] obj = ArrayTransferUtils.copyArraySimple(new Object[] {roleId, resId}, (Object[]) map.get("obj"));
				String sql = "select pid from bs_t_sm_roleresop where roleid = ? and resid = ? and opid in (" + map.get("?") + ")";
				DataTable table = queryAdapter.executeQuery(sql, obj);
				if(table != null && table.length() > 0)
				{
					for(int i=0;i<table.length();i++)
					{
						String temp = table.getDataRow(i).getString(0);
						if(!"".equals(StringUtils.checkNullString(temp)))
						{
							rroIdList.add(temp);
						}
					}
				}
			}
			catch  (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		return rroIdList;
	}
	
	public boolean addDataPrivilege(List rroIdList, List rpIdList, List rpValueList, String sql)
	{
		boolean resultOp = false;
		boolean resultSql = false;
		if(this.clearDataPrivilegeByRroID(rroIdList))//清空数据权限
		{
			resultSql = this.addSqlDataPrivilege(rroIdList, sql);//添加SQL条件配置权限
			resultOp = this.addOpDataPrivilege(rroIdList, rpIdList, rpValueList);//添加操作数据权限
		}
		return resultOp && resultSql;
	}
	
	/**
	 * 添加操作数据权限
	 * @param rroIdList 角色资源操作IDList
	 * @param rpIdList 资源属性IDList
	 * @param rpValueList 属性对应权限数据List
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addOpDataPrivilege(List rroIdList, List rpIdList, List rpValueList)
	{
		boolean result = false;
		rpIdList = UltraSmUtil.removeNullData(rpIdList);
		rroIdList = UltraSmUtil.removeNullData(rroIdList);
		if(rroIdList!=null && rroIdList.size() > 0 && rpIdList != null && rpIdList.size() > 0)
		{
			try
			{
				OpDataPrivilege odp = null;
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
				for(int k=0;k<rroIdList.size();k++)
				{
					String rroId = (String) rroIdList.get(k);
					RoleResOp rro = roleResOpDao.get(rroId);
					for(int i=0;i<rpIdList.size();i++)
					{
						odp = new OpDataPrivilege();
						odp.setRroid(rroId);
						odp.setResproid((String) rpIdList.get(i));
						odp.setResid(rro.getResid());
						odp.setOpid(rro.getOpid());
						String values = (String) rpValueList.get(i); //这个值包含了数据的id集合、名称集合、操作符
						String[] value = values.split(";");
						odp.setPrivilegedata(value[0]);
						odp.setListvalue(value[1]);
						odp.setOperator(value[2]);
						odp.setCreater(userpid);
						odp.setCreatetime(currentTime);
						odp.setLastmodifier(userpid);
						odp.setLastmodifytime(currentTime);
						opDataPrivilegeDao.save(odp);
					}
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
	
	/**
	 * 添加SQL条件配置权限
	 * @param rroIdList 角色资源操作IDList
	 * @param sql SQL条件串
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addSqlDataPrivilege(List rroIdList, String sql)
	{
		boolean result = false;
		rroIdList = UltraSmUtil.removeNullData(rroIdList);
		if(rroIdList != null && rroIdList.size() > 0)
		{
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
			try
			{
				for(int i=0;i<rroIdList.size();i++)
				{
					String rroId = (String) rroIdList.get(i);
					SqlDataPrivilege sdp = this.getSqlDataByRroId(rroId);
					String oldSql = (sdp==null)?null:sdp.getPrivilegedata();
					if("".equals(StringUtils.checkNullString(sql)))
					{
						if(oldSql != null)
						{
							this.deleteSqlDataPrivilegeByRroID(rroId);
						}
					}
					else
					{
						if(oldSql != null)
						{
							sdp.setPrivilegedata(sql);
							sdp.setLastmodifier(userpid);
							sdp.setLastmodifytime(currentTime);
							sqlDataPrivilegeDao.saveOrUpdate(sdp);
						}
						else
						{
							sdp = new SqlDataPrivilege();
							sdp.setRroid(rroId);
							RoleResOp rro = roleResOpDao.get(rroId);
							sdp.setResid(rro.getResid());
							sdp.setOpid(rro.getOpid());
							sdp.setPrivilegedata(sql);
							sdp.setCreater(userpid);
							sdp.setCreatetime(currentTime);
							sdp.setLastmodifier(userpid);
							sdp.setLastmodifytime(currentTime);
							sqlDataPrivilegeDao.save(sdp);
						}
					}
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
	
	public SqlDataPrivilege getSqlDataByRroId(String rroId)
	{
		SqlDataPrivilege sdp = null;
		List<SqlDataPrivilege> sdpList = sqlDataPrivilegeDao.find(" from SqlDataPrivilege where rroid = ?", new Object[] {rroId});
		if(sdpList != null && sdpList.size() > 0)
		{
			sdp = sdpList.get(0);
		}
		return sdp;
	}

	public boolean clearDataPrivilegeByRroID(String rroId)
	{
		if("".equals(StringUtils.checkNullString(rroId)))
		{
			return false;
		}
		List rroIdList = new ArrayList();
		rroIdList.add(rroId);
		return this.clearDataPrivilegeByRroID(rroIdList);
	}
	
	private boolean clearDataPrivilegeByRroID(List rroIdList)
	{
		boolean result = false;
		rroIdList = UltraSmUtil.removeNullData(rroIdList);
		if(rroIdList != null && rroIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(rroIdList);
				opDataPrivilegeDao.executeUpdate(" delete OpDataPrivilege where rroid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
				sqlDataPrivilegeDao.executeUpdate(" delete SqlDataPrivilege where rroid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
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

	public List<ResPropertyShow> getOdpShowByRroID(String rroId)
	{
		List<ResPropertyShow> rpShowList = new ArrayList();
		try
		{
			RoleResOp rro = roleResOpDao.get(rroId);
			StringBuffer sql = new StringBuffer();
			sql.append("select rp.pid, rp.fieldname, rp.fielddisplayvalue, rp.intype, rp.indatasourtype, rp.indata, odp.operator, odp.privilegedata, odp.listvalue");
			sql.append("  from (select resproid,operator,privilegedata,listvalue from bs_t_sm_opdataprivilege odp where odp.rroid = ?) odp, bs_t_sm_resproperty rp");
			sql.append(" where odp.resproid(+) = rp.pid and rp.respid = ?");
			sql.append(" order by rp.ordernum");
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql.toString(), new Object[] {rroId, rro.getResid()});
			if(table != null && table.length() > 0)
			{
				DataRow row = null;
				ResPropertyShow rpShow = null;
				for(int i=0;i<table.length();i++)
				{
					rpShow = new ResPropertyShow();
					row = table.getDataRow(i);
					rpShow.setPid(row.getString("pid"));
					rpShow.setFieldname(row.getString("fieldname"));
					rpShow.setFielddisplayvalue(row.getString("fielddisplayvalue"));
					String intype = row.getString("intype");
					rpShow.setIntype(intype);
					String indatasourtype = row.getString("indatasourtype");
					rpShow.setIndatasourtype(indatasourtype);
					String indata = row.getString("indata");
					rpShow.setIndata(indata);
					rpShow.setOperator(row.getString("operator"));
					String value = row.getString("privilegedata");
					rpShow.setValue(value);
					rpShow.setListvalue(row.getString("listvalue"));
					rpShowList.add(rpShow);
				}
			}
		}
		catch  (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return rpShowList;
	}
	
	public List<DataInfo> getInfoBySql(String sql)
	{
		List<DataInfo> dataList = new ArrayList();
		if("".equals(StringUtils.checkNullString(sql)))
		{
			return dataList;
		}
		try
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql, null, 0, 0, 2);
			if(table != null && table.length() > 0)
			{
				DataRow row = null;
				DataInfo data = null;
				for(int i=0;i<table.length();i++)
				{
					row = table.getDataRow(i);
					data = new DataInfo();
					data.setId(StringUtils.checkNullString(row.getString(0)));
					data.setValue(StringUtils.checkNullString(row.getString(1)));
					dataList.add(data);
				}
			}
		}
		catch  (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return dataList;
	}
	
	/**
	删除组织所属角色
	@param orgId - 组织ID
	@param roleIdList
	@param type - 类型(用来标识人员或机构)
	@param systemMark 系统来源标识
	@return boolean
	@roseuid 4BEB9D0E02BF
	 */
	private boolean deleteOrgRole(String orgId, List roleIdList, long type, String systemMark)
	{
		boolean result = false;
		if(orgId == null)
		{
			return result;
		}
		roleIdList = UltraSmUtil.removeNullData(roleIdList);
		if(roleIdList != null && roleIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(roleIdList);
				Object[] obj = ArrayTransferUtils.copyArraySimple(new Object[] {type, orgId}, (Object[]) map.get("obj"));
				roleOrgDao.executeUpdate("delete RoleOrg where orgtype = ? and orgId = ? and roleId in ( " + map.get("?") + " )", obj);
				result = true;
				List<String> orgIdList = new ArrayList<String> ();
				orgIdList.add(orgId);
				int synchResult = synchDataDown.synchRoleOrgDel(roleIdList, orgIdList, String.valueOf(type), systemMark);
				if(synchResult > 0)
				{
					RecordLog.printLog("del role group user success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				}
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

	/**
	根据角色ID拷贝角色菜单目录树权限
	@param newRoleId - 新创建角色ID
	@param roleId - 角色ID（将此角色权限拷贝到新创建的角色上）
	@return boolean
	 */
	private boolean copyMenuPrivilege(String newRoleId, String roleId)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(newRoleId)) || "".equals(StringUtils.checkNullString(roleId)))
		{
			return result;
		}
		List menuIdList = this.getMenuIdByRoleID(roleId);
		if(menuIdList != null && menuIdList.size() > 0)
		{
			try
			{
				RoleMenu rm = null;
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
				for(int i=0;i<menuIdList.size();i++)
				{
					rm = new RoleMenu();
					rm.setRoleid(newRoleId);
					rm.setMenuid((String) menuIdList.get(i));
					rm.setCreater(userpid);
					rm.setCreatetime(currentTime);
					rm.setLastmodifier(userpid);
					rm.setLastmodifytime(currentTime);
					roleMenuDao.save(rm);
				}
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
	
	public List getMenuIdByRoleID(String roleId)
	{
		if("".equals(StringUtils.checkNullString(roleId)))
		{
			return null;
		}
		List list = new ArrayList();
		String sql = " select m.pid from bs_t_sm_menutree m, bs_t_sm_rolemenutree rm where m.pid = rm.menuid and m.status = 1 and rm.roleid = ?";
		try
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql, new Object[] {roleId});
			String temp;
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					temp = table.getDataRow(i).getString(0);
					if(!"".equals(StringUtils.checkNullString(temp)))
					{
						list.add(temp);
					}
				}
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return list;
	}

	/**
	根据角色ID拷贝角色资源操作权限
	@param newRoleId - 新创建角色ID
	@param roleId - 角色ID（将此角色权限拷贝到新创建的角色上）
	@return boolean
	 */
	private boolean copyResOpPrivilege(String newRoleId, String roleId)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(newRoleId)) || "".equals(StringUtils.checkNullString(roleId)))
		{
			return result;
		}
		try
		{
			List<RoleResOp> rroList = roleResOpDao.find(" from RoleResOp where roleid = ? order by pid ", new Object[] {roleId});
			if(rroList != null && rroList.size() > 0)
			{
				RoleResOp rro = null;
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
				List rroIdList = new ArrayList(); //存储被复制角色的权限PID，A复制给B，这里代表A的权限PID
				List newRroIdList = new ArrayList(); //存储复制角色的权限PID，A复制给B，这里代表B的权限PID
				RoleResOp newRro = null;
				for(int i=0;i<rroList.size();i++)
				{
					rro = rroList.get(i);
					rroIdList.add(rro.getPid());
					newRro = new RoleResOp();
					newRro.setRoleid(newRoleId);
					newRro.setResid(rro.getResid());
					newRro.setOpid(rro.getOpid());
					newRro.setStatus(rro.getStatus());
					newRro.setCreater(userpid);
					newRro.setCreatetime(currentTime);
					newRro.setLastmodifier(userpid);
					newRro.setLastmodifytime(currentTime);
					roleResOpDao.save(newRro);
					newRroIdList.add(newRro.getPid());
				}
				if(rroIdList != null && rroIdList.size() > 0)
				{
					Map map = UltraSmUtil.getSqlParameter(rroIdList);
					List<OpDataPrivilege> odpList = opDataPrivilegeDao.find(" from OpDataPrivilege where rroid in (" + map.get("?") + ") order by rroid ", (Object[]) map.get("obj"));
					if(odpList != null && odpList.size() > 0)
					{//复制操作数据权限
						OpDataPrivilege odp = null;
						OpDataPrivilege newOdp = null;
						for(int i=0;i<odpList.size();i++)
						{
							odp = odpList.get(i);
							int n = rroIdList.indexOf(odp.getRroid());
							newOdp = new OpDataPrivilege();
							newOdp.setRroid((String) newRroIdList.get(n));
							newOdp.setResid(odp.getResid());
							newOdp.setOpid(odp.getOpid());
							newOdp.setResproid(odp.getResproid());
							newOdp.setPrivilegedata(odp.getPrivilegedata());
							newOdp.setListvalue(odp.getListvalue());
							newOdp.setCreater(userpid);
							newOdp.setCreatetime(currentTime);
							newOdp.setLastmodifier(userpid);
							newOdp.setLastmodifytime(currentTime);
							opDataPrivilegeDao.save(newOdp);
						}
					}
					List<SqlDataPrivilege> sdpList = sqlDataPrivilegeDao.find(" from SqlDataPrivilege where rroid in (" + map.get("?") + ") order by rroid ", (Object[]) map.get("obj"));
					if(sdpList != null && sdpList.size() > 0)
					{//复制SQL数据权限
						SqlDataPrivilege sdp = null;
						SqlDataPrivilege newSdp = null;
						for(int i=0;i<sdpList.size();i++)
						{
							sdp = sdpList.get(i);
							int n = rroIdList.indexOf(sdp.getRroid());
							newSdp = new SqlDataPrivilege();
							newSdp.setRroid((String) newRroIdList.get(n));
							newSdp.setResid(sdp.getResid());
							newSdp.setOpid(sdp.getOpid());
							newSdp.setPrivilegedata(sdp.getPrivilegedata());
							newSdp.setCreater(userpid);
							newSdp.setCreatetime(currentTime);
							newSdp.setLastmodifier(userpid);
							newSdp.setLastmodifytime(currentTime);
							sqlDataPrivilegeDao.save(newSdp);
						}
					}
				}
			}
			result = true;
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}

	public boolean clearRroByResId(List resIdList)
	{
		return this.clearAllPrivilege(resIdList,"res");
	}
	
	public boolean clearRroByOpId(List opIdList)
	{
		return this.clearAllPrivilege(opIdList,"op");
	}
	
	private boolean clearPrivilegeByRoleId(List roleIdList)
	{
		boolean resultMenu = false;
		if(roleIdList != null && roleIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(roleIdList);
				roleMenuDao.executeUpdate(" delete RoleMenu where roleid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
				resultMenu = true;
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		return resultMenu && this.clearAllPrivilege(roleIdList,"role");
	}
	
	/**
	 * 清除角色所有权限
	 * @param roleId	角色ID
	 * @return
	 */
	private boolean clearAllPrivilege(List idList, String type)
	{
		boolean resultResOp = false;
		idList = UltraSmUtil.removeNullData(idList);
		if(idList != null && idList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(idList);
				String sql = "";
				if("role".equals(type))
				{
					sql = " select pid from bs_t_sm_roleresop where roleid in (" + map.get("?") + ")";
				}
				else if("res".equals(type))
				{
					sql = " select pid from bs_t_sm_roleresop where resid in (" + map.get("?") + ")";
				}
				else if("op".equals(type))
				{
					sql = " select pid from bs_t_sm_roleresop where opid in (" + map.get("?") + ")";
				}
				QueryAdapter queryAdapter = new QueryAdapter();
				DataTable table = queryAdapter.executeQuery(sql, (Object[]) map.get("obj"));
				List rroIdList = new ArrayList();
				if(table != null && table.length() > 0)
				{
					DataRow row = null;
					for(int i=0;i<table.length();i++)
					{
						row = table.getDataRow(i);
						rroIdList.add(row.getObject("pid"));
					}
				}
				if(this.deleteRoleResOpByID(rroIdList))
				{
					resultResOp = true;
				}
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		else
		{
			resultResOp = true;
		}
		return resultResOp;
	}

	public boolean deleteOdpByRpId(List rpIdList)
	{
		boolean result = false;
		rpIdList = UltraSmUtil.removeNullData(rpIdList);
		if(rpIdList != null && rpIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(rpIdList);
				opDataPrivilegeDao.executeUpdate("delete OpDataPrivilege where resproid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
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
	
	/**
	根据资源属性ID删除操作数据权限
	删除操作为rroId的并且资源属性ID为rpId的操作数据权限
	@param rroId - 角色资源操作ID
	@param rpId - 资源属性ID
	@return boolean
	 */
	private boolean deleteOpDataPrivilegeByRroRpID(String rroId, String rpId) {
		return true;
	}

	/**
	@param rroId
	@return boolean
	 */
	private boolean deleteSqlDataPrivilegeByRroID(String rroId)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(rroId)))
			return result;
		try
		{
			sqlDataPrivilegeDao.executeUpdate(" delete SqlDataPrivilege where rroid = ?", new Object[] {rroId});
			result = true;
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}

	public List<String> clearChildFrmList(List<String> roleId)
	{
		if(roleId==null||roleId.size()==0)
		{
			return null;
		}
		//排重
		Map<String,String> idmap = new HashMap<String,String>();
		for(int i=0;i<roleId.size();i++)
		{
			idmap.put(roleId.get(i),roleId.get(i));
		}
		Set<String> indexlst = idmap.keySet();
		roleId.clear();
		for(String str:indexlst)
		{
			roleId.add(idmap.get(str));
		}
		//查询DNS
		List<Integer> countLst = new ArrayList<Integer>();
		List<String> dnsLst = new ArrayList<String>();
		Map map = UltraSmUtil.getSqlParameter(roleId);
		String sql = "select pid,roledns from bs_t_sm_role where pid in("+map.get("?")+")";
		QueryAdapter qad = new QueryAdapter();
		DataTable dtb = qad.executeQuery(sql,(Object[])map.get("obj"));
		roleId.clear();
		for(int i=0;i<dtb.length();i++)
		{
			DataRow dr = dtb.getDataRow(i);
			roleId.add(dr.getString("pid"));
			dnsLst.add(dr.getString("roledns"));
		}
		//记录需要移除的
		for(int i=0;i<dnsLst.size();i++)
		{
			String curDns = dnsLst.get(i);
			for(int j=0;j<dnsLst.size();j++)
			{
				if(j!=i)
				{
					if(dnsLst.get(j).indexOf(curDns)==0)
					{
						countLst.add(j);
					}
				}
			}
		}
		//去重
		Map<String,Integer> tempIndex = new HashMap<String,Integer>();
		for(int i=0;i<countLst.size();i++)
		{
			tempIndex.put(String.valueOf(countLst.get(i)), countLst.get(i));
		}
		countLst.clear();
		for(String str:tempIndex.keySet())
		{
			countLst.add(tempIndex.get(str));
		}
		Collections.sort(countLst); //升序
		for(int i=countLst.size()-1;i>=0;i--)
		{
			roleId.remove(countLst.get(i).intValue());
		}
		return roleId;
	}
	
	/**
	 * 清空权限缓存
	 * @param roleIdList 角色IDList
	 */
	private void clearPrivilegeCache(List roleIdList)
	{
		List userIdList = this.getUserIdByRoleID(roleIdList);//根据角色IDList获取这些角色所包含的所有用户ID
		int userLen = 0;//用户个数
		if(userIdList != null)
			userLen = userIdList.size();
		for(int i=0;i<userLen;i++)
		{
			String userId = (String) userIdList.get(i);
			Object obj = BaseCacheManager.get(Constants.PRIVILEGECACHE, userId);
			if(obj != null)
				BaseCacheManager.removeElement(Constants.PRIVILEGECACHE, userId);
		}
	}
	
	private List getUserIdByRoleID(List roleIdList)
	{
		List userIdList = new ArrayList();
		roleIdList = UltraSmUtil.removeNullData(roleIdList);
		if(roleIdList != null && roleIdList.size() > 0)
		{
			Map map = UltraSmUtil.getSqlParameter(roleIdList);
			String sql = "select ro.orgid from bs_t_sm_roleorg ro where ro.roleid in (" + map.get("?") + ")";
			try
			{
				//查询组织ID
				QueryAdapter queryAdapter = new QueryAdapter();
				DataTable table = queryAdapter.executeQuery(sql, (Object[]) map.get("obj"));
				if(table != null && table.length() > 0)
				{
					for(int i=0;i<table.length();i++)
					{
						String temp = table.getDataRow(i).getString(0);
						if(!"".equals(StringUtils.checkNullString(temp)))
						{
							userIdList.add(temp);
						}
					}
				}
			}
			catch  (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		return userIdList;
	}
	
	public boolean synchRoleToPasm()
	{
		boolean result = false;
		try
		{
			List<RoleInfo> roleList = roleManagerDao.find("from RoleInfo order by roledns", null);
			result = synchInfoPasm.batchSynchAddRoleInfo(roleList);
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	public void setRoleOrgDao(IDao<RoleOrg> roleOrgDao) {
		this.roleOrgDao = roleOrgDao;
	}
	public void setRoleManagerDao(IDao<RoleInfo> roleManagerDao) {
		this.roleManagerDao = roleManagerDao;
	}
	public void setRoleMenuDao(IDao<RoleMenu> roleMenuDao) {
		this.roleMenuDao = roleMenuDao;
	}
	public void setRoleResOpDao(IDao<RoleResOp> roleResOpDao) {
		this.roleResOpDao = roleResOpDao;
	}
	public void setResourceDao(IDao<Resource> resourceDao) {
		this.resourceDao = resourceDao;
	}
	public void setUserManagerDao(IDao<UserInfo> userManagerDao) {
		this.userManagerDao = userManagerDao;
	}
	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}
	public void setDnsManagerService(DnsManagerService dnsManagerService) {
		this.dnsManagerService = dnsManagerService;
	}
	public void setOperateManagerService(OperateManagerService operateManagerService) {
		this.operateManagerService = operateManagerService;
	}
	public void setOpDataPrivilegeDao(IDao<OpDataPrivilege> opDataPrivilegeDao) {
		this.opDataPrivilegeDao = opDataPrivilegeDao;
	}
	public void setSqlDataPrivilegeDao(IDao<SqlDataPrivilege> sqlDataPrivilegeDao) {
		this.sqlDataPrivilegeDao = sqlDataPrivilegeDao;
	}
	public void setSynchDataDown(SynchDataService synchDataDown) {
		this.synchDataDown = synchDataDown;
	}
	public void setSynchInfoPasm(EomsInfoDownPasm synchInfoPasm) {
		this.synchInfoPasm = synchInfoPasm;
	}
}

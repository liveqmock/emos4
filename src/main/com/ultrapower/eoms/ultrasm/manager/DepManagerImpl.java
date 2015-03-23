package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.support.PageLimit;
import com.ultrapower.eoms.common.core.util.ArrayTransferUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.external.service.EomsInfoDownPasm;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.RoleOrg;
import com.ultrapower.eoms.ultrasm.model.UserDep;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.DnsManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.service.SynchDataService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;


@Transactional
public class DepManagerImpl implements DepManagerService
{
	private IDao<DepInfo> depManagerDao;
	private IDao<UserInfo> userManagerDao;
	private IDao<UserDep> userDepDao;
	private IDao<RoleOrg> roleOrgDao;
	private IDao<Map> depJdbcDao;
	private UserManagerService userManagerService;
	private RoleManagerService roleManagerService;
	private DnsManagerService dnsManagerService;
	private SynchDataService synchDataDown;
	private EomsInfoDownPasm synchInfoPasm;
	
	public DepInfo getDepByFullname(String depFullName)
	{
		DepInfo dep = null;
		if("".equals(StringUtils.checkNullString(depFullName)))
			return dep;
		try
		{
			List<DepInfo> depList = depManagerDao.find(" from DepInfo where depfullname = ? ", new Object[] {depFullName});
			if(depList != null && depList.size() > 0)
				dep = depList.get(0);
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return dep;
	}
	
	public String getUserNamesByDepFullname(String depFullname, boolean isAll)
	{
		String userNames = "";
		if("".equals(StringUtils.checkNullString(depFullname)))
		{
			return userNames;
		}
		try
		{
			String sql = "select u.pid userid, u.fullname username " +
					     "  from bs_t_sm_user u, bs_t_sm_dep d, bs_t_sm_userdep ud" +
					     " where u.pid = ud.userid and d.pid = ud.depid";
			DataTable table = null;
			if(isAll)
			{
				DepInfo dep = this.getDepByFullname(depFullname);
				String depdns = "";
				if(dep != null)
					depdns = StringUtils.checkNullString(dep.getDepdns());
				sql += " and depdns like '" + depdns + "%'";
				QueryAdapter queryAdapter = new QueryAdapter();
				table = queryAdapter.executeQuery(sql, null);
			}
			else
			{
				sql += " and depfullname = ?";
				QueryAdapter queryAdapter = new QueryAdapter();
				table = queryAdapter.executeQuery(sql, new Object[] {depFullname});
			}
			int tableLen = 0;
			if(table != null)
				tableLen = table.length();
			String ids = "";
			String names = "";
			DataRow row;
			for(int i=0;i<tableLen;i++)
			{
				row = table.getDataRow(i);
				if(!"".equals(ids))
				{
					ids += ",";
					names += ",";
				}
				ids += StringUtils.checkNullString(row.getString("userid"));
				names += StringUtils.checkNullString(row.getString("username"));
			}
			if(!"".equals(ids))
				userNames = ids + ";" + names;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return userNames;
	}
	
	public String getDepNameByID(String depId)
	{
		String depName = "";
		if(depId == null)
		{
			return depName;
		}
		List<DepInfo> depList = depManagerDao.find(" from DepInfo where pid = ?", new Object[] {depId});
		if(depList != null && depList.size()>0)
		{
			depName = depList.get(0).getDepname();
		}
		return depName;
	}
	
	public String getDepNamesByIDs(List depIdList)
	{
		String depStr = "";
		depIdList = UltraSmUtil.removeNullData(depIdList);
		if(depIdList != null && depIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(depIdList);
				QueryAdapter queryAdapter = new QueryAdapter();
				DataTable table = queryAdapter.executeQuery("select pid, depname from bs_t_sm_dep where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
				if(table != null && table.length() > 0)
				{
					DataRow row = null;
					String depid = "";
					String depname = "";
					for(int i=0;i<table.length();i++)
					{
						row = table.getDataRow(i);
						if(!"".equals(depid))
						{
							depid += ",";
							depname += ",";
						}
						depid += row.getString("pid");
						depname += row.getString("depname");
					}
					if(!"".equals(depid))
					{
						depStr = depid + ";" + depname;
					}
				}
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		return depStr;
	}
	
	public String getDepNamesById(String depIds)
	{
		String depInfos = "";
		depIds = StringUtils.checkNullString(depIds).replaceAll(" ", "");
		if("".equals(depIds))
		{
			return depInfos;
		}
		try
		{
			Map map = UltraSmUtil.getSqlParameter(depIds);
			List<DepInfo> depList = depManagerDao.find("from DepInfo where pid in ("+map.get("?")+")", (Object[]) map.get("obj"));
			int depLen = 0;
			if(depList != null)
			{
				depLen = depList.size();
			}
			DepInfo dep;
			depIds = "";
			String depNames = "";
			for(int i=0 ; i<depLen ; i++)
			{
				dep = depList.get(i);
				if(dep == null)
					continue;
				if(!"".equals(depIds))
				{
					depIds += ",";
					depNames += ",";
				}
				depIds += dep.getPid();
				depNames += dep.getDepname();
			}
			if(depLen > 0)
				depInfos = depIds + ";" + depNames;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return depInfos;
	}
	
	public List getUserNameByDepID(String depId)
	{
		return this.getUserFieldByDepID(depId,"fullname");
	}
	
	public DepInfo getTopDepByDepid(String depId)
	{
		DepInfo topDep = null;
		if("".equals(StringUtils.checkNullString(depId)))
		{
			return topDep;
		}
		try
		{
			DepInfo dep = this.getDepByID(depId);
			String depDns = "";
			if(dep != null)
			{
				depDns = StringUtils.checkNullString(dep.getDepdns());
			}
			String topdns = depDns.split("\\.")[0];
			List<DepInfo> depList = depManagerDao.find(" from DepInfo where depdns = ? ", new Object[] {topdns});
			if(depList != null && depList.size() > 0)
			{
				topDep = depList.get(0);
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return topDep;
	}
	
	public DepInfo getCompanyByDepid(String depId)
	{
		if("".equals(StringUtils.checkNullString(depId)))
			return null;
		String id = depId;
		DepInfo dep = null;
		while(true)
		{
			dep = this.getDepByID(id);
			if(dep == null)
				break;
			String depType = dep.getDeptype();
			if("1".equals(depType) || "2".equals(depType))
				break;
			id = StringUtils.checkNullString(dep.getParentid());
		}
		return dep;
	}
	
	public String getAllParentIdById(String depId)
	{
		String ids = "";
		if("".equals(StringUtils.checkNullString(depId)))
		{
			return ids;
		}
		DepInfo dep = this.getDepByID(depId);
		if(dep != null)
		{
			List depIdList = dnsManagerService.getParentIdListByDns("bs_t_sm_dep", "depdns", dep.getDepdns());
			ids = UltraSmUtil.listThanString(depIdList);
		}
		return "".equals(ids)?"0":"0,"+ids;
	}
	
	public List<String> getAllParentIdListByUserId(String userID)
	{
		List<String> depIDList = getDepIdByUser(userID, null);
		int depIDListLength = depIDList.size();
		Map<String,String> aLLDepIdMap = new HashMap<String,String>();
		for(int index = 0;index < depIDListLength; index++)
		{
			String depIDSub = depIDList.get(index);
			String depIDTemp = getAllParentIdById(depIDSub);
			String[] depIDArray = depIDTemp.split(",");
			aLLDepIdMap.put(depIDSub, depIDSub);
			for(String depID:depIDArray)
			{
				aLLDepIdMap.put(depID, depID);
			}
		}
		List<String> returnList = new ArrayList<String>();
		for(String key:aLLDepIdMap.keySet())
		{
			returnList.add(key);
		}
		
		return returnList;
	}

	public boolean saveDepInfo(DepInfo dep, List userIdList, List roleIdList, List delUserIdList, List delRoleIdList)
	{
		if(dep == null)
		{  
			return false;
		}
		if("".equals(StringUtils.checkNullString(dep.getPid())))
		{
			return this.addDepInfo(dep, userIdList, roleIdList);
		}
		else
		{
			return this.updateDepInfo(dep, userIdList, roleIdList, delUserIdList, delRoleIdList);
		}
	}
	
	private boolean addDepInfo(DepInfo dep, List userIdList, List roleIdList)
	{
		String depId = this.addDepInfo(dep);
		if(!"".equals(StringUtils.checkNullString(depId)))
		{
			return this.addDepRelation(depId, userIdList, roleIdList);
		}
		return false;
	}

	public String addDepInfo(DepInfo dep)
	{
		return this.addDepInfo(dep, "");
	}
	
	public String addDepInfo(DepInfo dep, String systemMark)
	{
		if(dep == null)
		{
			return "";
		}
		String depId = "";
		try
		{
			dep.setDepdn(dnsManagerService.getCurrentDn("bs_t_sm_dep", "depdn", dep.getParentid()));
			dep.setDepdns(dnsManagerService.getCurrentDns("bs_t_sm_dep", "depdns", "depdn", dep.getParentid()));
			if("".equals(StringUtils.checkNullString(dep.getPid()))) {
				dep.setPid(UUIDGenerator.getUUIDoffSpace());
			}
			depManagerDao.save(dep);
			int synchResult = synchDataDown.synchDepAdd(dep, systemMark);
			if(synchResult == 0)
			{
				depId = dep.getPid();
			}
			else
			{
				RecordLog.printLog("add depinfo success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				depManagerDao.remove(dep);
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return depId;
	}

	public boolean addDepInfo(List<DepInfo> depList)
	{
		if(depList != null && depList.size() > 0)
		{
			DepInfo dep;
			for(int i=0;i<depList.size();i++)
			{
				dep = depList.get(i);
				if("".equals(StringUtils.checkNullString(this.addDepInfo(dep))))
				{
					return false;
				}
			}
		}
		return true;
	}

	private boolean addDepRelation(String depId, List userIdList, List roleIdList)
	{
		if(!"".equals(StringUtils.checkNullString(depId)))
		{
			return this.addDepUser(depId, userIdList) && this.addDepRole(depId, roleIdList);  
		}
		return false;
	}

	public boolean addDepRole(String depId, List roleIdList)
	{
		return roleManagerService.addDepRole(depId, roleIdList);
	}

	public boolean addDepUser(String depId, List userIdList)
	{
		return this.addDepUser(depId, userIdList, "");
	}
	
	public boolean addDepUser(String depId, List userIdList, String systemMark)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(depId)))
		{
			return result;
		}
		userIdList = UltraSmUtil.removeNullData(userIdList);
		if(userIdList != null && userIdList.size() > 0)
		{
			UserDep ud = null;
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
			List oldUserIdList = this.getUserFieldByDepID(depId, "pid");
			List<UserDep> udList = new ArrayList<UserDep> ();
			List<String> uidList = new ArrayList<String> ();
			for(int i=0;i<userIdList.size();i++)
			{
				String userId = (String) userIdList.get(i);
				if(oldUserIdList.indexOf(userId) >= 0)
					continue;
				ud = new UserDep();
				ud.setUserid(userId);
				ud.setDepid(depId);
				ud.setLoginname(userManagerService.getLoginNameByPid((String) userIdList.get(i)));
				ud.setRelatetype("1");//关系类型 1.所属组 2.兼职部门
				ud.setCreater(userpid);
				ud.setCreatetime(currentTime);
				ud.setLastmodifier(userpid);
				ud.setLastmodifytime(currentTime);
				udList.add(ud);
				uidList.add(userId);
			}
			try
			{
				if(udList != null && udList.size() > 0)
				{
					for(int i=0;i<udList.size();i++)
					{
						userDepDao.save(udList.get(i));
					}
					result = this.updateUserGroup(userIdList, systemMark);//更新人员所属组信息
					List<String> didList = new ArrayList<String> ();
					didList.add(depId);
					int synchResult = synchDataDown.synchDepUserAdd(didList, uidList, systemMark);
					if(synchResult > 0)
					{
						RecordLog.printLog("add group user success, synch failure!",RecordLog.LOG_LEVEL_ERROR);
					}
				}
				else
				{
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
	
	//需要考虑的比较多：子集部门
	public boolean deleteDepByID(String depId)
	{
		return this.deleteDepByID(depId, "");
	}
	
	public boolean deleteDepByID(String depId, String systemMark)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(depId)))
		{
			return result;
		}
		try
		{
			List<String> depIdList = dnsManagerService.getSubIdList("bs_t_sm_dep", depId);//获取子集部门IDList
			if(depIdList != null && depIdList.size() > 0)
			{
				DepInfo dep = this.getDepByID(depId);
				Map map = UltraSmUtil.getSqlParameter(depIdList);
				//清除部门和人员的关系
				userDepDao.executeUpdate("delete UserDep where depId in (" + map.get("?") + ")", (Object[]) map.get("obj"));
				//清除部门和角色的关系
				roleOrgDao.executeUpdate("delete RoleOrg where orgId in (" + map.get("?") + ") and orgtype = 2", (Object[]) map.get("obj"));
				//删除部门记录
				depManagerDao.executeUpdate(" delete DepInfo where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
				userManagerDao.executeUpdate(" update UserInfo set depid = '', depname = '' where depid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
				//更新人员的所属组和兼职部门信息
				String sql = " select u.pid from bs_t_sm_user u, bs_t_sm_userdep ud where u.pid = ud.userid and u.status = 1 and ud.depid in (" + map.get("?") + ")";
				List userIdList = new ArrayList();
				QueryAdapter queryAdapter = new QueryAdapter();
				DataTable table = queryAdapter.executeQuery(sql, (Object[])map.get("obj"));
				String temp;
				if(table != null && table.length() > 0)
				{
					for(int i=0;i<table.length();i++)
					{
						temp = table.getDataRow(i).getString(0);
						if(temp != null)
						{
							userIdList.add(temp);
						}
					}
				}
				if(this.updateUserGroup(userIdList, systemMark) && this.updateUserPtdep(userIdList, systemMark))//更新人员所属组信息 兼职部门信息
				{
					result = true;
				}
				int synchResult = synchDataDown.synchDepDel(depId, systemMark);
				if(synchResult > 0)
				{
					RecordLog.printLog("delete group success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				}
				else
				{
					int udResult = synchDataDown.synchDepUserDel(depIdList, "2", systemMark); //删除人员部门关系
					if(udResult > 0)
					{
						RecordLog.printLog("delete group success, synch delete group success, but delete the groupuser failure!", RecordLog.LOG_LEVEL_ERROR);
					}
					int urResult = synchDataDown.synchRoleOrgDel(depIdList, "2", systemMark); //删除人员角色关系
					if(urResult > 0)
					{
						RecordLog.printLog("delete group success, synch delete group success, but delete the rolegroup failure!", RecordLog.LOG_LEVEL_ERROR);
					}
				}
			}
			else
			{
				result = true;
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean deleteDepByID(List depIdList)
	{
		depIdList = UltraSmUtil.removeNullData(depIdList);
		if(depIdList != null && depIdList.size() > 0)
		{
			for(int i=0;i<depIdList.size();i++)
			{
				if(!this.deleteDepByID((String) depIdList.get(i)))
					return false;
			}
		}
		return true;
	}

	public boolean deleteDepRole(String depId, List roleIdList)
	{
		return this.deleteDepRole(depId, roleIdList, "");
	}
	
	public boolean deleteDepRole(String depId, List roleIdList, String systemMark)
	{
		return roleManagerService.deleteDepRole(depId, roleIdList, systemMark);
	}

	public boolean deleteDepUser(String depId, List userIdList)
	{
		return this.deleteDepUser(depId, userIdList, "");
	}
	
	public boolean deleteDepUser(String depId, List userIdList, String systemMark)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(depId)))
		{
			return result;
		}
		userIdList = UltraSmUtil.removeNullData(userIdList);
		if(userIdList != null && userIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(userIdList);
				Object[] obj = ArrayTransferUtils.copyArraySimple(new Object[] {depId} , (Object[]) map.get("obj"));
//				List<UserDep> udList = userDepDao.find("from UserDep where depId = ? and userId in ( " + map.get("?") + " )", obj);
				userDepDao.executeUpdate("delete UserDep where depId = ? and userId in ( " + map.get("?") + " )", obj);
				this.updateUserGroup(userIdList, systemMark);//更新人员所属组信息
				this.updateUserPtdep(userIdList, systemMark);//更新人员兼职部门信息
				result = true;
				List<String> depIdList = new ArrayList<String> ();
				depIdList.add(depId);
				int synchResult = synchDataDown.synchDepUserDel(depIdList, userIdList, systemMark);
				if(synchResult > 0)
				{
					RecordLog.printLog("del group user success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
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

	public DepInfo getDepByID(String depId)
	{
		if("".equals(StringUtils.checkNullString(depId)))
		{
			return null;
		}
		return depManagerDao.get(depId);
	}

	public List<DepInfo> getDepByID(List depIdList)
	{
		List<DepInfo> depList = null;
		depIdList = UltraSmUtil.removeNullData(depIdList);
		if(depIdList != null && depIdList.size() > 0)
		{
			Map map = UltraSmUtil.getSqlParameter(depIdList);
			depList = depManagerDao.find(" from DepInfo where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
		}
		return depList;
	}
	
	public List<DepInfo> getDepByParentID(String depId)
	{
		if("".equals(StringUtils.checkNullString(depId)))
		{
			return null;
		}
		List<DepInfo> depList = depManagerDao.find(" from DepInfo where parentid = ?", new Object[] {depId});
		return depList;
	}

	public List getDepIdByUser(String userId, String type)
	{
		if("".equals(StringUtils.checkNullString(userId)))
			return null;
		List depIdList = new ArrayList();
		String sql = " select depid from bs_t_sm_userdep where userid = ? ";
		type = StringUtils.checkNullString(type);
		if(!"".equals(type))
			sql += " and relatetype = '" + type + "'";
		try
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql, new Object[] {userId});
			String temp;
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					temp = table.getDataRow(i).getString(0);
					if(temp != null)
					{
						depIdList.add(temp);
					}
				}
			}
		}
		catch  (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return depIdList;
	}
	
	public List<RoleInfo> getRoleByDepID(String depId)
	{
		if("".equals(StringUtils.checkNullString(depId)))
		{
			return null;
		}
		return roleManagerService.getRoleByDepID(depId);
	}

	public List<String> getUserIdByDepID(String depId)
	{
		if("".equals(StringUtils.checkNullString(depId)))
		{
			return null;
		}
		return this.getUserFieldByDepID(depId, "pid");
	}
	
	public List<UserInfo> getUserByDepID(String depId, boolean isPage)
	{
		if("".equals(StringUtils.checkNullString(depId)))
		{
			return null;
		}
		List<UserInfo> userList = null;
		List userIdList = this.getUserFieldByDepID(depId, "pid");
		if(userIdList != null && userIdList.size() > 0)
		{
			Map map = UltraSmUtil.getSqlParameter(userIdList);
			if(isPage)
			{
				PageLimit pageLimit = PageLimit.getInstance();
				userList = userManagerDao.pagedQuery(" from UserInfo where pid in (" + map.get("?") + ")", pageLimit, (Object[]) map.get("obj"));
			}
			else
				userList = userManagerDao.find(" from UserInfo where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
		}
		return userList;
	}
	
	public List<UserInfo> getSubUserByDep(String depids, String depid, UserInfo userInfo)
	{
		List<UserInfo> userList = null;
		try
		{
			if(!"".equals(StringUtils.checkNullString(depids)))
			{
				List<DepInfo> depList = this.getDepByID(UltraSmUtil.arrayToList(depids.split(",")));
				List<String> dnsList = null;
				int depLen = 0;
				if(depList != null)
				{
					depLen = depList.size();
					dnsList = new ArrayList<String> ();
				}
				for(int i=0 ; i<depLen ; i++)
					dnsList.add(depList.get(i).getDepdns());

				//拼条件
				StringBuffer con = new StringBuffer();
				con.append("   and d.status = 1");
				con.append("   and u.status = 1");
				if(userInfo != null)
				{
					String ln = StringUtils.checkNullString(userInfo.getLoginname());
					if(!"".equals(ln))
						con.append(" and u.loginname like '%" + ln + "%'");
					String fn = StringUtils.checkNullString(userInfo.getFullname());
					if(!"".equals(fn))
						con.append(" and u.fullname like '%" + fn + "%'");
					String dn = StringUtils.checkNullString(userInfo.getDepname());
					if(!"".equals(dn))
						con.append(" and u.depname like '%" + dn + "%'");
				}
				con.append("   and d.pid <> '" + depid + "'");
				con.append("   and (1=2");
				int dnsLen = 0;
				if(dnsList != null)
					dnsLen = dnsList.size();
				String dns;
				for(int i=0 ; i<dnsLen ; i++)
				{
					dns = dnsList.get(i);
					con.append(" or d.depdns like '" + dns + "%'");
				}
				con.append(")");
				//拼SQL
				StringBuffer sql = new StringBuffer();
				sql.append("select /*COUNT*/ * /*COUNT*/ from (");
				sql.append("select distinct u.pid userid, u.loginname loginname, u.fullname username, u.depname depname");
				sql.append("  from bs_t_sm_user u, bs_t_sm_userdep ud, bs_t_sm_dep d");
				sql.append(" where u.pid = ud.userid");
				sql.append("   and d.pid = ud.depid");
				sql.append(con.toString());
				sql.append(" union all ");
				sql.append("select u.pid userid, u.loginname loginname, u.fullname username, u.depname depname");
				sql.append("  from bs_t_sm_user u, bs_t_sm_dep d");
				sql.append(" where u.depid = d.pid");
				sql.append("   and (groupid is null or groupid = '')");
				sql.append(con.toString());
				sql.append(") order by username");
				QueryAdapter queryAdapter = new QueryAdapter();
				PageLimit pageLimit = PageLimit.getInstance();
				DataTable table = queryAdapter.executeQuery(sql.toString(), null, pageLimit.getPageSize(), pageLimit.getCURRENT_ROWS_SIZE(), 2);
				pageLimit.getLimit().setRowAttributes(queryAdapter.getQueryResultSetCount(), pageLimit.getCURRENT_ROWS_SIZE());
				int tableLen = 0;
				if(table != null)
				{
					tableLen = table.length();
					userList = new ArrayList<UserInfo> ();
				}
				UserInfo user;
				DataRow row;
				for(int i=0 ; i<tableLen ; i++)
				{
					row = table.getDataRow(i);
					user = new UserInfo();
					user.setPid(row.getString("userid"));
					user.setLoginname(row.getString("loginname"));
					user.setFullname(row.getString("username"));
					user.setDepname(row.getString("depname"));
					userList.add(user);
				}
			}
			else
			{
				StringBuffer sql = new StringBuffer();
				sql.append("select /*COUNT*/ * /*COUNT*/ from (");
				sql.append("select distinct u.pid userid, u.loginname loginname, u.fullname username, u.depname depname");
				sql.append("  from bs_t_sm_user u, bs_t_sm_userdep ud, bs_t_sm_dep d");
				sql.append(" where u.pid = ud.userid");
				sql.append("   and d.pid = ud.depid");
				sql.append("   and d.status = 1");
				sql.append("   and u.status = 1");
				if(userInfo != null)
				{
					String ln = StringUtils.checkNullString(userInfo.getLoginname());
					if(!"".equals(ln))
						sql.append(" and u.loginname like '%" + ln + "%'");
					String fn = StringUtils.checkNullString(userInfo.getFullname());
					if(!"".equals(fn))
						sql.append(" and u.fullname like '%" + fn + "%'");
					String dn = StringUtils.checkNullString(userInfo.getDepname());
					if(!"".equals(dn))
						sql.append(" and u.depname like '%" + dn + "%'");
				}
				sql.append("   and d.pid <> '" + depid + "'");
				sql.append(" order by u.fullname");
				sql.append(")");
				QueryAdapter queryAdapter = new QueryAdapter();
				PageLimit pageLimit = PageLimit.getInstance();
				DataTable table = queryAdapter.executeQuery(sql.toString(), null, pageLimit.getPageSize(), pageLimit.getCURRENT_ROWS_SIZE(), 2);
				pageLimit.getLimit().setRowAttributes(queryAdapter.getQueryResultSetCount(), pageLimit.getCURRENT_ROWS_SIZE());
				int tableLen = 0;
				if(table != null)
				{
					tableLen = table.length();
					userList = new ArrayList<UserInfo> ();
				}
				UserInfo user;
				DataRow row;
				for(int i=0 ; i<tableLen ; i++)
				{
					row = table.getDataRow(i);
					user = new UserInfo();
					user.setPid(row.getString("userid"));
					user.setLoginname(row.getString("loginname"));
					user.setFullname(row.getString("username"));
					user.setDepname(row.getString("depname"));
					userList.add(user);
				}
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return userList;
	}
	
	public boolean setDepDisable(List depIdList)
	{
		boolean result = false;
		depIdList = UltraSmUtil.removeNullData(depIdList);
		if(depIdList != null && depIdList.size() > 0)
		{
			try
			{
				List<DepInfo> depList = this.getDepByID(depIdList);
				int depLen = 0;
				if(depList != null)
					depLen = depList.size();
				DepInfo dep;
				int i=0;
				for(i=0;i<depLen;i++)
				{
					dep = depList.get(i);
					if(dep != null)
					{
						dep.setStatus((long) 0);
						depManagerDao.saveOrUpdate(dep);
						if(synchDataDown.synchDepEdit(dep, "") > 0)
						{
							RecordLog.printLog("dep:" + dep.getPid() + " disabled success, synch failure!", RecordLog.LOG_LEVEL_INFO);
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
		}
		else
		{
			result = true;
		}
		return result;
	}

	public boolean setDepEnabled(List depIdList)
	{
		boolean result = false;
		depIdList = UltraSmUtil.removeNullData(depIdList);
		if(depIdList != null && depIdList.size() > 0)
		{
			try
			{
				List<DepInfo> depList = this.getDepByID(depIdList);
				int depLen = 0;
				if(depList != null)
					depLen = depList.size();
				DepInfo dep;
				int i=0;
				List<String> topIdList;
				for(i=0;i<depLen;i++)
				{
					dep = depList.get(i);
					if(dep != null)
					{
						dep.setStatus((long) 1);
						depManagerDao.saveOrUpdate(dep);
						if(synchDataDown.synchDepEdit(dep, "") > 0)
						{
							RecordLog.printLog("dep:" + dep.getPid() + " enabled success, synch failure!", RecordLog.LOG_LEVEL_INFO);
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
		}
		else
		{
			result = true;
		}
		return result;
	}

	private boolean updateDepInfo(DepInfo dep, List userIdList, List roleIdList, List delUserIdList, List delRoleIdList)
	{
		String depId = this.updateDepInfo(dep);
		if(!"".equals(StringUtils.checkNullString(depId)))
		{
			return this.updateDepRelation(depId, userIdList, roleIdList, delUserIdList, delRoleIdList);
		}
		return false;
	}

	public String updateDepInfo(DepInfo dep)
	{
		return this.updateDepInfo(dep, "");
	}
	
	public String updateDepInfo(DepInfo dep, String systemMark)
	{
		if(dep == null)
		{
			return null;
		}
		String depId = null;
		try
		{
			String oldDepname="";
			String oldParentid = "";
			String oldDepdns = "";
			String oldDepfullname = "";
			List mapList = depJdbcDao.find(" select depname,parentid,depdns,depfullname from bs_t_sm_dep where pid = ?", new Object[] {dep.getPid()});
			if(mapList != null && mapList.size() > 0)
			{
				Map depMap = (Map)mapList.get(0);
				oldDepname = StringUtils.checkNullString(depMap.get("depname"));
				oldParentid = StringUtils.checkNullString(depMap.get("parentid"));
				oldDepdns = StringUtils.checkNullString(depMap.get("depdns"));
				oldDepfullname = StringUtils.checkNullString(depMap.get("depfullname"));
			}
			boolean flag = true; //标记是否修改了子级dns，修改了dns就不用修改子级全名了
			if(!dep.getParentid().equals(oldParentid))//当修改了上级部门时
			{
				//修改该部门DNS以及子集和子集的子集DNS信息
				String newDns = dnsManagerService.getCurrentDns("bs_t_sm_dep", "depdns", "depdn", dep.getParentid());
				String newDn = dnsManagerService.getCurrentDn("bs_t_sm_dep", "depdn", dep.getParentid());
				dnsManagerService.updateSubDnsAndFullName("bs_t_sm_dep", "depdns", "depfullname", oldDepdns, oldDepfullname, newDns, dep.getDepfullname());
				flag = false;
				dep.setDepdns(newDns);
				dep.setDepdn(newDn);
			}
			if(!dep.getDepname().equals(oldDepname))//当修改了部门名称时
			{
				//将名称更新到人员信息表中
				String depid = StringUtils.checkNullString(dep.getPid());
				String sql = "";
				//修改人员表行政部门
				userManagerDao.executeUpdate(" update UserInfo set depname = ? where depid = ? ", new Object[] {dep.getDepname(), depid});
				//修改人员表所属组名称
				userManagerDao.executeUpdate(" update UserInfo set groupname = ? where groupid = ? ", new Object[] {dep.getDepname(), depid});
				sql = " update UserInfo set groupname = '"+dep.getDepname()+"' || substr(groupname, " + (oldDepname.length()+1) + ") where groupid like '"+depid+",%' ";
				userManagerDao.executeUpdate(sql, null);
				sql = " update UserInfo set groupname = replace(groupname, ?, ?) where groupid like '%,"+depid+",%' ";
				userManagerDao.executeUpdate(sql, new Object[] {oldDepname, dep.getDepname()});
				sql = " update UserInfo set groupname = substr(groupname, 1, length(groupname) - " + oldDepname.length() + ") || '" + dep.getDepname() + "' where groupid like '%,"+depid+"' ";
				userManagerDao.executeUpdate(sql, null);
				//修改人员表兼职部门名称
				userManagerDao.executeUpdate(" update UserInfo set ptdepname = ? where ptdepid = ? ", new Object[] {dep.getDepname(), depid});
				sql = " update UserInfo set ptdepname = '"+dep.getDepname()+"' || substr(ptdepname, " + (oldDepname.length()+1) + ") where ptdepid like '"+depid+",%' ";
				userManagerDao.executeUpdate(sql, null);
				sql = " update UserInfo set ptdepname = replace(ptdepname, ?, ?) where ptdepid like '%,"+depid+",%' ";
				userManagerDao.executeUpdate(sql, new Object[] {oldDepname, dep.getDepname()});
				sql = " update UserInfo set ptdepname = substr(ptdepname, 1, length(ptdepname) - " + oldDepname.length() + ") || '" + dep.getDepname() + "' where ptdepid like '%,"+depid+"' ";
				userManagerDao.executeUpdate(sql, null);
				//修改部门表该部门下所有子集部门的全名信息
				if(flag)
				{
					dnsManagerService.updateSubFullName("bs_t_sm_dep", "depdns", "depfullname", oldDepdns, oldDepfullname, dep.getDepfullname());
				}
			}
			depManagerDao.saveOrUpdate(dep);
			depId = dep.getPid();
			if(!"".equals(depId))
			{
				int synchResult = synchDataDown.synchDepEdit(dep, systemMark);
				if(synchResult > 0)
				{
					RecordLog.printLog("update depinfo success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				}
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return depId;
	}

	private boolean updateDepRelation(String depId, List userIdList, List roleIdList, List delUserIdList, List delRoleIdList)
	{
		if(!"".equals(StringUtils.checkNullString(depId)))
		{
			return this.addDepRelation(depId, userIdList, roleIdList) && this.deleteDepUser(depId, delUserIdList) && this.deleteDepRole(depId, delRoleIdList);
		}
		return false;
	}

	private List getUserFieldByDepID(String depId,String fieldName)
	{
		if(depId == null)
		{
			return null;
		}
		List list = new ArrayList();
		String sql = " select u."+fieldName+" from bs_t_sm_user u, bs_t_sm_userdep ud where u.pid = ud.userid and u.status = 1 and ud.depid = ?";
		try
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql, new Object[] {depId});
			String temp;
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					temp = table.getDataRow(i).getString(0);
					if(temp != null)
					{
						list.add(temp);
					}
				}
			}
		}
		catch  (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 更新人员所属组信息
	 * @param userIdList	人员IDList
	 * @param systemMark	系统来源标识
	 * @return
	 */
	private boolean updateUserGroup(List userIdList, String systemMark)
	{
		boolean result = false;
		userIdList = UltraSmUtil.removeNullData(userIdList);
		if(userIdList != null && userIdList.size() > 0)
		{
			try
			{
				List<UserInfo> userList = userManagerService.getUserByID(userIdList);
				String sql_depid = " from UserDep where userid = ? and relatetype <> 2";
				UserInfo user = null;
				List<UserDep> udList = null;
				for(int i=0;i<userList.size();i++)
				{
					String groupIds = "";
					String groupNames = "";
					user = userList.get(i);
					udList = userDepDao.find(sql_depid, new Object[] {user.getPid()});
					for(int j=0;j<udList.size();j++)
					{
						String depId = udList.get(j).getDepid();
						String depName = this.getDepNameByID(depId);
						if(!"".equals(groupIds))
						{
							groupIds += ",";
							groupNames += ",";
						}
						groupIds += depId;
						groupNames += depName;
					}
					user.setGroupid(groupIds);
					user.setGroupname(groupNames);
					userManagerDao.saveOrUpdate(user);
					//userManagerService.updateUserInfo(user);
					int synchResult = synchDataDown.synchUserEdit(user, systemMark);
					if(synchResult > 0)
					{
						RecordLog.printLog("update userinfo success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
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
	
	private boolean updateUserPtdep(List userIdList, String systemMark)
	{
		boolean result = false;
		userIdList = UltraSmUtil.removeNullData(userIdList);
		if(userIdList != null && userIdList.size() > 0)
		{
			try
			{
				List<UserInfo> userList = userManagerService.getUserByID(userIdList);
				String sql_depid = " from UserDep where userid = ? and relatetype = 2";
				UserInfo user = null;
				List<UserDep> udList = null;
				for(int i=0;i<userList.size();i++)
				{
					String ptdepIds = "";
					String ptdepNames = "";
					user = userList.get(i);
					udList = userDepDao.find(sql_depid, new Object[] {user.getPid()});
					for(int j=0;j<udList.size();j++)
					{
						String depId = udList.get(j).getDepid();
						String depName = this.getDepNameByID(depId);
						if(!"".equals(ptdepIds))
						{
							ptdepIds += ",";
							ptdepNames += ",";
						}
						ptdepIds += depId;
						ptdepNames += depName;
					}
					user.setPtdepid(ptdepIds);
					user.setPtdepname(ptdepNames);
					userManagerDao.saveOrUpdate(user);
					//userManagerService.updateUserInfo(user);
					int synchResult = synchDataDown.synchUserEdit(user, systemMark);
					if(synchResult > 0)
					{
						RecordLog.printLog("update userinfo success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
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
	
	public boolean synchDepToPasm()
	{
		boolean result = false;
		try
		{
			List<DepInfo> depList = depManagerDao.find("from DepInfo order by depdns", null);
			result = synchInfoPasm.batchSynchAddDepInfo(depList);
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean synchUserDepToPasm()
	{
		boolean result = false;
		try
		{
			List<UserDep> udList = userDepDao.find("from UserDep ", null);
			result = synchInfoPasm.batchSynchAddGroupUser(udList);
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	public IDao<DepInfo> getDepManagerDao() {
		return depManagerDao;
	}
	public void setDepManagerDao(IDao<DepInfo> depManagerDao) {
		this.depManagerDao = depManagerDao;
	}
	public IDao<UserInfo> getUserManagerDao() {
		return userManagerDao;
	}
	public void setUserManagerDao(IDao<UserInfo> userManagerDao) {
		this.userManagerDao = userManagerDao;
	}
	public IDao<UserDep> getUserDepDao() {
		return userDepDao;
	}
	public void setUserDepDao(IDao<UserDep> userDepDao) {
		this.userDepDao = userDepDao;
	}
	public IDao<RoleOrg> getRoleOrgDao() {
		return roleOrgDao;
	}
	public void setRoleOrgDao(IDao<RoleOrg> roleOrgDao) {
		this.roleOrgDao = roleOrgDao;
	}
	public void setRoleManagerService(RoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}
	public void setDnsManagerService(DnsManagerService dnsManagerService) {
		this.dnsManagerService = dnsManagerService;
	}
	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
	public void setDepJdbcDao(IDao<Map> depJdbcDao) {
		this.depJdbcDao = depJdbcDao;
	}
	public void setSynchDataDown(SynchDataService synchDataDown) {
		this.synchDataDown = synchDataDown;
	}
	public void setSynchInfoPasm(EomsInfoDownPasm synchInfoPasm) {
		this.synchInfoPasm = synchInfoPasm;
	}
}

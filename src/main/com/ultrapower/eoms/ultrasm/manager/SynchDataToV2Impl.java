package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.util.ArrayTransferUtils;
import com.ultrapower.eoms.common.core.util.CryptUtils;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.SynchDataToV2;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;
import com.ultrapower.remedy4j.core.RemedyField;
import com.ultrapower.remedy4j.core.RemedyFieldType;
import com.ultrapower.remedy4j.core.RemedySession;

public class SynchDataToV2Impl implements SynchDataToV2
{
	private static final boolean sameDb = true;
	private static final String remedyUser = UltraSmUtil.MANAGER;
	private static final String remedyPwd = PropertiesUtils.getProperty("eoms.remedy.demopwd");
	
	private UserManagerService userManagerService;
	private DepManagerService depManagerService;
	
	public int synchUserAdd(UserInfo user)
	{
		int result = 9;
		if(sameDb) //相同数据库
		{
			RemedySession remedySession = new RemedySession(remedyUser, remedyPwd);//登录remedySession
			if(remedySession != null)
			{
				String c1 = "";
				List<RemedyField> remedyFieldList = this.getUserRemedyField(user);//获取一条remedy字段及信息集合
				if(remedyFieldList != null && remedyFieldList.size() > 0)
				{
					c1 = remedySession.addEntry("UltraProcess:SysUser", remedyFieldList);//添加用户实体
				}
				if(!"".equals(StringUtils.checkNullString(c1)))
				{
					remedyFieldList.add(new RemedyField("630000029", "", RemedyFieldType.INTEGER, NumberUtils.formatToInt(c1)+""));//int 用户ID
					if(!remedySession.updateEntry("UltraProcess:SysUser", c1, remedyFieldList))// 更新int 用户ID
						RecordLog.printLog("add user, login remedy success, but update the int id failure!", RecordLog.LOG_LEVEL_ERROR);
					result = 0;//添加用户成功
				}
			}
			else
			{
				result = 8;//登录remedy失败
				RecordLog.printLog("add user, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			remedySession.logout();
		}
		else
		{
			//后续实现
		}
		return result;
	}
	
	public int synchUserDel(String userId)
	{
		int result = 9;
		if(sameDb)
		{
			String entryId = StringUtils.checkNullString(this.getEntryId("UltraProcess:SysUser", userId));//获取用户对应的主键C1
			if("".equals(entryId))
			{
				result = 2;//此人不存在
				RecordLog.printLog("the user:" + userId + " is not exist, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			else
			{
				RemedySession remedySession = new RemedySession(remedyUser, remedyPwd);//登录remedySession
				if(remedySession != null)
				{
					if(remedySession.deleteEntry("UltraProcess:SysUser", entryId))
					{
						result = 0;//删除用户成功
					}
				}
				else
				{
					result = 8;//登录remedy失败
					RecordLog.printLog("del user, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
				}
				remedySession.logout();
			}
		}
		else
		{
			
		}
		return result;
	}
	
	public int synchUserEdit(UserInfo user)
	{
		int result = 9;
		if(sameDb)
		{
			List<RemedyField> remedyFieldList = this.getUserRemedyField(user);//获取一条remedy字段及信息集合
			if(remedyFieldList != null && remedyFieldList.size() > 0)
			{
				String userId = user.getPid();
				String entryId = StringUtils.checkNullString(this.getEntryId("UltraProcess:SysUser", userId));//获取用户对应的主键C1
				if("".equals(entryId))
				{
					result = 2;//此人不存在
					RecordLog.printLog("the user:" + userId + " is not exist, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
				}
				else
				{
					RemedySession remedySession = new RemedySession(remedyUser, remedyPwd);
					if(remedySession != null)
					{
						if(remedySession.updateEntry("UltraProcess:SysUser", entryId, remedyFieldList))
						{
							result = 0;//修改用户成功
						}
					}
					else
					{
						result = 8;//登录remedy失败
						RecordLog.printLog("edit user, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
					}
					remedySession.logout();
				}
			}
		}
		else
		{
			
		}
		return result;
	}
	
	public int synchUserPwdEdit(String userId, String newPwd)
	{
		int result = 9;
		if(sameDb)
		{
			String entryId = StringUtils.checkNullString(this.getEntryId("UltraProcess:SysUser", userId));//获取用户对应的主键C1
			if("".equals(entryId))
			{
				result = 2;//此人不存在
				RecordLog.printLog("the user:" + userId + " is not exist, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			else
			{
				UserInfo user = userManagerService.getUserByID(userId);
				if(user != null)
				{
					RemedySession remedySession = new RemedySession(remedyUser, remedyPwd);
					if(remedySession != null)
					{
						List<RemedyField> remedyFieldList = new ArrayList<RemedyField> ();
						remedyFieldList.add(new RemedyField("630000002", "", RemedyFieldType.CHAR, newPwd));
						if(remedySession.updateEntry("UltraProcess:SysUser", entryId, remedyFieldList))
						{
							result = 0;//修改密码成功
						}
					}
					else
					{
						result = 8;//登录remedy失败
						RecordLog.printLog("edit user password, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
					}
					remedySession.logout();
				}
				else
				{
					result = 2;//此人不存在
					RecordLog.printLog("the object user is null, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
				}
			}
		}
		else
		{
			
		}
		return result;
	}
	
	public int synchDepAdd(DepInfo dep)
	{
		int result = 9;
		if(sameDb)
		{
			RemedySession remedySession = new RemedySession(remedyUser, remedyPwd);//登录remedySession
			if(remedySession != null)
			{
				String c1 = "";
				List<RemedyField> remedyFieldList = this.getDepRemedyField(dep);//获取一条remedy字段及信息集合
				if(remedyFieldList != null && remedyFieldList.size() > 0)
				{
					c1 = remedySession.addEntry("UltraProcess:SysGroup", remedyFieldList);//添加部门实体
				}
				if(!"".equals(StringUtils.checkNullString(c1)))
				{
					remedyFieldList.add(new RemedyField("630000030", "", RemedyFieldType.INTEGER, NumberUtils.formatToInt(c1)+""));//int 用户ID
					if(!remedySession.updateEntry("UltraProcess:SysGroup", c1, remedyFieldList))// 更新int 用户ID
						RecordLog.printLog("add dep, login remedy success, but update the int id failure!", RecordLog.LOG_LEVEL_ERROR);
					result = 0;//添加部门成功
				}
			}
			else
			{
				result = 8;//登录remedy失败
				RecordLog.printLog("add dep, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			remedySession.logout();
		}
		else
		{
			
		}
		return result;
	}
	
	public int synchDepDel(String depId)
	{
		int result = 9;
		if(sameDb)
		{
			String entryId = StringUtils.checkNullString(this.getEntryId("UltraProcess:SysGroup", depId));//获取用户对应的主键C1
			if("".equals(entryId))
			{
				result = 2;//此部门不存在
				RecordLog.printLog("the dep:" + depId + " is not exist, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			else
			{
				RemedySession remedySession = new RemedySession(remedyUser, remedyPwd);//登录remedySession
				if(remedySession != null)
				{
					if(remedySession.deleteEntry("UltraProcess:SysGroup", entryId))
					{
						result = 0;//删除部门成功
					}
				}
				else
				{
					result = 8;//登录remedy失败
					RecordLog.printLog("del dep, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
				}
				remedySession.logout();
			}
		}
		else
		{
			
		}
		return result;
	}
	
	public int synchDepEdit(DepInfo dep)
	{
		int result = 9;
		if(sameDb)
		{
			List<RemedyField> remedyFieldList = this.getDepRemedyField(dep);//获取一条remedy字段及信息集合
			if(remedyFieldList != null && remedyFieldList.size() > 0)
			{
				String depId = dep.getPid();
				String entryId = StringUtils.checkNullString(this.getEntryId("UltraProcess:SysGroup", depId));//获取用户对应的主键C1
				if("".equals(entryId))
				{
					result = 2;//此部门不存在
					RecordLog.printLog("the dep:" + depId + " is not exist, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
				}
				else
				{
					RemedySession remedySession = new RemedySession(remedyUser, remedyPwd);
					if(remedySession != null)
					{
						if(remedySession.updateEntry("UltraProcess:SysGroup", entryId, remedyFieldList))
						{
							result = 0;//修改部门成功
						}
					}
					else
					{
						result = 8;//登录remedy失败
						RecordLog.printLog("edit dep, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
					}
					remedySession.logout();
				}
			}
		}
		else
		{
			
		}
		return result;
	}
	
	public int synchDepUserAdd(List<String> depIdList, List<String> userIdList)
	{
		int result = 9;
		if(sameDb)
		{
			int depLen = 0;
			if(depIdList != null)
				depLen = depIdList.size();
			int userLen = 0;
			if(userIdList != null)
				userLen = userIdList.size();
			if(depLen > 0 && userLen > 0)
			{
				RemedySession remedySession = new RemedySession(remedyUser, remedyPwd);
				if(remedySession != null)
				{
					String c1;
					String depId;
					String userId;
					String depV2Id;
					String userV2Id;
					List<RemedyField> remedyFieldList;
					int t = 0;//同步失败的个数
					for(int i=0;i<depLen;i++)
					{
						depId = depIdList.get(i);
						depV2Id = StringUtils.checkNullString(this.getEntryId("UltraProcess:SysGroup", depId));
						for(int j=0;j<userLen;j++)
						{
							userId = userIdList.get(j);
							userV2Id = StringUtils.checkNullString(this.getEntryId("UltraProcess:SysUser", userId));
							if("".equals(depV2Id) || "".equals(userV2Id))
							{
								t++;
								RecordLog.printLog("synch add relation, user:" + userId + " dep:" + depId + " is not exist in V2, synch to v2 failure!", RecordLog.LOG_LEVEL_INFO);
								continue;
							}
							remedyFieldList = new ArrayList<RemedyField> ();
							remedyFieldList.add(new RemedyField("620000027", "", RemedyFieldType.CHAR, depV2Id));
							remedyFieldList.add(new RemedyField("620000028", "", RemedyFieldType.CHAR, userV2Id));
							remedyFieldList.add(new RemedyField("620000032", "", RemedyFieldType.CHAR, depId));
							remedyFieldList.add(new RemedyField("620000033", "", RemedyFieldType.CHAR, userId));
							c1 = remedySession.addEntry("UltraProcess:SysGroupUser", remedyFieldList);
							if("".equals(StringUtils.checkNullString(c1)))
							{
								t++;
								RecordLog.printLog("synch add relation, user:" + userId + " dep:" + depId + " failure, synch to v2 failure!", RecordLog.LOG_LEVEL_INFO);
							}
						}
					}
					if(t == 0)
						result = 0;
				}
				else
				{
					result = 8;//登录remedy失败
					RecordLog.printLog("add group user, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
				}
				remedySession.logout();
			}
		}
		else
		{
			
		}
		return result;
	}
	
	public int synchDepUserDel(List<String> depIdList, List<String> userIdList)
	{
		int result = 0;
		if(sameDb)
		{
			int depLen = 0;
			if(depIdList != null)
				depLen = depIdList.size();
			int userLen = 0;
			if(userIdList != null)
				userLen = userIdList.size();
			if(depLen > 0 && userLen > 0)
			{
				try
				{
					String sql = "select c1, c620000032 depid, c620000033 userid from {UltraProcess:SysGroupUser} where 1=1";
					Map mapDep = UltraSmUtil.getSqlParameter(depIdList);
					sql = sql + " and c620000032 in (" + mapDep.get("?") + ")";
					Map mapUser = UltraSmUtil.getSqlParameter(userIdList);
					sql = sql + " and c620000033 in (" + mapUser.get("?") + ")";
					QueryAdapter queryAdapter = new QueryAdapter();
					DataTable table = queryAdapter.executeQuery(SqlReplace.replaceAllVar(sql, null, null).getSql(), ArrayTransferUtils.copyArraySimple((Object[]) mapDep.get("obj"), (Object[]) mapUser.get("obj")));
					int tableLen = 0;
					RemedySession remedySession = null;
					if(table != null)
					{
						tableLen = table.length();
						remedySession = new RemedySession(remedyUser, remedyPwd);
					}
					if(remedySession != null)
					{
						String entryId;
						int t = 0;
						for(int i=0;i<tableLen;i++)
						{
							entryId = StringUtils.checkNullString(table.getDataRow(i).getString("c1"));
							if(!remedySession.deleteEntry("UltraProcess:SysGroupUser", entryId))
							{
								t++;
								RecordLog.printLog("synch del relation, user:" + table.getDataRow(i).getString("userid") + " dep:" + table.getDataRow(i).getString("depid") + " failure, synch to v2 failure!", RecordLog.LOG_LEVEL_INFO);
							}
						}
						if(t == 0)
							result = 0;
					}
					else
					{
						result = 8;//登录remedy失败
						RecordLog.printLog("del group user, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
					}
					remedySession.logout();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			
		}
		return result;
	}
	
	public int synchDepUserDel(List<String> orgIdList, String orgType)
	{
		int result = 0;
		if(sameDb)
		{
			int orgLen = 0;
			if(orgIdList != null)
				orgLen = orgIdList.size();
			if(!"1".equals(StringUtils.checkNullString(orgType)) && !"2".equals(orgType))
			{
				result = 7;//传入的数据错误
				orgLen = 0;
				RecordLog.printLog("the parameter 'orgType' is wrong, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			if(orgLen > 0)
			{
				try
				{
					String sql = "select c1, c620000032 depid, c620000033 userid from {UltraProcess:SysGroupUser} where 1=1";
					Map mapOrg = UltraSmUtil.getSqlParameter(orgIdList);
					if("1".equals(orgType))//人员
						sql = sql + " and c620000033 in (" + mapOrg.get("?") + ")";
					else if("2".equals(orgType))//部门
						sql = sql + " and c620000032 in (" + mapOrg.get("?") + ")";
					else
						sql = sql + " and 1=2";
					QueryAdapter queryAdapter = new QueryAdapter();
					DataTable table = queryAdapter.executeQuery(SqlReplace.replaceAllVar(sql, null, null).getSql(), (Object[]) mapOrg.get("obj"));
					int tableLen = 0;
					RemedySession remedySession = null;
					if(table != null)
					{
						tableLen = table.length();
						remedySession = new RemedySession(remedyUser, remedyPwd);
					}
					if(remedySession != null)
					{
						String entryId;
						int t = 0;
						for(int i=0;i<tableLen;i++)
						{
							entryId = StringUtils.checkNullString(table.getDataRow(i).getString("c1"));
							if(!remedySession.deleteEntry("UltraProcess:SysGroupUser", entryId))
							{
								t++;
								RecordLog.printLog("synch del relation, user:" + table.getDataRow(i).getString("userid") + " dep:" + table.getDataRow(i).getString("depid") + " failure, synch to v2 failure!", RecordLog.LOG_LEVEL_INFO);
							}
						}
						if(t == 0)
							result = 0;
					}
					else
					{
						result = 8;
						RecordLog.printLog("del group user, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
					}
					remedySession.logout();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			
		}
		return result;
	}
	
	public int synchRoleAdd(RoleInfo role)
	{
		int result = 9;
		if(sameDb)
		{
			RemedySession remedySession = new RemedySession(remedyUser, remedyPwd);//登录remedySession
			if(remedySession != null)
			{
				String c1 = "";
				List<RemedyField> remedyFieldList = this.getRoleRemedyField(role);//获取一条remedy字段及信息集合
				if(remedyFieldList != null && remedyFieldList.size() > 0)
				{
					c1 = remedySession.addEntry("UltraProcess:RolesManage", remedyFieldList);//添加角色实体
				}
				if(!"".equals(StringUtils.checkNullString(c1)))
				{
					result = 0;//添加角色成功
				}
			}
			else
			{
				result = 8;//登录remedy失败
				RecordLog.printLog("add role, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			remedySession.logout();
		}
		else
		{
			
		}
		return result;
	}
	
	public int synchRoleDel(String roleId)
	{
		int result = 9;
		if(sameDb)
		{
			String entryId = StringUtils.checkNullString(this.getEntryId("UltraProcess:RolesManage", roleId));//获取角色对应的主键C1
			if("".equals(entryId))
			{
				result = 2;//此角色不存在
				RecordLog.printLog("the role:" + roleId + " is not exist, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			else
			{
				RemedySession remedySession = new RemedySession(remedyUser, remedyPwd);//登录remedySession
				if(remedySession != null)
				{
					if(remedySession.deleteEntry("UltraProcess:RolesManage", entryId))
					{
						result = 0;//删除角色成功
					}
				}
				else
				{
					result = 8;//登录remedy失败
					RecordLog.printLog("del role, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
				}
				remedySession.logout();
			}
		}
		else
		{
			
		}
		return result;
	}
	
	public int synchRoleEdit(RoleInfo role)
	{
		int result = 9;
		if(sameDb)
		{
			List<RemedyField> remedyFieldList = this.getRoleRemedyField(role);//获取一条remedy字段及信息集合
			if(remedyFieldList != null && remedyFieldList.size() > 0)
			{
				String roleId = role.getPid();
				String entryId = StringUtils.checkNullString(this.getEntryId("UltraProcess:RolesManage", role.getPid()));//获取角色对应的主键C1
				if("".equals(entryId))
				{
					result = 2;//此角色不存在
					RecordLog.printLog("the role:" + roleId + " is not exist, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
				}
				else
				{
					RemedySession remedySession = new RemedySession(remedyUser, remedyPwd);
					if(remedySession != null)
					{
						if(remedySession.updateEntry("UltraProcess:RolesManage", entryId, remedyFieldList))
						{
							result = 0;//修改角色成功
						}
					}
					else
					{
						result = 8;//登录remedy失败
						RecordLog.printLog("edit role, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
					}
					remedySession.logout();
				}
			}
		}
		else
		{
			
		}
		return result;
	}
	
	public int synchRoleOrgAdd(List<String> roleIdList, List<String> orgIdList, String orgType)
	{
		int result = 9;
		if(sameDb)
		{
			int roleLen = 0;
			if(roleIdList != null)
				roleLen = roleIdList.size();
			int orgLen = 0;
			if(orgIdList != null)
				orgLen = orgIdList.size();
			if(!"1".equals(StringUtils.checkNullString(orgType)) && !"2".equals(orgType))
			{
				result = 7;
				roleLen = 0;
				orgLen = 0;
				RecordLog.printLog("the parameter 'orgType' is wrong, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			if(roleLen > 0 && orgLen > 0)
			{
				RemedySession remedySession = new RemedySession(remedyUser, remedyPwd);
				if(remedySession != null)
				{
					String c1;
					String roleId;
					String orgId;
					String roleV2Id;
					String orgV2Id = "";
					List<RemedyField> remedyFieldList;
					int t = 0;//同步失败的个数
					for(int i=0;i<roleLen;i++)
					{
						roleId = roleIdList.get(i);
						roleV2Id = StringUtils.checkNullString(this.getEntryId("UltraProcess:RolesManage", roleId));
						for(int j=0;j<orgLen;j++)
						{
							orgId = orgIdList.get(j);
							remedyFieldList = new ArrayList<RemedyField> ();
							remedyFieldList.add(new RemedyField("660000028", "", RemedyFieldType.CHAR, roleV2Id));
							remedyFieldList.add(new RemedyField("660000031", "", RemedyFieldType.CHAR, roleId));
							if("1".equals(orgType))
							{
								orgV2Id = StringUtils.checkNullString(this.getEntryId("UltraProcess:SysUser", orgId));
								remedyFieldList.add(new RemedyField("660000026", "", RemedyFieldType.CHAR, orgV2Id));
								remedyFieldList.add(new RemedyField("660000033", "", RemedyFieldType.CHAR, orgId));
							}
							else if("2".equals(orgType))
							{
								orgV2Id = StringUtils.checkNullString(this.getEntryId("UltraProcess:SysGroup", orgId));
								remedyFieldList.add(new RemedyField("660000027", "", RemedyFieldType.CHAR, orgV2Id));
								remedyFieldList.add(new RemedyField("660000032", "", RemedyFieldType.CHAR, orgId));
							}
							if("".equals(roleV2Id) || "".equals(orgV2Id))
							{
								t++;
								RecordLog.printLog("synch add relation, role:" + roleId + " org:" + orgId + " is not exist in V2, synch to v2 failure!", RecordLog.LOG_LEVEL_INFO);
								continue;
							}
							c1 = remedySession.addEntry("UltraProcess:RolesUserGroupRel", remedyFieldList);
							if("".equals(StringUtils.checkNullString(c1)))
							{
								t++;
								RecordLog.printLog("synch add relation, role:" + roleId + " org:" + orgId + " failure, synch to v2 failure!", RecordLog.LOG_LEVEL_INFO);
							}
						}
					}
					if(t == 0)
						result = 0;
				}
				else
				{
					result = 8;//登录remedy失败
					RecordLog.printLog("add role org, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
				}
				remedySession.logout();
			}
		}
		else
		{
			
		}
		return result;
	}
	
	public int synchRoleOrgDel(List<String> roleIdList, List<String> orgIdList, String orgType)
	{
		int result = 9;
		if(sameDb)
		{
			int roleLen = 0;
			if(roleIdList != null)
				roleLen = roleIdList.size();
			int orgLen = 0;
			if(orgIdList != null)
				orgLen = orgIdList.size();
			if(!"1".equals(StringUtils.checkNullString(orgType)) && !"2".equals(orgType))
			{
				result = 7;
				roleLen = 0;
				orgLen = 0;
				RecordLog.printLog("the parameter 'orgType' is wrong, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			if(roleLen > 0 && orgLen > 0)
			{
				try
				{
					String sql = "select c1, c660000031 roleid, c660000032 depid, c660000033 userid from {UltraProcess:RolesUserGroupRel} where 1=1";
					Map mapRole = UltraSmUtil.getSqlParameter(roleIdList);
					sql = sql + " and c660000031 in (" + mapRole.get("?") + ")";
					Map mapOrg = UltraSmUtil.getSqlParameter(orgIdList);
					if("1".equals(orgType))
						sql = sql + " and c660000033 in (" + mapOrg.get("?") + ")";
					else if("2".equals(orgType))
						sql = sql + " and c660000032 in (" + mapOrg.get("?") + ")";
					else
						sql = sql + " and 1=2";
					QueryAdapter queryAdapter = new QueryAdapter();
					DataTable table = queryAdapter.executeQuery(SqlReplace.replaceAllVar(sql, null, null).getSql(), ArrayTransferUtils.copyArraySimple((Object[]) mapRole.get("obj"), (Object[]) mapOrg.get("obj")));
					int tableLen = 0;
					RemedySession remedySession = null;
					if(table != null)
					{
						tableLen = table.length();
						remedySession = new RemedySession(remedyUser, remedyPwd);
					}
					if(remedySession != null)
					{
						String entryId;
						int t = 0;
						for(int i=0;i<tableLen;i++)
						{
							entryId = StringUtils.checkNullString(table.getDataRow(i).getString("c1"));
							if(!remedySession.deleteEntry("UltraProcess:RolesUserGroupRel", entryId))
							{
								t++;
								RecordLog.printLog("synch del relation, role:" + table.getDataRow(i).getString("roleid") + " org:" + ("1".equals(orgType) ? table.getDataRow(i).getString("userid") : table.getDataRow(i).getString("depid")) + " failure, synch to v2 failure!", RecordLog.LOG_LEVEL_INFO);
							}
						}
						if(t == 0)
							result = 0;
					}
					else
					{
						result = 8;//登录remedy失败
						RecordLog.printLog("del role org, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
					}
					remedySession.logout();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			
		}
		return result;
	}
	
	public int synchRoleOrgDel(List<String> objIdList, String objType)
	{
		int result = 9;
		if(sameDb)
		{
			int objLen = 0;
			if(objIdList != null)
				objLen = objIdList.size();
			if(!"0".equals(StringUtils.checkNullString(objType)) && !"1".equals(objType) && !"2".equals(objType))
			{
				result = 7;//传入的数据错误
				objLen = 0;
				RecordLog.printLog("the parameter 'objType' is wrong, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			if(objLen > 0)
			{
				try
				{
					String sql = "select c1, c660000031 roleid, c660000032 depid, c660000033 userid from {UltraProcess:RolesUserGroupRel} where 1=1";
					Map mapObj = UltraSmUtil.getSqlParameter(objIdList);
					if("0".equals(objType))//角色
						sql = sql + " and c660000031 in (" + mapObj.get("?") + ")";
					else if("1".equals(objType))//人员
						sql = sql + " and c660000033 in (" + mapObj.get("?") + ")";
					else if("2".equals(objType))//部门
						sql = sql + " and c660000032 in (" + mapObj.get("?") + ")";
					else
						sql = sql + " and 1=2";
					QueryAdapter queryAdapter = new QueryAdapter();
					DataTable table = queryAdapter.executeQuery(SqlReplace.replaceAllVar(sql, null, null).getSql(), (Object[]) mapObj.get("obj"));
					int tableLen = 0;
					RemedySession remedySession = null;
					if(table != null)
					{
						tableLen = table.length();
						remedySession = new RemedySession(remedyUser, remedyPwd);
					}
					if(remedySession != null)
					{
						String entryId;
						int t = 0;
						for(int i=0;i<tableLen;i++)
						{
							entryId = StringUtils.checkNullString(table.getDataRow(i).getString("c1"));
							if(!remedySession.deleteEntry("UltraProcess:RolesUserGroupRel", entryId))
							{
								t++;
								RecordLog.printLog("synch del relation, role:" + table.getDataRow(i).getString("roleid") + " org:" + (!"".equals(table.getDataRow(i).getString("userid")) ? table.getDataRow(i).getString("userid") : table.getDataRow(i).getString("depid")) + " failure, synch to v2 failure!", RecordLog.LOG_LEVEL_INFO);
							}
						}
						if(t == 0)
							result = 0;
					}
					else
					{
						result = 8;//登录remedy失败
						RecordLog.printLog("del role org, login remedy failure, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
					}
					remedySession.logout();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			
		}
		return result;
	}
	
	/**
	 * 构造人员对应的remedy字段信息
	 * @param user
	 * @return
	 */
	private List<RemedyField> getUserRemedyField(UserInfo user)
	{
		List<RemedyField> remedyFieldList = new ArrayList<RemedyField> ();
		if(user != null)
		{
			boolean isAdd = true;// 是否添加
			CryptUtils crypt = CryptUtils.getInstance();
			String depId = StringUtils.checkNullString(user.getDepid());
			DepInfo company = depManagerService.getCompanyByDepid(depId);
			if(company == null)
				isAdd = false;
			String companyid = StringUtils.checkNullString(this.getEntryId("UltraProcess:SysGroup", isAdd ? company.getPid() : ""));
			depId = StringUtils.checkNullString(this.getEntryId("UltraProcess:SysGroup", depId));
			if("".equals(depId))
				isAdd = false;
			String groupId = StringUtils.checkNullString(user.getGroupid());
			if("".equals(groupId))
				isAdd = false;
			else
			{
				String[] groupArray = groupId.split(",");
				int groupLen = groupArray.length;
				groupId = "";
				for(int i=0;i<groupLen;i++)
				{
					groupId += this.getEntryId("UltraProcess:SysGroup", groupArray[i]) + ";";
				}
			}
			if(isAdd)
			{
				remedyFieldList.add(new RemedyField("630000001", "", RemedyFieldType.CHAR, StringUtils.checkNullString(user.getLoginname())));
				remedyFieldList.add(new RemedyField("630000003", "", RemedyFieldType.CHAR, StringUtils.checkNullString(user.getFullname())));
				remedyFieldList.add(new RemedyField("630000002", "", RemedyFieldType.CHAR, StringUtils.checkNullString(crypt.decode(user.getPwd()))));
				remedyFieldList.add(new RemedyField("630000005", "", RemedyFieldType.CHAR, StringUtils.checkNullString(user.getPosition())));
				remedyFieldList.add(new RemedyField("630000007", "", RemedyFieldType.CHAR, StringUtils.checkNullString(user.getType())));
				remedyFieldList.add(new RemedyField("630000008", "", RemedyFieldType.CHAR, StringUtils.checkNullString(user.getMobile())));
				remedyFieldList.add(new RemedyField("630000009", "", RemedyFieldType.CHAR, StringUtils.checkNullString(user.getPhone())));
				remedyFieldList.add(new RemedyField("630000010", "", RemedyFieldType.CHAR, StringUtils.checkNullString(user.getFax())));
				remedyFieldList.add(new RemedyField("630000011", "", RemedyFieldType.CHAR, StringUtils.checkNullString(user.getEmail())));
				remedyFieldList.add(new RemedyField("630000012", "", RemedyFieldType.CHAR, ("1".equals(StringUtils.checkNullString(user.getStatus())) ? "0" : "4")));
				remedyFieldList.add(new RemedyField("630000017", "", RemedyFieldType.CHAR, StringUtils.checkNullString(user.getOrdernum())));
				remedyFieldList.add(new RemedyField("630000015", "", RemedyFieldType.CHAR, depId));
				remedyFieldList.add(new RemedyField("630000036", "", RemedyFieldType.CHAR, groupId));
				remedyFieldList.add(new RemedyField("630000013", "", RemedyFieldType.CHAR, companyid));
				//remedyFieldList.add(new RemedyField("630000029", "", RemedyFieldType.CHAR, StringUtils.checkNullString("")));//int 用户ID
				remedyFieldList.add(new RemedyField("630000050", "", RemedyFieldType.CHAR, StringUtils.checkNullString(user.getPid())));//v4  用户ID
			}
		}
		else
		{
			RecordLog.printLog("the object user is null, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
		}
		return remedyFieldList;
	}

	private List<RemedyField> getDepRemedyField(DepInfo dep)
	{
		List<RemedyField> remedyFieldList = new ArrayList<RemedyField> ();
		if(dep != null)
		{
			boolean isAdd = true;// 是否添加
			String depId = dep.getPid();
			DepInfo company = depManagerService.getCompanyByDepid(depId);
			if(company == null)
			{
				isAdd = false;
			}
			String iddn = StringUtils.checkNullString(depManagerService.getAllParentIdById(depId));
			if("".equals(iddn))
			{
				isAdd = false;
			}
			else
			{
				String[] idArray = iddn.split(",");
				iddn = "";
				for(int i=0;i<idArray.length;i++)
				{
					if(!"0".equals(idArray[i]))
					{
						if(!"".equals(iddn))
							iddn += ".";
						iddn += this.getEntryId("UltraProcess:SysGroup", idArray[i]);
					}
				}
			}
			if(isAdd)
			{
				remedyFieldList.add(new RemedyField("630000018", "", RemedyFieldType.CHAR, StringUtils.checkNullString(dep.getDepname())));
				String deptype = StringUtils.checkNullString(dep.getDeptype());
				if("1".equals(deptype))
					deptype = "0";
				else if("2".equals(deptype))
					deptype = "1";
				else if("3".equals(deptype))
					deptype = "6";
				else if("4".equals(deptype))
					deptype = "7";
				else
					deptype = "3";
				String parentid = StringUtils.checkNullString(dep.getParentid());
				parentid = this.getEntryId("UltraProcess:SysGroup", parentid);
				String companyid = StringUtils.checkNullString(company.getPid());
				companyid = this.getEntryId("UltraProcess:SysGroup", companyid);
				remedyFieldList.add(new RemedyField("630000027", "", RemedyFieldType.CHAR, deptype));
				remedyFieldList.add(new RemedyField("630000020", "", RemedyFieldType.CHAR, parentid));
				remedyFieldList.add(new RemedyField("630000019", "", RemedyFieldType.CHAR, StringUtils.checkNullString(dep.getDepfullname())));
				remedyFieldList.add(new RemedyField("630000022", "", RemedyFieldType.CHAR, StringUtils.checkNullString(dep.getOrdernum())));
				remedyFieldList.add(new RemedyField("630000023", "", RemedyFieldType.CHAR, StringUtils.checkNullString(dep.getDepphone())));
				remedyFieldList.add(new RemedyField("630000024", "", RemedyFieldType.CHAR, StringUtils.checkNullString(dep.getDepfax())));
				remedyFieldList.add(new RemedyField("630000025", "", RemedyFieldType.CHAR, ("1".equals(StringUtils.checkNullString(dep.getStatus())) ? "0" : "4")));
				remedyFieldList.add(new RemedyField("630000028", "", RemedyFieldType.CHAR, StringUtils.checkNullString(dep.getRemark())));
				remedyFieldList.add(new RemedyField("630000037", "", RemedyFieldType.CHAR, StringUtils.checkNullString(iddn)));
				remedyFieldList.add(new RemedyField("630000027", "", RemedyFieldType.CHAR, companyid));
				//remedyFieldList.add(new RemedyField("", "", RemedyFieldType.CHAR, StringUtils.checkNullString("")));//int 组ID
				remedyFieldList.add(new RemedyField("630000050", "", RemedyFieldType.CHAR, StringUtils.checkNullString(dep.getPid())));//v4  部门ID
			}
		}
		else
		{
			RecordLog.printLog("the object dep is null, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
		}
		return remedyFieldList;
	}
	
	private List<RemedyField> getRoleRemedyField(RoleInfo role)
	{
		List<RemedyField> remedyFieldList = new ArrayList<RemedyField> ();
		if(role != null)
		{
			remedyFieldList.add(new RemedyField("660000001", "", RemedyFieldType.CHAR, StringUtils.checkNullString(role.getRolename())));
			remedyFieldList.add(new RemedyField("660000003", "", RemedyFieldType.CHAR, StringUtils.checkNullString(role.getRemark())));
			remedyFieldList.add(new RemedyField("630000050", "", RemedyFieldType.CHAR, StringUtils.checkNullString(role.getPid())));//v4  角色ID
		}
		else
		{
			RecordLog.printLog("the object role is null, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
		}
		return remedyFieldList;
	}
	
	/**
	 * 获取C1字段
	 * @param schema
	 * @param pid
	 * @return
	 */
	private String getEntryId(String schema, String pid)
	{
		String entryId = "";
		if("".equals(StringUtils.checkNullString(pid)))
			return entryId;
		String sql = "select c1 from {" + schema + "} where c630000050 = ?";
		QueryAdapter queryAdapter = new QueryAdapter();
		try
		{
			DataTable table = queryAdapter.executeQuery(SqlReplace.replaceAllVar(sql, null, null).getSql(), new Object[] {pid});
			if(table != null && table.length() > 0)
			{
				entryId = StringUtils.checkNullString(table.getDataRow(0).getString("c1"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return entryId;
	}
	
	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}
}

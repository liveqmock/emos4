package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.accredit.api.SecurityService;
import com.ultrapower.accredit.common.value.GroupUser;
import com.ultrapower.accredit.common.value.Organise;
import com.ultrapower.accredit.common.value.Role;
import com.ultrapower.accredit.common.value.User;
import com.ultrapower.accredit.rmiclient.RmiClientApplication;
import com.ultrapower.eoms.common.core.util.CryptUtils;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.service.SynchDataToPasm;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;

public class SynchDataToPasmImpl implements SynchDataToPasm
{
	private UserManagerService userManagerService;
	private DepManagerService depManagerService;
	private RoleManagerService roleManagerService;
	
	public int synchUserAdd(UserInfo userInfo)
	{
		int result = 9;
		if(userInfo != null)
		{
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service != null)
			{
				CryptUtils crypt = CryptUtils.getInstance();
				User user = new User();
				user.setUserAccount(StringUtils.checkNullString(userInfo.getLoginname()));//登录名
				user.setUserName(StringUtils.checkNullString(userInfo.getFullname()));//全名
				user.setPass(StringUtils.checkNullString(crypt.decode(userInfo.getPwd())));//密码
				user.setMobile(StringUtils.checkNullString(userInfo.getMobile()));//手机
				user.setTelephone(StringUtils.checkNullString(userInfo.getPhone()));//固定电话
				user.setEmail(StringUtils.checkNullString(userInfo.getEmail()));//E-mail
				user.setFax(StringUtils.checkNullString(userInfo.getFax()));//传真
				user.setUserStatus(NumberUtils.formatToInt(userInfo.getStatus()) == 1 ? 0 : 1);//状态
				user.setUserOrderby(NumberUtils.formatToInt(userInfo.getOrdernum()));//排序值
				user.setNote(StringUtils.checkNullString(userInfo.getRemark()));//备注
				//user.setDuty_id(StringUtils.checkNullString(userInfo.getPosition()));//用户职位
				//user.setUserType_id("0");//用户类型
				user.setMemo(StringUtils.checkNullString(userInfo.getPid()));
				user.setSysUser(0);//是否为系统用户(0:普通用户/1:系统内置用户(root))
				user.setLastModifyOperateDate(System.currentTimeMillis());
				user.setDeptID(StringUtils.checkNullString(userInfo.getDepid()));
				user.setDeptName(StringUtils.checkNullString(userInfo.getDepname()));
//				user.setUserCardNum();//员工编号
//				user.setPortralURI(StringUtils.checkNullString(userInfo.getImage()));//头像
				int f = service.addUser(user);
				if(f == 0)
				{
					result = 0;
				}
				else if(f == 2)
				{
					result = 1;//该用户在pasm中已存在
					RecordLog.printLog("the user is exist in pasm, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
				}
			}
			else
			{
				result = 8;//PASM链接RMI失败
				RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
		}
		else
		{
			RecordLog.printLog("the object userInfo is null, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
		}
		return result;
	}
	
	public int synchUserDel(String loginName)
	{
		int result = 9;
		SecurityService service = RmiClientApplication.getInstance().getSecurityService();
		if(service != null)
		{
			User user = null;
			try {
				user = service.getUserByAccount(loginName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(user == null)
			{
				result = 2;//该用户不存在
				RecordLog.printLog("the user:" + loginName + " is not exist, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			else
			{
				int resultPasm = service.removeUser(loginName);
				if(resultPasm == 0)
				{
					result = 0;
				}
			}
		}
		else
		{
			result = 8;//PASM链接RMI失败
			RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
		}
		return result;
	}
	
	public int synchUserEdit(UserInfo userInfo)
	{
		int result = 9;
		if(userInfo != null)
		{
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service != null)
			{
				String loginName = userInfo.getLoginname();
				String userId = userInfo.getPid();
				User user = null;
				try {
					user = service.getUserByAccount(loginName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(user == null)
				{
					result = 2;//该用户不存在
					RecordLog.printLog("the user:" + userId + " is not exist, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
				}
				else
				{
					CryptUtils crypt = CryptUtils.getInstance();
					if(this.synchUserPwdEdit(userId, crypt.decode(userInfo.getPwd())) == 0)
					{
						user.setUserAccount(StringUtils.checkNullString(userInfo.getLoginname()));//登录名
						user.setUserName(StringUtils.checkNullString(userInfo.getFullname()));//全名
						user.setMobile(StringUtils.checkNullString(userInfo.getMobile()));//手机
						user.setTelephone(StringUtils.checkNullString(userInfo.getPhone()));//固定电话
						user.setEmail(StringUtils.checkNullString(userInfo.getEmail()));//E-mail
						user.setFax(StringUtils.checkNullString(userInfo.getFax()));//传真
						user.setUserStatus(NumberUtils.formatToInt(userInfo.getStatus()) == 1 ? 0 : 1);//状态
						user.setUserOrderby(NumberUtils.formatToInt(userInfo.getOrdernum()));//排序值
						user.setNote(StringUtils.checkNullString(userInfo.getRemark()));//备注
						//user.setDuty_id(StringUtils.checkNullString(userInfo.getPosition()));//用户职位
						//user.setUserType_id(StringUtils.checkNullString("0"));//用户类型
						user.setMemo(StringUtils.checkNullString(userInfo.getPid()));
						user.setDeptID(StringUtils.checkNullString(userInfo.getDepid()));//部门id
						user.setLastModifyOperateDate(System.currentTimeMillis());
						user.setDeptName(StringUtils.checkNullString(userInfo.getDepname()));//部门名称
						user.setLastDate(NumberUtils.formatToInt(userInfo.getLastlogintime())*1000);
//						user.setUserCardNum(0);//员工编号
//						user.setPortralURI(StringUtils.checkNullString(userInfo.getImage()));//头像
						int resultPasm = service.updateUser(user);
						if(resultPasm == 0)
						{
							result = 0;
						}
					}
					else
					{
						RecordLog.printLog("edit user password failure!", RecordLog.LOG_LEVEL_ERROR);
					}
				}
			}
			else
			{
				result = 8;//PASM链接RMI失败
				RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
		}
		else
		{
			RecordLog.printLog("the object userInfo is null, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
		}
		return result;
	}
	
	public int synchUserPwdEdit(String userId, String newPwd)
	{
		int result = 9;
		UserInfo userInfo = userManagerService.getUserByID(userId);
		if(userInfo != null)
		{
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service != null)
			{
				String loginName = userInfo.getLoginname();
				User user = null;
				try {
					user = service.getUserByAccount(loginName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(user == null)
				{
					result = 2;//该用户不存在
					RecordLog.printLog("the user:" + userId + " is not exist, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
				}
				else
				{
					try
					{
						int resultPasm = service.clientChangePassWord(loginName, newPwd);
						if(resultPasm != 0)
						{
							result = 0;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			else
			{
				result = 8;//PASM链接RMI失败
				RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
		}
		else
		{
			RecordLog.printLog("the object userInfo is null, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
		}
		return result;
	}

	public int synchDepAdd(DepInfo depInfo)
	{
		int result = 9;
		if(depInfo != null)
		{
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service != null)
			{
				Organise organise = new Organise();
				organise.setDept_id(StringUtils.checkNullString(depInfo.getPid()));//部门id
				organise.setDept_name(StringUtils.checkNullString(depInfo.getDepname()));//部门名称
				organise.setNode(StringUtils.checkNullString(depInfo.getRemark()));
				organise.setOrg_fax(StringUtils.checkNullString(depInfo.getDepfax()));
				organise.setOrg_phone(StringUtils.checkNullString(depInfo.getDepphone()));
				organise.setOrg_orderBy(NumberUtils.formatToInt(depInfo.getOrdernum()));
				organise.setOrg_status(NumberUtils.formatToInt(depInfo.getStatus()) == 1 ? 0 : 1);
				organise.setSuper_id(StringUtils.checkNullString(depInfo.getParentid().equals("0")?"1":depInfo.getParentid()));
				organise.setType(0);
				organise.setSys_group(0);
//				organise.setState(depInfo.getStatus()==1?0:1);
//				organise.setGroup_dnid(StringUtils.checkNullString(depInfo.getDepdns()));
//				organise.setGroup_dnname(StringUtils.checkNullString(depInfo.getDepfullname()));
				boolean resultPasm = service.addOrganise(organise);
				if(resultPasm)
				{
					result = 0;
				}
			}
			else
			{
				result = 8;//PASM链接RMI失败
				RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
		}
		else
		{
			RecordLog.printLog("the object depInfo is null, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
		}
		return result;
	}
	
	public int synchDepDel(String depId)
	{
		int result = 9;
		SecurityService service = RmiClientApplication.getInstance().getSecurityService();
		if(service != null)
		{
			Organise organise = null;
			try {
				organise = service.getOrganise(depId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(organise == null)
			{
				result = 2;//该部门不存在
				RecordLog.printLog("the dep:" + depId + " is not exist, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			else
			{
				List<String> depIdList = new ArrayList<String> ();
				depIdList.add(depId);
				if(this.synchDepUserDel(depIdList, "2") == 0)
				{
					boolean resultPasm = service.removeOrganise(organise);
					if(resultPasm)
					{
						result = 0;
					}
				}
				else
				{
					RecordLog.printLog("delete the dep's user failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
				}
			}
		}
		else
		{
			result = 8;//PASM链接RMI失败
			RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
		}
		return result;
	}
	
	public int synchDepEdit(DepInfo depInfo)
	{
		int result = 9;
		if(depInfo != null)
		{
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service != null)
			{
				String depId = depInfo.getPid();
				Organise organise = null;
				try {
					organise = service.getOrganise(depId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(organise == null)
				{
					result = 2;//该部门不存在
					RecordLog.printLog("the dep:" + depId + " is not exist, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
				}
				else
				{
					organise.setDept_id(StringUtils.checkNullString(depInfo.getPid()));//部门id
					organise.setDept_name(StringUtils.checkNullString(depInfo.getDepname()));//部门名称
					organise.setNode(StringUtils.checkNullString(depInfo.getRemark()));
					organise.setOrg_fax(StringUtils.checkNullString(depInfo.getDepfax()));
					organise.setOrg_phone(StringUtils.checkNullString(depInfo.getDepphone()));
					organise.setOrg_orderBy(NumberUtils.formatToInt(depInfo.getOrdernum()));
					organise.setOrg_status(NumberUtils.formatToInt(depInfo.getStatus()) == 1 ? 0 : 1);
					organise.setSuper_id(StringUtils.checkNullString(depInfo.getParentid().equals("0")?"1":depInfo.getParentid()));
					organise.setType(0);
					organise.setSys_group(0);
//					organise.setState(depInfo.getStatus()==1?0:1);
//					organise.setGroup_dnid(StringUtils.checkNullString(depInfo.getDepdns()));
//					organise.setGroup_dnname(StringUtils.checkNullString(depInfo.getDepfullname()));
					boolean resultPasm = service.updateOrganise(organise);
					if(resultPasm)
					{
						result = 0;
					}
				}
			}
			else
			{
				result = 8;//PASM链接RMI失败
				RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
		}
		else
		{
			RecordLog.printLog("the object userInfo is null, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
		}
		return result;
	}
	
	public int synchDepUserAdd(List<String> depIdList, List<String> userIdList)
	{
		int result = 9;
		int depLen = 0;
		if(depIdList != null)
			depLen = depIdList.size();
		int userLen = 0;
		if(userIdList != null)
			userLen = userIdList.size();
		if(depLen > 0 && userLen > 0)
		{
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service != null)
			{
				List<GroupUser> groupUserList = new ArrayList<GroupUser> ();
				GroupUser groupUser;
				String loginName;
				int t = 0;
				for(int i=0;i<userLen;i++)
				{
					loginName = userManagerService.getLoginNameByPid(userIdList.get(i));
					if("".equals(StringUtils.checkNullString(loginName)))
					{
						t++;
						RecordLog.printLog("the user: " + loginName + " is null, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
						continue;
					}
					for(int j=0;j<depLen;j++)
					{
						groupUser = new GroupUser();
						groupUser.setDept_id(depIdList.get(j));
						groupUser.setUserAccount(loginName);
						groupUserList.add(groupUser);
					}
				}
				int resultPasm = -1;
				try {
					resultPasm = service.addGroupUser(groupUserList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(t == 0 && resultPasm == 0)
				{
					result = 0;
				}
			}
			else
			{
				result = 8;//PASM链接RMI失败
				RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return result;
	}
	
	public int synchDepUserDel(List<String> depIdList, List<String> userIdList)
	{
		int result = 9;
		int depLen = 0;
		if(depIdList != null)
			depLen = depIdList.size();
		int userLen = 0;
		if(userIdList != null)
			userLen = userIdList.size();
		if(depLen > 0 && userLen > 0)
		{
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service != null)
			{
				List<GroupUser> groupUserList = new ArrayList<GroupUser> ();
				GroupUser groupUser;
				String loginName;
				int t = 0;
				for(int i=0;i<userLen;i++)
				{
					loginName = userManagerService.getLoginNameByPid(userIdList.get(i));
					if("".equals(StringUtils.checkNullString(loginName)))
					{
						t++;
						RecordLog.printLog("the user: " + loginName + " is null, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
						continue;
					}
					for(int j=0;j<depLen;j++)
					{
						groupUser = new GroupUser();
						groupUser.setDept_id(depIdList.get(j));
						groupUser.setUserAccount(loginName);
						groupUserList.add(groupUser);
					}
				}
				int resultPasm = -1;
				try {
					resultPasm = service.removeGroupUser(groupUserList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(t == 0 && resultPasm == 0)
				{
					result = 0;
				}
			}
			else
			{
				result = 8;//PASM链接RMI失败
				RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return result;
	}
	
	public int synchDepUserDel(List<String> orgIdList, String orgType)
	{
		int result = 9;
		int orgLen = 0;
		if(orgIdList != null)
			orgLen = orgIdList.size();
		if(!"2".equals(StringUtils.checkNullString(orgType)))
		{
			if(!"1".equals(orgType))//当orgType=1时，pasm会自动删除组成员关系
			{
				result = 7;
				RecordLog.printLog("the parameter 'orgLen' is wrong, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			else
			{
				result = 0;//当orgType=1时，pasm会自动删除组成员关系
			}
			orgLen = 0;
		}
		if(orgLen > 0)
		{
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service != null)
			{
				List<GroupUser> groupUserList = new ArrayList<GroupUser> ();
				GroupUser groupUser;
				if("2".equals(orgType))//加判断原因：为了以后扩展
				{
					List<UserInfo> userList;
					UserInfo user;
					String depId;
					String loginName;
					int resultPasm;
					int userLen;
					int t = 0;
					for(int i=0;i<orgLen;i++)
					{
						depId = orgIdList.get(i);
						userList = depManagerService.getUserByDepID(depId, false);
						userLen = 0;
						if(userList != null)
							userLen = userList.size();
						for(int j=0;j<userLen;j++)
						{
							user = userList.get(j);
							if(user == null)
								continue;
							loginName = StringUtils.checkNullString(user.getLoginname());
							if("".equals(loginName))
							{
								t++;
								RecordLog.printLog("the user: " + loginName + " is null, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
								continue;
							}
							groupUser = new GroupUser();
							groupUser.setDept_id(depId);
							groupUser.setUserAccount(loginName);
							groupUserList.add(groupUser);
						}
						if(groupUserList != null && groupUserList.size() > 0)
						{
							resultPasm = -1;
							try {
								resultPasm = service.removeGroupUser(groupUserList);
							} catch (Exception e) {
								e.printStackTrace();
							}
							if(t == 0 && resultPasm == 0)
							{
								result = 0;
							}
						}
						else
						{
							result = 0;
						}
					}
				}
			}
			else
			{
				result = 8;//PASM链接RMI失败
				RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
		}
		result = 0;
		return result;
	}
	
	public int synchRoleAdd(RoleInfo roleInfo)
	{
		int result = 9;
		if(roleInfo != null)
		{
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service != null)
			{
				Role role = new Role();
				role.setId(StringUtils.checkNullString(roleInfo.getPid()));
				role.setName(StringUtils.checkNullString(roleInfo.getRolename()));
				role.setState(0);
				role.setSuper_id(StringUtils.checkNullString(roleInfo.getParentid()));
				role.setNote(StringUtils.checkNullString(roleInfo.getRemark()));
				
				int f = service.addRole(role);
				if(f == 0)
				{
					result = 0;
				}
			}
			else
			{
				result = 8;//PASM链接RMI失败
				RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
		}
		else
		{
			RecordLog.printLog("the object roleInfo is null, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
		}
		return result;
	}
	
	public int synchRoleDel(String roleId)
	{
		int result = 9;
		SecurityService service = RmiClientApplication.getInstance().getSecurityService();
		if(service != null)
		{
			Role role = service.getRole(roleId);
			if(role == null)
			{
				result = 2;//该角色不存在
				RecordLog.printLog("the role:" + roleId + " is not exist, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			else
			{
				int resultPasm = -1;
				try {
					resultPasm = service.removeRole(roleId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(resultPasm == 0)
				{
					result = 0;
				}
			}
		}
		else
		{
			result = 8;//PASM链接RMI失败
			RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
		}
		return result;
	}
	
	public int synchRoleEdit(RoleInfo roleInfo)
	{
		int result = 9;
		if(roleInfo != null)
		{
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service != null)
			{
				String roleId = roleInfo.getPid();
				Role role = service.getRole(roleId);
				if(role == null)
				{
					result = 2;//该角色不存在
					RecordLog.printLog("the role:" + roleId + " is not exist, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
				}
				else
				{
					role.setName(StringUtils.checkNullString(roleInfo.getRolename()));
					role.setNote(StringUtils.checkNullString(roleInfo.getRemark()));
					int resultPasm = service.updateRole(role);
					if(resultPasm == 0)
					{
						result = 0;
					}
					else if(resultPasm == 2)
					{
						result = 1;//该角色名称已经存在
						RecordLog.printLog("the rolename is exist, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
					}
				}
			}
			else
			{
				result = 8;//PASM链接RMI失败
				RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
		}
		else
		{
			RecordLog.printLog("the object roleInfo is null, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
		}
		return result;
	}
	
	public int synchRoleOrgAdd(List<String> roleIdList, List<String> orgIdList, String orgType)
	{
		int result = 9;
		int roleLen = 0;
		if(roleIdList != null)
			roleLen = roleIdList.size();
		int orgLen = 0;
		if(orgIdList != null)
			orgLen = orgIdList.size();
		if(!"1".equals(StringUtils.checkNullString(orgType)))
		{
			if("2".equals(orgType))
			{
				result = 0;
			}
			else
			{
				result = 7;
				RecordLog.printLog("the parameter 'orgType' is wrong, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			roleLen = 0;
			orgLen = 0;
		}
		if(roleLen > 0 && orgLen > 0)
		{
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service != null)
			{
				String loginName;
				try
				{
					int resultPasm;
					int t = 0;
					for(int i=0;i<orgLen;i++)
					{
						loginName = userManagerService.getLoginNameByPid(orgIdList.get(i));
						resultPasm = service.addUserRole(loginName, roleIdList);
						if(resultPasm <= 0)
						{
							t++;
							RecordLog.printLog("synch add relation, the user's(" + loginName + ") role failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
						}
					}
					if(t == 0)
						result = 0;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else
			{
				result = 8;//PASM链接RMI失败
				RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return result;
	}
	
	public int synchRoleOrgDel(List<String> roleIdList, List<String> orgIdList, String orgType)
	{
		int result = 9;
		int roleLen = 0;
		if(roleIdList != null)
			roleLen = roleIdList.size();
		int orgLen = 0;
		if(orgIdList != null)
			orgLen = orgIdList.size();
		if(!"1".equals(StringUtils.checkNullString(orgType)))
		{
			if("2".equals(orgType))
			{
				result = 0;
			}
			else
			{
				result = 7;
				RecordLog.printLog("the parameter 'orgType' is wrong, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			roleLen = 0;
			orgLen = 0;
		}
		if(roleLen > 0 && orgLen > 0)
		{
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service != null)
			{
				String loginName;
				try
				{
					int resultPasm;
					int t = 0;
					for(int i=0;i<orgLen;i++)
					{
						loginName = userManagerService.getLoginNameByPid(orgIdList.get(i));
						resultPasm = service.removeUserRole(loginName, roleIdList);
						if(resultPasm <= 0)
						{
							t++;
							RecordLog.printLog("synch del relation, the user's(" + loginName + ") role failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
						}
					}
					if(t == 0)
						result = 0;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else
			{
				result = 8;//PASM链接RMI失败
				RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return result;
	}
	
	public int synchRoleOrgDel(List<String> objIdList, String objType)
	{
		int result = 9;
		int objLen = 0;
		if(objIdList != null)
			objLen = objIdList.size();
		if(!"0".equals(StringUtils.checkNullString(objType)) && !"1".equals(objType))
		{
			if("2".equals(objType))
			{
				result = 0;
			}
			else
			{
				result = 7;
				RecordLog.printLog("the parameter 'orgType' is wrong, synch to v2 failure!", RecordLog.LOG_LEVEL_ERROR);
			}
			objLen = 0;
		}
		if(objLen > 0)
		{
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service != null)
			{
				if("0".equals(objType))
				{
					List<UserInfo> userList;
					UserInfo user;
					String roleId;
					List<String> ridList;
					int t = 0;
					for(int i=0;i<objLen;i++)
					{
						roleId = objIdList.get(i);
						ridList = new ArrayList<String> ();
						ridList.add(roleId);
						userList = roleManagerService.getUserByRoleID(roleId);
						int userLen = 0;
						if(userList != null)
							userLen = userList.size();
						for(int j=0;j<userLen;j++)
						{
							user = userList.get(j);
							if(user == null)
								continue;
							if(service.removeUserRole(user.getLoginname(), ridList) <= 0)
							{
								t++;
								RecordLog.printLog("synch del relation, role:" + roleId + " user:" + user.getLoginname() + " failure, synch to v2 failure!", RecordLog.LOG_LEVEL_INFO);
							}
						}
					}
					if(t == 0)
						result = 0;
				}
				else if("1".equals(objType))
				{
					List<String> roleIdList;
					String userId;
					String loginName;
					int resultPasm;
					int t = 0;
					for(int i=0;i<objLen;i++)
					{
						userId = objIdList.get(i);
						roleIdList = roleManagerService.getRoleIdByUserID(userId);
						loginName = userManagerService.getLoginNameByPid(userId);
						resultPasm = service.removeUserRole(loginName, roleIdList);
						if(resultPasm <= 0)
						{
							t++;
							RecordLog.printLog("synch del relation, the user's(" + loginName + ") role failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
						}
					}
					if(t == 0)
						result = 0;
				}
			}
			else
			{
				result = 8;//PASM链接RMI失败
				RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return result;
	}
	
	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}
	public void setRoleManagerService(RoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}
}

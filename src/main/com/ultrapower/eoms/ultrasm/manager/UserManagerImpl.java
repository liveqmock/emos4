package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.ArrayTransferUtils;
import com.ultrapower.eoms.common.core.util.CryptUtils;
import com.ultrapower.eoms.common.core.util.PinYinUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.external.service.EomsInfoDownPasm;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.msextend.notice.web.NoticeViewLogAction;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.PwdManage;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.RoleOrg;
import com.ultrapower.eoms.ultrasm.model.RoleOrgShow;
import com.ultrapower.eoms.ultrasm.model.SearchCondition; //import com.ultrapower.eoms.ultrasm.model.SearchCondition;
import com.ultrapower.eoms.ultrasm.model.UserDep;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.AttachmentManagerService;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.eoms.ultrasm.service.PwdManageService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.service.SynchDataService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;
import com.ultrapower.remedy4j.common.IRemedyUserManager;

@Transactional
public class UserManagerImpl implements UserManagerService
{
	private NoticeViewLogAction noticeViewLogAction;
	private IDao<UserInfo> userManagerDao;
	private IDao<UserDep> userDepDao;
	private IDao<RoleOrg> roleOrgDao;
	private IDao<PwdManage> pwdManageDao;
	private IDao<SearchCondition> searchConditionDao;
	private IDao<DepInfo> depInfoDao;
	private IDao<Map> depJdbcDao;
	private RoleManagerService roleManagerService;
	private DicManagerService dicManagerService;
	private DepManagerService depManagerService;
	private PwdManageService pwdManageService;
	private IRemedyUserManager remedyUserManager;
	private SynchDataService synchDataDown;
	private EomsInfoDownPasm synchInfoPasm;
	private AttachmentManagerService attachmentManagerService;

	public boolean isUnique(String loginName)
	{
		if ("".equals(StringUtils.checkNullString(loginName)))
		{
			return false;
		}
		List<UserInfo> userList = userManagerDao.find(" from UserInfo where loginname = ?", new Object[] { loginName });
		if (userList != null && userList.size() > 0)
		{
			return false;
		}
		return true;
	}

	public String getPidByLoginName(String loginName)
	{
		String pid = "";
		if (!"".equals(StringUtils.checkNullString(loginName)))
		{
			List<UserInfo> userList = userManagerDao.find(" from UserInfo where loginname = ?", new Object[] { loginName });
			if (userList != null && userList.size() > 0)
			{
				pid = userList.get(0).getPid();
			}
		}
		return pid;
	}

	public String getGroupIdsByLoginName(String loginName)
	{
		UserInfo user = this.getUserByLoginName(loginName, false);
		if (user == null)
		{
			return null;
		}
		return StringUtils.checkNullString(user.getGroupid());
	}

	public String getLoginNameByPid(String pid)
	{
		String loginName = "";
		UserInfo user = this.getUserByID(pid);
		if (user != null)
		{
			loginName = user.getLoginname();
		}
		return loginName;
	}

	public List<String> getLoginNameByPid(List<String> userIdList)
	{
		List<String> loginNameList = null;
		userIdList = UltraSmUtil.removeNullData(userIdList);
		if (userIdList != null && userIdList.size() > 0)
		{
			loginNameList = new ArrayList<String>();
			Map map = UltraSmUtil.getSqlParameter(userIdList);
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery("from UserInfo where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
			if (table != null && table.length() > 0)
			{
				for (int i = 0; i < table.length(); i++)
				{
					String temp = table.getDataRow(i).getString(0);
					if (!"".equals(StringUtils.checkNullString(temp)))
					{
						loginNameList.add(temp);
					}
				}
			}
		}
		return loginNameList;
	}

	public String getUserNameByID(String userId)
	{
		if ("".equals(StringUtils.checkNullString(userId)))
		{
			return "";
		}
		String userName = "";
		List<UserInfo> userList = userManagerDao.find(" from UserInfo where pid = ?", new Object[] { userId });
		if (userList != null && userList.size() > 0)
		{
			userName = userList.get(0).getFullname();
		}
		return userName;
	}

	public String getUserNamesById(String userIds)
	{
		return this.getUserNames(userIds, "2");
	}

	public String getUserNames(String userIds, String type)
	{
		String userInfos = "";
		userIds = StringUtils.checkNullString(userIds).replaceAll(" ", "");
		if ("".equals(userIds))
		{
			return "";
		}
		try
		{
			type = StringUtils.checkNullString(type);
			Map map = UltraSmUtil.getSqlParameter(userIds);
			String field = "pid";
			if ("1".equals(type))
				field = "loginname";
			List<UserInfo> userList = userManagerDao.find("from UserInfo where " + field + " in (" + map.get("?") + ")", (Object[]) map.get("obj"));
			int userLen = 0;
			if (userList != null)
			{
				userLen = userList.size();
			}
			UserInfo user;
			userIds = "";
			String userNames = "";
			for (int i = 0; i < userLen; i++)
			{
				user = userList.get(i);
				if (user == null)
					continue;
				if (!"".equals(userIds))
				{
					userIds += ",";
					userNames += ",";
				}
				if ("1".equals(type))
					userIds += user.getLoginname();
				else
					userIds += user.getPid();
				userNames += user.getFullname();
			}
			if (userLen > 0)
				userInfos = userIds + ";" + userNames;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return userInfos;
	}

	public String getImageById(String userId)
	{
		if ("".equals(StringUtils.checkNullString(userId)))
		{
			return "";
		}
		String image = "";
		List<UserInfo> userList = userManagerDao.find(" from UserInfo where pid = ?", new Object[] { userId });
		if (userList != null && userList.size() > 0)
		{
			image = userList.get(0).getImage();
		}
		if ("".equals(StringUtils.checkNullString(image)))
			image = "default.png";
		else
		{
			List<Attachment> attachmentList = attachmentManagerService.getAttachmentByRelation(image);
			if (attachmentList != null && attachmentList.size() > 0)
				image = StringUtils.checkNullString(attachmentList.get(attachmentList.size() - 1).getRealname());
		}
		return image;
	}

	public boolean saveUserInfo(UserInfo user, List groupIdList, List ptdepIdList, List roleIdList, List delGroupIdList, List delPtdepIdList, List delRoleIdList, String updatePwd)
	{
		if (user == null)
		{
			return false;
		}
		//密码加密
		if ("true".equalsIgnoreCase(updatePwd))
		{
			user.setPwd(this.encodeUserPwd(user.getPwd()));
		}
		if ("".equals(StringUtils.checkNullString(user.getPid())))
		{
			return this.addUserInfo(user, groupIdList, ptdepIdList, roleIdList);
		}
		else
		{
			return this.updateUserInfo(user, groupIdList, ptdepIdList, roleIdList, delGroupIdList, delPtdepIdList, delRoleIdList);
		}
	}

	private boolean addUserInfo(UserInfo user, List groupIdList, List ptdepIdList, List roleIdList)
	{
		String userId = this.addUserInfo(user);
		if (!"".equals(StringUtils.checkNullString(userId)))
		{
			return this.addUserRelation(userId, groupIdList, ptdepIdList, roleIdList);
		}
		return false;
	}

	public String addUserInfo(UserInfo user)
	{
		return this.addUserInfo(user, "");
	}

	public String addUserInfo(UserInfo user, String systemMark)
	{
		if (user == null)
		{
			return null;
		}
		String userId = "";
		try
		{
			//			String image = user.getImage();
			//			if(!"".equals(StringUtils.checkNullString(image)))
			//			{
			//				List<Attachment> attList = attachmentManagerService.getAttachmentByRelation(image);
			//				if(attList != null && attList.size() > 0)
			//					user.setImage(attList.get(0).getRealname());
			//			}
			String pwd = this.decodePwd(user.getPwd());
			String isSynchToRemedy = StringUtils.checkNullString(PropertiesUtils.getProperty("eoms.isSynchToRemedy"));
			boolean isSuccess = true;
			if ("true".equals(isSynchToRemedy)) // 如果同步到remedy
				isSuccess = remedyUserManager.addUser(user.getLoginname(), pwd, user.getFullname());
			if (isSuccess) // 同步remedy成功
			{
				if (Constants.PWD_MANAGE)
				{
					this.saveUserPwd(user.getLoginname(), pwd, false);
				}
				user.setPyname(PinYinUtils.getPinYin(user.getFullname(), "3"));
				if ("".equals(StringUtils.checkNullString(user.getPid())))
				{
					user.setPid(UUIDGenerator.getUUIDoffSpace());
				}
				userManagerDao.save(user);
				int synchResult = synchDataDown.synchUserAdd(user, systemMark);
				if (synchResult == 0)
				{
					userId = user.getPid();
				}
				else
				{
					RecordLog.printLog("add userinfo success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
					userManagerDao.remove(user);
				}
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return userId;
	}

	public boolean addUserInfo(List<UserInfo> userList)
	{
		if (userList != null && userList.size() > 0)
		{
			for (int i = 0; i < userList.size(); i++)
			{
				if ("".equals(StringUtils.checkNullString(this.addUserInfo(userList.get(i)))))
				{
					return false;
				}
			}
		}
		return true;
	}

	private boolean addUserRelation(String userId, List groupIdList, List ptdepIdList, List roleIdList)
	{
		if ("".equals(StringUtils.checkNullString(userId)))
		{
			return false;
		}
		return this.addUserDep(userId, groupIdList, ptdepIdList) && this.addUserRole(userId, roleIdList);
	}

	public boolean addUserDep(String userId, List groupIdList, List ptdepIdList)
	{
		return this.addUserDep(userId, groupIdList, ptdepIdList, "");
	}

	public boolean addUserDep(String userId, List groupIdList, List ptdepIdList, String systemMark)
	{
		boolean result = false;
		if ("".equals(StringUtils.checkNullString(userId)))
		{
			return false;
		}
		UserInfo user = this.getUserByID(userId);
		groupIdList = UltraSmUtil.removeNullData(groupIdList);
		List<UserDep> udList = new ArrayList<UserDep>();
		List<String> didList = new ArrayList<String>();
		if (groupIdList != null && groupIdList.size() > 0)
		{
			UserDep ud = null;
			String userpid = "";
			if ("".equals(StringUtils.checkNullString(systemMark)))
			{
				UserSession userSession = (UserSession) ActionContext.getRequest().getSession().getAttribute("userSession");
				if (userSession != null)
				{
					userpid = userSession.getPid();
				}
				else
				{
					userpid = this.getPidByLoginName(UltraSmUtil.MANAGER);
				}
			}
			long currentTime = TimeUtils.getCurrentTime();
			List oldGroupIdList = depManagerService.getDepIdByUser(userId, "1");
			for (int i = 0; i < groupIdList.size(); i++)
			{
				String groupId = (String) groupIdList.get(i);
				if (oldGroupIdList.indexOf(groupId) >= 0)
				{
					continue;
				}
				ud = new UserDep();
				ud.setUserid(userId);
				ud.setDepid(groupId);
				ud.setLoginname(user.getLoginname());
				ud.setRelatetype("1");//关系类型 1.所属组 2.兼职部门
				ud.setCreater(userpid);
				ud.setCreatetime(currentTime);
				ud.setLastmodifier(userpid);
				ud.setLastmodifytime(currentTime);
				udList.add(ud);
				didList.add(groupId);
			}
		}
		ptdepIdList = UltraSmUtil.removeNullData(ptdepIdList);
		if (ptdepIdList != null && ptdepIdList.size() > 0)
		{
			UserDep ud = null;
			String userpid = "";
			if ("".equals(StringUtils.checkNullString(systemMark)))
			{
				UserSession userSession = (UserSession) ActionContext.getRequest().getSession().getAttribute("userSession");
				if (userSession != null)
				{
					userpid = userSession.getPid();
				}
				else
				{
					userpid = this.getPidByLoginName(UltraSmUtil.MANAGER);
				}
			}
			long currentTime = TimeUtils.getCurrentTime();
			List oldPtdepIdList = depManagerService.getDepIdByUser(userId, "2");
			for (int i = 0; i < ptdepIdList.size(); i++)
			{
				String ptdepId = (String) ptdepIdList.get(i);
				if (oldPtdepIdList.indexOf(ptdepId) >= 0)
				{
					continue;
				}
				ud = new UserDep();
				ud.setUserid(userId);
				ud.setDepid(ptdepId);
				ud.setLoginname(user.getLoginname());
				ud.setRelatetype("2");//关系类型 1.所属组 2.兼职部门
				ud.setCreater(userpid);
				ud.setCreatetime(currentTime);
				ud.setLastmodifier(userpid);
				ud.setLastmodifytime(currentTime);
				udList.add(ud);
				didList.add(ptdepId);
			}
		}
		try
		{
			if (udList != null && udList.size() > 0)
			{
				for (int i = 0; i < udList.size(); i++)
				{
					userDepDao.save(udList.get(i));
				}
				result = true;
				List<String> uidList = new ArrayList<String>();
				uidList.add(userId);
				int synchResult = synchDataDown.synchDepUserAdd(didList, uidList, systemMark);
				if (synchResult > 0)
				{
					RecordLog.printLog("add group user success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				}
			}
			else
			{
				result = true;
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}

	public boolean addUserRole(String userId, List roleIdList)
	{
		return this.addUserRole(userId, roleIdList, "");
	}

	public boolean addUserRole(String userId, List roleIdList, String systemMark)
	{
		return roleManagerService.addUserRole(userId, roleIdList, systemMark);
	}

	public boolean deleteUserByID(String userId)
	{
		return this.deleteUserByID(userId, "");
	}

	public boolean deleteUserByID(String userId, String systemMark)
	{
		boolean result = false;
		if ("".equals(StringUtils.checkNullString(userId)))
		{
			return result;
		}
		try
		{
			UserInfo user = this.getUserByID(userId);
			if (user == null)
				return result;
			String isSynchToRemedy = PropertiesUtils.getProperty("eoms.isSynchToRemedy");
			boolean isSuccess = true;
			if ("true".equals(isSynchToRemedy)) // 如果同步到remedy
				isSuccess = remedyUserManager.deleteUser(this.getLoginNameByPid(userId));
			if (isSuccess) // 同步remedy成功
			{
				//清除人员和部门的关系
				userDepDao.executeUpdate("delete UserDep where userId = ? ", new Object[] { userId });
				//清除人员和角色的关系
				roleOrgDao.executeUpdate("delete RoleOrg where orgId = ? and orgtype = 1", new Object[] { userId });
				//删除人员记录
				String code = StringUtils.checkNullString(user.getImage());
				if (!"".equals(code))
					attachmentManagerService.deleteAttachmentByCode(code);
				if (Constants.PWD_MANAGE)//萨班斯密码管理
				{
					pwdManageDao.executeUpdate(" delete PwdManage where loginname = ?", new Object[] { this.getLoginNameByPid(userId) });
				}
				String loginName = user.getLoginname();
				userManagerDao.removeById(userId);
				result = true;
				int synchResult = synchDataDown.synchUserDel(userId, loginName, systemMark);
				if (synchResult > 0)
				{
					RecordLog.printLog("delete user success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				}
				else
				{
					List<String> userIdList = new ArrayList<String>();
					userIdList.add(userId);
					int udResult = synchDataDown.synchDepUserDel(userIdList, "1", systemMark); //删除人员部门关系
					if (udResult > 0)
					{
						RecordLog.printLog("delete user success, synch delete user success, but delete the groupuser failure!", RecordLog.LOG_LEVEL_ERROR);
					}
					int urResult = synchDataDown.synchRoleOrgDel(userIdList, "1", systemMark); //删除人员角色关系
					if (urResult > 0)
					{
						RecordLog.printLog("delete user success, synch delete user success, but delete the roleuser failure!", RecordLog.LOG_LEVEL_ERROR);
					}
				}
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}

	public boolean deleteUserByID(List userIdList)
	{
		if (userIdList != null && userIdList.size() > 0)
		{
			for (int i = 0; i < userIdList.size(); i++)
			{
				if (!this.deleteUserByID((String) userIdList.get(i)))
					return false;
			}
		}
		return true;
	}

	public boolean deleteUserDep(String userId, List depIdList)
	{
		return this.deleteUserDep(userId, depIdList, "");
	}

	public boolean deleteUserDep(String userId, List depIdList, String systemMark)
	{
		boolean result = false;
		if ("".equals(StringUtils.checkNullString(userId)))
		{
			return result;
		}
		depIdList = UltraSmUtil.removeNullData(depIdList);
		if (depIdList != null && depIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(depIdList);
				Object[] obj = ArrayTransferUtils.copyArraySimple(new Object[] { userId }, (Object[]) map.get("obj"));
				userDepDao.executeUpdate("delete UserDep where userId = ? and depId in ( " + map.get("?") + " )", obj);
				result = true;
				List<String> userIdList = new ArrayList<String>();
				userIdList.add(userId);
				int synchResult = synchDataDown.synchDepUserDel(depIdList, userIdList, systemMark);
				if (synchResult > 0)
				{
					RecordLog.printLog("del group user success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				}
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		else
		{
			result = true;
		}
		return result;
	}

	public boolean deleteUserRole(String userId, List roleIdList)
	{
		return this.deleteUserRole(userId, roleIdList, "");
	}

	public boolean deleteUserRole(String userId, List roleIdList, String systemMark)
	{
		return roleManagerService.deleteUserRole(userId, roleIdList, systemMark);
	}

	private boolean updateUserInfo(UserInfo user, List groupIdList, List ptdepIdList, List roleIdList, List delGroupIdList, List delPtdepIdList, List delRoleIdList)
	{
		String userId = this.updateUserInfo(user);
		if ("".equals(StringUtils.checkNullString(userId)))
		{
			return false;
		}
		return this.updateUserRelation(userId, groupIdList, ptdepIdList, roleIdList, delGroupIdList, delPtdepIdList, delRoleIdList);
	}

	public String updateUserInfo(UserInfo user)
	{
		return this.updateUserInfo(user, "");
	}

	public String updateUserInfo(UserInfo user, String systemMark)
	{
		if (user == null)
		{
			return null;
		}
		String userId = "";
		try
		{
			//			String image = user.getImage();
			//			if(!"".equals(StringUtils.checkNullString(image)))
			//			{
			//				List<Attachment> attList = attachmentManagerService.getAttachmentByRelation(image);
			//				if(attList != null && attList.size() > 0)
			//					user.setImage(attList.get(0).getRealname());
			//			}
			List mapList = depJdbcDao.find(" select pwd, fullname from bs_t_sm_user where pid = ? ", new Object[] { user.getPid() });
			String oldPwd = "";
			String oldFullName = "";
			if (mapList != null && mapList.size() > 0)
			{
				Map map = (Map) mapList.get(0);
				oldPwd = (String) map.get("pwd");
				oldFullName = (String) map.get("fullname");
			}
			//当密码和全名都相等时 不需要同步到remedy 否则要同步到remedy
			String isSynchToRemedy = PropertiesUtils.getProperty("eoms.isSynchToRemedy");
			boolean isSuccess = true;
			if (oldPwd.equals(user.getPwd()) && oldFullName.equals(user.getFullname())) // 如果密码和全名都没有修改 则不同步remedy
				isSynchToRemedy = "false";
			if ("true".equals(isSynchToRemedy)) // 如果同步到remedy
				isSuccess = remedyUserManager.updateUser(user.getLoginname(), this.decodePwd(user.getPwd()), user.getFullname());
			if (isSuccess) // 同步remedy成功
			{
				if (!oldPwd.equals(user.getPwd()) && Constants.PWD_MANAGE)
				{
					this.saveUserPwd(user.getLoginname(), this.decodePwd(user.getPwd()), false);
				}
				user.setPyname(PinYinUtils.getPinYin(user.getFullname(), "3"));
				userManagerDao.saveOrUpdate(user);
				userId = user.getPid();
			}
			if (!"".equals(userId))
			{
				int synchResult = synchDataDown.synchUserEdit(user, systemMark);
				if (synchResult > 0)
				{
					RecordLog.printLog("update userinfo success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				}
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return userId;
	}

	private boolean updateUserRelation(String userId, List groupIdList, List ptdepIdList, List roleIdList, List delGroupIdList, List delPtdepIdList, List delRoleIdList)
	{
		if ("".equals(StringUtils.checkNullString(userId)))
		{
			return false;
		}
		return this.addUserRelation(userId, groupIdList, ptdepIdList, roleIdList) && this.deleteUserDep(userId, delGroupIdList) && this.deleteUserDep(userId, delPtdepIdList) && this.deleteUserRole(userId, delRoleIdList);
	}

	public boolean updateUserPwd(String userId, String pwd)
	{
		return this.updateUserPwd(userId, pwd, "");
	}

	public boolean updateUserPwd(String userId, String pwd, String systemMark)
	{
		boolean result = false;
		if ("".equals(StringUtils.checkNullString(userId)))
		{
			return result;
		}
		UserInfo user = this.getUserByID(userId);
		if (user == null)
			return result;
		pwd = StringUtils.checkNullString(pwd);
		String isSynchToRemedy = PropertiesUtils.getProperty("eoms.isSynchToRemedy");
		boolean isSuccess = true;
		if ("true".equals(isSynchToRemedy)) // 如果同步到remedy
			isSuccess = remedyUserManager.updateUser(user.getLoginname(), pwd, user.getFullname());
		if (isSuccess) // 同步remedy成功
		{
			if (Constants.PWD_MANAGE)
			{
				this.saveUserPwd(this.getLoginNameByPid(userId), pwd, false);
			}
			//加密密码
			CryptUtils crypt = CryptUtils.getInstance();
			pwd = crypt.encode(pwd);
			String userpid = "";
			if ("".equals(StringUtils.checkNullString(systemMark)))
			{
				UserSession userSession = (UserSession) ActionContext.getRequest().getSession().getAttribute("userSession");
				if (userSession != null)
				{
					userpid = userSession.getPid();
				}
				else
				{
					userpid = this.getPidByLoginName(UltraSmUtil.MANAGER);
				}
			}
			long currentTime = TimeUtils.getCurrentTime();
			user.setPwd(pwd);
			user.setLastmodifier(userpid);
			user.setLastmodifytime(currentTime);
			try
			{
				userManagerDao.saveOrUpdate(user);
				result = true;
				int synchResult = synchDataDown.synchUserPwdEdit(userId, pwd, systemMark);
				if (synchResult > 0)
				{
					RecordLog.printLog("update the password success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
				}
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean setUserDisable(List userIdList)
	{
		boolean result = false;
		userIdList = UltraSmUtil.removeNullData(userIdList);
		if (userIdList != null && userIdList.size() > 0)
		{
			try
			{
				List<UserInfo> userList = this.getUserByID(userIdList);
				UserInfo user;
				int i = 0;
				for (i = 0; i < userList.size(); i++)
				{
					user = userList.get(i);
					if (user != null)
					{
						user.setStatus((long) 0);
						userManagerDao.saveOrUpdate(user);
						if (synchDataDown.synchUserEdit(user, "") > 0)
						{
							RecordLog.printLog("user:" + user.getLoginname() + " disabled success, synch failure!", RecordLog.LOG_LEVEL_INFO);
						}
					}
				}
				result = true;
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		else
		{
			result = true;
		}
		return result;
	}

	public boolean setUserEnabled(List userIdList)
	{
		boolean result = false;
		userIdList = UltraSmUtil.removeNullData(userIdList);
		if (userIdList != null && userIdList.size() > 0)
		{
			try
			{
				List<UserInfo> userList = this.getUserByID(userIdList);
				UserInfo user;
				int i = 0;
				for (i = 0; i < userList.size(); i++)
				{
					user = userList.get(i);
					if (user != null)
					{
						user.setStatus((long) 1);
						userManagerDao.saveOrUpdate(user);
						if (synchDataDown.synchUserEdit(user, "") > 0)
						{
							RecordLog.printLog("user:" + user.getLoginname() + " enabled success, synch failure!", RecordLog.LOG_LEVEL_INFO);
						}
					}
				}
				result = true;
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		else
		{
			result = true;
		}
		return result;
	}

	public UserInfo getUserByLoginName(String loginName, boolean onlyUser)
	{
		if ("".equals(StringUtils.checkNullString(loginName)))
			return null;
		List<UserInfo> userList = userManagerDao.find(" from UserInfo where loginname = ?", new Object[] { loginName });
		UserInfo user = null;
		if (userList != null && userList.size() > 0)
		{
			user = userList.get(0);
			if (onlyUser)
			{
				DepInfo dep = depManagerService.getCompanyByDepid(user.getDepid());
				if (dep != null)
				{
					user.setCompanyId(dep.getPid());
					user.setCompanyName(dep.getDepfullname());
				}
				DepInfo cdbdep = depManagerService.getDepByID(user.getDepid());
				user.setCdbInfo(cdbdep);//设置国开行的单位和部门名称
				//查询国开行的团队
				//42e7ce4521e1430882bc060c7c5313de,ce8b031916f649dd8a2f8b397a747ac5
				String groupIds[] = user.getGroupid().split(",");
				List<DepInfo> depInfos = null;
				List<DepInfo> depInfosRes = new ArrayList<DepInfo>();
				for(String groupId:groupIds){
					depInfos = depInfoDao.find(" from DepInfo d where d.pid = ? and d.deptype = 4 ", new String[] { groupId });
					depInfosRes.addAll(depInfos);
				}
				DepInfo depInfo = null;
				if(depInfosRes.size()>0){
					depInfo = depInfosRes.get(0);
				}
				if(!"".equals(depInfo) && depInfo!=null){
					user.setCdbTeamId(depInfo.getPid());
					user.setCdbTeamName(depInfo.getDepname());
				}
			}
		}
		user.setGroupDepNames(noticeViewLogAction.getDgsIncludeParent(user.getGroupid(), user.getDepid()));
		return user;
	}

	public UserInfo getUserByLoginName(String loginName)
	{
		return this.getUserByLoginName(loginName, true);
	}

	public List<UserInfo> getUserByLoginName(List<String> lnList)
	{
		List<UserInfo> userList = null;
		lnList = UltraSmUtil.removeNullData(lnList);
		if (lnList != null && lnList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(lnList);
				userList = userManagerDao.find(" from UserInfo where loginname in (" + map.get("?") + ")", (Object[]) map.get("obj"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return userList;
	}

	public UserInfo getUserByID(String userId)
	{
		if ("".equals(StringUtils.checkNullString(userId)))
		{
			return null;
		}
		return userManagerDao.get(userId);
	}

	public List<UserInfo> getUserByID(List userIdList)
	{
		//		PageLimit pageLimit = PageLimit.getInstance();
		List<UserInfo> userList = null;
		userIdList = UltraSmUtil.removeNullData(userIdList);
		if (userIdList != null && userIdList.size() > 0)
		{
			Map map = UltraSmUtil.getSqlParameter(userIdList);
			//			userList = userManagerDao.pagedQuery(" from UserInfo where pid in (" + map.get("?") + ")", pageLimit, (Object[]) map.get("obj"));
			userList = userManagerDao.find(" from UserInfo where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
		}
		return userList;
	}

	public List<RoleInfo> getRoleByUserID(String userId)
	{
		if ("".equals(StringUtils.checkNullString(userId)))
		{
			return null;
		}
		return roleManagerService.getRoleByUserID(userId);
	}

	public List<RoleOrgShow> getRoleOrgByUserID(String userId)
	{
		if ("".equals(StringUtils.checkNullString(userId)))
		{
			return null;
		}
		List<RoleOrgShow> roShowList = new ArrayList();
		try
		{
//			List<String> depIdList = depManagerService.getDepIdByUser(userId, null);
			List<String> depIdList = depManagerService.getAllParentIdListByUserId(userId);
			Object[] obj = { userId };
			StringBuffer sql = new StringBuffer();
			sql.append("select distinct ro.roleid, r.rolename, r.definetype, ro.orgtype, ro.orgid");
			sql.append("  from bs_t_sm_role r, bs_t_sm_roleorg ro");
			sql.append(" where ro.roleid = r.pid and ((ro.orgid = ? and ro.orgtype = 1)");
			if (depIdList != null && depIdList.size() > 0)
			{
				Map map = UltraSmUtil.getSqlParameter(depIdList);
				sql.append(" or (ro.orgid in (");
				sql.append(map.get("?"));
				sql.append(") and ro.orgtype = 2)");
				obj = ArrayTransferUtils.copyArraySimple(obj, (Object[]) map.get("obj"));
			}
			sql.append(") order by ro.orgtype, r.rolename");
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql.toString(), obj);
			if (table != null && table.length() > 0)
			{
				RoleOrgShow roShow = null;
				DataRow row = null;
				for (int i = 0; i < table.length(); i++)
				{
					row = table.getDataRow(i);
					roShow = new RoleOrgShow();
					String orgtype = StringUtils.checkNullString(row.getString("orgtype"));
					String roleId = StringUtils.checkNullString(row.getString("roleid"));
					if ("2".equals(orgtype))//当组织类型等于2时 即部门时 则将角色Id拼装一下 因为前台的部门角色是不能通过人员信息维护时删除的 所以将部门类型的角色ID修改不影响系统操作
						roleId = roleId + "_" + orgtype;
					roShow.setRoleid(roleId);
					orgtype = dicManagerService.getTextByValue("orgtype", orgtype);
					roShow.setRolename(StringUtils.checkNullString(row.getString("rolename")));
					roShow.setOrgtype(orgtype);
					roShow.setOrgid(StringUtils.checkNullString(row.getString("orgid")));
					roShow.setRoletype(StringUtils.checkNullString(row.getString("definetype")));
					roShowList.add(roShow);
				}
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return roShowList;
	}

	public String encodeUserPwd(String pwd)
	{
		pwd = StringUtils.checkNullString(pwd);
		CryptUtils crypt = CryptUtils.getInstance();
		return crypt.encode(pwd);
	}

	public String decodePwd(String pwd)
	{
		pwd = StringUtils.checkNullString(pwd);
		CryptUtils crypt = CryptUtils.getInstance();
		return crypt.decode(pwd);
	}

	public List<String> getMobileListByUserId(List<String> userIdList)
	{
		List<String> mobileLst = new ArrayList<String>();
		if (userIdList == null || userIdList.size() == 0)
		{
			return mobileLst;
		}
		Map map = UltraSmUtil.getSqlParameter(userIdList);
		List<UserInfo> userList = (List<UserInfo>) userManagerDao.find("from UserInfo where pid in(" + map.get("?") + ")", (Object[]) map.get("obj"));
		if (userList != null)
		{
			for (int i = 0; i < userList.size(); i++)
			{
				mobileLst.add(userList.get(i).getMobile());
			}
		}
		return mobileLst;
	}

	public List<String> getMobileListByDepId(List<String> depIdList)
	{
		List<String> mobileLst = new ArrayList<String>();
		if (depIdList == null || depIdList.size() == 0)
		{
			return mobileLst;
		}
		Map map = UltraSmUtil.getSqlParameter(depIdList);
		String sql = "select u.mobile mobile from bs_t_sm_user u, bs_t_sm_userdep ud where ud.relatetype='1' and ud.depid in (" + map.get("?") + ") and u.pid = ud.userid";
		QueryAdapter adapter = new QueryAdapter();
		DataTable dtb = adapter.executeQuery(sql, (Object[]) map.get("obj"));
		if (dtb != null)
		{
			for (int i = 0; i < dtb.length(); i++)
			{
				DataRow dr = dtb.getDataRow(i);
				mobileLst.add(dr.getString("mobile"));
			}
		}
		return mobileLst;
	}

	public boolean canLogin(String loginName, String pwd)
	{
		boolean tag = false;
		if (!"".equals(StringUtils.checkNullString(loginName)))
		{
			pwd = StringUtils.checkNullString(pwd);
			String hql = "from UserInfo where loginname = ?";
			List<UserInfo> ulst = userManagerDao.find(hql, new Object[] { loginName });
			if (ulst != null && ulst.size() > 0)
			{
				UserInfo user = ulst.get(0);
				if (pwd.equals(decodePwd(user.getPwd())))
				{
					tag = true;
				}
			}
		}
		return tag;
	}

	public boolean saveUserPwd(String loginName, String pwd, boolean isUpdateUser)
	{
		boolean result = false;
		try
		{
			boolean pwdManage = pwdManageService.saveUserPwd(loginName, pwd);
			if (pwdManage && isUpdateUser)
			{
				UserInfo user = this.getUserByLoginName(loginName, false);
				if (user == null)
					return result;
				String isSynchToRemedy = PropertiesUtils.getProperty("eoms.isSynchToRemedy");
				boolean isSuccess = true;
				if ("true".equals(isSynchToRemedy)) // 如果同步到remedy
					isSuccess = remedyUserManager.updateUser(loginName, pwd, user.getFullname());
				if (isSuccess) // 同步remedy成功
				{
					UserSession userSession = (UserSession) ActionContext.getRequest().getSession().getAttribute("userSession");
					String userpid = "";
					if (userSession != null)
					{
						userpid = userSession.getPid();
					}
					else
					{
						userpid = this.getPidByLoginName(loginName);
					}
					long currentTime = TimeUtils.getCurrentTime();
					user.setPwd(this.encodeUserPwd(pwd));
					user.setLastmodifier(userpid);
					user.setLastmodifytime(currentTime);
					userManagerDao.saveOrUpdate(user);
					int synchResult = synchDataDown.synchUserPwdEdit(user.getPid(), pwd, "");
					if (synchResult > 0)
					{
						RecordLog.printLog("update the password success, synch failure!", RecordLog.LOG_LEVEL_ERROR);
					}
				}
			}
			result = pwdManage;
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}

	public boolean synchUserToPasm()
	{
		boolean result = false;
		try
		{
			List<UserInfo> userList = userManagerDao.find("from UserInfo", null);
			int userLen = 0;
			if (userList != null)
				userLen = userList.size();
			for (int i = 0; i < userLen; i++)
			{
				String encodePwd = userList.get(i).getPwd();
				String decodePwd = this.decodePwd(encodePwd);
				userList.get(i).setPwd(decodePwd);
			}
			result = synchInfoPasm.batchSynchAddUserInfo(userList);
			for (int i = 0; i < userLen; i++)
			{
				String decodePwd = userList.get(i).getPwd();
				String encodePwd = this.encodeUserPwd(decodePwd);
				userList.get(i).setPwd(encodePwd);
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}

	public boolean synchUserToRemedy()
	{
		boolean result = false;
		String isSynchToRemedy = StringUtils.checkNullString(PropertiesUtils.getProperty("eoms.isSynchToRemedy"));
		if ("false".equals(isSynchToRemedy))
			return result;
		try
		{
			List<UserInfo> userList = userManagerDao.find("from UserInfo where loginname <> 'Demo'", null);
			int userLen = 0;
			if (userList != null)
				userLen = userList.size();
			UserInfo user;
			System.out.println("同步用户到 remedy Start!");
			for (int i = 0; i < userLen; i++)
			{
				user = userList.get(i);
				String loginName = user.getLoginname();
				String password = this.decodePwd(user.getPwd());
				String fullName = user.getFullname();
				if (remedyUserManager.addUser(loginName, password, fullName))
					System.out.println("=====同步用户 " + loginName + " 到 remedy 成功！=====");
				else
					System.out.println("((((((((((=====同步用户 " + loginName + " 到 remedy 失败！=====))))))))))");
			}
			System.out.println("同步用户到 remedy End!");
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}

	public String saveOrUpdateUserInfo(List<UserInfo> userList)
	{
		String resultMessage = "";
		int userLen = 0;
		if (userList != null)
			userLen = userList.size();
		if (userLen > 0)
		{
			UserInfo userInfo;
			UserInfo user;
			String loginName;
			int updateNum = 0; // 更新用户的数量
			int addNum = 0; //添加用户的数量
			int errorNum = 0; //错误用户的数量
			for (int i = 0; i < userLen; i++)
			{
				userInfo = userList.get(i);
				if (userInfo == null)
				{
					errorNum++;
					continue;
				}
				loginName = StringUtils.checkNullString(userInfo.getLoginname());
				if ("".equals(loginName))
				{
					errorNum++;
					continue;
				}
				user = this.getUserByLoginName(loginName, false);
				if (user == null)
				{
					addNum++;
					this.addUserInfo(userInfo); // 添加用户
				}
				else
				{
					updateNum++;
					this.updateUserInfo(userInfo); //更新用户
				}
			}
			resultMessage = "新增的用户个数为：" + addNum + " ；更新的用户个数为：" + updateNum + " ；错误的用户个数为：" + errorNum;
		}
		else
		{
			resultMessage = "用户列表为空！";
		}
		return resultMessage;
	}

	public IDao<UserInfo> getUserManagerDao()
	{
		return userManagerDao;
	}

	public void setUserManagerDao(IDao<UserInfo> userManagerDao)
	{
		this.userManagerDao = userManagerDao;
	}

	public IDao<UserDep> getUserDepDao()
	{
		return userDepDao;
	}

	public void setUserDepDao(IDao<UserDep> userDepDao)
	{
		this.userDepDao = userDepDao;
	}

	public IDao<RoleOrg> getRoleOrgDao()
	{
		return roleOrgDao;
	}

	public void setRoleOrgDao(IDao<RoleOrg> roleOrgDao)
	{
		this.roleOrgDao = roleOrgDao;
	}

	public void setPwdManageDao(IDao<PwdManage> pwdManageDao)
	{
		this.pwdManageDao = pwdManageDao;
	}

	public void setDepJdbcDao(IDao<Map> depJdbcDao)
	{
		this.depJdbcDao = depJdbcDao;
	}

	public void setRoleManagerService(RoleManagerService roleManagerService)
	{
		this.roleManagerService = roleManagerService;
	}

	public void setDepManagerService(DepManagerService depManagerService)
	{
		this.depManagerService = depManagerService;
	}

	public void setAttachmentManagerService(AttachmentManagerService attachmentManagerService)
	{
		this.attachmentManagerService = attachmentManagerService;
	}

	public void setDicManagerService(DicManagerService dicManagerService)
	{
		this.dicManagerService = dicManagerService;
	}

	public void setPwdManageService(PwdManageService pwdManageService)
	{
		this.pwdManageService = pwdManageService;
	}

	public void setRemedyUserManager(IRemedyUserManager remedyUserManager)
	{
		this.remedyUserManager = remedyUserManager;
	}

	public void setSynchDataDown(SynchDataService synchDataDown)
	{
		this.synchDataDown = synchDataDown;
	}

	public void setSynchInfoPasm(EomsInfoDownPasm synchInfoPasm)
	{
		this.synchInfoPasm = synchInfoPasm;
	}
	
	public IDao<DepInfo> getDepInfoDao() {
		return depInfoDao;
	}

	public void setDepInfoDao(IDao<DepInfo> depInfoDao) {
		this.depInfoDao = depInfoDao;
	}

	public UserInfo getUserByJobNum(String loginName)
	{
		List<UserInfo> userList = userManagerDao.find(" from UserInfo where loginname = ?", new Object[] { loginName });
		UserInfo user = null;
		if (userList != null && userList.size() > 0)
		{
			user = userList.get(0);
		}
		else
		{

		}
		return user;
	}

	public Map<String, String> getUserSearchCondition(String loginName)
	{
		Map<String, String> searchCondition = new HashMap<String, String>();
		List<SearchCondition> conditionList = searchConditionDao.find("from SearchCondition where loginname=?", loginName);
		for (SearchCondition condition : conditionList)
		{
			searchCondition.put(condition.getSqlname(), condition.getConditions());
		}
		return searchCondition;
	}

	public void saveSearchConditionConfig(String loginName, String sqlname, String conditions)
	{
		List<SearchCondition> conditionList = searchConditionDao.find("from SearchCondition where loginname=? and sqlname=?", new String[] { loginName, sqlname });
		SearchCondition searchCondition = null;
		if (conditionList.size() > 0)
		{
			searchCondition = conditionList.get(0);
		}
		if (conditions != null && conditions.length() > 0)
		{
			if (searchCondition != null)
			{
				searchCondition.setConditions(conditions);
				searchConditionDao.saveOrUpdate(searchCondition);
			}
			else
			{
				searchCondition = new SearchCondition();
				searchCondition.setConditions(conditions);
				searchCondition.setLoginname(loginName);
				searchCondition.setSqlname(sqlname);
				searchCondition.setPid(UUIDGenerator.getUUID());
				searchConditionDao.save(searchCondition);
			}
		}
		else
		{
			if (searchCondition != null)
			{
				searchConditionDao.remove(searchCondition);
			}
		}

	}

	public IDao<SearchCondition> getSearchConditionDao()
	{
		return searchConditionDao;
	}

	public void setSearchConditionDao(IDao<SearchCondition> searchConditionDao)
	{
		this.searchConditionDao = searchConditionDao;
	}

	public List<UserInfo> getUsersByGroupId(String groupId) {
		return userManagerDao.find("from UserInfo t where t.groupid = ?", groupId);
	}

	/**
	 * 根据组全称查找该组下所有成员
	 */
	
	public List<UserInfo> getUsersByGroupFullname(String groupFullname) {
		
		DepInfo  depInfo = depManagerService.getDepByFullname(groupFullname);
		
		String queryUserIdsHql = "select t.userid from  UserDep t where t.depid = :pid"; 
		Query queryUserIds = userManagerDao.createQuery(queryUserIdsHql);  //查询该组下的userid
		queryUserIds.setParameter("pid", depInfo.getPid());
        List userIdList = queryUserIds.list(); 
        
        String queryUserlistByIds= " from UserInfo t where t.id in (:ids)";  
        Query queryUserInfo = userManagerDao.createQuery(queryUserlistByIds); //查询这些id所对应的UserInfo
        queryUserInfo.setParameterList("ids", userIdList);  
		return queryUserInfo.list();
	}

	@Override
	public String getUserNameByLoginName(String loginName) {
		if ("".equals(StringUtils.checkNullString(loginName)))
		{
			return "";
		}
		String userName = "";
		List<UserInfo> userList = userManagerDao.find(" from UserInfo where loginname = ?", new Object[] { loginName });
		if (userList != null && userList.size() > 0)
		{
			userName = userList.get(0).getFullname();
		}
		return userName;
	}
	public void setNoticeViewLogAction(NoticeViewLogAction noticeViewLogAction) {
		this.noticeViewLogAction = noticeViewLogAction;
	}
}

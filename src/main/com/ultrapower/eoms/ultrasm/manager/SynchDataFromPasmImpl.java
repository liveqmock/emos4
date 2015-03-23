package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.accredit.common.value.GroupUser;
import com.ultrapower.accredit.common.value.Organise;
import com.ultrapower.accredit.common.value.Role;
import com.ultrapower.accredit.common.value.User;
import com.ultrapower.accredit.common.value.UserRole;
import com.ultrapower.accredit.util.GetUserUtil;
import com.ultrapower.eoms.common.constants.ConstantsSynch;
import com.ultrapower.eoms.common.core.util.CryptUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.service.SynchDataFromPasm;
import com.ultrapower.eoms.ultrasm.service.SynchDataService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;

public class SynchDataFromPasmImpl implements SynchDataFromPasm {
	private static String LOGIN_NAME = GetUserUtil.getUsername();
	private SynchDataService synchDataUp;
	private UserManagerService userManagerService;
	private DepManagerService depManagerService;
	private RoleManagerService roleManagerService;
	
	public boolean synchUserAdd(List<User> pasmUserList, boolean isSynchRela) {
		boolean result = true;
		int userLen = 0;
		if(pasmUserList != null) {
			userLen = pasmUserList.size();
		}
		UserInfo userInfo;
		int n;
		CryptUtils crypt = CryptUtils.getInstance();
		for(int i=0 ; i<userLen ; i++) {
			userInfo = pUserToEomsUser(null, pasmUserList.get(i));
			if(userInfo == null) {
				continue;
			}
			userInfo.setPwd(crypt.encode(StringUtils.checkNullString(pasmUserList.get(i).getPass()))); // 用户明文密码
			userInfo.setGroupid(userInfo.getDepid());
			userInfo.setGroupname(userInfo.getDepname());
			userInfo.setLastlogintime(0L); // 最后登录时间 创建时默认为0
			userInfo.setCreater(LOGIN_NAME); // 创建人
			userInfo.setCreatetime(TimeUtils.getCurrentTime()); // 创建时间
			n = synchDataUp.synchUserAdd(userInfo, ConstantsSynch.SYSTEM_PASM);
			if(n == 0) {
				RecordLog.printLog("JMS receive PASM opertype:添加用户消息(user=" + userInfo.getLoginname() + ")同步成功!", RecordLog.LOG_LEVEL_INFO);
				if(isSynchRela) {
					List<String> userId = new ArrayList<String> ();
					List<String> depId = new ArrayList<String> ();
					userId.add(userInfo.getPid());
					depId.add(userInfo.getGroupid());
					n = synchDataUp.synchDepUserAdd(depId, userId, ConstantsSynch.SYSTEM_PASM);
					if(n == 0) {
						RecordLog.printLog("JMS receive PASM opertype:添加用户消息-同步组成员信息同步成功!", RecordLog.LOG_LEVEL_INFO);
					} else {
						result = false;
						RecordLog.printLog("JMS receive PASM opertype:添加用户消息-同步组成员信息同步失败!返回标识:" + n, RecordLog.LOG_LEVEL_ERROR);
					}
				}
			} else {
				result = false;
				RecordLog.printLog("JMS receive PASM opertype:添加用户消息(user=" + userInfo.getLoginname() + ")同步失败,返回标识:" + n, RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return result;
	}
	
	public boolean synchUserEdit(List<User> pasmUserList) {
		boolean result = true;
		int userLen = 0;
		if(pasmUserList != null) {
			userLen = pasmUserList.size();
		}
		UserInfo userInfo;
		User user;
		int n;
		for(int i=0 ; i<userLen ; i++) {
			user = pasmUserList.get(i);
			if(user == null) {
				continue;
			}
			userInfo = userManagerService.getUserByLoginName(user.getUserAccount(), false);
			if(userInfo == null) {
				continue;
			}
			userInfo = pUserToEomsUser(userInfo, user);
			n = synchDataUp.synchUserEdit(userInfo, ConstantsSynch.SYSTEM_PASM);
			if(n == 0) {
				RecordLog.printLog("JMS receive PASM opertype:更新用户消息(user=" + userInfo.getLoginname() + ")同步成功!", RecordLog.LOG_LEVEL_INFO);
			} else {
				result = false;
				RecordLog.printLog("JMS receive PASM opertype:更新用户消息(user=" + userInfo.getLoginname() + ")同步失败,返回标识:" + n, RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return result;
	}
	
	public boolean synchUserDel(List<String> loginNameList) {
		boolean result = true;
		int loginLen = 0;
		if(loginNameList != null) {
			loginLen = loginNameList.size();
		}
		String loginName;
		int n;
		for(int i=0 ; i<loginLen ; i++) {
			loginName = StringUtils.checkNullString(loginNameList.get(i));
			n = synchDataUp.synchUserDel("", loginName, ConstantsSynch.SYSTEM_PASM);
			if(n == 0) {
				RecordLog.printLog("JMS receive PASM opertype:删除用户消息(user=" + loginName + ")同步成功!", RecordLog.LOG_LEVEL_INFO);
			} else {
				result = false;
				RecordLog.printLog("JMS receive PASM opertype:删除用户消息(user=" + loginName + ")同步失败,返回标识:" + n, RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return result;
	}
	
	public boolean synchUserPwdEdit(List<HashMap<String, String>> mapList) {
		boolean result = true;
		int mapLen = 0;
		if(mapList != null) {
			mapLen = mapList.size();
		}
		HashMap<String, String> userMap;
		UserInfo userInfo;
		String loginName;
		String pwd;
		int n;
		CryptUtils crypt = CryptUtils.getInstance();
		for(int i=0 ; i<mapLen ; i++) {
			userMap = mapList.get(i);
			if(userMap == null) {
				continue;
			}
			for(Map.Entry<String, String> entry : userMap.entrySet()) {
				loginName = StringUtils.checkNullString(entry.getKey());
				pwd = StringUtils.checkNullString(entry.getValue());
				userInfo = userManagerService.getUserByLoginName(loginName, false);
				if(userInfo == null) {
					continue;
				}
				userInfo.setPwd(crypt.encode(pwd));
				n = synchDataUp.synchUserEdit(userInfo, ConstantsSynch.SYSTEM_PASM);
				if(n == 0) {
					RecordLog.printLog("JMS receive PASM opertype:更新用户密码消息(user=" + loginName + ")同步成功!", RecordLog.LOG_LEVEL_INFO);
				} else {
					result = false;
					RecordLog.printLog("JMS receive PASM opertype:更新用户密码消息(user=" + loginName + ")同步失败,返回标识:" + n, RecordLog.LOG_LEVEL_ERROR);
				}
			}
		}
		return result;
	}
	
	public boolean synchDepAdd(List<Organise> organiseList) {
		boolean result = true;
		int organiseLen = 0;
		if(organiseList != null) {
			organiseLen = organiseList.size();
		}
		DepInfo depInfo;
		Organise organise;
		int n;
		for(int i=0 ; i<organiseLen ; i++) {
			organise = organiseList.get(i);
			depInfo = this.pDepToEomsDep(null, organise);
			if(depInfo == null) {
				continue;
			}
			depInfo.setCreater(LOGIN_NAME); // 创建人
			depInfo.setCreatetime(TimeUtils.getCurrentTime());
			n = synchDataUp.synchDepAdd(depInfo, ConstantsSynch.SYSTEM_PASM);
			if(n == 0) {
				RecordLog.printLog("JMS receive PASM opertype:添加部门消息(dep=" + depInfo.getDepname() + ")同步成功!", RecordLog.LOG_LEVEL_INFO);
			} else {
				result = false;
				RecordLog.printLog("JMS receive PASM opertype:添加部门消息(dep=" + depInfo.getDepname() + ")同步失败!返回标识:" + n, RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return result;
	}
	
	public boolean synchDepEdit(List<Organise> organiseList) {
		boolean result = true;
		int organiseLen = 0;
		if(organiseList != null) {
			organiseLen = organiseList.size();
		}
		DepInfo depInfo = null;
		Organise organise;
		int n;
		for(int i=0 ; i<organiseLen ; i++) {
			organise = organiseList.get(i);
			if(organise == null) {
				continue;
			}
			depInfo = depManagerService.getDepByID(organise.getDept_id());
			if(depInfo == null) {
				continue;
			}
			depInfo = this.pDepToEomsDep(depInfo, organise);
			n = synchDataUp.synchDepEdit(depInfo, ConstantsSynch.SYSTEM_PASM);
			if(n == 0) {
				RecordLog.printLog("JMS receive PASM opertype:更新部门消息(dep=" + depInfo.getDepname() + ")同步成功!", RecordLog.LOG_LEVEL_INFO);
			} else {
				result = false;
				RecordLog.printLog("JMS receive PASM opertype:更新部门消息(dep=" + depInfo.getDepname() + ")同步失败!返回标识:" + n, RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return result;
	}

	public boolean synchDepDel(List<String> orgIdList) {
		boolean result = true;
		int orgIdLen = 0;
		if(orgIdList != null) {
			orgIdLen = orgIdList.size();
		}
		String orgId;
		int n;
		for(int i=0 ; i<orgIdLen ; i++) {
			orgId = StringUtils.checkNullString(orgIdList.get(i));
			n = synchDataUp.synchDepDel(orgId, ConstantsSynch.SYSTEM_PASM);
			if(n == 0) {
				RecordLog.printLog("JMS receive PASM opertype:删除部门消息(dep=" + orgId + ")同步成功!", RecordLog.LOG_LEVEL_INFO);
			} else {
				result = false;
				RecordLog.printLog("JMS receive PASM opertype:删除部门消息(dep=" + orgId + ")同步失败!返回标识:" + n, RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return result;
	}
	
	/**
	 * 同步组成员信息添加
	 * @param groupUserList
	 * @return
	 */
	public boolean synchDepUserUpdate(List<GroupUser> groupUserList) {
		boolean result = true;
		int groupUserLen = 0;
		if(groupUserList != null) {
			groupUserLen = groupUserList.size();
		}
		List<String> userId = new ArrayList<String> ();
		List<String> depId = new ArrayList<String> ();
		GroupUser groupUser;
		UserInfo userInfo;
		for(int i=0 ; i<groupUserLen ; i++) {
			groupUser = groupUserList.get(i);
			if(groupUser == null) {
				continue;
			}
			userInfo = userManagerService.getUserByLoginName(groupUser.getUserAccount(), false);
			if(userInfo == null) {
				continue;
			}
			if(!"".equals(StringUtils.checkNullString(groupUser.getDept_id())) && depId.indexOf(groupUser.getDept_id()) < 0) {
				depId.add(groupUser.getDept_id());
			}
			if(userId.indexOf(userInfo.getPid()) < 0) {
				userId.add(userInfo.getPid());
			}
		}
		if(groupUserLen > 0) {
//			int n_del = synchDataUp.synchDepUserDel(userId, "1", ConstantsSynch.SYSTEM_PASM);
			int n_del = this.synchDepUserDelByDepSame(userId, ConstantsSynch.SYSTEM_PASM);
			int n_add = synchDataUp.synchDepUserAdd(depId, userId, ConstantsSynch.SYSTEM_PASM);
			if(n_del + n_add == 0) {
				RecordLog.printLog("JMS receive PASM opertype:更新部门消息-同步组成员信息同步成功!", RecordLog.LOG_LEVEL_INFO);
			} else {
				result = false;
				RecordLog.printLog("JMS receive PASM opertype:更新部门消息-同步组成员信息同步失败!返回标识:del:" + n_del + "-add:" + n_add, RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return result;
	}

	/**
	 * 只删除原user部门相同的部门(因同步时会删除所有人与与组的关联，替换为此类以规避该问题)
	 * @param userId
	 * @return
	 */
	public int synchDepUserDelByDepSame(List<String> userId, String systemMark) {
		int result = 0; // 0:同步成功 1:用户登录名已存在 2:此人不存在 3:登录名为空 8:参数为空 9:同步失败
		int userLen = 0;
		if(userId != null) {
			userLen = userId.size();
		}
		if(userLen > 0) {
			for(int i=0 ; i<userLen ; i++) {
//				UserInfo userInfo = userManagerService.getUserByID(userId.get(i));
//				List<String> depId = new ArrayList<String>();
//				if(userInfo != null && !"".equals(StringUtils.checkNullString(userInfo.getDepid()))){
//					depId.add(userInfo.getDepid());
//				}
//				RecordLog.printLog("delete === USERID:"+userId.get(i)+";depId:"+depId);
//				if(!userManagerService.deleteUserDep(userId.get(i), depId, systemMark)) {
//					result = 9;
//				}
				//原逻辑，只删除了userdep表，未处理user表的groupid和depid
				UserInfo userInfo = userManagerService.getUserByID(userId.get(i));
				String depId = "";
				if(userInfo != null && !"".equals(StringUtils.checkNullString(userInfo.getDepid()))){
					depId = userInfo.getDepid();
				}
				List<String> newUserId = new ArrayList<String>();
				newUserId.add(userId.get(i));
				RecordLog.printLog("delete === USERID:"+userId.get(i)+";depId:"+depId);
				if(!depManagerService.deleteDepUser(depId, newUserId, systemMark)) {
					result = 9;
				}
			}
		} else {
			result = 8;
		}
		return result;
	}

	public boolean synchRoleAdd(List<Role> roleList) {
		boolean result = true;
		int roleLen = 0;
		if(roleList != null) {
			roleLen = roleList.size();
		}
		RoleInfo roleInfo;
		Role role;
		int n;
		for(int i=0 ; i<roleLen ; i++) {
			role = roleList.get(i);
			roleInfo = this.pRoleToEomsRole(null, role);
			if(roleInfo == null) {
				continue;
			}
			roleInfo.setCreater(LOGIN_NAME);
			roleInfo.setCreatetime(TimeUtils.getCurrentTime());
			n = synchDataUp.synchRoleAdd(roleInfo, ConstantsSynch.SYSTEM_PASM);
			if(n == 0) {
				RecordLog.printLog("JMS receive PASM opertype:添加角色消息(role=" + roleInfo.getRolename() + ")同步成功!", RecordLog.LOG_LEVEL_INFO);
			} else {
				result = false;
				RecordLog.printLog("JMS receive PASM opertype:添加角色消息(role=" + roleInfo.getRolename() + ")同步失败!返回标识:" + n, RecordLog.LOG_LEVEL_INFO);
			}
		}
		return result;
	}
	
	public boolean synchRoleEdit(List<Role> roleList) {
		boolean result = true;
		int roleLen = 0;
		if(roleList != null) {
			roleLen = roleList.size();
		}
		RoleInfo roleInfo = null;
		Role role;
		int n;
		for(int i=0 ; i<roleLen ; i++) {
			role = roleList.get(i);
			if(role == null) {
				continue;
			}
			roleInfo = roleManagerService.getRoleByID(role.getId());
			if(roleInfo == null) {
				continue;
			}
			roleInfo = this.pRoleToEomsRole(roleInfo, role);
			n = synchDataUp.synchRoleEdit(roleInfo, ConstantsSynch.SYSTEM_PASM);
			if(n == 0) {
				RecordLog.printLog("JMS receive PASM opertype:更新角色消息(role=" + roleInfo.getRolename() + ")同步成功!", RecordLog.LOG_LEVEL_INFO);
			} else {
				result = false;
				RecordLog.printLog("JMS receive PASM opertype:更新角色消息(role=" + roleInfo.getRolename() + ")同步失败!返回标识:" + n, RecordLog.LOG_LEVEL_INFO);
			}
		}
		return result;
	}
	
	public boolean synchRoleDel(List<String> roleIdList) {
		boolean result = true;
		int roleLen = 0;
		if(roleIdList != null) {
			roleLen = roleIdList.size();
		}
		String roleId;
		RoleInfo roleInfo;
		int n;
		for(int i=0 ; i<roleLen ; i++) {
			roleId = roleIdList.get(i);
			roleInfo = roleManagerService.getRoleByID(roleId);
			if(roleInfo == null) {
				continue;
			}
			n = synchDataUp.synchRoleDel(roleId, ConstantsSynch.SYSTEM_PASM);
			if(n == 0) {
				RecordLog.printLog("JMS receive PASM opertype:删除角色消息(role=" + roleInfo.getRolename() + ")同步成功!", RecordLog.LOG_LEVEL_INFO);
			} else {
				result = false;
				RecordLog.printLog("JMS receive PASM opertype:删除角色消息(role=" + roleInfo.getRolename() + ")同步失败!返回标识:" + n, RecordLog.LOG_LEVEL_INFO);
			}
		}
		return result;
	}
	
	public boolean synchUserRoleRela(List<UserRole> userRoleList) {
		boolean result = true;
		int userRoleLen = 0;
		if(userRoleList != null) {
			userRoleLen = userRoleList.size();
		}
		List<String> userId = new ArrayList<String> ();
		List<String> roleId = new ArrayList<String> ();
		String u_id;
		String r_id;
		UserRole userRole;
		for(int i=0 ; i<userRoleLen ; i++) {
			userRole = userRoleList.get(i);
			u_id = StringUtils.checkNullString(userRole.getUserId());
			r_id = StringUtils.checkNullString(userRole.getRoleId());
			if(userId.indexOf(u_id) < 0) {
				userId.add(u_id);
			}
			if(roleId.indexOf(r_id) < 0) {
				roleId.add(r_id);
			}
		}
		if(userRoleLen > 0) {
			int n_del = synchDataUp.synchRoleOrgDel(roleId, userId, "1", ConstantsSynch.SYSTEM_PASM);
			int n_add = synchDataUp.synchRoleOrgAdd(roleId, userId, "1", ConstantsSynch.SYSTEM_PASM);
			if(n_del + n_add == 0) {
				RecordLog.printLog("JMS receive PASM opertype:更新角色成员消息同步成功!", RecordLog.LOG_LEVEL_INFO);
			} else {
				result = false;
				RecordLog.printLog("JMS receive PASM opertype:更新角色成员消息同步失败!返回标识:del:" + n_del + "-add:" + n_add, RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return result;
	}
	
	/**
	 * pasm用户转换eoms用户对象
	 * @param userInfo eoms用户对象
	 * @param user pasm用户对象
	 * @return
	 */
	private UserInfo pUserToEomsUser(UserInfo userInfo, User user) {
		if(user == null) {
			return userInfo;
		}
		if(userInfo == null) {
			userInfo = new UserInfo();
		}
		userInfo.setLoginname(user.getUserAccount()); // 用户登录名
		userInfo.setFullname(user.getUserName()); // 用户全名
		userInfo.setMobile(user.getMobile()); // 用户手机号
		userInfo.setPhone(user.getTelephone()); // 用户固话
		userInfo.setFax(user.getFax()); // 用户传真
		userInfo.setEmail(user.getEmail()); // 用户邮箱
		userInfo.setStatus(user.getUserStatus()==0 ? 1L : 0L); // 用户状态
		long orderby = user.getUserOrderby();
		userInfo.setOrdernum(orderby > 99999 ? 99999 : orderby); // 用户排序值
		userInfo.setDepid(user.getDeptID()); // 行政部门id
		userInfo.setDepname(user.getDeptName()); // 行政部门名称
		userInfo.setRemark(user.getNote()); // 描述信息
		userInfo.setLastmodifier(LOGIN_NAME); // 最后修改人
		userInfo.setLastmodifytime(TimeUtils.getCurrentTime()); // 最后修改时间
		return userInfo;
	}
	
	/**
	 * pasm部门转换eoms部门对象
	 * @param depInfo eoms部门对象
	 * @param organise pasm部门对象
	 * @return
	 */
	private DepInfo pDepToEomsDep(DepInfo depInfo, Organise organise) {
		if(organise == null) {
			return depInfo;
		}
		if(depInfo == null) {
			depInfo = new DepInfo();
		}
		depInfo.setPid(organise.getDept_id()); // 主键部门id
		String parentId = StringUtils.checkNullString(organise.getSuper_id());
		if("1".equals(parentId)) {
			parentId = "0";
			depInfo.setDeptype("1");
		} else {
			depInfo.setDeptype(organise.getType()==0 ? "3" : "4");
		}
		depInfo.setParentid(parentId); // 上级部门id
		depInfo.setDepname(organise.getDept_name()); // 部门名称
		String depFullName = StringUtils.checkNullString(organise.getGroup_dnname());
		depInfo.setDepfullname(depFullName.startsWith(".") ? depFullName.substring(1) : depFullName); // 部门全名 格式不统一，不同步
		depInfo.setDepfax(organise.getOrg_fax()); // 部门传真
		depInfo.setDepphone(organise.getOrg_phone()); // 部门电话
		depInfo.setStatus(organise.getOrg_status() == 0 ? 1L : 0L); // 部门状态
		long orderby = organise.getOrg_orderBy();
		depInfo.setOrdernum(orderby > 99999 ? 99999 : orderby); // 部门排序值
		depInfo.setRemark(organise.getNode()); // 部门描述
		depInfo.setLastmodifier(LOGIN_NAME); // 最后修改人
		depInfo.setLastmodifytime(TimeUtils.getCurrentTime());
		return depInfo;
	}
	
	/**
	 * pasm角色转换eoms角色对象
	 * @param roleInfo
	 * @param role
	 * @return
	 */
	private RoleInfo pRoleToEomsRole(RoleInfo roleInfo, Role role) {
		if(role == null) {
			return roleInfo;
		}
		if(roleInfo == null) {
			roleInfo = new RoleInfo();
		}
		roleInfo.setPid(role.getId());
		roleInfo.setRolename(role.getName());
		roleInfo.setParentid(role.getSuper_id());
		roleInfo.setRemark(role.getNote());
		roleInfo.setLastmodifier(LOGIN_NAME);
		roleInfo.setLastmodifytime(TimeUtils.getCurrentTime());
		return roleInfo;
	}
	
	public void setSynchDataUp(SynchDataService synchDataUp) {
		this.synchDataUp = synchDataUp;
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

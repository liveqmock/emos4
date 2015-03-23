package com.ultrapower.eoms.ultrasm.manager;

import java.util.List;

import com.ultrapower.eoms.common.core.util.CryptUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.service.SynchDataService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;

/**
 * 其他项目向eoms4同步系统管理数据接口实现
 * @author SunHailong
 */
public class SynchDataUpImpl implements SynchDataService {
	private UserManagerService userManagerService;
	private DepManagerService depManagerService;
	private RoleManagerService roleManagerService;
	
	/**
	 * 同步添加一个用户信息
	 * @param user
	 * @return
	 */
	public int synchUserAdd(final UserInfo user, String systemMark) {
		int result = 0; // 0:同步成功 1:用户登录名已存在 2:此人不存在 3:登录名为空 8:参数为空 9:同步失败
		if(user == null) {
			result = 8; // user对象为空
			return result;
		}
		String loginName = StringUtils.checkNullString(user.getLoginname());
		if("".equals(loginName)) {
			result = 3; // 用户登录名为空
			return result;
		}
		UserInfo userInfo = userManagerService.getUserByLoginName(loginName, false);
		if(userInfo != null) {
			result = 1; // 用户登录名已存在
			return result;
		}
		if("".equals(StringUtils.checkNullString(userManagerService.addUserInfo(user, systemMark)))) {
			result = 9; // 同步失败
		}
		return result;
	}
	
	/**
	 * 同步删除一个用户信息
	 * @param userId
	 * @param loginName
	 * @return
	 */
	public int synchUserDel(String userId, String loginName, String systemMark) {
		int result = 0; // 0:同步成功 1:用户登录名已存在 2:此人不存在 3:登录名为空 8:参数为空 9:同步失败
		if("".equals(StringUtils.checkNullString(userId))) {
			if(!"".equals(StringUtils.checkNullString(loginName))) {
				UserInfo user = userManagerService.getUserByLoginName(loginName, false);
				if(user != null) {
					userId = user.getPid();
				}
			}
		}
		if("".equals(StringUtils.checkNullString(userId))) {
			result = 8; // 用户标识为空
			return result;
		}
		if(!userManagerService.deleteUserByID(userId, systemMark)) {
			result = 9;
			return result;
		}
		return result;
	}
	/**
	 * 同步修改一个用户信息
	 * @param user
	 * @return
	 */
	public int synchUserEdit(final UserInfo user, String systemMark) {
		int result = 0; // 0:同步成功 1:用户登录名已存在 2:此人不存在 3:登录名为空 8:参数为空 9:同步失败
		if(user == null) {
			result = 8; // user对象为空
			return result;
		}
		if("".equals(StringUtils.checkNullString(userManagerService.updateUserInfo(user, systemMark)))) {
			result = 9; // 同步失败
		}
		return result;
	}
	/**
	 * 同步修改一个用户密码
	 * @param userId
	 * @param newPwd
	 * @return
	 */
	public int synchUserPwdEdit(String userId, String newPwd, String systemMark) {
		int result = 0; // 0:同步成功 1:用户登录名已存在 2:此人不存在 3:登录名为空 8:参数为空 9:同步失败
		UserInfo user = userManagerService.getUserByID(userId);
		if(user == null) {
			result = 2;
			return result;
		}
		if(!userManagerService.updateUserPwd(userId, newPwd, systemMark)) {
			result = 9;
			return result;
		}
		return result;
	}
	/**
	 * 同步添加一个部门信息
	 * @param dep
	 * @return
	 */
	public int synchDepAdd(DepInfo dep, String systemMark) {
		int result = 0; // 0:同步成功 1:该部门已存在 2:此人不存在 3:登录名为空 8:参数为空 9:同步失败
		if(dep == null) {
			result = 8;
			return result;
		}
		DepInfo depInfo = depManagerService.getDepByID(dep.getPid());
		if(depInfo != null) {
			result = 1;
			return result;
		}
		if("".equals(StringUtils.checkNullString(depManagerService.addDepInfo(dep, systemMark)))) {
			result = 9;
			return result;
		}
		return result;
	}
	/**
	 * 同步删除一个部门信息
	 * @param depId
	 * @return
	 */
	public int synchDepDel(String depId, String systemMark) {
		int result = 0; // 0:同步成功 1:用户登录名已存在 2:此部门不存在 3:登录名为空 8:参数为空 9:同步失败
		if("".equals(StringUtils.checkNullString(depId))) {
			result = 8;
			return result;
		}
		DepInfo dep = depManagerService.getDepByID(depId);
		if(dep == null) {
			result = 2;
			return result;
		}
		if(!depManagerService.deleteDepByID(depId, systemMark)) {
			result = 9;
			return result;
		}
		return result;
	}
	/**
	 * 同步修改一个部门信息
	 * @param dep
	 * @return
	 */
	public int synchDepEdit(DepInfo dep, String systemMark) {
		int result = 0; // 0:同步成功 1:用户登录名已存在 2:此人不存在 3:登录名为空 8:参数为空 9:同步失败
		if(dep == null) {
			result = 8;
			return result;
		}
		if("".equals(StringUtils.checkNullString(depManagerService.updateDepInfo(dep, systemMark)))) {
			result = 9;
			return result;
		}
		return result;
	}
	/**
	 * 同步添加组成员关系
	 * @param depId
	 * @param userId
	 * @return
	 */
	public int synchDepUserAdd(List<String> depId, List<String> userId, String systemMark) {
		int result = 0; // 0:同步成功 1:用户登录名已存在 2:此人不存在 3:登录名为空 8:参数为空 9:同步失败
		int depLen = 0;
		if(depId != null) {
			depLen = depId.size();
		}
		int userLen = 0;
		if(userId != null) {
			userLen = userId.size();
		}
		if(userLen > 0 && depLen > 0) {
			if(userLen < depLen) {
				for(int i=0 ; i<userLen ; i++) {
					if(!userManagerService.addUserDep(userId.get(i), depId, null, systemMark)) {
						result = 9;
					}
				}
			} else {
				for(int i=0 ; i<depLen ; i++) {
					if(!depManagerService.addDepUser(depId.get(i), userId, systemMark)) {
						result = 9;
					}
				}
			}
		} else {
			result = 8; // 参数列表为空
		}
		return result;
	}
	/**
	 * 同步删除组成员关系
	 * @param depId
	 * @param userId
	 * @return
	 */
	public int synchDepUserDel(List<String> depId, List<String> userId, String systemMark) {
		int result = 0; // 0:同步成功 1:用户登录名已存在 2:此人不存在 3:登录名为空 8:参数为空 9:同步失败
		int depLen = 0;
		if(depId != null) {
			depLen = depId.size();
		}
		int userLen = 0;
		if(userId != null) {
			userLen = userId.size();
		}
		if(userLen > 0 && depLen > 0) {
			if(userLen < depLen) {
				for(int i=0 ; i<userLen ; i++) {
					if(!userManagerService.deleteUserDep(userId.get(i), depId, systemMark)) {
						result = 9;
					}
				}
			} else {
				for(int i=0 ; i<depLen ; i++) {
					if(!depManagerService.deleteDepUser(depId.get(i), userId, systemMark)) {
						result = 9;
					}
				}
			}
		} else {
			result = 8; // 参数列表为空
		}
		return result;
	}
	/**
	 * 同步删除组成员关系
	 * @param orgId
	 * @param orgType
	 * @return
	 */
	public int synchDepUserDel(List<String> orgId, String orgType, String systemMark) {
		int result = 0; // 0:同步成功 1:用户登录名已存在 2:此人不存在 3:登录名为空 8:参数为空 9:同步失败
		if("".equals(StringUtils.checkNullString(orgType))) {
			result = 8;
		}
		int orgLen = 0;
		if(orgId != null) {
			orgLen = orgId.size();
		}
		if(orgLen > 0) {
			for(int i=0 ; i<orgLen ; i++) {
				if("1".equals(orgType)) {
					List<String> depId = depManagerService.getDepIdByUser(orgId.get(i), "1");
					if(!userManagerService.deleteUserDep(orgId.get(i), depId, systemMark)) {
						result = 9;
					}
				} else if("2".equals(orgType)) {
					List<String> userId = depManagerService.getUserIdByDepID(orgId.get(i));
					if(!depManagerService.deleteDepUser(orgId.get(i), userId, systemMark)) {
						result = 9;
					}
				}
			}
		} else {
			result = 8;
		}
		return result;
	}
	/**
	 * 同步添加一个角色信息
	 * @param role
	 * @return
	 */
	public int synchRoleAdd(RoleInfo role, String systemMark) {
		int result = 0; // 0:同步成功 1:该角色已存在 2:此人不存在 3:登录名为空 8:参数为空 9:同步失败
		if(role == null) {
			result = 8;
			return result;
		}
		RoleInfo roleInfo = roleManagerService.getRoleByID(role.getPid());
		if(roleInfo != null) {
			result = 1;
			return result;
		}
		if("".equals(StringUtils.checkNullString(roleManagerService.addRoleInfo(role, systemMark)))) {
			result = 9;
			return result;
		}
		return result;
	}
	/**
	 * 同步删除一个角色信息
	 * @param roleId
	 * @return
	 */
	public int synchRoleDel(String roleId, String systemMark) {
		int result = 0; // 0:同步成功 1:用户登录名已存在 2:此角色不存在 3:登录名为空 8:参数为空 9:同步失败
		if("".equals(StringUtils.checkNullString(roleId))) {
			result = 8;
			return result;
		}
		RoleInfo role = roleManagerService.getRoleByID(roleId);
		if(role == null) {
			result = 2;
			return result;
		}
		if(!roleManagerService.deleteRoleByID(roleId, systemMark)) {
			result = 9;
			return result;
		}
		return result;
	}
	/**
	 * 同步修改一个角色信息
	 * @param role
	 * @return
	 */
	public int synchRoleEdit(RoleInfo role, String systemMark) {
		int result = 0; // 0:同步成功 1:用户登录名已存在 2:此人不存在 3:登录名为空 8:参数为空 9:同步失败
		if(role == null) {
			result = 8;
			return result;
		}
		if("".equals(StringUtils.checkNullString(roleManagerService.updateRoleInfo(role, systemMark)))) {
			result = 9;
			return result;
		}
		return result;
	}
	/**
	 * 同步添加角色组织机构关系
	 * @param roleId
	 * @param orgId
	 * @param orgType
	 * @return
	 */
	public int synchRoleOrgAdd(List<String> roleId, List<String> orgId, String orgType, String systemMark) {
		int result = 0; // 0:同步成功 1:用户登录名已存在 2:此人不存在 3:登录名为空 8:参数为空 9:同步失败
		if("".equals(StringUtils.checkNullString(orgType))) {
			result = 8;
			return result;
		}
		int roleLen = 0;
		if(roleId != null) {
			roleLen = roleId.size();
		}
		int orgLen = 0;
		if(orgId != null) {
			orgLen = orgId.size();
		}
		if(roleLen > 0 && orgLen > 0) {
			List<String> userId = null;
			List<String> depId = null;
			if("1".equals(orgType)) {
				userId = orgId;
			} else if("".equals(orgType)) {
				depId = orgId;
			}
			if(!roleManagerService.addRoleOrg(roleId, userId, depId, systemMark)) {
				result = 9;
			}
		} else {
			result = 8;
		}
		return result;
	}
	/**
	 * 同步删除角色组织机构关系
	 * @param roleId
	 * @param orgId
	 * @param orgType
	 * @return
	 */
	public int synchRoleOrgDel(List<String> roleId, List<String> orgId, String orgType, String systemMark) {
		int result = 0; // 0:同步成功 1:用户登录名已存在 2:此人不存在 3:登录名为空 8:参数为空 9:同步失败
		if("".equals(StringUtils.checkNullString(orgType))) {
			result = 8;
			return result;
		}
		int roleLen = 0;
		if(roleId != null) {
			roleLen = roleId.size();
		}
		int orgLen = 0;
		if(orgId != null) {
			orgLen = orgId.size();
		}
		if(roleLen > 0 && orgLen > 0) {
			List<String> userId = null;
			List<String> depId = null;
			if("1".equals(orgType)) {
				userId = orgId;
			} else if("".equals(orgType)) {
				depId = orgId;
			}
			for(int i=0 ; i<roleLen ; i++) {
				if(!roleManagerService.deleteRoleOrg(roleId.get(i), userId, depId, systemMark)) {
					result = 9;
				}
			}
		} else {
			result = 8;
		}
		return result;
	}
	/**
	 * 同步删除角色组织机构关系
	 * @param objId
	 * @param objType 0:角色 1:人员 2:部门
	 * @return
	 */
	public int synchRoleOrgDel(List<String> objId, String objType, String systemMark) {
		int result = 0; // 0:同步成功 1:用户登录名已存在 2:此人不存在 3:登录名为空 8:参数为空 9:同步失败
		if("".equals(StringUtils.checkNullString(objType))) {
			result = 8;
			return result;
		}
		int objLen = 0;
		if(objId != null) {
			objLen = objId.size();
		}
		if(objLen > 0) {
			if("0".equals(objType)) {
				for(int i=0 ; i<objLen ; i++) {
					List<String> userId = roleManagerService.getOrgIdByRoleID(objId.get(i), 1);
					List<String> depId = roleManagerService.getOrgIdByRoleID(objId.get(i), 2);
					if(!roleManagerService.deleteRoleOrg(objId.get(i), userId, depId, systemMark)) {
						result = 9;
					}
				}
			} else if("1".equals(objType)) {
				for(int i=0 ; i<objLen ; i++) {
					List<String> roleId = roleManagerService.getRoleIdByOrgId(objId.get(i), 1);
					if(!userManagerService.deleteUserRole(objId.get(i), roleId, systemMark)) {
						result = 9;
					}
				}
			} else if("2".equals(objType)) {
				for(int i=0 ; i<objLen ; i++) {
					List<String> roleId = roleManagerService.getRoleIdByOrgId(objId.get(i), 2);
					if(!depManagerService.deleteDepRole(objId.get(i), roleId, systemMark)) {
						result = 9;
					}
				}
			}
		} else {
			result = 8;
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

package com.ultrapower.eoms.ultrasm.service;

import java.util.HashMap;
import java.util.List;

import com.ultrapower.accredit.common.value.GroupUser;
import com.ultrapower.accredit.common.value.Organise;
import com.ultrapower.accredit.common.value.Role;
import com.ultrapower.accredit.common.value.User;
import com.ultrapower.accredit.common.value.UserRole;

/**
 * 数据同步接口（pasm - eoms）
 * @author SunHailong
 */
public interface SynchDataFromPasm {
	
	/**
	 * 同步添加一组用户
	 * @param pasmUserList pasm用户List
	 * @param isSynchRela 是否同步组成员关系
	 * @return
	 */
	public boolean synchUserAdd(List<User> pasmUserList, boolean isSynchRela);
	
	/**
	 * 同步更新一组用户
	 * @param pasmUserList pasm用户List
	 * @return
	 */
	public boolean synchUserEdit(List<User> pasmUserList);
	
	/**
	 * 同步删除一组用户
	 * @param loginNameList pasm用户登录名List
	 * @return
	 */
	public boolean synchUserDel(List<String> loginNameList);
	
	/**
	 * 同步更新一组用户密码
	 * @param mapList Map中的 key是用户账号；value是新密码
	 * @return
	 */
	public boolean synchUserPwdEdit(List<HashMap<String, String>> mapList);
	
	/**
	 * 同步添加组织机构(用户组)
	 * @param organiseList pasm部门List
	 * @return
	 */
	public boolean synchDepAdd(List<Organise> organiseList);
	
	/**
	 * 同步修改组织机构(用户组)
	 * @param organiseList pasm部门List
	 * @return
	 */
	public boolean synchDepEdit(List<Organise> organiseList);
	
	/**
	 * 同步删除组织机构(用户组)
	 * @param orgIdList pasm部门idList
	 * @return
	 */
	public boolean synchDepDel(List<String> orgIdList);
	
	/**
	 * 同步组成员信息更新
	 * @param groupUserList
	 * @return
	 */
	public boolean synchDepUserUpdate(List<GroupUser> groupUserList);
	
	/**
	 * 同步添加角色信息
	 * @param roleList 角色信息列表
	 * @return
	 */
	public boolean synchRoleAdd(List<Role> roleList);
	
	/**
	 * 同步修改角色信息
	 * @param roleList 角色信息列表
	 * @return
	 */
	public boolean synchRoleEdit(List<Role> roleList);
	
	/**
	 * 同步删除角色信息
	 * @param roleIdList 角色idList
	 * @return
	 */
	public boolean synchRoleDel(List<String> roleIdList);
	
	public boolean synchUserRoleRela(List<UserRole> userRoleList);
}

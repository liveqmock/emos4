package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;

public interface SynchDataToPasm
{
	/**
	 * 同步添加一个用户信息
	 * @param user
	 * @return
	 */
	public int synchUserAdd(UserInfo user);
	/**
	 * 同步删除一个用户信息
	 * @param loginName
	 * @return
	 */
	public int synchUserDel(String loginName);
	/**
	 * 同步修改一个用户信息
	 * @param user
	 * @return
	 */
	public int synchUserEdit(UserInfo user);
	/**
	 * 同步修改一个用户密码
	 * @param userId
	 * @param newPwd
	 * @return
	 */
	public int synchUserPwdEdit(String userId, String newPwd);
	/**
	 * 同步添加一个部门信息
	 * @param dep
	 * @return
	 */
	public int synchDepAdd(DepInfo dep);
	/**
	 * 同步删除一个部门信息
	 * @param depId
	 * @return
	 */
	public int synchDepDel(String depId);
	/**
	 * 同步修改一个部门信息
	 * @param dep
	 * @return
	 */
	public int synchDepEdit(DepInfo dep);
	/**
	 * 同步添加组成员关系
	 * @param depIdList
	 * @param userIdList
	 * @return
	 */
	public int synchDepUserAdd(List<String> depIdList, List<String> userIdList);
	/**
	 * 同步删除组成员关系
	 * @param depIdList
	 * @param userIdList
	 * @return
	 */
	public int synchDepUserDel(List<String> depIdList, List<String> userIdList);
	/**
	 * 同步删除组成员关系
	 * @param orgIdList
	 * @param orgType 1:人员 2:部门
	 * @return
	 */
	public int synchDepUserDel(List<String> orgIdList, String orgType);
	/**
	 * 同步添加一个角色信息
	 * @param role
	 * @return
	 */
	public int synchRoleAdd(RoleInfo role);
	/**
	 * 同步删除一个角色信息
	 * @param roleId
	 * @return
	 */
	public int synchRoleDel(String roleId);
	/**
	 * 同步修改一个角色信息
	 * @param role
	 * @return
	 */
	public int synchRoleEdit(RoleInfo role);
	/**
	 * 同步添加角色组织机构关系
	 * @param roleIdList
	 * @param orgIdList
	 * @param orgType
	 * @return
	 */
	public int synchRoleOrgAdd(List<String> roleIdList, List<String> orgIdList, String orgType);
	/**
	 * 同步删除角色组织机构关系
	 * @param roleIdList
	 * @param orgIdList
	 * @param orgType
	 * @return
	 */
	public int synchRoleOrgDel(List<String> roleIdList, List<String> orgIdList, String orgType);
	/**
	 * 同步删除角色组织机构关系
	 * @param objIdList
	 * @param objType 0:角色 1:人员 2:部门
	 * @return
	 */
	public int synchRoleOrgDel(List<String> objIdList, String objType);
}

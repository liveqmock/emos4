package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;

/**
 * 同步数据公共接口类
 * @author SunHailong
 */
public interface SynchDataService
{
	/**
	 * 同步添加一个用户信息
	 * @param user 用户对象
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchUserAdd(UserInfo user, String systemMark);
	/**
	 * 同步删除一个用户信息
	 * @param userId 用户id
	 * @param loginName 用户登录名
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchUserDel(String userId, String loginName, String systemMark);
	/**
	 * 同步修改一个用户信息
	 * @param user 用户对象
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchUserEdit(UserInfo user, String systemMark);
	/**
	 * 同步修改一个用户密码
	 * @param userId 用户id
	 * @param newPwd 用户新密码
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchUserPwdEdit(String userId, String newPwd, String systemMark);
	/**
	 * 同步添加一个部门信息
	 * @param dep 部门对象
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchDepAdd(DepInfo dep, String systemMark);
	/**
	 * 同步删除一个部门信息
	 * @param depId 部门id
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchDepDel(String depId, String systemMark);
	/**
	 * 同步修改一个部门信息
	 * @param dep 部门对象
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchDepEdit(DepInfo dep, String systemMark);
	/**
	 * 同步添加组成员关系
	 * @param depId 部门id
	 * @param userId 用户id
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchDepUserAdd(List<String> depId, List<String> userId, String systemMark);
	/**
	 * 同步删除组成员关系
	 * @param depId 部门id
	 * @param userId 用户id
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchDepUserDel(List<String> depId, List<String> userId, String systemMark);
	/**
	 * 同步删除组成员关系
	 * @param orgId 组织id
	 * @param orgType 组织类型 1.用户 2.部门
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchDepUserDel(List<String> orgId, String orgType, String systemMark);
	/**
	 * 同步添加一个角色信息
	 * @param role 角色对象
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchRoleAdd(RoleInfo role, String systemMark);
	/**
	 * 同步删除一个角色信息
	 * @param roleId 角色id
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchRoleDel(String roleId, String systemMark);
	/**
	 * 同步修改一个角色信息
	 * @param role 角色对象
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchRoleEdit(RoleInfo role, String systemMark);
	/**
	 * 同步添加角色组织机构关系
	 * @param roleId 角色id
	 * @param orgId 组织id
	 * @param orgType 组织类型 1.用户 2.部门
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchRoleOrgAdd(List<String> roleId, List<String> orgId, String orgType, String systemMark);
	/**
	 * 同步删除角色组织机构关系
	 * @param roleId 角色id
	 * @param orgId 组织id
	 * @param orgType 组织类型 1.用户 2.部门
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchRoleOrgDel(List<String> roleId, List<String> orgId, String orgType, String systemMark);
	/**
	 * 同步删除角色组织机构关系
	 * @param objId 组织id
	 * @param objType 0:角色 1.人员 2.部门
	 * @param systemMark 系统来源标识
	 * @return
	 */
	public int synchRoleOrgDel(List<String> objId, String objType, String systemMark);
}

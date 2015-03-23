package com.ultrapower.eoms.common.external.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.UserDep;
import com.ultrapower.eoms.ultrasm.model.UserInfo;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-12-29 上午10:32:43
 * @descibe 应用系统信息下行同步到pasm系统服务提供者
 */
public interface EomsInfoDownPasm {

	/**
	 * 同步添加用户信息
	 * @param p_UserInfo 用户信息
	 * @param p_DepId 组id
	 * @return
	 */
	public boolean  synchAddUserInfo(UserInfo p_UserInfo,List<String> p_DepId);
	
	/**
	 * 同步更新用户信息
	 * @param p_UserInfo 用户信息
	 * @param p_DelDepId 要删除的部门id集合
	 * @param p_AddDepId 要添加部门的id集合
	 * @return
	 */
	public boolean  synchUpdateUserInfo(UserInfo p_UserInfo,List<String> p_DelDepId,List<String> p_AddDepId);
	
	/**
	 * 同步删除用户信息
	 * @param p_UserInfo 用户信息
	 * @param p_DepId 组id
	 * @return
	 */
	public boolean  synchDelUserInfo(UserInfo p_UserInfo,List<String> p_DepId);
	
	/**
	 * 同步添加部门信息
	 * @param p_DepInfo 部门信息
	 * @param p_UserInfo 用户登录名
	 * @return
	 */
	public boolean synchAddDepInfo(DepInfo p_DepInfo,List<String> p_UserInfo);
	
	/**
	 * 同步更新部门信息
	 * @param p_DepInfo 部门信息
	 * @param p_UserInfo 用户登录名
	 * @return
	 */
	public boolean synchUpdateDepInfo(DepInfo p_DepInfo,List<String> p_UserInfo);
	
	/**
	 * 同步删除部门信息
	 * @param p_DepInfo 部门信息
	 * @param p_UserInfo 用户登录名
	 * @return
	 */
	public boolean synchDelDepInfo(DepInfo p_DepInfo,List<String> p_UserInfo);
	
	/**
	 * 同步添加角色信息
	 * @param p_RoleInfo 角色信息
	 * @return
	 */
	public boolean synchAddRoleInfo(RoleInfo p_RoleInfo);
	
	/**
	 * 同步更新角色信息
	 * @param p_RoleInfo 角色信息
	 * @return
	 */
	public boolean synchUpdateRoleInfo(RoleInfo p_RoleInfo);
	
	/**
	 * 同步删除角色信息
	 * @param p_RoleInfoId 角色id
	 * @return
	 */
	public boolean synchDelRoleInfo(String p_RoleInfoId);
	
	/**
	 * 同步用户角色信息
	 * @param loginname 用户帐号
	 * @param roleId 角色id集合
	 * @return
	 */
	public boolean synchAddUserRole(String loginname,List<String> roleId);
	
	/**
	 * 同步组成员关系
	 * @param p_userDepList 组用户集合
	 * @return 
	 */
	public boolean synchAddGroupuser(List<UserDep> p_userDepList);
	
	/**
	 * 删除组成员关系
	 * @param p_userDepList 组用户集合
	 * @return
	 */
	public boolean synchDelGroupuser(List<UserDep> p_userDepList);
	
	/**
	 * 批量人员添加
	 * @param p_UserInfo 用户集合
	 * @return
	 */
	public boolean batchSynchAddUserInfo(List<UserInfo> p_UserInfo);
	
	/**
	 * 批量部门添加
	 * @param p_DepInfo 部门集合
	 * @return
	 */
	public boolean batchSynchAddDepInfo(List<DepInfo> p_DepInfo);
	
	/**
	 * 批量添加部门人员关系
	 * @param p_UserDepList 组成员集合
	 * @return
	 */
	public boolean batchSynchAddGroupUser(List<UserDep> p_UserDepList);
	
	/**
	 * 批量角色添加
	 * @param p_RoleInfoList 角色集合
	 * @return
	 */
	public boolean batchSynchAddRoleInfo(List<RoleInfo> p_RoleInfoList);
}

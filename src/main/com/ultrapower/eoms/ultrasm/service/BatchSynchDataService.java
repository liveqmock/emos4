package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;

/**
 * 批量同步数据通用接口
 * @author SunHailong
 */
public interface BatchSynchDataService {
	
	/**
	 * pasm批量同步用户（pasm-eoms）
	 * 单独同步用户信息
	 * @param preventUser 不同步的用户 格式：Demo,root,admin
	 * @return
	 */
	public boolean pasmBatchSynchUserToEoms(String preventUser);
	
	/**
	 * pasm批量同步部门（pasm-eoms）
	 * 同步部门信息以及部门下的成员关系
	 * @return
	 */
	public boolean pasmBatchSynchDeptToEoms();
	
	/**
	 * pasm批量同步用户（eoms-pasm）
	 * @param userList eoms的用户列表
	 * @return
	 */
	public boolean pasmBatchSynchUserToPasm(List<UserInfo> userList);
	
	/**
	 * pasm批量同步部门（eoms-pasm）
	 * 同步部门信息以及部门下的成员关系
	 * @param depList eoms的部门列表
	 * @return
	 */
	public boolean pasmBatchSynchDeptToPasm(List<DepInfo> depList);
}

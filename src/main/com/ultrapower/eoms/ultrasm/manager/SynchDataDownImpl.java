package com.ultrapower.eoms.ultrasm.manager;

import java.util.List;

import com.ultrapower.eoms.common.constants.ConstantsSynch;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.SynchDataService;
import com.ultrapower.eoms.ultrasm.service.SynchDataToPasm;
import com.ultrapower.eoms.ultrasm.service.SynchDataToV2;

/**
 * eoms4同步系统管理数据到其他项目接口实现
 * @author SunHailong
 */
public class SynchDataDownImpl implements SynchDataService
{
	private SynchDataToV2 synchDataToV2;
	private SynchDataToPasm synchDataToPasm;
	
	public int synchUserAdd(UserInfo user, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 2:此人不存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "user", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchUserAdd(user);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "user", systemMark))
		{
			//调用PASM接口
			resultPasm = synchDataToPasm.synchUserAdd(user);
			if(resultPasm > 0)//如果pasm同步失败 则将V2中用户一同删除
			{
				synchDataToV2.synchUserDel(user.getPid());
			}
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}
	
	public int synchUserDel(String userId, String loginName, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 2:此人不存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "user", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchUserDel(userId);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "user", systemMark))
		{
			//调用PASM接口
			resultPasm = synchDataToPasm.synchUserDel(loginName);
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}

	public int synchUserEdit(UserInfo user, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 2:此人不存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "user", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchUserEdit(user);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "user", systemMark))
		{
			//调用PASM接口
			resultPasm = synchDataToPasm.synchUserEdit(user);
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}

	public int synchUserPwdEdit(String userId, String newPwd, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 2:此人不存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "user", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchUserPwdEdit(userId, newPwd);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "user", systemMark))
		{
			//调用PASM接口
			resultPasm = synchDataToPasm.synchUserPwdEdit(userId, newPwd);
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}

	public int synchDepAdd(DepInfo dep, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "dep", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchDepAdd(dep);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "dep", systemMark))
		{
			//调用PASM接口
			resultPasm = synchDataToPasm.synchDepAdd(dep);
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}

	public int synchDepDel(String depId, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "dep", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchDepDel(depId);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "dep", systemMark))
		{
			//调用PASM接口
			resultPasm = synchDataToPasm.synchDepDel(depId);
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}

	public int synchDepEdit(DepInfo dep, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "dep", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchDepEdit(dep);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "dep", systemMark))
		{
			//调用PASM接口
			resultPasm = synchDataToPasm.synchDepEdit(dep);
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}

	public int synchDepUserAdd(List<String> depId, List<String> userId, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "userdep", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchDepUserAdd(depId, userId);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "userdep", systemMark))
		{
			//调用PASM接口
			resultPasm = synchDataToPasm.synchDepUserAdd(depId, userId);
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}

	public int synchDepUserDel(List<String> depId, List<String> userId, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "userdep", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchDepUserDel(depId, userId);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "userdep", systemMark))
		{
			//调用PASM接口
			resultPasm = synchDataToPasm.synchDepUserDel(depId, userId);
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}

	public int synchDepUserDel(List<String> orgId, String orgType, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "userdep", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchDepUserDel(orgId, orgType);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "userdep", systemMark))
		{
			//调用PASM接口
			//由于此方法synchDepUserDel适用在删除用户或部门的时候所调用
			//而PASM端在删除用户时，会自动删除组成员关系
			//       在删除部门时，前提条件是不存在组成员才可以删除
			//所以在此不做对PASM的同步，若过后有所变更，再进行补充
//			resultPasm = synchDataToPasm.synchDepUserDel(orgId, orgType);
			resultPasm = 0;
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}

	public int synchRoleAdd(RoleInfo role, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "role", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchRoleAdd(role);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "role", systemMark))
		{
			//调用PASM接口
			resultPasm = synchDataToPasm.synchRoleAdd(role);
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}

	public int synchRoleDel(String roleId, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "role", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchRoleDel(roleId);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "role", systemMark))
		{
			//调用PASM接口
			resultPasm = synchDataToPasm.synchRoleDel(roleId);
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}
	
	public int synchRoleEdit(RoleInfo role, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "role", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchRoleEdit(role);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "role", systemMark))
		{
			//调用PASM接口
			resultPasm = synchDataToPasm.synchRoleEdit(role);
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}

	public int synchRoleOrgAdd(List<String> roleId, List<String> orgId, String orgType, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "roleorg", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchRoleOrgAdd(roleId, orgId, orgType);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "roleorg", systemMark))
		{
			//调用PASM接口
			resultPasm = synchDataToPasm.synchRoleOrgAdd(roleId, orgId, orgType);
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}

	public int synchRoleOrgDel(List<String> roleId, List<String> orgId, String orgType, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "roleorg", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchRoleOrgDel(roleId, orgId, orgType);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "roleorg", systemMark))
		{
			//调用PASM接口
			resultPasm = synchDataToPasm.synchRoleOrgDel(roleId, orgId, orgType);
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}

	public int synchRoleOrgDel(List<String> objId, String objType, String systemMark)
	{
		int result = 0;		//0:同步成功 (10-19]:V2同步失败 (90-99]:PASM同步失败
		int resultV2 = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		int resultPasm = 0;	//0:同步成功 1:用户登录名已存在 9:同步失败
		
		//进行V2系统的同步
		if(this.isSynchDown("v2", "roleorg", systemMark))
		{
			//调用V2接口
			resultV2 = synchDataToV2.synchRoleOrgDel(objId, objType);
		}
		else
		{
			resultV2 = 0;
		}
		
		//进行PASM系统的同步
		if(resultV2 == 0 && this.isSynchDown("pasm", "roleorg", systemMark))
		{
			//调用PASM接口
			//由于此方法synchRoleOrgDel适用在删除用户、部门、角色的时候所调用
			//而PASM端在删除用户时，会自动删除组成员关系
			//       在删除部门时，PASM不存在角色和部门的关系
			//       在删除角色时，EOMS4端在删除角色前不能存在组成员
			//所以在此不做对PASM的同步，若过后有所变更，再进行补充
			//synchDataToPasm.synchRoleOrgDel(objId, objType)此方法以实现删除角色组成员关系
//			resultPasm = synchDataToPasm.synchRoleOrgDel(objId, objType);
			resultPasm = 0;
		}
		else
		{
			resultPasm = 0;
		}
		
		//同步结果记录
		if(resultV2 > 0)
		{
			result = 10 + resultV2;
		}
		else if(resultPasm > 0)
		{
			result = 90 + resultPasm;
		}
		return result;
	}

	/**
	 * 是否可以下行同步数据
	 * @param systype 系统类型 pasm、v2
	 * @param datatype 数据类型 user、dep、userdep、role、roleorg
	 * @param 系统来源标识
	 * @return
	 */
	private boolean isSynchDown(String systype, String datatype, String systemMark)
	{
		boolean result = false;
		if(ConstantsSynch.isSynch && "pasm".equals(systype))
		{
			if(!systemMark.equals(ConstantsSynch.SYSTEM_PASM)) {
				if(ConstantsSynch.isSynchToPasm && ("all".equals(ConstantsSynch.synchToPasmContent) || ConstantsSynch.synchToPasmContent.indexOf(datatype) >= 0))
					result = true;
			}
		}
		else if("v2".equals(systype))
		{
			if(!systemMark.equals(ConstantsSynch.SYSTEM_V2)) {
				if(ConstantsSynch.isSynchToV2 && ("all".equals(ConstantsSynch.synchToV2Content) || ConstantsSynch.synchToV2Content.indexOf(datatype) >= 0))
					result = true;
			}
		}
		return result;
	}
	
	public void setSynchDataToV2(SynchDataToV2 synchDataToV2) {
		this.synchDataToV2 = synchDataToV2;
	}
	public void setSynchDataToPasm(SynchDataToPasm synchDataToPasm) {
		this.synchDataToPasm = synchDataToPasm;
	}
}

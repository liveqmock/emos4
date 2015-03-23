package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;

/**
 * 部分信息管理服务类
 * 此服务主要提供了对部门信息的增、删、改、查的服务
 * 也提供了部门和用户、角色的关系维护的服务
 * @author 孙海龙
 */
public interface DepManagerService
{
	/**
	 * 根据部门全名获取部门对象信息
	 * 值班导入时所需要的服务
	 * @param depFullName 部门全名
	 * @return DepInfo 返回部门对象
	 */
	public DepInfo getDepByFullname(String depFullName);
	
	/**
	 * 根据部门ID获取部门名称
	 * @param depId 部门ID
	 * @return String 返回部门名称
	 */
	public String getDepNameByID(String depId);
	
	/**
	 * 根据IDList获取部门名称
	 * 用于数据移植
	 * @param depIdList
	 * @return String 返回数据格式 id1,id2,id3;name1,name2,name3
	 */
	public String getDepNamesByIDs(List depIdList);
	
	/**
	 * 根据多个部门ID获取对应的部门名称
	 * @param depIds
	 * @return String 返回数据格式 id1,id2,id3;name1,name2,name3
	 */
	public String getDepNamesById(String depIds);
	
	/**
	 * 根据机构ID获取该部门下的用户名称
	 * @param depId 部门ID
	 * @return List 返回用户名称List
	 */
	public List getUserNameByDepID(String depId);
	
	/**
	 * 保存部门信息：添加 或 修改
	 * 操作包含部门信息的添加和修改、部门用户关系的添加和删除、部门角色关系的添加和删除
	 * @param dep 部门对象
	 * @param userIdList 添加的用户IDList
	 * @param roleIdList 添加的角色IDList
	 * @param delUserIdList 删除的用户IDList
	 * @param delRoleIdList 删除的角色IDList
	 * @return boolean 返回true或false体现是否保存成功
	 */
	public boolean saveDepInfo(DepInfo dep, List userIdList, List roleIdList, List delUserIdList, List delRoleIdList);
	
	/**
	 * 根据部门ID获取顶级部门信息
	 * @param depId 部门ID
	 * @return DepInfo 返回部门信息对象
	 */
	public DepInfo getTopDepByDepid(String depId);
	
	/**
	 * 根据部门ID获取所属公司
	 * @param depId 部门ID
	 * @return DepInfo 返回部门信息对象
	 */
	public DepInfo getCompanyByDepid(String depId);
	
	/**
	 * 根据部门ID获取所有级别上级部门ID
	 * 专门为刷新树节点提供
	 * @param depId 部门ID
	 * @return String 返回所有上级部门ID 如：0,id1,id2,id3 其中顺序按照从高到低的顺序 不包含传入的部门ID
	 */
	public String getAllParentIdById(String depId);
	/**
	 * 根据用户ID获取所有级别上级部门ID,并且进行去重处理
	 */
	public List<String> getAllParentIdListByUserId(String userID);
	/**
	 * 添加部门信息
	 * 专门添加部门基本信息的方法
	 * @param dep 部门对象
	 * @return String 返回部门ID
	 */
	public String addDepInfo(DepInfo dep);
	
	/**
	 * 添加部门信息
	 * 专门添加部门基本信息的方法
	 * @param dep 部门对象
	 * @param systemMark 系统来源标识
	 * @return String 返回部门ID
	 */
	public String addDepInfo(DepInfo dep, String systemMark);

	/**
	 * 添加部门信息
	 * 为批量添加部门信息情况提供的方法
	 * @param depList 部门List
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addDepInfo(List<DepInfo> depList);

	/**
	 * 添加部门用户
	 * 专门为部门配置用户的方法
	 * @param depId 部门ID
	 * @param userIdList 用户IDList
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addDepUser(String depId, List userIdList);
	
	/**
	 * 添加部门用户
	 * 专门为部门配置用户的方法
	 * @param depId 部门ID
	 * @param userIdList 用户IDList
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addDepUser(String depId, List userIdList, String systemMark);

	/**
	 * 添加部门所属角色
	 * 专门配置部门和角色的关系的方法
	 * @param depId 部门ID
	 * @param roleIdList 角色IDList
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addDepRole(String depId, List roleIdList);

	/**
	 * 删除部门信息 删除一个部门信息
	 * 首先将部门人员关系、部门角色关系删除，再将部门彻底删除，此服务只供系统内部使用
	 * @param depId 部门ID
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteDepByID(String depId);
	
	/**
	 * 删除部门信息 删除一个部门信息
	 * 首先将部门人员关系、部门角色关系删除，再将部门彻底删除，此服务只供系统内部使用
	 * @param depId 部门ID
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteDepByID(String depId, String systemMark);
	
	/**
	 * 删除部门信息 删除多个部门信息
	 * 首先将部门人员关系、部门角色关系删除，再将部门彻底删除，此服务只供系统内部使用
	 * @param depIdList 部门IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteDepByID(List depIdList);

	/**
	 * 根据部门和用户ID来删除部门下的用户
	 * @param depId 部门ID
	 * @param userIdList 用户IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteDepUser(String depId, List userIdList);
	
	/**
	 * 根据部门和用户ID来删除部门下的用户
	 * @param depId 部门ID
	 * @param userIdList 用户IDList
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteDepUser(String depId, List userIdList, String systemMark);

	/**
	 * 根据部门和角色ID来删除部门所属角色
	 * @param depId 部门ID
	 * @param roleIdList 角色IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteDepRole(String depId, List roleIdList);
	
	/**
	 * 根据部门和角色ID来删除部门所属角色
	 * @param depId 部门ID
	 * @param roleIdList 角色IDList
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteDepRole(String depId, List roleIdList, String systemMark);

	/**
	 * 修改部门信息
	 * 专门修改部门基本信息的方法
	 * @param dep 部门对象
	 * @return String 返回部门ID
	 */
	public String updateDepInfo(DepInfo dep);
	
	/**
	 * 修改部门信息
	 * 专门修改部门基本信息的方法
	 * @param dep 部门对象
	 * @param systemMark 系统来源标识
	 * @return String 返回部门ID
	 */
	public String updateDepInfo(DepInfo dep, String systemMark);

	/**
	 * 同时将多个部门停用
	 * @param depIdList 部门IDList
	 * @return boolean 返回true或false体现状态是否修改成功
	 */
	public boolean setDepDisable(List depIdList);

	/**
	 * 同时将多个部门启用
	 * @param depIdList 部门IDList
	 * @return boolean 返回true或false体现状态是否修改成功
	 */
	public boolean setDepEnabled(List depIdList);

	/**
	 * 获取部门信息
	 * 根据部门ID获取部门信息
	 * @param depId 部门ID
	 * @return 返回部门信息对象
	 */
	public DepInfo getDepByID(String depId);

	/**
	 * 获取部门信息List
	 * 根据部门IDList获取部门信息List
	 * @param depIdList 部门IDList
	 * @return List<DepInfo> 返回部门信息List
	 */
	public List<DepInfo> getDepByID(List depIdList);
	
	/**
	 * 根据上级部门ID获取部门List
	 * @param depId 部门ID
	 * @return List<DepInfo> 返回部门信息List
	 */
	public List<DepInfo> getDepByParentID(String depId);

	/**
	 * 根据用户ID来获取部门IDList
	 * @param userId 用户ID
	 * @param type 关系类型1.所属组 2.所属兼职部门 空为全部
	 * @return List 返回部门IDList
	 */
	public List getDepIdByUser(String userId, String type);
	
	/**
	 * 根据部门ID来获取部门下的用户List
	 * @param depId 部门ID
	 * @param isPage 是否分页
	 * @return List<UserInfo> 返回人员信息List
	 */
	public List<UserInfo> getUserByDepID(String depId, boolean isPage);
	
	/**
	 * 根据部门ID来获取部门下用户IDList
	 * @param depId 部门id
	 * @return
	 */
	public List<String> getUserIdByDepID(String depId);

	/**
	 * 根据部门ID来获取部门所属角色
	 * @param depId 部门ID
	 * @return List<RoleInfo> 返回角色信息List
	 */
	public List<RoleInfo> getRoleByDepID(String depId);
	
	/**
	 * 同步部门到PASM
	 * @return
	 */
	public boolean synchDepToPasm();
	
	/**
	 * 同步组成员关系到PASM
	 * @return
	 */
	public boolean synchUserDepToPasm();
}

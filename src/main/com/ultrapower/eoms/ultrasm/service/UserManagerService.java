package com.ultrapower.eoms.ultrasm.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.RoleOrgShow;
import com.ultrapower.eoms.ultrasm.model.UserInfo;

/**
 * 用户信息管理服务类
 * 此服务主要提供了对人员信息的增、删、改、查的服务
 * 也提供了人员和部门、角色的关系维护的服务
 * @author 孙海龙
 */
public interface UserManagerService
{
	/**
	 * 验证用户登录名唯一性
	 * @param loginName 用户登录名
	 * @return boolean 返回用户登陆名的唯一性
	 */
	public boolean isUnique(String loginName);
	
	/**
	 * 根据用户登录名获取用户ID
	 * @param loginName 用户登陆名
	 * @return String 返回用户ID
	 */
	public String getPidByLoginName(String loginName);
	
	/**
	 * 根据用户登陆名获取所属组ID
	 * @param loginName
	 * @return
	 */
	public String getGroupIdsByLoginName(String loginName);
	
	/**
	 * 根据用户ID获取用户登录名
	 * @param pid
	 * @return
	 */
	public String getLoginNameByPid(String pid);
	
	/**
	 * 根据用户ID获取用户登录名
	 * @param userIdList
	 * @return
	 */
	public List<String> getLoginNameByPid(List<String> userIdList);
	
	/**
	 * 根据用户ID获取用户名称
	 * @param userId
	 * @return String 用户名称
	 */
	public String getUserNameByID(String userId);
	
	/**
	 * 根据用户登录名获取用户名称
	 * @param loginName 用户登录名
	 * @return String 用户名称
	 */
	public String getUserNameByLoginName(String loginName);
	
	/**
	 * 根据多个用户ID获取对应的用户名称
	 * @param userIds 用户IDs
	 * @return String 返回数据格式 id1,id2,id3;name1,name2,name3
	 */
	public String getUserNamesById(String userIds);
	
	/**
	 * 根据多个用户关键字获取对应用户姓名
	 * @param userIds
	 * @param type 1.用户登录名 2.用户id
	 * @return
	 */
	public String getUserNames(String userIds, String type);
	
	/**
	 * 根据用户ID获取用户头像名称
	 * @param userId
	 * @return
	 */
	public String getImageById(String userId);
	
	/**
	 * 保存人员信息：添加 或 修改
	 * 操作包括对人员信息的添加和修改、对人员部门关系的添加和删除、对人员角色关系的添加和删除
	 * @param user 人员对象
	 * @param groupIdList 添加的所属组织IDList
	 * @param ptdepIdList 添加的兼职部门IDList
	 * @param roleIdList 添加的角色IDList
	 * @param delGroupIdList 删除的所属组织IDList
	 * @param delPtdepIdList 删除的兼职部门IDList
	 * @param delRoleIdList 删除的角色IDList
	 * @param updatePwd 是否需要修改用户密码（"true" or "false"）
	 * @return boolean 返回true或false体现是否保存信息成功
	 */
	public boolean saveUserInfo(UserInfo user, List groupIdList, List ptdepIdList, List roleIdList
			, List delGroupIdList, List delPtdepIdList, List delRoleIdList,String updatePwd);
	
	/**
	 * 添加人员信息
	 * 只添加人员基本信息
	 * @param user 人员对象
	 * @return String 返回人员ID
	 */
	public String addUserInfo(UserInfo user);
	
	/**
	 * 添加人员信息
	 * 只添加人员基本信息
	 * @param user 人员对象
	 * @param systemMark 系统来源标识
	 * @return String 返回人员ID
	 */
	public String addUserInfo(UserInfo user, String systemMark);

	/**
	 * 添加人员信息
	 * 为批量添加人员信息的情况所提供
	 * @param userList 人员对象List
	 * @return boolean 返回true或false体现批量添加人员信息是否成功
	 */
	public boolean addUserInfo(List<UserInfo> userList);

	/**
	 * 添加人员所属机构
	 * 专门设置人员和机构关系的方法
	 * @param userId 人员ID
	 * @param groupIdList 所属组织IDList
	 * @param ptdepIdList 兼职部门IDList
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addUserDep(String userId, List groupIdList, List ptdepIdList);
	
	/**
	 * 添加人员所属机构
	 * 专门设置人员和机构关系的方法
	 * @param userId 人员ID
	 * @param groupIdList 所属组织IDList
	 * @param ptdepIdList 兼职部门IDList
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addUserDep(String userId, List groupIdList, List ptdepIdList, String systemMark);

	/**
	 * 添加人员所属角色
	 * 专门设置人员和角色关系的方法
	 * @param userId 人员ID
	 * @param roleIdList 角色IDList
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addUserRole(String userId, List roleIdList);
	
	/**
	 * 添加人员所属角色
	 * 专门设置人员和角色关系的方法
	 * @param userId 人员ID
	 * @param roleIdList 角色IDList
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addUserRole(String userId, List roleIdList, String systemMark);

	/**
	 * 删除人员信息 删除一个人员信息
	 * 首先删除人员的关系信息（人员部门关系、人员角色关系），再人员彻底删除，此服务只供系统内部使用
	 * @param userId 人员ID
	 * @return boolean  返回true或false体现是否删除成功
	 */
	public boolean deleteUserByID(String userId);
	
	/**
	 * 删除人员信息 删除一个人员信息
	 * 首先删除人员的关系信息（人员部门关系、人员角色关系），再人员彻底删除，此服务只供系统内部使用
	 * @param userId 人员ID
	 * @param systemMark 系统来源标识
	 * @return boolean  返回true或false体现是否删除成功
	 */
	public boolean deleteUserByID(String userId, String systemMark);
	
	/**
	 * 删除人员信息 删除多个人员信息
	 * 首先删除人员的关系信息（人员部门关系、人员角色关系），再人员彻底删除，此服务只供系统内部使用
	 * @param userIdList 人员IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteUserByID(List userIdList);

	/**
	 * 删除人员所属部门
	 * 即是删除人员和部门的关系
	 * @param userId 人员ID
	 * @param depIdList 部门IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteUserDep(String userId, List depIdList);
	
	/**
	 * 删除人员所属部门
	 * 即是删除人员和部门的关系
	 * @param userId 人员ID
	 * @param depIdList 部门IDList
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteUserDep(String userId, List depIdList, String systemMark);

	/**
	 * 删除人员所属角色
	 * 即是删除人员与角色的关系
	 * @param userId 人员ID
	 * @param roleIdList 角色IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteUserRole(String userId, List roleIdList);
	
	/**
	 * 删除人员所属角色
	 * 即是删除人员与角色的关系
	 * @param userId 人员ID
	 * @param roleIdList 角色IDList
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteUserRole(String userId, List roleIdList, String systemMark);

	/**
	 * 修改人员信息
	 * 专门修改人员基本信息的方法
	 * @param user 人员对象
	 * @return String 返回人员ID
	 */
	public String updateUserInfo(UserInfo user);
	
	/**
	 * 修改人员信息
	 * 专门修改人员基本信息的方法
	 * @param user 人员对象
	 * @param systemMark 系统来源标识
	 * @return String 返回人员ID
	 */
	public String updateUserInfo(UserInfo user, String systemMark);

	/**
	 * 修改用户密码
	 * @param userId 人员ID
	 * @param pwd 用户密码
	 * @return boolean 返回true或false体现是否修改成功
	 */
	public boolean updateUserPwd(String userId, String pwd);
	
	/**
	 * 修改用户密码
	 * @param userId 人员ID
	 * @param pwd 用户密码
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否修改成功
	 */
	public boolean updateUserPwd(String userId, String pwd, String systemMark);

	/**
	 * 同时将多个人员停用
	 * @param userIdList 人员IDList
	 * @return boolean 返回true或false体现状态是否修改成功
	 */
	public boolean setUserDisable(List userIdList);

	/**
	 * 同时将多个人员启用
	 * @param userIdList 人员IDList
	 * @return boolean 返回true或false体现状态是否修改成功
	 */
	public boolean setUserEnabled(List userIdList);

	/**
	 * 根据用户登录名获取用户对象
	 * @param loginName	返回用户登录名
	 * @return
	 */
	public UserInfo getUserByLoginName(String loginName);
	
	/**
	 * 根据用户登录名获取用户对象
	 * @param loginName 用户登录名
	 * @param onlyUser 是否只加载用户信息
	 * @return
	 */
	public UserInfo getUserByLoginName(String loginName, boolean onlyUser);
	
	/**
	 * 根据用户登录名获取用户信息List
	 * @param lnList
	 * @return
	 */
	public List<UserInfo> getUserByLoginName(List<String> lnList);
	
	/**
	 * 获取人员信息
	 * 根据人员ID获取人员信息
	 * @param userId 人员ID
	 * @return UserInfo 返回人员信息对象
	 */
	public UserInfo getUserByID(String userId);
	
	/**
	 * 获取人员信息List
	 * 根据人员IDList获取人员信息List
	 * @param userIdList 人员IDList
	 * @return List<UserInfo> 返回人员信息List
	 */
	public List<UserInfo> getUserByID(List userIdList);

	/**
	 * 根据人员ID来获取角色List
	 * @param userId 人员ID
	 * @return List<RoleInfo> 返回角色信息List
	 */
	public List<RoleInfo> getRoleByUserID(String userId);
	
	/**
	 * 根据人员ID获取角色关系信息
	 * @param userId 人员ID
	 * @return List<RoleOrgShow> 返回角色组织关系信息List
	 */
	public List<RoleOrgShow> getRoleOrgByUserID(String userId);

	/**
	 * 用户加密密码
	 * @param pwd 明文密码
	 * @return String 返回密文密码
	 */
	public String encodeUserPwd(String pwd);
	
	/**
	 * 用户密码解密
	 * @param pwd 密文密码
	 * @return String 返回明文密码
	 */
	public String decodePwd(String pwd);
	
	/**
	 * 根据用户ID取得用户手机号集合
	 * @param userIdList 用户IDList
	 * @return List<String> 返回用户手机号List
	 */
	public List<String> getMobileListByUserId(List<String> userIdList);
	
	/**
	 * 根据部门ID取得对应部门下人员手机号集合
	 * @param depIdList 部门IDList
	 * @return List<String> 返回用户手机号List
	 */
	public List<String> getMobileListByDepId(List<String> depIdList);
	
	/**
	 * 根据用户名和密码判断该用户是否可以正常登录
	 * @param loginName 用户登陆名
	 * @param pwd 用户密码
	 * @return 返回true或false体现该用户是否正常登陆
	 */
	public boolean canLogin(String loginName, String pwd);
	
	/**
	 * 萨班斯密码管理 保存密码信息
	 * @param loginName 用户登陆名
	 * @param pwd 用户密码
	 * @param isUpdateUser 是否更新用户信息表
	 * @return 返回true或false体现是否保存密码成功
	 */
	public boolean saveUserPwd(String loginName, String pwd, boolean isUpdateUser);
	
	/**
	 * 同步人员到PASM
	 * @return
	 */
	public boolean synchUserToPasm();
	
	/**
	 * 同步人员到REMEDY
	 * @return
	 */
	public boolean synchUserToRemedy();
	
	/**
	 * 批量保存或更新用户信息
	 * 根据用户登录名为标识，若存在此登录名则更新用户信息，否则新增人员信息
	 * @param userList 用户信息列表
	 * @return
	 */
	public String saveOrUpdateUserInfo(List<UserInfo> userList);
	
	/**
	 * 根据用户登录名获取用户对象
	 * @param jobNum	返回用户对象
	 * @return
	 */
	public UserInfo getUserByJobNum(String loginName);
	/**
	 * 根据用户登录名获取用户配置的搜索条件 key：搜索sql的名称 value：条件字符串，条件用‘，’号隔开。
	 * @param loginName
	 * @return
	 */
	public Map<String,String> getUserSearchCondition(String loginName);
	/**
	 * 
	 * @param sqlname
	 * @param conditions
	 */
	public void saveSearchConditionConfig(String loginName,String sqlname,String conditions);
	
	/**
	 * 根据组id获取组内用户
	 * @param groupId
	 * @return
	 */
	public List<UserInfo> getUsersByGroupId(String groupId);
	

	/**
	 * 根据组id获取组内用户
	 * @param groupId
	 * @return
	 */
	public List<UserInfo> getUsersByGroupFullname(String groupFullname);
	
}

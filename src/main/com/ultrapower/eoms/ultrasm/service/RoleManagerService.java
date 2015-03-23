package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.ultrasm.model.DataInfo;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.Operate;
import com.ultrapower.eoms.ultrasm.model.ResPropertyShow;
import com.ultrapower.eoms.ultrasm.model.Resource;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.RoleResOpShow;
import com.ultrapower.eoms.ultrasm.model.SqlDataPrivilege;
import com.ultrapower.eoms.ultrasm.model.UserInfo;

/**
 * 角色管理服务类
 * 此服务主要实现角色的信息维护、角色成员关系维护、角色权限信息维护
 * 维护信息主要包含增、删、改、查以及一些特殊服务
 * @author 孙海龙
 */
public interface RoleManagerService
{
	/**
	 * 向角色添加所有普通用户
	 * @param roleid 角色id
	 * @param preventUser 拒绝加入此角色的用户登录名 格式：Demo,admin,root
	 * @return
	 */
	public boolean addAllUserToRole(String roleid, String preventUser);
	
	/**
	 * 添加角色信息并拷贝其他角色权限赋予此角色
	 * @param role 角色对象
	 * @param roleId 角色ID（通过此角色为新建角色拷贝角色权限）
	 * @return String 返回创建角色的ID
	 */
	public String addRoleAndCopyPrivilege(RoleInfo role, String roleId);
	
	/**
	 * 添加角色信息
	 * 根据上级角色创建角色的DN和DNS并将此角色信息保存到数据库
	 * @param role 角色对象
	 * @return String 返回创建角色的ID
	 */
	public String addRoleInfo(RoleInfo role);
	
	/**
	 * 添加角色信息
	 * 根据上级角色创建角色的DN和DNS并将此角色信息保存到数据库
	 * @param role 角色对象
	 * @param systemMark 系统来源标识
	 * @return String 返回创建角色的ID
	 */
	public String addRoleInfo(RoleInfo role, String systemMark);

	/**
	 * 根据角色ID拷贝角色权限（只能再创建时才能复制角色权限）
	 * @param newRoleId 新创建角色ID
	 * @param roleId 角色ID（将此角色权限拷贝到新创建的角色上）
	 * @return boolean 返回true或false体现是否拷贝成功
	 */
	public boolean copyFromRoleID(String newRoleId, String roleId);

	/**
	 * 验证角色是否可以删除
	 * 不可删除条件：若该角色下包含子角色或者该角色和组织机构有关联则不能删除
	 * @param roleIdList 角色IDList
	 * @return String 返回不能删除的角色名称（多个名称以","分割），若为""则可以删除
	 */
	public String deleteJudgeInfo(List roleIdList);

	/**
	 * 根据角色ID删除角色信息
	 * 删除前提：若该角色下包含子角色或者该角色和组织机构有关联则不能删除
	 * 删除过程：先清除该角色下的所有权限，再将该角色删除
	 * @param roleId 角色ID
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteRoleByID(String roleId);
	
	/**
	 * 根据角色ID删除角色信息
	 * 删除前提：若该角色下包含子角色或者该角色和组织机构有关联则不能删除
	 * 删除过程：先清除该角色下的所有权限，再将该角色删除
	 * @param roleId 角色ID
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteRoleByID(String roleId, String systemMark);
	
	/**
	 * 根据角色IDList删除角色信息
	 * 删除前提：若存在某个角色下包含子角色或者某个角色和组织机构有关联则不能删除
	 * 删除过程：先清除角色下的所有权限，再将角色删除
	 * @param roleIdList 角色IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteRoleByID(List roleIdList);

	/**
	 * 修改角色信息
	 * @param role 角色对象
	 * @return boolean 返回true或false体现角色信息是否修改成功
	 */
	public boolean updateRoleInfo(RoleInfo role);
	
	/**
	 * 修改角色信息
	 * @param role 角色对象
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现角色信息是否修改成功
	 */
	public boolean updateRoleInfo(RoleInfo role, String systemMark);

	/**
	 * 根据角色ID获取角色信息
	 * @param roleId 角色ID
	 * @return RoleInfo 返回角色对象
	 */
	public RoleInfo getRoleByID(String roleId);

	/**
	 * 根据角色IDList获取角色信息列表
	 * @param roleIdList 角色IDList
	 * @return List<RoleInfo> 返回角色对象列表
	 */
	public List<RoleInfo> getRoleByID(List roleIdList);
	
	/**
	 * 根据角色上级ID获取角色List
	 * @param roleId 角色ID
	 * @return List<RoleInfo> 返回角色对象列表
	 */
	public List<RoleInfo> getRoleByParentID(String roleId);

	/**
	 * 配置角色与组织机构的关系
	 * 为多个角色配置关系
	 * @param roleIdList 角色IDList
	 * @param userIdList 用户IDList
	 * @param depIdList 部门IDList
	 * @return boolean 返回true或false体现是否配置成功
	 */
	public boolean addRoleOrg(List roleIdList, List userIdList, List depIdList);
	
	/**
	 * 配置角色与组织机构的关系
	 * 为多个角色配置关系
	 * @param roleIdList 角色IDList
	 * @param userIdList 用户IDList
	 * @param depIdList 部门IDList
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否配置成功
	 */
	public boolean addRoleOrg(List roleIdList, List userIdList, List depIdList, String systemMark);

	/**
	 * 配置角色与组织机构的关系
	 * 为一个角色配置关系
	 * @param roleId 角色ID
	 * @param userIdList 用户IDList
	 * @param depIdList 部门IDList
	 * @return boolean 返回true或false体现是否配置成功
	 */
	public boolean addRoleOrg(String roleId, List userIdList, List depIdList);
	
	/**
	 * 配置角色与组织机构的关系
	 * 为一个角色配置关系
	 * @param roleId 角色ID
	 * @param userIdList 用户IDList
	 * @param depIdList 部门IDList
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否配置成功
	 */
	public boolean addRoleOrg(String roleId, List userIdList, List depIdList, String systemMark);

	/**
	 * 添加用户所属角色
	 * 为组织机构模块提供
	 * @param userId 用户ID
	 * @param roleIdList 角色IDList
	 * @return boolean 返回true或false体现是否配置成功
	 */
	public boolean addUserRole(String userId, List roleIdList);

	/**
	 * 添加用户所属角色
	 * 为组织机构模块提供
	 * @param userId 人员ID
	 * @param roleIdList 角色IDList
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addUserRole(String userId, List roleIdList, String systemMark);
	
	/**
	 * 添加部门所属角色
	 * 为组织机构模块提供
	 * @param depId 部门ID
	 * @param roleIdList 角色IDList
	 * @return boolean 返回true或false体现是否配置成功
	 */
	public boolean addDepRole(String depId, List roleIdList);
	
	/**
	 * 添加部门所属角色
	 * 为组织机构模块提供
	 * @param depId 部门ID
	 * @param roleIdList 角色IDList
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否配置成功
	 */
	public boolean addDepRole(String depId, List roleIdList, String systemMark);

	/**
	 * 根据角色组织ID删除角色组织List
	 * @param roIdList 角色组织IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteRoleOrgByID(List roIdList);
	
	/**
	 * 根据角色组织ID删除角色组织List
	 * @param roIdList 角色组织IDList
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteRoleOrgByID(List roIdList, String systemMark);

	/**
	 * 根据角色和组织的ID来删除角色下的人员和部门
	 * 实质就是删除角色和组织的关系
	 * @param roleId 角色ID
	 * @param userIdList 用户IDList
	 * @param depIdList 部门IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteRoleOrg(String roleId, List userIdList, List depIdList);
	
	/**
	 * 根据角色和组织的ID来删除角色下的人员和部门
	 * 实质就是删除角色和组织的关系
	 * @param roleId 角色ID
	 * @param userIdList 用户IDList
	 * @param depIdList 部门IDList
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteRoleOrg(String roleId, List userIdList, List depIdList, String systemMark);

	/**
	 * 根据用户和角色ID来删除用户所属角色
	 * @param userId 用户ID
	 * @param roleIdList 角色IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteUserRole(String userId, List roleIdList);
	
	/**
	 * 根据用户和角色ID来删除用户所属角色
	 * @param userId 用户ID
	 * @param roleIdList 角色IDList
	 * @param systemMark 系统来源标识
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteUserRole(String userId, List roleIdList, String systemMark);

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
	 * 根据角色ID查询用户信息List
	 * 通过关系首先查询用户IDList，再获取对象信息List
	 * @param roleId 角色ID
	 * @return List<UserInfo> 返回用户信息列表
	 */
	public List<UserInfo> getUserByRoleID(String roleId);
	
	/**
	 * 根据角色id获取组织机构id
	 * @param roleId
	 * @param type
	 * @return
	 */
	public List getOrgIdByRoleID(String roleId, long type);
	
	/**
	 * 根据组织id获取角色idList
	 * @param orgId 组织id
	 * @param type
	 * @return
	 */
	public List getRoleIdByOrgId(String orgId, long type);
	
	/**
	 * 根据角色ID查询部门信息List
	 * 通过关系首先查询部门IDList，再获取对象信息List
	 * @param roleId 角色ID
	 * @return List<DepInfo> 返回部门信息列表
	 */
	public List<DepInfo> getDepByRoleID(String roleId);

	/**
	 * 根据用户ID来获取角色IDList
	 * @param userId 用户ID
	 * @return List 返回角色IDList
	 */
	public List getRoleIdByUserID(String userId);
	
	/**
	 * 根据用户ID来获取用户所在组，以及上级组的角色IDList
	 * @param userId
	 * @return
	 */
	public List getDeptRoleIdByUserID(String userId);
	/**
	 * 根据用户ID获得用户角色，用户所在组以及所有上级组的角色
	 * @param userId
	 * @return
	 */
	public List getAllRoleIdByUserID(String userId);
	/**
	 * 根据用户ID来获取角色信息List
	 * 为组织机构模块提供
	 * @param userId 用户ID
	 * @return List<RoleInfo> 返回角色信息列表
	 */
	public List<RoleInfo> getRoleByUserID(String userId);

	/**
	 * 根据部门ID来获取角色信息List
	 * 为组织机构模块提供
	 * @param depId 部门ID
	 * @return List<RoleInfo> 返回角色信息列表
	 */
	public List<RoleInfo> getRoleByDepID(String depId);

	/**
	 * 获取用户角色ID、名称、DNS信息
	 * 为系统登陆时存入session使用
	 * @param userId 用户ID
	 * @return String 返回角色信息字符串 格式：id1,id2;name1,name2;dns1,dns2
	 */
	public String getRoleIdAndNameByUserId(String userId);

	/**
	 * 添加角色目录树关系（即是角色的菜单权限）
	 * 为多个角色配置角色目录树
	 * @param roleIdList 角色IDList
	 * @param menuIdList 节点IDList
	 * @return boolean 返回true或false体现是否配置成功
	 */
	public boolean addRoleMenu(List roleIdList, List menuIdList);

	/**
	 * 添加角色目录树关系（即是角色的菜单权限）
	 * 为一个角色配置角色目录树
	 * @param roleId 角色ID
	 * @param menuIdList 节点IDList
	 * @return boolean 返回true或false体现是否配置成功
	 */
	public boolean addRoleMenu(String roleId, List menuIdList);

	/**
	 * 根据角色目录树ID来删除关系
	 * @param rmIdList 角色目录树IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteRoleMenuByID(List rmIdList);

	/**
	 * 根据角色和节点ID来删除角色菜单目录的关系
	 * @param roleId 角色ID
	 * @param menuIdList 节点IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteRoleMenu(String roleId, List menuIdList);
	
	/**
	 * 根据角色ID来获取该角色所享有的菜单目录节点IDList
	 * @param roleId 角色ID
	 * @return List 返回菜单目录节点IDList
	 */
	public List getMenuIdByRoleID(String roleId);
	
	/**
	 * 根据角色ID来获取该角色所享有的菜单目录树List
	 * @param roleId 角色ID
	 * @return List<MenuDtree> 菜单目录树List
	 */
	public List<MenuDtree> getMenuByRoleID(String roleId);

	/**
	 * 根据角色ID来获取该角色所享有的菜单目录树List
	 * @param roleIdList 角色IDList
	 * @return List<MenuDtree> 菜单目录树List
	 */
	public List<MenuDtree> getMenuByRoleID(List roleIdList);

	/**
	 * 添加角色资源操作关系（即是角色资源操作权限）
	 * 为多个角色配置角色资源操作
	 * @param roleIdList 角色IDList
	 * @param resIdList 资源IDList
	 * @param opIdList 操作IDList
	 * @return boolean 返回true或false体现是否配置成功
	 */
	public boolean addRoleResOp(List roleIdList, List resIdList, List opIdList);

	/**
	 * 添加角色资源操作关系（即是角色资源操作权限）
	 * 为一个角色配置角色资源操作
	 * @param roleId 角色ID
	 * @param resIdList 资源IDList
	 * @param opIdList 操作IDList
	 * @return boolean 返回true或false体现是否配置成功
	 */
	public boolean addRoleResOp(String roleId, List resIdList, List opIdList);

	/**
	 * 根据角色资源操作ID来删除关系
	 * 首先根据角色资源操作关系ID删除对应的操作数据权限，再删除关系
	 * @param rroIdList 角色资源操作IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteRoleResOpByID(List rroIdList);

	/**
	 * 根据关系ID使角色资源对应的操作停用
	 * 此方法作为扩展，系统暂时不提供。
	 * @param rroIdList 角色资源操作IDList
	 * @return boolean 返回true或false体现是否设置成功
	 */
	public boolean setRoleResOpDisable(List rroIdList);

	/**
	 * 根据关系ID使角色资源对应的操作启用
	 * 此方法作为扩展，系统暂时不提供。
	 * @param rroIdList 角色资源操作IDList
	 * @return boolean 返回true或false体现是否设置成功
	 */
	public boolean setRoleResOpEnabled(List rroIdList);
	
	/**
	 * 根据角色资源操作ID来获取角色资源操作信息
	 * @param rroId	角色资源操作关系ID
	 * @return RoleResOpShow 返回角色资源操作信息对象
	 */
	public RoleResOpShow getRroShowByID(String rroId);

	/**
	 * 根据角色ID来查询该角色所享有的资源信息List
	 * 查询一个角色所享有的资源信息
	 * @param roleId 角色ID
	 * @return List<Resource> 返回资源信息List
	 */
	public List<Resource> getResByRoleID(String roleId);

	/**
	 * 根据角色ID来查询该角色所享有的资源信息List
	 * 查询多个角色所享有的所有资源信息
	 * @param roleIdList 角色IDList
	 * @return List<Resource> 返回资源信息List
	 */
	public List<Resource> getResByRoleID(List roleIdList);

	/**
	 * 根据角色和资源ID来查询该角色对应资源所享有的操作信息List
	 * 查询一个角色对应资源所享有的操作信息
	 * @param roleId 角色ID
	 * @param resId 资源ID
	 * @return List<Operate> 返回操作信息List
	 */
	public List<Operate> getOpByRoleResID(String roleId, String resId);

	/**
	 * 根据角色和资源ID来查询该角色对应资源所享有的操作信息List
	 * 查询多个角色对应资源所享有的操作信息
	 * @param roleIdList 角色IDList
	 * @param resId 资源ID
	 * @return List<Operate> 返回操作信息List
	 */
	public List<Operate> getOpByRoleResID(List roleIdList, String resId);

	/**
	 * 根据角色、资源、操作ID来获取角色资源操作关系IDList
	 * @param roleId 角色ID
	 * @param resId 资源ID
	 * @param opIdList 操作IDList
	 * @return List 返回角色资源操作关系IDList
	 */
	public List getRroIdByRoleResOpID(String roleId, String resId, List opIdList);
	
	/**
	 * 添加数据权限（先清空数据权限再进行添加）
	 * 数据权限分为操作数据权限和操作SQL配置权限
	 * @param rroIdList 角色资源操作IDList
	 * @param rpIdList 资源属性IDList
	 * @param rpValueList 属性对应权限数据List
	 * @param sql SQL条件串
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addDataPrivilege(List rroIdList, List rpIdList, List rpValueList, String sql);
	
	/**
	 * 添加操作数据权限
	 * @param rroIdList 角色资源操作IDList
	 * @param rpIdList 资源属性IDList
	 * @param rpValueList 属性对应权限数据List
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addOpDataPrivilege(List rroIdList, List rpIdList, List rpValueList);

	/**
	 * 添加操作SQL条件配置权限
	 * @param rroIdList 角色资源操作IDList
	 * @param sql SQL条件串
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addSqlDataPrivilege(List rroIdList, String sql);
	
	/**
	 * 根据角色资源操作关系ID来获取SQL数据权限对象
	 * @param rroId 角色资源操作ID
	 * @return SqlDataPrivilege SQL数据权限对象
	 */
	public SqlDataPrivilege getSqlDataByRroId(String rroId);
	
	/**
	 * 根据资源属性ID删除数据权限
	 * 当删除资源属性时调用此方法
	 * @param rpIdList	资源属性IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteOdpByRpId(List rpIdList);
	
	/**
	 * 根据资源ID来清空角色资源操作关系
	 * @param resIdList 资源IDList
	 * @return boolean 返回true或false体现是否清空成功
	 */
	public boolean clearRroByResId(List resIdList);
	
	/**
	 * 根据操作ID清空角色资源操作关系
	 * @param opIdList 操作IDList
	 * @return boolean 返回true或false体现是否清空成功
	 */
	public boolean clearRroByOpId(List opIdList);
	
	/**
	 * 根据角色资源操作ID来清空数据权限
	 * @param rroId 角色资源操作ID
	 * @return boolean 返回true或false体现是否清空成功
	 */
	public boolean clearDataPrivilegeByRroID(String rroId);

	/**
	 * 根据角色资源操作ID来查询操作数据权限信息
	 * @param rroId 角色资源操作ID
	 * @return List<ResPropertyShow> 操作数据权限对象List
	 */
	public List<ResPropertyShow> getOdpShowByRroID(String rroId);

	/**
	 * 根据指定格式的SQL查询出数据信息字典
	 * SQL格式：select id,name from *** where *** 其中id和name不是固定的2个字段名 只是位置固定的2个字段 前面存放ID后面存放字典显示值
	 * @param sql 指定格式的SQL
	 * @return List<DataInfo> 数据信息List
	 */
	public List<DataInfo> getInfoBySql(String sql);
	
	/**
	 * 
	 * 如果roleId集合当中有父角色和该角色的子角色同时存在，则将子角色ID从该集合中移除
	 * @param roleId 角色ID集合
	 * @return List<String>
	 */
	public List<String> clearChildFrmList(List<String> roleId);
	
	/**
	 * 同步角色信息到PASM
	 * @return
	 */
	public boolean synchRoleToPasm();
	
	/**
	 * 获取所有的MenuTree
	 * @return 所有的MenuTree
	 */
	public List<MenuDtree> getAllMenu();
}

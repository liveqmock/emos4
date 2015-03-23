package com.ultrapower.eoms.ultrasm.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.rquery.core.SqlResult;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;

/**
 * 权限管理接口类
 * 此服务主要实现将用户所有权限查询出来并放入缓存，以便取权限时效率更高
 * 用户权限包含菜单目录权限、资源操作权限、操作数据权限
 * @author 孙海龙
 */
public interface PrivilegeManagerService
{
	/**
	 * 根据用户ID、资源ID、操作ID来判断该用户是否拥有此操作
	 * @param userId 用户ID
	 * @param resId 资源ID
	 * @param opId 操作ID
	 * @return boolean 返回true或false
	 */
	public boolean isAllow(String userId, String resId, String opId);

	/**
	 * 根据用户ID、资源ID、操作ID来获取配置的数据权限所对应的SQL条件
	 * @param userId 用户ID
	 * @param resId 资源ID
	 * @param opId 操作ID
	 * @return SqlResult 返回条件存储对象
	 */
	public SqlResult getPrivilegeSql(String userId, String resId, String opId);
	
	/**
	 * 根据用户ID、资源ID、操作ID来获取配置的数据权限所对应的Map
	 * @param userId 用户ID
	 * @param resId 资源ID
	 * @param opId 操作ID
	 * @return Map 返回Map格式的权限数据
	 */
	public Map getPrivelegeMap(String userId, String resId, String opId);
	
	/**
	 * 根据用户ID、资源ID、操作ID来获取配置的数据权限Map数据集
	 * @param userId 用户ID
	 * @param resId 资源ID
	 * @param opId 操作ID
	 * @return Map 返回操作所对应数据权限
	 */
	public Map getOpData(String userId, String resId, String opId);

	/**
	 * 根据用户ID来获取该用户所拥有的导航栏菜单信息
	 * @param userId 用户ID
	 * @return List<MenuDtree> 返回菜单目录集合
	 */
	public List<MenuDtree> getNavigationMenu(String userId);

	/**
	 * 根据用户ID、导航菜单ID来获取导航菜单对应左侧目录树
	 * @param userId 用户ID
	 * @param menuId 导航栏菜单ID
	 * @return List<MenuDtree> 返回菜单目录集合
	 */
	public List<MenuDtree> getMenuByNavigationID(String userId, String menuId);

	/**
	 * 根据用户ID查询该用户所有权限并存入缓存
	 * @param userId 用户ID
	 */
	public void setPrivilegeToCache(String userId);
	
}

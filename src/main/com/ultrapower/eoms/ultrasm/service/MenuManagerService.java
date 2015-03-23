package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.MenuInfo;

/**
 * 菜单目录管理服务类
 * @author 孙海龙
 */
public interface MenuManagerService
{
	/**
	 * 验证节点标识是否唯一
	 * @param nodeMark 节点标识
	 * @param menuId 节点ID
	 * @return boolean 返回true或false体现该节点标识是否唯一
	 */
	public boolean isUnique(String nodeMark, String menuId);
	
	/**
	 * 根据节点ID获取节点名称
	 * @param menuId 节点ID
	 * @return String 返回节点名称
	 */
	public String getMenuNameById(String menuId);
	
	/**
	 * 根据节点ID获取节点路径
	 * @param menuId 节点ID
	 * @return 返回节点路径
	 */
	public String getNodePahtById(String menuId);
	
	/**
	 * 根据节点URL获取ID
	 * @param url 节点URL
	 * @return
	 */
	public String getIdByUrl(String url);
	
	/**
	 * 根据节点标识获取节点对象
	 * @param nodeMark 节点标识
	 * @return MenuInfo 返回节点对象
	 */
	public MenuInfo getMenuByMark(String nodeMark);
	
	/**
	 * 根据节点标识获取节点ID
	 * @param nodeMark 节点标识
	 * @return String 返回节点ID
	 */
	public String getMenuIdByMark(String nodeMark);
	
	/**
	 * 添加节点信息
	 * @param menu 节点对象
	 * @return String 返回节点ID
	 */
	public String addMenuInfo(MenuInfo menu);

	/**
	 * 根据ID删除节点信息
	 * 删除信息包括此节点和此节点所有子集节点信息和关系
	 * @param menuId 节点ID
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteMenuByID(String menuId);

	/**
	 * 根据标识删除节点信息
	 * 删除信息包括此节点和此节点所有子集节点信息和关系
	 * @param nodeMark 节点标识
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteMenuByMark(String nodeMark);
	
	/**
	 * 修改节点信息
	 * @param menu 节点对象
	 * @return String 返回节点ID
	 */
	public String updateMenuInfo(MenuInfo menu);

	/**
	 * 根据节点ID查询节点信息
	 * @param menuId 节点ID
	 * @return MenuInfo 返回节点对象
	 */
	public MenuInfo getMenuByID(String menuId);
	
	/**
	 * 根据节点ID查询该节点的顶级节点信息
	 * @param menuId 节点ID
	 * @return MenuInfo 返回节点对象
	 */
	public MenuInfo getNavigateMenuById(String menuId);
	
	/**
	 * 根据节点ID获取该节点直属的所有上级IDList
	 * @param menuId 节点ID
	 * @return List<String> 返回该节点ID的直属上级IDList
	 */
	public List<String> getParentidListById(String menuId);
	
	/**
	 * 根据节点IDList查询节点信息List
	 * @param menuIdList 节点IDList
	 * @return List<MenuInfo> 返回节点信息List
	 */
	public List<MenuInfo> getMenuByID(List menuIdList);
	
	/**
	 * 根据父节点ID查询该节点下的节点List
	 * @param menuId 节点ID
	 * @return List<MenuInfo> 返回节点信息List
	 */
	public List<MenuInfo> getMenuByParentID(String menuId);
	
	/**
	 * 添加用户菜单目录
	 * @param userId 用户ID
	 * @param menuIdList 节点IDList
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addUserMenu(String userId, List menuIdList);
	
	/**
	 * 根据用户菜单ID来删除用户菜单目录
	 * @param umIdList 用户菜单IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteUserMenuById(List umIdList);
}

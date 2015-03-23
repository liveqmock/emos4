package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.MyMenu;

/**
 * 我的菜单服务类
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jul 14, 2010 2:27:57 PM
 */
public interface MyMenuManagerService {

	/**
	 * 添加我的菜单信息项
	 * @param myMenu
	 */
	public String addMymenu(MyMenu myMenu);
	
	/**
	 * 根据父Id,获取该节点下的根节点列表
	 * @param parentid
	 * @return
	 */
	public List<MyMenu> getList(String parentid,String userid);
	
	/**
	 * 根据ID获取我的菜单的信息
	 * @param myMenuId
	 * @return
	 */
	public MyMenu getMyMenuByID(String myMenuId);
	
	/**
	 * 修改我的菜单信息
	 * @param myMenu
	 * @return
	 */
	public String updateMymenu(MyMenu myMenu);
	
	/**
	 * 根据ID删除我的菜单节点信息
	 * @param myMenuId
	 * @return
	 */
	public boolean deleteMyMenuByID(String myMenuId);
	
	public String getMyMenuListHtml(String userid);
}

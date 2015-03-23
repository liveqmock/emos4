package com.ultrapower.eoms.ultrabpp.develop.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.UserDep;


public interface AssignTreeConfigService
{
	/**
	 * 保存子节点
	 * @return
	 */
	public void saveChildren(String org,String configIDTemp);
	
	/**
	 * 根据userDep同步自定义派发树的人员
	 * @param userDepList
	 */
	public boolean addOragnizeModel(List<UserDep> userDepList);
}

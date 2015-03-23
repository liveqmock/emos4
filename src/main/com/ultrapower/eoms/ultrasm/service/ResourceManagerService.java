package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.ResProperty;
import com.ultrapower.eoms.ultrasm.model.Resource;

/**
 * 资源管理服务
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 8, 2010 6:13:15 PM
 */
public interface ResourceManagerService {

	/**
	 * 查询资源列表信息
	 * @return 返回资源对象集合
	 */
	public List<Resource> getResource();
	
	/**
	 * 查询资源列表信息
	 * @param name 资源名称
	 * @param parentid 父资源ID
	 * @param systemtype 系统分类
	 * @param defineType 自定义分类
	 * @return 返回资源对象集合
	 */
	public List<Resource> getResource(String name,String parentid,String systemtype,String defineType);
	
	/**
	 * 查询资源明细
	 * @param pid 资源标识
	 * @return 返回资源对象
	 */
	public Resource getResource(String pid);
	
	
	/**
	 * 添加资源信息
	 * @param resource 资源信息实体
	 */
	public void addResource(Resource resource);
	
	
	/**
	 * 添加资源信息,同时添加该资源的属性配置信息
	 * @param resource 资源信息实体
	 * @param resProperyList 资源属性配置信息集合
	 */
	public void addResource(Resource resource,List<ResProperty> resProperyList);
	
	/**
	 * 修改资源、资源属性
	 * @param resource 资源对象资源信息实体
	 * @param addResPropertyList 添加的资源属性集合
	 * @param modResPropertyList 修改的资源属性集合
	 * @param delResPropertyId 删除的资源主键id
	 */
	public void updateResource(Resource resource,List<ResProperty> addResPropertyList,List<ResProperty> modResPropertyList,List delResPropertyId);
	
	/**
	 * 查询资源列表信息
	 * @param resourcePid 资源标识
	 * @return 返回资源对象集合
	 */
	public List<ResProperty> getResProperty(String resourcePid);
	
	
	/**
	 * 查询资源信息
	 * @param resourcePid 资源标识
	 * @return 返回资源信息
	 */
	public ResProperty getResPropertyInfo(String resourcePid);
	
	/**
	 * 修改资源信息
	 * @param resource 资源信息实体
	 */
	public void updateResource(Resource resource);
	
	/**
	 * 删除资源,同时删除资源所拥有的属性配置
	 * @param pids 资源id集合 eg: '2','2'
	 */
	public void setResourceStatus(String pids);
	
	/**
	 * 删除资源
	 * @param resIdList 资源标识集合
	 */
	public boolean deleteResourceById(List resIdList);
}

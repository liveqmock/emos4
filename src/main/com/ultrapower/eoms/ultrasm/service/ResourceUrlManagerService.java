package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.ResourceUrl;

/**
 * 该类提供资源操作URL的查询和维护服务
 * @author RenChenglin
 *
 */
public interface ResourceUrlManagerService {

	/**
	 * 根据url获取对应的资源URL对象（主要信息是资源和操作ID）
	 * @param url 访问的url链接
	 * @return 资源操作URL对象
	 */
	public ResourceUrl getResourceUrlByUrl(String url);
	
	/**
	 * 根据Id获取对应的资源URL对象（主要信息是资源和操作ID）
	 * @param pid 资源URL对象Id
	 * @return 资源操作URL对象
	 */
	public ResourceUrl getResourceUrlById(String pid);
	
	/**
	 * 保存资源操作RUL对象
	 * @param resUrl 资源操作URL对象
	 * @return 布尔值。true-成功；false-失败
	 */
	public boolean saveResourceUrl(ResourceUrl resurl);
	
	/**
	 * 添加资源操作URL对象
	 * @param resurl 资源操作URL对象
	 * @return 布尔值。true-成功；false-失败
	 */
	public boolean addResourceUrl(ResourceUrl resurl);
	
	/**
	 * 修改资源操作URL对象
	 * @param resurl 资源操作URL对象
	 * @return 布尔值。true-成功；false-失败
	 */
	public boolean updateResourceUrl(ResourceUrl resurl);
	
	/**
	 * 根据id集合删除资源操作URL对象信息
	 * @param idList 资源操作URL对象id集合
	 * @return 布尔值。true-成功；false-失败
	 */
	public boolean delResUrlByIdList(List<String> idList);
	
	/**
	 * 根据资源ID删除资源URL对象
	 * @param resIdList 资源Id的List集合
	 * @return 布尔值。true-成功；false-失败
	 */
	public boolean delResUrlByResId(List resIdList);
	
	/**
	 * 根据操作ID删除资源URL对象
	 * @param opIdList 对象id的List集合
	 * @return 布尔值。true-成功；false-失败
	 */
	public boolean delResUrlByOpId(List opIdList);
	
	/**
	 * 根据资源操作URL对象id集合启用资源URL对象
	 * @param idList 资源操作Url对象Id集合
	 * @return 布尔值。true-成功；false-失败
	 */
	public boolean enableResUrl(List<String> idList);
	
	/**
	 * 根据资源操作URL对象id集合停用资源URL对象
	 * @param idList 资源操作Url对象Id集合
	 * @return 布尔值。true-成功；false-失败
	 */
	public boolean disableResUrl(List<String> idList);
}

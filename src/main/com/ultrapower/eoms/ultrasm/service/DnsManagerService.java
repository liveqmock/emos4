package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

/**
 * DNS管理服务类
 * 此类主要提供了DN和DNS的创建、修改以及相关上下级处理通用的服务
 * @author 孙海龙
 */
public interface DnsManagerService
{
	/**
	 * 获取当前级别DNS
	 * @param tableName 数据库表名 如：bs_t_sm_dep
	 * @param dnsFiledName DNS字段名 	如：depdns
	 * @param dnFieldName DN字段名 如：depdn
	 * @param prevId 上级ID 具体值
	 * @return String 返回DNS
	 */
	public String getCurrentDns(String tableName, String dnsFieldName, String dnFieldName, String prevId);
	
	/**
	 * 获取当前级别DN
	 * @param tableName 数据库表名 如：bs_t_sm_dep
	 * @param dnFieldName DN字段名 如：depdn
	 * @param prevId 上级ID 具体值
	 * @return String 返回DN
	 */
	public String getCurrentDn(String tableName, String dnFieldName, String prevId);
	
	/**
	 * 存在关系时获取当前级别DNS
	 * 为数据字典模块使用
	 * @param tableName 数据库表名 如：bs_t_sm_dicitem
	 * @param dnsFieldName DNS字段名 如：dicdns
	 * @param dnFieldName DN字段名 如：dicdn
	 * @param prevId 上级ID 具体值
	 * @param relaFieldName 关系字段名 如：dtcode
	 * @param relaValue 关系值 如：status
	 * @return String 返回DNS
	 */
	public String getCurrentDns(String tableName, String dnsFieldName, String dnFieldName, String prevId, String relaFieldName, String relaValue);
	
	/**
	 * 存在关系时获取当前级别DN
	 * 为数据字典模块使用
	 * @param tableName 数据库表名 如：bs_t_sm_dicitem
	 * @param dnFieldName DN字段名 如：dicdn
	 * @param prevId 上级ID 具体值
	 * @param relaFieldName 关系字段名 如：dtcode
	 * @param relaValue 关系值 如：status
	 * @return String 返回DN
	 */
	public String getCurrentDn(String tableName, String dnFieldName, String prevId, String relaFieldName, String relaValue);
	
	/**
	 * 更新子集以及子集的子集...的DNS
	 * @param tableName 数据库表名 如：bs_t_sm_dep
	 * @param dnsFieldName DNS字段名 如：depdns
	 * @param oldDns 被修改字段原DNS 具体值
	 * @param newDns 被修改字段新DNS 具体值
	 * @return boolean 返回true或false体现是否更新成功
	 */
	public boolean updateSubDns(String tableName, String dnsFieldName, String oldDns, String newDns);
	
	/**
	 * 更新子集以及子集的子集...的全名
	 * @param tableName 数据库表名 如：bs_t_sm_dep
	 * @param dnsFieldName DNS字段名 如：depdns
	 * @param fullNameName 全名字段名 如：depfullname
	 * @param oldDns 被修改字段原DNS 具体值
	 * @param oldFullName 被修改字段原全名 具体值
	 * @param newFullName 被修改字段新全名 具体值
	 * @return boolean 返回true或false体现是否更新成功
	 */
	public boolean updateSubFullName(String tableName, String dnsFieldName, String fullNameName, String oldDns, String oldFullName, String newFullName);
	
	/**
	 * 存在关系时更新子集以及子集的子集...的全名
	 * 为数据字典模块使用
	 * @param tableName 数据库表名 如：bs_t_sm_dep
	 * @param dnsFieldName DNS字段名 如：depdns
	 * @param fullNameName 全名字段名 如：depfullname
	 * @param oldDns 被修改字段原DNS 具体值
	 * @param oldFullName 被修改字段原全名 具体值
	 * @param newFullName 被修改字段新全名 具体值
	 * @param relaFieldName 关系字段名 如：dtcode
	 * @param relaValue 关系值 如：字典类型编码
	 * @return boolean 返回true或false体现是否更新成功
	 */
	public boolean updateSubFullName(String tableName, String dnsFieldName, String fullNameName, String oldDns, String oldFullName, String newFullName, String relaFieldName, String relaValue);
	
	/**
	 * 更新子集以及子集的子集...的DNS和全名
	 * @param tableName 数据库表名 如：bs_t_sm_dep
	 * @param dnsFieldName DNS字段名 如：depdns
	 * @param fullNameName 全名字段名 如：depfullname
	 * @param oldDns 被修改字段原DNS 具体值
	 * @param oldFullName 被修改字段原全名 具体值
	 * @param newDns 被修改字段新DNS 具体值
	 * @param newFullName 被修改字段新全名 具体值
	 * @return boolean 返回true或false体现是否更新成功
	 */
	public boolean updateSubDnsAndFullName(String tableName, String dnsFieldName, String fullNameName, String oldDns, String oldFullName, String newDns, String newFullName);
	
	/**
	 * 根据ID获取该节点下的所有子集id列表
	 * @param tableName 数据库表名 如：bs_t_sm_dep
	 * @param pid 主键ID
	 * @return List 返回子集IDList
	 */
	public List getSubIdList(String tableName, String pid);
	
	/**
	 * 根据DNS获取该DNS顶级到本级的所有ID
	 * @param tableName 数据库表名 如：bs_t_sm_dep
	 * @param dnsFieldName DNS字段名 如：depdns
	 * @param dns DNS值
	 * @return List 返回IDList
	 */
	public List getParentIdListByDns(String tableName, String dnsFieldName, String dns);
}

package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

/**
 * 数据移植管理服务类
 * @author 孙海龙
 */
public interface DataShiftService
{
	/**
	 * 组织机构数据移植
	 * 处理内容：用户信息表（密码加密、行政部门名称、所属组名称）
	 *         部门信息表（创建部门的DN和DNS）
	 * @return List<String> 返回处理错误信息 如果List为空则说明处理时未出现错误
	 */
	public List<String> organizationShift();

	/**
	 * 创建部门的DN和DNS
	 * @return
	 */
	public List<String> createDepDns();
	/**
	 * 根据配置的表名导出初始化数据 .sql文件
	 * @param downLoadPath 导出详细地址 如："D:/sm_data.sql"
	 * @param tbNameList 表名List
	 * @return boolean 返回true或false体现是否导出成功
	 */
	public boolean exportInitData(String downLoadPath, List<String> tbNameList);
	
	/**
	 * 双数据库数据实时同步
	 * 通过中间表并在中间表中做标识信息来实现双数据库数据实时同步
	 * @return
	 */
	public void updateByMidTable();
	
	/**
	 * 更新节点路径字段信息
	 * @return
	 */
	public boolean updateNodepath();
	
	/**
	 * 更新所有人员的拼音名字
	 * @return
	 */
	public boolean updateUserPyName();
}

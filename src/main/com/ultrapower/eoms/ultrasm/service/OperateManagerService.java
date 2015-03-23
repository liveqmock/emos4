package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.Operate;

/**
 * 该服务提供对操作信息的增删改查功能
 * @author RenChenglin
 *
 */
public interface OperateManagerService {

	/**
	 * 检查操作id是否唯一
	 * @param opId 操作id
	 * @return 布尔值。true-唯一，可以使用；false-不唯一，不可以使用
	 */
	public boolean isUnique(String opId);
	
	/**
	 * 保存操作对象信息
	 * @param operate 操作对象
	 * @return 布尔值。true-成功；false-失败
	 */
	public boolean saveOperateInfo(Operate operate);
	
	
	/**
	 * 根据操作id集合删除多个操作信息
	 * @param opIdList 操作id的List集合
	 * @return 布尔值。true-成功；false-失败
	 */
	public boolean deleteOperateById(List opIdList);
	
	/**
	 * 通过操作ID获取操作对象
	 * @param opId	操作ID
	 * @return 操作对象
	 */
	public Operate getOperateByID(String opId);
	
	/**
	 * 根据操作IDList获取操作对象List
	 * @param opIdList	操作IDList
	 * @return 操作对象集合
	 */
	public List<Operate> getOperateByID(List opIdList);
	
	public List<Operate> getAllOperate();
}

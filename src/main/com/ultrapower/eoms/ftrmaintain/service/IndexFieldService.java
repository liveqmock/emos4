package com.ultrapower.eoms.ftrmaintain.service;

import java.util.List;

import com.ultrapower.eoms.ftrmaintain.model.IndexFieldCfg;

public interface IndexFieldService {

	/**
	 * 根据字段ID获得字段对象
	 * @param pid
	 * @return
	 */
	public IndexFieldCfg getFieldById(String pid);
	
	/**
	 * 保存字段定义
	 * @param field
	 * @return
	 */
	public boolean saveField(IndexFieldCfg field);
	
	/**
	 * 根据字段ID删除字段
	 * @param pid
	 * @return
	 */
	public boolean deleteField(List<String> pid);
}

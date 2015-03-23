package com.ultrapower.eoms.ultrasla.service;

import com.ultrapower.eoms.ultrasla.model.SlaDefine;

/**
 * @author RenChenglin
 * @date 2011-11-15 下午03:43:49
 * @version
 */
public interface ISlaDefineService {
	/**
	 * 保存SLA定义
	 * @param slaDefine
	 * @return
	 */
	public String save(SlaDefine slaDefine);
	
	/**
	 * 根据ID删除SLA定义
	 * @param id
	 * @return
	 */
	public int deleteById(String id);
	
	/**
	 * 根据ID获得SLA定义
	 * @param id
	 * @return
	 */
	public SlaDefine get(String id);
	
	/**
	 * 通过工单SCHEMA获得
	 * @param baseSchema
	 * @return
	 */
	public SlaDefine getBySchema(String baseSchema);
	
	/**
	 * 根据工单类别删除
	 * @param baseSchema
	 * @return
	 */
	public int deleteBySchema(String baseSchema);
}

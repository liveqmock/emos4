package com.ultrapower.eoms.common.core.component.sla.service;

import com.ultrapower.eoms.common.core.component.sla.model.SlaMailAction;

/**
 * SLA关于邮件动作的服务提供者
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-30 下午04:43:27
 */
public interface SlaMailActionService {

	/**
	 * 添加邮件动作业务
	 * @param slaMailAction 邮件动作业务信息
	 * @return 返回true 或 false
	 */
	public boolean addSlaMailAction(SlaMailAction slaMailAction);
	
	/**
	 * 更新邮件动作业务
	 * @param slaMailAction 邮件动作业务信息
	 * @return 返回true 或 false
	 */
	public boolean updateSlaMailAction(SlaMailAction slaMailAction);
	
	/**
	 * 删除邮件动作业务
	 * @param id 唯一标识
	 * @return 返回true 或 false
	 */
	public boolean deleteSlaMailAction(String id);
	
	/**
	 * 获取邮件动作业务信息
	 * @param id 邮件动作业务标识
	 * @return 邮件动作业务信息对象
	 */
	public SlaMailAction get(String id);
	
	/**
	 * 根据动作标识是否唯一
	 * @param actionmark 动作标识
	 * @return 返回true 或 false
	 */
	public boolean uniqueMark(String actionmark);
}

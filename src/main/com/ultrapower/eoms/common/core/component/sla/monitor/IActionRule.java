package com.ultrapower.eoms.common.core.component.sla.monitor;

/**
 * 
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-10 下午02:59:59
 */
public interface IActionRule {
	
	/**
	 * 
	 * @param p_threadNO 线程号
	 */
	public void execute(final long p_threadNO);
}

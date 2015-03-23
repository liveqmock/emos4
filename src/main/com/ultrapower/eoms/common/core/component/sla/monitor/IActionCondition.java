package com.ultrapower.eoms.common.core.component.sla.monitor;

import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;

/**
 * 业务数据接口
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-10 下午03:07:12
 */
public interface IActionCondition {

	/**
	 * 根据sql进行业务数据查询
	 * @param sql
	 * @param ruleStr
	 * @return
	 */
	public List<DataRow> matchSqlResult(final String sql,final String ruleStr);
	
	/**
	 * 根据源数据和规则进行业务数据查询
	 * @param objList
	 * @param ruleStr
	 * @return
	 */
	public List<DataRow> matchRuleResult(final List<Object> objList,final String ruleStr);
}

package com.ultrapower.eoms.common.core.component.sla.service;

import java.util.List;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-13 上午09:37:56
 */
public interface SlaMatchDataSourceService {

	/**
	 * 获取数据
	 * @return  Object 目前只支持 DaTaRow 和 Map 两种对象
	 */
	public List<Object> getDataSource();
}

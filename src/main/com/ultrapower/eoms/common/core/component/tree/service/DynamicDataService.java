package com.ultrapower.eoms.common.core.component.tree.service;

import java.util.List;

import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;

/**
 * 获取动态数据
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jul 1, 2010 10:57:03 AM
 */
public interface DynamicDataService {

	/**
	 * 通过id获取该节点下的节点集合
	 * @param parentid 父节点标识
	 * @return 返回节点集合
	 */
	public List<DtreeBean> getChildList(String parentid);
	
}

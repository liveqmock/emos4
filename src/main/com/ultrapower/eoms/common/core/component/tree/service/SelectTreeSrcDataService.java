package com.ultrapower.eoms.common.core.component.tree.service;
/**
 * 定义左边选择树的源数据接口
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 30, 2010 11:27:47 AM
 */
public interface SelectTreeSrcDataService {

	/**
	 * 获取源数据对象
	 * @param par String类型参数
	 * @return 返回数据对象
	 */
	public Object getSourceDataObj(String par);
}

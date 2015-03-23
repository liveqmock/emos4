package com.ultrapower.eoms.ultrasla.service;

import java.util.HashMap;

/**
 * 消息内容动态参数KEY-VALUE查询接口
 * @author Administrator
 * 
 */
public interface ICustomParamService {

	/**
	 * 通过实现类获得KEY-VALUE
	 * @param param
	 * @param implClass
	 * @return
	 */
	public HashMap<String, String> getParamValFromImpl(HashMap param);
}

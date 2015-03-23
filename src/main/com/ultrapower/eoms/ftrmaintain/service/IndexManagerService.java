package com.ultrapower.eoms.ftrmaintain.service;

import java.util.List;

import com.ultrapower.eoms.ftrmaintain.model.SysParam;

public interface IndexManagerService {
	
	/**
	 * 获得配置的系统参数
	 * @return
	 */
	public List<SysParam> getSystemParam();
	
}

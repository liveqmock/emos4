package com.ultrapower.eoms.ultrasla.service;

import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.ultrasla.util.Receiver;

/**
 * 自定义组织结构人员获得接口
 * @author Administrator
 */
public interface ICustomOrganizationService {

	/**
	 * 通过实现类获得消息接受者信息
	 * @param param
	 * @param implclass
	 * @return
	 */
	public List<Receiver> getReceiverFromImpl(HashMap param);
}

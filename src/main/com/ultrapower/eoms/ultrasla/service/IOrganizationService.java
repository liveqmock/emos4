package com.ultrapower.eoms.ultrasla.service;

import java.util.List;

import com.ultrapower.eoms.ultrasla.util.Receiver;
import com.ultrapower.eoms.ultrasla.util.ConstantMark.OrgType;

/**
 * 组织机构接口
 * @author SunHailong
 */
public interface IOrganizationService {

	/**
	 * 获得消息接收者
	 * @param id
	 * @param type
	 * @param idtype 用户的id类型 1.loginName 2.id(非0,1)
	 * @return
	 */
	public List<Receiver> getReceiver(String id, OrgType type, int idtype);
}

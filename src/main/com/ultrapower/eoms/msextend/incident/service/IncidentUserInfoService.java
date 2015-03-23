package com.ultrapower.eoms.msextend.incident.service;

import java.util.ArrayList;

public interface IncidentUserInfoService {

	/**
	 * 根据用户名查找用户信息
	 * 
	 * @param userName
	 * @return
	 */
	public ArrayList<String> getUserinfoByName(String userName);

	/**
	 * 根据字典类型和字典分类值查找职位
	 * 
	 * @param dtcode
	 * @param divalue
	 * @return
	 */

	public String getUserPos(String dtcode, String divalue);

}

package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.BsTCmbcAlarmnote;

public interface BsTCmbcAlarmnoteService {
	/**
	 * 根据Id得到对象
	 */
	public BsTCmbcAlarmnote getBsTCmbcAlarmnote(String pid);
	
	/**
	 * 更新对象状态
	 */
	public boolean updateBsTCmbcAlarmnote(BsTCmbcAlarmnote bsTCmbcAlarmnote);
	
	/**
	 * 	根据pid，删除对象
	 */
	public boolean remove(String pid);
}

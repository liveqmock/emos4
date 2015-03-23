package com.ultrapower.eoms.cdbBusiness.service;

import com.ultrapower.eoms.ultrabpp.runtime.model.ClientCallDataModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.MenuModel;

public class ServiceCategorySelectExternal extends ServiceCategorySelect {

	@Override
	public ClientCallDataModel<MenuModel> getMenus() {
		parameter.put("forwho", "对外");
		return super.getMenus();
	}
	
}

package com.ultrapower.eoms.ultrabpp.model.component.datafield.selectfield;

import com.ultrapower.eoms.ultrabpp.runtime.model.ClientCallDataModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.MenuModel;

public interface SelectMenu {
	public ClientCallDataModel<MenuModel> getMenus();
}

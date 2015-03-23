package com.ultrapower.eoms.cdbBusiness.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.msextend.serverQuest.manager.CreatTreeImpl;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.selectfield.SelectMenu;
import com.ultrapower.eoms.ultrabpp.runtime.model.ClientCallDataModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.MenuModel;

public class ServiceCategorySelect implements SelectMenu{
	HashMap<String, String> parameter = new HashMap<String, String>();

	@Override
	public ClientCallDataModel<MenuModel> getMenus() {
		CreatTreeImpl creatTreeImpl = new CreatTreeImpl();
		parameter.put("releaseScope", WebApplicationManager.getUserSession().getGroupDepNames());
		DtreeBean dtreeBean = creatTreeImpl.getServiceCategoryDtreeBean(parameter);
		ClientCallDataModel<MenuModel> model = new ClientCallDataModel<MenuModel>();
		List<MenuModel> datas = new ArrayList<MenuModel>();
		transformDtreeBean2MenuModelList(datas,dtreeBean.getChildList());
		model.setData(datas);
		return model;
	}
	
	private void transformDtreeBean2MenuModelList(List<MenuModel> menuModels,List<DtreeBean> dtreeBeans){
		for(DtreeBean db : dtreeBeans){
			MenuModel menu = new MenuModel();
			menu.setValue(db.getText());
			menu.setText(db.getText());
			menu.setDnIDs(db.getUserdata().get("serverquestfullname"));
			menu.setDns(db.getUserdata().get("serverquestfullname"));
			menuModels.add(menu);
			List<DtreeBean> child = db.getChildList();
			if(child!=null && child.size()>0){
				List<MenuModel> subMenus = new ArrayList<MenuModel>(); 
				menu.setSubMenus(subMenus);
				transformDtreeBean2MenuModelList(subMenus,child);
			}
		}
	}

}

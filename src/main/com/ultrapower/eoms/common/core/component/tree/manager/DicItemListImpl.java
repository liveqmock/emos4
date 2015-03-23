package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.tree.service.SelectTreeSrcDataService;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;

public class DicItemListImpl implements SelectTreeSrcDataService {
	
	private DicManagerService dicManagerService;
	
	/**
	 * 取得岗位类型集合
	 * 
	 * @param par 值班室ID
	 */
	public Object getSourceDataObj(String par) {
		Map<String, String> itemMap = new LinkedHashMap<String, String>();
		List<DicItem> list = dicManagerService.getDicItemByDicType(par);
		if(list != null && list.size() > 0){
			for(DicItem di : list){
				itemMap.put(di.getPid(), di.getDiname());
			}
		}
		return itemMap;
	}

	public void setDicManagerService(DicManagerService dicManagerService) {
		this.dicManagerService = dicManagerService;
	}
}

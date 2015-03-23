package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.service.DynamicDataService;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;

public class TaskTypeSearchTreeImpl extends TreeManager implements DynamicDataService{
	
	private DicManagerService dicManagerService;
	
	/**
	 *  任务预约左侧树                               数据字典值
	 */
	public List<DtreeBean> getChildList(String pareid){
			 List<DtreeBean> list = new ArrayList();
			 HashMap map = null;
			 DtreeBean menuDtree = null;
			 if(pareid.split(",")!=null&&pareid.split(",").length<2){
				 List<DicItem> listDic = dicManagerService.getRootItsmByDicType("tasksort");
				 for(DicItem dicItem : listDic){
					 menuDtree = new DtreeBean();
					 menuDtree.setId("taskTypeTreeService,00"+dicItem.getDivalue());//数据字典值充当ID，两位
					 menuDtree.setText(dicItem.getDiname());
					 map = new HashMap();
					 map.put("url","ultratask/execTaskbookingList.action?tasksort="+dicItem.getDivalue());
					 menuDtree.setUserdata(map);
					 list.add(menuDtree);
				 }
			 }
			 return list;
	}
	
	public void setDicManagerService(DicManagerService dicManagerService) {
		this.dicManagerService = dicManagerService;
	}
	
}

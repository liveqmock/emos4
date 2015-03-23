package com.ultrapower.eoms.common.core.component.tree.service;

import java.util.List;

import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;

public interface TaskTypeSheetTreeService {
	
	/**
	 * 展示树形结构 
	 * @param parentid
	 * @return
	 */
	String getTaskTypeXml(String parentid);
}

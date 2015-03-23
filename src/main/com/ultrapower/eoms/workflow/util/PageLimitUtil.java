package com.ultrapower.eoms.workflow.util;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.support.PageLimit;

/**
 * 内存分页工具类
 * @version UltraPower-Eoms4.0-0.1
 * @author：liuchuanzu
 * @since jdk1.5
 * @date 2010-06-12
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 */
public class PageLimitUtil {

	
	/**
	 * 内存中给List分页
	 * @param list
	 * @return
	 */
	public static List pageLimit(List list){
		List guideList = new ArrayList();
		PageLimit pageLimit = PageLimit.getInstance();
		int page = pageLimit.getLimit().getPage();
		int pageSize = pageLimit.getLimit().getPageSize();
		int rowStart = (page-1) * pageSize;
		int rowEnd = page * pageSize;
		for(int i = rowStart;i<rowEnd;i++){
			if(list.size()<=i){
				break;
			}
			Object obj = list.get(i);
			guideList.add(obj);
		}
		pageLimit.getLimit().setRowAttributes(list.size(),pageLimit.getCURRENT_ROWS_SIZE());
		return guideList;
	}
}

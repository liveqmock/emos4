package com.ultrapower.eoms.common.plugin.datagrid.util;

import javax.servlet.http.HttpServletRequest;

import com.ultrapower.eoms.common.plugin.datagrid.grid.GridLimit;
import com.ultrapower.eoms.common.plugin.datagrid.grid.Limit;

public class RequestUtils {

	public static Limit getPageLimit(HttpServletRequest request)
	{
		GridLimit gridLimit=new GridLimit(request,1);
		return gridLimit;
	}
	public static Limit getWebLimit(HttpServletRequest request)
	{
		GridLimit gridLimit=new GridLimit(request,2);
		return gridLimit;
	}
	
}

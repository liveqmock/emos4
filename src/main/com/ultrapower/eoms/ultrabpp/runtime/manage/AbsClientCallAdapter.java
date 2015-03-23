package com.ultrapower.eoms.ultrabpp.runtime.manage;

import java.util.Map;

import net.sf.json.JSONArray;

import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.ultrabpp.runtime.model.ClientCallDataModel;
import com.ultrapower.eoms.ultrabpp.runtime.service.ClientCallAdapterService;

public abstract class AbsClientCallAdapter implements ClientCallAdapterService
{
	public String call(String serviceName, String methodName, Map<String, String> parameters)
	{
		ClientCallDataModel<?> dataModel = call(methodName, parameters);
		
		String returnStr = "";
		if(dataModel.getReturnType().equals(ClientCallDataModel.DataType.List))
		{
			JSONArray json = JSONArray.fromObject(dataModel.getRe_ListData());
			returnStr = json.toString();
		}
		else if(dataModel.getReturnType().equals(ClientCallDataModel.DataType.Map))
		{
			JSONArray json = JSONArray.fromObject(dataModel.getRe_MapData());
			returnStr = json.toString();
		}
		else if(dataModel.getReturnType().equals(ClientCallDataModel.DataType.Table))
		{
			DataTable table = dataModel.getRe_TableData();
			JSONArray json = JSONArray.fromObject(table.getRowList());
			returnStr = json.toString();
		}
		else if(dataModel.getReturnType().equals(ClientCallDataModel.DataType.Bean))
		{
			JSONArray json = JSONArray.fromObject(dataModel.getre_BeanData());
			returnStr = json.toString();
		}
		else
		{
			returnStr = dataModel.getRe_StringData();
		}
		
		return returnStr;
	}
	
	public abstract ClientCallDataModel<?> call(String methodName, Map<String, String> parameters);
}

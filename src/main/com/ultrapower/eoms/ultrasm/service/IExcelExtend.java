package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.ultrasm.model.ImpTableConfig;

public interface IExcelExtend {

	
	public void extendImpRun(List<String> errorInfoList,ImpTableConfig impTableConfig,DataTable excelDt);
	public void extendExpRun(List<String> errorInfoList,ImpTableConfig impTableConfig);
}

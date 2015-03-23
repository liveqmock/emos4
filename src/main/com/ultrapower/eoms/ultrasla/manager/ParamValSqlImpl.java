package com.ultrapower.eoms.ultrasla.manager;

import java.util.HashMap;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;

public class ParamValSqlImpl {
	/**
	 * 通过SQL查询获得KEY-VALUE
	 * @param param
	 * @param sqlName
	 * @return
	 */
	public HashMap<String, String> getParamValFromSql(final HashMap param, String sqlName) {
		HashMap<String, String> paramMap = null;
		if(sqlName == null || "".equals(sqlName)) {
			return paramMap;
		}
		RQuery rquery = new RQuery(sqlName, param);
		DataTable table = rquery.getDataTable(); // 获取SQL数据集
		int tableLen = 0;
		if(table != null) {
			tableLen = table.length();
		}
		DataRow row = tableLen > 0 ? table.getDataRow(0) : null;
		int rowLen = 0;
		if(row != null) {
			rowLen = row.length();
			paramMap = new HashMap<String, String> ();
		}
		// 以下为封装参数列表信息代码
		for (int i=0 ; i<rowLen ; i++) {
			row.getString(i);
			paramMap.put(row.getKey(i), row.getString(i));
		}
		return paramMap;
	}
}

package com.ultrapower.eoms.ultrasla.custom;

import java.util.HashMap;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasla.service.ICustomParamService;

public class FormCustomParamImpl implements ICustomParamService {

	public HashMap<String, String> getParamValFromImpl(HashMap param) {
		if(param == null) {
			return param;
		}
		String baseSchema = StringUtils.checkNullString(param.get("BASESCHEMA"));
		String baseId = StringUtils.checkNullString(param.get("BASEID"));
		if("".equals(baseSchema) || "".equals(baseId)) {
			return param;
		}
		DataTable fieldTable = this.getFieldTable(baseSchema); // 获取工单对应的动态查询字段
		String paramSql = this.getParamQuerySql(fieldTable, baseSchema); // 获取参数查询SQL
		DataTable paramTable = this.getDataBySql(paramSql, new Object[] {baseId});
		return this.getParamMap(paramTable, param);
	}
	
	private DataTable getFieldTable(String baseSchema) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.c650000003 fieldname, t.c650000004 fieldalias");
		sql.append("  from {WF:Config_BaseOwnFieldInfo} t");
		sql.append(" where t.c650000002 = ?"); // 工单类别
		sql.append("   and t.c650042018 = 1"); // SLA使用字段标识
		return this.getDataBySql(sql.toString(), new Object[] {baseSchema});
	}
	
	private String getParamQuerySql(DataTable fieldTable, String baseSchema) {
		StringBuffer sql = new StringBuffer();
		int tableLen = 0;
		if(fieldTable != null) {
			tableLen = fieldTable.length();
		}
		DataRow row;
		for(int i=0 ; i<tableLen ; i++) {
			row = fieldTable.getDataRow(i);
			if(i > 0) {
				sql.append(",");
			}
			sql.append("c");
			sql.append(row.getString("fieldname"));
			sql.append(" as ");
			sql.append(row.getString("fieldalias").toUpperCase());
		}
		if(sql.length() > 0) {
			sql.insert(0, "select ");
			sql.append(" from {");
			sql.append(baseSchema);
			sql.append("} where c1 = ?");
		}
		return sql.toString();
	}
	
	private HashMap<String, String> getParamMap(DataTable paramTable, HashMap param) {
		HashMap<String, String> paramMap = param;
		if(paramMap == null) {
			paramMap = new HashMap<String, String> ();
		}
		int tableLen = 0;
		if(paramTable != null) {
			tableLen = paramTable.length();
		}
		DataRow row = tableLen > 0 ? paramTable.getDataRow(0) : null;
		int rowLen = 0;
		if(row != null) {
			rowLen = row.length();
		}
		// 以下为封装参数列表信息代码
		for (int i=0 ; i<rowLen ; i++) {
			row.getString(i);
			paramMap.put(row.getKey(i), row.getString(i));
		}
		return paramMap;
	}
	
	private DataTable getDataBySql(String sql, Object[] obj) {
		DataTable table = null;
		QueryAdapter queryAdapter = new QueryAdapter();
		try {
			table = queryAdapter.executeQuery(SqlReplace.stringReplaceAllVar(sql.toString(), null), obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}
}

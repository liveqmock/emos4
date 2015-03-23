package com.ultrapower.eoms.common.core.component.sla.monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.component.rule.RuleException;
import com.ultrapower.eoms.common.core.component.rule.RuleExpression;

/**
 * 业务数据实现
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-13 上午10:28:10
 */
public class ActionConditionImpl implements IActionCondition{

	public static QueryAdapter queryAdapter = new QueryAdapter();
	public List<DataRow> matchRuleResult(final List<Object> objList,final String ruleStr) {
		List<DataRow> dataRowList = new ArrayList<DataRow>();
		int objListLen = 0;
		if(objList!=null)
			objListLen = objList.size();
		String strReplaceResult = "";
		for(int i=0;i<objListLen;i++){
			Object obj = objList.get(i);
			if(obj instanceof HashMap){
				strReplaceResult = SqlReplace.stringReplaceAllVar(ruleStr,(HashMap)obj);
			}else if(obj instanceof DataRow){
				strReplaceResult = SqlReplace.stringReplaceAllVar(ruleStr,((DataRow) obj).getRowHashMap());
			}else{
				RecordLog.printLog("非法数据类型!", RecordLog.LOG_LEVEL_ERROR);
				continue;
			}
			try {
				if(RuleExpression.execute(strReplaceResult).equals("true")){
					if(obj instanceof HashMap){
						HashMap objMap = (HashMap)obj;
						DataRow dataRow = new DataRow();
						Iterator it = objMap.keySet().iterator();
						while(it.hasNext()){
							String key = (String)it.next();
							Object value = objMap.get(key);
							dataRow.put(key, value);
						}	
						dataRowList.add(dataRow);
					}else if(obj instanceof DataRow){
						dataRowList.add((DataRow) obj);
					}else{}
				}
					
			} catch (RuleException e) {
				e.printStackTrace();
			}
		}
		return dataRowList;
	}

	public List<DataRow> matchSqlResult(final String sql,final String ruleStr) {
		List<DataRow> dataRowList = new ArrayList<DataRow>();
		if(sql=="")
			return null;
		StringBuffer p_sql = new StringBuffer(1024);
		p_sql.append(sql);
		if(ruleStr!="")
			p_sql.append(" and " + ruleStr);
		Object[] values = null;
		DataTable dataTable = null;
		try{
		    dataTable = queryAdapter.executeQuery(p_sql.toString(),values,2);
		}catch(Exception e){
			RecordLog.printLog("数据源匹配规则查询数据出错,查询脚本：" + p_sql, RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			dataRowList.add(dataRow);
		}
		return dataRowList;
	}
}

package com.ultrapower.eoms.common.core.component.sla.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import com.ultrapower.eoms.common.core.component.rquery.util.RConstants;
import com.ultrapower.eoms.common.core.component.sla.model.SqlConditionExp;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-13 下午02:27:37
 */
public class DataExpDeal {

	public static String getWhereExpr(final HashMap<String,List<SqlConditionExp>> map,final String dealmodel){
		Iterator<String> it = map.keySet().iterator(); 
		StringBuffer p_sql = new StringBuffer();
		while (it.hasNext()) { 
			if(!p_sql.toString().equals(""))
				p_sql.append(" or ");
			List<SqlConditionExp> sqlList =  (List<SqlConditionExp>) map.get(it.next()); 
			int sqlListLen = 0;
			if(sqlList!=null)
				sqlListLen = sqlList.size();
			String oneGroupExp = "";
			for(int row=0;row<sqlListLen;row++){
				if(!oneGroupExp.equals(""))
					oneGroupExp += " and ";
				SqlConditionExp exp = sqlList.get(row);
				String field = exp.getFieldid();
				String inputvaluetype = exp.getInputvaluetype();
				String operator = exp.getOperator();
				String value = exp.getValue();
				if("1".equals(operator))//操作符转换
					operator = "=";
				else if("2".equals(operator))
					operator = ">";
				else if("3".equals(operator))
					operator = "<";
				else if("4".equals(operator))
					operator = ">=";
				else if("5".equals(operator))
					operator = "<=";
				else if("6".equals(operator))
					operator = "<>";
				else if("7".equals(operator))
					operator = "like";
				else if("8".equals(operator))
					operator = "leftlike";
				else if("9".equals(operator))
					operator = "rightlike";
				else
					operator = "=";
				if(!value.equals("")){
					String values[]=value.split(RConstants.DATA_SPILT);
					oneGroupExp += getFieldExp(field,operator,values,inputvaluetype,dealmodel);
				}
			}
			if(oneGroupExp!="" && sqlListLen>1)
				oneGroupExp = " and (" + oneGroupExp + ")";
			p_sql.append(oneGroupExp);
		}
		return p_sql.toString();
	}
	
	
	public static String getFieldExp(String p_colname,final String p_operator,Object[] p_value,final String datatype,final String dealmodel){
		StringBuffer p_condition = new StringBuffer();
		String operator=StringUtils.checkNullString(p_operator).trim().toLowerCase();
		Object values[]=p_value;
		int valuesLen=0;
		if(values!=null)
			valuesLen=values.length;
		if(operator.equals(""))
			operator = "=";
		String value;
		for(int i=0;i<valuesLen;i++)
		{
			value=StringUtils.checkNullString(values[i]);
			if(!value.equals(""))
			{
				if(!p_condition.toString().equals(""))
				{
					p_condition.append(" or ");
				}
				if(datatype.equals("1") && dealmodel.equals("2"))//规则匹配
					p_colname = "'" + p_colname + "'";
				p_condition.append(p_colname);
				p_condition.append(" ");
				if(operator.equals(RConstants.OPERATOR_LEFT_LIKE) || operator.equals(RConstants.OPERATOR_RIGHT_LIKE) || operator.equals(RConstants.OPERATOR_LIKE)){
					p_condition.append("like");
				}else{
					p_condition.append(operator);
				}
				p_condition.append(" ");
				if(operator.equals(RConstants.OPERATOR_LIKE))
					p_condition.append("'%"+value+"%'");
				else if(operator.equals(RConstants.OPERATOR_LEFT_LIKE))
					p_condition.append("'"+value+"%'");
				else if(operator.equals(RConstants.OPERATOR_RIGHT_LIKE))
					p_condition.append("'%"+value+"'");
				else{
					if(datatype.equals("1")){//字符串
						p_condition.append("'"+value+"'");
					}else{//整型
						p_condition.append(value);
					}
				}
			}
		}
		String conditionStr = p_condition.toString();
		if(conditionStr!="" && valuesLen>1)
			conditionStr = "(" + conditionStr + ")";
		return conditionStr;
	}
}

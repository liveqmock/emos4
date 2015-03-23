package com.ultrapower.eoms.common.core.component.rquery.core;

import java.util.HashMap;
import java.util.List;

import org.jdom.Element;

import com.ultrapower.eoms.common.core.component.rquery.startup.StartUp;
import com.ultrapower.eoms.common.core.component.rquery.util.Parameters;
import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.util.StringUtils;

public class SqlQuery {
	
	 SqlResult sqlResult=null;
	 /**
	  * 
	  * @param sqlName
	  * @param p_indirectValues
	  * @param isLoadRequestValue 是否读取request中的参数数据到参数hashmap中 true 为读取
	  */
	 public SqlQuery (String sqlName ,HashMap p_indirectValues,boolean isLoadRequestValue)
	 {
		Object obj = null;
		if (StartUp.sqlmapElement != null)
		{
			obj = StartUp.sqlmapElement.get(sqlName);
		} 
		if (obj != null)//
		{
			Element sqlqueryElement = (Element) obj;
			init(sqlqueryElement,p_indirectValues,isLoadRequestValue);
		}
		
		
		
	 }
	public SqlQuery(Element sqlqueryElement,HashMap p_indirectValues,boolean isLoadRequestValue)
	{	
		init(sqlqueryElement,p_indirectValues,isLoadRequestValue);
	}
	
	private void init(Element sqlqueryElement,HashMap p_indirectValues,boolean isLoadRequestValue)
	{
		boolean loadVaue=true;
		HashMap indirectValues=p_indirectValues;
		if(isLoadRequestValue)
		{
			indirectValues=Parameters.CollectionParameter(p_indirectValues);
			loadVaue=false;
		}
		
		indirectValues=Parameters.CollectionParameter(indirectValues,sqlqueryElement);
		if(sqlqueryElement!=null)
		{
			parseSql(sqlqueryElement,indirectValues,loadVaue);
		}		
	}
	
	 
//	private void parseSql(XmlParser xml,HashMap reqeustValues,HashMap indrectValues)
	private void parseSql(final Element sqlqueryElement,HashMap indrectValues,boolean isLoadRequestValue)
	{
		Element element=null;
		List lstElement=sqlqueryElement.getChildren("select");
		if(lstElement!=null && lstElement.size()>0)
		{	
			element=(Element)lstElement.get(0);
		}
		String sql="";
		if(element!=null)
		{
			sql=StringUtils.checkNullString(element.getText()).trim();
		}
		if(sql.equals(""))
		{
			return ;
		}
		String orderby=StringUtils.checkNullString(indrectValues.get("orderby")).trim();
		if(!orderby.equals(""))
		{
			sql=replaceOrderbyString(sql,orderby);
		}	    
		//替换表名
		//sql=StringReplace.stringReplaceAllVar(sql, null, RConstants.DATA_VAR_TABLE);
		//sql=StringReplace.stringReplaceAllVar(sql, indrectValues);
	    CustWhere custWhere=new CustWhere(sqlqueryElement,indrectValues,isLoadRequestValue);
	    
	    sqlResult=SqlReplace.replaceAllVar(sql,indrectValues,custWhere);
		
		
	}
	/**
	 * 替换sql中的排序
	 * @param sql
	 * @param orderby
	 * @return
	 */
	private String replaceOrderbyString(String sql,String orderby) {
		String regstr = "/*ORDERBY*/";
		String strSelect = sql.toString();// "select /*COUNT*/ base.c1
													// /*COUNT*/ from ";//
		int startlen = strSelect.indexOf(regstr);
		// strSelect=strSelect.replaceFirst("/\\*COUNT\\*/","");
		int endlen = strSelect.lastIndexOf(regstr);
		if (startlen < 0 || endlen < 0)
			return sql;
		StringBuffer stringbuffer=new StringBuffer();
		stringbuffer.append(strSelect.substring(0, startlen));
		stringbuffer.append(orderby);
		stringbuffer.append(strSelect.substring(endlen + 11));
/*		String other=strSelect.substring(endlen + 11);
		
		if(!other.trim().equals(""))
		{
			stringbuffer.append(other);
		}	
*/		
		
		//strSelect = strSelect.substring(0, startlen) + " count(*) as ROWCOUNT "+ strSelect.substring(endlen + 11);
		return  stringbuffer.toString();
	}	
	
	/**
	 * 返回获得的sql对像
	 * @return
	 */
	public SqlResult getSql()
	{
		return sqlResult;
	}
	
	
	
	/**
	 * 替换条件变量
	 * @param sql
	 * @param custWhere
	 * @return
	 */
/*	public  SqlResult replaceAllWhereVar(String sql,CustWhere custWhere)
	{
		StringBuffer strinBuffer=new StringBuffer();
		String select=sql;
		String strSelect=sql;
//		List values=new ArrayList();
		String prepix="$";
				
    	//替换逻辑变量SQL
    	String name;
    	boolean isfind=false;
    	SqlResult sqlResultTemp;
    	SqlResult sqlResult=new SqlResult();
    	if(!prepix.equals(""))
    	{
    		isfind=true;
    	}
    	while(isfind)
    	{
	    	int startlen=strSelect.indexOf(prepix);
	    	if(startlen<0)
	    		break;
	    	strSelect=strSelect.replaceFirst("\\"+prepix,"");
	    	int endlen=strSelect.indexOf(prepix);
        	if(endlen<0)
        		break;	    	
	    	name=strSelect.substring(startlen,endlen);
	    	//name=remedyDBOp.GetRemedyTableName(strSelect);
	    	if(name.equals("privilege"))
	    	{
	    		//权限数据
	    		sqlResultTemp=ActionContext.getContext().getDataPrivilege();
	    	}
	    	else
	    	{
	    		sqlResultTemp=custWhere.getSqlResult(name);
	    	}
	    	String value="";
	    	if(sqlResultTemp!=null)
	    	{
	    		value=sqlResultTemp.getSql();
	    		sqlResult.appendValues(sqlResultTemp.getValues());
	    	}
	    	select=select.replaceFirst("\\"+prepix+name+"\\"+prepix,value);
	    	
	    	strSelect=select;
    	}
    	
    	sqlResult.appendSql(select);
    	
		return sqlResult;
	}	*/
	
	
	public static void main(String[] args)
	{
 
		String path="E:\\WorkSpace2\\eoms4\\WebRoot\\sqlconfig";
		StartUp.loadFile(path);
		HashMap hash=new HashMap();
		SqlQuery sqlQuery=new SqlQuery("SQL_QuerySimple_001",null,false);
		SqlResult sqlResult=sqlQuery.getSql();
		System.out.println("原始的SQL:"+sqlResult.getSql());
		List list=sqlResult.getValues();
		int lstCount=0;
		if(list!=null)
		{
			lstCount=list.size();
		}
		String sql=sqlResult.getSql();
		for(int j=0;j<lstCount;j++)
		{
			Object str=list.get(j);
			sql=sql.replaceFirst("\\?", "'"+str.toString()+"'");
		}
		System.out.println("完整的SQL:"+sql);		
	}
}

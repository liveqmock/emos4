package com.ultrapower.eoms.common.core.component.rquery.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.rquery.core.CustWhere;
import com.ultrapower.eoms.common.core.component.rquery.core.NextVar;
import com.ultrapower.eoms.common.core.component.rquery.core.SqlResult;
import com.ultrapower.eoms.common.core.util.ArrayTransferUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.portal.model.UserSession;

public class SqlReplace {
	/**
	 * 替换字符串，将字符串中的变量进行替换
	 * @param sql 字符串
	 * @param hashPar 值map
	 * @return
	 */
	public static String stringReplaceAllVar(final String sql,final HashMap indirectValues)
	{
		StringBuffer select=new StringBuffer();
    	//替换逻辑变量SQL
    	String varName;
//    	boolean isfind=true;
    	NextVar nextVar;
    	int startpos=0;
    	while(true)
    	{
    		nextVar=getNextVar(sql,startpos,0);
    		//name=strSelect.substring();
	    	//name=remedyDBOp.GetRemedyTableName(strSelect);
    		if(nextVar==null)
    		{
    			if(startpos<sql.length())
    			{
    				select.append(sql.substring(startpos));
    			}
    			break;
    		}
	    	String value="";
	    	varName=nextVar.getVarname();
	    	switch(nextVar.getVartype())
	    	{
	    		case RConstants.DATA_VAR_PAGE://替换#号变量
	    			value=getSystemVar(varName);
	    			if(value==null)
	    			{
	    		    	if(indirectValues!=null)
	    		    		value=StringUtils.checkNullString(indirectValues.get(varName));
	    			}
	    	    	select.append(sql.substring(startpos,nextVar.getStartpos()));
	    	    	select.append(value);
	    			break;
	    		case RConstants.DATA_VAR_TABLE://替换表的变量，ar的表转换成T表
	    	    	select.append(sql.substring(startpos,nextVar.getStartpos()));
	    	    	value=StringUtils.checkNullString(com.ultrapower.remedy4j.core.RemedySession.UtilInfor.getTableName(varName));
	    	    	select.append(value);
	    			break;
	    	}
	    	startpos=nextVar.getEndpos()+1;
	    	//select=select.replaceFirst("\\"+prepix+name+"\\"+prepix,"?");
    	}
    	if(startpos==0)
    		return sql;
    	else
    		return select.toString();
	}

	/**
	 * 将sql字符串替换成对应的sql对象
	 * @param sql
	 * @param indirectValues 传入的参数值
	 * @param custwhere where条件解析类，用于提供$$变量的数据
	 * @return
	 */
	public static SqlResult replaceAllVar(final String sql,final HashMap indirectValues,final CustWhere custwhere)
	{
		StringBuffer select=new StringBuffer();
		List values=new ArrayList();
		SqlResult sqlResult=new SqlResult();
		SqlResult sqlResultTemp;
    	//替换逻辑变量SQL
    	String varName;
//    	boolean isfind=true;
    	NextVar nextVar;
    	int startpos=0;
    	while(true)
    	{
    		nextVar=getNextVar(sql,startpos,0);
    		if(nextVar==null)
    		{
    			if(startpos<sql.length())
    			{
    				select.append(sql.substring(startpos));
    			}
    			break;
    		}
	    	String value="";
	    	varName=StringUtils.checkNullString(nextVar.getVarname());
	    	switch(nextVar.getVartype())
	    	{
	    		case RConstants.DATA_VAR_PAGE://替换#号变量
	    			value=getSystemVar(varName);
	    			if(value==null)
	    			{
	    		    	if(indirectValues!=null)
	    		    		value=StringUtils.checkNullString(indirectValues.get(varName));
	    			}
	    	    	select.append(sql.substring(startpos,nextVar.getStartpos()));
	    	    	if(("orderby").equals(varName))
	    	    	{
	    	    		select.append(value);
	    	    	}
	    	    	else
	    	    	{
	    	    		select.append("?");
	    	    		values.add(value);
	    	    	}
	    			break;
	    		case RConstants.DATA_VAR_TABLE://替换表的变量，ar的表转换成T表
	    	    	select.append(sql.substring(startpos,nextVar.getStartpos()));
	    	    	if(varName.indexOf("#")>-1)
	    	    	{
	    	    		varName=stringReplaceAllVar(varName,indirectValues);
	    	    	}
	    	    	value=StringUtils.checkNullString(com.ultrapower.remedy4j.core.RemedySession.UtilInfor.getTableName(varName));
	    	    	select.append(value);
	    			break;
	    		case RConstants.DATA_VAR_DIRECT://直接变量 只做替换
	    	    	select.append(sql.substring(startpos,nextVar.getStartpos()));
	    		    if(indirectValues!=null)
    		    		value=StringUtils.checkNullString(indirectValues.get(varName));
	    	    	select.append(value);
	    			break;	    			
	    		case RConstants.DATA_VAR_SYSTEM:
	    			select.append(sql.substring(startpos,nextVar.getStartpos()));
	    			if(!varName.equals(""))
	    			{
	    				sqlResultTemp=null;
	    				if(varName.equals("privilege"))
	    				{
	    		    		//权限数据
	    		    		sqlResultTemp=ActionContext.getContext().getDataPrivilege();
	    				}
	    				else if(custwhere!=null)
	    				{
	    					sqlResultTemp=custwhere.getSqlResult(varName);
	    					
	    					if(sqlResultTemp==null)
	    					{
	    						
	    					}
	    				}
	    				//sqlResultTemp=custwhere.getSqlResult(varName);
	    				if(sqlResultTemp!=null)
	    				{
	    					select.append(sqlResultTemp.getSql());
	    					try {
								values=ArrayTransferUtils.copyListSimple(values, sqlResultTemp.getValues());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	    				}
	    				else
	    				{
	    					//如果为空的话判断是不是在参数表中有，有的话直接替换.用于解决外面直接传sql的问题 2011-5-31
	    					if(indirectValues!=null)
	    					{
	    						select.append(StringUtils.checkNullString(indirectValues.get(varName)));
	    					}
	    				}
	    			}

	    			break;
	    	}
	    	startpos=nextVar.getEndpos()+1;
	    	//select=select.replaceFirst("\\"+prepix+name+"\\"+prepix,"?");
    	}
    	if(startpos>0)
    	{
    		sqlResult.appendSql(select.toString());
    		sqlResult.appendValues(values);
    	}
    	else
    	{
    		sqlResult.appendSql(sql);
    	}
		return sqlResult;
	}
	
	
	/**
	 * 获得系统变量 当前登录人姓名，当前登录人部门等
	 * @param varName
	 * @return
	 */
	public static String getSystemVar(final String varName)
	{
		String result=null;
		String var=varName.toLowerCase().trim();
		
		if(varName.equals(SystemVar.CURRENT_DATETIME))
		{
			return String.valueOf(System.currentTimeMillis()/1000);
		}
		UserSession userSession=ActionContext.getContext().getUserSession();
		if(userSession==null|| varName==null)
			return result;
		
		if(SystemVar.CURRENT_LOGINPID.equals(var))
		{
			result=userSession.getPid();
		}
		else if(SystemVar.CURRENT_LOGINNAME.equals(var))
		{
			result=userSession.getLoginName();
		}
		else if(SystemVar.CURRENT_LOGINFULLNAME.equals(var))
		{
			result=userSession.getFullName();
		}
		else if(SystemVar.CURRENT_DEPARTMENTPID.equals(var))
		{
			result=userSession.getDepId();
		}
		else if(SystemVar.CURRENT_DEPARTMENTPDNS.equals(var))
		{
			result=userSession.getDepDns();
		}		
		else if(SystemVar.CURRENT_DEPARTMENTPNAME.equals(var))
		{
			result=userSession.getDepName();
		}
		else if(SystemVar.CURRENT_GROUGPPID.equals(var))
		{
			result=userSession.getGroupId();
		}
		else if(SystemVar.CURRENT_GROUGPNAME.equals(var))
		{
			result=userSession.getGroupName();
		}
		else if(SystemVar.CURRENT_COMPANYID.equals(var))
		{
			result=userSession.getCompanyId();
		}
		else if(SystemVar.CURRENT_COMPANYNAME.equals(var))
		{
			result=userSession.getCompanyName();
		}
		else if(SystemVar.CURRENT_ROLEID.equals(var))
		{
			result=userSession.getRoleId();
		}
		else if(SystemVar.CURRENT_ROLENAME.equals(var))
		{
			result=userSession.getRoleName();
		}		
		return result;
	}
	
	/**
	 * 获取字符串中的下一个变量
	 * @param str 字符串
	 * @param start 开始位置
	 * @param end 结束位置
	 * @return
	 */
	public static NextVar getNextVar(final String str,int start,int end)
	{
		NextVar nextVar=null;
		if(str==null)
			return null;
    	int len=str.length();
    	int flag=0,startpos=0;
    	int vartype=0;
    	String varName;
    	char c;
    	if(start<0)
    		start=0;
    	if(end<=0)
    		end=len;
    	for(int charAt=start;charAt<end;charAt++)
    	{
    		c=str.charAt(charAt);
    		if(((flag==0 && c=='{')||(flag>0 && c=='}')) && (vartype==0 || vartype==RConstants.DATA_VAR_TABLE))
    		{
        		if(flag==0)
        		{
        			startpos=charAt;
        		}
        		vartype=RConstants.DATA_VAR_TABLE;
    			flag++;
    		}
    		else if(c=='#' && (vartype==0 || vartype==RConstants.DATA_VAR_PAGE))
    		{
        		if(flag==0)
        		{
        			startpos=charAt;
        		}
        		vartype=RConstants.DATA_VAR_PAGE;
    			flag++;
    		}
    		else if(c=='$' && (vartype==0 || vartype==RConstants.DATA_VAR_SYSTEM))
    		{
        		if(flag==0)
        		{
        			startpos=charAt;
        		}
        		vartype=RConstants.DATA_VAR_SYSTEM;
    			flag++;
    		}
    		else if(c=='@' && (vartype==0 || vartype==RConstants.DATA_VAR_DIRECT))
    		{
        		if(flag==0)
        		{
        			startpos=charAt;
        		}
        		vartype=RConstants.DATA_VAR_DIRECT;
    			flag++;    			
    		}
    		else
    			continue;
    		if(flag%2==0)
    		{
    			nextVar=new NextVar();
    			nextVar.setStartpos(startpos);
    			nextVar.setEndpos(charAt);
    			nextVar.setVartype(vartype);
    			varName=str.substring(startpos+1,charAt);
    			nextVar.setVarname(varName);
    			break;
    		}
    	}
		return nextVar;
	}
	public static void main(String[] args)
	{
		String sql="abd3#test# as #sys_datetime#r";
		
/*		StringReplace str=new StringReplace();
		NextVar var=str.getNextVar(sql, 0, 0);
		System.out.println(var.getVarname());
		System.out.println(var.getVartype());
		System.out.println(var.getStartpos());
		System.out.println(var.getEndpos());
*/		
//		str.replaceAllVar(sql, hashPar)
		
		HashMap h1=new HashMap();
		h1.put("test", " test ");
		h1.put("ss", "h2 bb");
		System.out.println("sq1: "+sql);
		String strr=stringReplaceAllVar(sql,h1);
		System.out.println("strr: "+strr);
		SqlResult sqlResult=replaceAllVar(sql,h1,null);
		System.out.println("sq1: "+sqlResult.getSql());
		List lst=sqlResult.getValues();
		for(int i=0;i<lst.size();i++)
		{
			System.out.println(i+": "+lst.get(i).toString());
		}
//		System.out.println("sq2: "+stringReplaceAllVar(sql,h1,RConstants.DATA_VAR_TABLE));
		//System.out.println("sq3: "+stringReplaceAllVar(sql,h1,RConstants.DATA_VAR_SYSTEM));
		
/*		HashMap h1=new HashMap(); 
		h1.put("aa", "h1 aa");
		h1.put("bb", "h1 bb");
		HashMap h2=new HashMap();
		h2.put("aa", "h2 aa");
		HashMap h3=null;
		h2.putAll(h1);
		h2.putAll(h3);
		
		h1.size();
		for(Iterator iter = h1.entrySet().iterator();iter.hasNext();)
		{
			Map.Entry element = (Map.Entry)iter.next();  
			Object strKey = element.getKey();  
			Object strObj = element.getValue();
			System.out.println (strKey.toString()+": "+strObj.toString());
		}*/
/*		char varChar='$';
		StringBuffer stringBuffer=new StringBuffer();
		String sql="aa $ddd$ as $ss$ $ddd";
    	int len=sql.length();
    	int flag=0,start=0,end=0;
    	String varName;
    	for(int charAt=0;charAt<len;charAt++)
    	{
    		if(sql.charAt(charAt)==varChar)
    		{
    			flag++;
    		}
    		else
    		{
    			continue;
    		}
    		if(flag%2==1)
    		{
    			stringBuffer.append(sql.substring(start,charAt));
    		}
    		else
    		{
    			varName=sql.substring(start,charAt);
    			System.out.println("varName: "+varName);
    			stringBuffer.append("ttt");
    			flag=0;
    		}
    		start=charAt+1;
    	}
    	System.out.println(stringBuffer.toString());*/
	}

	/**
	 * 根据字段排序的类型转换成排序的sql
	 * @param sortFileds 排序的字段 0是升序 否则为降序
	 * @param sortType
	 * @return
	 */
	public static String getOrderby(String sortFileds,int sortType)
	{
		String strRe="";
		if(!sortFileds.trim().equals(""))
		{
			//如果升序
			if(sortType==0)
				strRe=" order by "+sortFileds;
			else
			{
				String[] strAry =sortFileds.split(",");
				
				for(int index=0;index<strAry.length;index++)
				{	if(strRe.trim().equals(""))
						strRe+=" order by "+ strAry[index]+" desc";
					else
						strRe+=","+ strAry[index]+" desc";
				}
				//strRe=" order by "+strRe;
			}//if(OrderByType==0)
		}
		return strRe;	
	}
	
	
}

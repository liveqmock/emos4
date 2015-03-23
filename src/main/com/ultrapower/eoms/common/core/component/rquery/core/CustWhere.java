package com.ultrapower.eoms.common.core.component.rquery.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.jdom.Element;

import com.ultrapower.eoms.common.core.component.rquery.core.EnumUtil.NonSensitiveFieldEnum;
import com.ultrapower.eoms.common.core.component.rquery.datatransfer.DataTransfer;
import com.ultrapower.eoms.common.core.component.rquery.startup.StartUp;
import com.ultrapower.eoms.common.core.component.rquery.util.Parameters;
import com.ultrapower.eoms.common.core.component.rquery.util.RConstants;
import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.component.rule.RuleException;
import com.ultrapower.eoms.common.core.component.rule.RuleExpression;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;

public class CustWhere {
	
	private HashMap customWhere;
	//private HashMap reqeustValues;//ruequest中的值
	private HashMap indirectValues;//自定义传入的值
	//XmlParser xmlFile;
	private Element custwhereElement;
	
	/**
	 * 
	 * @param sqlName
	 * @param p_indirectValues
	 * @param isLoadRequestValue 是否读取request中的参数数据到参数hashmap中 true 为读取
	 */
	public CustWhere (String sqlName ,HashMap p_indirectValues,boolean isLoadRequestValue)
	{
		Object obj=null;
		if(StartUp.sqlmapElement!=null)
			obj=StartUp.sqlmapElement.get(sqlName);
		if(obj!=null)
		{
			this.custwhereElement=(Element)obj;
		}
		if(isLoadRequestValue)
		{
			this.indirectValues=Parameters.CollectionParameter(p_indirectValues);
		}
		else
		{
			this.indirectValues=p_indirectValues;
		}
	}
	
	public CustWhere(Element custwhereElement,HashMap p_indirectValues,boolean isLoadRequestValue)
	{
		//转换为大小写不敏感
		for (NonSensitiveFieldEnum nfEnum :NonSensitiveFieldEnum.values()) {
			if (p_indirectValues.containsKey(nfEnum.toString())){
				p_indirectValues.put(nfEnum.toString(), p_indirectValues.get(nfEnum.toString()).toString().toUpperCase()); 
			}
		}
		
//		this.reqeustValues=reqeustValues;
		this.indirectValues=p_indirectValues;
		this.custwhereElement=custwhereElement;
		//initParameter(indirectValues);
		if(isLoadRequestValue)
		{
			this.indirectValues=Parameters.CollectionParameter(p_indirectValues);
		}
		//
	}
	
	
	private void init()
	{
		
	}

	/**
	 * 返回全部的查询条件
	 * @return
	 */
	public SqlResult getSqlResult()
	{
		return getSqlResult("");
	}
	/**
	 * 根据相应的customewhere的name返回对应的sql
	 * @param whereName
	 * @return
	 */
	public SqlResult getSqlResult(String whereName)
	{
		if(this.customWhere==null)
			this.buildSqlResult();
		SqlResult sqlResult=null;
		whereName=StringUtils.checkNullString(whereName);
		if(!whereName.equals(""))
		{
			Object obj=customWhere.get(whereName);
			if(obj!=null)
			{
				sqlResult=(SqlResult)obj;
			}
		}
		else
		{
			//取全部的条件
			sqlResult=new SqlResult();
			Iterator it = customWhere.entrySet().iterator();
			SqlResult tempSql;
			while (it.hasNext())
			{ 
				Entry entry =  (Entry)it.next();  
				tempSql=(SqlResult)entry.getValue(); 
				sqlResult.appendSql( tempSql.getSql());
				sqlResult.appendValues(tempSql.getValues());
			}
		}
		return sqlResult;
	}
 	
	/**
	 * 解析custowhere
	 */
	private void buildSqlResult()
	{
		customWhere=new HashMap();
//		SqlResult sqlResult=new SqlResult();
		SqlResult tempSql;
		List lstAction=null;
		String actionName;
		if(custwhereElement!=null)
		{
			lstAction=custwhereElement.getChildren("customwhere");
		}
		int actLen=0;
		if(lstAction!=null)
		{
			actLen=lstAction.size();
		}
		String whereName;
		String prepend;
		Element element;
		for(int i=0;i<actLen;i++)
		{
			element=(Element)lstAction.get(i);
			whereName=StringUtils.checkNullString(element.getAttributeValue("name"));
			prepend=StringUtils.checkNullString(element.getAttributeValue("prepend"));
			
			tempSql=this.getNextLevelSqlResult(element);
			
			if(tempSql!=null)
			{
				StringBuffer sql=new StringBuffer();
				sql.append(" ");
				sql.append(prepend);
				sql.append(" ");
				sql.append("(");
				sql.append(tempSql.getSql());
				sql.append(")");
				tempSql.setSql(sql.toString());
				customWhere.put(whereName, tempSql);
			}
			//System.out.println(element.getName());
		}
//		return sqlResult;
	}
	
	/**
	 * 对括号进行解析
	 * @param element
	 * @return
	 */
	private SqlResult getParentheses(Element element)
	{
		SqlResult sqlResult=null;
		StringBuffer sql=new StringBuffer();
		String prepend="";
		if(element!=null) 
		{
			prepend=StringUtils.checkNullString(element.getAttributeValue("prepend"));
		}
		SqlResult sqlResultTemp=getNextLevelSqlResult(element);
		if(sqlResultTemp!=null)
			sql.append(sqlResultTemp.getSql());
		if(!sql.toString().equals(""))
		{
			sqlResult=new SqlResult();
			sqlResult.appendSql(" ");
//			sqlResult.appendSql(prepend);
			sqlResult.appendSql("(");
			sqlResult.appendSql(sql.toString());
			sqlResult.appendSql(")");
			sqlResult.appendValues(sqlResultTemp.getValues());
		}
		return sqlResult;
	}
	
	/**
	 * 对节点内的下一级action进行再解析。
	 * @param element
	 * @return
	 */
	private SqlResult getNextLevelSqlResult(Element element)
	{
		SqlResult sqlResult=null;
		List lstAction=null;
		StringBuffer sql=new StringBuffer();
		
		List values=new ArrayList();
		SqlResult tempSql;
		String prepend="";
		if(element!=null)
		{
			lstAction=element.getChildren();
			prepend=StringUtils.checkNullString(element.getAttributeValue("prepend"));
		}
		int actLen=0;
		if(lstAction!=null)
		{
			actLen=lstAction.size();
		}
		String actionName;
		Element nextElement;
		String nextPrepend;
		String strSql;
		for(int i=0;i<actLen;i++)
		{
			tempSql=null;
			nextElement=(Element)lstAction.get(i);
			actionName=nextElement.getName().trim();
			if(actionName.equals(RConstants.E_ACT_FIELD))
			{
				tempSql=getFiled(nextElement);
			}
			else if(actionName.equals(RConstants.E_ACT_PARENTHESES))
			{
				tempSql=getParentheses(nextElement);
			}
			else if(actionName.equals(RConstants.E_ACT_SWITCH))
			{
				tempSql=getSwitch(nextElement);
			}
			else if(actionName.equals(RConstants.E_ACT_IF))
			{
				tempSql=this.getIf(nextElement);
			}
			else if(actionName.equals(RConstants.E_ACT_SQL))
			{
				tempSql=this.getSql(nextElement);
			}
			strSql="";
			if(tempSql!=null)
			{
				strSql=tempSql.getSql().trim();
			}
			if(!strSql.equals(""))
			{
				if(sql.toString().equals(""))
				{
					sql.append( tempSql.getSql());
				}
				else
				{
					nextPrepend=StringUtils.checkNullString(nextElement.getAttributeValue("prepend"));
					sql.append(" ");
					sql.append(nextPrepend);
					sql.append(" ");
					sql.append( tempSql.getSql());
				}
				values.addAll(tempSql.getValues());
			}
		}//for(int i=0;i<actLen;i++)
		if(!sql.toString().equals(""))
		{
			sqlResult=new SqlResult();
			sqlResult.appendSql(sql.toString());
			sqlResult.appendValues(values);
		}		
		return sqlResult;
	}
	/**
	 * 解析filed动作生成sql
	 * @param element
	 * @return
	 */
	private SqlResult getFiled(Element element)
	{
		SqlResult sqlResult=new SqlResult();
		String operator=StringUtils.checkNullString(element.getAttributeValue("operator"));
		String values=StringUtils.checkNullString(element.getAttributeValue("value")).trim();
		String colname=StringUtils.checkNullString(element.getAttributeValue("colname"));
		//String maping=StringUtils.checkNullString(element.getAttributeValue("maping")).trim();
		int datatype=NumberUtils.formatToInt(element.getAttributeValue("datatype"));
		
		if(!values.equals(""))
		{	
			//values=SqlReplace.stringReplaceAllVar(values, this.indirectValues,RConstants.DATA_VAR_PAGE);
//			values=SqlReplace.stringReplaceAllVar(values, this.indirectValues,RConstants.DATA_VAR_SYSTEM);			
			values=SqlReplace.stringReplaceAllVar(values, this.indirectValues);

			//values=SqlReplace.stringReplaceAllVar(values, this.reqeustValues,RConstants.DATA_VAR_PAGE);
		}
		//如果是日期
		if(datatype==RConstants.DATA_TYPE_DATATIME)
		{
			long lngDateTime=TimeUtils.formatDateStringToInt(values);
			if(lngDateTime>0)
			{
				values=String.valueOf(lngDateTime);
			}
		}
		
		sqlResult=DataTransfer.getFieldValues("", operator, colname, values, datatype);
		return sqlResult;
	}
	/**
	 *  解析sql动作生成sql
	 * @param element
	 * @return
	 */
	private SqlResult getSql(Element element)
	{
		if(element==null)
			return null;
		SqlResult sqlResult,sqlResult1;
		String sql=StringUtils.checkNullString(element.getText());
//		sqlResult1=SqlReplace.replaceAllVar(sql,this.indirectValues,RConstants.DATA_VAR_PAGE);
		sqlResult1=SqlReplace.replaceAllVar(sql,this.indirectValues,null);
		if(sqlResult1!=null)
		{
			sql=sqlResult1.getSql();
		}
		
		sqlResult=new SqlResult();
		if(sqlResult1!=null)
		{
			sqlResult.appendSql(sqlResult1.getSql());
		}
		else
		{
			sqlResult.appendSql(sqlResult1.getSql());
		}
		if(sqlResult1!=null)
			sqlResult.appendValues(sqlResult1.getValues());
		return sqlResult;
	}
	/**
	 * 解析switch动作生成sql
	 * @param element
	 * @return
	 */
	private SqlResult getSwitch(Element element)
	{
		if(element==null)
			return null;
		//StringBuffer string=new StringBuffer();
		SqlResult sqlResult=new SqlResult();
		String exp=StringUtils.checkNullString(element.getAttributeValue("exp"));
		String sql;
//		exp=SqlReplace.stringReplaceAllVar(exp,this.indirectValues,RConstants.DATA_VAR_PAGE);
//		exp=SqlReplace.stringReplaceAllVar(exp,this.indirectValues,RConstants.DATA_VAR_SYSTEM);
		exp=SqlReplace.stringReplaceAllVar(exp,this.indirectValues);
		String expValue="";
		try {
			expValue=RuleExpression.execute(exp);
		} catch (RuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
 		List children=element.getChildren("case");
		int childLen=0;
		if(children!=null)
			childLen=children.size();
		if(childLen==0)
		{
			return null;
		}
		Element eleChild;
		//String actionName;
		List lstLevelNextChild;
		int nextLevelChildLen=0;
		//Element nextLevelelement;
		SqlResult tmpResult;
		for(int i=0;i<childLen;i++)
		{
			eleChild=(Element)children.get(i);
			String caseValue=StringUtils.checkNullString(eleChild.getAttributeValue("value")).trim();
			if(expValue.equals(caseValue)|| caseValue.equals(""))
			{
				lstLevelNextChild=eleChild.getChildren();
				if(lstLevelNextChild!=null)
				{
					nextLevelChildLen=lstLevelNextChild.size();
				}
				if(nextLevelChildLen==0)
				{
					sql=StringUtils.checkNullString(eleChild.getText()).trim();
					//SqlResult sqlResult1,sqlResult2;
//					sqlResult=SqlReplace.replaceAllVar(sql,this.indirectValues,RConstants.DATA_VAR_PAGE);
					sqlResult=SqlReplace.replaceAllVar(sql,this.indirectValues,null);
				}
				else
				{
					tmpResult=this.getNextLevelSqlResult(eleChild);
					if(tmpResult!=null)
					{
						sqlResult.appendSql(tmpResult.getSql());
						sqlResult.appendValues(tmpResult.getValues());
					}
				}
				break;
			}//if(expValue.equals(caseValue))
			
		}//for(int i=0;i<childLen;i++)
		return sqlResult;
	}
	/**
	 * 解析if动作生成sql
	 * @param element
	 * @return
	 */
	private SqlResult getIf(Element element)
	{
		if(element==null)
			return null;
		//StringBuffer string=new StringBuffer();
		SqlResult sqlResult=new SqlResult();
		String exp=StringUtils.checkNullString(element.getAttributeValue("exp"));
		String sql;
//		exp=SqlReplace.stringReplaceAllVar(exp,this.indirectValues,RConstants.DATA_VAR_PAGE);
//		exp=SqlReplace.stringReplaceAllVar(exp,this.indirectValues,RConstants.DATA_VAR_SYSTEM);
		exp=SqlReplace.stringReplaceAllVar(exp,this.indirectValues);
		String expValue="";
		try {
			if(!exp.trim().equals(""))
			{	
				expValue=RuleExpression.execute(exp);
			}
			else
			{
				expValue="true";
			}
		} catch (RuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
 		List children=null;
 		if(expValue.equals("true"))
 		{
 			children=element.getChildren(RConstants.E_ACT_IF_SUCCESS);
 		}
 		else
 		{
 			children=element.getChildren(RConstants.E_ACT_IF_ELSE);
 		}
		int childLen=0;
		if(children!=null)
			childLen=children.size();
		if(childLen==0)
		{
			return null;
		}
		else
		{
			childLen=1;//取第一个即可
		}
		Element eleChild;
		//String actionName;
		List lstLevelNextChild;
		int nextLevelChildLen=0;
		//Element nextLevelelement;
		SqlResult tmpResult;
		for(int i=0;i<childLen;i++)
		{
			eleChild=(Element)children.get(i);
			
			String caseValue=StringUtils.checkNullString(eleChild.getAttributeValue("value")).trim();
			if(expValue.equals(caseValue)|| caseValue.equals(""))
			{
				lstLevelNextChild=eleChild.getChildren();
				if(lstLevelNextChild!=null)
				{
					nextLevelChildLen=lstLevelNextChild.size();
				}
				if(nextLevelChildLen==0)
				{
					sql=StringUtils.checkNullString(eleChild.getText()).trim();
//					sqlResult=SqlReplace.replaceAllVar(sql,this.indirectValues,RConstants.DATA_VAR_PAGE);
					sqlResult=SqlReplace.replaceAllVar(sql,this.indirectValues,null);
				}
				else
				{
					tmpResult=this.getNextLevelSqlResult(eleChild);
					if(tmpResult!=null)
					{
						sqlResult.appendSql(tmpResult.getSql());
						sqlResult.appendValues(tmpResult.getValues());
					}
				}
				break;
			}//if(expValue.equals(caseValue))
			
		}//for(int i=0;i<childLen;i++)
		return sqlResult;
	}	
	
	
	public static void main(String[] args)
	{
		
		String path="D:\\projects\\eoms4\\src\\resources\\sqlconfig";
		StartUp.loadFile(path);
 		HashMap hash=new HashMap();
		//hash.put("loginname" ,"Demo");
		//hash.put("isovertime", "2");
		//CustWhere custWhere=new CustWhere("SQL_QuerySimple_001",hash,false);
 		hash.put("startTime", "2");
 		hash.put("endTime", "10");
		CustWhere custWhere=new CustWhere("SQL_SM_BookCatalog1.query",hash,false);
		
		SqlResult sqlResult=custWhere.getSqlResult("");
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
			sql=sql.replaceFirst("\\?", str.toString());
		}
		System.out.println("完整的SQL:"+sql);
	}

}

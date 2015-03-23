package com.ultrapower.eoms.common.core.component.rquery.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jdom.Element;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.ActionContext;

public class Parameters {
	/**
	 * 将request参数、手工参数valuemap、传入的参数indirectValues、xml默认缺省的变量 合成一个参数变量
	 * @param indirectValues
	 */
	public static HashMap CollectionParameter(HashMap indirectValues)
	{
		HashMap var_hash=null;
		//this.custwhereElement=(Element)obj;
		HttpServletRequest request=ActionContext.getRequest();
		HashMap reqeustValues=null;//ruequest中的值
		HashMap reqeustCustomValues=null;//自定义的Map
		if(request!=null)
		{
			reqeustValues=new HashMap();
			Object objmap=request.getAttribute("valuemap");
			if(objmap!=null && objmap instanceof HashMap)
			{
				reqeustCustomValues=(HashMap)objmap;
			}
			if(indirectValues==null)
				indirectValues=new HashMap();
			Map map=request.getParameterMap();
			String strTemp;
			for(Iterator iter = map.entrySet().iterator();iter.hasNext();)
			{  
				Map.Entry element = (Map.Entry)iter.next();  
				Object strKey = element.getKey();  
				Object strObj = element.getValue();
				String[] strAry = (String[])strObj;
				strTemp="";
				for(int i=0;i<strAry.length;i++)
				{
					if(strTemp.equals(""))
						strTemp=strAry[0];
					else
						strTemp+=","+strAry[0];
				}
				reqeustValues.put(strKey, strTemp);
			}
		}
		if(reqeustValues!=null)
		{
			if(reqeustCustomValues!=null)
				reqeustValues.putAll(reqeustCustomValues);
			if(indirectValues!=null)
				reqeustValues.putAll(indirectValues);
			var_hash=reqeustValues;
		}
		else
		{
			var_hash=indirectValues;
		}
		
		return var_hash;
			
	}
	
	/**
	 * 将传入的变量和配置文件中缺省的变量合成1个哈希
	 * @param indirectValues
	 * @param sqlqueryElement
	 * @return
	 */
	public static HashMap CollectionParameter(final HashMap indirectValues,final Element sqlqueryElement)
	{
		HashMap hash=indirectValues;
		
		List lstAction=null;
		if(sqlqueryElement!=null)
		{
			lstAction=sqlqueryElement.getChildren("condition");
		}
		int lstLen=0;
		if(lstAction!=null)
		{
			lstLen=lstAction.size();
			if(lstLen>0)
			{
				lstLen=0;
				Element ele=(Element)lstAction.get(0);
				lstAction=ele.getChildren();
				if(lstAction!=null)
					lstLen=lstAction.size();
				Element element;
				String defaultValue;
				String name;
				for(int i=0;i<lstLen;i++)
				{
					element=(Element)lstAction.get(i);
					name=StringUtils.checkNullString(element.getAttributeValue("name"));
					defaultValue=StringUtils.checkNullString(element.getAttributeValue("defaultvalue"));
					if(!defaultValue.equals(""))
					{
						defaultValue=SqlReplace.stringReplaceAllVar(defaultValue, indirectValues);
						if(hash.get(name)==null)
						{
							hash.put(name, defaultValue);
						}
					}
				}
			}
		}		
		return hash;
	}
}


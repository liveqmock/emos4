package com.ultrapower.eoms.common.core.web;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ultrapower.eoms.common.core.component.rquery.core.SqlResult;

import com.ultrapower.eoms.common.portal.model.UserSession;

/**
 * 用于保存当前线程中的变量数据，为其他类使用。如：国际化
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-5-26
 */
public class ActionContext implements Serializable 
{
	//private static ThreadLocal<HttpServletRequest> session = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal actionContext = new ThreadLocal();
	Map<String, Object> context;
	public static final String HTTP_REQUEST = "com.ultrapower.HttpServletRequest";
	public static final String HTTP_RESPONSE = "com.ultrapower.HttpServletResponse";
	public static final String HTTP_RESOURCE = "com.ultrapower.ResourceId";//资源ID
	public static final String HTTP_OPERATE= "com.ultrapower.OperationId";//操作ID
	public static final String HTTP_OPERATE_DATAPRIVILEGE= "com.ultrapower.DataPrivilege";//操作数据权限
	public static final String HTTP_OPERATE_MAPPRIVILEGE="com.ultrapower.MapPrivilege";//操作数据权限参数MAP
	
    public static void setContext(ActionContext context) 
    {
	        actionContext.set(context);
    }
	    
	 public static ActionContext getContext() 
	 {
		 Object object=actionContext.get();
		 if(object!=null)
		 {
	        return (ActionContext) object;
		 }
		 else
			 return null;
	 }
	public ActionContext(Map<String, Object> context)
	{
		this.context=context;
	}
    public Object get(String key) 
    {
        return context.get(key);
    }
    public void put(String key, Object value) {
        context.put(key, value);
    }
    
    public Map<String, Object> getContextMap() {
        return context;
    }
    
	public  static void setRequest(HttpServletRequest p_request)
	{
		
		getContext().put(HTTP_REQUEST,p_request);
	}
	
	public static HttpServletRequest getRequest()
	{
		Object obj=null;
		if(getContext()!=null)
			obj=getContext().get(HTTP_REQUEST);
		if(obj!=null)
		{
			return (HttpServletRequest)obj;
		}
		else
		{
			return null;
		}
	}
	
	public  static void setResponse(HttpServletResponse p_request)
	{
		getContext().put(HTTP_RESPONSE,p_request);
	}
	
	public static HttpServletResponse getResponse()
	{
		Object obj=getContext().get(HTTP_RESPONSE);
		if(obj!=null)
		{
			return (HttpServletResponse)obj;
		}
		else
		{
			return null;
		}		
	}
	
	/**
	 * 获取数据权限SqlResult
	 * @return
	 */
	public static SqlResult getDataPrivilege()
	{
		SqlResult sqlResult=null;
		Object obj=null;
		if(getContext()!=null)
			obj=getContext().get(HTTP_OPERATE_DATAPRIVILEGE);
		if(obj!=null)
		{
			sqlResult=(SqlResult)obj;
		}
		return sqlResult;
	}
	public static Map getMapPrivilege()
	{
		Map valueMap=null;
		Object obj=null;
		if(getContext()!=null)
			obj=getContext().get(HTTP_OPERATE_MAPPRIVILEGE);
		if(obj!=null)
		{
			valueMap=(Map)obj;
		}
		return valueMap;
	}
	public static UserSession getUserSession()
	{
		UserSession sessionBean=null;
		HttpServletRequest rq=getRequest();
		if(rq!=null)
		{
			Object obj=rq.getSession().getAttribute("userSession");
			if(obj!=null)
			{
				sessionBean=(UserSession)obj;
			}
		}
		return sessionBean;
	}

}


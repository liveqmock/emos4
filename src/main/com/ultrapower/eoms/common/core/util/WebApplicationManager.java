package com.ultrapower.eoms.common.core.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ultrapower.eoms.common.portal.model.UserSession;

/**
 * 通过webApplicationContext的方式获取注入的spring对象
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 22, 2010 6:32:37 PM
 */
public class WebApplicationManager {

	public static WebApplicationContext webApplicationContext;
	public static ServletContext servletContext;
	
	/**
	 * 获取注入的对象实例
	 * @param beanid
	 * @return
	 */
	public static Object getBean(String beanid){
		if(webApplicationContext==null && servletContext!=null)
			webApplicationContext= WebApplicationContextUtils.getWebApplicationContext(servletContext);
		if(webApplicationContext!=null)
			return webApplicationContext.getBean(beanid);
		else
			return null;
	}
	
	public static UserSession getUserSession(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return (UserSession)request.getSession().getAttribute("userSession");
	}
	
}

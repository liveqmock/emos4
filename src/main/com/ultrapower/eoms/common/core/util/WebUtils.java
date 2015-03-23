package com.ultrapower.eoms.common.core.util;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.web.ActionContext;

/**
 * @author calvin
 */
public class WebUtils {

	private WebUtils() {
	}
	/**
	 * 重载Spring WebUtils中的函数,作用如函数名所示 加入泛型转换,改变输入参数为request 而不是session
	 * @param name  session中变量名称
	 * @param clazz session中变量的类型
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getOrCreateSessionAttribute(HttpServletRequest request, String name, Class<T> clazz) {
		return (T) org.springframework.web.util.WebUtils.getOrCreateSessionAttribute(request.getSession(), name, clazz);
	}
	
	public static void refreshSpringConf(){
		ServletContext servletContext = ServletActionContext.getRequest().getSession().getServletContext();  
		ConfigurableWebApplicationContext wac = (ConfigurableWebApplicationContext)WebApplicationContextUtils.
	             getRequiredWebApplicationContext(servletContext); 
		wac.refresh();
		
		
	}
	
	/**  
     * 得到上下文路径，例如：/eoms4  
     * @return 上下文路径  
     */   
	public static String getContextPath(){
		String contextPath = "";
		if(ActionContext.getRequest()!=null)
			contextPath = ActionContext.getRequest().getContextPath();
		return StringUtils.checkNullString(contextPath);
	}
	
	/**
	 * 获取访问的IP,考虑了代理的问题
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static HashMap<String, String> getIpConfigMap() {
		HashMap<String, String> ipMap = new HashMap<String, String> ();
		String ipInfos = PropertiesUtils.getProperty("pasm.network.map");
		if(ipInfos == null || "".equals(ipInfos))
			return ipMap;
		String [] ipArr = ipInfos.split(";");
		String [] subIp;
		for(int i=0 ; i<ipArr.length ; i++) {
			if("".equals(ipArr[i]))
				continue;
			subIp = ipArr[i].split(":");
			ipMap.put(subIp[0], subIp[1]);
		}
		return ipMap;
	}
}

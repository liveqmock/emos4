package com.ultrapower.eoms.common.core.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.MenuManagerService;

/**
 * Action的基类
 * 
 * @author andy
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
public abstract class BaseAction extends ActionSupport implements IBaseAction 
{

	protected transient final Logger log = LoggerFactory.getLogger(this
			.getClass());
	public Map webMap;
	private String target;
	public String id;
	private String nodePath;
	public String getTarget()
	{
		return target;
	}

	public void setTarget(String target)
	{
		this.target = target;
	}

	protected String findForward(String target)
	{
		String CUSTOM = "custom";
		setTarget(target);
		return CUSTOM;
	}

	protected String findRedirect(String _target)
	{
		String CUSTOM_REDIRECT = "redirect";
		setTarget(_target);
		return CUSTOM_REDIRECT;
	}
	protected String findRedirectPar(String _target)
	{
		String CUSTOM_REDIRECT = "redirectpar";
		setTarget(_target);
		return CUSTOM_REDIRECT;
	}
	
	protected HttpServletRequest getRequest() 
	{
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse()
	{

		return ServletActionContext.getResponse();
	}

	protected HttpSession getSession() 
	{
		return getRequest().getSession();
	}

	protected UserSession getUserSession()
	{
		return (UserSession) this.getSession().getAttribute("userSession");
	}
	
	protected UserInfo getUserModelSession()
	{
		Object obj = this.getSession().getAttribute("userModelSession");
		UserInfo userInfo = null;
		if(obj != null) {
			userInfo = (UserInfo) obj;
		}
		return (userInfo) ;
	}
	/*public SessionBean getSessionBean() {
		Object sessionObj = getSession().getAttribute("SessionBean");
		return sessionObj == null ? null : (SessionBean) sessionObj;
	}*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 绕过action,直接输出内容的简便函数.
	 */
	protected void render(final String text, final String contentType)
	{
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	protected void renderLocation(String location)
	{
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
	//		response.setContentType(contentType);
			//response.getWriter().write(text);
			response.sendRedirect(location);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
	@Override
	public Object clone()
	{
		Object result = null;
		try {
			result = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 直接输出字符串,编码为UTF-8.
	 */
	protected void renderText(final String text) 
	{
		render(text, "text/plain;charset=UTF-8");
	}

	/**
	 * 直接输出HTML,编码为UTF-8.
	 */
	protected void renderHtml(final String html) 
	{
		render(html, "text/html;charset=UTF-8");
	}

	/**
	 * 直接输出XML,编码为UTF-8.
	 */
	protected void renderXML(final String xml)
	{
		render(xml, "text/xml;charset=UTF-8");
	}

	protected void setValueMap(Map map) 
	{
		ActionContext.getContext().setApplication(map);
	}

	public Map getActionMap()
	{

		Map map = new HashMap();
		if (webMap == null)
			return map;
		for (Iterator names = webMap.keySet().iterator(); names.hasNext();) {
			String name = (String) names.next();
			if (name != null) {
				Object webMapValue = webMap.get(name);
				if (webMapValue instanceof String[]) {
					String[] theWebMapValue = (String[]) webMapValue;
					if (theWebMapValue.length == 1) {
						String value = theWebMapValue[0];
						if (StringUtils.hasLength(value))
							map.put(name, value);
					} else {
						map.put(name, theWebMapValue);
					}

				}
			}
		}
		return map;
	}

	public void setWebMap(Map webMap)
	{
		this.webMap = webMap;
	}

	public Map getWebMap() 
	{
		return webMap;
	}

	public void setPage(String name, String value)
	{
		webMap = webMap == null ? new HashMap<String, String>() : webMap;
		webMap.put(name, value);
	}

	public String getPage(String key)
	{
		if (webMap == null)
			return "";
		if (webMap.get(key) == null)
			return "";
		return (String) webMap.get(key);
	}
	
	public void clearValidate(){
		if(getFieldErrors()!=null)
		this.setFieldErrors(null);
	}
	
	public String getNodePath() throws Exception{
		MenuManagerService menuManagerService = (MenuManagerService) WebApplicationManager.getBean("menuManagerService");
		if(menuManagerService != null)
			nodePath = menuManagerService.getNodePahtById(id.split(",")[0]);
		else
			nodePath = "当前位置：";
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}
    
}

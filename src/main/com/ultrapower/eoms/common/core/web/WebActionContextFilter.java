package com.ultrapower.eoms.common.core.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ultrapower.accredit.util.GetUserUtil;
import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.constants.ConstantsSynch;
import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.component.rquery.core.SqlResult;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.util.CryptUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.common.portal.service.PortalManagerService;
import com.ultrapower.eoms.ultrasm.model.MenuInfo;
import com.ultrapower.eoms.ultrasm.model.ResourceUrl;
import com.ultrapower.eoms.ultrasm.service.MenuManagerService;
import com.ultrapower.eoms.ultrasm.service.PrivilegeManagerService;
import com.ultrapower.eoms.ultrasm.service.ResourceUrlManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;

/**
 * 访问web时加载的类
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-5-26
 */
public class WebActionContextFilter implements Filter
{
	
	private static List<MenuDtree> allMenuTree = null;

	public void destroy() 
	{ 

	}

	
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response=(HttpServletResponse)arg1;
		
		String currentURL=request.getRequestURI();
		String targetURL = currentURL.substring(currentURL.indexOf("/", 1),currentURL.length()); //截取到当前文件名用于比较
		String urltype = request.getParameter("urltype"); // 是否记录访问的url标识
		String menuid = StringUtils.checkNullString(request.getParameter("menuid"));
		if(menuid.equals(""))
			menuid = StringUtils.checkNullString(request.getParameter("id"));
		Object objSession = request.getSession().getAttribute("userSession");
		UserSession userSession = null;
		if(objSession!=null)
		{
			userSession=(UserSession)objSession;
		}
		else
		{
			if(ConstantsSynch.isSynch){
				//uip同步
				String loginName = GetUserUtil.getUsername();
				PortalManagerService portalManagerService = (PortalManagerService)WebApplicationManager.getBean("portalManagerService");
				userSession = portalManagerService.login(loginName, "", false);
				request.getSession().setAttribute("userSession", userSession);
			}
			else if(ConstantsSynch.IS_SSO==true)
			{
				Cookie[] cookies = request.getCookies();
				if(cookies==null)
				{
					System.out.println("The cookie is null.");
				}
				else
				{
					for(Cookie cookie:cookies)
					{
						if(cookie.getName().equals(ConstantsSynch.SSO_COOKIE_NAME))
						{
							String value = cookie.getValue();
							if(value==null)
								break;
							String[] pri = value.split(ConstantsSynch.SSO_USER_PWD_SEPARATOR);
							if(pri.length>0)
							{
								CryptUtils crypt = CryptUtils.getInstance();
								String loginName = pri[0];
								String pwd = pri.length==2?crypt.decode(pri[1]):"";
								PortalManagerService portalManagerService = (PortalManagerService)WebApplicationManager.getBean("portalManagerService");
								PrivilegeManagerService privilegeManagerService = (PrivilegeManagerService)WebApplicationManager.getBean("privilegeManagerService");
								userSession = portalManagerService.login(loginName, pwd, true);
								if(Constants.ISUSERCACHE && Constants.CACHE_TYPE == 1)
									privilegeManagerService.setPrivilegeToCache(userSession.getPid());
								request.getSession().setAttribute("userSession", userSession);
							}
							break;
						}
					}
				}
			} 			
		}
		
		if(userSession!=null)
		{
			if("/".equals(targetURL))
			{
				String url=request.getContextPath()+"/portal/index_office.action";
				response.sendRedirect(url);
			}
			else
			{
				boolean isAllow=true;
				String resourcepid="";
				String operatorid="";
				SqlResult sqlResult=null;
				Map valueMap=null;
				Object obj=WebApplicationManager.getBean("resourceUrlManagerService");
				if(obj!=null)
				{
					//根据url查询资源和操作
					ResourceUrlManagerService resourceUrlManagerService=(ResourceUrlManagerService)obj;
					ResourceUrl resourceUrl=resourceUrlManagerService.getResourceUrlByUrl(targetURL);
					if(resourceUrl!=null)
					{
						resourcepid=resourceUrl.getResid();
						operatorid=resourceUrl.getOpid();
						obj=WebApplicationManager.getBean("privilegeManagerService");
						if(obj!=null)
						{
							PrivilegeManagerService privilegeManagerService=(PrivilegeManagerService)obj;
							isAllow=privilegeManagerService.isAllow(userSession.getPid(), resourcepid, operatorid);
							sqlResult=privilegeManagerService.getPrivilegeSql(userSession.getPid(), resourcepid, operatorid);
							valueMap = privilegeManagerService.getPrivelegeMap(userSession.getPid(), resourcepid, operatorid);
						}
					}
				}
				
				if(allMenuTree == null)
				{
					RoleManagerService roleManagerService = (RoleManagerService)WebApplicationManager.getBean("roleManagerService");
					allMenuTree = roleManagerService.getAllMenu();
				}
				boolean menuCheck = false;
				boolean hasmenu = false;
				if(!targetURL.equals("/common/core/privilegeError.jsp"))
				{
					for(MenuDtree tree : allMenuTree)
					{
						if(tree.getUserdata().get("url").indexOf(targetURL) > -1)
						{
							hasmenu = true;
							Map<String, List<MenuDtree>> menuMap = ((Map<String, List<MenuDtree>>)((Map) BaseCacheManager.get(Constants.PRIVILEGECACHE, userSession.getPid())).get("menu"));
							for(List<MenuDtree> userTreeList : menuMap.values())
							{
								for(MenuDtree userTree : userTreeList)
								{
									if(userTree.getUserdata().get("url").indexOf(targetURL) > -1)
									{
										menuCheck = true;
										break;
									}
								}
								if(menuCheck)
								{
									break;
								}
							}
						}
					}
				}
				
				ActionContext.setContext(createActionContext(arg0,arg1,resourcepid,operatorid,sqlResult,valueMap));
				this.printFluxStatLog(targetURL, urltype, menuid);
				//RecordLog.printFluxStatLog(targetURL);
				if(!isAllow || (!menuCheck && hasmenu))
				{
					String url=request.getContextPath()+"/common/core/privilegeError.jsp";
					response.sendRedirect(url);
				}
				else
				{
					arg2.doFilter(arg0, arg1);
				}
				
			}
		}
		else
		{
			int isar=targetURL.indexOf("arsys/forms");
			if ("/portal/login.action".equals(targetURL)
					|| "/portal/userLogout.action".equals(targetURL)
					|| "/welcom.jsp".equals(targetURL)
					|| "/portal/password.action".equals(targetURL)
					|| "/portal/extentLogin.action".equals(targetURL)
					|| "/portal/editPwd.action".equals(targetURL)
					|| "/portal/login_v2.action".equals(targetURL)
					|| "/portal/loginManager.action".equals(targetURL)
					|| "/portal/editPwd.action".equals(targetURL)
					|| "/portal/findpwd.action".equals(targetURL)					
					|| "/formImport/formImportAction/importBase.action".equals(targetURL)					
					|| "/workflow/manageTools/importBase.jsp".equals(targetURL)					
					|| isar>-1
				)
			{
				ActionContext.setContext(createActionContext(arg0,arg1,"","",null,null));
				arg2.doFilter(arg0, arg1);
			}
			else
			{
				//跳转到登录页面
				String url=request.getContextPath()+"/portal/login.action";
				response.sendRedirect(url);
			}
		}

	}

	/**
	 * 记录访问日志
	 * @param url 访问url
	 * @param type 类别 1.记录日志
	 * @param menuid 菜单节点id
	 */
	private void printFluxStatLog(String url, String type, String menuid)
	{
		if(type != null && "1".equals(type)) {
			MenuManagerService menuManagerService = (MenuManagerService) WebApplicationManager.getBean("menuManagerService");
			MenuInfo menuInfo = menuManagerService.getMenuByID(menuid);
			String nodeName = "";
			if(menuInfo != null)
				nodeName = StringUtils.checkNullString(menuInfo.getNodename());
			RecordLog.printFluxStatLog(url, nodeName);
		}
	}	
	/**
	 * 保存线程数据
	 * @param arg0
	 * @param arg1
	 * @param resId 资源ID
	 * @param opId 操作ID
	 * @param sqlResult 数据权限对象
	 * @return
	 */
	private ActionContext createActionContext(ServletRequest arg0, ServletResponse arg1,String resId,String opId,SqlResult sqlResult,Map valueMap)
	{
		ActionContext ctx;
	//	ActionContext oldContext = ActionContext.getContext();
/*		if(oldContext!=null)
		{
			ctx=new ActionContext(new HashMap<String, Object>(oldContext.getContextMap()));
			
		}else
		{*/
			HashMap hash=new HashMap();
			hash.put(ActionContext.HTTP_REQUEST, arg0);
			//int page=NumberUtils.formatToInt(arg0.getParameter(GridConstants.HIDDEN_CURRENT_PAGE));
			hash.put(ActionContext.HTTP_RESOURCE,resId);
			hash.put(ActionContext.HTTP_OPERATE,opId);
			if(sqlResult!=null)
				hash.put(ActionContext.HTTP_OPERATE_DATAPRIVILEGE, sqlResult);
			if(valueMap != null)
				hash.put(ActionContext.HTTP_OPERATE_MAPPRIVILEGE, valueMap);
			ctx=new ActionContext(hash);
		//}
		return ctx;
	}
	public void init(FilterConfig arg0) throws ServletException 
	{
	}
	
}

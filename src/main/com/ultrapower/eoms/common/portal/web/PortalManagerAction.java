package com.ultrapower.eoms.common.portal.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import com.ultrapower.accredit.util.GetUserUtil;
import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.constants.ConstantsSynch;
import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.util.CryptUrlUtils;
import com.ultrapower.eoms.common.core.util.CryptUtils;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.common.portal.service.PortalManagerService;
import com.ultrapower.eoms.common.startup.SessionListener;
import com.ultrapower.eoms.ultrasm.model.MenuInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.MenuManagerService;
import com.ultrapower.eoms.ultrasm.service.MyMenuManagerService;
import com.ultrapower.eoms.ultrasm.service.PrivilegeManagerService;
import com.ultrapower.eoms.ultrasm.service.PwdManageService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.remedy4j.core.RemedySession;

/**
 * 该管理器用来管理登录及首页
 * @author SunHailong
 * @version UltrPower-Eoms4.0-0.1
 */
public class PortalManagerAction extends BaseAction
{
	private PortalManagerService portalManagerService;
	private PrivilegeManagerService privilegeManagerService;
	private UserManagerService userManagerService;
	private PwdManageService pwdManageService;
	private MenuManagerService menuManagerService;
	private MyMenuManagerService mymenuManagerService;
	private List<MenuDtree> navigateList;
	private String loginName;
	private String pwd;
	private String msg;//登录错误时返回的信息
	private String myMenuHtml;
	
	public String findpwd()
	{
		String result=portalManagerService.findpwd(loginName);
		if(loginName==null ||"".equals(loginName))
		{
			msg="登录名不能为空";
		}
		else if(!result.equals("SUCCESS"))
		{
			msg=result;
		}
		else
		{
			msg="密码已发至手机，请注意查收!";
		}
		
		return SUCCESS;
	}

	/**
	 * 获取密码信息
	 */
	public void password()
	{
		String pwd = "null";
		//Object obj = ServletActionContext.getServletContext().getAttribute(com.ultrapower.eoms.common.startup.SessionListener.USERS_KEY);
		String loginName=StringUtils.checkNullString(this.getRequest().getParameter("loginname"));
		Object obj=null;
		if(!loginName.equals(""))
		{
			obj=BaseCacheManager.get(SessionListener.SESSION_CACHENAME, loginName);
		}
		if(obj != null)
		{
			pwd = ((UserSession)obj).getPwd();
		}
		else
		{
			UserInfo user = userManagerService.getUserByLoginName(loginName, false);
			if(user != null)
			{
				pwd = user.getPwd();
			}
		}
		this.renderText(pwd);
	}
	
	/**
	 * 该方法用来跳转主页
	 * @return String
	 */
	public String index()
	{
		UserSession userSession = this.getUserSession();
		if(userSession == null)
		{
			return this.findRedirect("login");
		}
		
		if (userSession.getSearchCondition() == null) {
			// 将用户配置的搜索条件加入userSession
			Map<String, String> searchCondition = userManagerService.getUserSearchCondition(loginName);
			userSession.setSearchCondition(searchCondition);
		}
		
		navigateList = privilegeManagerService.getNavigationMenu(userSession.getPid());
		myMenuHtml = mymenuManagerService.getMyMenuListHtml(userSession.getPid());
		String url = "index_rightmenu";
		return this.findForward(url);
	}
	
	/**
	 * 该方法用来跳转主页
	 * @return String
	 */
	public String index_rightmenu()
	{
		UserSession userSession = this.getUserSession();
		if(userSession == null)
		{
			return this.findRedirect("login");
		}
		if (userSession.getSearchCondition() == null) {
			// 将用户配置的搜索条件加入userSession
			Map<String, String> searchCondition = userManagerService.getUserSearchCondition(loginName);
			userSession.setSearchCondition(searchCondition);
		}
		navigateList = privilegeManagerService.getNavigationMenu(userSession.getPid());
		myMenuHtml = mymenuManagerService.getMyMenuListHtml(userSession.getPid());
		String url = "index_rightmenu";
		return this.findForward(url);
	}
	
	/**
	 * 该方法用来跳转主页
	 * @return String
	 */
	public String index_office()
	{
		UserSession userSession = this.getUserSession();
		if(userSession == null)
		{
			return this.findRedirect("login");
		}
		if (userSession.getSearchCondition() == null) {
			// 将用户配置的搜索条件加入userSession
			Map<String, String> searchCondition = userManagerService.getUserSearchCondition(loginName);
			userSession.setSearchCondition(searchCondition);
		}
		navigateList = privilegeManagerService.getNavigationMenu(userSession.getPid());
		myMenuHtml = mymenuManagerService.getMyMenuListHtml(userSession.getPid());
		String url = "index_office";
		return this.findForward(url);
	}
	
	public String content_frame()
	{
		String menuid = this.getRequest().getParameter("menuid");
		MenuInfo menu = menuManagerService.getMenuByID(menuid);
		String menuName = "";
		String nodemark = "";
		String menuUrl = "";
		String urlId = "";
		if(menu != null)
		{
			menuName = StringUtils.checkNullString(menu.getNodename());
			nodemark = StringUtils.checkNullString(menu.getNodemark());
			menuUrl = StringUtils.checkNullString(menu.getNodeurl());
			urlId = menuManagerService.getIdByUrl(menuUrl);
			if(!"".equals(urlId))
			{
				if(menuUrl.indexOf("?") > 0)
					menuUrl = menuUrl + "&id=" + urlId;
				else
					menuUrl = menuUrl + "?id=" + urlId;
			}
		}
		String src = "frame/content_frame";
		if("docsManager".equals(nodemark) || "repository".equals(nodemark))
			src = "frame/content_advancedframe";
		this.getRequest().setAttribute("menuid", menuid);
		this.getRequest().setAttribute("menuName", menuName);
		this.getRequest().setAttribute("menuUrl", menuUrl);
		return this.findForward(src);
	}	
	
	public String office_frame()
	{
		String nodemark = this.getRequest().getParameter("nodemark");
		MenuInfo menu = menuManagerService.getMenuByMark(nodemark);
		String menuid = "";
		String menuName = "";
		String menuUrl = "";
		if(menu != null)
		{
			menuid = StringUtils.checkNullString(menu.getPid());
			menuName = StringUtils.checkNullString(menu.getNodename());
			menuUrl = StringUtils.checkNullString(menu.getNodeurl());
			if(!"".equals(menuUrl))
			{
				if(menuUrl.indexOf("?") > 0)
					menuUrl = menuUrl + "&id=" + menuid;
				else
					menuUrl = menuUrl + "?id=" + menuid;
			}
		}
		String src = "frame/content_frame";
		MenuInfo topMenu = menuManagerService.getNavigateMenuById(menuid);
		if(topMenu != null)
		{
			String topMark = topMenu.getNodemark();
			if("docsManager".equals(topMark) || "repository".equals(topMark))
				src = "frame/content_advancedframe";
		}
		this.getRequest().setAttribute("menuid", menuid);
		this.getRequest().setAttribute("menuName", menuName);
		this.getRequest().setAttribute("menuUrl", menuUrl);
		return this.findForward(src);
	}
	
	public String left()
	{
		String menuid = this.getRequest().getParameter("menuid");
		MenuInfo menu = menuManagerService.getNavigateMenuById(menuid);
		String menuName = "";
		String navigateId = "";
		if(menu != null)
		{
			navigateId = menu.getPid();
			menuName = StringUtils.checkNullString(menu.getNodename());
		}
		this.getRequest().setAttribute("menuid", navigateId);
		this.getRequest().setAttribute("openmenuid", menuid);
		this.getRequest().setAttribute("menuName", menuName);
		return this.findForward("frame/left");
	}
	
	public String advanced_left()
	{
		String menuid = this.getRequest().getParameter("menuid");
		MenuInfo menu = menuManagerService.getNavigateMenuById(menuid);
		String menuName = "";
		String nodemark = "";
		String navigateId = "";
		if(menu != null)
		{
			navigateId = menu.getPid();
			menuName = StringUtils.checkNullString(menu.getNodename());
			nodemark = StringUtils.checkNullString(menu.getNodemark());
		}
		this.getRequest().setAttribute("menuid", navigateId);
		this.getRequest().setAttribute("openmenuid", menuid);
		this.getRequest().setAttribute("nodemark", nodemark);
		this.getRequest().setAttribute("menuName", menuName);
		return this.findForward("frame/advanced_left");
	}
	
	/**
	 * SSO登录信息验证
	 */
	public String extentLogin()
	{
		StringBuffer tmpUrl = new StringBuffer(this.getRequest().getQueryString());
		String url = tmpUrl.substring(tmpUrl.indexOf("url=") + 4);
		String data = StringUtils.checkNullString(this.getRequest().getParameter("data"));
		if(!"".equals(url) && !"".equals(data))
		{
			data = data.replace("&amp;", "&");
			data = data.replace("&jing;", "#");
			String[] up = data.split(ConstantsSynch.SSO_USER_PWD_SEPARATOR);
			String realLoginName = null;
			String realPwd = null;
			if(up.length==2)
			{
				realLoginName = up[0];
				realPwd = CryptUtils.getInstance().decode(up[1]);
			}
			if(realLoginName!=null && realPwd!=null)
			{
				boolean canLogin = portalManagerService.isValidateLoginInfo(realLoginName, realPwd);
				if(canLogin)
				{
					//this.getResponse().setHeader("P3P","CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
					UserSession userSession = portalManagerService.login(realLoginName, realPwd, true);
					if(userSession!=null && !"".equals(StringUtils.checkNullString(userSession.getPid())))
					{
						if(Constants.ISUSERCACHE && Constants.CACHE_TYPE == 1)
							privilegeManagerService.setPrivilegeToCache(userSession.getPid());
						this.getSession().setAttribute("userSession", userSession);
						Cookie login_cook = new Cookie(ConstantsSynch.SSO_COOKIE_NAME,realLoginName+ConstantsSynch.SSO_USER_PWD_SEPARATOR+CryptUtils.getInstance().encode(realPwd));
						login_cook.setMaxAge(-1);
						login_cook.setPath(this.getRequest().getContextPath());
						this.getResponse().addCookie(login_cook);
					}
				}
			}
		}
		return this.findRedirectPar(url);
	}
	
	/**
	 * SSO登出
	 */
	public String extentLogout()
	{
		if(Constants.ISUSERCACHE && Constants.CACHE_TYPE == 1)
		{//清空缓存 当使用缓存并且权限缓存类型为登陆缓存的情况
			UserSession userSession = this.getUserSession();
			if(userSession != null)
			{
				String userId = StringUtils.checkNullString(userSession.getPid());
				Object obj = BaseCacheManager.get(Constants.PRIVILEGECACHE, userId);
				if(obj != null)
					BaseCacheManager.removeElement(Constants.PRIVILEGECACHE, userId);
			}
		}
		getSession().invalidate();
		//删除登陆cookie
		Cookie login_cook = new Cookie(ConstantsSynch.SSO_COOKIE_NAME,null);
		login_cook.setMaxAge(0);
		login_cook.setPath(this.getRequest().getContextPath());
		this.getResponse().addCookie(login_cook);
		String url = StringUtils.checkNullString(this.getRequest().getParameter("url"));
		return findRedirectPar(url);
	}
	/**
	 * 该方法用来管理登录跳转，登录成功跳转到主页面，登录不成功跳转到登录页面
	 * @return String
	 */
	public String login()
	{
		boolean isSynch = true; //是否需要密码验证
		if(ConstantsSynch.isSynch){
			loginName = GetUserUtil.getUsername();
			isSynch = false;
		}
		if(null == loginName)
		{
			return SUCCESS;
		}
		String administrator = StringUtils.checkNullString(PropertiesUtils.getProperty("eoms.administrator"));
		boolean isAdmin = false;
		if(administrator.indexOf(loginName) >= 0)
			isAdmin = true;
		//根据配置eoms.pwdmanage 是否采用密码管理
		if(!isAdmin && Constants.PWD_MANAGE)
		{
			if(this.getSession().getAttribute(loginName) == null) //当存在session则代表登录第一次进入此方法，尚未通过萨班斯密码校验
			{
				if(isSynch && !portalManagerService.isValidateLoginInfo(loginName, pwd)) //如果密码验证失败则返回登录页并给予提示
				{
					msg = Internation.language("sm_msg_loginRetnMsg");
					return SUCCESS;
				}
				String result = pwdManageService.isCanLogin(loginName);
				if(!"".equals(result))
				{
					this.getRequest().setAttribute("loginName", loginName);
					this.getRequest().setAttribute("isLogin", "true"); //是否是登录时修改密码
					msg = result;
					List<String> pwdRuleList = getPwdRuleList();
					this.getRequest().setAttribute("pwdRuleList", pwdRuleList);
					return this.findForward("editPwd");
				}
			}
			isSynch = false; //密码已经验证，不需要再进行密码校验
		}
		String checkcode = StringUtils.checkNullString(PropertiesUtils.getProperty("eoms.checkcode"));
		if(!isAdmin && "true".equals(checkcode) && Constants.CHECKCODE_MANAGE) //是否需要验证码验证
		{
			String isVerifyPwd = StringUtils.checkNullString(this.getRequest().getParameter("isVerifyPwd"));
			if("true".equals(isVerifyPwd)) //如果已经验证通过则不进行再次验证，将isSynch标识设置为不验证密码
				isSynch = false;
			if(isSynch && !portalManagerService.isValidateLoginInfo(loginName, pwd)) //如果密码验证失败则返回登录页并给予提示
			{
				msg = Internation.language("sm_msg_loginRetnMsg");
				return SUCCESS;
			}
			this.getRequest().setAttribute("isVerifyPwd", "true");
			checkcode = StringUtils.checkNullString(this.getRequest().getParameter("checkcode")); //获取输入的验证码
			String saveInfo = StringUtils.checkNullString(this.getSession().getAttribute(loginName)); //获取校验的验证码及时间
			String[] tmp = saveInfo.split(":");
			saveInfo = tmp[0];
			long oldTime = 0; //生成验证码时间
			long currentTime = TimeUtils.getCurrentTime(); //当前时间
			long differSecond = NumberUtils.formatToLong(PropertiesUtils.getProperty("eoms.verifyMinute")) * 60; //验证码失效的秒数
			if(tmp.length > 1)
				oldTime = NumberUtils.formatToLong(tmp[1]);
			boolean checkPath = false;
			if("".equals(checkcode)) //第一次登录 或者 用户手工获取验证码时
			{
				checkcode = portalManagerService.getCheckCode(loginName);
				this.getSession().setAttribute(loginName, checkcode + ":" + TimeUtils.getCurrentTime());
			}
			else if(currentTime - oldTime > differSecond) //验证码失效
				msg = Internation.language("sm_msg_checkCodeOutTime");
			else if(!saveInfo.equals(checkcode)) //验证码错误
				msg = Internation.language("sm_msg_checkCodeMsg");
			else
				checkPath = true;
			if(!checkPath)
				return this.findForward("checkCodeVerify");
		}
		UserSession userSession = portalManagerService.login(loginName, pwd, isSynch);
		if(userSession == null)
		{//没有该用户
			msg = Internation.language("sm_msg_loginRetnMsg");
			return SUCCESS;
		}
		else if("".equals(StringUtils.checkNullString(userSession.getPid())))
		{//此用户被停用
			msg = Internation.language("sm_msg_userDisable");
			return SUCCESS;
		}
		
		//根据人员ID查询此人员所有权限并存入缓存（满足条件才查询权限）
		if(Constants.ISUSERCACHE && Constants.CACHE_TYPE == 1)
			privilegeManagerService.setPrivilegeToCache(userSession.getPid());
		
/*		//有该用户
		Cookie[] cookies = this.getRequest().getCookies();
		String skinType = null;//肤色
		if(cookies != null)
		{
			//读取cookie肤色信息
			for(int i=0;i<cookies.length;i++)
			{
				Cookie cookie = cookies[i];
				if(loginName.equals(cookie.getName()))
				{
					skinType = cookie.getValue();
					break;
				}
			}
		}
		if(skinType == null)
		{
			skinType = PropertiesUtils.getProperty("eoms.default.skin");//读取默认肤色
		}
		userSession.setSkinType(skinType);*/
		
		//将用户配置的搜索条件加入userSession
		Map<String,String> searchCondition = userManagerService.getUserSearchCondition(loginName);
		userSession.setSearchCondition(searchCondition);
			
		//将用户信息存入session
		UserInfo userInfo = userManagerService.getUserByLoginName(loginName);
		this.getSession().setAttribute("userModelSession", userInfo);
		this.getSession().setAttribute("userSession", userSession);
		return findRedirect("index_office");
	}
	
	public String loginManager()
	{
		boolean isSynch = true; //是否需要密码验证
		if(ConstantsSynch.isSynch){
			loginName = GetUserUtil.getUsername();
			isSynch = false;
		}
		if(null == loginName)
		{
			return SUCCESS;
		}
		String logincode = StringUtils.checkNullString(this.getRequest().getParameter("code"));
		if(!logincode.equals(Constants.LOGIN_CODE))
		{
			msg = "请从正规途径登录系统！";
			return SUCCESS;
		}
		UserSession userSession = portalManagerService.login(loginName, pwd, isSynch);
		if(userSession == null)
		{//没有该用户
			msg = Internation.language("sm_msg_loginRetnMsg");
			return SUCCESS;
		}
		else if("".equals(StringUtils.checkNullString(userSession.getPid())))
		{//此用户被停用
			msg = Internation.language("sm_msg_userDisable");
			return SUCCESS;
		}
		//根据人员ID查询此人员所有权限并存入缓存（满足条件才查询权限）
		if(Constants.ISUSERCACHE && Constants.CACHE_TYPE == 1)
			privilegeManagerService.setPrivilegeToCache(userSession.getPid());
		UserInfo userInfo = userManagerService.getUserByLoginName(loginName);
		this.getSession().setAttribute("userModelSession", userInfo);
		this.getSession().setAttribute("userSession", userSession);
		return findRedirect("index_cqnew");
	}
	
	public String login_v2()
	{
		String url = StringUtils.checkNullString(this.getRequest().getParameter("url"));
		String param = StringUtils.checkNullString(this.getRequest().getParameter("param"));
		String pwdtype = StringUtils.checkNullString(this.getRequest().getParameter("pwdtype"));
		String simple = StringUtils.checkNullString(this.getRequest().getParameter("simple"));
		if(!"".equals(param))
		{
			param = param.replaceAll(";", "&");
			url = url + "?" + param;
		}
		if(!"".equals(url) && !url.startsWith("http://")) {
			url = "../" + url;
		}
		if(this.getUserSession() == null)
		{
			if("crypt".equals(pwdtype)) { // 当密码是以crypt加密的话，在此需要解密
				pwd = CryptUrlUtils.decode(pwd);
			}
			UserSession userSession = null;
			if("true".equals(simple)) {
				userSession = portalManagerService.loginSimple(loginName, pwd, true);
			} else {
				userSession = portalManagerService.login(loginName, pwd, true);
			}
			UserInfo userInfo = userManagerService.getUserByLoginName(loginName);
			this.getSession().setAttribute("userModelSession", userInfo);
			this.getSession().setAttribute("userSession", userSession);
		}
		this.getResponse().addHeader("P3P","CP=CAO PSA OUR");
		return this.findRedirectPar(url);
	}
	
	/**
	 * 该方法用于管理用户登出时系统需要做的处理
	 * @param String
	 */
	public String userLogout()
	{
		if(Constants.ISUSERCACHE && Constants.CACHE_TYPE == 1)
		{//清空缓存 当使用缓存并且权限缓存类型为登陆缓存的情况
			UserSession userSession = this.getUserSession();
			if(userSession != null)
			{
				String userId = StringUtils.checkNullString(userSession.getPid());
				loginName = StringUtils.checkNullString(userSession.getLoginName());
				Object obj = BaseCacheManager.get(Constants.PRIVILEGECACHE, userId);
				if(obj != null)
					BaseCacheManager.removeElement(Constants.PRIVILEGECACHE, userId);
			}
		}
		/*其它处理过程*/
		String logoutType = this.getRequest().getParameter("logoutType");
		this.getSession().invalidate();
		portalManagerService.saveUserLoginInfo(loginName, "logout");
		String logoutUrl = RemedySession.UtilInfor.getLogoutUrl();
		String basePath = this.getRequest().getScheme()+"://"+this.getRequest().getServerName()+":"+this.getRequest().getServerPort();
		if (true || "arsys".equals(logoutType)) {
			System.out.println("返回ARSYS登陆页面：" + basePath + logoutUrl);
			if(ConstantsSynch.isSynch){//登出单点
				//http://192.168.106.195:58045/ucas/logout
				String ucasIp = PropertiesUtils.getProperty("iam.server.ip");
				String ucasPort = PropertiesUtils.getProperty("iam.http.port");
				this.renderLocation("http://" + ucasIp + ":" + ucasPort
						+ "/ucas/logout?service=" + basePath
						+ "/" + this.getRequest().getContextPath() + "/portal/login.action");
				return null;
			}
		}
		return findRedirect("login");
	}
	
	/**
	 * 系统换肤
	 */
	public String changeSkin()
	{
/*		String skinType = this.getRequest().getParameter("skinType");//读取页面配置肤色
		if(skinType==null)
		{
			skinType = PropertiesUtils.getProperty("eoms.default.skin");//读取默认肤色
		}
		UserSession userSession = this.getUserSession();
		Cookie cookie = new Cookie(userSession.getLoginName(),skinType);//创建肤色cookie
		int maxAge = Integer.valueOf(PropertiesUtils.getProperty("eoms.default.maxage"));//读取cookie肤色的默认生存期
		cookie.setMaxAge(maxAge);
		this.getResponse().addCookie(cookie);
		userSession.setSkinType(skinType);
		this.getSession().setAttribute("userSession", userSession);*/
		String skinType = this.getRequest().getParameter("skinType");//读取页面配置肤色
		UserSession userSession = this.getUserSession();
		String userpid = userSession == null ? "" : userSession.getPid();
		UserInfo user = userManagerService.getUserByID(userpid);
		user.setSystemskin(skinType);
		userManagerService.updateUserInfo(user);
		userSession.setSkinType(skinType);
		this.getSession().setAttribute("userSession", userSession);
		return this.findRedirect("index");
	}
	
	/**
	 * 获得密码密文
	 * @throws IOException 
	 */
	public void getNewPwdCypt() throws IOException
	{
		String newpwd = StringUtils.checkNullString(this.getRequest().getParameter("newpwd"));
		newpwd = userManagerService.encodeUserPwd(newpwd);
		this.getResponse().getWriter().print(newpwd);
	}
	
	/**
	 * 用户更改密码
	 */
	public String updatePwd()
	{
		if(Constants.PWD_MANAGE)
		{
			UserSession userSession = this.getUserSession();
			String loginName = "";
			if(userSession != null)
			{
				loginName = userSession.getLoginName();
			}
			this.getRequest().setAttribute("loginName", loginName);
			this.getRequest().setAttribute("isLogin", "false"); //是否是登录时修改密码
			List<String> pwdRuleList = getPwdRuleList();
			this.getRequest().setAttribute("pwdRuleList", pwdRuleList);
			return this.findForward("editPwd");
		}
		else
		{
			return SUCCESS;
		}
	}
	
	public String editPwd()
	{
		if(Constants.PWD_MANAGE)//萨班斯密码管理
		{
			String loginName = this.getRequest().getParameter("loginName");
			String isLogin = StringUtils.checkNullString(this.getRequest().getParameter("isLogin"));
			this.getRequest().setAttribute("loginName", loginName);
			this.getRequest().setAttribute("isLogin", isLogin);
			List<String> pwdRuleList = getPwdRuleList();
			this.getRequest().setAttribute("pwdRuleList", pwdRuleList);
			UserSession userSession = this.getUserSession();
			if("false".equals(isLogin)) //如果非登录时修改密码，则需输入原密码验证
			{
				String oldPwd = this.getRequest().getParameter("oldPwd");
				if(userSession != null)
				{
					if(!oldPwd.equals(userManagerService.decodePwd(userSession.getPwd())))
					{
						msg = Internation.language("sm_lb_initPwdErr");
						return SUCCESS;
					}
				}
				else
				{
					msg = "session已丢失，请重新登陆系统！";
					return SUCCESS;
				}
			}
			String newPwd = this.getRequest().getParameter("newPwd");
			//萨班斯密码验证
			msg = pwdManageService.isEnablePwd(loginName, newPwd);
			if(!"".equals(msg))
			{
				return SUCCESS;
			}
			//萨班斯密码管理
			userManagerService.saveUserPwd(loginName, newPwd, true);
			this.getRequest().setAttribute("returnMessage", "更改成功！");
			if("false".equals(isLogin))
			{
				userSession.setPwd(userManagerService.encodeUserPwd(newPwd));
				return SUCCESS;
			}
			return this.findRedirectPar("login.action?loginName="+loginName+"&pwd="+newPwd);
		}
		else//非萨班斯密码管理
		{
			String newpwd = this.getRequest().getParameter("new_pwd");
			String userId = ((UserSession)this.getSession().getAttribute("userSession")).getPid();
			UserSession us = (UserSession)this.getSession().getAttribute("userSession");
			if(userManagerService.updateUserPwd(userId, newpwd) && us!=null)
			{
				us.setPwd(userManagerService.encodeUserPwd(newpwd));//将新密码设置到userSession中
				this.getRequest().setAttribute("returnMessage", "更改成功！");
			}
			else
			{
				this.getRequest().setAttribute("returnMessage", "更改失败！");
			}
			return this.findForward("updatePwd");
		}
	}
	
	private List<String> getPwdRuleList()
	{
		Object obj = BaseCacheManager.get("pwdManageCfg", "pwdManageCfg");
		HashMap<String, Object> pwdManageCfg = null;
		if(obj != null)
			pwdManageCfg = (HashMap<String, Object>) obj;
		List<String> pwdRuleList = new ArrayList<String> ();
		if(pwdManageCfg != null)
			pwdRuleList = (List<String>) pwdManageCfg.get("pwdRuleList");
		return pwdRuleList;
	}
	
	/**
	 * 以下方法为属性get/set方法
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getMsg() {
		return msg;
	}
	public List<MenuDtree> getNavigateList() {
		return navigateList;
	}
	public void setPortalManagerService(PortalManagerService portalManagerService) {
		this.portalManagerService = portalManagerService;
	}
	public void setPrivilegeManagerService(PrivilegeManagerService privilegeManagerService) {
		this.privilegeManagerService = privilegeManagerService;
	}
	public void setNavigateList(List<MenuDtree> navigateList) {
		this.navigateList = navigateList;
	}
	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
	public void setPwdManageService(PwdManageService pwdManageService) {
		this.pwdManageService = pwdManageService;
	}
	public void setMenuManagerService(MenuManagerService menuManagerService) {
		this.menuManagerService = menuManagerService;
	}
	public void setMymenuManagerService(MyMenuManagerService mymenuManagerService) {
		this.mymenuManagerService = mymenuManagerService;
	}
	public String getMyMenuHtml() {
		return myMenuHtml;
	}
	public void setMyMenuHtml(String myMenuHtml) {
		this.myMenuHtml = myMenuHtml;
	}
}

package com.ultrapower.eoms.ultrasm.web;

import java.io.IOException;
import java.util.List;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.Resource;
import com.ultrapower.eoms.ultrasm.model.ResourceUrl;
import com.ultrapower.eoms.ultrasm.service.ResourceUrlManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

public class ResUrlManagerAction extends BaseAction {
	private ResourceUrlManagerService resourceUrlManagerService;
	private List<Resource> resourceList;
	private ResourceUrl resUrl;

	public String resOpUrlList()
	{
		return SUCCESS;
	}
	
	public String transferStatus()
	{
		String transType = StringUtils.checkNullString(this.getRequest().getParameter("transType"));
		String ids = StringUtils.checkNullString(this.getRequest().getParameter("var_selectvalues"));
		if(!"".equals(transType) && !"".equals(ids))
		{
			if("inuse".equals(transType))
			{
				resourceUrlManagerService.enableResUrl(UltraSmUtil.arrayToList(ids.split(",")));
			}
			if("outuse".equals(transType))
			{
				resourceUrlManagerService.disableResUrl(UltraSmUtil.arrayToList(ids.split(",")));
			}
		}
		return findForward("resOpUrlList");
	}
	
	public String saveResUrl()
	{
		String parafresh = "true";
		this.getRequest().setAttribute("parafresh", parafresh);
		String returnMessage = Internation.language("com_msg_saveErr")+"!";
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		String userpid = "";
		if(userSession != null)
			userpid = userSession.getPid();
		long currentTime = TimeUtils.getCurrentTime();
		if(resUrl.getPid()==null||"".equals(resUrl.getPid()))
		{
			
			resUrl.setCreater(userpid);
			resUrl.setCreatetime(currentTime);
		}
		resUrl.setLastmodifier(userpid);
		resUrl.setLastmodifytime(currentTime);
		if(resourceUrlManagerService.saveResourceUrl(resUrl))
		{
			returnMessage = Internation.language("com_msg_saveSuccess")+"!";
		}
		this.getRequest().setAttribute("returnMessage", returnMessage);
		return "refresh";
	}
	
	public String resUrlLoad()
	{
		String pid = this.getRequest().getParameter("resUrlID");
		if(pid==null || "".equals(pid))
		{
			return findForward("resOpUrlSave");
		}
		this.setResUrl(resourceUrlManagerService.getResourceUrlById(pid));
		return findForward("resOpUrlSave");
	}
	
	public String resourceList()
	{
		//resourceList = resourceManagerService.getResource();
		return findForward("selectRes");
	}
	
	public String operateList()
	{
		return findForward("selectOper");
	}
	
	public String delResUrl()
	{
		String ids = this.getRequest().getParameter("var_selectvalues");
		if(ids==null || "".equals(ids))
		{
			return findForward("resOpUrlList");
		}
		else
		{
			resourceUrlManagerService.delResUrlByIdList(UltraSmUtil.arrayToList(ids.split(",")));
			return findForward("resOpUrlList");
		}
	}
	
	public void chkUrlUnique()throws IOException
	{
		String url = this.getRequest().getParameter("urlstr");
		if(url==null || "".equals(url))
		{
			this.getResponse().getWriter().print("");
		}
		else
		{
			ResourceUrl temp = resourceUrlManagerService.getResourceUrlByUrl(url);
			if(temp==null)
			{
				this.getResponse().getWriter().print("true");
			}
			else
			{
				this.getResponse().getWriter().print("false");
			}
		}
	}
	
	/*
	 * 以下为属性的get/set方法区域
	 */
	public void setResourceUrlManagerService(
			ResourceUrlManagerService resourceUrlManagerService) 
	{
		this.resourceUrlManagerService = resourceUrlManagerService;
	}
	public ResourceUrl getResUrl() 
	{
		return resUrl;
	}
	public void setResUrl(ResourceUrl resUrl) 
	{
		this.resUrl = resUrl;
	}
	public List<Resource> getResourceList() 
	{
		return resourceList;
	}
	public void setResourceList(List<Resource> resourceList) 
	{
		this.resourceList = resourceList;
	}
}

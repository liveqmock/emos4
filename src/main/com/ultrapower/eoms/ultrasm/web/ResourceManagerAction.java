package com.ultrapower.eoms.ultrasm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.ResProperty;
import com.ultrapower.eoms.ultrasm.model.Resource;
import com.ultrapower.eoms.ultrasm.service.ResourceManagerService;
import com.ultrapower.eoms.ultrasm.util.JTableParseUtils;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 8, 2010 5:24:27 PM
 * @descibe 
 */
public class ResourceManagerAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private List<Resource> resourceList;
	private List<ResProperty> resPropertyList;
	private ResourceManagerService resourceManagerService;
	private String ser_resourceName;
	private String ser_resourceSystemType;
	private String ser_resourceDefineType;
	private String ser_parentId;
	private Resource resource;
	private ResProperty resProperty;
	private String resourceId;
	private String resids;
	private String respropertyid;
	private String xmlData;
	
	public String resourceList()
	{
		//resourceList = resourceManagerService.getResource();
		return SUCCESS;
	}

	public String resourceSearch()
	{
		resourceList = resourceManagerService.getResource(ser_resourceName.trim(), ser_parentId.trim(), ser_resourceSystemType.trim(), ser_resourceDefineType.trim());
		return this.findForward("resourceList");
	}
	
	public String getResourceInfo()
	{
		if(resourceId!=null)
		{
			resource = resourceManagerService.getResource(resourceId);
			resPropertyList = resourceManagerService.getResProperty(resourceId);
		}
		return this.findForward("resourceSave");
	}
	
	public String getResPropertyInfo()
	{
		if(respropertyid!=null)
		{
			resProperty = resourceManagerService.getResPropertyInfo(respropertyid);
		}
		return this.findForward("resPropertySave");
	}
	
	public String addResource()
	{
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		String parafresh = "";
		String returnMessage = "";
		HashMap map = new HashMap();
		if(xmlData!=null && !xmlData.equals(""))
		{
			map = JTableParseUtils.getResPropertyData(xmlData);
		}
		List<ResProperty> addResPropertyList = new ArrayList();
		List<ResProperty> modResPropertyList = new ArrayList();
		List delResPropertyList = new ArrayList();
		if(map!=null)
		{
			addResPropertyList = (List<ResProperty>) map.get("add");
			modResPropertyList = (List<ResProperty>) map.get("mod");
			delResPropertyList = (List) map.get("del");
		}
		if(resource!=null)
		{//做修改操作
			if(resource.getPid()!=null){
				resource.setLastmodifier(userSession.getPid());
				resource.setLastmodifytime(TimeUtils.getCurrentTime());
				resourceManagerService.updateResource(resource, addResPropertyList, modResPropertyList, delResPropertyList);
				returnMessage = Internation.language("com_msg_saveSuccess")+"!";
				parafresh = "true";
			}
			else
			{//做添加操作
				resource.setCreater(userSession.getPid());
				resource.setCreatetime(TimeUtils.getCurrentTime());
				resource.setLastmodifier(userSession.getPid());
				resource.setLastmodifytime(TimeUtils.getCurrentTime());
				resourceManagerService.addResource(resource,addResPropertyList);
				returnMessage = Internation.language("com_msg_saveSuccess")+"!";
				parafresh = "true";
			}
		}
		else
		{
			returnMessage = Internation.language("com_msg_saveErr")+"!";
			parafresh = "false";
		}
		this.getRequest().setAttribute("parafresh", parafresh);
		this.getRequest().setAttribute("returnMessage", returnMessage);
		return "refresh";
	}
	
	public String transferStatus()
	{
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		resids = StringUtils.checkNullString(resids);
		List idList = UltraSmUtil.arrayToList(resids.split(","));
		String isAudit = PropertiesUtils.getProperty("eoms.isaudit");//是否添加审计日志
		String resName = "";
		if("true".equals(isAudit))
		{
			resName = UltraSmUtil.getNameById("bs_t_sm_resource", "resname", idList);
		}
		boolean result = resourceManagerService.deleteResourceById(idList);
		if("true".equals(isAudit) && result && !"".equals(resName))
		{
			RecordLog.printOperateAuditLog("1", "10502", "删除了("+resName+")资源！");
		}
		resourceList = resourceManagerService.getResource();
		return this.findForward("resourceList");
	}
	
	public String resUrlLoad()
	{
		String pid = this.getRequest().getParameter("resUrlID");
		return findForward("resOpUrlSave");
	}
	
	public List<Resource> getResourceList() 
	{
		return resourceList;
	}

	public void setResourceManagerService(
			ResourceManagerService resourceManagerService) 
	{
		this.resourceManagerService = resourceManagerService;
	}
	
	public String getSer_resourceName() 
	{
		return ser_resourceName;
	}

	public void setSer_resourceName(String ser_resourceName) 
	{
		this.ser_resourceName = ser_resourceName;
	}

	public String getSer_resourceSystemType() 
	{
		return ser_resourceSystemType;
	}

	public void setSer_resourceSystemType(String ser_resourceSystemType) 
	{
		this.ser_resourceSystemType = ser_resourceSystemType;
	}

	public String getSer_resourceDefineType() 
	{
		return ser_resourceDefineType;
	}

	public void setSer_resourceDefineType(String ser_resourceDefineType) 
	{
		this.ser_resourceDefineType = ser_resourceDefineType;
	}

	public String getSer_parentId() 
	{
		return ser_parentId;
	}

	public void setSer_parentId(String ser_parentId) 
	{
		this.ser_parentId = ser_parentId;
	}

	public Resource getResource() 
	{
		return resource;
	}

	public void setResource(Resource resource) 
	{
		this.resource = resource;
	}
	
	public String getResourceId() 
	{
		return resourceId;
	}

	public void setResourceId(String resourceId) 
	{
		this.resourceId = resourceId;
	}

	public String getResids() 
	{
		return resids;
	}

	public void setResids(String resids) 
	{
		this.resids = resids;
	}

	public String getRespropertyid() 
	{
		return respropertyid;
	}

	public void setRespropertyid(String respropertyid) 
	{
		this.respropertyid = respropertyid;
	}

	public void setXmlData(String xmlData) 
	{
		this.xmlData = xmlData;
	}

	public List<ResProperty> getResPropertyList() 
	{
		return resPropertyList;
	}

	public void setResPropertyList(List<ResProperty> resPropertyList) 
	{
		this.resPropertyList = resPropertyList;
	}

	public ResProperty getResProperty() 
	{
		return resProperty;
	}

	public void setResProperty(ResProperty resProperty) 
	{
		this.resProperty = resProperty;
	}
}

package com.ultrapower.eoms.ultrabpp.cache.model;

import java.util.List;

import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.configuration.version.model.WfVersion;

/**
 * 
 * @author fying 
 * @version 
 * @since  
 * @see TemplateDoc
 */
public class WfTypeExportInfo
{
	private WfType wftype;
	private String wfinfo;
	private String fieldexport;
	private String stepexport;
	private String wfdesign;
	private String jsppage;
	private String publishVersion;
	private List<WfVersion> versionList;
	
	public WfType getWftype()
	{
		return wftype;
	}
	public void setWftype(WfType wftype)
	{
		this.wftype = wftype;
	}
	public String getWfinfo()
	{
		return wfinfo;
	}
	public void setWfinfo(String wfinfo)
	{
		this.wfinfo = wfinfo;
	}
	public String getFieldexport()
	{
		return fieldexport;
	}
	public void setFieldexport(String fieldexport)
	{
		this.fieldexport = fieldexport;
	}
	public String getStepexport()
	{
		return stepexport;
	}
	public void setStepexport(String stepexport)
	{
		this.stepexport = stepexport;
	}
	public String getWfdesign()
	{
		return wfdesign;
	}
	public void setWfdesign(String wfdesign)
	{
		this.wfdesign = wfdesign;
	}
	public String getJsppage()
	{
		return jsppage;
	}
	public void setJsppage(String jsppage)
	{
		this.jsppage = jsppage;
	}
	public List<WfVersion> getVersionList()
	{
		return versionList;
	}
	public void setVersionList(List<WfVersion> versionList)
	{
		this.versionList = versionList;
	}
	public String getPublishVersion()
	{
		return publishVersion;
	}
	public void setPublishVersion(String publishVersion)
	{
		this.publishVersion = publishVersion;
	}
	
}

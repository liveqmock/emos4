package com.ultrapower.eoms.ultrabpp.cache.model;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.tools.zip.ZipFile;
//import java.util.zip.ZipFile;

import com.ultrapower.workflow.configuration.sort.model.WfType;

/**
 * 
 * @author fying 
 * @version 
 * @since  
 * @see TemplateDoc
 */
public class ExportInfo
{
	private String wfinfo;
	private String fieldexport;
	private String stepexport;
	private String wfdesign;
	private String jsppage;
	private ZipFile zipfile;
	//private String fileURL;
	
	
	private Map<String,WfTypeExportInfo> wftypeMap;
	
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
	
	public Map<String, WfTypeExportInfo> getWftypeMap()
	{
		return wftypeMap;
	}
	public void setWftypeMap(Map<String, WfTypeExportInfo> wftypeMap)
	{
		this.wftypeMap = wftypeMap;
	}
	public ZipFile getZipfile()
	{
		return zipfile;
	}
	public void setZipfile(ZipFile zipfile)
	{
		this.zipfile = zipfile;
	}

	
	
}

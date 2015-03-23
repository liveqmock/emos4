package com.ultrapower.eoms.ultrabpp.cache.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import jxl.write.WritableWorkbook;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrabpp.cache.manager.ExportXmlImpl;
import com.ultrapower.eoms.ultrabpp.cache.model.ExportInfo;
import com.ultrapower.eoms.ultrabpp.cache.model.WfTypeExportInfo;
import com.ultrapower.eoms.ultrabpp.cache.service.ExportService;
import com.ultrapower.eoms.ultrabpp.model.BaseField;
import com.ultrapower.eoms.workflow.sort.web.WfSortTreeImpl;
import com.ultrapower.eoms.workflow.util.PageLimitUtil;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfSort;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.configuration.sort.service.IWfSortService;
import com.ultrapower.workflow.configuration.version.model.WfVersion;

/**
 * @author fying
 * @version
 * @since
 * @see TemplateDoc
 */
public class ExportAction extends BaseAction
{
	private IWfSortManager ver;
	private WfSortTreeImpl wfSortTreeImpl;
	private ExportService exportService;
	private IWfSortService sortServiceImpl;

	private String wfinfo;
	private String fieldExport;
	private String stepExport;
	private String wfdesign;
	private String jsppage;
	private String baseSchemas;

	// export部分参数
	/**
	 * 流程分类ID
	 */
	private String wfSortId;

	// import部分参数
	private File importFile;
	private String reLoad;
	private ExportInfo exportInfo;
	private String baseSchema;
	private String selectVersion;
	private List<WfVersion> versionList;
	private String baseSchemasConfig;
	private String force;
	private Map<String, List<String>> sqlMapByBaseschema;
	/**
	 * 流程类型list
	 */
	private List<WfType> wfTypeList;
	private Map<String,String> wfTypeMap;

	private final String EXPORT_INFO_XML = "export_info.xml";

	public String exportWfTypeList()
	{
		ver = WorkFlowServiceClient.clientInstance().getSortService();
		wfTypeList = sortServiceImpl.getAllWfType();

		/*List<WfSort> list = wfSortTreeImpl.getAllChildSort(wfSortId);
		try
		{
			this.wfTypeList.addAll(ver.getWfTypeBySortId(wfSortId));
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}

		for (WfSort sort : list)
		{
			try
			{
				this.wfTypeList.addAll(ver.getWfTypeBySortId(sort.getId()));
			} catch (RemoteException e)
			{
				e.printStackTrace();
			}
		}*/

		//wfTypeList = PageLimitUtil.pageLimit(wfTypeList);

		if (baseSchemas != null && baseSchemas.length() > 0)
		{
			String[] baseSchemaStrings = baseSchemas.split(",");
			exportService.exportBegin(baseSchemaStrings, wfinfo, fieldExport, stepExport, wfdesign, jsppage);
			for (int i = 0; i < baseSchemaStrings.length; i++)
			{
				String baseSchema = baseSchemaStrings[i];
				exportService.export(baseSchema, wfinfo, fieldExport, stepExport, wfdesign, jsppage);
			}
			exportService.exportEnd();
			File file = null;
			FileInputStream fis = null;
			OutputStream outs = null;
			try
			{
				this.getResponse().getOutputStream();

				this.getResponse().setContentType("application/octet-stream; CHARSET=utf-8");
				this.getResponse().addHeader("Content-transfer-Encoding", "binary");
				this.getResponse().addHeader("Content-Disposition", "attachment;filename=export.zip");

				file = new File(ExportXmlImpl.class.getResource("/").getPath() + "export.zip");
				fis = new FileInputStream(file);
				BufferedInputStream buff = new BufferedInputStream(fis);
				byte[] b = new byte[1024];
				long k = 0;
			    outs = this.getResponse().getOutputStream();
				outs.flush();
				while (k < file.length())
				{
					int j = buff.read(b, 0, 1024);
					k += j;
					outs.write(b, 0, j);
				}
				outs.flush();
			} catch (IOException e)
			{
				e.printStackTrace();
			}finally{
				
					try
					{ 	if(fis!=null)fis.close();
						if(outs!=null)outs.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				
			}
			return null;
		}

		return "success";
	}

	public String importWfTypeList()
	{
			// 加载文件，解压缩，将对应的配置文件信息得到
			InputStream is = null;
			ZipFile zipFile = null;
			FileOutputStream fileOut = null;
			try
			{
				if("true".equals(this.getRequest().getSession().getAttribute("selectVersionAction"))){
					reLoad = null;
					this.getRequest().getSession().removeAttribute("selectVersionAction");
				}
				if(reLoad!=null&&reLoad.equals("1")){
					this.getRequest().getSession().removeAttribute("importinfo");
				}
				ExportInfo exportInfo = (ExportInfo) this.getRequest().getSession().getAttribute("importinfo");
				if (exportInfo == null)
				{
					exportInfo = new ExportInfo();
				} else
				{
					this.getRequest().getSession().setAttribute("importinfo", exportInfo);
					this.exportInfo = exportInfo;
					return "success";
				}

				File file = new File(ExportXmlImpl.class.getResource("/").getPath() + "export.zip");
				if(!file.exists()){
					file.createNewFile();
				}
				fileOut = new FileOutputStream(file);
				long k = 0;
				byte[] b = new byte[1024];
//				ZipOutputStream zipOut = new ZipOutputStream(fileOut);
				BufferedInputStream buff = new BufferedInputStream(new FileInputStream(importFile));
				fileOut.flush();
				while (k < importFile.length())
				{
					int j = buff.read(b, 0, 1024);
					k += j;
					fileOut.write(b);
				}
				fileOut.flush();
				buff.close();
				
				zipFile = new ZipFile(importFile);
				String zipFileName = importFile.getPath();
				exportInfo.setZipfile(zipFile);

				Enumeration entries = zipFile.getEntries();//.entries();

				ZipEntry entry;
				

				while (entries.hasMoreElements())
				{
					entry = (ZipEntry) entries.nextElement();
					String entryName = entry.getName();

					if (entryName.indexOf("/") == -1 && entryName.indexOf(".xml") > -1)
					{
						//加载export_info.xml文件中导入流程的信息。
						SAXReader reader = new SAXReader();
						reader.setEncoding("UTF-8");
						is = zipFile.getInputStream(entry);
						Document doc = reader.read(is);
						exportService.loadExportInfo(exportInfo, doc);

					} else if (entryName.indexOf("workflow_info.xml") > -1)
					{
						//加载具体流程的流程导出信息
						SAXReader reader = new SAXReader();
						reader.setEncoding("UTF-8");
						is = zipFile.getInputStream(entry);
						Document doc = reader.read(is);
						String baseSchema = entryName.substring(0, entryName.indexOf("/"));
						exportService.loadWorkflowInfo(baseSchema, exportInfo, doc);
					}
				}
				if(is!=null)
					is.close();

				this.getRequest().getSession().setAttribute("importinfo", exportInfo);

				this.exportInfo = exportInfo;
				
				if(exportInfo.getWftypeMap()==null){
					this.getRequest().setAttribute("returnMessage", "没有有效的导入信息，请选择可用的压缩包！");
					return	this.findForward("importFileSelect");
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			}finally{
					try
					{
						if(is!=null)is.close();
						if(fileOut!=null)fileOut.close();
						if(zipFile!=null)zipFile.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
			}

			try
			{
				wfTypeList = WorkFlowServiceClient.clientInstance().getSortService().getAllWfType();
				wfTypeMap = new HashMap<String,String>();
				for(WfType wfType:wfTypeList)
				{
					wfTypeMap.put(wfType.getCode(), wfType.getCode());
				}
			}
			catch (RemoteException e)
			{
				e.printStackTrace();
			}
		
		return "success";
	}

	public String versionList()
	{
		ExportInfo exportInfo = (ExportInfo) this.getRequest().getSession().getAttribute("importinfo");

		WfTypeExportInfo wfTypeExportInfo = exportInfo.getWftypeMap().get(baseSchema);
		if (wfTypeExportInfo != null)
		{
			versionList = wfTypeExportInfo.getVersionList();
		}

		return "success";
	}

	public String saveSelectVersion()
	{
		ExportInfo exportInfo = (ExportInfo) this.getRequest().getSession().getAttribute("importinfo");
		WfTypeExportInfo wfTypeExportInfo = exportInfo.getWftypeMap().get(baseSchema);
		wfTypeExportInfo.setPublishVersion(selectVersion);
		this.getRequest().getSession().setAttribute("importinfo", exportInfo);
		this.getRequest().setAttribute("parafresh", "true");
		this.getRequest().getSession().setAttribute("selectVersionAction", "true");
		return "refresh";
	}

	public String importConfig()
	{
		String fieldImportBaschema = "";
		ExportInfo exportInfo = (ExportInfo) this.getRequest().getSession().getAttribute("importinfo");
		Map<String,WfTypeExportInfo> map = exportInfo.getWftypeMap();
		String[] baseSchemasArray = baseSchemasConfig.split("#");
		for (int i = 0; i < baseSchemasArray.length; i++)
		{
			String baseSchemaConfig = baseSchemasArray[i];
			String[] baseSchemaArray = baseSchemaConfig.split(",");

			String baseSchema = baseSchemaArray[0];
			String wfinfo = baseSchemaArray[1];
			String fieldexport = baseSchemaArray[2];
			String stepexport = baseSchemaArray[3];
			String jsppage = baseSchemaArray[4];
			String wfversion = "";
			for(int j=5;j<baseSchemaArray.length;j++){
				if(j==5){
					wfversion = baseSchemaArray[j];
				}else{
					wfversion = ","+ baseSchemaArray[j];
				}
			}
			
			WfTypeExportInfo wfTypeExportInfo = map.get(baseSchema);
			
			if(wfTypeExportInfo!=null){
				wfTypeExportInfo.setWfinfo(wfinfo);
				wfTypeExportInfo.setFieldexport(fieldexport);
				wfTypeExportInfo.setJsppage(jsppage);
				wfTypeExportInfo.setStepexport(stepexport);
				wfTypeExportInfo.setPublishVersion(wfversion);
				if(fieldexport.equals("1")){
					if(fieldImportBaschema.length()==0){
						fieldImportBaschema = baseSchema;
					}else{
						fieldImportBaschema += ","+baseSchema;
					}
				}
			}
		}
		this.getRequest().getSession().setAttribute("importinfo", exportInfo);
		if("0".equals(force)){
			if(fieldImportBaschema.length()==0){
				exportService.importWorkflowInfo(exportInfo);
				this.getRequest().setAttribute("returnMessage", "导入操作成功！");
				return this.findForward("importFileSelect");
			}else{
				Map<String,Map<String, List<BaseField>>> baseSchemaChangeMap = exportService.contrastOldAndNewField(exportInfo, fieldImportBaschema);
				sqlMapByBaseschema = exportService.getFieldSql(baseSchemaChangeMap);
				return "importFieldSql";
			}
		}else{
			exportService.importWorkflowInfo(exportInfo, true);
			this.getRequest().setAttribute("returnMessage", "强制导入操作成功！");
			return this.findForward("importFileSelect");
		}
		//return "refresh";
	}

	public String importConfigOther(){
		ExportInfo exportInfo = (ExportInfo) this.getRequest().getSession().getAttribute("importinfo");
		exportService.importWorkflowInfo(exportInfo);
		this.getRequest().getSession().setAttribute("importinfo",exportInfo);
		this.exportInfo = exportInfo;
		this.getRequest().setAttribute("returnMessage", "导入操作成功！");
		//return  findForward("importWfTypeList");
		return this.findForward("importFileSelect");
	}
	public WfSortTreeImpl getWfSortTreeImpl()
	{
		return wfSortTreeImpl;
	}

	public void setWfSortTreeImpl(WfSortTreeImpl wfSortTreeImpl)
	{
		this.wfSortTreeImpl = wfSortTreeImpl;
	}

	public String getWfSortId()
	{
		return wfSortId;
	}

	public void setWfSortId(String wfSortId)
	{
		this.wfSortId = wfSortId;
	}

	public List<WfType> getWfTypeList()
	{
		return wfTypeList;
	}

	public void setWfTypeList(List<WfType> wfTypeList)
	{
		this.wfTypeList = wfTypeList;
	}

	public ExportService getExportService()
	{
		return exportService;
	}

	public void setExportService(ExportService exportService)
	{
		this.exportService = exportService;
	}

	public String getWfinfo()
	{
		return wfinfo;
	}

	public void setWfinfo(String wfinfo)
	{
		this.wfinfo = wfinfo;
	}

	public String getFieldExport()
	{
		return fieldExport;
	}

	public void setFieldExport(String fieldExport)
	{
		this.fieldExport = fieldExport;
	}

	public String getStepExport()
	{
		return stepExport;
	}

	public void setStepExport(String stepExport)
	{
		this.stepExport = stepExport;
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

	public String getBaseSchemas()
	{
		return baseSchemas;
	}

	public void setBaseSchemas(String baseSchemas)
	{
		this.baseSchemas = baseSchemas;
	}

	public File getImportFile()
	{
		return importFile;
	}

	public void setImportFile(File importFile)
	{
		this.importFile = importFile;
	}

	
	public String getReLoad()
	{
		return reLoad;
	}

	public void setReLoad(String reLoad)
	{
		this.reLoad = reLoad;
	}

	public ExportInfo getExportInfo()
	{
		return exportInfo;
	}

	public void setExportInfo(ExportInfo exportInfo)
	{
		this.exportInfo = exportInfo;
	}

	public String getBaseSchema()
	{
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}

	public String getSelectVersion()
	{
		return selectVersion;
	}

	public void setSelectVersion(String selectVersion)
	{
		this.selectVersion = selectVersion;
	}

	public List<WfVersion> getVersionList()
	{
		return versionList;
	}

	public void setVersionList(List<WfVersion> versionList)
	{
		this.versionList = versionList;
	}

	public String getBaseSchemasConfig()
	{
		return baseSchemasConfig;
	}

	public void setBaseSchemasConfig(String baseSchemasConfig)
	{
		this.baseSchemasConfig = baseSchemasConfig;
	}

	public String getForce()
	{
		return force;
	}

	public void setForce(String force)
	{
		this.force = force;
	}

	public Map<String, List<String>> getSqlMapByBaseschema()
	{
		return sqlMapByBaseschema;
	}

	public void setSqlMapByBaseschema(Map<String, List<String>> sqlMapByBaseschema)
	{
		this.sqlMapByBaseschema = sqlMapByBaseschema;
	}

	public Map<String, String> getWfTypeMap()
	{
		return wfTypeMap;
	}

	public void setWfTypeMap(Map<String, String> wfTypeMap)
	{
		this.wfTypeMap = wfTypeMap;
	}

	public IWfSortService getSortServiceImpl()
	{
		return sortServiceImpl;
	}

	public void setSortServiceImpl(IWfSortService sortServiceImpl)
	{
		this.sortServiceImpl = sortServiceImpl;
	}

	


}

package com.ultrapower.eoms.ultrasm.web;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.ExcelExpCfg;
import com.ultrapower.eoms.ultrasm.service.CommonExcelService;
import com.ultrapower.eoms.ultrasm.service.ExcelManagerService;
import com.ultrapower.eoms.ultrasm.util.ExcelExpXmlUtil;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

public class ExcelManagerAction extends BaseAction 
{
	private CommonExcelService commonExcelService;
	private File doc;
	private ExcelManagerService excelManagerService;
	private ExcelExpCfg excelExpCfg;
	private List<String> errorInfoList;
	private String xmlstr;
	private String updateTableFrame; //是否更改了表结构
	
	public String exportExcel()
	{
		try
		{
			String cfgMark = this.getRequest().getParameter("cfgmark");
			String sqlName = this.getRequest().getParameter("sqlname");
			String cacheid = "";
			UserSession usersession = ActionContext.getUserSession();
			if (usersession != null)
			{
				cacheid = sqlName + "_" + usersession.getLoginName();
			}
			else
			{
				cacheid = sqlName + "_" + this.getSession().getId();
			}
			errorInfoList = commonExcelService.exportExcelByCfg(this.getResponse(), cfgMark, cacheid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if(errorInfoList != null && errorInfoList.size() > 0)
			return this.findForward("../common/importResult");
		return null;
	}
	
	public String importExcel() throws IOException
	{
		String impConfigXmlName = StringUtils.checkNullString(this.getRequest().getParameter("xmlname"));
		String imptype = StringUtils.checkNullString(this.getRequest().getParameter("imptype"));
		String xmlPath = this.getRequest().getRealPath("");
		xmlPath = xmlPath + "/WEB-INF/classes/importconfig/" + impConfigXmlName;
		if(!"remedy".equals(imptype))
			errorInfoList = commonExcelService.importExcelByCfg(doc, xmlPath);
		else
			errorInfoList = commonExcelService.importExcelByCfgToRemedy(doc, xmlPath);
		if(errorInfoList != null && errorInfoList.size() > 0)
		{
			System.out.println("导入EXCEL过程中出现以下问题：");
			for(int i=0;i<errorInfoList.size();i++)
			{
				System.out.println((i+1)+". "+errorInfoList.get(i));
			}
		}
		if(errorInfoList != null && errorInfoList.size() > 0)
		{
			return this.findForward("../common/importResult");
		}
		this.getRequest().setAttribute("parafresh", true);
		this.getRequest().setAttribute("returnMessage", "导入成功！");
		return "refresh";
	}
	
	public String downloadImportTemplate()
	{
		String impConfigXmlName = StringUtils.checkNullString(this.getRequest().getParameter("xmlname"));
		String xmlPath = this.getRequest().getRealPath("");
		xmlPath = xmlPath + "/WEB-INF/classes/importconfig/" + impConfigXmlName;
		errorInfoList = commonExcelService.downloadImportTemplate(this.getResponse(), xmlPath);
		if(errorInfoList != null && errorInfoList.size() > 0)
			return this.findForward("../common/importResult");
		return null;
	}
	
	/**
	 * 获取导出配置列表
	 * @return
	 */
	public String excelList()
	{
		return SUCCESS;
	}
	/**
	 * 加载对应ID的导出配置
	 * @return
	 */
	public String loadExcelExpCfg()
	{
		String pid = StringUtils.checkNullString(this.getRequest().getParameter("pid"));
		if(!"".equals(pid))
		{
			excelExpCfg = excelManagerService.getExcelCfgByPid(pid);
		}
		return findForward("excelCfg");
	}
	
	/**
	 * 按既定获取表格数据，传到页面根据既定方式进行显示
	 */
	public void getTbData()
	{
		String pid = StringUtils.checkNullString(this.getRequest().getParameter("pid"));
		if(!"".equals(pid))
		{
			Map dataMap = ExcelExpXmlUtil.createXmlData(pid, excelManagerService);
			String tbstr = (String)dataMap.get("tbdata");
			//this.getResponse().getWriter().print("123");
			this.renderText(tbstr);
		}
	}
	
	/**
	 * 保存EXCEL导出配置信息
	 * @return
	 */
	public String saveExcelExpCfg()
	{
		if(excelExpCfg!=null)
		{
			UserSession userSession = ActionContext.getUserSession();
			if("".equals(StringUtils.checkNullString(excelExpCfg.getPid())))
			{
				excelExpCfg.setCreater(userSession.getPid());
				excelExpCfg.setCreatetime(TimeUtils.getCurrentTime());
			}
			excelExpCfg.setLastmodifier(userSession.getPid());
			excelExpCfg.setLastmodifytime(TimeUtils.getCurrentTime());
			Map datamap = ExcelExpXmlUtil.getExcelCfgTrTdData(xmlstr,userSession.getPid());
			boolean b = false;
			b = excelManagerService.saveExcelExpCfg(excelExpCfg, datamap);
			String returnMessage = Internation.language("com_msg_saveErr")+"!";
			if(b)
			{
				returnMessage = Internation.language("com_msg_saveSuccess")+"!";
			}
			String parafresh = "true";
			this.getRequest().setAttribute("parafresh", parafresh);
			this.getRequest().setAttribute("returnMessage", returnMessage);
		}
		return "refresh";
	}
	
	/**
	 * 删除EXCEL导出配置信息
	 * @return
	 */
	public String delExcelCfg()
	{
		String ids = StringUtils.checkNullString(this.getRequest().getParameter("var_selectvalues"));
		if(!"".equals(ids))
		{
			List lst = UltraSmUtil.arrayToList(ids.split(","));
			excelManagerService.deleteExcelExpCfg(lst);
		}
		return findForward("excelList");
	}
	
	/**
	 * 检测EXCEL导出配置标识符是否唯一
	 */
	public void checkCfgMarkUnique()
	{
		String cfgMark = StringUtils.checkNullString(this.getRequest().getParameter("cfgMark"));
		boolean b = false;
		if(!"".equals(cfgMark))
		{
			b = excelManagerService.isCfgMarkUnique(cfgMark);
		}
		this.renderText(String.valueOf(b));
	}
	
	public ExcelExpCfg getExcelExpCfg() {
		return excelExpCfg;
	}

	public void setExcelExpCfg(ExcelExpCfg excelExpCfg) {
		this.excelExpCfg = excelExpCfg;
	}

	public String getXmlstr() {
		return xmlstr;
	}

	public void setXmlstr(String xmlstr) {
		this.xmlstr = xmlstr;
	}

	public String isUpdateTableFrame() {
		return updateTableFrame;
	}

	public void setUpdateTableFrame(String updateTableFrame) {
		this.updateTableFrame = updateTableFrame;
	}

	public void setCommonExcelService(CommonExcelService commonExcelService) {
		this.commonExcelService = commonExcelService;
	}
	public void setDoc(File doc) {
		this.doc = doc;
	}
	public void setExcelManagerService(ExcelManagerService excelManagerService) {
		this.excelManagerService = excelManagerService;
	}
	public List<String> getErrorInfoList() {
		return errorInfoList;
	}
}

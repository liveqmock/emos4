package com.ultrapower.eoms.ultrasm.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.FieldInfo;
import com.ultrapower.eoms.ultrasm.model.TableInfo;
import com.ultrapower.eoms.ultrasm.service.TableManagerService;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.util.JTableParseUtils;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

public class TableManagerAction extends BaseAction {
	
	private TableManagerService tableManagerService;
	private TableInfo tbinfo;
	private List<FieldInfo> fieldList;
	private FieldInfo field;
	private String xmlData;
	
	/**
	 * 返回到库表信息列表
	 * @return
	 */
	public String tableList()
	{
		return SUCCESS;
	}
	
	/**
	 * 加载库表
	 * @return
	 */
	public String load()
	{
		String enname = this.getRequest().getParameter("enname");
		if(enname!=null && !"".equals(enname))
		{
			tbinfo = tableManagerService.getTableByEnname(enname);
			fieldList = tableManagerService.getFieldByTbName(enname);
		}
		return findForward("tableSave");
	}
	
	/**
	 * 存储库表和字段信息
	 * @return
	 */
	public String saveTable()
	{
		
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		String parafresh = "";
		String returnMessage = "";
		HashMap map = new HashMap();
		if(xmlData!=null && !xmlData.equals(""))
		{
			map = JTableParseUtils.getTableFieldData(xmlData);
		}
		List<FieldInfo> addFieldList = new ArrayList();
		List<FieldInfo> modFieldList = new ArrayList();
		List delFieldList = new ArrayList();
		if(map!=null&&map.size()>0){
			addFieldList = (List<FieldInfo>) map.get("add");
			modFieldList = (List<FieldInfo>) map.get("mod");
			delFieldList = (List) map.get("del");
		}
		
		if(tbinfo!=null)
		{
			if(tbinfo.getPid()!=null&&!"".equals(tbinfo.getPid()))
			{
				tbinfo.setLastmodifier(userSession.getPid());
				tbinfo.setLastmodifytime(TimeUtils.getCurrentTime());
				tableManagerService.updateTableAndField(tbinfo, addFieldList, modFieldList, delFieldList);
				returnMessage = Internation.language("com_msg_saveSuccess");
				parafresh = "true";
			}
			else
			{
				tbinfo.setCreater(userSession.getPid());
				tbinfo.setCreatetime(TimeUtils.getCurrentTime());
				tbinfo.setLastmodifier(userSession.getPid());
				tbinfo.setLastmodifytime(TimeUtils.getCurrentTime());
				tableManagerService.addTableAndField(tbinfo, addFieldList);
				returnMessage= Internation.language("com_msg_saveSuccess");
				parafresh = "true";
			}
		}
		else
		{
			returnMessage= Internation.language("com_msg_saveErr");
			parafresh = "false";
		}
		this.getRequest().setAttribute("parafresh", parafresh);
		this.getRequest().setAttribute("returnMessage", returnMessage);
		return "refresh";
	}
	
	/**
	 * 删除库表和字段信息
	 * @return
	 */
	public String delTable()
	{
		String ennames = StringUtils.checkNullString(this.getRequest().getParameter("var_selectvalues"));
		if(!"".equals(ennames))
		{
			tableManagerService.deleteTableByEnname(UltraSmUtil.arrayToList(ennames.split(",")));
		}
		return this.findForward("tableList");
	}
	
	/**
	 * 检查库表唯一性
	 * @throws IOException
	 */
	public void checkUnique() throws IOException
	{
		String ename = StringUtils.checkNullString(this.getRequest().getParameter("tbName"));
		if(!"".equals(ename))
		{
			if(tableManagerService.checkEnameUnique(ename))
			{
				this.getResponse().getWriter().print("true");
			}
			else
			{
				this.getResponse().getWriter().print("false");
			}
		}
		else
		{
			this.getResponse().getWriter().print("false");
		}
		
	}
	
	/**
	 * 加载字段信息
	 * @return
	 */
	public String loadField()
	{
		String id = StringUtils.checkNullString(this.getRequest().getParameter("pid"));
		if(!"".equals(id))
		{
			field = tableManagerService.getFieldById(id);
		}
		else
		{
			String tbEnname = StringUtils.checkNullString(this.getRequest().getParameter("tbEnname"));
			if(!"".equals(tbEnname))
			{
				field = new FieldInfo();
				field.setEnname(tbEnname);
			}
		}
		return findForward("addField");
	}
	
	/**
	 * 检查字段唯一性
	 * @throws IOException
	 */
	public void chkFieldUnique() throws IOException
	{
		String tbEnname = StringUtils.checkNullString(this.getRequest().getParameter("tbEnname"));
		String fieldEnname = StringUtils.checkNullString(this.getRequest().getParameter("fieldEnname"));
		if(!"".equals(tbEnname) && !"".equals(fieldEnname))
		{
			if(tableManagerService.checkFieldUnique(tbEnname, fieldEnname))
			{
				this.getResponse().getWriter().print("true");
			}
			else
			{
				this.getResponse().getWriter().print("false");
			}
		}
		else
		{
			this.getResponse().getWriter().print("false");
		}
	}
	
	public String tableFieldManageFrame()
	{
		return SUCCESS;
	}
	
	/**
	 * 根据库表名获得表字段信息
	 * @return
	 */
	public String getFieldFromTable()
	{
		String tbEnname = StringUtils.checkNullString(this.getRequest().getParameter("tbEnname"));
		Map map = new HashMap();
		map.put("tbenname", tbEnname); 
		this.getRequest().setAttribute("valuemap", map);
		return this.findForward("tableFieldManage_right");
	}
	
	/**
	 * 保存字段信息
	 * @return
	 */
	public String saveField()
	{
		String parafresh = "true";
		String returnMessage = "";
		if(tableManagerService.saveField(field))
		{
			returnMessage = Internation.language("com_msg_saveSuccess")+"!";
		}
		else
		{
			returnMessage = Internation.language("com_msg_saveErr")+"!";
		}
		this.getRequest().setAttribute("parafresh", parafresh);
		this.getRequest().setAttribute("returnMessage", returnMessage);
		return "refresh";
	}
	
	/**
	 * 删除字段信息
	 * @return
	 */
	public String delField()
	{
		String ids = StringUtils.checkNullString(this.getRequest().getParameter("var_selectvalues"));
		if(!"".equals(ids))
		{
			tableManagerService.deleteField(UltraSmUtil.arrayToList(ids.split(",")));
		}
		String tbEnname = StringUtils.checkNullString(this.getRequest().getParameter("tbEnname"));
		Map map = new HashMap();
		map.put("tbenname", tbEnname); 
		this.getRequest().setAttribute("valuemap", map);
		return this.findForward("tableFieldManage_right");
	}
	
	/**
	 * 返回到字典类型选择列表
	 * @return
	 */
	public String loadDic()
	{
		return findForward("dicSelect");
	}
	
	/*
	 * 以下为属性的get/set区域
	 */
	public void setTableManagerService(TableManagerService tableManagerService) 
	{
		this.tableManagerService = tableManagerService;
	}

	public TableInfo getTbinfo() 
	{
		return tbinfo;
	}

	public void setTbinfo(TableInfo tbinfo) {
		this.tbinfo = tbinfo;
	}

	public List<FieldInfo> getFieldList() 
	{
		return fieldList;
	}

	public void setFieldList(List<FieldInfo> fieldList) 
	{
		this.fieldList = fieldList;
	}

	public FieldInfo getField() 
	{
		return field;
	}

	public void setField(FieldInfo field) 
	{
		this.field = field;
	}

	public void setXmlData(String xmlData) 
	{
		this.xmlData = xmlData;
	}
}

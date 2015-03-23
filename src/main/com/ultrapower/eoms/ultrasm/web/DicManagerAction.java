package com.ultrapower.eoms.ultrasm.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import net.sf.json.JSONObject;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.DicType;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;

public class DicManagerAction extends BaseAction
{
	private DicManagerService dicManagerService;
	private List<DicItem> dicitsmList;
	private DicType dictype;
	private DicItem dicitem;
	private String dicId;//字典ID
	private String type;//字典类型和信息项标识 1:类型;2:信息项
	
	//根据字典code和字典项的value获取字典项并返回josn
	public void getDicItemByCodeAndValue(){
		DicItem dicItem = dicManagerService.getDicItemByValue( dicitem.getDtcode(),dicitem.getDivalue());
		renderText(JSONObject.fromObject(dicItem).toString());
	}
	
	public void getChildNode() throws IOException {
		String dictypecode = this.getRequest().getParameter("dictypecodePara");
		String dicvalue = this.getRequest().getParameter("dicvaluePara");
		dicitsmList = dicManagerService.getDicItem(dicvalue,dictypecode);
		int dicitsmListLen = 0;
		if(dicitsmList!=null)
			dicitsmListLen = dicitsmList.size();
		String text = "";
		for(int i=0;i<dicitsmListLen;i++){
			DicItem dicItem = dicitsmList.get(i);
			String value = dicItem.getDivalue();
			String name = dicItem.getDiname();
			text += value+","+name+";";
		}
		if(text!="")
			text = text.substring(0, text.length()-1);
		this.renderText(text);
	}
	
	public void getTextByValue()
	{
		String dictypecode = this.getRequest().getParameter("dictypecodePara");
		String dicvalue = this.getRequest().getParameter("dicvaluePara");
		dicitsmList = dicManagerService.getDicItem(dicvalue,dictypecode);
		int dicitsmListLen = 0;
		if(dicitsmList!=null)
			dicitsmListLen = dicitsmList.size();
		String text = "";
		for(int i=0;i<dicitsmListLen;i++){
			DicItem dicItem = dicitsmList.get(i);
			String value = dicItem.getDivalue();
			String name = dicItem.getDiname();
			text += value+","+name+";";
		}
		if(text!="")
			text = text.substring(0, text.length()-1);
		this.renderText(text);
	}
	
	/**
	 * 返回字典类型列表页面
	 * @return
	 */
	public String dicTypeList()
	{
		return this.findForward("dicTypeList");
	}
	
	/**
	 * 返回字典信息项列表页面
	 * @return
	 */
	public String dicItemList()
	{
		return this.findForward("dicItemList");
	}
	
	/**
	 * 数据字典信息管理配置
	 * @return
	 */
	public String dicManager()
	{
		dicitsmList = dicManagerService.getDicItemByDicType("dictype");
		return this.findForward("dicManageFrame");
	}
	
	public String dicManagerLeft()
	{
		String dictype = StringUtils.checkNullString(this.getRequest().getParameter("datadictype"));
		this.getRequest().setAttribute("datadictype", dictype);
		String diname = "字典类型：";
		if("0".equals(dictype))
			diname += "所有类型";
		else
			diname += dicManagerService.getTextByValue("dictype", dictype);
		this.getRequest().setAttribute("diname", diname);
		return this.findForward("dicManage_left");
	}
	
	/**
	 * 根据ID获取字典信息
	 * @return
	 */
	public String dicInfo()
	{
		String src = "dicManage_type";
		if(dicId != null && !"0".equals(dicId))
		{
			if(dicId.indexOf(":") > 0)//当ID中包含":"字符时,此字典ID为字典类型ID,则获取字典类型信息
			{
				String dtId = dicId.split(":")[0];
				dictype = dicManagerService.getDicTypeById(dtId);
				type = "1";
				src = "dicManage_type";
			}
			else//否则,此字典ID为字典信息项ID,则获取字典信息项信息
			{
				dicitem = dicManagerService.getDicItemById(dicId);
				if(dicitem != null)
				{
					this.getRequest().setAttribute("dtname", dicManagerService.getDtNameByDtcode(dicitem.getDtcode()));
					String parentname = "";
					if(!"0".equals(dicitem.getParentid()))
					{
						DicItem di = dicManagerService.getDicItemById(dicitem.getParentid());
						if(di != null)
							parentname = di.getDiname();
					}
					this.getRequest().setAttribute("parentname", parentname);
				}
				type = "2";
				src = "dicManage_item";
			}
		}
		//cfgpage参数：1.代表返回的是配置页面 0.代表返回的是非配置页面 区别：配置页面包含页面上方的工具按钮;非配置页面包含下侧的保存和取消按钮
		this.getRequest().setAttribute("cfgpage", StringUtils.checkNullString(this.getRequest().getParameter("cfgpage")));
		return this.findForward(src);
	}
	
	/**
	 * 跳转页面,在类型管理配置页面时添加字典信息项
	 * @return
	 */
	public String addItem()
	{
		if(dictype != null)
		{
			dicitem = new DicItem();
			dicitem.setPid("");
			dicitem.setDtid(dictype.getPid());
			dicitem.setDtcode(dictype.getDtcode());
			this.getRequest().setAttribute("dtname", dictype.getDtname());
			dicitem.setParentid("0");
			//dicitem.setIsdefault("0");
			dicitem.setStatus((long) 1);
			type = "2";
		}
		String cfgpage = StringUtils.checkNullString(this.getRequest().getParameter("cfgpage"));
		this.getRequest().setAttribute("cfgpage", cfgpage);
		return this.findForward("dicManage_item");
	}
	
	/**
	 * 保存字典信息
	 * @return
	 */
	public String dicInfoSave()
	{
		String src = "dicManage_type";
		if(type != null)//type 1.表示字典类型 2.表示字典信息项
		{
			UserSession userSession = this.getUserSession();
			String userpid = "";
			if(userSession != null)
				userpid = userSession.getPid();
			long currentTime = TimeUtils.getCurrentTime();
			if("1".equals(type))
			{
				dictype.setLastmodifier(userpid);
				dictype.setLastmodifytime(currentTime);
				if("".equals(StringUtils.checkNullString(dictype.getPid())))
				{
					dictype.setCreater(userpid);
					dictype.setCreatetime(currentTime);
					dicId = dicManagerService.addDicType(dictype);
				}
				else
				{
					dicId = dicManagerService.updateDicType(dictype);
				}
				src = "dicManage_type";
			}
			else if("2".equals(type))
			{
				dicitem.setLastmodifier(userpid);
				dicitem.setLastmodifytime(currentTime);
				if("".equals(StringUtils.checkNullString(dicitem.getPid())))
				{
					dicitem.setCreater(userpid);
					dicitem.setCreatetime(currentTime);
					DicItem di = dicManagerService.getDicItemById(dicitem.getParentid());
					String dicFullName = "";
					if(di != null)
						dicFullName = StringUtils.checkNullString(di.getDicfullname());
					dicFullName = "".equals(dicFullName)?dicitem.getDiname():dicFullName+"."+dicitem.getDiname();
					dicitem.setDicfullname(dicFullName);
					dicId = dicManagerService.addDicItem(dicitem);
				}
				else
				{
					dicId = dicManagerService.updateDicItem(dicitem);
				}
				src = "dicManage_item";
			}
		}
		
		//cfgpage参数：1.代表返回的是配置页面 0.代表返回的是非配置页面 区别：配置页面包含页面上方的工具按钮;非配置页面包含下侧的保存和取消按钮
		String cfgpage = StringUtils.checkNullString(this.getRequest().getParameter("cfgpage"));
		this.getRequest().setAttribute("cfgpage", cfgpage);
		if("0".equals(cfgpage))
		{
			String returnMessage = Internation.language("com_msg_saveErr")+"!";
			if(!"".equals(StringUtils.checkNullString(dicId)))
				returnMessage = Internation.language("com_msg_saveSuccess")+"!";
			this.getRequest().setAttribute("parafresh", "true");
			this.getRequest().setAttribute("returnMessage", returnMessage);
			return "refresh";
		}
		this.getRequest().setAttribute("message","success");
		return this.findForward(src);
	}
	
	/**
	 * 验证是否唯一字典
	 * @throws IOException
	 */
	public void checkUnique() throws IOException
	{
		boolean result = false;
		if("1".equals(type))
		{
			String dtcode = this.getRequest().getParameter("dtcode");
			result = dicManagerService.isUniqueDictype(dtcode);
		}
		else if("2".equals(type))
		{
			String dtcode = this.getRequest().getParameter("dtcode");
			String divalue = this.getRequest().getParameter("divalue");
			result = dicManagerService.isUniqueDicItem(dtcode, divalue);
		}
		PrintWriter out = this.getResponse().getWriter();
		out.print(String.valueOf(result));
	}
	
	public String dicInfoDel()
	{
		boolean result = false;
		String src = "dicManage_type";
		if("1".equals(type))
		{
			dictype = dicManagerService.getDicTypeById(dicId);
			result = dicManagerService.deleteDicTypeById(dicId);
			src = "dicManage_type";
		}
		else if("2".equals(type))
		{
			dicitem = dicManagerService.getDicItemById(dicId);
			result = dicManagerService.deleteDicItemById(dicId);
			src = "dicManage_item";
		}
		String isAudit = PropertiesUtils.getProperty("eoms.isaudit");//是否添加审计日志
		if("true".equals(isAudit) && result)
		{
			if("1".equals(type))
				RecordLog.printOperateAuditLog("1", "10702", "删除了("+dictype.getDtname()+")字典类型！");
			else if("2".equals(type))
				RecordLog.printOperateAuditLog("1", "10702", "删除了("+dicitem.getDiname()+")字典信息项！");
			this.getRequest().setAttribute("message","success");
		}
		return this.findForward(src);
	}
	
	public void getLevelMenu() throws IOException
	{
		String result = "";
		String top_id = StringUtils.checkNullString(this.getRequest().getParameter("top_id"));
		String lm_id = StringUtils.checkNullString(this.getRequest().getParameter("lm_id"));
		List<DicItem> diList = null;
		if(top_id.equals(lm_id)) {
			diList = dicManagerService.getTopDicItemByDtcode(top_id);
		} else {
			diList = dicManagerService.getDicItem(lm_id, top_id);
		}
		result = this.thansObjToStr(diList);
		PrintWriter out = this.getResponse().getWriter();
		out.print(result);
	}
	
	private String thansObjToStr(List<DicItem> diList) {
		if(diList == null) {
			return "";
		}
		StringBuffer diStr = new StringBuffer();
		for(DicItem dicItem : diList) {
			if(dicItem == null) {
				continue;
			}
			diStr.append(";");
			diStr.append(dicItem.getDivalue());
			diStr.append(",");
			diStr.append(dicItem.getDiname());
			diStr.append(",");
			diStr.append("1");
		}
		return diStr.length() > 0 ? diStr.toString().substring(1) : "";
	}
	
	/*
	 * 以下为属性的GET/SET方法
	 */
	public void setDicManagerService(DicManagerService dicManagerService) 
	{
		this.dicManagerService = dicManagerService;
	}
	public List<DicItem> getDicitsmList() 
	{
		return dicitsmList;
	}
	public void setDicitsmList(List<DicItem> dicitsmList) 
	{
		this.dicitsmList = dicitsmList;
	}
	public DicType getDictype() 
	{
		return dictype;
	}
	public DicItem getDicitem() 
	{
		return dicitem;
	}
	public void setDictype(DicType dictype) 
	{
		this.dictype = dictype;
	}
	public void setDicitem(DicItem dicitem) 
	{
		this.dicitem = dicitem;
	}
	public String getDicId() 
	{
		return dicId;
	}
	public void setDicId(String dicId) 
	{
		this.dicId = dicId;
	}
	public String getType() 
	{
		return type;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
}

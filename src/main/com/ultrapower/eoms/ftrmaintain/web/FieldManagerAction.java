package com.ultrapower.eoms.ftrmaintain.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ftrmaintain.model.IndexFieldCfg;
import com.ultrapower.eoms.ftrmaintain.service.IndexCategoryService;
import com.ultrapower.eoms.ftrmaintain.service.IndexFieldService;

public class FieldManagerAction extends BaseAction {
	private IndexFieldService indexFieldService;
	private IndexCategoryService indexCategoryService;
	private IndexFieldCfg field;
	private Map<String,String> indexCategory;
	
	public String indexFieldList()
	{
		return SUCCESS;
	}
	
	public String fieldLoad()
	{
		String pid = StringUtils.checkNullString(this.getRequest().getParameter("pid"));
		if(!"".equals(pid))
		{
			field = indexFieldService.getFieldById(pid);
		}
		indexCategory = indexCategoryService.getPhysicalCategoryMap();
		return findForward("indexFieldSave");
	}

	public String saveField()
	{
		boolean b = false;
		if(field!=null)
		{
			b = indexFieldService.saveField(field);
		}
		if(b)
		{
			this.getRequest().setAttribute("parafresh", "true");
			this.getRequest().setAttribute("returnMessage", Internation.language("com_msg_saveSuccess")+"!");
		}
		else
		{
			this.getRequest().setAttribute("parafresh", "false");
			this.getRequest().setAttribute("returnMessage", Internation.language("com_msg_saveErr")+"!");
		}
		return "refresh";
	}
	
	public String delField()
	{
		String ids = StringUtils.checkNullString(this.getRequest().getParameter("var_selectvalues"));
		if(!"".equals(ids))
		{
			String[] idArr = ids.split(",");
			List<String> idlst = new ArrayList<String>();
			for(int i=0;i<idArr.length;i++)
			{
				idlst.add(idArr[i]);
			}
			if(idlst.size()>0)
				indexFieldService.deleteField(idlst);
		}
		return findRedirect("indexFieldList");
	}
	
	public void setIndexFieldService(IndexFieldService indexFieldService) {
		this.indexFieldService = indexFieldService;
	}

	public IndexFieldCfg getField() {
		return field;
	}

	public void setField(IndexFieldCfg field) {
		this.field = field;
	}

	public void setIndexCategoryService(IndexCategoryService indexCategoryService) {
		this.indexCategoryService = indexCategoryService;
	}

	public Map<String, String> getIndexCategory() {
		return indexCategory;
	}

	public void setIndexCategory(Map<String, String> indexCategory) {
		this.indexCategory = indexCategory;
	}
	
}

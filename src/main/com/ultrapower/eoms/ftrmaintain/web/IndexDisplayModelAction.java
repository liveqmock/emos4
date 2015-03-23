package com.ultrapower.eoms.ftrmaintain.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ftrmaintain.model.DisplayModel;
import com.ultrapower.eoms.ftrmaintain.service.IndexCategoryService;
import com.ultrapower.eoms.ftrmaintain.service.IndexDisplayModelService;

public class IndexDisplayModelAction extends BaseAction {
	private IndexDisplayModelService indexDisplayModelService;
	private IndexCategoryService indexCategoryService;
	private DisplayModel displayModel;
	private Map<String,String> indexCategory;
	
	public String modelList()
	{
		return findForward("displayModelList");
	}
	
	public String loadModel()
	{
		String pid = StringUtils.checkNullString(this.getRequest().getParameter("pid"));
		if("".equals(pid))
		{
			indexCategory = indexDisplayModelService.getPhysicalCategoryMap();
			return findForward("displayModelSave");
		}
		displayModel = indexDisplayModelService.getModelById(pid);
		if(displayModel==null)
		{
			indexCategory = indexDisplayModelService.getPhysicalCategoryMap();
		}
		else
		{
			indexCategory = indexCategoryService.getPhysicalCategoryMap();
		}
		return findForward("displayModelSave");
	}
	
	public String saveModel()
	{
		boolean b = false;
		if(displayModel!=null)
		{
			String spanInfo = StringUtils.checkNullString(this.getRequest().getParameter("fldSpanInfo"));
			if("".equals(spanInfo))
				b = false;
			else
			{
				b = indexDisplayModelService.saveModel(displayModel, spanInfo);
			}
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
	
	public void getFieldSpanInfo()
	{
		String indexid = this.getRequest().getParameter("indexid");
		String info = indexDisplayModelService.getSpanInfoByIndexId(indexid);
		this.renderText(info);
	}
	
	public String delModel()
	{
		String idstr = StringUtils.checkNullString(getRequest().getParameter("var_selectvalues"));
		if(!"".equals(idstr))
		{
			String[] idArr = idstr.split(",");
			List<String> idlst = new ArrayList<String>();
			for(int i=0;i<idArr.length;i++)
			{
				idlst.add(idArr[i]);
			}
			indexDisplayModelService.delModel(idlst);
		}
		return this.findRedirect("modelList");
	}
	
	public DisplayModel getDisplayModel() {
		return displayModel;
	}

	public void setDisplayModel(DisplayModel displayModel) {
		this.displayModel = displayModel;
	}

	public void setIndexDisplayModelService(
			IndexDisplayModelService indexDisplayModelService) {
		this.indexDisplayModelService = indexDisplayModelService;
	}

	public Map<String, String> getIndexCategory() {
		return indexCategory;
	}

	public void setIndexCategoryService(IndexCategoryService indexCategoryService) {
		this.indexCategoryService = indexCategoryService;
	}
	
}

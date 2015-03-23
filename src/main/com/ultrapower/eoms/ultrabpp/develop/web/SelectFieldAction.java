package com.ultrapower.eoms.ultrabpp.develop.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.model.component.specialfield.assigntreefield.AssignTreeField;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.model.DicType;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;

public class SelectFieldAction extends BaseAction
{
	private DicManagerService dicManagerService;

	public void getDicInfo() throws IOException
	{
		String dicId = StringUtils.checkNullString(this.getRequest().getParameter("dicId"));
		String code = "";
		String para = ""; 
		if (dicId != null && !"0".equals(dicId))
		{
			if (dicId.indexOf(":") > 0)//当ID中包含":"字符时,此字典ID为字典类型ID,则获取字典类型信息
			{
				String dtId = dicId.split(":")[0];
				DicType dictype = dicManagerService.getDicTypeById(dtId);  
				code = dictype.getDtcode(); 
			}
			else
			//否则,此字典ID为字典信息项ID,则获取字典信息项信息
			{
				DicItem dicitem = dicManagerService.getDicItemById(dicId);
				code = dicitem.getDtcode();
				para = dicitem.getDicfullname(); 
			}
		}
		StringBuffer result = new StringBuffer();
		result.append(code);
		result.append(":");
		result.append(para);
		PrintWriter out = this.getResponse().getWriter();
		out.print(result.toString());
	}

	public DicManagerService getDicManagerService()
	{
		return dicManagerService;
	}

	public void setDicManagerService(DicManagerService dicManagerService)
	{
		this.dicManagerService = dicManagerService;
	}
	
}

package com.ultrapower.eoms.ultrabpp.model.component.specialfield.rollbacklistfield;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDraw;


public class RollbackListDrawPC implements BaseFieldDraw<RollbackListField>
{

	String RN = DeployManagerImpl.RN;
	
	public String doStartTag(RollbackListField field)
	{
		String html = "";
		if (field != null)
		{
			String label = field.getLabel();
			String fieldName = field.getFieldName();
			int singleSelect = field.getSingleSelect();
			int visiable = field.getVisiable();
			String type = field.getType();
			String typeStr = "";
			if(StringUtils.isNotBlank(type))
			{
				typeStr = "type=\""+type+"\"";
			}
			html += "<ubf:RollbackListField name=\""+fieldName+"\" label=\""+label+"\" radio=\""+singleSelect+"\" "+typeStr+" visiable=\""+visiable+"\" />" + RN;
		}
		return html;
	}

	public String doEndTag(RollbackListField field)
	{
		String html = "";
		if (field != null)
		{
			
		}
		return html;
	}
}

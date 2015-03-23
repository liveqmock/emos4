package com.ultrapower.eoms.ultrabpp.model.component.displayfield.viewfield;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDraw;

public class ViewDrawPC implements BaseFieldDraw<ViewField>
{
	String RN = DeployManagerImpl.RN;
	
	public String doStartTag(ViewField field) {
		String html = "";
		if (field != null) {
			int visiable = field.getVisiable();
			String fieldName = field.getFieldName();
			String label = field.getLabel();
			String pageurl = field.getPageurl();
			String type = field.getType();
			if(StringUtils.isNotBlank(type)) type = "frame";
			int rowspan = field.getRowspan();
			int cellspan = field.getColspan();
			String labelHtml = "";
			if (StringUtils.isNotBlank(label)) {
				labelHtml = "label=\""+label+"\"";
			}
			html = "<ubf:ViewField name=\""+fieldName+"\" "+labelHtml+" row=\""+rowspan+"\" cell=\""+cellspan+"\" type=\""+type+"\" visiable=\""+visiable+"\" url=\""+pageurl+"\"/>" + RN;
		}
		return html;
	}

	
	public String doEndTag(ViewField field) {
		String html = "";
		if (field != null) {
			
		}
		return html;
	}

}

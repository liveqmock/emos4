package com.ultrapower.eoms.ultrabpp.model.component.datafield.selectfield;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDraw;

public class SelectDrawPC implements BaseFieldDraw<SelectField>
{
	String RN = DeployManagerImpl.RN;
	
	public String doStartTag(SelectField field) {
		String html = "";
		if (field != null) {
			int visiable = field.getVisiable();
			String fieldName = field.getFieldName();
			String label = field.getLabel();
			String type = field.getType();
			String typeResource = field.getTypeResource();
			String paras = field.getParas();
			if (paras == null) {
				paras = "";
			}
			String defaultValue = field.getDefaultValue();
			String def = "";
			if (StringUtils.isNotBlank(defaultValue)) {
				def = "defaultValue=\""+defaultValue+"\"";
			}
			int colspan = field.getColspan();
			html = "<ubf:SelectField name=\""+fieldName+"\" label=\""+label+"\" type=\""+type+"\" resource=\""+typeResource+"\" paras=\""+paras+"\" cell=\""+colspan+"\" visiable=\""+visiable+"\" "+def+" />" + RN;
		}
		return html;
	}

	public String doEndTag(SelectField field) {
		String html = "";
		if (field != null) {
			
		}
		return html;
	}


}

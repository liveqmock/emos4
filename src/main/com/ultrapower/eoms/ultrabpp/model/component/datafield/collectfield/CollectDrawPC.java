package com.ultrapower.eoms.ultrabpp.model.component.datafield.collectfield;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDraw;

public class CollectDrawPC implements BaseFieldDraw<CollectField>
{
	String RN = DeployManagerImpl.RN;
	
	public String doEndTag(CollectField field)
	{
		String html = "";
		return html;
	}

	public String doStartTag(CollectField field)
	{
		String html = "";
		if (field != null) {
			int visiable = field.getVisiable();
			String fieldName = field.getFieldName();
			String label = field.getLabel();
			String type = field.getType();
			String typeResource = field.getTypeResource();
			String paras = field.getParas();
			String showType = field.getShowType();
			if (paras == null) {
				paras = "";
			}
			String defaultValue = field.getDefaultValue();
			String def = "";
			if (StringUtils.isNotBlank(defaultValue)) {
				def = "defaultValue=\""+defaultValue+"\"";
			}
			int colspan = field.getColspan();
			int rowspan = field.getRowspan();
			html = "<ubf:CollectField name=\""+fieldName+"\" label=\""+label+"\" type=\""+type+"\" showtype=\""+showType+"\" resource=\""+typeResource+"\" paras=\""+paras+"\" row=\""+rowspan+"\" cell=\""+colspan+"\" visiable=\""+visiable+"\" "+def+" />" + RN;
		}
		return html;
	}

}

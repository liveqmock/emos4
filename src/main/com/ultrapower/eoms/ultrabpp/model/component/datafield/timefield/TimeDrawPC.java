package com.ultrapower.eoms.ultrabpp.model.component.datafield.timefield;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDraw;

public class TimeDrawPC implements BaseFieldDraw<TimeField>
{
	String RN = DeployManagerImpl.RN;
	
	public String doStartTag(TimeField field) {
		String html = "";
		if (field != null) {
			int visiable = field.getVisiable();
			String fieldName = field.getFieldName();
			String label = field.getLabel();
			int colspan = field.getColspan();
			String timeFormat = field.getTimeFormat();
			String format = "";
			if (StringUtils.isNotBlank(timeFormat)) {
				format = "format=\""+timeFormat+"\"";
			}
			String def = "";
			String defaultValue = field.getDefaultValue();
			if (StringUtils.isNotBlank(defaultValue)) {
				def = "defaultValue=\""+defaultValue+"\"";
			}
			html = "<ubf:TimeField name=\""+fieldName+"\" label=\""+label+"\" row=\"1\" cell=\""+colspan+"\" visiable=\""+visiable+"\" "+format+" "+def+" />" + RN;
		}
		return html;
	}


	public String doEndTag(TimeField field) {
		String html = "";
		if (field != null) {
			
		}
		return html;
	}

}

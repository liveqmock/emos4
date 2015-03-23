package com.ultrapower.eoms.ultrabpp.model.component.datafield.hiddenfield;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDraw;

public class HiddenDrawPC implements BaseFieldDraw<HiddenField>
{
	String RN = DeployManagerImpl.RN;
	
	public String doStartTag(HiddenField field) {
		String html = "";
		if (field != null) {
			String fieldName = field.getFieldName();
			String label = field.getLabel();
			String defaultValue = field.getDefaultValue();
			int visiable = field.getVisiable();
			int colspan = field.getColspan();
			int length = field.getLength();
			String def = "";
			if (StringUtils.isNotBlank(defaultValue)) {
				def = "defaultValue=\""+defaultValue+"\"";
			}
			html = "<ubf:HiddenField name=\""+fieldName+"\" label=\""+label+"\" cell=\""+colspan+"\" length=\""+length+"\" "+def+"/>" + RN;
		}
		return html;
	}

	public String doEndTag(HiddenField field) {
		String html = "";
		if (field != null) {
			
		}
		return html;
	}
}

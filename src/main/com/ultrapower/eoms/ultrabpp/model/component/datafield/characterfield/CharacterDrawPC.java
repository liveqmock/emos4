package com.ultrapower.eoms.ultrabpp.model.component.datafield.characterfield;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDraw;

public class CharacterDrawPC implements BaseFieldDraw<CharacterField>
{
	
	String RN = DeployManagerImpl.RN;
	
	public String doStartTag(CharacterField field) {
		String html = "";
		if (field != null) {
			String fieldName = field.getFieldName();
			String label = field.getLabel();
			String defaultValue = field.getDefaultValue();
			int visiable = field.getVisiable();
			int rowspan = field.getRowspan();
			int colspan = field.getColspan();
			int length = field.getLength();
			String def = "";
			if (StringUtils.isNotBlank(defaultValue)) {
				def = "defaultValue=\""+defaultValue+"\"";
			}
			html = "<ubf:CharacterField name=\""+fieldName+"\" label=\""+label+"\" row=\""+rowspan+"\" cell=\""+colspan+"\" visiable=\""+visiable+"\" length=\""+length+"\" "+def+"/>" + RN;
		}
		return html;
	}

	public String doEndTag(CharacterField field) {
		String html = "";
		if (field != null) {
			int visiable = field.getVisiable();
			if (1 == visiable) {
				
			}
		}
		return html;
	}

}

package com.ultrapower.eoms.ultrabpp.model.component.displayfield.blankfield;

import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDraw;

public class BlankDrawPC implements BaseFieldDraw<BlankField>
{
	String RN = DeployManagerImpl.RN;
	
	public String doStartTag(BlankField field) {
		String html = "";
		if (field != null) {
			int visiable = field.getVisiable();
			String fieldName = field.getFieldName();
			int cols = field.getColspan();
			int rows = field.getRowspan();
			html = "<ubf:BlankField name=\""+fieldName+"\" cell=\""+cols+"\" row=\""+rows+"\" visiable=\""+visiable+"\"></ubf:BlankField>" + RN;
		}
		return html;
	}

	public String doEndTag(BlankField field) {
		String html = "";
		if (field != null) {
			
		}
		return html;
	}
}

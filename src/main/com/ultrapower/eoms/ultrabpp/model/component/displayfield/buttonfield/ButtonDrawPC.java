package com.ultrapower.eoms.ultrabpp.model.component.displayfield.buttonfield;

import org.apache.commons.lang.StringUtils;
import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDraw;

public class ButtonDrawPC implements BaseFieldDraw<ButtonField>
{
String RN = DeployManagerImpl.RN;
	
	public String doStartTag(ButtonField field) {
		String html = "";
		if (field != null) {
			int visiable = field.getVisiable();
			String fieldName = field.getFieldName();
			String label = field.getLabel();
			int cols = field.getColspan();
			int rows = field.getRowspan();
			String code = field.getButtonCode();
			String labelHtml = "";
			if (StringUtils.isNotBlank(label)) {
				labelHtml = "label=\""+label+"\"";
			}
			html = "<ubf:ButtonField name=\""+fieldName+"\" "+labelHtml+" cell=\""+cols+"\" row=\""+rows+"\" code=\""+code+"\" visiable=\""+visiable+"\"></ubf:ButtonField>" + RN;
		}
		return html;
	}
	
	public String doEndTag(ButtonField field) {
		String html = "";
		if (field != null) {
			
		}
		return html;
	}
}

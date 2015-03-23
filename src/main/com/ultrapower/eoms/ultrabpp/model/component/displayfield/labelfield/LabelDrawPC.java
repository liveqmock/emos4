package com.ultrapower.eoms.ultrabpp.model.component.displayfield.labelfield;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDraw;

public class LabelDrawPC implements BaseFieldDraw<LabelField>
{
	String RN = DeployManagerImpl.RN;
	
	public String doStartTag(LabelField field) {
		String html = "";
		if (field != null) {
			int visiable = field.getVisiable();
			String fieldName = field.getFieldName();
			String label = field.getLabel();
			int cols = field.getColspan();
			int rows = field.getRowspan();
			String csshtml = "";
			String css = field.getCssName();
			if(StringUtils.isNotBlank(css))
			{
				csshtml = "css=\"" + css + "\"";
			}
			html = "<ubf:LabelField name=\""+fieldName+"\" label=\""+label+"\" cell=\""+cols+"\" row=\""+rows+"\" visiable=\""+visiable+"\" "+csshtml+"></ubf:LabelField>" + RN;
		}
		return html;
	}
	
	public String doEndTag(LabelField field) {
		String html = "";
		if (field != null) {
			
		}
		return html;
	}

}

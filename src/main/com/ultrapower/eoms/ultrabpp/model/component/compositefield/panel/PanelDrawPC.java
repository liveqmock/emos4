package com.ultrapower.eoms.ultrabpp.model.component.compositefield.panel;

import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDraw;

public class PanelDrawPC implements BaseFieldDraw<PanelField>
{
	
	String RN = DeployManagerImpl.RN;
	
	public String doStartTag(PanelField field) {
		String html = "";
		if (field != null) {
			int visiable = field.getVisiable();
			String label = field.getLabel();
			String fieldName = field.getFieldName();
			html = "<ubf:Panel name=\""+fieldName+"\" label=\""+label+"\" visiable=\""+visiable+"\">" + RN;
		}
		return html;
	}

	public String doEndTag(PanelField field) {
		String html = "";
		if (field != null) {
			int visiable = field.getVisiable();
//			if (1 == visiable) {
				html = "</ubf:Panel>" + RN;
//			}
		}
		return html;
	}

//	public String drawHeader(BaseField baseField) {
//		String html = "";
//		if (baseField != null && baseField instanceof PanelField) {
//			PanelField pf = (PanelField) baseField;
//			int visiable = pf.getVisiable();
//			if (1 == visiable) {
//				String fieldName = pf.getFieldName();
//				String label = pf.getLabel();
//				html = "<li id=\""+fieldName+"\" ubftype=\"TabPanelHeader\" class=\"bpp_TabPanelGroup_Header_Active\">" + RN +
//				"				<a href=\"javascript:void(0);\" onclick=\"TabPanelField.switchPanel(this)\">"+label+"</a>" + RN +
//				"			</li>" + RN;
//			}
//		}
//		return html;
//	}

}
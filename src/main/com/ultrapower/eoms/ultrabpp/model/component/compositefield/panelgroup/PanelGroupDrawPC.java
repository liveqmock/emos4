package com.ultrapower.eoms.ultrabpp.model.component.compositefield.panelgroup;


import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDraw;

public class PanelGroupDrawPC implements BaseFieldDraw<PanelGroupField>
{

	String RN = DeployManagerImpl.RN;
	
	public String doStartTag(PanelGroupField field) {
		String html = "";
		if (field != null) {
			String panelID = field.getParentID();
			int visiable = field.getVisiable();
			int colspan = field.getColspan();
			int titleVisiable = field.getTitleVisible();
			if ("main".equals(panelID)) {
				html = "<ubf:PanelGroup name=\"PG_Main\" cell=\"4\" type=\"" + field.getType() + "\" visiable=\""+visiable+"\" showtitle=\""+titleVisiable+"\">" + RN;
			} else {
				html = "<ubf:PanelGroup name=\""+field.getFieldName()+"\" cell=\""+colspan+"\" type=\"" + field.getType() + "\" visiable=\""+visiable+"\" showtitle=\""+titleVisiable+"\">" + RN;
			}
		}
		return html;
	}
	
	public String doEndTag(PanelGroupField field) {
		String html = "";
		if (field != null) {
//			int visiable = field.getVisiable();
//			if (1 == visiable) {
				html = "</ubf:PanelGroup>" + RN;
//			}
		}
		return html;
	}
}

package com.ultrapower.eoms.ultrabpp.model.component.specialfield.assigntreefield;

import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDraw;

public class AssignTreeDrawPC implements BaseFieldDraw<AssignTreeField>
{

	String RN = DeployManagerImpl.RN;

	public String doStartTag(AssignTreeField field)
	{
		String html = "";
		if (field != null)
		{
			String label = field.getLabel();
			String fieldName = field.getFieldName();
			Integer singleSelect = field.getSingleSelect();
			int visiable = field.getVisiable();
			int row = field.getRowspan();
			int cell = field.getColspan();
			String action = field.getAction();
			String actionName = field.getActionName();
			String stepno = field.getStepno();
			String selectType = field.getSelectType();
			String actionStr = "";
			if (action != null && !action.equals(""))
			{
				actionStr = "action=\"" + action + "\"";
			}
			String actionNameStr = "";
			if (actionName != null && !actionName.equals(""))
			{
				actionNameStr = "actionName=\"" + actionName + "\"";
			}
			String stepnoStr = "";
			if (stepno != null && !stepno.equals(""))
			{
				stepnoStr = "stepno=\"" + stepno + "\"";
			}
			String selectTypeStr = "";
			if (selectType != null && selectType.length() > 0)
			{
				selectTypeStr = "selectType=\""+selectType+"\"";
			}
			
			String next = field.getNext();
			html += "<ubf:AssignTreeField name=\"" + fieldName + "\" label=\"" + label + "\" row=\"" + row + "\" cell=\"" + cell + "\" single=\"" + singleSelect + "\" " + actionStr + " " + stepnoStr + " " + selectTypeStr +"  " + actionNameStr + " next=\"" + next + "\" visiable=\"" + visiable + "\" />" + RN;
		}
		return html;
	}

	public String doEndTag(AssignTreeField field)
	{
		String html = "";
		if (field != null)
		{

		}
		return html;
	}

}

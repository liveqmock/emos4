package com.ultrapower.eoms.ultrabpp.model.component.specialfield.assigntreefield;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.ultrapower.eoms.ultrabpp.model.component.specialfield.SpecialFieldTag;
import com.ultrapower.eoms.ultrabpp.utils.FieldTagUtil;

public class AssignTreeTag extends SpecialFieldTag<AssignTreeField>
{
	private static final long serialVersionUID = 7084727964180530777L;
	protected String label;
	protected int cell = 1;
	protected int row = 1;
	protected int single = 1;
	protected String action = "";
	protected int next = 0;
	protected String actionName = "";
	protected String stepno = "";
	protected String selectType = "";
	
	protected AssignTreeField field = null;
	
	@Override
	public int doEndTag() throws JspException
	{
		JspWriter out = pageContext.getOut();
		try
 {
			initParameter();

			String visiableClass = "";
			if (visiable == 0) {
				visiableClass = " display:none;";
			}

			String imgClass = "";
			if (single == 1)
				imgClass = "bpp_AssignTreeBtn_single";
			else
				imgClass = "bpp_AssignTreeBtn_multi";

			StringBuilder tagHtml = new StringBuilder();

			FieldTagUtil temp = new FieldTagUtil("AssignTreeField", "bpp_AssignTreeField", name, label, row, cell, theme, required, "", visiableClass);
			int cellpx = temp.getMaxWidth();
			int rowpx = temp.getMaxHeight();

			tagHtml.append("<input class=\"" + imgClass + "\" type=\"button\" valign=\"middle\" value=\"\" />");
			if (row > 1) {
				tagHtml.append("<textarea id=\"" + name + "\" name=\"" + name + "\" class=\"bpp_AssignTreeText\" style=\"width:" + (cellpx-26) + "px; height:" + rowpx + "px;\" onkeydown=\"return false;\" onpaste=\"return false;\"></textarea>");
			} else {
				tagHtml.append("<input id=\"" + name + "\" name=\"" + name + "\" class=\"bpp_AssignTreeText\" style=\"width:" + (cellpx-26) + "px;\" value=\"\" onkeydown=\"return false;\" onpaste=\"return false;\" />");
			}
			tagHtml.append("<input type=\"hidden\" id=\"" + name + "_AssignString\" value=\"\">");
			String scriptString = "";
			if(!"".equals(stepno) && !"".equals(actionName)&& !"".equals(selectType)){
				scriptString = "<script type=\"text/javascript\">ClientContext.addField(new AssignTreeField('"+name+"','"+label+"',"+single+","+required+",'"+action+"',"+next+",'"+actionName+"','"+stepno+"','"+selectType+"'));</script>";
			}else if (!"".equals(stepno) && !"".equals(actionName)) {
				scriptString = "<script type=\"text/javascript\">ClientContext.addField(new AssignTreeField('"+name+"','"+label+"',"+single+","+required+",'"+action+"',"+next+",'"+actionName+"','"+stepno+"'));</script>";
			} else {
				scriptString = "<script type=\"text/javascript\">ClientContext.addField(new AssignTreeField('"+name+"','"+label+"',"+single+","+required+",'"+action+"',"+next+"));</script>";
			}
			String tagString = temp.getTemplateHeader() + tagHtml.toString() + temp.getTemplateFooter();
			out.println(tagString + scriptString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the label
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * @return the cell
	 */
	public int getCell()
	{
		return cell;
	}

	/**
	 * @param cell the cell to set
	 */
	public void setCell(int cell)
	{
		this.cell = cell;
	}

	/**
	 * @return the single
	 */
	public int getSingle()
	{
		return single;
	}

	/**
	 * @param single the single to set
	 */
	public void setSingle(int single)
	{
		this.single = single;
	}

	/**
	 * @return the action
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * @return the row
	 */
	public int getRow()
	{
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(int row)
	{
		this.row = row;
	}

	/**
	 * @return the next
	 */
	public int getNext()
	{
		return next;
	}

	/**
	 * @param next the next to set
	 */
	public void setNext(int next)
	{
		this.next = next;
	}

	public String getStepno() {
		return stepno;
	}

	public void setStepno(String stepno) {
		this.stepno = stepno;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getSelectType()
	{
		return selectType;
	}

	public void setSelectType(String selectType)
	{
		this.selectType = selectType;
	}
	
	
}

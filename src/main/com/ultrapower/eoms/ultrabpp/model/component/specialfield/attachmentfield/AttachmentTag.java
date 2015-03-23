package com.ultrapower.eoms.ultrabpp.model.component.specialfield.attachmentfield;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.ultrabpp.cache.model.EditableFieldModel;
import com.ultrapower.eoms.ultrabpp.model.component.specialfield.SpecialFieldTag;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;
import com.ultrapower.eoms.ultrabpp.utils.FieldTagUtil;
import com.ultrapower.workflow.utils.UUIDGenerator;

public class AttachmentTag extends SpecialFieldTag<AttachmentField>
{
	private static final long serialVersionUID = 6044378630962794573L;
	protected String label;
	protected String types = "*.*";
	
	protected AttachmentField field = null;
	FieldTagUtil temp;
	
	@Override
	public int doStartTag() throws JspException
	{
		boolean uploadable = false;
		WorksheetDisplayContext displayCxt = (WorksheetDisplayContext)pageContext.getRequest().getAttribute("displayCxt");
		String relcationCode = UUIDGenerator.getId();
		if (displayCxt != null) {
			Map<String, String> fieldMap = displayCxt.getFieldMap();
			if (fieldMap != null) {
				String relCode = fieldMap.get(name);
				if (StringUtils.isNotBlank(relCode)) {
					relcationCode = relCode;
				}
			}
			
		}
		if(displayCxt == null && pageContext.getRequest().getParameter("type").equals("action"))
		{
			uploadable = true;
		}
		else
		{
			Map<String, EditableFieldModel> editableFieldMap = displayCxt.getEditableFieldMap();
			EditableFieldModel efModel = editableFieldMap.get(name);
			if(efModel != null)
			{
				uploadable = true;
			}
		}
		
		pageContext.getRequest().setAttribute(name + "_uploadable", uploadable);
		pageContext.getRequest().setAttribute(name + "_relcationCode", relcationCode);
		pageContext.getRequest().setAttribute(name + "_types", types);
		pageContext.getRequest().setAttribute(name + "_edit", uploadable ? 1 : 0);
		
		JspWriter out = pageContext.getOut();
		try
		{
			initParameter();
			
			String visiableClass = "";
			if(visiable == 0)
			{
				visiableClass = " display:none;";
			}
			
			StringBuilder tagHtml = new StringBuilder();
			
			temp = new FieldTagUtil("AttachField", "bpp_AttachField", name, label, 0, 4, theme, 0, "", visiableClass);
			
			tagHtml.append("<input type=\"hidden\" name=\""+name+"\" value=\""+relcationCode+"\"/>");
			
			out.println(temp.getTemplateHeader() + tagHtml.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException
	{
		JspWriter out = pageContext.getOut();
		try
		{
			out.println(temp.getTemplateFooter());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return EVAL_PAGE;
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
	 * @return the types
	 */
	public String getTypes()
	{
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(String types)
	{
		this.types = types;
	}
}

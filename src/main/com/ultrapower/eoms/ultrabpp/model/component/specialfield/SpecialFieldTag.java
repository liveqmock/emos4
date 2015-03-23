package com.ultrapower.eoms.ultrabpp.model.component.specialfield;

import java.util.Map;

import javax.servlet.jsp.tagext.TagSupport;

import com.ultrapower.eoms.ultrabpp.cache.model.EditableFieldModel;
import com.ultrapower.eoms.ultrabpp.cache.model.ThemeModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;

public abstract class SpecialFieldTag<F extends SpecialField>  extends TagSupport
{
	private static final long serialVersionUID = 1L;
	protected String name;
	protected int visiable = 1;
	protected int required = 0;
	protected F field = null;
	protected ThemeModel theme;
	
	protected void initParameter()
	{
		field = null;
		
		WorksheetDisplayContext displayCxt = (WorksheetDisplayContext)pageContext.getRequest().getAttribute("displayCxt");
		if(displayCxt == null || "PREVIEWMAIN".equals(displayCxt.getEditType()) || "PREVIEWACTION".equals(displayCxt.getEditType()))
		{if(displayCxt != null) theme = displayCxt.getTheme();}
		else
		{
			theme = displayCxt.getTheme();
			Map<String, EditableFieldModel> editableFieldMap = displayCxt.getEditableFieldMap();
			EditableFieldModel efModel = editableFieldMap.get(name);
			if(efModel != null)
			{
				visiable = efModel.getVisiable();
				field = (F)efModel.getBaseField();
				required = efModel.getRequired();
			}
		}
		if(theme == null) theme = new ThemeModel();
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
	 * @return the visiable
	 */
	public int getVisiable()
	{
		return visiable;
	}

	/**
	 * @param visiable the visiable to set
	 */
	public void setVisiable(int visiable)
	{
		this.visiable = visiable;
	}

	/**
	 * @return the field
	 */
	public F getField()
	{
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(F field)
	{
		this.field = field;
	}
}

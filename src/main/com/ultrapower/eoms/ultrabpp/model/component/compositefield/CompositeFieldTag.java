package com.ultrapower.eoms.ultrabpp.model.component.compositefield;

import java.util.Map;

import javax.servlet.jsp.tagext.TagSupport;

import com.ultrapower.eoms.ultrabpp.cache.model.EditableFieldModel;
import com.ultrapower.eoms.ultrabpp.cache.model.ThemeModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;

public abstract class CompositeFieldTag<F extends CompositeField> extends TagSupport
{
	private static final long serialVersionUID = 1L;
	protected String name;
	protected int visiable = 1;
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
			Map<String, String> fieldMap = displayCxt.getFieldMap();
			Map<String, EditableFieldModel> editableFieldMap = displayCxt.getEditableFieldMap();
			EditableFieldModel efModel = editableFieldMap.get(name);
			if(efModel != null)
			{
				visiable = efModel.getVisiable();
				field = (F)efModel.getBaseField();
			}
		}
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

	public int getVisiable() {
		return visiable;
	}

	public void setVisiable(int visiable) {
		this.visiable = visiable;
	}
}

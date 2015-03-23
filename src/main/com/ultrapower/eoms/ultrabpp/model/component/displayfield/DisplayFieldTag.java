package com.ultrapower.eoms.ultrabpp.model.component.displayfield;

import java.util.Map;

import javax.servlet.jsp.tagext.TagSupport;

import com.ultrapower.eoms.ultrabpp.cache.model.EditableFieldModel;
import com.ultrapower.eoms.ultrabpp.cache.model.ThemeModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;

public abstract class DisplayFieldTag<F extends DisplayField> extends TagSupport
{
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String defaultValue;
	protected int cell = 1;
	protected int visiable = 1;
	protected F field = null;
	protected String type = "tab";
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

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue()
	{
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}
}

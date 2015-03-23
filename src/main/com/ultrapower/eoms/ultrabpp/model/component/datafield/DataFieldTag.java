package com.ultrapower.eoms.ultrabpp.model.component.datafield;

import java.util.Map;

import javax.servlet.jsp.tagext.TagSupport;

import com.ultrapower.eoms.ultrabpp.cache.model.EditableFieldModel;
import com.ultrapower.eoms.ultrabpp.cache.model.ThemeModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;

public abstract class DataFieldTag<F extends DataField> extends TagSupport
{
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String label;
	protected String defaultValue;
	protected int row = 1;
	protected int cell = 1;
	protected int length = 0;
	protected int visiable = 1;
	protected int required = 0;
	protected int editable = 0;
	protected String value = null;
	protected F field = null;
	protected ThemeModel theme;
	
	protected void initParameter()
	{
		length = length < 0 ? 0 : length;
		required = 0;
		editable = 0;
		field = null;
		
		WorksheetDisplayContext displayCxt = (WorksheetDisplayContext)pageContext.getRequest().getAttribute("displayCxt");
		if(displayCxt == null || "PREVIEWMAIN".equals(displayCxt.getEditType()) || "PREVIEWACTION".equals(displayCxt.getEditType()))
		{
			editable = 1;
			value = defaultValue;
			if(displayCxt != null) theme = displayCxt.getTheme();
		}
		else
		{
			theme = displayCxt.getTheme();
			Map<String, String> fieldMap = displayCxt.getFieldMap();
			Map<String, EditableFieldModel> editableFieldMap = displayCxt.getEditableFieldMap();
			EditableFieldModel efModel = editableFieldMap.get(name);
			value = fieldMap.get(name);
			if(efModel != null)
			{
				editable = 1;
				visiable = efModel.getVisiable();
				required = efModel.getRequired();
				field = (F)efModel.getBaseField();
				length = field.getLength();
				
				//新建时，需要为空字段赋默认值
				if(((value == null || value.equals(""))&&displayCxt.getFieldMap().get("BASESTATUS") != null && !(displayCxt.getFieldMap().get("BASESTATUS").equals("新建")||displayCxt.getFieldMap().get("BASESTATUS").equals("受理建单")))
						|| displayCxt.getFieldMap().get("BASESTATUS") == null
						)
				{
					if(defaultValue == null) defaultValue = "";
					value = defaultValue;
				}
			}
		}
		if(theme == null) theme = new ThemeModel();
		if(value == null) value = "";
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
	 * @return the required
	 */
	public int getRequired()
	{
		return required;
	}

	/**
	 * @param required the required to set
	 */
	public void setRequired(int required)
	{
		this.required = required;
	}

	/**
	 * @return the editable
	 */
	public int getEditable()
	{
		return editable;
	}

	/**
	 * @param editable the editable to set
	 */
	public void setEditable(int editable)
	{
		this.editable = editable;
	}

	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
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
	 * @return the length
	 */
	public int getLength()
	{
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length)
	{
		this.length = length;
	}
}

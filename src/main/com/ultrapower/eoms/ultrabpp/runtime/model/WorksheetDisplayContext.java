package com.ultrapower.eoms.ultrabpp.runtime.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ultrabpp.cache.model.EditableFieldModel;
import com.ultrapower.eoms.ultrabpp.cache.model.ThemeModel;

import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;

public class WorksheetDisplayContext extends ProcessContext
{
	protected ThemeModel theme;
	protected String formFolder;
	protected List<ActionModel> fixActionList = new ArrayList<ActionModel>();
	protected List<ActionModel> freeActionList = new ArrayList<ActionModel>();
	protected Map<String, EditableFieldModel> editableFieldMap = new HashMap<String, EditableFieldModel>();
	protected List<String> clientFuncList = new ArrayList<String>();
	
	protected String layoutJSON;
	/**
	 * @return the clientFuncList
	 */
	public List<String> getClientFuncList()
	{
		return clientFuncList;
	}
	/**
	 * @param clientFuncList the clientFuncList to set
	 */
	public void setClientFuncList(List<String> clientFuncList)
	{
		this.clientFuncList = clientFuncList;
	}
	/**
	 * @return the formFolder
	 */
	public String getFormFolder()
	{
		return formFolder;
	}
	/**
	 * @param formFolder the formFolder to set
	 */
	public void setFormFolder(String formFolder)
	{
		this.formFolder = formFolder;
	}
	public Map<String, EditableFieldModel> getEditableFieldMap() {
		return editableFieldMap;
	}
	public void setEditableFieldMap(Map<String, EditableFieldModel> editableFieldMap) {
		this.editableFieldMap = editableFieldMap;
	}
	/**
	 * @return the fixActionList
	 */
	public List<ActionModel> getFixActionList()
	{
		return fixActionList;
	}
	/**
	 * @param fixActionList the fixActionList to set
	 */
	public void setFixActionList(List<ActionModel> fixActionList)
	{
		this.fixActionList = fixActionList;
	}
	/**
	 * @return the freeActionList
	 */
	public List<ActionModel> getFreeActionList()
	{
		return freeActionList;
	}
	/**
	 * @param freeActionList the freeActionList to set
	 */
	public void setFreeActionList(List<ActionModel> freeActionList)
	{
		this.freeActionList = freeActionList;
	}
	/**
	 * @return the theme
	 */
	public ThemeModel getTheme()
	{
		return theme;
	}
	/**
	 * @param theme the theme to set
	 */
	public void setTheme(ThemeModel theme)
	{
		this.theme = theme;
	}
	public String getLayoutJSON()
	{
		return layoutJSON;
	}
	public void setLayoutJSON(String layoutJSON)
	{
		this.layoutJSON = layoutJSON;
	}
	
	
}

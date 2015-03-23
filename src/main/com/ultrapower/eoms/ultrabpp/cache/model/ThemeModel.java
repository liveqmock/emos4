package com.ultrapower.eoms.ultrabpp.cache.model;

public class ThemeModel
{
	private String name = "classic";
	private String type = "PC";
	private String folder = "classic";
	private int labelWidth = 95;
	private int mainWidth = 960;
	private int cell = 4;
	
	public ThemeModel(String _name, String _type, String _folder, int _cell,int _labelWidth, int _mainWidth)
	{
		name = _name;
		type = _type;
		folder = _folder;
		cell = _cell;
		mainWidth = _mainWidth;
	}
	
	public ThemeModel()
	{
	    
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
	/**
	 * @return the folder
	 */
	public String getFolder()
	{
		return folder;
	}
	/**
	 * @param folder the folder to set
	 */
	public void setFolder(String folder)
	{
		this.folder = folder;
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
	public int getLabelWidth()
	{
	    return labelWidth;
	}
	public void setLabelWidth(int labelWidth)
	{
	    this.labelWidth = labelWidth;
	}

	/**
	 * @return the mainWidth
	 */
	public int getMainWidth()
	{
		return mainWidth;
	}

	/**
	 * @param mainWidth the mainWidth to set
	 */
	public void setMainWidth(int mainWidth)
	{
		this.mainWidth = mainWidth;
	}
	
}

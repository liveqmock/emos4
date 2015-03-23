package com.ultrapower.eoms.ultrabpp.runtime.model;

import java.util.ArrayList;
import java.util.List;

public class MenuModel
{
	private String value;
	private String text;
	private String dnIDs;
	private String dns;
	private List<MenuModel> subMenus = new ArrayList<MenuModel>();
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
	 * @return the text
	 */
	public String getText()
	{
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text)
	{
		this.text = text;
	}
	/**
	 * @return the subMenus
	 */
	public List<MenuModel> getSubMenus()
	{
		return subMenus;
	}
	/**
	 * @param subMenus the subMenus to set
	 */
	public void setSubMenus(List<MenuModel> subMenus)
	{
		this.subMenus = subMenus;
	}
	/**
	 * @return the dns
	 */
	public String getDns()
	{
		return dns;
	}
	/**
	 * @param dns the dns to set
	 */
	public void setDns(String dns)
	{
		this.dns = dns;
	}
	/**
	 * @return the dnIDs
	 */
	public String getDnIDs()
	{
		return dnIDs;
	}
	/**
	 * @param dnIDs the dnIDs to set
	 */
	public void setDnIDs(String dnIDs)
	{
		this.dnIDs = dnIDs;
	}
}

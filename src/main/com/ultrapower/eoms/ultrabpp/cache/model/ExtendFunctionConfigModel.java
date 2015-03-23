package com.ultrapower.eoms.ultrabpp.cache.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ultrapower.workflow.configuration.sort.model.WfType;

public class ExtendFunctionConfigModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5088262178276873255L;
	private String baseSchema;
	private List<String> openFunctions = new ArrayList<String>();
	private List<String> commitFunctions = new ArrayList<String>();
	private List<String> clientFunctions = new ArrayList<String>();
	private boolean clear = true;
	private List<String> attrbutes = new ArrayList<String>();
	private String relateType;
	private List<WfType> relateSchemas = new ArrayList<WfType>();
	/**
	 * @return the baseSchema
	 */
	public String getBaseSchema()
	{
		return baseSchema;
	}
	/**
	 * @param baseSchema the baseSchema to set
	 */
	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}
	/**
	 * @return the openFunctions
	 */
	public List<String> getOpenFunctions()
	{
		return openFunctions;
	}
	/**
	 * @param openFunctions the openFunctions to set
	 */
	public void setOpenFunctions(List<String> openFunctions)
	{
		this.openFunctions = openFunctions;
	}
	/**
	 * @return the commitFunctions
	 */
	public List<String> getCommitFunctions()
	{
		return commitFunctions;
	}
	/**
	 * @param commitFunctions the commitFunctions to set
	 */
	public void setCommitFunctions(List<String> commitFunctions)
	{
		this.commitFunctions = commitFunctions;
	}
	/**
	 * @return the clientFunctions
	 */
	public List<String> getClientFunctions()
	{
		return clientFunctions;
	}
	/**
	 * @param clientFunctions the clientFunctions to set
	 */
	public void setClientFunctions(List<String> clientFunctions)
	{
		this.clientFunctions = clientFunctions;
	}
	/**
	 * @return the attrbutes
	 */
	public List<String> getAttrbutes()
	{
		return attrbutes;
	}
	/**
	 * @param attrbutes the attrbutes to set
	 */
	public void setAttrbutes(List<String> attrbutes)
	{
		this.attrbutes = attrbutes;
	}
	/**
	 * @return the clear
	 */
	public boolean isClear()
	{
		return clear;
	}
	/**
	 * @param clear the clear to set
	 */
	public void setClear(boolean clear)
	{
		this.clear = clear;
	}
	public String getRelateType() {
		return relateType;
	}
	public void setRelateType(String relateType) {
		this.relateType = relateType;
	}
	public List<WfType> getRelateSchemas() {
		return relateSchemas;
	}
	public void setRelateSchemas(List<WfType> relateSchemas) {
		this.relateSchemas = relateSchemas;
	}
}

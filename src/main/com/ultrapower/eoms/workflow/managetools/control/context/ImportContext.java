package com.ultrapower.eoms.workflow.managetools.control.context;

import java.util.List;

public class ImportContext
{
	private String entryID;
	
	private String entryType;
	
	private String version;
	
	private String topEntryID;
	
	private String flowID;
	
	private CxtBaseInfor baseInfor;
	
	private List<CxtField> fieldList;
	
	private CxtProcess preCxtProcess;

	/**
	 * @return the entryID
	 */
	public String getEntryID()
	{
		return entryID;
	}

	/**
	 * @param entryID the entryID to set
	 */
	public void setEntryID(String entryID)
	{
		this.entryID = entryID;
	}

	/**
	 * @return the topEntryID
	 */
	public String getTopEntryID()
	{
		return topEntryID;
	}

	/**
	 * @param topEntryID the topEntryID to set
	 */
	public void setTopEntryID(String topEntryID)
	{
		this.topEntryID = topEntryID;
	}

	/**
	 * @return the flowID
	 */
	public String getFlowID()
	{
		return flowID;
	}

	/**
	 * @param flowID the flowID to set
	 */
	public void setFlowID(String flowID)
	{
		this.flowID = flowID;
	}

	/**
	 * @return the baseInfor
	 */
	public CxtBaseInfor getBaseInfor()
	{
		return baseInfor;
	}

	/**
	 * @param baseInfor the baseInfor to set
	 */
	public void setBaseInfor(CxtBaseInfor baseInfor)
	{
		this.baseInfor = baseInfor;
	}

	/**
	 * @return the fieldList
	 */
	public List<CxtField> getFieldList()
	{
		return fieldList;
	}

	/**
	 * @param fieldList the fieldList to set
	 */
	public void setFieldList(List<CxtField> fieldList)
	{
		this.fieldList = fieldList;
	}

	/**
	 * @return the preCxtProcess
	 */
	public CxtProcess getPreCxtProcess()
	{
		return preCxtProcess;
	}

	/**
	 * @param preCxtProcess the preCxtProcess to set
	 */
	public void setPreCxtProcess(CxtProcess preCxtProcess)
	{
		this.preCxtProcess = preCxtProcess;
	}

	/**
	 * @return the entryType
	 */
	public String getEntryType()
	{
		return entryType;
	}

	/**
	 * @param entryType the entryType to set
	 */
	public void setEntryType(String entryType)
	{
		this.entryType = entryType;
	}

	/**
	 * @return the version
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version)
	{
		this.version = version;
	}
}

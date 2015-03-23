package com.ultrapower.eoms.workflow.managetools.control.module;

import java.util.List;

import com.ultrapower.eoms.workflow.managetools.control.context.ImportContext;

public abstract class AbstractEntry
{
	protected String entryType;
	
	protected String version;
	
	protected List<AbstractProcess> processes;
	
	public abstract ImportContext signal(ImportContext context);

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

	/**
	 * @return the processes
	 */
	public List<AbstractProcess> getProcesses()
	{
		return processes;
	}

	/**
	 * @param processes the processes to set
	 */
	public void setProcesses(List<AbstractProcess> processes)
	{
		this.processes = processes;
	}
}

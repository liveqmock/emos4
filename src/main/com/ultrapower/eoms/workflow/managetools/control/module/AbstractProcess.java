package com.ultrapower.eoms.workflow.managetools.control.module;

import com.ultrapower.eoms.workflow.managetools.control.context.ImportContext;
import com.ultrapower.eoms.workflow.managetools.control.template.TemplateProcess;

public abstract class AbstractProcess
{
	protected String phaseNo;
	
	protected String processType;
	
	protected String topPhaseNo;
	
	protected String subFlowVersion;
	
	protected String roleCode;
	
	protected String roleName;
	
	protected String actionName;
	
	protected String desc;
	
	protected int fieldStart;
	
	protected int fieldEnd;
	
	protected AbstractProcess preProcess;
	
	public abstract ImportContext signal(ImportContext context);
	
	public void buildProcess(TemplateProcess tProcess)
	{
		setPhaseNo(tProcess.getPhaseNo());
		setProcessType(tProcess.getProcessType());
		setTopPhaseNo(tProcess.getTopPhaseNo());
		setSubFlowVersion(tProcess.getSubFlowVersion());
		setRoleCode(tProcess.getRoleCode());
		setRoleName(tProcess.getRoleName());
		setActionName(tProcess.getActionName());
		setDesc(tProcess.getDesc());
		setFieldStart(tProcess.getFieldStart());
		setFieldEnd(tProcess.getFieldEnd());
	}

	/**
	 * @return the phaseNo
	 */
	public String getPhaseNo()
	{
		return phaseNo;
	}

	/**
	 * @param phaseNo the phaseNo to set
	 */
	public void setPhaseNo(String phaseNo)
	{
		this.phaseNo = phaseNo;
	}

	/**
	 * @return the processType
	 */
	public String getProcessType()
	{
		return processType;
	}

	/**
	 * @param processType the processType to set
	 */
	public void setProcessType(String processType)
	{
		this.processType = processType;
	}

	/**
	 * @return the roleCode
	 */
	public String getRoleCode()
	{
		return roleCode;
	}

	/**
	 * @param roleCode the roleCode to set
	 */
	public void setRoleCode(String roleCode)
	{
		this.roleCode = roleCode;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName()
	{
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	/**
	 * @return the actionName
	 */
	public String getActionName()
	{
		return actionName;
	}

	/**
	 * @param actionName the actionName to set
	 */
	public void setActionName(String actionName)
	{
		this.actionName = actionName;
	}

	/**
	 * @return the desc
	 */
	public String getDesc()
	{
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	/**
	 * @return the fieldStart
	 */
	public int getFieldStart()
	{
		return fieldStart;
	}

	/**
	 * @param fieldStart the fieldStart to set
	 */
	public void setFieldStart(int fieldStart)
	{
		this.fieldStart = fieldStart;
	}

	/**
	 * @return the fieldEnd
	 */
	public int getFieldEnd()
	{
		return fieldEnd;
	}

	/**
	 * @param fieldEnd the fieldEnd to set
	 */
	public void setFieldEnd(int fieldEnd)
	{
		this.fieldEnd = fieldEnd;
	}

	/**
	 * @return the subFlowVersion
	 */
	public String getSubFlowVersion()
	{
		return subFlowVersion;
	}

	/**
	 * @param subFlowVersion the subFlowVersion to set
	 */
	public void setSubFlowVersion(String subFlowVersion)
	{
		this.subFlowVersion = subFlowVersion;
	}

	/**
	 * @return the topPhaseNo
	 */
	public String getTopPhaseNo()
	{
		return topPhaseNo;
	}

	/**
	 * @param topPhaseNo the topPhaseNo to set
	 */
	public void setTopPhaseNo(String topPhaseNo)
	{
		this.topPhaseNo = topPhaseNo;
	}

	/**
	 * @return the preProcess
	 */
	public AbstractProcess getPreProcess()
	{
		return preProcess;
	}

	/**
	 * @param preProcess the preProcess to set
	 */
	public void setPreProcess(AbstractProcess preProcess)
	{
		this.preProcess = preProcess;
	}
}

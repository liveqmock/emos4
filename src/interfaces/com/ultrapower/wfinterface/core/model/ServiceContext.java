package com.ultrapower.wfinterface.core.model;

import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataTable;

public class ServiceContext
{
	private String contextID;
	private String sheetType;
	private String opAction;
	private String serviceType;
	private String serialNo;
	private String supplier;
	private String caller;
	private long callTime;
	private int sendNumber;
	private String opPerson;
	private String opCorp;
	private String opDepart;
	private String opContact;
	private long opTime;
	private String opDetailXml;
	private String attachXml;
	private long createTime;
	private int status;
	private long scanTime;
	
	private DataTable parameterTable;
	private List<Attachment> attachments;
	private String methodName;
	
	
	public String getContextID()
	{
		return contextID;
	}
	public void setContextID(String contextID)
	{
		this.contextID = contextID;
	}
	public String getSheetType()
	{
		return sheetType;
	}
	public void setSheetType(String sheetType)
	{
		this.sheetType = sheetType;
	}
	public String getOpAction()
	{
		return opAction;
	}
	public void setOpAction(String opAction)
	{
		this.opAction = opAction;
	}
	public String getServiceType()
	{
		return serviceType;
	}
	public void setServiceType(String serviceType)
	{
		this.serviceType = serviceType;
	}
	public String getSerialNo()
	{
		return serialNo;
	}
	public void setSerialNo(String serialNo)
	{
		this.serialNo = serialNo;
	}
	public String getSupplier()
	{
		return supplier;
	}
	public void setSupplier(String supplier)
	{
		this.supplier = supplier;
	}
	public String getCaller()
	{
		return caller;
	}
	public void setCaller(String caller)
	{
		this.caller = caller;
	}
	public long getCallTime()
	{
		return callTime;
	}
	public void setCallTime(long callTime)
	{
		this.callTime = callTime;
	}
	public int getSendNumber()
	{
		return sendNumber;
	}
	public void setSendNumber(int sendNumber)
	{
		this.sendNumber = sendNumber;
	}
	public String getOpPerson()
	{
		return opPerson;
	}
	public void setOpPerson(String opPerson)
	{
		this.opPerson = opPerson;
	}
	public String getOpCorp()
	{
		return opCorp;
	}
	public void setOpCorp(String opCorp)
	{
		this.opCorp = opCorp;
	}
	public String getOpDepart()
	{
		return opDepart;
	}
	public void setOpDepart(String opDepart)
	{
		this.opDepart = opDepart;
	}
	public String getOpContact()
	{
		return opContact;
	}
	public void setOpContact(String opContact)
	{
		this.opContact = opContact;
	}
	public long getOpTime()
	{
		return opTime;
	}
	public void setOpTime(long opTime)
	{
		this.opTime = opTime;
	}
	public String getOpDetailXml()
	{
		return opDetailXml;
	}
	public void setOpDetailXml(String opDetailXml)
	{
		this.opDetailXml = opDetailXml;
	}
	public String getAttachXml()
	{
		return attachXml;
	}
	public void setAttachXml(String attachXml)
	{
		this.attachXml = attachXml;
	}
	public long getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(long createTime)
	{
		this.createTime = createTime;
	}
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public long getScanTime()
	{
		return scanTime;
	}
	public void setScanTime(long scanTime)
	{
		this.scanTime = scanTime;
	}
	public DataTable getParameterTable()
	{
		return parameterTable;
	}
	public void setParameterTable(DataTable parameterTable)
	{
		this.parameterTable = parameterTable;
	}
	public List<Attachment> getAttachments()
	{
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments)
	{
		this.attachments = attachments;
	}
	public String getMethodName()
	{
		return methodName;
	}
	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}
}

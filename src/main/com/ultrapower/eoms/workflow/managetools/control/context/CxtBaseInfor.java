package com.ultrapower.eoms.workflow.managetools.control.context;

public class CxtBaseInfor
{
	private String baseID;
	
	private String serialNo;
	
	private String baseSchema;
	
	private String baseName;
	
	private String version;
	
	private long createTime;
	
	private long closeTime;

	/**
	 * @return the baseID
	 */
	public String getBaseID()
	{
		return baseID;
	}

	/**
	 * @param baseID the baseID to set
	 */
	public void setBaseID(String baseID)
	{
		this.baseID = baseID;
	}

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
	 * @return the baseName
	 */
	public String getBaseName()
	{
		return baseName;
	}

	/**
	 * @param baseName the baseName to set
	 */
	public void setBaseName(String baseName)
	{
		this.baseName = baseName;
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
	 * @return the serialNo
	 */
	public String getSerialNo()
	{
		return serialNo;
	}

	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo)
	{
		this.serialNo = serialNo;
	}

	/**
	 * @return the closeTime
	 */
	public long getCloseTime()
	{
		return closeTime;
	}

	/**
	 * @param closeTime the closeTime to set
	 */
	public void setCloseTime(long closeTime)
	{
		this.closeTime = closeTime;
	}

	/**
	 * @return the createTime
	 */
	public long getCreateTime()
	{
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(long createTime)
	{
		this.createTime = createTime;
	}
	
}

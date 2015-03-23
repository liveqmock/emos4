package com.ultrapower.wfinterface.core.model;

/**
 * 工单下一步处理人对象，工单的下一步处理人通过该对象进行封装
 * @author BigMouse
 *
 */
public class Actor
{
	/**
	 * 处理人登录名(actorType=USER)/处理组ID(actorType=GROUP)/处理角色细分ID(actorType=ROLE)
	 */
	private String actorName;
	/**
	 * 处理人全名(actorType=USER)/处理组名称(actorType=GROUP)/处理角色细分名称(actorType=ROLE)
	 */
	private String actorText;
	/**
	 * 处理对象类型，USER:处理人，GROUP:处理组，ROLE:处理角色细分
	 */
	private ActorType actorType = ActorType.USER;
	/**
	 * 处理类型，1:主办，2:协办，3:抄送，4:完成，5:归档，6:提审。默认为1:主办
	 */
	private int dealType = 1;
	/**
	 * 环节受理时间
	 */
	private int acceptOverTime;
	/**
	 * 环节处理时间
	 */
	private int dealOverTime;
	/**
	 * 目标环节号，用于阶段建议、催办、追回、退回等操作时来标识的目标环节的环节号
	 */
	private String processID;
	
	public enum ActorType
	{
		USER, GROUP, ROLE
	}

	/**
	 * 获取处理人登录名(actorType=USER)/处理组ID(actorType=GROUP)/处理角色细分ID(actorType=ROLE)
	 * @return 处理人登录名(actorType=USER)/处理组ID(actorType=GROUP)/处理角色细分ID(actorType=ROLE)
	 */
	public String getActorName()
	{
		return actorName;
	}

	/**
	 * 设置处理人登录名(actorType=USER)/处理组ID(actorType=GROUP)/处理角色细分ID(actorType=ROLE)
	 * @param actorName 处理人登录名(actorType=USER)/处理组ID(actorType=GROUP)/处理角色细分ID(actorType=ROLE)
	 */
	public void setActorName(String actorName)
	{
		this.actorName = actorName;
	}

	/**
	 * 处理人全名(actorType=USER)/处理组名称(actorType=GROUP)/处理角色细分名称(actorType=ROLE)
	 * @return 处理人全名(actorType=USER)/处理组名称(actorType=GROUP)/处理角色细分名称(actorType=ROLE)
	 */
	public String getActorText()
	{
		return actorText;
	}

	/**
	 * 设置处理人全名(actorType=USER)/处理组名称(actorType=GROUP)/处理角色细分名称(actorType=ROLE)
	 * @param actorText 处理人全名(actorType=USER)/处理组名称(actorType=GROUP)/处理角色细分名称(actorType=ROLE)
	 */
	public void setActorText(String actorText)
	{
		this.actorText = actorText;
	}

	/**
	 * 获取处理对象类型，USER:处理人，GROUP:处理组，ROLE:处理角色细分
	 * @return 处理对象类型，USER:处理人，GROUP:处理组，ROLE:处理角色细分
	 */
	public ActorType getActorType()
	{
		return actorType;
	}

	/**
	 * 设置处理对象类型，USER:处理人，GROUP:处理组，ROLE:处理角色细分
	 * @param actorType 处理对象类型，USER:处理人，GROUP:处理组，ROLE:处理角色细分
	 */
	public void setActorType(ActorType actorType)
	{
		this.actorType = actorType;
	}

	/**
	 * 获取处理类型，1:主办，2:协办，3:抄送，4:完成，5:归档，6:提审。默认为1:主办
	 * @return 处理类型，1:主办，2:协办，3:抄送，4:完成，5:归档，6:提审。默认为1:主办
	 */
	public int getDealType()
	{
		return dealType;
	}

	/**
	 * 设置处理类型，1:主办，2:协办，3:抄送，4:完成，5:归档，6:提审。默认为1:主办
	 * @param dealType 处理类型，1:主办，2:协办，3:抄送，4:完成，5:归档，6:提审。默认为1:主办
	 */
	public void setDealType(int dealType)
	{
		this.dealType = dealType;
	}

	/**
	 * 获取环节受理时间
	 * @return 环节受理时间
	 */
	public int getAcceptOverTime()
	{
		return acceptOverTime;
	}

	/**
	 * 设置环节受理时间
	 * @param acceptOverTime 环节受理时间
	 */
	public void setAcceptOverTime(int acceptOverTime)
	{
		this.acceptOverTime = acceptOverTime;
	}

	/**
	 * 获取环节处理时间
	 * @return 环节处理时间
	 */
	public int getDealOverTime()
	{
		return dealOverTime;
	}

	/**
	 * 设置环节处理时间
	 * @param dealOverTime 环节处理时间
	 */
	public void setDealOverTime(int dealOverTime)
	{
		this.dealOverTime = dealOverTime;
	}

	/**
	 * 获取目标环节号，用于阶段建议、催办、追回、退回等操作时来标识的目标环节的环节号
	 * @return 目标环节号，用于阶段建议、催办、追回、退回等操作时来标识的目标环节的环节号
	 */
	public String getProcessID()
	{
		return processID;
	}

	/**
	 * 设置目标环节号，用于阶段建议、催办、追回、退回等操作时来标识的目标环节的环节号
	 * @param processID 目标环节号，用于阶段建议、催办、追回、退回等操作时来标识的目标环节的环节号
	 */
	public void setProcessID(String processID)
	{
		this.processID = processID;
	}

}

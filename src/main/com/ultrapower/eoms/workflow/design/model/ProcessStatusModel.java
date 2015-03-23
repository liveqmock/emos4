package com.ultrapower.eoms.workflow.design.model;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public class ProcessStatusModel
{
	/**
	 * 环节名称
	 */
	private String statusName;

	/**
	 * 获取环节名称
	 * 
	 * @return 环节名称
	 */
	public String getStatusName()
	{
		return statusName;
	}

	/**
	 * 设置环节名称
	 * 
	 * @param statusName：环节名称
	 */
	public void setStatusName(String statusName)
	{
		this.statusName = statusName;
	}

	/**
	 * 环节的背景色
	 */
	private String color;

	/**
	 * 获取环节的背景色代码
	 * 
	 * @return 环节的背景色代码
	 */
	public String getColor()
	{
		return color;
	}

	/**
	 * 设置环节的背景色
	 * 
	 * @param color：环节的背景色代码
	 */
	public void setColor(String color)
	{
		this.color = color;
	}
	
	private String color1;
	
	

	/**
	 * 环节上显示环节的名称
	 */
	private String infoText;

	/**
	 * 获取环节上显示环节的名称
	 * 
	 * @return 环节上显示环节的名称
	 */
	public String getInfoText()
	{
		return infoText;
	}

	/**
	 * 设置环节上显示环节的名称
	 * 
	 * @param infoText：环节上显示环节的名称
	 */
	public void setInfoText(String infoText)
	{
		this.infoText = infoText;
	}
	
	/**
	 * 环节的箭头的方向
	 */
	private String arrowWay;

	/**
	 * 获取环节的箭头的方向
	 * 
	 * @return 环节的箭头的方向
	 */
	public String getArrowWay()
	{
		return arrowWay;
	}

	/**
	 * 设置环节的箭头的方向
	 * 
	 * @param arrowWay 环节的箭头的方向
	 */
	public void setArrowWay(String arrowWay)
	{
		this.arrowWay = arrowWay;
	}

	/**
	 * 环节的箭头的类型
	 */
	private String arrowType;

	/**
	 * 获取环节的箭头的类型
	 * 
	 * @return 环节的箭头的类型
	 */
	public String getArrowType()
	{
		return arrowType;
	}

	/**
	 * 设置环节的箭头的类型
	 * 
	 * @param arrowImage：环节的箭头的类型
	 */
	public void setArrowType(String arrowType)
	{
		this.arrowType = arrowType;
	}

	public String getColor1()
	{
		return color1;
	}

	public void setColor1(String color1)
	{
		this.color1 = color1;
	}

	private String xSpace;

	public String getXSpace() {
		return xSpace;
	}

	public void setXSpace(String space) {
		xSpace = space;
	}
	
}

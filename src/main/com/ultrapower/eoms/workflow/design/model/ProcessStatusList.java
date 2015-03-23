package com.ultrapower.eoms.workflow.design.model;

import java.util.*;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public class ProcessStatusList
{
	/**
	 * 起始横坐标
	 */
	private int startX;

	/**
	 * 设置起始横坐标
	 * 
	 * @param startX：起始横坐标
	 */
	public void setStartX(int startX)
	{
		this.startX = startX;
	}

	/**
	 * 获取起始横坐标
	 * 
	 * @return 起始横坐标
	 */
	public int getStartX()
	{
		return startX;
	}

	/**
	 * 起始纵坐标
	 */
	private int startY;

	/**
	 * 设置起始纵坐标
	 * 
	 * @param startY：起始纵坐标
	 */
	public void setStartY(int startY)
	{
		this.startY = startY;
	}

	/**
	 * 获取起始纵坐标
	 * 
	 * @return 起始纵坐标
	 */
	public int getStartY()
	{
		return startY;
	}

	/**
	 * 环节是审批时的间距
	 *
	 */
	public int xSpace;
	
	/**
	 * 环节宽度
	 */
	private int modelWidth;

	/**
	 * 设置环节宽度
	 * 
	 * @param modelWidth：环节宽度
	 */
	public void setModelWidth(int modelWidth)
	{
		this.modelWidth = modelWidth;
	}

	/**
	 * 获取环节宽度
	 * 
	 * @return 环节宽度
	 */
	public int getModelWidth()
	{
		return modelWidth;
	}

	/**
	 * 环节高度
	 */
	private int modelHeight;

	/**
	 * 设置环节高度
	 * 
	 * @param modelHeight：环节高度
	 */
	public void setModelHeight(int modelHeight)
	{
		this.modelHeight = modelHeight;
	}

	/**
	 * 获取环节高度
	 * 
	 * @return 环节高度
	 */
	public int getModelHeight()
	{
		return modelHeight;
	}

	/**
	 * 箭头长度
	 */
	private int arrowLength;

	/**
	 * 设置箭头长度
	 * 
	 * @param arrowLength：箭头长度
	 */
	public void setArrowLength(int arrowLength)
	{
		this.arrowLength = arrowLength;
	}

	/**
	 * 获取箭头长度
	 * 
	 * @return 箭头长度
	 */
	public int getArrowLength()
	{
		return arrowLength;
	}

	/**
	 * 箭头宽度
	 */
	private int arrowwidth;

	/**
	 * 设置箭头宽度
	 * 
	 * @param arrowwidth：箭头宽度
	 */
	public void setArrowwidth(int arrowwidth)
	{
		this.arrowwidth = arrowwidth;
	}

	/**
	 * 获取箭头宽度
	 * 
	 * @return 箭头宽度
	 */
	public int getArrowwidth()
	{
		return arrowwidth;
	}
	
	/**
	 * 行间距
	 */
	private int rowHeight;
	
	/**
	 * 设置行间距
	 * @param rowHeight：行间距
	 */
	public void setRowHeight(int rowHeight)
	{
		this.rowHeight = rowHeight;
	}
	
	/**
	 * 获取行间距
	 * @return 行间距
	 */
	public int getRowHeight()
	{
		return rowHeight;
	}

	/**
	 * 箭头的样式模板
	 */
	private String arrowTemplite;

	/**
	 * 设置箭头的样式模板
	 * 
	 * @param arrowTemplite：箭头的样式模板
	 */
	public void setArrowTemplite(String arrowTemplite)
	{
		this.arrowTemplite = arrowTemplite;
	}

	/**
	 * 获取箭头的样式模板
	 * 
	 * @return 箭头的样式模板
	 */
	public String getArrowTemplite()
	{
		return arrowTemplite;
	}

	/**
	 * 环节状态集合
	 */
	private List processStatusModelList;

	/**
	 * 设置环节状态集合
	 * 
	 * @param processStatusModelList：环节状态集合
	 */
	public void setProcessStatusModelList(List processStatusModelList)
	{
		this.processStatusModelList = processStatusModelList;
	}

	/**
	 * 获取环节状态集合
	 * 
	 * @return 环节状态集合
	 */
	public List getProcessStatusModelList()
	{
		return processStatusModelList;
	}

	/**
	 * 通过环节状态名称获取环节状态实体对象
	 * 
	 * @param statusName：环节状态名称
	 * @return 环节状态实体对象
	 */
	public ProcessStatusModel getProcessStatusModel(String statusName)
	{
		for (Iterator it = processStatusModelList.iterator(); it.hasNext();)
		{
			ProcessStatusModel psModel = (ProcessStatusModel) it.next();
			if (psModel.getStatusName().equals(statusName))
			{
				return psModel;
			}
		}
		return null;
	}

	public int getXSpace() {
		return xSpace;
	}

	public void setXSpace(int space) {
		xSpace = space;
	}
}

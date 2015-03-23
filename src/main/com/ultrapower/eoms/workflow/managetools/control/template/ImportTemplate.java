package com.ultrapower.eoms.workflow.managetools.control.template;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.workflow.managetools.control.module.AbstractEntry;

public class ImportTemplate
{
	/**
	 * 工单信息
	 */
	private TemplateBaseInfor baseInfor;
	
	/**
	 * 环节信息Map
	 */
	private Map<String, TemplateProcess> processMap;
	
	/**
	 * 字段信息List
	 */
	private List<TemplateField> fieldList;
	
	/**
	 * 流程入口
	 */
	private AbstractEntry enterPoint;

	/**
	 * @return the baseInfor
	 */
	public TemplateBaseInfor getBaseInfor()
	{
		return baseInfor;
	}

	/**
	 * @param baseInfor the baseInfor to set
	 */
	public void setBaseInfor(TemplateBaseInfor baseInfor)
	{
		this.baseInfor = baseInfor;
	}

	/**
	 * @return the processMap
	 */
	public Map<String, TemplateProcess> getProcessMap()
	{
		return processMap;
	}

	/**
	 * @param processMap the processMap to set
	 */
	public void setProcessMap(Map<String, TemplateProcess> processMap)
	{
		this.processMap = processMap;
	}

	/**
	 * @return the fieldList
	 */
	public List<TemplateField> getFieldList()
	{
		return fieldList;
	}

	/**
	 * @param fieldList the fieldList to set
	 */
	public void setFieldList(List<TemplateField> fieldList)
	{
		this.fieldList = fieldList;
	}

	/**
	 * @return the enterPoint
	 */
	public AbstractEntry getEnterPoint()
	{
		return enterPoint;
	}

	/**
	 * @param enterPoint the enterPoint to set
	 */
	public void setEnterPoint(AbstractEntry enterPoint)
	{
		this.enterPoint = enterPoint;
	}
	
}

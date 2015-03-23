package com.ultrapower.eoms.workflow.design.model;

import java.util.List;

/**
 * 
 * @author fying 
 * @version 
 * @since  
 * @see TemplateDoc
 */
public class DrawLineModel
{
	private String path;
	private String edProcessAction;
	private List<DrawLineModel> nextModel;
	private DrawLineModel parentModel;
	public String getPath()
	{
		return path;
	}
	public void setPath(String path)
	{
		this.path = path;
	}
	
	public String getEdProcessAction()
	{
		return edProcessAction;
	}
	public void setEdProcessAction(String edProcessAction)
	{
		this.edProcessAction = edProcessAction;
	}
	public List<DrawLineModel> getNextModel()
	{
		return nextModel;
	}
	public void setNextModel(List<DrawLineModel> nextModel)
	{
		this.nextModel = nextModel;
	}
	public DrawLineModel getParentModel()
	{
		return parentModel;
	}
	public void setParentModel(DrawLineModel parentModel)
	{
		this.parentModel = parentModel;
	}
	
	
	
	
}

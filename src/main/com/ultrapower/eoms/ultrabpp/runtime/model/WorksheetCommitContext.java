package com.ultrapower.eoms.ultrabpp.runtime.model;

import java.util.List;

import com.ultrapower.workflow.engine.core.model.EngineModel;
import com.ultrapower.workflow.engine.task.model.BaseTask;

public class WorksheetCommitContext extends ProcessContext
{
	protected String actionID;
	protected String actionType;
	protected String actionText;
	protected int closeAfterCommit = 1;
	protected String assignString;
	protected List<BaseTask> oldTasks;
	protected List<BaseTask> newTasks;
	protected String returnMsg;
	protected EngineModel engineModel;
	
	/**
	 * @return the actionID
	 */
	public String getActionID()
	{
		return actionID;
	}
	/**
	 * @param actionID the actionID to set
	 */
	public void setActionID(String actionID)
	{
		this.actionID = actionID;
	}
	/**
	 * @return the actionType
	 */
	public String getActionType()
	{
		return actionType;
	}
	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(String actionType)
	{
		this.actionType = actionType;
	}
	/**
	 * @return the actionText
	 */
	public String getActionText()
	{
		return actionText;
	}
	/**
	 * @param actionText the actionText to set
	 */
	public void setActionText(String actionText)
	{
		this.actionText = actionText;
	}
	public String getAssignString() {
		return assignString;
	}
	public void setAssignString(String assignString) {
		this.assignString = assignString;
	}
	/**
	 * @return the oldTasks
	 */
	public List<BaseTask> getOldTasks()
	{
		return oldTasks;
	}
	/**
	 * @param oldTasks the oldTasks to set
	 */
	public void setOldTasks(List<BaseTask> oldTasks)
	{
		this.oldTasks = oldTasks;
	}
	/**
	 * @return the newTasks
	 */
	public List<BaseTask> getNewTasks()
	{
		return newTasks;
	}
	/**
	 * @param newTasks the newTasks to set
	 */
	public void setNewTasks(List<BaseTask> newTasks)
	{
		this.newTasks = newTasks;
	}
	/**
	 * @return the returnMsg
	 */
	public String getReturnMsg()
	{
		return returnMsg;
	}
	/**
	 * @param returnMsg the returnMsg to set
	 */
	public void setReturnMsg(String returnMsg)
	{
		this.returnMsg = returnMsg;
	}
	/**
	 * @return the closeAfterCommit
	 */
	public int getCloseAfterCommit()
	{
		return closeAfterCommit;
	}
	/**
	 * @param closeAfterCommit the closeAfterCommit to set
	 */
	public void setCloseAfterCommit(int closeAfterCommit)
	{
		this.closeAfterCommit = closeAfterCommit;
	}
	public EngineModel getEngineModel()
	{
		return engineModel;
	}
	public void setEngineModel(EngineModel engineModel)
	{
		this.engineModel = engineModel;
	}
	
	
}

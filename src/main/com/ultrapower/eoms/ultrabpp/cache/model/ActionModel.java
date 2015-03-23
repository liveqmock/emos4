package com.ultrapower.eoms.ultrabpp.cache.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author fying
 * @version
 * @since
 * @see TemplateDoc
 */
@Entity
@Table(name = "BS_T_BPP_ACTION")
public class ActionModel
{
	private String id;
	private String stepNo;
	private String baseSchema;
	private String label;
	private String actionName;
	private String actionType;
	private Integer isFree = 0;
	private Integer hasNext = 1;
	private String description;
	private Integer isClose = 1;
	private Integer orderId = 0;

	/**
	 * 是否部署
	 */
	private int hasDeploy;
	/**
	 * 部署前操作
	 */
	private String operate;

	@Id
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getBaseSchema()
	{
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getActionType()
	{
		return actionType;
	}

	public void setActionType(String actionType)
	{
		this.actionType = actionType;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getStepNo()
	{
		return stepNo;
	}

	public void setStepNo(String stepNo)
	{
		this.stepNo = stepNo;
	}

	public String getActionName()
	{
		return actionName;
	}

	public void setActionName(String actionName)
	{
		this.actionName = actionName;
	}

	public int getHasDeploy()
	{
		return hasDeploy;
	}

	public void setHasDeploy(int hasDeploy)
	{
		this.hasDeploy = hasDeploy;
	}

	public String getOperate()
	{
		return operate;
	}

	public void setOperate(String operate)
	{
		this.operate = operate;
	}

	public Integer getIsFree()
	{
		return isFree;
	}

	public void setIsFree(Integer isFree)
	{
		this.isFree = isFree;
	}

	public Integer getHasNext()
	{
		return hasNext;
	}

	public void setHasNext(Integer hasNext)
	{
		this.hasNext = hasNext;
	}

	public Integer getIsClose()
	{
		return isClose;
	}

	public void setIsClose(Integer isClose)
	{
		this.isClose = isClose;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public ActionModel(){
		
	}
	public ActionModel(String actionName,String actionType,String label,String description,Integer hasNext,Integer isClose,Integer isFree, Integer orderId){
		this.label = label;
		this.actionName = actionName;
		this.actionType = actionType;
		this.description = description;
		this.hasNext = hasNext;
		this.isClose = isClose;
		this.isFree = isFree;
		this.orderId = orderId;
	}
	
	public ActionModel copyModel()
	{
		try
		{
			return (ActionModel)this.clone();
		}
		catch (CloneNotSupportedException e)
		{
			ActionModel newModel = new ActionModel();
			newModel.setActionName(this.actionName);
			newModel.setActionType(this.actionType);
			newModel.setBaseSchema(this.baseSchema);
			newModel.setDescription(this.description);
			newModel.setHasDeploy(this.hasDeploy);
			newModel.setHasNext(this.hasNext);
			newModel.setId(this.id);
			newModel.setIsClose(this.isClose);
			newModel.setIsFree(this.isFree);
			newModel.setLabel(this.label);
			newModel.setOperate(this.operate);
			newModel.setStepNo(this.stepNo);
			newModel.setOrderId(this.orderId);
			return newModel;
		}
	}

}

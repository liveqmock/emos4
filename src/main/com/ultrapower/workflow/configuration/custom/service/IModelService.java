package com.ultrapower.workflow.configuration.custom.service;

import com.ultrapower.workflow.configuration.custom.model.WfModel;
import com.ultrapower.workflow.configuration.custom.model.WfModelProperties;
import java.util.List;

public abstract interface IModelService
{
  public abstract boolean saveOrUpdateModel(WfModel paramWfModel);

  public abstract boolean delModelById(String paramString);

  public abstract List<WfModel> getAllModel();

  public abstract WfModel getModelById(String paramString);

  public abstract WfModel getModelByCode(String paramString);

  public abstract List<WfModelProperties> getModelPropsByCode(String paramString);

  public abstract void delModelPropById(String paramString);

  public abstract boolean saveOrUpdateModelProp(WfModelProperties paramWfModelProperties);

  public abstract WfModelProperties getModelPropById(String paramString);

  public abstract List<WfModel> getWfModelByWftype(String paramString);
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.custom.service.IModelService
 * JD-Core Version:    0.6.0
 */
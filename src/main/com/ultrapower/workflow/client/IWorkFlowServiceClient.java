package com.ultrapower.workflow.client;

import com.ultrapower.workflow.bizconfig.custom.IModelManager;
import com.ultrapower.workflow.bizconfig.interfaces.IInterfaceManager;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.bizconfig.version.IWfVersionManager;
import com.ultrapower.workflow.bizworkflow.facade.IBizFacade;

public abstract interface IWorkFlowServiceClient
{
  public abstract IWfVersionManager getVersionService();

  public abstract IWfSortManager getSortService();

  public abstract IInterfaceManager getInterfaceService();

  public abstract IModelManager getModelService();

  public abstract IBizFacade getBizFacade();
}
package com.ultrapower.workflow.client.local;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.workflow.bizconfig.custom.IModelManager;
import com.ultrapower.workflow.bizconfig.interfaces.IInterfaceManager;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.bizconfig.version.IWfVersionManager;
import com.ultrapower.workflow.bizworkflow.facade.IBizFacade;
import com.ultrapower.workflow.client.IWorkFlowServiceClient;

public class LocalServiceClientImpl
  implements IWorkFlowServiceClient
{
  private IBizFacade bizFacade = (IBizFacade)WebApplicationManager.getBean("bizFacade");
  private IWfVersionManager versionManager = (IWfVersionManager)WebApplicationManager.getBean("rmiVerServiceImpl");
  private IModelManager modelManager = (IModelManager)WebApplicationManager.getBean("rmiModelServiceImpl");
  private IInterfaceManager interManager = (IInterfaceManager)WebApplicationManager.getBean("rmiInterServiceImpl");

  public IWfVersionManager getVersionService() {
    return this.versionManager;
  }

  public IWfSortManager getSortService() {
    IWfSortManager sortManager = (IWfSortManager)WebApplicationManager.getBean("rmiSortServiceImpl");
    return sortManager;
  }

  public IInterfaceManager getInterfaceService() {
    return this.interManager;
  }

  public IModelManager getModelService() {
    return this.modelManager;
  }

  public IBizFacade getBizFacade() {
    return this.bizFacade;
  }
}
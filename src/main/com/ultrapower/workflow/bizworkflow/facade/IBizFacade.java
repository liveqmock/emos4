package com.ultrapower.workflow.bizworkflow.facade;

import com.ultrapower.workflow.bizform.model.DealProcessModel;
import com.ultrapower.workflow.engine.core.model.ActionInfo;
import com.ultrapower.workflow.engine.core.model.DataField;
import com.ultrapower.workflow.engine.core.model.EngineModel;
import com.ultrapower.workflow.engine.core.model.WfAction;
import com.ultrapower.workflow.engine.core.model.WfStep;
import com.ultrapower.workflow.engine.def.model.WorkflowDescriptor;
import com.ultrapower.workflow.engine.task.model.ProcessTask;
import com.ultrapower.workflow.role.model.ChildRole;
import com.ultrapower.workflow.role.model.Dimension;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public abstract interface IBizFacade extends Remote
{
  public abstract void test()
    throws RemoteException;

  public abstract List<String> call(List<String> paramList)
    throws RemoteException;

  public abstract DealProcessModel checkPerm(String paramString1, String paramString2, String paramString3)
    throws RemoteException;

  public abstract ProcessTask checkTask(String paramString1, String paramString2, String paramString3)
    throws RemoteException;

  public abstract List<Dimension> matchDimensions(String paramString1, String paramString2)
    throws RemoteException;

  public abstract List<ChildRole> matchRole(String paramString1, String paramString2, String paramString3)
    throws RemoteException;

  public abstract List<String> getOwnFields(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws RemoteException;

  public abstract List<String> execPlugin(String paramString, List<String> paramList)
    throws RemoteException;

  public abstract List<WfAction> getAvailableActions(String paramString1, String paramString2, String paramString3, Map<String, DataField> paramMap)
    throws RemoteException;

  public abstract EngineModel doAction(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, boolean paramBoolean, List<ActionInfo> paramList, Map<String, DataField> paramMap, Map<String, String> paramMap1)
    throws RemoteException;

  public abstract List<WfStep> getNextWfSteps(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Map<String, DataField> paramMap)
    throws RemoteException;

  public abstract List<WfStep> getCurrentWfSteps(String paramString, Map<String, DataField> paramMap)
    throws RemoteException;

  public abstract WfStep getCurrentWfStep(String paramString1, String paramString2, String paramString3, Map<String, DataField> paramMap)
    throws RemoteException;

  public abstract List<ChildRole> matchRole(String paramString1, String paramString2, String paramString3, Map<String, DataField> paramMap)
    throws RemoteException;

  public abstract List<String> getConditions(String paramString1, String paramString2)
    throws RemoteException;

  public abstract WorkflowDescriptor getWorkflowDescriptor(String paramString1, String paramString2)
    throws RemoteException;
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizworkflow.facade.IBizFacade
 * JD-Core Version:    0.6.0
 */
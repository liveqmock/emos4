package com.ultrapower.workflow.bizconfig.custom;

import com.ultrapower.workflow.configuration.custom.model.WfModel;
import com.ultrapower.workflow.configuration.custom.model.WfModelProperties;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public abstract interface IModelManager extends Remote
{
  public abstract void test()
    throws RemoteException;

  public abstract boolean saveOrUpdateModel(WfModel paramWfModel)
    throws RemoteException;

  public abstract boolean delModelById(String paramString)
    throws RemoteException;

  public abstract List<WfModel> getAllModel()
    throws RemoteException;

  public abstract WfModel getModelByCode(String paramString)
    throws RemoteException;

  public abstract WfModel getModelById(String paramString)
    throws RemoteException;

  public abstract List<WfModelProperties> getModelPropsByCode(String paramString)
    throws RemoteException;

  public abstract void delModelPropById(String paramString)
    throws RemoteException;

  public abstract boolean saveOrUpdateModelProp(WfModelProperties paramWfModelProperties)
    throws RemoteException;

  public abstract WfModelProperties getModelPropById(String paramString)
    throws RemoteException;

  public abstract List<WfModel> getWfModelByWftype(String paramString)
    throws RemoteException;
}


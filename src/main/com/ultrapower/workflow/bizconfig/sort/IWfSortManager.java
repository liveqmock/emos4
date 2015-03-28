package com.ultrapower.workflow.bizconfig.sort;

import com.ultrapower.workflow.configuration.sort.model.WfSort;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public abstract interface IWfSortManager extends Remote
{
  public abstract void test()
    throws RemoteException;

  public abstract List<WfSort> getAllWfSort()
    throws RemoteException;

  public abstract boolean saveWfSort(WfSort paramWfSort)
    throws RemoteException;

  public abstract boolean delWfSort(String paramString)
    throws RemoteException;

  public abstract WfSort getWfSortByid(String paramString)
    throws RemoteException;

  public abstract List<WfType> getWfTypeBySortId(String paramString)
    throws RemoteException;

  public abstract boolean saveWfType(WfType paramWfType)
    throws RemoteException;

  public abstract boolean delWfTypeById(String paramString)
    throws RemoteException;

  public abstract WfType getWfTypeByid(String paramString)
    throws RemoteException;

  public abstract List<WfSort> getChildSortById(String paramString)
    throws RemoteException;

  public abstract List<WfType> getAllWfType()
    throws RemoteException;

  public abstract WfType getWfTypeByCode(String paramString)
    throws RemoteException;

  public abstract WfSort getWfSortByCode(String paramString)
    throws RemoteException;
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizconfig.sort.IWfSortManager
 * JD-Core Version:    0.6.0
 */
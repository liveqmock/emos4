package com.ultrapower.workflow.bizconfig.version;

import com.ultrapower.workflow.configuration.version.model.WfVersion;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public abstract interface IWfVersionManager extends Remote
{
  public abstract void test()
    throws RemoteException;

  public abstract boolean saveWfVersion(WfVersion paramWfVersion, boolean paramBoolean)
    throws RemoteException;

  public abstract boolean removeById(String paramString)
    throws RemoteException;

  public abstract List<WfVersion> getListByBaseCode(String paramString)
    throws RemoteException;

  public abstract List<WfVersion> getMainListByBaseCode(String paramString)
    throws RemoteException;

  public abstract List<WfVersion> getSubListByBaseCode(String paramString)
    throws RemoteException;

  public abstract List<WfVersion> getEnableListByBaseCode(String paramString)
    throws RemoteException;

  public abstract List<WfVersion> getAllWfList()
    throws RemoteException;

  public abstract WfVersion getById(String paramString)
    throws RemoteException;

  public abstract boolean startVersion(String paramString)
    throws RemoteException;

  public abstract boolean stopVersion(String paramString)
    throws RemoteException;

  public abstract boolean importWf(WfVersion paramWfVersion)
    throws RemoteException;

  public abstract WfVersion exportWf(String paramString)
    throws RemoteException;

  public abstract WfVersion getByCode(String paramString)
    throws RemoteException;

  public abstract String getSerialNum(String paramString1, String paramString2)
    throws RemoteException;
}

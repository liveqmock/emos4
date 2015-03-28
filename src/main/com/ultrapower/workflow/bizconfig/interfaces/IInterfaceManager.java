package com.ultrapower.workflow.bizconfig.interfaces;

import com.ultrapower.workflow.configuration.interfaces.model.WfInterSetting;
import com.ultrapower.workflow.configuration.interfaces.model.WfInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public abstract interface IInterfaceManager extends Remote
{
  public abstract void test()
    throws RemoteException;

  public abstract boolean saveOrUpdateInte(WfInterface paramWfInterface)
    throws RemoteException;

  public abstract boolean removeInterByCode(String paramString)
    throws RemoteException;

  public abstract List<WfInterface> getAllInte()
    throws RemoteException;

  public abstract boolean saveOrUpdateSetting(WfInterSetting paramWfInterSetting)
    throws RemoteException;

  public abstract List<WfInterSetting> getInteSettingByWfCode(String paramString)
    throws RemoteException;

  public abstract WfInterSetting getInteSettingById(String paramString)
    throws RemoteException;

  public abstract List<WfInterSetting> getInteByWfCodeAndType(String paramString1, String paramString2)
    throws RemoteException;

  public abstract WfInterface getInteByCode(String paramString)
    throws RemoteException;

  public abstract List<WfInterface> getInterfaceListByType(String paramString)
    throws RemoteException;
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizconfig.interfaces.IInterfaceManager
 * JD-Core Version:    0.6.0
 */
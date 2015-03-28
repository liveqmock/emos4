package com.ultrapower.workflow.wfcontrol;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface WfControl extends Remote
{
  public abstract void sendCommand(String paramString)
    throws RemoteException;
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.wfcontrol.WfControl
 * JD-Core Version:    0.6.0
 */
package com.ultrapower.workflow.client;

import com.ultrapower.workflow.bizworkflow.facade.IBizFacade;
import com.ultrapower.workflow.client.config.PropUtils;
import com.ultrapower.workflow.client.local.LocalServiceClientImpl;
import com.ultrapower.workflow.client.rmi.RMIServiceClientImpl;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public abstract class WorkFlowServiceClient
{
  private static IWorkFlowServiceClient rmiClient = new RMIServiceClientImpl();

  public static IWorkFlowServiceClient clientInstance() {
    String serviceType = PropUtils.getProp("ServiceType");
    if ("LOCAL".equals(serviceType)) {
      try {
        IWorkFlowServiceClient localClient = new LocalServiceClientImpl();
        return localClient;
      } catch (Exception e) {
        return null;
      }
    }
    return rmiClient;
  }

  public static void main(String[] args) throws RemoteException, Throwable
  {
    List params = new ArrayList();
    params.add("TRANSFLOW");
    IBizFacade bizFacade = clientInstance().getBizFacade();
    bizFacade.test();

    Thread.sleep(15000L);
    bizFacade = clientInstance().getBizFacade();

    params.add("");

    params.add("FREESUBTEST-20100729094420");

    params.add("DEFINE");

    params.add("");

    params.add("");

    params.add("Demo");

    params.add("ASSIGN");

    params.add("");

    params.add("U#:huangwei#:ASSIGN#:2#:11#:22#:33#:#:dp_5#:#:这里写派发说明#;U#:wangxiaodong#:ASSIGN#:2#:11#:22#:33#:#:dp_5#:#:这里写派发说明");

    params.add("ActionText@:4@:下一步动作");

    params.add("1");

    bizFacade.call(params);
  }
}
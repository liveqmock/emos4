package com.ultrapower.workflow.client.rmi;

import com.ultrapower.workflow.bizconfig.custom.IModelManager;
import com.ultrapower.workflow.bizconfig.interfaces.IInterfaceManager;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.bizconfig.version.IWfVersionManager;
import com.ultrapower.workflow.bizworkflow.facade.IBizFacade;
import com.ultrapower.workflow.client.IWorkFlowServiceClient;
import com.ultrapower.workflow.client.config.PropUtils;
import java.io.PrintStream;
import java.rmi.Naming;
import org.apache.log4j.Logger;

public class RMIServiceClientImpl
  implements IWorkFlowServiceClient
{
  private static Logger log = Logger.getLogger(RMIServiceClientImpl.class);
  private static IWfVersionManager remoteVersion;
  private static IWfSortManager remoteSort;
  private static IInterfaceManager remoteInter;
  private static IModelManager remoteModel;
  private static IBizFacade bizBizFacade;
  public static String url = PropUtils.getProp("Url");
  public static String cfgUrl = url + "version";
  public static String sortUrl = url + "sort";
  public static String interUrl = url + "inter";
  public static String modelUrl = url + "model";
  public static String bizFacadeUrl = url + "bizFacade";

  public synchronized IWfVersionManager getVersionService()
  {
    System.out.println("remoteConfig是否为空 " + (remoteVersion == null));
    if (remoteVersion == null)
    {
      connect();
    }
    else try {
        System.out.println("测试连通！");
        remoteVersion.test();
        System.out.println("连通ok");
      } catch (Exception e) {
        System.out.println("缓存对象已失效，重新连接！");
        connect();
      }

    return remoteVersion;
  }

  public synchronized IWfSortManager getSortService()
  {
    log.info("sortVersion " + (remoteSort == null));

    if (remoteSort == null)
    {
      connect();
    }
    else try {
        remoteSort.test();
      } catch (Exception e) {
        log.info("缓存对象已失效，重新连接！");
        connect();
      }

    return remoteSort;
  }

  public synchronized IInterfaceManager getInterfaceService()
  {
    log.info("remoteInter " + (remoteInter == null));

    if (remoteInter == null)
    {
      connect();
    }
    else try {
        remoteInter.test();
      } catch (Exception e) {
        log.info("缓存对象已失效，重新连接！");
        connect();
      }

    return remoteInter;
  }

  public synchronized IModelManager getModelService()
  {
    log.info("remoteModel " + (remoteModel == null));

    if (remoteModel == null)
    {
      connect();
    }
    else try {
        remoteModel.test();
      } catch (Exception e) {
        log.info("缓存对象已失效，重新连接！");
        connect();
      }

    return remoteModel;
  }

  public synchronized IBizFacade getBizFacade()
  {
    if (bizBizFacade == null)
    {
      connect();
    }
    else try {
        bizBizFacade.test();
      } catch (Exception e) {
        log.info("缓存对象已失效，重新连接！");
        connect();
      }

    return bizBizFacade;
  }

  public void connect()
  {
    try
    {
      log.info("连接cfgUrl : " + cfgUrl);
      log.info("连接sortUrl : " + sortUrl);
      log.info("连接interUrl : " + interUrl);
      log.info("连接modelUrl : " + modelUrl);
      log.info("连接bizFacadeUrl : " + bizFacadeUrl);
      remoteVersion = (IWfVersionManager)Naming.lookup(cfgUrl);
      remoteSort = (IWfSortManager)Naming.lookup(sortUrl);
      remoteInter = (IInterfaceManager)Naming.lookup(interUrl);
      remoteModel = (IModelManager)Naming.lookup(modelUrl);
      bizBizFacade = (IBizFacade)Naming.lookup(bizFacadeUrl);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public void disconnect()
  {
    try
    {
      Naming.unbind(cfgUrl);
      Naming.unbind(sortUrl);
      Naming.unbind(interUrl);
      Naming.unbind(modelUrl);
      Naming.unbind(bizFacadeUrl);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
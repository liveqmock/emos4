/*     */ package com.ultrapower.workflow.bizworkflow.server;
/*     */ 
/*     */ import com.ultrapower.workflow.bizconfig.custom.IModelManager;
/*     */ import com.ultrapower.workflow.bizconfig.interfaces.impl.InterfaceManagerImpl;
/*     */ import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
/*     */ import com.ultrapower.workflow.bizconfig.version.IWfVersionManager;
/*     */ import com.ultrapower.workflow.bizworkflow.facade.IBizFacade;
/*     */ import com.ultrapower.workflow.configuration.version.service.impl.XMLWfVersionService;
/*     */ import com.ultrapower.workflow.engine.def.WorkflowFactory;
/*     */ import com.ultrapower.workflow.utils.ApplicationContextUtils;
/*     */ import com.ultrapower.workflow.utils.PropertiesUtils;
/*     */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*     */ import com.ultrapower.workflow.utils.WfEventUtils;
/*     */ import com.ultrapower.workflow.utils.WorkflowConfigParser;
/*     */ import com.ultrapower.workflow.wfcontrol.WfControl;
/*     */ import com.ultrapower.workflow.wfcontrol.impl.WfControlImpl;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.registry.LocateRegistry;
/*     */ import java.rmi.registry.Registry;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class RMIServerMain
/*     */ {
/*  29 */   private static Logger log = LoggerFactory.getLogger(RMIServerMain.class);
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  33 */     startup();
/*     */   }
/*     */ 
/*     */   public static void startup()
/*     */   {
/*     */     try
/*     */     {
/*  42 */       bind();
/*     */ 
/*  44 */       load();
/*     */ 
/*  46 */       String ip = getIp();
/*  47 */       int port = getPort();
/*  48 */       log.info("server监听地址:" + ip + ":" + port);
/*  49 */       log.info("流程引擎启动完毕!");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  53 */       log.error(e.getMessage(), e);
/*  54 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void load()
/*     */   {
/*     */     try
/*     */     {
/*  64 */       WorkflowConfigParser.initWorkflowConfig();
/*     */ 
/*  67 */       WfEngineUtils.loadStates();
/*     */ 
/*  70 */       WfEngineUtils.loadARSchema();
/*     */ 
/*  73 */       WfEngineUtils.loadNoticeTemplate();
/*     */ 
/*  76 */       WfEngineUtils.loadMessage();
/*     */ 
/*  79 */       WfEventUtils.loadEventDefines();
/*     */ 
/*  82 */       XMLWfVersionService verServ = (XMLWfVersionService)ApplicationContextUtils.getBean("verServiceImpl");
/*  83 */       verServ.init();
/*     */ 
/*  86 */       WorkflowFactory factory = (WorkflowFactory)ApplicationContextUtils.getBean("factory");
/*  87 */       factory.init();
/*     */     } catch (Exception e) {
/*  89 */       log.error(e.getMessage(), e);
/*  90 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static Registry createRegistry() throws RemoteException
/*     */   {
/*  96 */     String ip = getIp();
/*  97 */     int port = getPort();
/*  98 */     Registry registry = null;
/*     */ 
/* 107 */     registry = LocateRegistry.createRegistry(port);
/* 108 */     registry.list();
/* 109 */     return registry;
/*     */   }
/*     */ 
/*     */   public static int getPort() {
/* 113 */     int port = 3344;
/* 114 */     String ports = PropertiesUtils.getProp("PORT");
/* 115 */     if (StringUtils.isNotBlank(ports)) {
/*     */       try {
/* 117 */         port = Integer.parseInt(ports);
/*     */       } catch (Exception e) {
/* 119 */         port = 3344;
/*     */       }
/*     */     }
/* 122 */     return port;
/*     */   }
/*     */ 
/*     */   public static String getIp() {
/* 126 */     String ip = PropertiesUtils.getProp("IP");
/* 127 */     if (StringUtils.isBlank(ip)) {
/* 128 */       ip = "localhost";
/*     */     }
/* 130 */     return ip;
/*     */   }
/*     */ 
/*     */   public static void bind() throws RemoteException {
/* 134 */     Registry registry = null;
/* 135 */     registry = createRegistry();
/* 136 */     IWfVersionManager ver = (IWfVersionManager)ApplicationContextUtils.getBean("rmiVerServiceImpl");
/* 137 */     IWfSortManager sort = (IWfSortManager)ApplicationContextUtils.getBean("rmiSortServiceImpl");
/* 138 */     InterfaceManagerImpl inter = (InterfaceManagerImpl)ApplicationContextUtils.getBean("rmiInterServiceImpl");
/* 139 */     IModelManager model = (IModelManager)ApplicationContextUtils.getBean("rmiModelServiceImpl");
/* 140 */     IBizFacade bizFacade = (IBizFacade)ApplicationContextUtils.getBean("bizFacade");
/* 141 */     WfControl control = new WfControlImpl();
/* 142 */     registry.rebind("version", ver);
/* 143 */     registry.rebind("sort", sort);
/* 144 */     registry.rebind("inter", inter);
/* 145 */     registry.rebind("model", model);
/* 146 */     registry.rebind("control", control);
/* 147 */     registry.rebind("bizFacade", bizFacade);
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizworkflow.server.RMIServerMain
 * JD-Core Version:    0.6.0
 */
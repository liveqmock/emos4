/*    */ package com.ultrapower.workflow.bizworkflow.server;
/*    */ 
/*    */ import com.ultrapower.workflow.wfcontrol.WfControl;
/*    */ import java.net.MalformedURLException;
/*    */ import java.rmi.Naming;
/*    */ import java.rmi.NotBoundException;
/*    */ import java.rmi.RemoteException;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ public class RMIServerStop
/*    */ {
/* 15 */   private static Logger log = LoggerFactory.getLogger(RMIServerStop.class);
/*    */ 
/*    */   public static void main(String[] args)
/*    */     throws MalformedURLException, RemoteException, NotBoundException
/*    */   {
/* 24 */     String ip = RMIServerMain.getIp();
/* 25 */     int port = RMIServerMain.getPort();
/* 26 */     log.info("server监听地址:" + ip + ":" + port);
/* 27 */     String url = "rmi://" + ip + ":" + port + "/control";
/*    */     try {
/* 29 */       WfControl control = (WfControl)Naming.lookup(url);
/* 30 */       control.sendCommand("close");
/*    */     } catch (Exception e) {
/* 32 */       log.error(e.getMessage(), e);
/* 33 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizworkflow.server.RMIServerStop
 * JD-Core Version:    0.6.0
 */
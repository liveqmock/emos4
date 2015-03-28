/*    */ package com.ultrapower.workflow.bizworkflow.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.ServerSocket;
/*    */ import java.net.Socket;
/*    */ import java.rmi.server.RMISocketFactory;
/*    */ 
/*    */ public class WFRMISocket extends RMISocketFactory
/*    */ {
/*    */   public ServerSocket createServerSocket(int port)
/*    */     throws IOException
/*    */   {
/* 14 */     if (port == 0)
/*    */     {
/* 16 */       port = RMIServerMain.getPort() + 1;
/*    */     }
/* 18 */     return new ServerSocket(port);
/*    */   }
/*    */ 
/*    */   public Socket createSocket(String host, int port)
/*    */     throws IOException
/*    */   {
/* 24 */     return new Socket(host, port);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizworkflow.server.WFRMISocket
 * JD-Core Version:    0.6.0
 */
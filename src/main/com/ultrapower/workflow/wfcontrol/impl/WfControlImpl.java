/*    */ package com.ultrapower.workflow.wfcontrol.impl;
/*    */ 
/*    */ import com.ultrapower.workflow.utils.ApplicationContextUtils;
/*    */ import com.ultrapower.workflow.utils.WfEngineConstants;
/*    */ import com.ultrapower.workflow.wfcontrol.WfControl;
/*    */ import java.rmi.RemoteException;
/*    */ import java.rmi.server.UnicastRemoteObject;
/*    */ import java.util.Vector;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.ConfigurableApplicationContext;
/*    */ 
/*    */ public class WfControlImpl extends UnicastRemoteObject
/*    */   implements WfControl
/*    */ {
/* 24 */   private static Logger log = LoggerFactory.getLogger(WfControlImpl.class);
/*    */ 
/* 53 */   private int i = 0;
/*    */ 
/*    */   public WfControlImpl()
/*    */     throws RemoteException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void sendCommand(String command)
/*    */     throws RemoteException
/*    */   {
/* 27 */     log.info("command : " + command);
/* 28 */     if ((StringUtils.isNotBlank(command)) && 
/* 29 */       (command.equals("close"))) {
/* 30 */       log.info("开始关闭流程引擎......");
/* 31 */       WfEngineConstants.ISALIVE = false;
/*    */ 
/* 33 */       new Thread(new Runnable()
/*    */       {
/*    */         public void run() {
/* 36 */           if (WfControlImpl.this.isFinish()) {
/* 37 */             ApplicationContext ctx = ApplicationContextUtils.ctx;
/* 38 */             if ((ctx instanceof ConfigurableApplicationContext)) {
/* 39 */               WfControlImpl.log.info("释放Spring容器资源！");
/* 40 */               ConfigurableApplicationContext cfg = (ConfigurableApplicationContext)ctx;
/* 41 */               cfg.close();
/*    */             }
/* 43 */             WfControlImpl.log.info("流程引擎已关闭！");
/* 44 */             System.exit(0);
/*    */           }
/*    */         }
/*    */       }).start();
/*    */     }
/*    */   }
/*    */ 
/*    */   public boolean isFinish()
/*    */   {
/* 55 */     this.i += 1;
/* 56 */     if (this.i >= 6) {
/* 57 */       log.info("关闭等待超时，强制关闭！！");
/* 58 */       return true;
/*    */     }
/* 60 */     Vector curr = WfEngineConstants.currentThreads;
/* 61 */     if (CollectionUtils.isNotEmpty(curr)) {
/*    */       try {
/* 63 */         log.info("当前还有[" + curr.size() + "]个线程正在运行！");
/* 64 */         Thread.sleep(500L);
/* 65 */         isFinish();
/*    */       } catch (InterruptedException e) {
/* 67 */         e.printStackTrace();
/*    */       }
/*    */     }
/* 70 */     return true;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.wfcontrol.impl.WfControlImpl
 * JD-Core Version:    0.6.0
 */
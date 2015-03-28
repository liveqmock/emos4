/*    */ package com.ultrapower.workflow.bizconfig.interfaces.impl;
/*    */ 
/*    */ import com.ultrapower.workflow.bizconfig.interfaces.IInterfaceManager;
/*    */ import com.ultrapower.workflow.configuration.interfaces.model.WfInterSetting;
/*    */ import com.ultrapower.workflow.configuration.interfaces.model.WfInterface;
/*    */ import com.ultrapower.workflow.configuration.interfaces.service.IInterfaceManagerService;
/*    */ import java.io.PrintStream;
/*    */ import java.rmi.RemoteException;
/*    */ import java.rmi.server.UnicastRemoteObject;
/*    */ import java.util.List;
/*    */ 
/*    */ public class InterfaceManagerImpl extends UnicastRemoteObject
/*    */   implements IInterfaceManager
/*    */ {
/*    */   IInterfaceManagerService interServiceImpl;
/*    */ 
/*    */   public InterfaceManagerImpl()
/*    */     throws RemoteException
/*    */   {
/*    */   }
/*    */ 
/*    */   public List<WfInterface> getAllInte()
/*    */     throws RemoteException
/*    */   {
/* 22 */     return this.interServiceImpl.getAllInte();
/*    */   }
/*    */ 
/*    */   public List<WfInterSetting> getInteByWfCodeAndType(String wfCode, String runType) throws RemoteException {
/* 26 */     return this.interServiceImpl.getInteByWfCodeAndType(wfCode, runType);
/*    */   }
/*    */ 
/*    */   public WfInterSetting getInteSettingById(String settingId) throws RemoteException
/*    */   {
/* 31 */     return this.interServiceImpl.getInteSettingById(settingId);
/*    */   }
/*    */ 
/*    */   public List<WfInterSetting> getInteSettingByWfCode(String wfCode) throws RemoteException
/*    */   {
/* 36 */     return this.interServiceImpl.getInteSettingByWfCode(wfCode);
/*    */   }
/*    */ 
/*    */   public List<WfInterface> getInterfaceListByType(String type)
/*    */     throws RemoteException
/*    */   {
/* 43 */     return this.interServiceImpl.getInterfaceListByType(type);
/*    */   }
/*    */ 
/*    */   public boolean removeInterByCode(String Code) throws RemoteException
/*    */   {
/* 48 */     return this.interServiceImpl.removeInterByCode(Code);
/*    */   }
/*    */ 
/*    */   public boolean saveOrUpdateInte(WfInterface wfInte) throws RemoteException {
/* 52 */     return this.interServiceImpl.saveOrUpdateInte(wfInte);
/*    */   }
/*    */ 
/*    */   public boolean saveOrUpdateSetting(WfInterSetting wfInteSetting) throws RemoteException
/*    */   {
/* 57 */     return this.interServiceImpl.saveOrUpdateSetting(wfInteSetting);
/*    */   }
/*    */ 
/*    */   public WfInterface getInteByCode(String wfInteCode)
/*    */     throws RemoteException
/*    */   {
/* 63 */     return this.interServiceImpl.getInteByCode(wfInteCode);
/*    */   }
/*    */ 
/*    */   public void test() throws RemoteException {
/* 67 */     System.out.println("succ");
/*    */   }
/*    */ 
/*    */   public IInterfaceManagerService getInterServiceImpl() {
/* 71 */     return this.interServiceImpl;
/*    */   }
/*    */ 
/*    */   public void setInterServiceImpl(IInterfaceManagerService interServiceImpl) {
/* 75 */     this.interServiceImpl = interServiceImpl;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizconfig.interfaces.impl.InterfaceManagerImpl
 * JD-Core Version:    0.6.0
 */
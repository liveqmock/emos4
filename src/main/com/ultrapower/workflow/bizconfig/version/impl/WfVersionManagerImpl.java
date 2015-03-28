/*    */ package com.ultrapower.workflow.bizconfig.version.impl;
/*    */ 
/*    */ import com.ultrapower.workflow.bizconfig.version.IWfVersionManager;
/*    */ import com.ultrapower.workflow.configuration.version.model.WfVersion;
/*    */ import com.ultrapower.workflow.configuration.version.service.IWfVersionService;
/*    */ import java.io.PrintStream;
/*    */ import java.rmi.RemoteException;
/*    */ import java.rmi.server.UnicastRemoteObject;
/*    */ import java.util.List;
/*    */ 
/*    */ public class WfVersionManagerImpl extends UnicastRemoteObject
/*    */   implements IWfVersionManager
/*    */ {
/*    */   private IWfVersionService verServiceImpl;
/*    */ 
/*    */   public WfVersionManagerImpl()
/*    */     throws RemoteException
/*    */   {
/*    */   }
/*    */ 
/*    */   public WfVersion exportWf(String wfVersionCode)
/*    */     throws RemoteException
/*    */   {
/* 21 */     return this.verServiceImpl.exportWf(wfVersionCode);
/*    */   }
/*    */ 
/*    */   public List<WfVersion> getAllWfList() throws RemoteException {
/* 25 */     return this.verServiceImpl.getAllWfList();
/*    */   }
/*    */ 
/*    */   public WfVersion getByCode(String verCode) throws RemoteException {
/* 29 */     return this.verServiceImpl.getByCode(verCode);
/*    */   }
/*    */ 
/*    */   public WfVersion getById(String id) throws RemoteException {
/* 33 */     return this.verServiceImpl.getById(id);
/*    */   }
/*    */ 
/*    */   public List<WfVersion> getEnableListByBaseCode(String baseCode) throws RemoteException
/*    */   {
/* 38 */     return this.verServiceImpl.getEnableListByBaseCode(baseCode);
/*    */   }
/*    */ 
/*    */   public List<WfVersion> getListByBaseCode(String baseCode) throws RemoteException
/*    */   {
/* 43 */     return this.verServiceImpl.getListByBaseCode(baseCode);
/*    */   }
/*    */ 
/*    */   public String getSerialNum(String baseSchema, String versionCode) throws RemoteException {
/* 47 */     return this.verServiceImpl.getSerialNum(baseSchema, versionCode);
/*    */   }
/*    */ 
/*    */   public boolean importWf(WfVersion verion) throws RemoteException {
/* 51 */     return this.verServiceImpl.importWf(verion);
/*    */   }
/*    */ 
/*    */   public boolean removeById(String id) throws RemoteException {
/* 55 */     return this.verServiceImpl.removeById(id);
/*    */   }
/*    */ 
/*    */   public boolean saveWfVersion(WfVersion version, boolean asNew) throws RemoteException {
/* 59 */     return this.verServiceImpl.saveWfVersion(version, asNew);
/*    */   }
/*    */ 
/*    */   public boolean startVersion(String id) throws RemoteException {
/* 63 */     return this.verServiceImpl.startVersion(id);
/*    */   }
/*    */ 
/*    */   public boolean stopVersion(String id) throws RemoteException {
/* 67 */     return this.verServiceImpl.stopVersion(id);
/*    */   }
/*    */ 
/*    */   public void test() throws RemoteException {
/* 71 */     System.out.println("连接正常！");
/*    */   }
/*    */ 
/*    */   public List<WfVersion> getMainListByBaseCode(String baseCode) {
/* 75 */     return this.verServiceImpl.getMainListByBaseCode(baseCode);
/*    */   }
/*    */ 
/*    */   public List<WfVersion> getSubListByBaseCode(String baseCode) {
/* 79 */     return this.verServiceImpl.getSubListByBaseCode(baseCode);
/*    */   }
/*    */ 
/*    */   public IWfVersionService getVerServiceImpl() {
/* 83 */     return this.verServiceImpl;
/*    */   }
/*    */ 
/*    */   public void setVerServiceImpl(IWfVersionService verServiceImpl) {
/* 87 */     this.verServiceImpl = verServiceImpl;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizconfig.version.impl.WfVersionManagerImpl
 * JD-Core Version:    0.6.0
 */
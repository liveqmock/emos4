/*    */ package com.ultrapower.workflow.bizconfig.sort.impl;
/*    */ 
/*    */ import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
/*    */ import com.ultrapower.workflow.configuration.sort.model.WfSort;
/*    */ import com.ultrapower.workflow.configuration.sort.model.WfType;
/*    */ import com.ultrapower.workflow.configuration.sort.service.IWfSortService;
/*    */ import java.rmi.RemoteException;
/*    */ import java.rmi.server.UnicastRemoteObject;
/*    */ import java.util.List;
/*    */ 
/*    */ public class WfSortManagerImpl extends UnicastRemoteObject
/*    */   implements IWfSortManager
/*    */ {
/*    */   IWfSortService sortServiceImpl;
/*    */ 
/*    */   public WfSortManagerImpl()
/*    */     throws RemoteException
/*    */   {
/*    */   }
/*    */ 
/*    */   public boolean delWfSort(String sortId)
/*    */     throws RemoteException
/*    */   {
/* 22 */     return this.sortServiceImpl.delWfSort(sortId);
/*    */   }
/*    */ 
/*    */   public boolean delWfTypeById(String typeId) throws RemoteException {
/* 26 */     return this.sortServiceImpl.delWfTypeById(typeId);
/*    */   }
/*    */ 
/*    */   public List<WfSort> getAllWfSort() throws RemoteException {
/* 30 */     return this.sortServiceImpl.getAllWfSort();
/*    */   }
/*    */ 
/*    */   public WfSort getWfSortByid(String sortId) throws RemoteException {
/* 34 */     return this.sortServiceImpl.getWfSortByid(sortId);
/*    */   }
/*    */ 
/*    */   public List<WfType> getWfTypeBySortId(String sortId) throws RemoteException {
/* 38 */     return this.sortServiceImpl.getWfTypeBySortId(sortId);
/*    */   }
/*    */ 
/*    */   public WfSort getWfSortByCode(String code)
/*    */     throws RemoteException
/*    */   {
/* 47 */     return this.sortServiceImpl.getWfSortByCode(code);
/*    */   }
/*    */ 
/*    */   public boolean saveWfSort(WfSort wfSort) throws RemoteException {
/* 51 */     return this.sortServiceImpl.saveWfSort(wfSort);
/*    */   }
/*    */ 
/*    */   public boolean saveWfType(WfType wfType) throws RemoteException {
/* 55 */     return this.sortServiceImpl.saveWfType(wfType);
/*    */   }
/*    */ 
/*    */   public WfType getWfTypeByid(String typeId) throws RemoteException {
/* 59 */     return this.sortServiceImpl.getWfTypeByid(typeId);
/*    */   }
/*    */ 
/*    */   public List<WfSort> getChildSortById(String sortId)
/*    */     throws RemoteException
/*    */   {
/* 67 */     return this.sortServiceImpl.getChildSortById(sortId);
/*    */   }
/*    */ 
/*    */   public void test() throws RemoteException {
/*    */   }
/*    */ 
/*    */   public IWfSortService getSortServiceImpl() {
/* 74 */     return this.sortServiceImpl;
/*    */   }
/*    */ 
/*    */   public void setSortServiceImpl(IWfSortService sortServiceImpl) {
/* 78 */     this.sortServiceImpl = sortServiceImpl;
/*    */   }
/*    */ 
/*    */   public List<WfType> getAllWfType()
/*    */     throws RemoteException
/*    */   {
/* 86 */     return this.sortServiceImpl.getAllWfType();
/*    */   }
/*    */ 
/*    */   public WfType getWfTypeByCode(String typeCode)
/*    */     throws RemoteException
/*    */   {
/* 93 */     return this.sortServiceImpl.getWfTypeByCode(typeCode);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizconfig.sort.impl.WfSortManagerImpl
 * JD-Core Version:    0.6.0
 */
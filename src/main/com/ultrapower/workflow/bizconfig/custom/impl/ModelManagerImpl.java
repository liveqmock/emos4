/*    */ package com.ultrapower.workflow.bizconfig.custom.impl;
/*    */ 
/*    */ import com.ultrapower.workflow.bizconfig.custom.IModelManager;
/*    */ import com.ultrapower.workflow.configuration.custom.model.WfModel;
/*    */ import com.ultrapower.workflow.configuration.custom.model.WfModelProperties;
/*    */ import com.ultrapower.workflow.configuration.custom.service.IModelService;
/*    */ import java.rmi.RemoteException;
/*    */ import java.rmi.server.UnicastRemoteObject;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ModelManagerImpl extends UnicastRemoteObject
/*    */   implements IModelManager
/*    */ {
/*    */   IModelService modelServiceImpl;
/*    */ 
/*    */   public ModelManagerImpl()
/*    */     throws RemoteException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void test()
/*    */     throws RemoteException
/*    */   {
/*    */   }
/*    */ 
/*    */   public boolean delModelById(String modelId)
/*    */     throws RemoteException
/*    */   {
/* 26 */     return this.modelServiceImpl.delModelById(modelId);
/*    */   }
/*    */ 
/*    */   public void delModelPropById(String propId) throws RemoteException {
/* 30 */     this.modelServiceImpl.delModelPropById(propId);
/*    */   }
/*    */ 
/*    */   public List<WfModel> getAllModel() throws RemoteException {
/* 34 */     return this.modelServiceImpl.getAllModel();
/*    */   }
/*    */ 
/*    */   public WfModel getModelById(String id) throws RemoteException {
/* 38 */     return this.modelServiceImpl.getModelById(id);
/*    */   }
/*    */ 
/*    */   public WfModelProperties getModelPropById(String id) throws RemoteException {
/* 42 */     return this.modelServiceImpl.getModelPropById(id);
/*    */   }
/*    */ 
/*    */   public List<WfModelProperties> getModelPropsByCode(String modelId) throws RemoteException
/*    */   {
/* 47 */     return this.modelServiceImpl.getModelPropsByCode(modelId);
/*    */   }
/*    */ 
/*    */   public boolean saveOrUpdateModel(WfModel model) throws RemoteException {
/* 51 */     return this.modelServiceImpl.saveOrUpdateModel(model);
/*    */   }
/*    */ 
/*    */   public boolean saveOrUpdateModelProp(WfModelProperties prop) throws RemoteException
/*    */   {
/* 56 */     return this.modelServiceImpl.saveOrUpdateModelProp(prop);
/*    */   }
/*    */ 
/*    */   public WfModel getModelByCode(String code)
/*    */     throws RemoteException
/*    */   {
/* 65 */     return this.modelServiceImpl.getModelByCode(code);
/*    */   }
/*    */ 
/*    */   public List<WfModel> getWfModelByWftype(String wfType) throws RemoteException
/*    */   {
/* 70 */     return this.modelServiceImpl.getWfModelByWftype(wfType);
/*    */   }
/*    */ 
/*    */   public IModelService getModelServiceImpl() {
/* 74 */     return this.modelServiceImpl;
/*    */   }
/*    */ 
/*    */   public void setModelServiceImpl(IModelService modelServiceImpl) {
/* 78 */     this.modelServiceImpl = modelServiceImpl;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizconfig.custom.impl.ModelManagerImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.ultrapower.workflow.engine.def.impl;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
/*    */ import com.ultrapower.workflow.configuration.version.model.WfVersion;
/*    */ import com.ultrapower.workflow.configuration.version.service.IWfVersionService;
/*    */ import com.ultrapower.workflow.engine.def.WorkflowFactory;
/*    */ import com.ultrapower.workflow.engine.def.model.WorkflowDescriptor;
/*    */ import com.ultrapower.workflow.engine.def.parser.XMLParser;
/*    */ import com.ultrapower.workflow.exception.WorkflowDefineException;
/*    */ import java.util.List;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ 
/*    */ public class WorkflowFactoryImpl
/*    */   implements WorkflowFactory
/*    */ {
/*    */   IWfVersionService verServiceImpl;
/*    */ 
/*    */   public void init()
/*    */     throws WorkflowDefineException
/*    */   {
/* 24 */     List verList = this.verServiceImpl.getAllWfList();
/* 25 */     if (CollectionUtils.isNotEmpty(verList))
/* 26 */       for (int i = 0; i < verList.size(); i++) {
/* 27 */         WfVersion ver = (WfVersion)verList.get(i);
/* 28 */         String defName = ver.getCode();
/* 29 */         String workflowXml = ver.getWorkflowXml();
/*    */         try {
/* 31 */           XMLParser parser = new XMLParser(defName, workflowXml);
/*    */ 
/* 33 */           BaseCacheManager.put("WFDefine", defName.toUpperCase(), parser.parseString());
/*    */         } catch (Exception e) {
/* 35 */           e.printStackTrace();
/*    */         }
/*    */       }
/*    */   }
/*    */ 
/*    */   public WorkflowDescriptor getWorkflowDescriptor(String defName)
/*    */     throws WorkflowDefineException
/*    */   {
/* 43 */     WorkflowDescriptor wfDesc = (WorkflowDescriptor)BaseCacheManager.get("WFDefine", defName.toUpperCase());
/* 44 */     return wfDesc;
/*    */   }
/*    */ 
/*    */   public void putCache(String defName, String xmlContent) {
/*    */     try {
/* 49 */       removeCache(defName);
/* 50 */       XMLParser parser = new XMLParser(defName, xmlContent);
/*    */ 
/* 52 */       BaseCacheManager.put("WFDefine", defName.toUpperCase(), parser.parseString());
/*    */     } catch (Exception e) {
/* 54 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void reflushCache()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void removeCache(String defName)
/*    */   {
/* 64 */     BaseCacheManager.removeElement("WFDefine", defName);
/*    */   }
/*    */ 
/*    */   public IWfVersionService getVerServiceImpl() {
/* 68 */     return this.verServiceImpl;
/*    */   }
/*    */ 
/*    */   public void setVerServiceImpl(IWfVersionService verServiceImpl) {
/* 72 */     this.verServiceImpl = verServiceImpl;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.def.impl.WorkflowFactoryImpl
 * JD-Core Version:    0.6.0
 */
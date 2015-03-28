/*    */ package com.ultrapower.workflow.configuration.version.service.impl;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
/*    */ import com.ultrapower.eoms.common.core.dao.IDao;
/*    */ import com.ultrapower.workflow.configuration.version.model.WfVersion;
/*    */ import com.ultrapower.workflow.engine.def.WorkflowFactory;
/*    */ import java.util.List;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class DBWfVersionService extends AbstractWfVersionService
/*    */ {
/*    */   public WfVersion exportWf(String wfVersionCode)
/*    */   {
/* 15 */     return null;
/*    */   }
/*    */ 
/*    */   public String getSerialNum(String baseSchema, String versionCode)
/*    */   {
/* 20 */     return null;
/*    */   }
/*    */ 
/*    */   public boolean importWf(WfVersion verion)
/*    */   {
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */   public List<WfVersion> getAllWfList() {
/* 29 */     return this.verDao.getAll();
/*    */   }
/*    */ 
/*    */   public boolean removeById(String id)
/*    */   {
/* 34 */     WfVersion ver = getById(id);
/* 35 */     this.verDao.removeById(id);
/*    */ 
/* 37 */     BaseCacheManager.removeElement("WFVersion", id);
/* 38 */     this.factory.removeCache(ver.getCode());
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean saveWfVersion(WfVersion version, boolean asNew) {
/* 43 */     this.verDao.save(version);
/* 44 */     WfVersion cache = version.clone();
/*    */ 
/* 46 */     BaseCacheManager.put("WFVersion", cache.getId(), cache);
/* 47 */     this.factory.putCache(cache.getCode(), cache.getWorkflowXml());
/* 48 */     return true;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.version.service.impl.DBWfVersionService
 * JD-Core Version:    0.6.0
 */
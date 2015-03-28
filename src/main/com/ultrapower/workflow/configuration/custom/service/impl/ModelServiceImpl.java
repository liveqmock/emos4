/*    */ package com.ultrapower.workflow.configuration.custom.service.impl;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.dao.IDao;
/*    */ import com.ultrapower.workflow.configuration.custom.model.WfModel;
/*    */ import com.ultrapower.workflow.configuration.custom.model.WfModelProperties;
/*    */ import com.ultrapower.workflow.configuration.custom.service.IModelService;
/*    */ import java.util.List;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class ModelServiceImpl
/*    */   implements IModelService
/*    */ {
/*    */   private IDao<WfModel> molDao;
/*    */   private IDao<WfModelProperties> proDao;
/*    */ 
/*    */   public WfModel getModelById(String id)
/*    */   {
/* 19 */     return (WfModel)this.molDao.get(id);
/*    */   }
/*    */ 
/*    */   public WfModel getModelByCode(String code) {
/* 23 */     return (WfModel)this.molDao.findUnique("from WfModel w where w.code=?", new Object[] { code });
/*    */   }
/*    */ 
/*    */   public WfModelProperties getModelPropById(String id) {
/* 27 */     return (WfModelProperties)this.proDao.get(id);
/*    */   }
/*    */ 
/*    */   public boolean delModelById(String modelId) {
/* 31 */     WfModel wf = getModelById(modelId);
/* 32 */     this.molDao.removeById(modelId);
/* 33 */     this.proDao.executeUpdate("delete from WfModelProperties w where w.modelCode=?", new Object[] { wf.getCode() });
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */   public List<WfModel> getAllModel() {
/* 38 */     return this.molDao.getAll();
/*    */   }
/*    */ 
/*    */   public List<WfModelProperties> getModelPropsByCode(String modelId) {
/* 42 */     String hql = "from WfModelProperties where modelCode = '" + modelId + "' order by orderBy";
/* 43 */     return this.proDao.find(hql, null);
/*    */   }
/*    */ 
/*    */   public List<WfModel> getWfModelByWftype(String wfType)
/*    */   {
/* 51 */     String hql = "from WfModel w where w.type = '" + wfType + "'";
/* 52 */     List list = this.molDao.find(hql, null);
/* 53 */     return list;
/*    */   }
/*    */ 
/*    */   public void delModelPropById(String propId) {
/* 57 */     this.proDao.removeById(propId);
/*    */   }
/*    */ 
/*    */   public boolean saveOrUpdateModelProp(WfModelProperties prop) {
/* 61 */     this.proDao.saveOrUpdate(prop);
/* 62 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean saveOrUpdateModel(WfModel model) {
/* 66 */     this.molDao.saveOrUpdate(model);
/* 67 */     return true;
/*    */   }
/*    */ 
/*    */   public IDao<WfModel> getMolDao() {
/* 71 */     return this.molDao;
/*    */   }
/*    */ 
/*    */   public void setMolDao(IDao<WfModel> molDao) {
/* 75 */     this.molDao = molDao;
/*    */   }
/*    */ 
/*    */   public IDao<WfModelProperties> getProDao() {
/* 79 */     return this.proDao;
/*    */   }
/*    */ 
/*    */   public void setProDao(IDao<WfModelProperties> proDao) {
/* 83 */     this.proDao = proDao;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.custom.service.impl.ModelServiceImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.ultrapower.workflow.relate.impl;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.dao.IDao;
/*    */ import com.ultrapower.workflow.relate.RelateService;
/*    */ import com.ultrapower.workflow.relate.model.RelateModel;
/*    */ import java.util.List;
/*    */ 
/*    */ public class RelateManagerImpl
/*    */   implements RelateService
/*    */ {
/*    */   public IDao<RelateModel> relaDao;
/*    */ 
/*    */   public List<RelateModel> getSyncRelateList(String baseId, String baseSchema)
/*    */   {
/* 14 */     String hql = "from RelateModel where baseId='" + baseId + "' and baseSchema = '" + baseSchema + "' and relateType=1";
/* 15 */     return this.relaDao.find(hql, null);
/*    */   }
/*    */ 
/*    */   public List<RelateModel> getASuncRelateList(String baseId, String baseSchema)
/*    */   {
/* 20 */     String hql = "from RelateModel where baseId='" + baseId + "' and baseSchema = '" + baseSchema + "' and relateType=0";
/* 21 */     return this.relaDao.find(hql, null);
/*    */   }
/*    */ 
/*    */   public void save(RelateModel model) {
/* 25 */     this.relaDao.save(model);
/*    */   }
/*    */ 
/*    */   public IDao<RelateModel> getRelaDao() {
/* 29 */     return this.relaDao;
/*    */   }
/*    */ 
/*    */   public void setRelaDao(IDao<RelateModel> relaDao) {
/* 33 */     this.relaDao = relaDao;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.relate.impl.RelateManagerImpl
 * JD-Core Version:    0.6.0
 */
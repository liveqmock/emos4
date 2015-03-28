/*    */ package com.ultrapower.workflow.bizform.dpfunction.free;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.dao.IDao;
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import java.util.List;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ public class CanActiveStep
/*    */ {
/* 15 */   private static Logger log = LoggerFactory.getLogger(CanActiveStep.class);
/*    */   protected IDao<DealProcessModel> bizDao;
/*    */ 
/*    */   public boolean judge(String baseId, String baseSchema, String entryId, String thisStepId, String tarStepCode)
/*    */   {
/* 29 */     boolean canActive = false;
/* 30 */     String hql = "from DealProcessModel where entryId='" + entryId + "' and flagEndDuplicated='1' and stepId!='" + thisStepId + "' and flagActive!=0 and flagAssignType not in ('" + "协办" + "', '" + "抄送" + "') and (prePhaseNo='" + tarStepCode + "' or origPhaseNo='" + tarStepCode + "')";
/* 31 */     log.info("查询环节是否可以激活，hql=" + hql);
/* 32 */     List find = this.bizDao.find(hql, null);
/* 33 */     if (CollectionUtils.isEmpty(find)) {
/* 34 */       canActive = true;
/*    */ 
/* 36 */       hql = "from DealProcessModel where parentEntryId='" + entryId + "' and flagEndDuplicated='1' and stepId!='" + thisStepId + "' and flagActive!=0 and flagAssignType not in ('" + "协办" + "', '" + "抄送" + "') and prePhaseNo<>'BEGIN' ";
/* 37 */       find = this.bizDao.find(hql, null);
/* 38 */       if (CollectionUtils.isEmpty(find)) {
/* 39 */         canActive = true;
/* 40 */         log.info("前置环节均已完成，可以激活！baseId=" + baseId + ",baseSchema=" + baseSchema);
/*    */       } else {
/* 42 */         canActive = false;
/* 43 */         log.info("有固定环节内部自流程未完成，不能激活！！baseId=" + baseId + ",baseSchema=" + baseSchema);
/*    */       }
/*    */     }
/*    */     else
/*    */     {
/* 48 */       log.info("还有dp未完成，不能激活！");
/* 49 */       for (int i = 0; i < find.size(); i++) {
/* 50 */         DealProcessModel dp = (DealProcessModel)find.get(i);
/* 51 */         log.info("dp=" + dp.getProcessId());
/*    */       }
/*    */     }
/* 54 */     return canActive;
/*    */   }
/*    */ 
/*    */   public IDao<DealProcessModel> getBizDao() {
/* 58 */     return this.bizDao;
/*    */   }
/*    */ 
/*    */   public void setBizDao(IDao<DealProcessModel> bizDao) {
/* 62 */     this.bizDao = bizDao;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.free.CanActiveStep
 * JD-Core Version:    0.6.0
 */
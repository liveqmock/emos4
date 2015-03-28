/*    */ package com.ultrapower.workflow.bizform.dpfunction.define;
/*    */ 
/*    */ import com.ultrapower.workflow.bizform.model.DealProcessModel;
/*    */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.engine.def.model.AbstractDescriptor;
/*    */ import com.ultrapower.workflow.engine.def.model.MetaDescriptor;
/*    */ import com.ultrapower.workflow.engine.def.model.StepDescriptor;
/*    */ import com.ultrapower.workflow.engine.store.model.Step;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import com.ultrapower.workflow.engine.task.model.ProcessTask;
/*    */ import com.ultrapower.workflow.utils.CommonUtils;
/*    */ import com.ultrapower.workflow.utils.WfEngineConstants;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Transactional
/*    */ public class DefaultDefInFunction extends AbstractDefInFunction
/*    */ {
/* 31 */   private static Logger log = LoggerFactory.getLogger(DefaultDefInFunction.class);
/*    */ 
/*    */   public void setOwnFlag(String userId, BaseTask task, Step step, ActionInfo acInfo, Map<String, DataField> inputs, DealProcessModel dp, AbstractDescriptor desc)
/*    */   {
/* 37 */     log.info("开始解析固定流程环节上的流程动作");
/* 38 */     if ((desc != null) && ((desc instanceof StepDescriptor)) && (dp != null) && (task != null)) {
/* 39 */       ProcessTask ptask = (ProcessTask)task;
/* 40 */       StepDescriptor stDesc = (StepDescriptor)desc;
/* 41 */       List metaList = stDesc.getMetaList();
/* 42 */       if (CollectionUtils.isNotEmpty(metaList))
/* 43 */         for (int i = 0; i < metaList.size(); i++) {
/* 44 */           MetaDescriptor mtDesc = (MetaDescriptor)metaList.get(i);
/* 45 */           if (mtDesc != null) {
/* 46 */             String name = mtDesc.getName();
/* 47 */             String value = CommonUtils.getString(mtDesc.getValue());
/* 48 */             long longValue = CommonUtils.getLong(value);
/* 49 */             if (WfEngineConstants.CUSTOM_ACTIONS.equals(name)) {
/* 50 */               dp.setCustomActions(value);
/* 51 */               ptask.setCustomActions(value);
/* 52 */             } else if (WfEngineConstants.flagAssign.equals(name)) {
/* 53 */               dp.setFlagAssign(Long.valueOf(longValue));
/* 54 */               ptask.setFlagAssign(longValue);
/* 55 */             } else if (WfEngineConstants.flagAssist.equals(name)) {
/* 56 */               dp.setFlagAssist(Long.valueOf(longValue));
/* 57 */               ptask.setFlagAssist(longValue);
/* 58 */             } else if (WfEngineConstants.flagCopy.equals(name)) {
/* 59 */               dp.setFlagCopy(Long.valueOf(longValue));
/* 60 */               ptask.setFlagCopy(longValue);
/* 61 */             } else if (WfEngineConstants.flagTurnUp.equals(name)) {
/* 62 */               dp.setFlagTurnUp(Long.valueOf(longValue));
/* 63 */               ptask.setFlagTurnUp(longValue);
/* 64 */             } else if (WfEngineConstants.flagRecall.equals(name)) {
/* 65 */               dp.setFlagRecall(Long.valueOf(longValue));
/* 66 */               ptask.setFlagRecall(longValue);
/* 67 */             } else if (WfEngineConstants.flagCancel.equals(name)) {
/* 68 */               dp.setFlagCancel(Long.valueOf(longValue));
/* 69 */               ptask.setFlagCancel(longValue);
/* 70 */             } else if (WfEngineConstants.flagClose.equals(name)) {
/* 71 */               dp.setFlagClose(Long.valueOf(longValue));
/* 72 */               ptask.setFlagClose(longValue);
/* 73 */             } else if (WfEngineConstants.flagTurnDown.equals(name)) {
/* 74 */               dp.setFlagTurnDown(Long.valueOf(longValue));
/* 75 */               ptask.setFlagTurnDown(longValue);
/* 76 */             } else if (WfEngineConstants.flagToAuditing.equals(name)) {
/* 77 */               dp.setFlagToAuditing(Long.valueOf(longValue));
/* 78 */               ptask.setFlagToAuditing(longValue);
/* 79 */             } else if (WfEngineConstants.flagStartInsideFlow.equals(name)) {
/* 80 */               dp.setFlagStartInsideFlow(Long.valueOf(longValue));
/*    */             }
/*    */           }
/*    */         }
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.dpfunction.define.DefaultDefInFunction
 * JD-Core Version:    0.6.0
 */
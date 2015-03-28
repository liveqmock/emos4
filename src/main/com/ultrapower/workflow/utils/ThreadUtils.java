/*    */ package com.ultrapower.workflow.utils;
/*    */ 
/*    */ import com.ultrapower.workflow.engine.def.model.StepDescriptor;
/*    */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ThreadUtils
/*    */ {
/* 17 */   private static ThreadLocal<List<BaseTask>> newTasks = new ThreadLocal();
/* 18 */   private static ThreadLocal<List<BaseTask>> oldTasks = new ThreadLocal();
/* 19 */   private static ThreadLocal isUpdateEntryState = new ThreadLocal();
/* 20 */   private static ThreadLocal isAllFinish = new ThreadLocal();
/* 21 */   private static ThreadLocal<StepDescriptor> topDefStep = new ThreadLocal();
/*    */ 
/*    */   public static void reset() {
/* 24 */     newTasks.set(new ArrayList());
/* 25 */     oldTasks.set(new ArrayList());
/* 26 */     isUpdateEntryState.set(null);
/* 27 */     isAllFinish.set(null);
/*    */   }
/*    */ 
/*    */   public static Object getIsAllFinish() {
/* 31 */     return isAllFinish.get();
/*    */   }
/*    */ 
/*    */   public static void setIsAllFinish() {
/* 35 */     isAllFinish.set(new Object());
/*    */   }
/*    */ 
/*    */   public static Object isUpdate() {
/* 39 */     return isUpdateEntryState.get();
/*    */   }
/*    */ 
/*    */   public static void setUpdate() {
/* 43 */     isUpdateEntryState.set(new Object());
/*    */   }
/*    */ 
/*    */   public static List<BaseTask> getNewTasks() {
/* 47 */     List list = (List)newTasks.get();
/* 48 */     return list;
/*    */   }
/*    */ 
/*    */   public static List<BaseTask> getOldTasks() {
/* 52 */     List list = (List)oldTasks.get();
/* 53 */     return list;
/*    */   }
/*    */ 
/*    */   public static void addNewTask(BaseTask task) {
/* 57 */     List list = (List)newTasks.get();
/* 58 */     list.add(task);
/*    */   }
/*    */ 
/*    */   public static void removeNewTask(BaseTask task) {
/* 62 */     List list = (List)newTasks.get();
/* 63 */     list.remove(task);
/*    */   }
/*    */ 
/*    */   public static void addOldTask(BaseTask task) {
/* 67 */     List list = (List)oldTasks.get();
/* 68 */     list.add(task);
/*    */   }
/*    */ 
/*    */   public static void removeOldTask(BaseTask task) {
/* 72 */     List list = (List)oldTasks.get();
/* 73 */     list.remove(task);
/*    */   }
/*    */ 
/*    */   public static StepDescriptor getTopDefStep()
/*    */   {
/* 81 */     return (StepDescriptor)topDefStep.get();
/*    */   }
/*    */ 
/*    */   public static void setTopDefStep(StepDescriptor topDefStep)
/*    */   {
/* 89 */     topDefStep.set(topDefStep);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.utils.ThreadUtils
 * JD-Core Version:    0.6.0
 */
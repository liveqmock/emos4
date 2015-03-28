 package com.ultrapower.workflow.utils;
 
 import com.ultrapower.workflow.engine.def.model.StepDescriptor;
 import com.ultrapower.workflow.engine.task.model.BaseTask;
 import java.util.ArrayList;
 import java.util.List;
 
 public class ThreadUtils
 {
   private static ThreadLocal<List<BaseTask>> newTasks = new ThreadLocal();
   private static ThreadLocal<List<BaseTask>> oldTasks = new ThreadLocal();
   private static ThreadLocal isUpdateEntryState = new ThreadLocal();
   private static ThreadLocal isAllFinish = new ThreadLocal();
   private static ThreadLocal<StepDescriptor> topDefStep = new ThreadLocal();
 
   public static void reset() {
     newTasks.set(new ArrayList());
     oldTasks.set(new ArrayList());
     isUpdateEntryState.set(null);
     isAllFinish.set(null);
   }
 
   public static Object getIsAllFinish() {
     return isAllFinish.get();
   }
 
   public static void setIsAllFinish() {
     isAllFinish.set(new Object());
   }
 
   public static Object isUpdate() {
     return isUpdateEntryState.get();
   }
 
   public static void setUpdate() {
     isUpdateEntryState.set(new Object());
   }
 
   public static List<BaseTask> getNewTasks() {
     List list = (List)newTasks.get();
     return list;
   }
 
   public static List<BaseTask> getOldTasks() {
     List list = (List)oldTasks.get();
     return list;
   }
 
   public static void addNewTask(BaseTask task) {
     List list = (List)newTasks.get();
     list.add(task);
   }
 
   public static void removeNewTask(BaseTask task) {
     List list = (List)newTasks.get();
     list.remove(task);
   }
 
   public static void addOldTask(BaseTask task) {
     List list = (List)oldTasks.get();
     list.add(task);
   }
 
   public static void removeOldTask(BaseTask task) {
     List list = (List)oldTasks.get();
     list.remove(task);
   }
 
   public static StepDescriptor getTopDefStep()
   {
     return (StepDescriptor)topDefStep.get();
   }
 
   public static void setTopDefStep(StepDescriptor topDefStep) {
    topDefStep = topDefStep;
    }
 }

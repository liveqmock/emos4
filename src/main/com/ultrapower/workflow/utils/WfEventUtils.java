/*     */ package com.ultrapower.workflow.utils;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.workflow.event.model.EventDefine;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ 
/*     */ public class WfEventUtils
/*     */ {
/*     */   private static Map<String, EventDefine> eventDefineMap;
/*     */ 
/*     */   public static void loadEventDefines()
/*     */   {
/*  25 */     eventDefineMap = new HashMap();
/*  26 */     IDao eventDefineDao = (IDao)ApplicationContextUtils.getBean("eventDefineDao");
/*  27 */     List all = eventDefineDao.find("from EventDefine", null);
/*  28 */     System.out.println("EventDefineListCount===========" + all.size());
/*  29 */     if (CollectionUtils.isNotEmpty(all))
/*  30 */       for (int i = 0; i < all.size(); i++) {
/*  31 */         EventDefine eventDefine = (EventDefine)all.get(i);
/*  32 */         eventDefineMap.put(eventDefine.getPid(), eventDefine);
/*  33 */         System.out.println("EventDefineItem==========");
/*  34 */         System.out.println("eventDefine.getPid():" + eventDefine.getPid() + 
/*  35 */           "eventDefine.getBaseschema()" + eventDefine.getBaseschema() + 
/*  36 */           "eventDefine.getEventsubject()" + eventDefine.getEventsubject() + 
/*  37 */           "eventDefine.getEventaction()" + eventDefine.getEventaction() + 
/*  38 */           "eventDefine.getEventcondition()" + eventDefine.getEventcondition());
/*  39 */         System.out.println("==========");
/*     */       }
/*     */   }
/*     */ 
/*     */   public static void addEventDefineCache(String pid, EventDefine eventDefine)
/*     */   {
/*  45 */     eventDefineMap.put(pid, eventDefine);
/*     */   }
/*     */ 
/*     */   public static void removeEventDefineCache(String pid) {
/*  49 */     eventDefineMap.remove(pid);
/*     */   }
/*     */ 
/*     */   public static EventDefine getEventDefineById(String pid) {
/*  53 */     return (EventDefine)eventDefineMap.get(pid);
/*     */   }
/*     */ 
/*     */   public static void deleteEventDef(String baseSchema, String eventType, String operationType) {
/*  57 */     if (eventDefineMap != null) {
/*  58 */       List dels = new ArrayList();
/*  59 */       for (Iterator iter = eventDefineMap.keySet().iterator(); iter.hasNext(); ) {
/*  60 */         String pid = (String)iter.next();
/*  61 */         EventDefine eventDefine = (EventDefine)eventDefineMap.get(pid);
/*  62 */         String baseschema2 = eventDefine.getBaseschema();
/*  63 */         String eventtype2 = eventDefine.getEventtype();
/*  64 */         String operationtype2 = eventDefine.getOperationtype();
/*  65 */         if ((baseSchema.equals(baseschema2)) && (eventType.equals(eventtype2)) && (operationType.equals(operationtype2))) {
/*  66 */           dels.add(pid);
/*     */         }
/*     */       }
/*  69 */       if (CollectionUtils.isNotEmpty(dels))
/*  70 */         for (int i = 0; i < dels.size(); i++)
/*  71 */           eventDefineMap.remove(dels.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void deleteBySchema(String baseSchema, String operationType)
/*     */   {
/*  78 */     if (eventDefineMap != null) {
/*  79 */       List dels = new ArrayList();
/*  80 */       for (Iterator iter = eventDefineMap.keySet().iterator(); iter.hasNext(); ) {
/*  81 */         String pid = (String)iter.next();
/*  82 */         EventDefine eventDefine = (EventDefine)eventDefineMap.get(pid);
/*  83 */         String baseschema2 = eventDefine.getBaseschema();
/*  84 */         String operationtype2 = eventDefine.getOperationtype();
/*  85 */         if ((baseSchema.equals(baseschema2)) && (operationType.equals(operationtype2))) {
/*  86 */           dels.add(pid);
/*     */         }
/*     */       }
/*  89 */       if (CollectionUtils.isNotEmpty(dels))
/*  90 */         for (int i = 0; i < dels.size(); i++)
/*  91 */           eventDefineMap.remove(dels.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void delEventDefByEventSubject(String baseSchema, String eventSubject, String operationType)
/*     */   {
/*  98 */     if (eventDefineMap != null) {
/*  99 */       List dels = new ArrayList();
/* 100 */       for (Iterator iter = eventDefineMap.keySet().iterator(); iter.hasNext(); ) {
/* 101 */         String pid = (String)iter.next();
/* 102 */         EventDefine eventDefine = (EventDefine)eventDefineMap.get(pid);
/* 103 */         String baseschema2 = eventDefine.getBaseschema();
/* 104 */         String eventsubject2 = eventDefine.getEventsubject();
/* 105 */         String operationtype2 = eventDefine.getOperationtype();
/* 106 */         if ((baseSchema.equals(baseschema2)) && (eventSubject.equals(eventsubject2)) && (operationType.equals(operationtype2))) {
/* 107 */           dels.add(pid);
/*     */         }
/*     */       }
/* 110 */       if (CollectionUtils.isNotEmpty(dels))
/* 111 */         for (int i = 0; i < dels.size(); i++)
/* 112 */           eventDefineMap.remove(dels.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void delEventDefOfStepSubject(String baseSchema, String operationType)
/*     */   {
/* 119 */     if (eventDefineMap != null) {
/* 120 */       List dels = new ArrayList();
/* 121 */       for (Iterator iter = eventDefineMap.keySet().iterator(); iter.hasNext(); ) {
/* 122 */         String pid = (String)iter.next();
/* 123 */         EventDefine eventDefine = (EventDefine)eventDefineMap.get(pid);
/* 124 */         String baseschema2 = eventDefine.getBaseschema();
/* 125 */         String operationtype2 = eventDefine.getOperationtype();
/* 126 */         String eventsubject = eventDefine.getEventsubject();
/* 127 */         if ((baseSchema.equals(baseschema2)) && (operationType.equals(operationtype2)) && (!"FORM".equals(eventsubject))) {
/* 128 */           dels.add(pid);
/*     */         }
/*     */       }
/* 131 */       if (CollectionUtils.isNotEmpty(dels))
/* 132 */         for (int i = 0; i < dels.size(); i++)
/* 133 */           eventDefineMap.remove(dels.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static List<EventDefine> getFormEventDefine(String baseSchema, String eventcondition, String eventAction, String operationType)
/*     */   {
/* 144 */     List events = new ArrayList();
/* 145 */     for (Iterator iter = eventDefineMap.keySet().iterator(); iter.hasNext(); ) {
/* 146 */       String pid = (String)iter.next();
/* 147 */       EventDefine eventDefine = (EventDefine)eventDefineMap.get(pid);
/* 148 */       String baseschema2 = eventDefine.getBaseschema();
/* 149 */       String operationtype2 = eventDefine.getOperationtype();
/* 150 */       String eventAction2 = eventDefine.getEventaction();
/* 151 */       String eventsubject = eventDefine.getEventsubject();
/* 152 */       String eventcondition2 = eventDefine.getEventcondition();
/* 153 */       String eventType = eventDefine.getEventtype();
/* 154 */       String handleType = eventDefine.getHandletype();
/* 155 */       System.out.println("获取Form事件定义===========================");
/* 156 */       System.out.println("baseSchema:" + baseSchema + "=" + baseschema2);
/* 157 */       System.out.println("operationType=" + operationType + "=" + operationtype2);
/* 158 */       System.out.println("eventAction=" + eventAction + "=" + eventAction2);
/* 159 */       System.out.println("eventcondition=" + eventcondition + "=" + eventcondition2);
/* 160 */       System.out.println("handleType=" + handleType);
/* 161 */       System.out.println("eventType=" + eventType);
/* 162 */       if ((eventType.equals("FORM")) && (baseSchema.equals(baseschema2)) && (operationType.equals(operationtype2)) && ("FORM".equals(eventsubject)) && (eventAction.equals(eventAction2)) && (eventcondition.equals(eventcondition2))) {
/* 163 */         EventDefine rtn = eventDefine;
/* 164 */         events.add(rtn);
/*     */       }
/*     */     }
/* 167 */     return events;
/*     */   }
/*     */ 
/*     */   public static List<EventDefine> getByEventType(String baseSchema, String eventType, String eventcondition)
/*     */   {
/* 172 */     List events = new ArrayList();
/* 173 */     for (Iterator iter = eventDefineMap.keySet().iterator(); iter.hasNext(); ) {
/* 174 */       String pid = (String)iter.next();
/* 175 */       EventDefine eventDefine = (EventDefine)eventDefineMap.get(pid);
/* 176 */       String baseschema2 = eventDefine.getBaseschema();
/* 177 */       String eventtype2 = eventDefine.getEventtype();
/* 178 */       String eventcondition2 = eventDefine.getEventcondition();
/* 179 */       if ((baseSchema.equals(baseschema2)) && (eventType.equals(eventtype2)) && (eventcondition.equals(eventcondition2))) {
/* 180 */         events.add(eventDefine);
/*     */       }
/*     */     }
/* 183 */     return events;
/*     */   }
/*     */ 
/*     */   public static List<EventDefine> getByEventSubject(String baseSchema, String eventtype, String eventSubjecte, String eventcondition)
/*     */   {
/* 188 */     List events = new ArrayList();
/* 189 */     for (Iterator iter = eventDefineMap.keySet().iterator(); iter.hasNext(); ) {
/* 190 */       String pid = (String)iter.next();
/* 191 */       EventDefine eventDefine = (EventDefine)eventDefineMap.get(pid);
/* 192 */       String baseschema2 = eventDefine.getBaseschema();
/* 193 */       String eventtype2 = eventDefine.getEventtype();
/* 194 */       String eventcondition2 = eventDefine.getEventcondition();
/* 195 */       String eventsubject = eventDefine.getEventsubject();
/* 196 */       String handleType = eventDefine.getHandletype();
/* 197 */       System.out.println("获取Action事件定义===========================");
/* 198 */       System.out.println("baseSchema:" + baseSchema + "=" + baseschema2);
/* 199 */       System.out.println("eventtype=" + eventtype + "=" + eventtype2);
/* 200 */       System.out.println("eventcondition2=" + eventcondition + "=" + eventcondition2);
/* 201 */       System.out.println("eventsubject=" + eventSubjecte + "=" + eventsubject);
/* 202 */       System.out.println("handleType=" + handleType);
/* 203 */       if ((baseSchema.equals(baseschema2)) && (eventtype.equals(eventtype2)) && ((eventcondition.equals(eventcondition2)) || ("ALL".equals(eventcondition2))) && ((eventSubjecte.equals(eventsubject)) || ("ALL".equals(eventsubject)))) {
/* 204 */         events.add(eventDefine);
/*     */       }
/*     */     }
/* 207 */     return events;
/*     */   }
/*     */ 
/*     */   public static List<EventDefine> getStepEventDefine(String baseSchema, String eventsubject, String eventAction, String operationType) {
/* 211 */     List events = new ArrayList();
/* 212 */     for (Iterator iter = eventDefineMap.keySet().iterator(); iter.hasNext(); ) {
/* 213 */       String pid = (String)iter.next();
/* 214 */       EventDefine eventDefine = (EventDefine)eventDefineMap.get(pid);
/* 215 */       String baseschema2 = eventDefine.getBaseschema();
/* 216 */       String operationtype2 = eventDefine.getOperationtype();
/* 217 */       String eventAction2 = eventDefine.getEventaction();
/* 218 */       String eventsubject2 = eventDefine.getEventsubject();
/* 219 */       String eventType = eventDefine.getEventtype();
/* 220 */       String handleType = eventDefine.getHandletype();
/* 221 */       System.out.println("获取Step事件定义===========================");
/* 222 */       System.out.println("baseSchema:" + baseSchema + "=" + baseschema2);
/* 223 */       System.out.println("operationType=" + operationType + "=" + operationtype2);
/* 224 */       System.out.println("eventAction=" + eventAction + "=" + eventAction2);
/* 225 */       System.out.println("eventsubject=" + eventsubject + "=" + eventsubject2);
/* 226 */       System.out.println("handleType=" + handleType);
/* 227 */       System.out.println("eventType=" + eventType);
/* 228 */       if ((eventType.equals("STEP")) && (baseSchema.equals(baseschema2)) && (operationType.equals(operationtype2)) && (eventAction.equals(eventAction2)) && ((eventsubject.equals(eventsubject2)) || (("ALL".equals(eventsubject2)) && (eventsubject.indexOf("dp_") < 0)))) {
/* 229 */         EventDefine rtn = eventDefine;
/* 230 */         events.add(rtn);
/*     */       }
/*     */     }
/* 233 */     return events;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.utils.WfEventUtils
 * JD-Core Version:    0.6.0
 */
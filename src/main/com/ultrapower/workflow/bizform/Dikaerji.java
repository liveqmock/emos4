/*    */ package com.ultrapower.workflow.bizform;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ 
/*    */ public class Dikaerji
/*    */ {
/*    */   public static List<String> dikaerji(List<List<String>> totalList)
/*    */   {
/* 45 */     List list1 = null;
/* 46 */     List list2 = null;
/* 47 */     if (CollectionUtils.isNotEmpty(totalList)) {
/* 48 */       for (int i = 0; i < totalList.size(); i++) {
/* 49 */         if (i == 0) {
/* 50 */           list2 = null;
/* 51 */           list1 = (List)totalList.get(i);
/*    */         } else {
/* 53 */           list2 = (List)totalList.get(i);
/* 54 */           list1 = loop(list1, list2);
/*    */         }
/*    */       }
/*    */     }
/* 58 */     return list1;
/*    */   }
/*    */ 
/*    */   private static List<String> loop(List<String> list1, List<String> list2) {
/* 62 */     if (CollectionUtils.isEmpty(list2)) {
/* 63 */       return list1;
/*    */     }
/* 65 */     List result = new ArrayList();
/* 66 */     if ((CollectionUtils.isNotEmpty(list1)) && (CollectionUtils.isNotEmpty(list2))) {
/* 67 */       for (int i = 0; i < list1.size(); i++) {
/* 68 */         for (int j = 0; j < list2.size(); j++) {
/* 69 */           result.add((String)list1.get(i) + "%#" + (String)list2.get(j));
/*    */         }
/*    */       }
/*    */     }
/* 73 */     return result;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.Dikaerji
 * JD-Core Version:    0.6.0
 */
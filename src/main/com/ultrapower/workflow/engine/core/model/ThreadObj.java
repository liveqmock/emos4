/*    */ package com.ultrapower.workflow.engine.core.model;
/*    */ 
/*    */ import com.ultrapower.eoms.ultrasm.model.DepInfo;
/*    */ import com.ultrapower.eoms.ultrasm.model.UserInfo;
/*    */ import com.ultrapower.eoms.ultrasm.service.DepManagerService;
/*    */ import com.ultrapower.eoms.ultrasm.service.UserManagerService;
/*    */ import com.ultrapower.workflow.role.model.ChildRole;
/*    */ import com.ultrapower.workflow.role.service.IRoleService;
/*    */ import com.ultrapower.workflow.utils.ApplicationContextUtils;
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ThreadObj
/*    */   implements Serializable
/*    */ {
/* 17 */   private static ThreadLocal<Map<String, UserInfo>> usersThread = new ThreadLocal();
/* 18 */   private static ThreadLocal<Map<String, DepInfo>> depsThread = new ThreadLocal();
/* 19 */   private static ThreadLocal<Map<String, ChildRole>> childRolesMap = new ThreadLocal();
/*    */ 
/*    */   public static UserInfo getUsersInfo(String loginName) {
/* 22 */     Map map = (Map)usersThread.get();
/* 23 */     if (map == null) {
/* 24 */       map = new HashMap();
/* 25 */       usersThread.set(map);
/*    */     }
/* 27 */     UserInfo userInfo = (UserInfo)map.get(loginName);
/* 28 */     if (userInfo == null) {
/* 29 */       UserManagerService userManagerService = (UserManagerService)ApplicationContextUtils.getBean("userManagerService");
/* 30 */       userInfo = userManagerService.getUserByLoginName(loginName);
/* 31 */       map.put(loginName, userInfo);
/*    */     }
/* 33 */     return userInfo;
/*    */   }
/*    */ 
/*    */   public static DepInfo getDepInfo(String depId) {
/* 37 */     Map map = (Map)depsThread.get();
/* 38 */     if (map == null) {
/* 39 */       map = new HashMap();
/* 40 */       depsThread.set(map);
/*    */     }
/* 42 */     DepInfo depInfo = (DepInfo)map.get(depId);
/* 43 */     if (depInfo == null) {
/* 44 */       DepManagerService depManagerService = (DepManagerService)ApplicationContextUtils.getBean("depManagerService");
/* 45 */       depInfo = depManagerService.getDepByID(depId);
/* 46 */       map.put(depId, depInfo);
/*    */     }
/* 48 */     return depInfo;
/*    */   }
/*    */ 
/*    */   public static ChildRole getChildRole(String chiId) {
/* 52 */     Map map = (Map)childRolesMap.get();
/* 53 */     if (map == null) {
/* 54 */       map = new HashMap();
/* 55 */       childRolesMap.set(map);
/*    */     }
/* 57 */     ChildRole chiRole = (ChildRole)map.get(chiId);
/* 58 */     if (chiRole == null) {
/* 59 */       IRoleService roleService = (IRoleService)ApplicationContextUtils.getBean("roleService");
/* 60 */       chiRole = roleService.getChildRoleById(chiId);
/* 61 */       map.put(chiId, chiRole);
/*    */     }
/* 63 */     return chiRole;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.core.model.ThreadObj
 * JD-Core Version:    0.6.0
 */
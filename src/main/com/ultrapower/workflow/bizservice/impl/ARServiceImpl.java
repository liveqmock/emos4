/*    */ package com.ultrapower.workflow.bizservice.impl;
/*    */ 
/*    */ import com.ultrapower.eoms.ultrasm.model.UserInfo;
/*    */ import com.ultrapower.eoms.ultrasm.service.UserManagerService;
/*    */ import com.ultrapower.workflow.bizservice.ARService;
/*    */ import com.ultrapower.workflow.engine.core.model.ThreadObj;
/*    */ import com.ultrapower.workflow.utils.ApplicationContextUtils;
/*    */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*    */ import java.sql.Connection;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.Statement;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.sql.DataSource;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ public class ARServiceImpl
/*    */   implements ARService
/*    */ {
/* 24 */   private static Logger log = LoggerFactory.getLogger(ARServiceImpl.class);
/*    */   private UserManagerService userManagerService;
/*    */ 
/*    */   public List<String> getOwnFields(String baseschema, String stepCode, String entryState, String entryType, String defName)
/*    */   {
/* 30 */     log.info("查询工单的可编辑字段");
/* 31 */     List reVals = new ArrayList();
/* 32 */     StringBuffer sb = new StringBuffer();
/* 33 */     DataSource ds = (DataSource)ApplicationContextUtils.getBean("dataSource");
/*    */     try {
/* 35 */       String sql = "select c650000003 as id,c650000019 as nullflag from t" + WfEngineUtils.getTableName("WF4:Config_BaseOwnFieldInfo") + " where c650000014 <> 0 and c650000002='" + baseschema + "' and ";
/* 36 */       if ("1".equals(entryType)) {
/* 37 */         if (StringUtils.isNotBlank(defName)) {
/* 38 */           sql = sql + " (c650000022='" + defName + "' or c650000022='' or c650000022 is null) and ";
/*    */         }
/* 40 */         sql = sql + "c650000011 like '%" + dealStr(stepCode) + "%'";
/* 41 */       } else if ("0".equals(entryType)) {
/* 42 */         sql = sql + "c650000009 like '%" + dealStr(entryState) + "%'";
/*    */       }
/* 44 */       log.info("查询工单的可编辑字段 sql=" + sql);
/* 45 */       Connection con = ds.getConnection();
/* 46 */       Statement st = con.createStatement();
/* 47 */       ResultSet rs = st.executeQuery(sql);
/* 48 */       while (rs.next()) {
/* 49 */         String name = rs.getString("id");
/* 50 */         String nullflag = rs.getString("nullflag");
/* 51 */         sb.append(name + ":" + nullflag + ";");
/*    */       }
/* 53 */       String str = sb.toString();
/* 54 */       if (StringUtils.isNotBlank(str)) {
/* 55 */         str = str.substring(0, str.length() - 1);
/*    */       }
/* 57 */       reVals.add(str);
/* 58 */       st.close();
/* 59 */       rs.close();
/* 60 */       con.close();
/*    */     } catch (Exception e) {
/* 62 */       e.printStackTrace();
/*    */     }
/* 64 */     return reVals;
/*    */   }
/*    */ 
/*    */   private static String dealStr(String str) {
/* 68 */     String rtn = "";
/* 69 */     String prefix = ";";
/* 70 */     if (StringUtils.isNotBlank(str)) {
/* 71 */       if (!str.startsWith(prefix)) {
/* 72 */         str = prefix + str;
/*    */       }
/* 74 */       if (!str.endsWith(prefix)) {
/* 75 */         str = str + prefix;
/*    */       }
/* 77 */       rtn = str;
/*    */     }
/* 79 */     return rtn;
/*    */   }
/*    */ 
/*    */   public UserManagerService getUserManagerService() {
/* 83 */     return this.userManagerService;
/*    */   }
/*    */ 
/*    */   public void setUserManagerService(UserManagerService userManagerService) {
/* 87 */     this.userManagerService = userManagerService;
/*    */   }
/*    */ 
/*    */   public UserInfo getUserInfo(String userLoginName) {
/* 91 */     UserInfo user = ThreadObj.getUsersInfo(userLoginName);
/* 92 */     return user;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizservice.impl.ARServiceImpl
 * JD-Core Version:    0.6.0
 */
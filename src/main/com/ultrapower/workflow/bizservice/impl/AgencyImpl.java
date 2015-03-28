/*     */ package com.ultrapower.workflow.bizservice.impl;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.eoms.common.core.support.PageLimit;
/*     */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*     */ import com.ultrapower.workflow.bizservice.AgencyService;
/*     */ import com.ultrapower.workflow.bizservice.model.Agency;
/*     */ import com.ultrapower.workflow.role.model.RoleUser;
/*     */ import com.ultrapower.workflow.role.service.IRoleService;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.List<Lcom.ultrapower.workflow.bizservice.model.Agency;>;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class AgencyImpl
/*     */   implements AgencyService
/*     */ {
/*     */   private IDao<Agency> agencyDao;
/*     */   private IRoleService roleService;
/*     */ 
/*     */   public void saveOrUpdate(Agency agency)
/*     */   {
/*  22 */     String bgDateStr = agency.getBgDateStr();
/*  23 */     String edDateStr = agency.getEdDateStr();
/*  24 */     if (StringUtils.isNotBlank(bgDateStr)) {
/*  25 */       agency.setBgDate(Long.valueOf(TimeUtils.formatDateStringToInt(bgDateStr)));
/*     */     }
/*  27 */     if (StringUtils.isNotBlank(edDateStr)) {
/*  28 */       agency.setEdDate(Long.valueOf(TimeUtils.formatDateStringToInt(edDateStr)));
/*     */     }
/*  30 */     if (StringUtils.isBlank(agency.getId())) {
/*  31 */       agency.setId(null);
/*  32 */       agency.setCreateTime(Long.valueOf(TimeUtils.getCurrentTime()));
/*     */     }
/*  34 */     this.agencyDao.saveOrUpdate(agency);
/*     */   }
/*     */ 
/*     */   public void saveDudyAgency(String dealerLoginName, String dealerFullName, String agentLoginName, String agentFullName, long startDate, long endDate) {
/*  38 */     Agency agency = new Agency();
/*  39 */     agency.setDealerId(dealerLoginName);
/*  40 */     agency.setDealer(dealerFullName);
/*  41 */     agency.setAgentId(agentLoginName);
/*  42 */     agency.setAgent(agentFullName);
/*  43 */     agency.setBgDate(Long.valueOf(startDate));
/*  44 */     agency.setEdDate(Long.valueOf(endDate));
/*  45 */     agency.setCreateTime(Long.valueOf(TimeUtils.getCurrentTime()));
/*  46 */     this.agencyDao.save(agency);
/*     */   }
/*     */ 
/*     */   public List<Agency> getAgencyListByAgentUser(String agentUserName, String baseSchema, String sourceID, String sourceType, int agentTime)
/*     */   {
/*  52 */     String queryAgentSql = "from Agency where baseSchema=? and bgDate<? and edDate>? and agentId=?";
/*  53 */     List agentAllList = this.agencyDao.find(queryAgentSql, new Object[] { baseSchema, Long.valueOf(agentTime), Long.valueOf(agentTime), agentUserName });
/*     */ 
/*  56 */     List sourceUserList = new ArrayList();
/*     */ 
/*  58 */     if (sourceType.equals("USER"))
/*     */     {
/*  60 */       String[] sources = (String[])null;
/*  61 */       if (sourceID.indexOf(",") > -1)
/*     */       {
/*  63 */         sources = sourceID.split(",");
/*     */       }
/*     */       else
/*     */       {
/*  67 */         sources = new String[] { sourceID };
/*     */       }
/*  69 */       for (String u : sources)
/*     */       {
/*  71 */         sourceUserList.add(u);
/*     */       }
/*     */ 
/*     */     }
/*  75 */     else if (sourceType.equals("ROLE"))
/*     */     {
/*  77 */       List rUsers = this.roleService.getRoleUserByCroleID(sourceID);
/*  78 */       if (rUsers.size() > 0)
/*     */       {
/*  80 */         for (RoleUser rUser : rUsers)
/*     */         {
/*  82 */           sourceUserList.add(rUser.getLoginName());
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  88 */     List agentFinalList = new ArrayList();
/*  89 */     for (Agency agency : agentAllList)
/*     */     {
/*  91 */       for (??? = sourceUserList.iterator(); ((Iterator)???).hasNext(); ) { String source = (String)((Iterator)???).next();
/*     */ 
/*  93 */         if (!source.equals(agency.getDealerId()))
/*     */           continue;
/*  95 */         agentFinalList.add(agency);
/*  96 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 100 */     return (List<Agency>)agentFinalList;
/*     */   }
/*     */ 
/*     */   public void deleteById(String id) {
/* 104 */     this.agencyDao.removeById(id);
/*     */   }
/*     */ 
/*     */   public List<Agency> getAll() {
/* 108 */     return this.agencyDao.getAll();
/*     */   }
/*     */ 
/*     */   public List<Agency> getAllByPage()
/*     */   {
/* 113 */     return this.agencyDao.pagedQuery("from Agency ", PageLimit.getInstance(), null);
/*     */   }
/*     */ 
/*     */   public List<Agency> getByUser(String loginName) {
/* 117 */     return this.agencyDao.pagedQuery("from Agency where dealerId='" + loginName + "'", PageLimit.getInstance(), null);
/*     */   }
/*     */ 
/*     */   public Agency getById(String id) {
/* 121 */     return (Agency)this.agencyDao.get(id);
/*     */   }
/*     */ 
/*     */   public IDao<Agency> getAgencyDao() {
/* 125 */     return this.agencyDao;
/*     */   }
/*     */ 
/*     */   public void setAgencyDao(IDao<Agency> agencyDao) {
/* 129 */     this.agencyDao = agencyDao;
/*     */   }
/*     */ 
/*     */   public IRoleService getRoleService() {
/* 133 */     return this.roleService;
/*     */   }
/*     */ 
/*     */   public void setRoleService(IRoleService roleService) {
/* 137 */     this.roleService = roleService;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizservice.impl.AgencyImpl
 * JD-Core Version:    0.6.0
 */
 package com.ultrapower.workflow.bizservice.impl;
 
 import com.ultrapower.eoms.common.core.dao.IDao;
 import com.ultrapower.eoms.common.core.support.PageLimit;
 import com.ultrapower.eoms.common.core.util.TimeUtils;
 import com.ultrapower.workflow.bizservice.AgencyService;
 import com.ultrapower.workflow.bizservice.model.Agency;
 import com.ultrapower.workflow.role.model.RoleUser;
 import com.ultrapower.workflow.role.service.IRoleService;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 
 public class AgencyImpl
   implements AgencyService
 {
   private IDao<Agency> agencyDao;
   private IRoleService roleService;
 
   public void saveOrUpdate(Agency agency)
   {
     String bgDateStr = agency.getBgDateStr();
     String edDateStr = agency.getEdDateStr();
     if (StringUtils.isNotBlank(bgDateStr)) {
       agency.setBgDate(Long.valueOf(TimeUtils.formatDateStringToInt(bgDateStr)));
     }
     if (StringUtils.isNotBlank(edDateStr)) {
       agency.setEdDate(Long.valueOf(TimeUtils.formatDateStringToInt(edDateStr)));
     }
     if (StringUtils.isBlank(agency.getId())) {
       agency.setId(null);
       agency.setCreateTime(Long.valueOf(TimeUtils.getCurrentTime()));
     }
     this.agencyDao.saveOrUpdate(agency);
   }
 
   public void saveDudyAgency(String dealerLoginName, String dealerFullName, String agentLoginName, String agentFullName, long startDate, long endDate) {
     Agency agency = new Agency();
     agency.setDealerId(dealerLoginName);
     agency.setDealer(dealerFullName);
     agency.setAgentId(agentLoginName);
     agency.setAgent(agentFullName);
     agency.setBgDate(Long.valueOf(startDate));
     agency.setEdDate(Long.valueOf(endDate));
     agency.setCreateTime(Long.valueOf(TimeUtils.getCurrentTime()));
     this.agencyDao.save(agency);
   }
 
   public List<Agency> getAgencyListByAgentUser(String agentUserName, String baseSchema, String sourceID, String sourceType, int agentTime)
   {
     String queryAgentSql = "from Agency where baseSchema=? and bgDate<? and edDate>? and agentId=?";
     List<Agency> agentAllList = this.agencyDao.find(queryAgentSql, new Object[] { baseSchema, Long.valueOf(agentTime), Long.valueOf(agentTime), agentUserName });
 
     List sourceUserList = new ArrayList();
 
     if (sourceType.equals("USER"))
     {
       String[] sources = (String[])null;
       if (sourceID.indexOf(",") > -1)
       {
         sources = sourceID.split(",");
       }
       else
       {
         sources = new String[] { sourceID };
       }
       for (String u : sources)
       {
         sourceUserList.add(u);
       }
 
     }
     else if (sourceType.equals("ROLE"))
     {
       List<RoleUser> rUsers = this.roleService.getRoleUserByCroleID(sourceID);
       if (rUsers.size() > 0)
       {
         for (RoleUser rUser : rUsers)
         {
           sourceUserList.add(rUser.getLoginName());
         }
       }
 
     }
 
     List<Agency> agentFinalList = new ArrayList();
     for (Agency agency : agentAllList)
     {
       for (Iterator<String> iter = sourceUserList.iterator(); iter.hasNext(); ) { 
         String source = (String)iter.next();
         if (!source.equals(agency.getDealerId()))
           continue;
         agentFinalList.add(agency);
         break;
       }
     }
 
     return (List<Agency>)agentFinalList;
   }
 
   public void deleteById(String id) {
     this.agencyDao.removeById(id);
   }
 
   public List<Agency> getAll() {
     return this.agencyDao.getAll();
   }
 
   public List<Agency> getAllByPage()
   {
     return this.agencyDao.pagedQuery("from Agency ", PageLimit.getInstance(), null);
   }
 
   public List<Agency> getByUser(String loginName) {
     return this.agencyDao.pagedQuery("from Agency where dealerId='" + loginName + "'", PageLimit.getInstance(), null);
   }
 
   public Agency getById(String id) {
     return (Agency)this.agencyDao.get(id);
   }
 
   public IDao<Agency> getAgencyDao() {
     return this.agencyDao;
   }
 
   public void setAgencyDao(IDao<Agency> agencyDao) {
     this.agencyDao = agencyDao;
   }
 
   public IRoleService getRoleService() {
     return this.roleService;
   }
 
   public void setRoleService(IRoleService roleService) {
     this.roleService = roleService;
   }
 }

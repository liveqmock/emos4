/*     */ package com.ultrapower.workflow.role.service.impl;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.component.data.DataRow;
/*     */ import com.ultrapower.eoms.common.core.component.data.DataTable;
/*     */ import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.eoms.common.core.support.PageLimit;
/*     */ import com.ultrapower.eoms.ultrasm.model.DepInfo;
/*     */ import com.ultrapower.eoms.ultrasm.model.DicItem;
/*     */ import com.ultrapower.eoms.ultrasm.model.UserInfo;
/*     */ import com.ultrapower.eoms.ultrasm.service.DepManagerService;
/*     */ import com.ultrapower.eoms.ultrasm.service.DicManagerService;
/*     */ import com.ultrapower.eoms.ultrasm.service.UserManagerService;
/*     */ import com.ultrapower.remedy4j.core.RemedySession;
/*     */ import com.ultrapower.remedy4j.core.UtilInforHandler;
/*     */ import com.ultrapower.workflow.bizform.Dikaerji;
/*     */ import com.ultrapower.workflow.engine.core.model.DataField;
/*     */ import com.ultrapower.workflow.role.model.ChildRole;
/*     */ import com.ultrapower.workflow.role.model.Dimension;
/*     */ import com.ultrapower.workflow.role.model.DimensionValue;
/*     */ import com.ultrapower.workflow.role.model.RoleMatchDimension;
/*     */ import com.ultrapower.workflow.role.model.RoleUser;
/*     */ import com.ultrapower.workflow.role.model.UserRole;
/*     */ import com.ultrapower.workflow.role.model.WorkflowRole;
/*     */ import com.ultrapower.workflow.role.service.IRoleService;
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import com.ultrapower.workflow.utils.WfEngineConstants;
/*     */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.beanutils.BeanUtils;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.ArrayUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.hibernate.SessionFactory;
/*     */ import org.hibernate.classic.Session;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional
/*     */ public class RoleServiceImpl
/*     */   implements IRoleService
/*     */ {
/*  52 */   public static Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
/*     */   private IDao<Dimension> dimensionDao;
/*     */   private IDao<ChildRole> childRoleDao;
/*     */   private IDao<WorkflowRole> wfRoleDao;
/*     */   private IDao<UserRole> userRoleDao;
/*     */   private IDao<RoleMatchDimension> roleMatchDemensionDao;
/*     */   private IDao<RoleUser> roleUserDao;
/*     */   protected SessionFactory sessionFactory;
/*     */   private UserManagerService userManagerService;
/*     */   protected DepManagerService depManagerService;
/*     */   private DicManagerService dicManagerService;
/*     */ 
/*     */   public boolean saveOrUpdateRole(WorkflowRole wr)
/*     */   {
/*  68 */     this.wfRoleDao.saveOrUpdate(wr);
/*  69 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean saveOrUpdateDimension(Dimension dimension)
/*     */   {
/*  78 */     if ((dimension.getDimensionid() == null) || ("".equals(dimension.getDimensionid()))) {
/*  79 */       dimension.setDimensionid(UUIDGenerator.getId());
/*  80 */       this.dimensionDao.save(dimension);
/*     */     } else {
/*  82 */       this.dimensionDao.saveOrUpdate(dimension);
/*     */     }
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */   public Dimension getDimensionById(String id)
/*     */   {
/*  91 */     return (Dimension)this.dimensionDao.get(id);
/*     */   }
/*     */ 
/*     */   public List<Dimension> getDimensionBySchema(String schema)
/*     */   {
/*  98 */     if (StringUtils.isNotBlank(schema)) {
/*  99 */       return this.dimensionDao.find("from Dimension d where d.baseschema = ? order by baseschema", new Object[] { schema });
/*     */     }
/* 101 */     return this.dimensionDao.find("from Dimension d where d.baseschema is null order by baseschema", null);
/*     */   }
/*     */ 
/*     */   public void deleteDimensionById(String id)
/*     */   {
/* 110 */     Dimension dimension = (Dimension)this.dimensionDao.get(id);
/* 111 */     if (dimension != null) {
/* 112 */       this.dimensionDao.removeById(id);
/* 113 */       String sql = "delete from RoleMatchDimension where dimensioncode=?";
/* 114 */       this.roleMatchDemensionDao.executeUpdate(sql, new Object[] { dimension.getDimensioncode() });
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<ChildRole> getChildRoleListByRoleCode(String roleCode)
/*     */   {
/* 123 */     PageLimit pageLimit = PageLimit.getInstance();
/* 124 */     return this.childRoleDao.pagedQuery("from ChildRole c where c.roleCode = ?", pageLimit, new Object[] { roleCode });
/*     */   }
/*     */ 
/*     */   public List<ChildRole> getChildRoleFromTree(String roleCode, String childRoleName) {
/* 128 */     PageLimit pageLimit = PageLimit.getInstance();
/* 129 */     String hql = "from ChildRole c where c.roleCode = ?";
/* 130 */     if ((childRoleName != null) && (!"".equals(childRoleName)))
/* 131 */       hql = hql + " and childrolename like '%" + childRoleName + "%'";
/* 132 */     return this.childRoleDao.pagedQuery(hql, pageLimit, new Object[] { roleCode });
/*     */   }
/*     */ 
/*     */   public void saveOrUpdate(RoleUser roleUser)
/*     */   {
/* 139 */     this.roleUserDao.saveOrUpdate(roleUser);
/*     */   }
/*     */ 
/*     */   public void userRoleManage(RoleUser roleUser)
/*     */   {
/* 147 */     UserRole ur = (UserRole)this.userRoleDao.findUnique("from UserRole u where u.loginName=?", new Object[] { roleUser.getLoginName() });
/* 148 */     List<RoleUser> list = this.roleUserDao.find("from RoleUser r where r.loginName = ?", new Object[] { roleUser.getLoginName() });
/*     */ 
/* 150 */     StringBuffer sb = new StringBuffer();
/* 151 */     Map<String, String> map = new HashMap<String, String>();
/* 152 */     for (RoleUser ru : list) {
/* 153 */       map.put(ru.getChildRoleId(), ru.getChildRoleId());
/*     */     }
/*     */ 
/* 156 */     for (String key : map.keySet()) {
/* 157 */       sb.append("," + key);
/*     */     }
/* 159 */     if ("".equals(sb.toString())) {
/* 160 */       if (ur != null)
/* 161 */         this.userRoleDao.remove(ur);
/*     */     }
/*     */     else {
/* 164 */       if (ur == null) {
/* 165 */         ur = new UserRole();
/*     */         try {
/* 167 */           BeanUtils.copyProperties(ur, roleUser);
/*     */         } catch (IllegalAccessException e) {
/* 169 */           e.printStackTrace();
/*     */         } catch (InvocationTargetException e) {
/* 171 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 174 */       ur.setChildRoleId(sb.toString().substring(1));
/* 175 */       this.userRoleDao.save(ur);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteRoleUserById(String id)
/*     */   {
/* 184 */     RoleUser ur = (RoleUser)this.roleUserDao.get(id);
/* 185 */     this.roleUserDao.removeById(id);
/*     */   }
/*     */ 
/*     */   public void saveChildRole(ChildRole childRole)
/*     */   {
/* 193 */     this.childRoleDao.saveOrUpdate(childRole);
/*     */   }
/*     */ 
/*     */   public void removeChildRoleById(String id)
/*     */   {
/* 200 */     if (id != null) { List roleUserList = this.roleUserDao.find("from RoleUser r where r.childRoleId = ?", new Object[] { id });
/* 202 */       String sql = "delete from RoleUser u where u.childRoleId= ?";
/* 203 */       this.roleUserDao.executeUpdate(sql, new Object[] { id });
/*     */ 
/* 206 */       String sql2 = "delete from ChildRole c where c.childRoleId = ?";
/* 207 */       this.childRoleDao.executeUpdate(sql2, new Object[] { id });
/*     */       RoleUser localRoleUser;
/* 209 */       for (Iterator localIterator = roleUserList.iterator(); localIterator.hasNext(); localRoleUser = (RoleUser)localIterator.next());
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<WorkflowRole> getWfRoleBySchema(String schema) {
/* 219 */     String hql = "from WorkflowRole w where w.baseversion = ?";
/* 220 */     return this.wfRoleDao.find(hql, new Object[] { schema });
/*     */   }
/*     */ 
/*     */   public void deleteAllRoleMatchConditionByRoleCode(String roleCode)
/*     */   {
/* 227 */     String sql = "delete from RoleMatchDimension u where u.rolecode = ?";
/* 228 */     this.roleMatchDemensionDao.executeUpdate(sql, new Object[] { roleCode });
/*     */   }
/*     */ 
/*     */   public void saveRoleMatchCondition(RoleMatchDimension roleMatchConditions)
/*     */   {
/* 234 */     this.roleMatchDemensionDao.save(roleMatchConditions);
/*     */   }
/*     */ 
/*     */   public List<Dimension> getDimensionIds(String version, String processNo)
/*     */   {
/* 241 */     List rtn = new ArrayList();
/* 242 */     WorkflowRole wfRole = getWorkflowRoleByVersionAndProcessNo(version, processNo);
/* 243 */     if (wfRole != null) {
/* 244 */       List<RoleMatchDimension> list = getRoleMatchDemensionByRoleCode(wfRole.getRolecode());
/* 245 */       if (CollectionUtils.isNotEmpty(list)) {
/* 246 */         for (RoleMatchDimension r : list) {
/* 247 */           Dimension d = getDimensionByCode(r.getDimensioncode());
/* 248 */           if (d != null) {
/* 249 */             rtn.add(d);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 254 */     return rtn;
/*     */   }
/*     */ 
/*     */   public ChildRole getChildRoleByDim(String version, String phaseNo, String dimensionValue)
/*     */   {
/* 261 */     WorkflowRole wfRole = getWorkflowRoleByVersionAndProcessNo(version, phaseNo);
/* 262 */     if (wfRole != null) {
/* 263 */       String roleCode = wfRole.getRolecode();
/* 264 */       ChildRole chiRole = getChildRoleByValue(roleCode, dimensionValue);
/* 265 */       if (chiRole != null)
/*     */       {
/* 269 */         setChildRoleUsers(chiRole);
/* 270 */         return chiRole;
/*     */       }
/*     */     }
/* 273 */     return null;
/*     */   }
/*     */ 
/*     */   public ChildRole getChildRoleByValue(String roleCode, String dimensionValues)
/*     */   {
/* 282 */     ChildRole chiRole = null;
/*     */ 
/* 284 */     if (WfEngineConstants.DATABASE_TYPE_ORACLE.equals(WfEngineConstants.DATABASE_TYPE)) {
/* 285 */       String sql = "from ChildRole c where roleCode = '" + roleCode + "' and '" + dimensionValues + "' like '%' || c.dimensionValue || '%' order by length(c.dimensionValue) desc";
/* 286 */       log.info("匹配角色细分sql = " + sql);
/* 287 */       List roles = this.childRoleDao.find(sql, new Object[0]);
/*     */ 
/* 289 */       if (CollectionUtils.isNotEmpty(roles))
/* 290 */         chiRole = (ChildRole)roles.get(0);
/*     */     }
/* 292 */     else if (WfEngineConstants.DATABASE_TYPE_DB2.equals(WfEngineConstants.DATABASE_TYPE)) {
/* 293 */       Connection conn = null;
/* 294 */       CallableStatement st = null;
/*     */       try
/*     */       {
/* 297 */         conn = this.sessionFactory.getCurrentSession().connection();
/* 298 */         st = conn.prepareCall("{call PROC_MATCHCHILDROLE(?,?,?,?,?,?,?,?)}");
/* 299 */         st.setString(1, roleCode);
/* 300 */         st.setString(2, dimensionValues);
/* 301 */         st.registerOutParameter(3, 12);
/* 302 */         st.registerOutParameter(4, 12);
/* 303 */         st.registerOutParameter(5, 12);
/* 304 */         st.registerOutParameter(6, 4);
/* 305 */         st.registerOutParameter(7, 12);
/* 306 */         st.registerOutParameter(8, 12);
/* 307 */         st.execute();
/* 308 */         if ((st.getString(3) != null) && (!st.getString(3).equals("")))
/*     */         {
/* 310 */           chiRole = new ChildRole();
/* 311 */           chiRole.setChildRoleId(st.getString(3));
/* 312 */           chiRole.setChildRoleName(st.getString(4));
/* 313 */           chiRole.setRoleCode(st.getString(5));
/* 314 */           chiRole.setMatchCount(Long.valueOf(st.getLong(6)));
/* 315 */           chiRole.setDimensionValue(st.getString(7));
/* 316 */           chiRole.setCharge(st.getString(8));
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 321 */         e.printStackTrace();
/*     */         try
/*     */         {
/* 327 */           st.close();
/* 328 */           conn.close();
/*     */         }
/*     */         catch (Exception e1)
/*     */         {
/* 332 */           e1.printStackTrace();
/*     */         }
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/* 327 */           st.close();
/* 328 */           conn.close();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 332 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 336 */     setChildRoleUsers(chiRole);
/* 337 */     return chiRole;
/*     */   }
/*     */ 
/*     */   public ChildRole getChildRoleById(String id) {
/* 341 */     ChildRole childRole = (ChildRole)this.childRoleDao.get(id);
/* 342 */     setChildRoleUsers(childRole);
/* 343 */     return childRole;
/*     */   }
/*     */ 
/*     */   public WorkflowRole getWorkflowRoleByVersionAndProcessNo(String version, String processNo) {
/* 347 */     String sql = "from WorkflowRole w where w.baseversion = '" + version + "' and w.phaseno like '%#" + processNo + "#%'";
/* 348 */     return (WorkflowRole)this.wfRoleDao.findUnique(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public WorkflowRole getWorkflowRoleById(String id) {
/* 352 */     return (WorkflowRole)this.wfRoleDao.get(id);
/*     */   }
/*     */ 
/*     */   public void deleteWorkflowRoleById(String id) {
/* 356 */     this.wfRoleDao.removeById(id);
/*     */   }
/*     */ 
/*     */   public boolean isBelongChildRole(String user, String chiRoleId)
/*     */   {
/* 366 */     boolean flag = false;
/* 367 */     String sql = "from RoleUser c where childRoleId = '" + chiRoleId + "' ";
/* 368 */     List roleUsers = this.roleUserDao.find(sql, new Object[0]);
/* 369 */     if (CollectionUtils.isNotEmpty(roleUsers)) {
/* 370 */       for (int i = 0; i < roleUsers.size(); i++) {
/* 371 */         RoleUser roleUser = (RoleUser)roleUsers.get(i);
/* 372 */         String depID = roleUser.getDepID();
/* 373 */         String loginName = roleUser.getLoginName();
/* 374 */         String groupIds = this.userManagerService.getGroupIdsByLoginName(user);
/* 375 */         if ((StringUtils.isNotBlank(loginName)) && (loginName.equals(user))) {
/* 376 */           flag = true;
/* 377 */           break;
/*     */         }
/* 379 */         if ((StringUtils.isNotBlank(groupIds)) && (StringUtils.isNotBlank(depID)) && (groupIds.indexOf(depID) > -1)) {
/* 380 */           flag = true;
/* 381 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 385 */     return flag;
/*     */   }
/*     */ 
/*     */   public List<RoleMatchDimension> getRoleMatchDemensionByRoleCode(String code)
/*     */   {
/* 394 */     String hql = "from RoleMatchDimension w where w.rolecode=? order by orderby";
/* 395 */     return this.roleMatchDemensionDao.find(hql, new Object[] { code });
/*     */   }
/*     */ 
/*     */   public Dimension getDimensionByCode(String dimensionCode)
/*     */   {
/* 404 */     return (Dimension)this.dimensionDao.findUnique("from Dimension d where d.dimensioncode=?", new Object[] { dimensionCode });
/*     */   }
/*     */ 
/*     */   public List<ChildRole> matchChildRole(String baseSchema, String defName, String tarStepCode, Map<String, DataField> inputs) {
/* 408 */     log.info("根据传入的维度值自动匹配角色细分，专门为接口建单提供！baseSchema=" + baseSchema + ",defName=" + defName + ",stepCode=" + tarStepCode);
/* 409 */     List chiList = new ArrayList();
/*     */     try {
/* 411 */       if (StringUtils.isBlank(defName)) {
/* 412 */         defName = WfEngineUtils.getDefVersionBySchema(baseSchema);
/*     */       }
/* 414 */       StringBuffer sb = new StringBuffer();
/* 415 */       List dimensions = getDimensionIds(defName, tarStepCode);
/* 416 */       if (CollectionUtils.isNotEmpty(dimensions)) {
/* 417 */         for (int i = 0; i < dimensions.size(); i++) {
/* 418 */           Dimension dim = (Dimension)dimensions.get(i);
/* 419 */           String filedCnName = dim.getDimensionname();
/* 420 */           String filedName = dim.getDimCode();
/* 421 */           String dictfieldid = dim.getDictfieldid();
/* 422 */           String dictschema = dim.getDictschema();
/* 423 */           String fieldid = dim.getFieldid();
/* 424 */           if ((StringUtils.isBlank(dictfieldid)) && (StringUtils.isBlank(dictschema))) {
/* 425 */             filedCnName = filedName;
/* 426 */             if (StringUtils.isNotBlank(fieldid)) {
/* 427 */               filedName = fieldid;
/*     */             }
/*     */           }
/* 430 */           String valueFromInput = WfEngineUtils.getValueFromInput(inputs, filedName);
/* 431 */           if (StringUtils.isNotBlank(valueFromInput)) {
/* 432 */             sb.append(filedCnName + "=" + valueFromInput + "%#");
/*     */           }
/*     */         }
/*     */       }
/* 436 */       if (StringUtils.isNotBlank(sb.toString())) {
/* 437 */         ChildRole chiRole = getChildRoleByDim(defName, tarStepCode, sb.toString());
/* 438 */         if (chiRole == null) {
/* 439 */           log.error("没有匹配到角色细分！");
/*     */         } else {
/* 441 */           chiList.add(chiRole);
/* 442 */           log.info("匹配到的角色细分 " + chiRole.getChildRoleName());
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 446 */       e.printStackTrace();
/*     */     }
/* 448 */     setChildRoleUsers(chiList);
/* 449 */     return chiList;
/*     */   }
/*     */ 
/*     */   public List<ChildRole> matchRole(String defName, String stepCode, String dimensionValue) {
/* 453 */     List chiList = new ArrayList();
/* 454 */     List totalList = new ArrayList();
/* 455 */     if (StringUtils.isNotBlank(dimensionValue)) {
/* 456 */       String[] dimStr = dimensionValue.split("%#");
/* 457 */       if (!ArrayUtils.isEmpty(dimStr)) {
/* 458 */         for (int i = 0; i < dimStr.length; i++) {
/* 459 */           List dimValueList = new ArrayList();
/* 460 */           String[] dimValues = dimStr[i].split("=");
/* 461 */           if ((!ArrayUtils.isEmpty(dimValues)) && (dimValues.length == 2)) {
/* 462 */             String dimName = dimValues[0];
/* 463 */             if (StringUtils.isNotBlank(dimValues[1])) {
/* 464 */               String[] vals = dimValues[1].split(";");
/* 465 */               for (int j = 0; j < vals.length; j++) {
/* 466 */                 dimValueList.add(dimName + "=" + vals[j]);
/*     */               }
/*     */             }
/* 469 */             totalList.add(dimValueList);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 474 */     List result = Dikaerji.dikaerji(totalList);
/* 475 */     if (CollectionUtils.isNotEmpty(result)) {
/* 476 */       for (int i = 0; i < result.size(); i++) {
/* 477 */         ChildRole chiRole = getChildRoleByDim(defName, stepCode, (String)result.get(i));
/* 478 */         if (chiRole != null) {
/* 479 */           chiList.add(chiRole);
/*     */         }
/*     */       }
/*     */     }
/* 483 */     setChildRoleUsers(chiList);
/* 484 */     return chiList;
/*     */   }
/*     */ 
/*     */   public List<RoleUser> getRoleUserByHql(String hql) {
/* 488 */     return this.roleUserDao.find(hql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List<RoleUser> getRoleUserByCroleID(String croleId) {
/* 492 */     return this.roleUserDao.find("from RoleUser r where r.childRoleId = ? order by r.fullName", new Object[] { croleId });
/*     */   }
/*     */ 
/*     */   public IDao<Dimension> getDimensionDao() {
/* 496 */     return this.dimensionDao;
/*     */   }
/*     */ 
/*     */   public void setDimensionDao(IDao<Dimension> dimensionDao) {
/* 500 */     this.dimensionDao = dimensionDao;
/*     */   }
/*     */ 
/*     */   public IDao<ChildRole> getChildRoleDao() {
/* 504 */     return this.childRoleDao;
/*     */   }
/*     */ 
/*     */   public void setChildRoleDao(IDao<ChildRole> childRoleDao) {
/* 508 */     this.childRoleDao = childRoleDao;
/*     */   }
/*     */ 
/*     */   public IDao<WorkflowRole> getWfRoleDao() {
/* 512 */     return this.wfRoleDao;
/*     */   }
/*     */ 
/*     */   public void setWfRoleDao(IDao<WorkflowRole> wfRoleDao) {
/* 516 */     this.wfRoleDao = wfRoleDao;
/*     */   }
/*     */ 
/*     */   public IDao<RoleMatchDimension> getRoleMatchDemensionDao() {
/* 520 */     return this.roleMatchDemensionDao;
/*     */   }
/*     */ 
/*     */   public void setRoleMatchDemensionDao(IDao<RoleMatchDimension> roleMatchDemensionDao)
/*     */   {
/* 525 */     this.roleMatchDemensionDao = roleMatchDemensionDao;
/*     */   }
/*     */ 
/*     */   public IDao<RoleUser> getRoleUserDao() {
/* 529 */     return this.roleUserDao;
/*     */   }
/*     */ 
/*     */   public void setRoleUserDao(IDao<RoleUser> roleUserDao) {
/* 533 */     this.roleUserDao = roleUserDao;
/*     */   }
/*     */ 
/*     */   public SessionFactory getSessionFactory()
/*     */   {
/* 538 */     return this.sessionFactory;
/*     */   }
/*     */ 
/*     */   public void setSessionFactory(SessionFactory sessionFactory)
/*     */   {
/* 543 */     this.sessionFactory = sessionFactory;
/*     */   }
/*     */ 
/*     */   public void delWfRoleByRoleCode(String roleCode, String defName)
/*     */   {
/* 548 */     String sql = "delete from WorkflowRole where rolecode=? ";
/* 549 */     if (StringUtils.isNotBlank(defName)) {
/* 550 */       sql = sql + " and baseversion=?";
/* 551 */       this.wfRoleDao.executeUpdate(sql, new Object[] { roleCode, defName });
/*     */     } else {
/* 553 */       this.wfRoleDao.executeUpdate(sql, new Object[] { roleCode });
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<ChildRole> getChildRoleByHql(String hql) {
/* 558 */     List find = this.childRoleDao.find(hql, new Object[0]);
/* 559 */     setChildRoleUsers(find);
/* 560 */     return find;
/*     */   }
/*     */ 
/*     */   public List<ChildRole> getChildRoleByValue(String dimensionValues) {
/* 564 */     String sql = "from ChildRole c where c.dimensionValue like '%" + dimensionValues + "%'";
/* 565 */     List find = this.childRoleDao.find(sql, new Object[0]);
/* 566 */     setChildRoleUsers(find);
/* 567 */     return find;
/*     */   }
/*     */ 
/*     */   public List<DimensionValue> getDimensionValue(Dimension dimension) {
/* 571 */     List list = new ArrayList();
/* 572 */     if (dimension != null)
/*     */     {
/* 574 */       String dimCode = dimension.getDimCode();
/* 575 */       String dimensionname = dimension.getDimensionname();
/* 576 */       String dictfieldid = dimension.getDictfieldid();
/* 577 */       String dictschema = dimension.getDictschema();
/*     */       int i;
/* 579 */       if ("remedy".equals(dimension.getDimensiontype())) {
/* 580 */         String tablename = RemedySession.UtilInfor.getTableName(dimension.getDictschema());
/* 581 */         String sql = "select " + dimension.getDictfieldid() + " as code from " + tablename;
/* 582 */         QueryAdapter queryAdapter = new QueryAdapter();
/* 583 */         DataTable table = queryAdapter.executeQuery(sql, null);
/* 584 */         if (table != null)
/*     */         {
/* 586 */           for (i = 0; i < table.length(); i++) {
/* 587 */             DataRow row = table.getDataRow(i);
/* 588 */             DimensionValue d = new DimensionValue();
/* 589 */             String code = row.getString("code");
/* 590 */             d.setValue(code);
/* 591 */             d.setCode(code);
/* 592 */             list.add(d);
/*     */           }
/*     */         }
/*     */       }
/* 596 */       if ("sysdic".equals(dimension.getDimensiontype())) {
/* 597 */         String tablename = "bs_t_sm_dicitem";
/* 598 */         String dtcode = dimension.getDtcode();
/* 599 */         String divalue = dimension.getDivalue();
/*     */ 
/* 619 */         List<DicItem> items = this.dicManagerService.getAllSubDicItemByFullName(dtcode, divalue);
/*     */ 
/* 621 */         for (DicItem item : items)
/*     */         {
/* 623 */           String key = item.getDivalue();
/* 624 */           String value = item.getDicfullname();
/* 625 */           if ((divalue != null) && (divalue.length() > 0)) {
/* 626 */             value = value.replaceFirst(divalue + ".", "");
/*     */           }
/* 628 */           DimensionValue d = new DimensionValue();
/* 629 */           d.setCode(value);
/* 630 */           d.setValue(value);
/* 631 */           list.add(d);
/*     */         }
/*     */       }
/* 634 */       if ("table".equals(dimension.getDimensiontype())) {
/* 635 */         String tablename = dimension.getTablename();
/* 636 */         String tablecol = dimension.getTablecol();
/* 637 */         String customSql = dimension.getCustomSql();
/* 638 */         if (StringUtils.isNotBlank(customSql)) {
/* 639 */           QueryAdapter queryAdapter = new QueryAdapter();
/* 640 */           DataTable table = queryAdapter.executeQuery(customSql, null);
/* 641 */           if (table != null)
/*     */           {
/* 643 */             for (int j = 0; j < table.length(); j++) {
/* 644 */               DataRow row = table.getDataRow(j);
/* 645 */               DimensionValue d = new DimensionValue();
/* 646 */               d.setCode(row.getString(0));
/* 647 */               d.setValue(row.getString(1));
/* 648 */               list.add(d);
/*     */             }
/*     */           }
/*     */         } else {
/* 652 */           String sql = "select " + tablecol + " from " + tablename;
/* 653 */           QueryAdapter queryAdapter = new QueryAdapter();
/* 654 */           DataTable table = queryAdapter.executeQuery(sql, null);
/* 655 */           if (table != null)
/*     */           {
/* 657 */             for (int k = 0; k < table.length(); k++) {
/* 658 */               DataRow row = table.getDataRow(k);
/* 659 */               DimensionValue d = new DimensionValue();
/* 660 */               d.setCode(row.getString(tablecol));
/* 661 */               d.setValue(row.getString(tablecol));
/* 662 */               list.add(d);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 668 */     return list;
/*     */   }
/*     */ 
/*     */   public List<WorkflowRole> getRoleByVersionCode(String versionCode) {
/* 672 */     String sql = "from WorkflowRole w where w.baseversion = ?";
/* 673 */     return this.wfRoleDao.find(sql, new Object[] { versionCode });
/*     */   }
/*     */ 
/*     */   public WorkflowRole getWfRoleByCode(String code) {
/* 677 */     String hql = "from WorkflowRole w where w.rolecode=?";
/* 678 */     List list = this.wfRoleDao.find(hql, new Object[] { code });
/* 679 */     return (list == null) || (list.size() == 0) ? null : (WorkflowRole)list.get(0);
/*     */   }
/*     */ 
/*     */   public WorkflowRole getWfRoleByVersonAndProcessId(String version, String processsNo)
/*     */   {
/* 684 */     String sql = "from WorkflowRole w where w.baseversion = '" + version + "' and w.phaseno like '%#" + processsNo + "#%'";
/* 685 */     return (WorkflowRole)this.wfRoleDao.findUnique(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public IDao<UserRole> getUserRoleDao() {
/* 689 */     return this.userRoleDao;
/*     */   }
/*     */ 
/*     */   public void setUserRoleDao(IDao<UserRole> userRoleDao) {
/* 693 */     this.userRoleDao = userRoleDao;
/*     */   }
/*     */ 
/*     */   public UserManagerService getUserManagerService() {
/* 697 */     return this.userManagerService;
/*     */   }
/*     */ 
/*     */   public void setUserManagerService(UserManagerService userManagerService) {
/* 701 */     this.userManagerService = userManagerService;
/*     */   }
/*     */ 
/*     */   public DicManagerService getDicManagerService()
/*     */   {
/* 706 */     return this.dicManagerService;
/*     */   }
/*     */ 
/*     */   public void setDicManagerService(DicManagerService dicManagerService)
/*     */   {
/* 711 */     this.dicManagerService = dicManagerService;
/*     */   }
/*     */ 
/*     */   private void setChildRoleUsers(ChildRole chiRole) {
/* 715 */     if (chiRole != null) {
/* 716 */       String hql = "from RoleUser where childRoleId=?";
/* 717 */       List find = this.roleUserDao.find(hql, new Object[] { chiRole.getChildRoleId() });
/* 718 */       if (CollectionUtils.isNotEmpty(find))
/* 719 */         for (int i = 0; i < find.size(); i++) {
/* 720 */           RoleUser ru = (RoleUser)find.get(i);
/* 721 */           String loginName = ru.getLoginName();
/* 722 */           String depID = ru.getDepID();
/* 723 */           if (StringUtils.isNotBlank(loginName)) {
/* 724 */             UserInfo user = this.userManagerService.getUserByLoginName(loginName);
/* 725 */             chiRole.getUserList().add(user);
/*     */           }
/* 727 */           if (StringUtils.isNotBlank(depID)) {
/* 728 */             DepInfo dep = this.depManagerService.getDepByID(depID);
/* 729 */             chiRole.getDepList().add(dep);
/*     */           }
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setChildRoleUsers(List<ChildRole> chiRoleList)
/*     */   {
/* 737 */     if (CollectionUtils.isNotEmpty(chiRoleList))
/* 738 */       for (int i = 0; i < chiRoleList.size(); i++) {
/* 739 */         ChildRole chiRole = (ChildRole)chiRoleList.get(i);
/* 740 */         setChildRoleUsers(chiRole);
/*     */       }
/*     */   }
/*     */ 
/*     */   public DepManagerService getDepManagerService()
/*     */   {
/* 746 */     return this.depManagerService;
/*     */   }
/*     */ 
/*     */   public void setDepManagerService(DepManagerService depManagerService) {
/* 750 */     this.depManagerService = depManagerService;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.role.service.impl.RoleServiceImpl
 * JD-Core Version:    0.6.0
 */
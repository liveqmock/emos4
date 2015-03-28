/*     */ package com.ultrapower.workflow.engine.task;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.eoms.ultrasm.service.DepManagerService;
/*     */ import com.ultrapower.eoms.ultrasm.service.UserManagerService;
/*     */ import com.ultrapower.workflow.engine.core.model.ActionInfo;
/*     */ import com.ultrapower.workflow.engine.core.model.DataField;
/*     */ import com.ultrapower.workflow.engine.def.WorkflowFactory;
/*     */ import com.ultrapower.workflow.engine.def.model.StepDescriptor;
/*     */ import com.ultrapower.workflow.engine.store.model.Step;
/*     */ import com.ultrapower.workflow.engine.store.model.WfCurrentStep;
/*     */ import com.ultrapower.workflow.engine.store.model.WfEntry;
/*     */ import com.ultrapower.workflow.engine.store.model.WfHistoryStep;
/*     */ import com.ultrapower.workflow.engine.task.model.BaseTask;
/*     */ import com.ultrapower.workflow.exception.TaskException;
/*     */ import com.ultrapower.workflow.role.service.IRoleService;
/*     */ import com.ultrapower.workflow.utils.CommonUtils;
/*     */ import com.ultrapower.workflow.utils.ThreadUtils;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional
/*     */ public abstract class AbstractTaskManager<B extends BaseTask, C extends B, H extends B>
/*     */   implements ITaskManager<B, C, H>
/*     */ {
/*  33 */   Logger log = LoggerFactory.getLogger(AbstractTaskManager.class);
/*     */   protected IDao<C> currTaskDao;
/*     */   protected IDao<H> hisTaskDao;
/*     */   protected IDao<WfCurrentStep> currentDao;
/*     */   protected IDao<WfHistoryStep> historyDao;
/*     */   protected UserManagerService userManagerService;
/*     */   protected DepManagerService depManagerService;
/*     */   protected IRoleService roleService;
/*     */   protected IDao<WfEntry> entryDao;
/*     */   protected WorkflowFactory factory;
/*     */ 
/*     */   public B assignTask(String userId, B prevTask, Step step, StepDescriptor stDesc, ActionInfo acInfo, Map<String, DataField> inputs)
/*     */     throws TaskException
/*     */   {
/*  48 */     String actionType = acInfo.getActionType();
/*  49 */     if (StringUtils.isNotBlank(actionType)) {
/*  50 */       if ((actionType.equals("SAVE")) || 
/*  51 */         (actionType.equals("NEW")) || 
/*  52 */         (actionType.equals("ASSIGN")) || 
/*  53 */         (actionType.equals("REASSIGN")) || 
/*  54 */         (actionType.equals("APPENDASSIGN")) || 
/*  55 */         (actionType.equals("ASSIST")) || 
/*  56 */         (actionType.equals("MAKECOPY")) || 
/*  57 */         (actionType.equals("NEXT")) || 
/*  58 */         (actionType.equals("RENEXT")) || 
/*  59 */         (actionType.equals("AUDIT")) || 
/*  60 */         (actionType.equals("REAUDIT")) || 
/*  61 */         (actionType.equals("ORGANIZEAUDIT")))
/*  62 */         return assign(userId, prevTask, step, stDesc, acInfo, inputs);
/*  63 */       if ((actionType.equals("ACCEPT")) || (actionType.equals("NOTICE"))) {
/*  64 */         return accept(userId, prevTask, step, acInfo, inputs);
/*     */       }
/*  66 */       return customTask(userId, prevTask, step, acInfo, inputs);
/*     */     }
/*     */ 
/*  69 */     return null;
/*     */   }
/*     */ 
/*     */   protected B assign(String userId, B prevTask, Step step, StepDescriptor stDesc, ActionInfo acInfo, Map<String, DataField> inputs)
/*     */   {
/*  83 */     return createTask(prevTask, step, stDesc, acInfo, inputs);
/*     */   }
/*     */ 
/*     */   protected BaseTask accept(String userId, BaseTask bTask, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
/*     */   {
/*  96 */     throw new TaskException("受理方法必须由子类重写！");
/*     */   }
/*     */ 
/*     */   public void updateAllTaskEntryState(String topEntryId, String entryState)
/*     */     throws TaskException
/*     */   {
/* 103 */     String currClassName = CommonUtils.getSuperClassGenricType(getClass(), 1).getSimpleName();
/* 104 */     String hisClassName = CommonUtils.getSuperClassGenricType(getClass(), 2).getSimpleName();
/* 105 */     this.log.info("更新所有任务的工单状态！topEntryId=" + topEntryId);
/* 106 */     String conHql = " update " + currClassName + " set entryState = '" + entryState + "' where topEntryId = '" + topEntryId + "'";
/* 107 */     this.currTaskDao.executeUpdate(conHql, null);
/*     */ 
/* 109 */     conHql = " update " + hisClassName + " set entryState = '" + entryState + "' where topEntryId = '" + topEntryId + "'";
/* 110 */     this.hisTaskDao.executeUpdate(conHql, null);
/*     */   }
/*     */ 
/*     */   public void cancelAuditTask(WfCurrentStep curStep, BaseTask curTask)
/*     */   {
/* 133 */     throw new TaskException("该方法必须由子类重写！");
/*     */   }
/*     */ 
/*     */   protected abstract B createTask(B paramB, Step paramStep, StepDescriptor paramStepDescriptor, ActionInfo paramActionInfo, Map<String, DataField> paramMap);
/*     */ 
/*     */   public abstract void updateBizProperties(C paramC, Map<String, DataField> paramMap);
/*     */ 
/*     */   protected B customTask(String userId, B prevTask, Step step, ActionInfo acInfo, Map<String, DataField> inputs) throws TaskException
/*     */   {
/* 144 */     throw new TaskException("任务管理器没有匹配的执行动作！actionType=" + acInfo.getActionType());
/*     */   }
/*     */ 
/*     */   public C getCurrentTaskById(String taskId) throws TaskException {
/* 148 */     return (BaseTask)this.currTaskDao.get(taskId);
/*     */   }
/*     */ 
/*     */   public H getHistoryTaskById(String taskId) throws TaskException {
/* 152 */     return (BaseTask)this.hisTaskDao.get(taskId);
/*     */   }
/*     */ 
/*     */   public C saveCurrentTask(C currTask) throws TaskException {
/* 156 */     this.currTaskDao.save(currTask);
/* 157 */     ThreadUtils.addNewTask(currTask);
/* 158 */     return currTask;
/*     */   }
/*     */ 
/*     */   public H saveHistoryTask(H hisTask) throws TaskException {
/* 162 */     this.hisTaskDao.save(hisTask);
/* 163 */     ThreadUtils.addOldTask(hisTask);
/* 164 */     return hisTask;
/*     */   }
/*     */ 
/*     */   public C updateCurrentTask(C task) throws TaskException {
/* 168 */     this.currTaskDao.saveOrUpdate(task);
/* 169 */     return task;
/*     */   }
/*     */ 
/*     */   public H updateHistoryTask(H task) throws TaskException {
/* 173 */     this.hisTaskDao.saveOrUpdate(task);
/* 174 */     return task;
/*     */   }
/*     */ 
/*     */   public boolean deleteCurrentTaskById(String taskId) throws TaskException {
/* 178 */     this.currTaskDao.removeById(taskId);
/* 179 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean deleteHistoryTaskById(String taskId) throws TaskException {
/* 183 */     this.hisTaskDao.removeById(taskId);
/* 184 */     return true;
/*     */   }
/*     */ 
/*     */   public void moveToHistory(C task) throws TaskException {
/* 188 */     BaseTask history = task.toHistory();
/* 189 */     this.hisTaskDao.save(history);
/* 190 */     ThreadUtils.addOldTask(history);
/* 191 */     this.currTaskDao.remove(task);
/* 192 */     ThreadUtils.removeNewTask(task);
/*     */   }
/*     */ 
/*     */   public C moveToCurrent(H task) throws TaskException {
/* 196 */     this.hisTaskDao.remove(task);
/* 197 */     ThreadUtils.removeOldTask(task);
/* 198 */     BaseTask current = task.toCurrent();
/* 199 */     current.setFinishTime(0L);
/* 200 */     this.currTaskDao.save(current);
/* 201 */     ThreadUtils.addNewTask(current);
/* 202 */     return current;
/*     */   }
/*     */ 
/*     */   public List<C> queryCurrentTask(String hql) throws TaskException
/*     */   {
/* 207 */     return this.currTaskDao.find(hql, null);
/*     */   }
/*     */ 
/*     */   public List<H> queryHistoryTask(String hql) throws TaskException {
/* 211 */     return this.hisTaskDao.find(hql, null);
/*     */   }
/*     */ 
/*     */   public C getCurrentTaskByStepId(String stepId) throws TaskException {
/* 215 */     String currClassName = CommonUtils.getSuperClassGenricType(getClass(), 1).getSimpleName();
/* 216 */     String hql = "from " + currClassName + " where stepId = '" + stepId + "'";
/* 217 */     List queryList = this.currTaskDao.find(hql, null);
/* 218 */     if (CollectionUtils.isNotEmpty(queryList)) {
/* 219 */       if (queryList.size() == 1) {
/* 220 */         return (BaseTask)queryList.get(0);
/*     */       }
/* 222 */       throw new TaskException("待办任务表中stepId=" + stepId + "的任务有多条！");
/*     */     }
/* 224 */     throw new TaskException("待办任务表中stepId=" + stepId + "的任务不存在 ！");
/*     */   }
/*     */ 
/*     */   public H getHistoryTaskByStepId(String stepId) throws TaskException
/*     */   {
/* 229 */     String hisClassName = CommonUtils.getSuperClassGenricType(getClass(), 2).getSimpleName();
/* 230 */     String hql = "from " + hisClassName + " where stepId = '" + stepId + "'";
/* 231 */     List queryList = this.hisTaskDao.find(hql, null);
/* 232 */     if (CollectionUtils.isNotEmpty(queryList)) {
/* 233 */       if (queryList.size() == 1) {
/* 234 */         return (BaseTask)queryList.get(0);
/*     */       }
/* 236 */       throw new TaskException("已办任务表中stepId=" + stepId + "的任务有多条！");
/*     */     }
/* 238 */     throw new TaskException("已办任务表中stepId=" + stepId + "的任务不存在 ！");
/*     */   }
/*     */ 
/*     */   public BaseTask getTaskById(String taskId) {
/* 242 */     BaseTask baseTask = getCurrentTaskById(taskId);
/* 243 */     if (baseTask == null) {
/* 244 */       baseTask = getHistoryTaskById(taskId);
/*     */     }
/* 246 */     return baseTask;
/*     */   }
/*     */ 
/*     */   public IDao<C> getCurrTaskDao() {
/* 250 */     return this.currTaskDao;
/*     */   }
/*     */ 
/*     */   public void setCurrTaskDao(IDao<C> currTaskDao) {
/* 254 */     this.currTaskDao = currTaskDao;
/*     */   }
/*     */ 
/*     */   public IDao<H> getHisTaskDao() {
/* 258 */     return this.hisTaskDao;
/*     */   }
/*     */ 
/*     */   public void setHisTaskDao(IDao<H> hisTaskDao) {
/* 262 */     this.hisTaskDao = hisTaskDao;
/*     */   }
/*     */ 
/*     */   public IDao<WfCurrentStep> getCurrentDao() {
/* 266 */     return this.currentDao;
/*     */   }
/*     */ 
/*     */   public void setCurrentDao(IDao<WfCurrentStep> currentDao) {
/* 270 */     this.currentDao = currentDao;
/*     */   }
/*     */ 
/*     */   public IDao<WfHistoryStep> getHistoryDao() {
/* 274 */     return this.historyDao;
/*     */   }
/*     */ 
/*     */   public void setHistoryDao(IDao<WfHistoryStep> historyDao) {
/* 278 */     this.historyDao = historyDao;
/*     */   }
/*     */ 
/*     */   public UserManagerService getUserManagerService() {
/* 282 */     return this.userManagerService;
/*     */   }
/*     */ 
/*     */   public void setUserManagerService(UserManagerService userManagerService) {
/* 286 */     this.userManagerService = userManagerService;
/*     */   }
/*     */ 
/*     */   public DepManagerService getDepManagerService() {
/* 290 */     return this.depManagerService;
/*     */   }
/*     */ 
/*     */   public void setDepManagerService(DepManagerService depManagerService) {
/* 294 */     this.depManagerService = depManagerService;
/*     */   }
/*     */ 
/*     */   public IDao<WfEntry> getEntryDao() {
/* 298 */     return this.entryDao;
/*     */   }
/*     */ 
/*     */   public void setEntryDao(IDao<WfEntry> entryDao) {
/* 302 */     this.entryDao = entryDao;
/*     */   }
/*     */ 
/*     */   public IRoleService getRoleService() {
/* 306 */     return this.roleService;
/*     */   }
/*     */ 
/*     */   public void setRoleService(IRoleService roleService) {
/* 310 */     this.roleService = roleService;
/*     */   }
/*     */ 
/*     */   public WorkflowFactory getFactory()
/*     */   {
/* 318 */     return this.factory;
/*     */   }
/*     */ 
/*     */   public void setFactory(WorkflowFactory factory)
/*     */   {
/* 326 */     this.factory = factory;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.task.AbstractTaskManager
 * JD-Core Version:    0.6.0
 */
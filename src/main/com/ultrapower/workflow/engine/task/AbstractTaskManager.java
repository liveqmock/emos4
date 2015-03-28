 package com.ultrapower.workflow.engine.task;
 
 import com.ultrapower.eoms.common.core.dao.IDao;
 import com.ultrapower.eoms.ultrasm.service.DepManagerService;
 import com.ultrapower.eoms.ultrasm.service.UserManagerService;
 import com.ultrapower.workflow.engine.core.model.ActionInfo;
 import com.ultrapower.workflow.engine.core.model.DataField;
 import com.ultrapower.workflow.engine.def.WorkflowFactory;
 import com.ultrapower.workflow.engine.def.model.StepDescriptor;
 import com.ultrapower.workflow.engine.store.model.Step;
 import com.ultrapower.workflow.engine.store.model.WfCurrentStep;
 import com.ultrapower.workflow.engine.store.model.WfEntry;
 import com.ultrapower.workflow.engine.store.model.WfHistoryStep;
 import com.ultrapower.workflow.engine.task.model.BaseTask;
 import com.ultrapower.workflow.exception.TaskException;
 import com.ultrapower.workflow.role.service.IRoleService;
 import com.ultrapower.workflow.utils.CommonUtils;
 import com.ultrapower.workflow.utils.ThreadUtils;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.collections.CollectionUtils;
 import org.apache.commons.lang.StringUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.transaction.annotation.Transactional;
 
 @Transactional
 public abstract class AbstractTaskManager<B extends BaseTask, C extends B, H extends B>
   implements ITaskManager<B, C, H>
 {
   Logger log = LoggerFactory.getLogger(AbstractTaskManager.class);
   protected IDao<C> currTaskDao;
   protected IDao<H> hisTaskDao;
   protected IDao<WfCurrentStep> currentDao;
   protected IDao<WfHistoryStep> historyDao;
   protected UserManagerService userManagerService;
   protected DepManagerService depManagerService;
   protected IRoleService roleService;
   protected IDao<WfEntry> entryDao;
   protected WorkflowFactory factory;
 
   public B assignTask(String userId, B prevTask, Step step, StepDescriptor stDesc, ActionInfo acInfo, Map<String, DataField> inputs)
     throws TaskException
   {
     String actionType = acInfo.getActionType();
     if (StringUtils.isNotBlank(actionType)) {
       if ((actionType.equals("SAVE")) || 
         (actionType.equals("NEW")) || 
         (actionType.equals("ASSIGN")) || 
         (actionType.equals("REASSIGN")) || 
         (actionType.equals("APPENDASSIGN")) || 
         (actionType.equals("ASSIST")) || 
         (actionType.equals("MAKECOPY")) || 
         (actionType.equals("NEXT")) || 
         (actionType.equals("RENEXT")) || 
         (actionType.equals("AUDIT")) || 
         (actionType.equals("REAUDIT")) || 
         (actionType.equals("ORGANIZEAUDIT")))
         return assign(userId, prevTask, step, stDesc, acInfo, inputs);
       if ((actionType.equals("ACCEPT")) || (actionType.equals("NOTICE"))) {
         return (B) accept(userId, prevTask, step, acInfo, inputs);
       }
       return customTask(userId, prevTask, step, acInfo, inputs);
     }
 
     return null;
   }
 
   protected B assign(String userId, B prevTask, Step step, StepDescriptor stDesc, ActionInfo acInfo, Map<String, DataField> inputs)
   {
     return createTask(prevTask, step, stDesc, acInfo, inputs);
   }
 
   protected BaseTask accept(String userId, BaseTask bTask, Step step, ActionInfo acInfo, Map<String, DataField> inputs)
   {
     throw new TaskException("受理方法必须由子类重写！");
   }
 
   public void updateAllTaskEntryState(String topEntryId, String entryState)
     throws TaskException
   {
     String currClassName = CommonUtils.getSuperClassGenricType(getClass(), 1).getSimpleName();
     String hisClassName = CommonUtils.getSuperClassGenricType(getClass(), 2).getSimpleName();
     this.log.info("更新所有任务的工单状态！topEntryId=" + topEntryId);
     String conHql = " update " + currClassName + " set entryState = '" + entryState + "' where topEntryId = '" + topEntryId + "'";
     this.currTaskDao.executeUpdate(conHql, null);
 
     conHql = " update " + hisClassName + " set entryState = '" + entryState + "' where topEntryId = '" + topEntryId + "'";
     this.hisTaskDao.executeUpdate(conHql, null);
   }
 
   public void cancelAuditTask(WfCurrentStep curStep, BaseTask curTask)
   {
     throw new TaskException("该方法必须由子类重写！");
   }
 
   protected abstract B createTask(B paramB, Step paramStep, StepDescriptor paramStepDescriptor, ActionInfo paramActionInfo, Map<String, DataField> paramMap);
 
   public abstract void updateBizProperties(C paramC, Map<String, DataField> paramMap);
 
   protected B customTask(String userId, B prevTask, Step step, ActionInfo acInfo, Map<String, DataField> inputs) throws TaskException
   {
     throw new TaskException("任务管理器没有匹配的执行动作！actionType=" + acInfo.getActionType());
   }
 
   public C getCurrentTaskById(String taskId) throws TaskException {
     return (C)this.currTaskDao.get(taskId);
   }
 
   public H getHistoryTaskById(String taskId) throws TaskException {
     return (H)this.hisTaskDao.get(taskId);
   }
 
   public C saveCurrentTask(C currTask) throws TaskException {
     this.currTaskDao.save(currTask);
     ThreadUtils.addNewTask(currTask);
     return currTask;
   }
 
   public H saveHistoryTask(H hisTask) throws TaskException {
     this.hisTaskDao.save(hisTask);
     ThreadUtils.addOldTask(hisTask);
     return hisTask;
   }
 
   public C updateCurrentTask(C task) throws TaskException {
     this.currTaskDao.saveOrUpdate(task);
     return task;
   }
 
   public H updateHistoryTask(H task) throws TaskException {
     this.hisTaskDao.saveOrUpdate(task);
     return task;
   }
 
   public boolean deleteCurrentTaskById(String taskId) throws TaskException {
     this.currTaskDao.removeById(taskId);
     return true;
   }
 
   public boolean deleteHistoryTaskById(String taskId) throws TaskException {
     this.hisTaskDao.removeById(taskId);
     return true;
   }
 
   public void moveToHistory(C task) throws TaskException {
     BaseTask history = task.toHistory();
     this.hisTaskDao.save((H) history);
     ThreadUtils.addOldTask(history);
     this.currTaskDao.remove(task);
     ThreadUtils.removeNewTask(task);
   }
 
   public C moveToCurrent(H task) throws TaskException {
     this.hisTaskDao.remove(task);
     ThreadUtils.removeOldTask(task);
     BaseTask current = task.toCurrent();
     current.setFinishTime(0L);
     this.currTaskDao.save((C) current);
     ThreadUtils.addNewTask(current);
     return (C) current;
   }
 
   public List<C> queryCurrentTask(String hql) throws TaskException
   {
     return this.currTaskDao.find(hql, null);
   }
 
   public List<H> queryHistoryTask(String hql) throws TaskException {
     return this.hisTaskDao.find(hql, null);
   }
 
   public C getCurrentTaskByStepId(String stepId) throws TaskException {
     String currClassName = CommonUtils.getSuperClassGenricType(getClass(), 1).getSimpleName();
     String hql = "from " + currClassName + " where stepId = '" + stepId + "'";
     List queryList = this.currTaskDao.find(hql, null);
     if (CollectionUtils.isNotEmpty(queryList)) {
       if (queryList.size() == 1) {
         return (C)queryList.get(0);
       }
       throw new TaskException("待办任务表中stepId=" + stepId + "的任务有多条！");
     }
     throw new TaskException("待办任务表中stepId=" + stepId + "的任务不存在 ！");
   }
 
   public H getHistoryTaskByStepId(String stepId) throws TaskException
   {
     String hisClassName = CommonUtils.getSuperClassGenricType(getClass(), 2).getSimpleName();
     String hql = "from " + hisClassName + " where stepId = '" + stepId + "'";
     List queryList = this.hisTaskDao.find(hql, null);
     if (CollectionUtils.isNotEmpty(queryList)) {
       if (queryList.size() == 1) {
         return (H)queryList.get(0);
       }
       throw new TaskException("已办任务表中stepId=" + stepId + "的任务有多条！");
     }
     throw new TaskException("已办任务表中stepId=" + stepId + "的任务不存在 ！");
   }
 
   public BaseTask getTaskById(String taskId) {
     BaseTask baseTask = getCurrentTaskById(taskId);
     if (baseTask == null) {
       baseTask = getHistoryTaskById(taskId);
     }
     return baseTask;
   }
 
   public IDao<C> getCurrTaskDao() {
     return this.currTaskDao;
   }
 
   public void setCurrTaskDao(IDao<C> currTaskDao) {
     this.currTaskDao = currTaskDao;
   }
 
   public IDao<H> getHisTaskDao() {
     return this.hisTaskDao;
   }
 
   public void setHisTaskDao(IDao<H> hisTaskDao) {
     this.hisTaskDao = hisTaskDao;
   }
 
   public IDao<WfCurrentStep> getCurrentDao() {
     return this.currentDao;
   }
 
   public void setCurrentDao(IDao<WfCurrentStep> currentDao) {
     this.currentDao = currentDao;
   }
 
   public IDao<WfHistoryStep> getHistoryDao() {
     return this.historyDao;
   }
 
   public void setHistoryDao(IDao<WfHistoryStep> historyDao) {
     this.historyDao = historyDao;
   }
 
   public UserManagerService getUserManagerService() {
     return this.userManagerService;
   }
 
   public void setUserManagerService(UserManagerService userManagerService) {
     this.userManagerService = userManagerService;
   }
 
   public DepManagerService getDepManagerService() {
     return this.depManagerService;
   }
 
   public void setDepManagerService(DepManagerService depManagerService) {
     this.depManagerService = depManagerService;
   }
 
   public IDao<WfEntry> getEntryDao() {
     return this.entryDao;
   }
 
   public void setEntryDao(IDao<WfEntry> entryDao) {
     this.entryDao = entryDao;
   }
 
   public IRoleService getRoleService() {
     return this.roleService;
   }
 
   public void setRoleService(IRoleService roleService) {
     this.roleService = roleService;
   }
 
   public WorkflowFactory getFactory()
   {
     return this.factory;
   }
 
   public void setFactory(WorkflowFactory factory)
   {
     this.factory = factory;
   }
 }


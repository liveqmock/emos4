 package com.ultrapower.workflow.utils;
 
 import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
 import com.ultrapower.eoms.common.core.dao.IDao;
 import com.ultrapower.eoms.common.core.util.TimeUtils;
 import com.ultrapower.eoms.ultrasm.model.DepInfo;
 import com.ultrapower.eoms.ultrasm.model.UserInfo;
 import com.ultrapower.workflow.bizform.BizFormUtil;
 import com.ultrapower.workflow.bizform.model.DealProcessLogModel;
 import com.ultrapower.workflow.bizform.model.DealProcessModel;
 import com.ultrapower.workflow.bizform.model.Notice;
 import com.ultrapower.workflow.configuration.sort.model.WfType;
 import com.ultrapower.workflow.engine.core.ICondition;
 import com.ultrapower.workflow.engine.core.IDefFunction;
 import com.ultrapower.workflow.engine.core.IFreeFunction;
 import com.ultrapower.workflow.engine.core.model.ActionInfo;
 import com.ultrapower.workflow.engine.core.model.DataField;
 import com.ultrapower.workflow.engine.core.model.EntryState;
 import com.ultrapower.workflow.engine.core.model.FieldDataType;
 import com.ultrapower.workflow.engine.core.model.NoticeTemplate;
 import com.ultrapower.workflow.engine.core.model.ThreadObj;
 import com.ultrapower.workflow.engine.core.model.WfAction;
 import com.ultrapower.workflow.engine.core.model.WfStep;
 import com.ultrapower.workflow.engine.def.model.ActionDescriptor;
 import com.ultrapower.workflow.engine.def.model.ActorDescriptor;
 import com.ultrapower.workflow.engine.def.model.FunctionDescriptor;
 import com.ultrapower.workflow.engine.def.model.MetaDescriptor;
 import com.ultrapower.workflow.engine.def.model.StatusDescriptor;
 import com.ultrapower.workflow.engine.def.model.StepDescriptor;
 import com.ultrapower.workflow.engine.def.model.SubflowDescriptor;
 import com.ultrapower.workflow.engine.def.model.SubflowsDescriptor;
 import com.ultrapower.workflow.engine.def.model.WorkflowDescriptor;
 import com.ultrapower.workflow.engine.store.model.Step;
 import com.ultrapower.workflow.engine.task.ITaskManager;
 import com.ultrapower.workflow.engine.task.model.BaseTask;
 import com.ultrapower.workflow.engine.task.model.ProcessTask;
 import com.ultrapower.workflow.exception.WorkflowEngineException;
 import com.ultrapower.workflow.exception.WorkflowException;
 import com.ultrapower.workflow.role.model.ChildRole;
 import java.io.Serializable;
 import java.sql.Connection;
 import java.sql.DatabaseMetaData;
 import java.sql.ResultSet;
 import java.sql.Statement;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import javax.sql.DataSource;
 import org.apache.commons.collections.CollectionUtils;
 import org.apache.commons.lang.ArrayUtils;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Node;
 import org.dom4j.io.SAXReader;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.context.ApplicationContext;
 import org.springframework.context.support.ClassPathXmlApplicationContext;
 
 public class WfEngineUtils
 {
   private static Logger log = LoggerFactory.getLogger(WfEngineUtils.class);
   private static Map<String, EntryState> states;
   private static Map<String, String> arSchemas;
   private static Map<String, Map<String, NoticeTemplate>> templateMap;
   private static Map<String, String> msgMap;
 
   public static void loadStates()
     throws DocumentException
   {
     String path = "/wfengine/workflows/entry_state.xml";
     states = new HashMap();
     log.info("开始加载工单状态配置文件。path=" + path);
     SAXReader reader = new SAXReader();
     Document doc = reader.read(WfEngineUtils.class.getResourceAsStream(path));
     List nodes = doc.selectNodes("//actions/action");
     if (CollectionUtils.isNotEmpty(nodes))
       for (int i = 0; i < nodes.size(); i++) {
         Node node = (Node)nodes.get(i);
         String code = node.valueOf("@code");
         if (StringUtils.isNotBlank(code)) {
           code = code.toUpperCase();
           String cnname = node.valueOf("@cnname");
           String entryState = node.valueOf("@entryState");
           String inProcessState = node.valueOf("@inProcessState");
           String outProcessState = node.valueOf("@outProcessState");
           String desc = node.valueOf("@desc");
           String isTrans = node.valueOf("@isTrans");
           states.put(code, new EntryState(code, cnname, entryState, inProcessState, outProcessState, desc, isTrans));
         } else {
           throw new WorkflowException("action的code属性不能为空！" + node.asXML());
         }
       }
   }
 
   public static void loadARSchema()
   {
     log.info("读取ARSchema信息进缓存！");
     arSchemas = new HashMap();
     DataSource ds = (DataSource)ApplicationContextUtils.getBean("dataSource");
     try {
       Connection con = ds.getConnection();
       String dbType = con.getMetaData().getDatabaseProductName();
       if (StringUtils.isNotBlank(dbType)) {
         if (dbType.toLowerCase().indexOf(WfEngineConstants.DATABASE_TYPE_ORACLE) > -1)
           WfEngineConstants.DATABASE_TYPE = WfEngineConstants.DATABASE_TYPE_ORACLE;
         else if (dbType.toLowerCase().indexOf(WfEngineConstants.DATABASE_TYPE_DB2) > -1) {
           WfEngineConstants.DATABASE_TYPE = WfEngineConstants.DATABASE_TYPE_DB2;
         }
       }
       String sql = "select name,schemaid from ARSCHEMA";
       Statement st = con.createStatement();
       ResultSet rs = st.executeQuery(sql);
       while (rs.next()) {
         String name = rs.getString("name");
         String schemaId = rs.getString("schemaid");
         arSchemas.put(name, schemaId);
       }
       st.close();
       rs.close();
       con.close();
     } catch (Exception e) {
       log.error("从arschema读取数据失败！");
     }
   }
 
   public static void loadNoticeTemplate()
   {
     try
     {
       templateMap = new HashMap();
       String path = "/wfengine/configs/noticetemplate.xml";
       log.info("开始加载工单短信通知配置文件。path=" + path);
       SAXReader reader = new SAXReader();
       Document doc = reader.read(WfEngineUtils.class.getResourceAsStream(path));
       List<Node> nodesList = doc.selectNodes("//notice-config/action");
       for (Node node : nodesList) {
         Map noticeMap = new HashMap();
         String actionCode = node.valueOf("@name");
         List<Node> noticeList = node.selectNodes("notice");
         for (Node notice : noticeList) {
           String baseSchema = notice.valueOf("@schema");
           String[] keys = notice.valueOf("@key").split(",");
           NoticeTemplate model = new NoticeTemplate();
           model.setActionCode(actionCode);
           model.setBaseSchema(baseSchema);
           model.setKeys(keys);
           model.setTemplate(notice.getText());
           noticeMap.put(baseSchema, model);
        }
         templateMap.put(actionCode, noticeMap);
      }
      
     } catch (Exception e) {
       e.printStackTrace();
       log.error(e.getMessage(), e);
     }
   }
   
   public static void main(String[] args) {
     WfEngineUtils wfutil = new WfEngineUtils();
     wfutil.loadNoticeTemplate();
     
  }
 
   public static void loadMessage()
   {
     try
     {
       msgMap = new HashMap();
       String path = "/wfengine/configs/message.xml";
       log.info("开始加载message.xml。path=" + path);
       SAXReader reader = new SAXReader();
       Document doc = reader.read(WfEngineUtils.class.getResourceAsStream(path));
       List nodes = doc.selectNodes("//messages/message");
       if (CollectionUtils.isNotEmpty(nodes))
         for (int i = 0; i < nodes.size(); i++) {
           Node node = (Node)nodes.get(i);
           String code = node.valueOf("@name");
           String text = node.getText();
           msgMap.put(code, text);
         }
     }
     catch (Exception e) {
       e.printStackTrace();
       log.error(e.getMessage(), e);
     }
   }
 
   public static String formatNoticeContent(ActionDescriptor acDesc, String actionCode, String baseSchema, Map<String, DataField> input)
   {
     log.info("获取短信模板：actionName=" + actionCode + ",baseSchema=" + baseSchema);
 
     Map noticeMap = null;
     String actionNo = null;
     if (acDesc != null) {
       actionNo = acDesc.getActionNo();
     }
     if (StringUtils.isNotBlank(actionNo)) {
       noticeMap = (Map)templateMap.get(actionNo);
     }
     if (noticeMap == null) noticeMap = (Map)templateMap.get(actionCode);
     log.info("获取短信模板：根据动作获取结果=" + (noticeMap == null));
     if (noticeMap == null) noticeMap = (Map)templateMap.get("*");
     if (noticeMap == null) {
       return "";
     }
 
     NoticeTemplate template = (NoticeTemplate)noticeMap.get(baseSchema);
     log.info("获取短信模板：根据工单类型获取结果=" + (baseSchema == null));
     if (template == null) template = (NoticeTemplate)noticeMap.get("*");
     if (template == null) {
       return "";
     }
 
     String[] keys = template.getKeys();
     String finalContent = template.getTemplate();
 
     for (String key : keys)
     {
       if (key.equals(""))
         continue;
       DataField field = (DataField)input.get(key);
       if (field != null)
       {
         FieldDataType type = field.getType();
         String value = field.getValue();
         if (type == FieldDataType.DateTime) {
           long longVal = parseLong(value);
           value = TimeUtils.formatIntToDateString(longVal);
         }
 
         finalContent = finalContent.replace("{" + key + "}", value);
       }
       else
       {
         finalContent = finalContent.replace("{" + key + "}", "");
       }
     }
 
     return finalContent;
   }
 
   public static String getTableName(String baseSchema) {
     return (String)arSchemas.get(baseSchema);
   }
 
   private static EntryState getEntryStateWithCheck(String actionType) {
     EntryState state = null;
     if (StringUtils.isBlank(actionType)) {
       throw new WorkflowException("动作标识不能为空！");
     }
     state = (EntryState)states.get(actionType.toUpperCase());
     if (state == null) {
       throw new WorkflowException("与该动作对应的工单状态配置不存在！code=" + actionType);
     }
     return state;
   }
 
   public static String getActionName(String actionType) {
     EntryState entryState = getEntryStateWithCheck(actionType);
     return entryState.getCnname();
   }
 
   public static boolean isTrans(String actionType) {
     EntryState entryState = getEntryStateWithCheck(actionType);
     return entryState.isTrans();
   }
 
   public static String inProcessState(String actionType) {
     EntryState entryState = getEntryStateWithCheck(actionType);
     return entryState.getInProcessState();
   }
 
   public static String entryState(String actionType) {
     EntryState entryState = getEntryStateWithCheck(actionType);
     return entryState.getEntryState();
   }
 
   public static String outProcessState(String actionType) {
     EntryState entryState = getEntryStateWithCheck(actionType);
     return entryState.getOutProcessState();
   }
 
   public static WfAction convert(ActionDescriptor acDesc)
   {
     WfAction action = new WfAction();
     String id = acDesc.getId();
     String name = acDesc.getName();
     String actionNo = acDesc.getActionNo();
     action.setActionId(id);
     action.setActionName(name);
     action.setActionNo(actionNo);
     action.setActionType("NEXT");
     action.setLinkpri(acDesc.getLinkpri());
     action.setRemap(acDesc.getRemap());
     return action;
   }
 
   public static WfStep convert(StepDescriptor stDesc, String taskId, Map<String, DataField> inputs) {
     WfStep step = null;
     if (stDesc != null) {
       WorkflowDescriptor wfDesc = (WorkflowDescriptor)stDesc.getParent();
       String statusId = stDesc.getStatusId();
       List metaList = stDesc.getMetaList();
       SubflowsDescriptor subflows = stDesc.getSubflows();
       step = new WfStep();
       step.setId(stDesc.getId());
       step.setDesc(stDesc.getDesc());
       step.setStatusId(statusId);
       step.setStatusName(wfDesc.getStatusDescriptor(statusId).getName());
       step.setAcceptOver(stDesc.getAcceptOver());
       step.setDealOver(stDesc.getDealOver());
       step.setAssignOver(stDesc.getAssignOver());
       step.setTaskPolicy(stDesc.getTaskPolicy());
       step.setAuto(stDesc.isAuto());
       step.setStepNo(stDesc.getStepNo());
       step.setStepGroup(stDesc.getStepGroup());
       String type = stDesc.getType();
       if (StringUtils.isBlank(type)) {
         type = "DEAL";
       }
       step.setType(type);
       ActorDescriptor actorDesc = stDesc.getActorDesc();
       if (actorDesc != null) {
         String roleId = actorDesc.getRoleId();
         String roleName = actorDesc.getRoleName();
         String rule = actorDesc.getRule();
         String actorType = actorDesc.getActorType();
         step.setRoleId(roleId);
         step.setRoleName(roleName);
         if (StringUtils.isNotBlank(taskId)) {
           ITaskManager taskManager = (ITaskManager)ApplicationContextUtils.getBean("taskManager");
           BaseTask task = taskManager.getTaskById(taskId);
 
           if ((StringUtils.isNotBlank(actorType)) && (StringUtils.isNotBlank(rule))) {
             if (actorType.equals("0"))
             {
               List rule0 = rule0(stDesc.getId(), rule);
               step.setAcInfoList(rule0);
             } else if (actorType.equals("1"))
             {
               List rule1 = rule1(task, stDesc.getId(), rule);
               step.setAcInfoList(rule1);
             } else if (actorType.equals("2"))
             {
               List rule2 = rule2(stDesc.getId(), rule, inputs);
               step.setAcInfoList(rule2);
             } else if (!actorType.equals("3"))
             {
               actorType.equals("4");
             }
           }
         }
       }
 
       if (CollectionUtils.isNotEmpty(metaList)) {
         for (int i = 0; i < metaList.size(); i++) {
           MetaDescriptor metaDesc = (MetaDescriptor)metaList.get(i);
           if (metaDesc != null) {
             String name = metaDesc.getName();
             String value = metaDesc.getValue();
             step.getMetaMaps().put(name, value);
           }
         }
       }
       if (subflows != null) {
         Map subflowMap = subflows.getSubflowMap();
         if (subflowMap != null) {
           Set keySet = subflowMap.keySet();
           Iterator iter = keySet.iterator();
           if (iter.hasNext()) {
             String key = (String)iter.next();
             SubflowDescriptor subflow = (SubflowDescriptor)subflowMap.get(key);
             if (subflow != null) {
               String subName = subflow.getType();
               step.setSubName(subName);
             }
           }
         }
       }
     }
 
     return step;
   }
 
   public static List<ActionInfo> rule0(String tarStepCode, String rule)
   {
     List acList = new ArrayList();
     String[] rules = rule.split("#");
     if (!ArrayUtils.isEmpty(rules)) {
       String assignee = rules[0].trim();
       String assigneeId = rules[1].trim();
       String assigneeGroup = rules[2].trim();
       String assigneeGroupId = rules[3].trim();
       String[] assigneeAry = assignee.split(":");
       String[] assigneeIdAry = assigneeId.split(":");
       String[] assigneeGroupAry = assigneeGroup.split(":");
       String[] assigneeGroupIdAry = assigneeGroupId.split(":");
       if (assigneeIdAry.length == 2) {
         String actorName = assigneeAry.length == 2 ? assigneeAry[1] : "";
         acList.add(new ActionInfo(assigneeIdAry[1], "USER", actorName, "NEXT", null, tarStepCode));
       } else if (assigneeGroupIdAry.length == 2) {
         String groupName = assigneeGroupAry.length == 2 ? assigneeGroupAry[1] : "";
         acList.add(new ActionInfo(assigneeGroupIdAry[1], "GROUP", groupName, "NEXT", null, tarStepCode));
       } else {
         log.info("解析处理人规则失败[固定的执行角色]！rule=" + rule);
       }
     }
     return acList;
   }
 
   public static List<ActionInfo> rule1(BaseTask curTask, String tarStepCode, String rule)
   {
     List acList = new ArrayList();
     if (rule.indexOf(WfEngineConstants.CREATEUSER) > 0) {
       String creator = curTask.getCreator();
       String creatorName = curTask.getCreatorName();
       String creatorType = curTask.getCreatorType();
       acList.add(new ActionInfo(creator, creatorType, creatorName, "NEXT", null, tarStepCode));
     } else if (rule.indexOf(WfEngineConstants.STEPCODE) > 0) {
       String[] rules = rule.split("=");
       if ((ArrayUtils.isEmpty(rules)) || (rules.length != 2) || (StringUtils.isBlank(rules[1]))) {
         throw new WorkflowException("同其他环节的处理人规则不合法！rule=" + rule);
       }
       String hql = "from HistoryProcessTask where entryId='" + curTask.getEntryId() + "' and stepCode='" + rules[1] + "' order by createTime desc";
       ITaskManager taskManager = (ITaskManager)ApplicationContextUtils.getBean("taskManager");
       List find = taskManager.queryHistoryTask(hql);
       if (CollectionUtils.isNotEmpty(find)) {
         BaseTask dp = (BaseTask)find.get(0);
         String assigneeId = dp.getAssigneeId();
         String assignee = dp.getAssignee();
         String assignGroupId = dp.getAssignGroupId();
         String assignGroup = dp.getAssignGroup();
         String actorType = dp.getActorType();
         if (StringUtils.isNotBlank(assigneeId))
           acList.add(new ActionInfo(assigneeId, actorType, assignee, "NEXT", null, tarStepCode));
         else
           acList.add(new ActionInfo(assignGroupId, actorType, assignGroup, "NEXT", null, tarStepCode));
       }
       else {
         throw new WorkflowException("没有从历史数据中查找到符合条件的数据[同其他环节的处理人规则]！rule=" + rule);
       }
     }
     return acList;
   }
 
   public static List<ActionInfo> rule2(String tarStepCode, String rule, Map<String, DataField> inputs)
   {
     List acList = new ArrayList();
     if ((StringUtils.isNotBlank(rule)) && (inputs != null)) {
       int len = "contextKey:".length();
       String key = rule.substring(len);
       String value = null;
       if (StringUtils.isNotBlank(key)) {
         DataField df = (DataField)inputs.get(key);
         if (df != null) {
           value = df.getValue();
           if (StringUtils.isNotBlank(value)) {
             String[] preAsses = value.split("#;");
             if (!ArrayUtils.isEmpty(preAsses)) {
               for (int i = 0; i < preAsses.length; i++) {
                 BizFormUtil.parseAssignStr(acList, preAsses[i]);
               }
             }
           }
         }
         log.info("从流程流转中关联的上下文获取处理人,rule=" + rule + ",value=" + value);
       }
     }
     return acList;
   }
 
   public static String quotaStr(String str) {
     String rtn = null;
     if (StringUtils.isNotBlank(str)) {
       rtn = str.replace(",", "','");
     }
     return rtn;
   }
 
   public static List<ActionInfo> getActionInfoList(List<ActionInfo> acInfoList, String actionType, boolean getAllFreeActor, String stepCode, String assignTree) {
     List acInfos = new ArrayList();
     if (acInfoList != null) {
       for (int i = 0; i < acInfoList.size(); i++) {
         ActionInfo acInfo = (ActionInfo)acInfoList.get(i);
         String acType = acInfo.getActionType();
         String stCode = acInfo.getStepCode();
         String actorId = acInfo.getActorId();
         if (actionType.equals("NEXT")) {
           if ((!StringUtils.isNotBlank(actorId)) || 
             (!StringUtils.isNotBlank(acType)) || 
             (!acType.equalsIgnoreCase(actionType)) || (
             ((!StringUtils.isNotBlank(stepCode)) || 
             (!stepCode.equalsIgnoreCase(stCode))) && 
             (!StringUtils.isBlank(stCode)) && 
             (!"dp0".equalsIgnoreCase(stCode)) && (
             (!StringUtils.isNotBlank(assignTree)) || (!assignTree.equals(stCode))))) continue;
           acInfos.add(acInfo);
         }
         else if ((StringUtils.isNotBlank(acType)) && (!acType.equals("NEXT"))) {
           if (getAllFreeActor) {
             if (StringUtils.isNotBlank(assignTree)) {
               if (assignTree.equals(stCode))
                 acInfos.add(acInfo);
             }
             else {
               acInfos.add(acInfo);
             }
           }
           else if (acType.equalsIgnoreCase(actionType)) {
             if (StringUtils.isNotBlank(assignTree)) {
               if (assignTree.equals(stCode))
                 acInfos.add(acInfo);
             }
             else {
               acInfos.add(acInfo);
             }
           }
         }
       }
 
     }
 
     return acInfos;
   }
 
   public static boolean isNextAndCopy(List<ActionInfo> acInfoList)
   {
     boolean next = false;
     boolean copy = false;
     if (CollectionUtils.isNotEmpty(acInfoList)) {
       for (int i = 0; i < acInfoList.size(); i++) {
         ActionInfo ac = (ActionInfo)acInfoList.get(i);
         String actionType = ac.getActionType();
         if ((!"NEXT".equals(actionType)) && (!"MAKECOPY".equals(actionType))) {
           return false;
         }
         if ("NEXT".equals(actionType)) {
           next = true;
         }
         if ("MAKECOPY".equals(actionType)) {
           copy = true;
         }
       }
     }
     return (next) && (copy);
   }
 
   public static void initContext() {
     String file = "./spring/applicationContext.xml";
     ApplicationContext ac = new ClassPathXmlApplicationContext(file);
     ApplicationContextUtils.ctx = ac;
   }
 
   public static long parseLong(String str) {
     long l = 0L;
     try {
       l = Long.parseLong(str);
     } catch (Exception localException) {
     }
     return l;
   }
 
   public static String getDefActionName(Map<String, DataField> inputs) {
     DataField df = null;
     if (inputs != null)
       df = (DataField)inputs.get("ActionText");
     if (df != null) {
       return df.getValue();
     }
     return "";
   }
 
   public static String getDealDesc(Map<String, DataField> inputs) {
     DataField df = (DataField)inputs.get("DealDesc");
     if (df != null) {
       return df.getValue();
     }
     return "";
   }
 
   public static String getValueFromInput(Map<String, DataField> inputs, String key) {
     if (inputs != null) {
       Set keySet = inputs.keySet();
       Set<String> keys = inputs.keySet();
       Iterator iter = keys.iterator();
       for (String ky : keys) {
         if (ky.equals(key)) {
           return ((DataField)inputs.get(key)).getValue();
         }
       }
     }
     return "";
   }
 
   public static String getMessage(String key) {
     return (String)msgMap.get(key);
   }
 
   private static String getMessage(String key, String userFullName) {
     String mes = "";
     String tmp = getMessage(key);
     if (StringUtils.isNotBlank(tmp)) {
       mes = tmp.replace("{user}", userFullName);
     }
     return mes;
   }
 
   public static String getMessage(String key, String userFullName, String actorFullName) {
     String mes = "";
     String tmp = getMessage(key, userFullName);
     if (StringUtils.isNotBlank(tmp)) {
       mes = tmp.replace("{actor}", actorFullName);
     }
     return mes;
   }
 
   public static Long getThreadId()
   {
     return Thread.currentThread().getId();
   }
 
   public static IFreeFunction getDefaultFreePostFunction(ActionInfo acInfo) {
     IFreeFunction func = null;
     try {
       func = (IFreeFunction)ApplicationContextUtils.getBean(acInfo.getActionType().toLowerCase() + "OutFunction");
     } catch (Exception e) {
       func = (IFreeFunction)ApplicationContextUtils.getBean("defaultFreeOutFunction");
     }
     return func;
   }
 
   public static IFreeFunction getDefaultFreePreFunction(ActionInfo acInfo) {
     IFreeFunction func = null;
     try {
       func = (IFreeFunction)ApplicationContextUtils.getBean(acInfo.getActionType().toLowerCase() + "InFunction");
     } catch (Exception e) {
       func = (IFreeFunction)ApplicationContextUtils.getBean("defaultFreeInFunction");
     }
     return func;
   }
 
   public static void executeFunction(List<FunctionDescriptor> fnDescs, String userId, BaseTask fromTask, BaseTask task, Step step, ActionDescriptor acDesc, StepDescriptor stDesc, ActionInfo acInfo, Map<String, DataField> inputs) throws WorkflowEngineException {
     if (fnDescs != null)
       for (int i = 0; i < fnDescs.size(); i++) {
         FunctionDescriptor fnDesc = (FunctionDescriptor)fnDescs.get(i);
         String beanName = fnDesc.getValue();
         if (StringUtils.isNotBlank(beanName)) {
           IDefFunction func = null;
           if (beanName.indexOf(".") > 0) {
             try {
               Class clazz = Class.forName(beanName);
               func = (IDefFunction)clazz.newInstance();
             } catch (ClassNotFoundException e) {
               e.printStackTrace();
             } catch (InstantiationException e) {
               e.printStackTrace();
             } catch (IllegalAccessException e) {
               e.printStackTrace();
             }
           }
           if (func == null) {
             func = (IDefFunction)ApplicationContextUtils.getBean(beanName);
           }
           if (func != null)
             func.execute(userId, fromTask, task, step, acDesc, stDesc, acInfo, inputs);
         }
       }
   }
 
   public static void executeFunction(List<FunctionDescriptor> fnDescs, String userId, BaseTask fromTask, BaseTask task, Step step, StepDescriptor stDesc, ActionInfo acInfo, Map<String, DataField> inputs)
     throws WorkflowEngineException
   {
     executeFunction(fnDescs, userId, fromTask, task, step, null, stDesc, acInfo, inputs);
   }
 
   public static ICondition getCondition(String conName) {
     ICondition con = null;
     con = (ICondition)ApplicationContextUtils.getBean(conName);
     return con;
   }
 
   public static String getDpDesc(String actionType, Map<String, DataField> inputs, String user, String actor) {
     String actionName = "";
     if ("NEXT".equals(actionType)) {
       actionName = getDefActionName(inputs);
     } else {
       actionName = getMessage(actionType, user, actor);
       if (StringUtils.isBlank(actionName)) {
         actionName = getActionName(actionType);
         if (StringUtils.isNotBlank(user)) {
           actionName = user + actionName;
         }
         if (StringUtils.isNotBlank(actor)) {
           actionName = actionName + actor;
         }
       }
     }
     return actionName;
   }
 
   public static String getActionName(String actionType, Map<String, DataField> inputs) {
     String actionName = "";
 
     actionName = getDefActionName(inputs);
     if ((actionName.length() == 0) && (!"NEXT".equals(actionType))) {
       actionName = getActionName(actionType);
     }
 
     return actionName;
   }
 
   public static void sendMsg(String userId, ActionInfo acInfo, ActionDescriptor acDesc, BaseTask fromTask, BaseTask task, Map<String, DataField> inputs) {
     IDao noticeDao = (IDao)ApplicationContextUtils.getBean("noticeDao");
     if ((StringUtils.isNotBlank(userId)) && (task != null) && (acInfo != null)) {
       String actorId = acInfo.getActorId();
       String actorType = acInfo.getActorType();
       String actorName = "";
       String acId = "";
       String acName = "";
       String groupId = "";
       String groupName = "";
       String noticeType = "";
       if (("USER".equals(actorType)) && (StringUtils.isNotBlank(actorId))) {
         UserInfo actor = ThreadObj.getUsersInfo(actorId);
         if (actor != null) {
           actorName = actor.getFullname();
           acId = actor.getPid();
           acName = actor.getFullname();
           noticeType = "USER";
         }
       } else if (("ROLE".equals(actorType)) && (StringUtils.isNotBlank(actorId))) {
         ChildRole role = ThreadObj.getChildRole(actorId);
         if (role != null) {
           actorName = role.getChildRoleName();
           groupId = role.getChildRoleId();
           groupName = role.getChildRoleName();
           noticeType = "ROLE";
         }
       } else if (("GROUP".equals(actorType)) && (StringUtils.isNotBlank(actorId))) {
         DepInfo dep = ThreadObj.getDepInfo(actorId);
         if (dep != null) {
           actorName = dep.getDepfullname();
           groupId = dep.getPid();
           groupName = dep.getDepfullname();
           noticeType = "GROUP";
         }
       }
       UserInfo user = ThreadObj.getUsersInfo(userId);
       if (user != null) {
         String actionType = acInfo.getActionType();
         String dealOverStr = "";
         long dealOverTimeDate = acInfo.getDealOverTimeDate();
         String baseDealOverTime = getValueFromInput(inputs, "BaseDealOutTime");
         if (dealOverTimeDate > 0L) {
           dealOverStr = TimeUtils.formatIntToDateString(dealOverTimeDate);
         } else {
           long parseLong = parseLong(baseDealOverTime);
           dealOverStr = TimeUtils.formatIntToDateString(parseLong);
         }
         log.info("开始发送信息，userId=" + userId + ",actorId=" + actorId + ",acInfo=" + actionType);
         String actionName = getActionName(actionType, inputs);
         ProcessTask pTask = (ProcessTask)task;
         String baseName = pTask.getBaseName();
         String serialNum = pTask.getSerialNum();
         String workSheetTitle = pTask.getWorkSheetTitle();
         String baseSchema = pTask.getBaseSchema();
         String baseId = pTask.getBaseId();
         String taskId = pTask.getId();
         String stepCode = pTask.getStepCode();
         String fromTaskId = null;
         String fromPhaseNo = null;
         if (fromTask != null) {
           fromTaskId = fromTask.getId();
           fromPhaseNo = fromTask.getStepCode();
         }
         String mailInfo = formatNoticeContent(acDesc, actionType, baseSchema, inputs);
         mailInfo = mailInfo.replace("{actionName}", actionName)
           .replace("{actorName}", actorName)
           .replace("{userName}", user.getFullname())
           .replace("{BaseName}", baseName)
           .replace("{BaseSummary}", StringUtils.isBlank(workSheetTitle) ? "" : workSheetTitle)
           .replace("{serialNum}", serialNum)
           .replace("{BaseID}", baseId)
           .replace("{BaseSchema}", baseSchema)
           .replace("{BaseDealOutTime}", dealOverStr);
         String newMailBuffer = ((DataField)inputs.get(WfEngineConstants.MAILBUFFER)).getValue() + mailInfo;
         inputs.put(WfEngineConstants.MAILBUFFER, new DataField(WfEngineConstants.MAILBUFFER, FieldDataType.String, newMailBuffer));
 
         Notice notice = new Notice(baseId, baseSchema, serialNum, taskId, workSheetTitle, "", actionType, groupName, groupId, acName, acId, mailInfo, "");
         notice.setPhaseNo(stepCode);
         notice.setFromTaskId(fromTaskId);
         notice.setFromPhaseNo(fromPhaseNo);
         notice.setNoticeUserLoginName(actorId);
         notice.setCreateTime(TimeUtils.getCurrentTime());
         notice.setNoticeType(noticeType);
         if ((StringUtils.isNotBlank(baseId)) && (baseId.length() == 30))
         {
           notice.setIsSent(1);
           notice.setIsScaned(1);
         }
         notice.setIsPush(0);
         noticeDao.save(notice);
       }
     }
   }
 
   public static void sendMsg(String userId, ActionInfo acInfo, BaseTask fromTask, BaseTask task, Map<String, DataField> inputs)
   {
     sendMsg(userId, acInfo, null, fromTask, task, inputs);
   }
 
   public static void setDpAssignee(DealProcessModel dp, String userId) {
     UserInfo user = ThreadObj.getUsersInfo(userId);
     DepInfo dep = ThreadObj.getDepInfo(user.getDepid());
     if ((dp != null) && (user != null)) {
       dp.setAssigneeId(user.getLoginname());
       dp.setAssignee(user.getFullname());
       dp.setAssigneeDepId(user.getDepid());
       dp.setAssigneeDep(user.getDepname());
       if (dep != null) {
         dp.setAssigneeDn(dep.getDepfullname());
         dp.setAssigneeDnid(dep.getDepdns());
       }
       dp.setAssigneeCorpId(user.getCompanyId());
       dp.setAssigneeCorp(user.getCompanyName());
     }
   }
 
   public static void setTaskAssignee(BaseTask task, String userId) {
     UserInfo user = ThreadObj.getUsersInfo(userId);
     DepInfo dep = ThreadObj.getDepInfo(user.getDepid());
     if ((task != null) && (user != null)) {
       task.setAssigneeId(user.getLoginname());
       task.setAssignee(user.getFullname());
       task.setAssigneeDepId(user.getDepid());
       task.setAssigneeDep(user.getDepname());
       if (dep != null) {
         task.setAssigneeDn(dep.getDepfullname());
         task.setAssigneeDnid(dep.getDepdns());
       }
       task.setAssigneeCorpId(user.getCompanyId());
       task.setAssigneeCorp(user.getCompanyName());
     }
   }
 
   public static void setDpAssigner(DealProcessModel dp, String userId) {
     UserInfo user = ThreadObj.getUsersInfo(userId);
     DepInfo dep = ThreadObj.getDepInfo(user.getDepid());
     if ((dp != null) && (user != null)) {
       dp.setAssignerId(user.getLoginname());
       dp.setAssigner(user.getFullname());
       dp.setAssignerDepId(user.getDepid());
       dp.setAssignerDep(user.getDepname());
       if (dep != null) {
         dp.setAssignerDn(dep.getDepfullname());
         dp.setAssignerDnid(dep.getDepdns());
       }
       dp.setAssignerCorpId(user.getCompanyId());
       dp.setAssignerCorp(user.getCompanyName());
     }
   }
 
   public static void setTaskAssigner(BaseTask task, String userId) {
     UserInfo user = ThreadObj.getUsersInfo(userId);
     DepInfo dep = ThreadObj.getDepInfo(user.getDepid());
     if ((task != null) && (user != null)) {
       task.setAssignerId(user.getLoginname());
       task.setAssigner(user.getFullname());
       task.setAssignerDepId(user.getDepid());
       task.setAssignerDep(user.getDepname());
       if (dep != null) {
         task.setAssignerDn(dep.getDepfullname());
         task.setAssignerDnid(dep.getDepdns());
       }
       task.setAssignerCorpId(user.getCompanyId());
       task.setAssignerCorp(user.getCompanyName());
     }
   }
 
   public static void setDpDealer(DealProcessModel dp, String userId) {
     UserInfo user = ThreadObj.getUsersInfo(userId);
     DepInfo dep = ThreadObj.getDepInfo(user.getDepid());
     if ((dp != null) && (user != null)) {
       dp.setDealerId(user.getLoginname());
       dp.setDealer(user.getFullname());
       dp.setDealerDepId(user.getDepid());
       dp.setDealerDep(user.getDepname());
       if (dep != null) {
         dp.setDealerDn(dep.getDepfullname());
         dp.setDealerDnid(dep.getDepdns());
       }
       dp.setDealerCorpId(user.getCompanyId());
       dp.setDealerCorp(user.getCompanyName());
     }
   }
 
   public static void setTaskDealer(BaseTask task, String userId)
   {
     UserInfo user = ThreadObj.getUsersInfo(userId);
     DepInfo dep = ThreadObj.getDepInfo(user.getDepid());
     if ((task != null) && (user != null)) {
       task.setDealerId(user.getLoginname());
       task.setDealer(user.getFullname());
       task.setDealerDepId(user.getDepid());
       task.setDealerDep(user.getDepname());
       if (dep != null) {
         task.setDealerDn(dep.getDepfullname());
         task.setDealerDnid(dep.getDepdns());
       }
       task.setDealerCorpId(user.getCompanyId());
       task.setDealerCorp(user.getCompanyName());
     }
   }
 
   public static void resetDpDealer(DealProcessModel dp)
   {
     dp.setDealer(null);
     dp.setDealerId(null);
     dp.setDealerDep(null);
     dp.setDealerDepId(null);
     dp.setDealerCorp(null);
     dp.setDealerCorpId(null);
     dp.setDealerDn(null);
     dp.setDealerDnid(null);
   }
 
   public static void resetTaskDealer(BaseTask task) {
     task.setDealer(null);
     task.setDealerId(null);
     task.setDealerDep(null);
     task.setDealerDepId(null);
     task.setDealerCorp(null);
     task.setDealerCorpId(null);
     task.setDealerDn(null);
     task.setDealerDnid(null);
   }
 
   public static void setDpLogUser(DealProcessLogModel logModel, String userId) {
     UserInfo user = ThreadObj.getUsersInfo(userId);
     DepInfo dep = ThreadObj.getDepInfo(user.getDepid());
     if ((logModel != null) && (user != null)) {
       logModel.setLogUserId(user.getLoginname());
       logModel.setLogUser(user.getFullname());
       logModel.setLogUserDepId(user.getDepid());
       logModel.setLogUserDep(user.getDepname());
       logModel.setLogUserCorpId(user.getCompanyId());
       logModel.setLogUserCorp(user.getCompanyName());
       if (dep != null) {
         logModel.setLogUserDn(dep.getDepfullname());
         logModel.setLogUserDnid(dep.getDepdns());
       }
     }
   }
 
   public static void resetDpAssignee(DealProcessModel dp) {
     dp.setAssignee(null);
     dp.setAssigneeId(null);
     dp.setAssigneeDep(null);
     dp.setAssigneeDepId(null);
     dp.setAssigneeDn(null);
     dp.setAssigneeDnid(null);
     dp.setAssigneeCorp(null);
     dp.setAssigneeCorpId(null);
     dp.setAssignGroup(null);
     dp.setAssignGroupId(null);
   }
 
   public static void resetTaskAssignee(BaseTask task) {
     task.setAssignee(null);
     task.setAssigneeId(null);
     task.setAssigneeDep(null);
     task.setAssigneeDepId(null);
     task.setAssigneeDn(null);
     task.setAssigneeDnid(null);
     task.setAssigneeCorp(null);
     task.setAssigneeCorpId(null);
     task.setAssignGroup(null);
     task.setAssignGroupId(null);
   }
 
   public static String getActionDesc(ActionInfo acInfo) {
     if ((acInfo != null) && (StringUtils.isNotBlank(acInfo.getActionDesc()))) {
       return "：" + acInfo.getActionDesc();
     }
     return "";
   }
 
   public static String joinList(List<String> list, String sep) {
     String rtn = "";
     if (CollectionUtils.isNotEmpty(list)) {
       for (int i = 0; i < list.size(); i++) {
         rtn = rtn + (String)list.get(i);
         if (i != list.size() - 1) {
           rtn = rtn + sep;
         }
       }
     }
     return rtn;
   }
 
   public static String getDealType(String dealStr) {
     String dealType = "SHARE";
     if (StringUtils.isNotBlank(dealStr)) {
       if ("1".equals(dealStr))
         dealType = "SHARE";
       else if ("2".equals(dealStr))
         dealType = "ONLY";
       else if ("3".equals(dealStr)) {
         dealType = "MANAGEMANT";
       }
     }
     return dealType;
   }
 
   public static void setFlagActive(DealProcessModel dpModel, ProcessTask task, String actionType)
   {
     if ((dpModel != null) && (task != null) && (StringUtils.isNotBlank(actionType)))
       if (actionType.equals("ASSIGN")) {
         dpModel.setFlagActive(Long.valueOf(2L));
         task.setFlagActive(2L);
       } else if (actionType.equals("AUDIT")) {
         dpModel.setFlagActive(Long.valueOf(3L));
         task.setFlagActive(3L);
       } else if (actionType.equals("ORGANIZEAUDIT")) {
         dpModel.setFlagActive(Long.valueOf(10L));
         task.setFlagActive(10L);
       } else {
         dpModel.setFlagActive(Long.valueOf(0L));
         task.setFlagActive(0L);
       }
   }
 
   public static boolean isTransStep(Step step)
   {
     if (step != null) {
       String type = step.getType();
 
       return (!"ASSIST".equals(type)) && (!"COPY".equals(type));
     }
 
     return false;
   }
 
   public static WfType getWfTypeBySchema(String baseSchema)
   {
     WfType wfType = null;
     if (StringUtils.isNotBlank(baseSchema)) {
       Serializable ser = BaseCacheManager.get("WFType", baseSchema);
       if (ser != null) {
         wfType = (WfType)ser;
       }
     }
     return wfType;
   }
 
   public static String getDefVersionBySchema(String baseSchema) {
     String defName = null;
     if (StringUtils.isNotBlank(baseSchema)) {
       Serializable ser = BaseCacheManager.get("WFType", baseSchema);
       if (ser != null) {
         WfType wfType = (WfType)ser;
         if (1L == wfType.getWfType()) {
           defName = wfType.getWfDefaultVersion();
         }
       }
     }
     return defName;
   }
 
   public static boolean isARflow(String baseSchema) {
     boolean isAR = false;
     if ((StringUtils.isNotBlank(baseSchema)) && ((baseSchema.startsWith("WF4:")) || (baseSchema.startsWith("WF:")))) {
       isAR = true;
     }
     return isAR;
   }
 
   public static String subflowDesc(String userId, List<ActionInfo> acList, Map<String, DataField> inputs)
   {
     StringBuffer sb = new StringBuffer();
     if ((StringUtils.isNotBlank(userId)) && (CollectionUtils.isNotEmpty(acList))) {
       UserInfo user = ThreadObj.getUsersInfo(userId);
       if (user != null) {
         String fullname = user.getFullname();
         sb.append(fullname);
         for (int i = 0; i < acList.size(); i++) {
           ActionInfo actionInfo = (ActionInfo)acList.get(i);
           String actionType = actionInfo.getActionType();
           String actionName = getActionName(actionType, inputs);
           String actorType = actionInfo.getActorType();
           String actorId = actionInfo.getActorId();
           String actorName = "";
           if (("USER".equals(actorType)) && (StringUtils.isNotBlank(actorId))) {
             UserInfo actor = ThreadObj.getUsersInfo(actorId);
             if (actor != null)
               actorName = actor.getFullname();
           }
           else if (("ROLE".equals(actorType)) && (StringUtils.isNotBlank(actorId))) {
             ChildRole role = ThreadObj.getChildRole(actorId);
             if (role != null)
               actorName = role.getChildRoleName();
           }
           else if (("GROUP".equals(actorType)) && (StringUtils.isNotBlank(actorId))) {
             DepInfo dep = ThreadObj.getDepInfo(actorId);
             if (dep != null) {
               actorName = dep.getDepfullname();
             }
           }
           sb.append(actionName + actorName);
           if (i != acList.size() - 1) {
             sb.append("、");
           }
         }
       }
     }
     return sb.toString();
   }
 
   public static List<BaseTask> updateCacheEntryState(List<BaseTask> tasks, String entryState) {
     if ((CollectionUtils.isNotEmpty(tasks)) && (StringUtils.isNotBlank(entryState))) {
       for (int i = 0; i < tasks.size(); i++) {
         ((BaseTask)tasks.get(i)).setEntryState(entryState);
       }
     }
     return tasks;
   }

 }
 

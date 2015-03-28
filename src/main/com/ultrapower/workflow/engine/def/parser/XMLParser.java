 package com.ultrapower.workflow.engine.def.parser;
 
 import com.ultrapower.eoms.common.core.util.NumberUtils;
 import com.ultrapower.workflow.engine.def.model.AbstractDescriptor;
 import com.ultrapower.workflow.engine.def.model.ActionDescriptor;
 import com.ultrapower.workflow.engine.def.model.ActorDescriptor;
 import com.ultrapower.workflow.engine.def.model.ConditionDescriptor;
 import com.ultrapower.workflow.engine.def.model.EndDescriptor;
 import com.ultrapower.workflow.engine.def.model.FunctionDescriptor;
 import com.ultrapower.workflow.engine.def.model.JoinDescriptor;
 import com.ultrapower.workflow.engine.def.model.MetaDescriptor;
 import com.ultrapower.workflow.engine.def.model.SplitDescriptor;
 import com.ultrapower.workflow.engine.def.model.SplitToDescriptor;
 import com.ultrapower.workflow.engine.def.model.StartDescriptor;
 import com.ultrapower.workflow.engine.def.model.StatusDescriptor;
 import com.ultrapower.workflow.engine.def.model.StepDescriptor;
 import com.ultrapower.workflow.engine.def.model.SubflowDescriptor;
 import com.ultrapower.workflow.engine.def.model.SubflowsDescriptor;
 import com.ultrapower.workflow.engine.def.model.WorkflowDescriptor;
 import com.ultrapower.workflow.exception.WorkflowDefineException;
 import java.io.File;
 import java.net.URL;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public class XMLParser
 {
   private static Logger log = LoggerFactory.getLogger(XMLParser.class);
 
   private String fileName = null;
 
   private File file = null;
 
   private String xml = null;
 
   public static void main(String[] args) throws Exception {
     String filePath = XMLParser.class.getResource("/workflows/NewFile.xml").getPath();
     File file = new File(filePath);
     XMLParser parser = new XMLParser(file);
     parser.parseFile();
   }
 
   public XMLParser(File file) {
     this.file = file;
   }
 
   public XMLParser(String fileName, String xml) {
     this.fileName = fileName;
     this.xml = xml;
   }
 
   public WorkflowDescriptor parseString() throws WorkflowDefineException {
     log.info("开始解析流程定义文件 defName=" + this.fileName);
     Document doc = null;
     try
     {
       doc = DocumentHelper.parseText(this.xml);
     }
     catch (DocumentException e)
     {
       e.printStackTrace();
       throw new WorkflowDefineException("读取流程定义文件异常，请确认流程定义文件是否存在！fileName=" + this.fileName);
     }
     WorkflowDescriptor wfDesc = parseDoc(doc);
     wfDesc.setName(this.fileName);
     return wfDesc;
   }
 
   public WorkflowDescriptor parseFile() throws WorkflowDefineException {
     Document doc;
     this.fileName = this.file.getName();
     SAXReader read = new SAXReader();
     try
     {
       doc = read.read(this.file);
     }
     catch (DocumentException e)
     {
       e.printStackTrace();
       throw new WorkflowDefineException("读取流程定义文件异常，请确认流程定义文件是否存在！fileName=" + this.fileName);
     }
     WorkflowDescriptor wfDesc = parseDoc(doc);
     wfDesc.setName(this.fileName);
     return wfDesc;
   }
 
   private WorkflowDescriptor parseDoc(Document doc) throws WorkflowDefineException {
     WorkflowDescriptor wfDesc = new WorkflowDescriptor();
     if (doc != null) {
       Element root = doc.getRootElement();
       Element start = root.element("start");
       Element steps = root.element("steps");
       Element joins = root.element("joins");
       Element splits = root.element("splits");
       Element statuses = root.element("statuses");
       Element ends = root.element("ends");
       log.info("开始解析status节点");
       readStatuses(statuses, wfDesc);
       log.info("开始解析start节点");
       readStart(start, wfDesc);
       log.info("开始解析step节点");
       readSteps(steps, wfDesc);
       log.info("开始解析join节点");
       readJoins(joins, wfDesc);
       log.info("开始解析split节点");
       readSplits(splits, wfDesc);
       log.info("开始解析end节点");
       readEnds(ends, wfDesc);
     }
     log.info("解析完毕！");
     return wfDesc;
   }
 
   private void readStart(Element start, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
     if (start == null) {
       throw new WorkflowDefineException(this.fileName + " 没有start节点！");
     }
     String to = start.attributeValue("to");
     if (StringUtils.isBlank(to)) {
       throw new WorkflowDefineException(this.fileName + " 开始节点没有to属性");
     }
     StartDescriptor stDesc = new StartDescriptor(wfDesc);
     stDesc.setTo(to);
     wfDesc.setStartDesc(stDesc);
   }
 
   private void readSteps(Element steps, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
     if (steps != null) {
       Iterator iter = steps.elementIterator();
       while (iter.hasNext()) {
         Element step = (Element)iter.next();
         readStep(step, wfDesc);
       }
     }
   }
 
   private void readStep(Element step, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
     String stepId = step.attributeValue("id");
     String name = step.attributeValue("name");
     String desc = step.attributeValue("desc");
     String statusId = step.attributeValue("statusId");
     String assignOver = step.attributeValue("assignOver");
     String acceptOver = step.attributeValue("acceptOver");
     String dealOver = step.attributeValue("dealOver");
     String taskPolicy = step.attributeValue("taskPolicy");
     String auto = step.attributeValue("auto");
     String type = step.attributeValue("type");
     String stepNo = step.attributeValue("stepNo");
     String stepGroup = step.attributeValue("stepGroup");
 
     if (StringUtils.isBlank(stepId)) {
       throw new WorkflowDefineException(this.fileName + " step节点id为空！\r\n" + step.asXML());
     }
     StepDescriptor exist = wfDesc.getStepDescriptor(stepId);
     if (exist != null) {
       throw new WorkflowDefineException(this.fileName + " 已经存在id为" + stepId + "的step节点！\r\n" + step.asXML());
     }
     Element prefuncs = step.element("prefuncs");
     Element actions = step.element("actions");
     Element metas = step.element("metas");
     Element subflows = step.element("subflows");
     Element postfuncs = step.element("postfuncs");
     Element actor = step.element("actor");
 
     StepDescriptor stDesc = new StepDescriptor(wfDesc);
     readActor(actor, stDesc);
     readPrefunc(prefuncs, stDesc);
     readPostfunc(postfuncs, stDesc);
     readActions(actions, stDesc);
     readMetas(metas, stDesc);
     readSubflows(subflows, stDesc);
     stDesc.setId(stepId);
     stDesc.setName(name);
     stDesc.setDesc(desc);
     stDesc.setStatusId(statusId);
     stDesc.setStatusName(wfDesc.getStatusDescriptor(statusId).getName());
     stDesc.setDealOver(dealOver);
     stDesc.setAssignOver(assignOver);
     stDesc.setAcceptOver(acceptOver);
     stDesc.setTaskPolicy(taskPolicy);
     stDesc.setType(type);
     stDesc.setStepNo(stepNo);
     stDesc.setStepGroup(stepGroup);
     stDesc.setAuto(Boolean.parseBoolean(auto));
     wfDesc.putStepDescriptor(stepId, stDesc);
   }
 
   private void readActor(Element actor, StepDescriptor stDesc) {
     if (actor != null) {
       ActorDescriptor acDesc = new ActorDescriptor(stDesc);
       String roleId = actor.attributeValue("roleID");
       String roleName = actor.attributeValue("roleName");
       String actorType = actor.attributeValue("actorType");
       String actorRule = actor.getText();
       acDesc.setRoleId(roleId);
       acDesc.setRoleName(roleName);
       acDesc.setRule(actorRule);
       acDesc.setActorType(actorType);
       stDesc.setActorDesc(acDesc);
     }
   }
 
   private void readPrefunc(Element prefuncs, AbstractDescriptor absDesc) throws WorkflowDefineException {
     if (prefuncs != null) {
       Iterator iter = prefuncs.elementIterator();
       while (iter.hasNext()) {
         Element func = (Element)iter.next();
 
         String funcId = func.attributeValue("id");
         if (StringUtils.isBlank(funcId)) {
           throw new WorkflowDefineException(this.fileName + " prefunc没有id！\r\n" + func.asXML());
         }
         String value = func.getText();
         FunctionDescriptor fuDesc = new FunctionDescriptor();
         fuDesc.setId(funcId);
         fuDesc.setValue(value);
         fuDesc.setParent(absDesc);
         if ((absDesc instanceof StepDescriptor)) {
           StepDescriptor stDesc = (StepDescriptor)absDesc;
           FunctionDescriptor exist = stDesc.getPreFuncById(funcId);
           if (exist != null) {
             throw new WorkflowDefineException(this.fileName + " 已经存在id为" + funcId + "的prefunc节点！\r\n" + func.asXML());
           }
           stDesc.putPreFuncMap(funcId, fuDesc);
         }
         if ((absDesc instanceof ActionDescriptor)) {
           ActionDescriptor acDesc = (ActionDescriptor)absDesc;
           FunctionDescriptor exist = acDesc.getPreFuncById(funcId);
           if (exist != null) {
             throw new WorkflowDefineException(this.fileName + " 已经存在id为" + funcId + "的prefunc节点！\r\n" + func.asXML());
           }
           acDesc.putPreFuncMap(funcId, fuDesc);
         }
       }
     }
   }
 
   private void readPostfunc(Element postfuncs, AbstractDescriptor absDesc) throws WorkflowDefineException {
     if (postfuncs != null) {
       Iterator iter = postfuncs.elementIterator();
       while (iter.hasNext()) {
         Element func = (Element)iter.next();
         String funcId = func.attributeValue("id");
         if (StringUtils.isBlank(funcId)) {
           throw new WorkflowDefineException(this.fileName + " postfunc没有id！\r\n" + func.asXML());
         }
         String value = func.getText();
         FunctionDescriptor fuDesc = new FunctionDescriptor();
         fuDesc.setId(funcId);
         fuDesc.setValue(value);
         fuDesc.setParent(absDesc);
         if ((absDesc instanceof StepDescriptor)) {
           StepDescriptor stDesc = (StepDescriptor)absDesc;
           FunctionDescriptor exist = stDesc.getPostFuncById(funcId);
           if (exist != null) {
             throw new WorkflowDefineException(this.fileName + " 已经存在id为" + funcId + "的postfunc节点！\r\n" + func.asXML());
           }
           stDesc.putPostFuncMap(funcId, fuDesc);
         }
         if ((absDesc instanceof ActionDescriptor)) {
           ActionDescriptor acDesc = (ActionDescriptor)absDesc;
           FunctionDescriptor exist = acDesc.getPostFuncById(funcId);
           if (exist != null) {
             throw new WorkflowDefineException(this.fileName + " 已经存在id为" + funcId + "的postfunc节点！\r\n" + func.asXML());
           }
           acDesc.putPostFuncMap(funcId, fuDesc);
         }
       }
     }
   }
 
   private void readActions(Element actions, StepDescriptor stDesc) throws WorkflowDefineException {
     if (actions != null) {
       Iterator iter = actions.elementIterator();
       while (iter.hasNext()) {
         Element action = (Element)iter.next();
         readAction(action, stDesc);
       }
     }
   }
 
   private void readAction(Element action, StepDescriptor stDesc) throws WorkflowDefineException {
     String id = action.attributeValue("id");
     if (StringUtils.isBlank(id)) {
       throw new WorkflowDefineException(this.fileName + " action的id为null!\r\n" + action.asXML());
     }
     ActionDescriptor exist = stDesc.getActionById(id);
     if (exist != null) {
       throw new WorkflowDefineException(this.fileName + " 已经存在id为" + id + "的action!\r\n" + action.asXML());
     }
     Element to = action.element("to");
     if (to == null) {
       throw new WorkflowDefineException(this.fileName + " id为" + id + "的action没有to元素!\r\n" + action.asXML());
     }
     String name = action.attributeValue("name");
     String linkpri = action.attributeValue("linkpri");
     String actionNo = action.attributeValue("actionNo");
     String remap = action.attributeValue("remap");
     Element prefuncs = action.element("prefuncs");
     Element postfuncs = action.element("postfuncs");
     String stepId = to.attributeValue("stepId");
     String joinId = to.attributeValue("joinId");
     String splitId = to.attributeValue("splitId");
     String endId = to.attributeValue("endId");
     ActionDescriptor acDesc = new ActionDescriptor();
     readPrefunc(prefuncs, acDesc);
     readPostfunc(postfuncs, acDesc);
     acDesc.setId(id);
     acDesc.setName(name);
     acDesc.setActionNo(actionNo);
     acDesc.setParent(stDesc);
     acDesc.setLinkpri(NumberUtils.formatToInt(linkpri));
     acDesc.setRemap(NumberUtils.formatToInt(remap));
     if (StringUtils.isNotBlank(stepId))
       acDesc.setToStepId(stepId);
     else if (StringUtils.isNotBlank(joinId))
       acDesc.setToJoinId(joinId);
     else if (StringUtils.isNotBlank(splitId))
       acDesc.setToSplitId(splitId);
     else if (StringUtils.isNotBlank(endId)) {
       acDesc.setToEndId(endId);
     }
     stDesc.putActionMap(id, acDesc);
   }
 
   private void readMetas(Element metas, StepDescriptor stDesc) throws WorkflowDefineException {
     if (metas != null) {
       Iterator iter = metas.elementIterator();
       while (iter.hasNext()) {
         Element meta = (Element)iter.next();
         readMeta(meta, stDesc);
       }
     }
   }
 
   private void readMeta(Element meta, StepDescriptor stDesc) throws WorkflowDefineException {
     if (meta != null) {
       String id = meta.attributeValue("id");
       String name = meta.attributeValue("name");
       String value = meta.getText();
       if (StringUtils.isBlank(id)) {
         throw new WorkflowDefineException(this.fileName + " meta的id不能为空！\r\n" + meta.asXML());
       }
 
       MetaDescriptor mtDesc = new MetaDescriptor(stDesc);
       mtDesc.setId(id);
       mtDesc.setName(name);
       mtDesc.setValue(value);
 
       stDesc.getMetaList().add(mtDesc);
     }
   }
 
   private void readJoins(Element joins, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
     if (joins != null) {
       Iterator iter = joins.elementIterator();
       while (iter.hasNext()) {
         Element join = (Element)iter.next();
         readJoin(join, wfDesc);
       }
     }
   }
 
   private void readJoin(Element join, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
     if (join != null) {
       String id = join.attributeValue("id");
       Element to = join.element("to");
       Element cond = join.element("condition");
       if (StringUtils.isBlank(id)) {
         throw new WorkflowDefineException(this.fileName + " join的id不能为空！\r\n" + join.asXML());
       }
       if (to == null) {
         throw new WorkflowDefineException(this.fileName + " join的to不能为空！\r\n" + join.asXML());
       }
       String stepId = to.attributeValue("stepId");
       String splitId = to.attributeValue("splitId");
       String endId = to.attributeValue("endId");
       if ((StringUtils.isBlank(stepId)) && (StringUtils.isBlank(splitId)) && (StringUtils.isBlank(endId))) {
         throw new WorkflowDefineException(this.fileName + " join的to元素的stepId,splitId,endId属性不能同时为空！\r\n" + join.asXML());
       }
       if (cond == null) {
         throw new WorkflowDefineException(this.fileName + " join的condition不能为空！\r\n" + join.asXML());
       }
       JoinDescriptor exist = wfDesc.getJoinDescriptor(id);
       if (exist != null) {
         throw new WorkflowDefineException(this.fileName + " id为" + id + "的join已经存在！\r\n" + join.asXML());
       }
       JoinDescriptor jnDesc = new JoinDescriptor(wfDesc);
       jnDesc.setId(id);
       jnDesc.setStepId(stepId);
       jnDesc.setSplitId(splitId);
       jnDesc.setEndId(endId);
       jnDesc.setParent(wfDesc);
       jnDesc.setConDesc(readCondition(cond));
       wfDesc.putJoinDescriptor(id, jnDesc);
     }
   }
 
   private void readSplits(Element splits, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
     if (splits != null) {
       Iterator iter = splits.elementIterator();
       while (iter.hasNext()) {
         Element split = (Element)iter.next();
         readSplit(split, wfDesc);
       }
     }
   }
 
   private void readSplit(Element split, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
     if (split != null) {
       String id = split.attributeValue("id");
       if (StringUtils.isBlank(id)) {
         throw new WorkflowDefineException(this.fileName + " split的id不能为空！\r\n" + split.asXML());
       }
       SplitDescriptor exist = wfDesc.getSplitDescriptor(id);
       if (exist != null) {
         throw new WorkflowDefineException(this.fileName + " id为" + id + "的split已经存在！\r\n" + split.asXML());
       }
       Element tos = split.element("tos");
       if ((tos == null) || (tos.elementIterator() == null)) {
         throw new WorkflowDefineException(this.fileName + " id为" + id + "的split 不存在tos元素！\r\n" + split.asXML());
       }
       Element cond = split.element("condition");
       SplitDescriptor spDesc = new SplitDescriptor(wfDesc);
       spDesc.setId(id);
       Iterator it = tos.elementIterator("to");
       while (it.hasNext()) {
         Element to = (Element)it.next();
         String toId = to.attributeValue("id");
         String stepId = to.attributeValue("stepId");
         String splitId = to.attributeValue("splitId");
         String joinId = to.attributeValue("joinId");
         String endId = to.attributeValue("endId");
         String assignTree = to.attributeValue("assignTree");
         String remap = to.attributeValue("remap");
         SplitToDescriptor toDesc = new SplitToDescriptor(spDesc);
         toDesc.setId(toId);
         toDesc.setStepId(stepId);
         toDesc.setSplitId(splitId);
         toDesc.setJoinId(joinId);
         toDesc.setEndId(endId);
         toDesc.setAssignTree(assignTree);
         toDesc.setRemap(NumberUtils.formatToInt(remap));
         spDesc.getToList().add(toDesc);
       }
       spDesc.setConDesc(readCondition(cond));
       wfDesc.putSplitDescriptor(id, spDesc);
     }
   }
 
   private void readStatuses(Element statuses, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
     if (statuses != null) {
       Iterator iter = statuses.elementIterator();
       while (iter.hasNext()) {
         Element status = (Element)iter.next();
         readStatus(status, wfDesc);
       }
     }
   }
 
   private void readStatus(Element status, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
     if (status != null) {
       String id = status.attributeValue("id");
       String name = status.attributeValue("name");
       String value = status.attributeValue("value");
       if (StringUtils.isBlank(id)) {
         throw new WorkflowDefineException(this.fileName + " status的id不能为空！\r\n" + status.asXML());
       }
       StatusDescriptor exist = wfDesc.getStatusDescriptor(id);
       if (exist != null) {
         throw new WorkflowDefineException(this.fileName + " 已经存在id为" + id + "的status！\r\n" + status.asXML());
       }
       StatusDescriptor stDesc = new StatusDescriptor();
       stDesc.setId(id);
       stDesc.setName(name);
       stDesc.setValue(value);
       stDesc.setParent(wfDesc);
       wfDesc.putStatusDescriptor(id, stDesc);
     }
   }
 
   private void readEnds(Element ends, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
     if (ends != null) {
       Iterator iter = ends.elementIterator();
       while (iter.hasNext()) {
         Element end = (Element)iter.next();
         readEnd(end, wfDesc);
       }
     }
   }
 
   private void readEnd(Element end, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
     if (end != null) {
       String id = end.attributeValue("id");
       String statusId = end.attributeValue("statusId");
       if (StringUtils.isBlank(id)) {
         throw new WorkflowDefineException(this.fileName + " end的id不能为空！\r\n" + end.asXML());
       }
       EndDescriptor exist = wfDesc.getEndDescriptor(id);
       if (exist != null) {
         throw new WorkflowDefineException(this.fileName + " 已经存在id为" + id + "的end！\r\n" + end.asXML());
       }
       EndDescriptor edDesc = new EndDescriptor();
       edDesc.setId(id);
       edDesc.setStatusId(statusId);
       edDesc.setParent(wfDesc);
       edDesc.setStatusName(wfDesc.getStatusDescriptor(statusId).getName());
       wfDesc.putEndDescriptor(id, edDesc);
     }
   }
 
   private void readSubflows(Element subflows, StepDescriptor stDesc) throws WorkflowDefineException {
     if (subflows != null) {
       SubflowsDescriptor subs = new SubflowsDescriptor();
 
       Element con = subflows.element("condition");
       subs.setConDesc(readCondition(con));
 
       Iterator iter = subflows.elementIterator("subflow");
       while (iter.hasNext()) {
         Element subflow = (Element)iter.next();
         readSubflow(subflow, subs);
       }
       stDesc.setSubflows(subs);
     }
   }
 
   private void readSubflow(Element sub, SubflowsDescriptor subs) throws WorkflowDefineException {
     String id = sub.attributeValue("id");
     String name = sub.attributeValue("name");
     String type = sub.attributeValue("type");
 
     SubflowDescriptor subDesc = new SubflowDescriptor(subs);
     subDesc.setId(id);
     subDesc.setName(name);
     subDesc.setType(type);
     subs.getSubflowMap().put(id, subDesc);
   }
 
   private ConditionDescriptor readCondition(Element con) {
     ConditionDescriptor conDesc = new ConditionDescriptor();
     String id = con.attributeValue("id");
     String type = con.attributeValue("type");
     String value = con.attributeValue("value");
     String text = con.getText();
     conDesc.setId(id);
     conDesc.setType(type);
     conDesc.setValue(value);
     conDesc.setText(text);
     return conDesc;
   }
 }


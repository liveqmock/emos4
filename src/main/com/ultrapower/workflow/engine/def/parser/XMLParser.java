/*     */ package com.ultrapower.workflow.engine.def.parser;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.util.NumberUtils;
/*     */ import com.ultrapower.workflow.engine.def.model.AbstractDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.ActionDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.ActorDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.ConditionDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.EndDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.FunctionDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.JoinDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.MetaDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.SplitDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.SplitToDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.StartDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.StatusDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.StepDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.SubflowDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.SubflowsDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.WorkflowDescriptor;
/*     */ import com.ultrapower.workflow.exception.WorkflowDefineException;
/*     */ import java.io.File;
/*     */ import java.net.URL;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class XMLParser
/*     */ {
/*  41 */   private static Logger log = LoggerFactory.getLogger(XMLParser.class);
/*     */ 
/*  43 */   private String fileName = null;
/*     */ 
/*  45 */   private File file = null;
/*     */ 
/*  47 */   private String xml = null;
/*     */ 
/*     */   public static void main(String[] args) throws Exception {
/*  50 */     String filePath = XMLParser.class.getResource("/workflows/NewFile.xml").getPath();
/*  51 */     File file = new File(filePath);
/*  52 */     XMLParser parser = new XMLParser(file);
/*  53 */     parser.parseFile();
/*     */   }
/*     */ 
/*     */   public XMLParser(File file) {
/*  57 */     this.file = file;
/*     */   }
/*     */ 
/*     */   public XMLParser(String fileName, String xml) {
/*  61 */     this.fileName = fileName;
/*  62 */     this.xml = xml;
/*     */   }
/*     */ 
/*     */   public WorkflowDescriptor parseString() throws WorkflowDefineException {
/*  66 */     log.info("开始解析流程定义文件 defName=" + this.fileName);
/*     */     try
/*     */     {
/*  69 */       doc = DocumentHelper.parseText(this.xml);
/*     */     }
/*     */     catch (DocumentException e)
/*     */     {
/*     */       Document doc;
/*  71 */       e.printStackTrace();
/*  72 */       throw new WorkflowDefineException("读取流程定义文件异常，请确认流程定义文件是否存在！fileName=" + this.fileName);
/*     */     }
/*     */     Document doc;
/*  74 */     WorkflowDescriptor wfDesc = parseDoc(doc);
/*  75 */     wfDesc.setName(this.fileName);
/*  76 */     return wfDesc;
/*     */   }
/*     */ 
/*     */   public WorkflowDescriptor parseFile() throws WorkflowDefineException {
/*  80 */     this.fileName = this.file.getName();
/*  81 */     SAXReader read = new SAXReader();
/*     */     try
/*     */     {
/*  84 */       doc = read.read(this.file);
/*     */     }
/*     */     catch (DocumentException e)
/*     */     {
/*     */       Document doc;
/*  86 */       e.printStackTrace();
/*  87 */       throw new WorkflowDefineException("读取流程定义文件异常，请确认流程定义文件是否存在！fileName=" + this.fileName);
/*     */     }
/*     */     Document doc;
/*  89 */     WorkflowDescriptor wfDesc = parseDoc(doc);
/*  90 */     wfDesc.setName(this.fileName);
/*  91 */     return wfDesc;
/*     */   }
/*     */ 
/*     */   private WorkflowDescriptor parseDoc(Document doc) throws WorkflowDefineException {
/*  95 */     WorkflowDescriptor wfDesc = new WorkflowDescriptor();
/*  96 */     if (doc != null) {
/*  97 */       Element root = doc.getRootElement();
/*  98 */       Element start = root.element("start");
/*  99 */       Element steps = root.element("steps");
/* 100 */       Element joins = root.element("joins");
/* 101 */       Element splits = root.element("splits");
/* 102 */       Element statuses = root.element("statuses");
/* 103 */       Element ends = root.element("ends");
/* 104 */       log.info("开始解析status节点");
/* 105 */       readStatuses(statuses, wfDesc);
/* 106 */       log.info("开始解析start节点");
/* 107 */       readStart(start, wfDesc);
/* 108 */       log.info("开始解析step节点");
/* 109 */       readSteps(steps, wfDesc);
/* 110 */       log.info("开始解析join节点");
/* 111 */       readJoins(joins, wfDesc);
/* 112 */       log.info("开始解析split节点");
/* 113 */       readSplits(splits, wfDesc);
/* 114 */       log.info("开始解析end节点");
/* 115 */       readEnds(ends, wfDesc);
/*     */     }
/* 117 */     log.info("解析完毕！");
/* 118 */     return wfDesc;
/*     */   }
/*     */ 
/*     */   private void readStart(Element start, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
/* 122 */     if (start == null) {
/* 123 */       throw new WorkflowDefineException(this.fileName + " 没有start节点！");
/*     */     }
/* 125 */     String to = start.attributeValue("to");
/* 126 */     if (StringUtils.isBlank(to)) {
/* 127 */       throw new WorkflowDefineException(this.fileName + " 开始节点没有to属性");
/*     */     }
/* 129 */     StartDescriptor stDesc = new StartDescriptor(wfDesc);
/* 130 */     stDesc.setTo(to);
/* 131 */     wfDesc.setStartDesc(stDesc);
/*     */   }
/*     */ 
/*     */   private void readSteps(Element steps, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
/* 135 */     if (steps != null) {
/* 136 */       Iterator iter = steps.elementIterator();
/* 137 */       while (iter.hasNext()) {
/* 138 */         Element step = (Element)iter.next();
/* 139 */         readStep(step, wfDesc);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readStep(Element step, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
/* 145 */     String stepId = step.attributeValue("id");
/* 146 */     String name = step.attributeValue("name");
/* 147 */     String desc = step.attributeValue("desc");
/* 148 */     String statusId = step.attributeValue("statusId");
/* 149 */     String assignOver = step.attributeValue("assignOver");
/* 150 */     String acceptOver = step.attributeValue("acceptOver");
/* 151 */     String dealOver = step.attributeValue("dealOver");
/* 152 */     String taskPolicy = step.attributeValue("taskPolicy");
/* 153 */     String auto = step.attributeValue("auto");
/* 154 */     String type = step.attributeValue("type");
/* 155 */     String stepNo = step.attributeValue("stepNo");
/* 156 */     String stepGroup = step.attributeValue("stepGroup");
/*     */ 
/* 158 */     if (StringUtils.isBlank(stepId)) {
/* 159 */       throw new WorkflowDefineException(this.fileName + " step节点id为空！\r\n" + step.asXML());
/*     */     }
/* 161 */     StepDescriptor exist = wfDesc.getStepDescriptor(stepId);
/* 162 */     if (exist != null) {
/* 163 */       throw new WorkflowDefineException(this.fileName + " 已经存在id为" + stepId + "的step节点！\r\n" + step.asXML());
/*     */     }
/* 165 */     Element prefuncs = step.element("prefuncs");
/* 166 */     Element actions = step.element("actions");
/* 167 */     Element metas = step.element("metas");
/* 168 */     Element subflows = step.element("subflows");
/* 169 */     Element postfuncs = step.element("postfuncs");
/* 170 */     Element actor = step.element("actor");
/*     */ 
/* 173 */     StepDescriptor stDesc = new StepDescriptor(wfDesc);
/* 174 */     readActor(actor, stDesc);
/* 175 */     readPrefunc(prefuncs, stDesc);
/* 176 */     readPostfunc(postfuncs, stDesc);
/* 177 */     readActions(actions, stDesc);
/* 178 */     readMetas(metas, stDesc);
/* 179 */     readSubflows(subflows, stDesc);
/* 180 */     stDesc.setId(stepId);
/* 181 */     stDesc.setName(name);
/* 182 */     stDesc.setDesc(desc);
/* 183 */     stDesc.setStatusId(statusId);
/* 184 */     stDesc.setStatusName(wfDesc.getStatusDescriptor(statusId).getName());
/* 185 */     stDesc.setDealOver(dealOver);
/* 186 */     stDesc.setAssignOver(assignOver);
/* 187 */     stDesc.setAcceptOver(acceptOver);
/* 188 */     stDesc.setTaskPolicy(taskPolicy);
/* 189 */     stDesc.setType(type);
/* 190 */     stDesc.setStepNo(stepNo);
/* 191 */     stDesc.setStepGroup(stepGroup);
/* 192 */     stDesc.setAuto(Boolean.parseBoolean(auto));
/* 193 */     wfDesc.putStepDescriptor(stepId, stDesc);
/*     */   }
/*     */ 
/*     */   private void readActor(Element actor, StepDescriptor stDesc) {
/* 197 */     if (actor != null) {
/* 198 */       ActorDescriptor acDesc = new ActorDescriptor(stDesc);
/* 199 */       String roleId = actor.attributeValue("roleID");
/* 200 */       String roleName = actor.attributeValue("roleName");
/* 201 */       String actorType = actor.attributeValue("actorType");
/* 202 */       String actorRule = actor.getText();
/* 203 */       acDesc.setRoleId(roleId);
/* 204 */       acDesc.setRoleName(roleName);
/* 205 */       acDesc.setRule(actorRule);
/* 206 */       acDesc.setActorType(actorType);
/* 207 */       stDesc.setActorDesc(acDesc);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readPrefunc(Element prefuncs, AbstractDescriptor absDesc) throws WorkflowDefineException {
/* 212 */     if (prefuncs != null) {
/* 213 */       Iterator iter = prefuncs.elementIterator();
/* 214 */       while (iter.hasNext()) {
/* 215 */         Element func = (Element)iter.next();
/*     */ 
/* 217 */         String funcId = func.attributeValue("id");
/* 218 */         if (StringUtils.isBlank(funcId)) {
/* 219 */           throw new WorkflowDefineException(this.fileName + " prefunc没有id！\r\n" + func.asXML());
/*     */         }
/* 221 */         String value = func.getText();
/* 222 */         FunctionDescriptor fuDesc = new FunctionDescriptor();
/* 223 */         fuDesc.setId(funcId);
/* 224 */         fuDesc.setValue(value);
/* 225 */         fuDesc.setParent(absDesc);
/* 226 */         if ((absDesc instanceof StepDescriptor)) {
/* 227 */           StepDescriptor stDesc = (StepDescriptor)absDesc;
/* 228 */           FunctionDescriptor exist = stDesc.getPreFuncById(funcId);
/* 229 */           if (exist != null) {
/* 230 */             throw new WorkflowDefineException(this.fileName + " 已经存在id为" + funcId + "的prefunc节点！\r\n" + func.asXML());
/*     */           }
/* 232 */           stDesc.putPreFuncMap(funcId, fuDesc);
/*     */         }
/* 234 */         if ((absDesc instanceof ActionDescriptor)) {
/* 235 */           ActionDescriptor acDesc = (ActionDescriptor)absDesc;
/* 236 */           FunctionDescriptor exist = acDesc.getPreFuncById(funcId);
/* 237 */           if (exist != null) {
/* 238 */             throw new WorkflowDefineException(this.fileName + " 已经存在id为" + funcId + "的prefunc节点！\r\n" + func.asXML());
/*     */           }
/* 240 */           acDesc.putPreFuncMap(funcId, fuDesc);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readPostfunc(Element postfuncs, AbstractDescriptor absDesc) throws WorkflowDefineException {
/* 247 */     if (postfuncs != null) {
/* 248 */       Iterator iter = postfuncs.elementIterator();
/* 249 */       while (iter.hasNext()) {
/* 250 */         Element func = (Element)iter.next();
/* 251 */         String funcId = func.attributeValue("id");
/* 252 */         if (StringUtils.isBlank(funcId)) {
/* 253 */           throw new WorkflowDefineException(this.fileName + " postfunc没有id！\r\n" + func.asXML());
/*     */         }
/* 255 */         String value = func.getText();
/* 256 */         FunctionDescriptor fuDesc = new FunctionDescriptor();
/* 257 */         fuDesc.setId(funcId);
/* 258 */         fuDesc.setValue(value);
/* 259 */         fuDesc.setParent(absDesc);
/* 260 */         if ((absDesc instanceof StepDescriptor)) {
/* 261 */           StepDescriptor stDesc = (StepDescriptor)absDesc;
/* 262 */           FunctionDescriptor exist = stDesc.getPostFuncById(funcId);
/* 263 */           if (exist != null) {
/* 264 */             throw new WorkflowDefineException(this.fileName + " 已经存在id为" + funcId + "的postfunc节点！\r\n" + func.asXML());
/*     */           }
/* 266 */           stDesc.putPostFuncMap(funcId, fuDesc);
/*     */         }
/* 268 */         if ((absDesc instanceof ActionDescriptor)) {
/* 269 */           ActionDescriptor acDesc = (ActionDescriptor)absDesc;
/* 270 */           FunctionDescriptor exist = acDesc.getPostFuncById(funcId);
/* 271 */           if (exist != null) {
/* 272 */             throw new WorkflowDefineException(this.fileName + " 已经存在id为" + funcId + "的postfunc节点！\r\n" + func.asXML());
/*     */           }
/* 274 */           acDesc.putPostFuncMap(funcId, fuDesc);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readActions(Element actions, StepDescriptor stDesc) throws WorkflowDefineException {
/* 281 */     if (actions != null) {
/* 282 */       Iterator iter = actions.elementIterator();
/* 283 */       while (iter.hasNext()) {
/* 284 */         Element action = (Element)iter.next();
/* 285 */         readAction(action, stDesc);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readAction(Element action, StepDescriptor stDesc) throws WorkflowDefineException {
/* 291 */     String id = action.attributeValue("id");
/* 292 */     if (StringUtils.isBlank(id)) {
/* 293 */       throw new WorkflowDefineException(this.fileName + " action的id为null!\r\n" + action.asXML());
/*     */     }
/* 295 */     ActionDescriptor exist = stDesc.getActionById(id);
/* 296 */     if (exist != null) {
/* 297 */       throw new WorkflowDefineException(this.fileName + " 已经存在id为" + id + "的action!\r\n" + action.asXML());
/*     */     }
/* 299 */     Element to = action.element("to");
/* 300 */     if (to == null) {
/* 301 */       throw new WorkflowDefineException(this.fileName + " id为" + id + "的action没有to元素!\r\n" + action.asXML());
/*     */     }
/* 303 */     String name = action.attributeValue("name");
/* 304 */     String linkpri = action.attributeValue("linkpri");
/* 305 */     String actionNo = action.attributeValue("actionNo");
/* 306 */     String remap = action.attributeValue("remap");
/* 307 */     Element prefuncs = action.element("prefuncs");
/* 308 */     Element postfuncs = action.element("postfuncs");
/* 309 */     String stepId = to.attributeValue("stepId");
/* 310 */     String joinId = to.attributeValue("joinId");
/* 311 */     String splitId = to.attributeValue("splitId");
/* 312 */     String endId = to.attributeValue("endId");
/* 313 */     ActionDescriptor acDesc = new ActionDescriptor();
/* 314 */     readPrefunc(prefuncs, acDesc);
/* 315 */     readPostfunc(postfuncs, acDesc);
/* 316 */     acDesc.setId(id);
/* 317 */     acDesc.setName(name);
/* 318 */     acDesc.setActionNo(actionNo);
/* 319 */     acDesc.setParent(stDesc);
/* 320 */     acDesc.setLinkpri(NumberUtils.formatToInt(linkpri));
/* 321 */     acDesc.setRemap(NumberUtils.formatToInt(remap));
/* 322 */     if (StringUtils.isNotBlank(stepId))
/* 323 */       acDesc.setToStepId(stepId);
/* 324 */     else if (StringUtils.isNotBlank(joinId))
/* 325 */       acDesc.setToJoinId(joinId);
/* 326 */     else if (StringUtils.isNotBlank(splitId))
/* 327 */       acDesc.setToSplitId(splitId);
/* 328 */     else if (StringUtils.isNotBlank(endId)) {
/* 329 */       acDesc.setToEndId(endId);
/*     */     }
/* 331 */     stDesc.putActionMap(id, acDesc);
/*     */   }
/*     */ 
/*     */   private void readMetas(Element metas, StepDescriptor stDesc) throws WorkflowDefineException {
/* 335 */     if (metas != null) {
/* 336 */       Iterator iter = metas.elementIterator();
/* 337 */       while (iter.hasNext()) {
/* 338 */         Element meta = (Element)iter.next();
/* 339 */         readMeta(meta, stDesc);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readMeta(Element meta, StepDescriptor stDesc) throws WorkflowDefineException {
/* 345 */     if (meta != null) {
/* 346 */       String id = meta.attributeValue("id");
/* 347 */       String name = meta.attributeValue("name");
/* 348 */       String value = meta.getText();
/* 349 */       if (StringUtils.isBlank(id)) {
/* 350 */         throw new WorkflowDefineException(this.fileName + " meta的id不能为空！\r\n" + meta.asXML());
/*     */       }
/*     */ 
/* 356 */       MetaDescriptor mtDesc = new MetaDescriptor(stDesc);
/* 357 */       mtDesc.setId(id);
/* 358 */       mtDesc.setName(name);
/* 359 */       mtDesc.setValue(value);
/*     */ 
/* 361 */       stDesc.getMetaList().add(mtDesc);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readJoins(Element joins, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
/* 366 */     if (joins != null) {
/* 367 */       Iterator iter = joins.elementIterator();
/* 368 */       while (iter.hasNext()) {
/* 369 */         Element join = (Element)iter.next();
/* 370 */         readJoin(join, wfDesc);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readJoin(Element join, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
/* 376 */     if (join != null) {
/* 377 */       String id = join.attributeValue("id");
/* 378 */       Element to = join.element("to");
/* 379 */       Element cond = join.element("condition");
/* 380 */       if (StringUtils.isBlank(id)) {
/* 381 */         throw new WorkflowDefineException(this.fileName + " join的id不能为空！\r\n" + join.asXML());
/*     */       }
/* 383 */       if (to == null) {
/* 384 */         throw new WorkflowDefineException(this.fileName + " join的to不能为空！\r\n" + join.asXML());
/*     */       }
/* 386 */       String stepId = to.attributeValue("stepId");
/* 387 */       String splitId = to.attributeValue("splitId");
/* 388 */       String endId = to.attributeValue("endId");
/* 389 */       if ((StringUtils.isBlank(stepId)) && (StringUtils.isBlank(splitId)) && (StringUtils.isBlank(endId))) {
/* 390 */         throw new WorkflowDefineException(this.fileName + " join的to元素的stepId,splitId,endId属性不能同时为空！\r\n" + join.asXML());
/*     */       }
/* 392 */       if (cond == null) {
/* 393 */         throw new WorkflowDefineException(this.fileName + " join的condition不能为空！\r\n" + join.asXML());
/*     */       }
/* 395 */       JoinDescriptor exist = wfDesc.getJoinDescriptor(id);
/* 396 */       if (exist != null) {
/* 397 */         throw new WorkflowDefineException(this.fileName + " id为" + id + "的join已经存在！\r\n" + join.asXML());
/*     */       }
/* 399 */       JoinDescriptor jnDesc = new JoinDescriptor(wfDesc);
/* 400 */       jnDesc.setId(id);
/* 401 */       jnDesc.setStepId(stepId);
/* 402 */       jnDesc.setSplitId(splitId);
/* 403 */       jnDesc.setEndId(endId);
/* 404 */       jnDesc.setParent(wfDesc);
/* 405 */       jnDesc.setConDesc(readCondition(cond));
/* 406 */       wfDesc.putJoinDescriptor(id, jnDesc);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readSplits(Element splits, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
/* 411 */     if (splits != null) {
/* 412 */       Iterator iter = splits.elementIterator();
/* 413 */       while (iter.hasNext()) {
/* 414 */         Element split = (Element)iter.next();
/* 415 */         readSplit(split, wfDesc);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readSplit(Element split, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
/* 421 */     if (split != null) {
/* 422 */       String id = split.attributeValue("id");
/* 423 */       if (StringUtils.isBlank(id)) {
/* 424 */         throw new WorkflowDefineException(this.fileName + " split的id不能为空！\r\n" + split.asXML());
/*     */       }
/* 426 */       SplitDescriptor exist = wfDesc.getSplitDescriptor(id);
/* 427 */       if (exist != null) {
/* 428 */         throw new WorkflowDefineException(this.fileName + " id为" + id + "的split已经存在！\r\n" + split.asXML());
/*     */       }
/* 430 */       Element tos = split.element("tos");
/* 431 */       if ((tos == null) || (tos.elementIterator() == null)) {
/* 432 */         throw new WorkflowDefineException(this.fileName + " id为" + id + "的split 不存在tos元素！\r\n" + split.asXML());
/*     */       }
/* 434 */       Element cond = split.element("condition");
/* 435 */       SplitDescriptor spDesc = new SplitDescriptor(wfDesc);
/* 436 */       spDesc.setId(id);
/* 437 */       Iterator it = tos.elementIterator("to");
/* 438 */       while (it.hasNext()) {
/* 439 */         Element to = (Element)it.next();
/* 440 */         String toId = to.attributeValue("id");
/* 441 */         String stepId = to.attributeValue("stepId");
/* 442 */         String splitId = to.attributeValue("splitId");
/* 443 */         String joinId = to.attributeValue("joinId");
/* 444 */         String endId = to.attributeValue("endId");
/* 445 */         String assignTree = to.attributeValue("assignTree");
/* 446 */         String remap = to.attributeValue("remap");
/* 447 */         SplitToDescriptor toDesc = new SplitToDescriptor(spDesc);
/* 448 */         toDesc.setId(toId);
/* 449 */         toDesc.setStepId(stepId);
/* 450 */         toDesc.setSplitId(splitId);
/* 451 */         toDesc.setJoinId(joinId);
/* 452 */         toDesc.setEndId(endId);
/* 453 */         toDesc.setAssignTree(assignTree);
/* 454 */         toDesc.setRemap(NumberUtils.formatToInt(remap));
/* 455 */         spDesc.getToList().add(toDesc);
/*     */       }
/* 457 */       spDesc.setConDesc(readCondition(cond));
/* 458 */       wfDesc.putSplitDescriptor(id, spDesc);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readStatuses(Element statuses, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
/* 463 */     if (statuses != null) {
/* 464 */       Iterator iter = statuses.elementIterator();
/* 465 */       while (iter.hasNext()) {
/* 466 */         Element status = (Element)iter.next();
/* 467 */         readStatus(status, wfDesc);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readStatus(Element status, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
/* 473 */     if (status != null) {
/* 474 */       String id = status.attributeValue("id");
/* 475 */       String name = status.attributeValue("name");
/* 476 */       String value = status.attributeValue("value");
/* 477 */       if (StringUtils.isBlank(id)) {
/* 478 */         throw new WorkflowDefineException(this.fileName + " status的id不能为空！\r\n" + status.asXML());
/*     */       }
/* 480 */       StatusDescriptor exist = wfDesc.getStatusDescriptor(id);
/* 481 */       if (exist != null) {
/* 482 */         throw new WorkflowDefineException(this.fileName + " 已经存在id为" + id + "的status！\r\n" + status.asXML());
/*     */       }
/* 484 */       StatusDescriptor stDesc = new StatusDescriptor();
/* 485 */       stDesc.setId(id);
/* 486 */       stDesc.setName(name);
/* 487 */       stDesc.setValue(value);
/* 488 */       stDesc.setParent(wfDesc);
/* 489 */       wfDesc.putStatusDescriptor(id, stDesc);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readEnds(Element ends, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
/* 494 */     if (ends != null) {
/* 495 */       Iterator iter = ends.elementIterator();
/* 496 */       while (iter.hasNext()) {
/* 497 */         Element end = (Element)iter.next();
/* 498 */         readEnd(end, wfDesc);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readEnd(Element end, WorkflowDescriptor wfDesc) throws WorkflowDefineException {
/* 504 */     if (end != null) {
/* 505 */       String id = end.attributeValue("id");
/* 506 */       String statusId = end.attributeValue("statusId");
/* 507 */       if (StringUtils.isBlank(id)) {
/* 508 */         throw new WorkflowDefineException(this.fileName + " end的id不能为空！\r\n" + end.asXML());
/*     */       }
/* 510 */       EndDescriptor exist = wfDesc.getEndDescriptor(id);
/* 511 */       if (exist != null) {
/* 512 */         throw new WorkflowDefineException(this.fileName + " 已经存在id为" + id + "的end！\r\n" + end.asXML());
/*     */       }
/* 514 */       EndDescriptor edDesc = new EndDescriptor();
/* 515 */       edDesc.setId(id);
/* 516 */       edDesc.setStatusId(statusId);
/* 517 */       edDesc.setParent(wfDesc);
/* 518 */       edDesc.setStatusName(wfDesc.getStatusDescriptor(statusId).getName());
/* 519 */       wfDesc.putEndDescriptor(id, edDesc);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readSubflows(Element subflows, StepDescriptor stDesc) throws WorkflowDefineException {
/* 524 */     if (subflows != null) {
/* 525 */       SubflowsDescriptor subs = new SubflowsDescriptor();
/*     */ 
/* 527 */       Element con = subflows.element("condition");
/* 528 */       subs.setConDesc(readCondition(con));
/*     */ 
/* 530 */       Iterator iter = subflows.elementIterator("subflow");
/* 531 */       while (iter.hasNext()) {
/* 532 */         Element subflow = (Element)iter.next();
/* 533 */         readSubflow(subflow, subs);
/*     */       }
/* 535 */       stDesc.setSubflows(subs);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readSubflow(Element sub, SubflowsDescriptor subs) throws WorkflowDefineException {
/* 540 */     String id = sub.attributeValue("id");
/* 541 */     String name = sub.attributeValue("name");
/* 542 */     String type = sub.attributeValue("type");
/*     */ 
/* 544 */     SubflowDescriptor subDesc = new SubflowDescriptor(subs);
/* 545 */     subDesc.setId(id);
/* 546 */     subDesc.setName(name);
/* 547 */     subDesc.setType(type);
/* 548 */     subs.getSubflowMap().put(id, subDesc);
/*     */   }
/*     */ 
/*     */   private ConditionDescriptor readCondition(Element con) {
/* 552 */     ConditionDescriptor conDesc = new ConditionDescriptor();
/* 553 */     String id = con.attributeValue("id");
/* 554 */     String type = con.attributeValue("type");
/* 555 */     String value = con.attributeValue("value");
/* 556 */     String text = con.getText();
/* 557 */     conDesc.setId(id);
/* 558 */     conDesc.setType(type);
/* 559 */     conDesc.setValue(value);
/* 560 */     conDesc.setText(text);
/* 561 */     return conDesc;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.def.parser.XMLParser
 * JD-Core Version:    0.6.0
 */
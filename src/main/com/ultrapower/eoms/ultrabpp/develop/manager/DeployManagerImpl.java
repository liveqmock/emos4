package com.ultrapower.eoms.ultrabpp.develop.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionMeta;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.cache.model.StepMeta;
import com.ultrapower.eoms.ultrabpp.cache.model.WorksheetMeta;
import com.ultrapower.eoms.ultrabpp.cache.service.MetaCacheService;
import com.ultrapower.eoms.ultrabpp.develop.model.TreeNode;
import com.ultrapower.eoms.ultrabpp.develop.service.DeployService;
import com.ultrapower.eoms.ultrabpp.develop.service.TemplateService;
import com.ultrapower.eoms.ultrabpp.model.BaseField;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDraw;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDrawFactory;
import com.ultrapower.eoms.ultrabpp.model.component.specialfield.attachmentfield.AttachmentDrawPC;
import com.ultrapower.eoms.ultrabpp.model.component.specialfield.attachmentfield.AttachmentField;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;

public class DeployManagerImpl implements DeployService {
	
private static Logger log = LoggerFactory.getLogger(DeployManagerImpl.class);
	
	protected IDao jdbcDao;
	
	private MetaCacheService metaCacheService;
	
	private TemplateService templateService;
	
	private static final String pageHead = "<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>"; 
	private static final String pageTaglib = "<%@include file=\"../../../theme/taglibs.jsp\"%>"; 
	public static final String RN = "\r\n"; 
	public static final String T = "\t"; 
	public static final String PRE_TAG = "<ubf:Preview/>"; 
	
	public void depAction(String baseSchema, String stepNo, String actionName, String baseStatus, String actionType) {
		String fileName = "";
		List<BaseField> actionFields = null;
		if (StringUtils.isNotBlank(stepNo)) {
			fileName = actionName;
			actionFields = templateService.actionFieldForPage(baseSchema, stepNo, actionName);
			createActionJsp(fileName, baseSchema, stepNo, actionName, baseStatus, actionType, actionFields, false);
		} else {
			List<String> baseStatusList = new ArrayList<String>();
			List<String> actionTypeList = new ArrayList<String>();
			List<String> stepNoList = new ArrayList<String>();
			//if (actionType.indexOf(",") > 0) {
				String[] split = actionType.split(",");
				if (split != null) {
					for (int i = 0; i < split.length; i++) {
						String[] split2 = split[i].split(":");
						if (!ArrayUtils.isEmpty(split2)) {
							baseStatusList.add(split2[0]);
							String[] split3 = split2[1].split("_");
							actionTypeList.add(split3[0]);
							if (split3.length ==2 ) {
								stepNoList.add(split3[1]);
							} else {
								stepNoList.add("");
							}
						}
					}
				}
			//} else {
			//	baseStatusList.add(baseStatus);
			//	actionTypeList.add(actionType);
			//	stepNoList.add("");
			//}
				
			Map<String, List<BaseField>> pages = templateService.freeActionFieldForPage(baseSchema, baseStatusList, actionTypeList, stepNoList);
			if (pages != null) {
				for (Iterator iterator = pages.keySet().iterator(); iterator.hasNext();) {
					String pageName = (String) iterator.next();
					List<BaseField> fieldList = pages.get(pageName);
					createActionJsp(pageName, baseSchema, stepNo, actionName, baseStatus, actionType, fieldList, false);
				}
			}
		}
		
//		if (StringUtils.isNotBlank(stepNo)) {
////			templateService.actionFieldChangeUpdate(baseSchema, stepNo, actionName);
//		} else {
//			
//		}
		
//		DataAdapter qAdapter = new DataAdapter();
//		String mainTable = "BS_F_" + baseSchema;
//		String modLogTable = "BS_F_" + baseSchema + "_FML";
//		Map<String, List<BaseField>> changeMap = templateService.actionFieldChangeMap(baseSchema, stepNo, actionName);
//		if (StringUtils.isNotBlank(stepNo)) {
//			changeMap = templateService.actionFieldChangeMap(baseSchema, stepNo, actionName);
//		} else {
//		}
//		List<String> rollbackSql = new ArrayList<String>();
//		List<String> commitSql = new ArrayList<String>();
//		try {
//			if (changeMap != null) {
//				List<BaseField> addList = changeMap.get("add");
//				List<BaseField> delList = changeMap.get("delete");
//				List<BaseField> modList = changeMap.get("update");
//				if (CollectionUtils.isNotEmpty(delList)) {
//					for (int i = 0; i < delList.size(); i++) {
//						BaseField dataField = delList.get(i);
//						String fieldName = dataField.getFieldName();
//						String delColSql = dataField.getDelColSql();
//						if (StringUtils.isNotBlank(delColSql)) {
//							String renameSql = "alter table "+mainTable+" rename column "+fieldName+" to " + fieldName + "_temp";
//							int execute = qAdapter.execute(renameSql, null);
//							if (execute == -1 ) {
//								throw new RuntimeException("删除字段前重命名字段失败！fieldName = " + fieldName);
//							}
//							commitSql.add("alter table "+mainTable+" drop column " + fieldName + "_temp");
//							rollbackSql.add("alter table "+mainTable+" rename column "+fieldName  + "_temp" +" to " + fieldName);
//						}
//					}
//				}
//				if (CollectionUtils.isNotEmpty(addList)) {
//					for (int i = 0; i < addList.size(); i++) {
//						BaseField dataField = addList.get(i);
//						String fieldName = dataField.getFieldName();
//						String addColSql = dataField.getAddColSql();
//						if (StringUtils.isNotBlank(addColSql)) {
//							int execute = qAdapter.execute("alter table "+mainTable+" add " + addColSql, null);
//							if (execute == -1 ) {
//								throw new RuntimeException("新增字段失败！fieldName = " + fieldName);
//							}
//							rollbackSql.add("alter table "+mainTable+" drop column " + fieldName);
//						}
//					}
//				}
//				if (CollectionUtils.isNotEmpty(modList)) {
//					for (int i = 0; i < modList.size(); i++) {
//						BaseField dataField = modList.get(i);
//						String fieldName = dataField.getFieldName();
//						String modColSql = dataField.getModColSql();
//						if (StringUtils.isNotBlank(modColSql)) {
//							int execute = qAdapter.execute("alter table "+mainTable+" modify " + modColSql, null);
//							if (execute == -1 ) {
//								throw new RuntimeException("修改字段失败！fieldName = " + fieldName);
//							}
//						}
//					}
//				}
//				if (CollectionUtils.isNotEmpty(commitSql)) {
//					for (int i = 0; i < commitSql.size(); i++) {
//						qAdapter.execute(commitSql.get(i), null);
//					}
//				}
//				if (StringUtils.isNotBlank(stepNo)) {
//					templateService.actionFieldChangeUpdate(baseSchema, stepNo, actionName);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (CollectionUtils.isNotEmpty(rollbackSql)) {
//				for (int i = 0; i < rollbackSql.size(); i++) {
//					qAdapter.execute(rollbackSql.get(i), null);
//				}
//			}
//			throw new RuntimeException("部署失败！");
//		}
	}

	public void depMain(String baseSchema, boolean isForce) {
		log.info("主表单部署,baseSchema=" + baseSchema + ",isForce=" + isForce);
		createFolder(baseSchema);
		createJs(baseSchema);
		createHiddenJsp(baseSchema);
		
		createMainJsp(baseSchema, false);
		
		DataAdapter qAdapter = new DataAdapter();
		String mainTable = "BS_F_" + baseSchema;
		String modLogTable = "BS_F_" + baseSchema + "_FML";
		Map<String, List<BaseField>> changeMap = templateService.worksheetFieldChangeMap(baseSchema);
		List<String> rollbackSql = new ArrayList<String>();
		List<String> commitSql = new ArrayList<String>();
		try {
			if (changeMap != null) {
				List<BaseField> addList = changeMap.get("add");
				List<BaseField> delList = changeMap.get("delete");
				List<BaseField> modList = changeMap.get("update");
				if (CollectionUtils.isNotEmpty(delList)) {
					for (int i = 0; i < delList.size(); i++) {
						BaseField dataField = delList.get(i);
//						if (dataField instanceof DataField) {
//							DataField df = (DataField) dataField;
							String fieldName = dataField.getFieldName();
							String delColSql = dataField.getDelColSql();
							
							if (StringUtils.isNotBlank(delColSql)) {
								String renameSql = "alter table "+mainTable+" rename column "+fieldName+" to " + fieldName + "_temp";
								int execute = qAdapter.execute(renameSql, null);
								if (execute == -1 ) {
//									throw new RuntimeException("删除字段前重命名字段失败！fieldName = " + fieldName);
								} else {
									commitSql.add("alter table "+mainTable+" drop column " + fieldName + "_temp");
									rollbackSql.add("alter table "+mainTable+" rename column "+fieldName  + "_temp" +" to " + fieldName);
								}
							}
//						}
					}
				}
				if (CollectionUtils.isNotEmpty(addList)) {
					for (int i = 0; i < addList.size(); i++) {
						BaseField dataField = addList.get(i);
//						if (dataField instanceof DataField) {
//							DataField df = (DataField) dataField;
							String fieldName = dataField.getFieldName();
							String addColSql = dataField.getAddColSql();
							System.out.println("addColSql:" + addColSql);
							if (StringUtils.isNotBlank(addColSql)) {
								int execute = qAdapter.execute("alter table "+mainTable+" add " + addColSql, null);
								if (execute == -1 ) {
									throw new RuntimeException("新增字段失败！fieldName = " + fieldName);
								}
								rollbackSql.add("alter table "+mainTable+" drop column " + fieldName);
							}
//						}
					}
				}
				if (CollectionUtils.isNotEmpty(modList)) {
					for (int i = 0; i < modList.size(); i++) {
						BaseField dataField = modList.get(i);
//						if (dataField instanceof DataField) {
//							DataField df = (DataField) dataField;
							String fieldName = dataField.getFieldName();
							String modColSql = dataField.getModColSql();
//							Integer needSave = df.getNeedSave();
							if (StringUtils.isNotBlank(modColSql)) {
								int execute = qAdapter.execute("alter table "+mainTable+" modify " + modColSql, null);
								if (execute == -1 ) {
									throw new RuntimeException("修改字段失败！fieldName = " + fieldName);
								}
							}
//						}
					}
				}
				if (CollectionUtils.isNotEmpty(commitSql)) {
					for (int i = 0; i < commitSql.size(); i++) {
						qAdapter.execute(commitSql.get(i), null);
					}
				}
				templateService.worksheetFieldChangeUpdate(baseSchema);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (CollectionUtils.isNotEmpty(rollbackSql)) {
				for (int i = 0; i < rollbackSql.size(); i++) {
					qAdapter.execute(rollbackSql.get(i), null);
				}
			}
			throw new RuntimeException("部署失败！");
		}
	}
	
	public void depAll(String baseSchema) {
		IWfSortManager ver = WorkFlowServiceClient.clientInstance().getSortService();
		try {
			WfType wfType = ver.getWfTypeByCode(baseSchema);
			long type = wfType.getWfType();
			if (type == 1) {
				WorksheetMeta wkMeta = templateService.getWorksheetMeta(baseSchema);
				if (wkMeta != null) {
					depMain(baseSchema, true);
					List<StepMeta> stMetas = wkMeta.getSteps();
					if (CollectionUtils.isNotEmpty(stMetas)) {
						for (int i = 0; i < stMetas.size(); i++) {
							StepMeta stMeta = stMetas.get(i);
							List<ActionMeta> acMetas = stMeta.getActions();
							if (CollectionUtils.isNotEmpty(acMetas)) {
								for (int j = 0; j < acMetas.size(); j++) {
									ActionMeta acMeta = acMetas.get(j);
									ActionModel actionModel = acMeta.getActionModel();
									String stepNo = actionModel.getStepNo();
									Integer hasNext = actionModel.getHasNext();
									String actionName = actionModel.getActionName();
									String actionType = actionModel.getActionType();
									if (hasNext == 1) {
										depAction(baseSchema, stepNo, actionName, null, actionType);
									}
								}
							}
						}
					}
				}
			} else {
				depMain(baseSchema, true);
				List<Object[]> statusAndAction = templateService.getStatusAndActionType(baseSchema);
				if (CollectionUtils.isNotEmpty(statusAndAction)) {
					for (int i = 0; i < statusAndAction.size(); i++) {
						Object[] arys = statusAndAction.get(i);
						if (!ArrayUtils.isEmpty(arys)) {
							depAction(baseSchema, null, null, null, arys[0] + ":" + arys[1] + "_");
						}
					}
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createFolder(String baseSchema) {
		try {
			FileUtils.forceMkdir(new File(getPath(baseSchema, "action")));
			FileUtils.forceMkdir(new File(getPath(baseSchema, "main")));
			FileUtils.forceMkdir(new File(getPath(baseSchema, "script")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createJs(String baseSchema) {
		File jsFile = new File(getPath(baseSchema, "script") + baseSchema + ".js");
		try {
			if (!jsFile.exists()) {
				jsFile.createNewFile();
				OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(jsFile), "utf-8");
				out.write("function bpp_init() {");
				out.write(RN);
				out.write(RN);
				out.write("}");
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void recuTreeNode(TreeNode treeNode, StringBuffer html, int c) {
		if (treeNode != null) {
			BaseField baseField = treeNode.getBaseField();
			BaseFieldDraw draw = BaseFieldDrawFactory.getDraw(baseField);
			if (draw != null) {
				html.append(countTab(c));
				if (draw instanceof AttachmentDrawPC) {
					html.append(((AttachmentDrawPC)draw).doStartTag((AttachmentField)baseField, countTab(c)));
				} else {
					html.append(draw.doStartTag(baseField));
				}
			}
			List<TreeNode> chiNodes = treeNode.getChiNodes();
			if (CollectionUtils.isNotEmpty(chiNodes)) {
				int d = c + 1;
				for (int i = 0; i < chiNodes.size(); i++) {
					recuTreeNode(chiNodes.get(i), html, d);
				}
			}
			if (draw != null) {
				String doEndTag = draw.doEndTag(baseField);
				if (StringUtils.isNotBlank(doEndTag)) {
					html.append(countTab(c));
					html.append(doEndTag);
				}
			}
		}
	}
	
	private String countTab(int c) {
		String tab = "";
		for (int i = 0; i < c; i++) {
			tab += T;
		}
		return tab;
	}
	
	private void createHiddenJsp(String baseSchema) {
		File jsFile = new File(getPath(baseSchema, "main") + "hidden.jsp");
		try {
			if (!jsFile.exists()) {
				jsFile.createNewFile();
				OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(jsFile), "utf-8");
				out.write(pageHead);
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getPath(String baseSchema, String type) {
		String webRoot = SwfuploadUtil.APP_ROOT_REALPATH;
		String runTimePath = webRoot + File.separator + "ultrabpp" + File.separator + "runtime" + File.separator;
		String formsPath = runTimePath + "forms" + File.separator + baseSchema + File.separator; 
		String actionPath = formsPath + "action" + File.separator;
		String mainPath = formsPath + "main" + File.separator;
		String scriptPath = formsPath + "script" + File.separator;
		if ("action".equals(type)) {
			return actionPath;
		} else if ("main".equals(type)) {
			return mainPath;
		} else if ("script".equals(type)) {
			return 	scriptPath;
		} else if ("theme".equals(type)) {
			try {
				IWfSortManager ver = WorkFlowServiceClient.clientInstance().getSortService();
				WfType wfType = ver.getWfTypeByCode(baseSchema);
				String theme = wfType.getTheme();
				return runTimePath + File.separator + "theme" + File.separator + theme + File.separator;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String prevAction(String baseSchema, String stepNo, String actionName, String baseStatus, String actionType) {
		String fileName = "";
		List<BaseField> actionFields = null;
		if (StringUtils.isNotBlank(stepNo)) {
			fileName = actionName;
			actionFields = templateService.actionFieldForPage(baseSchema, stepNo, actionName);
		} else {
			fileName = actionType;
			actionFields = templateService.freeActionFieldDeploy(baseSchema, baseStatus, actionType);
		}
		return createActionJsp(fileName, baseSchema, stepNo, actionName, baseStatus, actionType, actionFields, true);
	}
	
	private String createActionJsp(String fileName, String baseSchema, String stepNo, String actionName, String baseStatus, String actionType, List<BaseField> actionFields, boolean isPreview) {
		createFolder(baseSchema);
		String filePath = getPath(baseSchema, "action");
		if (isPreview) {
			fileName = "delete_only_" + TimeUtils.getCurrentTime();
			filePath = getPath(baseSchema, "theme");
		}
		try {
			if(fileName.indexOf("_")!=-1){
				fileName = fileName.substring(fileName.indexOf("_")+1, fileName.length());
			}
			File file = new File( filePath + fileName + ".jsp");
			if (file.exists()) {
				FileUtils.copyFile(file, new File(filePath + fileName + ".jsp_bak"));
				file.delete();
			}
			file.createNewFile();
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			StringBuffer html = new StringBuffer();
			html.append(pageHead + RN);
			if (isPreview) { 
				html.append("<html>" + RN);
				html.append(countTab(1) + "<%@include file=\"../taglibs.jsp\" %>" + RN);
				html.append(countTab(1) + "<%@include file=\"../header.jsp\" %>" + RN);
				html.append(countTab(1) + "<%@include file=\"style.jsp\" %>" + RN);
				html.append(countTab(1) + "<body>" + RN);
				html.append(countTab(2) + "<div id=\"bpp_FormContainer\">" + RN);
				html.append(countTab(3) + "<div id=\"bpp_ActPanel\" style=\"display:block\">" + RN);
				html.append(countTab(4) + "<div style=\"height:5px;\"></div>" + RN);
				html.append(countTab(4) + "<div id=\"bpp_ActBody\">" + RN);
				html.append(countTab(5) + "<div id=\"bpp_ActComment\"></div>" + RN);
				html.append(countTab(5) + "<div id=\"bpp_ActFields\">" + RN);
				if (CollectionUtils.isNotEmpty(actionFields)) {
					for (int i = 0; i < actionFields.size(); i++) {
						BaseField baseField = actionFields.get(i);
						BaseFieldDraw draw = BaseFieldDrawFactory.getDraw(baseField);
						String tagStr = draw.doStartTag(baseField);
						if (StringUtils.isNotBlank(tagStr)) {
							html.append(countTab(6) + tagStr);
						}
					}
				}
				html.append(countTab(5) + "</div>" + RN);
				html.append(countTab(5) + "<div class=\"bpp_White_Block\"></div>" + RN);
				html.append(countTab(4) + "</div>" + RN);
				html.append(countTab(3) + "</div>" + RN);
				html.append(countTab(2) + "</div>" + RN);
				html.append(countTab(1) + "</body>" + RN);
				html.append("</html>" + RN);
				html.append(PRE_TAG);
			} else {
				html.append(countTab(0) + "<%@include file=\"../../../theme/taglibs.jsp\" %>" + RN);
				if (CollectionUtils.isNotEmpty(actionFields)) {
					for (int i = 0; i < actionFields.size(); i++) {
						BaseField baseField = actionFields.get(i);
						if (baseField != null) {
							BaseFieldDraw draw = BaseFieldDrawFactory.getDraw(baseField);
							String tagStr = draw.doStartTag(baseField);
							if (StringUtils.isNotBlank(tagStr)) {
								html.append(countTab(0) + tagStr);
							}
						}
					}
				}
			}
			out.write(html.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}

	public String prevMain(String baseSchema) {
		return createMainJsp(baseSchema, true);
	}
	
	private String createMainJsp(String baseSchema, boolean isPreview) {
		createFolder(baseSchema);
		TreeNode treeNode = templateService.worksheetFieldForPage(baseSchema);
		String fileName = "main";
		if (isPreview) {
			fileName = "delete_only_" + TimeUtils.getCurrentTime();
		}
		try {
			File jsFile = new File(getPath(baseSchema, "main") + fileName + ".jsp");
			if (jsFile.exists()) {
				FileUtils.copyFile(jsFile, new File(getPath(baseSchema, "main") + fileName + ".jsp_bak"));
				jsFile.delete();
			}
			jsFile.createNewFile();
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(jsFile), "utf-8");
			StringBuffer html = new StringBuffer();
			html.append(pageHead);
			html.append(RN);
			html.append(pageTaglib);
			html.append(RN);
			if (treeNode != null) {
				recuTreeNode(treeNode, html, 0);
			}
			out.write(html.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	public IDao getJdbcDao() {
		return jdbcDao;
	}

	public void setJdbcDao(IDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	public MetaCacheService getMetaCacheService() {
		return metaCacheService;
	}

	public void setMetaCacheService(MetaCacheService metaCacheService) {
		this.metaCacheService = metaCacheService;
	}

	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

}

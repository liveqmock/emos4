package com.ultrapower.eoms.ultrabpp.cache.manager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
//import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
//import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Constructor;

import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.fileutil.FileOperUtil;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionMeta;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.cache.model.AttributeConfig;
import com.ultrapower.eoms.ultrabpp.cache.model.ExportInfo;
import com.ultrapower.eoms.ultrabpp.cache.model.FieldConfig;
import com.ultrapower.eoms.ultrabpp.cache.model.FreeActionFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.StepFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.StepMeta;
import com.ultrapower.eoms.ultrabpp.cache.model.StepModel;
import com.ultrapower.eoms.ultrabpp.cache.model.WfTypeExportInfo;
import com.ultrapower.eoms.ultrabpp.cache.model.WorksheetMeta;
import com.ultrapower.eoms.ultrabpp.cache.service.ExportService;
import com.ultrapower.eoms.ultrabpp.model.BaseField;
import com.ultrapower.eoms.ultrabpp.model.component.compositefield.panel.PanelField;
import com.ultrapower.eoms.workflow.design.control.FlowMapConfig;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.configuration.version.model.WfVersion;
import com.ultrapower.workflow.exception.WorkflowException;
import com.ultrapower.workflow.utils.UUIDGenerator;

/**
 * @author fying
 * @version
 * @since
 * @see TemplateDoc
 */
public class ExportXmlImpl implements ExportService
{

	private IDao hibernateDao;
	private IDao jdbcDao;
	private String tmpPath = ExportXmlImpl.class.getResource("/").getPath();
	private final String folderPath = "export_tmp";
	private final String txtfilePath = "workflow/export/readme.txt";
	private final String WORKSHEET_CACHEKEY = "bppWorksheetMap";
	private final String FIELDINFO_CACHEKEY = "bppFieldInfoMap";

	public void exportBegin(String[] baseSchemaStrings, String wfinfo, String fieldExport, String stepExport, String wfdesign, String jsppage)
	{
		// 创建export文件夹

		FileOperUtil.newFolder(tmpPath + folderPath);
		System.out.println("创建文件夹成功");
		// 创建导出操作文件export_info.xml
		this.exportInfo(baseSchemaStrings, wfinfo, fieldExport, stepExport, wfdesign, jsppage);
		// 复制readme.txt文件
		try
		{
			FileOperUtil.copyFile(tmpPath + txtfilePath, tmpPath + folderPath + "/readme.txt");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 根据工单类型和导出文件选项导出文件，生成xml文件，拷贝流程定义文件，拷贝工单jsp文件
	 */
	public void export(String baseSchema, String wfinfo, String fieldExport, String stepExport, String wfdesign, String jsppage)
	{
		// 在export文件夹下创建 以工单类型命名的 文件夹
		FileOperUtil.newFolder(tmpPath + folderPath + "/" + baseSchema);
		// 导出工单类型信息
		this.exportWfinfo(baseSchema);

		// 导出表单配置字段信息
		if ("1".equals(fieldExport))
		{
			this.exportFieldInfo(baseSchema);
		}
		// 导出环节配置信息
		if ("1".equals(stepExport))
		{
			this.exportStepInfo(baseSchema);
		}
		// 导出流程图定义文件
		if ("1".equals(wfdesign))
		{
			// 加载数据
			WfType wftype = (WfType) hibernateDao.findUnique("from WfType where code=?", new String[] { baseSchema });

			if (wftype.getWfType() == 1)
				this.exportWfdesign(baseSchema);
		}
		// 导出jsp页面文件
		if ("1".equals(jsppage))
		{
			this.exportJsppage(baseSchema);
		}
	}

	public void exportEnd()
	{

		// 将export文件夹压缩为export.zip
		try
		{
			File inFile = new File(tmpPath + folderPath);
			FileOutputStream fileOut = new FileOutputStream(tmpPath + "export.zip");
			CheckedOutputStream checkOut = new CheckedOutputStream(fileOut, new CRC32());
			ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(checkOut));
			zipOut.setComment("表单配置压缩包");
			zip(zipOut, inFile, "", checkOut);
			zipOut.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// 压缩成功后，将export文件夹删除
		FileOperUtil.delFolder(tmpPath + folderPath);
	}

	public void loadExportInfo(ExportInfo exportInfo, Document doc)
	{

		List root = doc.selectNodes("//exports");
		if (CollectionUtils.isNotEmpty(root))
		{
			for (int i = 0; i < root.size(); i++)
			{
				Node rootNode = (Node) root.get(i);
				exportInfo.setWfinfo(rootNode.valueOf("@wfinfo"));
				exportInfo.setFieldexport(rootNode.valueOf("@fieldexport"));
				exportInfo.setStepexport(rootNode.valueOf("@stepexport"));
				exportInfo.setWfdesign(rootNode.valueOf("@wfdesign"));
				exportInfo.setJsppage(rootNode.valueOf("@jsppage"));
			}
		}

		Map<String, WfTypeExportInfo> map = exportInfo.getWftypeMap();
		if (map == null)
		{
			map = new HashMap<String, WfTypeExportInfo>();
			exportInfo.setWftypeMap(map);
		}
		List nodes = doc.selectNodes("//exports/export");
		if (CollectionUtils.isNotEmpty(nodes))
		{
			for (int i = 0; i < nodes.size(); i++)
			{
				Node node = (Node) nodes.get(i);
				String baseSchema = node.valueOf("@baseSchema");
				WfTypeExportInfo info = map.get(baseSchema);
				if (info == null)
				{
					info = new WfTypeExportInfo();
					map.put(baseSchema, info);
				}
				info.setWfinfo(exportInfo.getWfinfo());
				info.setFieldexport(exportInfo.getFieldexport());
				info.setStepexport(exportInfo.getStepexport());
				info.setWfdesign(exportInfo.getWfdesign());
				info.setJsppage(exportInfo.getJsppage());
			}
		}
	}

	public void loadWorkflowInfo(String baseSchema, ExportInfo exportInfo, Document doc)
	{
		Map<String, WfTypeExportInfo> map = exportInfo.getWftypeMap();
		if (map == null)
		{
			map = new HashMap<String, WfTypeExportInfo>();
			exportInfo.setWftypeMap(map);
		}
		WfTypeExportInfo wfTypeExportInfo = (WfTypeExportInfo) map.get(baseSchema);
		if (wfTypeExportInfo == null)
		{
			wfTypeExportInfo = new WfTypeExportInfo();
			map.put(baseSchema, wfTypeExportInfo);
		}
		List root = doc.selectNodes("//workflow");
		if (CollectionUtils.isNotEmpty(root))
		{
			for (int i = 0; i < root.size(); i++)
			{
				Node rootNode = (Node) root.get(i);
				WfType wftype = new WfType();
				wftype.setId(rootNode.valueOf("@id"));
				wftype.setSortId(rootNode.valueOf("@sortId"));
				wftype.setCode(rootNode.valueOf("@code"));
				wftype.setName(rootNode.valueOf("@name"));
				wftype.setWfCountType(rootNode.valueOf("@wfCountType"));
				wftype.setWfType(new Long(rootNode.valueOf("@wfType")));
				wftype.setWfDefaultVersion(rootNode.valueOf("@wfDefaultVersion"));
				wftype.setBaseCategoryCode(rootNode.valueOf("@baseCategoryCode"));
				wftype.setBaseCategoryIsDefaultFix(new Long(rootNode.valueOf("@baseCategoryIsDefaultFix")));
				wftype.setBaseCategoryState(new Long(rootNode.valueOf("@baseCategoryState")));
				wftype.setPanelGroupID(rootNode.valueOf("@panelGroupID"));
				wftype.setTheme(rootNode.valueOf("@theme"));
				wfTypeExportInfo.setWftype(wftype);
			}
		}
		List<WfVersion> versionList = new ArrayList<WfVersion>();
		List nodes = doc.selectNodes("//workflow/version");
		if (CollectionUtils.isNotEmpty(root))
		{
			for (int i = 0; i < nodes.size(); i++)
			{
				Node node = (Node) nodes.get(i);
				WfVersion wfVersion = new WfVersion();
				wfVersion.setId(node.valueOf("@id"));
				wfVersion.setBaseCode(node.valueOf("@baseCode"));
				wfVersion.setBaseName(node.valueOf("@baseName"));
				wfVersion.setCode(node.valueOf("@code"));
				wfVersion.setIsPublish(new Long(node.valueOf("@isPublish")));
				wfVersion.setName(node.valueOf("@name"));
				wfVersion.setSubflag(new Long(node.valueOf("@subflag")));
				wfVersion.setPublishTime(new Long(node.valueOf("@publishTime")));
				wfVersion.setRemark(node.valueOf("@remark"));
				wfVersion.setCreateTime(new Long(node.valueOf("@createTime")));

				if (wfVersion.getIsPublish() == 1)
				{
					wfTypeExportInfo.setPublishVersion(wfVersion.getCode());
				}

				if ((i == nodes.size() - 1) && wfTypeExportInfo.getPublishVersion() == null)
				{
					wfTypeExportInfo.setPublishVersion(wfVersion.getCode());
				}
				versionList.add(wfVersion);
			}
		}

		wfTypeExportInfo.setVersionList(versionList);

	}

	public void importWorkflowInfo(ExportInfo exportInfo)
	{
		// 导入数据
		Map<String, WfTypeExportInfo> map = exportInfo.getWftypeMap();
		ZipFile zipFile = null;
		File file = null;
		try
		{
			file = new File(ExportXmlImpl.class.getResource("/").getPath() + "export.zip");
			zipFile = new ZipFile(file);
			if(zipFile==null){
				zipFile = exportInfo.getZipfile();
			}
			Enumeration entries = zipFile.getEntries();//.entries();

			ZipEntry entry;
			InputStream is = null;
			SAXReader reader = new SAXReader();
			reader.setEncoding("UTF-8");

			WfTypeExportInfo wfTypeExportInfo = new WfTypeExportInfo();
			while (entries.hasMoreElements())
			{
				entry = (ZipEntry) entries.nextElement();
				is = zipFile.getInputStream(entry);//.getInputStream(entry);

				String entryName = entry.getName();
				String baseSchema = "";
				if (entryName.indexOf("/") > -1)
				{
					baseSchema = entryName.substring(0, entryName.indexOf("/"));
				}
				if (baseSchema.length() > 0)
				{
					wfTypeExportInfo = map.get(baseSchema);
				}
				// 流程分类信息
				if (entryName.contains("workflow_info.xml") && "1".equals(wfTypeExportInfo.getWfinfo()))
				{
					Document doc = reader.read(is);
					importWorkflowInfo(baseSchema, wfTypeExportInfo);
				}
				// 环节配置信息
				if (entryName.contains("steps_info.xml") && "1".equals(wfTypeExportInfo.getStepexport()))
				{
					Document doc = reader.read(is);
					importStepsInfo(baseSchema, doc);
				}
				// 状态动作配置信息
				if (entryName.contains("status_action_info.xml") && "1".equals(wfTypeExportInfo.getStepexport()))
				{
					Document doc = reader.read(is);
					importFreeactionInfo(baseSchema, doc);
				}
				// 表单字段配置信息
				if (entryName.contains("fields_info.xml") && "1".equals(wfTypeExportInfo.getFieldexport()))
				{
					Document doc = reader.read(is);
					importFieldsInfo(baseSchema, doc);
				}
				// jsp文件
				if (entryName.contains("/jsp/") && entryName.contains(".js") && "1".equals(wfTypeExportInfo.getJsppage()))
				{
					String webRoot = SwfuploadUtil.APP_ROOT_REALPATH;
					String runTimePath = webRoot + "/ultrabpp/runtime/";
					String formsPath = runTimePath + "forms/";
					File outFile = new File(formsPath + entryName.replace("jsp/", ""));
					if (!outFile.getParentFile().exists())
					{
						outFile.getParentFile().mkdirs();
					}
					if (!outFile.exists())
					{
						outFile.createNewFile();
					}
					FileOutputStream output = new FileOutputStream(outFile);
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = is.read(b)) != -1)
					{
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
				}
				// 流程定义文件
				if (entryName.contains("/workflows/") && entryName.contains(".xml") && "1".equals(wfTypeExportInfo.getWfdesign()))
				{
					String selectVersion = wfTypeExportInfo.getPublishVersion();
					String versionNo = entryName.substring(entryName.lastIndexOf("/") + 1, entryName.length());
					String tempVersionNo = versionNo.replace("_design.xml", "").replace(".xml", "");
					if (selectVersion.contains(tempVersionNo))
					{
						String folderURL = tmpPath + "wfengine/workflows/" + baseSchema;
						File outFile = new File(folderURL + "/" + versionNo);
						if (!outFile.getParentFile().exists())
						{
							outFile.getParentFile().mkdirs();
						}
						if (!outFile.exists())
						{
							outFile.createNewFile();
						}
						FileOutputStream output = new FileOutputStream(outFile);
						byte[] b = new byte[1024 * 5];
						int len;
						while ((len = is.read(b)) != -1)
						{
							output.write(b, 0, len);
						}
						output.flush();
						output.close();
					}
				}
			}
			is.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void importWorkflowInfo(ExportInfo exportInfo, boolean force)
	{
		if (force)
		{
			Map<String, WfTypeExportInfo> map = exportInfo.getWftypeMap();
			Set<String> keys = map.keySet();
			for (String key : keys)
			{
				WfTypeExportInfo wfTypeExportInfo = map.get(key);
				if ("1".equals(wfTypeExportInfo.getFieldexport()))
				{

					// drop BS_F_ 删除开头的主表 创建新表
					this.createWorkSheetTable(key);
					this.createModLogTable(key);
					// 删除 与工单关联的信息
					try
					{
						DataAdapter qAdapter = new DataAdapter();
						String sql = "delete from bs_t_wf_historysteps where id in (select distinct h.id from bs_t_wf_dealprocess d, bs_t_wf_historysteps h where d.entryid = h.entryid and d.baseschema = '" + key + "')";
						qAdapter.execute(sql, null);
						sql = "delete from bs_t_wf_currentsteps where id in (select distinct c.id from bs_t_wf_dealprocess d, bs_t_wf_currentsteps c where d.entryid = c.entryid and d.baseschema ='" + key + "')";
						qAdapter.execute(sql, null);
						sql = "delete from bs_t_wf_entry where id in (select distinct e.id  from bs_t_wf_dealprocess d, bs_t_wf_entry e  where d.entryid = e.id  and d.baseschema ='" + key + "')";
						qAdapter.execute(sql, null);
						sql = "delete from bs_t_wf_notice t where t.baseschema='" + key + "'";
						qAdapter.execute(sql, null);
						sql = "delete from bs_t_wf_historytask t where t.baseschema='" + key + "'";
						qAdapter.execute(sql, null);
						sql = "delete from bs_t_wf_dealprocesslog t where t.baseschema='" + key + "'";
						qAdapter.execute(sql, null);
						sql = "delete from bs_t_wf_dealprocess t where t.baseschema='" + key + "'";
						qAdapter.execute(sql, null);
						sql = "delete from bs_t_wf_currenttask t where t.baseschema='" + key + "'";
						qAdapter.execute(sql, null);

					} catch (Error e)
					{
					}
				}
			}

			Map<String, List<BaseField>> fieldmap = this.getNewField(exportInfo);
			keys = fieldmap.keySet();

			DataAdapter qAdapter = new DataAdapter();
			for (String key : keys)
			{
				List<BaseField> list = fieldmap.get(key);
				String mainTable = "BS_F_" + key;

				for (int i = 0; i < list.size(); i++)
				{
					BaseField dataField = list.get(i);
					String fieldName = dataField.getFieldName();
					String addColSql = dataField.getAddColSql();
					if (StringUtils.isNotBlank(addColSql))
					{
						qAdapter.execute("alter table " + mainTable + " add " + addColSql, null);
					}
				}
			}
		}
		importWorkflowInfo(exportInfo);
	}

	public Map<String, Map<String, List<BaseField>>> contrastOldAndNewField(ExportInfo exportInfo, String fieldImportBaschema)
	{
		Map<String, Document> docMap = new HashMap<String, Document>();
		//String filePath = exportInfo.getFileURL();
		File file = null;
		ZipFile zipFile;
		try
		{
			
			file = new File(ExportXmlImpl.class.getResource("/").getPath() + "export.zip");
			zipFile = new ZipFile(file);
			if(zipFile==null){
				zipFile = exportInfo.getZipfile();
			}
			Enumeration entries = zipFile.getEntries();//.entries();
			ZipEntry entry;
			InputStream is = null;
			SAXReader reader = new SAXReader();
			reader.setEncoding("UTF-8");
			while (entries.hasMoreElements())
			{
				entry = (ZipEntry) entries.nextElement();
				is = zipFile.getInputStream(entry);

				String entryName = entry.getName();
				String baseSchema = "";
				if (entryName.indexOf("/") > -1)
				{
					baseSchema = entryName.substring(0, entryName.indexOf("/"));
				}
				if (entryName.contains("fields_info.xml"))
				{
					Document doc = reader.read(is);
					docMap.put(baseSchema, doc);
				}

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		// 封装字段对比的map
		Map<String, Map<String, List<BaseField>>> map = new HashMap<String, Map<String, List<BaseField>>>();

		Map<String, WorksheetMeta> metaMap = (Map<String, WorksheetMeta>) BaseCacheManager.get(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY);
		String[] baseSchemaArray = fieldImportBaschema.split(",");
		for (String baseSchema : baseSchemaArray)
		{
			Map<String, List<BaseField>> baseSchemaMap = new HashMap<String, List<BaseField>>();

			List<BaseField> addList = new ArrayList<BaseField>();
			List<BaseField> delList = new ArrayList<BaseField>();
			List<BaseField> modList = new ArrayList<BaseField>();
			baseSchemaMap.put("add", addList);
			baseSchemaMap.put("delete", delList);
			baseSchemaMap.put("update", modList);

			// 将xml整理为字段Map，供之后调用对比
			Map<String, BaseField> newFieldMap = this.getFieldMapByDoc(docMap.get(baseSchema));

			// 获取导出文件的配置信息
			WfTypeExportInfo wfTypeExportInfo = exportInfo.getWftypeMap().get(baseSchema);

			// 获取缓存中的流程配置信息
			WorksheetMeta worksheetMeta = metaMap.get(baseSchema);
			if (worksheetMeta != null)
			{
				// 缓存中的字段信息
				Map<String, BaseField> fieldsMap = worksheetMeta.getFields();
				// 缓存中的动作字段信息
				Map<String, BaseField> actionfieldsMap = worksheetMeta.getActionFields();

				// 遍历原始字段信息 遍历一次将需要删除和修改的字段统计出来
				Set<String> keys = fieldsMap.keySet();
				for (String key : keys)
				{
					BaseField baseFieldOld = fieldsMap.get(key);
					BaseField baseFieldNew = newFieldMap.get(key);
					if (baseFieldNew == null)
					{
						delList.add(baseFieldOld);
					} else if (baseFieldOld.getFieldType().equals(baseFieldNew.getFieldType()))
					{
						modList.add(baseFieldNew);
					} else
					{
						delList.add(baseFieldOld);
						addList.add(baseFieldNew);
					}
				}

				// 遍历原始动作字段信息 遍历一次将需要删除和修改的字段统计出来
				keys = actionfieldsMap.keySet();
				for (String key : keys)
				{
					BaseField baseFieldOld = actionfieldsMap.get(key);
					BaseField baseFieldNew = newFieldMap.get(key);
					if (baseFieldNew == null)
					{
						delList.add(baseFieldOld);
					} else if (baseFieldOld.getFieldType().equals(baseFieldNew.getFieldType()))
					{
						modList.add(baseFieldNew);
					} else
					{
						delList.add(baseFieldOld);
						addList.add(baseFieldNew);
					}
				}

				// 将修改字段列表 与 newFieldMap进行对比 不在修改字段列表里的字段 就是
				keys = newFieldMap.keySet();
				for (String key : keys)
				{
					BaseField baseFieldNew = newFieldMap.get(key);
					if (!modList.contains(baseFieldNew) && !addList.contains(baseFieldNew))
					{
						addList.add(baseFieldNew);
					}
				}
			}
			map.put(baseSchema, baseSchemaMap);
		}
		return map;
	}

	public Map<String, List<BaseField>> getNewField(ExportInfo exportInfo)
	{
		Map<String, Document> docMap = new HashMap<String, Document>();
		//String filePath = exportInfo.getFileURL();
		File file = null;
		ZipFile zipFile = null;
		
		InputStream is = null;
		try
		{
			file = new File(ExportXmlImpl.class.getResource("/").getPath() + "export.zip");
			zipFile = new ZipFile(file);
			if(zipFile==null){
				zipFile = exportInfo.getZipfile();
			}
			Enumeration entries = zipFile.getEntries();//.entries();
			ZipEntry entry;
			
			SAXReader reader = new SAXReader();
			reader.setEncoding("UTF-8");
			while (entries.hasMoreElements())
			{
				entry = (ZipEntry) entries.nextElement();
				is = zipFile.getInputStream(entry);
				String entryName = entry.getName();
				String baseSchema = "";
				if (entryName.indexOf("/") > -1)
				{
					baseSchema = entryName.substring(0, entryName.indexOf("/"));
				}
				if (entryName.contains("fields_info.xml"))
				{
					Document doc = reader.read(is);
					docMap.put(baseSchema, doc);
				}

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			
				try
				{
					if(is!=null)is.close();
					if(zipFile!=null)zipFile.close();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}

		// 封装字段对比的map
		Map<String, List<BaseField>> map = new HashMap<String, List<BaseField>>();

		Set<String> keys = exportInfo.getWftypeMap().keySet();
		for (String key : keys)
		{
			WfTypeExportInfo wfTypeExportInfo = exportInfo.getWftypeMap().get(key);
			if ("1".equals(wfTypeExportInfo.getFieldexport()))
			{
				List<BaseField> newFieldList = new ArrayList<BaseField>();
				map.put(key, newFieldList);

				// 将xml整理为字段Map，供之后调用对比
				Map<String, BaseField> newFieldMap = this.getFieldMapByDoc(docMap.get(key));
				Set<String> fieldkeys = newFieldMap.keySet();
				for (String fieldkey : fieldkeys)
				{
					newFieldList.add(newFieldMap.get(fieldkey));
				}
			}
		}
		return map;
	}

	public Map<String, List<String>> getFieldSql(Map<String, Map<String, List<BaseField>>> baseSchemaChangeMap)
	{
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Set<String> keys = baseSchemaChangeMap.keySet();
		for (String key : keys)
		{
			Map<String, List<BaseField>> changeMap = baseSchemaChangeMap.get(key);
			List<BaseField> addList = changeMap.get("add");
			List<BaseField> delList = changeMap.get("delete");
			List<BaseField> modList = changeMap.get("update");

			String mainTable = "BS_F_" + key;
			List<String> commitSql = new ArrayList<String>();
			if (CollectionUtils.isNotEmpty(delList))
			{
				for (int i = 0; i < delList.size(); i++)
				{
					BaseField dataField = delList.get(i);
					String fieldName = dataField.getFieldName();
					String delColSql = dataField.getDelColSql();

					if (StringUtils.isNotBlank(delColSql))
					{
						commitSql.add("alter table " + mainTable + " drop column " + fieldName);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(addList))
			{
				for (int i = 0; i < addList.size(); i++)
				{
					BaseField dataField = addList.get(i);
					String fieldName = dataField.getFieldName();
					String addColSql = dataField.getAddColSql();
					if (StringUtils.isNotBlank(addColSql))
					{
						commitSql.add("alter table " + mainTable + " add " + addColSql);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(modList))
			{
				for (int i = 0; i < modList.size(); i++)
				{
					BaseField dataField = modList.get(i);
					String fieldName = dataField.getFieldName();
					String modColSql = dataField.getModColSql();
					if (StringUtils.isNotBlank(modColSql))
					{
						commitSql.add("alter table " + mainTable + " modify " + modColSql);
					}
				}
			}

			map.put(key, commitSql);

		}

		return map;
	}

	private void exportWfinfo(String baseSchema)
	{
		// 在工单分类对应目录下 创建workflow_info.xml文件
		// 加载数据
		WfType wftype = (WfType) hibernateDao.findUnique("from WfType where code=?", new String[] { baseSchema });
		List<WfVersion> versionList = hibernateDao.find("from WfVersion where baseCode=?", new String[] { baseSchema });
		// 封装xml
		Document doc = DocumentHelper.createDocument();
		Element workflow = doc.addElement("workflow");

		try
		{
			Class wfClass;
			wfClass = Class.forName("com.ultrapower.workflow.configuration.sort.model.WfType");
			Field[] fields = wfClass.getDeclaredFields();
			for (Field field : fields)
			{
				String name = field.getName();
				Object value = wfClass.getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1)).invoke(wftype);
				if (value instanceof String)
				{

					workflow.addAttribute(name, (String) value);
				} else if (value instanceof Long)
				{
					workflow.addAttribute(name, (Long) value + "");
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		for (WfVersion wfVersion : versionList)
		{
			Element version = workflow.addElement("version");
			try
			{
				Class versionClass = Class.forName("com.ultrapower.workflow.configuration.version.model.WfVersion");
				Field[] fields = versionClass.getDeclaredFields();
				for (Field field : fields)
				{
					String name = field.getName();
					Object value = versionClass.getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1)).invoke(wfVersion);
					if (value instanceof String)
					{

						version.addAttribute(name, (String) value);
					} else if (value instanceof Long)
					{
						version.addAttribute(name, (Long) value + "");
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		// 生成xml文件
		try
		{
			FileOperUtil.newXMLFile(tmpPath + folderPath + File.separator + baseSchema + File.separator + "workflow_info.xml", doc);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	private void exportFieldInfo(String baseSchema)
	{
		// 在工单分类对应目录下 创建fields_info.xml文件
		// 首先从缓存中获取所有表单配置信息
		Map<String, WorksheetMeta> metaMap = (Map<String, WorksheetMeta>) BaseCacheManager.get(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY);
		WorksheetMeta worksheetMeta = metaMap.get(baseSchema);
		Map<String, BaseField> fieldMap = worksheetMeta.getFields();
		Map<String, BaseField> actionFieldMap = worksheetMeta.getActionFields();

		// 从缓存中得到 字段配置信息
		Map<String, FieldConfig> fieldInfoMap = (Map<String, FieldConfig>) BaseCacheManager.get(FIELDINFO_CACHEKEY, FIELDINFO_CACHEKEY);

		// 封装xml
		Document doc = DocumentHelper.createDocument();
		Element fieldsElement = doc.addElement("fields");

		Set<String> keys = fieldMap.keySet();
		// 封装主表单字段
		for (String key : keys)
		{
			Element fieldElement = fieldsElement.addElement("field");
			BaseField baseField = fieldMap.get(key);
			FieldConfig fieldConfig = fieldInfoMap.get(baseField.getFieldType());
			try
			{
				Class fieldClass = Class.forName(fieldConfig.getFullName());
				// 封装通用属性
				List<AttributeConfig> attList = fieldConfig.getAttrList();
				for (AttributeConfig attributeConfig : attList)
				{
					String name = attributeConfig.getName();
					Object value = fieldClass.getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1)).invoke(baseField);
					if (value instanceof String)
					{
						fieldElement.addAttribute(name, (String) value);
					} else if (value instanceof Long)
					{
						fieldElement.addAttribute(name, (Long) value + "");
					} else if (value instanceof Integer)
					{
						fieldElement.addAttribute(name, (Integer) value + "");
					}
				}
				// 封装私有属性

				Field[] fields = fieldClass.getDeclaredFields();
				for (Field field : fields)
				{
					String name = field.getName();
					Object value = fieldClass.getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1)).invoke(baseField);
					if (value instanceof String)
					{
						fieldElement.addAttribute(name, (String) value);
					} else if (value instanceof Long)
					{
						fieldElement.addAttribute(name, (Long) value + "");
					} else if (value instanceof Integer)
					{
						fieldElement.addAttribute(name, (Integer) value + "");
					}
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		keys = actionFieldMap.keySet();
		// 封装动作表单字段
		for (String key : keys)
		{
			Element fieldElement = fieldsElement.addElement("field");
			BaseField baseField = actionFieldMap.get(key);
			FieldConfig fieldConfig = fieldInfoMap.get(baseField.getFieldType());
			try
			{
				Class fieldClass = Class.forName(fieldConfig.getFullName());
				// 封装通用属性
				List<AttributeConfig> attList = fieldConfig.getAttrList();
				for (AttributeConfig attributeConfig : attList)
				{
					String name = attributeConfig.getName();
					Object value = fieldClass.getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1)).invoke(baseField);
					if (value instanceof String)
					{
						fieldElement.addAttribute(name, (String) value);
					} else if (value instanceof Long)
					{
						fieldElement.addAttribute(name, (Long) value + "");
					} else if (value instanceof Integer)
					{
						fieldElement.addAttribute(name, (Integer) value + "");
					}
				}
				// 封装私有属性
				Field[] fields = fieldClass.getDeclaredFields();
				for (Field field : fields)
				{
					String name = field.getName();
					Object value = fieldClass.getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1)).invoke(baseField);
					if (value instanceof String)
					{
						fieldElement.addAttribute(name, (String) value);
					} else if (value instanceof Long)
					{
						fieldElement.addAttribute(name, (Long) value + "");
					} else if (value instanceof Integer)
					{
						fieldElement.addAttribute(name, (Integer) value + "");
					}
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}

		// 生成xml文件
		try
		{
			FileOperUtil.newXMLFile(tmpPath + folderPath + File.separator + baseSchema + File.separator + "fields_info.xml", doc);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	private void exportStepInfo(String baseSchema)
	{
		// 在工单分类对应目录下 创建steps_info.xml文件
		// 首先从缓存中获取所有表单配置信息
		Map<String, WorksheetMeta> metaMap = (Map<String, WorksheetMeta>) BaseCacheManager.get(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY);
		WorksheetMeta worksheetMeta = metaMap.get(baseSchema);
		List<StepMeta> stepMetaList = worksheetMeta.getSteps();
		List<FreeActionFieldRelationModel> freeActionList = worksheetMeta.getFreeActionFields();

		// 封装xml
		Document doc = DocumentHelper.createDocument();
		Element stepsElement = doc.addElement("steps");
		for (StepMeta stepMeta : stepMetaList)
		{
			Element stepElement = stepsElement.addElement("step");
			// 封装step节点属性
			StepModel stepModel = stepMeta.getStepModel();
			try
			{
				Class stepModelClass = Class.forName(StepModel.class.getPackage().getName() + ".StepModel");
				// 封装属性
				Field[] fields = stepModelClass.getDeclaredFields();
				for (Field field : fields)
				{
					String name = field.getName();
					Object value = stepModelClass.getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1)).invoke(stepModel);
					if (value instanceof String)
					{
						stepElement.addAttribute(name, (String) value);
					} else if (value instanceof Integer)
					{
						stepElement.addAttribute(name, (Integer) value + "");
					}
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			// 封装环节配置字段信息
			Element fieldrels = stepElement.addElement("fieldrels");
			List<StepFieldRelationModel> fieldrelList = stepMeta.getFields();
			for (StepFieldRelationModel fieldRelationModel : fieldrelList)
			{
				Element fieldrelElement = fieldrels.addElement("fieldrel");
				fieldrelElement.addAttribute("id", fieldRelationModel.getId());
				fieldrelElement.addAttribute("stepNo", fieldRelationModel.getStepNo());
				fieldrelElement.addAttribute("baseSchema", fieldRelationModel.getBaseSchema());
				fieldrelElement.addAttribute("fieldID", fieldRelationModel.getFieldID());
				fieldrelElement.addAttribute("visiable", fieldRelationModel.getVisiable() + "");
				fieldrelElement.addAttribute("required", fieldRelationModel.getRequired() + "");
				fieldrelElement.addAttribute("fieldType", fieldRelationModel.getFieldType());
			}

			// 封装环节对应的动作
			Element actionsElement = stepElement.addElement("actions");
			List<ActionMeta> actions = stepMeta.getActions();
			for (ActionMeta actionMeta : actions)
			{
				ActionModel actionModel = actionMeta.getActionModel();
				Element actionElement = actionsElement.addElement("action");
				actionElement.addAttribute("id", actionModel.getId());
				actionElement.addAttribute("stepNo", actionModel.getStepNo());
				actionElement.addAttribute("baseSchema", actionModel.getBaseSchema());
				actionElement.addAttribute("label", actionModel.getLabel());
				actionElement.addAttribute("actionName", actionModel.getActionName());
				actionElement.addAttribute("actionType", actionModel.getActionType());
				actionElement.addAttribute("isFree", actionModel.getIsFree() + "");
				actionElement.addAttribute("hasNext", actionModel.getHasNext() + "");
				actionElement.addAttribute("description", actionModel.getDescription());
				actionElement.addAttribute("isClose", actionModel.getIsClose() + "");
				actionElement.addAttribute("hasDeploy", actionModel.getHasDeploy() + "");
				actionElement.addAttribute("operate", actionModel.getOperate());

				// 封装动作配置字段信息
				List<ActionFieldRelationModel> fieldRelList = actionMeta.getFields();
				Element fieldrelsElement = actionElement.addElement("fieldrels");
				for (ActionFieldRelationModel actionFieldRelationModel : fieldRelList)
				{
					Element fieldrelElement = fieldrelsElement.addElement("fieldrel");
					fieldrelElement.addAttribute("id", actionFieldRelationModel.getId());
					fieldrelElement.addAttribute("stepNo", actionFieldRelationModel.getStepNo());
					fieldrelElement.addAttribute("baseSchema", actionFieldRelationModel.getBaseSchema());
					fieldrelElement.addAttribute("actionName", actionFieldRelationModel.getActionName());
					fieldrelElement.addAttribute("fieldID", actionFieldRelationModel.getFieldID());
					fieldrelElement.addAttribute("visiable", actionFieldRelationModel.getVisiable() + "");
					fieldrelElement.addAttribute("required", actionFieldRelationModel.getRequired() + "");
					fieldrelElement.addAttribute("fieldType", actionFieldRelationModel.getFieldType());
				}
			}

		}

		// 生成xml文件
		try
		{
			FileOperUtil.newXMLFile(tmpPath + folderPath + File.separator + baseSchema + File.separator + "steps_info.xml", doc);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		exportFreeactionInfo(baseSchema);
		
	}

	private void exportFreeactionInfo(String baseSchema){
		Map<String, WorksheetMeta> metaMap = (Map<String, WorksheetMeta>) BaseCacheManager.get(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY);
		WorksheetMeta worksheetMeta = metaMap.get(baseSchema);
		
		List<FreeActionFieldRelationModel> freeActionList = worksheetMeta.getFreeActionFields();
		// 封装xml
		Document doc = DocumentHelper.createDocument();
		Element freerelsElement = doc.addElement("freerels");
		for (FreeActionFieldRelationModel freeRelmodel : freeActionList)
		{
			Element freerelElement = freerelsElement.addElement("freerel");
			// 封装step节点属性
			freerelElement.addAttribute("id",freeRelmodel.getId());
			freerelElement.addAttribute("actionType",freeRelmodel.getActionType());
			freerelElement.addAttribute("baseSchema",freeRelmodel.getBaseSchema());
			freerelElement.addAttribute("baseStatus",freeRelmodel.getBaseStatus());
			freerelElement.addAttribute("fieldID",freeRelmodel.getFieldID());
			freerelElement.addAttribute("fieldType",freeRelmodel.getFieldType());
			freerelElement.addAttribute("required",freeRelmodel.getRequired()+"");
			freerelElement.addAttribute("visiable",freeRelmodel.getVisiable()+"");
			
		}

		// 生成xml文件
		try
		{
			FileOperUtil.newXMLFile(tmpPath + folderPath + File.separator + baseSchema + File.separator + "status_action_info.xml", doc);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	private void exportWfdesign(String baseSchema)
	{
		// 在工单分类对应目录下 创建wfdesign文件夹
		String copyFolderURL = tmpPath + "wfengine" + File.separator + "workflows" + File.separator + baseSchema;
		String folderURL = tmpPath + folderPath + File.separator + baseSchema + File.separator + "workflows";
		File a = new File(copyFolderURL);
		String[] file = a.list();
		if (file != null && file.length > 0)
			FileOperUtil.copyFolderExcept(copyFolderURL, folderURL, ".svn");

	}

	private void exportJsppage(String baseSchema)
	{
		// 在工单分类对应目录下 创建jsp文件夹
		String webRoot = SwfuploadUtil.APP_ROOT_REALPATH;
		String runTimePath = webRoot + File.separator + "ultrabpp" + File.separator + "runtime" + File.separator;
		String formsPath = runTimePath + "forms" + File.separator + baseSchema;
		String folderURL = tmpPath + folderPath + File.separator + baseSchema + File.separator + "jsp";
		File a = new File(formsPath);
		String[] file = a.list();
		if (file != null && file.length > 0)
			FileOperUtil.copyFolderExcept(formsPath, folderURL, ".svn");
	}

	private void exportInfo(String[] baseSchemaStrings, String wfinfo, String fieldexport, String stepexport, String wfdesign, String jsppage)
	{
		// 生成导出的信息文件

		// xml文件整理
		Document doc = DocumentHelper.createDocument();
		Element exports = doc.addElement("exports");

		exports.addAttribute("wfinfo", wfinfo);
		exports.addAttribute("fieldexport", fieldexport);
		exports.addAttribute("stepexport", stepexport);
		exports.addAttribute("wfdesign", wfdesign);
		exports.addAttribute("jsppage", jsppage);

		for (int i = 0; i < baseSchemaStrings.length; i++)
		{
			String baseSchema = baseSchemaStrings[i];
			Element export = exports.addElement("export");
			export.addAttribute("baseSchema", baseSchema);
		}
		// 生成xml文件
		try
		{
			FileOperUtil.newXMLFile(tmpPath + folderPath + "/export_info.xml", doc);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 导入流程分类信息
	 * 
	 * @param baseSchema
	 * @param doc
	 */
	private void importWorkflowInfo(String baseSchema, WfTypeExportInfo wfTypeExportInfo)
	{

		String selectVersion = wfTypeExportInfo.getPublishVersion();
		try
		{
			IWfSortManager sortService = WorkFlowServiceClient.clientInstance().getSortService();
			WfType wfType = sortService.getWfTypeByCode(baseSchema);

			List<WfVersion> versionList = hibernateDao.find("from WfVersion where baseCode=?", new String[] { baseSchema });
			if (wfType != null)
			{
				wfType.setPanelGroupID(wfTypeExportInfo.getWftype().getPanelGroupID());
				sortService.saveWfType(wfType);
			}

			if (versionList.size() > 0)
			{
				for (WfVersion version : versionList)
				{
					String versionCode = version.getCode();
					if (selectVersion.contains(versionCode))
					{
						selectVersion = selectVersion.replace(versionCode, "");
					}
				}
			}
			String[] versionArray = selectVersion.split(",");
			for (int i = 0; i < versionArray.length; i++)
			{
				String versionCode = versionArray[i];
				if (versionArray[i].length() > 0)
				{
					WfVersion wfVersion = new WfVersion();
					List<WfVersion> versions = wfTypeExportInfo.getVersionList();
					for (WfVersion version : versions)
					{
						if (versionCode.equals(version.getCode()))
						{
							wfVersion.setId(version.getId());
							wfVersion.setBaseCode(version.getBaseCode());
							wfVersion.setBaseName(version.getBaseName());
							wfVersion.setCode(version.getCode());
							wfVersion.setCreateTime(version.getCreateTime());
							wfVersion.setEntryCount(0);
							wfVersion.setIsPublish(0);
							wfVersion.setName(version.getName());
							wfVersion.setRemark(version.getRemark());
							wfVersion.setSubflag(version.getSubflag());
							hibernateDao.save(wfVersion);
						}
					}
				}
			}
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

	private void importStepsInfo(String baseSchema, Document doc)
	{
		// 环节导入逻辑相对复杂
		// 清理环节信息
		hibernateDao.executeUpdate("delete from StepModel where baseSchema='" + baseSchema + "'");
		// 清理环节关联字段信息
		hibernateDao.executeUpdate("delete from StepFieldRelationModel where baseSchema='" + baseSchema + "'");
		// 清理环节动作信息
		hibernateDao.executeUpdate("delete from ActionModel where baseSchema='" + baseSchema + "'");
		// 清理环节动作关联字段信息
		hibernateDao.executeUpdate("delete from ActionFieldRelationModel where baseSchema='" + baseSchema + "'");
		// 解析doc文件
		Element root = doc.getRootElement();
		for (Iterator i = root.elementIterator(); i.hasNext();)
		{

			Element stepElement = (Element) i.next();
			StepModel stepModel = new StepModel();
			// 导入环节信息
			stepModel.setId(stepElement.attributeValue("id"));
			stepModel.setStepNo(stepElement.attributeValue("stepNo"));
			stepModel.setAcceptOver(stepElement.attributeValue("acceptOver"));
			stepModel.setActionType(stepElement.attributeValue("actionType"));
			stepModel.setAssignee(stepElement.attributeValue("assignee"));
			stepModel.setAssigneeID(stepElement.attributeValue("assigneeID"));
			stepModel.setBaseSchema(baseSchema);
			stepModel.setContextKey(stepElement.attributeValue("contextKey"));
			stepModel.setDealOver(stepElement.attributeValue("dealOver"));
			stepModel.setDescription(stepElement.attributeValue("description"));
			stepModel.setGroupID(stepElement.attributeValue("groupID"));
			stepModel.setGroupName(stepElement.attributeValue("groupName"));
			stepModel.setHasDeploy(1);
			stepModel.setHasSubFlow(new Integer(stepElement.attributeValue("hasSubFlow")));
			stepModel.setOperate("add");
			stepModel.setRoleID(stepElement.attributeValue("roleID"));
			stepModel.setRoleKey(stepElement.attributeValue("roleKey"));
			stepModel.setRoleName(stepElement.attributeValue("roleName"));
			stepModel.setRoleProcessRoleType(stepElement.attributeValue("roleProcessRoleType"));
			stepModel.setTaskPolicy(stepElement.attributeValue("taskPolicy"));
			hibernateDao.save(stepModel);
			// 导入环节关联字段信息
			Element stepRelsElment = stepElement.element("fieldrels");
			for (Iterator j = stepRelsElment.elementIterator(); j.hasNext();)
			{
				Element stepRelElment = (Element) j.next();
				StepFieldRelationModel stepRelModel = new StepFieldRelationModel();
				stepRelModel.setId(stepRelElment.attributeValue("id"));
				stepRelModel.setBaseSchema(baseSchema);
				stepRelModel.setFieldID(stepRelElment.attributeValue("fieldID"));
				stepRelModel.setFieldType(stepRelElment.attributeValue("fieldType"));
				stepRelModel.setRequired(Integer.parseInt(stepRelElment.attributeValue("required")));
				stepRelModel.setStepNo(stepRelElment.attributeValue("stepNo"));
				stepRelModel.setVisiable(Integer.parseInt(stepRelElment.attributeValue("visiable")));
				hibernateDao.save(stepRelModel);
			}
			// 导入环节动作信息
			Element actionsElment = stepElement.element("actions");
			for (Iterator j = actionsElment.elementIterator(); j.hasNext();)
			{
				Element actionElment = (Element) j.next();
				ActionModel actionModel = new ActionModel();
				// 导入环节动作关联字段信息
				actionModel.setActionName(actionElment.attributeValue("actionName"));
				actionModel.setActionType(actionElment.attributeValue("actionType"));
				actionModel.setBaseSchema(baseSchema);
				actionModel.setDescription(actionElment.attributeValue("description"));
				actionModel.setHasDeploy(1);
				actionModel.setHasNext(new Integer(actionElment.attributeValue("hasNext")));
				actionModel.setId(actionElment.attributeValue("id"));
				actionModel.setIsClose(new Integer(actionElment.attributeValue("isClose")));
				actionModel.setIsFree(new Integer(actionElment.attributeValue("isFree")));
				actionModel.setLabel(actionElment.attributeValue("label"));
				actionModel.setOperate("add");
				actionModel.setStepNo(actionElment.attributeValue("stepNo"));
				hibernateDao.save(actionModel);
				Element actionRelsElment = actionElment.element("fieldrels");// .selectNodes("//fieldrels/fieldrel");
				for (Iterator k = actionRelsElment.elementIterator(); k.hasNext();)
				{
					Element actionRelElment = (Element) k.next();
					ActionFieldRelationModel actionRelModel = new ActionFieldRelationModel();
					actionRelModel.setActionName(actionRelElment.attributeValue("actionName"));
					actionRelModel.setBaseSchema(baseSchema);
					actionRelModel.setFieldID(actionRelElment.attributeValue("fieldID"));
					actionRelModel.setFieldType(actionRelElment.attributeValue("fieldType"));
					actionRelModel.setId(actionRelElment.attributeValue("id"));
					actionRelModel.setRequired(new Integer(actionRelElment.attributeValue("required")));
					actionRelModel.setStepNo(actionRelElment.attributeValue("stepNo"));
					actionRelModel.setVisiable(new Integer(actionRelElment.attributeValue("visiable")));
					hibernateDao.save(actionRelModel);
				}
			}
		}

		
	}

	private void importFreeactionInfo(String baseSchema, Document doc){
		// 清理状态动作信息
		hibernateDao.executeUpdate("delete from FreeActionFieldRelationModel where baseSchema='" + baseSchema + "'");
		
		// 解析doc文件
		Element root = doc.getRootElement();
		for (Iterator i = root.elementIterator(); i.hasNext();)
		{

			Element freeActionFieldRelElement = (Element) i.next();
			FreeActionFieldRelationModel freeActionFieldRelationModel = new FreeActionFieldRelationModel();
			
			freeActionFieldRelationModel.setId(freeActionFieldRelElement.attributeValue("id"));
			freeActionFieldRelationModel.setActionType(freeActionFieldRelElement.attributeValue("actionType"));
			freeActionFieldRelationModel.setBaseSchema(freeActionFieldRelElement.attributeValue("baseSchema"));
			freeActionFieldRelationModel.setBaseStatus(freeActionFieldRelElement.attributeValue("baseStatus"));
			freeActionFieldRelationModel.setFieldID(freeActionFieldRelElement.attributeValue("fieldID"));
			freeActionFieldRelationModel.setFieldType(freeActionFieldRelElement.attributeValue("fieldType"));
			freeActionFieldRelationModel.setRequired(Integer.parseInt(freeActionFieldRelElement.attributeValue("required")));
			freeActionFieldRelationModel.setVisiable(Integer.parseInt(freeActionFieldRelElement.attributeValue("visiable")));
			hibernateDao.save(freeActionFieldRelationModel);
		}

	}
	private void importFieldsInfo(String baseSchema, Document doc)
	{
		// 字段导入开始
		// 清理字段数据
		Map<String, FieldConfig> fieldInfoMap = (Map<String, FieldConfig>) BaseCacheManager.get(FIELDINFO_CACHEKEY, FIELDINFO_CACHEKEY);
		Set<String> keys = fieldInfoMap.keySet();
		for (String key : keys)
		{
			FieldConfig fieldConfig = fieldInfoMap.get(key);
			hibernateDao.executeUpdate("delete from " + fieldConfig.getName() + " where baseSchema=?", baseSchema);
		}

		// 解析xml文件，导入字段
		List<Node> fields = doc.selectNodes("//fields/field");
		for (Node field : fields)
		{
			String fieldType = field.valueOf("@fieldType");
			FieldConfig fieldConfig = fieldInfoMap.get(fieldType);
			try
			{

				Class fieldClass = Class.forName(fieldConfig.getFullName());
				// 解析公共属性
				List<AttributeConfig> attList = fieldConfig.getAttrList();
				BaseField baseField = (BaseField) fieldClass.newInstance();
				for (AttributeConfig attributeConfig : attList)
				{
					String name = attributeConfig.getName();
					Class type = Class.forName(attributeConfig.getType());
					if (attributeConfig.getType().contains("Integer") && (field.valueOf("@" + name) == null || "".equals(field.valueOf("@" + name))))
					{
					} else
					{
						Constructor<?> constructor = type.getConstructor(String.class);
						fieldClass.getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), new Class<?>[] { Class.forName(attributeConfig.getType()) }).invoke(baseField, constructor.newInstance(field.valueOf("@" + name)));
					}
				}
				// 解析私有属性
				Field[] declaredFields = fieldClass.getDeclaredFields();
				for (Field declaredField : declaredFields)
				{
					String name = declaredField.getName();
					Class type = declaredField.getType();
					if (type.toString().contains("Integer") && (field.valueOf("@" + name) == null || "".equals(field.valueOf("@" + name))))
					{
					} else
					{
						Constructor<?> constructor = type.getConstructor(String.class);
						fieldClass.getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), new Class<?>[] { type }).invoke(baseField, constructor.newInstance(field.valueOf("@" + name)));
					}
				}

				hibernateDao.save(baseField);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	private Map<String, BaseField> getFieldMapByDoc(Document doc)
	{
		Map<String, BaseField> map = new HashMap<String, BaseField>();
		Map<String, FieldConfig> fieldInfoMap = (Map<String, FieldConfig>) BaseCacheManager.get(FIELDINFO_CACHEKEY, FIELDINFO_CACHEKEY);
		// 解析xml文件，导入字段
		List<Node> fields = doc.selectNodes("//fields/field");
		for (Node field : fields)
		{
			String fieldType = field.valueOf("@fieldType");
			FieldConfig fieldConfig = fieldInfoMap.get(fieldType);
			try
			{

				Class fieldClass = Class.forName(fieldConfig.getFullName());
				// 解析公共属性
				List<AttributeConfig> attList = fieldConfig.getAttrList();
				BaseField baseField = (BaseField) fieldClass.newInstance();
				for (AttributeConfig attributeConfig : attList)
				{
					String name = attributeConfig.getName();
					Class type = Class.forName(attributeConfig.getType());
					if (attributeConfig.getType().contains("Integer") && (field.valueOf("@" + name) == null || "".equals(field.valueOf("@" + name))))
					{
					} else
					{
						Constructor<?> constructor = type.getConstructor(String.class);
						fieldClass.getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), new Class<?>[] { Class.forName(attributeConfig.getType()) }).invoke(baseField, constructor.newInstance(field.valueOf("@" + name)));
					}
				}
				// 解析私有属性
				Field[] declaredFields = fieldClass.getDeclaredFields();
				for (Field declaredField : declaredFields)
				{
					String name = declaredField.getName();
					Class type = declaredField.getType();
					if (type.toString().contains("Integer") && (field.valueOf("@" + name) == null || "".equals(field.valueOf("@" + name))))
					{
					} else
					{
						Constructor<?> constructor = type.getConstructor(String.class);
						fieldClass.getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), new Class<?>[] { type }).invoke(baseField, constructor.newInstance(field.valueOf("@" + name)));
					}
				}

				map.put(baseField.getFieldName(), baseField);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return map;
	}

	public IDao getHibernateDao()
	{
		return hibernateDao;
	}

	public void setHibernateDao(IDao hibernateDao)
	{
		this.hibernateDao = hibernateDao;
	}

	private void zip(ZipOutputStream out, File inFile, String root, CheckedOutputStream cs) throws Exception
	{

		if (inFile.isDirectory())
		{

			File[] files = inFile.listFiles();

			if (root.length() > 0)
				out.putNextEntry(new ZipEntry(root + "/"));

			root = root.length() == 0 ? "" : root + "/";

			for (int i = 0; i < files.length; i++)
			{

				zip(out, files[i], root + files[i].getName(), cs);

			}

		} else
		{

			BufferedInputStream in = new BufferedInputStream(new FileInputStream(inFile));

			out.putNextEntry(new ZipEntry(root));

			int c;

			while ((c = in.read()) != -1)

				out.write(c);

			in.close();
		}

	}

	private void createWorkSheetTable(String baseSchema)
	{
		try
		{
			DataAdapter qAdapter = new DataAdapter();

			String mainTable = "BS_F_" + baseSchema;

			String dropSql = "drop table " + mainTable;

			qAdapter.execute(dropSql, null);

			String mainSql = "create table " + mainTable + " (BASEID   VARCHAR2(50) not null primary key,BASESCHEMA   VARCHAR2(255),BASENAME VARCHAR2(255),BASESN VARCHAR2(255),BASEENTRYID VARCHAR2(50),BASECREATEDATE  NUMBER(15),BASESENDDATE NUMBER(15),BASEACCEPTDATE  NUMBER(15),BASEFINISHDATE NUMBER(15),BASECLOSEDATE NUMBER(15),BASESTATUS VARCHAR2(255),BASETPLID  VARCHAR2(255),BASEISARCHIVE   VARCHAR2(255),BASEISTRUECLOSE  VARCHAR2(255),BASEWORKFLOWFLAG      NUMBER(15),BASECATAGORYNAME  VARCHAR2(255),BASECATAGORYID    VARCHAR2(255),BASECREATORFULLNAME   VARCHAR2(255),BASECREATORLOGINNAME  VARCHAR2(255),BASECREATORCONNECTWAY VARCHAR2(255),BASECREATORCORP  VARCHAR2(255),BASECREATORCORPID VARCHAR2(255),BASECREATORDEP  VARCHAR2(255),BASECREATORDEPID VARCHAR2(255),BASECREATORDN  VARCHAR2(255),BASECREATORDNID VARCHAR2(255))";

			qAdapter.execute(mainSql, null);
		} catch (Error e)
		{
		}
	}

	private void createModLogTable(String baseSchema)
	{
		try
		{
			DataAdapter qAdapter = new DataAdapter();
			String modLogTable = "BS_F_" + baseSchema + "_FML";

			String dropSql = "drop table " + modLogTable;
			qAdapter.execute(dropSql, null);

			String modLogSql = "create table " + modLogTable + "(" + "  ID         VARCHAR2(50) not null primary key," + "  BASEID     VARCHAR2(255)," + "  BASESCHEMA VARCHAR2(255)," + "  PHASENO    VARCHAR2(255)," + "  FIELDID    VARCHAR2(255)," + "  FIELDTYPE  VARCHAR2(255)," + "  FIELDVALUE clob," + "  MODIFYDATE NUMBER(15)," + "  DEALERID   VARCHAR2(255)," + "  DEALER     VARCHAR2(255)," + "  TASKID     VARCHAR2(255)," + "  FIELDCODE  VARCHAR2(255)," + "  ACTIONTYPE VARCHAR2(255)," + "  ACTIONNAME VARCHAR2(255)," + "  FIELDLABEL VARCHAR2(255)," + "  ORDERNUM   NUMBER(15)," + "  COLSPAN    NUMBER(15))";

			qAdapter.execute(modLogSql, null);
		} catch (Error e)
		{
		}
	}

	public IDao getJdbcDao()
	{
		return jdbcDao;
	}

	public void setJdbcDao(IDao jdbcDao)
	{
		this.jdbcDao = jdbcDao;
	}

}

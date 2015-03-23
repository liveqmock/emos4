package com.ultrapower.eoms.ultrabpp.cache.service;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;

import com.ultrapower.eoms.ultrabpp.cache.model.ExportInfo;
import com.ultrapower.eoms.ultrabpp.model.BaseField;

/**
 * 
 * @author fying 
 * @version 
 * @since  
 * @see TemplateDoc
 */
public interface ExportService
{
	/**
	 * 导出操作前，在指定目录下面创建export文件夹，在文件夹内部创建export_info.xml文件，和readme.txt说明文件
	 * @param baseSchemas
	 * @param wfinfo
	 * @param fieldExport
	 * @param stepExport
	 * @param wfdesign
	 * @param jsppage
	 */
	public void exportBegin(String[] baseSchemaStrings,String wfinfo,String fieldExport,String stepExport,String wfdesign,String jsppage);
	/**
	 * 导出中，根据工单分类导出工单配置文件
	 * @param baseSchema
	 * @param wfinfo
	 * @param fieldExport
	 * @param stepExport
	 * @param wfdesign
	 * @param jsppage
	 */
	public void export(String baseSchema,String wfinfo,String fieldExport,String stepExport,String wfdesign,String jsppage);
	/**
	 * 导出操作后，将成功导出的文件进行打包操作，生成export.zip压缩文件，打包完成后，将export文件夹和文件夹下所有文件删除
	 */
	public void exportEnd();
	/**
	 * 加载导出文件的配置信息
	 */
	public void loadExportInfo(ExportInfo exportInfo,Document doc);
	/***
	 * 加载workflow_info.xml
	 */
	public void loadWorkflowInfo(String baseSchema,ExportInfo exportInfo,Document doc);
	/**
	 * 导入操作 
	 */
	public void importWorkflowInfo(ExportInfo exportInfo);
	/**
	 * 强制导入操作 
	 */
	public void importWorkflowInfo(ExportInfo exportInfo,boolean force);
	/**
	 * 对比原始字段与导入字段直接点关系
	 */
	public Map<String,Map<String, List<BaseField>>> contrastOldAndNewField(ExportInfo exportInfo,String fieldImportBaschema);
	/**
	 * 封装所有表单配置字段
	 */
	public Map<String,List<BaseField>> getNewField(ExportInfo exportInfo);
	/**
	 * 根据对比得到的字段map 返回sqlMap
	 */
	public Map<String, List<String>> getFieldSql(Map<String,Map<String, List<BaseField>>> changeMap);
}

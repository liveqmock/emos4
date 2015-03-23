package com.ultrapower.eoms.ultrasm.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ultrapower.eoms.common.core.component.data.DataTable;

/**
 * 通用导入导出EXCEL管理服务类
 * 此类主要提供了通用导入EXCEL、导出EXCEL和下载导入模版服务
 * @author： 孙海龙
 * @date： 2010-8-1 
 */
public interface CommonExcelService
{
	/**
	 * 根据导出配置XML的标识和导出数据集来导出EXCEL
	 * @param response
	 * @param cfgMark 导出配置标识
	 * @param dataTable 导出数据集
	 * @return List<String> 返回导出错误信息 如果List为空则说明不存在错误 即导出成功
	 */
	public List<String> exportExcelByDt(HttpServletResponse response, String cfgMark, DataTable dataTable);
	
	/**
	 * 根据导出配置XML的标识和查询SQL缓存key值来导出EXCEL
	 * @param response
	 * @param cfgMark 导出配置标识
	 * @param cacheid 查询SQL缓存key值
	 * @return List<String> 返回导出错误信息 如果List为空则说明不存在错误 即导出成功
	 * @throws Exception
	 */
	public List<String> exportExcelByCfg(HttpServletResponse response, String cfgMark, String cacheid) throws Exception;
	
	/**
	 * 根据XML配置和导入的EXCEL文件 将数据导入到数据库中
	 * @param file 导入的EXCEL文件
	 * @param xmlPath XML配置文件路径
	 * @return List<String> 返回导入错误信息 如果List为空则说明不存在错误 即导入成功
	 * @throws IOException
	 */
	public List<String> importExcelByCfg(File file, String xmlPath) throws IOException;
	
	/**
	 * 根据XML配置和导入的EXCEL文件 将数据导入到remedy中
	 * @param file 导入的EXCEL文件
	 * @param xmlPath XML配置文件路径
	 * @return List<String> 返回导入错误信息 如果List为空则说明不存在错误 即导入成功
	 * @throws IOException
	 */
	public List<String> importExcelByCfgToRemedy(File file, String xmlPath) throws IOException;
	
	/**
	 * 下载导入模版
	 * @param response
	 * @param xmlPath XML配置文件路径
	 * @return List<String> 返回导出模版错误信息 如果List为空则说明不存在错误 即模版下载成功
	 */
	public List<String> downloadImportTemplate(HttpServletResponse response, String xmlPath);
}

package com.ultrapower.eoms.ultrasm.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultrasm.model.ExcelExpCfg;
import com.ultrapower.eoms.ultrasm.model.ExcelExpCfgFld;
import com.ultrapower.eoms.ultrasm.model.ExcelExpCfgRow;

/**
 * 描述：提供EXCEL导出配置修改保存等相关服务
 * @author RenChenglin
 */
public interface ExcelManagerService
{
	/**
	 * 根据配置标识符获取导出配置对象（基本信息）
	 * @param cfgMark	配置标识
	 * @return EXCEL导出配置对象
	 */
	public ExcelExpCfg getExcelCfgByMark(String cfgMark);
	
	/**
	 * 根据导出配置对象的id获取导出配置对象
	 * @param pid 对象主键
	 * @return EXCEL导出配置对象
	 */
	public ExcelExpCfg getExcelCfgByPid(String pid);
	
	/**
	 * 保存EXCEL导出配置对象的基本信息，以及导出配置对象的表头行对象信息和单元格属性信息
	 * @param excelExpCfg EXCEL导出配置基本信息对象
	 * @param cfgData 表头各行各单元格信息（cfgData.get("tdMatrix")获得一个矩阵，该矩阵按行列表示各行各单元格信息）
	 * @return 布尔值。true-成功；false-失败
	 */
	public boolean saveExcelExpCfg(ExcelExpCfg excelExpCfg,Map cfgData);
	
	/**
	 * 保存EXCEL导出配置对象基本信息
	 * @param excelExpCfg EXCEL导出配置对象
	 * @return 导出配置对象主键Id
	 */
	public String saveExcelExpCfg(ExcelExpCfg excelExpCfg);
	
	/**
	 * 保存EXCEL导出配置表头行对象信息
	 * @param excelExpCfgRow EXCEL导出配置行对象
	 * @return 导出配置行对象主键Id
	 */
	public String saveExcelExpCfgRow(ExcelExpCfgRow excelExpCfgRow);
	
	/**
	 * 保存EXCEL导出配置表头字段对象信息（单元格）
	 * @param excelExpCfgFld EXCEL导出配置字段对象
	 * @return 导出配置字段（单元格）主键Id
	 */
	public String saveExcelExpCfgFld(ExcelExpCfgFld excelExpCfgFld);
	
	/**
	 * 根据EXCEL导出配置ID删除其行和单元格配置数据
	 * @param excelCfgPid EXCEL导出配置主键id
	 * @return 布尔值。true-成功；false-失败
	 */
	public boolean deleteExcelExpCfgData(String excelCfgPid);
	
	/**
	 * 根据EXCEL导出配置的ID集合删除导出配置数据
	 * @param idList 导出配置行的Id集合
	 * @return 布尔值。true-成功；false-失败
	 */
	public boolean deleteExcelExpCfg(List<String> idList);
	
	/**
	 * 检测新输入的导出配置标识是否唯一
	 * @param cfg 配置标识符
	 * @return 布尔值。true-唯一，可以使用；false-不唯一，不可以使用
	 */
	public boolean isCfgMarkUnique(String cfg);
	
	public IDao<ExcelExpCfgFld> getExcelExpFldDao();
	
	public IDao<ExcelExpCfgRow> getExcelExpRowDao();
}

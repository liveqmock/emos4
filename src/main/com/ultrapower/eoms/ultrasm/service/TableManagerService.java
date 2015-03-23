package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.FieldInfo;
import com.ultrapower.eoms.ultrasm.model.TableInfo;

/**
 * 该服务提供库表以及库表字段的保存、修改、删除、查询和其他服务
 * @author RenChenglin
 *
 */
public interface TableManagerService {
	
	/**
	 * 根据id获得库表信息
	 * @param id 库表id
	 * @return 库表对象
	 */
	public TableInfo getTableById(String id);
	
	/**
	 * 根据库表英文名获得表信息，前提是库表英文名具有唯一性
	 * @param enname 库表英文名
	 * @return 库表对象
	 */
	public TableInfo getTableByEnname(String enname);
	
	/**
	 * 保存库表对象（添加/修改操作）
	 * @param tbInfo 库表对象
	 * @return 布尔值，true-成功，false-失败
	 */
	public boolean SaveTable(TableInfo tbInfo);
	
	/**
	 * 根据库表ID集合删除多个库表
	 * @param ids 被删除库表的id的list集合
	 * @return 布尔值，true-成功，false-失败
	 */
	public boolean deleteTableById(List<String> ids);
	
	/**
	 * 根据库表英文名集合删除多个库表
	 * @param names 被删除库表的英文名集合
	 * @return 布尔值，true-成功，false-失败
	 */
	public boolean deleteTableByEnname(List<String> names);
	
	/**
	 * 根据获得的英文表名判断库表是否已经存在，在添加库表为库表取英文名的时候检测英文名唯一性
	 * @param ename 库表英文名
	 * @return 布尔值。true-唯一，可以使用；false-不唯一，不可以使用
	 */
	public boolean checkEnameUnique(String ename);
	
	/**
	 * 保存库表字段信息（添加/修改操作）
	 * @param field 字段对象
	 * @return 布尔值。true-成功;false-失败
	 */
	public boolean saveField(FieldInfo field);
	
	/**
	 * 根据库表字段id获得字段信息
	 * @param id 字段信息id
	 * @return 字段对象
	 */
	public FieldInfo getFieldById(String id);
	
	/**
	 * 根据库表字段ID集合获取字段信息集合
	 * @param fieldIdList 字段id集合
	 * @return 字段对象集合
	 */
	public List<FieldInfo> getFieldById(List fieldIdList);
	
	/**
	 * 根据字段id集合删除对应的字段信息
	 * @param ids 字段id集合
	 * @return 布尔值。true-成功;false-失败
	 */
	public boolean deleteField(List<String> ids);
	
	/**
	 * 根据库表的英文名字获得该库表的所有字段信息
	 * @param tableEnname 库表英文名
	 * @return 字段对象集合
	 */
	public List<FieldInfo> getFieldByTbName(String tableEnname);
	
	/**
	 * 根据库表名和字段名判断该库表中是否已经含有该字段，在为库表添加字段的时候必须检测字段名唯一性
	 * @param tbEname 库表英文名
	 * @param fieldEname 字段名
	 * @return 布尔值。true-唯一，可以使用;false-不唯一，不可以使用
	 */
	public boolean checkFieldUnique(String tbEnname,String fieldEnname);
	
	/**
	 * 保存库表和其字段信息
	 * @param tbinfo 库表对象
	 * @param fieldlst 字段对象集合
	 * @return 布尔值。true-成功;false-失败
	 */
	public boolean addTableAndField(TableInfo tbinfo,List<FieldInfo> fieldlst);
	
	/**
	 * 修改库表信息，包括库表基本信息以及库表字段的添加、修改和删除
	 * @param tbinfo 库表对象
	 * @param addlst 添加的字段对象集合
	 * @param updatelst 修改的字段对象集合
	 * @param delIdlst 删除的字段id集合
	 * @return 布尔值。true-成功;false-失败
	 */
	public boolean updateTableAndField(TableInfo tbinfo,List<FieldInfo> addlst,List<FieldInfo> updatelst,List<String> delIdlst);
}

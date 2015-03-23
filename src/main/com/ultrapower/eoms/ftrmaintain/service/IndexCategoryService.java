package com.ultrapower.eoms.ftrmaintain.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ftrmaintain.model.DBIndexSource;
import com.ultrapower.eoms.ftrmaintain.model.FileIndexSource;
import com.ultrapower.eoms.ftrmaintain.model.IndexCategory;
import com.ultrapower.eoms.ftrmaintain.model.IndexPath;

public interface IndexCategoryService {
	
	/**
	 * 保存索引类别信息
	 * @param category
	 * @param indexPath
	 * @param dbSource
	 * @param fileSource
	 * @return
	 */
	public boolean saveCategoryInfo(IndexCategory category,List<IndexPath> indexPath,
			List<DBIndexSource> dbSource,List<FileIndexSource> fileSource,List<IndexCategory> children);
	
	/**
	 * 保存索引类别基本信息
	 * @param category
	 * @return
	 */
	public String saveCategory(IndexCategory category,String parentId);
	
	/**
	 * 保存索引路径信息
	 * @param indexPath
	 * @param categoryId
	 * @return
	 */
	public boolean saveIndexPath(List<IndexPath> indexPath,String categoryId);
	
	/**
	 * 保存索引数据库数据源信息
	 * @param dbsource
	 * @param categoryId
	 * @return
	 */
	public boolean saveDBSource(List<DBIndexSource> dbsource,String categoryId);
	
	/**
	 * 保存索引文件数据源信息
	 * @param filesource
	 * @param categoryId
	 * @return
	 */
	public boolean saveFileSource(List<FileIndexSource> filesource,String categoryId);
	
	/**
	 * 删除索引类别
	 * @param categoryId
	 * @return
	 */
	public boolean deleteCategory(List<String> categoryId);
	
	/**
	 * 根据索引类别ID获得索引类别
	 * @param id
	 * @return
	 */
	public IndexCategory getCategoryById(String id);
	
	/**
	 * 根据父ID取得其直接子类
	 * @param parentId
	 * @return
	 */
	public List<IndexCategory> getChildCategory(String parentId);
	
	/**
	 * 根据Id集合取得索引类别集合
	 * @param idList
	 * @return
	 */
	public List<IndexCategory> getCategoryByIdList(List<String> idList);
	
	/**
	 * 根据索引类别ID取得该索引类别的索引路径
	 * @param categoryId
	 * @return
	 */
	public List<IndexPath> getIndexPathByCategoryId(String categoryId);
	
	/**
	 * 根据索引类别ID取得对应的数据库数据源
	 * @param categoryId
	 * @return
	 */
	public List<DBIndexSource> getDBSourceByCategoryId(String categoryId);
	
	/**
	 * 根据索引类别ID取得对应的文件数据源
	 * @param categoryId
	 * @return
	 */
	public List<FileIndexSource> getFileSourceByCategoryId(String categoryId);
	
	/**
	 * 获得可供选择的索引类别用来构造逻辑索引类别
	 * @return
	 */
	public Map<String,String> getSelectiveChildren(String categoryId);
	
	/**
	 * 返回物理索引类别PID和DISPLAYNAME的Map
	 * @return
	 */
	public Map<String,String> getPhysicalCategoryMap();
}

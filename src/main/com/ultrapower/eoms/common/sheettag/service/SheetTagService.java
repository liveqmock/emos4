package com.ultrapower.eoms.common.sheettag.service;

import java.util.List;

import com.ultrapower.eoms.common.sheettag.common.PageInfo;
import com.ultrapower.eoms.common.sheettag.model.SheetTag;
import com.ultrapower.eoms.common.sheettag.model.SheetType;


/**
 * 首页工单标签业务接口
 * 
 * @author <a href="mailto:liuzhuo@ultrapower.com.cn">liuzhuo</a>
 * 
 * @version 
 * 
 * @since 2013-2-27
 */
public interface SheetTagService {
 
	
	/**
	 * 
	 * 工单标签列表
	 * @return
	 */
	public List<SheetTag> listTag(PageInfo page);
	
	
	/**
	 * 获取工单标签总数
	 * @return
	 */
	public Long getTagCount();
	
	
	/**
	 * 通过id获取工单标签
	 * @param id
	 * @return
	 */
	public SheetTag getTagById(String id);
	
	/**
	 * 删除工单标签
	 * @param id
	 */
	public void removeTagById(String id);
	
	
	/**
	 * 保存工单标签
	 * @param o
	 */
	public void saveTag(SheetTag o);
	
	
	
	/**
	 * 
	 * 工单类别列表
	 * @return
	 */
	public List<SheetType> listType(PageInfo page);
	
	/**
	 * 
	 * 工单类别列表自定义分页
	 * @return
	 */
	public List<SheetType> listPageType(PageInfo page);
	
	
	/**
	 * 通过id获取工单类别
	 * @param id
	 * @return
	 */
	public SheetType getTypeById(String id);
	
	/**
	 * 删除工单类别
	 * @param id
	 */
	public void removeTypeById(String id);
	
	
	/**
	 * 保存工单标签
	 * @param o
	 */
	public void saveType(SheetType o);
	/**
	 * 获取工单类别总数
	 * @return
	 */
	public Long getTypeCount();
	
	
	/**
	 * 
	 * 获取关联标签的工单类别列表
	 * @return
	 */
	public List<SheetType> listTypeByTag(String id);
	
	
	/**
	 * 
	 * 保存工单标签和类别的关联
	 */
	public void saveTagTypeLink(String id,String[] typeIds);
	
	
	/**
	 * 获取全部工单标签
	 * @return
	 */
	public List<SheetTag> listAllTag();
	
	/**
	 * 按级别获取工单标签
	 * @param level
	 * @return
	 */
	public List<SheetTag> getListAllTagByLevel(int taglevel);
	
	/**
	 * 按父id获取工单标签
	 * @param level
	 * @return
	 */
	public List<SheetTag> getListAllTagByParentId(String parentid);
	
	/**
	 * 根据baseSechema获取标签类别
	 * @param baseschema
	 * @return
	 */
	public SheetType getSheetTypeByBaseSchema(String baseschema);
	
	/**
	 * 根据标签类别ID删除关联表数据
	 * @param sheetTypeId
	 */
	public void removeTagTypeLinkBySheetTypeId(String sheetTypeId);
	
	/**
	 * 根据baseSchema获取工单标签
	 * @param baseschema
	 * @return
	 */
	public List<SheetTag> getListTagBySheetTypeId(String baseschema);
	
	/**
	 * 根据权限获取工单标签
	 * @param partSQL
	 * @return
	 */
	public List<SheetTag> listNavigationTag(String partSQL, String upepSQL);
	
	/**
	 * 根据权限获取服务请求类工单标签
	 * @param partSQL
	 * @param stbSQL 
	 * @return
	 */
	public List<SheetTag> listServiceTag(String partSQL, String stbSQL);
	
	/**
	 * 验证工单标签名称
	 * @param editTagName
	 * @return
	 */
	public String editvalidate(String editTagName);
	
	/**
	 * 保存工单标签和类别的关联
	 * @param tagId
	 * @param typeId
	 */
	public void saveTagTypeLink(String tagId, String typeId);
	
}

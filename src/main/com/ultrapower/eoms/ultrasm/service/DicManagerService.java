package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.DicType;
import com.ultrapower.eoms.ultrasm.model.DicItem;

/**
 * 数据字段管理服务类
 * 此服务主要提供数据字典的增、删、改、查等服务
 * @author 孙海龙
 */
public interface DicManagerService
{
	/**
	 * 根据字典类型编码获取字典类型名称
	 * @param dtcode 字典类型编码
	 * @return String 返回字典类型名称
	 */
	public String getDtNameByDtcode(String dtcode);
	
	/**
	 * 根据字典类型编码和字典信息值来获取对应的展示信息
	 * 首先从缓存中获取此类型字典，若不存在则查询数据库并放入缓存，并取出所需要的信息
	 * @param dtcode 字典类型编码
	 * @param divalue 字典信息值
	 * @return String 返回字典值对应的展示信息
	 */
	public String getTextByValue(String dtcode, String divalue);
	
	/**
	 * 验证此字典类型编码是否唯一
	 * @param dtcode 字典类型编码
	 * @return boolean 返回true或false体现该字典类型编码是否唯一
	 */
	public boolean isUniqueDictype(String dtcode);
	
	/**
	 * 验证此信息项的值是否是dtcode类型中唯一值
	 * @param dtcode 字典类型编码
	 * @param divalue 字典信息项值
	 * @return boolean 返回true或false体现该字典类型中的字典信息值是否唯一
	 */
	public boolean isUniqueDicItem(String dtcode, String divalue);
	
	/**
	 * 添加字典类型信息
	 * @param dictype 字典类型信息对象
	 * @return String 返回该字典类型ID
	 */
	public String addDicType(DicType dictype);
	
	/**
	 * 修改字典类型信息
	 * @param dictype 字典类型信息对象
	 * @return String  返回该字典类型ID
	 */
	public String updateDicType(DicType dictype);
	
	/**
	 * 新增字典信息项信息
	 * @param dicitem 字典信息项对象
	 * @return String 返回该信息项ID
	 */
	public String addDicItem(DicItem dicitem);
	
	/**
	 * 修改字典信息项信息
	 * @param dicitem 字典信息项对象
	 * @return String 返回该信息项ID
	 */
	public String updateDicItem(DicItem dicitem);
	
	/**
	 * 根据字典类型ID来删除字典类型对象
	 * @param dtId 字典类型ID
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteDicTypeById(String dtId);
	
	/**
	 * 根据字典信息项ID来删除字典信息项对象
	 * @param diId 字典信息项ID
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteDicItemById(String diId);
	
	/**
	 * 根据字典类型ID来获取数据字典类型信息
	 * @param dtId 字典类型ID
	 * @return DicType 返回字典类型信息
	 */
	public DicType getDicTypeById(String dtId);
	
	/**
	 * 根据字典类型编码来获取字典对象信息
	 * @param dtcode 字典类型编码
	 * @return DicType 返回字典类型信息
	 */
	public DicType getDicTypeByCode(String dtcode);
	
	/**
	 * 根据ID获取数据字典信息项信息
	 * @param diId 字典信息项ID
	 * @return DicItem 返回字典信息项信息
	 */
	public DicItem getDicItemById(String diId);
	
	/**
	 * 根据字典类型和字典值来获取字典信息项信息对象
	 * @param dtcode 字典类型编码
	 * @param divalue 字典信息值
	 * @return DicItem 返回字典信息项信息
	 */
	public DicItem getDicItemByValue(String dtcode, String divalue);
	
	/**
	 * 根据上级ID来获取该节点下的字典信息项信息
	 * @param parentid 字典信息项ID
	 * @return List<DicItem> 字典信息项信息List
	 */
	public List<DicItem> getDicItemByParentID(String parentid);
	
	/**
	 * 根据字典信息值全名获取字典信息列表
	 * @param dicFullName 字典全名
	 * @return
	 */
	public List<DicItem> getDicItemByFullName(String dtcode, String dicFullName);
	
	/**
	 * 根据字典信息值全名获取所有自己字典信息列表
	 * @param dtcode
	 * @param dicFullName
	 * @return
	 */
	public List<DicItem> getAllSubDicItemByFullName(String dtcode, String dicFullName);
	
	/**
	 * 根据字典类型编码获取顶级字典信息集合
	 * @param dtcode 字典类型编码
	 * @return
	 */
	public List<DicItem> getTopDicItemByDtcode(String dtcode);
	
	/**
	 * 根据字典类型编码和字典信息值来获取该信息项下的字典信息
	 * @param divalue 字典信息值
	 * @param dtcode 字典类型编码
	 * @return List<DicItem> 字典信息项信息List
	 */
	public List<DicItem> getDicItem(String divalue,String dtcode);
	
	/**
	 * 根据字典类型编码来获取该类型下字典的顶级节点列表数据
	 * @param dtcode 字典类别码
	 * @return List<DicItem> 字典信息项信息List
	 */
	public List<DicItem> getRootItsmByDicType(String dtcode);
	
	/**
	 * 根据字典类型编码来获取该类型下的所有字典信息
	 * @param dtcode 字典类型编码
	 * @return List<DicItem> 字典信息项信息List
	 */
	public List<DicItem> getDicItemByDicType(String dtcode);
	
	/**
	 * 获取所有启用类型
	 * @return List<DicType> 返回字典类型信息List
	 */
	public List<DicType> getDicType();
	
	/**
	 * 根据字典类型编码和字典值来查询该字典信息下所有子集的字典值
	 * @param dtcode 字典类型编码
	 * @param divalue 字典值
	 * @param isCon 是否作为条件 如果为true 则返回值格式为：'1','2','3','4','5'
	 * @return String 返回字典值串 如：1,2,3,4,5
	 */
	public String getSubDivalue(String dtcode, String divalue, boolean isCon);
}

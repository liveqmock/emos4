package com.ultrapower.eoms.ftrmaintain.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ftrmaintain.model.DisplayModel;

public interface IndexDisplayModelService {
	
	/**
	 * 根据模板ID获得模板对象
	 * @param id
	 * @return
	 */
	public DisplayModel getModelById(String id);
	
	/**
	 * 根据索引类别ID获得其可以用来展示的字段，这些被取出来的字段定义了绝对位置，这些位置对应了
	 * 字段定义List集合的下标，索引字段定义的List集合按照字段的排序值排序查询出来
	 * @param id
	 * @return
	 */
	public String getSpanInfoByIndexId(String id);
	
	/**
	 * 返回没有展示模板的物理索引类别PID和DISPLAYNAME的Map
	 * @return
	 */
	public Map<String,String> getPhysicalCategoryMap();
	
	/**
	 * 保存展示模板信息
	 * @param model --模板基本信息对象
	 * @param spaninfo --模板展示字段字符串，格式为"字段ID1,所占列数,排序值,字段位置;字段ID2,所占列数,排序值,字段位置"
	 * @return
	 */
	public boolean saveModel(DisplayModel model,String spaninfo);
	
	/**
	 * 删除展示模板
	 * @param modelId --模板ID集合
	 * @return
	 */
	public boolean delModel(List<String> modelId);
}

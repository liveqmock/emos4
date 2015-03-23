package com.ultrapower.eoms.ultrasm.service;

import com.ultrapower.eoms.ultrasm.model.UserTemplate;

/**
 * 人员模版配置接口
 * @author SunHailong
 * @since  2011-05-16
 */
public interface UserTemplateService
{
	/**
	 * 添加一个人员模版 将人员模版数据和模版共享信息一同添加
	 * @param userTemplate
	 * @return
	 */
	public boolean addUserTemplate(UserTemplate userTemplate);
	
	/**
	 * 删除一个人员模版 将人员模版数据和模版共享信息一同删除
	 * @param utId
	 * @return
	 */
	public boolean deleteUserTemplate(String utid);
	
	/**
	 * 更新一个人员模版 将人员模版数据和模版共享信息一同更新
	 * 实现方式 将人员模版数据和共享信息全删全增
	 * @param userTemplate
	 * @param updateUtType
	 * @param updateUtName
	 * @param updateData
	 * @param updateShare
	 * @return
	 */
	public boolean updateUserTemplate(UserTemplate userTemplate, boolean updateUtType, boolean updateUtName, boolean updateData, boolean updateShare);
	
	/**
	 * 获取人员模版以及人员模版和模版共享信息数据
	 * @param utid
	 * @return
	 */
	public UserTemplate getUserTemplateById(String utid);
	
	/**
	 * 根据人员模版ID获取人员模版名称
	 * @param utids
	 * @return
	 */
	public String getUserTemplateNamesByIds(String utids);
	
	/**
	 * 根据多个模版id获取模版下配置的人和组
	 * @param utids 模版id
	 * @param idType 获取模版中人员id的类型 0:id-loginname 1:loginname 2:id(非0,1)
	 * @return 返回字符串 格式：  userid1,userid2,...:username1,username2,...;depid1,depid2,...:depname1,depname2,...
	 */
	public String getUserTemplateDataByIds(String utids, String idType);
}

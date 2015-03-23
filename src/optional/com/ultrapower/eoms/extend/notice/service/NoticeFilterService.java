package com.ultrapower.eoms.extend.notice.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.extend.notice.model.NoticeFilter;

/**
 * @author RenChenglin
 * @date 2011-12-12 下午03:57:32
 * @version
 */
public interface NoticeFilterService {
	/**
	 * 根据登陆名和工单类型获取
	 * @param loginName
	 * @param baseSchema
	 * @return 
	 */
	public NoticeFilter getNoticeFilter(String loginName,String baseSchema);
	/**
	 * 保存
	 * @param filter
	 * @return 返回PID
	 */
	public String save(NoticeFilter filter);
	
	/**
	 * 根据ID删除
	 * @param pid
	 * @return
	 */
	public boolean deleteById(String pid);
	
	/**
	 * 根据ID获得
	 * @param pid
	 * @return
	 */
	public NoticeFilter getById(String pid);
	
	/**
	 * 进行工单通知过滤校验
	 * @param baseSchema 工单类别
	 * @param userlogin 用户登录名
	 * @param paramMap 必要参数
	 * @return 1:通过过滤
	 */
	public int doNoticeFilter(String baseSchema, String userlogin, Map<String,String> paramMap);

	/**
	 * 进行通知过滤校验
	 * @param businessType 业务类别
	 * @param businessMark 业务类别标识
	 * @param userType 用户标识类别 1.用户登录名 0.用户id
	 * @param userMark 用户标识 根据标识类别而定
	 * @param paramMap 必要参数
	 * @return 1：通过过滤
	 */
	public int doNoticeFilter(String businessType, String businessMark, String userType, String userMark, Map<String,String> paramMap);

	/**
	 * 获取延迟通知时间
	 * @param baseSchema 工单类别
	 * @param userlogin 用户登录名
	 * @param paramMap 必要参数
	 * @return
	 */
	public long getDelayTime(String baseSchema, String userlogin, Map<String,String> paramMap);

	/**
	 * 获取延迟通知时间
	 * @param businessType 业务类别
	 * @param businessMark 业务类别标识
	 * @param userType 用户标识类别 1.用户登录名 0.用户id
	 * @param userMark 用户标识 根据标识类别而定
	 * @param paramMap 必要参数
	 * @return
	 */
	public long getDelayTime(String businessType, String businessMark, String userType, String userMark, Map<String,String> paramMap);
	
}

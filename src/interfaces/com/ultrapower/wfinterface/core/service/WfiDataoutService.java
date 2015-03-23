package com.ultrapower.wfinterface.core.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.wfinterface.core.model.WfiDataout;

/**
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 * @author： lza
 * @date： 2011-7-6 15:32:57
 * 当前版本：
 * 摘要:
 */
public interface WfiDataoutService {
	
	/**
	 * 根据Id取得接口数据
	 * @param pid
	 * @return
	 */
	public WfiDataout getWfiDataout(String pid);

	/**
	 * 保存接口数据
	 * 
	 * @param wfiDataout
	 * @return
	 */
	public String saveWfiDataout(WfiDataout wfiDataout);

	/**
	 * 保存接口数据和附件
	 * 
	 * @param wfiDataout
	 * @param list
	 * @return
	 */
	public String saveWfiDataoutAndAttachments(WfiDataout wfiDataout,List<Attachment> list) throws Exception;
	/**
	 * 根据Id删除接口数据
	 * @param pid
	 * @return
	 */
	public void deleteWfiDataout(String pid);

	/**
	 * 根据状态取得接口数据列表
	 * @param opState
	 * @return
	 */
	public List<WfiDataout> getWfiDataoutList(int opState);

}

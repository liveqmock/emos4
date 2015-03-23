package com.ultrapower.wfinterface.core.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.wfinterface.core.model.WfiDatain;
import com.ultrapower.wfinterface.core.model.WfiTempAttachment;

/**
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 * @author： lza
 * @date： 2011-7-6 15:32:57
 * 当前版本：
 * 摘要:
 */
public interface WfiDatainService {
	
	/**
	 * 根据Id取得接口数据
	 * @param pid
	 * @return
	 */
	public WfiDatain getWfiDatain(String pid);

	/**
	 * 保存接口数据
	 * 
	 * @param wfiDatain
	 * @return
	 */
	public String saveWfiDatain(WfiDatain wfiDatain);

	/**
	 * 保存接口数据和附件
	 * 
	 * @param wfiDatain
	 * @param list
	 * @return
	 */
	public String saveWfiDatainAndAttachments(WfiDatain wfiDatain,List<Attachment> list) throws Exception;

	/**
	 * 根据Id删除接口数据
	 * @param pid
	 * @return
	 */
	public void deleteWfiDatain(String pid);

	/**
	 * 根据状态取得接口数据列表
	 * @param opState
	 * @return
	 */
	public List<WfiDatain> getWfiDatainList(int opState);

}

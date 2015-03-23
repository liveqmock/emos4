package com.ultrapower.wfinterface.core.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.Attachment;

/**
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 * @author： lza
 * @date： 2011-7-6 15:32:57
 * 当前版本：
 * 摘要:
 */
public interface WfiTempAttachmentService {
	
	/**
	 * 根据Id取得接口附件数据
	 * @param pid
	 * @return
	 */
	public Attachment getAttachment(String pid);

	/**
	 * 保存接口附件数据
	 * 
	 * @param Attachment
	 * @return
	 */
	public String saveAttachment(Attachment Attachment) throws Exception;

	/**
	 * 保存接口附件数据列表
	 * 
	 * @param list
	 * @return
	 */
	public String saveAttachment(List<Attachment> list) throws Exception;
	

	/**
	 * 根据Id删除接口附件数据
	 * @param pid
	 * @return
	 */
	public void deleteAttachment(String pid);

	/**
	 * 根据数据Id删除接口附件数据
	 * @param dataid
	 * @return
	 */
	public void deleteAttachmentList(String dataid);

	/**
	 * 根据数据id取得接口附件数据列表
	 * @param dataid
	 * @return
	 */
	public List<Attachment> getAttachmentList(String dataid);

}

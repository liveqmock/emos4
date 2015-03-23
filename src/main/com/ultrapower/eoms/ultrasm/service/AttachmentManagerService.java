package com.ultrapower.eoms.ultrasm.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.ultrasm.model.Attachment;

/**
 * 附件信息管理服务类
 * 此服务主要对附件的上传、下载以及一些特殊操作进行管理
 * @author 孙海龙
 */
public interface AttachmentManagerService
{
	/**
	 * 添加附件信息
	 * @param attachment 附件对象
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addAttachment(Attachment attachment);
	
	/**
	 * 添加附件信息,批量添加
	 * @param attachmentList 附件对象List
	 * @return boolean 返回true或false体现是否添加成功
	 */
	public boolean addAttachment(List<Attachment> attachmentList);
	
	/**
	 * 根据ID获取附件信息
	 * @param pid 附件ID
	 * @return Attachment 附件信息对象
	 */
	public Attachment getAttachmentById(String pid);
	
	/**
	 * 根据关系编码获取该组附件信息对象List
	 * @param code 关系编码
	 * @return List<Attachment> 附件信息对象List
	 */
	public List<Attachment> getAttachmentByRelation(String code);
	
	/**
	 * 根据ID删除附件信息
	 * @param attId 附件ID
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteAttachmentById(String attId);
	/**
	 * 根据ID删除附件信息
	 * @param attIdList 附件IDList
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteAttachmentById(List attIdList);
	
	/**
	 * 根据关系组ID删除该组附件信息
	 * @param code 关系编码
	 * @return boolean 返回true或false体现是否删除成功
	 */
	public boolean deleteAttachmentByCode(String code);
	
	/**
	 * 根据用户Id和附件关系Id获取附件集合
	 * @param userId 人员id
	 * @param relationCode 附件关系id
	 * @return List<Attachment> 附件信息List
	 */
	public List<Attachment> getAttachByUseridAndRelationCode(String userId, String relationCode);
	
	/**
	 * 根据SQL来查询附件信息List
	 * @param hql 查询SQL
	 * @return List<Attachment> 附件信息List
	 */
	public List<Attachment> queryByHql(String hql);
	
	/**
	 * 更新附件表的关联编码
	 * @param oldCode
	 * @param newCode
	 */
	public void updateRelationcode(String oldCode, String newCode);
	
	/**
	 * 上传附件接口
	 * @param ins --文件输入流（必填）
	 * @param fileName --文件名（必填）
	 * @param savePath --文件路径（如果不写文件路径，就保存到默认的根目录下）
	 * @param relationCode --关联代码（必填，表示一组附件）
	 * @param creater --附件创建人
	 * @param remark --附件备注
	 * @return --返回附件对象
	 */
	public Attachment uploadAttachment(InputStream ins,
			String fileName,String savePath,String relationCode,String creater,String remark);
	
	/**
	 * 附件上传接口
	 * @param ins --文件输入流（必填）
	 * @param attachment --附件对象（必填，其中的name、path、relationcode属性必填）
	 * @return --返回附件对象
	 */
	public Attachment uploadAttachment(InputStream ins,Attachment attachment);
	
	/**
	 * 通过路径和真实文件名下载附件
	 * @param savePath
	 * @param realName
	 * @param ftpUtil --可以为Null
	 * @return
	 */
	public InputStream downloadAttByPath(String savePath,String realName);
	
	/**
	 * 通过关联Code下载一组附件，批量下载
	 * @param relationCode
	 * @return
	 */
	public InputStream downloadAttByRelationCode(String relationCode);
	
	/**
	 * 通过附件的ID下载附件
	 * @param attId
	 * @return
	 */
	public InputStream downloadAttById(String attId);
	
	/**
	 * 通过附件ID的集合下载附件，批量下载
	 * @param attIdLst
	 * @return
	 */
	public InputStream downloadAttByIdLst(List<String> attIdLst);
	
	/**
	 * 下载多个附件对象，批量下载
	 * @param attObjLst
	 * @param packageName
	 * @return
	 */
	public InputStream downloadAtts(List<Attachment> attObjLst,String packageName);
	
}

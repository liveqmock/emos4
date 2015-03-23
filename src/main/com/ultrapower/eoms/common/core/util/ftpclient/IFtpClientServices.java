package com.ultrapower.eoms.common.core.util.ftpclient;

import java.io.InputStream;

public interface IFtpClientServices {

	/**
	 * 初始化服务器相关信息及登录
	 * @param ip
	 * @param port
	 * @param user
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public boolean connectServer(String ip,int port,String user,String pwd) throws Exception;
	
	/**
	 * 关闭连接
	 * @throws Exception
	 */
	public void closeServer() throws Exception;
	/**
	 * 
	 * @param romteDir ftp的文件目录  eg: workflow/test
	 * @param romteFilename  ftp的文件  eg: test.doc
	 * @param localDir  本地路径  eg: d:/test/workflow
	 * @param localFilename 本地文件名，下到本地的重命名 如果为空则和ftp的文件名一样  eg:2000112.doc
	 * @return 返回true则表示下载成功
	 * @throws Exception
	 */
	public boolean downloadFile(String romteDir,String romteFilename,String localDir,String localFilename) throws Exception;
	
	/**
	 * 根据直接路径来下载
	 * @param romtePath ftp文件路径： eg:  临时/a.log
	 * @param localPath 本地路径:  eg:  d:/临时/a.log
	 * @return 返回true则表示下载成功
	 * @throws Exception
	 */
	public boolean downloadFile(String romtePath,String localPath) throws Exception;
	
	/**
	 * 
	 * @param romteDir ftp的文件目录  eg: workflow/test
	 * @param romteFilename  ftp的文件  eg: test.doc
	 * @param localDir  本地路径  eg: d:/test/workflow
	 * @param localFilename 本地文件名，下到本地的重命名 如果为空则和ftp的文件名一样  eg:2000112.doc
	 * @return 返回true则表示下载成功
	 * @throws Exception
	 */
	public boolean uploadFile(String romteDir,String romteFilename,String localDir,String localFilename) throws Exception;
	/**
	 * 附件上传
	 * @param romtePath ftp文件路径： eg:  临时/a.log
	 * @param localPath 本地路径:  eg:  d:/临时/a.log
	 * @return 返回true则表示下载成功
	 * @throws Exception
	 */
	public boolean uploadFile(String romtePath,String localPath) throws Exception;
	
	/**
	 * 将传递的流上传
	 * @param remoteDir
	 * @param remoteFilename
	 * @param ins
	 * @return
	 */
	public boolean uploadFile(String remoteDir,String remoteFilename,InputStream ins) throws Exception;
	
	/**
	 * 将文件以流的形式下载
	 * @param remoteDir
	 * @param remoteFilename
	 * @return
	 */
	public InputStream downloadFileAsStream(String remoteDir,String remoteFilename) throws Exception;
	
	/**
	 * 根据完整的ftpURL直接下载文件
	 * @param url  ftp的完整路径, ex: ftp://csp:tjcsp@10.142.78.70:21/WORKFLOW/11072800/1107280000000034/kbsget_6004_220_20110726_000002.doc
	 * @param localPath 本地绝地路径 ex: d:/test/a.log
	 * @throws Exception 
	 */
	public void downloadForUrl(String url,String localPath) throws Exception;
	
	/**
	 * 删除文件
	 * @param romtePath 文件存放路径，包括文件名
	 * @throws Exception
	 */
	public boolean deleteFile(String romtePath) throws Exception;
}

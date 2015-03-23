package com.ultrapower.eoms.common.plugin.swfupload.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.core.util.ftpclient.IFtpClientServices;

public class SwfuploadUtil
{
	private IFtpClientServices ftpClient;
	
	public SwfuploadUtil(){
		ftpClient = SwfuploadUtil.getFtpClient();
	}
	
	/**
	 * 本地存储
	 * @param ins
	 * @param savePath
	 * @param fileName
	 * @return
	 */
	private String localStore(InputStream ins,SwfDoloadBean downloadBean){
		if(ins==null||downloadBean==null||downloadBean.getSaveName()==null)
			return null;
		String savePath = downloadBean.getSavePath();
		String fileName = downloadBean.getSaveName();
		String repositoryPath = SwfuploadUtil.pathProcess(SwfuploadUtil.SWFUPLOAD_UPLOAD_PATH);
		if("".equals(repositoryPath))
			return null;
		String customPath = SwfuploadUtil.pathProcess(savePath);
		if(customPath.startsWith("/"))
			customPath = customPath.substring(1);
		if(!"".equals(customPath))
			repositoryPath = repositoryPath + "/" + customPath;
		if(!SwfuploadUtil.isAbsolutePath(repositoryPath)){//相对路径
			if(SwfuploadUtil.APP_ROOT_REALPATH==null)
				return null;
			repositoryPath = SwfuploadUtil.APP_ROOT_REALPATH + "/" + repositoryPath;
		}
		File destDir = new File(repositoryPath);
		if (!destDir.exists())
			destDir.mkdirs();
		File destFile = new File(destDir, fileName);
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(destFile));
			byte[] b = new byte[2048];
			while(true){
				int i = ins.read(b, 0, 2048);
				if(i==-1)
					break;
				os.write(b, 0, i);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				if(os!=null){
					os.close();
				}
				ins.close();
			} catch (IOException e) {
				e.printStackTrace();
				return customPath;
			}
		}
		return customPath;
	}
	
	/**
	 * ftp存储
	 * @param ins
	 * @param savePath
	 * @param fileName
	 * @return
	 */
	private String ftpStore(InputStream ins,SwfDoloadBean downloadBean){
		if(ins==null||downloadBean==null||downloadBean.getSaveName()==null)
			return null;
		String savePath = downloadBean.getSavePath();
		String fileName = downloadBean.getSaveName();
		if(ftpClient==null)
			return null;
		try {
			ftpClient.uploadFile(SwfuploadUtil.getFullPathOfAttach(savePath), fileName, ins);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return savePath;
	}
	
	/**
	 * @param fileDir 拷贝文件所在目录
	 * @param fileNamePairs 要拷贝的文件名字对,格式：xxx.jpg:348934839.jpg
	 * @return 返回临时文件目录
	 */
	private String batchCopyFile_local(List<SwfDoloadBean> downloadBean)
	{
		if(downloadBean==null)
			return null;
		//附件保存的根路径
		String rootPath = SwfuploadUtil.pathProcess(SwfuploadUtil.SWFUPLOAD_UPLOAD_PATH);
		if("".equals(rootPath))
			return null;
		if(!SwfuploadUtil.isAbsolutePath(rootPath))
		{
			rootPath = SwfuploadUtil.APP_ROOT_REALPATH + "/" + rootPath;
		}
		//批量下载的临时路径
		String fileDir = SwfuploadUtil.APP_ROOT_REALPATH + "/" + SwfuploadUtil.SWFUPLOAD_BATCHDOWNLOAD_TEMP_DIR;
		File dirTestor = new File(fileDir);
		if(!dirTestor.exists())
			dirTestor.mkdirs();
		// 拷贝临时文件的目录
		String tempDir = fileDir + "/" + SwfuploadUtil.getTempDirName();
		File tempDirF = new File(tempDir);
		tempDirF.mkdir();
		File downInfo = new File(tempDir + "/" + "下载错误信息.txt");
		PrintWriter pw = null;
		try 
		{
			downInfo.createNewFile();
			pw = new PrintWriter(new FileWriter(downInfo));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		boolean errorFlag = true;
		for (SwfDoloadBean bean : downloadBean)
		{
			if(bean.getFileName()==null||bean.getSaveName()==null)
				continue;
			File sourceFile = new File((rootPath+"/" + SwfuploadUtil.pathProcess(bean.getSavePath())
					+ "/" + bean.getSaveName()).replace("//", "/"));
			if (sourceFile.exists())
			{
				File destFile = new File(tempDir + "/" + bean.getFileName());
				try
				{
					destFile.createNewFile();
					FileUtils.copyFile(sourceFile, destFile);
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				pw.println("该文件不存在或被删除："+bean.getFileName());
				errorFlag = false;
			}
		}
		pw.close();
		if(errorFlag)
			downInfo.delete();
		return tempDir;
	}
	
	/**
	 * FTP批量文件拷贝
	 * @param fileNamePairs
	 * @param fu
	 * @return
	 */
	private String batchCopyFile_ftp(List<SwfDoloadBean> downloadBean){
		if(downloadBean==null)
			return null;
		if(ftpClient==null)
			return null;
		//批量下载的临时路径
		String fileDir = SwfuploadUtil.APP_ROOT_REALPATH + "/" + SwfuploadUtil.SWFUPLOAD_BATCHDOWNLOAD_TEMP_DIR;
		File dirTestor = new File(fileDir);
		if(!dirTestor.exists())
			dirTestor.mkdirs();
		// 拷贝临时文件的目录
		String tempDir = fileDir + "/" + SwfuploadUtil.getTempDirName();
		File tempDirF = new File(tempDir);
		tempDirF.mkdir();
		File downInfo = new File(tempDir + "/" + "下载错误信息.txt");
		PrintWriter pw = null;
		try
		{
			downInfo.createNewFile();
			pw = new PrintWriter(new FileWriter(downInfo));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		boolean errorFlag = true;
		for (SwfDoloadBean bean : downloadBean)
		{
			if(bean.getFileName()==null||bean.getSaveName()==null)
				continue;
			try
			{
				ftpClient.downloadFile(SwfuploadUtil.getFullPathOfAttach(bean.getSavePath()), bean.getSaveName(), tempDir, bean.getFileName());
			} catch (Exception e){
				e.printStackTrace();
				errorFlag = false;
				pw.println("未能从FTP服务器成功下载该文件："+bean.getFileName());
				continue;
			}
		}
		pw.close();
		if(errorFlag)
			downInfo.delete();
		return tempDir;
	}
	
	/**
	 * 关闭附件连接
	 */
	public void close(){
		SwfuploadUtil.releaseFtp(ftpClient);
	}
	
	/**
	 * 附件上传
	 * @param ins
	 * @param downloadBean
	 * @return
	 */
	public String upload(InputStream ins,SwfDoloadBean downloadBean){
		if(ins==null||downloadBean==null||downloadBean.getSaveName()==null)
			return null;
		String storeType = PropertiesUtils.getProperty("attachment.storetype");
		if(SwfuploadUtil.STORE_TYPE_FTP.equalsIgnoreCase(storeType)){
			return ftpStore(ins,downloadBean);
		}else if(SwfuploadUtil.STORE_TYPE_LOCAL.equalsIgnoreCase(storeType)){
			return localStore(ins,downloadBean);
		}else{
			return null;
		}
	}
	
	/**
	 * 下载附件，realName是必须的参数
	 * @param downloadBean
	 * @return
	 */
	public InputStream download(SwfDoloadBean downloadBean){
		if(downloadBean==null)
			return null;
		String realName = downloadBean.getSaveName();
		String savePath = downloadBean.getSavePath();
		if(realName==null)
			return null;
		String fullPath = SwfuploadUtil.getFullPathOfAttach(savePath);
		String storeType = PropertiesUtils.getProperty("attachment.storetype");
		if(storeType==null)
			return null;
		String tempDir = downloadBean.getCustomDownPath();
		if(tempDir==null||"".equals(tempDir))
			tempDir = SwfuploadUtil.APP_ROOT_REALPATH + "/" + SwfuploadUtil.SWFUPLOAD_DOWNLOAD_TEMP_DIR + "/"+SwfuploadUtil.getTempDirName();
		File tempDirF = new File(tempDir);
		if(!tempDirF.exists())
			tempDirF.mkdirs();
		String tempFileName = null;
		if(downloadBean.getCustomDownName()==null)
			tempFileName = realName;
		else
			tempFileName = downloadBean.getCustomDownName();
		File tempFile = new File(tempDir,tempFileName);
		if(SwfuploadUtil.STORE_TYPE_FTP.equals(storeType.toLowerCase())){
			if(ftpClient==null)
				return null;
			if(fullPath.startsWith("/"))
				fullPath = fullPath.substring(1);
			try {
				ftpClient.downloadFile(fullPath, realName, tempDir, tempFileName);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}else if(SwfuploadUtil.STORE_TYPE_LOCAL.equals(storeType.toLowerCase())){
			File file = new File(fullPath,realName);
			if(file.exists()){
				try {
					FileUtils.copyFile(file, tempFile);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		InputStream ins = null;
		try {
			ins = new FileInputStream(tempFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return ins;
	}
	
	/**
	 * 批量下载附件
	 * @param fileNamePairs
	 * @param packageName
	 * @return
	 */
	public InputStream batchDownload(List<SwfDoloadBean> downloadBean,String packageName){
		if(downloadBean==null)
			return null;
		String tempDir = batchCopyFile(downloadBean);
		if(tempDir==null)
			return null;
		String packPath = null;
		try {
			packPath = packZip(tempDir,packageName);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		if(packPath==null)
			return null;
		FileInputStream ins = null;
		try {
			ins = new FileInputStream(new File(packPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return ins;
	}
	
	/**
	 * 删除附件
	 * @param relativePath
	 * @param realName
	 */
	public void deleteAttachment(SwfDoloadBean downloadBean){
		if(downloadBean==null)
			return;
		String realName = downloadBean.getSaveName();
		if(realName==null)
			return;
		String relativePath = downloadBean.getSavePath();
		String fullPath = SwfuploadUtil.getFullPathOfAttach(relativePath);
		String storeType = PropertiesUtils.getProperty("attachment.storetype");
		if(SwfuploadUtil.STORE_TYPE_FTP.equals(storeType.toLowerCase())){
			if(fullPath.startsWith("/"))
				fullPath = fullPath.substring(1);
			if(ftpClient==null)
				return;
			try {
				ftpClient.deleteFile(fullPath+"/"+realName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(SwfuploadUtil.STORE_TYPE_LOCAL.equals(storeType.toLowerCase())){
			File file = new File(fullPath,realName);
			if(file.exists()){
				file.delete();
			}
		}
	}
	
	/**
	 * 附件批量拷贝
	 * @param fileNamePairs
	 * @return
	 */
	public String batchCopyFile(List<SwfDoloadBean> downloadBean){
		String storeType = PropertiesUtils.getProperty("attachment.storetype");
		if(SwfuploadUtil.STORE_TYPE_FTP.equalsIgnoreCase(storeType)){
			return batchCopyFile_ftp(downloadBean);
		}else if(SwfuploadUtil.STORE_TYPE_LOCAL.equalsIgnoreCase(storeType)){
			return batchCopyFile_local(downloadBean);
		}else{
			return null;
		}
	}
	
	/**
	 * 打包文件列表
	 * @param 文件列表路径
	 * @return 打包文件路径
	 * @throws IOException
	 */
	public String packZip(String dir,String packageName) throws IOException
	{
		String zipFileName = null;
		if(packageName==null)
			zipFileName = dir + File.separator + SWFUPLOAD_BATCH_FILENAME;
		else
			zipFileName = dir + File.separator + packageName;
		File directory = new File(dir);
		Collection<File> files = FileUtils.listFiles(directory, null, false);
		// 不能把压缩文件算在内
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
		zipOutputStream.setEncoding("GBK");
		for (File file : files)
		{
			byte[] buf = new byte[1024 * 5];
			int len;
			String temp_filename = file.getName();
			ZipEntry zipEntry = new ZipEntry(temp_filename);
			//zipEntry.setUnixMode(755);//目录
			zipEntry.setUnixMode(644);//文件
			FileInputStream fin = new FileInputStream(file);
			BufferedInputStream in = new BufferedInputStream(fin);
			zipOutputStream.putNextEntry(zipEntry);
			while ((len = in.read(buf)) != -1)
			{
				zipOutputStream.write(buf, 0, len);
			}
			zipOutputStream.closeEntry();
			in.close();
		}
		zipOutputStream.close();
		return zipFileName;
	}

	/**
	 * 删除目录和该目录下的文件
	 * @param directory
	 * @throws IOException
	 */
	public static void deleteDirectory(String directory) throws IOException
	{
		File dir = new File(directory);
		if (dir.exists())
		{
			FileUtils.deleteDirectory(dir);
		}
	}
	
	/**
	 * 判断是否是绝度路径
	 * @param path
	 * @return
	 */
	public static boolean isAbsolutePath(String path)
	{
		if(path==null || "".equals(path))
		{
			return false;
		}
		if(path.startsWith("/") || path.matches("[C-Zc-z]:.*"))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 获得文件名称，按给定年月组成
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFileName_yearMonth(String year, String month)
	{//2010年3月--》201003 2010年12月--》201012
		String yearMonth = null;
		if(year!=null && month!=null)
		{
			month = month.length()==1?"0"+month:month;
			yearMonth = year + month;
		}
		return yearMonth;
	}
	
	/**
	 * 获得文件名称，按当前年月组成
	 * @return
	 */
	public static String getFileName_yearMonth()
	{//2010年3月--》201003 2010年12月--》201012
		String yearMonth = null;
		Date tempDate = new Date();
		String year = String.valueOf(TimeUtils.getYearOfDate(tempDate));
		String month = String.valueOf(TimeUtils.getMonthOfDate(tempDate)).length()==1?"0"+String.valueOf(TimeUtils.getMonthOfDate(tempDate))
									:String.valueOf(TimeUtils.getMonthOfDate(tempDate));
		yearMonth = year + month;
		return yearMonth;
	}
	
	/**
	 * 将文件存储路径通通处理为"/"（能适应WINDOWS和UNIX操作系统平台 如 D:/window     /homes/user），
	 * 去掉路径中最后一个斜杠，如果就剩一个斜杠，将返回空字符串
	 * @param path
	 * @return
	 */
	public static String pathProcess(String path)
	{
		if(path==null||"".equals(path.trim()))
		{
			path = "";
		}else{
			path = path.replace("\\", "/");
			if(path.endsWith("/")&&path.length()>1)
			{
				path = path.substring(0,path.lastIndexOf("/"));
			}else if(path.equals("/")){
				path = "";
			}
		}
		return path;
	}
	
	/**
	 * 获得FTP客户端
	 * @return
	 */
	public static IFtpClientServices getFtpClient(){
		Object obj = WebApplicationManager.getBean("ftpClient");
		if(obj==null||!(obj instanceof IFtpClientServices))
			return null;
		IFtpClientServices ftpClient = (IFtpClientServices)obj;
		String storeType = PropertiesUtils.getProperty("attachment.storetype");
		if(storeType==null)
			return null;
		if(SwfuploadUtil.STORE_TYPE_LOCAL.equals(storeType.toLowerCase()))
			return null;
		try {
			boolean flag = ftpClient.connectServer(PropertiesUtils.getProperty("attachment.ftp.server") 
					, Integer.valueOf(PropertiesUtils.getProperty("attachment.ftp.port"))
					, PropertiesUtils.getProperty("attachment.ftp.username")
					, PropertiesUtils.getProperty("attachment.ftp.password"));
			if(flag)
				return ftpClient;
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 释放FTP连接
	 * @param ftpClient
	 */
	public static void releaseFtp(IFtpClientServices ftpClient){
		try {
			if(ftpClient!=null)
				ftpClient.closeServer();
		} catch (Exception e) {
			//System.out.println("断开FTP服务器连接失败！");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 重命名文件名
	 * @param oldName
	 * @return
	 */
	public static String reName(String oldName)
	{
		if(oldName==null)
			return null;
		int poi = oldName.lastIndexOf(".");
		if(poi==-1)
			return UUIDGenerator.getUUIDoffSpace();
		String suffix = org.apache.commons.lang.StringUtils.substring(oldName, oldName
		        .lastIndexOf("."));
		String currentUUID = UUIDGenerator.getUUIDoffSpace();
		return currentUUID + suffix;
	}
	
	/**
	 * 获得临时目录名
	 * @return
	 */
	public static synchronized String getTempDirName(){
		return "swfupload"+UUIDGenerator.getUUIDoffSpace();
	}
	
	/**
	 * 获得文件的全存储路径，不包含文件名
	 * @param relativePath --文件存储的相对路径，不包含文件名
	 * @return
	 */
	public static String getFullPathOfAttach(String relativePath){
		if(relativePath==null||"".equals(relativePath.trim()))
			relativePath = "";
		String fullPath = null;
		String storeType = PropertiesUtils.getProperty("attachment.storetype");
		if(SwfuploadUtil.STORE_TYPE_FTP.equals(storeType.toLowerCase())){
			String rootPath = SwfuploadUtil.pathProcess(PropertiesUtils.getProperty("attachment.path"));
			relativePath = SwfuploadUtil.pathProcess(relativePath);
			if(relativePath.startsWith("/"))
				relativePath = relativePath.substring(1);
			fullPath = rootPath + "/" + relativePath;
		}else if(SwfuploadUtil.STORE_TYPE_LOCAL.equals(storeType.toLowerCase())){
			fullPath = SwfuploadUtil.pathProcess(SwfuploadUtil.SWFUPLOAD_UPLOAD_PATH);
			relativePath = SwfuploadUtil.pathProcess(relativePath);
			if(!"".equals(relativePath))
				fullPath = fullPath + "/" + relativePath;
			if(!SwfuploadUtil.isAbsolutePath(fullPath)){
				fullPath = SwfuploadUtil.APP_ROOT_REALPATH + "/" + fullPath;
			}
		}
		return fullPath;
	}
	
	/**
	 * 附件临时目录清理
	 * @author Administrator
	 */
	public static class AttachTempDirCleaner{
		private boolean running = false;
		
		public void startClear(){
			if(running)
				return;
			running = true;
			this.run();
		}
		
		public void run(){
			try {
				long delayTime = 0;
				String delayTimeStr = PropertiesUtils.getProperty("attachment.temp.clear.delay");
				if(delayTimeStr==null||"".equals(delayTimeStr.trim())){
					delayTime = 86400000l;
				}else{
					try{
						delayTime = Long.valueOf(delayTimeStr).longValue();
						delayTime = delayTime*1000;
						if(delayTime<=0){
							delayTime = 86400000l;
						}
					}catch(Exception e){
						System.err.println("附件临时文件清理周期没有正确配置："+delayTimeStr);
						delayTime = 86400000l;
					}
				}
				long persistentTime = 0;
				String persistentTimeStr = PropertiesUtils.getProperty("attachment.tempfile.persistent.time");
				if(persistentTimeStr==null||"".equals(persistentTimeStr.trim())){
					persistentTime = 86400000l;
				}else{
					try{
						persistentTime = Long.valueOf(persistentTimeStr).longValue();
						persistentTime = persistentTime*1000;
						if(persistentTime<=0){
							persistentTime = 86400000l;
						}
					}catch(Exception e){
						System.err.println("附件临时文件清理周期没有正确配置："+persistentTimeStr);
						persistentTime = 86400000l;
					}
				}
				clearn(delayTime,persistentTime);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		
		private void clearn(final long delayTime,final long persistentTime){
			(new Timer()).schedule(new TimerTask(){
				@Override
				public void run() {
					//System.out.println(new Date().toString()+":附件临时目录开始清理——"+delayTime+","+persistentTime);
					File swfuploadTmpDir = new File(SwfuploadUtil.APP_ROOT_REALPATH+"/"+SwfuploadUtil.SWFUPLOAD_DOWNLOAD_TEMP_DIR);
					clear(swfuploadTmpDir,persistentTime);
					File swfuploadTmpBatchDir = new File(SwfuploadUtil.APP_ROOT_REALPATH + "/" + SwfuploadUtil.SWFUPLOAD_BATCHDOWNLOAD_TEMP_DIR);
					clear(swfuploadTmpBatchDir,persistentTime);
				}
				private void clear(File dir,long persistentTime){
					if(dir.exists()){
						File[] tempf = null;
						try {
							tempf = dir.listFiles();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						if(tempf==null)
							return;
						int len = tempf.length;
						for(int i=0;i<len;i++){
							if(System.currentTimeMillis()-tempf[i].lastModified()>=persistentTime){
								try {
									if(tempf[i].isFile()){
										tempf[i].delete();
									}
									else{
										org.apache.commons.io.FileUtils.deleteDirectory(tempf[i]);
									}
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}, 1000, delayTime);//默认延迟24小时清理
		}
	}
	//附件临时目录清理
	public static final SwfuploadUtil.AttachTempDirCleaner tempAttachCleaner = new SwfuploadUtil.AttachTempDirCleaner();
	
	// 批量下载文件名串分隔符
	public static final String BATCH_DWN_FILE_SEPARATOR = "@semi@";
	// 批量下载文件名分隔符
	public static final String BATCH_DWN_FILE_NAME_SEPARATOR = "@comm@";
	// div
	public static final String SWFUPLOAD_DIV = "UploaderContainer";
	//form
	public static final String SWFUPLOAD_FORM = "_SWFUploaderForm";
	//附件存储的根路径，也是默认的存储路径
	public static String SWFUPLOAD_UPLOAD_PATH;
	//工程根路径
	public static String APP_ROOT_REALPATH;
	// 批量下载文件名
	public static final String SWFUPLOAD_BATCH_FILENAME = "attachment.zip";
	//附件批量下载”接口“所使用的临时目录名
	public static final String SWFUPLOAD_BATCHDOWNLOAD_TEMP_DIR = "sys_batchdownload_tempDir";
	//附件单个文件下载“接口”所使用的临时目录名
	public static final String SWFUPLOAD_DOWNLOAD_TEMP_DIR = "sys_download_tempDir";
	
	//上传到本地存储
	public static final String STORE_TYPE_LOCAL = "local";
	//上传到FTP进行存储
	public static final String STORE_TYPE_FTP = "ftp";
}

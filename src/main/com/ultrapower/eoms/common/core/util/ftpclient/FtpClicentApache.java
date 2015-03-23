package com.ultrapower.eoms.common.core.util.ftpclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpClicentApache implements IFtpClientServices{
	private FTPClient ftpClient = new FTPClient();
	public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE; //binary传输模式
	public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE; //ascii传输模式
	
	public void closeServer() throws Exception{
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	public boolean connectServer(String ip, int port, String user, String pwd)
			throws Exception {
		if(ftpClient==null){
			ftpClient = new FTPClient();
		}
		ftpClient.connect(ip, port);
		//System.out.println("Connected to " + ip + ".");
		//System.out.println(ftpClient.getReplyCode());
		ftpClient.login(user, pwd);
		ftpClient.setFileType(FtpClicentApache.BINARY_FILE_TYPE);//设定为二进制传输模式
		return true;
	}

	public boolean downloadFile(String romteDir, String romteFilename,
			String localDir, String localFilename) throws Exception {
		if(romteFilename==null||localFilename==null)
			return false;
		this.ftpClient.changeWorkingDirectory("/");
		romteDir = this.processPath(romteDir);
		localDir = this.processPath(localDir);
		File of = null;
		if("".equals(localDir)){
			of = new File(localFilename);
		}
		else{
			File tempDir = new File(localDir);
			if(!tempDir.exists())
				tempDir.mkdirs();
			of = new File(localDir,localFilename);
		}
		if(!"".equals(romteDir)){
			boolean tag = this.ftpClient.changeWorkingDirectory(romteDir);
			if(!tag)
				return false;
		}
		OutputStream os = null;
		boolean flag = false;
		try {
			os = new BufferedOutputStream(new FileOutputStream(of));
			flag = this.ftpClient.retrieveFile(romteFilename, os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(os!=null)
				os.close();
		}
		return flag;
	}

	public boolean downloadFile(String romtePath, String localPath)
			throws Exception {
		if(localPath==null||localPath==null
				||"".equals(localPath)||"".equals(romtePath))
			return false;
		romtePath = this.processPath(romtePath);
		localPath = this.processPath(localPath);
		String romteDir = null;
		String romteFilename = null;
		String localDir = null;
		String localFileName = null;
		int index = localPath.lastIndexOf("/");
		if(index==-1){
			localFileName = localPath;
		}else{
			localDir = localPath.substring(0, index);
			localFileName = localPath.substring(index+1);
		}
		index = romtePath.lastIndexOf("/");
		if(index==-1){
			romteFilename = romtePath;
		}else{
			romteDir = romtePath.substring(0, index);
			romteFilename = romtePath.substring(index+1);
		}
		return downloadFile(romteDir,romteFilename,localDir,localFileName);
	}

	public void downloadForUrl(String url, String localPath) throws Exception {
		// TODO Auto-generated method stub
	}

	public boolean uploadFile(String romteDir, String romteFilename,
			String localDir, String localFilename) throws Exception {
		if(localFilename==null||"".equals(localFilename))
			return false;
		this.ftpClient.changeWorkingDirectory("/");
		romteDir = this.processPath(romteDir);
		localDir = this.processPath(localDir);
		File inf = null;
		if("".equals(localDir))
			inf = new File(localFilename);
		else
			inf = new File(localDir,localFilename);
		if(!"".equals(romteDir))
			this.ensureDirectories(romteDir);
		if(romteFilename==null)
			romteFilename = localFilename;
		InputStream ins = null;
		boolean flag = false;
		try {
			ins = new BufferedInputStream(new FileInputStream(inf));
			flag = ftpClient.storeFile(romteFilename, ins);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ins!=null)
				ins.close();
		}
		return flag;
	}

	public boolean uploadFile(String romtePath, String localPath)
			throws Exception {
		if(localPath==null||"".equals(localPath.trim()))
			return false;
		romtePath = this.processPath(romtePath);
		localPath = this.processPath(localPath);
		String romteDir = null;
		String romteFilename = null;
		String localDir = null;
		String localFileName = null;
		int index = localPath.lastIndexOf("/");
		if(index==-1){
			localFileName = localPath;
		}else{
			localDir = localPath.substring(0, index);
			localFileName = localPath.substring(index+1);
		}
		index = romtePath.lastIndexOf("/");
		if(index==-1){
			if("".equals(romtePath))
				romteFilename = localFileName;
			else
				romteFilename = romtePath;
		}else{
			romteDir = romtePath.substring(0, index);
			romteFilename = romtePath.substring(index+1);
		}
		return uploadFile(romteDir,romteFilename,localDir,localFileName);
	}

	public InputStream downloadFileAsStream(String remoteDir,
			String remoteFilename) throws Exception{
		if(remoteFilename==null)
			return null;
		this.ftpClient.changeWorkingDirectory("/");
		remoteDir = this.processPath(remoteDir);
		if(!"".equals(remoteDir))
			this.ftpClient.changeWorkingDirectory(remoteDir);
		return ftpClient.retrieveFileStream(remoteFilename);
	}

	public boolean uploadFile(String remoteDir, String remoteFilename,
			InputStream ins) throws Exception{
		if(remoteFilename==null||ins==null)
			return false;
		this.ftpClient.changeWorkingDirectory("/");
		remoteDir = this.processPath(remoteDir);
		if(!"".equals(remoteDir))
			this.ensureDirectories(remoteDir);
		try {
			this.ftpClient.storeFile(remoteFilename, ins);
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally{
			if(ins!=null)
				ins.close();
		}
		return false;
	}
	
	/**
	 * 删除文件
	 * @param pathName
	 * @return
	 * @throws IOException
	 */
	public boolean deleteFile(String pathName) throws IOException {
		if(pathName==null)
			return false;
		else
			pathName = this.processPath(pathName);
		return ftpClient.deleteFile(pathName);
	}

	/**
	 * 创建多级目录
	 * @param pathName
	 * @throws IOException
	 */
	private void ensureDirectories(String pathName) throws IOException{
		if(pathName==null)
			return;
		pathName = pathName.replace("\\", "/");
		if(pathName.startsWith("/"))
			pathName = pathName.substring(1);
		String[] subPath = pathName.split("/");
		int len = subPath.length;
		String tempPath;
		int i = 0;
		boolean b;
		for(;i<len;i++){// a/b/c
			tempPath = subPath[i];
//			if(!this.existDirectory(tempPath)){
//				ftpClient.makeDirectory(tempPath);
//			}
			b = ftpClient.changeWorkingDirectory(tempPath);
			if(!b){
				ftpClient.makeDirectory(tempPath);
				ftpClient.changeWorkingDirectory(tempPath);
			}
		}
	}
	
	/**
	 * 判断该路径是否存在
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean existDirectory(String path) throws IOException {
		boolean flag = false;
//		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
//		for (FTPFile ftpFile : ftpFileArr) {
//			if (ftpFile.isDirectory()
//					&& ftpFile.getName().equalsIgnoreCase(path)) {
//				flag = true;
//				break;
//			}
//		}
		flag = ftpClient.changeWorkingDirectory(path);
		if(flag){
			ftpClient.changeToParentDirectory();
		}
		return flag;
	}
	
	private String processPath(String path){
		if(path==null||"".equals(path.trim()))
			return "";
		path = path.replaceAll("\\\\", "/");
		if("/".equals(path))
			return "";
		if(path.startsWith("/"))
			path = path.substring(1);
		if(path.endsWith("/"))
			path = path.substring(0, path.length()-1);
		return path;
	}
	
	public static void main(String[] args){
		FtpClicentApache c = new FtpClicentApache();
		try{
			c.connectServer("192.168.106.195", 21, "Demo", "wf2010");
			//c.uploadFile(null, "D:\\hello.txt");
			//c.uploadFile("d1/d2/d3/hello1.txt", "D:\\hello.txt");
			
			//c.uploadFile(null, null, "D:", "hello3.txt");
			//c.uploadFile("d1/d2", "hello3.txt", "d:", "hello3.txt");
			
//			c.downloadFile("hello3.txt", "D:\\hello4.txt");
//			c.downloadFile("d1/d2/hello3.txt", "d:\\hello5.txt");
			
//			c.downloadFile(null, "hello3.txt", "D:", "hello6.txt");
//			c.downloadFile("d1/d2/d3/", "hello1.txt", "d:\\d1\\d2\\d3", "hello7.txt");
			
//			c.deleteFile("d1/d2/d3/hello1.txt");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				c.closeServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

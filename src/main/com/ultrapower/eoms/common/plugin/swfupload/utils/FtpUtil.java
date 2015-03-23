package com.ultrapower.eoms.common.plugin.swfupload.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpUtil {
	private FTPClient ftpClient;
	public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE; //binary传输模式
	public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE; //ascii传输模式

	/**
	 * 连接FTP服务器
	 * @param ftpConfig --该对象包含了链接ftp服务器的所有参数信息
	 * @throws SocketException
	 * @throws IOException
	 */
	public void connectServer(FtpConfig ftpConfig) throws SocketException,
			IOException {
		String server = ftpConfig.getServer();
		int port = ftpConfig.getPort();
		String user = ftpConfig.getUsername();
		String password = ftpConfig.getPassword();
		String location = ftpConfig.getLocation()==null?"":ftpConfig.getLocation();
		connectServer(server, port, user, password, location);
	}
	
	/**
	 * 链接FTP服务器
	 * @param server --服务器地址
	 * @param port --端口
	 * @param user --用户名
	 * @param password --密码
	 * @param path --访问的相对路径
	 * @throws SocketException
	 * @throws IOException
	 */
	public void connectServer(String server, int port, String user,
			String password, String path) throws SocketException, IOException {
		ftpClient = new FTPClient();
		ftpClient.connect(server, port);
		System.out.println("Connected to " + server + ".");
		System.out.println(ftpClient.getReplyCode());
		ftpClient.login(user, password);
		// Path is the sub-path of the FTP path   
		if (path.length() != 0) {
			ensureDirectories(path);
			//ftpClient.changeWorkingDirectory(path);
		}
	}

	/**
	 * 设置FTP传输模式
	 * @param fileType
	 * @throws IOException
	 */
	public void setFileType(int fileType) throws IOException {
		ftpClient.setFileType(fileType);
	}

	public void closeServer() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	//=======================================================================   
	//==         目录操作相关       =====   
	// The following method using relative path better.   
	//=======================================================================   
	
	/**
	 * 更改操作路径
	 * @param path
	 */
	public boolean changeDirectory(String path) throws IOException {
		return ftpClient.changeWorkingDirectory(path);
	}
	
	/**
	 * 新建路径
	 * @param pathName
	 * @return
	 * @throws IOException
	 */
	public boolean createDirectory(String pathName) throws IOException {
		return ftpClient.makeDirectory(pathName);
	}
	
	/**
	 * 创建多级目录
	 * @param pathName
	 * @throws IOException
	 */
	public void ensureDirectories(String pathName) throws IOException{
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
//				this.createDirectory(tempPath);
//			}
			b = ftpClient.changeWorkingDirectory(tempPath);
			if(!b){
				this.createDirectory(tempPath);
				this.changeDirectory(tempPath);
			}
		}
	}
	
	/**
	 * 删除路径
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean removeDirectory(String path) throws IOException {
		return ftpClient.removeDirectory(path);
	}

	/**
	 * 删除路径下的所有子目录和文件
	 * @param path
	 * @param isAll
	 * @return
	 * @throws IOException
	 */ 
	public boolean removeDirectory(String path, boolean isAll)
			throws IOException {

		if (!isAll) {
			return removeDirectory(path);
		}

		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr == null || ftpFileArr.length == 0) {
			return removeDirectory(path);
		}
		//    
		for (FTPFile ftpFile : ftpFileArr) {
			String name = ftpFile.getName();
			if (ftpFile.isDirectory()) {
				System.out.println("* [sD]Delete subPath [" + path + "/" + name
						+ "]");
				removeDirectory(path + "/" + name, true);
			} else if (ftpFile.isFile()) {
				System.out.println("* [sF]Delete file [" + path + "/" + name
						+ "]");
				deleteFile(path + "/" + name);
			} else if (ftpFile.isSymbolicLink()) {
				
			} else if (ftpFile.isUnknown()) {
				
			}
		}
		return ftpClient.removeDirectory(path);
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

	

	/**
	 * 列出路径下的文件
	 * @param path
	 * @return
	 * @throws IOException
	 */ 
	public List<String> getFileList(String path) throws IOException {
		// listFiles return contains directory and file, it's FTPFile instance   
		// listNames() contains directory, so using following to filer directory.   
		//String[] fileNameArr = ftpClient.listNames(path);   
		FTPFile[] ftpFiles = ftpClient.listFiles(path);

		List<String> retList = new ArrayList<String>();
		if (ftpFiles == null || ftpFiles.length == 0) {
			return retList;
		}
		for (FTPFile ftpFile : ftpFiles) {
			if (ftpFile.isFile()) {
				retList.add(ftpFile.getName());
			}
		}
		return retList;
	}
	
	/**
	 * 删除文件
	 * @param pathName
	 * @return
	 * @throws IOException
	 */
	public boolean deleteFile(String pathName) throws IOException {
		return ftpClient.deleteFile(pathName);
	}
	
	// #2. upload to ftp server   
	// InputStream <------> byte[]  simple and See API   
	
	/**
	 * 上传文件
	 * @param fileName
	 * @param newName
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(String fileName, String newName)
			throws IOException {
		boolean flag = false;
		InputStream iStream = null;
		try {
			iStream = new FileInputStream(fileName);
			flag = ftpClient.storeFile(newName, iStream);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			if (iStream != null) {
				iStream.close();
			}
		}
		return flag;
	}
	
	/**
	 * 上传文件
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(String fileName) throws IOException {
		return uploadFile(fileName, fileName);
	}
	
	/**
	 * 以流文件的形式上传文件
	 * @param iStream
	 * @param newName
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(InputStream iStream, String newName)
			throws IOException {
		boolean flag = false;
		try {
			// can execute [OutputStream storeFileStream(String remote)]   
			// Above method return's value is the local file stream.   
			flag = ftpClient.storeFile(newName, iStream);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			if (iStream != null) {
				iStream.close();
			}
		}
		return flag;
	}

	    
	/**
	 * 下载文件到本地目录
	 * @param remoteFileName
	 * @param localFileName
	 * @return
	 * @throws IOException
	 */
	public boolean download(String remoteFileName, String localFileName)
			throws IOException {
		boolean flag = false;
		File outfile = new File(localFileName);
		OutputStream oStream = null;
		try {
			oStream = new FileOutputStream(outfile);
			flag = ftpClient.retrieveFile(remoteFileName, oStream);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			if(oStream!=null)
				oStream.close();
		}
		return flag;
	}
	
	/**
	 * 下载文件，获得文件输入流
	 * @param sourceFileName
	 * @return
	 * @throws IOException
	 */
	public InputStream downFile(String sourceFileName) throws IOException {
		return ftpClient.retrieveFileStream(sourceFileName);
	}
	
	public FTPClient getClient(){
		return this.ftpClient;
	}
	
	
	public static void main(String[] args) throws SocketException, IOException{
		//
	}
}
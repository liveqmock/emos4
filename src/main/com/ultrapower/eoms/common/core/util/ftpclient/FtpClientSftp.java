package com.ultrapower.eoms.common.core.util.ftpclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.ultrapower.eoms.common.core.util.NumberUtils;

public class FtpClientSftp implements IFtpClientServices {

	private ChannelSftp sftp;
	private FileOutputStream out;
	private FileInputStream in;
	private String rootPath = "";

	public void closeServer() throws Exception{
		// TODO Auto-generated method stub
		try {
			if (sftp != null) {
				sftp.disconnect();
				//System.out.println("sftp disconnect");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean connectServer(String ip, int port, String user, String pwd) throws Exception {
		// TODO Auto-generated method stub
		boolean isSuccess = false;
		try {
			JSch jsch = new JSch();
			jsch.getSession(user, ip, port);
			Session sshSession = jsch.getSession(user, ip, port);
			//System.out.println("Session created");
			sshSession.setPassword(pwd);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			//System.out.println("Session connected");
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			rootPath = sftp.pwd();
			//System.out.println("Connected to " + ip);
			isSuccess = true;
		} catch (Exception e) {
			throw new Exception("Connect ftp server error:" + e.getMessage());
		}
		return isSuccess;
	}

	public boolean deleteFile(String romtePath) throws Exception {
		// TODO Auto-generated method stub
		boolean isSuccess = false;
		if(romtePath != null && !"".equals(romtePath)) {
			romtePath = romtePath.replaceAll("\\\\", "/");
		}
		try {
			if (sftp != null) {
				sftp.cd(rootPath);
				sftp.setFilenameEncoding("GBK");
				String romteFilename = "";
				int space = romtePath.lastIndexOf("/");
				if(space > 0) {
					String romteDir = romtePath.substring(0, space);
					romteFilename = romtePath.substring(space + 1);
					romteDir = this.removeBothWord(romteDir, "/");
					sftp.cd(romteDir);
				}
				sftp.rm(romteFilename);
				isSuccess = true;
			} else {
				throw new Exception ("Delete file failure, please look up your path!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

	public boolean uploadFile(String romteDir, String romteFilename, String localDir, String localFilename) throws Exception {
		// TODO Auto-generated method stub
		return this.operateFile(romteDir, romteFilename, localDir, localFilename, "upload");
	}

	public boolean uploadFile(String romtePath, String localPath) throws Exception {
		// TODO Auto-generated method stub
		return this.operateFile(romtePath, localPath, "upload");
	}
	
	public boolean downloadFile(String romteDir, String romteFilename, String localDir, String localFilename) throws Exception {
		// TODO Auto-generated method stub
		return this.operateFile(romteDir, romteFilename, localDir, localFilename, "download");
	}

	public boolean downloadFile(String romtePath, String localPath) throws Exception {
		// TODO Auto-generated method stub
		return this.operateFile(romtePath, localPath, "download");
	}

	public InputStream downloadFileAsStream(String romteDir, String romteFilename) throws Exception {
		InputStream inputStream = null;
		try {
			if (sftp != null) {
				sftp.cd(rootPath);
				sftp.setFilenameEncoding("GBK");
				if(romteDir != null && !"".equals(romteDir)) {
					sftp.cd(romteDir);
				}
				inputStream = sftp.get(romteFilename);
			} else {
				throw new Exception ("Download the file failure, please connect ftp server!");
			}
		} catch (Exception e) {
			throw new Exception ("Download the file failure, please look up your path!");
		}
		return inputStream;
	}

	public boolean uploadFile(String romteDir, String romteFilename, InputStream ins) throws Exception {
		// TODO Auto-generated method stub
		boolean isSuccess = false;
		try {
			if (sftp != null) {
				sftp.cd(rootPath);
				sftp.setFilenameEncoding("GBK");
				System.out.println();
				if(romteDir != null && !"".equals(romteDir)) {
					this.mkdirSftp(romteDir);
				}
				sftp.put(ins, romteFilename);
				isSuccess = true;
			} else {
				throw new Exception ("Upload the file failure, please connect ftp server!");
			}
		} catch (Exception e) {
			throw new Exception ("Upload the file failure, please look up your path!");
		} finally {
			if(ins!=null)
				ins.close();
		}
		return isSuccess;
	}
	
	public void downloadForUrl(String url, String localPath) throws Exception {
		// TODO Auto-generated method stub
		//url参数格式如下
		//ftp://hdpasm:hdpasm@192.168.101.13:22/路径1/路径2/上传的文件名称.jpg
		boolean isUrl = true;
		if(url != null && !"".equals(url) && url.startsWith("ftp://")) {
			//解析url
			String user = "";
			String pwd = "";
			String ip = "";
			int port = 0;
			int space = 0;
			url = url.replace("ftp://", "");
			if(isUrl) {
				//截取用户名
				space = url.indexOf(":");
				if(space > 0) {
					user = url.substring(0, space);
					url = url.substring(space + 1);
				} else {
					isUrl = false;
				}
			}
			if(isUrl) {
				//截取密码
				space = url.indexOf("@");
				if(space > 0) {
					pwd = url.substring(0, space);
					url = url.substring(space + 1);
				} else {
					isUrl = false;
				}
			}
			if(isUrl) {
				//截取ip地址
				space = url.indexOf(":");
				if(space > 0) {
					ip = url.substring(0, space);
					url = url.substring(space + 1);
				} else {
					isUrl = false;
				}
			}
			if(isUrl) {
				//截取端口号
				space = url.indexOf("/");
				if(space > 0) {
					port = NumberUtils.formatToInt(url.substring(0, space));
					url = url.substring(space + 1);
				} else {
					isUrl = false;
				}
			}
			if(isUrl) {
				//创建sftp实例
				FtpClientSftp sftp = new FtpClientSftp();
				try {
					sftp.connectServer(ip, port, user, pwd);
					sftp.downloadFile(url, localPath);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new Exception (e.getMessage());
				} finally {
					//关闭sftp
					if(sftp != null) {
						sftp.closeServer();
					}
				}
			}
		} else {
			isUrl = false;
		}
		if(!isUrl) {
			throw new Exception ("the parameter url is wrong!");
		}
	}
	
	/**
	 * sftp文件上传主体方法
	 * @param romteDir 服务器文件目录
	 * @param romteFilename 服务器文件名称
	 * @param localPath 本地文件路径
	 * @return
	 * @throws Exception
	 */
	private boolean uploadFile(String romteDir, String romteFilename, String localPath) throws Exception {
		boolean isSuccess = false;
		try {
			if (sftp != null) {
				sftp.cd(rootPath);
				sftp.setFilenameEncoding("GBK");
				System.out.println();
				if(romteDir != null && !"".equals(romteDir)) {
					this.mkdirSftp(romteDir);
				}
				File file = new File(localPath);
				if(!file.exists()){
					throw new Exception("local file doesn't exist:"+file.getAbsolutePath());
				}
				if((romteFilename == null || "".equals(romteFilename)) && file != null) {
					romteFilename = file.getName();
				}
				in = new FileInputStream(file);
				sftp.put(in, romteFilename);
				isSuccess = true;
			} else {
				throw new Exception ("Upload the file failure, please connect ftp server!");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(in!=null)
				in.close();
		}
		return isSuccess;
	}

	/**
	 * 验证远程文件夹是否存在，如果不存在则创建文件夹
	 * @param romteDir
	 * @return
	 */
	private boolean mkdirSftp(String romteDir) throws Exception {
		boolean result = false;
		String [] dirArr = romteDir.split("/");
		String dir;
		try {
			for(int i=0; i<dirArr.length ; i++) {
				dir = dirArr[i];
				if(dir == null) {
					continue;
				}
				if(!this.existDir(dir)) {
					for(int k=i ; k<dirArr.length ; k++) {
						sftp.mkdir(dirArr[k]);
						sftp.cd(dirArr[k]);
					}
					break;
				}
				sftp.cd(dir);
			}
			result = true;
		} catch (SftpException e) {
			throw new Exception (e.getMessage());
		}
		return result;
	}
	
	/**
	 * 验证当前路径下是否存在指定文件夹
	 * @param dir 文件夹名称
	 * @return
	 * @throws Exception
	 */
	private boolean existDir(String dir) throws Exception {
		boolean result = false;
		Vector<LsEntry> v;
		try {
			v = sftp.ls(sftp.pwd());
			for (Iterator iterator = v.iterator(); iterator.hasNext();) {
				LsEntry lsEntry = (LsEntry) iterator.next();
				String tmp = lsEntry.getFilename();
				if(lsEntry.getFilename().equalsIgnoreCase(dir)) {
					result = true;
					break;
				}
			}
		} catch (SftpException e) {
			throw new Exception (e.getMessage());
		}
		return result;
	}
	
	/**
	 * sftp文件下载主体方法
	 * @param romteDir 服务器目录
	 * @param romteFilename 服务器文件名称
	 * @param localPath 本地文件路径
	 * @return
	 * @throws Exception
	 */
	private boolean downloadFile(String romteDir, String romteFilename, String localPath) throws Exception {
		boolean isSuccess = false;
		try {
			if (sftp != null) {
				sftp.cd(rootPath);
				sftp.setFilenameEncoding("GBK");
				if(romteDir != null && !"".equals(romteDir)) {
					sftp.cd(romteDir);
				}
				File file = new File(localPath);
				if(file.isDirectory()){
					throw new Exception("not a file:"+localPath);
				}
				File dir = file.getParentFile();
				if(!dir.exists()){
					dir.mkdirs();
				}
				out = new FileOutputStream(file);
				sftp.get(romteFilename, out);
				isSuccess = true;
			} else {
				throw new Exception ("Download the file failure, please connect ftp server!");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(out!=null)
				out.close();
		}
		return isSuccess;
	}

	/**
	 * 处理文件路径再根据操作类型上传或下载附件
	 * @param romteDir 服务器文件目录
	 * @param romteFilename 服务器文件名称
	 * @param localDir 本地文件目录
	 * @param localFilename 本地保存文件名称
	 * @param optype 操作类型 上传：upload 下载：download
	 * @return
	 * @throws Exception
	 */
	private boolean operateFile(String romteDir, String romteFilename, String localDir, String localFilename, String optype) throws Exception {
		if(localDir == null || romteDir == null) {
			throw new Exception ("the parameters romteDir or localDir is null!");
		}
		romteDir = romteDir.replaceAll("\\\\", "/");
		localDir = localDir.replaceAll("\\\\", "/");
		romteDir = this.removeBothWord(romteDir, "/"); //去除字符串romteDir两端的/字符
		localDir = this.removeBothWord(localDir, "/"); //去除字符串localDir两端的/字符
		String localPath = localDir;
		if (localFilename != null && !"".equals(localFilename)) {
			localPath += "/" + localFilename;
		} else {
			if(romteFilename != null) {
				localPath += "/" + romteFilename;
			}
		}
		boolean isSuccess = false;
		if(optype != null && "upload".equals(optype)) {
			isSuccess = this.uploadFile(romteDir, romteFilename, localPath);
		} else if (optype != null && "download".equals(optype)) {
			isSuccess = this.downloadFile(romteDir, romteFilename, localPath);
		}
		return isSuccess;

	}
	
	/**
	 * 处理文件路径再根据操作类型上传或下载附件
	 * @param romtePath 服务器文件路径
	 * @param localPath 本地文件路径
	 * @param optype 操作类型 上传：upload 下载：download
	 * @return
	 * @throws Exception
	 */
	private boolean operateFile(String romtePath, String localPath, String optype) throws Exception {
		if(romtePath == null || localPath == null) {
			throw new Exception ("the parameters romtePath or localPath is null!");
		}
		romtePath = romtePath.replaceAll("\\\\", "/");
		localPath = localPath.replaceAll("\\\\", "/");
		romtePath = this.removeBothWord(romtePath, "/"); //去除字符串romtePath两端的/字符
		localPath = this.removeBothWord(localPath, "/"); //去除字符串localPath两端的/字符
		int space = romtePath.lastIndexOf("/");
		String romteDir = "";
		String romteFilename = "";
		if(space > 0) {
			romteDir = romtePath.substring(0, space);
			romteFilename = romtePath.substring(space + 1);
		} else {
			romteFilename = romtePath;
		}
		boolean isSuccess = false;
		if(optype != null && "upload".equals(optype)) {
			isSuccess = this.uploadFile(romteDir, romteFilename, localPath);
		} else if (optype != null && "download".equals(optype)) {
			isSuccess = this.downloadFile(romteDir, romteFilename, localPath);
		}
		return isSuccess;
	}
	
	/**
	 * 去除字符串mainStr两端的字符word
	 * @param mainStr 主体字符串
	 * @param word 字符
	 * @return
	 */
	private String removeBothWord(String mainStr, String word) {
		if(mainStr == null || "".equals(mainStr)) {
			return "";
		}
		if(word == null || "".equals(word)) {
			return mainStr;
		}
		//去除mainStr前端的/字符
		while (mainStr.startsWith(word)) {
			mainStr = mainStr.substring(1);
		}
		//去除mainStr后端的/字符
		while (mainStr.endsWith(word)) {
			mainStr = mainStr.substring(0, mainStr.length() - 1);
		}
		return mainStr;
	}
	
	public static void main (String[] args) {
		//直接通过ftp的url形式下载文件
//		IFtpClientServices sftp = new FtpClientSftp();
//		sftp.downloadForUrl("ftp://hdpasm:hdpasm@192.168.101.13:22/路径1/路径2/上传的文件名称.jpg", "D:/Desktop/浏览器大作战.jpg");
		
		//通过ftp非url形式上传和下载文件
		IFtpClientServices sftp = new FtpClientSftp();
		try {
			boolean isSuccess = sftp.connectServer("192.168.101.13", 22, "hdpasm", "hdpasm");
			if(isSuccess) {
				//上传实例
				//sftp.uploadFile("路径1/路径2", "上传的文件名称_1.jpg", "D:/Desktop/Temp", "浏览器大作战.jpg");
				//sftp.uploadFile("路径1/路径2/上传的文件名称.jpg", "D:/Desktop/Temp/浏览器大作战.jpg");
				//下载实例
				//sftp.uploadFile("路径1/路径2", "上传的文件名称.jpg", "D:/Desktop/Temp", "浏览器大作战.jpg");
				//sftp.downloadFile("路径1/路径2/上传的文件名称.jpg", "D:/Desktop/浏览器大作战.jpg");
				//删除文件实例
				//sftp.deleteFile("路径1/路径2/上传的文件名称_1.jpg");
				//下载文件流实例
				//File file = new File("D:/Desktop/浏览器.jpg");
				//inputStreamToFile(sftp.downloadFileAsStream("路径1/路径2", "上传的文件名称.jpg"), file);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(sftp != null) {
				try {
					sftp.closeServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 流转文件方法
	 * @param ins
	 * @param file
	 */
	public static void inputStreamToFile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
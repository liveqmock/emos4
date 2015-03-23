package com.ultrapower.eoms.common.core.util.ftpclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

public class FtpClientSun implements IFtpClientServices {


	private FtpClient ftpClient;

	public void upload(String ftpSerPath, String localPath) {
		// TODO Auto-generated method stub
		
	}

	public boolean connectServer(String ip, int port, String user,
			String pwd) throws Exception 
	{
		boolean isSuccess = false;  
        try {  
            ftpClient = new FtpClient();  
            ftpClient.openServer(ip, port);  
            ftpClient.login(user, pwd);  
            isSuccess = true;  
        } catch (Exception ex) {  
            throw new Exception("Connect ftp server error:" + ex.getMessage());  
        }
		return isSuccess;
	}

	public void closeServer()
	{
		// TODO Auto-generated method stub
		try{
		 if (ftpClient!=null) 
		 {
			 ftpClient.closeServer();
		 }
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}	
	public void downloadForUrl(String url, String localPath) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean downloadFile(String romtePath, String localPath)
			throws Exception {
		// TODO Auto-generated method stub
		boolean result=false;
		
		System.out.println("FTP下载");
		System.out.println("romtePath:"+romtePath);
		System.out.println("localPath:"+localPath);
		try {  
            if (ftpClient!=null) 
            {  
/*                if(romteDir.length() != 0)
                {
                    ftpClient.cd(romteDir);
                }*/
                ftpClient.binary();  
                TelnetInputStream is = ftpClient.get(romtePath);  
                File file_out=new File(localPath) ;
/*                if(localFilename!=null && !"".equals(localFilename))
                {
                	file_out= new File(localDir + File.separator + localFilename);
                }
                else
                {
                	file_out= new File(localDir + File.separator + romteFilename);
                }
                */
                FileOutputStream os = new FileOutputStream(file_out);  
                byte[] bytes = new byte[1024];  
                int c;  
                while ((c = is.read(bytes)) != -1) {  
                    os.write(bytes, 0, c);  
                }  
                is.close();  
                os.close();
                //ftpClient.closeServer();
                result=true;
            }  
        } catch (Exception ex) {  
            throw new Exception("ftp download file error:" + ex.getMessage());  
        }  			
        return result;
	}
	public boolean downloadFile(String romteDir,String romteFilename,String localDir,String localFilename)
			throws Exception 
	{
		String romtePath=romteDir+"/"+romteFilename;
		String localPath=localDir;
		if(localFilename!=null && !"".equals(localFilename))
		{
			localPath+="/"+localFilename;
		}
		else 
		{
			localPath+="/"+romteFilename;
		}
		return downloadFile(romtePath,localPath);
		// TODO Auto-generated method stub
	}

	public boolean uploadFile(String romtePath, String localPath)
			throws Exception {
		// TODO Auto-generated method stub
		   try {  
			   if (ftpClient!=null) 
			   {
	                 ftpClient.binary();  
	                 TelnetOutputStream os = ftpClient.put(romtePath);  
	                 File file_in = new File(localPath);  
	                 FileInputStream is = new FileInputStream(file_in);  
	                 byte[] bytes = new byte[1024];  
	                 int c;  
	                 while ((c = is.read(bytes)) != -1) {  
	                     os.write(bytes, 0, c);  
	                 }  
	                 is.close();  
	                 os.close();  
	             }  
	         } catch (Exception ex) {  
	              throw new Exception("ftp upload file error:" + ex.getMessage());  
	          }  		
		return false;
	}

	public boolean uploadFile(String romteDir, String romteFilename,
			String localDir, String localFilename) throws Exception 
	{
		// TODO Auto-generated method stub
		String romtePath=romteDir+"/"+romteFilename;
		String localPath=localDir;
		if(localFilename!=null && !"".equals(localFilename))
		{
			localPath+="/"+localFilename;
		}
		else 
		{
			localPath+="/"+romteFilename;
		}
		return uploadFile(romtePath,localPath);

	}	
	
	public static void main(String[] args)
	{
		FtpClientSun ftp=new FtpClientSun();
		try {
			ftp.connectServer("192.168.101.13", 21, "hdpasm", "hdpasm");
			//boolean dw=ftp.downloadFile("WORKFLOW/11072800/1107280000000034/kbsget_6004_220_20110726_000002.doc","d:/test/1234.doc");
			ftp.uploadFile("test/浏览器大作战1.jpg","D:/Desktop/Temp/浏览器大作战.jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean deleteFile(String path) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public InputStream downloadFileAsStream(String remoteDir,
			String remoteFilename) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean uploadFile(String remoteDir, String remoteFilename,
			InputStream ins) {
		// TODO Auto-generated method stub
		return false;
	}	

}

package com.ultrapower.eoms.common.core.util.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * 基于TCP/IP的HTTP客户端类
 * @author 朱朝辉(zhuzhaohui@ultrapower.com.cn) 
 * @version Apr 21, 2010 5:50:00 PM
 */
public class HttpClientUtil {

	private URL url;
	private HttpURLConnection conn;
	/**
	 * 根据提供的URL建立连接
	 * @param httpUrl 服务端提供的链接地址
	 */
	public HttpClientUtil(String httpUrl){
		try{
			System.out.println("获取网关链接地址："+httpUrl);
			System.out.println("与网关建立连接--begin");
			url = new URL(httpUrl);
			System.out.println("与网关建立连接--end");
			System.out.println("打开网关连接--begin");
			conn = (HttpURLConnection)url.openConnection();
			System.out.println("打开网关连接--end");
			conn.setRequestProperty("Pragma:", "no-cache");
			conn.setRequestProperty("Content-Type", "text/xml;charset=GBK");
		}catch(MalformedURLException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 采用HTTP的POST方式进行数据传输
	 * @param content 向服务端发送的数据
	 */
	public void sendContent(String content){
		System.out.println("对封装后的短信报文进行发送,内容为："+content);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		try{
			System.out.println("数据通过POST方式传送");
			conn.setRequestMethod("POST");
			System.out.println("获取输出流--begin");
			OutputStream dataOut = conn.getOutputStream();
			System.out.println("获取输出流--end");
			dataOut.write(content.getBytes());
		    dataOut.flush();
		    dataOut.close();
		}catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 从网关获取到响应内容
	 * @return String 从服务端获取的数据
	 */
	public String getContent(){
		String result = "";
		try{
			conn.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			StringBuffer buffer = new StringBuffer(1024);
			while((line=in.readLine())!=null){
				buffer.append(line);
			}
			result = buffer.toString();
			System.out.println("获取到响应内容:"+result);
			}catch(IOException e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}catch(Exception e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		return result;
	}
}

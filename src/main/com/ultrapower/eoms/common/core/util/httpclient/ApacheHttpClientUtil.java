package com.ultrapower.eoms.common.core.util.httpclient;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

public class ApacheHttpClientUtil { 
	private static int connectionTimeOut = 3000;
	private static int soTimeOut = 3000;
	
	public static int sendContentByGet(String url,String parm){
		HttpClient httpClient = new HttpClient();
		HttpConnectionManagerParams managerParams = httpClient
				.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(connectionTimeOut);
		// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(soTimeOut);
		String urlR = url+"?"+parm;
		GetMethod getMethod = new GetMethod(urlR);
		String strResponse = null;
		int statusCode = -1;
		try {
			statusCode = httpClient.executeMethod(getMethod);
			strResponse = getMethod.getResponseBodyAsString();
		} catch (Exception ex) {
			throw new IllegalStateException(ex.toString());
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return statusCode;
	}
	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}
	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}
	public int getSoTimeOut() {
		return soTimeOut;
	}
	public void setSoTimeOut(int soTimeOut) {
		this.soTimeOut = soTimeOut;
	}
}

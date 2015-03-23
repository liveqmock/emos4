package com.ultrapower.wfinterface.clients;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

public class Webservices {
	
	public static Call initWebServerInfo(String webServerAddress,String p_CallOperationName) 
	{
		Service   service   =   new   Service();   
		Call call = null;
		try 
		{
			call = (Call)service.createCall();
			
			call.setTargetEndpointAddress(webServerAddress);   
			call.setOperationName(p_CallOperationName);
			
			call.setReturnType(XMLType.XSD_STRING);   
		} 
		catch (ServiceException e) {
			e.printStackTrace();
		}					
		return call;
	}
}

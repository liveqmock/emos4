package com.ultrapower.wfinterface.clients;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

public class TestWebservices {
	
	private static Call initWebServerInfo(String webServerAddress,String p_CallOperationName) 
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

	public static void InterTest()
	{
		String result="";
		try{
			String webServerAddress="http://localhost:8080/eoms4/services/InterSwitchAlarm?wsdl";
			Call m_Call = initWebServerInfo(webServerAddress,"newWorkSheet");
			m_Call.addParameter("str1",   XMLType.XSD_STRING,   ParameterMode.IN);
			m_Call.addParameter("str2",   XMLType.XSD_STRING,   ParameterMode.IN);
			m_Call.addParameter("str3",   XMLType.XSD_STRING,   ParameterMode.IN);
			m_Call.addParameter("str4",   XMLType.XSD_STRING,   ParameterMode.IN);
			m_Call.addParameter("str5",   XMLType.XSD_STRING,   ParameterMode.IN);
			m_Call.addParameter("str6",   XMLType.XSD_STRING,   ParameterMode.IN);
			result = (String)m_Call.invoke(new Object[] {"str1","str2","str3","str4","str5","str6"});
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		System.out.println("result:"+result);
		
	}
	
	public static void xfire()
	{
		
//		oper.setStyle(Style.getStyle("rpc"));
//		oper.setUse(Use.getUse("encoded"));
		String result="";
		try{
			String webServerAddress="http://localhost:8080/xfireweb2/services/InterIncidentOperate";
			Call m_Call = initWebServerInfo(webServerAddress,"newWorkSheet");
			m_Call.addParameter("serSupplier",   XMLType.XSD_STRING,   ParameterMode.IN);
			m_Call.addParameter("serCaller",   XMLType.XSD_STRING,   ParameterMode.IN);
			m_Call.addParameter("callerPwd",   XMLType.XSD_STRING,   ParameterMode.IN);
			m_Call.addParameter("callTime",   XMLType.XSD_STRING,   ParameterMode.IN);
			m_Call.addParameter("opDetail",   XMLType.XSD_STRING,   ParameterMode.IN);
			m_Call.addParameter("attachRef",   XMLType.XSD_STRING,   ParameterMode.IN);
			result = (String)m_Call.invoke(new Object[] {"str1","str2","str3","str4","str5","str6"});
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		System.out.println("result:"+result);
		
	}	
	
	public static void ptest()
	{
		try{
			String endpointURL="http://localhost:8080/xfireweb2/services/InterIncidentOperate";
			Service service=new Service();
			Call call=(Call)service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			call.setOperationName(new QName("http://services.com","newWorkSheet"));
			call.addParameter("serSupplier",   XMLType.XSD_STRING,   ParameterMode.IN);
			call.addParameter("serCaller",   XMLType.XSD_STRING,   ParameterMode.IN);
			call.addParameter("callerPwd",   XMLType.XSD_STRING,   ParameterMode.IN);
			call.addParameter("callTime",   XMLType.XSD_STRING,   ParameterMode.IN);
			call.addParameter("opDetail",   XMLType.XSD_STRING,   ParameterMode.IN);
			call.addParameter("attachRef",   XMLType.XSD_STRING,   ParameterMode.IN);
			call.setReturnType(XMLType.SOAP_STRING); 
			String ret= (String)call.invoke(new Object[] {"str1","str2","str3","str4","str5","str6"});
			System.out.println(ret);
		}catch(Exception ex)
		{
			ex.printStackTrace(); 
		}
	}
	public static void update()
	{
		try{
			String endpointURL="http://localhost:8080/pbccrc_itsm/services/AlarmService";
			Service service=new Service();
			Call call=(Call)service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			call.setOperationName(new QName("http://services.com","updateDealer"));
			call.addParameter("alarmid",   XMLType.XSD_STRING,   ParameterMode.IN);
			call.addParameter("dealer",   XMLType.XSD_STRING,   ParameterMode.IN);
			call.addParameter("dealtime",   XMLType.XSD_STRING,   ParameterMode.IN);
			call.setReturnType(XMLType.SOAP_STRING); 
			String ret= (String)call.invoke(new Object[] {"3646433","serSupplier","2013-11-15 00:03:40"});
			System.out.println(ret);
		}catch(Exception ex)
		{
			ex.printStackTrace(); 
		}
	}
	public static void main(String[] args)
	{
		update();
	}
}

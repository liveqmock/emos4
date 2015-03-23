package com.ultrapower.wfinterface.services;

import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;
import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.wfinterface.clients.Webservices;

@Transactional
public class ReleaseProcessSheet_callmyself{

	/**
	 * @param serSupplier
	 * @param serCaller
	 * @param callerPwd
	 * @param callTime
	 * @param opDetail
	 * @return result
	 */
	@Transactional
	public String newWorkSheet(String serSupplier, String serCaller,String callerPwd,String callTime,String opDetail) 
	{

		String result = "";
		try{
			String webServerAddress="http://127.0.0.1:8080/emos/services/ReleaseProcessSheet";
			
			Call m_Call =Webservices.initWebServerInfo(webServerAddress,"newWorkSheet");
			m_Call.addParameter("serSupplier",   XMLType.XSD_STRING,   ParameterMode.IN);
			m_Call.addParameter("serCaller",   XMLType.XSD_STRING,   ParameterMode.IN);
			m_Call.addParameter("callerPwd",   XMLType.XSD_STRING,   ParameterMode.IN);
			m_Call.addParameter("callTime",   XMLType.XSD_STRING,   ParameterMode.IN);
			m_Call.addParameter("opDetail",   XMLType.XSD_STRING,   ParameterMode.IN);
			
			result = (String)m_Call.invoke(new Object[] {"serSupplier","serCaller","callerPwd",TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss"),opDetail});
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 	连通测试 (isAlive)
	 */
	public String isAlive()
	{		
		return "true";
	}
}

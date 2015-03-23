package com.ultrapower.wfinterface.core.manager;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.encoding.XMLType;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.wfinterface.core.model.ConfigCallParameter;
import com.ultrapower.wfinterface.core.model.ConfigClient;
import com.ultrapower.wfinterface.core.service.IServiceCaller;

public class WebServiceCallerImpl implements IServiceCaller
{
	String nameSpace;
	/**
	 * 探测接口是否正常
	 * @return 为空表示相应服务可用，非空表示该服务当前出现问题
	 */
	public String isAlive(ConfigClient client) throws Exception
	{
		Call call = init(client.getServiceAddress(), "isAlive", client.getServiceNameSpace());
		String result = (String)call.invoke(new Object[]{});
		return result;
	}
	
	/**
	 * 调用服务
	 * @param client
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String call(ConfigClient client, DataRow data) throws Exception
	{
		Call call = init(client.getServiceAddress(), client.getServiceOperateName(), client.getServiceNameSpace());
		List<ConfigCallParameter> paras = client.getParameters();
		List<Object> values = new ArrayList<Object>();
		OperationDesc opDesc = new OperationDesc();
		for(ConfigCallParameter para : paras)
		{
			String value = StringUtils.checkNullString(data.getString(para.getName().toUpperCase()));
			if(para.getDataType() == 7 &&!value.equals(""))
			{
				opDesc.addParameter(new QName(nameSpace, para.getName()), new QName(nameSpace, "string"), String.class, ParameterDesc.IN, false, false);
				values.add(TimeUtils.formatIntToDateString(Long.valueOf(value)));
		 
			}
			else if(para.getDataType() == 2 &&!value.equals(""))
			{
				opDesc.addParameter(new QName(nameSpace, para.getName()), new QName(nameSpace, "int"), String.class, ParameterDesc.IN, false, false);
				values.add(NumberUtils.formatToInt(value));
			}
			else if(para.getDataType() == 6 &&!value.equals(""))
			{
				opDesc.addParameter(new QName(nameSpace, para.getName()), new QName(nameSpace, "string"), String.class, ParameterDesc.IN, false, false);
				if(value.endsWith(";")) value = value.substring(0, value.length()-1);
				String[] dics = value.split(";");
				for(String dic : dics)
				{
					String[] dicarr = dic.split(":");
					if(dicarr[0].trim().equals(value.trim()))
					{
						value = dicarr[1].trim();
					}
				}
				values.add(value);
			}
			else
			{
				opDesc.addParameter(new QName(nameSpace, para.getName()), new QName(nameSpace, "string"), String.class, ParameterDesc.IN, false, false);
				values.add(value);
			}
		}
		if(paras != null && paras.size() > 0)
		{
			opDesc.setReturnType(XMLType.XSD_STRING);
			call.setOperation(opDesc);
		}
		
		String result = (String)call.invoke(values.toArray());
		return result;
	}

/*	private Call init(String serviceName, String operateName,String ns)
	  {
	    Service service = new Service();
	    Call call = null;
	    try
	    {
	      call = (Call)service.createCall();
	      call.setTargetEndpointAddress(serviceName);
	      call.setOperationName(operateName);
	      this.nameSpace = ("http://service.eoms.chinamobile.com" + serviceName.substring(serviceName.lastIndexOf("/")));
	    }
	    catch (ServiceException e)
	    {
	      e.printStackTrace();
	    }
	    return call;
	  }	*/
	
	/**
	 * .NET的调用
	 * @param serviceName
	 * @param operateName
	 * @param ns
	 * @return
	 */
	private Call init(String serviceName, String operateName, String ns)
	{
		Service service = new Service();
		Call call = null;
		try
		{
			if(ns == null || ns.equals(""))
			{
				String tmpAddr = serviceName.substring(serviceName.lastIndexOf("/"));
				if(tmpAddr.lastIndexOf(".") > -1)
				{
					tmpAddr = tmpAddr.substring(0, tmpAddr.lastIndexOf("."));
					nameSpace = "http://service.eoms.chinamobile.com" + tmpAddr;
				}
				else
				{
					nameSpace = "http://service.eoms.chinamobile.com" + tmpAddr;
				}
			}
			else
			{
				nameSpace = ns;
			}
			System.out.println("serviceName:" + serviceName);
			System.out.println("nameSpace:" + nameSpace);
			System.out.println("operateName:" + operateName);
			
			call = (Call)service.createCall();
			call.setTargetEndpointAddress(serviceName);
			call.setOperationName(new QName(nameSpace, operateName));
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(nameSpace + "//" + operateName);
			call.setReturnType(XMLType.XSD_STRING);
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
		}
		return call;
	}
	
	public static void main(String[] args)
	{
		IServiceCaller caller = new WebServiceCallerImpl();
		ConfigClient client = new ConfigClient();
//		client.setServiceAddress("http://localhost:14387/TestWS/Service.asmx");
		client.setServiceAddress("http://192.168.0.204:8080/eoms4/services/SheetStateSync");
//		client.setServiceOperateName("HelloWorld");
		client.setServiceOperateName("syncSheetState");
		List<ConfigCallParameter> paras = new ArrayList<ConfigCallParameter>();
//		ConfigCallParameter para = new ConfigCallParameter("name", 4, ConfigCallParameter.toType("STRING"), "", ConfigCallParameter.toMode("IN"));
		ConfigCallParameter para1 = new ConfigCallParameter("serSupplier", 4, ConfigCallParameter.toType("STRING"), "", ConfigCallParameter.toMode("IN"));
		ConfigCallParameter para2 = new ConfigCallParameter("serCaller", 4, ConfigCallParameter.toType("STRING"), "", ConfigCallParameter.toMode("IN"));
		ConfigCallParameter para3 = new ConfigCallParameter("callerPwd", 4, ConfigCallParameter.toType("STRING"), "", ConfigCallParameter.toMode("IN"));
		ConfigCallParameter para4 = new ConfigCallParameter("callTime", 4, ConfigCallParameter.toType("STRING"), "", ConfigCallParameter.toMode("IN"));
		ConfigCallParameter para5 = new ConfigCallParameter("opDetail", 4, ConfigCallParameter.toType("STRING"), "", ConfigCallParameter.toMode("IN"));
//		paras.add(para);
		paras.add(para1);
		paras.add(para2);
		paras.add(para3);
		paras.add(para4);
		paras.add(para5);
		client.setParameters(paras);
		DataRow row = new DataRow();
//		row.put("name", "HE He!");
		row.put("serSupplier", "aa1");
		row.put("serCaller", "aa2");
		row.put("callerPwd", "aa3");
		row.put("callTime", "2011-08-08 08:08:08");
		row.put("opDetail", "<opDetail>asdf</opDetail>");
		try
		{
			System.out.println(caller.call(client, row));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

package com.ultrapower.eoms.common.core.component.sms.manager;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.UUID;

import javax.xml.rpc.ServiceException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.component.sms.service.SendService;
import com.ultrapower.wfinterface.clients.sendMsgClient.CUNFSendMessage;
import com.ultrapower.wfinterface.clients.sendMsgClient.CUNFSendMessageLocator;
import com.ultrapower.wfinterface.clients.sendMsgClient.MessageServicePortType;
import com.ultrapower.wfinterface.clients.sendMsgClient.SendMessageRequest;

public class SendSmByWS implements SendService {
	private String sendXml;
	@Override
	public String SendSm(String pid, String mobile, String content) {
		String rMsg = "";
		String flowNo = UUID.randomUUID().toString().replaceAll("-", "");
		sendXml = buildServiceXml(mobile,content,flowNo);
		SendMessageRequest sendMessageRequest = new SendMessageRequest();
		sendMessageRequest.setServicexml(sendXml);
		CUNFSendMessage cunfSendMessage = new CUNFSendMessageLocator();
		MessageServicePortType messageServicePortType;
		String url = PropertiesUtils.getProperty("sms.url");
		try {
			messageServicePortType = cunfSendMessage.getSOAPEventSource(new URL(url));
			rMsg = "MAINBODY:"+messageServicePortType.sendMessage(sendMessageRequest);
		} catch (ServiceException e) {
			e.printStackTrace();
			rMsg = "ERROR:ServiceException(MalformedURLException);";
		} catch (RemoteException e) {
			e.printStackTrace();
			rMsg = "ERROR:RemoteException;";
		} catch (Exception e){
			e.printStackTrace();
			rMsg = "ERROR:"+e.getMessage()+";";
		}
		
		return rMsg;
	}
	
	//构造请求XML
	public String buildServiceXml(String mobile,String content,String flowNo){
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("Service");
		Element sys_Header = root.addElement("Sys_Header");
		sys_Header.addElement("RTS").addText("20090818120216999");
		sys_Header.addElement("SrvNo").addText("20090818120216999");
		sys_Header.addElement("SrcSys").addText("UITSM");
		sys_Header.addElement("DestSys").addText("CUNF");
		
		Element trunk = root.addElement("Trunk");
		Element msg = trunk.addElement("Msg");
		Element head = msg.addElement("Head");
		head.addElement("servicesid").addText("uitsm_msgservice001"); //服务模板标识	文本，选填
		head.addElement("flowno").addText(flowNo); //流水号	文本，必填
	    head.addElement("phone").addText(mobile); //手机号码    文本，可以多个手机号，每个手机号必须以”;” （英文字符）结束, 手机号码必须符合。13412345678;
		head.addElement("sendtime"); //发送时间	文本，选填 发送时间，发送时间为空时为即时短信
		
		Element body = msg.addElement("Body");
		body.addElement("content").addText(content);
		
		trunk.addElement("RetMsg");
		trunk.addElement("BizType").addText("1");
		
		Element status = root.addElement("Status");
		status.addElement("StdCode");
		status.addElement("StdDesc");
		
		return doc.asXML();
	}

	public String getSendXml() {
		return sendXml;
	}
	
	
}
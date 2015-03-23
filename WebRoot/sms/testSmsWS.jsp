<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ultrapower.wfinterface.clients.sendMsgClient.*"%>
<%@ page import="java.rmi.RemoteException"%>
<%@ page import="javax.xml.rpc.ServiceException"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String sendXml = (String)request.getParameter("sendXml");
SendMessageRequest sendMessageRequest = new SendMessageRequest();
sendMessageRequest.setServicexml(sendXml);
String rMsg = "";
String flowNo = UUID.randomUUID().toString().replaceAll("-", "");;
System.out.println(sendXml);
if(null!=sendXml && !"".equals(sendXml)){
	CUNFSendMessage cunfSendMessage = new CUNFSendMessageLocator();
	MessageServicePortType messageServicePortType;
	try {
		messageServicePortType = cunfSendMessage.getSOAPEventSource();
		rMsg = "MAINBODY:"+messageServicePortType.sendMessage(sendMessageRequest);
	} catch (ServiceException e) {
		e.printStackTrace();
		rMsg = e.getMessage();
	} catch (RemoteException e) {
		e.printStackTrace();
		rMsg = e.getMessage();
	} catch (Exception e){
		e.printStackTrace();
		rMsg = e.getMessage();
	}
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'testSmsWS.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>

	<body>
		<form method="post">
		<textarea rows="40" cols="130" name="sendXml">
<Service>
	<Sys_Header>
		<RTS>20090818120216999</RTS>
		<SrvNo>20090818120216999</SrvNo>
		<SrcSys>UITSM</SrcSys>`
		<DestSys>CUNF</DestSys>
	</Sys_Header>
	<Trunk>
		<Msg>
			<Head>
				<servicesid>UITSM_MSGSERVICES001</servicesid> 
 				<flowno><%=flowNo%></flowno>
				<phone>13976325434;13823456754;13932345432;</phone>
				<sendtime>2009-09-05 19:00:00</sendtime>
			</Head>
			<Body>
				<content>最近有暴雨，请注意预防</content>
			</Body>
		</Msg>
		<RetMsg></RetMsg>
		<BizType>1</BizType>
	</Trunk>
	<Status>
		<StdCode></StdCode>
		<StdDesc></StdDesc>
	</Status>
</Service>
		</textarea>
		<input type="submit" value="发送"/>
		</form>
		<div id="returnMsg"><%=rMsg %></div>
	</body>
</html>

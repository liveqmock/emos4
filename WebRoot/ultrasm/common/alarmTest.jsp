<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.wfinterface.services.SheetImplService"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		
		<%@ include file="/common/core/taglibs.jsp"%>
		<title>告警建单测试</title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		window.onload = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		
	</script>
	</head>

  <body>
   <% 
  	 SheetImplService sheetImplService = new SheetImplService();
	 String s = sheetImplService.createForm("INC_980890988", "故障告警", "事件工单告警建单",
			"应用系统分类1", "告警一级分类", "告警二级分类", "告警三级分类", "泰岳大厦", "1428999999",
			"1438999999", "服务管理事业部"); 
	%>
	结果：<%=s%>
  </body>
</html>

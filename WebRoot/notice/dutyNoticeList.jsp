<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="/common/plugin/swfupload/import.jsp"%>
<%@ page import="com.ultrapower.eoms.common.portal.model.UserSession"%>
<%@ page import="com.ultrapower.eoms.common.core.component.data.DataRow"%>
<%@ page import="com.ultrapower.eoms.common.core.component.data.DataTable"%>
<%@ page import="com.ultrapower.eoms.common.core.component.data.QueryAdapter"%>
<%@ page import="com.ultrapower.eoms.common.core.component.rquery.RQuery"%>

<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@ include file="/common/core/taglibs.jsp"%>
	<script type="text/javascript" src="${ctx}/common/javascript/kxbdMarquee.js" ></script>
	
	
	<style type="text/css">
	   * {margin:0;padding:0;}
	   body { 
	   		font-size:12px;
	   		background-color: #ddf5f5;
	   }
	   a {color:#209fa4;}
	   ul {list-style:none;}
	   #marquee2 {position:absolute;width:100%;height:75px;overflow:hidden; }
	   #marquee2 ul li {float:left; padding:0 10px; line-height:25px;}
	   #marquee2 li span { line-height: 70px;width: 100%;font-size: 40px;font-family: 华文琥珀;color:#209fa4;}
	</style>
	<script type="text/javascript">
	   $(function(){
	   		$('#marquee2').kxbdMarquee({isEqual:false});
	   });
	</script>
	<title>
		<eoms:lable name="wf_sort_wfsort" />
		
	</title>
</head>
<body>
	<%
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		String userId = userSession.getPid();
		
		String resultView = "";
		String sqlname = "SQL_NOITCEVIEWLOG_List.query";
		HashMap hashMap=new HashMap();
		
		
		Map map = new HashMap();
		map.put("userid", userId);
		map.put("noticeStatus", "1");
		map.put("noticetype", "2");
		request.setAttribute("valuemap",map);
		
		RQuery rQuery=new RQuery(sqlname,hashMap);
		DataTable datatable=rQuery.getDataTable();
		String noticetitle = "";
		if(datatable != null){
			int len = datatable.length();
			if(len>0){
				noticetitle = datatable.getDataRow(0).getString("noticetitle");
			}
	%>
		  <div id="marquee2">
			   <ul>
	<% 
		for(int i=0;i<len;i++){
			DataRow dataRow = datatable.getDataRow(i);
			String title = dataRow.getString("noticetitle");
	%>
			    <li><span><%=title %></span></li>
	<%
			}
		}
	%>
			   </ul>
			</div>
</body>
</html>
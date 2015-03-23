<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ultrapower.eoms.fulltext.common.cache.SystemContext" %>
<%@ page import="com.ultrapower.eoms.fulltext.model.IndexCategory" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>全文检索异常处理界面</title>
    <script type="text/javascript">
    	function delIndexFile(){
    		var cid = document.getElementById("delIndexFile").value;
    		if(""==cid||cid==null){
    			alert("请选择索引类别！");
    		}else{
    			window.open('backStatus.jsp?op=delIndexFile&cid='+cid);
    		}
    	}
    	function delMaintainRecord(){
    		var cid = document.getElementById("delMaintainRecord").value;
    		if(""==cid||cid==null){
    			alert("请选择索引类别！");
    		}else{
    			window.open('backStatus.jsp?op=delMaintainRecord&cid='+cid);
    		}
    	}
    	function getLastDataDate(){
    		var cid = document.getElementById("getLastDataDate").value;
    		if(""==cid||cid==null){
    			alert("请选择索引类别！");
    		}else{
    			window.open('backStatus.jsp?op=getLastDataDate&cid='+cid);
    		}
    	}
    </script>
  </head>
  
  <body>
   <a href="javascript:;" onclick="window.open('backStatus.jsp?op=statusReset');">索引维护完成状态重置！</a><br/>
   <hr/>
   <a href="javascript:;" onclick="window.open('backStatus.jsp?op=destoryWriter');">销毁写索引器！</a><br/>
   <hr/>
   <a href="javascript:;" onclick="window.open('backStatus.jsp?op=destoryReader');">销毁读索引器！</a><br/>
   <hr/>
   <span>删除索引文件：选择索引类别</span>
   <select id="delIndexFile"><option value="@ALLCID@">全部删除</option>
   	<%
   		List<String> keys = SystemContext.getAllPhysicCategoryKey();
   		if(keys!=null){
   			int len = keys.size();
   			IndexCategory ca;
   			for(int i=0;i<len;i++){
   				ca = SystemContext.getPhysicalCategory(keys.get(i));
   				if(ca!=null){
   					out.print("<option value=\"");
   					out.print(ca.getPid());
   					out.print("\">");
   					out.print(ca.getDisplayName());
   					out.print("</option>");
   				}
   			}
   		}
   	%>
   </select>
   <input type="button" value="确认删除" onclick="delIndexFile();"><br/>
   <hr/>
   <a href="javascript:;" onclick="window.open('backStatus.jsp?op=reInitSystem');">重新初始化系统！</a><br/>
   <hr/>
   <span>删除索引维护记录：选择索引类别</span>
   <select id="delMaintainRecord"><option value="@ALLCID@">全部删除</option>
   	<%
   		if(keys!=null){
   			int len = keys.size();
   			IndexCategory ca;
   			for(int i=0;i<len;i++){
   				ca = SystemContext.getPhysicalCategory(keys.get(i));
   				if(ca!=null){
   					out.print("<option value=\"");
   					out.print(ca.getPid());
   					out.print("\">");
   					out.print(ca.getDisplayName());
   					out.print("</option>");
   				}
   			}
   		}
   	%>
   </select>
   <input type="button" value="确认删除" onclick="delMaintainRecord();"><br/>
   <hr/>
   <span>查看最后一次索引数据日期：选择索引类别</span>
   <select id="getLastDataDate">
   	<%
   		if(keys!=null){
   			int len = keys.size();
   			IndexCategory ca;
   			for(int i=0;i<len;i++){
   				ca = SystemContext.getPhysicalCategory(keys.get(i));
   				if(ca!=null){
   					out.print("<option value=\"");
   					out.print(ca.getPid());
   					out.print("\">");
   					out.print(ca.getDisplayName());
   					out.print("</option>");
   				}
   			}
   		}
   	%>
   </select>
   <input type="button" value="查看" onclick="getLastDataDate();"><br/>
   <hr/>
   <a href="javascript:;" onclick="window.open('backStatus.jsp?op=forceStopSchedule');">强制停止索引维护线程！</a><br/>
   <hr/>
   <a href="javascript:;" onclick="window.open('backStatus.jsp?op=forceStopIndexCycle');">强制退出索引循环！</a><br/>
   <hr/>
  </body>
</html>

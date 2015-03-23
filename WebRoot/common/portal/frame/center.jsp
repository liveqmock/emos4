<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <%@ include file="/common/core/taglibs.jsp"%>
    <title><eoms:lable name="com_t_title" /></title>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	
	<style>
	.center{
		margin:-45px 0 0 -3px;
		width:10px;height:90;
		position:absolute;
		top:50%;left:50%;
		}
	</style>
	<script>
	 var displayBar=true;
	 function switchBar(obj) {
	  if (displayBar){
	    parent.mainFrm.cols="0,0,5,*,4";
	    displayBar=false;
	    obj.src="../../style/blue/images/index/open.png";
	    obj.title="打开左边管理菜单";
	   }
	else{
	    parent.mainFrm.cols="4,230,5,*,4";
	    displayBar=true;
	    obj.src="../../style/blue/images/index/close.png";
	    obj.title="关闭左边管理菜单";
	   }
	 }
	 </script>	
  </head>
  
  <body>
	<div class="center">
		<!-- <img  src="../../style/blue/images/index/close.png" style="cursor:hand;" title="关闭左边管理菜单" onClick="switchBar(this)"/> -->
	</div>
  </body>
</html>

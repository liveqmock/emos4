<%@ page language="java" pageEncoding="UTF-8"
	import="java.util.*,com.ultrapower.eoms.fulltext.common.cache.SystemContext
			,com.ultrapower.eoms.fulltext.common.constant.Constant" 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
		<base target="_blank"/>
		<%@ include file="/common/core/taglibs.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="../../style/blue/css/portal.css" />
		<script src="../../javascript/main.js"></script>
		<title>案例库首页</title>
	</head>
	<body class="portal_overflow">
	<div>
	<table cellpadding="0" cellspacing="5" class="layout_table" style="width:100%" >
	  <tr>
	    <td class="left">  
			<div class="left_no1">
			     <iframe  name="knowledge_userinfo" src="knowledge_userinfo.jsp" width="100%" height="230"  scrolling="no" frameborder="0" >
			     </iframe>
			</div>
        </td>
	    <td class="left" colspan="2">  
			<div class="left_no2">
			     <iframe  name="foot_platform" src="../../../ultratask/footPlatForm.action" width="100%" height="230" scrolling="no" frameborder="0" >
			     </iframe>
			</div>
		</td>
       </tr>
	   <!--centercol--> 
	  <tr>
	    <td class="left">  
            <div class="center_no1">
                 <iframe  name="abortlight" src="abortlight.jsp" width="100%" height="230" scrolling="no" frameborder="0" >
               </iframe>
            </div> 
        </td>
	    <td class="left">  
            <div class="center_no2">
                <iframe  name="abortlight" src="abortlight.jsp" width="100%" height="230" scrolling="no" frameborder="0" >
                </iframe>
            </div>     
        </td>
	    <td class="left">  
            <div class="center_no3">
                <iframe  name="abortlight" src="abortlight.jsp" width="100%" height="230" scrolling="no" frameborder="0" >
                </iframe>
            </div> 
	    </td>
       </tr>
	    <!--rightcol-->
       <tr>
	    <td class="right" colspan="3">
            <div class="right_no1">
                <iframe  name="announcement" src="announcement.jsp" width="100%" height="230" scrolling="no" frameborder="0" >
                </iframe>
            </div>    
	    </td>
	  </tr>
	</table>
	</div>
	</body>
</html>

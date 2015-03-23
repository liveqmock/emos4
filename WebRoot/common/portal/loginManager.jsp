<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String code = request.getParameter("code");
	code = code==null ? "" : code;
%>
<html>
  <head>
  	<%@ include file="/common/core/taglibs.jsp"%> 
    <title><eoms:lable name="com_t_title"/></title>
	<link href="${ctx}/common/style/blue/css/login.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		 if (window != top)
		 {
		 	 //alert(1);
		 	 top.location.href = location.href;
		 	  //window.history.go(-1);
		 }
		function err(){
		  document.getElementById("error").style.visibility= 'visible';
		  //document.getElementById("loginbox").style.paddingTop= '80px';
		  return;
		}
		function loginChk(){
			var reg = /^\w{1,20}$/;
			if(reg.test(document.getElementById("loginName").value))
			{
				document.getElementById("error").style.visibility= 'hidden';
				return true;
			}
			else
			{
				err();
				document.getElementById("loginName").focus();
				return false;
			}
		}
	</script>
  </head>
 
  <body onload="document.login_form.loginName.focus();">
	<div class="center">
	 <div class="top"></div>
	 <form name="login_form" action="${ctx}/portal/loginManager.action?code=<%=code%>" method="post" id="Form1" onsubmit="return loginChk()">
		 <div class="middle">
		    <ul id="loginbox">
		       <c:choose>
		       	<c:when test="${msg!=null}">
		        	<li id="error" style="top:35%;position:absolute;"><div class="error" ><span>${msg}</span></div></li>
		       	</c:when>
		       	<c:otherwise>
		       		<li id="error" style="visibility:hidden;top:35%;position:absolute"><div class="error"><span><eoms:lable name='sm_msg_loginMsg'/>！</span></div></li>
		       	</c:otherwise>
		       </c:choose>
		       <li><span class="lable"><eoms:lable name="sm_lb_userName"/>：</span>
		           <input name="loginName" id="loginName" value="${loginName}" type="text" tabindex="1" onblur="this.className='blur'" onfocus="this.className='focus'" class="blur" style="width:150px"/>
		       </li>
		       <li><span class="lable"><eoms:lable name="sm_lb_userPwd"/>：</span>
		           <input name="pwd" type="password" tabindex="2" onblur="this.className='blur'" onfocus="this.className='focus'" class="blur" style="width:150px"/>
		       </li>
		   	   <div class="button_position">
	       	     <input type="submit" class="button"  onmouseover="this.className='buttonhover'" onmouseout="this.className='button'" value="<eoms:lable name="sm_btn_login"/>"/>
		         <input type="button" class="button"  onmouseover="this.className='buttonhover'" onmouseout="this.className='button'" value="<eoms:lable name="sm_btn_findPwd"/>" /> 
		       </div>
		   </ul>
		 </div>
	  </form>
	 <div class="copyright"><eoms:lable name="com_lb_copyRight"/></div>
	</div>
  </body>
</html>

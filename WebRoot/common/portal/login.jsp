<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		function findpwd()
		{
			window.open("${ctx}/portal/findpwd.action", "_blank", "height=150,width=350,resizable=no,status=no,titlebar=no,toolbar=no");
		}	
	</script>
  </head>
 
  <body onload="document.login_form.loginName.focus();">
	<div class="center">
	 <div class="top"></div>
	 <form name="login_form" action="${ctx}/portal/login.action" method="post" id="Form1" onsubmit="return loginChk()">
		 <div class="middle">
		    <ul id="loginbox">
		       <c:choose>
		       	<c:when test="${msg!=null}">
		        	<li id="error" class="errorli"><div class="error" ><span>${msg}</span></div></li>
		       	</c:when>
		       	<c:otherwise>
		       		<li id="error" class="errorli"  style="visibility:hidden;"><div class="error"><span><eoms:lable name='sm_msg_loginMsg'/>！</span></div></li>
		       	</c:otherwise>
		       </c:choose>
		       <li><span class="lable"><eoms:lable name="sm_lb_userName"/>：</span>
		           <input name="loginName" id="loginName" value="${loginName}" type="text" tabindex="1" onblur="this.className='blur'" onfocus="this.className='focus'" class="blur" style="width:150px"/>
		       </li>
		       <li><span class="lable"><eoms:lable name="sm_lb_userPwd"/>：</span>
		           <input name="pwd" type="password" tabindex="2" onblur="this.className='blur'" onfocus="this.className='focus'" class="blur" style="width:150px"/>
		       </li>
               <li class="login_memory">
								<input type="checkbox"><label>记住账号和密码</label></li>
		   	   <li class="button_position">
	       	     <input type="submit" class="button"  onmouseover="this.className='buttonhover'" onmouseout="this.className='button'" value="<eoms:lable name="sm_btn_login"/>"/>
		         <!--<input type="button" class="button" onclick="findpwd();" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'" value="<eoms:lable name="sm_btn_findPwd"/>" /> -->
		       </li>
		   </ul>
		 </div>
	  </form>
	 <div class="copyright"><!--<eoms:lable name="com_lb_copyRight"/>-->© 2014 国家开发银行版权所有</div>
	</div>
  </body>
</html>

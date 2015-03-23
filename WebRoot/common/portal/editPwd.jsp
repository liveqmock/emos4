<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/header_form.jsp"%>
		<title><eoms:lable name="com_t_title" /></title>
		<!-- <link href="${ctx}/common/style/blue/css/login.css" rel="stylesheet" type="text/css" /> -->
		<script type="text/javascript">
		window.onresize = function() {
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		window.onload = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
			if('${returnMessage}'!='')
			{
				alert('${returnMessage}');
				window.close();
			}
			if('${isLogin}' == 'false')
				document.login_form.oldPwd.focus();
			else
				document.login_form.newPwd.focus();
		}
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
		function editPwd()
		{
			var new_pwd = document.getElementById('newPwd').value;
			var new_pwd_repeat = document.getElementById('reNewPwd').value;
			if(new_pwd == new_pwd_repeat)
			{
				document.forms[0].action = "${ctx}/portal/editPwd.action";
				document.forms[0].submit();
			}
			else
			{
				alert("<eoms:lable name='sm_lb_repeatPwdErr' />！");
			}
		}
		function backLogin()
		{
			document.getElementById('loginName').disabled = 'disabled';
			document.forms[0].action = "${ctx}/portal/login.action";
			document.forms[0].submit();
		}
	</script>
	</head>
	<body>
	<form name="login_form" action="${ctx}/portal/editPwd.action" method="post" id="Form1">
		<div class="content">
			<div class="add_scroll_div_x" id="center" style="text-align: center; margin: 0 auto;">
				<div class="blank_tr"></div>
					<c:if test="${isLogin!='false'}">
					<br/><br/><br/><br/>
					</c:if>
					<table style="width:300px">
						<tr><td><b>萨班斯密码管理（规则描述）</b></td></tr>
						<tr>
							<td align="left">
								<c:if test="${isLogin!='false'}">
								<br/>
								</c:if>
								<c:forEach var="pwdRule" items="${pwdRuleList}" varStatus="status">
									${status.count}、${pwdRule}；<br/>
								</c:forEach>
							</td>
						</tr>
					</table>
					<c:if test="${isLogin!='false'}">
					<br/><br/>
					</c:if>
					<c:choose>
						<c:when test="${msg!=null}">
							<font color=red>${msg}</font>
						</c:when>
						<c:otherwise>
							<br/>
						</c:otherwise>
					</c:choose>
					<table style="width:400px">
						<tr>
							<td align="right">用户登陆名：</td>
							<td align="left">${loginName}
							<input type="hidden" id="loginName" name="loginName" value="${loginName}"/>
							<input type="hidden" id="isLogin" name="isLogin" value="${isLogin}"/>
							</td>
						</tr>
						<c:if test="${isLogin=='false'}">
						<tr>
							<td align="right">原密码：</td>
							<td align="left"><input type="password" id="oldPwd" name="oldPwd" style="width:150px"/></td>
						</tr>
						</c:if>
						<tr>
							<td align="right">新密码：</td>
							<td align="left"><input type="password" id="newPwd" name="newPwd" style="width:150px"/></td>
						</tr>
						<tr>
							<td align="right">重复新密码：</td>
							<td align="left"><input type="password" id="reNewPwd" name="reNewPwd" style="width:150px"/></td>
						</tr>
						<c:if test="${isLogin!='false'}">
						<tr>
							<td></td>
							<td>
								<input type="button" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" value="确认" onclick="editPwd();" />
								<input type="button" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"value="返回" onclick="backLogin();" />
							</td>
						</tr>
						</c:if>
					</table>
				<div class="blank_tr"></div>
			</div>
			<div class="add_bottom">
				<c:if test="${isLogin=='false'}">
					<input type="button" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" value="确认" onclick="editPwd();" />
					<input type="button" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"value="关闭" onclick="window.close();" />
				</c:if>
			</div>
		</div>
	</form>
	</body>
</html>

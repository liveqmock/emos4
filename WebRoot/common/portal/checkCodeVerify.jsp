<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/common/core/taglibs_new_simple.jsp"%>
	<link href="${ctx}/common/style/blue/css/verification_code.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript">
		window.onload = function() {
			if('${msg}' != '')
				alert('${msg}');
			document.checkForm.checkcode.focus();
		}
		function check() {
			if(document.getElementById('checkcode').value == '')
			{
				alert('请输入手机验证码！');
				return 0;
			}
			document.checkForm.submit();
		}
		function getCode() {
			document.getElementById('checkcode').value = '';
			document.checkForm.submit();
		}
	</script>
</head>
<body>
<form id="checkForm" name="checkForm" action="${ctx}/portal/login.action" method="post">
	<input type="hidden" id="loginName" name="loginName" value="${loginName}"/>
	<input type="hidden" id="isVerifyPwd" name="isVerifyPwd" value="${isVerifyPwd}"/>
	<div class="center_position">
		<div class="center">
			<div class="body_bg">
				<div class="content_verification_code">
					<div>
						<span style="color:white"><b>验证码</b>:</span>
						<span>
							<input type="text" class="blur" id="checkcode" name="checkcode" value=""/>
						</span>
					</div>
					<div class="button_position">
						<span>
							<input type="button" class="button_login" value="登录" onclick="check();"/>
						</span>
						<span>
							<input type="button" class="get_verification_code" value="获取验证码" onclick="getCode();"/>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>403 - 缺少权限</title>
	</head>

	<body>
		<div>
			<div>
				<h1>
					你没有访问该页面的权限
				</h1>
			</div>
			<div>
				<a href="<c:url value="/"/>">返回首页</a>
				<a href="<c:url value="/j_spring_security_logout"/>">退出登录</a>
			</div>
			<div style="display:none">
				Never give up, Never lose hope. Always have faith, It allows you to
				cope. Trying times will pass, As they always do. Just have patience,

				Your dreams will come true. So put on a smile, You'll live through
				your pain. Know it will pass, And strength you will gain
			</div>
		</div>
	</body>
</html>
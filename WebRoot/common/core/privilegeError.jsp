<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/core/taglibs.jsp"%>
<link href="${ctx}/common/style/${skintype}/css/error.css" rel="stylesheet" type="text/css" />
<title>权限错误</title>
</head>
<body>
	<div class="container" >
	      <div class="errorContent">
	            <h1 class="red">错误！</h1>
	            <h1 class="gray">报歉，您没有权限访问该页面！</h1>
	            <p>请您联系管理员进行相关问题的解决。</p>
	       </div>
	      
	</div>
</body>
</html>

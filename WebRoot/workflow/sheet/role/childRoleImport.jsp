<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
</head>
<body>
	<form action="${ctx}/childroleextend/childRoleExtendAction/upload.action" method="post" enctype="multipart/form-data" target="_self">
		角色细分导入：
		<input id="excel" name="excel" type="file" />
		<input id="submit" name="submit" type="submit" value="Submit!" />
	</form>
</body>
</html>
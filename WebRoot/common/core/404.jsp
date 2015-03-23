<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/core/taglibs.jsp"%>
<link href="${ctx}/common/style/${skintype}/css/error.css" rel="stylesheet" type="text/css" />
<title>404-页面不存在</title>
</head>
<body>
<div class="container" >
  <div class="errorContent1">
        <h1 class="red">404错误</h1>
            <h1 class="gray">报歉，无法找到您需要的页面！</h1>
            <p>因为系统页面不存在，您所需要的页面，没有找到！您可以返回上一页，或者返回首页，重新浏览页面！</p>
  </div>
</div>
</body>
</html>

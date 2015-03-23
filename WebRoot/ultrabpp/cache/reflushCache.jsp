<%@ page language="java" import="java.util.*,com.ultrapower.eoms.ultrabpp.cache.service.MetaCacheService,com.ultrapower.workflow.utils.ApplicationContextUtils" pageEncoding="utf-8"%>
<%@page import="com.ultrapower.eoms.ultrabpp.cache.service.ExtendFunctionCacheService"%>
<%@page import="com.ultrapower.eoms.ultrabpp.cache.service.ThemeCacheService"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

    //表单缓存
    MetaCacheService metaCacheService = (MetaCacheService) ApplicationContextUtils.getBean("metaCacheService");
    /**
     * 加载/workflows/fields_config.xml 文件 
     */
    metaCacheService.reflushFieldInfo();
    /**
     * 加载"/workflows/freeaction_config.xml"文件
     */
    metaCacheService.reflushFreeAction();
    /**
     * 加载所有表单配置文件进入缓存。
     */
    metaCacheService.reflushCache(null);
    
    ExtendFunctionCacheService extendFuncCacheService = (ExtendFunctionCacheService)ApplicationContextUtils.getBean("extendFuncCacheService");
	extendFuncCacheService.reflush(null);
	

	ThemeCacheService themeCacheService = (ThemeCacheService) ApplicationContextUtils.getBean("themeCacheService");
	themeCacheService.initThemeConfig();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>My JSP 'reflushCache.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>
	<body>
		缓存加载完成
	</body>
</html>

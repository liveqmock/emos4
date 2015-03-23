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
		<script language="javascript">
			window.onresize = function() {
			 setCenter(0,60);
			}
			window.onload = function() {
			 setCenter(0,60);
			}
			function planeSearch()
			{
				var keyWords = document.getElementById("txtKeyword").value;
				var categoryId = document.getElementById("indexType").value;
				if(keyWords=="" || keyWords=="请输入查询条件" || categoryId=="")
				{
					return;
				}
				document.getElementById("form_categoryId").value = categoryId;
				document.getElementById("form_keyWords").value = keyWords;
				document.searchForm.submit();
			}
			function checkSearch1()
			{
				if(document.getElementById("txtKeyword").value=="请输入查询条件")
				{
					document.getElementById("txtKeyword").value = "";
				}
			}
			function checkSearch2()
			{
				if(document.getElementById("txtKeyword").value=="")
				{
					document.getElementById("txtKeyword").value = "请输入查询条件";
				}
			}
		</script>
		<title>footer</title>
	</head>
	<body>
		<form name="searchForm" action="${ctx}/fulltext/search/resultList.jsp"
			method="post" style="display: none">
			<input type="hidden" name="categoryId" id="form_categoryId" value="" />
			<input type="hidden" name="keyWords" id="form_keyWords" value="" />
		</form>
		<div class="right_search">
			<li>
				<input id="txtKeyword" type="text" value="请输入查询条件"
					onfocus="checkSearch1()" onblur="checkSearch2()" maxlength="100"
					style="width: 180px;" />
			</li>
			<li>
				<select id="indexType" style="margin-top: 0px; * margin-top: 1px;">
					<%
						out.println("<option value=\"\">全部</option>");
						StringBuffer allkey = new StringBuffer();
						List<String> logkey = SystemContext.getAllLogicalCategoryKey();
						if (logkey != null && logkey.size() > 0) {
							for (int i = 0; i < logkey.size(); i++) {
								allkey.append("&comm;" + logkey.get(i));
								Map<String, Object> logMap = SystemContext
										.getLogicalCategory(logkey.get(i));
								out
										.println("<option value=\""
												+ logkey.get(i)
												+ "\">"
												+ logMap
														.get(Constant.INDEX_CATEGORY_LOGICAL_DISPLAYNAME)
												+ "</option>");
							}
						}
						List<String> phykey = SystemContext.getAllPhysicCategoryKey();
						if (phykey != null && phykey.size() > 0) {
							for (int i = 0; i < phykey.size(); i++) {
								allkey.append("&comm;" + phykey.get(i));
								out.println("<option value=\""
										+ phykey.get(i)
										+ "\">"
										+ SystemContext.getPhysicalCategory(phykey.get(i))
												.getDisplayName() + "</option>");
							}
						}
						if (allkey.length() > 0) {
							out.println("<script type=\"text/javascript\">");
							out
									.println(" var sel = document.getElementById(\"indexType\");");
							out.println(" sel.options[0].value=\""
									+ allkey.substring("&comm;".length()) + "\";");
							out.println("</script>");
						} else {
							out.println("<script type=\"text/javascript\">");
							out
									.println(" var sel = document.getElementById(\"indexType\");");
							out.println(" sel.options[0].value=\"\";");
							out.println(" sel.options[0].text=\"没有索引分类\";");
							out.println("</script>");
						}
					%>
				</select>
			</li>
			<li>
				<input name="search" type="button" value="搜索" class="searchButton"
					onmouseover="this.className='searchButton_hover'"
					onmouseout="this.className='searchButton'" onclick="planeSearch();"
					style="margin-top: 0px; * margin-top: 1px;" />
			</li>
			<li>
				<input name="searchB" type="button" value="高级搜索"
					class="searchadv_button"
					onmouseover="this.className='searchadv_button_hover'"
					onmouseout="this.className='searchadv_button'"
					onclick="window.open('${ctx}/fulltext/search/search.jsp');"
					style="margin-top: 0px; * margin-top: 1px;" />
			</li>
		</div>
	</body>
</html>
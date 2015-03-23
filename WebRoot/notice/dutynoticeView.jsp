<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<%@ include file="/common/core/taglibs.jsp"%>
		<link href="${ctx}/common/portal/index/style/css/main.css"
			rel="stylesheet" />
		<link href="${ctx}/common/portal/index/style/css/reset-1.0.css"
			rel="stylesheet" />
		<script language="javascript">
			//修改流程分类
			function view(noticeId){
				var src = '${ctx}/notice/viewNotice.action?noticeId='+noticeId;
				window.open(src,'','width=1000,height=450,top=100,left=200,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
			}
			function goPage(pageNum) {	
				document.getElementById("page").value=pageNum;
				list.submit();
			}
		</script>
	</head>
	<body>
	   <form name="list" id="list" action="${ctx}/notice/dutynoticeView.action" method="get">
	   <input type="hidden" name="page" id="page" value=""/>
		<div class="newannouncement" style="height:200px;">
			<div class="title">
				<div class="leftright">
				   <img  src="${ctx}/common/portal/index/style/images/leftarrow.png" onclick="goPage('${pageInfo.previousPage}');"  ></img>
					        ${pageInfo.page}/${pageInfo.totalPage}
					<img src="${ctx}/common/portal/index/style/images/rightarrow.png"  onclick="goPage('${pageInfo.nextPage}');"  ></img>
				</div>
				 <span class="gonggao">公告</span><a href="javascript:void(0)" onclick="view('${noticeList[0].pid}');">
				 	    <c:if test="${fn:length(noticeList[0].noticeTitle)>6}">${fn:substring(noticeList[0].noticeTitle,0,6)}...</c:if>
						<c:if test="${fn:length(noticeList[0].noticeTitle)<=6}">${fn:substring(noticeList[0].noticeTitle,0,6)}</c:if></a>
			</div>
			<div class="scroll_div">
				<table style="width:200px;">
					<c:forEach items="${noticeList}" var="nl">
						<tr>
							<td>
							    ${nl.noticeContent}
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		</from>
	</body>
</html>

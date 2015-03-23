<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
		<base target="_blank" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/common/portal/index/common/taglibs.jsp"%>
		<script src="${ctx}/common/javascript/dropmenu.js"
			type="text/javascript"></script>
		<script src="${ctx}/common/style/office/js/index.js"
			type="text/javascript"></script>
		<%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<script type="text/javascript"
			src="${ctx}/common/plugin/jquery/ui/jquery.timers-1.1.2.js"></script>
		<link href="${ctx}/common/portal/index/style/css/main.css"
			rel="stylesheet" />
		<link href="${ctx}/common/portal/index/style/css/reset-1.0.css"
			rel="stylesheet" />
		<script language="javascript">
			window.onresize = function() 
			{
				//setCenter(0,30);
			}
			window.onload = function() 
			{
				//setCenter(0,30);changeRow_color("tab");
			}
			//修改流程分类
			function view(noticeId){
				var src = '${ctx}/notice/viewNotice.action?noticeId='+noticeId;
				window.open(src,'','width=1000,height=450,top=100,left=200,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
			}
		</script>
		<style>.case_total1 .newaddmore{height:20px;background:none;}.newaddmore a{background:none;float:right;margin-right:5px;color:#209fa4;line-height:20px;}</style>
	</head>
	<body>
		<div class="gk_cnt">
			<h2 class="gk_title">公告</h2>
			  <ul class="gk_notice">
			<%int i=0;%>
			<dg:datagrid var="noitceList" title="${nodePath}" action=""
					sqlname="SQL_NOITCEVIEWLOG_List.query" cachemode="sql">
				<dg:gridrow>
				<%
					i++;
					if (i <= 5) {
				%>
				<li>
					<a style="cursor: pointer;" onclick="view('${noticeid}');" title='${noticetitle}'>
				        <c:if test="${fn:length(noticetitle)>12}">${fn:substring(noticetitle,0,12)}...</c:if>
						<c:if test="${fn:length(noticetitle)<=12}">${noticetitle}</c:if>
					</a>
					<span><eoms:date pattern="yyyy-MM-dd" value="${noticecreatetime}"/></span>
				</li>
				<%
				}
				%>
				</dg:gridrow>
			</dg:datagrid>
			<!-- <li class="newaddmore"><a href="${ctx}/notice/noticeViewLogFrame.action">更多>></a></li> -->
			</ul>
	     </div>     
	</body>
	<script language="javascript">
		//document.getElementById("center").style.width='170px';
		document.getElementById("center").style.height='127px';
		//$(".content").css("height","60%");
		//document.getElementById("center").id="";
	</script>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.eoms.msextend.constants.ExtendConstants"%>
<script type="text/javascript" src="../common/plugin/jquery/ui/ui.draggable.js"></script>
<%
	request.setAttribute("serverName",request.getServerName());
%>
<style>
.pull h2 {
	text-align: left;
	font-size: 14px;
	font-weight: normal;
	padding: 0 8px;
	background:#3C89CC;
	width: 135px;
	height: 24px;
	line-height: 24px;
	text-shadow: 2px 1px 0px #000;
	margin: 0px;
}

.pull {
	width: 203px;
}


strong {
	font-weight: bold;
	color: #fff;
}

.pull h2 img {
	float: right;
	color: #fff;
	font-size: 12px;
}

#kmdiv{
display: none;
}

</style>
<div class="pull">
		<h2>
			<img
				src="../ultrabpp/runtime/theme/classic/images/field_menu.gif"
				onclick="showKm()" /><strong>关联知识</strong>
		</h2>
		<div id="kmdiv">
			<iframe id="kmiframe" src="" frameborder="0" width="151px"
				height="290px"></iframe>
		</div>
	</div>

<script type="text/javascript">

function changeKm(){
	$("#kmsearchdiv").draggable();
	var url = "http://www.baidu.com";
	//知识库返回页面搜索值
	var baseSummary = $("#BaseSummary").val();
	var url = "http://${serverName}:39090/<%=ExtendConstants.KM_PROJECTNAME %>/kmAddExperie.do?method=searchkm&inputwidth=250&recordno=5&keyword="+encodeURIComponent(baseSummary);
	$("#kmiframe").attr("src",url);
}

function showKm(){
	$("#kmdiv").toggle();
}	
</script>


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:scroll;overflow-x:hidden;">
<head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<script>
	$(function(){
	//return;
		var viewbox = window.parent.$("#${recordViewFieldName}_ubfbox");
		var height = 150;
		var iframeHeight = document.body.scrollHeight;
		if(iframeHeight < height){
			height = iframeHeight;
		}
		viewbox.height(height)//调整父窗口处理记录的高度
		viewbox.children("table").height(height)
		window.parent.$("#${recordViewFieldName}").height(height)
	});
	function showDetail(){
		window.open("/change/ciRelevance/detailCI.jsp",'detail','height=800px,width=600px,toolbar=no,status=no,location=no,directories=no');
	}
	</script>
</head>
<body style="">
	<table class="tableborder" style="table-layout: fixed;">
		<tr class="t2">
			<th width="30">序号</th>
			<th width="40%">CI类别</th>
			<th width="40%">CI名称</th>
			<th width="20%">操作</th>
		</tr>
		<!--<c:forEach items="${wfRecords }" var="w" varStatus="s">
			<tr class="t1">
				<td>${s.index+1 }</td>
				<td>${w.currentUser }[${w.currentUserInfo.depname},${w.currentUserInfo.phone}]</td>
				<td>${w.dealAction }</td>
				<td><input type="button" value="查看" onclick="showDetail()"/></td>
			</tr>
		</c:forEach>-->
		<tr class="t1">
			<td>1</td>
			<td>物理服务器</td>
			<td>IT运维系统T0001机</td>
			<td><input class="button" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'"  type="button" id="Confirm" value="查&nbsp;&nbsp;看"/></td>
		</tr>
		<tr class="t1">
			<td>2</td>
			<td>物理服务器</td>
			<td>IT运维系统T0001机</td>
			<td><input class="button" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'"  type="button" id="Confirm" value="查&nbsp;&nbsp;看"/></td>
		</tr>
	</table>
</body>
</html>

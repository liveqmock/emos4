<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
</head>
<body>
<%
	request.setAttribute("timename", request.getParameter("timename"));
	request.setAttribute("serverName", request.getServerName());
%>
<form id="form" action="" method="post">
<input type="hidden" id="datefromto" name="datefromto"  />
<input type="hidden" name="timetype" value="customday" />
<table align="center">
	<tr>
		<td>建单开始日期</td>
		<td><input id="begin" class="Wdate" type="text" onClick="WdatePicker()"></td>
		<td width="150px"></td>
		<td>建单截止日期</td>
		<td><input id="end" class="Wdate" type="text" onClick="WdatePicker()"></td>
		<td width="100px"></td>
		<td><input class="button" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'"  type="button" id="Confirm" value="查&nbsp;&nbsp;询"/>
	</tr>
</table>
</form> 
<iframe id='reportSrc' name='reportSrc' width='100%' frameborder='0' align = 'middle'  height="625px" scrolling="auto"></iframe>
</body>
<script type="text/javascript">
$(function(){
	var s = new Date();
	var year = s.getYear();
	var month = s.getMonth()+1;
	if(month<10){
		month = "0" + month;}
	var day = s.getDate();
	if(day<10)
		day = "0"+ day;
	$("#begin").val(year+"-"+month+"-"+day);
	$("#end").val(year+"-"+month+"-"+day);	
	$("#reportSrc").attr("src","http://${serverName}:8090/report/bizman/common/result.jsp?timename=${timename}&StartTime="+$("#begin").val()+"&EndTime="+$("#end").val());
	$("#Confirm").click(function(){		
			var _begin = $("#begin").val();
			var _end = $("#end").val();
			if(_begin == ''){
				alert('请选择开始日期');
				return false;
			}
			if(_end == ''){
				alert('请选择截止日期');
				return false;
			}
			if(_begin > _end){
				alert('开始日期不能大于截止日期');
				return false;
			}
			//var _datefromto= _begin + "~" + _end;
			$("#reportSrc").attr("src","http://${serverName}:8090/report/bizman/common/result.jsp?timename=${timename}&StartTime="+_begin+"&EndTime="+_end);
	});
});
</script>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title>用户满意度调查</title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	</head>
	<body>
		<div style="margin: 20px auto; width: 600px;">
			<h2 style="margin-top: 10px;">用户满意度调查</h2>
			<div>
				<p>1.请您填写用户满意度调查</p>
				<input type="radio" id="satisfaction_1" name="satisfaction" value="非常满意" checked="checked" />
				<label for="satisfaction_1">非常满意</label>
				<br />
				<input type="radio" id="satisfaction_2" name="satisfaction" value="满意" />
				<label for="satisfaction_2">满意</label>
				<br />
				<input type="radio" id="satisfaction_3" name="satisfaction" value="基本满意" />
				<label for="satisfaction_3">基本满意</label>
				<br />
				<input type="radio" id="satisfaction_4" name="satisfaction" value="不满意" />
				<label for="satisfaction_4">不满意</label>
				<br />
				<input type="radio" id="satisfaction_5" name="satisfaction" value="非常不满意" />
				<label for="satisfaction_5">非常不满意</label>
				<br />
				<p>2.请您留下您的保贵意见？</p>
				<textarea name="suggest" id="suggest" rows="7" style="width: 400px;"></textarea>
			</div>
			<div style="margin-top:20px;"><input type="button" value="提交调查" onclick="checkSubmit();"/></div>
		</div>
	</body>
<script type="text/javascript"><!--
/*
$(document).ready(function() {
	alert(111111111);
	var reg = new RegExp("(^|&)"+ "baseid" +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	var reg1 = new RegExp("(^|&)"+ "baseschema" +"=([^&]*)(&|$)");
	var r1 = window.location.search.substr(1).match(reg1);
	$.ajax({
		type: "get",
		url : "${ctx}/satisfaction/checkTimeOut.action?baseid="+unescape(r[2])+"&baseschema="+unescape(r1[2]),
		success : function(mes) {
			alert(mes);
		}
	});
}); 
*/

function checkSubmit(){
	var baseid=getQuery("baseid");
	var baseschema=getQuery("baseschema");
	$.ajax({
		type: "get",
		url : "${ctx}/satisfaction/checkTimeOut.action?baseid="+baseid+"&baseschema="+baseschema,
		success : function(mes) {
			if(mes==''){
				submit();
			}else{
			if(mes=='timeout'){
				alert("满意度调查已经超过7天，不能对此工单进行评价！");
			}else{
				alert("不是事件单或者服务请求单！");
			}
			}
		}
	});
}

function getQuery(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)
		return unescape(r[2]);
	return null;
}

function submit() {
	var satis;
	var chkObjs = document.getElementsByName("satisfaction");
	for(var i=0;i<chkObjs.length;i++){
		if(chkObjs[i].checked){ 
			satis = chkObjs[i].value;
			break;
		}
	}
	
	$.post("${ctx}/satisfaction/doSatisSurvey.action",
	{
		
		baseid:getQuery("baseid"),
		baseschema:getQuery("baseschema"),
		loginname:getQuery("loginname"),
		satisfaction:satis,
		suggest:document.getElementsByName("suggest")[0].innerHTML
	},
	function(data) {
		if(data!=null && data!='null' && data!="") {
			alert("保存成功!");
			window.close();
		} else{
			alert("保存失败!");
			window.close();
		}
	});
}
</script>
</html>

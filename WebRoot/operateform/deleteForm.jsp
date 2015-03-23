<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="/common/plugin/swfupload/import.jsp"%>
<head>
   
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
	 <title>
		     <eoms:lable name="wf_sort_wfsort" />
	</title>
	
    <script type="text/javascript">
    
    var message = '${message}';
	if(message != null && message != ''){
		if(message == 'true'){
			alert("删除成功！");
		}else{
			alert("删除失败！");
		}
	}
	
	window.onresize = function()
	{
		setCenter(0,61);
	}
	window.onload = function(){
	    setCenter(0,61);
	}
    
	function saveForm(){
		if(!confirm("是否确定要删除该工单？")){
			return false;
		}
		var baseID = document.getElementById("baseID");
		if("" == baseID.value){
			alert("baseID不能为空！");
			baseID.focus();
			return;
		}
		var baseSchema = document.getElementById("baseSchema");
		if("" == baseSchema.value){
			alert("baseSchema不能为空！");
			baseSchema.focus();
			return;
		}
        
		addForm.submit();
	}
	
	function del(){
		if(!confirm("是否确定要删除该工单？")){
			return false;
		}
		var baseID = document.getElementById("baseID");
		if("" == baseID.value){
			alert("baseID不能为空！");
			baseID.focus();
			return;
		}
		var baseSchema = document.getElementById("baseSchema");
		if("" == baseSchema.value){
			alert("baseSchema不能为空！");
			baseSchema.focus();
			return;
		}
        
        $.ajax({
		  type: "GET",
		  url: "msextend/doDeleteForm.action",
		  data: "baseID="+baseID.value+"&baseSchema="+baseSchema.value,
		  success: function(msg){
		  	if(msg == 'true'){
		    	alert("删除成功！");
		    }else{
		    	alert("删除失败！");
		    }
		  }
		});
	}
    </script>
</head>
<body>
<div class="content" id="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							删除工单
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div class="scroll_div" id="center">
			<br />
		    <s:form action="doDeleteForm.action" theme="simple" name="addForm" id="addForm" method="post">
				<table class="add_user">
					<tr>
						<td class="texttd">
							baseID：<font color="red">*</font>
						</td>
						<td>
							<s:textfield name="baseID" id="baseID" cssStyle="width:300px;" maxlength="100" />
						</td>
					</tr>
					<tr>
						<td class="texttd">
							baseSchema：<font color="red">*</font>
						</td>
						<td>
							<s:textfield name="baseSchema" id="baseSchema" cssStyle="width:300px;" maxlength="100" />
						</td>
					</tr>
				</table>
			</s:form>
  		</div>
		<div class="add_bottom">
			<input type="button" value="确定" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="del();" />
		</div>
	</div>
</body>
</html>
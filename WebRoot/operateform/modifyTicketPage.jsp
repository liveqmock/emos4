<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/dictTree/jsp/dict.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<link href="${ctx}/common/plugin/autocomplete/jquery.autocomplete.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="${ctx}/common/plugin/autocomplete/jquery.autocomplete.js" ></script>
		<script language="javascript">
			$(function() {
				$("#assignee").autocomplete($ctx+"/userManager/autoSearchExOutHtml.action", {
					extraParams: 
			        {   
			             sqlQuery: "SQL_TEST_SearchUser.query"
			        },
					cellSeparator: "|",
					lineSeparator: "\n",
					minChars: 1,
					width: 350,
					maxItemsToShow: 10,
					formatItem: function(row) {
						return row[0] + "[" + row[1] + "]" + row[2];
					}
	
				}).result(function(event, row, formatted) {
					document.getElementsByName('assigneeLoginName')[0].value = row[1];
				});	
			}); 
			
			
			function modifyTicket(){
				if($('#assignee').val()==""){
					alert("处理人不能为空");
					return false;
				}
				var assigneeLoginName = $('#assigneeLoginName').val();
		        $.ajax({
				  type: "GET",
				  url: "operateform/modifyTicket.action?r="+Math.random(),
				  data: "baseID=${baseID}&baseSchema=${baseSchema}&assigneeLoginName="+assigneeLoginName,
				  success: function(msg){
				  	if(msg == 'true'){
				    	alert("修改成功！");
				    	self.opener=null;
				    	self.close();
				    }else{
				    	alert("修改失败！");
				    }
				  }
				});
			}
		</script>
</head>
<body>
	<div class="content">
		<div class="page_div_bg">
			<div class="page_div">
				<eoms:operate cssclass="page_saves_button" id="page_saves_button" onmouseover="this.className='page_saves_button_hover'" onmouseout="this.className='page_saves_button'" onclick="modifyTicket()" text="com_btn_save" operate="com_save"/>
  	 		</div>
		</div>
		<div class="add_scroll_div_x" id="center">
			<fieldset class="fieldset_style">
				<legend>工单信息修改</legend>
				<div class="blank_tr"></div>
				<table class="add_user">
					<tr>
						<td class="texttd" style="width:15%">处理人：<span class="must">*</span></td>
						<td style="width:35%">
							<input type="text" id="assignee" name='assignee' maxlength="254" style="width: 96%;" />
							<input type="hidden" id="assigneeLoginName"/>
						</td>
						<td style="width:15%"></td>
						<td style="width:35%"></td>
					</tr>
				</table>
			</fieldset>
		</div>
	</div>
</body>
</html>

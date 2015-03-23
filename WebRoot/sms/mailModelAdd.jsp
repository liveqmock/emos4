<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<title>邮件模板新建</title>
		<link type="text/css" rel="Stylesheet" href="${ctx}/common/plugin/JTable/JTable.css" />
		<script type="text/javascript" src="${ctx}/common/plugin/JTable/JTable.js"></script>
		<script language="javascript">
		window.onresize = function() {
			setCenter(0,61);
		}
		window.onload = function() {
			setCenter(0,61);
		}
		
		function getSelectText() {
	        var sel = document.getElementById('selectValue').value;
	        document.getElementById('mailModel.modelType').value = sel;
	        if(""!=sel){
		        ajaxGetMailModel(sel);
	        }
		}
		
		function getMailModelByModelType(){
			if(""!=$("#mailModel.modelType").val()){
				ajaxGetMailModel($("#mailModel.modelType").val());
			}
		}
		
		function ajaxGetMailModel(sel){
			$.ajax({
			    url: "${ctx}/mailModel/loadMailModel.action?modelType="+sel,
			    type: 'POST',
			    dataType: 'json',
			    success: function(json){
						document.getElementById("mailModel.pid").value=json.pid;
						document.getElementById("mailModel.content").value=json.content;
			    }
			 });
		}
		
		function formSubmit()
		{
			if(Validator.Validate(document.forms[0],2))	document.forms[0].submit();
		}
	</script>
	</head>

	<body>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"><span class="title_icon2">邮件模板新建</span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend>邮件模板新建</legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
							<table class="add_user">
							 <form action="${ctx}/mailModel/mailModelSave.action" method="post" name="mailModelSave" >
								<tr>
									<td class="texttd" width="15%">
										PID:
									</td>
									<td>
										<input id="mailModel.pid" name="mailModel.pid" value="${mailModel.pid}" readonly="true" style="width:400px"/>
									</td>
								</tr>
								<tr>
									<td class="texttd" width="15%">
										模板类别：
									</td>
									<td>
										<select id="selectValue" onchange="getSelectText()">
											<c:forEach items="${requestScope.schemaList}" var="modeltype">
												<option value="${modeltype}" <c:if test="${modeltype==mailModel.modelType}">selected</c:if>>${modeltype}</option>
											</c:forEach>
										</select>
										实际模板类别：
										<input name="mailModel.modelType" id="mailModel.modelType" value="${mailModel.modelType}" onblur="getMailModelByModelType()"/>
										<validation id="mailModel.modelTypeV" dataType="Require" msg="请选择或填写模板类别！" />
									</td>
								</tr>
								<tr>
									<td class="texttd" width="15%">
										模板标题：
									</td>
									<td>
										<input name="mailModel.mailTitle" id="mailModel.mailTitle" value="${mailModel.mailTitle}" style="width:800px"/>
									</td>
								</tr>
								<tr>
									<td class="texttd" width="15%">
										模板内容：
									</td>
									<td>
										<textarea rows="25" cols="100" id="mailModel.content" name="mailModel.content" class="textInput">${mailModel.content}</textarea>
									</td>
								</tr>
								<tr>
									<td class="texttd">
										
									</td>
									<td>
										1.标题和正文可替换字段<br/>
										BASEID:#BASEID#<br/>
										BASESCHEMA:#BASESCHEMA#<br/>
										上一处理人:#CURRENTUSER#<br/>
										上一处理人OA号:#CURRENTUSERLOGINNAME#<br/>
										处理时间:#DEALTIME#<br/>
										处理说明:#DEALDESC#<br/>
										处理动作:#DEALACTION#<br/>
										当前处理人(多人):#NEXTDEALUSER#<br/>
										邮件内容(工单流转时插入):#EMAILCONTENT#<br/>
										邮件接收人OA号:#LOGINNAME#<br/>
										邮件接收人:#FULLNAME#<br/>
										BASENAME:#BASENAME#<br/>
										BASESN:#BASESN#<br/>
										主题:#BASESUMMARY#<br/>
										2.事件、服务请求流程的建单后和关闭后通知申请人，可替换工单主表单字段，替换方式为的#字段英文名#<br/>
										模板名固定为:<br/>
										创建环节提交邮件发送，CBD_INCIDENT_CREATE_RECORDCONTENT、	CDB_SERVICEREQUEST_CREATE_RECORDCONTENT<br/>
										关单环节满意度邮件发送，CBD_INCIDENT_CLOSE_RECORDCONTENT、CDB_SERVICEREQUEST_CLOSE_RECORDCONTENT<br/>
										3.事件、服务请求流程的正常流转通知，可替换工单主表单字段，替换方式为的#字段英文名#。如果即有名为SCHEMA的模板又有下面的模板，则下面的模板优先级高。<br/>
										模板名固定为:CBD_INCIDENT_RECORDCONTENT、CDB_SERVICEREQUEST_RECORDCONTENT<br/>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</fieldset>
				<div class="blank_tr"></div>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save"/>" 
				class="save_button" onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="formSubmit();" />
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	  
	</body>
</html>

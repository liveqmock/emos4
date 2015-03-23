<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/dictTree/jsp/dict.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/ultrabpp/runtime/theme/classic/js/main.js"></script>
		<script language="javascript">
			window.onresize = function()
			{
				setCenter(0,30);
			}
			window.onload = function()
			{
				setCenter(0,30);
				
				var result = '<%=request.getAttribute("result")%>';
				if(result == 'success'){
					window.opener.document.getElementById('form1').submit();
					window.close();
				}
			}
			
			
		function openpeopletree(returnid,isSelectType){
			window.open($ctx +"/serverQuest/peopleTree.jsp?isRadio=0&id='0'&first=true&isSelectType='"+isSelectType+"'&returnId="+returnid, '人员列表', 'height=400px, width=600px, top=100, left=300, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no');
		}
		
		//保存配置信息
		function configSave(){
			var vali =  Validator.Validate(document.getElementById('configForm'),1);
			if(vali){
				var ids = '${ids}';
				if(ids == null || ids == ''){
					var sys = document.getElementById('userMap.busSystem').value;
					$.post($ctx + "/business/checkReleaseDealUserConfig_AJAX.action",{busSystem:sys},function(data){
						if(data == 'true'){
							if(confirm('本业务系统人员配置已存在，是否覆盖')){
								$('#configForm').submit();
							}else{
								$('#configForm').submit();
							}
						}else if(data == 'false'){
							$('#configForm').submit();
						}
					});
				}else{
					$('#configForm').submit();
				}
			}
		}
		
		function openDepCheckBoxTree(form_name,input_name,input_id,isRadio)
			{
				if(form_name!='undefined' && input_name!='undefined')
				{
					if(isRadio=="1"){
						showModalDialog('${ctx}/common/tools/depCheckBoxTree.jsp?form_name='+form_name+"&input_name="+input_name+"&input_id="+input_id,window,'help:no;center:true;scroll:no;status:no;dialogWidth:380px;dialogHeight:480px');
					}else if(isRadio=="0"){
						showModalDialog('${ctx}/common/tools/depOpenRadioTree.jsp?form_name='+form_name+"&input_name="+input_name+"&input_id="+input_id,window,'help:no;center:true;scroll:no;status:no;dialogWidth:380px;dialogHeight:480px');
					}
				}
			}
		</script>
</head>
<body>
<form action="${ctx}/business/saveReleaseDealUserConfig.action"  id="configForm" method="post">
	<input type="hidden" name="ids" value="${ids}" />
	<div class="content">
		<div class="page_div_bg">
			<div class="page_div">
				<eoms:operate cssclass="page_saves_button" id="page_saves_button" onmouseover="this.className='page_saves_button_hover'" onmouseout="this.className='page_saves_button'" onclick="configSave()" text="com_btn_save" operate="com_save"/>
  	 		</div>
		</div>
		<div class="add_scroll_div_x" id="center">
			<fieldset class="fieldset_style">
				<legend>发布处理人配置</legend>
				<div class="blank_tr"></div>
				<table class="add_user">
					<tr>
						<td class="texttd" style="width:15%">业务系统：<span class="must">*</span></td>
						<td style="width:35%">
							<input type="hidden" id="busSystemCode" name='userMap.BUSSYSTEMCODE' value="${userMap.BUSSYSTEMCODE}" />
							<eoms:dictTree name="userMap.busSystem" value="${userMap.BUSSYSTEM}" style="width:97%;"  sqlStr="select id,name as value,name as text,name as fulltext,pid,tlevel from bs_t_sm_commontree where type='busSystem'"></eoms:dictTree>
							<validation id="userMap.busSystemV" dataType="Custom" regexp="^.{1,1000}$" msg="业务系统不能为空" />
						</td>
						<td style="width:15%"></td>
						<td style="width:35%"></td>
					</tr>
					<tr>
						<td class="texttd">发布承办人：</td>
						<td>
							<input type="hidden" id="EDITUSERID" name='userMap.EDITUSERID' value="${userMap.EDITUSERID}" />
							<input type="text" id="EDITUSER" name='userMap.EDITUSER' value="${userMap.EDITUSER}" onclick="openpeopletree('EDITUSERID@EDITUSER',1)" maxlength="2000" style="width: 96%;" />
						</td>
						<td class="texttd">方案验证人：</td>
						<td>
							<input type="hidden" id="confirmId" name='userMap.confirmId' value="${userMap.CONFIRMID}" />
							<input type="text" id="confirmName" name='userMap.confirmName' value="${userMap.CONFIRMNAME}" onclick="openpeopletree('confirmId@confirmName',1)" maxlength="2000" style="width: 96%;" />
						</td>
					</tr>
					<tr>
						<td class="texttd">一级审批人：</td>
						<td>
							<input type="hidden" id="auditUserIds" name='userMap.auditUserIds' value="${userMap.AUDITUSERIDS}" />
							<input type="text" id="auditUsers" name='userMap.auditUsers' value="${userMap.AUDITUSERS}" onclick="openpeopletree('auditUserIds@auditUsers',1)" maxlength="2000" style="width: 96%;" />
						</td>
						<td class="texttd">二级审批人：</td>
						<td>
							<input type="hidden" id="auditUserIds_2" name='userMap.auditUserIds_2' value="${userMap.AUDITUSERIDS_2}" />
							<input type="text" id="auditUsers_2" name='userMap.auditUsers_2' value="${userMap.AUDITUSERS_2}" onclick="openpeopletree('auditUserIds_2@auditUsers_2',1)" maxlength="2000" style="width: 96%;" />
						</td>
					</tr>
					<tr style="display: none;">
						<td class="texttd">评审人：</td>
						<td>
							<input type="hidden" id="ORGANIZEAUDITID" name='userMap.ORGANIZEAUDITID' value="${userMap.ORGANIZEAUDITID}" />
							<input type="text" id="ORGANIZEAUDITNAME" name='userMap.ORGANIZEAUDITNAME' value="${userMap.ORGANIZEAUDITNAME}" onclick="openpeopletree('ORGANIZEAUDITID@ORGANIZEAUDITNAME',1)" maxlength="2000" style="width: 96%;" />
						</td>
					</tr>
					<tr>
						<td class="texttd">实施人：</td>
						<td>
							<input type="hidden" id="excuteUserId" name='userMap.excuteUserId' value="${userMap.EXCUTEUSERID}" />
							<input type="text" id="excuteUser" name='userMap.excuteUser' value="${userMap.EXCUTEUSER}" onclick="openpeopletree('excuteUserId@excuteUser',1)" maxlength="2000" style="width: 96%;" />
						</td>
						<td class="texttd">验证人：</td>
						<td>
							<input type="hidden" id="testUserId" name='userMap.testUserId' value="${userMap.TESTUSERID}" />
							<input type="text" id="testUser" name='userMap.testUser' value="${userMap.TESTUSER}" onclick="openpeopletree('testUserId@testUser',1)" maxlength="2000" style="width: 96%;" />
						</td>
					</tr>
					<tr>
						<td class="texttd">描述：</td>
						<td colspan="3">
							<textarea id="configDesc" name='userMap.configDesc' rows="3" cols="3" maxlength="2000" style="width: 97%;">${userMap.CONFIGDESC}</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</div>
	</div>
</form>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$("#view_userMap\\.busSystem").bind("propertychange",function(){
		var busSystem = $("#view_userMap\\.busSystem").val();
		setFieldIdByName("busSystemCode",busSystem,"busSystem","PROPFIELD_03");
	})
});
</script>
</html>

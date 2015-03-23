<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<script language="javascript">
			window.onresize = function()
			{
				setCenter(0,30);
			}
			window.onload = function()
			{
				setCenter(0,30);
				refreshNode();
			}
			
			function refreshNode()
			{
				var message = '${returnMessage}';
				if(message == 'true')
				{
					alert("删除成功");
					parent.document.getElementById('leftFrame2').contentWindow.refresh("0");
				}
			}

			function menuDel()
			{
				if(document.getElementById("pid").value == "")
				{
					alert("<eoms:lable name='sm_msg_seletefirst'/>!");
					return false;
				}
				if(confirm("确认要删除本岗位及其下级岗位吗？"))
				{
					window.location.href = 'deleteOrgLevelManage.action?id='+document.getElementById('pid').value;
				}
			}
			
			function addnew(level)//level标识当前级别或是下级
			{
				var id = $("#pid").val();
				var isChild = "0";
				if (level == 'lower') {
					isChild = "1";
				}
				window.open("${ctx}/dutyOrgLevel/orgLevelManageAdd.action?id="+id+"&isChild="+isChild,id,"width=600,height=400,top=200,left=300,Location=no,Toolbar=no,Resizable=no,scrollbars=no");
			}
		</script>
	</head>
	<body>
		<input type="hidden" id="pid" value="${dol.pid}"/><!-- 本岗位id -->
		<form action="menuSave.action" method="post">
		<div class="content">
			<div class="page_div_bg">
				<div class="page_div">
					<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="menuDel()" text="com_btn_delete" operate="com_delete"/>
					<eoms:operate cssclass="page_sameadd_button" id="page_sameadd_button" onmouseover="this.className='page_sameadd_button_hover'" onmouseout="this.className='page_sameadd_button'" onclick="addnew('current')" text="sm_btn_currentadd" operate="com_add"/>
					<eoms:operate cssclass="page_loweradd_button" id="page_loweradd_button" onmouseover="this.className='page_loweradd_button_hover'" onmouseout="this.className='page_loweradd_button'" onclick="addnew('lower')" text="sm_btn_loweradd" operate="com_add"/>
	  	 		</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend>岗位详细信息</legend>
					<div class="blank_tr"></div>
					<table class="add_user">
						<tr>
							<td class="texttd" style="width:15%">岗位名称：</td>
							<td style="width:35%">
								<input type="text" id="orgName" name="dutyOrg.organizationname" maxlength="30" 
										class="textInput" value="${dutyOrg.organizationname }" />
							</td>
							<td class="texttd" style="width:15%">值班电话：</td>
							<td style="width:35%">
								<input type="text" id="telephone" name="dutyOrg.telephone" maxlength="15"
										class="textInput" value="${dutyOrg.telephone }"/>
							</td>
						</tr>
						<tr>
							<td class="texttd">接班提前时间：</td>
							<td>
								<input type="text" name="dutyOrg.onTimeBef" id="onTimeBef" maxlength="3" class="textInput"
										readonly="readonly" value="${dutyOrg.onTimeBef }分"/>
							</td>
							<td class="texttd">交班提前时间：</td>
							<td>
								<input type="text" name="dutyOrg.offTimeBef" id="offTimeBef" maxlength="3" class="textInput"
										readonly="readonly" value="${dutyOrg.offTimeBef }分"/>
							</td>
						</tr>
							<tr>
							<td class="texttd">启用停用：</td>
							<td>
								<input type="text" id="stateName" name="dutyOrg.stateName" class="textInput" 
									value="${dutyOrg.stateName }" readonly/>
							</td>
						</tr>
						<tr>
							<td class="texttd">班次：</td>
							<td colspan="3">
								<input type="text" id="shiftNames" name="dutyOrg.shiftNames" class="textInput"
									value="${dutyOrg.shiftNames }" readonly="readonly"/>
							</td>
						</tr>
						<tr>
								<td class="texttd">
									<span class="desc">值班人员：</span>
								</td>
								<td colspan="3">
										<input type="hidden" id="qualityerIds" name="dutyOrganization.qualityerIds" value="${dutyOrg.qualityerIds }"/>
										<input type="text" id="qualityerIds_name" name="dutyOrganization.qualityerNames" value="${dutyOrg.qualityerNames }" class="textInput" readonly="readonly" onclick="selectMultiPerSon(this);"/><span class="must">*</span>
								</td>
							</tr>
						<tr>
							<td class="texttd">描述：</td>
							<td colspan="3">
								<textarea id="note" name="dutyOrg.note" cols="45" rows="3" style="width: 98.5%" readonly="readonly">${dutyOrg.note }</textarea>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
		</div>
		</form>
	</body>
</html>

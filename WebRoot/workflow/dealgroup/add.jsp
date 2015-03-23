<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/core/taglibs_jsp.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
    
	 <title>
		     <eoms:lable name="wf_dealgroup_add" />
	</title>
	
    <script type="">
	window.onload = function(){
		setCenter(0,61);
		chgSel();	
	}
	window.onresize = function()
			{
				setCenter(0,61);
			}

	function submit() {
		$('#dealGroupForm')[0].submit();
	}

	function showTree() {
		showModalDialog('${ctx}/common/tools/depOpenRadioTree.jsp?form_name=dealGroupForm'+"&input_name=groupName&input_id=groupId",window,'help:no;center:true;scroll:no;status:no;dialogWidth:380px;dialogHeight:480px');
	}

	function chgSel() {
	   var checkText=$("#dealGroup.groupType").find("option:selected").text();  
		var type = $("#groupType").val();
		if(type == 'GROUP') {
			$('#groupId').unbind();
			$('#groupId').bind('click',showTree);
			$('#groupId').attr('readonly','true');
		} else {
			$('#groupId').unbind();
			$('#groupId').removeAttr('readonly');
		}
	}
    </script>
</head>
<body>
<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							<eoms:lable name="wf_dealgroup_add" />
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>

		<div class="scroll_div" id="center">
						
					    <s:form action="save.action" theme="simple" id="dealGroupForm" name="dealGroupForm" method="post" target="_self">
					    	<s:hidden name="dealGroup.id"></s:hidden>
					    	<s:hidden name="dealGroup.createTime"></s:hidden>
						
							<div class="tabContent_top">
								<table class="gd_table1" >
									<tr>
										<td class="rightalign1">
											<eoms:lable name="wf_dealgroup_name" />：
										</td>
										<td>
											<s:textfield name="dealGroup.name" id="dealGroup.name" cssClass="gd_input2"></s:textfield>
										</td>
									</tr>
									<tr>
										<td class="rightalign1">
											<eoms:lable name="wf_dealgroup_grouptype" />：
										</td>
										<td>
											<s:select onchange="chgSel()" name="dealGroup.groupType" id="groupType" list="#{'GROUP':'组','ROLE':'角色细分'}" emptyOption="false" cssClass="gd_select2"></s:select>
										</td>
									</tr>
									<tr>
										<td class="rightalign1">
											<eoms:lable name="wf_dealgroup_groupid" />：
										</td>
										<td>
											<s:textfield name="dealGroup.groupId" id="groupId" cssClass="gd_input2"></s:textfield>
										</td>
									</tr>
									<tr>
										<td class="rightalign1">
											<eoms:lable name="wf_dealgroup_groupname" />：
										</td>
										<td>
											<s:textfield name="dealGroup.groupName" id="groupName" cssClass="gd_input2"></s:textfield>
										</td>
									</tr>
									<tr>
										<td class="rightalign1">
											<eoms:lable name="wf_dealgroup_entrystate" />(以“;”分割)：
										</td>
										<td>
											<s:textarea name="dealGroup.entryState" cssClass="gd_textare" rows="5" cols="10"></s:textarea>
										</td>
									</tr>
									<tr>
										<td class="rightalign1">
											<eoms:lable name="wf_dealgroup_isenable" />：
										</td>
										<td>
											<s:select name="dealGroup.isEnable" list="#{'1':'启用','0':'停用'}" emptyOption="false" cssClass="gd_select2"></s:select>
										</td>
									</tr>
								</table>
							</div>
  					  </s:form>
				</div>
					
				<div class="add_bottom">
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="submit()" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
				</div>
	</div>
</body>
</html>
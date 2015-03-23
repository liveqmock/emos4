<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp" %>
	 <title>
		     <eoms:lable name="wf_sheet_childrole_addOrEdit" />
	</title>
	
    <script type="text/javascript">
	window.onload = function(){
		setCenter(0,61);
	}
	window.onresize = function()
	{
		setCenter(0,61);
	}

	function wfFormSubmit(){
		document.getElementById('wfForm').submit();
	}
    </script>
</head>
<body>
<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
								<eoms:lable name="wf_sheet_childrole_addOrEdit" />
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>

		<div class="scroll_div" id="center">
					    <s:form action="sheetrole/saveChildRole.action" theme="simple" id="wfForm" target="_self">
					    	<s:hidden name="childRole.childRoleId"></s:hidden>
					    	<s:hidden name="childRole.roleCode"></s:hidden>
					    	<s:hidden name="childRole.matchCount"></s:hidden>
					    	
							<div class="tabContent_top">
								<table class="add_user">
									<tr>
										<td class="texttd">
											<eoms:lable name="wf_sheet_childrolename" />：
										</td>
										<td>
											<input type="text" name="childRole.childRoleName" class="textInput" value="${childRole.childRoleName}" maxlength="50"/>
										</td>
									</tr>
									<tr>
										<td class="texttd"><eoms:lable name="wf_sheet_dimensionvalue" />：</td>
										<td>
											<s:textarea cssClass="textInput" name="childRole.dimensionValue" id="childRole.dimensionValue" cssStyle="width:96%" rows="3">
											</s:textarea>
										</td>
									</tr>
									<tr>
										<td class="texttd"><eoms:lable name="com_lb_remark" />：</td>
										<td>
											<s:textarea cssClass="textInput" name="childRole.childRoleDesc" id="childRole.childRoleDesc" cssStyle="width:96%" rows="3">
											</s:textarea>
										</td>
									</tr>
								</table>
							</div>
  					  </s:form>
				</div>
					
				<div class="add_bottom">
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onclick="wfFormSubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
				</div>
	</div>
</body>
</html>
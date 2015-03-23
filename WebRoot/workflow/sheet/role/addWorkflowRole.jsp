<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<%@ include file="/common/plugin/jquery/jquery.jsp" %>
    	<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
		 <title>
			     角色编辑
		</title>
	<script  language="javascript">
		window.onload = function(){
    		setCenter(0,61);
		}

		window.onresize = function()
			{
				setCenter(0,61);
			}

		function wfFormSubmit(){
			var wfForm = document.getElementById('wfForm');
			var bl  = Validator.Validate(wfForm,2,false);
			if(bl){
				document.getElementById('wfForm').submit();
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
							角色编辑
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>

		<div class="scroll_div" id="center">
					    <s:form action="saveWorkflowRole.action" theme="simple" id="wfForm" method="post" target="_self">
					    	<s:hidden name="commonWfRole.roleid"></s:hidden>
					    	<s:hidden name="commonWfRole.baseversion"></s:hidden>
					    	<s:hidden name="commonWfRole.phaseno"></s:hidden>
							<div class="tabContent_top">
								<table class="add_user2">
									<tr>
										<td class="texttd">
											业务名称<span class="must">*</span>:
										</td>
										<td>
											<s:textfield name="commonWfRole.basename" id="commonWfRole.basename" cssClass="textInput" maxlength="80"></s:textfield>
											<validation id="commonWfRole.basenameV" require="true" dataType="Require" msg="业务名称！" />
										</td>
									</tr>
									<tr>
										<td class="texttd">
											业务标识<span class="must">*</span>:
										</td>
										<td>
											<s:textfield name="commonWfRole.baseschema" id="commonWfRole.baseschema" cssClass="textInput" maxlength="80"></s:textfield>
											<validation id="commonWfRole.baseschemaV" require="true" dataType="Require" msg="业务标识！" />
										</td>
									</tr> 
									<tr>
										<td class="texttd">
											角色名称<span class="must">*</span>:
										</td>
										<td>
											<s:textfield name="commonWfRole.rolename" id="commonWfRole.rolename" cssClass="textInput" maxlength="80"></s:textfield>
											<validation id="commonWfRole.rolenameV" require="true" dataType="Require" msg="角色名称！" />
										</td>
									</tr>
									<tr>
										<td class="texttd">
											角色标识<span class="must">*</span>:
										</td>
										<td>
											<s:textfield name="commonWfRole.rolecode" id="commonWfRole.rolecode" cssClass="textInput" maxlength="80"></s:textfield>
											<validation id="commonWfRole.rolecodeV" require="true" dataType="Require" msg="角色标识！" />
										</td>
									</tr> 
								</table>
							</div>
  					  </s:form>
				</div>
					
				<div class="add_bottom">
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="wfFormSubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
				</div>
	</div>
</body>
</html>
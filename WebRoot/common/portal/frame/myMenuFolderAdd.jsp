<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_lb_createmenufolder"/></title>
		<script language="javascript">
		window.onresize = function() {
			setCenter(0,60);
		}
		
		window.onload = function() 
		{
			setCenter(0,60);
		}
		
		function openMyMenuFloderTreePage(form_name,input_name,input_id)
		{
			showModalDialog('${ctx}/common/tools/myMenuFolderTree.jsp?form_name='+form_name+"&input_name="+input_name+"&input_id="+input_id,window,'help:no;center:true;scroll:no;status:no;dialogWidth:350px;dialogHeight:400px');
		}
		
		function submitForm()
		{
			if(Validator.Validate(document.addMyMenuFolder,2))
				document.addMyMenuFolder.submit();
		}
	</script>
	</head>

	<body>
	  <form method="post" name="addMyMenuFolder" action="${ctx}/common/portal/addMyMenuFolder.action">
		<div class="content">
			<div class="title_right">
				<div class="title_left"> 
					<span class="title_bg">
						<span class="title_icon2"><eoms:lable name="sm_lb_createmenufolder"/></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<div class="blank_tr"></div>
					<div class="tabContent_top">
						<table class="add_user">
							<tr>
								<td class="texttd">
									<eoms:lable name="sm_lb_mymenufoldername"/>：
								</td>
								<td>
									<input type="text" name="myMenuNodename" value="" class="textInput" />
									<validation id="myMenuNodenameV" dataType="Custom" regexp="^.{1,40}$" msg="<eoms:lable name='sm_msg_mymenunodefolderConstraint'/>" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="sm_lb_mymenufolderpath"/>：
								</td>
								<td>
									<input type="text" name="nodename" id="nodename" value="<eoms:lable name="sm_lb_mymenufolder"/>" class="textInput" readonly onFocus="openMyMenuFloderTreePage('addMyMenuFolder','nodename','myMenuParentid');"/>
									<input type="hidden" name="myMenuParentid" id="myMenuParentid"/>
								</td>
							</tr>
						</table>
					</div>
				</fieldset>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save"/>" 
				class="save_button" onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="submitForm();" />
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	  </form>
	</body>
</html>

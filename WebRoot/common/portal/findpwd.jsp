<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>找回密码</title>
    
	<%@ include file="/common/core/taglibs.jsp"%> 
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
	  	<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2"> 找回密码
					</span> </span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="scroll_div" id="center">
				<fieldset class="fieldset_style">
					<legend></legend><!-- 角色基本信息 --> 
							<div id="div1">
							<form name="roleForm" action="${ctx}/portal/findpwd.action" method="post"> 
								<table style="align:center;"> 
									<tr> 
										<td width="80">登录名：</td> 
										<td><input name="loginName"></td> 
										<td width="50"><input type="submit" value="发送短信"></td> 
									</tr> 
									 
								</table> 
							</form> 
						</div>
					<div class="tabContent_top">;
						<label>${msg}</label>
					</div>
				</fieldset>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save" />" 
				class="save_button" onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="basicInfoSubmit('close');"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button"
				onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"
				onclick="window.close();" id="cancelButton"/>
		</div>
  </body>
</html>

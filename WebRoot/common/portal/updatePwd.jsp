<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>   
   	<%@ include file="/common/core/header_form.jsp"%>
   	<title><eoms:lable name='sm_lb_updatePwd' /></title>
	<script type="text/javascript">
		window.onresize = function() {
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		window.onload = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
			if('${returnMessage}'!='')
			{
				alert('${returnMessage}');
				window.close();
			}
			document.getElementById('init_pwd').focus();
		}
		function myformsubmit()
		{
			if(Validator.Validate(document.forms[0],2))
			{
				var sys_init_pwd = '${userSession.pwd}';
				var init_pwd = document.getElementById('init_pwd').value;
				var new_pwd = document.getElementById('new_pwd').value;
				var new_pwd_repeat = document.getElementById('new_pwd_repeat').value;
				$.get("${ctx}/portal/getNewPwdCypt.action",{newpwd:init_pwd},function(result){
					if(sys_init_pwd==result)
					{
						if(new_pwd!=new_pwd_repeat)
						{
							alert("<eoms:lable name='sm_lb_repeatPwdErr' />！");
						}
						else
						{
							document.forms[0].submit();
						}
					}
					else
					{
						alert("<eoms:lable name='sm_lb_initPwdErr' />！");
					}
				});
			}
		}
	</script>
  </head>
  <body>
  	<form action="${ctx}/portal/editPwd.action" method="post">
	  	<div class="content">
		    <div class="add_scroll_div_x" id="center">
		    	<br/><br/><br/><br/>
		    	<table style="width:400px">
		    		<tr>
		    			<td align="right" width="40%"><eoms:lable name='sm_lb_initPwd' />：</td>
		    			<td align="left" width="60%">
		    				<input type="password" name="init_pwd" id="init_pwd" value="" style="width:200px"/>
		    				<validation id="init_pwdV" dataType="Limit" max="30" msg="<eoms:lable name='sm_msg_pwdConstraint' />！"/>
		    			</td>
		    		</tr>
		    		<tr><td><br/></td></tr>
		    		<tr>
		    			<td align="right"><eoms:lable name='sm_lb_newPwd' />：</td>
		    			<td align="left">
		    				<input type="password" id="new_pwd" name="new_pwd" value="" style="width:200px"/>
		    				<validation id="new_pwdV" dataType="Limit" max="30" msg="<eoms:lable name='sm_msg_newPwdConstraint' />！"/>
		    			</td>
		    		</tr>
		    		<tr><td><br/></td></tr>
		    		<tr>
		    			<td align="right"><eoms:lable name='sm_lb_newPwdRepeat' />：</td>
		    			<td align="left">
		    				<input type="password" id="new_pwd_repeat" name="new_pwd_repeat" value="" style="width:200px"/>
		    				<validation id="new_pwd_repeatV" dataType="Limit" max="30" msg="<eoms:lable name='sm_msg_newPwdRepConstraint' />！"/>
		    			</td>
		    		</tr>
		    	</table>
		    </div>
		    <div class="add_bottom">
				<input type="button" id="submitbtn" value="<eoms:lable name="com_btn_save"/>" class="save_button"
					onmouseover="this.className='save_button_hover'" 
					onmouseout="this.className='save_button'" onclick="myformsubmit();"/>
				<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
					class="cancel_button"
					onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					onclick="window.close();" />
			</div>
		</div>
	</form>	
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>   
   	<%@ include file="/common/core/taglibs.jsp"%>
   	<base target="_self"/>
   	<title><eoms:lable name="sm_lb_addRoleOrg" /></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript">
		window.onresize = function() {
			setCenter(0,80);
		}
		window.onload = function() 
		{
			setCenter(0,80);
		}
		
		/*
		 获得所选择的部门和人员的ID
		*/
		function getItemInfo()
		{
			var str_temp = document.getElementById('tree').contentWindow.getCheckedInfo();
			//alert(str_temp);
			if(str_temp!=null)
			{
				var arr_temp = str_temp.split(';');
				var depids = arr_temp[0];
				var userids = arr_temp[2];
				document.getElementById('depids').value = depids;
				document.getElementById('userids').value = userids;
			}
		}
		
		function formSubmit()
		{
			getItemInfo();
			var roleids = '${param.theroleid}';
			document.getElementById('roleids').value = roleids;
			var depids = document.getElementById('depids').value;
			var userids = document.getElementById('userids').value;
			if(depids=='' && userids=='')
			{
				alert('<eoms:lable name="sm_msg_chooseDepOrUser" />！');
				return;
			}
			if(roleids=='')
			{
				return;
			}
			if(roleids!='')
			{
				document.roleOrgForm.submit();
			}
		}
	</script>
  </head>
  <body>
  	<form name="roleOrgForm" action="${ctx}/roleManager/theRoleOrgSave.action" method="post">
  		<input type="hidden" name="depids" id="depids" value=""/>
  		<input type="hidden" name="userids" id="userids" value=""/>
  		<input type="hidden" name="roleids" id="roleids" value=""/>
  	</form>
  	<div class="content">
	    <div class="title_right">
	      <div class="title_left">
	        <span class="title_bg">
	          <span class="title_icon2"><eoms:lable name="sm_lb_addRoleOrg"/></span>
	        </span>
	        <span class="title_xieline"></span>
	      </div>
	    </div>
	    <div style="overflow:auto;" id="center">
			<iframe src="${ctx}/common/tools/depListTree.jsp?isRadio=${param.isRadio}&isSelectType=${param.isSelectType}" id='tree' scrolling="no" frameborder="0" width="100%" height="100%"></iframe>
		</div>
	</div>
	<div class="add_bottom">
		<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button"
			onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="formSubmit();"/>
		<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button"
			onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"
			onclick="window.close();" />
	</div>	
  </body>
</html>

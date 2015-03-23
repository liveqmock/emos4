<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<base target="_self"/>
   	<%@ include file="/common/core/taglibs.jsp"%>
   	<title><eoms:lable name='sm_lb_addRoleMenu' /></title>
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
			openMenuTree('${parentid}');
		}
		var tree;
		function openMenuTree(roleid)
		{
			tree=new dhtmlXTreeObject("center","100%","100%",0);
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");
			tree.enableCheckBoxes(1);
			tree.enableTreeLines(true);
			tree.setXMLAutoLoading("${ctx}/common/menuTree.action?roleid="+roleid);
			tree.loadXML("${ctx}/common/menuTree.action?id=0&roleid="+roleid);
		}
		function formSubmit()
		{
			var menuids = tree.getAllChecked();
			document.getElementById('menuids').value = menuids;
			var roleids = document.getElementById('theroleid').value;
			if(''==menuids)
			{
				alert("<eoms:lable name='sm_msg_chooseOpObject' />！");
				return;
			}
			if(menuids!='' && roleids!='')
			{
				document.roleMenuForm.submit();
			}
		}
	</script>
  </head>
  <body>
  	<form action="${ctx}/roleManager/theRoleMenuSave.action" method="post" name="roleMenuForm">
  		<input type="hidden" name="menuids" id="menuids" value=""/>
  		<input type="hidden" name="theroleid" id="theroleid" value="${theroleid }"/>
  	</form>
  	<div class="content">
	   <div class="title_right">
	      <div class="title_left">
	        <span class="title_bg">
	          <span class="title_icon2"><eoms:lable name='sm_lb_addRoleMenu' /></span>
	        </span>
	        <span class="title_xieline"></span>
	      </div>
	    </div>
	    <div id="center" ></div>
	</div>
	<div class="add_bottom">
		<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button"
			onmouseover="this.className='save_button_hover'" 
			onmouseout="this.className='save_button'" onclick="formSubmit();"/>
		<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
			class="cancel_button"
			onmouseover="this.className='cancel_button_hover'"
			onmouseout="this.className='cancel_button'"
			onclick="window.close();" />
	</div>	
  </body>
</html>

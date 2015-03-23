<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>   
   	<%@ include file="/common/core/taglibs.jsp"%>
   	<title><eoms:lable name='sm_t_roleMenuCfg' /></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript">
		window.onresize = function() {
			setCenter(0, LAYOUT_FORM_OPEN + 30);
		}
		window.onload = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN + 30);
		}
		var tree;
		function openMenuTree(roleid,selfid)
		{
			tree=new dhtmlXTreeObject("center","100%","98%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			if("${sessionScope.userSession.isAdmin}"=="0" && "${sessionScope.userSession.roleId}".lastIndexOf(selfid)!=-1)
			{//不能给自己配权限
				tree.enableCheckBoxes(2);
				document.getElementById('submitButton').style.visibility = "hidden";
				alert("<eoms:lable name='sm_msg_selfRoleAssignConstraint' />！");
			}
			else
			{
				tree.enableCheckBoxes(1);
				document.getElementById('submitButton').style.visibility = "visible";
			}
			tree.enableTreeLines(true);//是否显示结点间的连线,默认false
			tree.setOnClickHandler(doHandler);
			tree.setXMLAutoLoading("${ctx}/common/menuTree.action?roleid="+roleid);
			tree.loadXML("${ctx}/common/menuTree.action?id=0&roleid="+roleid);
		}
		function doHandler(key){}
		function openTree(formname,input_id,input_name,input_parentid)
		{
			showModalDialog('${ctx}/roleManager/getSelfRoleTree.action?form_name='+formname+'&input_name='+input_name+'&input_id='+input_id+'&input_parentid='+input_parentid,window,'help:no;scroll:yes;status:no;dialogWidth:300px;dialogHeight:400px');
			var parentid = document.getElementById(input_parentid).value;
			var selfid = document.getElementById(input_id).value;
			if(parentid=='')
			{
				return;
			}
			var tem = document.getElementById('center').childNodes;
			for(var i=tem.length-1;i>=0;i--)
			{
				document.getElementById('center').removeChild(tem[i]);
			}
			openMenuTree(parentid,selfid);
		}
		function submit()
		{
			var menuids = tree.getAllChecked();
			document.getElementById('menuids').value = menuids;
			var roleids = document.getElementById('roleids').value;
			if(menuids!='' && roleids!='')
			{
				document.roleMenuForm.submit();
			}
		}
	</script>
  </head>
  <body>
  	<div class="content">
	   <div class="title_right">
	      <div class="title_left">
	        <span class="title_bg">
	          <span class="title_icon2"><eoms:lable name='sm_t_roleMenuCfg' /></span>
	        </span>
	        <span class="title_xieline"></span>
	      </div>
	    </div>
	    <div>
	    	<form action="${ctx}/roleMenuManager/saveRoleMenuTree.action" name="roleMenuForm" method="post">
	    		<table width="100%">
					<tr>
						<td width="20%"><eoms:lable name='com_btn_chooseRole' />：</td>
						<td width="60%">
							<input type="text" name="roleNames" id="roleNames" value="" class="textInput" readonly="readonly"/>
							<input type="hidden" name="roleids" id="roleids" value=""/>
							<input type="hidden" name="parentids" id="parentids" value=""/>
							<input type="hidden" name="menuids" id="menuids" value=""/> 
						</td>
						<td width="20%">
							<input type="button" name="button3" id="button3" value="<eoms:lable name="com_btn_choose"/>"
								class="operate_button"
								onmouseover="this.className='operate_button_hover'"
								onmouseout="this.className='operate_button'" 
								onclick="openTree('roleMenuForm','roleids','roleNames','parentids')" />
						</td>
					</tr>
				</table>
			</form>
	    </div>
	    <div class="add_scroll_div_x" style="overflow:hidden;" id="center" ></div>
	    <div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button"
				onmouseover="this.className='save_button_hover'" 
				onmouseout="this.className='save_button'" onclick="submit();" id="submitButton" style="visibility:hidden;"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	</div>	
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_subt_addMymenu"/></title>
		<script language="javascript">
		window.onresize = function() {
			setCenter(0,55);
		}
		
		window.onload = function() 
		{
			setCenter(0,55);
		}
		
		function openMyMenuFloderTreePage(form_name,input_name,input_id)
		{
			showModalDialog('${ctx}/common/tools/myMenuFolderTree.jsp?form_name='+form_name+"&input_name="+input_name+"&input_id="+input_id,window,'help:no;center:true;scroll:no;status:no;dialogWidth:350px;dialogHeight:400px');
		}
		
		function createMenuFolder(url,name,iWidth,iHeight)
		{
			var url; 
			var name; 
			var iWidth; 
			var iHeight; 
			var iTop = (window.screen.availHeight-30-iHeight)/2; 
			var iLeft = (window.screen.availWidth-10-iWidth)/2; 
			window.open(url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no');
		}
		
		function submitForm()
		{
			if(Validator.Validate(document.addMyMenu,2))
				document.addMyMenu.submit();
		}
	</script>
	</head>

	<body>
	  <form method="post" name="addMyMenu" action="${ctx}/common/portal/addMyMenu.action">
	 	<input type="hidden" name="menuInfo.pid" value="${menuInfo.pid}" />
	 	<input type="hidden" name="menuInfo.nodeurl" value="${menuInfo.nodeurl}" />
	 	<input type="hidden" name="menuInfo.status" value="${menuInfo.status}" />
	 	<input type="hidden" name="menuInfo.nodemark" value="${menuInfo.nodemark}" />
		<div class="content">
			<div class="title_right">
				<div class="title_left"> 
					<span class="title_bg">
						<span class="title_icon2"><eoms:lable name="sm_subt_addMymenu"/></span>
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
									<img src="${ctx}/common/style/blue/images/stowmenu.jpg"  alt="<eoms:lable name="sm_subt_addMymenu"/>" />
								</td>
								<td colspan="3">
									<eoms:lable name="sm_msg_mymenureadme"/>
								</td>
							</tr>
							<tr>
								<td class="texttd">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="sm_lb_mymenuname"/>：
								</td>
								<td colspan="4">
									<input type="text" name="menuInfo.nodename" value="${menuInfo.nodename}" class="textInput" />
									<validation id="menuInfo.nodenameV" dataType="Custom" regexp="^.{1,30}$" msg="<eoms:lable name='sm_msg_mymenunodenameConstraint'/>" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="sm_lb_mymenupath"/>：
								</td>
								<td colspan="3">
									<input type="text" name="nodename" id="nodename" value="<eoms:lable name="sm_lb_mymenufolder"/>" class="textInput" readonly onFocus="openMyMenuFloderTreePage('addMyMenu','nodename','myMenuParentid');"/>
									<input type="hidden" name="myMenuParentid" id="myMenuParentid"/>
								</td>
								<td>
									<input type="button" value="<eoms:lable name="sm_lb_createmenufolder"/>" class="texttd" onclick="createMenuFolder('${ctx}/common/portal/myMenuFolderAdd.action','',550,270);"/>
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

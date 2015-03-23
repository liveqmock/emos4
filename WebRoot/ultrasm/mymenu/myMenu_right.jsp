<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript">
			window.onresize = function()
			{
				setCenter(0,30);
			}
			window.onload = function()
			{
				setCenter(0,30);
				refreshNode();
				var pid = "${myMenu.pid}";
				if(pid=="")
				{
					document.getElementById("com_btn_del").style.display = "none";
					document.getElementById("page_sameadd_button").style.display = "none";
					document.getElementById("page_loweradd_button").style.display = "none";
					document.getElementById("page_cancelchanges_button").style.display = "none";
				}
			}
			
			function refreshNode()
			{
				var message = '${message}';
				if(message == 'success')
				{
					var pid = '${parentId}';
					parent.document.getElementById('leftFrame2').contentWindow.refresh(pid);
				}
			}

			function myMenuDel()
			{
				if(document.getElementById("myMenu.pid").value == "")
				{
					alert("<eoms:lable name='sm_msg_seletefirst'/>!");
					return false;
				}
				if(confirm("<eoms:lable name='sm_msg_delMenuinfo'/>？"))
				{
					window.location.href = 'myMenuDel.action?myMenuId='+document.getElementById('pid').value+'&parentId='+document.getElementById('myMenu.parentid').value;
				}
			}
			
			function myMenuSave()
			{
				if(document.getElementById("myMenu.parentid").value == "")
				{
					//alert("<eoms:lable name='sm_msg_seletefirst'/>!");
					//return false;
					document.getElementById("myMenu.parentid").value = "0";
				}
				if(!isChange())
				{
					alert("<eoms:lable name='sm_msg_nomodify'/>!");
					return;
				}
				if(Validator.Validate(document.forms[0],2))
				{
					document.forms[0].submit();
					/*
					var src = document.getElementById('myMenu.nodemark');
					$.get("checkUnique.action",{nodeMark:src.value,menuId:document.getElementById('myMenu.pid').value},function(result)
					{
						if(result=='false')
						{
							src.style.color = 'red';
							alert('<eoms:lable name="sm_msg_uniqueMenuMark"/>！');
							src.focus();
						}
						else
						{
							src.style.color = 'black';
							document.forms[0].submit();
						}
					});
					*/
				}
			}
			
			function addnew(level)//level标识当前级别或是下级
			{
				if(document.getElementById("myMenu.parentid").value == "")
				{
					alert("<eoms:lable name='sm_msg_seletefirst'/>!");
					return false;
				}
				if(isChange())
				{
					alert("<eoms:lable name='sm_msg_savefirst'/>!");
					return false;
				}
				if(level == 'lower')
				{
					var nodetype = document.getElementById('myMenu.nodetype').value;
					if(nodetype == '2')
					{
						alert("<eoms:lable name='sm_msg_menuNodeBanChild'/>！");
						return false;
					}
					document.getElementById("myMenu.parentid").value = document.getElementById("myMenu.pid").value;
					document.getElementById("myMenuNodename").value = document.getElementById("myMenu.nodename").value;
				}
				document.getElementById("myMenu.pid").value = "";
				document.getElementById("myMenu.nodename").value = "";
				document.getElementById("myMenu.nodemark").value = "";
				document.getElementById("myMenu.nodetype").value = "1";
				document.getElementById("myMenu.nodeurl").value = "";
				document.getElementById("myMenu.nodeurl").disabled = "disabled";
				document.getElementById("myMenu.status").value = "1";
				document.getElementById("myMenu.ordernum").value = "";
				document.getElementById("myMenu.remark").value = "";
				
			}
			
			function changeType()
			{
				var nodetype = document.getElementById('myMenu.nodetype').value;
				if(nodetype == '1')
					document.getElementById('myMenu.nodeurl').disabled = 'disabled';
				else if(nodetype == '2')
					document.getElementById('myMenu.nodeurl').disabled = '';
			}
			
			function isChange()
			{
				if(document.getElementById('nodename').value != document.getElementById('myMenu.nodename').value)
					return true;
				if(document.getElementById('parentid').value != document.getElementById('myMenu.parentid').value)
					return true;
				if(document.getElementById('parent').value != document.getElementById('myMenuNodename').value)
					return true;
				if(document.getElementById('nodemark').value != document.getElementById('myMenu.nodemark').value)
					return true;
				if(document.getElementById('nodeurl').value != document.getElementById('myMenu.nodeurl').value)
					return true;
				if(document.getElementById('status').value != document.getElementById('myMenu.status').value)
					return true;
				if(document.getElementById('ordernum').value != document.getElementById('myMenu.ordernum').value)
					return true;
				if(document.getElementById('remark').value != document.getElementById('myMenu.remark').value)
					return true;
				return false;
			}
			
			function resume()
			{
				if(!isChange() || document.getElementById("myMenu.parentid").value == "")
				{
					alert("<eoms:lable name='sm_msg_nomodify'/>!");
					return;
				}
				document.getElementById('myMenu.pid').value = document.getElementById('pid').value;
				document.getElementById('myMenu.parentid').value = document.getElementById('parentid').value;
				document.getElementById('myMenuNodename').value = document.getElementById('parent').value;
				document.getElementById('myMenu.nodetype').value = document.getElementById('nodetype').value;
				document.getElementById('myMenu.nodename').value = document.getElementById('nodename').value;
				document.getElementById('myMenu.nodemark').value = document.getElementById('nodemark').value;
				document.getElementById('myMenu.nodeurl').value = document.getElementById('nodeurl').value;
				changeType();
				document.getElementById('myMenu.status').value = document.getElementById('status').value;
				document.getElementById('myMenu.ordernum').value = document.getElementById('ordernum').value;
				document.getElementById('myMenu.remark').value = document.getElementById('remark').value;
			}
		</script>
	</head>
	<body>
		<form action="myMenuSave.action" method="post">
		<input type="hidden" name="myMenu.pid" value="${myMenu.pid}"/>
		<div class="content">
			<div class="page_div_bg">
				<div class="page_div">
					<eoms:operate cssclass="page_saves_button" id="page_saves_button" onmouseover="this.className='page_saves_button_hover'" onmouseout="this.className='page_saves_button'" onclick="myMenuSave()" text="com_btn_save" />
					<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="myMenuDel()" text="com_btn_delete" />
					<eoms:operate cssclass="page_sameadd_button" id="page_sameadd_button" onmouseover="this.className='page_sameadd_button_hover'" onmouseout="this.className='page_sameadd_button'" onclick="addnew('current')" text="sm_btn_currentadd" />
					<eoms:operate cssclass="page_loweradd_button" id="page_loweradd_button" onmouseover="this.className='page_loweradd_button_hover'" onmouseout="this.className='page_loweradd_button'" onclick="addnew('lower')" text="sm_btn_loweradd" />
					<eoms:operate cssclass="page_cancelchanges_button" id="page_cancelchanges_button" onmouseover="this.className='page_cancelchanges_button_hover'" onmouseout="this.className='page_cancelchanges_button'" onclick="resume()" text="sm_btn_resumeedit"/>
	  	 		</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend><eoms:lable name="sm_t_menu"/></legend>
					<div class="blank_tr"></div>
					<table class="add_user">
						<tr>
							<td class="texttd" style="width:15%"><eoms:lable name="sm_lb_parentname" />：</td>
							<td style="width:35%">
								<input type="hidden" id="myMenu.parentid" name="myMenu.parentid" class="textInput" value="${myMenu.parentid}"/>
								<input type="text" id="myMenuNodename" name="myMenuNodename" class="textInput" value="${myMenuNodename}" disabled/>
							</td>
							<td class="texttd" style="width:15%"><eoms:lable name="sm_lb_mymenutype" />：<span class="must">*</span></td>
							<td style="width:35%">
								<eoms:select name="myMenu.nodetype" style="select" dataDicTypeCode="myMenuType" value="${myMenu.nodetype}" onChangeFun="changeType();" isnull="false"/>
								<validation id="myMenu.nodetypeV" dataType="Limit" Min="1" Max="2" msg="<eoms:lable name='sm_msg_statusConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="sm_lb_nodename" />：<span class="must">*</span></td>
							<td>
								<input type="text" id="myMenu.nodename" name="myMenu.nodename" class="textInput" value="${myMenu.nodename}" />
								<validation id="myMenu.nodenameV" require="true" dataType="Limit" Min="1" Max="20" msg="<eoms:lable name='sm_msg_nodenameConstraint'/>！" />
							</td>
							<td class="texttd"><eoms:lable name="sm_lb_nodemark" />：<span class="must">*</span></td>
							<td>
								<input type="text" id="myMenu.nodemark" name="myMenu.nodemark" class="textInput" value="${myMenu.nodemark}" />
								<validation id="myMenu.nodemarkV" require="true" dataType="Custom" regexp="^\w{1,30}$" msg="<eoms:lable name='sm_msg_nodemarkConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="com_lb_status" />：<span class="must">*</span></td>
							<td>
								<eoms:select name="myMenu.status" style="select" dataDicTypeCode="status" value="${myMenu.status}" isnull="false"/>
								<validation id="myMenu.statusV" dataType="Limit" Min="1" Max="2" msg="<eoms:lable name='sm_msg_statusConstraint'/>！" />
							</td>
							<td class="texttd"><eoms:lable name="com_lb_orderNum" />：<span class="must">*</span></td>
							<td>
								<input type="text" id="myMenu.ordernum" name="myMenu.ordernum" class="textInput" value="${myMenu.ordernum}" />
								<validation id="myMenu.ordernumV" dataType="Custom" regexp="^[0-9]{1,5}$" msg="<eoms:lable name='sm_msg_orderNumConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="sm_lb_nodeurl" />：</td>
							<td colspan="6">
								<input type="text" id="myMenu.nodeurl" name="myMenu.nodeurl" style="width:99%" class="textInput" value="${myMenu.nodeurl}" />
								<validation id="myMenu.nodeurlV" require="false" dataType="Limit" max="80" msg="<eoms:lable name='sm_msg_nodeurlConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top"><eoms:lable name="com_lb_remark"/>：</td>
							<td colspan="3">
								<textarea name="myMenu.remark" id="textarea" rows="6" class="textInput">${myMenu.remark}</textarea>
								<validation id="myMenu.remarkV" require="false" dataType="Limit" max="160" msg="<eoms:lable name='sm_msg_remarkConstraint'/>！" />
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
		</div>
		<input type="hidden" id="pid" name="pid" value="${myMenu.pid}"/>
		<input type="hidden" id="parentid" name="parentid" value="${myMenu.parentid}"/>
		<input type="hidden" id="parent" name="parent" value="${myMenuNodename}"/>
		<input type="hidden" id="nodetype" name="nodetype" value="${myMenu.nodetype}"/>
		<input type="hidden" id="nodename" name="nodename" value="${myMenu.nodename}"/>
		<input type="hidden" id="nodemark" name="nodemark" value="${myMenu.nodemark}"/>
		<input type="hidden" id="nodeurl" name="nodeurl" value="${myMenu.nodeurl}"/>
		<input type="hidden" id="status" name="status" value="${myMenu.status}"/>
		<input type="hidden" id="ordernum" name="ordernum" value="${myMenu.ordernum}"/>
		<input type="hidden" id="remark" name="remark" value="${myMenu.remark}"/>
		<input type="hidden" id="myMenu.creater" name="myMenu.creater" value="${myMenu.creater}"/>
		<input type="hidden" id="myMenu.createtime" name="myMenu.createtime" value="${myMenu.createtime}"/>
		<input type="hidden" id="myMenu.lastmodifier" name="myMenu.lastmodifier" value="${myMenu.lastmodifier}"/>
		<input type="hidden" id="myMenu.lastmodifytime" name="myMenu.lastmodifytime" value="${myMenu.lastmodifytime}"/>
		</form>
	</body>
</html>

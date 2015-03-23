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
			}
			
			function refreshNode()
			{
				var message = '${message}';
				if(message == 'success')
				{
					var pid = '${menuinfo.parentid}';
					parent.document.getElementById('leftFrame2').contentWindow.refresh(pid);
				}
			}

			function menuDel()
			{
				if(document.getElementById("menuinfo.pid").value == "")
				{
					alert("<eoms:lable name='sm_msg_seletefirst'/>!");
					return false;
				}
				if(confirm("<eoms:lable name='sm_msg_delMenuinfo'/>？"))
				{
					window.location.href = 'menuDel.action?menuId='+document.getElementById('pid').value;
				}
			}
			
			function menuSave()
			{
				if(document.getElementById("menuinfo.parentid").value == "")
				{
					alert("<eoms:lable name='sm_msg_seletefirst'/>!");
					return false;
				}
				if(!isChange())
				{
					alert("<eoms:lable name='sm_msg_nomodify'/>!");
					return;
				}
				if(Validator.Validate(document.forms[0],2))
				{
					var src = document.getElementById('menuinfo.nodemark');
					$.get("checkUnique.action",{nodeMark:src.value,menuId:document.getElementById('menuinfo.pid').value},function(result)
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
				}
			}
			
			function addnew(level)//level标识当前级别或是下级
			{
				if(document.getElementById("menuinfo.parentid").value == "")
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
					document.getElementById("menuinfo.parentid").value = document.getElementById("menuinfo.pid").value;
					document.getElementById("parentName").value = document.getElementById("menuinfo.nodename").value;
				}
				document.getElementById("menuinfo.pid").value = "";
				document.getElementById("menuinfo.nodename").value = "";
				document.getElementById("menuinfo.nodemark").value = "";
				if(document.getElementById("menuinfo.parentid").value == "0")
				{
					document.getElementById("menuinfo.nodetype").value = "0";
					document.getElementById('nodetype').value = "<eoms:dic dictype='menutype' value='0'/>";
				}
				else
				{
					document.getElementById("menuinfo.nodetype").value = "1";
					document.getElementById('nodetype').value = "<eoms:dic dictype='menutype' value='1'/>";
				}
				document.getElementById("menuinfo.nodeurl").value = "";
				document.getElementById("menuinfo.openway").value = "1";
				document.getElementById("menuinfo.status").value = "1";
				document.getElementById("menuinfo.ordernum").value = "";
				document.getElementById("menuinfo.classname").value = "";
				document.getElementById("menuinfo.remark").value = "";
			}
			
			function isChange()
			{
				if(document.getElementById('nodename').value != document.getElementById('menuinfo.nodename').value)
					return true;
				if(document.getElementById('parentid').value != document.getElementById('menuinfo.parentid').value)
					return true;
				if(document.getElementById('parent').value != document.getElementById('parentName').value)
					return true;
				if(document.getElementById('nodemark').value != document.getElementById('menuinfo.nodemark').value)
					return true;
				if(document.getElementById('nodeurl').value != document.getElementById('menuinfo.nodeurl').value)
					return true;
				if(document.getElementById('openway').value != document.getElementById('menuinfo.openway').value)
					return true;
				if(document.getElementById('status').value != document.getElementById('menuinfo.status').value)
					return true;
				if(document.getElementById('ordernum').value != document.getElementById('menuinfo.ordernum').value)
					return true;
				if(document.getElementById('classname').value != document.getElementById('menuinfo.classname').value)
					return true;
				if(document.getElementById('remark').value != document.getElementById('menuinfo.remark').value)
					return true;
				return false;
			}
			
			function resume()
			{
				if(!isChange() || document.getElementById("menuinfo.parentid").value == "")
				{
					alert("<eoms:lable name='sm_msg_nomodify'/>!");
					return;
				}
				document.getElementById('menuinfo.pid').value = document.getElementById('pid').value;
				document.getElementById('menuinfo.parentid').value = document.getElementById('parentid').value;
				document.getElementById('parentName').value = document.getElementById('parent').value;
				document.getElementById('menuinfo.nodetype').value = document.getElementById('nodetype').value;
				document.getElementById('menuinfo.nodename').value = document.getElementById('nodename').value;
				document.getElementById('menuinfo.nodemark').value = document.getElementById('nodemark').value;
				document.getElementById('menuinfo.nodeurl').value = document.getElementById('nodeurl').value;
				document.getElementById('menuinfo.openway').value = document.getElementById('openway').value;
				document.getElementById('menuinfo.status').value = document.getElementById('status').value;
				document.getElementById('menuinfo.ordernum').value = document.getElementById('ordernum').value;
				document.getElementById('menuinfo.classname').value = document.getElementById('classname').value;
				document.getElementById('menuinfo.remark').value = document.getElementById('remark').value;
			}
		</script>
	</head>
	<body>
		<form action="menuSave.action" method="post">
		<input type="hidden" id="menuinfo.pid" name="menuinfo.pid" value="${menuinfo.pid}"/>
		<input type="hidden" id="menuinfo.nodepath" name="menuinfo.nodepath" value="${menuinfo.nodepath}"/>
		<div class="content">
			<div class="page_div_bg">
				<div class="page_div">
					<eoms:operate cssclass="page_saves_button" id="page_saves_button" onmouseover="this.className='page_saves_button_hover'" onmouseout="this.className='page_saves_button'" onclick="menuSave()" text="com_btn_save" operate="com_save"/>
					<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="menuDel()" text="com_btn_delete" operate="com_delete"/>
					<eoms:operate cssclass="page_sameadd_button" id="page_sameadd_button" onmouseover="this.className='page_sameadd_button_hover'" onmouseout="this.className='page_sameadd_button'" onclick="addnew('current')" text="sm_btn_currentadd" operate="com_add"/>
					<eoms:operate cssclass="page_loweradd_button" id="page_loweradd_button" onmouseover="this.className='page_loweradd_button_hover'" onmouseout="this.className='page_loweradd_button'" onclick="addnew('lower')" text="sm_btn_loweradd" operate="com_add"/>
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
								<input type="hidden" id="menuinfo.parentid" name="menuinfo.parentid" class="textInput" value="${menuinfo.parentid}"/>
								<input type="text" id="parentName" name="parentName" class="textInput" value="${parentName}" disabled/>
							</td>
							<td class="texttd" style="width:15%"><eoms:lable name="sm_lb_nodetype" />：</td>
							<td style="width:35%">
								<input type="hidden" id="menuinfo.nodetype" name="menuinfo.nodetype" class="textInput" value="${menuinfo.nodetype}"/>
								<input type="text" name="nodetype" class="textInput" value="<eoms:dic dictype='menutype' value='${menuinfo.nodetype}'/>" disabled/>
							</td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="sm_lb_nodename" />：<span class="must">*</span></td>
							<td>
								<input type="text" id="menuinfo.nodename" name="menuinfo.nodename" class="textInput" value="${menuinfo.nodename}" />
								<validation id="menuinfo.nodenameV" require="true" dataType="Limit" Max="20" msg="<eoms:lable name='sm_msg_nodenameConstraint'/>！" />
							</td>
							<td class="texttd"><eoms:lable name="sm_lb_nodemark" />：<span class="must">*</span></td>
							<td>
								<input type="text" id="menuinfo.nodemark" name="menuinfo.nodemark" class="textInput" value="${menuinfo.nodemark}" />
								<validation id="menuinfo.nodemarkV" require="true" dataType="Custom" regexp="^[^\u4e00-\u9fa5]{1,80}$" msg="<eoms:lable name='sm_msg_nodemarkConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="com_lb_status" />：<span class="must">*</span></td>
							<td>
								<eoms:select name="menuinfo.status" style="select" dataDicTypeCode="status" value="${menuinfo.status}" isnull="false"/>
								<validation id="menuinfo.statusV" dataType="Limit" Min="1" Max="2" msg="<eoms:lable name='sm_msg_statusConstraint'/>！" />
							</td>
							<td class="texttd"><eoms:lable name="sm_lb_openway" />：<span class="must">*</span></td>
							<td>
								<eoms:select name="menuinfo.openway" style="select" dataDicTypeCode="openway" value="${menuinfo.openway}" isnull="false"/>
								<validation id="menuinfo.openwayV" dataType="Limit" Min="1" Max="2" msg="<eoms:lable name='sm_msg_openwayConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="com_lb_orderNum" />：<span class="must">*</span></td>
							<td>
								<input type="text" id="menuinfo.ordernum" name="menuinfo.ordernum" class="textInput" value="${menuinfo.ordernum}" />
								<validation id="menuinfo.ordernumV" dataType="Custom" regexp="^[0-9]{1,5}$" msg="<eoms:lable name='sm_msg_orderNumConstraint'/>！" />
							</td>
							<td class="texttd"><eoms:lable name="sm_lb_classname" />：</td>
							<td>
								<input type="text" id="menuinfo.classname" name="menuinfo.classname" class="textInput" value="${menuinfo.classname}" />
								<validation id="menuinfo.classnameV" require="false" dataType="Custom" regexp="^\w{1,30}$" msg="<eoms:lable name='sm_msg_classnameConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="sm_lb_nodeurl" />：</td>
							<td colspan="6">
								<input type="text" id="menuinfo.nodeurl" name="menuinfo.nodeurl" style="width:99%" class="textInput" value="${menuinfo.nodeurl}" />
								<validation id="menuinfo.nodeurlV" require="false" dataType="Limit" max="150" msg="<eoms:lable name='sm_msg_nodeurlConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top"><eoms:lable name="com_lb_remark"/>：</td>
							<td colspan="3">
								<textarea id="menuinfo.remark" name="menuinfo.remark" rows="6" class="textInput">${menuinfo.remark}</textarea>
								<validation id="menuinfo.remarkV" require="false" dataType="Limit" max="160" msg="<eoms:lable name='sm_msg_remarkConstraint'/>！" />
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
		</div>
		<input type="hidden" id="pid" name="pid" value="${menuinfo.pid}"/>
		<input type="hidden" id="parentid" name="parentid" value="${menuinfo.parentid}"/>
		<input type="hidden" id="parent" name="parent" value="${parentName}"/>
		<input type="hidden" id="nodetype" name="nodetype" value="${menuinfo.nodetype}"/>
		<input type="hidden" id="nodename" name="nodename" value="${menuinfo.nodename}"/>
		<input type="hidden" id="nodepath" name="nodepath" value="${menuinfo.nodepath}"/>
		<input type="hidden" id="nodemark" name="nodemark" value="${menuinfo.nodemark}"/>
		<input type="hidden" id="nodeurl" name="nodeurl" value="${menuinfo.nodeurl}"/>
		<input type="hidden" id="openway" name="openway" value="${menuinfo.openway}"/>
		<input type="hidden" id="status" name="status" value="${menuinfo.status}"/>
		<input type="hidden" id="ordernum" name="ordernum" value="${menuinfo.ordernum}"/>
		<input type="hidden" id="classname" name="classname" value="${menuinfo.classname}"/>
		<input type="hidden" id="remark" name="remark" value="${menuinfo.remark}"/>
		<input type="hidden" id="menuinfo.creater" name="menuinfo.creater" value="${menuinfo.creater}"/>
		<input type="hidden" id="menuinfo.createtime" name="menuinfo.createtime" value="${menuinfo.createtime}"/>
		<input type="hidden" id="menuinfo.lastmodifier" name="menuinfo.lastmodifier" value="${menuinfo.lastmodifier}"/>
		<input type="hidden" id="menuinfo.lastmodifytime" name="menuinfo.lastmodifytime" value="${menuinfo.lastmodifytime}"/>
		</form>
	</body>
</html>

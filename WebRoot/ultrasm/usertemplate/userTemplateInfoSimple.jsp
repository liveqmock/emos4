<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/header_form.jsp"%>
		<title><eoms:lable name='sm_t_userTemplate'/></title>
		<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0, LAYOUT_LIST_OPEN); //由于按钮在上面，所以使用list的工具条的高度
		}
		window.onload = function() 
		{
			var message = '${returnMessage}';
			if(message != '')
			{
				parent.document.getElementById('leftTreeFrame').contentWindow.refresh('${parentid}');
			}
			setCenter(0, LAYOUT_LIST_OPEN);
		}
		function selectTemplateData()
		{
			var selectData = 'U:' + document.getElementById('userdata_id').value + ';D:' + document.getElementById('depdata_id').value;
			showModalDialog('${ctx}/common/tools/orgtree/organizaTree.jsp?isSelectType=2&user_name=userdata_name&user_id=userdata_id&dep_name=depdata_name&dep_id=depdata_id&targetDataArr='+selectData,window,'help:no;center:true;scroll:no;status:no;dialogWidth:580px;dialogHeight:490px');
		}
		function selectTemplateShare()
		{
			var selectData = 'U:' + document.getElementById('usershare_id').value + ';D:' + document.getElementById('depshare_id').value;
			showModalDialog('${ctx}/common/tools/orgtree/organizaTree.jsp?isSelectType=2&user_name=usershare_name&user_id=usershare_id&dep_name=depshare_name&dep_id=depshare_id&targetDataArr='+selectData,window,'help:no;center:true;scroll:no;status:no;dialogWidth:580px;dialogHeight:490px');
		}
		function clearTemplateData()
		{
			document.getElementById('userdata_id').value = '';
			document.getElementById('userdata_name').value = '';
			document.getElementById('depdata_id').value = '';
			document.getElementById('depdata_name').value = '';
		}
		function clearTemplateShare()
		{
			document.getElementById('usershare_id').value = '';
			document.getElementById('usershare_name').value = '';
			document.getElementById('depshare_id').value = '';
			document.getElementById('depshare_name').value = '';
		}
		function setShare()
		{
			if(document.getElementById('userTemplate.isshare').value == '1')
			{
				document.getElementById('cfgShare').disabled = '';
				document.getElementById('clearShare').disabled = '';
			}
			else
			{
				document.getElementById('cfgShare').disabled = 'disabled';
				document.getElementById('clearShare').disabled = 'disabled';
			}
		}
		function formSubmit()
		{
			//dataid：当dataid=false 时，则说明此尚未修改配置数据 不用进行操作配置数据表
			var dataid = document.getElementById('userdata_id').value + ':' + document.getElementById('depdata_id').value;
			if(document.getElementById('dataid').value == dataid)
			{
				document.getElementById('dataid').value = 'false';
			}
			//shareid：当shareid=false 时，则说明此尚未修改配置共享 不用进行操作配置共享表
			var shareid = document.getElementById('usershare_id').value + ':' + document.getElementById('depshare_id').value;
			if(document.getElementById('shareid').value == shareid)
			{
				document.getElementById('shareid').value = 'false';
			}
			if(Validator.Validate(userTemplate, 2))
			{
				document.forms[0].submit();
			}
		}
		
		//嵌入到组织机构树中需要的方法Start
		function setSelect(p) {}
		function cancelSelect(p) {}
		function clearAllSelect() {}
		//嵌入到组织机构树中需要的方法End
	</script>
	</head>
	<body>
	 	<form name="userTemplate" action="${ctx}/userTemplate/userTemplateSave.action" method="post" onsubmit="return Validator.Validate(this, 2);">
			<input type="hidden" id="userTemplate.pid" name="userTemplate.pid" value="${userTemplate.pid}"/>
			<input type="hidden" id="userTemplate.uttype" name="userTemplate.uttype" value="1"/><!-- 目前写死 1代表工单 -->
			<input type="hidden" id="userTemplate.creater" name="userTemplate.creater" value="${userTemplate.creater}"/>
			<input type="hidden" id="userTemplate.createtime" name="userTemplate.createtime" value="${userTemplate.createtime}"/>
			<input type="hidden" id="typedata" name="userTemplate.typedata" value="${userTemplate.typedata}"/>
			<input type="hidden" id="userdata_id" name="userTemplate.userdata" value="${userTemplate.userdata}"/>
			<input type="hidden" id="depdata_id" name="userTemplate.depdata" value="${userTemplate.depdata}"/>
			<input type="hidden" id="usershare_id" name="userTemplate.usershare" value="${userTemplate.usershare}"/>
			<input type="hidden" id="depshare_id" name="userTemplate.depshare" value="${userTemplate.depshare}"/>
			<input type="hidden" id="uttype" name="uttype" value="${userTemplate.typedata}"/>
			<input type="hidden" id="dataid" name="dataid" value="${userTemplate.userdata}:${userTemplate.depdata}"/>
			<input type="hidden" id="shareid" name="shareid" value="${userTemplate.usershare}:${userTemplate.depshare}"/>
			<input type="hidden" id="userTemplate.isshare" name="userTemplate.isshare" value="${userTemplate.isshare}"/>
			<input type="hidden" id="userTemplate.remark" name="userTemplate.remark" value="${userTemplate.remark}"/>
			<input type="hidden" id="refreshArea" name="refreshArea" value="${refreshArea}"/>
			<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"><span class="title_icon2"><eoms:lable name='sm_lb_userTemplateInfo'/></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="page_div_bg">
				<div class="page_div">
					<eoms:operate cssclass="page_saves_button" id="page_saves_button" onmouseover="this.className='page_saves_button_hover'" onmouseout="this.className='page_saves_button'" onclick="formSubmit();" text="com_btn_save" operate="com_save"/>
					<eoms:operate cssclass="page_sameadd_button" id="page_sameadd_button" onmouseover="this.className='page_sameadd_button_hover'" onmouseout="this.className='page_sameadd_button'" onclick="selectTemplateData();" text="sm_lb_templateDataConfig" operate="com_add" />
	  	 		</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style" style="width:90%">
					<legend><eoms:lable name='sm_lb_userTemplateInfo'/></legend>
					<div class="blank_tr"></div>
					<table class="add_user">
						<tr>
							<td class="texttd" style="width: 35%">
								<eoms:lable name='sm_lb_templateName'/>：
							</td>
							<td style="width: 65%">
								<input type="text" id="userTemplate.templatename" name="userTemplate.templatename" class="textInput" value="${userTemplate.templatename}"/>
								<validation id="userTemplate.templatenameV" dataType="Limit" min="1" max="25" msg="<eoms:lable name='sm_msg_userTemplate'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd">
								<eoms:lable name="com_lb_status"/>：
							</td>
							<td>
								<eoms:select name="userTemplate.status" style="select" dataDicTypeCode="status"  value="${userTemplate.status}" isnull="false"/>
								<validation id="userTemplate.statusV" dataType="Require" msg="<eoms:lable name='sm_msg_statusConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd">
								<eoms:lable name="com_lb_orderNum"/>：
							</td>
							<td>
								<input type="text" name="userTemplate.ordernum" value="${userTemplate.ordernum }" class="textInput" />
								<validation id="userTemplate.ordernumV" dataType="Custom" regexp="^[0-9]{1,5}$" msg="<eoms:lable name='sm_msg_orderNumConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">
								<eoms:lable name="sm_lb_templateDataUser"/>：
							</td>
							<td>
								<textarea name="userdata_name" rows="5" style="width:96.8%" readonly>${userData}</textarea>
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">
								<eoms:lable name="sm_lb_templateDataDep"/>：
							</td>
							<td>
								<textarea name="depdata_name" rows="5" style="width:96.8%" readonly>${depData}</textarea>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
		</form>
	</body>
</html>

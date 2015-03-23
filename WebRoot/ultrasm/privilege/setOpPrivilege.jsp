<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_t_setOpPrivilege" /></title>
		<link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css" />
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
		<script type="text/javascript">
		window.onresize = function() {
			setCenter(0,80);
		}
		window.onload = function() {
			setCenter(0,80);
			var allowCfg = '${param.allowCfg}';
			if(allowCfg=='false')
			{
				$("input:not(#cancelButton)").attr("disabled","disabled");
				$("select").attr("disabled","disabled");
				$("textarea").attr("disabled","disabled");
			}
		}
		
		function operate(input_name,input_id,type,rpid)
		{
			if(type == '1' || type == '2' || type == '3' || type == '6')
				window.open('${ctx}/roleResOpManager/getValue.action?type='+type+'&rpid='+rpid+'&input_name='+input_name+'&input_id='+input_id,'','location=no,toolbar=no,resizable=yes,scrollbars=no,width=700,height=450,top=100,left=300');
			else if(type == '4')
				window.open('${ctx}/common/tools/depOrUserSelect.jsp?isRadio=1&isSelectType=1&input_name='+input_name+'&input_id='+input_id,'','location=no,toolbar=no,resizable=yes,scrollbars=no,width=287,height=315,top=100,left=300');
			else if(type == '5')
				window.open('${ctx}/common/tools/depOrUserSelect.jsp?isRadio=1&isSelectType=0&input_name='+input_name+'&input_id='+input_id,'','location=no,toolbar=no,resizable=yes,scrollbars=no,width=287,height=315,top=100,left=300');
		}
		
		function cleardata(input_name,input_id,input_operator)
		{
			document.getElementById(input_name).value = '';
			document.getElementById(input_id).value = '';
			document.getElementById(input_operator).value = '1';
		}
		
		function formSubmit()
		{
			var i = 1;
			var data = '';
			while(document.getElementById('id'+i) != null)
			{
				var id = document.getElementById('rpid'+i).value;//资源属性ID
				var value = document.getElementById('id'+i).value;//对应数据ID
				var text = '';//对应数据显示值
				var operator = document.getElementById('operator'+i).value;//对应操作符
				if(document.getElementById('text'+i) == null)
					text = value;
				else
					text = document.getElementById('text'+i).value;
				value = value.replace(' ','');
				if(operator == '')
					operator = '1';
				if(value != '')//拼装成固定格式传入后台进行解析保存
				{
					if(data != '')
						data += ';';
					data += id+':'+value+':'+text+':'+operator;
				}
				i++;
			}
			document.getElementById('rpdata').value = data;
			var i = 1;
			var opid = '';//所选择的本资源其他操作ID
			while(document.getElementById('checkbox'+i) != null)
			{
				if(document.getElementById('checkbox'+i).checked)
				{
					opid += ','+document.getElementById('opid'+i).value;
				}
				i++;
			}
			document.getElementById('opId').value += opid;//当前配置操作+本资源其他操作
			document.forms[0].submit();
		}
		</script>
	</head>
	<body>
		<form action="${ctx}/roleResOpManager/saveOpPrivilege.action" method="post" name="setPrivilegeForm">
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg">
						<span class="title_icon2"><eoms:lable name="sm_subt_cfgOpPrivilege" /></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div_x" id="center" style="text-align: center; margin: 0 auto;">
				<div class="blank_tr"></div>
				<fieldset class="fieldset_style">
					<legend>
						<!-- <eoms:lable name="com_lb_basicInfo"/> -->
						<eoms:lable name="sm_lb_roleResOpInfo" />
					</legend>
					<table class="tableborder">
						<tr>
							<td style="width: 11%"><b><eoms:lable name="com_lb_role" />：</b></td>
							<td style="width: 22%">${rroShow.rolename}</td>
							<td style="width: 11%"><b><eoms:lable name="com_lb_resource" />：</b></td>
							<td style="width: 22%">${rroShow.resname}</td>
							<td style="width: 11%"><b><eoms:lable name="sm_lb_operation" />：</b></td>
							<td style="width: 22%">${rroShow.opname}</td>
						</tr>
						<tr>
							<td colspan="6" style="color: blue">
								<b><eoms:lable name="sm_lb_resourceOtherOp" /></b>
							</td>
						</tr>
						<tr>
							<td colspan="6">
								<c:forEach items="${opList}" var="op" varStatus="opStatus">
									<input type="checkbox" name="cbx" id="checkbox${opStatus.count}" />${op.opname}
									<input type="hidden" name="opid${opStatus.count}" value="${op.pid}" />
								</c:forEach>
							</td>
						</tr>
					</table>
					<input type="hidden" name="rpdata" />
					<input type="hidden" name="roleId" value="${rroShow.roleid}" />
					<input type="hidden" name="resId" value="${rroShow.resid}" />
					<input type="hidden" name="opId" value="${rroShow.opid}" />
				</fieldset>
				<div class="blank_tr"></div>
				<fieldset class="fieldset_style">
					<legend>
						<!-- <eoms:lable name="com_lb_basicInfo"/> -->
						<eoms:lable name="sm_lb_cfgDataPrivilege" />
					</legend>
					<table class="tableborder">
						<tr>
							<th style="width: 20%"><eoms:lable name="sm_lb_textArea" /></th>
							<th style="width: 60%" colspan="2"><eoms:lable name="sm_lb_dataArea" /></th>
							<th style="width: 20%"><eoms:lable name="sm_lb_operateArea" /></th>
						</tr>
						<c:forEach items="${rpShowList}" var="rp" varStatus="rpStatus">
							<tr>
								<td style="width: 20%">
									${rp.fielddisplayvalue}：
									<input type="hidden" name="rpid${rpStatus.count}" value="${rp.pid}" />
								</td>
								<td style="width: 15%">
									<eoms:select name="operator${rpStatus.count}" style="select" dataDicTypeCode="operator" value="${rp.operator}" isnull="false"/>
								</td>
								<c:choose>
									<c:when test="${rp.intype=='1'}">
										<td style="width: 45%">
											<input type="text" name="id${rpStatus.count}" value="${rp.value}" style="width: 100%" class="textInput" />
										</td>
										<td style="width: 20%"></td>
									</c:when>
									<c:when test="${rp.intype=='2'}">
										<td style="width: 45%">
											<input type="text" name="text${rpStatus.count}" id="text${rpStatus.count}" value="${rp.listvalue}" style="width: 100%" class="textInput" readonly />
											<input type="hidden" name="id${rpStatus.count}" id="id${rpStatus.count}" value="${rp.value}" />
										</td>
										<td style="width: 20%" align="center">
											<input type="button" name="button3" id="button3"
												value="<eoms:lable name="com_btn_choose"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'"
												onclick="operate('text${rpStatus.count}','id${rpStatus.count}','${rp.indatasourtype}','${rp.pid}');" />
											<input type="button" name="button3" id="button3"
												value="<eoms:lable name="com_btn_clear"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'"
												onclick="cleardata('text${rpStatus.count}','id${rpStatus.count}','operator${rpStatus.count}');" />
										</td>
									</c:when>
								</c:choose>
							</tr>
						</c:forEach>
						<tr>
							<td style="width: 20%"><eoms:lable name="sm_lb_SQLSet" />：</td>
							<td style="width: 60%" colspan="2"><textarea rows="4" class="textInput" style="width: 100%" name="sqlCon">${sqlCon}</textarea></td>
							<td style="width: 20%"></td>
						</tr>
					</table>
				</fieldset>
			</div>
			<div class="add_bottom">
				<c:if test="${param.allowCfg=='true'}">
					<input type="button" value="<eoms:lable name="com_btn_save"/>"
						class="save_button"
						onmouseover="this.className='save_button_hover'"
						onmouseout="this.className='save_button'" onclick="formSubmit();" />
				</c:if>
				<input type="button" value="<eoms:lable name='com_btn_cancel'/>"
					class="cancel_button"
					id="cancelButton"
					onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					onclick="window.close();" />
			</div>
		</div>
		</form>
	</body>
</html>

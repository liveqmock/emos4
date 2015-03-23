<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base target="_self" />
	<%@ include file="/common/core/taglibs.jsp"%>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<script language="javascript">
	window.onresize = function() {
		setCenter(0, LAYOUT_ZERO);
	}
	window.onload = function() {
		setCenter(0, LAYOUT_ZERO);changeRow_color("tab");
	}
	function batchSynchUp(systemMark, dataType) {
		var url = '';
		if(systemMark == 'PASM') {
			if(dataType == 'USER') {
				url = '${ctx}/batchSynchData/patchSynchUserFromPasm.action';
			} else if(dataType == 'GROUP') {
				url = '${ctx}/batchSynchData/patchSynchGroupFromPasm.action';
			} else {
				url = '${ctx}/batchSynchData/patchSynchFromPasm.action';
			}
		}
		if(url != '') {
			btnStatusUpdate('disabled');
			$.get(url, '', function () {
				alert('同步完毕！');
				btnStatusUpdate('');
			});
		}
	}
	function synchToPasm(type)
		{
			var url = '';
			if(type == '')
				return;
			else if(type == 'user')
				{
				url = "${ctx}/dataSynch/synchToPasm_User.action";
				type="用户";
				}
			else if(type == 'dep')
				{
				url = "${ctx}/dataSynch/synchToPasm_Dep.action";
				type="部门";
				}
			else if(type == 'userdep')
			{
				url = "${ctx}/dataSynch/synchToPasm_UserDep.action";
				type="关系";
			}
			else if(type == 'role')
			{
				url = "${ctx}/dataSynch/synchToPasm_Role.action";
				type="角色";
			}	
		if(url != '') {
			btnStatusUpdate('disabled');
			$.get(url, '', function () {
				alert('同步'+type+'完毕！');
				btnStatusUpdate('');
			});
		}
	}
		
	function btnStatusUpdate(type) {
		var i = 1;
		while (true) {
			if(document.getElementById('btn'+i)) {
				document.getElementById('btn'+i).disabled = type;
				i++;
			} else {
				break;
			}
		}
	}
</script>
</head>
<body>
	<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg"> <span class="title_icon2">当前位置：自管理>>批量同步列表</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div class='scroll_div' id='center'>
			<table id='tab' class='tableborder'>
				<!-- 标题描述区_Start -->
				<tr>
					<th width="30px">序号</th>
					<th>描述区</th>
					<th width="300px">操作区</th>
				</tr>
				<!-- 标题描述区_End -->
				<tr>
					<td>1</td>
					<td>将PASM数据信息批量同步到EOMS</td>
					<td>
						<input type="button" value="第一步：同步用户" id="btn2" name="btn2" class="button" onclick="batchSynchUp('PASM', 'USER');"/>
						<input type="button" value="第二步：同步部门" id="btn3" name="btn3" class="button" onclick="batchSynchUp('PASM', 'GROUP');"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>

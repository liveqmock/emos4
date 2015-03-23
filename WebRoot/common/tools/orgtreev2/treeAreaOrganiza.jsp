<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ultrapower.eoms.common.core.util.StringUtils"%>
<%@ page import="java.net.URLEncoder"%>
<%
	String rearchData = StringUtils.checkNullString(request.getParameter("rearchData"));//查询条件值
	String resultData = StringUtils.checkNullString(request.getParameter("resultData"));//列表数据
	String findType = StringUtils.checkNullString(request.getParameter("findType"));
	String selectId = StringUtils.checkNullString(request.getParameter("selectId")); //默认选中值
	String sltType = StringUtils.checkNullString(request.getParameter("sltType"));
	String sltData = StringUtils.checkNullString(request.getParameter("sltData"));
	String isFirst = StringUtils.checkNullString(request.getParameter("isFirst"));
	if("".equals(isFirst)) {
		selectId = sltData;
		isFirst = "1";
	}
	if ("".equals(rearchData)) {
		if("0".equals(findType)) {
			rearchData = "快速查找......";
		}
	}
%>
<html>
<head>
	<title>部门树</title>
	<%@ include file="/common/core/header_form.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/javascript/datagrid.js"  defer="defer"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript" src="${ctx}/common/tools/orgtreev2/js/organizaTree.js"></script>
	<script type="text/javascript" src="${ctx}/common/tools/orgtreev2/js/organizaTreeAdvanced.js"></script>
	<script type="text/javascript" defer="defer">
		var sltType = '<%=sltType%>';
		var findType = '<%=findType%>';
		var returnData = '';
		function rearchUserOrDep()
		{
			document.getElementById('selectId').value = bottomFrame.contentWindow.getDataFromResult('id', '0');
			document.datatree.submit();
		}
		function check()
		{
			document.getElementById('selectId').value = bottomFrame.contentWindow.getDataFromResult('id', '0');
		}
		window.onload = function()
		{
			var findType = '<%=findType%>';
			if(findType == '1') {
				var type = 'user';
				if(sltType == '0') {
					document.getElementById('btnUser').disabled = true;
					type = 'group';
				} else if(sltType == '1') {
					document.getElementById('btnGroup').disabled = true;
				}
				searchData(type);
				initParam();
			}
		}
	</script>
</head>
<body>
	<div class="content">
		<%
		if("0".equals(findType))
		{
		%>
		<form action="" method="post" name="datatree" onsubmit="check();">
			<div class="configroletree">
				<li>
					<input type="hidden" name="selectId" id="selectId" value="<%=selectId%>"/>
					<input type="hidden" name="isFirst" id="isFirst" value="<%=isFirst%>"/>
					<input type="text" name="rearchData" id="rearchData" maxlength="20" style="width: 260px"
						value="<%=rearchData%>" onfocus="if (value =='快速查找......'){value =''}" onblur="if (value ==''){value='快速查找......'}">
				</li>
				<li>
					<div class="ser1" onmouseover="this.className='ser2'" onmouseout="this.className='ser1'" onclick="rearchUserOrDep();"></div>
				</li>
			</div>
		</form>
		<div id="treeboxbox_tree" style="clear: both; background: #ffffff; border: 1px #d2e5fe solid; border-top: none; height:300px"></div>
		<%
		}
		else if("1".equals(findType))
		{
		%>
		<div id="treeboxbox_tree" style="clear: both; background: #ffffff; border: 1px #d2e5fe solid; border-top: none; float: left; height:320px; width:290px"></div>
		<form action="" method="post" name="datatree" onsubmit="check();">
			<div id="content">
				<div class='page_div_bg'>
					<input type="hidden" name="selectId" id="selectId"/>
					<input type="hidden" name="isFirst" id="isFirst" value="<%=isFirst%>"/>
					<input type="hidden" id="rearchData" name="rearchData" value="<%=rearchData%>"/>
					<input type="hidden" id="resultData" name="resultData" value="<%=resultData%>"/>
					<input type="button" value="搜索" class="button" onclick="showsearch();"/>
					<input type="button" value="用户" class="button" id="btnUser" onclick="searchData('user');"/>
					<input type="button" value="部门" class="button" id="btnGroup" onclick="searchData('group');"/>
				</div>
				<div id='serachdiv' class='serachdiv' style='display:none;'>
					<div class='type_condition'><span>查询内容</span></div>
					<table id="userTable">
						<tr>
							<td width="25%">姓名：</td>
							<td width="50%"><input type="text" id="username" name="username" onkeypress="enterSearch(event, 'user');"/></td>
							<td width="25%"></td>
						</tr>
						<tr>
							<td>登录名：</td>
							<td><input type="text" id="loginname" name="loginname" onkeypress="enterSearch(event, 'user');"/></td>
							<td></td>
						</tr>
						<tr>
							<td>手机号：</td>
							<td><input type="text" id="mobile" name="mobile" onkeypress="enterSearch(event, 'user');"/></td>
							<td></td>
						</tr>
						<tr>
							<td>职位：</td>
							<td><input type="text" id="position" name="position" onkeypress="enterSearch(event, 'user');"/></td>
							<td></td>
						</tr>
						<tr>
							<td>展现方式：</td>
							<td>
								<input type="radio" name="userdisptype" checked value="list"/>列表
								<input type="radio" name="userdisptype" value="tree"/>树形
							</td>
							<td><input type="button" class="button" value="查询" onclick="findInfo('user');"/></td>
						</tr>
						<tr>
							<td colspan="3" style="color:red">查询多个用","隔开</td>
						</tr>
					</table>
					<table id="groupTable">
						<tr>
							<td width="25%">二级部门：</td>
							<td width="50%"><input type="text" id="company" name="company" onkeypress="enterSearch(event, 'group');"/></td>
							<td width="25%"></td>
						</tr>
						<tr>
							<td>三级部门：</td>
							<td><input type="text" id="dept" name="dept" onkeypress="enterSearch(event, 'group');"/></td>
							<td></td>
						</tr>
						<tr>
							<td>组：</td>
							<td><input type="text" id="group" name="group" onkeypress="enterSearch(event, 'group');"/></td>
							<td></td>
						</tr>
						<tr>
							<td>展现方式：</td>
							<td>
								<input type="radio" name="groupdisptype" checked value="list"/>列表
								<input type="radio" name="groupdisptype" value="tree"/>树形
							</td>
							<td><input type="button" class="button" value="查询" onclick="findInfo('group');"/></td>
						</tr>
						<tr>
							<td colspan="3" style="color:red">查询多个用","隔开</td>
						</tr>
					</table>
				</div>
				<div style="overflow-y:auto;overflow-x:hidden;height:290px;">
					<table id="userDataTable" class='tableborder'>
						<tr>
							<th width="26px">选择</th>
							<th width="30%">用户名称</th>
							<th>部门名称</th>
						</tr>
					</table>
					<table id="groupDataTable" class='tableborder' style="display:none">
						<tr>
							<th width="26px">选择</th>
							<th width="35%">部门名称</th>
							<th width="65%">部门全名</th>
						</tr>
					</table>
				</div>
			</div>
		</form>
		<%
		}
		%>
		<script type="text/javascript">
		//部门组织树
		tree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
		tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
		tree.enableCheckBoxes(true);//1-带选择框的模式 (非1)-不带选择框的模式URLDecoder.decode(menuName,"UTF-8");
		tree.enableTreeLines(true);//是否显示结点间的连线,默认false
		tree.setXMLAutoLoading("${ctx}/organizaTreeV2/getOrganizaData.action?selectId=<%=selectId%>&sltType=<%=sltType%>");
		tree.loadXML("${ctx}/organizaTreeV2/getOrganizaData.action?id=0&selectId=<%=selectId%>&sltType=<%=sltType%>&findType=<%=findType%>&rearchData=<%=URLEncoder.encode(URLEncoder.encode(rearchData.equals("快速查找......") ? "" : rearchData,"UTF-8"), "utf-8")%>");
		tree.onCheckBoxClick = function (e) {
			if (!this.treeNod.callEvent("onBeforeCheck", [this.parentObject.id, this.parentObject.checkstate])) {
				return;
			}
			if (this.parentObject.dscheck) {
				return true;
			}
			if (this.treeNod.tscheck) {
				if (this.parentObject.checkstate == 1) {
					this.treeNod._setSubChecked(false, this.parentObject);
				} else {
					this.treeNod._setSubChecked(true, this.parentObject);
				}
			} else {
				if (this.parentObject.checkstate == 1) {
					var type = tree.getUserData(this.parentObject.id, "type");
					bottomFrame.contentWindow.delOneResult(this.parentObject.id, type);
					if(type == 'U' && rightFrame != null) {
						rightFrame.contentWindow.cancelSelect(this.parentObject.id);
					}
					this.treeNod._setCheck(this.parentObject, false);
				} else {
					var type = tree.getUserData(this.parentObject.id, "type");
					bottomFrame.contentWindow.addOneResult(this.parentObject.id, tree.getUserData(this.parentObject.id, "text"), type);
					if(type == 'U' && rightFrame != null) {
						rightFrame.contentWindow.setSelect(this.parentObject.id);
					}
					this.treeNod._setCheck(this.parentObject, true);
				}
			}
			this.treeNod._correctCheckStates(this.parentObject.parentObject);
			return this.treeNod.callEvent("onCheck", [this.parentObject.id, this.parentObject.checkstate]);
		};
		function setSelect(id, type) {
			if(tree.getUserData(id, 'type') == type) {
				tree.setCheck(id, '1');
			}
		}
		function cancelSelect(id, type) {
			if(tree.getUserData(id, 'type') == type) {
				tree.setCheck(id, '2');
			}
		}
		function clearAllSelect() {
			var ids = tree.getAllChecked();
			var idArray = ids.split(',');
			for(var i=0 ; i<idArray.length ; i++) {
				tree.setCheck(idArray[i], '2');
			}
		}
		function setSelectData() {
			var selectId = bottomFrame.contentWindow.getDataFromResult('id');
			var SelectData = selectId.split(':');
			var userId = SelectData[0];
			var depId = SelectData[1];
			if(userId != '') {
				var userArray = userId.split(',');
				for(var i=0 ; i<userArray.length ; i++) {
					type = tree.getUserData(userArray[i], 'type');
					if(type == 'U') {
						tree.setCheck(userArray[i], '1');
					}
				}
			}
			if(depId != '') {
				var depArray = depId.split(',');
				for(var i=0 ; i<depArray.length ; i++) {
					type = tree.getUserData(depArray[i], 'type');
					if(type == 'D') {
						tree.setCheck(depArray[i], '1');
					}
				}
			}
		}
		function refresh(id) {
			tree.refreshItem(id);
		}
	</script>
	</div>
</body>
</html>

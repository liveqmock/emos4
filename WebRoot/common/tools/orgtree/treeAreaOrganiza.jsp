<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.ultrapower.eoms.common.core.util.StringUtils"%>
<%
	String isRadio = request.getParameter("isRadio");//0:单选  1:多选 
	String rearchUserOrDep = request.getParameter("reachUserOrDep");//查询条件值
	String isSelectType = StringUtils.checkNullString(request.getParameter("isSelectType"));
	String selectId = StringUtils.checkNullString(request.getParameter("selectId")); //默认选中值
	String depids = StringUtils.checkNullString(request.getParameter("depids")); //部门id 若不为空 则只展示传进来的depids节点 及以下节点
	if("null".equals(depids))
		depids = "";
	String targetDataArr = StringUtils.checkNullString(request.getParameter("targetDataArr"));
	String isFirst = StringUtils.checkNullString(request.getParameter("isFirst"));
	if("".equals(isFirst))
	{
		selectId = targetDataArr;
		isFirst = "1";
	}
	String treeType = StringUtils.checkNullString(request.getParameter("treeType"));
	if (rearchUserOrDep == null)
		rearchUserOrDep = "快速查找......";
%>
<html>
<head>
	<title>部门树</title>
	<%@ include file="/common/core/header_form.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript" src="${ctx}/common/tools/orgtree/js/organizaTree.js"></script>
	<script type="text/javascript" defer="defer">
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
			//document.getElementById('treeboxbox_tree').style.height = document.body.clientHeight - 22;
		}
	</script>
</head>
<body>
	<div class="content">
		<form action="" method="post" name="datatree" onsubmit="check();">
			<div class="configroletree">
				<li>
					<input type="hidden" name="selectId" id="selectId"/>
					<input type="hidden" name="isFirst" id="isFirst" value="<%=isFirst%>"/>
					<input type="text" name="reachUserOrDep" id="reachUserOrDep" maxlength="20" style="width: 260px"
						value="<%=rearchUserOrDep%>" onfocus="if (value =='快速查找......'){value =''}" onblur="if (value ==''){value='快速查找......'}">
				</li>
				<li>
					<div class="ser1" onmouseover="this.className='ser2'" onmouseout="this.className='ser1'" onclick="rearchUserOrDep();"></div>
				</li>
			</div>
		</form>
		<div id="treeboxbox_tree" style="clear: both; background: #ffffff; border: 1px #d2e5fe solid; border-top: none; height:300px"></div>
		<div id="titleDiv" style="position:absolute;border: 1px solid #999999;z-index: 100;background:#F0FFFF;height:20px;width:auto;display:none"></div>
		<script type="text/javascript">
		//部门组织树
		tree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
		tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
		var isRadioPara = '<%=isRadio%>';
		/*
		if(isRadioPara == '0') //单选
		{
			tree.enableRadioButtons(true);
			tree.enableSingleRadioMode(true);
		}
		else //复选
		{
			tree.enableCheckBoxes(true);//1-带选择框的模式 (非1)-不带选择框的模式URLDecoder.decode(menuName,"UTF-8");	
		}
		*/
		tree.enableCheckBoxes(true);//1-带选择框的模式 (非1)-不带选择框的模式URLDecoder.decode(menuName,"UTF-8");
		tree.enableTreeLines(true);//是否显示结点间的连线,默认false
		tree.setXMLAutoLoading("${ctx}/organizaTree/getCommonTreeData.action?useType=myself&treeType=<%=treeType%>&selectId=<%=selectId%>&isSelectType=<%=isSelectType%>&depids=<%=depids%>");
		tree.loadXML("${ctx}/organizaTree/getCommonTreeData.action?id=0&useType=myself&treeType=<%=treeType%>&selectId=<%=selectId%>&isSelectType=<%=isSelectType%>&depids=<%=depids%>&rearchUserOrDep=<%=URLEncoder.encode(URLEncoder.encode(rearchUserOrDep.equals("快速查找......") ? "" : rearchUserOrDep,"UTF-8"), "utf-8")%>");
		tree.setOnClickHandler(doHandler);
		function doHandler(key)
		{
			var id = tree.getSelectedItemId();
			var op = tree.getUserData(id, "op");
			if('<%=depids%>' != '' && op == 'false')
				return 0;
			if('<%=isSelectType%>' == '0')
				return 0;
    		if(tree.getUserData(id, "parentid") != '0')
    		{
		    	var tmp = id.split('_');
		    	if(tmp.length >= 2)
		    	{
		    		id = tmp[1];
		    		var type = tmp[0];
		    		if(type == 'T')
		    		{
						window.open("${ctx}/userTemplate/userTemplateInfoSimple.action?utid=" + id, "rightTreeFrame");
		    		}
			    	else if(type != 'U') // if(type == 'D' || type == 'P' || type == 'R')
			    	{
			    		if('<%=treeType%>' != 'UserTemplate')
			    		{
			    			var selectId = bottomFrame.contentWindow.getDataFromResult('U_id', '0');
							window.open("${ctx}/organizaTree/commonUserArea.action?returnPage=userArea&treeType=<%=treeType%>&id="+id+"&selectId="+selectId, "rightTreeFrame");
						}
		    		}
		    	}
	    	}
		}
		tree.setOnMouseInHandler(showDetail);
		tree.setOnMouseOutHandler(hideDetail);
		function showDetail(key) {
			var type = tree.getUserData(key, "type");
			if(type != 'U') {
				return false;
			}
			var depname = tree.getUserData(key, "depname");
			var loginname = tree.getUserData(key, "loginname");
			var title = '';
			if(loginname != undefined && loginname != '') {
				title = '登录名：' + loginname;
			}
			if(depname != undefined && depname != '') {
				if(title != '') {
					title = title + '<br/>';
				}
				title = title + '部门名：' + depname;
			}
	    	if(title != '') {
	    		var tdiv = document.getElementById('titleDiv');
	    		if(tdiv) {
	    			tdiv.style.display = '';
	    			var mouse = mousePos();
	    			tdiv.innerHTML = title;
	    			tdiv.style.left = (mouse.x + 10) + 'px';
	    			tdiv.style.top = mouse.y + 'px';
	    		}
	    	}
		}
		function hideDetail() {
			var tdiv = document.getElementById('titleDiv');
			if(tdiv) {
				tdiv.style.display = 'none';
			}
		}
		tree.onCheckBoxClick = function (e)
		{
			if (!this.treeNod.callEvent("onBeforeCheck", [this.parentObject.id, this.parentObject.checkstate]))
			{
				return;
			}
			if (this.parentObject.dscheck)
			{
				return true;
			}
			if (this.treeNod.tscheck) 
			{
				if (this.parentObject.checkstate == 1) 
				{
					this.treeNod._setSubChecked(false, this.parentObject);
				}
				else 
				{
					this.treeNod._setSubChecked(true, this.parentObject);
				}
			}
			else 
			{
				if (this.parentObject.checkstate == 1)
				{
					var type = tree.getUserData(this.parentObject.id, "type");
					bottomFrame.contentWindow.delOneResult(this.parentObject.id, type);
					if(type == 'U' && rightFrame != null)
						rightFrame.contentWindow.cancelSelect(this.parentObject.id);
					this.treeNod._setCheck(this.parentObject, false);
				}
				else 
				{
					var type = tree.getUserData(this.parentObject.id, "type");
					bottomFrame.contentWindow.addOneResult(this.parentObject.id, tree.getUserData(this.parentObject.id, "text"), type);
					if(type == 'U' && rightFrame != null)
						rightFrame.contentWindow.setSelect(this.parentObject.id);
					this.treeNod._setCheck(this.parentObject, true);
				}
			}
			this.treeNod._correctCheckStates(this.parentObject.parentObject);
			return this.treeNod.callEvent("onCheck", [this.parentObject.id, this.parentObject.checkstate]);
		};
		function setSelect(id, type)
		{
			if(tree.getUserData(id, 'type') == type)
				tree.setCheck(id, '1');
		}
		function cancelSelect(id, type)
		{
			if(tree.getUserData(id, 'type') == type)
				tree.setCheck(id, '2');
		}
		function clearAllSelect()
		{
			var ids = tree.getAllChecked();
			var idArray = ids.split(',');
			for(var i=0 ; i<idArray.length ; i++)
			{
				tree.setCheck(idArray[i], '2');
			}
		}
		function setSelectData()
		{
			var selectId = bottomFrame.contentWindow.getDataFromResult('id');
			var SelectData = selectId.split(':');
			var userId = SelectData[0];
			var depId = SelectData[1];
			if(userId != '')
			{
				var userArray = userId.split(',');
				for(var i=0 ; i<userArray.length ; i++)
				{
					type = tree.getUserData(userArray[i], 'type');
					if(type == 'U')
						tree.setCheck(userArray[i], '1');
				}
			}
			if(depId != '')
			{
				var depArray = depId.split(',');
				for(var i=0 ; i<depArray.length ; i++)
				{
					type = tree.getUserData(depArray[i], 'type');
					if(type == 'D')
						tree.setCheck(depArray[i], '1');
				}
			}
		}
		function refresh(id)
		{
			tree.refreshItem(id);
		}
	</script>
	</div>
</body>
</html>

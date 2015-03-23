<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ultrapower.eoms.common.core.util.StringUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%
	//调用粘贴：showModalDialog('${ctx}/common/tools/orgtree/commonTreeFrame.jsp?input_data=&targetDataArr=',window,'help:no;center:true;scroll:no;status:no;dialogWidth:600px;dialogHeight:540px');
	//使用建议：窗口大小请设置为 width：600px height：540px 当useMode=frame时 请设置width：600px height：510px
	//useMode参数说明：使用模式，如果只用组织机构树选择，则不用传递此参数，若要嵌入到页面中的IFRAME里，则需要把此参数设置为：frame即可
	String useMode = StringUtils.checkNullString(request.getParameter("useMode"));
	//调用可直接粘贴下面一句，将用到参数进行赋值即可。只有使用showModalDialog调用时才需要传递这些参数input_data
	String input_data = StringUtils.checkNullString(request.getParameter("input_data"));
	//dataFormat 
	String dataFormat = StringUtils.checkNullString(request.getParameter("dataFormat"));
	String resolveT = StringUtils.checkNullString(request.getParameter("resolveT"));
	String step_no = StringUtils.checkNullString(request.getParameter("step"));
	String action_sign = StringUtils.checkNullString(request.getParameter("act"));
	String fieldID = StringUtils.checkNullString(request.getParameter("field"));
	if("".equals(resolveT))
		resolveT = "true";
	String depids = StringUtils.checkNullString(request.getParameter("depids"));
	String idtype = StringUtils.checkNullString(request.getParameter("idtype"));
	if("".equals(idtype))
		idtype = "2";
	//treeType参数说明：组织机构：Organiza 角色细分：RoleChild 人员模版：UserTemplate 职位：Position （区分大小写字母，请不要传递错误）
	//String treeType = StringUtils.checkNullString(request.getParameter("treeType"));
	//preventTree参数：设置不显示的选项标签 格式：RoleChild;Position  代表不展示角色细分和职位两个标签  注意：Organiza是这个树默认的标签，是设置不掉的。
	String preventTree = StringUtils.checkNullString(request.getParameter("preventTree"));
	String wfVersion = StringUtils.checkNullString(request.getParameter("wfVersion"));
	
	String utType = StringUtils.checkNullString(request.getParameter("utType")); // 人员模版类别
	String typeMark = StringUtils.checkNullString(request.getParameter("typeMark")); // 模版类别标识 如果模版类别为工单 则typeMark是工单类别
	
	//工单派发审批环节使用的参数_start
	String isApprove = StringUtils.checkNullString(request.getParameter("isApprove"));
	String companyId = StringUtils.checkNullString(request.getParameter("companyId"));
	String phaseno = StringUtils.checkNullString(request.getParameter("phaseno"));
	String approve = isApprove + ";" + typeMark + ";" + companyId + ";" + phaseno;
	boolean isShow = "true".equals(isApprove) ? false : true;
	//工单派发审批环节使用的参数_end
	
	//isRadio参数说明：0：单选 1：多选
	String isRadio = StringUtils.checkNullString(request.getParameter("isRadio"));
	if(!"0".equals(isRadio))
		isRadio = "1";
	//isSelectType参数说明：0：部门 1：人员 2：人和部门（默认范围）
	String isSelectType = StringUtils.checkNullString(request.getParameter("isSelectType"));
	//targetDataArr参数说明：默认选中值
	String targetDataArr = StringUtils.checkNullString(request.getParameter("targetDataArr"));
%>
<head>
	<%@ include file="/common/core/header_form.jsp"%>
	<title>组织人员树</title>
	<style>
		.tab_bg {
			background:url(${ctx}/common/style/blue/images/tab_bg.png);
			height:24px;
			padding-left:3px;
			padding-top:3px;
		}
	</style>
	<script language="javascript">
		window.onload = function()
		{
			document.getElementById('commonResultArea').contentWindow.initDataResult('<%=targetDataArr%>', '<%=idtype%>');
			getPageMenu('Organiza', 'treeFrame_Organiza');
			PageMenuActive('Organiza', 'treeFrame_Organiza');
		}
		/**
		 * dataType：element、group
		 * element:以元素为单位 U:id1,name1;U:id2,name2;...;D:id1,name1;D:id2,name2;...;T:id1,name1;T:id2,name2;...;R:id1,name1;R:id2,name2;...
		 * group:以组为单位 U:id1,id2,...:name1,name2,...;D:id1,id2,...:name1,name2,...;T:id1,id2,...:name1,name2,...;R:id1,id2,...:name1,name2,...
		 * dataFormat:返回数据类型（当dataType=group时有效）
		 * idtype:获取用户的时候 0:取id-loginName 1:取loginName 2:取ID（非0,1）
		 */
		function getSelectData(dataType, dataFormat, idtype)
		{
			var selectData = '';
			if(dataType == 'element')
				selectData = document.getElementById('commonResultArea').contentWindow.getResolveData(idtype);
			else
				selectData = document.getElementById('commonResultArea').contentWindow.getDataFromResult(dataFormat, idtype);
			return selectData;
		}
		function setSelectData(resolveT, dataFormat, idtype)
		{
			var hasparam = 'false';
			<% 
			if(!"".equals(input_data))
			{
			%>
				var callerWindowObj = dialogArguments;
				hasparam = 'true';
				if(resolveT == 'true')
				{
					var idStr = getSelectData('group', 'id', idtype);
					if(idStr == '')
					{
						window.returnValue = 'true';
						window.close();
						return ;
					}
					$.get("${ctx}/userTemplate/getMergerData.action", {idStr:idStr,idType:idtype}, function (dataInfos)
					{
						//dataInfos：U:id1:name1;U:id2:name2;D:id1:name1;D:id2:name2;R:id1:name1;R:id2:name2...
						callerWindowObj.document.getElementById('<%=input_data%>').value = dataInfos;
						window.returnValue = 'true';
						window.close();
					});
				}
				else
				{
					var data = document.getElementById('commonResultArea').contentWindow.getDataFromResult(dataFormat, idtype);;
					if(data == '')
					{
						window.close();
						return;
					}
					callerWindowObj.document.getElementById('<%=input_data%>').value = data;
					window.returnValue = 'true';
					window.close();
				}
			<%
			}
			%>
			if(hasparam == 'false')
			{
				alert('传入的参数（input_data）有错误！');
				window.close();
			}
		}
		var initType = 'Organiza';
		function setPageMenu(type)
		{
			PageMenuActive(type, 'treeFrame_' + type);
			if(initType.indexOf(type) < 0)
			{
				initType += ';' + type;
				// var selectData = document.getElementById('commonResultArea').contentWindow.getDataFromResult('');
				var src = '${ctx}/common/tools/orgtree/commonTreeFrameSpace.jsp?treeType='+type+'&isRadio=<%=isRadio%>&isSelectType=<%=isSelectType%>&utType=<%=utType%>&typeMark=<%=typeMark%>&preventTree=<%=preventTree%>&wfVersion=<%=wfVersion%>&targetDataArr=<%=targetDataArr%>';
				if(type=='Config')
					src += '&act=<%=action_sign%>&step=<%=step_no%>&field=<%=fieldID%>';
				document.getElementById('frame' + type).src = src;
				document.getElementById('frame' + type).height="325px";
			}
		}
	</script>
</head>
<body>
	<div class="content">
		<div class="tab_bg">
		<%	//if(preventTree.indexOf("Organiza") < 0) {
			if(true) {
		%>
			<div class="tab_show" id="Organiza" onclick="setPageMenu('Organiza')">
				<span>组织机构</span>
			</div>
		<%
			} if(isShow && preventTree.indexOf("UserTemplate") < 0) {
		%><%--
			<div class="tab_hide" id="UserTemplate" onclick="setPageMenu('UserTemplate')">
				<span>人员模版</span>
			</div>
			 --%>
		<%
			} if(isShow && preventTree.indexOf("Position") < 0) {
		%><%--
			<div class="tab_hide" id="Position" onclick="setPageMenu('Position')">
				<span>职位</span>
			</div> --%>
		<%
			} if(isShow && preventTree.indexOf("RoleChild") < 0) {
		%>
		<%--
			<div class="tab_hide" id="RoleChild" onclick="setPageMenu('RoleChild')">
				<span>角色细分</span>
			</div>
			 --%>
		<%
			}if(isShow && preventTree.indexOf("Config") < 0) {
		%>
		<%--
			<div class="tab_hide" id="Config" onclick="setPageMenu('Config')">
				<span>自定义人/组</span>
			</div>
			 --%>
		<%
			}
		%>
		</div>
		<div class="tabContent">
			<div id="treeFrame_Organiza">
				<iframe src="${ctx}/common/tools/orgtree/commonTreeFrameSpace.jsp?treeType=Organiza&isRadio=<%=isRadio%>&isSelectType=<%=isSelectType%>&approve=<%=approve%>&preventTree=<%=preventTree%>&depids=<%=depids%>&targetDataArr=<%=targetDataArr%>" frameborder="0" id="frameOrganiza" name="frameOrganiza" height="325px" width="100%"></iframe>
			</div>
			<div id="treeFrame_UserTemplate">
				<iframe src="" frameborder="0" id="frameUserTemplate" name="frameUserTemplate" height="0" width="100%"></iframe>
			</div>
			<div id="treeFrame_Position">
				<iframe src="" frameborder="0" id="framePosition" name="framePosition" height="0" width="100%"></iframe>
			</div>
			<div id="treeFrame_RoleChild">
				<iframe src="" frameborder="0" id="frameRoleChild" name="frameRoleChild" height="0" width="100%"></iframe>
			</div>
			<div id="treeFrame_Config">
				<iframe src="" frameborder="0" id="frameConfig" name="frameConfig" height="0" width="100%"></iframe>
			</div>
		</div>
		<iframe src="${ctx}/common/tools/orgtree/commonResultArea.jsp?isRadio=<%=isRadio%>&utType=<%=utType%>&typeMark=<%=typeMark%>" frameborder="0" id="commonResultArea" name="commonResultArea" height="100px" width="100%"></iframe>
		<%
		if("".equals(useMode))
		{
		%>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button"
				onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'"
				onclick="setSelectData('<%=resolveT%>', '<%=dataFormat%>', '<%=idtype%>');" />
			<input type="button" value="<eoms:lable name='com_btn_cancel'/>" class="cancel_button"
				onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
		<%
		}
		%>
	</div>
</body>
</html>
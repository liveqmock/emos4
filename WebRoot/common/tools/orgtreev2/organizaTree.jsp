<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ultrapower.eoms.common.core.util.StringUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<%
		//调用粘贴：showModalDialog('${ctx}/common/tools/orgtreev2/organizaTree.jsp?sltType=&form_name=&user_name=&user_id=&dep_name=&dep_id=&sltData=',window,'help:no;center:true;scroll:no;status:no;dialogWidth:290px;dialogHeight:490px');
		//使用建议：窗口大小请设置为 width：290px height：490px 当useMode=frame时 请设置width：290px height：430px
		//调用可直接粘贴下面一句，将用到参数进行赋值即可。只有使用showModalDialog调用时才需要传递这些参数form_name、user_name、user_id、dep_name、dep_id
		//form_name(必填)，对于后面的name和id只需要传递自己要的参数，如只选人则dep_name、dep_id设置为空即可
		String form_name = StringUtils.checkNullString(request.getParameter("form_name"));
		String user_name = StringUtils.checkNullString(request.getParameter("user_name"));
		String user_id = StringUtils.checkNullString(request.getParameter("user_id"));
		String dep_name = StringUtils.checkNullString(request.getParameter("dep_name"));
		String dep_id = StringUtils.checkNullString(request.getParameter("dep_id"));
		//System.out.println("form_name:" + form_name + "====user_name:" + user_name + "====user_id:" + user_id + "====dep_name:" + dep_name + "====dep_id:" + dep_id);
		//useMode参数说明：使用模式，如果只用组织机构树选择，则不用传递此参数，若要嵌入到页面中的IFRAME里，则需要把此参数设置为：frame即可
		String useMode = StringUtils.checkNullString(request.getParameter("useMode"));
		String idtype = StringUtils.checkNullString(request.getParameter("idtype"));
		if("".equals(idtype))
			idtype = "2";
		//isRadio参数说明：0：单选 1：多选
		String isRadio = StringUtils.checkNullString(request.getParameter("isRadio"));
		if(!"0".equals(isRadio))
			isRadio = "1";
		//sltType参数说明：0：部门 1：人员 2：人和部门（默认范围）
		String sltType = StringUtils.checkNullString(request.getParameter("sltType"));
		//sltData参数说明：默认选中值 格式为：U:id1,id2,...;D:id1,id2,...
		String sltData = StringUtils.checkNullString(request.getParameter("sltData"));
		//findType参数说明：0.基本(默认) 1.高级搜索
		String findType = StringUtils.checkNullString(request.getParameter("findType"));
		if("".equals(findType))
			findType = "0";
	%>
	<head>
		<%@ include file="/common/core/header_form.jsp"%>
		<title>请选择处理人/组</title>
		<script language="javascript" defer="defer">
			var height = 0;
			window.onload = function()
			{
				var useMode = '<%=useMode%>';
				if(useMode == '')
				{
					height = 35;
				}
				document.getElementById('center').contentWindow.document.getElementById('bottomTreeFrame').contentWindow.initDataResult('<%=sltData%>', '<%=idtype%>');
				setCenter(0, height);
			}
			window.onresize = function()
			{
				setCenter(0, height);
			}
			function getSelectData()
			{
				var sltType = '<%=sltType%>';
				var dataFormat = '';
				if(sltType == '0')
					dataFormat = 'D';
				else if(sltType == '1')
					dataFormat = 'U';
				else
					dataFormat = '';
				var returnData = document.getElementById('center').contentWindow.document.getElementById('bottomTreeFrame').contentWindow.getDataFromResult(dataFormat, '<%=idtype%>');
				return returnData;
			}
			/**
			 * dataType：element、group
			 * element:以元素为单位 U:id1,name1;U:id2,name2;...;D:id1,name1;D:id2,name2;...
			 * group:以组为单位 U:id1,id2,...:name1,name2,...;D:id1,id2,...:name1,name2,...
			 * dataFormat:返回数据类型（当dataType=group时有效）
			 * idtype:获取用户的时候 0:取id-loginName 1:取loginName 2:取ID（非0,1）
			 */
			function getSelectData(dataType, dataFormat, idtype)
			{
				var selectData = '';
				if(dataType == 'element')
					selectData = document.getElementById('center').contentWindow.document.getElementById('bottomTreeFrame').contentWindow.getResolveData(idtype);
				else
					selectData = document.getElementById('center').contentWindow.document.getElementById('bottomTreeFrame').contentWindow.getDataFromResult(dataFormat, idtype);
				return selectData;
			}
			function setSelectData()
			{
				//data格式 U:id1,id2,...:name1,name2,...;D:id1,id2,...:name1,name2,...
				var data = document.getElementById('center').contentWindow.document.getElementById('bottomTreeFrame').contentWindow.getDataFromResult('');
				var u_id = '';
				var u_text = '';
				var d_id = '';
				var d_text = '';
				if(data != '')
				{
					var dataArray = data.split(';');
					var userArray = dataArray[0].split(':');
					var depArray = dataArray[1].split(':');
					u_id = userArray[1];
					u_text = userArray[2];
					d_id = depArray[1];
					d_text = depArray[2];
				}
				var callerWindowObj = dialogArguments;
				<% 
					if(!"".equals(form_name))
					{
						if(!"".equals(user_id))
						{
				%>
					    	callerWindowObj.document.<%=form_name%>.<%=user_id%>.value = u_id;
				<%
						}
						if(!"".equals(user_name))
						{
				%>
							callerWindowObj.document.<%=form_name%>.<%=user_name%>.value = u_text;
				<%
						}
						if(!"".equals(dep_id))
						{
				%>
					    	callerWindowObj.document.<%=form_name%>.<%=dep_id%>.value = d_id;
				<%
						}
						if(!"".equals(dep_name))
						{
				%>
							callerWindowObj.document.<%=form_name%>.<%=dep_name%>.value = d_text;
				<%
						}
					}
				%>
				window.close();
			}
		</script>
	</head>
	<body>
		<div class="content">
			<iframe src="${ctx}/common/tools/orgtreev2/treeFrameSpace.jsp?isRadio=<%=isRadio%>&findType=<%=findType%>&sltType=<%=sltType%>&sltData=<%=sltData%>" frameborder="0" id="center" name="center"></iframe>
			<%
			if("".equals(useMode))
			{
			%>
			<div class="add_bottom">
				<input type="button" value="<eoms:lable name="com_btn_save"/>"
					class="save_button"
					onmouseover="this.className='save_button_hover'"
					onmouseout="this.className='save_button'" onclick="setSelectData();" />
				<input type="button" value="<eoms:lable name='com_btn_cancel'/>"
					class="cancel_button"
					id="cancelButton"
					onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					onclick="window.close();" />
			</div>
			<%
			}
			%>
		<div>
	</body>
</html>

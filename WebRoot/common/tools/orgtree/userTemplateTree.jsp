<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ultrapower.eoms.common.core.util.StringUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<%
		//调用粘贴：showModalDialog('${ctx}/common/tools/orgtree/userTemplateTree.jsp?form_name=&input_name=&input_id=&targetDataArr='+selectData,window,'help:no;center:true;scroll:no;status:no;dialogWidth:580px;dialogHeight:490px');
		//使用建议：窗口大小请设置为 width：580px height：490px 当useMode=frame时 请设置width：580px height：460px
		//调用可直接粘贴下面一句，将用到参数进行赋值即可。只有使用showModalDialog调用时才需要传递这些参数form_name、input_name、input_id
		//form_name(必填)，对于后面的name和id只需要传递自己要的参数，如只选人则input_name、input_id设置为空即可
		String form_name = StringUtils.checkNullString(request.getParameter("form_name"));
		String input_name = StringUtils.checkNullString(request.getParameter("input_name"));
		String input_id = StringUtils.checkNullString(request.getParameter("input_id"));
		//useMode参数说明：使用模式，如果只用组织机构树选择，则不用传递此参数，若要嵌入到页面中的IFRAME里，则需要把此参数设置为：frame即可
		String useMode = StringUtils.checkNullString(request.getParameter("useMode"));
		//isRadio参数说明：0：单选 1：多选
		String isRadio = StringUtils.checkNullString(request.getParameter("isRadio"));
		if(!"0".equals(isRadio))
			isRadio = "1";
		//targetDataArr参数说明：默认选中值 格式为： T:id1,id2...
		String targetDataArr = StringUtils.checkNullString(request.getParameter("targetDataArr"));
	%>
	<head>
		<%@ include file="/common/core/header_form.jsp"%>
		<title>请选择人员模版</title>
		<script language="javascript" defer="defer">
			var height;
			window.onload = function()
			{
				height = 0;
				var useMode = '<%=useMode%>';
				if(useMode == '')
				{
					height = LAYOUT_FORM_OPEN;
					document.getElementById('center').contentWindow.document.getElementById('bottomTreeFrame').contentWindow.initDataResult('<%=targetDataArr%>', 'false');
				}
				setCenter(0, height);
			}
			window.onresize = function()
			{
				setCenter(0, height);
			}
			function getSelectData()
			{
				var returnData = document.getElementById('center').contentWindow.document.getElementById('bottomTreeFrame').contentWindow.getDataFromResult('T');
				return returnData;
			}
			function setSelectData()
			{
				//data格式 id1,id2,...:name1,name2,...
				var data = document.getElementById('center').contentWindow.document.getElementById('bottomTreeFrame').contentWindow.getDataFromResult('T');
				var t_id = '';
				var t_text = '';
				if(data != '')
				{
					var tmpArray = data.split(':');
					t_id = tmpArray[0];
					t_text = tmpArray[1];
				}
				var callerWindowObj = dialogArguments;
				<% 
					if(!"".equals(form_name))
					{
						if(!"".equals(input_id))
						{
				%>
					    	callerWindowObj.document.<%=form_name%>.<%=input_id%>.value = t_id;
				<%
						}
						if(!"".equals(input_name))
						{
				%>
							callerWindowObj.document.<%=form_name%>.<%=input_name%>.value = t_text;
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
			<iframe src="${ctx}/common/tools/orgtree/treeFrameSpace.jsp?treeType=UserTemplate&isRadio=<%=isRadio%>&targetDataArr=<%=targetDataArr%>" frameborder="0" id="center" name="center"></iframe>
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

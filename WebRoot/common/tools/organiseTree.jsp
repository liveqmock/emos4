<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
     String isRadio = request.getParameter("isRadio");
     String isSelectType = request.getParameter("isSelectType");
 %>
<html>
  <head>   
   	<%@ include file="/common/core/taglibs.jsp"%>
   	<title><eoms:lable name="sm_t_setOpPrivilege" /></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript">
		window.onresize = function() {
			setCenter(0,56);
		}
		window.onload = function() {
			setCenter(0,56);
		}
		
		function formSubmit()
		{
			document.getElementById('tree').contentWindow.getDepAndUser();
			var datas = document.getElementById('tree').contentWindow.returnStr;
			 
			var isradioValue = '<%=isRadio%>';
			var isContinue = "yes";
			//根据格式分解  D:id,name,fullname;U:id,name,loginname;
			datas = datas.substring(0,datas.length-1);//去除最后一位的';'
			var values = '';
			var ids = '';
			if(datas != '')
			{
				var tep;
				var data = datas.split(';');
				for(var i=0;i<data.length;i++)
				{
					teps = data[i].split(':');
					if(isradioValue=='0'){//部门 单选
						var orgtype = teps[0];
						if(orgtype=='U'){
							alert('请选择类型为部门!');
							isContinue = 'no';
						}else{
							tep = teps[1].split(',');
							if(ids != '')
							{
								ids += ',';
								values += ',';
							}
							ids += tep[0];
							values += tep[1];
						}
					}else{
						tep = teps[1].split(',');
						if(ids != '')
						{
							ids += ',';
							values += ',';
						}
						ids += tep[0];
						values += tep[1];
					}
				}
			}
			if(isContinue=='yes'){
				var callerWindowObj = dialogArguments;
					<% 
						if(request.getParameter("form_name")!=null){
							if(request.getParameter("input_name")!=null)
					%>
							  callerWindowObj.document.<%=request.getParameter("form_name")%>.<%=request.getParameter("input_name")%>.value=values;
					<%	    if(request.getParameter("input_id")!=null){
					%>       
						      callerWindowObj.document.<%=request.getParameter("form_name")%>.<%=request.getParameter("input_id")%>.value=ids;
					<%      }else {} 
					     }%>
				window.close();
			}
		}
	</script>
  </head>
  <body style="overflow:hidden;">
	  <div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
					<span class="title_icon2"><eoms:lable name='sm_subt_depUserTree' /></span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div style="overflow:hidden;" id="center">
			<iframe src="${ctx}/common/tools/depListTree.jsp?isRadio=<%=isRadio%>&isSelectType=<%=isSelectType%>" id='tree' scrolling="no" frameborder="0" width="100%" height="100%"></iframe>
		</div>
		
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button"
				onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="formSubmit();"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button"
				onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	  </div>
  </body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<script src="${ctx}/common/javascript/util.js"></script>
		
		<title><eoms:lable name="creatorChoose"/></title>
		<%
		
			String textElementId = request.getParameter("textElementId");
			String idElementId = request.getParameter("idElementId");
			request.setAttribute("textElementId", textElementId);
		
		%>

	<script type="text/javascript">
		
		
		window.onload = function(){
			setCenter(0,36);
		}
		
		var actorname = '';
		var actorid = '';
		//选择人后，提交方法(U:402894f5297db9dc01297dc938b20001,王小东,wangxiaodong;)
		function formsubmit(){
			document.getElementById('deptree').contentWindow.getDepAndUser();
		 	var actorStr = document.getElementById('deptree').contentWindow.returnStr;
		 	var actors = actorStr.split(';');
		 	var actorIds = '';
		 	var actorNames = '';
		 	for(var i=0;i<actors.length;i++){
		 		var type = actors[i].substring(0,1);
		 		var infos = actors[i].substring(2).split(',');
		 		if(type == 'U'){//用户
					actorNames += ',' + infos[1];
					actorIds += ',' + infos[2];
		 		}
		 	
			}
		 	if(actorNames != '' && actorIds != ''){
		 		actorNames = actorNames.substring(1);
		 		actorIds = actorIds.substring(1);
		 	}
		 	if("${textElementId}" != ""){
		 		opener.window.document.getElementsByName('<%=textElementId%>')[0].value = actorNames;
				opener.window.document.getElementsByName('<%=idElementId%>')[0].value = actorIds;
		 	}else{
				opener.window.document.getElementsByName('creator')[0].value = actorNames;
				opener.window.document.getElementsByName('creator_loginname')[0].value = actorIds;
		 	}
			window.close();
		}
	</script>
</head>
	<body>
		<div class="content">
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style" style="height: 100%;width: 90%">
								<div class="blank_tr"></div>
								<legend><div  id="title_1"><eoms:lable name="wf_actor_assign_title1" /></div></legend>
				
				<iframe src="${ctx}/common/tools/depListTree.jsp?isRadio=0&isSelectChild=0&isSelectType=1"  frameborder="0" id="deptree" name="deptree" style="width: 100%;height: 90%"></iframe>
				</fieldset>
			</div>
			
			<div class="add_bottom">	
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onclick="formsubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
			</div>
			
		</div>
		
<script  type="text/javascript">
</script>
	</body>
</html>

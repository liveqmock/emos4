<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script language="javascript">
			window.onresize = function() 
			{
				setCenter(0,30);
			}
			window.onload = function() 
			{
				setCenter(0,30);
			}
			function addSendTree()
			{
				//document.getElementById("organizeInfo").value = "";
				var src = '${ctx}/ultrabpp/assigntree/configTreeEdit.action?configID=${configModel.id}';
				//window.showModalDialog('${ctx}/ultrabpp/assigntree/configTreeEdit.action?configID=${configModel.id}',window,'help:no;scroll:yes;status:no;dialogWidth:300px;dialogHeight:400px');
				//var organizeInfo = document.getElementById("organizeInfo").value;
				//if(organizeInfo != "")
				//{
				//	document.forms[0].submit();
				//}
				window.open(src,'','width=850px,height=400px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
			}
			function delSendTree()
			{
				if("${configID}"!="")
				{
					var delSendTreeInfo = document.getElementById("tree").contentWindow.getCheckedInfo();
					if(delSendTreeInfo!=="")
					{
						if(confirm("<eoms:lable name='sm_msg_deleteNodeConfirm'/>？"))
						{
							var tempArr = delSendTreeInfo.split(";");
							var depids = tempArr[0];
							var userids = tempArr[2];
							document.getElementById("depids").value = depids;
							document.getElementById("userids").value = userids;
							document.forms[1].submit();
						}
					}
				}
			}
		</script>
	</head>
	<body>
	   <form action="${ctx}/formCustSendTree/saveFromCustSendTree.action" method="post">
	       <input type="hidden" name="configModel.id" value="${configModel.id}"/>
	       <input type="hidden" name="configModel.baseschema" value="${configModel.baseSchema}"/>
	       <input type="hidden" name="configModel.stepNo" value="${configModel.stepNo}"/>
	       <input type="hidden" name="configModel.stepDesc" value="${configModel.stepDesc}"/>
	       <input type="hidden" name="configModel.actionName" value="${configModel.actionName}"/>
	       <input type="hidden" name="configModel.actionLabel" value="${configModel.actionLabel}"/>
	       <input type="hidden" name="configModel.fieldName" value="${configModel.fieldName}"/>
	       <input type="hidden" name="configModel.fieldLabel" value="${configModel.fieldLabel}"/>
	       <input type="hidden" name="organizeInfo" id="organizeInfo" value="" style="width:100%"/>
		   <div class="content">
				<div class="page_div_bg">
					<div class="page_div">
						<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
					  		onmouseout="this.className='page_add_button'" onclick="addSendTree();" text="增加"/>
					  	<c:if test="${configModel.id!=null}">
					  		<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'"
								onmouseout="this.className='page_del_button'" onclick="delSendTree();" text="com_btn_delete" operate="com_delete"/>
					  	</c:if>
			 	 	</div>
				</div>
				<div id="center">
					<div class="blank_tr"></div>
					<fieldset class="fieldset_style" style="width:90%">
						<legend><eoms:lable name="sm_lb_selfDefineSendTree"/></legend>
						<div class="blank_tr"></div>
						<div class="blank_tr"></div>
						<table style="width:100%;" cellpadding="0" cellspacing="0">
						 <tr>
						 	<td style="height:400px;">
						 		<iframe src="${ctx}/ultrabpp/develop/form/customOrganiseTreeView.jsp?isRadio=1&isSelectType=2&sendTreeId=${configID}" scrolling="no" id='tree' frameborder="0" style="width:100%;height:100%;"></iframe>
						 	</td>
						 </tr>
						</table>
					</fieldset>
				</div>
			</div>
		</form>
		<form action="${ctx}/ultrabpp/assigntree/delAssignTreeNode.action" method="post">
			<input type="hidden" name="configID" id="configID" value="${configID}"/>
			<input type="hidden" name="depids" id="depids" value=""/>
			<input type="hidden" name="userids" id="userids" value=""/>
		</form>
	</body>
</html>

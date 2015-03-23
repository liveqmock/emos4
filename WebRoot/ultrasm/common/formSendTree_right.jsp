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
				document.getElementById("customOrganizeInfo").value = "";
				window.showModalDialog('${ctx}/ultrasm/common/formSendTreeAdd.jsp?isRadio=1&isSelectChild=1',window,'help:no;scroll:yes;status:no;dialogWidth:300px;dialogHeight:400px');
				var customOrganizeInfo = document.getElementById("customOrganizeInfo").value;
				if(customOrganizeInfo != "")
				{
					document.forms[0].submit();
				}
			}
			function delSendTree()
			{
				if("${formCustSendTree.pid}"!="")
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
	       <input type="hidden" name="formCustSendTree.pid" value="${formCustSendTree.pid}"/>
	       <input type="hidden" name="formCustSendTree.baseschema" value="${formSchema}"/>
	       <input type="hidden" name="formCustSendTree.loginname" value="${formCustSendTree.loginname}"/>
	       <input type="hidden" name="formCustSendTree.creater" value="${formCustSendTree.creater}"/>
	       <input type="hidden" name="formCustSendTree.createtime" value="${formCustSendTree.createtime}"/>
	       <input type="hidden" name="customOrganizeInfo" id="customOrganizeInfo" value="" style="width:100%"/>
		   <div class="content">
				<div class="page_div_bg">
					<div class="page_div">
						<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
					  		onmouseout="this.className='page_add_button'" onclick="addSendTree();" text="com_btn_add"/>
					  	<c:if test="${formCustSendTree.pid!=null}">
					  		<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'"
								onmouseout="this.className='page_del_button'" onclick="delSendTree();" text="com_btn_delete" operate="com_delete"/>
					  	</c:if>
			 	 	</div>
				</div>
				<div id="center">
					<fieldset class="fieldset_style" style="width:90%;display:none;" id="fieldset_action">
						<legend>基本属性</legend>
						<div class="blank_tr"></div>
							<table class="add_user">
								<tr>
									<td class="texttd" style="width:15%">名称：<span class="must">*</span></td>
									<td style="width:35%">
										<input type="text" name="formCustSendTree.treename" value="${formCustSendTree.treename}" class="textInput"/>
									</td>
									<td class="texttd" style="width:15%">状态：</td>
									<td>
										<eoms:select name="formCustSendTree.status" style="select" dataDicTypeCode="status" value="${formCustSendTree.status}" isnull="false"/>
									</td>									
								</tr>
								<tr>
									<td class="texttd">排序值：<span class="must">*</span></td>
									<td>
										<input type="text" name="formCustSendTree.ordernum" value="0" class="textInput"/>
									</td>
								</tr>
								<tr>
									<td class="texttd" valign="top"><eoms:lable name="com_lb_desc" />：</td>
									<td colspan="3">
										<textarea name="formCustSendTree.describe" id="textarea2" rows="1" class="textInput">${formCustSendTree.describe}</textarea>
									</td>
								</tr>
							</table>
						<div class="blank_tr"></div>
					</fieldset>
					<div class="blank_tr"></div>
					<fieldset class="fieldset_style" style="width:90%">
						<legend><eoms:lable name="sm_lb_selfDefineSendTree"/></legend>
						<div class="blank_tr"></div>
						<div class="blank_tr"></div>
						<table style="width:100%;" cellpadding="0" cellspacing="0">
						 <tr>
						 	<td style="height:400px;">
						 		<iframe src="${ctx}/common/tools/customOrganiseTreeView.jsp?isRadio=1&isSelectType=2&sendTreeId=${formCustSendTree.pid}" scrolling="no" id='tree' frameborder="0" style="width:100%;height:100%;"></iframe>
						 	</td>
						 </tr>
						</table>
					</fieldset>
				</div>
			</div>
		</form>
		
		<form action="${ctx}/formCustSendTree/delFromCustSendTree.action" method="post">
			<input type="hidden" name="custSenderPid" value="${formCustSendTree.pid}"/>
			<input type="hidden" name="formSchema" value="${formSchema}"/>
			<input type="hidden" name="depids" id="depids" value=""/>
			<input type="hidden" name="userids" id="userids" value=""/>
			
		</form>
	</body>
</html>

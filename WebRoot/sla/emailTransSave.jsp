<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String nodeId = "";
	String nodeIdPara = request.getParameter("nodeId");
	String nodeIdAttri = (String)request.getAttribute("nodeId");
	if(nodeIdPara!=null&&!nodeIdPara.equals("")){
		nodeId = nodeIdPara;
	}else if(nodeIdAttri!=null&&!nodeIdAttri.equals("")){
		nodeId = nodeIdAttri;
	}else{}
 %>
<html>
  <head>
 	<%@ include file="/common/core/taglibs.jsp"%>
    <title><eoms:lable name="sm_subt_addOrUpdateSmailInfo"/></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<script language="javascript">
		window.onresize = function() 
			{
				setCenter(0,30);
			}
			window.onload = function() 
			{
				setCenter(0,30);
				if("true"=="${returnMessage}" && ""!="${refreshId}")
				{	
					alert('<eoms:lable name="com_msg_saveSuccess"/>！');	
					var refreshId = "${refreshId}";
					parent.document.getElementById('leftFrame2').contentWindow.refreshTrans(refreshId);
				}else if("false"=="${returnMessage}" && ""!="${refreshId}"){
					alert('<eoms:lable name="com_msg_saveErr"/>！');	
					var refreshId = "${refreshId}";
					parent.document.getElementById('leftFrame2').contentWindow.refreshTrans(refreshId);
				}else if("true"=="${returnMessage}" && ""!="${refreshBusiness}"){
				    alert('<eoms:lable name="com_msg_deleteSuccess"/>！');	
					var refreshId = "${refreshBusiness}";
					parent.document.getElementById('leftFrame2').contentWindow.refreshTrans(refreshId);
				}else if("false"=="${returnMessage}" && ""!="${refreshBusiness}"){
					alert('<eoms:lable name="com_msg_deleteErr"/>！');	
					var refreshId = "${refreshBusiness}";
					parent.document.getElementById('leftFrame2').contentWindow.refreshTrans(refreshId);
				}else{}
			}
			
			function openTree(input_name,input_id,type)
			{
				if("0"==type)
				{//角色
					showModalDialog('${ctx}/sla/businessOrgTree.jsp?form_name=slaEmailForm&input_name='+input_name+'&input_id='+input_id+'&enableCbx=1',window,'help:no;scroll:yes;status:no;dialogWidth:380px;dialogHeight:480px');  
				}
				if("1"==type)
				{//人员
					openwindow('${ctx}/common/tools/depOrUserSelect.jsp?input_name='+input_name+'&input_id='+input_id+'&isRadio=1&isSelectType=1','',380,480);
				}
				if("2"==type)
				{//组织
					openwindow('${ctx}/common/tools/depOrUserSelect.jsp?input_name='+input_name+'&input_id='+input_id+'&isRadio=1&isSelectType=0','',380,480);
				}
			}
		
			function cleardata(input_name,input_id)
			{
				document.getElementById(input_name).value = '';
				document.getElementById(input_id).value = '';
			}
		
			function submitData()
			{
				if(Validator.Validate(document.forms[0],2))
				{
					if(""=="${slaMailAction.pid}")
					{
						var actionmark = document.getElementById("slaMailAction.actionmark").value;
						$.get("${ctx}/slaMail/actionMarkUnique.action",{actionmark:actionmark},function(result)
						{
							if("true"==result)
							{
								document.forms[0].submit();
							}
							else
							{
								alert("<eoms:lable name='sm_msg_actionMarkUniqueTip'/>！");
								return;
							}
						});
					}
					else
					{
						document.forms[0].submit();
					}
				}
			}
			
			function del()
			{
				var pid = document.getElementById('slaMailAction.pid').value;
				var refreshBusiness = document.getElementById('slaMailAction.actiontype').value;
				if(confirm("<eoms:lable name="com_btn_confirm_del"/>")){
					window.location.href('${ctx}/slaMail/delSlaMail.action?pid='+pid+'&refreshBusiness='+refreshBusiness);
				}
			}
			
	</script>
  </head>
  
  <body>
	  <form action="${ctx}/slaMail/saveSlaMail.action" method="post" name="slaEmailForm">
	  	<c:if test="${slaMailAction.pid!=null}">
			<input type="hidden" id="slaMailAction.pid" name="slaMailAction.pid" value="${slaMailAction.pid}"/>
			<input type="hidden" id="slaMailAction.creater" name="slaMailAction.creater" value="${slaMailAction.creater}"/>
			<input type="hidden" id="slaMailAction.createtime" name="slaMailAction.createtime" value="${slaMailAction.createtime}"/>
		</c:if>
		<div class="content">
			<div class="page_div_bg">
				<div class="page_div">
					<eoms:operate cssclass="page_add_button" id="page_saves_button" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="submitData();" text="com_btn_save" operate="com_save"/>
					<c:if test="${slaMailAction.pid!=null}">
					<eoms:operate cssclass="page_listview_button" id="sm_btn_addRule" onmouseover="this.className='page_listview_button_hover'" 
	  		  	  				onmouseout="this.className='page_listview_button'" text="sm_btn_addRule" 
	  		  	  				onclick="window.location.href('${ctx}/slaActionManager/loadRule.action?modelId=${slaMailAction.ruletplid }&transId=${slaMailAction.pid }&actionType=${param.nodeId}')"/>
						<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="del();" text="com_btn_delete" operate="com_delete"/>
					</c:if>
	  	 		</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend><eoms:lable name="sm_subt_addOrUpdateSmailInfo"/></legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
					<table class="add_user">
						<tr>
							<td class="texttd">
								<eoms:lable name="sm_lb_slaTransName"/>：<span class="must">*</span>
							</td>
							<td colspan="3">
								<input type="text" id="slaMailAction.actionname" name="slaMailAction.actionname" class="textInput" value="${slaMailAction.actionname}" style="width:98%"/>
								<validation id="slaMailAction.actionnameV" dataType="Custom" regexp="^.{1,20}$" msg="<eoms:lable name="sm_msg_tplnameNotNullConstraint"/>"！ />
							</td>
						</tr>
						<tr>
							<td class="texttd" width="15%">
								<eoms:lable name="sm_lb_slaRuleModel"/>：<span class="must">*</span>
							</td>
							<td colspan="3">
								<input type="text" readonly="readonly" id="slaMailAction.ruletplname" name="slaMailAction.ruletplname" value="${slaMailAction.ruletplname}" class="textInput" style="width:82%" />
								<input type="hidden" id="slaMailAction.ruletplid" name="slaMailAction.ruletplid"  value="${slaMailAction.ruletplid}" />
								<validation id="slaMailAction.ruletplidV" dataType="Require" msg="<eoms:lable name="sm_msg_ruletplRequireConstraint"/>"！ />
								<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_choose"/>"
									class="operate_button" onmouseover="this.className='operate_button_hover'"
									onmouseout="this.className='operate_button'" onclick="openwindow('${ctx}/common/tools/ruleModelTree.jsp?input_id=slaMailAction.ruletplid&input_name=slaMailAction.ruletplname','',350,450);"/>
								<input type="button" name="button3" id="button3" value="<eoms:lable name="com_btn_clear"/>"
									class="operate_button" onmouseover="this.className='operate_button_hover'"
									onmouseout="this.className='operate_button'" onclick="cleardata('ruletplname','slaMailAction.ruletplid');" />
							</td>
						</tr>
						<tr>
							<td class="texttd" style="width:15%"><eoms:lable name="sm_lb_slaActionType"/>：</td>
							<td style="width:35%">
								<eoms:dic dictype="SlaActionType" value="<%=nodeId%>"/>
								<input type="hidden" id="slaMailAction.actiontype" name="slaMailAction.actiontype"  value="<%=nodeId%>" />
							</td>
							<td class="texttd" style="width:15%"><eoms:lable name="sm_lb_actionIndentifier" />：<span class="must">*</span></td>
							<td style="width:35%">
								<c:if test="${slaMailAction.pid==null}">
									<input type="text" name="slaMailAction.actionmark" id="slaMailAction.actionmark" class="textInput" value="${slaMailAction.actionmark}" />
									<validation id="slaMailAction.actionmarkV" require="true" dataType="Custom" regexp="^\w{1,20}$" msg="<eoms:lable name="sm_msg_slaactionIndentifier"/>"！ />
								</c:if>
								<c:if test="${slaMailAction.pid!=null}">
									<input type="text" name="slaMailAction.actionmark" id="slaMailAction.actionmark" class="textInput" value="${slaMailAction.actionmark}" readonly="readonly"/>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="com_lb_status"/>：</td>
							<td>
								<eoms:select name="slaMailAction.status" style="select" dataDicTypeCode="status" value="${slaMailAction.status}" isnull="false"/>
							</td>
							<td class="texttd"><eoms:lable name="sm_lb_pri"/>：</td>
							<td>
								<eoms:select name="slaMailAction.pri" style="select" dataDicTypeCode="slaPri" value="${slaMailAction.pri}" isnull="false"/>
							</td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="com_lb_orderNum" />：<span class="must">*</span></td>
							<td>
								<input type="text" id="slaMailAction.ordernum" name="slaMailAction.ordernum" class="textInput" value="${slaMailAction.ordernum}" />
								<validation id="slaMailAction.ordernumV" dataType="Custom" regexp="^[0-9]{1,5}$" msg="<eoms:lable name='sm_msg_orderNumConstraint'/>!" />
							</td>
							<td class="texttd"><eoms:lable name="sm_lb_isholidayactive" />：</td>
							<td>
								<eoms:select name="slaMailAction.isholiday" style="select" dataDicTypeCode="isHoliday" value="${slaMailAction.isholiday}" isnull="false"/>
							</td>
						</tr>
						<tr>
							<td class="texttd"  style="width:15%"><eoms:lable name="sm_lb_slaDealMode"/>：</td>
							<td style="width:35%">
								<eoms:select name="slaMailAction.dealmode" style="select" dataDicTypeCode="dealMode" value="${slaMailAction.dealmode}" isnull="false"/>
							</td>
							<td class="texttd">通知人/组：</td>
							<td>
								<eoms:select name="slaMailAction.isbusinessinformer" style="select" dataDicTypeCode="isdefault" value="${slaMailAction.isbusinessinformer}" isnull="false"/>
							</td>
						</tr>
						<tr>
							<td class="texttd"  style="width:15%">线程号：<span class="must">*</span></td>
							<td style="width:35%">
								<eoms:select name="slaMailAction.threadno" style="select" dataDicTypeCode="slaThread" value="${slaMailAction.threadno}" isnull="false"/>
							</td>	
							<td class="texttd" style="width:15%">角色模式：</td>
							<td style="width:35%">
								<eoms:select name="slaMailAction.slarolemodel" style="select" dataDicTypeCode="slaRoleModel" value="${slaMailAction.slarolemodel}" isnull="false"/>
							</td>
						</tr>
						<tr>
							<td class="texttd">自定义角色：</td>
							<td colspan="3">
								<input type="hidden" name="noticeroleid" value="${slaMailAction.noticeroleid}" id="slaMailAction.noticeroleid"/>
								<input type="text" name="noticerolename" value="${slaMailAction.noticerolename}" id="slaMailAction.noticerolename" readonly="readonly" class="textInput" style="width:82.5%"/>
								<input type="button" name="button3" id="button3" value="<eoms:lable name='com_btn_choose'/>"
										class="operate_button" onmouseover="this.className='operate_button_hover'"
										onmouseout="this.className='operate_button'" onclick="openTree('noticerolename','noticeroleid','0');"/>
								<input type="button" name="button3" id="button3" value="<eoms:lable name='com_btn_clear'/>"
										class="operate_button" onmouseover="this.className='operate_button_hover'"
										onmouseout="this.className='operate_button'" 
										onclick="document.getElementById('slaMailAction.noticerolename').value='';document.getElementById('slaMailAction.noticeroleid').value='';" />
										
							</td>
						</tr>
						<tr>
							<td class="texttd" width="15%"><eoms:lable name="sm_lb_noticeusername" />：</td>
							<td colspan="3">
								<input type="text" readonly="readonly" id="slaMailAction.noticeusername" name="slaMailAction.noticeusername" value="${slaMailAction.noticeusername}" class="textInput" style="width:82%" />
								<input type="hidden" id="slaMailAction.noticeuserid" name="slaMailAction.noticeuserid"  value="${slaMailAction.noticeuserid}" />
								<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_choose"/>"
									class="operate_button" onmouseover="this.className='operate_button_hover'"
									onmouseout="this.className='operate_button'" onclick="openTree('slaMailAction.noticeusername','slaMailAction.noticeuserid','1');" />
								<input type="button" name="button3" id="button3" value="<eoms:lable name="com_btn_clear"/>"
									class="operate_button" onmouseover="this.className='operate_button_hover'"
									onmouseout="this.className='operate_button'" onclick="cleardata('slaMailAction.noticeusername','slaMailAction.noticeuserid');" />
							</td>
						</tr>
						<tr>
							<td class="texttd" width="15%"><eoms:lable name="sm_lb_noticegroupname" />：</td>
							<td colspan="3">
								<input type="text" readonly="readonly" id="slaMailAction.noticegroupname" name="slaMailAction.noticegroupname" value="${slaMailAction.noticegroupname}" class="textInput" style="width:82%" />
								<input type="hidden" id="slaMailAction.noticegroupid" name="slaMailAction.noticegroupid"  value="${slaMailAction.noticegroupid}" />
								<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_choose"/>"
									class="operate_button" onmouseover="this.className='operate_button_hover'"
									onmouseout="this.className='operate_button'" onclick="openTree('slaMailAction.noticegroupname','slaMailAction.noticegroupid','2');"/>
								<input type="button" name="button3" id="button3" value="<eoms:lable name="com_btn_clear"/>"
									class="operate_button" onmouseover="this.className='operate_button_hover'"
									onmouseout="this.className='operate_button'" onclick="cleardata('slaMailAction.noticegroupname','slaMailAction.noticegroupid');" />
							</td>
						</tr>
						<tr>
							<td class="texttd" width="15%"><eoms:lable name="sm_lb_copyusername" />：</td>
							<td colspan="3">
								<input type="text" readonly="readonly" id="slaMailAction.copyusername" name="slaMailAction.copyusername" value="${slaMailAction.copyusername}" class="textInput" style="width:82%" />
								<input type="hidden" id="slaMailAction.copyuserid" name="slaMailAction.copyuserid"  value="${slaMailAction.copyuserid}" />
								<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_choose"/>"
									class="operate_button" onmouseover="this.className='operate_button_hover'"
									onmouseout="this.className='operate_button'" onclick="openTree('slaMailAction.copyusername','slaMailAction.copyuserid','1');" />
								<input type="button" name="button3" id="button3" value="<eoms:lable name="com_btn_clear"/>"
									class="operate_button" onmouseover="this.className='operate_button_hover'"
									onmouseout="this.className='operate_button'" onclick="cleardata('slaMailAction.copyusername','slaMailAction.copyuserid');" />
							</td>
						</tr>
						<tr>
							<td class="texttd" width="15%"><eoms:lable name="sm_lb_copygroupname" />：</td>
							<td colspan="3">
								<input type="text" readonly="readonly" id="slaMailAction.copygroupname" name="slaMailAction.copygroupname" value="${slaMailAction.copygroupname}" class="textInput" style="width:82%" />
								<input type="hidden" id="slaMailAction.copygroupid" name="slaMailAction.copygroupid"  value="${slaMailAction.copygroupid}" />
								<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_choose"/>"
									class="operate_button" onmouseover="this.className='operate_button_hover'"
									onmouseout="this.className='operate_button'" onclick="openTree('slaMailAction.copygroupname','slaMailAction.copygroupid','2'); "/>
								<input type="button" name="button3" id="button3" value="<eoms:lable name="com_btn_clear"/>"
									class="operate_button" onmouseover="this.className='operate_button_hover'"
									onmouseout="this.className='operate_button'" onclick="cleardata('slaMailAction.copygroupname','slaMailAction.copygroupid');" />
							</td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="sm_lb_mailsubject" />：<span class="must">*</span></td>
							<td colspan="3">
								<input type="text" id="slaMailAction.mailsubject" name="slaMailAction.mailsubject" style="width:99%" class="textInput" value="${slaMailAction.mailsubject}" />
								<validation id="slaMailAction.mailsubjectV" dataType="Limit" min="1" max="200" msg="<eoms:lable name='sm_msg_mailsubjectNotNullConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top"><eoms:lable name="sm_lb_mailcontent" />：<span class="must">*</span></td>
							<td colspan="3">
								<textarea name="slaMailAction.mailcontent" id="textarea" rows="5" class="textInput">${slaMailAction.mailcontent}</textarea>
								<validation id="slaMailAction.mailcontentV" dataType="Limit" min="1" max="1000" msg="<eoms:lable name='sm_msg_mailContentNotNullConstraint'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top"><eoms:lable name="com_lb_desc" />：</td>
							<td colspan="3">
								<textarea name="slaMailAction.remark" id="textarea" rows="4" class="textInput">${slaMailAction.remark}</textarea>
								<validation id="slaMailAction.remarkV" dataType="Limit" max="100" msg="<eoms:lable name='sm_msg_miaoshuInfoConstraint'/>！" />
							</td>
						</tr>
					</table>
					</div>
				</fieldset>
			</div>
		</div>
		</form>
	</body>
</html>

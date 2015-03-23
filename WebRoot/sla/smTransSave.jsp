<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript">
			window.onresize = function() 
			{
				setCenter(0,30);
			}
			window.onload = function() 
			{
				setCenter(0,30);
				if(""!="${saveResult}")
				{
					var serviceType = "${serviceType}";
					if("save"==serviceType)
					{
						if("success"=="${saveResult}")
						{
							alert("<eoms:lable name='com_msg_saveSuccess'/>！");
						}
						else
						{
							alert("<eoms:lable name='com_msg_saveErr'/>！");
						}
					}
					else if("delete"==serviceType)
					{
						if("success"=="${saveResult}")
						{
							alert("<eoms:lable name='com_msg_deleteSuccess'/>！");
						}
						else
						{
							alert("<eoms:lable name='com_msg_deleteErr'/>！");
						}
					}
					var refreshId = "${refreshId}";
					parent.document.getElementById('leftFrame2').contentWindow.refreshTrans(refreshId);
				}
			}
			function openTree(type)
			{
				if("0"==type)
				{//角色
					showModalDialog('${ctx}/roleManager/getSelfRoleTree.action?form_name=slaSmForm&input_name=noticerolename&input_id=noticeroleid&enableCbx=1',window,'help:no;scroll:yes;status:no;dialogWidth:380px;dialogHeight:480px');  
				}
				if("1"==type)
				{//人员
					openwindow('${ctx}/common/tools/depOrUserSelect.jsp?input_name=slaSmAction.noticeusername&input_id=slaSmAction.noticeuserid&isRadio=1&isSelectType=1','',380,480);
				}
				if("2"==type)
				{//组织
					openwindow('${ctx}/common/tools/depOrUserSelect.jsp?input_name=slaSmAction.noticegroupname&input_id=slaSmAction.noticegroupid&isRadio=1&isSelectType=0','',380,480);
				}
			}
			function delTrans()
			{
				var id = document.getElementById("slaSmAction.pid").value;
				if(confirm("<eoms:lable name='sm_msg_confirmDelSlaTrans'/>？"))
				{
					window.location.href("${ctx}/slaActionManager/delSmTrans.action?id="+id);
				}
			}
			function sqlPreview()
			{
				var tplid= document.getElementById("ruleModelId").value;
				var actionid = document.getElementById("slaSmAction.pid").value;
				window.open("${ctx}/sla/slaSql.jsp?tplid="+tplid+"&actionid="+actionid);
			}
			function formSubmit()
			{
				if(Validator.Validate(document.forms[0],2))
				{
					if(""=="${slaSmAction.pid }")
					{
						var actionmark = document.getElementById("slaSmAction.actionmark").value;
						$.get("${ctx}/slaActionManager/actionMarkUnique.action",{actionmark:actionmark},function(result)
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
		</script>
	</head>
	<body>
		<form action="${ctx}/slaActionManager/smTransSave.action" method="post" name="slaSmForm">
			<input type="hidden" name="slaSmAction.pid" id="slaSmAction.pid" value="${slaSmAction.pid }"/>
			<input type="hidden" name="slaSmAction.creater" id="slaSmAction.creater" value="${slaSmAction.creater }"/>
			<input type="hidden" name="slaSmAction.createtime" id="slaSmAction.createtime" value="${slaSmAction.createtime }"/>
	   <div class="content">
			<div class="page_div_bg">
				<div class="page_div">
					<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
				  			onmouseout="this.className='page_add_button'" 
				  			onclick="formSubmit();"
				  			text="com_btn_save"/>
      				<c:if test="${slaSmAction.pid!=null}">
	      				<eoms:operate cssclass="page_listview_button" id="sm_btn_addRule" onmouseover="this.className='page_listview_button_hover'" 
	  		  	  				onmouseout="this.className='page_listview_button'" text="sm_btn_addRule" 
	  		  	  				onclick="window.location.href('${ctx}/slaActionManager/loadRule.action?modelId=${slaSmAction.ruletplid }&transId=${slaSmAction.pid }&actionType=${param.nodeId}')" />
					  	<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" 
	  		  	  				onmouseout="this.className='page_del_button'" onclick="delTrans();" text="com_btn_delete"/>
				<eoms:operate cssclass="page_search_button" id="com_btn_search"
					onmouseover="this.className='page_search_button_hover'"
					onmouseout="this.className='page_search_button'"
					onclick="sqlPreview();" text="SQL" />	  		  	  				
	  		  	  				
  		  	  		</c:if>
		 	 	</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend><eoms:lable name="sm_lb_smTransInfo"/></legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd"><eoms:lable name="sm_lb_slaTransName"/>：<span class="must">*</span></td>
									<td colspan="3">
										<input type="text" id="slaSmAction.actionname" name="slaSmAction.actionname" value="${slaSmAction.actionname }" class="textInput" style="width:98.6%"/>
										<validation id="slaSmAction.actionnameV" dataType="Limit" max="50" msg="<eoms:lable name='sm_msg_slaTransNameConstraint'/>！" />
									</td>
																		
								</tr>
								<tr>
									<td class="texttd"><eoms:lable name="sm_lb_slaRuleModel"/>：<span class="must">*</span></td>
									<td colspan="3">
										<input type="hidden" name="slaSmAction.ruletplid" value="${slaSmAction.ruletplid }" id="ruleModelId"/>
										<validation id="slaSmAction.ruletplidV" dataType="Require" msg="<eoms:lable name='sm_msg_ruletplRequireConstraint'/>！" />
										<input type="text" class="textInput" style="width:82.2%" value="${slaSmAction.ruletplname}" id="ruleModelName" readonly="readonly"/>
										<input type="button" name="button3" id="button3" value="<eoms:lable name='com_btn_choose'/>"
												class="operate_button" onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="openwindow('${ctx}/common/tools/ruleModelTree.jsp?input_id=ruleModelId&input_name=ruleModelName&formName=smTransForm','',350,450);" />
										<input type="button" name="button3" id="button3" value="<eoms:lable name='com_btn_clear'/>"
												class="operate_button" onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="document.getElementById('ruleModelId').value='';document.getElementById('ruleModelName').value='';" />
									</td>
								</tr>
								<tr>
									<td class="texttd" style="width:15%"><eoms:lable name="sm_lb_slaActionType"/>：</td>
									<td style="width:35%">
										<c:choose>
											<c:when test="${param.nodeId!=null}">
												<eoms:dic dictype="SlaActionType" value="${param.nodeId}"/>
												<input type="hidden" name="slaSmAction.actiontype"  value="${param.nodeId}" />
											</c:when>
											<c:otherwise>
												<eoms:dic dictype="SlaActionType" value="${nodeId}"/>
												<input type="hidden" name="slaSmAction.actiontype"  value="${nodeId}" />
											</c:otherwise>
										</c:choose>
									</td>
									<td class="texttd"><eoms:lable name="sm_lb_actionIndentifier" />：<span class="must">*</span></td>
									<td>
										<c:if test="${slaSmAction.pid==null }">
											<input type="text" name="slaSmAction.actionmark" id="slaSmAction.actionmark" class="textInput" value="${slaSmAction.actionmark}" />
											<validation id="slaSmAction.actionmarkV" require="true" dataType="Custom" regexp="^\w{1,20}$" msg="<eoms:lable name="sm_msg_slaactionIndentifier"/>"！ />
										</c:if>
										<c:if test="${slaSmAction.pid!=null }">
											<input type="text" name="slaSmAction.actionmark" id="slaSmAction.actionmark" class="textInput" value="${slaSmAction.actionmark}" readonly="readonly"/>
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="texttd" style="width:15%"><eoms:lable name="com_lb_status"/>：</td>
									<td style="width:35%">
										<eoms:select name="slaSmAction.status" style="select" dataDicTypeCode="status" value="${slaSmAction.status }" isnull="false"/>
									</td>
									<td class="texttd" style="width:15%"><eoms:lable name="sm_lb_pri"/>：</td>
									<td style="width:35%">
										<eoms:select name="slaSmAction.pri" style="select" dataDicTypeCode="slaPri" value="${slaSmAction.pri }" isnull="false"/>
									</td>										
								</tr>
								<tr>
									<td class="texttd" style="width:15%"><eoms:lable name="com_lb_orderNum"/>：<span class="must">*</span></td>
									<td style="width:35%">
										<input id="slaSmAction.ordernum" name="slaSmAction.ordernum" value="${slaSmAction.ordernum }" type="text" class="textInput"/>
										<validation id="slaSmAction.ordernumV" dataType="Custom" regexp="^[0-9]{1,5}$"
													msg="<eoms:lable name='sm_msg_orderNumConstraint'/>！" />
									</td>
									<td class="texttd" style="width:15%"><eoms:lable name="sm_lb_isholidayactive"/>：</td>
									<td style="width:35%">
										<eoms:select name="slaSmAction.isholiday" style="select" dataDicTypeCode="isHoliday" value="${slaSmAction.isholiday}" isnull="false"/>
									</td>										
								</tr>
								<tr>
									<td class="texttd" style="width:15%">接收时间从：<span class="must">*</span></td>
									<td style="width:35%">
										<input type="text" name="slaSmAction.starttime" id="startTime" class="textInput" 
											onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});this.className='focus'"
											readonly="readonly" style='width:95%' value="${slaSmAction.starttime}" />
										<validation id="slaSmAction.starttimeV" dataType="Require" msg="<eoms:lable name='sm_msg_starttimeIsNullConstraint'/>！" />
									</td>
									<td class="texttd" style="width:15%"><eoms:lable name="sm_lb_sendendtime"/>：<span class="must">*</span></td>
									<td style="width:35%">
										<input type="text" name="slaSmAction.endtime" id="endTime" class="textInput" 
											onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});this.className='focus'"
											readonly="readonly" style='width:95%' value="${slaSmAction.endtime}" />
										<validation id="slaSmAction.endtimeV" dataType="Require" msg="<eoms:lable name='sm_msg_endtimeIsNullConstraint'/>！" />
									</td>								
								</tr>
								<tr>
									<td class="texttd"  style="width:15%"><eoms:lable name="sm_lb_slaDealMode"/>：<span class="must">*</span></td>
									<td style="width:35%">
										<eoms:select name="slaSmAction.dealmode" style="select" dataDicTypeCode="dealMode" value="${slaSmAction.dealmode }" isnull="false"/>
									</td>	
									<td class="texttd" style="width:15%">通知人/组：</td>
									<td style="width:35%">
										<eoms:select name="slaSmAction.isbusinessinformer" style="select" dataDicTypeCode="isdefault" value="${slaSmAction.isbusinessinformer }" isnull="false"/>
									</td>
								</tr>
								<tr>
									<td class="texttd"  style="width:15%">线程号：<span class="must">*</span></td>
									<td style="width:35%">
										<eoms:select name="slaSmAction.threadno" style="select" dataDicTypeCode="slaThread" value="${slaSmAction.threadno}" isnull="false"/>
									</td>	
									<td class="texttd" style="width:15%">角色模式：</td>
									<td style="width:35%">
										<eoms:select name="slaSmAction.slarolemodel" style="select" dataDicTypeCode="slaRoleModel" value="${slaSmAction.slarolemodel}" isnull="false"/>
									</td>
								</tr>
								<tr>
									<td class="texttd"  style="width:15%">发送次数：<span class="must">*</span></td>
									<td style="width:35%">
										<input id="slaSmAction.dealtime" name="slaSmAction.dealtime" value="${slaSmAction.dealtime }" type="text" class="textInput"/>
										<validation id="slaSmAction.dealtimeV" dataType="Custom" regexp="^[0-9]{1,5}$"
													msg="<eoms:lable name='sm_msg_orderNumConstraint'/>！" />
									</td>
									<td class="texttd" style="width:15%">间隔时间（分）：</td>
									<td style="width:35%">
										<input id="slaSmAction.steptime" name="slaSmAction.steptime" value="${slaSmAction.steptime}" type="text" class="textInput"/>
										<validation id="slaSmAction.steptimeV" dataType="Custom" regexp="^[0-9]{1,5}$"
													msg="<eoms:lable name='sm_msg_orderNumConstraint'/>！" />
									</td>									
								</tr>								
								<tr>
									<td class="texttd">自定义角色：</td>
									<td colspan="3">
										<input type="hidden" name="noticeroleid" value="${slaSmAction.noticeroleid}" id="slaSmAction.noticeroleid"/>
										<input type="text" name="noticerolename" value="${slaSmAction.noticerolename}" id="slaSmAction.noticerolename" readonly="readonly" class="textInput" style="width:82.5%"/>
										<input type="button" name="button3" id="button3" value="<eoms:lable name='com_btn_choose'/>"
												class="operate_button" onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" onclick="openTree('0');"/>
										<input type="button" name="button3" id="button3" value="<eoms:lable name='com_btn_clear'/>"
												class="operate_button" onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="document.getElementById('slaSmAction.noticerolename').value='';document.getElementById('slaSmAction.noticeroleid').value='';" />
												
									</td>
								</tr>
								<tr>
									<td class="texttd"><eoms:lable name="sm_lb_noticeusername"/>：</td>
									<td colspan="3">
										<input type="hidden" name="slaSmAction.noticeuserid" value="${slaSmAction.noticeuserid }" id="slaSmAction.noticeuserid"/>
										<input type="text" name="slaSmAction.noticeusername" value="${slaSmAction.noticeusername }" id="slaSmAction.noticeusername" readonly="readonly" class="textInput" style="width:82.5%"/>
										<input type="button" name="button3" id="button3" value="<eoms:lable name='com_btn_choose'/>"
												class="operate_button" onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" onclick="openTree('1');"/>
										<input type="button" name="button3" id="button3" value="<eoms:lable name='com_btn_clear'/>"
												class="operate_button" onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="document.getElementById('slaSmAction.noticeusername').value='';document.getElementById('slaSmAction.noticeuserid').value='';" />
												
									</td>
								</tr>
								<tr>
									<td class="texttd"><eoms:lable name="sm_lb_noticegroupname"/>：</td>
									<td colspan="3">
										<input type="hidden" name="slaSmAction.noticegroupid" value="${slaSmAction.noticegroupid }" id="slaSmAction.noticegroupid"/>
										<input type="text" name="slaSmAction.noticegroupname" value="${slaSmAction.noticegroupname }" id="slaSmAction.noticegroupname" readonly="readonly" class="textInput" style="width:82.5%"/>
										<input type="button" name="button3" id="button3" value="<eoms:lable name='com_btn_choose'/>"
												class="operate_button" onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" onclick="openTree('2');"/>
										<input type="button" name="button3" id="button3" value="<eoms:lable name='com_btn_clear'/>"
												class="operate_button" onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'" 
												onclick="document.getElementById('slaSmAction.noticegroupid').value='';document.getElementById('slaSmAction.noticegroupname').value='';" />
									</td>
								</tr>
								<tr>
									<td class="texttd" valign="top"><eoms:lable name="sm_lb_smcontent"/>：<span class="must">*</span></td>
									<td colspan="3">
										<textarea name="slaSmAction.smcontent" id="textarea2" rows="4" class="textInput">${slaSmAction.smcontent }</textarea>
										<validation id="slaSmAction.smcontentV" dataType="Limit" min="1" max="100" msg="<eoms:lable name='sm_msg_slaTransSmContentConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd" valign="top"><eoms:lable name="com_lb_desc" />：</td>
									<td colspan="3">
										<textarea name="slaSmAction.remark" id="textarea2" rows="4" class="textInput">${slaSmAction.remark }</textarea>
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

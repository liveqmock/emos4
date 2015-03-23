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
				if(""!="${param.saveResult}")
				{
					var serviceType = "${param.serviceType}";
					if("save"==serviceType)
					{
						if("success"=="${param.saveResult}")
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
						if("success"=="${param.saveResult}")
						{
							alert("<eoms:lable name='com_msg_deleteSuccess'/>！");
						}
						else
						{
							alert("<eoms:lable name='com_msg_deleteErr'/>！");
						}
					}
					var refreshId = "${param.refreshId}";
					var actionType = "${param.actionType}";
					parent.document.getElementById('leftFrame2').contentWindow.refreshRule(refreshId, actionType);
				}
			}
			function chooseValue(input_name,input_id,dsType,rpid,formName) //rpid:规则属性ID
			{
				//alert("input_name:"+input_name+"\n"+"input_id:"+input_id+"\ndsType:"+dsType+"\nrpid:"+rpid+"\nformname:"+formName);
				if(dsType == '1' || dsType == '2' || dsType == '3' || dsType == '6')
				{//系统内部变量、字典、脚本
					openwindow('${ctx}/slaActionManager/getValue.action?type='+dsType+'&rpid='+rpid+'&input_name='+input_name+'&input_id='+input_id,'',700,450);
				}
				else if(dsType=="4")
				{//人员树
					openwindow('${ctx}/common/tools/depOrUserSelect.jsp?isRadio=1&isSelectType=1&input_name='+input_name+'&input_id='+input_id,'',350,400);
				}
				else if(dsType=="5")
				{//部门树
					openwindow('${ctx}/common/tools/depOrUserSelect.jsp?isRadio=1&isSelectType=0&input_name='+input_name+'&input_id='+input_id,'',350,400);
				}
			}
			function delRule()
			{
				if(confirm("<eoms:lable name='sm_msg_slaRuleDelConfirm'/>？"))
				{
					window.location.href('${ctx}/slaActionManager/deleteRule.action?ruleId=${slaRule.pid }&modelId=${modelIdPar}&transId=${slaRule.actionid }&actionType=${param.actionType }');
				}
			}
			function createSlaRuleProStr()
			{
				var tb = document.getElementById('proTb');
				var len = tb.rows.length;
				var slaRuleProStr = "";
				for(var i=1;i<len;i++)
				{
					var temp = "";
					var pt = i-1;
					if(document.getElementById('displayValue'+pt).value !="")
					{
						temp += document.getElementById('rpid'+pt).value+"&comm;"; //属性ID
						temp += document.getElementById('value'+pt).value+"&comm;"; //值
						temp += document.getElementById('displayValue'+pt).value+"&comm;"; //显示值
						temp += document.getElementsByName('operator'+pt)[0].value; //操作符
						slaRuleProStr += temp+"&semi;";
					}
				}
				if(slaRuleProStr != "")
				{
					slaRuleProStr = slaRuleProStr.substring(0,slaRuleProStr.lastIndexOf("&semi;"));
				}
				document.getElementById("slaRuleProStr").value = slaRuleProStr;
			}
			function formSubmit()
			{
				if(Validator.Validate(document.forms[0],2))
				{
					if(document.getElementById("slaRule.ruleidentifier").value != "" && ""=="${slaRule.pid }")
					{
						var identifier = document.getElementById("slaRule.ruleidentifier").value;
						$.get("${ctx}/slaActionManager/isRuleIdferUnique.action",{identifier:identifier},function(result)
						{
							if("true"==result)
							{
								createSlaRuleProStr();
								document.forms[0].submit();
							}
							else
							{
								alert("<eoms:lable name='sm_msg_slaRuleIdentifierUniqueRe'/>！");
								return;
							}
						});
					}
					else
					{
						createSlaRuleProStr();
						document.forms[0].submit();
					}
				}
			}
		</script>
	</head>
	<body>
		<form action="${ctx}/slaActionManager/saveRule.action" method="post" name="ruleForm">
		   <input type="hidden" name="slaRuleProStr" value="" id="slaRuleProStr"/>
		   <span style="display:none">modelId:</span><input type="hidden" id="modelIdPar" name="modelIdPar" value="${modelIdPar}"/>
		   <span style="display:none">actionType:</span><input type="hidden" id="actionType" name="actionType" value="${param.actionType }"/>
		   <input type="hidden" id="slaRule.pid" name="slaRule.pid" value="${slaRule.pid }"/>
		   <c:if test="${slaRule.actionid==null }">
		       <span style="display:none">actionId:</span><input type="hidden" name="slaRule.actionid" value="${param.transId }"/>
		   </c:if>
		   <c:if test="${slaRule.actionid!=null }">
		       <span style="display:none">actionId:</span><input type="hidden" name="slaRule.actionid" value="${slaRule.actionid }"/>
		   </c:if>
		   <input type="hidden" id="slaRule.creater" name="slaRule.creater" value="${slaRule.creater }"/>
		   <input type="hidden" id="slaRule.createtime" name="slaRule.createtime" value="${slaRule.createtime }"/>
		   <div class="content">
				<div class="page_div_bg">
					<div class="page_div">
						<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
					  			onmouseout="this.className='page_add_button'" 
					  			onclick="formSubmit();"
					  			text="com_btn_save"/>
					  	<c:if test="${slaRule.pid != null}">
					  		<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" 
		  		  	  			onmouseout="this.className='page_del_button'" text="com_btn_delete" 
		  		  	  			onclick="delRule();"/>
					  	</c:if>
			 	 	</div>
				</div>
				<div class="add_scroll_div_x" id="center">
					<div class="blank_tr"></div>
					<fieldset class="fieldset_style" style="width:90%" id="fieldset_action">
						<legend><eoms:lable name="sm_lb_basicAttribute"/></legend>
						<div class="blank_tr"></div>
							<div class="tabContent_top">
								<table class="add_user">
									<tr>
										<td class="texttd" style="width:15%">
											<eoms:lable name="sm_lb_ruleName"/>：<span class="must">*</span>
										</td>
										<td style="width:35%" colspan="3">
											<input type="text" id="slaRule.rulename" name="slaRule.rulename" value="${slaRule.rulename }" class="textInput" style="width:98.4%"/>
											<validation id="slaRule.rulenameV" dataType="Custom" regexp="^.{1,25}$" msg="<eoms:lable name='sm_msg_slaRuleNameConstraint'/>！" />
										</td>
									</tr>
									<tr>
										<td class="texttd" style="width:15%">
											<eoms:lable name="sm_lb_ruleIndentifier"/>：
										</td>
										<td style="width:35%">
											<c:if test="${slaRule.pid==null }">
												<input type="text" name="slaRule.ruleidentifier" id="slaRule.ruleidentifier" value="${slaRule.ruleidentifier }" class="textInput"/>
											</c:if>
											<c:if test="${slaRule.pid!=null }">
												<input type="text" name="slaRule.ruleidentifier" id="slaRule.ruleidentifier" value="${slaRule.ruleidentifier }" readonly="readonly" class="textInput"/>
											</c:if>
										</td>
										<td class="texttd" style="width:15%">
											<eoms:lable name="sm_lb_pri"/>:
										</td>
										<td>
											<eoms:select name="slaRule.pri" style="select" dataDicTypeCode="slaPri" value="${slaRule.pri}" isnull="false"/>
										</td>
									</tr>
									<tr>
										<td class="texttd">
											<eoms:lable name="com_lb_status"/>：
										</td>
										<td>
											<eoms:select name="slaRule.status" style="select" dataDicTypeCode="status" value="${slaRule.status}" isnull="false"/>
										</td>
										<td class="texttd">
											<eoms:lable name="com_lb_orderNum"/>:<span class="must">*</span>
										</td>
										<td>
											<input type="text" name="slaRule.ordernum" value="${slaRule.ordernum }" class="textInput"/>
											<validation id="slaRule.ordernumV" dataType="Custom" regexp="^[0-9]{1,5}$"
													msg="<eoms:lable name='sm_msg_orderNumConstraint'/>！" />
										</td>
									</tr>
									<tr>
										<td class="texttd" valign="top">
											<eoms:lable name="com_lb_remark"/>：
										</td>
										<td colspan="3">
											<textarea name="slaRule.describe" rows="3" class="textInput" style="width:98.5%">${slaRule.describe }</textarea>
											<validation id="slaRule.describeV" dataType="Custom" regexp="^.{0,150}$" msg="<eoms:lable name='sm_msg_slaRuleRemarkConstraint'/>！" />
										</td>
									</tr>
								</table>
							</div>
						<div class="blank_tr"></div>
					</fieldset>
					<div class="blank_tr"></div>
					<div class="blank_tr"></div>
					<fieldset class="fieldset_style" style="width:90%" id="fieldset_set">
						<legend><eoms:lable name="sm_lb_advancedAttribute"/></legend>
						<div class="blank_tr"></div>
							<div class="tabContent_top">
								<table class="tableborder" id="proTb">
									<tr>
										<!--  
										<th style="width:50px"><eoms:lable name="sm_lb_chooseArea"/></th>
										-->
										<th style="width:25%"><eoms:lable name="sm_lb_AttributeArea"/></th>
										<th style="width:50%" colspan="2"><eoms:lable name="sm_lb_dataArea"/></th>
										<th><eoms:lable name="sm_lb_operateArea"/></th>
									</tr>
									<c:forEach items="${slaRuleProLst}" var="srps" varStatus="sta">
										<tr>
											<!-- 
											<td style="width:50px">
												<c:if test="${srps.isown==true }">
													<input type="checkbox" name="procbx" value="${srps.rpid }" checked="checked"/>
												</c:if>
												<c:if test="${srps.isown==false }">
													<input type="checkbox" name="procbx" value="${srps.rpid }"/>
												</c:if>
											</td>
											-->
											<td style="width:25%">
												<input type="hidden" id="rpid${sta.index}" value="${srps.rpid }"/>
												${srps.fieldname }
											</td>
											<td style="width:15%">
												<c:if test="${srps.inputtype==0}">
													<eoms:select name="operator${sta.index}" style="select" dataDicTypeCode="operator" value="10" isnull="false"/>
												</c:if>
												<c:if test="${srps.inputtype!=0}">
													<eoms:select name="operator${sta.index}" style="select" dataDicTypeCode="operator" value="${srps.slaRuleProperty.operator}" isnull="false"/>
												</c:if>
											</td>
											<c:choose>
												<c:when test="${srps.inputtype==2}">
													<td style="width:35%">
														<input type="text" name="displayValue${sta.index }" id="displayValue${sta.index }" value="${srps.slaRuleProperty.displayvalue}" readonly="readonly" class="textInput"/>
														<input type="hidden" name="value${sta.index }" id="value${sta.index }" value="${srps.slaRuleProperty.value}"/>
													</td>
													<td>
														<input type="button" name="button3" id="button3"
															value="<eoms:lable name="com_btn_choose"/>"
															class="operate_button"
															onmouseover="this.className='operate_button_hover'"
															onmouseout="this.className='operate_button'"
															onclick="chooseValue('displayValue${sta.index }','value${sta.index }','${srps.inputdatasourcetype }','${srps.rpid }','ruleForm');" />
														<input type="button" name="button3" id="button3"
															value="<eoms:lable name="com_btn_clear"/>"
															class="operate_button"
															onmouseover="this.className='operate_button_hover'"
															onmouseout="this.className='operate_button'"
															onclick="document.getElementById('value${sta.index }').value='';document.getElementById('displayValue${sta.index }').value='';" />
													</td>
												</c:when>
												<c:otherwise>
													<td style="width:40%">
														<c:if test="${srps.inputvaluetype==2}"><!-- 等于2的时候为时间类型 -->
															<input type="text" name="displayValue${sta.index }" id="displayValue${sta.index }" class="textInput" 
																onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true});this.className='focus'"
																readonly="readonly" style='width:95%' value="${srps.slaRuleProperty.displayvalue}" 
																onchange="document.getElementById('value${sta.index }').value=document.getElementById('displayValue${sta.index }').value;"/>
														</c:if>
														<c:if test="${srps.inputvaluetype!=2}">
															<input type="text" name="displayValue${sta.index }" id="displayValue${sta.index }" value="${srps.slaRuleProperty.displayvalue}" class="textInput"
														 		onchange="document.getElementById('value${sta.index }').value=document.getElementById('displayValue${sta.index }').value;"/>
														</c:if>
														<input type="hidden" name="value${sta.index }" id="value${sta.index }" value="${srps.slaRuleProperty.value}"/>
													</td>
													<td></td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>
								</table>
							</div>
						<div class="blank_tr"></div>
					</fieldset>
				</div>
			</div>
		</form>
	</body>
</html>

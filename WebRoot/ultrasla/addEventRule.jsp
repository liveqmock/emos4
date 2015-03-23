<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% 
	String ruleid = request.getParameter("ruleid")==null?"":request.getParameter("ruleid");
	String scope  = request.getParameter("scope")==null?"":request.getParameter("scope");//规则范围 form step
	String op     = request.getParameter("op")==null?"":request.getParameter("op");//操作方式 update preview
	String rownum = request.getParameter("rownum")==null?"-1":request.getParameter("rownum");//操作行
	String display = "none";
	if("step".equals(scope)){
		display = "block";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title>UltraSLA事件规则</title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/ultrasla/js/util.js"></script>
		<script type="text/javascript" src="${ctx}/ultrasla/js/slaDefine.js"></script>
		<script language="javascript">
			ctx = '${ctx}';
			window.onresize = function() 
			{
				setCenter(0, LAYOUT_FORM_OPEN);
			}
			window.onload = function() 
			{
				setCenter(0, LAYOUT_FORM_OPEN);
				getPageMenu('div1_1','div1');
				PageMenuActive('div1_1','div1');
				if("none"=="<%=display%>"){
					$("#stepTr").css("display","none");
				}
				optype = "<%=op%>";
				oprownum = parseInt("<%=rownum%>");
				initEventRule("<%=scope%>");
				if('<%=ruleid%>' != '') {
					setEventRuleData('<%=ruleid%>');
				}
			}
		</script>
	</head>

	<body>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg">
						<span class="title_icon2">当前位置：UltraSLA事件规则</span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<form>
					<input type="hidden" value="" id="tempvalue" name="tempvalue"/>
					<fieldset class="fieldset_style">
						<div class="blank_tr"></div>
						<div class="tab_bg">
							<div class="tab_show" id="div1_1" onclick="PageMenuActive('div1_1','div1')">
								<span>基本信息</span>
							</div>
							<div class="tab_hide" id="div1_2" onclick="PageMenuActive('div1_2','div2')">
								<span>通知对象</span>
							</div>
							<div class="tab_hide" id="div1_3" onclick="PageMenuActive('div1_3','div3')">
								<span>通知动作</span>
							</div>
						</div>
						<div class="tabContent">
							<div id="div1">
								<table class="add_user">
									<tr>
										<td style="width:20%;"></td>
										<td style="width:80%;"></td>
									</tr>
									<tr id="stepTr">
										<td>流程环节：</td>
										<td>
											<input type="text" style="width:83%" id="wfstep" name="wfstep"/>
											<input type="hidden" id="wfstep2" name="wfstep2"/>
											<validation id="wfstepV" require="false" dataType="Custom" regexp="^[\w\-]{0,20}$"
												msg="流程环节长度不超过20，只能出现字母、数字、下划线和减号！" />
											<input id="allStep" type="checkbox" value="ALL"/>所有环节
										</td>
									</tr>
									<tr>
										<td>规则名称：<span class="must">*</span></td>
										<td>
											<input type="text" class="textInput" id="rulename" name="rulename"/>
											<validation id="rulenameV" dataType="Limit" min="1" max="30"
															msg="规则名称不能为空，长度不超过30！" />
										</td>
									</tr>
									<tr style="display:none">
										<td>规则类别：</td>
										<td>
											<input type="text" value="workflow" class="textInput" id="ruletype" name="ruletype" readonly="readonly"/>
										</td>
									</tr>
									<tr>
										<td>逾期规则：<span class="must">*</span></td>
										<td>
											<div id="noTrigRuleDiv" style="color:red;border-style:solid;border-color:#E0E0E0;border-width:1px;width:96.7%"
												onmouseover="this.style.borderColor='#ACD6FF'" onmouseout="this.style.borderColor='#E0E0E0'">
												没有逾期规则！
											</div>
											<div id="trigRuleDiv" style="border-style:solid;border-color:#E0E0E0;border-width:1px;width:96.7%;display:none"
												onmouseover="this.style.borderColor='#ACD6FF'" onmouseout="this.style.borderColor='#E0E0E0'">
												<table>
													<!-- 
													<tr>
														<td><input type="radio" name="ovdureRule" value='trigId'/><span>未超时未受理</span></td>
													</tr>
													-->
												</table>
											</div>
											<validation id="ovdureRuleV" dataType="Require"
													msg="请选择逾期规则！" />
										</td>
									</tr>
									<tr>
										<td>逾期时间：<span class="must">*</span></td>
										<td>
											<div style="border-style:solid;border-color:#E0E0E0;border-width:1px;width:96.7%"
												onmouseover="this.style.borderColor='#ACD6FF'" onmouseout="this.style.borderColor='#E0E0E0'">
												【<input type="text" id="overday" name="overday"
													style="width:70px;border-width:1px;border-style:none;text-align:center"/>】
												&nbsp;天
												【<input type="text" id="overhour" name="overhour"
													style="width:70px;border-width:1px;border-style:none;text-align:center"/>】
												&nbsp;小时
												【<input type="text" id="overminute" name="overminute"
													style="width:70px;border-width:1px;border-style:none;text-align:center"/>】
												&nbsp;分钟
											</div>
										</td>
									</tr>
									<tr>
										<td valign="middle">条件表达式：</td>
										<td>
											<div style="border-style:solid;border-color:#E0E0E0;border-width:1px;width:96.7%"
												onmouseover="this.style.borderColor='#ACD6FF'" onmouseout="this.style.borderColor='#E0E0E0'">
												<textarea rows="3" style="width:99.6%" id="condexpress" name="condexpress"
													style="border-top:1px none;border-left:1px none;border-right:1px none"></textarea>
												<validation id="condexpressV" dataType="Limit" max="300"
																	msg="条件表达式长度不超过300！" />
												&nbsp;属性字段：
												<input type="text" style="width:107px;" id="attname"/>
												<select style="width:50px;" id="attrela">
													<option value="=">&nbsp;=&nbsp;</option>
													<option value="&lt;">&nbsp;&lt;&nbsp;</option>
													<option value="&lt;=">&nbsp;&lt;=&nbsp;</option>
													<option value="&gt;">&nbsp;&gt;&nbsp;</option>
													<option value="&gt;=">&nbsp;&gt;=&nbsp;</option>
													<option value="&lt;&gt;">&nbsp;&lt;&gt;&nbsp;</option>
												</select>
												<input type="text" style="width:107px;" id="attvalue"/>
												<br/>
												&nbsp;关系符号：
												<span id="opbtn">
													<input type="button" value=" and " style="width:50px;"/>
													<input type="button" value=" or " style="width:50px;"/>
													<input type="button" value="(" style="width:50px;"/>
													<input type="button" value=")" style="width:50px;"/>
												</span>
												<input type="button" value="增加条件" id="addcond"/>
											</div>
										</td>
									</tr>
									<tr>
										<td>优先级：</td>
										<td>
											<select style="width:97%" id="rulePri" name="rulePri">
												<option value="2">一般</option>
												<option value="1">低</option>
												<option value="3">中</option>
												<option value="4">高</option>
												<option value="5">紧急</option>
											</select>
										</td>
									</tr>
									<tr>
										<td>通知主题：</td>
										<td>
											<input type="text" class="textInput" id="topic" name="topic"/>
											<validation id="topicV" dataType="Limit" max="50"
												msg="通知主题长度不超过50！" />
										</td>
									</tr>
									<tr>
										<td>通知内容：<span class="must">*</span></td>
										<td>
											<textarea rows="3" class="textInput" id="content" name="content"></textarea>
											<validation id="contentV" dataType="Limit" min="1" max="300"
												msg="通知内容不能为空，长度不超过300！" />
										</td>
									</tr>
									<tr>
										<td>内容参数处理：</td>
										<td>
											<select style="width:97%" id="paramprotype" name="paramprotype">
												<option value="0"></option>
												<option value="1">SQL</option>
												<option value="2">实现类</option>
											</select>
										</td>
									</tr>
									<tr>
										<td>内容参数实现：</td>
										<td>
											<input type="text" class="textInput" id="processparam" name="processparam"/>
											<validation id="processparamV" require="false" dataType="Custom" regexp="^[\w\.\$]{0,300}$"
												msg="处理参数长度不超过300，只能出现字母、数字、下划线、英文点、美元符号！" />
										</td>
									</tr>
								</table>
							</div>
							<div id="div2" style="display:none">
								<table class="add_user">
									<tr>
										<td style="width:20%;"></td>
										<td style="width:80%;"></td>
									</tr>
									<tr>
										<td>通知默认人：</td>
										<td>
											<div style="border-style:solid;border-color:#E0E0E0;border-width:1px;width:96.7%"
												onmouseover="this.style.borderColor='#ACD6FF'" onmouseout="this.style.borderColor='#E0E0E0'">
												<input type="radio" name="noticedefault" value="1" checked/>是
												<input type="radio" name="noticedefault" value="0"/>否
											</div>
										</td>
									</tr>
									<tr style="display:none">
										<td>当前处理人查询方式：</td>
										<td>
											<select style="width:97%" id="defaultchecktype" name="defaultchecktype">
												<option value="0"></option>
												<option value="1">SQL</option>
												<option value="2">实现类</option>
											</select>
										</td>
									</tr>
									<tr style="display:none">
										<td>当前处理人查询实现：</td>
										<td>
											<input type="text" class="textInput" id="defaultcheckparam" name="defaultcheckparam"/>
											<validation id="defaultcheckparamV" require="false" dataType="Custom" regexp="^[\w\.\$]{0,300}$"
												msg="当前处理对象查询参数长度不超过300，只能出现字母、数字、下划线、英文点、美元符号！" />
										</td>
									</tr>
									<tr>
										<td>通知到：</td>
										<td>
											<input type="text" style="width:85.5%" id="noticeto_show" name="noticeto_show" readonly="readonly"/>
											<validation id="noticeto_showV" require="false" dataType="Custom" regexp="^.{0,180}$"
												msg="通知到长度不超过180！" />
											<input type="hidden" id="noticeto" name="noticeto"/>
											<input type="button" value="选择" id="noticeBtn"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'"/>
										</td>
									</tr>
									<tr>
										<td>抄送给：</td>
										<td>
											<input type="text" style="width:85.5%" id="duplicateto_show" name="duplicateto_show" readonly="readonly"/>
											<validation id="duplicateto_showV" require="false" dataType="Custom" regexp="^.{0,180}$"
												msg="抄送给长度不超过180，只能是字母、数字、下划线、减号和中文！" />
											<input type="hidden" id="duplicateto" name="duplicateto"/>
											<input type="button" value="选择" id="duplicateBtn"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'"/>
										</td>
									</tr>
									<tr>
										<td>升级到：</td>
										<td>
											<input type="text" style="width:85.5%" id="upgradeto_show" name="upgradeto_show" readonly="readonly"/>
											<validation id="upgradeto_showV" require="false" dataType="Custom" regexp="^.{0,180}$"
												msg="升级到长度不超过180，只能是字母、数字、下划线、减号和中文！" />
											<input type="hidden" id="upgradeto" name="upgradeto"/>
											<input type="button" value="选择" id="upgradeBtn"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'"/>
										</td>
									</tr>
									<tr>
										<td>固定通知电话：</td>
										<td>
											<input type="text" style="width:85.5%" id="fixedmobile" name="fixedmobile"/>
											<validation id="fixedmobileV" require="false" dataType="Custom" regexp="^[0-9\,]{0,239}$"
												msg="固定通知电话只能是以','分割的电话号码，最多只能通知20个手机号！" />
											<input type="hidden"/><!-- 用于与上列按钮对其ie6 -->
											<input type="button" value="清空" id="fixedmobileBtn"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'"/>
										</td>
									</tr>
									<tr>
										<td>固定通知邮件：</td>
										<td>
											<input type="text" style="width:85.5%" id="fixedmail" name="fixedmail"/>
											<validation id="fixedmailV" require="false" dataType="Custom" regexp="^[\w\@\,\.]{0,240}$"
												msg="固定通知邮件只能是以','分割，邮箱地址总长度不能超过240个字符！" />
											<input type="hidden"/><!-- 用于与上列按钮对其ie6 -->
											<input type="button" value="清空" id="fixedmailBtn"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'"/>
										</td>
									</tr>
									<tr>
										<td>自定义通知对象：</td>
										<td>
											<div id="noCompSpan" style="color:red;border-style:solid;border-color:#E0E0E0;border-width:1px;width:96.7%"
												onmouseover="this.style.borderColor='#ACD6FF'" onmouseout="this.style.borderColor='#E0E0E0'">
												没有自定义通知组件！
											</div>
											<div id="selfCompDiv" style="border-style:solid;border-color:#E0E0E0;border-width:1px;width:96.7%;display:none"
												onmouseover="this.style.borderColor='#ACD6FF'" onmouseout="this.style.borderColor='#E0E0E0'">
												<table>
													<!-- 
													<tr>
														<td><input type="checkbox" name="selfDefineComp" value="123"/>自定义通知组件1</td>
													</tr>
													-->
												</table>
											</div>
										</td>
									</tr>
								</table>
							</div>
							<div id="div3" style="display:none">
								<table class="add_user">
									<tr>
										<td style="width:20%;"></td>
										<td style="width:80%;"></td>
									</tr>
									<tr>
										<td>动作类型：<span class="must">*</span></td>
										<td>
											<select style="width:97%" id="actiontype" name="actiontype">
												<option value="1">短信通知</option>
												<option value="2">邮件通知</option>
												<option value="3">短线及邮件通知</option>
												<option value="9">自定义处理</option>
											</select>
										</td>
									</tr>
									<tr id="actionparamRow">
										<td>自定义处理参数：</td>
										<td>
											<input type="text" class="textInput" id="actionparam" name="actionparam"/>
											<validation id="actionparamV" require="false" dataType="Custom" regexp="^[\w\.\$]{0,300}$"
												msg="动作参数长度不超过300，只能出现字母、数字、下划线、英文点、美元符号！" />
										</td>
									</tr>
									<tr>
										<td>通知次数：<span class="must">*</span></td>
										<td>
											<input type="text" value="1" class="textInput" id="noticetimes" name="noticetimes"/>
											<validation id="noticetimesV" require="true" dataType="Number"
												msg="通知次数必须为数字！" />
										</td>
									</tr>
									<tr>
										<td>时间间隔（分钟）：<span class="must">*</span></td>
										<td>
											<input type="text" value="0" class="textInput" id="noticeinterval" name="noticeinterval"/>
											<validation id="noticeintervalV" require="true" dataType="Number"
												msg="时间间隔必须为数字！" />
										</td>
									</tr>
									<tr style="display:none">
										<td>通知时间段：<span class="must">*</span></td>
										<td>
											<div style="border-style:solid;border-color:#E0E0E0;border-width:1px;width:96.7%"
												onmouseover="this.style.borderColor='#ACD6FF'" onmouseout="this.style.borderColor='#E0E0E0'">
												【<input id="noticestarttime" name="noticestarttime"
													type="text" value="00:00" maxlength="8" readonly="readonly" 
													onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});"
													style="width:70px;border-width:1px;border-style:none;text-align:center"/>】
												<validation id="noticestarttimeV" dataType="Require"
													msg="通知开始时间不能为空！" />
												&nbsp;到
												【<input id="noticeendtime" name="noticeendtime"
													type="text" value="23:59" maxlength="8" readonly="readonly"
													onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});"
													style="width:70px;border-width:1px;border-style:none;text-align:center"/>】
												<validation id="noticeendtimeV" dataType="Require"
													msg="通知结束时间不能为空！" />
											</div>
										</td>
									</tr>
									<tr style="display:none">
										<td>是否补发：</td>
										<td>
											<div style="border-style:solid;border-color:#E0E0E0;border-width:1px;width:96.7%"
												onmouseover="this.style.borderColor='#ACD6FF'" onmouseout="this.style.borderColor='#E0E0E0'">
												<input type="radio" name="overduresend" value="1"/>是
												<input type="radio" name="overduresend" value="0" checked/>否
											</div>
										</td>
									</tr>
									<tr>
										<td>节假日类别：</td>
										<td>
											<eoms:select name="calendartype" style="select" dataDicTypeCode="workCalendarType"/>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</fieldset>
				</form>
				<div class="blank_tr"></div>
			</div>
		</div>
		<div class="add_bottom">
			<input id="addRuleBtn" type="button" class="save_button"
				value="确定"
				onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'"/>
			<input id="cancelBtn" type="button" class="cancel_button"
				value="取消"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"/>
		</div>
	</body>
</html>
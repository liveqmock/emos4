<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% 
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
		<title>UltraSLA时限规则</title>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/ultrasla/js/util.js"></script>
		<script type="text/javascript" src="${ctx}/ultrasla/js/slaDefine.js"></script>
		<script language="javascript">
			ctx = '${ctx}';
			window.onload = function()
			{
				setCenter(0, LAYOUT_FORM_OPEN);
				if("none"=="<%=display%>"){
					$("#stepTr").css("display","none");
				}
				optype = "<%=op%>";
				oprownum = parseInt("<%=rownum%>");
				initTimeRule("<%=scope%>");
			}
			window.onresize = function() 
			{
				setCenter(0, LAYOUT_FORM_OPEN);
			}
		</script>
	</head>
  
  <body>
    <div class="content">
    	<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
					<span class="title_icon2">
						当前位置：UltraSLA时限规则
					</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div class="add_scroll_div_x" id="center">
			<form>
				<fieldset class="fieldset_style">
					<div class="blank_tr"></div>
					<div class="tabContent" style="border-style:solid">
						<table class="add_user">
							<tr>
								<td style="width:20%"></td>
								<td style="width:80%"></td>
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
															msg="规则名称不能为空，且长度不超过30！" />
								</td>
							</tr>
							<tr style="display:none">
								<td>规则类别：</td>
								<td>
									<input type="text" class="textInput" id="ruletype" name="ruletype" value="workflow" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<td valign="middle">条件表达式：<span class="must">*</span></td>
								<td>
									<div style="border-style:solid;border-color:#E0E0E0;border-width:1px;width:96.7%"
										onmouseover="this.style.borderColor='#ACD6FF'" onmouseout="this.style.borderColor='#E0E0E0'">
										<textarea rows="3" style="width:99.6%" id="condexpress" name="condexpress"
											style="border-top:1px none;border-left:1px none;border-right:1px none"></textarea>
										<validation id="condexpressV" dataType="Limit" min="1" max="300"
																	msg="条件表达式不能为空，且长度不超过300！" />
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
								<td>受理时限：<span class="must">*</span></td>
								<td align="left" colspan="3">
									<div style="border-style:solid;border-color:#E0E0E0;border-width:1px;width:96.7%"
										onmouseover="this.style.borderColor='#ACD6FF'" onmouseout="this.style.borderColor='#E0E0E0'">
										【<input type="text" id="accday" name="accday"
											style="width:70px;border-width:1px;border-style:none;text-align:center"/>】
										&nbsp;天
										【<input type="text" id="acchour" name="acchour"
											style="width:70px;border-width:1px;border-style:none;text-align:center"/>】
										&nbsp;小时
										【<input type="text" id="accminute" name="accminute"
											style="width:70px;border-width:1px;border-style:none;text-align:center"/>】
										&nbsp;分钟
									</div>
								</td>
							</tr>
							<tr>
								<td>处理时限：<span class="must">*</span></td>
								<td align="left" colspan="3">
									<div style="border-style:solid;border-color:#E0E0E0;border-width:1px;width:96.7%"
										onmouseover="this.style.borderColor='#ACD6FF'" onmouseout="this.style.borderColor='#E0E0E0'">
										【<input type="text" id="proday" name="proday"
											style="width:70px;border-width:1px;border-style:none;text-align:center"/>】
										&nbsp;天
										【<input type="text" id="prohour" name="prohour"
											style="width:70px;border-width:1px;border-style:none;text-align:center"/>】
										&nbsp;小时
										【<input type="text" id="prominute" name="prominute"
											style="width:70px;border-width:1px;border-style:none;text-align:center"/>】
										&nbsp;分钟
									</div>
								</td>
							</tr>
						</table>
					</div>
				</fieldset>
			</form>
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
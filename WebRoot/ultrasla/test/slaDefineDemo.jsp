<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<style type="text/css">
			.op_rela_symbol{
				width:30px;
				height:15px;
				margin-right:2px;
				border:solid 1px #9D9D9D;
				float:left;
				cursor:pointer;
				background-color:#9D9D9D;
			}
			.op_rela_symbol_hover{
				width:30px;
				height:15px;
				margin-right:2px;
				border:solid 1px #000;
				float:left;
				cursor:pointer;
				background-color:#9D9D9D;
			}
			fieldset{
				border:1px #d2e5fe solid;
				width:95%;
			}
			legend{
				font-weight:bold;
			}
			hr{
				width:98%;
			}
		</style>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascritp" src="${ctx}/slm/slaDefine.js"></script>
		<script language="javascript">
			window.onload = function()
			{
				setCenter(0,30);
			}
			window.onresize = function() 
			{
				setCenter(0,30);
			}
		</script>
	</head>
	<body>
	   <div class="content">
			<div class="page_div_bg">
				<div class="page_div">
					<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" text="com_btn_save"/>
		 	 	</div>
			</div>
			<div id="center" class="add_scroll_div_x" align="center" style="overflow-y:scroll;overflow-x:hidden;">
				<!-- 流程服务水平协议（SLA）描述_Start -->
				<fieldset>
					<legend>流程服务水平协议（SLA）描述</legend>
					<div class="blank_tr"></div>
					<table class="add_user" style="width:98%;">
						<tr>
							<td class="texttd" style="width:15%">
							</td>
							<td style="width:35%">
							</td>
							<td class="texttd" style="width:15%">
							</td>
							<td style="width:35%">
							</td>
						</tr>
						<tr>
							<td class="texttd">
								SLA名称：
							</td>
							<td colspan="3" align="left">
								<input type="text" class="textInput"  style="width:98.8%"/>
							</td>
						</tr>
						<tr>
							<td class="texttd">
								有效时间从：
							</td>
							<td align="left">
								<input type="text" maxlength="8" readonly="readonly" class="textInput"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true});"/>
							</td>
							<td class="texttd">
								到：
							</td>
							<td>
								<input type="text" maxlength="8" readonly="readonly" class="textInput"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true});"/>
							</td>
						</tr>
						<tr>
							<td class="texttd">
								状态：
							</td>
							<td align="left">
								<eoms:select name="status"style="select" dataDicTypeCode="status" isnull="false"/>
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">
								描述：
							</td>
							<td colspan="3" rowspan="2" align="left">
								<textarea rows="3" style="width:98.7%"></textarea>
							</td>
						</tr>
					</table>
					<div class="blank_tr"></div>
				</fieldset>
				<!-- 流程服务水平协议（SLA）描述_End -->
				<div class="blank_tr"></div>
				<div class="blank_tr"></div>
				<!-- 流程服务水平协议（SLA）规则_Start -->
				<fieldset>
					<legend>流程服务水平协议（SLA）规则</legend>
					<div class="blank_tr"></div>
					<table class="tableborder" style="width:98%">
						<tr>
							<th style="font-weight:normal;width:50px;">序号</th>
							<th style="font-weight:normal">规则</th>
							<th style="font-weight:normal">受理时限（分钟）</th>
							<th style="font-weight:normal">处理时限（分钟）</th>
							<th style="font-weight:normal;width:130px">操作</th>
						</tr>
						<tr align="left">
							<td>1</td><td>网络分类="交换" AND 告警级别="紧急"</td><td>60</td><td>120</td>
							<td>
								<a style="text-decoration:underline" href="#">修改</a>
								<a style="text-decoration:underline" href="#">预览</a>
								<a style="text-decoration:underline" href="#">删除</a>
							</td>
						</tr>
						<tr align="left">
							<td>2</td><td>网络分类="传输" AND 告警级别="一般"</td><td>120</td><td>300</td>
							<td>
								<a style="text-decoration:underline" href="#">修改</a>
								<a style="text-decoration:underline" href="#">预览</a>
								<a style="text-decoration:underline" href="#">删除</a>
							</td>
						</tr>
					</table>
					<hr/>
					<table class="add_user">
						<tr>
							<td class="texttd" style="width:15%">
								属性字段：
							</td>
							<td style="width:35%" align="left">
								<select style="width:100px;">
									<option>网络分类</option>
									<option>告警级别</option>
									<option>故障地市</option>
								</select>
								<select style="width:50px;">
									<option>&nbsp;=&nbsp;</option>
									<option>&nbsp;&lt;&nbsp;</option>
									<option>&nbsp;&gt;&nbsp;</option>
									<option>&nbsp;&lt;&gt;&nbsp;</option>
								</select>
								<input type="text" style="width:100px;"/>
							</td>
							<td class="texttd" style="width:15%">
								关系符号：
							</td>
							<td style="width:35%" align="left">
								<input type="button" value="AND" style="width:35px;"/>
								<input type="button" value="OR" style="width:35px;"/>
								<input type="button" value="(" style="width:35px;"/>
								<input type="button" value=")" style="width:35px;"/>
								<input type="button" value="增加条件"/>
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">
								条件匹配标准：
							</td>
							<td colspan="3" align="left">
								<textarea rows="3" style="width:98.8%"></textarea>
							</td>
						</tr>
						<tr>
							<td class="texttd">
								受理时限：
							</td>
							<td align="left">
								<input type="text" class="textInput"/>
							</td>
							<td class="texttd">
								处理时限：
							</td>
							<td>
								<input type="text" class="textInput"/>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="right">
								<input type="button" value="&nbsp;加入规则&nbsp;"/>
							</td>
						</tr>
					</table>
					<div class="blank_tr"></div>
				</fieldset>
				<!-- 流程服务水平协议（SLA）规则_End -->
				<div class="blank_tr"></div>
				<div class="blank_tr"></div>
				<!-- 流程环节（OLA）规则_Start -->
				<fieldset>
					<legend>流程环节（OLA）规则</legend>
					<div class="blank_tr"></div>
					<table class="tableborder" style="width:98%">
						<tr>
							<th style="font-weight:normal;width:50px;">序号</th>
							<th style="font-weight:normal">环节号</th>
							<th style="font-weight:normal">规则</th>
							<th style="font-weight:normal">受理时限（分钟）</th>
							<th style="font-weight:normal">处理时限（分钟）</th>
							<th style="font-weight:normal;width:130px">操作</th>
						</tr>
						<tr align="left">
							<td>1</td><td>dp_01</td><td>网络分类="交换" AND 告警级别="紧急"</td><td>60</td><td>120</td>
							<td>
								<a style="text-decoration:underline" href="#">修改</a>
								<a style="text-decoration:underline" href="#">预览</a>
								<a style="text-decoration:underline" href="#">删除</a>
							</td>
						</tr>
						<tr align="left">
							<td>2</td><td>dp_02</td><td>网络分类="传输" AND 告警级别="一般"</td><td>120</td><td>300</td>
							<td>
								<a style="text-decoration:underline" href="#">修改</a>
								<a style="text-decoration:underline" href="#">预览</a>
								<a style="text-decoration:underline" href="#">删除</a>
							</td>
						</tr>
					</table>
					<hr/>
					<table class="add_user">
						<tr>
							<td class="texttd" style="width:15%">
							</td>
							<td style="width:35%">
							</td>
							<td class="texttd" style="width:15%">
							</td>
							<td style="width:35%">
							</td>
						</tr>
						<tr>
							<td class="texttd">流程环节</td>
							<td colspan="3" align="left">
								<select class="select" style="width:99.7%">
									<option>dp_01</option>
									<option>dp_02</option>
									<option>dp_03</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="texttd">
								属性字段：
							</td>
							<td align="left">
								<select style="width:100px;">
									<option>网络分类</option>
									<option>告警级别</option>
									<option>故障地市</option>
								</select>
								<select style="width:50px;">
									<option>&nbsp;=&nbsp;</option>
									<option>&nbsp;&lt;&nbsp;</option>
									<option>&nbsp;&gt;&nbsp;</option>
									<option>&nbsp;&lt;&gt;&nbsp;</option>
								</select>
								<input type="text" style="width:100px;"/>
							</td>
							<td class="texttd">
								关系符号：
							</td>
							<td align="left">
								<input type="button" value="AND" style="width:35px;"/>
								<input type="button" value="OR" style="width:35px;"/>
								<input type="button" value="(" style="width:35px;"/>
								<input type="button" value=")" style="width:35px;"/>
								<input type="button" value="增加条件"/>
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">
								条件匹配标准：
							</td>
							<td colspan="3" align="left">
								<textarea rows="3" style="width:98.8%"></textarea>
							</td>
						</tr>
						<tr>
							<td class="texttd">
								受理时限：
							</td>
							<td align="left">
								<input type="text" class="textInput"/>
							</td>
							<td class="texttd">
								处理时限：
							</td>
							<td>
								<input type="text" class="textInput"/>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="right">
								<input type="button" value="&nbsp;加入规则&nbsp;"/>
							</td>
						</tr>
					</table>
					<div class="blank_tr"></div>
				</fieldset>
				<!-- 流程环节（OLA）规则_End -->
				<div class="blank_tr"></div>
				<div class="blank_tr"></div>
				<!-- 流程SLA逾期通知或升级策略_Start -->
				<fieldset>
					<legend>流程SLA逾期通知或升级策略</legend>
					<div class="blank_tr"></div>
					<table class="tableborder" style="width:98%">
						<tr>
							<th style="font-weight:normal;width:50px;">序号</th>
							<th style="font-weight:normal">规则</th>
							<th style="font-weight:normal">逾期时长（分钟）</th>
							<th style="font-weight:normal">逾期策略</th>
							<th style="font-weight:normal;width:130px">操作</th>
						</tr>
						<tr align="left">
							<td>1</td><td>未超时未受理</td><td>60</td><td>邮件发送通知</td>
							<td>
								<a style="text-decoration:underline" href="#">修改</a>
								<a style="text-decoration:underline" href="#">预览</a>
								<a style="text-decoration:underline" href="#">删除</a>
							</td>
						</tr>
						<tr align="left">
							<td>2</td><td>已超时未处理</td><td>120</td><td>短信发送通知</td>
							<td>
								<a style="text-decoration:underline" href="#">修改</a>
								<a style="text-decoration:underline" href="#">预览</a>
								<a style="text-decoration:underline" href="#">删除</a>
							</td>
						</tr>
					</table>
					<hr/>
					<table class="add_user">
						<tr>
							<td class="texttd" style="width:15%;"></td><td></td>
						</tr>
						<tr>
							<td class="texttd">规则名称：</td>
							<td align="left"><input type="text" style="width:285px;"/></td>
						</tr>
						<tr>
							<td class="texttd">规则类别：</td>
							<td align="left"><input type="text" style="width:285px;"/></td>
						</tr>
						<tr>
							<td class="texttd" style="width:15%;" valign="top">逾期规则：</td>
							<td align="left">
								未受理逾期规则：
								<input type="radio" name="ovdureRule_form"/>未超时未受理
								<input type="radio" name="ovdureRule_form"/>已超时未受理
								<br/>
								未处理逾期规则：
								<input type="radio" name="ovdureRule_form"/>未超时未处理
								<input type="radio" name="ovdureRule_form"/>已超时未处理
							</td>
						</tr>
						<tr>
							<td class="texttd">逾期时间：</td>
							<td align="left">
								<input type="text"/>&nbsp;&nbsp;天
								<input type="text"/>&nbsp;&nbsp;小时
								<input type="text"/>&nbsp;&nbsp;分钟
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">逾期策略：</td>
							<td align="left">
								通知当前处理对象：
								<input type="radio" name="notice_default_ug_form" value="1" checked/>是
								<input type="radio" name="notice_default_ug_form" value="0"/>否
								<br/>
								当前处理对象查询方式：
								<select style="width:150px;">
									<option></option>
									<option>SQL</option>
									<option>实现类</option>
								</select>
								<br/>
								当前处理对象查询参数：
								<input type="text" style="width:275px;"/>
								<br/>
								通知到：&nbsp;<input type="text" style="width:300px;"/>
								&nbsp;<input type="button" value=" 选择 "/>
								<br/>
								抄送给：&nbsp;<input type="text" style="width:300px;"/>
								&nbsp;<input type="button" value=" 选择 "/>
								<br/>
								升级到：&nbsp;<input type="text" style="width:300px;"/>
								&nbsp;<input type="button" value=" 选择 "/>
								<br/>
								<a href="#" style="color:blue;">自定义通知对象</a>：
								<input type="checkbox"/>当前处理人二级经理
								<input type="checkbox"/>其他
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">策略动作：</td>
							<td align="left">
								动作类型：
								<select style="width:150px;">
									<option>短信通知</option>
									<option>邮件通知</option>
									<option>短线及邮件通知</option>
									<option>自定义处理</option>
								</select>
								<br/>
								动作参数：
								<input type="text" style="width:275px;"/>
								<br/>
								通知次数：
								<input type="text" value="1" style="width:116px"/>
								<br/>
								时间间隔（分钟）：
								<input type="text" value="0" style="width:116px"/>
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">通知时间段：</td>
							<td align="left">
								<input type="text" value="00:00" maxlength="8" readonly="readonly" style="width:100px"
									onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});"/>
								&nbsp;到&nbsp;
								<input type="text" value="23:59" maxlength="8" readonly="readonly" style="width:100px"
									onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});"/>
								<br/>
								是否补发：
								<input type="radio" name="overdure_send_form" value="1"/>是
								<input type="radio" name="overdure_send_form" value="0" checked/>否
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">内容参数处理：</td>
							<td align="left">
								处理方式：
								<select style="width:150px;">
									<option></option>
									<option>SQL</option>
									<option>实现类</option>
								</select>
								<br/>
								动作参数：
								<input type="text" style="width:275px;"/>
							</td>
						</tr>
						<tr>
							<td class="texttd">主题：</td>
							<td align="left">
								<input type="text" class="textInput" style="width:98.8%"/>
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">内容：</td>
							<td align="left">
								<textarea rows="3" style="width:98.8%"></textarea>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="right">
								<input type="button" value="&nbsp;加入规则&nbsp;"/>
							</td>
						</tr>
					</table>
					<div class="blank_tr"></div>
				</fieldset>
				<!-- 流程SLA逾期通知或升级策略_End -->
				<div class="blank_tr"></div>
				<div class="blank_tr"></div>
				<!-- 流程环节OLA逾期通知或升级策略_Start -->
				<fieldset>
					<legend>流程环节OLA逾期通知或升级策略</legend>
					<div class="blank_tr"></div>
					<table class="tableborder" style="width:98%">
						<tr>
							<th style="font-weight:normal;width:50px;">序号</th>
							<th style="font-weight:normal">流程环节</th>
							<th style="font-weight:normal">规则</th>
							<th style="font-weight:normal">逾期时长（分钟）</th>
							<th style="font-weight:normal">逾期策略</th>
							<th style="font-weight:normal;width:130px">操作</th>
						</tr>
						<tr align="left">
							<td>1</td><td>dp_01</td><td>未超时未受理</td><td>60</td><td>邮件发送通知</td>
							<td>
								<a style="text-decoration:underline" href="#">修改</a>
								<a style="text-decoration:underline" href="#">预览</a>
								<a style="text-decoration:underline" href="#">删除</a>
							</td>
						</tr>
						<tr align="left">
							<td>2</td><td>dp_02</td><td>已超时未处理</td><td>120</td><td>短信发送通知</td>
							<td>
								<a style="text-decoration:underline" href="#">修改</a>
								<a style="text-decoration:underline" href="#">预览</a>
								<a style="text-decoration:underline" href="#">删除</a>
							</td>
						</tr>
					</table>
					<hr/>
					<table class="add_user">
						<tr>
							<td class="texttd" style="width:15%;"></td><td></td>
						</tr>
						<tr>
							<td class="texttd" valign="top">流程环节：</td>
							<td align="left"><input type="text" style="width:285px;"/></td>
						</tr>
						<tr>
							<td class="texttd">规则名称：</td>
							<td align="left"><input type="text" style="width:285px;"/></td>
						</tr>
						<tr>
							<td class="texttd">规则类别：</td>
							<td align="left"><input type="text" style="width:285px;"/></td>
						</tr>
						<tr>
							<td class="texttd" style="width:15%;" valign="top">逾期规则：</td>
							<td align="left">
								未受理逾期规则：
								<input type="radio" name="ovdureRule_form"/>未超时未受理
								<input type="radio" name="ovdureRule_form"/>已超时未受理
								<br/>
								未处理逾期规则：
								<input type="radio" name="ovdureRule_form"/>未超时未处理
								<input type="radio" name="ovdureRule_form"/>已超时未处理
							</td>
						</tr>
						<tr>
							<td class="texttd">逾期时间：</td>
							<td align="left">
								<input type="text"/>&nbsp;&nbsp;天
								<input type="text"/>&nbsp;&nbsp;小时
								<input type="text"/>&nbsp;&nbsp;分钟
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">逾期策略：</td>
							<td align="left">
								通知当前处理对象：
								<input type="radio" name="notice_default_ug_form" value="1" checked/>是
								<input type="radio" name="notice_default_ug_form" value="0"/>否
								<br/>
								当前处理对象查询方式：
								<select style="width:150px;">
									<option></option>
									<option>SQL</option>
									<option>实现类</option>
								</select>
								<br/>
								当前处理对象查询参数：
								<input type="text" style="width:275px;"/>
								<br/>
								通知到：&nbsp;<input type="text" style="width:300px;"/>
								&nbsp;<input type="button" value=" 选择 "/>
								<br/>
								抄送给：&nbsp;<input type="text" style="width:300px;"/>
								&nbsp;<input type="button" value=" 选择 "/>
								<br/>
								升级到：&nbsp;<input type="text" style="width:300px;"/>
								&nbsp;<input type="button" value=" 选择 "/>
								<br/>
								<a href="#" style="color:blue;">自定义通知对象</a>：
								<input type="checkbox"/>当前处理人二级经理
								<input type="checkbox"/>其他
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">策略动作：</td>
							<td align="left">
								动作类型：
								<select style="width:150px;">
									<option>短信通知</option>
									<option>邮件通知</option>
									<option>短线及邮件通知</option>
									<option>自定义处理</option>
								</select>
								<br/>
								动作参数：
								<input type="text" style="width:275px;"/>
								<br/>
								通知次数：
								<input type="text" value="1" style="width:116px"/>
								<br/>
								时间间隔（分钟）：
								<input type="text" value="0" style="width:116px"/>
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">通知时间段：</td>
							<td align="left">
								<input type="text" value="00:00" maxlength="8" readonly="readonly" style="width:100px"
									onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});"/>
								&nbsp;到&nbsp;
								<input type="text" value="23:59" maxlength="8" readonly="readonly" style="width:100px"
									onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});"/>
								<br/>
								是否补发：
								<input type="radio" name="overdure_send_form" value="1"/>是
								<input type="radio" name="overdure_send_form" value="0" checked/>否
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">内容参数处理：</td>
							<td align="left">
								处理方式：
								<select style="width:150px;">
									<option></option>
									<option>SQL</option>
									<option>实现类</option>
								</select>
								<br/>
								动作参数：
								<input type="text" style="width:275px;"/>
							</td>
						</tr>
						<tr>
							<td class="texttd">主题：</td>
							<td align="left">
								<input type="text" class="textInput" style="width:98.8%"/>
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">内容：</td>
							<td align="left">
								<textarea rows="3" style="width:98.8%"></textarea>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="right">
								<input type="button" value="&nbsp;加入规则&nbsp;"/>
							</td>
						</tr>
					</table>
					<div class="blank_tr"></div>
				</fieldset>
				<!-- 流程环节OLA逾期通知或升级策略_End -->
			</div>
	   </div>
	</body>
</html>

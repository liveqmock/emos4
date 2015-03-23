<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title>添加/修改规则属性</title>
		<script type="text/javascript" src="${ctx}/common/javascript/Map.js"></script>
		<script language="javascript">
		window.onresize = function() {
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		
		window.onload = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		
		function formSubmit(){
			if(Validator.Validate(document.forms[0],2)){//字段验证
				var fieldid = trim(document.getElementById("slaTplProperty.fieldid").value);
				var fieldname = trim(document.getElementById("slaTplProperty.fieldname").value);
				var inputtype = trim(document.getElementById("slaTplProperty.inputtype").value);
				var inputvaluetype = trim(document.getElementById("slaTplProperty.inputvaluetype").value);
				var inputdatasourcetype = trim(document.getElementById("slaTplProperty.inputdatasourcetype").value);
				var indata = trim(document.getElementById("slaTplProperty.indata").value);
				var status = trim(document.getElementById("slaTplProperty.status").value);
				var ordernum = trim(document.getElementById("slaTplProperty.ordernum").value);
				var describe = trim(document.getElementById("slaTplProperty.describe").value);
				
				map = new Map();
				map.put('fieldid',fieldid);
				map.put('fieldname',fieldname);
				map.put('inputtype',inputtype);
				map.put('inputvaluetype',inputvaluetype);
				map.put('inputdatasourcetype',inputdatasourcetype);
				map.put('indata',indata);
				map.put('status',status);
				map.put('ordernum',ordernum);
				map.put('describe',describe);
				
				var pid = document.getElementById("slaTplProperty.pid").value;
				if(pid!=""){
					map.put('pid',pid);
					window.opener.modifyRowData(map);
				}else{
					window.opener.addRowData(map);
				}
				window.close();
			}
		}
		
		function intypeCon(){//输入类型 字段值事件
			var intype = document.getElementById("slaTplProperty.inputtype").value;
			if(intype=="0"){//当选择变量域, 输入数据类型 输入数据值 不可写
				document.getElementById("slaTplProperty.indata").value = "";
				document.getElementById("slaTplProperty.inputdatasourcetype").value = "";
				document.getElementById("slaTplProperty.inputvaluetype").disabled = true;
				document.getElementById("slaTplProperty.indata").disabled = true;
			 	document.getElementById("slaTplProperty.inputdatasourcetype").disabled = true;
			}else if(intype=="1")	{//当选择文本域, 输入数据类型 输入数据值 不可写
				document.getElementById("slaTplProperty.indata").value = "";
				document.getElementById("slaTplProperty.inputdatasourcetype").value = "";
				document.getElementById("slaTplProperty.indata").disabled = true;
			 	document.getElementById("slaTplProperty.inputdatasourcetype").disabled = true;
			 	document.getElementById("slaTplProperty.inputvaluetype").disabled = false;
			}else{
				document.getElementById("slaTplProperty.indata").disabled = false;
				document.getElementById("slaTplProperty.inputdatasourcetype").disabled = false;
			}
		}
		
		function indatasourceCon(){//输入数据类型 字段值事件
			var indatasourcetype = document.getElementById("slaTplProperty.inputdatasourcetype").value;
			if(indatasourcetype!=""){
				document.getElementById("slaTplProperty.inputtype").value = '2';
				
				if(indatasourcetype=="1" || indatasourcetype=="4" || indatasourcetype=="5"){
				document.getElementById("slaTplProperty.indata").value = "";
				document.getElementById("slaTplProperty.indata").disabled = true;
				}else{
					document.getElementById("slaTplProperty.indata").disabled = false;
				}
			}
		}
		
		function trim(str){//删除左右两端的空格
　　			return str.replace(/(^\s*)|(\s*$)/g, "");
　　		}
	</script>
	</head>

	<body>
	  <form method="post" name="addSlaRuleTplProperty" >
		<c:if test="${slaTplProperty.pid!=null}">
			<input id="slaTplProperty.pid" type="hidden" name="slaTplProperty.pid" value="${slaTplProperty.pid}"/>
		</c:if>
		<c:if test="${slaTplProperty.pid==null}">
			<input id="slaTplProperty.pid" type="hidden" name="slaTplProperty.pid" value=""/>
		</c:if>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"><span class="title_icon2">添加/修改规则属性</span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend>
						<eoms:lable name="com_lb_basicInfo"/>
					</legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
						<table class="add_user">
							<tr>
								<td class="texttd" width="15%">
									<eoms:lable name="sm_lb_propertiesID"/>： <span class="must">*</span>
								</td>
								<td width="35%">
									<input type="text" id="slaTplProperty.fieldid" name="slaTplProperty.fieldid" class="textInput" value="${slaTplProperty.fieldid}" />
									<validation id="slaTplProperty.fieldidV" dataType="Custom" regexp="^.{1,20}$" msg="<eoms:lable name='sm_msg_resPropertyFieldname' />！" />
								</td>
								<td class="texttd" width="15%">
									<eoms:lable name="sm_lb_propertiesName"/>：<span class="must">*</span>
								</td>
								<td width="35%">
									<input type="text" id="slaTplProperty.fieldname" name="slaTplProperty.fieldname" value="${slaTplProperty.fieldname}" class="textInput" />
									<validation id="slaTplProperty.fieldnameV" dataType="Custom" regexp="^.{1,50}$" msg="<eoms:lable name='sm_msg_resPropertyFielddisplayvalue' />！" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="sm_lb_propertiesInputType"/>：
								</td>
						        <td>
						        	<eoms:select  name="slaTplProperty.inputtype" dataDicTypeCode="slaintype" style="select" onChangeFun="intypeCon()" value="${slaTplProperty.inputtype}" ></eoms:select>
								</td>
								<td class="texttd">
									<eoms:lable name="sm_lb_propertiesInputValueType"/>：
								</td>
								<td>
									<eoms:select  name="slaTplProperty.inputvaluetype" dataDicTypeCode="invaluetype" style="select" value="${slaTplProperty.inputvaluetype}" ></eoms:select>
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="sm_lb_propertiesInputDataType"/>：
								</td>
								<td>
								    <eoms:select  name="slaTplProperty.inputdatasourcetype" dataDicTypeCode="indatasourtype" style="select" onChangeFun="indatasourceCon()" value="${slaTplProperty.inputdatasourcetype}" ></eoms:select>
								</td>
								<td class="texttd">
									<eoms:lable name="sm_lb_propertiesInputDataPara"/>：
								</td>
								<td>
									<input type="text" id="slaTplProperty.indata" name="slaTplProperty.indata" value="${slaTplProperty.indata}" class="textInput" />
									<validation id="slaTplProperty.indataV" dataType="Custom" regexp="^.{0,500}$" msg="<eoms:lable name="sm_msg_resPropertyIndata" />" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
										<eoms:lable name="com_lb_status"/>：
									</td>
									<td>
										<select name="slaTplProperty.status" id="slaTplProperty.status" class="select">
											<c:choose>
												<c:when test="${slaTplProperty.status==1}">
														<option value="1" selected><eoms:lable name="com_btn_inuse" /></option>
														<option value="0"><eoms:lable name="com_btn_outuse" /></option>
												</c:when>
												<c:when test="${slaTplProperty.status==0}">
														<option value="1"><eoms:lable name="com_btn_inuse" /></option>
														<option value="0" selected><eoms:lable name="com_btn_outuse" /></option>
												</c:when>
												<c:otherwise>
														<option value="1" selected><eoms:lable name="com_btn_inuse" /></option>
														<option value="0"><eoms:lable name="com_btn_outuse" /></option>
												</c:otherwise>
											    </c:choose>
										</select>
								</td>
								<td class="texttd">
									<eoms:lable name="com_lb_orderNum"/>：<span class="must">*</span>
								</td>
								<td>
									<input type="text" id="slaTplProperty.ordernum" name="slaTplProperty.ordernum" value="${slaTplProperty.ordernum}" class="textInput" />
									<validation id="slaTplProperty.ordernumV" dataType="Custom" regexp="^[0-9]{1,5}$" msg="<eoms:lable name='sm_msg_orderNumConstraint'/>!" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="com_lb_remark"/>：
								</td>
								<td colspan="3" rowspan="2">
									<textarea name="slaTplProperty.describe" id="slaTplProperty.describe" rows="5" style="width:98.7%">${slaTplProperty.describe}</textarea>
									<validation id="slaTplProperty.describeV" dataType="Custom" regexp="^.{0,200}$" msg="<eoms:lable name='sm_msg_reamrk'/>" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
				</fieldset>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_confirm"/>" 
				class="save_button" onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="formSubmit();" />
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	  </form>
	</body>
</html>
